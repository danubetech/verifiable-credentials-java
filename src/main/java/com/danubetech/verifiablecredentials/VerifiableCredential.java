package com.danubetech.verifiablecredentials;

import java.io.Reader;
import java.net.URI;
import java.util.Date;

import com.danubetech.verifiablecredentials.jsonld.VerifiableCredentialContexts;
import com.danubetech.verifiablecredentials.jsonld.VerifiableCredentialKeywords;
import foundation.identity.jsonld.JsonLDObject;
import foundation.identity.jsonld.JsonLDUtils;
import info.weboftrust.ldsignatures.LdProof;

import javax.json.JsonObject;

public class VerifiableCredential extends JsonLDObject {

	public static final URI[] DEFAULT_JSONLD_CONTEXTS = { VerifiableCredentialContexts.JSONLD_CONTEXT_W3C_2018_CREDENTIALS_V1 };
	public static final String[] DEFAULT_JSONLD_TYPES = { VerifiableCredentialKeywords.JSONLD_TERM_VERIFIABLE_CREDENTIAL };
	public static final String DEFAULT_JSONLD_PREDICATE = VerifiableCredentialKeywords.JSONLD_TERM_VERIFIABLECREDENTIAL;

	private VerifiableCredential() {
		super(VerifiableCredentialContexts.DOCUMENT_LOADER);
	}

	public VerifiableCredential(JsonObject jsonObject) {
		super(VerifiableCredentialContexts.DOCUMENT_LOADER, jsonObject);
	}

	/*
	 * Factory methods
	 */

	public static class Builder extends JsonLDObject.Builder<Builder, VerifiableCredential> {

		private URI issuer;
		private Date issuanceDate;
		private Date expirationDate;
		private CredentialSubject credentialSubject;
		private LdProof ldProof;

		public Builder(VerifiableCredential jsonLDObject) {
			super(jsonLDObject);
		}

		@Override
		public VerifiableCredential build() {

			super.build();

			// add JSON-LD properties
			if (this.issuer != null) JsonLDUtils.jsonLdAddString(this.jsonLDObject.getJsonObjectBuilder(), VerifiableCredentialKeywords.JSONLD_TERM_ISSUER, JsonLDUtils.uriToString(this.issuer));
			if (this.issuanceDate != null) JsonLDUtils.jsonLdAddString(this.jsonLDObject.getJsonObjectBuilder(), VerifiableCredentialKeywords.JSONLD_TERM_ISSUANCEDATE, JsonLDUtils.dateToString(this.issuanceDate));
			if (this.expirationDate != null) JsonLDUtils.jsonLdAddString(this.jsonLDObject.getJsonObjectBuilder(), VerifiableCredentialKeywords.JSONLD_TERM_EXPIRATIONDATE, JsonLDUtils.dateToString(this.expirationDate));
			if (this.credentialSubject != null) this.credentialSubject.addToJsonLDObject(this.jsonLDObject);
			if (this.ldProof != null) this.ldProof.addToJsonLDObject(this.jsonLDObject);

			return this.jsonLDObject;
		}

		public Builder issuer(URI issuer) {
			this.issuer = issuer;
			return this;
		}

		public Builder issuanceDate(Date issuanceDate) {
			this.issuanceDate = issuanceDate;
			return this;
		}

		public Builder expirationDate(Date expirationDate) {
			this.expirationDate = expirationDate;
			return this;
		}

		public Builder credentialSubject(CredentialSubject credentialSubject) {
			this.credentialSubject = credentialSubject;
			return this;
		}

		public Builder ldProof(LdProof ldProof) {
			this.ldProof = ldProof;
			return this;
		}
	}

	public static Builder builder() {
		return new Builder(new VerifiableCredential())
				.defaultContexts(true)
				.defaultTypes(true);
	}

	/*
	 * Reading the JSON-LD object
	 */

	public static VerifiableCredential fromJson(Reader reader) {
		return JsonLDObject.fromJson(VerifiableCredential.class, reader);
	}

	public static VerifiableCredential fromJson(String json) {
		return JsonLDObject.fromJson(VerifiableCredential.class, json);
	}

	/*
	 * Adding, getting, and removing the JSON-LD object
	 */

	public static VerifiableCredential getFromJsonLDObject(JsonLDObject jsonLdObject) {
		return JsonLDObject.getFromJsonLDObject(VerifiableCredential.class, jsonLdObject);
	}

	public static void removeFromJsonLdObject(JsonLDObject jsonLdObject) {
		JsonLDObject.removeFromJsonLdObject(VerifiableCredential.class, jsonLdObject);
	}

	/*
	 * Getters
	 */

	@SuppressWarnings("unchecked")
	public URI getIssuer() {
		return JsonLDUtils.stringToUri(JsonLDUtils.jsonLdGetString(this.getJsonObject(), VerifiableCredentialKeywords.JSONLD_TERM_ISSUER));
	}

	public Date getIssuanceDate() {
		return JsonLDUtils.stringToDate(JsonLDUtils.jsonLdGetString(this.getJsonObject(), VerifiableCredentialKeywords.JSONLD_TERM_ISSUANCEDATE));
	}

	public Date getExpirationDate() {
		return JsonLDUtils.stringToDate(JsonLDUtils.jsonLdGetString(this.getJsonObject(), VerifiableCredentialKeywords.JSONLD_TERM_EXPIRATIONDATE));
	}

	public CredentialSubject getCredentialSubject() {
		return CredentialSubject.getFromJsonLDObject(this);
	}

	public LdProof getLdProof() {
		return LdProof.getFromJsonLDObject(this);
	}
}
