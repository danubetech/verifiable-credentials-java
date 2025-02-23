package com.danubetech.verifiablecredentials.jsonld;

import com.apicatalog.jsonld.JsonLdError;
import com.apicatalog.jsonld.document.JsonDocument;
import com.apicatalog.jsonld.http.media.MediaType;
import com.apicatalog.jsonld.loader.DocumentLoader;
import com.danubetech.dataintegrity.jsonld.LDSecurityContexts;
import foundation.identity.jsonld.ConfigurableDocumentLoader;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class VerifiableCredentialContexts {

    public static final URI JSONLD_CONTEXT_W3C_2018_CREDENTIALS_V1 = URI.create("https://www.w3.org/2018/credentials/v1");
    public static final URI JSONLD_CONTEXT_W3C_CREDENTIALS_V2 = URI.create("https://www.w3.org/ns/credentials/v2");
    public static final URI JSONLD_CONTEXT_W3C_2018_CREDENTIALS_EXAMPLES_V1 = URI.create("https://www.w3.org/2018/credentials/examples/v1");
    public static final URI JSONLD_CONTEXT_W3C_NS_ODRL = URI.create("https://www.w3.org/ns/odrl.jsonld");
    public static final URI JSONLD_CONTEXT_DANUBETECH_2020_CREDENTIALS_V1 = URI.create("https://danubetech.com/2020/credentials/v1");
    public static final URI JSONLD_CONTEXT_W3ID_VC_REVOCATION_LIST_2020_V1 = URI.create("https://w3id.org/vc-revocation-list-2020/v1");
    public static final URI JSONLD_CONTEXT_W3ID_VC_STATUS_LIST_2021_V1 = URI.create("https://w3id.org/vc/status-list/2021/v1");
    public static final URI JSONLD_CONTEXT_DIF_PRESENTATION_EXCHANGE_SUBMISSIONS_V1 = URI.create("https://identity.foundation/presentation-exchange/submission/v1");

    public static final Map<URI, JsonDocument> CONTEXTS;
    public static final DocumentLoader DOCUMENT_LOADER;

    static {

        try {

            CONTEXTS = new HashMap<>();

            CONTEXTS.putAll(LDSecurityContexts.CONTEXTS);

            CONTEXTS.put(JSONLD_CONTEXT_W3C_2018_CREDENTIALS_V1,
                    JsonDocument.of(MediaType.JSON_LD, Objects.requireNonNull(VerifiableCredentialContexts.class.getResourceAsStream("credentials-v1.jsonld"))));
            CONTEXTS.put(JSONLD_CONTEXT_W3C_CREDENTIALS_V2,
                    JsonDocument.of(MediaType.JSON_LD, Objects.requireNonNull(VerifiableCredentialContexts.class.getResourceAsStream("credentials-v2.jsonld"))));
            CONTEXTS.put(JSONLD_CONTEXT_W3C_2018_CREDENTIALS_EXAMPLES_V1,
                    JsonDocument.of(MediaType.JSON_LD, Objects.requireNonNull(VerifiableCredentialContexts.class.getResourceAsStream("credentials-examples-v1.jsonld"))));
            CONTEXTS.put(JSONLD_CONTEXT_W3C_NS_ODRL,
                    JsonDocument.of(MediaType.JSON_LD, Objects.requireNonNull(VerifiableCredentialContexts.class.getResourceAsStream("odrl.jsonld"))));
            CONTEXTS.put(JSONLD_CONTEXT_DANUBETECH_2020_CREDENTIALS_V1,
                    JsonDocument.of(MediaType.JSON_LD, Objects.requireNonNull(VerifiableCredentialContexts.class.getResourceAsStream("danubetech-v1.jsonld"))));
            CONTEXTS.put(JSONLD_CONTEXT_W3ID_VC_REVOCATION_LIST_2020_V1,
                    JsonDocument.of(MediaType.JSON_LD, Objects.requireNonNull(VerifiableCredentialContexts.class.getResourceAsStream("vc-revocation-list-2020-v1.jsonld"))));
            CONTEXTS.put(JSONLD_CONTEXT_W3ID_VC_STATUS_LIST_2021_V1,
                    JsonDocument.of(MediaType.JSON_LD, Objects.requireNonNull(VerifiableCredentialContexts.class.getResourceAsStream("vc-status-list-2021-v1.jsonld"))));
            CONTEXTS.put(JSONLD_CONTEXT_DIF_PRESENTATION_EXCHANGE_SUBMISSIONS_V1,
                    JsonDocument.of(MediaType.JSON_LD, Objects.requireNonNull(VerifiableCredentialContexts.class.getResourceAsStream("presentation-exchange-submissions-v1.jsonld"))));

            for (Map.Entry<URI, JsonDocument> context : CONTEXTS.entrySet()) {
                context.getValue().setDocumentUrl(context.getKey());
            }
        } catch (JsonLdError ex) {

            throw new ExceptionInInitializerError(ex);
        }

        DOCUMENT_LOADER = new ConfigurableDocumentLoader(CONTEXTS);
    }
}
