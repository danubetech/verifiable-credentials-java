package com.danubetech.verifiablecredentials;

import com.danubetech.dataintegrity.DataIntegrityProof;
import com.danubetech.dataintegrity.signer.RsaSignature2018LdSigner;
import com.danubetech.dataintegrity.suites.DataIntegritySuites;
import com.danubetech.dataintegrity.verifier.RsaSignature2018LdVerifier;
import com.danubetech.verifiablecredentials.util.TestKeys;
import com.danubetech.verifiablecredentials.validation.Validation;
import foundation.identity.jsonld.JsonLDUtils;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.net.URI;
import java.util.Date;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BasicSignCredentialTest {

	@Test
	void testSign() throws Throwable {

		VerifiableCredential verifiableCredential = VerifiableCredential.fromJson(new InputStreamReader(Objects.requireNonNull(BasicVerifyCredentialTest.class.getResourceAsStream("input.vc.jsonld"))));

		URI verificationMethod = URI.create("did:sov:1yvXbmgPoUm4dl66D7KhyD#keys-1");
		Date created = JsonLDUtils.DATE_FORMAT.parse("2018-01-01T21:19:10Z");
		String domain = null;
		String nonce = "c0ae1c8e-c7e7-469f-b252-86e6a0e7387e";

		RsaSignature2018LdSigner signer = new RsaSignature2018LdSigner(TestKeys.testRSAPrivateKey);
		signer.setVerificationMethod(verificationMethod);
		signer.setCreated(created);
		signer.setDomain(domain);
		signer.setNonce(nonce);
		DataIntegrityProof dataIntegrityProof = signer.sign(verifiableCredential, true, false);

		assertEquals(DataIntegritySuites.DATA_INTEGRITY_SUITE_RSASIGNATURE2018.getTerm(), dataIntegrityProof.getType());
		assertEquals(verificationMethod, dataIntegrityProof.getVerificationMethod());
		assertEquals(created, dataIntegrityProof.getCreated());
		assertEquals(domain, dataIntegrityProof.getDomain());
		assertEquals(nonce, dataIntegrityProof.getNonce());
		assertEquals("eyJiNjQiOmZhbHNlLCJjcml0IjpbImI2NCJdLCJhbGciOiJSUzI1NiJ9..PsLGdGwDk19y1MTC6_jpBzLqwE43877Z8LtgVcJNSxmdjdltiC_yu3ZN0YxWGenfkywFAkockuqGjqGGy4v90I6k4PvkubgWSofhrjcmiFnKRqClRHRbHS6lB1-NBJJcfOz4OQtkKg0NxtMO0jwZWEGPS97IUeR0aDGLAj2raU8ai1hMIfEx0M4ntq6rsaKileQC5_k6SuOMkycNIgdN1-WX8WvK_36i0BZwOzPTmFEt-J66X7HaYT8ZEpq0xl2WYBKmg8Vnf4MaZLIInJyGi7kJ1kvUFw7pIok_vZMyOW0O8P8TjzRTSNhJKeznLJBEEg-YkNPpMZZBm6-gY54Clg", dataIntegrityProof.getJws());

		Validation.validate(verifiableCredential);

		RsaSignature2018LdVerifier verifier = new RsaSignature2018LdVerifier(TestKeys.testRSAPublicKey);
		boolean verify = verifier.verify(verifiableCredential);
		assertTrue(verify);
	}
}
