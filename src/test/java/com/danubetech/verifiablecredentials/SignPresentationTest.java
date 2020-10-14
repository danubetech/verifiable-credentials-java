package com.danubetech.verifiablecredentials;

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

public class SignPresentationTest {

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
		assertEquals("eyJiNjQiOmZhbHNlLCJjcml0IjpbImI2NCJdLCJhbGciOiJSUzI1NiJ9..VqTXD63c1DN1saWhkRz-RJL2VMbzNrNQSBcdkLQ82Cv04YcsdmSDJe0odqEDxk77B81zTXpIyeYxUoz8kYzL3vCtaoJTFOUmBLjztBEendyC3EJlH0XL1TEpXoAxBe5J3GLp5CVOphDEfc5SHQz0lVBP3ssX844ndxFgPzJGmLykoLTr7QmRDFkAAErJEEWDsJpTHP1V9MGLsMfbpgPIwmcLutK8mKJcDN7hnjGnHbua3nLQSb5aO6Pdduz9SSCFNk8TDqzowVymuI3wPsZjNBDePZgGIu1b016D0MkkwH7Xy0jZ2JReRPph4VX2TANSTjDeXQ4dSEupyLwd1wWljQ", ldSignature.getJws());

		RsaSignature2018LdVerifier verifier = new RsaSignature2018LdVerifier(TestUtil.testRSAPublicKey);
		boolean verify = verifier.verify(verifiablePresentation);
		assertTrue(verify);
	}
}
