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

public class CredentialStatus extends JsonLDObject {

	public static final URI[] DEFAULT_JSONLD_CONTEXTS = { VerifiableCredentialContexts.JSONLD_CONTEXT_W3C_2018_CREDENTIALS_V1 };
	public static final String[] DEFAULT_JSONLD_TYPES = { };
	public static final String DEFAULT_JSONLD_PREDICATE = VerifiableCredentialKeywords.JSONLD_TERM_CREDENTIALSTATUS;
	public static final DocumentLoader DEFAULT_DOCUMENT_LOADER = VerifiableCredentialContexts.DOCUMENT_LOADER;

	@JsonCreator
	public CredentialStatus() {
		super();
	}

	protected CredentialStatus(Map<String, Object> jsonObject) {
		super(jsonObject);
	}

	/*
	 * Factory methods
	 */

	public static class Builder<B extends Builder<B>> extends JsonLDObject.Builder<B> {

		public Builder(CredentialStatus jsonLDObject) {
			super(jsonLDObject);
		}

		@Override
		public CredentialStatus build() {

			super.build();

			return (CredentialStatus) this.jsonLDObject;
		}
	}

	public static Builder<? extends Builder<?>> builder() {
		return new Builder(new CredentialStatus());
	}

	public static CredentialStatus fromJsonObject(Map<String, Object> jsonObject) {
		return new CredentialStatus(jsonObject);
	}

	public static CredentialStatus fromJson(Reader reader) {
		return new CredentialStatus(readJson(reader));
	}

	public static CredentialStatus fromJson(String json) {
		return new CredentialStatus(readJson(json));
	}

	/*
	 * Adding, getting, and removing the JSON-LD object
	 */

	public static CredentialStatus getFromJsonLDObject(JsonLDObject jsonLdObject) {
		return JsonLDObject.getFromJsonLDObject(CredentialStatus.class, jsonLdObject);
	}

	public static void removeFromJsonLdObject(JsonLDObject jsonLdObject) {
		JsonLDObject.removeFromJsonLdObject(CredentialStatus.class, jsonLdObject);
	}
}
