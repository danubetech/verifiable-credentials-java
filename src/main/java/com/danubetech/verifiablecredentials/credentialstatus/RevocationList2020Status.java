package com.danubetech.verifiablecredentials.credentialstatus;

import com.apicatalog.jsonld.loader.DocumentLoader;
import com.danubetech.verifiablecredentials.CredentialSubject;
import com.danubetech.verifiablecredentials.jsonld.VerifiableCredentialContexts;
import com.danubetech.verifiablecredentials.jsonld.VerifiableCredentialKeywords;
import com.fasterxml.jackson.annotation.JsonCreator;
import foundation.identity.jsonld.JsonLDObject;
import foundation.identity.jsonld.JsonLDUtils;

import java.io.Reader;
import java.net.URI;
import java.util.Map;

public class RevocationList2020Status extends CredentialStatus {

	public static final URI[] DEFAULT_JSONLD_CONTEXTS = { VerifiableCredentialContexts.JSONLD_CONTEXT_W3C_2018_CREDENTIALS_V1 };
	public static final String[] DEFAULT_JSONLD_TYPES = { VerifiableCredentialKeywords.JSONLD_TERM_REVOCATION_LIST_2020_STATUS };
	public static final String DEFAULT_JSONLD_PREDICATE = VerifiableCredentialKeywords.JSONLD_TERM_CREDENTIALSTATUS;
	public static final DocumentLoader DEFAULT_DOCUMENT_LOADER = VerifiableCredentialContexts.DOCUMENT_LOADER;

	@JsonCreator
	public RevocationList2020Status() {
		super();
	}

	protected RevocationList2020Status(Map<String, Object> jsonObject) {
		super(jsonObject);
	}

	/*
	 * Factory methods
	 */

	public static class Builder<B extends Builder<B>> extends CredentialStatus.Builder<B> {

		private String revocationListIndex;
		private URI revocationListCredential;

		public Builder(RevocationList2020Status jsonLDObject) {
			super(jsonLDObject);
		}

		@Override
		public RevocationList2020Status build() {

			super.build();

			// add JSON-LD properties
			if (this.revocationListIndex != null) JsonLDUtils.jsonLdAdd(this.jsonLDObject, VerifiableCredentialKeywords.JSONLD_TERM_REVOCATIONLISTINDEX, this.revocationListIndex);
			if (this.revocationListCredential != null) JsonLDUtils.jsonLdAdd(this.jsonLDObject, VerifiableCredentialKeywords.JSONLD_TERM_REVOCATIONLISTCREDENTIAL, JsonLDUtils.uriToString(this.revocationListCredential));

			return (RevocationList2020Status) this.jsonLDObject;
		}

		public B revocationListIndex(String revocationListIndex) {
			this.revocationListIndex = revocationListIndex;
			return (B) this;
		}

		public B revocationListCredential(URI revocationListCredential) {
			this.revocationListCredential = revocationListCredential;
			return (B) this;
		}
	}

	public static Builder<? extends Builder<?>> builder() {
		return new Builder(new RevocationList2020Status());
	}

	public static RevocationList2020Status fromJsonObject(Map<String, Object> jsonObject) {
		return new RevocationList2020Status(jsonObject);
	}

	public static RevocationList2020Status fromJson(Reader reader) {
		return new RevocationList2020Status(readJson(reader));
	}

	public static RevocationList2020Status fromJson(String json) {
		return new RevocationList2020Status(readJson(json));
	}

	/*
	 * Adding, getting, and removing the JSON-LD object
	 */

	public static RevocationList2020Status getFromJsonLDObject(JsonLDObject jsonLdObject) {
		return JsonLDObject.getFromJsonLDObject(RevocationList2020Status.class, jsonLdObject);
	}

	public static void removeFromJsonLdObject(JsonLDObject jsonLdObject) {
		JsonLDObject.removeFromJsonLdObject(RevocationList2020Status.class, jsonLdObject);
	}

	/*
	 * Getters
	 */

	public String getRevocationListIndex() {
		return JsonLDUtils.jsonLdGetString(this.getJsonObject(), VerifiableCredentialKeywords.JSONLD_TERM_REVOCATIONLISTINDEX);
	}

	public URI getRevocationListCredential() {
		return JsonLDUtils.stringToUri(JsonLDUtils.jsonLdGetString(this.getJsonObject(), VerifiableCredentialKeywords.JSONLD_TERM_REVOCATIONLISTCREDENTIAL));
	}
}
