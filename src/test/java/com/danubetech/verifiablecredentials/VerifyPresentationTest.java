package com.danubetech.verifiablecredentials;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.codec.DecoderException;
import org.bitcoinj.core.Base58;
import org.junit.jupiter.api.Test;

import com.github.jsonldjava.utils.JsonUtils;

import info.weboftrust.ldsignatures.util.CanonicalizationUtil;
import info.weboftrust.ldsignatures.verifier.Ed25519Signature2018LdVerifier;

class VerifyPresentationTest {

	final static byte[] publicKeyPresentation1;
	final static byte[] publicKeyPresentation2;
	final static byte[] publicKeyCredential1;
	final static byte[] publicKeyCredential2;

	final static VerifiablePresentation verifiablePresentationGood1;
	final static VerifiablePresentation verifiablePresentationGood2;
	final static VerifiablePresentation verifiablePresentationBad1;
	final static VerifiablePresentation verifiablePresentationBad2;
	final static VerifiableCredential verifiableCredentialGood1;
	final static VerifiableCredential verifiableCredentialGood2;
	final static VerifiableCredential verifiableCredentialBad1;
	final static VerifiableCredential verifiableCredentialBad2;

	static {

		try {

			publicKeyPresentation1 = Base58.decode("DqS5F3GVe3rCxucgi4JBNagjv4dKoHc8TDLDw9kR58Pz");
			publicKeyPresentation2 = Base58.decode("5yKdnU7ToTjAoRNDzfuzVTfWBH38qyhE1b9xh4v8JaWF");
			publicKeyCredential1 = Base58.decode("5TVraf9itbKXrRvt2DSS95Gw4vqU3CHAdetoufdcKazA");
			publicKeyCredential2 = Base58.decode("5yKdnU7ToTjAoRNDzfuzVTfWBH38qyhE1b9xh4v8JaWF");

			LinkedHashMap<String, Object> jsonLdObjectGood1 = (LinkedHashMap<String, Object>) JsonUtils.fromInputStream(VerifyPresentationTest.class.getResourceAsStream("verifiable-presentation1.ldp.good.jsonld"));
			LinkedHashMap<String, Object> jsonLdObjectGood2 = (LinkedHashMap<String, Object>) JsonUtils.fromInputStream(VerifyPresentationTest.class.getResourceAsStream("verifiable-presentation2.ldp.good.jsonld"));
			LinkedHashMap<String, Object> jsonLdObjectBad1 = (LinkedHashMap<String, Object>) JsonUtils.fromInputStream(VerifyPresentationTest.class.getResourceAsStream("verifiable-presentation1.ldp.bad.jsonld"));
			LinkedHashMap<String, Object> jsonLdObjectBad2 = (LinkedHashMap<String, Object>) JsonUtils.fromInputStream(VerifyPresentationTest.class.getResourceAsStream("verifiable-presentation2.ldp.bad.jsonld"));
			verifiablePresentationGood1 = VerifiablePresentation.fromJsonLdObject(jsonLdObjectGood1);
			verifiablePresentationGood2 = VerifiablePresentation.fromJsonLdObject(jsonLdObjectGood2);
			verifiablePresentationBad1 = VerifiablePresentation.fromJsonLdObject(jsonLdObjectBad1);
			verifiablePresentationBad2 = VerifiablePresentation.fromJsonLdObject(jsonLdObjectBad2);
			verifiableCredentialGood1 = verifiablePresentationGood1.getVerifiableCredential();
			verifiableCredentialGood2 = verifiablePresentationGood2.getVerifiableCredential();
			verifiableCredentialBad1 = verifiablePresentationBad1.getVerifiableCredential();
			verifiableCredentialBad2 = verifiablePresentationBad2.getVerifiableCredential();
		} catch (Exception ex) {

			throw new RuntimeException(ex.getMessage(), ex);
		}
	}

	/*
	 * GOOD CREDENTIAL
	 */

	@Test
	void testVerifyGoodCredential1() throws Exception {

		Ed25519Signature2018LdVerifier verifier = new Ed25519Signature2018LdVerifier(publicKeyCredential1);
		boolean verify = verifier.verify(verifiableCredentialGood1.getJsonLdObject());

		assertTrue(verify);
		assertEquals("Bachelor of Science and Arts", (String) ((Map<String, Object>) verifiableCredentialGood1.getJsonLdCredentialSubject().get("degree")).get("name"));
	}

