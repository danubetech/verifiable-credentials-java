package com.danubetech.verifiablecredentials.credentialstatus;

import com.danubetech.verifiablecredentials.CredentialSubject;
import com.danubetech.verifiablecredentials.jsonld.VerifiableCredentialContexts;
import com.danubetech.verifiablecredentials.jsonld.VerifiableCredentialKeywords;
import foundation.identity.jsonld.JsonLDObject;
import foundation.identity.jsonld.JsonLDUtils;

import javax.json.JsonObject;
import java.io.Reader;
import java.net.URI;

public class RevocationQuery2020Status extends JsonLDObject {

	public static final URI[] DEFAULT_JSONLD_CONTEXTS = { VerifiableCredentialContexts.JSONLD_CONTEXT_W3C_2018_CREDENTIALS_V1 };
	public static final String[] DEFAULT_JSONLD_TYPES = { VerifiableCredentialKeywords.JSONLD_TERM_REVOCATION_QUERY_2020_STATUS };
	public static final String DEFAULT_JSONLD_PREDICATE = VerifiableCredentialKeywords.JSONLD_TERM_CREDENTIALSUBJECT;

	private RevocationQuery2020Status() {
		super(VerifiableCredentialContexts.DOCUMENT_LOADER);
	}

	public RevocationQuery2020Status(JsonObject jsonObject) {
		super(VerifiableCredentialContexts.DOCUMENT_LOADER, jsonObject);
	}

	/*
	 * Factory methods
	 */

	public static class Builder extends JsonLDObject.Builder<Builder, RevocationQuery2020Status> {

		private String credentialReference;
		private String revocationService;

		public Builder(RevocationQuery2020Status jsonLDObject) {
			super(jsonLDObject);
		}

		@Override
		public RevocationQuery2020Status build() {

			super.build();

			// add JSON-LD properties
			if (this.credentialReference != null) JsonLDUtils.jsonLdAddString(this.jsonLDObject.getJsonObjectBuilder(), VerifiableCredentialKeywords.JSONLD_TERM_CREDENTIALREFERENCE, this.credentialReference);
			if (this.revocationService != null) JsonLDUtils.jsonLdAddString(this.jsonLDObject.getJsonObjectBuilder(), VerifiableCredentialKeywords.JSONLD_TERM_REVOCATIONSERVICE, this.revocationService);

			return this.jsonLDObject;
		}

		public Builder credentialReference(String credentialReference) {
			this.credentialReference = credentialReference;
			return this;
		}

		public Builder revocationService(String revocationService) {
			this.revocationService = revocationService;
			return this;
		}
	}

	public static Builder builder() {
		return new Builder(new RevocationQuery2020Status());
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

	public String getCredentialReference() {
		return JsonLDUtils.jsonLdGetString(this.getJsonObject(), VerifiableCredentialKeywords.JSONLD_TERM_CREDENTIALREFERENCE);
	}

	public String getRevocationService() {
		return JsonLDUtils.jsonLdGetString(this.getJsonObject(), VerifiableCredentialKeywords.JSONLD_TERM_REVOCATIONSERVICE);
	}
}
