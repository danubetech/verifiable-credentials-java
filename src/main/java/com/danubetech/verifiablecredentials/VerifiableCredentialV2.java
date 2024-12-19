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
import java.util.List;
import java.util.Map;

public class VerifiableCredentialV2 extends JsonLDObject {

	public static final URI[] DEFAULT_JSONLD_CONTEXTS = { VerifiableCredentialContexts.JSONLD_CONTEXT_W3C_CREDENTIALS_V2 };
	public static final String[] DEFAULT_JSONLD_TYPES = { VerifiableCredentialKeywords.JSONLD_TERM_VERIFIABLE_CREDENTIAL };
	public static final String DEFAULT_JSONLD_PREDICATE = VerifiableCredentialKeywords.JSONLD_TERM_VERIFIABLECREDENTIAL;
	public static final DocumentLoader DEFAULT_DOCUMENT_LOADER = VerifiableCredentialContexts.DOCUMENT_LOADER;

	@JsonCreator
	public VerifiableCredentialV2() {
		super();
	}

	protected VerifiableCredentialV2(Map<String, Object> jsonObject) {
		super(jsonObject);
	}

	/*
	 * Factory methods
	 */

	public static class Builder<B extends Builder<B>> extends JsonLDObject.Builder<B> {

		private Object issuer;
		private Date validFrom;
		private Date validUntil;
		private CredentialSubject credentialSubject;
		private Object credentialStatus;
		private String name;
		private String description;
		private LdProof ldProof;

		public Builder(VerifiableCredentialV2 jsonLdObject) {
			super(jsonLdObject);
			this.forceContextsArray(true);
			this.forceTypesArray(true);
			this.defaultContexts(true);
			this.defaultTypes(true);
		}

		@Override
		public VerifiableCredentialV2 build() {

			super.build();

			// add JSON-LD properties
			if (this.issuer != null) JsonLDUtils.jsonLdAdd(this.jsonLdObject, VerifiableCredentialKeywords.JSONLD_TERM_ISSUER, this.issuer);
			if (this.validFrom != null) JsonLDUtils.jsonLdAdd(this.jsonLdObject, VerifiableCredentialKeywords.JSONLD_TERM_VALIDFROM, JsonLDUtils.dateToString(this.validFrom));
			if (this.validUntil != null) JsonLDUtils.jsonLdAdd(this.jsonLdObject, VerifiableCredentialKeywords.JSONLD_TERM_VALIDUNTIL, JsonLDUtils.dateToString(this.validUntil));
			if (this.credentialSubject != null) this.credentialSubject.addToJsonLDObject(this.jsonLdObject);
			if (this.credentialStatus != null) JsonLDUtils.jsonLdAdd(this.jsonLdObject, VerifiableCredentialKeywords.JSONLD_TERM_CREDENTIALSTATUS, this.credentialStatus);
			if(this.name != null) JsonLDUtils.jsonLdAdd(this.jsonLdObject, VerifiableCredentialKeywords.JSONLD_TERM_NAME, this.name);
			if(this.description != null) JsonLDUtils.jsonLdAdd(this.jsonLdObject, VerifiableCredentialKeywords.JSONLD_TERM_NAME, this.description);
			if (this.ldProof != null) this.ldProof.addToJsonLDObject(this.jsonLdObject);

			return (VerifiableCredentialV2) this.jsonLdObject;
		}

		public B issuer(URI issuer) {
			this.issuer = issuer;
			return (B) this;
		}

		public B validFrom(Date validFrom) {
			this.validFrom = validFrom;
			return (B) this;
		}

		public B validUntil(Date validUntil) {
			this.validUntil = validUntil;
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

		public B credentialStatus(List<CredentialStatus> credentialStatus) {
			this.credentialStatus = credentialStatus;
			return (B) this;
		}

		public B ldProof(LdProof ldProof) {
			this.ldProof = ldProof;
			return (B) this;
		}

		public B name(String name) {
			this.name = name;
			return (B) this;
		}

		public B description(String description) {
			this.description = description;
			return (B) this;
		}
	}

	public static Builder<? extends Builder<?>> builder() {
		return new Builder<>(new VerifiableCredentialV2());
	}

	public static VerifiableCredentialV2 fromJsonObject(Map<String, Object> jsonObject) {
		return new VerifiableCredentialV2(jsonObject);
	}

	public static VerifiableCredentialV2 fromJsonLDObject(JsonLDObject jsonLDObject) { return fromJsonObject(jsonLDObject.getJsonObject()); }

	public static VerifiableCredentialV2 fromJson(Reader reader) {
		return new VerifiableCredentialV2(readJson(reader));
	}

	public static VerifiableCredentialV2 fromJson(String json) {
		return new VerifiableCredentialV2(readJson(json));
	}

	public static VerifiableCredentialV2 fromMap(Map<String, Object> map) {
		return new VerifiableCredentialV2(map);
	}

	/*
	 * Adding, getting, and removing the JSON-LD object
	 */

	public static VerifiableCredentialV2 getFromJsonLDObject(JsonLDObject jsonLdObject) {
		return JsonLDObject.getFromJsonLDObject(VerifiableCredentialV2.class, jsonLdObject);
	}

	public static void removeFromJsonLdObject(JsonLDObject jsonLdObject) {
		JsonLDObject.removeFromJsonLdObject(VerifiableCredentialV2.class, jsonLdObject);
	}

	/*
	 * Getters
	 */

	public Object getIssuer() {
		return JsonLDUtils.stringToUri(JsonLDUtils.jsonLdGetStringOrObjectId(this.getJsonObject(), VerifiableCredentialKeywords.JSONLD_TERM_ISSUER));
	}

	public Date getValidFrom() {
		return JsonLDUtils.stringToDate(JsonLDUtils.jsonLdGetString(this.getJsonObject(), VerifiableCredentialKeywords.JSONLD_TERM_VALIDFROM));
	}

	public Date getValidUntil() {
		return JsonLDUtils.stringToDate(JsonLDUtils.jsonLdGetString(this.getJsonObject(), VerifiableCredentialKeywords.JSONLD_TERM_VALIDUNTIL));
	}

	public CredentialSubject getCredentialSubject() {
		return CredentialSubject.getFromJsonLDObject(this);
	}

	public LdProof getLdProof() {
		return LdProof.getFromJsonLDObject(this);
	}

	//The object can be either CredentialStatus object or list of CredentialStatus objects
	public Object getCredentialStatus() {
		return  CredentialStatus.getFromJsonLDObject(this);
	}


	public String getName(){
		return JsonLDUtils.jsonLdGetString(this.getJsonObject(), VerifiableCredentialKeywords.JSONLD_TERM_NAME);
	}

	public String getDescription(){
		return JsonLDUtils.jsonLdGetString(this.getJsonObject(), VerifiableCredentialKeywords.JSONLD_TERM_DESCRIPTION);
	}


}
