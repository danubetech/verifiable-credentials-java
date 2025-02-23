package com.danubetech.verifiablecredentials;

import com.danubetech.dataintegrity.verifier.RsaSignature2018LdVerifier;
import com.danubetech.verifiablecredentials.validation.Validation;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class VerifyCredentialTest {

	@Test
	void testVerify() throws Throwable {

		VerifiableCredential verifiableCredential = VerifiableCredential.fromJson(new InputStreamReader(Objects.requireNonNull(VerifyCredentialTest.class.getResourceAsStream("signed.good.vc.jsonld"))));

		Validation.validate(verifiableCredential);

		RsaSignature2018LdVerifier verifier = new RsaSignature2018LdVerifier(TestUtil.testRSAPublicKey);
		boolean verify = verifier.verify(verifiableCredential);

		assertTrue(verify);

		CredentialSubject credentialSubject = verifiableCredential.getCredentialSubject();
		String givenName = credentialSubject == null ? null : (String) credentialSubject.getClaims().get("givenName");

		assertEquals("Manu", givenName);
	}

	@Test
	void testBadVerify() throws Throwable {

		VerifiableCredential verifiableCredential = VerifiableCredential.fromJson(new InputStreamReader(Objects.requireNonNull(VerifyCredentialTest.class.getResourceAsStream("signed.bad.vc.jsonld"))));

		Validation.validate(verifiableCredential);

		RsaSignature2018LdVerifier verifier = new RsaSignature2018LdVerifier(TestUtil.testRSAPublicKey);
		boolean verify = verifier.verify(verifiableCredential);

		assertFalse(verify);
	}
}
