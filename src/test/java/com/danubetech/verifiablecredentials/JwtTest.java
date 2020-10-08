package com.danubetech.verifiablecredentials;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import com.danubetech.verifiablecredentials.jwt.FromJwtConverter;
import com.danubetech.verifiablecredentials.jwt.ToJwtConverter;
import foundation.identity.jsonld.JsonLDUtils;
import org.junit.jupiter.api.Test;

import com.danubetech.verifiablecredentials.jwt.JwtVerifiableCredential;
import com.nimbusds.jose.jwk.RSAKey;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonValue;

class JwtTest {

	static RSAKey rsaKey;

	static {

		rsaKey = new RSAKey.Builder(TestUtil.testRSAPublicKey).privateKey(TestUtil.testRSAPrivateKey).build();
	}

	@Test
	void testSign() throws Exception {

		Map<String, JsonValue> claims = new HashMap<>();
		JsonObject jsonLdDriversLicenseObject = Json.createObjectBuilder()
				.add("licenseClass", "trucks")
				.build();
		claims.put("driversLicense", jsonLdDriversLicenseObject);

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
	void testVerify() throws Exception {

		/*
		TODO: SKIP FOR NOW

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
		JsonObject jsonLdDriversLicenseObject = JsonLDUtils.jsonLdGetJsonObject(credentialSubject.getJsonObject(), "driversLicense");
		assertEquals("trucks", jsonLdDriversLicenseObject.getString("licenseClass"));

		 */
	}
}
