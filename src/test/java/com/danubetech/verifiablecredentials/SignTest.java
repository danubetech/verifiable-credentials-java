package com.danubetech.verifiablecredentials;
import java.net.URI;
import java.util.LinkedHashMap;

import com.danubetech.verifiablecredentials.VerifiableCredential;

import info.weboftrust.ldsignatures.LdSignature;
import info.weboftrust.ldsignatures.signer.RsaSignature2018LdSigner;
import info.weboftrust.ldsignatures.suites.SignatureSuites;
import junit.framework.TestCase;

public class SignTest extends TestCase {

	public void testSign() throws Exception {

		VerifiableCredential verifiableCredential = new VerifiableCredential();
		verifiableCredential.getContext().add("https://trafi.fi/credentials/v1");
		verifiableCredential.getType().add("DriversLicenseCredential");
		verifiableCredential.setIssuer(URI.create("did:sov:1yvXbmgPoUm4dl66D7KhyD"));
		verifiableCredential.setIssued("2018-01-01");

		verifiableCredential.setSubject("did:sov:21tDAKCERh95uGgKbJNHYp");
		LinkedHashMap<String, Object> jsonLdClaimsObject = verifiableCredential.getJsonLdClaimsObject();
		LinkedHashMap<String, Object> jsonLdDriversLicenseObject = new LinkedHashMap<String, Object> ();
		jsonLdDriversLicenseObject.put("licenseClass", "trucks");
		jsonLdClaimsObject.put("driversLicense", jsonLdDriversLicenseObject);

		URI creator = URI.create("did:sov:1yvXbmgPoUm4dl66D7KhyD#key1");
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
		assertEquals("eyJhbGciOiJSUzI1NiIsImI2NCI6ZmFsc2UsImNyaXQiOlsiYjY0Il19..msp8YLGYNvxoKokAtQpdp956jOqVgwT2XeFgLSUHd3g7GF7p4L4cKmekb344ZHjpyeETngdLd74L-lpbc63JFU5m8SypInKpth5aPndcTx5UGkQce7PHVJOvdee-1IXMzqiC-1g2veoc-T03GuC3d_G_GkKCp2qC6glFrouSaJDNTFjgt2K9qF3I9eU9L1fYajAZRq118xtRfKSClbgxrfmXnrUEUIBuNI4pIKLsAMBd3xsTkX6coausAiDBFlRVFpkfSNKzls6NSfqAGBzNx_FtE5R6rvyjIUYyrrZULCk5YP9JE10LzVZ2y7cPjaQ4DA0JlIpzYKEr5k8QAH2O2w", ldSignature.getSignatureValue());
	}
}
