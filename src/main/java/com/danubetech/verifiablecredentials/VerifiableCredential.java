package com.danubetech.verifiablecredentials;

import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.util.*;

import com.danubetech.verifiablecredentials.jsonld.VerifiableCredentialContexts;
import com.danubetech.verifiablecredentials.jsonld.VerifiableCredentialKeywords;
import com.danubetech.verifiablecredentials.validation.Validation;
import foundation.identity.jsonld.JsonLDObject;
import foundation.identity.jsonld.JsonLDUtils;
import info.weboftrust.ldsignatures.LdProof;
import info.weboftrust.ldsignatures.jsonld.LDSecurityKeywords;

import javax.json.Json;
import javax.json.JsonObject;

public class VerifiableCredential extends JsonLDObject {

	public static final String DEFAULT_JSONLD_CONTEXT = "https://www.w3.org/2018/credentials/v1";
	public static final String DEFAULT_JSONLD_TYPE = "VerifiableCredential";

	private VerifiableCredential() {
		super(VerifiableCredentialContexts.DOCUMENT_LOADER);
	}

	private VerifiableCredential(JsonObject jsonObject, boolean validate) {
		super(VerifiableCredentialContexts.DOCUMENT_LOADER, jsonObject);
		if (validate) Validation.validate(this);
	}

	public VerifiableCredential(JsonObject jsonObject) {
		this(jsonObject, true);
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

		public Builder() {
			super(new VerifiableCredential());
		}

		public VerifiableCredential build() {

			super.build();

			// add JSON-LD properties
			if (this.issuer  != null) JsonLDUtils.jsonLdAddString(this.jsonLDObject.getJsonObjectBuilder(), VerifiableCredentialKeywords.JSONLD_TERM_ISSUER, JsonLDUtils.uriToString(this.issuer));
			if (this.issuanceDate  != null) JsonLDUtils.jsonLdAddString(this.jsonLDObject.getJsonObjectBuilder(), VerifiableCredentialKeywords.JSONLD_TERM_ISSUANCE_DATE, JsonLDUtils.dateToString(this.issuanceDate));
			if (this.expirationDate != null) JsonLDUtils.jsonLdAddString(this.jsonLDObject.getJsonObjectBuilder(), VerifiableCredentialKeywords.JSONLD_TERM_ISSUANCE_DATE, JsonLDUtils.dateToString(this.expirationDate));
			if (this.credentialSubject != null) JsonLDUtils.jsonLdAddJsonValue(this.jsonLDObject.getJsonObjectBuilder(), VerifiableCredentialKeywords.JSONLD_TERM_CREDENTIAL_SUBJECT, this.credentialSubject.getJsonObject());
			if (this.ldProof != null) JsonLDUtils.jsonLdAddJsonValue(this.jsonLDObject.getJsonObjectBuilder(), LDSecurityKeywords.JSONLD_TERM_PROOF, this.ldProof.getJsonObject());

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

		return new Builder()
				.context(DEFAULT_JSONLD_CONTEXT)
				.type(DEFAULT_JSONLD_TYPE);
	}

	/*
	 * Serialization
	 */

	public static VerifiableCredential fromJson(Reader reader, boolean validate) {
		JsonObject jsonObject = Json.createReader(reader).readObject();
		return new VerifiableCredential(jsonObject, validate);
	}

	public static VerifiableCredential fromJson(String json, boolean validate) {
		return fromJson(new StringReader(json), validate);
	}

	public static VerifiableCredential fromJson(Reader reader) {
		return fromJson(reader, true);
	}

	public static VerifiableCredential fromJson(String json) {
		return fromJson(json, true);
	}

	/*
	 * Getters
	 */

	@SuppressWarnings("unchecked")
	public String getIssuer() {
		return JsonLDUtils.jsonLdGetString(this.getJsonObject(), VerifiableCredentialKeywords.JSONLD_TERM_ISSUER);
	}

	public Date getIssuanceDate() {
		return JsonLDUtils.stringToDate(JsonLDUtils.jsonLdGetString(this.getJsonObject(), VerifiableCredentialKeywords.JSONLD_TERM_ISSUANCE_DATE));
	}

	public Date getExpirationDate() {
		return JsonLDUtils.stringToDate(JsonLDUtils.jsonLdGetString(this.getJsonObject(), VerifiableCredentialKeywords.JSONLD_TERM_EXPIRATION_DATE));
	}

	public CredentialSubject getCredentialSubject() {
		return new CredentialSubject(JsonLDUtils.jsonLdGetJsonObject(this.getJsonObject(), VerifiableCredentialKeywords.JSONLD_TERM_CREDENTIAL_SUBJECT));
	}

	public LdProof getLdProof() {
		return new LdProof(JsonLDUtils.jsonLdGetJsonObject(this.getJsonObject(), LDSecurityKeywords.JSONLD_TERM_PROOF));
	}
}
