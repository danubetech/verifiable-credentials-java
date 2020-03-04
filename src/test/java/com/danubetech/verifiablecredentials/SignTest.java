package com.danubetech.verifiablecredentials;
import java.net.URI;
import java.util.LinkedHashMap;

import com.github.jsonldjava.utils.JsonUtils;

import info.weboftrust.ldsignatures.LdSignature;
import info.weboftrust.ldsignatures.signer.RsaSignature2018LdSigner;
import info.weboftrust.ldsignatures.suites.SignatureSuites;
import junit.framework.TestCase;

public class SignTest extends TestCase {

	public void testSign() throws Exception {

		LinkedHashMap<String, Object> jsonLdObject = (LinkedHashMap<String, Object>) JsonUtils.fromInputStream(VerifyTest.class.getResourceAsStream("verifiable-credential.input.jsonld"));
		VerifiableCredential verifiableCredential = VerifiableCredential.fromJsonLdObject(jsonLdObject);

		URI creator = URI.create("did:sov:1yvXbmgPoUm4dl66D7KhyD#keys-1");
		String created = "2018-01-01T21:19:10Z";
		String domain = null;
		String nonce = "c0ae1c8e-c7e7-469f-b252-86e6a0e7387e";

		RsaSignature2018LdSigner signer = new RsaSignature2018LdSigner(TestUtil.testRSAPrivateKey);
		signer.setCreator(creator);
		signer.setCreated(created);
		signer.setDomain(domain);
		signer.setNonce(nonce);
		LdSignature ldSignature = signer.sign(verifiableCredential.getJsonLdObject());

		assertEquals(SignatureSuites.SIGNATURE_SUITE_RSASIGNATURE2018.getTerm(), ldSignature.getType());
		assertEquals(creator, ldSignature.getCreator());
		assertEquals(created, ldSignature.getCreated());
		assertEquals(domain, ldSignature.getDomain());
		assertEquals(nonce, ldSignature.getNonce());
		assertEquals("eyJjcml0IjpbImI2NCJdLCJiNjQiOmZhbHNlLCJhbGciOiJSUzI1NiJ9..HS8vTNT4-3L3XSygBizStCNldcP4JGWkZt2YyVtMrQzcWJCvRMe5Px-rwdEgfPxXWDx1RN9WLLPsNrB5H2m5dw5AtISqDH3Roqos8C2U6-BZqS5lC04dBWbNBwt-6rHe16PlzKfXcImTGjVLSiy6u2vDHIuUHSU3iUUAP1LyyDv66TabkbRQfd66FDvIf5dCY5CUiv96KJyDl19ORUHqQip_1HlBRtdskxnFk7rbnKAzDNPaGt019NOaFZEicsGQzB2Lu3V7O0Dzo9dHXDuHShiLg7M67w5ax92dFiPXj7f5vCvGxKBgoRMTY_GU3LC_MJBHqNuWFQxrb6eGWjcynA", ldSignature.getJws());
	}
}
