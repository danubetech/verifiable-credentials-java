package com.danubetech.verifiablecredentials;

import com.danubetech.verifiablecredentials.validation.Validation;
import foundation.identity.jsonld.JsonLDUtils;
import info.weboftrust.ldsignatures.LdProof;
import info.weboftrust.ldsignatures.signer.RsaSignature2018LdSigner;
import info.weboftrust.ldsignatures.suites.SignatureSuites;
import info.weboftrust.ldsignatures.verifier.RsaSignature2018LdVerifier;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.net.URI;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SignPresentationTest {

	@Test
	void testSign() throws Throwable {

		VerifiablePresentation verifiablePresentation = VerifiablePresentation.fromJson(new InputStreamReader(VerifyCredentialTest.class.getResourceAsStream("input.vp.jsonld")));

		URI verificationMethod = URI.create("did:sov:1yvXbmgPoUm4dl66D7KhyD#keys-1");
		Date created = JsonLDUtils.DATE_FORMAT.parse("2018-01-01T21:19:10Z");
		String domain = null;
		String nonce = "c0ae1c8e-c7e7-469f-b252-86e6a0e7387e";

		RsaSignature2018LdSigner signer = new RsaSignature2018LdSigner(TestUtil.testRSAPrivateKey);
		signer.setVerificationMethod(verificationMethod);
		signer.setCreated(created);
		signer.setDomain(domain);
		signer.setNonce(nonce);
		LdProof ldSignature = signer.sign(verifiablePresentation, true, false);

		assertEquals(SignatureSuites.SIGNATURE_SUITE_RSASIGNATURE2018.getTerm(), ldSignature.getType());
		assertEquals(verificationMethod, ldSignature.getVerificationMethod());
		assertEquals(created, ldSignature.getCreated());
		assertEquals(domain, ldSignature.getDomain());
		assertEquals(nonce, ldSignature.getNonce());
		assertEquals("eyJiNjQiOmZhbHNlLCJjcml0IjpbImI2NCJdLCJhbGciOiJSUzI1NiJ9..PexTQ753-C3IjZwC0F5yA-06EuiM_McqPBRcyPhHw0PdCaVdvK628kqG8PWABJ1oISU8Z75lzpfhwNwD2qRiPTLg6uQqbmm8p633hM0HCIih8Uf3QzflrUlxfPIiAdUmWZiNRHNPbm4KD4hvPl4S0kYmCLJEp0evMbazZHKgnKOzzGsvOIqpCwheH30uzbk5--z8XJGflMLEHqrp42DWuYB8y9l_yn830mC6xAzWe25KRSbODDk2xy1gjIcMeBYPkMuZ4MCamRUYsPuj-aLHq8q8iDrhUoUDH307v0OevDlyu6cG7_H0bgG6fGTzAT5EGkb-EhE3NfAvKo7nh3d6Mw", ldSignature.getJws());

		Validation.validate(verifiablePresentation);

		RsaSignature2018LdVerifier verifier = new RsaSignature2018LdVerifier(TestUtil.testRSAPublicKey);
		boolean verify = verifier.verify(verifiablePresentation);
		assertTrue(verify);
	}

	@Test
	void testMultiSubjectSign() throws Throwable {

		VerifiablePresentation verifiablePresentation = VerifiablePresentation.fromJson(new InputStreamReader(VerifyCredentialTest.class.getResourceAsStream("input.multisubject.vp.jsonld")));

		URI verificationMethod = URI.create("did:sov:1yvXbmgPoUm4dl66D7KhyD#keys-1");
		Date created = JsonLDUtils.DATE_FORMAT.parse("2018-01-01T21:19:10Z");
		String domain = null;
		String nonce = "c0ae1c8e-c7e7-469f-b252-86e6a0e7387e";

		RsaSignature2018LdSigner signer = new RsaSignature2018LdSigner(TestUtil.testRSAPrivateKey);
		signer.setVerificationMethod(verificationMethod);
		signer.setCreated(created);
		signer.setDomain(domain);
		signer.setNonce(nonce);
		LdProof ldSignature = signer.sign(verifiablePresentation, true, false);

		assertEquals(SignatureSuites.SIGNATURE_SUITE_RSASIGNATURE2018.getTerm(), ldSignature.getType());
		assertEquals(verificationMethod, ldSignature.getVerificationMethod());
		assertEquals(created, ldSignature.getCreated());
		assertEquals(domain, ldSignature.getDomain());
		assertEquals(nonce, ldSignature.getNonce());
		assertEquals("eyJiNjQiOmZhbHNlLCJjcml0IjpbImI2NCJdLCJhbGciOiJSUzI1NiJ9..RNCMCgkZDI0gAQVXjG2LlJuAIFMv5ULpLE0u8dIryRTaqmLgx6gKXOTmr44qqIT2DmuMt_6guUXckXwKnRhBxHd0UP31QjCIK-DEJkNxOlccXqQUAxOj8pbS2IIrBh_a0lWeOxDrLRnLriqeLFvc_ihOOCkh45dTdCE7yhcf1QYX2Bn3-iZJlfLqHShI7tKC1n0S9-2f_KbOOLzfghY5pSEEPn-6QKQPhVSx0_G6t0zQXU6ti1eTx0Mx1E3qQ92eCrnxYkrLpDdhzvUCdh_vl8DwYzPnFYECZAkQxLKdeRmoISoiXhxQekhPPhz4JqJajBhpabTof1wv7qGwyw6qow", ldSignature.getJws());

		Validation.validate(verifiablePresentation);

		RsaSignature2018LdVerifier verifier = new RsaSignature2018LdVerifier(TestUtil.testRSAPublicKey);
		boolean verify = verifier.verify(verifiablePresentation);
		assertTrue(verify);
	}
}
