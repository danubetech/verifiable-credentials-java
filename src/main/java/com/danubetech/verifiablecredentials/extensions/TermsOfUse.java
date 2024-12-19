package com.danubetech.verifiablecredentials.extensions;

import com.fasterxml.jackson.annotation.JsonCreator;
import foundation.identity.jsonld.JsonLDObject;

import java.io.Reader;
import java.util.Map;

public class TermsOfUse extends JsonLDObject {
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

    public static void removeFromJsonLdObject(JsonLDObject jsonLdObject) {
        JsonLDObject.removeFromJsonLdObject(TermsOfUse.class, jsonLdObject);
    }
}

