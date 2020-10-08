package com.danubetech.verifiablecredentials;
import java.io.InputStreamReader;

import info.weboftrust.ldsignatures.verifier.RsaSignature2018LdVerifier;
import org.junit.jupiter.api.Test;

import javax.json.JsonString;

import static org.junit.jupiter.api.Assertions.*;

class VerifyCredentialTest {

	@Test
	void testVerify() throws Throwable {

		VerifiableCredential verifiableCredential = VerifiableCredential.fromJson(new InputStreamReader(VerifyCredentialTest.class.getResourceAsStream("signed.good.vc.jsonld")));

		RsaSignature2018LdVerifier verifier = new RsaSignature2018LdVerifier(TestUtil.testRSAPublicKey);
		boolean verify = verifier.verify(verifiableCredential);

		assertTrue(verify);

		CredentialSubject credentialSubject = verifiableCredential.getCredentialSubject();
		String givenName = credentialSubject == null ? null : ((JsonString) credentialSubject.getClaims().get("givenName")).getString();

		assertEquals("Manu", givenName);
	}

	@Test
	void testBadVerify() throws Throwable {

		VerifiableCredential verifiableCredential = VerifiableCredential.fromJson(new InputStreamReader(VerifyCredentialTest.class.getResourceAsStream("signed.bad.vc.jsonld")));

		RsaSignature2018LdVerifier verifier = new RsaSignature2018LdVerifier(TestUtil.testRSAPublicKey);
		boolean verify = verifier.verify(verifiableCredential);

		assertFalse(verify);
	}
}