	@Test
	void testVerifyGoodCredential2() throws Exception {

		Ed25519Signature2018LdVerifier verifier = new Ed25519Signature2018LdVerifier(publicKeyCredential2);
		boolean verify = verifier.verify(verifiableCredentialGood2.getJsonLdObject());

		assertTrue(verify);
		assertEquals("Bachelor of Science and Arts", (String) ((Map<String, Object>) verifiableCredentialGood1.getJsonLdCredentialSubject().get("degree")).get("name"));
	}

	/*
	 * BAD CREDENTIAL
	 */

	@Test
	void testVerifyBadCredential1() throws Exception {

		Ed25519Signature2018LdVerifier verifier = new Ed25519Signature2018LdVerifier(publicKeyCredential1);
		boolean verify = verifier.verify(verifiableCredentialBad1.getJsonLdObject());

		assertFalse(verify);
		assertEquals("Master of Science and Arts", (String) ((Map<String, Object>) verifiableCredentialBad1.getJsonLdCredentialSubject().get("degree")).get("name"));
	}

	@Test
	void testVerifyBadCredential2() throws Exception {

		Ed25519Signature2018LdVerifier verifier = new Ed25519Signature2018LdVerifier(publicKeyCredential2);
		boolean verify = verifier.verify(verifiableCredentialBad2.getJsonLdObject());

		assertFalse(verify);
		assertEquals("Master of Science and Arts", (String) ((Map<String, Object>) verifiableCredentialBad2.getJsonLdCredentialSubject().get("degree")).get("name"));
	}

	/*
	 * GOOD PRESENTATION
	 */

	@Test
	void testVerifyGoodPresentation1() throws Exception {

		VerifiablePresentation verifiablePresentation = VerifiablePresentation.fromJsonString(verifiablePresentationGood1.toJsonString());

		CanonicalizationUtil.fixImplicitGraph(verifiablePresentation.getVerifiableCredential().getLdSignature().getJsonLdProofObject());
		CanonicalizationUtil.fixImplicitGraph(verifiablePresentation.getVerifiableCredential().getJsonLdObject());

		Ed25519Signature2018LdVerifier verifier = new Ed25519Signature2018LdVerifier(publicKeyPresentation1);
		boolean verify = verifier.verify(verifiablePresentation.getJsonLdObject());

		assertTrue(verify);
	}

	@Test
	void testVerifyGoodPresentation2() throws Exception {

		VerifiablePresentation verifiablePresentation = VerifiablePresentation.fromJsonString(verifiablePresentationGood2.toJsonString());

		CanonicalizationUtil.fixImplicitGraph(verifiablePresentation.getVerifiableCredential().getLdSignature().getJsonLdProofObject());
		CanonicalizationUtil.fixImplicitGraph(verifiablePresentation.getVerifiableCredential().getJsonLdObject());

		Ed25519Signature2018LdVerifier verifier = new Ed25519Signature2018LdVerifier(publicKeyPresentation2);
		boolean verify = verifier.verify(verifiablePresentation.getJsonLdObject());

		assertTrue(verify);
	}

	/*
	 * BAD PRESENTATION
	 */

	@Test
	void testVerifyBadPresentation1() throws Exception {

		VerifiablePresentation verifiablePresentation = VerifiablePresentation.fromJsonString(verifiablePresentationBad1.toJsonString());

		CanonicalizationUtil.fixImplicitGraph(verifiablePresentation.getVerifiableCredential().getLdSignature().getJsonLdProofObject());
		CanonicalizationUtil.fixImplicitGraph(verifiablePresentation.getVerifiableCredential().getJsonLdObject());

		Ed25519Signature2018LdVerifier verifier = new Ed25519Signature2018LdVerifier(publicKeyPresentation1);
		boolean verify = verifier.verify(verifiablePresentation.getJsonLdObject());

		assertFalse(verify);
	}

	@Test
	void testVerifyBadPresentation2() throws Exception {

		VerifiablePresentation verifiablePresentation = VerifiablePresentation.fromJsonString(verifiablePresentationBad2.toJsonString());

		CanonicalizationUtil.fixImplicitGraph(verifiablePresentation.getVerifiableCredential().getLdSignature().getJsonLdProofObject());
		CanonicalizationUtil.fixImplicitGraph(verifiablePresentation.getVerifiableCredential().getJsonLdObject());

		Ed25519Signature2018LdVerifier verifier = new Ed25519Signature2018LdVerifier(publicKeyPresentation2);
		boolean verify = verifier.verify(verifiablePresentation.getJsonLdObject());

		assertFalse(verify);
	}
}
