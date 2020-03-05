package com.danubetech.verifiablecredentials;
import java.net.URI;
import java.util.Date;
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
		Date created = VerifiableCredential.DATE_FORMAT.parse("2018-01-01T21:19:10Z");
		String domain = null;
		String nonce = "c0ae1c8e-c7e7-469f-b252-86e6a0e7387e";

		RsaSignature2018LdSigner signer = new RsaSignature2018LdSigner(TestUtil.testRSAPrivateKey);
		signer.setCreator(creator);
		signer.setCreated(created);
		signer.setDomain(domain);
		signer.setNonce(nonce);
		LdSignature ldSignature = signer.sign(verifiableCredential.getJsonLdObject(), true, false);

		System.out.println(verifiableCredential.toPrettyJsonString());
		
		assertEquals(SignatureSuites.SIGNATURE_SUITE_RSASIGNATURE2018.getTerm(), ldSignature.getType());
		assertEquals(creator, ldSignature.getCreator());
		assertEquals(created, ldSignature.getCreated());
		assertEquals(domain, ldSignature.getDomain());
		assertEquals(nonce, ldSignature.getNonce());
		assertEquals("eyJjcml0IjpbImI2NCJdLCJiNjQiOmZhbHNlLCJhbGciOiJSUzI1NiJ9..pZtcYsR_vEtm5ZLEGNJZPYuWQeD_drBG55gDrX4V-Zxe-R0ue90QzfLn9ZAheBrnWxQNobOsmc0wLBLnSNp5fMbmxHzaMuPadkMXgyqdgH6r13YHidLhtsg8OWGBU0nlFQe5NPztP8HJdgdTmK8ohQlx1pB7BQuB3-iY_cHO7PLuVJFplI616v7zINW46SNc6PE2cJ_O-dnehA_PaNCnUn7s-TfqTYC7LQ2N95XImBt9zW5DYE7NRY7ZZh1sBNaSnHweOYZay-W6u789J3zTFxgbl-hZGziFA4EOJoWUAdb1vCBzlBWasfmkD0LAxlv7UV0Fp3wG2laIFiTwgrm9eg", ldSignature.getJws());
	}
}
