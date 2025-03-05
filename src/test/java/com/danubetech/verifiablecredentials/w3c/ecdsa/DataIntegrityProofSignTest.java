package com.danubetech.verifiablecredentials.w3c.ecdsa;

import com.danubetech.dataintegrity.DataIntegrityProof;
import com.danubetech.dataintegrity.signer.DataIntegrityProofLdSigner;
import com.danubetech.dataintegrity.verifier.DataIntegrityProofLdVerifier;
import com.danubetech.keyformats.PrivateKeyBytes;
import com.danubetech.keyformats.PublicKeyBytes;
import com.danubetech.keyformats.crypto.ByteSigner;
import com.danubetech.keyformats.crypto.ByteVerifier;
import com.danubetech.keyformats.crypto.impl.P_256_ES256_PrivateKeySigner;
import com.danubetech.keyformats.crypto.impl.P_256_ES256_PublicKeyVerifier;
import com.danubetech.keyformats.crypto.impl.P_384_ES384_PrivateKeySigner;
import com.danubetech.keyformats.crypto.impl.P_384_ES384_PublicKeyVerifier;
import com.danubetech.keyformats.crypto.provider.*;
import com.danubetech.keyformats.crypto.provider.impl.*;
import com.danubetech.verifiablecredentials.VerifiableCredentialV2;
import com.danubetech.verifiablecredentials.util.CitizenshipContexts;
import com.danubetech.verifiablecredentials.util.TestUtil;
import com.danubetech.verifiablecredentials.validation.Validation;
import foundation.identity.jsonld.JsonLDUtils;
import io.ipfs.multibase.Multibase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.net.URI;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataIntegrityProofSignTest {

	final static byte[] privateKeyCredential1;
	final static byte[] privateKeyCredential2;
	final static byte[] privateKeyCredential3;
	final static byte[] privateKeyCredential4;
	final static byte[] privateKeyCredential5;
	final static byte[] privateKeyCredential6;

	final static byte[] publicKeyCredential1;
	final static byte[] publicKeyCredential2;
	final static byte[] publicKeyCredential3;
	final static byte[] publicKeyCredential4;
	final static byte[] publicKeyCredential5;
	final static byte[] publicKeyCredential6;

	final static VerifiableCredentialV2 verifiableCredentialGood1;
	final static VerifiableCredentialV2 verifiableCredentialGood2;
	final static VerifiableCredentialV2 verifiableCredentialGood3;
	final static VerifiableCredentialV2 verifiableCredentialGood4;
	final static VerifiableCredentialV2 verifiableCredentialGood5;
	final static VerifiableCredentialV2 verifiableCredentialGood6;

	static {

		try {

			privateKeyCredential1 = TestUtil.removeMulticodec(Multibase.decode("z42twTcNeSYcnqg1FLuSFs2bsGH3ZqbRHFmvS9XMsYhjxvHN"));
			privateKeyCredential2 = TestUtil.removeMulticodec(Multibase.decode("z42twTcNeSYcnqg1FLuSFs2bsGH3ZqbRHFmvS9XMsYhjxvHN"));
			privateKeyCredential3 = TestUtil.removeMulticodec(Multibase.decode("z2fanyY7zgwNpZGxX5fXXibvScNaUWNprHU9dKx7qpVj7mws9J8LLt4mDB5TyH2GLHWkUc"));
			privateKeyCredential4 = TestUtil.removeMulticodec(Multibase.decode("z2fanyY7zgwNpZGxX5fXXibvScNaUWNprHU9dKx7qpVj7mws9J8LLt4mDB5TyH2GLHWkUc"));
			privateKeyCredential5 = TestUtil.removeMulticodec(Multibase.decode("z42twTcNeSYcnqg1FLuSFs2bsGH3ZqbRHFmvS9XMsYhjxvHN"));
			privateKeyCredential6 = TestUtil.removeMulticodec(Multibase.decode("z2fanyY7zgwNpZGxX5fXXibvScNaUWNprHU9dKx7qpVj7mws9J8LLt4mDB5TyH2GLHWkUc"));

			publicKeyCredential1 = TestUtil.removeMulticodec(Multibase.decode("zDnaepBuvsQ8cpsWrVKw8fbpGpvPeNSjVPTWoq6cRqaYzBKVP"));
			publicKeyCredential2 = TestUtil.removeMulticodec(Multibase.decode("zDnaepBuvsQ8cpsWrVKw8fbpGpvPeNSjVPTWoq6cRqaYzBKVP"));
			publicKeyCredential3 = TestUtil.removeMulticodec(Multibase.decode("z82LkuBieyGShVBhvtE2zoiD6Kma4tJGFtkAhxR5pfkp5QPw4LutoYWhvQCnGjdVn14kujQ"));
			publicKeyCredential4 = TestUtil.removeMulticodec(Multibase.decode("z82LkuBieyGShVBhvtE2zoiD6Kma4tJGFtkAhxR5pfkp5QPw4LutoYWhvQCnGjdVn14kujQ"));
			publicKeyCredential5 = TestUtil.removeMulticodec(Multibase.decode("zDnaepBuvsQ8cpsWrVKw8fbpGpvPeNSjVPTWoq6cRqaYzBKVP"));
			publicKeyCredential6 = TestUtil.removeMulticodec(Multibase.decode("z82LkuBieyGShVBhvtE2zoiD6Kma4tJGFtkAhxR5pfkp5QPw4LutoYWhvQCnGjdVn14kujQ"));

			verifiableCredentialGood1 = VerifiableCredentialV2.fromJson(new InputStreamReader(Objects.requireNonNull(DataIntegrityProofSignTest.class.getResourceAsStream("unsigned.good.DataIntegrityProof.1.jsonld"))));
			verifiableCredentialGood2 = VerifiableCredentialV2.fromJson(new InputStreamReader(Objects.requireNonNull(DataIntegrityProofSignTest.class.getResourceAsStream("unsigned.good.DataIntegrityProof.2.jsonld"))));
			verifiableCredentialGood3 = VerifiableCredentialV2.fromJson(new InputStreamReader(Objects.requireNonNull(DataIntegrityProofSignTest.class.getResourceAsStream("unsigned.good.DataIntegrityProof.3.jsonld"))));
			verifiableCredentialGood4 = VerifiableCredentialV2.fromJson(new InputStreamReader(Objects.requireNonNull(DataIntegrityProofSignTest.class.getResourceAsStream("unsigned.good.DataIntegrityProof.4.jsonld"))));
			verifiableCredentialGood5 = VerifiableCredentialV2.fromJson(new InputStreamReader(Objects.requireNonNull(DataIntegrityProofSignTest.class.getResourceAsStream("unsigned.good.DataIntegrityProof.5.jsonld"))));
			verifiableCredentialGood6 = VerifiableCredentialV2.fromJson(new InputStreamReader(Objects.requireNonNull(DataIntegrityProofSignTest.class.getResourceAsStream("unsigned.good.DataIntegrityProof.6.jsonld"))));

			verifiableCredentialGood2.setDocumentLoader(CitizenshipContexts.CITIZENSHIP_DOCUMENT_LOADER);
			verifiableCredentialGood4.setDocumentLoader(CitizenshipContexts.CITIZENSHIP_DOCUMENT_LOADER);
		} catch (Exception ex) {

			throw new RuntimeException(ex.getMessage(), ex);
		}
	}

	@BeforeEach
	public void before() {

		RandomProvider.set(new JavaRandomProvider());
		SHA256Provider.set(new JavaSHA256Provider());
		SHA384Provider.set(new JavaSHA384Provider());
		SHA512Provider.set(new JavaSHA512Provider());
		Ed25519Provider.set(new TinkEd25519Provider());
	}

	@Test
	void testValidity() {

		Validation.validate(verifiableCredentialGood1);
		Validation.validate(verifiableCredentialGood2);
		Validation.validate(verifiableCredentialGood3);
		Validation.validate(verifiableCredentialGood4);
		Validation.validate(verifiableCredentialGood5);
	}

	/*
	 * CREDENTIAL
	 */

	@Test
	void testSignCredential1() throws Exception {
		DataIntegrityProofLdSigner signer = new DataIntegrityProofLdSigner(byteSignerP256(privateKeyCredential1));
		DataIntegrityProofLdVerifier verifier = new DataIntegrityProofLdVerifier(byteVerifierP256(publicKeyCredential1));
		signer.setCryptosuite("ecdsa-rdfc-2019");
		signer.setCreated(JsonLDUtils.stringToDate("2023-02-24T23:36:38Z"));
		signer.setProofPurpose("assertionMethod");
		signer.setVerificationMethod(URI.create("did:key:zDnaepBuvsQ8cpsWrVKw8fbpGpvPeNSjVPTWoq6cRqaYzBKVP#zDnaepBuvsQ8cpsWrVKw8fbpGpvPeNSjVPTWoq6cRqaYzBKVP"));
		DataIntegrityProof dataIntegrityProof = signer.sign(verifiableCredentialGood1);
		assertTrue(verifier.verify(verifiableCredentialGood1));
	}

	@Test
	void testSignCredential2() throws Exception {
		DataIntegrityProofLdSigner signer = new DataIntegrityProofLdSigner(byteSignerP256(privateKeyCredential2));
		DataIntegrityProofLdVerifier verifier = new DataIntegrityProofLdVerifier(byteVerifierP256(publicKeyCredential2));
		signer.setCryptosuite("ecdsa-rdfc-2019");
		signer.setCreated(JsonLDUtils.stringToDate("2023-02-24T23:36:38Z"));
		signer.setProofPurpose("assertionMethod");
		signer.setVerificationMethod(URI.create("did:key:zDnaepBuvsQ8cpsWrVKw8fbpGpvPeNSjVPTWoq6cRqaYzBKVP#zDnaepBuvsQ8cpsWrVKw8fbpGpvPeNSjVPTWoq6cRqaYzBKVP"));
		DataIntegrityProof dataIntegrityProof = signer.sign(verifiableCredentialGood2);
		assertTrue(verifier.verify(verifiableCredentialGood2));
	}

	@Test
	void testSignCredential3() throws Exception {
		DataIntegrityProofLdSigner signer = new DataIntegrityProofLdSigner(byteSignerP384(privateKeyCredential3));
		DataIntegrityProofLdVerifier verifier = new DataIntegrityProofLdVerifier(byteVerifierP384(publicKeyCredential3));
		signer.setCryptosuite("ecdsa-rdfc-2019");
		signer.setCreated(JsonLDUtils.stringToDate("2023-02-24T23:36:38Z"));
		signer.setProofPurpose("assertionMethod");
		signer.setVerificationMethod(URI.create("did:key:z82LkuBieyGShVBhvtE2zoiD6Kma4tJGFtkAhxR5pfkp5QPw4LutoYWhvQCnGjdVn14kujQ#z82LkuBieyGShVBhvtE2zoiD6Kma4tJGFtkAhxR5pfkp5QPw4LutoYWhvQCnGjdVn14kujQ"));
		DataIntegrityProof dataIntegrityProof = signer.sign(verifiableCredentialGood3);
		assertTrue(verifier.verify(verifiableCredentialGood3));
	}

	@Test
	void testSignCredential4() throws Exception {
		DataIntegrityProofLdSigner signer = new DataIntegrityProofLdSigner(byteSignerP384(privateKeyCredential4));
		DataIntegrityProofLdVerifier verifier = new DataIntegrityProofLdVerifier(byteVerifierP384(publicKeyCredential4));
		signer.setCryptosuite("ecdsa-rdfc-2019");
		signer.setCreated(JsonLDUtils.stringToDate("2023-02-24T23:36:38Z"));
		signer.setProofPurpose("assertionMethod");
		signer.setVerificationMethod(URI.create("did:key:z82LkuBieyGShVBhvtE2zoiD6Kma4tJGFtkAhxR5pfkp5QPw4LutoYWhvQCnGjdVn14kujQ#z82LkuBieyGShVBhvtE2zoiD6Kma4tJGFtkAhxR5pfkp5QPw4LutoYWhvQCnGjdVn14kujQ"));
		DataIntegrityProof dataIntegrityProof = signer.sign(verifiableCredentialGood4);
		assertTrue(verifier.verify(verifiableCredentialGood4));
	}

	@Test
	void testSignCredential5() throws Exception {
		DataIntegrityProofLdSigner signer = new DataIntegrityProofLdSigner(byteSignerP256(privateKeyCredential5));
		DataIntegrityProofLdVerifier verifier = new DataIntegrityProofLdVerifier(byteVerifierP256(publicKeyCredential5));
		signer.setCryptosuite("ecdsa-jcs-2019");
		signer.setCreated(JsonLDUtils.stringToDate("2023-02-24T23:36:38Z"));
		signer.setProofPurpose("assertionMethod");
		signer.setVerificationMethod(URI.create("did:key:zDnaepBuvsQ8cpsWrVKw8fbpGpvPeNSjVPTWoq6cRqaYzBKVP#zDnaepBuvsQ8cpsWrVKw8fbpGpvPeNSjVPTWoq6cRqaYzBKVP"));
		DataIntegrityProof dataIntegrityProof = signer.sign(verifiableCredentialGood5);
		assertTrue(verifier.verify(verifiableCredentialGood5));
	}

	@Test
	void testSignCredential6() throws Exception {
		DataIntegrityProofLdSigner signer = new DataIntegrityProofLdSigner(byteSignerP384(privateKeyCredential6));
		DataIntegrityProofLdVerifier verifier = new DataIntegrityProofLdVerifier(byteVerifierP384(publicKeyCredential6));
		signer.setCryptosuite("ecdsa-jcs-2019");
		signer.setCreated(JsonLDUtils.stringToDate("2023-02-24T23:36:38Z"));
		signer.setProofPurpose("assertionMethod");
		signer.setVerificationMethod(URI.create("did:key:z82LkuBieyGShVBhvtE2zoiD6Kma4tJGFtkAhxR5pfkp5QPw4LutoYWhvQCnGjdVn14kujQ#z82LkuBieyGShVBhvtE2zoiD6Kma4tJGFtkAhxR5pfkp5QPw4LutoYWhvQCnGjdVn14kujQ"));
		DataIntegrityProof dataIntegrityProof = signer.sign(verifiableCredentialGood6);
		assertTrue(verifier.verify(verifiableCredentialGood6));
	}

	/*
	 + Helper methods
	 */

	private static ByteSigner byteSignerP256(byte[] privateKeyBytes) {
		return new P_256_ES256_PrivateKeySigner(PrivateKeyBytes.bytes_to_P_256PrivateKey(privateKeyBytes));
	}

	private static ByteSigner byteSignerP384(byte[] privateKeyBytes) {
		return new P_384_ES384_PrivateKeySigner(PrivateKeyBytes.bytes_to_P_384PrivateKey(privateKeyBytes));
	}

	private static ByteVerifier byteVerifierP256(byte[] publicKeyBytes) {
		return new P_256_ES256_PublicKeyVerifier(PublicKeyBytes.bytes_to_P_256PublicKey(publicKeyBytes));
	}

	private static ByteVerifier byteVerifierP384(byte[] publicKeyBytes) {
		return new P_384_ES384_PublicKeyVerifier(PublicKeyBytes.bytes_to_P_384PublicKey(publicKeyBytes));
	}
}
