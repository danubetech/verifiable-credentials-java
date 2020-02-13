package com.danubetech.verifiablecredentials;

import com.danubetech.verifiablecredentials.jwt.JwtVerifiableCredential;
import org.junit.jupiter.api.Test;

import java.security.GeneralSecurityException;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class JwtTest {

    @Test
    void testSign() throws Exception {

        VerifiableCredential verifiableCredential = new VerifiableCredential();
        verifiableCredential.getContext().add("https://trafi.fi/credentials/v1");
        verifiableCredential.getType().add("DriversLicenseCredential");
        verifiableCredential.setId("urn:uuid:a87bdfb8-a7df-4bd9-ae0d-d883133538fe");
        verifiableCredential.setIssuer("did:sov:1yvXbmgPoUm4dl66D7KhyD");
        verifiableCredential.setIssuanceDate(VerifiableCredential.DATE_FORMAT.parse("2019-06-16T18:56:59Z"));
        verifiableCredential.setExpirationDate(VerifiableCredential.DATE_FORMAT.parse("2019-06-17T18:56:59Z"));

        verifiableCredential.setCredentialSubject("did:sov:21tDAKCERh95uGgKbJNHYp");
        LinkedHashMap<String, Object> jsonLdCredentialSubject = verifiableCredential.getJsonLdCredentialSubject();
        LinkedHashMap<String, Object> jsonLdDriversLicenseObject = new LinkedHashMap<>();
        jsonLdDriversLicenseObject.put("licenseClass", "trucks");
        jsonLdCredentialSubject.put("driversLicense", jsonLdDriversLicenseObject);

        JwtVerifiableCredential jwtVerifiableCredential = JwtVerifiableCredential.fromVerifiableCredential(verifiableCredential);
        String jwtString = jwtVerifiableCredential.sign_RSA_RS256(TestUtil.rsaKey);
        String jwtPayload = jwtVerifiableCredential.getJwsObject().getPayload().toString();

        assertNotNull(jwtString);
        assertNotNull(jwtPayload);

        assertEquals(TestUtil.read(VerifyTest.class.getResourceAsStream("verifiable-credential.jwt.jsonld")).trim(), jwtString.trim());
        assertEquals(TestUtil.read(VerifyTest.class.getResourceAsStream("verifiable-credential.jwt.payload.jsonld")).trim(), jwtPayload.trim());
    }

    @Test
    void testVerify() throws Exception {

        JwtVerifiableCredential jwtVerifiableCredential = JwtVerifiableCredential.fromCompactSerialization(TestUtil.read(VerifyTest.class.getResourceAsStream("verifiable-credential.jwt.jsonld")));
        if (!jwtVerifiableCredential.verify_RSA_RS256(TestUtil.testRSAPublicKey))
            throw new GeneralSecurityException("Invalid signature.");

        String jwtPayload = jwtVerifiableCredential.getJwsObject().getPayload().toString();
        String jwtPayloadVerifiableCredential = jwtVerifiableCredential.getPayloadObject().toJsonString();

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
