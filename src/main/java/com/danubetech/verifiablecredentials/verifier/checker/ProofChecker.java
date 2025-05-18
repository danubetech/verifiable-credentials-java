package com.danubetech.verifiablecredentials.verifier.checker;

import com.danubetech.dataintegrity.DataIntegrityProof;
import com.danubetech.dataintegrity.suites.DataIntegritySuite;
import com.danubetech.dataintegrity.suites.DataIntegritySuites;
import com.danubetech.dataintegrity.verifier.LdVerifier;
import com.danubetech.dataintegrity.verifier.LdVerifierRegistry;
import com.danubetech.did.util.VerificationMethodUtil;
import com.danubetech.keyformats.JWK_to_PublicKey;
import com.danubetech.keyformats.crypto.PublicKeyVerifier;
import com.danubetech.keyformats.crypto.PublicKeyVerifierFactory;
import com.danubetech.keyformats.jose.JWK;
import com.danubetech.keyformats.jose.KeyTypeName;
import com.danubetech.uniresolver.local.extensions.impl.FragmentDIDExtension;
import com.danubetech.uniresolver.local.extensions.impl.FragmentJSONExtension;
import com.danubetech.uniresolver.local.extensions.impl.FragmentJSONLDExtension;
import com.danubetech.verifiablecredentials.verifier.VerifyingException;
import com.danubetech.verifiablecredentials.verifier.result.VerifyResult;
import foundation.identity.did.VerificationMethod;
import foundation.identity.did.representations.Representations;
import foundation.identity.jsonld.JsonLDException;
import foundation.identity.jsonld.JsonLDObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uniresolver.DereferencingException;
import uniresolver.ResolutionException;
import uniresolver.UniResolver;
import uniresolver.local.LocalUniDereferencer;
import uniresolver.result.DereferenceResult;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Map;

public abstract class ProofChecker<I extends JsonLDObject> extends AbstractChecker<I, Void> implements Checker<I, Void> {

	private static Logger log = LoggerFactory.getLogger(ProofChecker.class);

	private UniResolver uniResolver;

	public ProofChecker(String checkName, UniResolver uniResolver) {
		super(checkName);
		this.uniResolver = uniResolver;
	}

