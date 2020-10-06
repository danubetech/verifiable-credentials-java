package com.danubetech.verifiablecredentials.jsonld;

import com.apicatalog.jsonld.api.JsonLdError;
import com.apicatalog.jsonld.document.JsonDocument;
import com.apicatalog.jsonld.http.media.MediaType;
import com.apicatalog.jsonld.loader.DocumentLoader;
import foundation.identity.jsonld.ConfigurableDocumentLoader;
import foundation.identity.jsonld.JsonLDObject;
import info.weboftrust.ldsignatures.jsonld.LDSecurityContexts;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class VerifiableCredentialContexts {

    public static DocumentLoader DOCUMENT_LOADER = new ConfigurableDocumentLoader(VerifiableCredentialContexts.CONTEXTS);

    public static Map<URI, JsonDocument> CONTEXTS = new HashMap<URI, JsonDocument>();

    static {

        try {

            CONTEXTS.putAll(LDSecurityContexts.CONTEXTS);

            CONTEXTS.put(URI.create("https://www.w3.org/2018/credentials/v1"),
                    JsonDocument.of(MediaType.JSON_LD, VerifiableCredentialContexts.class.getResourceAsStream("credentials-v1.jsonld")));
            CONTEXTS.put(URI.create("https://www.w3.org/2018/credentials/examples/v1"),
                    JsonDocument.of(MediaType.JSON_LD, VerifiableCredentialContexts.class.getResourceAsStream("credentials-examples-v1.jsonld")));

            for (Map.Entry<URI, JsonDocument> context : CONTEXTS.entrySet()) {
                context.getValue().setDocumentUrl(context.getKey());
            }
        } catch (JsonLdError ex) {

            throw new ExceptionInInitializerError(ex);
        }
    }
}
