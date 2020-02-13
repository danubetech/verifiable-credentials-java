package com.danubetech.verifiablecredentials;

import com.github.jsonldjava.utils.JsonUtils;
import info.weboftrust.ldsignatures.util.CanonicalizationUtil;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CanonicalizationTest {

    @Test
    void testCanonicalization() throws Exception {

        LinkedHashMap<String, Object> jsonLdObject = (LinkedHashMap<String, Object>) JsonUtils.fromInputStream(CanonicalizationTest.class.getResourceAsStream("verifiable-credential.ldp.good.jsonld"));
        String canonicalizedDocument = TestUtil.read(CanonicalizationTest.class.getResourceAsStream("verifiable-credential.canonicalized.test"));

        assertEquals(CanonicalizationUtil.buildCanonicalizedDocument(jsonLdObject), canonicalizedDocument);
    }
}
