package com.danubetech.verifiablecredentials.credentialstatus;

import com.apicatalog.jsonld.loader.DocumentLoader;
import com.danubetech.verifiablecredentials.jsonld.VerifiableCredentialContexts;
import com.danubetech.verifiablecredentials.jsonld.VerifiableCredentialKeywords;
import com.danubetech.verifiablecredentials.proof.BlockchainHashProof2020;
import com.fasterxml.jackson.annotation.JsonCreator;
import foundation.identity.jsonld.JsonLDObject;
import foundation.identity.jsonld.JsonLDUtils;
import info.weboftrust.ldsignatures.LdProof;

import java.io.Reader;
import java.net.URI;
import java.util.Map;

public class RevocationQuery2020Status extends CredentialStatus {

	public static final URI[] DEFAULT_JSONLD_CONTEXTS = { VerifiableCredentialContexts.JSONLD_CONTEXT_W3C_2018_CREDENTIALS_V1 };
	public static final String[] DEFAULT_JSONLD_TYPES = { VerifiableCredentialKeywords.JSONLD_TERM_REVOCATION_QUERY_2020_STATUS };
	public static final String DEFAULT_JSONLD_PREDICATE = VerifiableCredentialKeywords.JSONLD_TERM_CREDENTIALSTATUS;
	public static final DocumentLoader DEFAULT_DOCUMENT_LOADER = VerifiableCredentialContexts.DOCUMENT_LOADER;

	@JsonCreator
	public RevocationQuery2020Status() {
		super();
	}

	protected RevocationQuery2020Status(Map<String, Object> jsonObject) {
		super(jsonObject);
	}

	/*
	 * Factory methods
	 */

	public static class Builder<B extends Builder<B>> extends CredentialStatus.Builder<B> {

		private String credentialReference;
		private String revocationService;

		public Builder(RevocationQuery2020Status jsonLdObject) {
			super(jsonLdObject);
			this.defaultTypes(true);
		}

		@Override
		public RevocationQuery2020Status build() {

			super.build();

			// add JSON-LD properties
			if (this.credentialReference != null) JsonLDUtils.jsonLdAdd(this.jsonLdObject, VerifiableCredentialKeywords.JSONLD_TERM_CREDENTIALREFERENCE, this.credentialReference);
			if (this.revocationService != null) JsonLDUtils.jsonLdAdd(this.jsonLdObject, VerifiableCredentialKeywords.JSONLD_TERM_REVOCATIONSERVICE, this.revocationService);

			return (RevocationQuery2020Status) this.jsonLdObject;
		}

		public B credentialReference(String credentialReference) {
			this.credentialReference = credentialReference;
			return (B) this;
		}

		public B revocationService(String revocationService) {
			this.revocationService = revocationService;
			return (B) this;
		}
	}

	public static Builder<? extends Builder<?>> builder() {
		return new Builder(new RevocationQuery2020Status());
	}

	public static RevocationQuery2020Status fromJsonObject(Map<String, Object> jsonObject) {
		return new RevocationQuery2020Status(jsonObject);
	}

	public static RevocationQuery2020Status fromJson(Reader reader) {
		return new RevocationQuery2020Status(readJson(reader));
	}

	public static RevocationQuery2020Status fromJson(String json) {
		return new RevocationQuery2020Status(readJson(json));
	}

	/*
	 * Adding, getting, and removing the JSON-LD object
	 */

	public static RevocationQuery2020Status getFromJsonLDObject(JsonLDObject jsonLdObject) {
		return JsonLDObject.getFromJsonLDObject(RevocationQuery2020Status.class, jsonLdObject);
	}

	public static void removeFromJsonLdObject(JsonLDObject jsonLdObject) {
		JsonLDObject.removeFromJsonLdObject(RevocationQuery2020Status.class, jsonLdObject);
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
