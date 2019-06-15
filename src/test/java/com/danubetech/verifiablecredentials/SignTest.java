package com.danubetech.verifiablecredentials;
import java.net.URI;
import java.util.LinkedHashMap;

import info.weboftrust.ldsignatures.LdSignature;
import info.weboftrust.ldsignatures.signer.RsaSignature2018LdSigner;
import info.weboftrust.ldsignatures.suites.SignatureSuites;
import junit.framework.TestCase;

public class SignTest extends TestCase {

	public void testSign() throws Exception {

		VerifiableCredential verifiableCredential = new VerifiableCredential();
		verifiableCredential.getContext().add("https://trafi.fi/credentials/v1");
		verifiableCredential.getType().add("DriversLicenseCredential");
		verifiableCredential.setIssuer("did:sov:1yvXbmgPoUm4dl66D7KhyD");
		verifiableCredential.setIssuanceDate(VerifiableCredential.ISSUANCE_DATE_FORMAT.parse("2017-10-24T05:33:31Z"));

		verifiableCredential.setCredentialSubject("did:sov:21tDAKCERh95uGgKbJNHYp");
		LinkedHashMap<String, Object> jsonLdClaimsObject = verifiableCredential.getJsonLdCredentialSubject();
		LinkedHashMap<String, Object> jsonLdDriversLicenseObject = new LinkedHashMap<String, Object> ();
		jsonLdDriversLicenseObject.put("licenseClass", "trucks");
		jsonLdClaimsObject.put("driversLicense", jsonLdDriversLicenseObject);

		URI creator = URI.create("did:sov:1yvXbmgPoUm4dl66D7KhyD#keys-1");
		String created = "2018-01-01T21:19:10Z";
		String domain = null;
		String nonce = "c0ae1c8e-c7e7-469f-b252-86e6a0e7387e";

		RsaSignature2018LdSigner signer = new RsaSignature2018LdSigner(creator, created, domain, nonce, TestUtil.testRSAPrivateKey);
		LdSignature ldSignature = signer.sign(verifiableCredential.getJsonLdObject());

		assertEquals(SignatureSuites.SIGNATURE_SUITE_RSASIGNATURE2018.getTerm(), ldSignature.getType());
		assertEquals(creator, ldSignature.getCreator());
		assertEquals(created, ldSignature.getCreated());
		assertEquals(domain, ldSignature.getDomain());
		assertEquals(nonce, ldSignature.getNonce());
		assertEquals("eyJhbGciOiJSUzI1NiIsImI2NCI6ZmFsc2UsImNyaXQiOlsiYjY0Il19..TF3iVD991QkINjdF-ohHs65DBwyoWZR-I-oC7H5u5Nb6cN4hEIHoIZHMYywDJ9qDwtoLL8sTOguEtuw2urK4juzwyUAOGtwF8QnsW162D0ty6frQ0gE3dWWdvDodAp_GtU_qFevkJ9-LMB-y8axn9X3lz3-8aWPnYju8uRZlLQ9HaUV8jQI4X7_BhNttUNc5WDweWl6Gp0wn_vhEA2OlYLfyIY-S3P6su4JUWdoRaQyguqkThL6dfDGHDaSH1JIDbakdUyZfiNqOrJ38qeabw2Pm883EpEJPwU-RptGM0-cm28b0hwgRVvRwOe8mhit6xtImrZWqpDfkNUoEOa4_HQ", ldSignature.getSignatureValue());
	}
}
