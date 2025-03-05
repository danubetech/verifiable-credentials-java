package com.danubetech.verifiablecredentials.util;

import com.apicatalog.jsonld.JsonLdError;
import com.apicatalog.jsonld.document.JsonDocument;
import com.apicatalog.jsonld.http.media.MediaType;
import com.apicatalog.jsonld.loader.DocumentLoader;
import com.danubetech.dataintegrity.jsonld.DataIntegrityContexts;
import com.danubetech.verifiablecredentials.jsonld.VerifiableCredentialContexts;
import foundation.identity.jsonld.ConfigurableDocumentLoader;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CitizenshipContexts {

    public static final Map<URI, JsonDocument> CITIZENSHIP_CONTEXTS;
    public static final DocumentLoader CITIZENSHIP_DOCUMENT_LOADER;

    static {

        CITIZENSHIP_CONTEXTS = new HashMap<>();

        CITIZENSHIP_CONTEXTS.putAll(DataIntegrityContexts.CONTEXTS);
        CITIZENSHIP_CONTEXTS.putAll(VerifiableCredentialContexts.CONTEXTS);

        try {
            CITIZENSHIP_CONTEXTS.put(URI.create("https://w3id.org/citizenship/v4rc1"),
                    JsonDocument.of(MediaType.JSON_LD, Objects.requireNonNull(TestUtil.class.getResourceAsStream("citizenship-v4rc1.jsonld"))));
        } catch (JsonLdError ex) {
            throw new RuntimeException(ex);
        }

        CITIZENSHIP_DOCUMENT_LOADER = new ConfigurableDocumentLoader(CITIZENSHIP_CONTEXTS);
    }
}
