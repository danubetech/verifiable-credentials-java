package com.danubetech.verifiablecredentials;
import java.util.LinkedHashMap;

import com.github.jsonldjava.utils.JsonUtils;

import info.weboftrust.ldsignatures.verifier.RsaSignature2018LdVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VerifyTest {

	@Test
	void testVerify() throws Exception {

		LinkedHashMap<String, Object> jsonLdObject = (LinkedHashMap<String, Object>) JsonUtils.fromInputStream(VerifyTest.class.getResourceAsStream("verifiable-credential.ldp.good.jsonld"));
		VerifiableCredential verifiableCredential = VerifiableCredential.fromJsonLdObject(jsonLdObject);

		RsaSignature2018LdVerifier verifier = new RsaSignature2018LdVerifier(TestUtil.testRSAPublicKey);
		boolean verify = verifier.verify(verifiableCredential.getJsonLdObject());

		assertTrue(verify);

		LinkedHashMap<String, Object> jsonLdCredentialSubject = verifiableCredential.getJsonLdCredentialSubject();
		String givenName = jsonLdCredentialSubject == null ? null : (String) jsonLdCredentialSubject.get("givenName");

		assertEquals("Manu", givenName);
	}

	@Test
	void testBadVerify() throws Exception {

		LinkedHashMap<String, Object> jsonLdObject = (LinkedHashMap<String, Object>) JsonUtils.fromInputStream(VerifyTest.class.getResourceAsStream("verifiable-credential.ldp.bad.jsonld"));
		VerifiableCredential verifiableCredential = VerifiableCredential.fromJsonLdObject(jsonLdObject);

		RsaSignature2018LdVerifier verifier = new RsaSignature2018LdVerifier(TestUtil.testRSAPublicKey);
		boolean verify = verifier.verify(verifiableCredential.getJsonLdObject());

		assertFalse(verify);
	}
}
