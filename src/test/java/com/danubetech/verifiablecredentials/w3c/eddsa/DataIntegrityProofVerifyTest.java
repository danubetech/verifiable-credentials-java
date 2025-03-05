package com.danubetech.verifiablecredentials.w3c.eddsa;

import com.danubetech.dataintegrity.verifier.DataIntegrityProofLdVerifier;
import com.danubetech.keyformats.PublicKeyBytes;
import com.danubetech.keyformats.crypto.ByteVerifier;
import com.danubetech.keyformats.crypto.impl.Ed25519_EdDSA_PublicKeyVerifier;
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

	final static VerifiableCredentialV2 verifiableCredentialGood1;
	final static VerifiableCredentialV2 verifiableCredentialGood2;
	final static VerifiableCredentialV2 verifiableCredentialGood3;
	final static VerifiableCredentialV2 verifiableCredentialGood4;

	static {

		try {

			publicKeyCredential1 = TestUtil.removeMulticodec(Multibase.decode("z6MkrJVnaZkeFzdQyMZu1cgjg7k1pZZ6pvBQ7XJPt4swbTQ2"));
			publicKeyCredential2 = TestUtil.removeMulticodec(Multibase.decode("z6MkrJVnaZkeFzdQyMZu1cgjg7k1pZZ6pvBQ7XJPt4swbTQ2"));
			publicKeyCredential3 = TestUtil.removeMulticodec(Multibase.decode("z6MkrJVnaZkeFzdQyMZu1cgjg7k1pZZ6pvBQ7XJPt4swbTQ2"));
			publicKeyCredential4 = TestUtil.removeMulticodec(Multibase.decode("z6MkrJVnaZkeFzdQyMZu1cgjg7k1pZZ6pvBQ7XJPt4swbTQ2"));

			verifiableCredentialGood1 = VerifiableCredentialV2.fromJson(new InputStreamReader(Objects.requireNonNull(DataIntegrityProofVerifyTest.class.getResourceAsStream("signed.good.DataIntegrityProof.1.jsonld"))));
			verifiableCredentialGood2 = VerifiableCredentialV2.fromJson(new InputStreamReader(Objects.requireNonNull(DataIntegrityProofVerifyTest.class.getResourceAsStream("signed.good.DataIntegrityProof.2.jsonld"))));
			verifiableCredentialGood3 = VerifiableCredentialV2.fromJson(new InputStreamReader(Objects.requireNonNull(DataIntegrityProofVerifyTest.class.getResourceAsStream("signed.good.DataIntegrityProof.3.jsonld"))));
			verifiableCredentialGood4 = VerifiableCredentialV2.fromJson(new InputStreamReader(Objects.requireNonNull(DataIntegrityProofVerifyTest.class.getResourceAsStream("signed.good.Ed25519Signature2020.4.jsonld"))));

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
		DataIntegrityProofLdVerifier verifier = new DataIntegrityProofLdVerifier(byteVerifierEd25519(publicKeyCredential1));
		assertTrue(verifier.verify(verifiableCredentialGood1));
	}

	@Test
	void testVerifyGoodCredential2() throws Exception {
		DataIntegrityProofLdVerifier verifier = new DataIntegrityProofLdVerifier(byteVerifierEd25519(publicKeyCredential2));
		assertTrue(verifier.verify(verifiableCredentialGood2));
	}

	@Test
	void testVerifyGoodCredential3() throws Exception {
		DataIntegrityProofLdVerifier verifier = new DataIntegrityProofLdVerifier(byteVerifierEd25519(publicKeyCredential3));
		assertTrue(verifier.verify(verifiableCredentialGood3));
	}

	@Test
	void testVerifyGoodCredential4() throws Exception {
		DataIntegrityProofLdVerifier verifier = new DataIntegrityProofLdVerifier(byteVerifierEd25519(publicKeyCredential4));
		assertTrue(verifier.verify(verifiableCredentialGood4));
	}

	/*
	 + Helper methods
	 */

	private static ByteVerifier byteVerifierEd25519(byte[] publicKeyBytes) {
		return new Ed25519_EdDSA_PublicKeyVerifier(PublicKeyBytes.bytes_to_Ed25519PublicKey(publicKeyBytes));
	}
}
