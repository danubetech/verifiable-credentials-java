package com.danubetech.verifiablecredentials.jwt;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.bitcoinj.core.ECKey;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jwt.JWTClaimsSet;

import info.weboftrust.ldsignatures.crypto.ByteSigner;
import info.weboftrust.ldsignatures.crypto.ByteVerifier;
import info.weboftrust.ldsignatures.crypto.adapter.JWSSignerAdapter;
import info.weboftrust.ldsignatures.crypto.adapter.JWSVerifierAdapter;
import info.weboftrust.ldsignatures.crypto.impl.Ed25519_EdDSA_PrivateKeySigner;
import info.weboftrust.ldsignatures.crypto.impl.Ed25519_EdDSA_PublicKeyVerifier;
import info.weboftrust.ldsignatures.crypto.impl.RSA_RS256_PrivateKeySigner;
import info.weboftrust.ldsignatures.crypto.impl.RSA_RS256_PublicKeyVerifier;
import info.weboftrust.ldsignatures.crypto.impl.secp256k1_ES256K_PrivateKeySigner;
import info.weboftrust.ldsignatures.crypto.impl.secp256k1_ES256K_PublicKeyVerifier;

abstract class JwtObject <T> {

	private final JWTClaimsSet payload;
	private final T payloadObject;

	private JWSObject jwsObject;
	private String compactSerialization;

	protected JwtObject(JWTClaimsSet payload, T payloadObject, JWSObject jwsObject, String compactSerialization) {

		if (payload == null) throw new NullPointerException();
		if (payloadObject == null) throw new NullPointerException();

		this.payload = payload;
		this.payloadObject = payloadObject;
		this.jwsObject = jwsObject;
		this.compactSerialization = compactSerialization;
	}

	/*
	 * Sign
	 */

	private String sign(JWSSigner jwsSigner, JWSAlgorithm alg) throws JOSEException {

		JWSHeader jwsHeader = new JWSHeader.Builder(alg).build();
		JWSObject jwsObject = new EscapedSlashWorkaroundJWSObject(jwsHeader, this.getPayload());

		jwsObject.sign(jwsSigner);

		this.jwsObject = jwsObject;
		this.compactSerialization = jwsObject.serialize();

		return this.compactSerialization;
	}

	public String sign_RSA_RS256(ByteSigner signer) throws JOSEException {

		return this.sign(new JWSSignerAdapter(signer, JWSAlgorithm.RS256), JWSAlgorithm.RS256);
	}

	public String sign_RSA_RS256(RSAPrivateKey privateKey) throws JOSEException {

		return this.sign_RSA_RS256(new RSA_RS256_PrivateKeySigner(privateKey));
	}

	public String sign_RSA_RS256(com.nimbusds.jose.jwk.RSAKey privateKey) throws JOSEException {

		return this.sign(new com.nimbusds.jose.crypto.RSASSASigner(privateKey), JWSAlgorithm.RS256);
	}

	public String sign_Ed25519_EdDSA(ByteSigner signer) throws JOSEException {

		return this.sign(new JWSSignerAdapter(signer, JWSAlgorithm.EdDSA), JWSAlgorithm.EdDSA);
	}

	public String sign_Ed25519_EdDSA(byte[] privateKey) throws JOSEException {

		return this.sign_Ed25519_EdDSA(new Ed25519_EdDSA_PrivateKeySigner(privateKey));
	}

	public String sign_Ed25519_EdDSA(com.nimbusds.jose.jwk.OctetKeyPair privateKey) throws JOSEException {

		return this.sign(new com.nimbusds.jose.crypto.Ed25519Signer(privateKey), JWSAlgorithm.EdDSA);
	}

	public String sign_secp256k1_ES256K(ByteSigner signer) throws JOSEException {

		return this.sign(new JWSSignerAdapter(signer, JWSAlgorithm.ES256K), JWSAlgorithm.ES256K);
	}

	public String sign_secp256k1_ES256K(ECKey privateKey) throws JOSEException {

		return this.sign_secp256k1_ES256K(new secp256k1_ES256K_PrivateKeySigner(privateKey));
	}

	public String sign_secp256k1_ES256K(com.nimbusds.jose.jwk.ECKey privateKey) throws JOSEException {

		return this.sign(new com.nimbusds.jose.crypto.ECDSASigner(privateKey), JWSAlgorithm.ES256K);
	}

	/*
	 * Verify
	 */

	private boolean verify(JWSVerifier jwsVerifier) throws JOSEException {

		return this.jwsObject.verify(jwsVerifier);
	}

	public boolean verify_RSA_RS256(ByteVerifier verifier) throws JOSEException {

		return this.verify(new JWSVerifierAdapter(verifier, JWSAlgorithm.RS256));
	}

	public boolean verify_RSA_RS256(RSAPublicKey publicKey) throws JOSEException {

		return this.verify_RSA_RS256(new RSA_RS256_PublicKeyVerifier(publicKey));
	}

	public boolean verify_RSA_RS256(com.nimbusds.jose.jwk.RSAKey publicKey) throws JOSEException {

		return this.verify(new com.nimbusds.jose.crypto.RSASSAVerifier(publicKey));
	}

	public boolean verify_Ed25519_EdDSA(ByteVerifier verifier) throws JOSEException {

		return this.verify(new JWSVerifierAdapter(verifier, JWSAlgorithm.EdDSA));
	}

	public boolean verify_Ed25519_EdDSA(byte[] publicKey) throws JOSEException {

		return this.verify_Ed25519_EdDSA(new Ed25519_EdDSA_PublicKeyVerifier(publicKey));
	}

	public boolean verify_Ed25519_EdDSA(com.nimbusds.jose.jwk.OctetKeyPair publicKey) throws JOSEException {

		return this.verify(new com.nimbusds.jose.crypto.Ed25519Verifier(publicKey));
	}

	public boolean verify_secp256k1_ES256K(ByteVerifier verifier) throws JOSEException {

		return this.verify(new JWSVerifierAdapter(verifier, JWSAlgorithm.ES256K));
	}

	public boolean verify_secp256k1_ES256K(ECKey publicKey) throws JOSEException {

		return this.verify_secp256k1_ES256K(new secp256k1_ES256K_PublicKeyVerifier(publicKey));
	}

	public boolean verify_secp256k1_ES256K(com.nimbusds.jose.jwk.ECKey publicKey) throws JOSEException {

		return this.verify(new com.nimbusds.jose.crypto.ECDSAVerifier(publicKey));
	}

	/*
	 * Helper class
	 */

	private static class EscapedSlashWorkaroundJWSObject extends JWSObject {

		private static final long serialVersionUID = -587898962717783109L;

		public EscapedSlashWorkaroundJWSObject(final JWSHeader header, final JWTClaimsSet claimsSet) {

			super(header, new Payload(claimsSet.toJSONObject().toJSONString().replace("\\/", "/")));
		}
	}

	/*
	 * Getters
	 */

	public JWTClaimsSet getPayload() {

		return this.payload;
	}

	public T getPayloadObject() {

		return this.payloadObject;
	}

	public JWSObject getJwsObject() {

		return this.jwsObject;
	}

	public String getCompactSerialization() {

		return this.compactSerialization;
	}
}
