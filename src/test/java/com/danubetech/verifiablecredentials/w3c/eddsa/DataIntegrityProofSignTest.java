package com.danubetech.verifiablecredentials.w3c.eddsa;

import com.danubetech.dataintegrity.DataIntegrityProof;
import com.danubetech.dataintegrity.signer.DataIntegrityProofLdSigner;
import com.danubetech.dataintegrity.signer.Ed25519Signature2020LdSigner;
import com.danubetech.dataintegrity.verifier.DataIntegrityProofLdVerifier;
import com.danubetech.dataintegrity.verifier.Ed25519Signature2020LdVerifier;
import com.danubetech.keyformats.PrivateKeyBytes;
import com.danubetech.keyformats.PublicKeyBytes;
import com.danubetech.keyformats.crypto.ByteSigner;
import com.danubetech.keyformats.crypto.ByteVerifier;
import com.danubetech.keyformats.crypto.impl.Ed25519_EdDSA_PrivateKeySigner;
import com.danubetech.keyformats.crypto.impl.Ed25519_EdDSA_PublicKeyVerifier;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataIntegrityProofSignTest {

	final static byte[] privateKeyCredential1;
	final static byte[] privateKeyCredential2;
	final static byte[] privateKeyCredential3;
	final static byte[] privateKeyCredential4;

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

			privateKeyCredential1 = TestUtil.removeMulticodec(Multibase.decode("z3u2en7t5LR2WtQH5PfFqMqwVHBeXouLzo6haApm8XHqvjxq"));
			privateKeyCredential2 = TestUtil.removeMulticodec(Multibase.decode("z3u2en7t5LR2WtQH5PfFqMqwVHBeXouLzo6haApm8XHqvjxq"));
			privateKeyCredential3 = TestUtil.removeMulticodec(Multibase.decode("z3u2en7t5LR2WtQH5PfFqMqwVHBeXouLzo6haApm8XHqvjxq"));
			privateKeyCredential4 = TestUtil.removeMulticodec(Multibase.decode("z3u2en7t5LR2WtQH5PfFqMqwVHBeXouLzo6haApm8XHqvjxq"));

			publicKeyCredential1 = TestUtil.removeMulticodec(Multibase.decode("z6MkrJVnaZkeFzdQyMZu1cgjg7k1pZZ6pvBQ7XJPt4swbTQ2"));
			publicKeyCredential2 = TestUtil.removeMulticodec(Multibase.decode("z6MkrJVnaZkeFzdQyMZu1cgjg7k1pZZ6pvBQ7XJPt4swbTQ2"));
			publicKeyCredential3 = TestUtil.removeMulticodec(Multibase.decode("z6MkrJVnaZkeFzdQyMZu1cgjg7k1pZZ6pvBQ7XJPt4swbTQ2"));
			publicKeyCredential4 = TestUtil.removeMulticodec(Multibase.decode("z6MkrJVnaZkeFzdQyMZu1cgjg7k1pZZ6pvBQ7XJPt4swbTQ2"));

			verifiableCredentialGood1 = VerifiableCredentialV2.fromJson(new InputStreamReader(Objects.requireNonNull(DataIntegrityProofSignTest.class.getResourceAsStream("unsigned.good.DataIntegrityProof.1.jsonld"))));
			verifiableCredentialGood2 = VerifiableCredentialV2.fromJson(new InputStreamReader(Objects.requireNonNull(DataIntegrityProofSignTest.class.getResourceAsStream("unsigned.good.DataIntegrityProof.2.jsonld"))));
			verifiableCredentialGood3 = VerifiableCredentialV2.fromJson(new InputStreamReader(Objects.requireNonNull(DataIntegrityProofSignTest.class.getResourceAsStream("unsigned.good.DataIntegrityProof.3.jsonld"))));
			verifiableCredentialGood4 = VerifiableCredentialV2.fromJson(new InputStreamReader(Objects.requireNonNull(DataIntegrityProofSignTest.class.getResourceAsStream("unsigned.good.Ed25519Signature2020.4.jsonld"))));

			verifiableCredentialGood2.setDocumentLoader(CitizenshipContexts.CITIZENSHIP_DOCUMENT_LOADER);
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
	 * CREDENTIAL
	 */

	@Test
	void testSignCredential1() throws Exception {
		DataIntegrityProofLdSigner signer = new DataIntegrityProofLdSigner(byteSignerEd25519(privateKeyCredential1));
		DataIntegrityProofLdVerifier verifier = new DataIntegrityProofLdVerifier(byteVerifierEd25519(publicKeyCredential1));
		signer.setCryptosuite("eddsa-rdfc-2022");
		signer.setCreated(JsonLDUtils.stringToDate("2023-02-24T23:36:38Z"));
		signer.setProofPurpose("assertionMethod");
		signer.setVerificationMethod(URI.create("did:key:z6MkrJVnaZkeFzdQyMZu1cgjg7k1pZZ6pvBQ7XJPt4swbTQ2#z6MkrJVnaZkeFzdQyMZu1cgjg7k1pZZ6pvBQ7XJPt4swbTQ2"));
		DataIntegrityProof dataIntegrityProof = signer.sign(verifiableCredentialGood1);
		assertEquals("z2YwC8z3ap7yx1nZYCg4L3j3ApHsF8kgPdSb5xoS1VR7vPG3F561B52hYnQF9iseabecm3ijx4K1FBTQsCZahKZme", dataIntegrityProof.getProofValue());
		assertTrue(verifier.verify(verifiableCredentialGood1));
	}

	@Test
	void testSignCredential2() throws Exception {
		DataIntegrityProofLdSigner signer = new DataIntegrityProofLdSigner(byteSignerEd25519(privateKeyCredential2));
		DataIntegrityProofLdVerifier verifier = new DataIntegrityProofLdVerifier(byteVerifierEd25519(publicKeyCredential2));
		signer.setCryptosuite("eddsa-rdfc-2022");
		signer.setCreated(JsonLDUtils.stringToDate("2023-02-24T23:36:38Z"));
		signer.setProofPurpose("assertionMethod");
		signer.setVerificationMethod(URI.create("did:key:z6MkrJVnaZkeFzdQyMZu1cgjg7k1pZZ6pvBQ7XJPt4swbTQ2#z6MkrJVnaZkeFzdQyMZu1cgjg7k1pZZ6pvBQ7XJPt4swbTQ2"));
		DataIntegrityProof dataIntegrityProof = signer.sign(verifiableCredentialGood2);
		assertEquals("zeuuS9pi2ZR8Q41bFFJKS9weSWkwa7pRcxHTHzxjDEHtVSZp3D9Rm3JdzT82EQpmXMb9wvfFJLuDPeSXZaRX1q1c", dataIntegrityProof.getProofValue());
		assertTrue(verifier.verify(verifiableCredentialGood2));
	}

	@Test
	void testSignCredential3() throws Exception {
		DataIntegrityProofLdSigner signer = new DataIntegrityProofLdSigner(byteSignerEd25519(privateKeyCredential3));
		DataIntegrityProofLdVerifier verifier = new DataIntegrityProofLdVerifier(byteVerifierEd25519(publicKeyCredential3));
		signer.setCryptosuite("eddsa-jcs-2022");
		signer.setCreated(JsonLDUtils.stringToDate("2023-02-24T23:36:38Z"));
		signer.setProofPurpose("assertionMethod");
		signer.setVerificationMethod(URI.create("did:key:z6MkrJVnaZkeFzdQyMZu1cgjg7k1pZZ6pvBQ7XJPt4swbTQ2#z6MkrJVnaZkeFzdQyMZu1cgjg7k1pZZ6pvBQ7XJPt4swbTQ2"));
		DataIntegrityProof dataIntegrityProof = signer.sign(verifiableCredentialGood3);
		assertEquals("z2HnFSSPPBzR36zdDgK8PbEHeXbR56YF24jwMpt3R1eHXQzJDMWS93FCzpvJpwTWd3GAVFuUfjoJdcnTMuVor51aX", dataIntegrityProof.getProofValue());
		assertTrue(verifier.verify(verifiableCredentialGood3));
	}

	@Test
	void testSignCredential4() throws Exception {
		Ed25519Signature2020LdSigner signer = new Ed25519Signature2020LdSigner(byteSignerEd25519(privateKeyCredential4));
		Ed25519Signature2020LdVerifier verifier = new Ed25519Signature2020LdVerifier(byteVerifierEd25519(publicKeyCredential4));
		signer.setCreated(JsonLDUtils.stringToDate("2023-02-24T23:36:38Z"));
		signer.setProofPurpose("assertionMethod");
		signer.setVerificationMethod(URI.create("did:key:z6MkrJVnaZkeFzdQyMZu1cgjg7k1pZZ6pvBQ7XJPt4swbTQ2#z6MkrJVnaZkeFzdQyMZu1cgjg7k1pZZ6pvBQ7XJPt4swbTQ2"));
		DataIntegrityProof dataIntegrityProof = signer.sign(verifiableCredentialGood4);
		assertEquals("z57Mm1vboMtZiCyJ4aReZsv8co4Re64Y8GEjL1ZARzMbXZgkARFLqFs1P345NpPGG2hgCrS4nNdvJhpwnrNyG3kEF", dataIntegrityProof.getProofValue());
		assertTrue(verifier.verify(verifiableCredentialGood4));
	}

	/*
	 + Helper methods
	 */

	private static ByteSigner byteSignerEd25519(byte[] publicKeyBytes) {
		return new Ed25519_EdDSA_PrivateKeySigner(PrivateKeyBytes.bytes_to_Ed25519PrivateKey(publicKeyBytes));
	}

	private static ByteVerifier byteVerifierEd25519(byte[] publicKeyBytes) {
		return new Ed25519_EdDSA_PublicKeyVerifier(PublicKeyBytes.bytes_to_Ed25519PublicKey(publicKeyBytes));
	}
}
