package com.danubetech.verifiablecredentials;

import com.danubetech.dataintegrity.DataIntegrityProof;
import com.danubetech.dataintegrity.signer.RsaSignature2018LdSigner;
import com.danubetech.dataintegrity.suites.DataIntegritySuites;
import com.danubetech.dataintegrity.verifier.RsaSignature2018LdVerifier;
import com.danubetech.verifiablecredentials.util.TestKeys;
import com.danubetech.verifiablecredentials.util.TestUtil;
import com.danubetech.verifiablecredentials.validation.Validation;
import foundation.identity.jsonld.JsonLDUtils;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.net.URI;
import java.util.Date;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BasicSignPresentationTest {

	@Test
	void testSign() throws Throwable {

		VerifiablePresentation verifiablePresentation = VerifiablePresentation.fromJson(new InputStreamReader(Objects.requireNonNull(BasicVerifyCredentialTest.class.getResourceAsStream("input.vp.jsonld"))));

		URI verificationMethod = URI.create("did:sov:1yvXbmgPoUm4dl66D7KhyD#keys-1");
		Date created = JsonLDUtils.DATE_FORMAT.parse("2018-01-01T21:19:10Z");
		String domain = null;
		String nonce = "c0ae1c8e-c7e7-469f-b252-86e6a0e7387e";

		RsaSignature2018LdSigner signer = new RsaSignature2018LdSigner(TestKeys.testRSAPrivateKey);
		signer.setVerificationMethod(verificationMethod);
		signer.setCreated(created);
		signer.setDomain(domain);
		signer.setNonce(nonce);
		DataIntegrityProof dataIntegrityProof = signer.sign(verifiablePresentation, true, false);

		assertEquals(DataIntegritySuites.DATA_INTEGRITY_SUITE_RSASIGNATURE2018.getTerm(), dataIntegrityProof.getType());
		assertEquals(verificationMethod, dataIntegrityProof.getVerificationMethod());
		assertEquals(created, dataIntegrityProof.getCreated());
		assertEquals(domain, dataIntegrityProof.getDomain());
		assertEquals(nonce, dataIntegrityProof.getNonce());
		assertEquals("eyJiNjQiOmZhbHNlLCJjcml0IjpbImI2NCJdLCJhbGciOiJSUzI1NiJ9..P5YxXCKV-bkWU0BmsFgvmG9bslwUcPMi6F6YpXZsy7bqFtfbb0y09syhydWMe0iKDa43_dbKBqvzfrLyHwIxQYYzU_AXggOy0sjUfc2ni-LhTHBxSBANPWkSjWgc2XAb26KvdlGmbnSI76gq-4ItiA43xYhRdocseqfQznOsYrDSg4FIxJE_Hn_kZR1srxAeyFhOfKdGufk57TH56v8uLJulnkyGDw2T1DSfD1sGj8R-rnWXZlY6lLcbIUMza4QeRu9SZj2gkHnE8sR3RDm52_jHx2keYbJkj8b4FacVSPGa1hXEIryQOt6EScf9SwLx1ojwMAPJp2BHCVZsF5MAog", dataIntegrityProof.getJws());

		Validation.validate(verifiablePresentation);

		RsaSignature2018LdVerifier verifier = new RsaSignature2018LdVerifier(TestKeys.testRSAPublicKey);
		boolean verify = verifier.verify(verifiablePresentation);
		assertTrue(verify);
	}
}