	@Override
	public Void checkInternal(JsonLDObject jsonLDObject, VerifyResult verifyResult) throws VerifyingException {

		// look up proof from Verifiable Credential

		DataIntegrityProof dataIntegrityProof = DataIntegrityProof.getFromJsonLDObject(jsonLDObject);
		if (dataIntegrityProof == null) throw new VerifyingException("No proof.");
		this.getCheckMetadata(verifyResult).put("proofType", dataIntegrityProof.getType());

		// look up cryptosuite

		String cryptosuite = dataIntegrityProof.getCryptosuite();
		this.getCheckMetadata(verifyResult).put("cryptosuite", cryptosuite);

		// look up verification method URI

		URI verificationMethodUri = dataIntegrityProof.getVerificationMethod();
		if (verificationMethodUri == null) throw new VerifyingException("No verification method URI for proof " + dataIntegrityProof);
		this.getCheckMetadata(verifyResult).put("verificationMethod", verificationMethodUri);

		// dereference verification method

		LocalUniDereferencer localUniDereferencer = new LocalUniDereferencer();
		localUniDereferencer.setUniResolver(this.getUniResolver());
		localUniDereferencer.getExtensions().add(new FragmentDIDExtension());
		localUniDereferencer.getExtensions().add(new FragmentJSONLDExtension());
		localUniDereferencer.getExtensions().add(new FragmentJSONExtension());

		VerificationMethod verificationMethod = null;

		try {
			DereferenceResult dereferenceResult = localUniDereferencer.dereference(verificationMethodUri.toString(), Map.of("accept", Representations.DEFAULT_MEDIA_TYPE));
			if (dereferenceResult.getContent() == null || dereferenceResult.getContent().length < 1) {
				throw new VerifyingException("No dereferencing result for " + verificationMethodUri);
			}

			verificationMethod = VerificationMethod.fromJson(new InputStreamReader(new ByteArrayInputStream(dereferenceResult.getContent()), StandardCharsets.UTF_8));
			if (log.isDebugEnabled()) log.debug("Found verification method for " + verificationMethodUri + ": " + verificationMethod.toJson());
			this.getCheckMetadata(verifyResult).put("verificationMethodType", verificationMethod.getType());
		} catch (ResolutionException | DereferencingException ex) {
			throw new VerifyingException("Cannot dereference URI " + verificationMethodUri + ": " + ex.getMessage(), ex);
		}

		// prepare LD verifier

		LdVerifier<?> ldVerifier;

		KeyTypeName verificationMethodKeyTypeName = findKeyTypeNameForVerificationMethod(verificationMethod);
		this.getCheckMetadata(verifyResult).put("verificationMethodKeyType", verificationMethodKeyTypeName);

		DataIntegritySuite dataIntegritySuite = findDataIntegritySuiteByLdProof(dataIntegrityProof);
		this.getCheckMetadata(verifyResult).put("dataIntegritySuite", dataIntegritySuite.getTerm());

		String jwsAlgorithm = findJwsAlgorithmByDataIntegritySuiteAndVerificationMethodKeyTypeName(dataIntegritySuite, verificationMethodKeyTypeName, cryptosuite);
		this.getCheckMetadata(verifyResult).put("jwsAlgorithm", jwsAlgorithm);

		JWK verificationMethodJwk = findVerificationMethodJwkByVerificationMethod(verificationMethod);
		this.getCheckMetadata(verifyResult).put("verificationMethodJwk", verificationMethodJwk.toMap());

		Object publicKey = findPublicKeyByVerificationMethodJwk(verificationMethodJwk);

		PublicKeyVerifier<?> publicKeyVerifier = findPublicKeyVerifierForVerificationMethodKeyTypeNameAndJwsAlgorithmAndPublicKey(verificationMethodKeyTypeName, jwsAlgorithm, publicKey);

		ldVerifier = findLdVerifierForDataIntegritySuite(dataIntegritySuite);
		ldVerifier.setVerifier(publicKeyVerifier);

		// verify

		try {

			if (! ldVerifier.verify(jsonLDObject)) throw new VerifyingException("Cannot verify signature.");
			if (log.isInfoEnabled()) log.info("Verified signature: " + dataIntegrityProof);
		} catch (JsonLDException | IOException ex) {

			throw new VerifyingException("Cannot verify signature (JSON-LD problem): " + ex.getMessage(), ex);
		} catch (GeneralSecurityException ex) {

			throw new VerifyingException("Cannot verify signature (security problem): " + ex.getMessage(), ex);
		}

		// done

		return null;
	}

	/*
	 * Helper methods
	 */

	public static KeyTypeName findKeyTypeNameForVerificationMethod(VerificationMethod verificationMethod) throws VerifyingException {
		KeyTypeName verificationMethodKeyTypeName = verificationMethod == null ? null : VerificationMethodUtil.getKeyTypeName(verificationMethod);
		if (verificationMethodKeyTypeName == null) throw new VerifyingException("No key type name for verification method " + (verificationMethod == null ? null : verificationMethod.getId()));
		if (log.isDebugEnabled()) log.debug("For verification method " + verificationMethod.getId() + " found key type name: " + verificationMethodKeyTypeName);
		return verificationMethodKeyTypeName;
	}

	public static DataIntegritySuite findDataIntegritySuiteByLdProof(DataIntegrityProof dataIntegrityProof) throws VerifyingException {
		DataIntegritySuite dataIntegritySuite = dataIntegrityProof == null ? null : DataIntegritySuites.findDataIntegritySuiteByTerm(dataIntegrityProof.getType());
		if (dataIntegritySuite == null) throw new VerifyingException("No data integrity suite for proof type " + (dataIntegrityProof == null ? null : dataIntegrityProof.getType()));
		if (log.isDebugEnabled()) log.debug("For proof type " + dataIntegrityProof.getType() + " found data integrity suite " + dataIntegritySuite.getId());
		return dataIntegritySuite;
	}

