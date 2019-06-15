package com.danubetech.verifiablecredentials;
import java.util.LinkedHashMap;

import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.github.jsonldjava.utils.JsonUtils;

import info.weboftrust.ldsignatures.validator.RsaSignature2018LdValidator;
import junit.framework.TestCase;

public class ValidateTest extends TestCase {

	@SuppressWarnings("unchecked")
	public void testValidate() throws Exception {

		LinkedHashMap<String, Object> jsonLdObject = (LinkedHashMap<String, Object>) JsonUtils.fromInputStream(ValidateTest.class.getResourceAsStream("verifiable-credential.test.jsonld"));
		VerifiableCredential verifiableCredential = VerifiableCredential.fromJsonLdObject(jsonLdObject);

		RsaSignature2018LdValidator validator = new RsaSignature2018LdValidator(TestUtil.testRSAPublicKey);
		boolean validate = validator.validate(verifiableCredential.getJsonLdObject());

		assertTrue(validate);

		LinkedHashMap<String, Object> jsonLdClaimsObject = verifiableCredential.getJsonLdCredentialSubject();
		LinkedHashMap<String, Object> jsonLdDriversLicenseObject = (LinkedHashMap<String, Object>) jsonLdClaimsObject.get("driversLicense");
		String licenseClass = jsonLdDriversLicenseObject == null ? null : (String) jsonLdDriversLicenseObject.get("licenseClass");

		assertEquals("trucks", licenseClass);
	}

	@SuppressWarnings("unchecked")
	public void testBadValidate() throws Exception {

		LinkedHashMap<String, Object> jsonLdObject = (LinkedHashMap<String, Object>) JsonUtils.fromInputStream(ValidateTest.class.getResourceAsStream("verifiable-credential.bad.test.jsonld"));
		VerifiableCredential verifiableCredential = VerifiableCredential.fromJsonLdObject(jsonLdObject);

		RsaSignature2018LdValidator validator = new RsaSignature2018LdValidator(TestUtil.testRSAPublicKey);
		boolean validate = validator.validate(verifiableCredential.getJsonLdObject());

		assertFalse(validate);
	}
}
