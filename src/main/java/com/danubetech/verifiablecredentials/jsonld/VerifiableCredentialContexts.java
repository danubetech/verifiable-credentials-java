package com.danubetech.verifiablecredentials.jsonld;

import com.apicatalog.jsonld.api.JsonLdError;
import com.apicatalog.jsonld.document.JsonDocument;
import com.apicatalog.jsonld.http.media.MediaType;
import com.apicatalog.jsonld.loader.DocumentLoader;
import foundation.identity.jsonld.ConfigurableDocumentLoader;
import info.weboftrust.ldsignatures.jsonld.LDSecurityContexts;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class VerifiableCredentialContexts {

    public static final URI JSONLD_CONTEXT_W3C_2018_CREDENTIALS_V1 = URI.create("https://www.w3.org/2018/credentials/v1");
    public static final URI JSONLD_CONTEXT_W3C_2018_CREDENTIALS_EXAMPLES_V1 = URI.create("https://www.w3.org/2018/credentials/examples/v1");
    public static final URI JSONLD_CONTEXT_DANUBETECH_2020_CREDENTIALS_V1 = URI.create("https://danubetech.com/2020/credentials/v1");

    public static final Map<URI, JsonDocument> CONTEXTS;
    public static final DocumentLoader DOCUMENT_LOADER;

    static {

        try {

            CONTEXTS = new HashMap<>();

            CONTEXTS.putAll(LDSecurityContexts.CONTEXTS);

            CONTEXTS.put(JSONLD_CONTEXT_W3C_2018_CREDENTIALS_V1,
                    JsonDocument.of(MediaType.JSON_LD, VerifiableCredentialContexts.class.getResourceAsStream("credentials-v1.jsonld")));
            CONTEXTS.put(JSONLD_CONTEXT_W3C_2018_CREDENTIALS_EXAMPLES_V1,
                    JsonDocument.of(MediaType.JSON_LD, VerifiableCredentialContexts.class.getResourceAsStream("credentials-examples-v1.jsonld")));
            CONTEXTS.put(JSONLD_CONTEXT_DANUBETECH_2020_CREDENTIALS_V1,
                    JsonDocument.of(MediaType.JSON_LD, VerifiableCredentialContexts.class.getResourceAsStream("danubetech-v1.jsonld")));

            for (Map.Entry<URI, JsonDocument> context : CONTEXTS.entrySet()) {
                context.getValue().setDocumentUrl(context.getKey());
            }
        } catch (JsonLdError ex) {

            throw new ExceptionInInitializerError(ex);
        }

        DOCUMENT_LOADER = new ConfigurableDocumentLoader(CONTEXTS);
    }
}
