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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
		private List<CredentialSubject> credentialSubjects;
		private CredentialStatus credentialStatus;
		private LdProof ldProof;

		public Builder(VerifiableCredential jsonLdObject) {
			super(jsonLdObject);
			this.credentialSubjects = new ArrayList<>();
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
			if (this.credentialSubjects != null) {
				if (this.credentialSubjects.size() == 1) {
					this.credentialSubjects.get(0).addToJsonLDObject(this.jsonLdObject);
				} else {
					JsonLDUtils.jsonLdAddAsJsonArray(this.jsonLdObject, VerifiableCredentialKeywords.JSONLD_TERM_CREDENTIALSUBJECT, this.credentialSubjects);
				}
			}
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
			this.credentialSubjects.add(credentialSubject);
			return (B) this;
		}

		public B credentialSubjects(List<CredentialSubject> credentialSubjects) {
			this.credentialSubjects = credentialSubjects;
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
		return new Builder(new VerifiableCredential());
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
		return getCredentialSubjects().get(0);
	}

	/**
	 * Get a list of all subjects contained in the receiver.
	 *
	 * @return A list of each subject in the "credentialSubject" property as `CredentialSubject` objects.
	 */
	public List<CredentialSubject> getCredentialSubjects() {
        /*
        The "credentialSubject" node may contain an array of objects as permissible in the VC spec:
        https://www.w3.org/TR/vc-data-model/#credential-subject
         */
		return new ArrayList<CredentialSubject>();
		
	}

	public LdProof getLdProof() {
		return LdProof.getFromJsonLDObject(this);
	}
}
