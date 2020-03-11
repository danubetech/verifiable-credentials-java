package com.danubetech.verifiablecredentials;

import com.github.jsonldjava.core.JsonLdConsts;
import com.github.jsonldjava.core.JsonLdOptions;
import com.github.jsonldjava.core.JsonLdProcessor;
import com.github.jsonldjava.utils.JsonUtils;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CanonicalizationTest {

	@SuppressWarnings("unchecked")
	@Test
	void testCanonicalization() throws Exception {

		LinkedHashMap<String, Object> jsonLdObject = (LinkedHashMap<String, Object>) JsonUtils.fromInputStream(CanonicalizationTest.class.getResourceAsStream("verifiable-credential.input.jsonld"));
		String canonicalizedDocument = TestUtil.read(CanonicalizationTest.class.getResourceAsStream("verifiable-credential.canonicalized.test"));

		assertEquals(buildCanonicalizeDocument(jsonLdObject), canonicalizedDocument);
	}

	private String buildCanonicalizeDocument(Object jsonLdObject) {
		JsonLdOptions options = new JsonLdOptions();
		options.format = JsonLdConsts.APPLICATION_NQUADS;
		return (String) JsonLdProcessor.normalize(jsonLdObject, options);
	}
}
