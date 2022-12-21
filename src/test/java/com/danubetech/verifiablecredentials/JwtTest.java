package com.danubetech.verifiablecredentials;

import com.danubetech.verifiablecredentials.jwt.FromJwtConverter;
import com.danubetech.verifiablecredentials.jwt.JwtVerifiableCredential;
import com.danubetech.verifiablecredentials.jwt.ToJwtConverter;
import com.nimbusds.jose.jwk.RSAKey;
import foundation.identity.jsonld.JsonLDUtils;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class JwtTest {

	static RSAKey rsaKey;

	static {

		rsaKey = new RSAKey.Builder(TestUtil.testRSAPublicKey).privateKey(TestUtil.testRSAPrivateKey.getPrivate()).build();
	}

	@Test
	void testSign() throws Exception {

		Map<String, Object> claims = new LinkedHashMap<>();
		Map<String, Object> driversLicense = new LinkedHashMap<String, Object>();
		driversLicense.put("licenseClass", "trucks");
		driversLicense.put("suspended", Boolean.FALSE);
		claims.put("name", "M S");
		claims.put("driversLicense", driversLicense);

		CredentialSubject credentialSubject = CredentialSubject.builder()
				.id(URI.create("did:sov:21tDAKCERh95uGgKbJNHYp"))
				.claims(claims)
				.build();

		VerifiableCredential verifiableCredential = VerifiableCredential.builder()
				.context(URI.create("https://trafi.fi/credentials/v1"))
				.type("DriversLicenseCredential")
				.id(URI.create("urn:uuid:a87bdfb8-a7df-4bd9-ae0d-d883133538fe"))
				.issuer(URI.create("did:sov:1yvXbmgPoUm4dl66D7KhyD"))
				.issuanceDate(JsonLDUtils.stringToDate("2019-06-16T18:56:59Z"))
				.expirationDate(JsonLDUtils.stringToDate("2019-06-17T18:56:59Z"))
				.credentialSubject(credentialSubject)
				.build();

		JwtVerifiableCredential jwtVerifiableCredential = ToJwtConverter.toJwtVerifiableCredential(verifiableCredential);
		String jwtString = jwtVerifiableCredential.sign_RSA_RS256(rsaKey);
		String jwtPayload = jwtVerifiableCredential.getJwsObject().getPayload().toString();

		assertNotNull(jwtString);
		assertNotNull(jwtPayload);

		assertEquals(TestUtil.read(VerifyCredentialTest.class.getResourceAsStream("jwt.vc.jsonld")).trim(), jwtString.trim());
		assertEquals(TestUtil.read(VerifyCredentialTest.class.getResourceAsStream("jwt.payload.vc.jsonld")).trim(), jwtPayload.trim());
	}

	@Test
	void testSignMultiSubjectUnsupported() throws Exception {

		Map<String, Object> firstClaims = new LinkedHashMap<>();
		Map<String, Object> firstDriversLicense = new LinkedHashMap<String, Object>();
		firstDriversLicense.put("licenseClass", "trucks");
		firstDriversLicense.put("suspended", Boolean.FALSE);
		firstClaims.put("name", "M S");
		firstClaims.put("driversLicense", firstDriversLicense);

		CredentialSubject firstCredentialSubject = CredentialSubject.builder()
				.id(URI.create("did:sov:21tDAKCERh95uGgKbJNHYp"))
				.claims(firstClaims)
				.build();

		Map<String, Object> secondClaims = new LinkedHashMap<>();
		Map<String, Object> secondDriversLicense = new LinkedHashMap<String, Object>();
		secondDriversLicense.put("licenseClass", "motorcycles");
		secondDriversLicense.put("suspended", Boolean.TRUE);
		secondClaims.put("name", "M S");
		secondClaims.put("driversLicense", secondDriversLicense);

		CredentialSubject secondCredentialSubject = CredentialSubject.builder()
				.id(URI.create("did:sov:21tDAKCERh95uGgKbJNHYq"))
				.claims(secondClaims)
				.build();

		VerifiableCredential verifiableCredential = VerifiableCredential.builder()
				.context(URI.create("https://trafi.fi/credentials/v1"))
				.type("DriversLicenseCredential")
				.id(URI.create("urn:uuid:a87bdfb8-a7df-4bd9-ae0d-d883133538fe"))
				.issuer(URI.create("did:sov:1yvXbmgPoUm4dl66D7KhyD"))
				.issuanceDate(JsonLDUtils.stringToDate("2019-06-16T18:56:59Z"))
				.expirationDate(JsonLDUtils.stringToDate("2019-06-17T18:56:59Z"))
				.credentialSubjects(new ArrayList<CredentialSubject>() {{
					add(firstCredentialSubject);
					add(secondCredentialSubject);
				}})
				.build();

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> { ToJwtConverter.toJwtVerifiableCredential(verifiableCredential); });
		assertEquals("JWTs are not capable of encoding multiple subjects and are thus not capable of encoding a verifiable credential with more than one subject.", exception.getMessage());
	}

	@Test
	void testVerify() throws Exception {

		JwtVerifiableCredential jwtVerifiableCredential = JwtVerifiableCredential.fromCompactSerialization(TestUtil.read(VerifyCredentialTest.class.getResourceAsStream("jwt.vc.jsonld")));
		if (! jwtVerifiableCredential.verify_RSA_RS256(TestUtil.testRSAPublicKey)) throw new GeneralSecurityException("Invalid signature.");

		String jwtPayload = jwtVerifiableCredential.getJwsObject().getPayload().toString();
		String jwtPayloadVerifiableCredential = jwtVerifiableCredential.getPayloadObject().toJson();

		assertNotNull(jwtPayload);
		assertNotNull(jwtPayloadVerifiableCredential);

		assertEquals(TestUtil.read(VerifyCredentialTest.class.getResourceAsStream("jwt.payload.vc.jsonld")).trim(), jwtPayload.trim());

		VerifiableCredential verifiableCredential = FromJwtConverter.fromJwtVerifiableCredential(jwtVerifiableCredential);

		assertNotNull(verifiableCredential);

		assertTrue(verifiableCredential.getTypes().contains("DriversLicenseCredential"));
		assertEquals(URI.create("urn:uuid:a87bdfb8-a7df-4bd9-ae0d-d883133538fe"), verifiableCredential.getId());
		assertEquals(URI.create("did:sov:1yvXbmgPoUm4dl66D7KhyD"), verifiableCredential.getIssuer());
		assertEquals(JsonLDUtils.DATE_FORMAT.parse("2019-06-16T18:56:59Z"), verifiableCredential.getIssuanceDate());
		assertEquals(JsonLDUtils.DATE_FORMAT.parse("2019-06-17T18:56:59Z"), verifiableCredential.getExpirationDate());

		CredentialSubject credentialSubject = verifiableCredential.getCredentialSubject();
		assertEquals(URI.create("did:sov:21tDAKCERh95uGgKbJNHYp"), credentialSubject.getId());
		Map<String, Object> jsonLdDriversLicenseObject = JsonLDUtils.jsonLdGetJsonObject(credentialSubject.getJsonObject(), "driversLicense");
		assertEquals("trucks", jsonLdDriversLicenseObject.get("licenseClass"));
	}
}
