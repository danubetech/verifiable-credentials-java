package com.danubetech.verifiablecredentials.w3c.bip340;

import com.danubetech.dataintegrity.DataIntegrityProof;
import com.danubetech.dataintegrity.signer.DataIntegrityProofLdSigner;
import com.danubetech.dataintegrity.verifier.DataIntegrityProofLdVerifier;
import com.danubetech.keyformats.PrivateKeyBytes;
import com.danubetech.keyformats.PublicKeyBytes;
import com.danubetech.keyformats.crypto.ByteSigner;
import com.danubetech.keyformats.crypto.ByteVerifier;
import com.danubetech.keyformats.crypto.impl.secp256k1_ES256KS_PublicKeyVerifier;
import com.danubetech.keyformats.crypto.impl.secp256k1_ES256K_PrivateKeySigner;
import com.danubetech.keyformats.crypto.provider.*;
import com.danubetech.keyformats.crypto.provider.impl.*;
import com.danubetech.verifiablecredentials.VerifiableCredentialV2;
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

	final static byte[] publicKeyCredential1;
	final static byte[] publicKeyCredential2;

	final static VerifiableCredentialV2 verifiableCredentialGood1;
	final static VerifiableCredentialV2 verifiableCredentialGood2;

	static {

		try {

			privateKeyCredential1 = TestUtil.removeMulticodec(Multibase.decode("z3u2en7t5LR2WtQH5PfFqMqwVHBeXouLzo6haApm8XHqvjxq"));
			privateKeyCredential2 = TestUtil.removeMulticodec(Multibase.decode("z3u2en7t5LR2WtQH5PfFqMqwVHBeXouLzo6haApm8XHqvjxq"));

			publicKeyCredential1 = TestUtil.removeMulticodec(Multibase.decode("z6MkrJVnaZkeFzdQyMZu1cgjg7k1pZZ6pvBQ7XJPt4swbTQ2"));
			publicKeyCredential2 = TestUtil.removeMulticodec(Multibase.decode("z6MkrJVnaZkeFzdQyMZu1cgjg7k1pZZ6pvBQ7XJPt4swbTQ2"));

			verifiableCredentialGood1 = VerifiableCredentialV2.fromJson(new InputStreamReader(Objects.requireNonNull(DataIntegrityProofSignTest.class.getResourceAsStream("unsigned.good.DataIntegrityProof.1.jsonld"))));
			verifiableCredentialGood2 = VerifiableCredentialV2.fromJson(new InputStreamReader(Objects.requireNonNull(DataIntegrityProofSignTest.class.getResourceAsStream("unsigned.good.DataIntegrityProof.2.jsonld"))));
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

	//@Test
	void testSignCredential1() throws Exception {
		DataIntegrityProofLdSigner signer = new DataIntegrityProofLdSigner(byteSignerSecp256k1(privateKeyCredential1));
		DataIntegrityProofLdVerifier verifier = new DataIntegrityProofLdVerifier(byteVerifierSecp256k1(publicKeyCredential1));
		signer.setCryptosuite("eddsa-rdfc-2022");
		signer.setCreated(JsonLDUtils.stringToDate("2023-02-24T23:36:38Z"));
		signer.setProofPurpose("assertionMethod");
		signer.setVerificationMethod(URI.create("did:key:z6MkrJVnaZkeFzdQyMZu1cgjg7k1pZZ6pvBQ7XJPt4swbTQ2#z6MkrJVnaZkeFzdQyMZu1cgjg7k1pZZ6pvBQ7XJPt4swbTQ2"));
		DataIntegrityProof dataIntegrityProof = signer.sign(verifiableCredentialGood1);
		assertEquals("z2YwC8z3ap7yx1nZYCg4L3j3ApHsF8kgPdSb5xoS1VR7vPG3F561B52hYnQF9iseabecm3ijx4K1FBTQsCZahKZme", dataIntegrityProof.getProofValue());
		assertTrue(verifier.verify(verifiableCredentialGood1));
	}

	//@Test
	void testSignCredential2() throws Exception {
		DataIntegrityProofLdSigner signer = new DataIntegrityProofLdSigner(byteSignerSecp256k1(privateKeyCredential2));
		DataIntegrityProofLdVerifier verifier = new DataIntegrityProofLdVerifier(byteVerifierSecp256k1(publicKeyCredential2));
		signer.setCryptosuite("eddsa-rdfc-2022");
		signer.setCreated(JsonLDUtils.stringToDate("2023-02-24T23:36:38Z"));
		signer.setProofPurpose("assertionMethod");
		signer.setVerificationMethod(URI.create("did:key:z6MkrJVnaZkeFzdQyMZu1cgjg7k1pZZ6pvBQ7XJPt4swbTQ2#z6MkrJVnaZkeFzdQyMZu1cgjg7k1pZZ6pvBQ7XJPt4swbTQ2"));
		DataIntegrityProof dataIntegrityProof = signer.sign(verifiableCredentialGood2);
		assertEquals("zeuuS9pi2ZR8Q41bFFJKS9weSWkwa7pRcxHTHzxjDEHtVSZp3D9Rm3JdzT82EQpmXMb9wvfFJLuDPeSXZaRX1q1c", dataIntegrityProof.getProofValue());
		assertTrue(verifier.verify(verifiableCredentialGood2));
	}

	/*
	 + Helper methods
	 */

	private static ByteSigner byteSignerSecp256k1(byte[] privateKeyBytes) {
		return new secp256k1_ES256K_PrivateKeySigner(PrivateKeyBytes.bytes_to_secp256k1PrivateKey(privateKeyBytes));
	}

	private static ByteVerifier byteVerifierSecp256k1(byte[] publicKeyBytes) {
		return new secp256k1_ES256KS_PublicKeyVerifier(PublicKeyBytes.bytes_to_secp256k1PublicKey(publicKeyBytes));
	}
}
