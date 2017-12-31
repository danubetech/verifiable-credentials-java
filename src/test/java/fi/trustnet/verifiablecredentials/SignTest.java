package fi.trustnet.verifiablecredentials;
import java.net.URI;
import java.util.LinkedHashMap;

import info.weboftrust.ldsignatures.LdSignature;
import info.weboftrust.ldsignatures.signer.RsaSignature2017LdSigner;
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

		RsaSignature2017LdSigner signer = new RsaSignature2017LdSigner(creator, created, domain, nonce, TestUtil.testRSAPrivateKey);
		LdSignature ldSignature = signer.sign(verifiableCredential.getJsonLdObject());

		assertEquals(SignatureSuites.SIGNATURE_SUITE_RSASIGNATURE2017.getTerm(), ldSignature.getType());
		assertEquals(creator, ldSignature.getCreator());
		assertEquals(created, ldSignature.getCreated());
		assertEquals(domain, ldSignature.getDomain());
		assertEquals(nonce, ldSignature.getNonce());
		assertEquals("eyJhbGciOiJSUzI1NiIsImI2NCI6ZmFsc2UsImNyaXQiOlsiYjY0Il19..LuQEd67YJH8auxrU7_SGeCW77tPOmd2CAR41MxG_gSkHbQVOiWtMshtY71AGRgCUu5EVkOHNHLDU4EWSLaPCGOApoEc4TPn2srOi3DyDYZkgPRlUiGGNiy8bBk8Gfli_7qFA053wtAdHNf0VGVrXn0QBxSd7PSN5g2g0CM6TKJWp96WmhwdXAWgpqAhK8qe9tIXORr2TZB4ANR9bCtlcTk8haJawbLda2DHtPY_zSJqAaXzr7qC_vqa2jYRQgS65UA2dsm4du-ajVProniyV1p6Iu82coqDQPELW30hEXyintNRMjK8e_6z-wEsNpUH5ir_H97ciX60vV7e7nKjDRQ", ldSignature.getSignatureValue());
	}
}
