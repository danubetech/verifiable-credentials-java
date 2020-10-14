package com.danubetech.verifiablecredentials;


import com.danubetech.verifiablecredentials.jsonld.VerifiableCredentialContexts;
import com.danubetech.verifiablecredentials.jsonld.VerifiableCredentialKeywords;
import foundation.identity.jsonld.JsonLDKeywords;
import foundation.identity.jsonld.JsonLDObject;
import foundation.identity.jsonld.JsonLDUtils;

import java.io.Reader;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

public class CredentialSubject extends JsonLDObject {

	public static final URI[] DEFAULT_JSONLD_CONTEXTS = { VerifiableCredentialContexts.JSONLD_CONTEXT_W3C_2018_CREDENTIALS_V1 };
	public static final String[] DEFAULT_JSONLD_TYPES = { };
	public static final String DEFAULT_JSONLD_PREDICATE = VerifiableCredentialKeywords.JSONLD_TERM_CREDENTIALSUBJECT;

	private CredentialSubject() {
		super(VerifiableCredentialContexts.DOCUMENT_LOADER);
	}

	public CredentialSubject(Map<String, Object> jsonObject) {
		super(VerifiableCredentialContexts.DOCUMENT_LOADER, jsonObject);
	}

	/*
	 * Factory methods
	 */

	public static class Builder extends JsonLDObject.Builder<Builder, CredentialSubject> {

		private Map<String, Object> claims;

		public Builder(CredentialSubject jsonLDObject) {
			super(jsonLDObject);
		}

		@Override
		public CredentialSubject build() {

			super.build();

			// add JSON-LD properties
			if (this.claims != null) JsonLDUtils.jsonLdAddAll(this.jsonLDObject, this.claims);

			return this.jsonLDObject;
		}

		public Builder claims(Map<String, Object> claims) {
			this.claims = claims;
			return this;
		}
	}

	public static Builder builder() {
		return new Builder(new CredentialSubject());
	}

	/*
	 * Reading the JSON-LD object
	 */

	public static CredentialSubject fromJson(Reader reader) {
		return JsonLDObject.fromJson(CredentialSubject.class, reader);
	}

	public static CredentialSubject fromJson(String json) {
		return JsonLDObject.fromJson(CredentialSubject.class, json);
	}

	/*
	 * Adding, getting, and removing the JSON-LD object
	 */

	public static CredentialSubject getFromJsonLDObject(JsonLDObject jsonLdObject) {
		return JsonLDObject.getFromJsonLDObject(CredentialSubject.class, jsonLdObject);
	}

	public static void removeFromJsonLdObject(JsonLDObject jsonLdObject) {
		JsonLDObject.removeFromJsonLdObject(CredentialSubject.class, jsonLdObject);
	}

	/*
	 * Getters
	 */

	public Map<String, Object> getClaims() {
		Map<String, Object> claims = new LinkedHashMap<>(this.getJsonObject());
		claims.remove(JsonLDKeywords.JSONLD_TERM_ID);
		return claims;
	}
}