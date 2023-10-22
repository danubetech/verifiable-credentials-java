package com.danubetech.verifiablecredentials;


import com.apicatalog.jsonld.lang.Keywords;
import com.apicatalog.jsonld.loader.DocumentLoader;
import com.danubetech.verifiablecredentials.jsonld.VerifiableCredentialContexts;
import com.danubetech.verifiablecredentials.jsonld.VerifiableCredentialKeywords;
import com.danubetech.verifiablecredentials.proof.BlockchainHashProof2020;
import com.fasterxml.jackson.annotation.JsonCreator;
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
	public static final DocumentLoader DEFAULT_DOCUMENT_LOADER = VerifiableCredentialContexts.DOCUMENT_LOADER;

	@JsonCreator
	public CredentialSubject() {
		super();
	}

	protected CredentialSubject(Map<String, Object> jsonObject) {
		super(jsonObject);
	}

	/*
	 * Factory methods
	 */

	public static class Builder<B extends Builder<B>> extends JsonLDObject.Builder<B> {

		private Map<String, Object> claims;

		public Builder(CredentialSubject jsonLdObject) {
			super(jsonLdObject);
		}

		@Override
		public CredentialSubject build() {

			super.build();

			// add JSON-LD properties
			if (this.claims != null) JsonLDUtils.jsonLdAddAll(this.jsonLdObject, this.claims);

			return (CredentialSubject) this.jsonLdObject;
		}

		public B claims(Map<String, Object> claims) {
			this.claims = claims;
			return (B) this;
		}
	}

	public static Builder<? extends Builder<?>> builder() {
		return new Builder<? extends BlockchainHashProof2020.Builder<?>>(new CredentialSubject());
	}

	public static CredentialSubject fromJsonObject(Map<String, Object> jsonObject) {
		return new CredentialSubject(jsonObject);
	}

	public static CredentialSubject fromJsonLDObject(JsonLDObject jsonLDObject) { return fromJsonObject(jsonLDObject.getJsonObject()); }

	public static CredentialSubject fromJson(Reader reader) {
		return new CredentialSubject(readJson(reader));
	}

	public static CredentialSubject fromJson(String json) {
		return new CredentialSubject(readJson(json));
	}

	public static CredentialSubject fromMap(Map<String, Object> jsonObject) {
		return new CredentialSubject(jsonObject);
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
		for (String key : this.getJsonObject().keySet()) if (Keywords.contains(key)) claims.remove(key);
		claims.remove(JsonLDKeywords.JSONLD_TERM_ID);
		claims.remove(JsonLDKeywords.JSONLD_TERM_TYPE);
		return claims;
	}
}