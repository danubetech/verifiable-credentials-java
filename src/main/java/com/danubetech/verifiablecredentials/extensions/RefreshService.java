package com.danubetech.verifiablecredentials.extensions;

import com.fasterxml.jackson.annotation.JsonCreator;
import foundation.identity.jsonld.JsonLDObject;

import java.io.Reader;
import java.util.Map;

public class RefreshService extends JsonLDObject {

    @JsonCreator
    public RefreshService() {
        super();
    }

    protected RefreshService(Map<String, Object> jsonObject) {
        super(jsonObject);
    }

    public static class Builder<B extends RefreshService.Builder<B>> extends JsonLDObject.Builder<B> {

        public Builder(RefreshService jsonLdObject) {
            super(jsonLdObject);
        }

        @Override
        public RefreshService build() {

            super.build();

            return (RefreshService) this.jsonLdObject;
        }
    }

    public static RefreshService.Builder<? extends RefreshService.Builder<?>> builder() {
        return new RefreshService.Builder<>(new RefreshService());
    }

    public static RefreshService fromJsonObject(Map<String, Object> jsonObject) {
        return new RefreshService(jsonObject);
    }

    public static RefreshService fromJsonLDObject(JsonLDObject jsonLDObject) {
        return fromJsonObject(jsonLDObject.getJsonObject());
    }

    public static RefreshService fromJson(Reader reader) {
        return new RefreshService(readJson(reader));
    }

    public static RefreshService fromJson(String json) {
        return new RefreshService(readJson(json));
    }

    public static RefreshService fromMap(Map<String, Object> jsonObject) {
        return new RefreshService(jsonObject);
    }

    /*
     * Adding, getting, and removing the JSON-LD object
     */

    public static RefreshService getFromJsonLDObject(JsonLDObject jsonLdObject) {
        return JsonLDObject.getFromJsonLDObject(RefreshService.class, jsonLdObject);
    }

    public static void removeFromJsonLdObject(JsonLDObject jsonLdObject) {
        JsonLDObject.removeFromJsonLdObject(RefreshService.class, jsonLdObject);
    }
}

