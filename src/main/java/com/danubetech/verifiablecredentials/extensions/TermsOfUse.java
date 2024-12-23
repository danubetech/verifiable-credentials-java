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

public class TermsOfUse extends JsonLDObject {

    public static final URI[] DEFAULT_JSONLD_CONTEXTS = { VerifiableCredentialContexts.JSONLD_CONTEXT_W3C_CREDENTIALS_V2 };
    public static final String[] DEFAULT_JSONLD_TYPES = { };
    public static final String DEFAULT_JSONLD_PREDICATE = VerifiableCredentialKeywords.JSONLD_TERM_TERMSOFUSE;
    public static final DocumentLoader DEFAULT_DOCUMENT_LOADER = VerifiableCredentialContexts.DOCUMENT_LOADER;

    @JsonCreator
    public TermsOfUse() {
        super();
    }

    protected TermsOfUse(Map<String, Object> jsonObject) {
        super(jsonObject);
    }

    public static class Builder<B extends TermsOfUse.Builder<B>> extends JsonLDObject.Builder<B> {

        public Builder(TermsOfUse jsonLdObject) {
            super(jsonLdObject);
        }

        @Override
        public TermsOfUse build() {

            super.build();

            return (TermsOfUse) this.jsonLdObject;
        }
    }

    public static TermsOfUse.Builder<? extends TermsOfUse.Builder<?>> builder() {
        return new TermsOfUse.Builder<>(new TermsOfUse());
    }

    public static TermsOfUse fromJsonObject(Map<String, Object> jsonObject) {
        return new TermsOfUse(jsonObject);
    }

    public static TermsOfUse fromJsonLDObject(JsonLDObject jsonLDObject) {
        return fromJsonObject(jsonLDObject.getJsonObject());
    }

    public static TermsOfUse fromJson(Reader reader) {
        return new TermsOfUse(readJson(reader));
    }

    public static TermsOfUse fromJson(String json) {
        return new TermsOfUse(readJson(json));
    }

    public static TermsOfUse fromMap(Map<String, Object> jsonObject) {
        return new TermsOfUse(jsonObject);
    }

    /*
     * Adding, getting, and removing the JSON-LD object
     */

    public static TermsOfUse getFromJsonLDObject(JsonLDObject jsonLdObject) {
        return JsonLDObject.getFromJsonLDObject(TermsOfUse.class, jsonLdObject);
    }

    public static List<TermsOfUse> getFromJsonLDObjectAsList(JsonLDObject jsonLdObject) {
        return JsonLDObject.getFromJsonLDObjectAsList(TermsOfUse.class, jsonLdObject);
    }

    public static void removeFromJsonLdObject(JsonLDObject jsonLdObject) {
        JsonLDObject.removeFromJsonLdObject(TermsOfUse.class, jsonLdObject);
    }
}

