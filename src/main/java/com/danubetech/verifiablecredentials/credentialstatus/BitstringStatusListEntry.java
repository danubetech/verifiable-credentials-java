package com.danubetech.verifiablecredentials.credentialstatus;

import com.apicatalog.jsonld.loader.DocumentLoader;
import com.danubetech.verifiablecredentials.jsonld.VerifiableCredentialContexts;
import com.danubetech.verifiablecredentials.jsonld.VerifiableCredentialKeywords;
import com.fasterxml.jackson.annotation.JsonCreator;
import foundation.identity.jsonld.JsonLDObject;
import foundation.identity.jsonld.JsonLDUtils;

import java.io.Reader;
import java.net.URI;
import java.util.List;
import java.util.Map;

public class BitstringStatusListEntry extends CredentialStatus {

    public static final URI[] DEFAULT_JSONLD_CONTEXTS = { VerifiableCredentialContexts.JSONLD_CONTEXT_W3C_CREDENTIALS_V2 };
    public static final String[] DEFAULT_JSONLD_TYPES = { VerifiableCredentialKeywords.JSONLD_TERM_BITSTRING_STATUS_LIST_ENTRY };
    public static final String DEFAULT_JSONLD_PREDICATE = VerifiableCredentialKeywords.JSONLD_TERM_CREDENTIALSTATUS;
    public static final DocumentLoader DEFAULT_DOCUMENT_LOADER = VerifiableCredentialContexts.DOCUMENT_LOADER;

    @JsonCreator
    public BitstringStatusListEntry() {
        super();
    }

    protected BitstringStatusListEntry(Map<String, Object> jsonObject) {
        super(jsonObject);
    }

    /*
     * Factory methods
     */

    public static class Builder<B extends BitstringStatusListEntry.Builder<B>> extends CredentialStatus.Builder<B> {

        private String statusListIndex;
        private URI statusListCredential;
        private String statusPurpose;
        private Integer statusSize = 1;
        private List<StatusMessage> statusMessage;
        private URI statusReference;



        public Builder(BitstringStatusListEntry jsonLdObject) {
            super(jsonLdObject);
            this.defaultTypes(true);
        }

        @Override
        public BitstringStatusListEntry build() {

            super.build();

            // add JSON-LD properties
            if (this.statusListIndex != null) JsonLDUtils.jsonLdAdd(this.jsonLdObject, VerifiableCredentialKeywords.JSONLD_TERM_STATUSLISTINDEX, this.statusListIndex);
            if (this.statusListCredential != null) JsonLDUtils.jsonLdAdd(this.jsonLdObject, VerifiableCredentialKeywords.JSONLD_TERM_STATUSLISTCREDENTIAL, JsonLDUtils.uriToString(this.statusListCredential));
            if (this.statusPurpose != null) JsonLDUtils.jsonLdAdd(this.jsonLdObject, VerifiableCredentialKeywords.JSONLD_TERM_STATUSPURPOSE, this.statusPurpose);
            if (this.statusSize != null) JsonLDUtils.jsonLdAdd(this.jsonLdObject, VerifiableCredentialKeywords.JSONLD_TERM_STATUSSIZE, this.statusSize);
            if (this.statusMessage != null) JsonLDUtils.jsonLdAdd(this.jsonLdObject, VerifiableCredentialKeywords.JSONLD_TERM_STATUSMESSAGE, this.statusMessage);
            if (this.statusReference != null) JsonLDUtils.jsonLdAdd(this.jsonLdObject, VerifiableCredentialKeywords.JSONLD_TERM_STATUSREFERENCE, JsonLDUtils.uriToString(this.statusReference));

            return (BitstringStatusListEntry) this.jsonLdObject;
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

        public B statusSize(Integer statusSize) {
            this.statusSize = statusSize;
            return (B) this;
        }

        public B statusMessage(List<StatusMessage> statusMessage) {
            this.statusMessage = statusMessage;
            return (B) this;
        }
        public B statusReference(URI statusReference) {
            this.statusReference = statusReference;
            return (B) this;
        }
    }

    public static Builder<? extends Builder<?>> builder() {
        return new Builder<>(new BitstringStatusListEntry());
    }

    public static BitstringStatusListEntry fromJsonObject(Map<String, Object> jsonObject) {
        return new BitstringStatusListEntry(jsonObject);
    }

    public static BitstringStatusListEntry fromJsonLDObject(JsonLDObject jsonLDObject) { return fromJsonObject(jsonLDObject.getJsonObject()); }

    public static BitstringStatusListEntry fromJson(Reader reader) {
        return new BitstringStatusListEntry(readJson(reader));
    }

    public static BitstringStatusListEntry fromJson(String json) {
        return new BitstringStatusListEntry(readJson(json));
    }

    /*
     * Adding, getting, and removing the JSON-LD object
     */

    public static BitstringStatusListEntry getFromJsonLDObject(JsonLDObject jsonLdObject) {
        return JsonLDObject.getFromJsonLDObject(BitstringStatusListEntry.class, jsonLdObject);
    }

    public static void removeFromJsonLdObject(JsonLDObject jsonLdObject) {
        JsonLDObject.removeFromJsonLdObject(BitstringStatusListEntry.class, jsonLdObject);
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

    public String getStatusSize() {
        return JsonLDUtils.jsonLdGetString(this.getJsonObject(), VerifiableCredentialKeywords.JSONLD_TERM_STATUSSIZE);
    }
    public List<Object> getStatusMessage() {
        return JsonLDUtils.jsonLdGetJsonArray(this.getJsonObject(), VerifiableCredentialKeywords.JSONLD_TERM_STATUSMESSAGE);
    }

    public URI getStatusReference() {
        return JsonLDUtils.stringToUri(JsonLDUtils.jsonLdGetString(this.getJsonObject(), VerifiableCredentialKeywords.JSONLD_TERM_STATUSREFERENCE));
    }

    public static class StatusMessage{
        private String status;
        private String message;

        public StatusMessage(String status, String message) {
            this.status = status;
            this.message = message;
        }

        public StatusMessage() {
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}