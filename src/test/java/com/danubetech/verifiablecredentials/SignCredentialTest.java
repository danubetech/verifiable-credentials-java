package com.danubetech.verifiablecredentials;

import com.danubetech.verifiablecredentials.validation.Validation;
import foundation.identity.jsonld.JsonLDUtils;
import info.weboftrust.ldsignatures.LdProof;
import info.weboftrust.ldsignatures.signer.RsaSignature2018LdSigner;
import info.weboftrust.ldsignatures.suites.SignatureSuites;
import info.weboftrust.ldsignatures.verifier.RsaSignature2018LdVerifier;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.net.URI;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SignCredentialTest {

	@Test
	void testSignJsonLd() throws Throwable {

		VerifiableCredential verifiableCredential = VerifiableCredential.fromJson(
				new InputStreamReader(getClass().getResourceAsStream("input.vc.jsonld")));

		URI verificationMethod = URI.create("did:sov:1yvXbmgPoUm4dl66D7KhyD#keys-1");
		Date created = JsonLDUtils.DATE_FORMAT.parse("2018-01-01T21:19:10Z");
		String domain = null;
		String nonce = "c0ae1c8e-c7e7-469f-b252-86e6a0e7387e";

		RsaSignature2018LdSigner signer = new RsaSignature2018LdSigner(TestUtil.testRSAPrivateKey);
		signer.setVerificationMethod(verificationMethod);
		signer.setCreated(created);
		signer.setDomain(domain);
		signer.setNonce(nonce);
		LdProof ldProof = signer.sign(verifiableCredential, true, false);

		assertEquals(SignatureSuites.SIGNATURE_SUITE_RSASIGNATURE2018.getTerm(), ldProof.getType());
		assertEquals(verificationMethod, ldProof.getVerificationMethod());
		assertEquals(created, ldProof.getCreated());
		assertEquals(domain, ldProof.getDomain());
		assertEquals(nonce, ldProof.getNonce());
		assertEquals("eyJiNjQiOmZhbHNlLCJjcml0IjpbImI2NCJdLCJhbGciOiJSUzI1NiJ9..Sn-LB5t_x-kh9mUDq1DaS1GScj3PY_2fMnNnhq09x-ZBf6_EzYfvgFOpEvdLUwxkJiEt7B2x-LGg7bp-o7UEGCbIxWdGUTG7BGAsKsU18hUwOHrVNZ6VHovbxeFgK0iNMn0MObDiGdQbYMG8C71m3AvquUP00-2UiDcqNxmGAYg5tHv7SHXLEgvaz7SnIkBklj1yj_TMXreSGa_okbXFYxh7SkMfFcxHbBFShr0Fzd8DTn8tr_WvPHR7Tx3bkJHmqFx9Wo-0e7FkLeICsgmBKa5Hzz-y_1yEQPsDaZRRsbXfBD4krL7WTplJtAwnQ5Sy-L9cwZzNhCQC6KsggGjTgQ", ldProof.getJws());

		Validation.validateJsonLd(verifiableCredential);

		RsaSignature2018LdVerifier verifier = new RsaSignature2018LdVerifier(TestUtil.testRSAPublicKey);
		boolean verify = verifier.verify(verifiableCredential);
		assertTrue(verify);
	}

	@Test
	void testSignJson() throws Throwable {

		VerifiableCredential verifiableCredential = VerifiableCredential.fromJson(
				new InputStreamReader(getClass().getResourceAsStream("input.vc.json")));

		URI verificationMethod = URI.create("did:sov:1yvXbmgPoUm4dl66D7KhyD#keys-1");
		Date created = JsonLDUtils.DATE_FORMAT.parse("2018-01-01T21:19:10Z");
		String domain = null;
		String nonce = "c0ae1c8e-c7e7-469f-b252-86e6a0e7387e";

		RsaSignature2018LdSigner signer = new RsaSignature2018LdSigner(TestUtil.testRSAPrivateKey);
		signer.setVerificationMethod(verificationMethod);
		signer.setCreated(created);
		signer.setDomain(domain);
		signer.setNonce(nonce);
		LdProof ldProof = signer.sign(verifiableCredential, true, false);

		assertEquals(SignatureSuites.SIGNATURE_SUITE_RSASIGNATURE2018.getTerm(), ldProof.getType());
		assertEquals(verificationMethod, ldProof.getVerificationMethod());
		assertEquals(created, ldProof.getCreated());
		assertEquals(domain, ldProof.getDomain());
		assertEquals(nonce, ldProof.getNonce());
		assertEquals("eyJiNjQiOmZhbHNlLCJjcml0IjpbImI2NCJdLCJhbGciOiJSUzI1NiJ9..GZYi1V8tbMLl5rLIZarlj-aX3KDTTqFJFtQr-2cV910J6embA7_fQPalX3pZLzld9mQ0SdJq2hlxWpMzujXKOElfWidtxJzOyp93ZsSbrtfj7fGSV_CYOSfQ7A8n3SR4O3pp6ja4vmDmBhP95oJXh_BVTbtqvU7e-_GngC2B9uoBr4JJd2mxsOu2_97u_-scPWv9xUIm5rFTGfLz5sUGbMihY96fywSATn9mD5aLDql2thHnrkfYHgsxAqQDV-gcvlZHw5-TtxN-NnG3DD5K_mugmlV3x10ZGLC5QCw0q83LGVi7NmBMShALOFtcO5CourGDSmc1jL9qA95GXMH_dA", ldProof.getJws());

		Validation.validateJson(verifiableCredential);

		RsaSignature2018LdVerifier verifier = new RsaSignature2018LdVerifier(TestUtil.testRSAPublicKey);
		boolean verify = verifier.verify(verifiableCredential);
		assertTrue(verify);
	}
}
