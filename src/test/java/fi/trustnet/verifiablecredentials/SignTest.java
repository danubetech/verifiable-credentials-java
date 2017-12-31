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
		assertEquals("eyJhbGciOiJSUzI1NiIsImI2NCI6ZmFsc2UsImNyaXQiOlsiYjY0Il19..N2BFAXxriD7rnDM6equaXeWEvxsI_b-55Rwfg_KwuNNICV_KqCKJ4u9yeP10SkU__8HOtBFs5lRJnJghHhyualJ6MubKsHVq-_4n3-frq-BdKnUEb_UaEMxubw-WyL5GO5ZVDx5Xq3ZZa6GyNUS6wAU-BUS8V4x9R-QXfoLOMW6JvhckYBdTIEhndgnZ_AL77WrhOOMFmujJqrHIZ0daBWwVFnsH5BzFBQvJ8Vj-sIqEGkE4Itn42AEQgLXgWXdyngbEUYTVIJVtW5Ec9HG-F3xiDyiI77q8AxlX8G4UHN3DzMnrZBa8frUnPjSSEcbOVrErurZ35L7MX4_f2YhkYQ", ldSignature.getSignatureValue());
	}
}
