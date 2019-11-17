package com.danubetech.verifiablecredentials;
import java.util.LinkedHashMap;

import org.jose4j.jws.AlgorithmIdentifiers;

import com.danubetech.verifiablecredentials.jwt.JwtVerifiableCredential;

import junit.framework.TestCase;

public class JwtTest extends TestCase {

	public void testSign() throws Exception {

		VerifiableCredential verifiableCredential = new VerifiableCredential();
		verifiableCredential.getContext().add("https://trafi.fi/credentials/v1");
		verifiableCredential.getType().add("DriversLicenseCredential");
		verifiableCredential.setId("urn:uuid:a87bdfb8-a7df-4bd9-ae0d-d883133538fe");
		verifiableCredential.setIssuer("did:sov:1yvXbmgPoUm4dl66D7KhyD");
		verifiableCredential.setIssuanceDate(VerifiableCredential.DATE_FORMAT.parse("2019-06-16T18:56:59Z"));
		verifiableCredential.setExpirationDate(VerifiableCredential.DATE_FORMAT.parse("2019-06-17T18:56:59Z"));

		verifiableCredential.setCredentialSubject("did:sov:21tDAKCERh95uGgKbJNHYp");
		LinkedHashMap<String, Object> jsonLdCredentialSubject = verifiableCredential.getJsonLdCredentialSubject();
		LinkedHashMap<String, Object> jsonLdDriversLicenseObject = new LinkedHashMap<String, Object> ();
		jsonLdDriversLicenseObject.put("licenseClass", "trucks");
		jsonLdCredentialSubject.put("driversLicense", jsonLdDriversLicenseObject);

		JwtVerifiableCredential jwtVerifiableCredential = JwtVerifiableCredential.fromVerifiableCredential(verifiableCredential);

		String jwtPayload = jwtVerifiableCredential.getPayload().toJson();

		assertNotNull(jwtPayload);

		String jwtString = jwtVerifiableCredential.toJwt(AlgorithmIdentifiers.RSA_USING_SHA256, TestUtil.testRSAPrivateKey);

		assertNotNull(jwtString);

		assertEquals(TestUtil.read(VerifyTest.class.getResourceAsStream("verifiable-credential.jwt.payload.jsonld")).trim(), jwtPayload.trim());
		assertEquals(TestUtil.read(VerifyTest.class.getResourceAsStream("verifiable-credential.jwt.jsonld")).trim(), jwtString.trim());
	}

	public void testVerify() throws Exception {

		JwtVerifiableCredential jwtVerifiableCredential = JwtVerifiableCredential.fromJwt(TestUtil.read(VerifyTest.class.getResourceAsStream("verifiable-credential.jwt.jsonld")), AlgorithmIdentifiers.RSA_USING_SHA256, TestUtil.testRSAPublicKey);

		String jwtPayload = jwtVerifiableCredential.getPayload().toJson();
		String jwtPayloadVerifiableCredential = jwtVerifiableCredential.getPayloadVerifiableCredential().toJsonString();

		assertNotNull(jwtPayload);
		assertNotNull(jwtPayloadVerifiableCredential);

		assertEquals(TestUtil.read(VerifyTest.class.getResourceAsStream("verifiable-credential.jwt.payload.jsonld")).trim(), jwtPayload.trim());

		VerifiableCredential verifiableCredential = jwtVerifiableCredential.toVerifiableCredential();

		assertNotNull(verifiableCredential);

		assertTrue(verifiableCredential.getType().contains("DriversLicenseCredential"));
		assertEquals("urn:uuid:a87bdfb8-a7df-4bd9-ae0d-d883133538fe", verifiableCredential.getId());
		assertEquals("did:sov:1yvXbmgPoUm4dl66D7KhyD", verifiableCredential.getIssuer());
		assertEquals(VerifiableCredential.DATE_FORMAT.parse("2019-06-16T18:56:59Z"), verifiableCredential.getIssuanceDate());
		assertEquals(VerifiableCredential.DATE_FORMAT.parse("2019-06-17T18:56:59Z"), verifiableCredential.getExpirationDate());

		assertEquals("did:sov:21tDAKCERh95uGgKbJNHYp", verifiableCredential.getCredentialSubject());
		LinkedHashMap<String, Object> jsonLdCredentialSubject = verifiableCredential.getJsonLdCredentialSubject();
		LinkedHashMap<String, Object> jsonLdDriversLicenseObject = (LinkedHashMap<String, Object>) jsonLdCredentialSubject.get("driversLicense");
		assertEquals("trucks", jsonLdDriversLicenseObject.get("licenseClass"));
	}
}
