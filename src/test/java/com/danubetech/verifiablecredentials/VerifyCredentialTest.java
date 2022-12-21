package com.danubetech.verifiablecredentials;

import com.danubetech.verifiablecredentials.validation.Validation;
import foundation.identity.jsonld.JsonLDException;
import info.weboftrust.ldsignatures.verifier.RsaSignature2018LdVerifier;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class VerifyCredentialTest {

	@Test
	void testVerify() throws Throwable {

		VerifiableCredential verifiableCredential = VerifiableCredential.fromJson(new InputStreamReader(VerifyCredentialTest.class.getResourceAsStream("signed.good.vc.jsonld")));

		Validation.validate(verifiableCredential);

		RsaSignature2018LdVerifier verifier = new RsaSignature2018LdVerifier(TestUtil.testRSAPublicKey);
		boolean verify = verifier.verify(verifiableCredential);

		assertTrue(verify);

		CredentialSubject credentialSubject = verifiableCredential.getCredentialSubject();
		String givenName = credentialSubject == null ? null : (String) credentialSubject.getClaims().get("givenName");

		assertEquals("Manu", givenName);

		// Credential subject as array has one entry
		List<CredentialSubject> credentialSubjects = verifiableCredential.getCredentialSubjects();

		assertEquals(1, credentialSubjects.size());
		assertEquals(credentialSubject, credentialSubjects.get(0));
	}

	@Test
	void testMultiSubjectVerify() throws Throwable {

		VerifiableCredential verifiableCredential = VerifiableCredential.fromJson(new InputStreamReader(VerifyCredentialTest.class.getResourceAsStream("signed.good.multisubject.vc.jsonld")));

		Validation.validate(verifiableCredential);

		RsaSignature2018LdVerifier verifier = new RsaSignature2018LdVerifier(TestUtil.testRSAPublicKey);
		boolean verify = verifier.verify(verifiableCredential);

		assertTrue(verify);

		// First credential subject still available
		CredentialSubject credentialSubject = verifiableCredential.getCredentialSubject();
		String givenName = credentialSubject == null ? null : (String) credentialSubject.getClaims().get("givenName");

		assertEquals("Manu", givenName);

		// Multiple credential subjects also available
		List<CredentialSubject> credentialSubjects = verifiableCredential.getCredentialSubjects();

		assertEquals(2, credentialSubjects.size());
	}

	@Test
	void testBadVerify() throws Throwable {

		VerifiableCredential verifiableCredential = VerifiableCredential.fromJson(new InputStreamReader(VerifyCredentialTest.class.getResourceAsStream("signed.bad.vc.jsonld")));

		Validation.validate(verifiableCredential);

		RsaSignature2018LdVerifier verifier = new RsaSignature2018LdVerifier(TestUtil.testRSAPublicKey);
		boolean verify = verifier.verify(verifiableCredential);

		assertFalse(verify);
	}
}
