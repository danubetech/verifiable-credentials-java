package com.danubetech.verifiablecredentials.credentialstatus;

import com.apicatalog.jsonld.loader.DocumentLoader;
import com.danubetech.verifiablecredentials.jsonld.VerifiableCredentialContexts;
import com.danubetech.verifiablecredentials.jsonld.VerifiableCredentialKeywords;
import com.fasterxml.jackson.annotation.JsonCreator;
import foundation.identity.jsonld.JsonLDObject;
import foundation.identity.jsonld.JsonLDUtils;

import java.io.Reader;
import java.net.URI;
import java.util.Map;

public class StatusList2021Status extends CredentialStatus {

    public static final URI[] DEFAULT_JSONLD_CONTEXTS = { VerifiableCredentialContexts.JSONLD_CONTEXT_W3C_2018_CREDENTIALS_V1 };
    public static final String[] DEFAULT_JSONLD_TYPES = { VerifiableCredentialKeywords.JSONLD_TERM_STATUS_LIST_2021_ENTRY };
    public static final String DEFAULT_JSONLD_PREDICATE = VerifiableCredentialKeywords.JSONLD_TERM_CREDENTIALSTATUS;
    public static final DocumentLoader DEFAULT_DOCUMENT_LOADER = VerifiableCredentialContexts.DOCUMENT_LOADER;

    @JsonCreator
    public StatusList2021Status() {
        super();
    }

    protected StatusList2021Status(Map<String, Object> jsonObject) {
        super(jsonObject);
    }

    /*
     * Factory methods
     */

    public static class Builder<B extends StatusList2021Status.Builder<B>> extends CredentialStatus.Builder<B> {

        private String statusListIndex;
        private URI statusListCredential;

        public Builder(StatusList2021Status jsonLdObject) {
            super(jsonLdObject);
            this.defaultTypes(true);
        }

        @Override
        public StatusList2021Status build() {

            super.build();

            // add JSON-LD properties
            if (this.statusListIndex != null) JsonLDUtils.jsonLdAdd(this.jsonLdObject, VerifiableCredentialKeywords.JSONLD_TERM_STATUSLISTINDEX, this.statusListIndex);
            if (this.statusListCredential != null) JsonLDUtils.jsonLdAdd(this.jsonLdObject, VerifiableCredentialKeywords.JSONLD_TERM_STATUSLISTCREDENTIAL, JsonLDUtils.uriToString(this.statusListCredential));

            return (StatusList2021Status) this.jsonLdObject;
        }

        public B statusListIndex(String statusListIndex) {
            this.statusListIndex = statusListIndex;
            return (B) this;
        }

        public B statusListCredential(URI statusListCredential) {
            this.statusListCredential = statusListCredential;
            return (B) this;
        }
    }

    public static StatusList2021Status.Builder<? extends StatusList2021Status.Builder<?>> builder() {
        return new StatusList2021Status.Builder(new StatusList2021Status());
    }

    public static StatusList2021Status fromJsonObject(Map<String, Object> jsonObject) {
        return new StatusList2021Status(jsonObject);
    }

    public static StatusList2021Status fromJsonLDObject(JsonLDObject jsonLDObject) { return fromJsonObject(jsonLDObject.getJsonObject()); }

    public static StatusList2021Status fromJson(Reader reader) {
        return new StatusList2021Status(readJson(reader));
    }

    public static StatusList2021Status fromJson(String json) {
        return new StatusList2021Status(readJson(json));
    }

    /*
     * Adding, getting, and removing the JSON-LD object
     */

    public static StatusList2021Status getFromJsonLDObject(JsonLDObject jsonLdObject) {
        return JsonLDObject.getFromJsonLDObject(StatusList2021Status.class, jsonLdObject);
    }

    public static void removeFromJsonLdObject(JsonLDObject jsonLdObject) {
        JsonLDObject.removeFromJsonLdObject(StatusList2021Status.class, jsonLdObject);
    }

    /*
     * Getters
     */

    public String getStatusListIndex() {
        return JsonLDUtils.jsonLdGetString(this.getJsonObject(), VerifiableCredentialKeywords.JSONLD_TERM_STATUSLISTINDEX);
    }

    public URI getStatusListCredential() {
        return JsonLDUtils.stringToUri(JsonLDUtils.jsonLdGetString(this.getJsonObject(), VerifiableCredentialKeywords.JSONLD_TERM_STATUSLISTCREDENTIAL));
    }
}