package com.danubetech.verifiablecredentials.credentialstatus;

import com.apicatalog.jsonld.loader.DocumentLoader;
import com.danubetech.verifiablecredentials.jsonld.VerifiableCredentialContexts;
import com.danubetech.verifiablecredentials.jsonld.VerifiableCredentialKeywords;
import com.danubetech.verifiablecredentials.proof.BlockchainHashProof2020;
import com.fasterxml.jackson.annotation.JsonCreator;
import foundation.identity.jsonld.JsonLDObject;
import foundation.identity.jsonld.JsonLDUtils;

import java.io.Reader;
import java.net.URI;
import java.util.Map;

public class StatusList2021Entry extends CredentialStatus {

    public static final URI[] DEFAULT_JSONLD_CONTEXTS = { VerifiableCredentialContexts.JSONLD_CONTEXT_W3C_2018_CREDENTIALS_V1 };
    public static final String[] DEFAULT_JSONLD_TYPES = { VerifiableCredentialKeywords.JSONLD_TERM_STATUS_LIST_2021_ENTRY };
    public static final String DEFAULT_JSONLD_PREDICATE = VerifiableCredentialKeywords.JSONLD_TERM_CREDENTIALSTATUS;
    public static final DocumentLoader DEFAULT_DOCUMENT_LOADER = VerifiableCredentialContexts.DOCUMENT_LOADER;

    @JsonCreator
    public StatusList2021Entry() {
        super();
    }

    protected StatusList2021Entry(Map<String, Object> jsonObject) {
        super(jsonObject);
    }

    /*
     * Factory methods
     */

    public static class Builder<B extends StatusList2021Entry.Builder<B>> extends CredentialStatus.Builder<B> {

        private String statusListIndex;
        private URI statusListCredential;

        private String statusPurpose;

        public Builder(StatusList2021Entry jsonLdObject) {
            super(jsonLdObject);
            this.defaultTypes(true);
        }

        @Override
        public StatusList2021Entry build() {

            super.build();

            // add JSON-LD properties
            if (this.statusListIndex != null) JsonLDUtils.jsonLdAdd(this.jsonLdObject, VerifiableCredentialKeywords.JSONLD_TERM_STATUSLISTINDEX, this.statusListIndex);
            if (this.statusListCredential != null) JsonLDUtils.jsonLdAdd(this.jsonLdObject, VerifiableCredentialKeywords.JSONLD_TERM_STATUSLISTCREDENTIAL, JsonLDUtils.uriToString(this.statusListCredential));
            if (this.statusPurpose != null) JsonLDUtils.jsonLdAdd(this.jsonLdObject, VerifiableCredentialKeywords.JSONLD_TERM_STATUSPURPOSE, this.statusPurpose);

            return (StatusList2021Entry) this.jsonLdObject;
        }

        public B statusListIndex(String statusListIndex) {
            this.statusListIndex = statusListIndex;
            return (B) this;
        }

        public B statusListCredential(URI statusListCredential) {
            this.statusListCredential = statusListCredential;
            return (B) this;
        }

        public B statusPurpose(String statusPurpose) {
            this.statusPurpose = statusPurpose;
            return (B) this;
        }
    }

    public static StatusList2021Entry.Builder<? extends StatusList2021Entry.Builder<?>> builder() {
        return new StatusList2021Entry.Builder<? extends BlockchainHashProof2020.Builder<?>>(new StatusList2021Entry());
    }

    public static StatusList2021Entry fromJsonObject(Map<String, Object> jsonObject) {
        return new StatusList2021Entry(jsonObject);
    }

    public static StatusList2021Entry fromJsonLDObject(JsonLDObject jsonLDObject) { return fromJsonObject(jsonLDObject.getJsonObject()); }

    public static StatusList2021Entry fromJson(Reader reader) {
        return new StatusList2021Entry(readJson(reader));
    }

    public static StatusList2021Entry fromJson(String json) {
        return new StatusList2021Entry(readJson(json));
    }

    /*
     * Adding, getting, and removing the JSON-LD object
     */

    public static StatusList2021Entry getFromJsonLDObject(JsonLDObject jsonLdObject) {
        return JsonLDObject.getFromJsonLDObject(StatusList2021Entry.class, jsonLdObject);
    }

    public static void removeFromJsonLdObject(JsonLDObject jsonLdObject) {
        JsonLDObject.removeFromJsonLdObject(StatusList2021Entry.class, jsonLdObject);
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

    public String getStatusPurpose(){
        return JsonLDUtils.jsonLdGetString(this.getJsonObject(), VerifiableCredentialKeywords.JSONLD_TERM_STATUSPURPOSE);
    }
}