	public static String findJwsAlgorithmByDataIntegritySuiteAndVerificationMethodKeyTypeName(DataIntegritySuite dataIntegritySuite, KeyTypeName verificationMethodKeyTypeName, String cryptosuite) throws VerifyingException {
		String jwsAlgorithm = dataIntegritySuite == null ? null : dataIntegritySuite.findDefaultJwsAlgorithmForKeyTypeName(verificationMethodKeyTypeName, cryptosuite);
		if (jwsAlgorithm == null) throw new VerifyingException("No JWS algorithm for data integrity suite " + (dataIntegritySuite == null ? null : dataIntegritySuite.getId()) + " and key type name " + verificationMethodKeyTypeName);
		if (log.isDebugEnabled()) log.debug("For data integrity suite " + (dataIntegritySuite == null ? null : dataIntegritySuite.getId()) + " and key type name " + verificationMethodKeyTypeName + " found JWS algorithm: " + jwsAlgorithm);
		return jwsAlgorithm;
	}

	public static JWK findVerificationMethodJwkByVerificationMethod(VerificationMethod verificationMethod) throws VerifyingException {
		JWK verificationMethodJwk = verificationMethod == null ? null : VerificationMethodUtil.getVerificationMethodJwk(verificationMethod);
		if (verificationMethodJwk == null) throw new VerifyingException("No verification method JWK for verification method " + (verificationMethod == null ? null : verificationMethod.getId()));
		if (log.isDebugEnabled()) log.debug("For verification method " + verificationMethod.getId() + " found verification method JWK: " + verificationMethodJwk);
		return verificationMethodJwk;
	}

	public static Object findPublicKeyByVerificationMethodJwk(JWK verificationMethodJwk) throws VerifyingException {
		Object publicKey = verificationMethodJwk == null ? null : JWK_to_PublicKey.JWK_to_anyPublicKey(verificationMethodJwk);
		if (publicKey == null) throw new VerifyingException("No public key for verification method JWK " + verificationMethodJwk);
		if (log.isDebugEnabled()) log.debug("For verification method JWK " + verificationMethodJwk + " found public key " + publicKey.getClass());
		return publicKey;
	}

	public static PublicKeyVerifier<?> findPublicKeyVerifierForVerificationMethodKeyTypeNameAndJwsAlgorithmAndPublicKey(KeyTypeName verificationMethodKeyTypeName, String jwsAlgorithm, Object publicKey) {
		PublicKeyVerifier<?> publicKeyVerifier = verificationMethodKeyTypeName == null ? null : PublicKeyVerifierFactory.publicKeyVerifierForKey(verificationMethodKeyTypeName, jwsAlgorithm, publicKey);
		if (log.isDebugEnabled()) log.debug("For key type name " + verificationMethodKeyTypeName + " and JWS algorithm " + jwsAlgorithm + " and public key " + (publicKey == null ? null : publicKey.getClass()) + " found public key verifier " + (publicKeyVerifier == null ? null : publicKeyVerifier.getClass()));
		return publicKeyVerifier;
	}

	public static LdVerifier<?> findLdVerifierForDataIntegritySuite(DataIntegritySuite dataIntegritySuite) throws VerifyingException {
		LdVerifier<?> ldVerifier = dataIntegritySuite == null ? null : LdVerifierRegistry.getLdVerifierByDataIntegritySuite(dataIntegritySuite);
		if (ldVerifier == null) throw new VerifyingException("No LD verifier for data integrity suite " + (dataIntegritySuite == null ? null : dataIntegritySuite.getId()));
		if (log.isDebugEnabled()) log.debug("For data integrity suite " + dataIntegritySuite.getId() + " found LD verifier " + ldVerifier.getClass());
		return ldVerifier;
	}

	/*
	 * Getters and setters
	 */

	public UniResolver getUniResolver() {
		return uniResolver;
	}

	public void setUniResolver(UniResolver uniResolver) {
		this.uniResolver = uniResolver;
	}
}
