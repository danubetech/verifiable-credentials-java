package com.danubetech.verifiablecredentials;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStreamReader;
import java.net.URI;
import java.util.Date;

import foundation.identity.jsonld.JsonLDUtils;
import info.weboftrust.ldsignatures.signer.Ed25519Signature2018LdSigner;
import info.weboftrust.ldsignatures.verifier.Ed25519Signature2018LdVerifier;
import info.weboftrust.ldsignatures.verifier.RsaSignature2018LdVerifier;
import org.junit.jupiter.api.Test;

import info.weboftrust.ldsignatures.LdProof;
import info.weboftrust.ldsignatures.signer.RsaSignature2018LdSigner;
import info.weboftrust.ldsignatures.suites.SignatureSuites;

public class SignCredentialTest {

	@Test
	void testSign() throws Throwable {

		VerifiableCredential verifiableCredential = VerifiableCredential.fromJson(new InputStreamReader(VerifyCredentialTest.class.getResourceAsStream("input.vc.jsonld")));

		URI creator = URI.create("did:sov:1yvXbmgPoUm4dl66D7KhyD#keys-1");
		Date created = JsonLDUtils.DATE_FORMAT.parse("2018-01-01T21:19:10Z");
		String domain = null;
		String nonce = "c0ae1c8e-c7e7-469f-b252-86e6a0e7387e";

		RsaSignature2018LdSigner signer = new RsaSignature2018LdSigner(TestUtil.testRSAPrivateKey);
		signer.setCreator(creator);
		signer.setCreated(created);
		signer.setDomain(domain);
		signer.setNonce(nonce);
		LdProof ldProof = signer.sign(verifiableCredential, true, false);

		assertEquals(SignatureSuites.SIGNATURE_SUITE_RSASIGNATURE2018.getTerm(), ldProof.getType());
		assertEquals(creator, ldProof.getCreator());
		assertEquals(created, ldProof.getCreated());
		assertEquals(domain, ldProof.getDomain());
		assertEquals(nonce, ldProof.getNonce());
		assertEquals("eyJiNjQiOmZhbHNlLCJjcml0IjpbImI2NCJdLCJhbGciOiJSUzI1NiJ9..m-5V3caaBBVWldf9c83ve2gBfgDdZqVrBGQZl5flXwgskOTkBlEIODMkK0j203PeOIfHSYZUO5wj4bGCSHZ8ORaQWoJFX_znskHipBvH2wc4TBu4mgMIJefP14gl1kDGF6Vw_BtbP6EWpmm2dnhKztDo-X4XuUGpbyBJ-lVX_CGl0qftzPAZtabUEmc-dB911EfiNbslJ6qJYKd_r7D7scSVKnJtE0xXK4obrWJg2_tx-cD1KWtrJXf-wIXa43gdwlFcVLLrHvzglf-KkHezxI9Ee6RNcIP-YQNrsjNet4obRzZuwcQAJ8-pBwdF901AgDKAI0Q85cjy_LGbxqb0GA", ldProof.getJws());

		RsaSignature2018LdVerifier verifier = new RsaSignature2018LdVerifier(TestUtil.testRSAPublicKey);
		boolean verify = verifier.verify(verifiableCredential);
		assertTrue(verify);
	}
}
