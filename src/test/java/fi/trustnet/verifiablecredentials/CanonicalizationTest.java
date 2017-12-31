package fi.trustnet.verifiablecredentials;

import java.util.LinkedHashMap;

import com.github.jsonldjava.utils.JsonUtils;

import info.weboftrust.ldsignatures.util.CanonicalizationUtil;
import junit.framework.TestCase;

public class CanonicalizationTest extends TestCase {

	@SuppressWarnings("unchecked")
	public void testCanonicalization() throws Exception {

		LinkedHashMap<String, Object> jsonLdObject = (LinkedHashMap<String, Object>) JsonUtils.fromInputStream(CanonicalizationTest.class.getResourceAsStream("verifiable-credential.test.jsonld"));
		String canonicalizedDocument = TestUtil.read(CanonicalizationTest.class.getResourceAsStream("verifiable-credential.canonicalized.test"));

		assertEquals(CanonicalizationUtil.buildCanonicalizedDocument(jsonLdObject), canonicalizedDocument);
	}
}
