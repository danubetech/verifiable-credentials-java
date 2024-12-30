package com.danubetech.verifiablecredentials.extensions;

import com.apicatalog.jsonld.loader.DocumentLoader;
import com.danubetech.verifiablecredentials.jsonld.VerifiableCredentialContexts;
import com.danubetech.verifiablecredentials.jsonld.VerifiableCredentialKeywords;
import com.fasterxml.jackson.annotation.JsonCreator;
import foundation.identity.jsonld.JsonLDObject;

import java.io.Reader;
import java.net.URI;
import java.util.List;
import java.util.Map;

public class Evidence extends JsonLDObject {

    public static final URI[] DEFAULT_JSONLD_CONTEXTS = { VerifiableCredentialContexts.JSONLD_CONTEXT_W3C_CREDENTIALS_V2 };
    public static final String[] DEFAULT_JSONLD_TYPES = { };
    public static final String DEFAULT_JSONLD_PREDICATE = VerifiableCredentialKeywords.JSONLD_TERM_EVIDENCE;
    public static final DocumentLoader DEFAULT_DOCUMENT_LOADER = VerifiableCredentialContexts.DOCUMENT_LOADER;

    @JsonCreator
    public Evidence() {
        super();
    }

    protected Evidence(Map<String, Object> jsonObject) {
        super(jsonObject);
    }

    public static class Builder<B extends Evidence.Builder<B>> extends JsonLDObject.Builder<B> {

        public Builder(Evidence jsonLdObject) {
            super(jsonLdObject);
        }

        @Override
        public Evidence build() {

            super.build();

            return (Evidence) this.jsonLdObject;
        }
    }

    public static Evidence.Builder<? extends Evidence.Builder<?>> builder() {
        return new Evidence.Builder<>(new Evidence());
    }

    public static Evidence fromJsonObject(Map<String, Object> jsonObject) {
        return new Evidence(jsonObject);
    }

    public static Evidence fromJsonLDObject(JsonLDObject jsonLDObject) { return fromJsonObject(jsonLDObject.getJsonObject()); }

    public static Evidence fromJson(Reader reader) {
        return new Evidence(readJson(reader));
    }

    public static Evidence fromJson(String json) {
        return new Evidence(readJson(json));
    }

    public static Evidence fromMap(Map<String, Object> jsonObject) {
        return new Evidence(jsonObject);
    }

    /*
     * Adding, getting, and removing the JSON-LD object
     */

    public static Evidence getFromJsonLDObject(JsonLDObject jsonLdObject) {
        return JsonLDObject.getFromJsonLDObject(Evidence.class, jsonLdObject);
    }

    public static List<Evidence> getFromJsonLDObjectAsList(JsonLDObject jsonLdObject) {
        return JsonLDObject.getFromJsonLDObjectAsList(Evidence.class, jsonLdObject);
    }

    public static void removeFromJsonLdObject(JsonLDObject jsonLdObject) {
        JsonLDObject.removeFromJsonLdObject(Evidence.class, jsonLdObject);
    }
}
