package com.danubetech.verifiablecredentials;

import foundation.identity.jsonld.JsonLDUtils;
import info.weboftrust.ldsignatures.LdProof;
import info.weboftrust.ldsignatures.signer.Ed25519Signature2018LdSigner;
import info.weboftrust.ldsignatures.signer.RsaSignature2018LdSigner;
import info.weboftrust.ldsignatures.suites.SignatureSuites;
import info.weboftrust.ldsignatures.verifier.Ed25519Signature2018LdVerifier;
import info.weboftrust.ldsignatures.verifier.RsaSignature2018LdVerifier;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.net.URI;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SignPresentationTest {

	@Test
	void testSign() throws Throwable {

		VerifiablePresentation verifiablePresentation = VerifiablePresentation.fromJson(new InputStreamReader(VerifyCredentialTest.class.getResourceAsStream("input.vp.jsonld")));

		URI creator = URI.create("did:sov:1yvXbmgPoUm4dl66D7KhyD#keys-1");
		Date created = JsonLDUtils.DATE_FORMAT.parse("2018-01-01T21:19:10Z");
		String domain = null;
		String nonce = "c0ae1c8e-c7e7-469f-b252-86e6a0e7387e";

		RsaSignature2018LdSigner signer = new RsaSignature2018LdSigner(TestUtil.testRSAPrivateKey);
		signer.setCreator(creator);
		signer.setCreated(created);
		signer.setDomain(domain);
		signer.setNonce(nonce);
		LdProof ldSignature = signer.sign(verifiablePresentation, true, false);

		assertEquals(SignatureSuites.SIGNATURE_SUITE_RSASIGNATURE2018.getTerm(), ldSignature.getType());
		assertEquals(creator, ldSignature.getCreator());
		assertEquals(created, ldSignature.getCreated());
		assertEquals(domain, ldSignature.getDomain());
		assertEquals(nonce, ldSignature.getNonce());
		assertEquals("eyJjcml0IjpbImI2NCJdLCJiNjQiOmZhbHNlLCJhbGciOiJSUzI1NiJ9..R1b-LuaT2_UxPDoLAUUSMrkZt3m51ukSz7nBMr8TMETIpQIaqM_o8r7aB3_NeGJk5o-U3T6WP7W7UhFU-Gkiqtk_b9CWZYVbaGt9SSztf6ElhsbJ4alxj-1j77Vhub671UYGDrRBtH1I0gG5hu4eSE6TsDlUMdTKgwC27XKzJobEwWU65VGxBWSPAywDzjbA3GERK8e6_8pxz7CrVUDcVqdJz11293bsYPYNi4-59Am59G3H5RywwwFs_La_AROTqpAD9UJasITr2N1lRvQG3IOe6vhtQd_ROAYdue-BUqrKH0sTsLmOvnRqEIYB3EnruGiB-2X5-4aAFwzkT_wx-w", ldSignature.getJws());

		RsaSignature2018LdVerifier verifier = new RsaSignature2018LdVerifier(TestUtil.testRSAPublicKey);
		boolean verify = verifier.verify(verifiablePresentation);
		assertTrue(verify);
	}
}
