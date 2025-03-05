package com.danubetech.verifiablecredentials.w3c.bip340;

import com.danubetech.dataintegrity.verifier.DataIntegrityProofLdVerifier;
import com.danubetech.keyformats.PublicKeyBytes;
import com.danubetech.keyformats.crypto.ByteVerifier;
import com.danubetech.keyformats.crypto.impl.secp256k1_ES256KS_PublicKeyVerifier;
import com.danubetech.keyformats.crypto.provider.*;
import com.danubetech.keyformats.crypto.provider.impl.*;
import com.danubetech.verifiablecredentials.VerifiableCredentialV2;
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

	final static VerifiableCredentialV2 verifiableCredentialGood1;
	final static VerifiableCredentialV2 verifiableCredentialGood2;

	static {

		try {

			publicKeyCredential1 = TestUtil.removeMulticodec(Multibase.decode("zQ3shcJDnkBjY3XqD4WVKktWQZqgQSrYzhaTo6gxcs6GXjUuM"));
			publicKeyCredential2 = TestUtil.removeMulticodec(Multibase.decode("zQ3shcJDnkBjY3XqD4WVKktWQZqgQSrYzhaTo6gxcs6GXjUuM"));

			verifiableCredentialGood1 = VerifiableCredentialV2.fromJson(new InputStreamReader(Objects.requireNonNull(DataIntegrityProofVerifyTest.class.getResourceAsStream("signed.good.DataIntegrityProof.1.jsonld"))));
			verifiableCredentialGood2 = VerifiableCredentialV2.fromJson(new InputStreamReader(Objects.requireNonNull(DataIntegrityProofVerifyTest.class.getResourceAsStream("signed.good.DataIntegrityProof.2.jsonld"))));
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

	//@Test
	void testVerifyGoodCredential1() throws Exception {
		DataIntegrityProofLdVerifier verifier = new DataIntegrityProofLdVerifier(byteVerifierSecp256k1(publicKeyCredential1));
		assertTrue(verifier.verify(verifiableCredentialGood1));
	}

	//@Test
	void testVerifyGoodCredential2() throws Exception {
		DataIntegrityProofLdVerifier verifier = new DataIntegrityProofLdVerifier(byteVerifierSecp256k1(publicKeyCredential2));
		assertTrue(verifier.verify(verifiableCredentialGood2));
	}

	/*
	 + Helper methods
	 */

	private static ByteVerifier byteVerifierSecp256k1(byte[] publicKeyBytes) {
		return new secp256k1_ES256KS_PublicKeyVerifier(PublicKeyBytes.bytes_to_secp256k1PublicKey(publicKeyBytes));
	}
}
