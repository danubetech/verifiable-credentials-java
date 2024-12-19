package com.danubetech.verifiablecredentials.extensions;



import com.fasterxml.jackson.annotation.JsonCreator;
import foundation.identity.jsonld.JsonLDObject;



import java.io.Reader;
import java.util.Map;

public class CredentialSchema extends JsonLDObject {

    @JsonCreator
    public CredentialSchema() {
        super();
    }

    protected CredentialSchema(Map<String, Object> jsonObject) {
        super(jsonObject);
    }

    public static class Builder<B extends CredentialSchema.Builder<B>> extends JsonLDObject.Builder<B> {

        public Builder(CredentialSchema jsonLdObject) {
            super(jsonLdObject);
        }

        @Override
        public CredentialSchema build() {

            super.build();

            return (CredentialSchema) this.jsonLdObject;
        }
    }

    public static CredentialSchema.Builder<? extends CredentialSchema.Builder<?>> builder() {
        return new CredentialSchema.Builder<>(new CredentialSchema());
    }

    public static CredentialSchema fromJsonObject(Map<String, Object> jsonObject) {
        return new CredentialSchema(jsonObject);
    }

    public static CredentialSchema fromJsonLDObject(JsonLDObject jsonLDObject) { return fromJsonObject(jsonLDObject.getJsonObject()); }

    public static CredentialSchema fromJson(Reader reader) {
        return new CredentialSchema(readJson(reader));
    }

    public static CredentialSchema fromJson(String json) {
        return new CredentialSchema(readJson(json));
    }

    public static CredentialSchema fromMap(Map<String, Object> jsonObject) {
        return new CredentialSchema(jsonObject);
    }

    /*
     * Adding, getting, and removing the JSON-LD object
     */

    public static CredentialSchema getFromJsonLDObject(JsonLDObject jsonLdObject) {
        return JsonLDObject.getFromJsonLDObject(CredentialSchema.class, jsonLdObject);
    }

    public static void removeFromJsonLdObject(JsonLDObject jsonLdObject) {
        JsonLDObject.removeFromJsonLdObject(CredentialSchema.class, jsonLdObject);
    }
}
