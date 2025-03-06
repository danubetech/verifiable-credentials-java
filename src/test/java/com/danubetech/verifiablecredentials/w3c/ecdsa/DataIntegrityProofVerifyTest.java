package com.danubetech.verifiablecredentials.w3c.ecdsa;

import com.danubetech.dataintegrity.verifier.DataIntegrityProofLdVerifier;
import com.danubetech.keyformats.PublicKeyBytes;
import com.danubetech.keyformats.crypto.ByteVerifier;
import com.danubetech.keyformats.crypto.impl.P_256_ES256_PublicKeyVerifier;
import com.danubetech.keyformats.crypto.impl.P_384_ES384_PublicKeyVerifier;
import com.danubetech.keyformats.crypto.provider.*;
import com.danubetech.keyformats.crypto.provider.impl.*;
import com.danubetech.verifiablecredentials.VerifiableCredentialV2;
import com.danubetech.verifiablecredentials.util.CitizenshipContexts;
import com.danubetech.verifiablecredentials.util.TestUtil;
import com.danubetech.verifiablecredentials.validation.Validation;
import io.ipfs.multibase.Multibase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataIntegrityProofVerifyTest {

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

			publicKeyCredential1 = TestUtil.removeMulticodec(Multibase.decode("zDnaepBuvsQ8cpsWrVKw8fbpGpvPeNSjVPTWoq6cRqaYzBKVP"));
			publicKeyCredential2 = TestUtil.removeMulticodec(Multibase.decode("zDnaepBuvsQ8cpsWrVKw8fbpGpvPeNSjVPTWoq6cRqaYzBKVP"));
			publicKeyCredential3 = TestUtil.removeMulticodec(Multibase.decode("z82LkuBieyGShVBhvtE2zoiD6Kma4tJGFtkAhxR5pfkp5QPw4LutoYWhvQCnGjdVn14kujQ"));
			publicKeyCredential4 = TestUtil.removeMulticodec(Multibase.decode("z82LkuBieyGShVBhvtE2zoiD6Kma4tJGFtkAhxR5pfkp5QPw4LutoYWhvQCnGjdVn14kujQ"));
			publicKeyCredential5 = TestUtil.removeMulticodec(Multibase.decode("zDnaepBuvsQ8cpsWrVKw8fbpGpvPeNSjVPTWoq6cRqaYzBKVP"));
			publicKeyCredential6 = TestUtil.removeMulticodec(Multibase.decode("z82LkuBieyGShVBhvtE2zoiD6Kma4tJGFtkAhxR5pfkp5QPw4LutoYWhvQCnGjdVn14kujQ"));

			verifiableCredentialGood1 = VerifiableCredentialV2.fromJson(new InputStreamReader(Objects.requireNonNull(DataIntegrityProofVerifyTest.class.getResourceAsStream("signed.good.DataIntegrityProof.1.jsonld"))));
			verifiableCredentialGood2 = VerifiableCredentialV2.fromJson(new InputStreamReader(Objects.requireNonNull(DataIntegrityProofVerifyTest.class.getResourceAsStream("signed.good.DataIntegrityProof.2.jsonld"))));
			verifiableCredentialGood3 = VerifiableCredentialV2.fromJson(new InputStreamReader(Objects.requireNonNull(DataIntegrityProofVerifyTest.class.getResourceAsStream("signed.good.DataIntegrityProof.3.jsonld"))));
			verifiableCredentialGood4 = VerifiableCredentialV2.fromJson(new InputStreamReader(Objects.requireNonNull(DataIntegrityProofVerifyTest.class.getResourceAsStream("signed.good.DataIntegrityProof.4.jsonld"))));
			verifiableCredentialGood5 = VerifiableCredentialV2.fromJson(new InputStreamReader(Objects.requireNonNull(DataIntegrityProofVerifyTest.class.getResourceAsStream("signed.good.DataIntegrityProof.5.jsonld"))));
			verifiableCredentialGood6 = VerifiableCredentialV2.fromJson(new InputStreamReader(Objects.requireNonNull(DataIntegrityProofVerifyTest.class.getResourceAsStream("signed.good.DataIntegrityProof.6.jsonld"))));

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
	}

	/*
	 * GOOD CREDENTIAL
	 */

	@Test
	void testVerifyGoodCredential1() throws Exception {
		DataIntegrityProofLdVerifier verifier = new DataIntegrityProofLdVerifier(byteVerifierP256(publicKeyCredential1));
		assertTrue(verifier.verify(verifiableCredentialGood1));
	}

	@Test
	void testVerifyGoodCredential2() throws Exception {
		DataIntegrityProofLdVerifier verifier = new DataIntegrityProofLdVerifier(byteVerifierP256(publicKeyCredential2));
		assertTrue(verifier.verify(verifiableCredentialGood2));
	}

	@Test
	void testVerifyGoodCredential3() throws Exception {
		DataIntegrityProofLdVerifier verifier = new DataIntegrityProofLdVerifier(byteVerifierP384(publicKeyCredential3));
		assertTrue(verifier.verify(verifiableCredentialGood3));
	}

	@Test
	void testVerifyGoodCredential4() throws Exception {
		DataIntegrityProofLdVerifier verifier = new DataIntegrityProofLdVerifier(byteVerifierP384(publicKeyCredential4));
		assertTrue(verifier.verify(verifiableCredentialGood4));
	}

	@Test
	void testVerifyGoodCredential5() throws Exception {
		DataIntegrityProofLdVerifier verifier = new DataIntegrityProofLdVerifier(byteVerifierP256(publicKeyCredential5));
		assertTrue(verifier.verify(verifiableCredentialGood5));
	}

	@Test
	void testVerifyGoodCredential6() throws Exception {
		DataIntegrityProofLdVerifier verifier = new DataIntegrityProofLdVerifier(byteVerifierP384(publicKeyCredential6));
		assertTrue(verifier.verify(verifiableCredentialGood6));
	}

	/*
	 + Helper methods
	 */

	private static ByteVerifier byteVerifierP256(byte[] publicKeyBytes) {
		return new P_256_ES256_PublicKeyVerifier(PublicKeyBytes.bytes_to_P_256PublicKey(publicKeyBytes));
	}

	private static ByteVerifier byteVerifierP384(byte[] publicKeyBytes) {
		return new P_384_ES384_PublicKeyVerifier(PublicKeyBytes.bytes_to_P_384PublicKey(publicKeyBytes));
	}
}
