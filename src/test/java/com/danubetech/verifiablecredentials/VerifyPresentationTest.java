package com.danubetech.verifiablecredentials;

import com.danubetech.dataintegrity.verifier.Ed25519Signature2018LdVerifier;
import com.danubetech.keyformats.crypto.provider.Ed25519Provider;
import com.danubetech.keyformats.crypto.provider.RandomProvider;
import com.danubetech.keyformats.crypto.provider.SHA256Provider;
import com.danubetech.keyformats.crypto.provider.impl.JavaRandomProvider;
import com.danubetech.keyformats.crypto.provider.impl.JavaSHA256Provider;
import com.danubetech.keyformats.crypto.provider.impl.TinkEd25519Provider;
import com.danubetech.verifiablecredentials.validation.Validation;
import org.bitcoinj.base.Base58;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class VerifyPresentationTest {

	final static byte[] publicKeyPresentation1;
	final static byte[] publicKeyPresentation2;
	final static byte[] publicKeyCredential1;
	final static byte[] publicKeyCredential2;

	final static VerifiablePresentation verifiablePresentationGood1;
	final static VerifiablePresentation verifiablePresentationGood2;
	final static VerifiablePresentation verifiablePresentationBad1;
	final static VerifiablePresentation verifiablePresentationBad2;
	final static VerifiableCredential verifiableCredentialGood1;
	final static VerifiableCredential verifiableCredentialGood2;
	final static VerifiableCredential verifiableCredentialBad1;
	final static VerifiableCredential verifiableCredentialBad2;

	static {

		try {

			publicKeyPresentation1 = Base58.decode("DqS5F3GVe3rCxucgi4JBNagjv4dKoHc8TDLDw9kR58Pz");
			publicKeyPresentation2 = Base58.decode("5yKdnU7ToTjAoRNDzfuzVTfWBH38qyhE1b9xh4v8JaWF");
			publicKeyCredential1 = Base58.decode("5TVraf9itbKXrRvt2DSS95Gw4vqU3CHAdetoufdcKazA");
			publicKeyCredential2 = Base58.decode("5yKdnU7ToTjAoRNDzfuzVTfWBH38qyhE1b9xh4v8JaWF");

			verifiablePresentationGood1 = VerifiablePresentation.fromJson(new InputStreamReader(Objects.requireNonNull(VerifyPresentationTest.class.getResourceAsStream("signed.good.vp1.jsonld"))));
			verifiablePresentationGood2 = VerifiablePresentation.fromJson(new InputStreamReader(Objects.requireNonNull(VerifyPresentationTest.class.getResourceAsStream("signed.good.vp2.jsonld"))));
			verifiablePresentationBad1 = VerifiablePresentation.fromJson(new InputStreamReader(Objects.requireNonNull(VerifyPresentationTest.class.getResourceAsStream("signed.bad.vp1.jsonld"))));
			verifiablePresentationBad2 = VerifiablePresentation.fromJson(new InputStreamReader(Objects.requireNonNull(VerifyPresentationTest.class.getResourceAsStream("signed.bad.vp2.jsonld"))));
			verifiableCredentialGood1 = verifiablePresentationGood1.getVerifiableCredential();
			verifiableCredentialGood2 = verifiablePresentationGood2.getVerifiableCredential();
			verifiableCredentialBad1 = verifiablePresentationBad1.getVerifiableCredential();
			verifiableCredentialBad2 = verifiablePresentationBad2.getVerifiableCredential();
		} catch (Exception ex) {

			throw new RuntimeException(ex.getMessage(), ex);
		}
	}

	@BeforeEach
	public void before() {

		RandomProvider.set(new JavaRandomProvider());
		SHA256Provider.set(new JavaSHA256Provider());
		Ed25519Provider.set(new TinkEd25519Provider());
	}

	@Test
	void testValidity() {

		Validation.validate(verifiablePresentationGood1);
		Validation.validate(verifiablePresentationGood2);
		Validation.validate(verifiablePresentationBad1);
		Validation.validate(verifiablePresentationBad2);
		Validation.validate(verifiableCredentialGood1);
		Validation.validate(verifiableCredentialGood2);
		Validation.validate(verifiableCredentialBad1);
		Validation.validate(verifiableCredentialBad2);
	}

	/*
	 * GOOD CREDENTIAL
	 */

	@Test
	void testVerifyGoodCredential1() throws Exception {

		Ed25519Signature2018LdVerifier verifier = new Ed25519Signature2018LdVerifier(publicKeyCredential1);
		boolean verify = verifier.verify(verifiableCredentialGood1);

		assertTrue(verify);
		assertEquals("Bachelor of Science and Arts", ((Map<String, Object>) verifiableCredentialGood1.getCredentialSubject().getClaims().get("degree")).get("name"));
	}

	@Test
	void testVerifyGoodCredential2() throws Exception {

		Ed25519Signature2018LdVerifier verifier = new Ed25519Signature2018LdVerifier(publicKeyCredential2);
		boolean verify = verifier.verify(verifiableCredentialGood2);

		assertTrue(verify);
		assertEquals("Bachelor of Science and Arts", ((Map<String, Object>) verifiableCredentialGood1.getCredentialSubject().getClaims().get("degree")).get("name"));
	}

	/*
	 * BAD CREDENTIAL
	 */

	@Test
	void testVerifyBadCredential1() throws Exception {

		Ed25519Signature2018LdVerifier verifier = new Ed25519Signature2018LdVerifier(publicKeyCredential1);
		boolean verify = verifier.verify(verifiableCredentialBad1);

		assertFalse(verify);
		assertEquals("Master of Science and Arts", ((Map<String, Object>) verifiableCredentialBad1.getCredentialSubject().getClaims().get("degree")).get("name"));
	}

	@Test
	void testVerifyBadCredential2() throws Exception {

		Ed25519Signature2018LdVerifier verifier = new Ed25519Signature2018LdVerifier(publicKeyCredential2);
		boolean verify = verifier.verify(verifiableCredentialBad2);

		assertFalse(verify);
		assertEquals("Master of Science and Arts", ((Map<String, Object>) verifiableCredentialBad2.getCredentialSubject().getClaims().get("degree")).get("name"));
	}

	/*
	 * GOOD PRESENTATION
	 */

	@Test
	void testVerifyGoodPresentation1() throws Exception {

		Ed25519Signature2018LdVerifier verifier = new Ed25519Signature2018LdVerifier(publicKeyPresentation1);
		boolean verify = verifier.verify(verifiablePresentationGood1);

		assertTrue(verify);
	}

	@Test
	void testVerifyGoodPresentation2() throws Exception {

		Ed25519Signature2018LdVerifier verifier = new Ed25519Signature2018LdVerifier(publicKeyPresentation2);
		boolean verify = verifier.verify(verifiablePresentationGood2);

		assertTrue(verify);
	}

	/*
	 * BAD PRESENTATION
	 */

	@Test
	void testVerifyBadPresentation1() throws Exception {

		Ed25519Signature2018LdVerifier verifier = new Ed25519Signature2018LdVerifier(publicKeyPresentation1);
		boolean verify = verifier.verify(verifiablePresentationBad1);

		assertFalse(verify);
	}

	@Test
	void testVerifyBadPresentation2() throws Exception {

		Ed25519Signature2018LdVerifier verifier = new Ed25519Signature2018LdVerifier(publicKeyPresentation2);
		boolean verify = verifier.verify(verifiablePresentationBad2);

		assertFalse(verify);
	}
}
