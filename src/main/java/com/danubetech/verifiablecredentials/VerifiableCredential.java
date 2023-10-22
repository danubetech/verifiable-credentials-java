package com.danubetech.verifiablecredentials;

import com.apicatalog.jsonld.loader.DocumentLoader;
import com.danubetech.verifiablecredentials.credentialstatus.CredentialStatus;
import com.danubetech.verifiablecredentials.jsonld.VerifiableCredentialContexts;
import com.danubetech.verifiablecredentials.jsonld.VerifiableCredentialKeywords;
import com.fasterxml.jackson.annotation.JsonCreator;
import foundation.identity.jsonld.JsonLDObject;
import foundation.identity.jsonld.JsonLDUtils;
import info.weboftrust.ldsignatures.LdProof;

import java.io.Reader;
import java.net.URI;
import java.util.Date;
import java.util.Map;

public class VerifiableCredential extends JsonLDObject {

	public static final URI[] DEFAULT_JSONLD_CONTEXTS = { VerifiableCredentialContexts.JSONLD_CONTEXT_W3C_2018_CREDENTIALS_V1 };
	public static final String[] DEFAULT_JSONLD_TYPES = { VerifiableCredentialKeywords.JSONLD_TERM_VERIFIABLE_CREDENTIAL };
	public static final String DEFAULT_JSONLD_PREDICATE = VerifiableCredentialKeywords.JSONLD_TERM_VERIFIABLECREDENTIAL;
	public static final DocumentLoader DEFAULT_DOCUMENT_LOADER = VerifiableCredentialContexts.DOCUMENT_LOADER;

	@JsonCreator
	public VerifiableCredential() {
		super();
	}

	protected VerifiableCredential(Map<String, Object> jsonObject) {
		super(jsonObject);
	}

	/*
	 * Factory methods
	 */

	public static class Builder<B extends Builder<B>> extends JsonLDObject.Builder<B> {

		private URI issuer;
		private Date issuanceDate;
		private Date expirationDate;
		private CredentialSubject credentialSubject;
		private CredentialStatus credentialStatus;
		private LdProof ldProof;

		public Builder(VerifiableCredential jsonLdObject) {
			super(jsonLdObject);
			this.forceContextsArray(true);
			this.forceTypesArray(true);
			this.defaultContexts(true);
			this.defaultTypes(true);
		}

		@Override
		public VerifiableCredential build() {

			super.build();

			// add JSON-LD properties
			if (this.issuer != null) JsonLDUtils.jsonLdAdd(this.jsonLdObject, VerifiableCredentialKeywords.JSONLD_TERM_ISSUER, JsonLDUtils.uriToString(this.issuer));
			if (this.issuanceDate != null) JsonLDUtils.jsonLdAdd(this.jsonLdObject, VerifiableCredentialKeywords.JSONLD_TERM_ISSUANCEDATE, JsonLDUtils.dateToString(this.issuanceDate));
			if (this.expirationDate != null) JsonLDUtils.jsonLdAdd(this.jsonLdObject, VerifiableCredentialKeywords.JSONLD_TERM_EXPIRATIONDATE, JsonLDUtils.dateToString(this.expirationDate));
			if (this.credentialSubject != null) this.credentialSubject.addToJsonLDObject(this.jsonLdObject);
			if (this.credentialStatus != null) this.credentialStatus.addToJsonLDObject(this.jsonLdObject);
			if (this.ldProof != null) this.ldProof.addToJsonLDObject(this.jsonLdObject);

			return (VerifiableCredential) this.jsonLdObject;
		}

		public B issuer(URI issuer) {
			this.issuer = issuer;
			return (B) this;
		}

		public B issuanceDate(Date issuanceDate) {
			this.issuanceDate = issuanceDate;
			return (B) this;
		}

		public B expirationDate(Date expirationDate) {
			this.expirationDate = expirationDate;
			return (B) this;
		}

		public B credentialSubject(CredentialSubject credentialSubject) {
			this.credentialSubject = credentialSubject;
			return (B) this;
		}

		public B credentialStatus(CredentialStatus credentialStatus) {
			this.credentialStatus = credentialStatus;
			return (B) this;
		}

		public B ldProof(LdProof ldProof) {
			this.ldProof = ldProof;
			return (B) this;
		}
	}

	public static Builder<? extends Builder<?>> builder() {
		return new Builder<>(new VerifiableCredential());
	}

	public static VerifiableCredential fromJsonObject(Map<String, Object> jsonObject) {
		return new VerifiableCredential(jsonObject);
	}

	public static VerifiableCredential fromJsonLDObject(JsonLDObject jsonLDObject) { return fromJsonObject(jsonLDObject.getJsonObject()); }

	public static VerifiableCredential fromJson(Reader reader) {
		return new VerifiableCredential(readJson(reader));
	}

	public static VerifiableCredential fromJson(String json) {
		return new VerifiableCredential(readJson(json));
	}

	public static VerifiableCredential fromMap(Map<String, Object> map) {
		return new VerifiableCredential(map);
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

	public URI getIssuer() {
		return JsonLDUtils.stringToUri(JsonLDUtils.jsonLdGetStringOrObjectId(this.getJsonObject(), VerifiableCredentialKeywords.JSONLD_TERM_ISSUER));
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

	public CredentialStatus getCredentialStatus() {
		return CredentialStatus.getFromJsonLDObject(this);
	}
}
