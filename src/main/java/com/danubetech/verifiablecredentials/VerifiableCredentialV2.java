package com.danubetech.verifiablecredentials;

import com.apicatalog.jsonld.loader.DocumentLoader;
import com.danubetech.dataintegrity.DataIntegrityProof;
import com.danubetech.verifiablecredentials.credentialstatus.CredentialStatus;
import com.danubetech.verifiablecredentials.extensions.CredentialSchema;
import com.danubetech.verifiablecredentials.extensions.Evidence;
import com.danubetech.verifiablecredentials.extensions.RefreshService;
import com.danubetech.verifiablecredentials.extensions.TermsOfUse;
import com.danubetech.verifiablecredentials.jsonld.VerifiableCredentialContexts;
import com.danubetech.verifiablecredentials.jsonld.VerifiableCredentialKeywords;
import com.fasterxml.jackson.annotation.JsonCreator;
import foundation.identity.jsonld.JsonLDObject;
import foundation.identity.jsonld.JsonLDUtils;

import java.io.Reader;
import java.net.URI;
import java.util.*;

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
		private List<CredentialSubject> credentialSubject;
		private List<CredentialStatus> credentialStatus;
		private String name;
		private String description;
		private List<DataIntegrityProof> dataIntegrityProof;

		//extensions

		private List<CredentialSchema> credentialSchema;
		private List<Evidence> evidence;
		private List<TermsOfUse> termsOfUse;
		private List<RefreshService> refreshService;

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
			if (this.issuer != null) JsonLDUtils.jsonLdAdd(this.jsonLdObject, VerifiableCredentialKeywords.JSONLD_TERM_ISSUER, this.issuer instanceof URI ? JsonLDUtils.uriToString((URI) this.issuer) : this.issuer);
			if (this.validFrom != null) JsonLDUtils.jsonLdAdd(this.jsonLdObject, VerifiableCredentialKeywords.JSONLD_TERM_VALIDFROM, JsonLDUtils.dateToString(this.validFrom));
			if (this.validUntil != null) JsonLDUtils.jsonLdAdd(this.jsonLdObject, VerifiableCredentialKeywords.JSONLD_TERM_VALIDUNTIL, JsonLDUtils.dateToString(this.validUntil));
			if (this.credentialSubject != null) this.credentialSubject.forEach(credentialSubject -> credentialSubject.addToJsonLDObject(this.jsonLdObject));
			if (this.credentialStatus != null) this.credentialStatus.forEach(credentialStatus -> credentialStatus.addToJsonLDObject(this.jsonLdObject));
			if (this.name != null) JsonLDUtils.jsonLdAdd(this.jsonLdObject, VerifiableCredentialKeywords.JSONLD_TERM_NAME, this.name);
			if (this.description != null) JsonLDUtils.jsonLdAdd(this.jsonLdObject, VerifiableCredentialKeywords.JSONLD_TERM_NAME, this.description);
			if (this.dataIntegrityProof != null) this.dataIntegrityProof.forEach(dataIntegrityProof -> dataIntegrityProof.addToJsonLDObject(this.jsonLdObject));

			// add extensions
			if (this.credentialSchema != null) this.credentialSchema.forEach(credentialSchema -> credentialSchema.addToJsonLDObject(this.jsonLdObject));
			if (this.evidence != null) this.evidence.forEach(evidence -> evidence.addToJsonLDObject(this.jsonLdObject));
			if (this.termsOfUse != null) this.termsOfUse.forEach(termsOfUse -> termsOfUse.addToJsonLDObject(this.jsonLdObject));
			if (this.refreshService != null) this.refreshService.forEach(refreshService -> refreshService.addToJsonLDObject(this.jsonLdObject));

			return (VerifiableCredentialV2) this.jsonLdObject;
		}

		public B issuer(URI issuer) {
			this.issuer = issuer;
			return (B) this;
		}

		public B issuer(Map<String,Object> issuer) {
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
			if(this.credentialSubject == null) this.credentialSubject = new ArrayList<>();
			this.credentialSubject.add(credentialSubject);
			return (B) this;
		}

		public B credentialStatus(CredentialStatus credentialStatus) {
			if (this.credentialStatus == null) this.credentialStatus = new ArrayList<>();
			this.credentialStatus.add(credentialStatus);
			return (B) this;
		}

		public B credentialStatus(Collection<CredentialStatus> credentialStatus) {
			if (this.credentialStatus == null) this.credentialStatus = new ArrayList<>();
			this.credentialStatus.addAll(credentialStatus);
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

		public B dataIntegrityProof(DataIntegrityProof dataIntegrityProof) {
			if (this.dataIntegrityProof == null) this.dataIntegrityProof = new ArrayList<>();
			this.dataIntegrityProof.add(dataIntegrityProof);
			return (B) this;
		}

		public B dataIntegrityProof(Collection<DataIntegrityProof> dataIntegrityProof) {
			if (this.dataIntegrityProof == null) this.dataIntegrityProof = new ArrayList<>();
			this.dataIntegrityProof.addAll(dataIntegrityProof);
			return (B) this;
		}

		public B credentialSchema(CredentialSchema credentialSchema) {
			if (this.credentialSchema == null) this.credentialSchema = new ArrayList<>();
			this.credentialSchema.add(credentialSchema);
			return (B) this;
		}

		public B credentialSchema(Collection<CredentialSchema> credentialSchema) {
			if (this.credentialSchema == null) this.credentialSchema = new ArrayList<>();
			this.credentialSchema.addAll(credentialSchema);
			return (B) this;
		}

		public B evidence(Evidence evidence) {
			if (this.evidence == null) this.evidence = new ArrayList<>();
			this.evidence.add(evidence);
			return (B) this;
		}

		public B evidence(Collection<Evidence> evidence) {
			if (this.evidence == null) this.evidence = new ArrayList<>();
			this.evidence.addAll(evidence);
			return (B) this;
		}

		public B termsOfUse(TermsOfUse termsOfUse) {
			if (this.termsOfUse == null) this.termsOfUse = new ArrayList<>();
			this.termsOfUse.add(termsOfUse);
			return (B) this;
		}

		public B termsOfUse(Collection<TermsOfUse> termsOfUse) {
			if (this.termsOfUse == null) this.termsOfUse = new ArrayList<>();
			this.termsOfUse.addAll(termsOfUse);
			return (B) this;
		}

		public B refreshService(RefreshService refreshService) {
			if (this.refreshService == null) this.refreshService = new ArrayList<>();
			this.refreshService.add(refreshService);
			return (B) this;
		}

		public B refreshService(Collection<RefreshService> refreshService) {
			if (this.refreshService == null) this.refreshService = new ArrayList<>();
			this.refreshService.addAll(refreshService);
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

	public static List<VerifiableCredentialV2> getFromJsonLDObjectAsList(JsonLDObject jsonLdObject) {
		return JsonLDObject.getFromJsonLDObjectAsList(VerifiableCredentialV2.class, jsonLdObject);
	}

	public static void removeFromJsonLdObject(JsonLDObject jsonLdObject) {
		JsonLDObject.removeFromJsonLdObject(VerifiableCredentialV2.class, jsonLdObject);
	}

	/*
	 * Getters
	 */

	public Object getIssuer() {
		return JsonLDUtils.jsonLdGetJsonValue(this.getJsonObject(), VerifiableCredentialKeywords.JSONLD_TERM_ISSUER);
	}

	public URI getIssuerUri() {
		Object issuer = JsonLDUtils.jsonLdGetJsonValue(this.getJsonObject(), VerifiableCredentialKeywords.JSONLD_TERM_ISSUER);
		return (issuer instanceof String) ? URI.create(issuer.toString()) : URI.create((((Map<String,Object>)issuer).get(VerifiableCredentialKeywords.JSONLD_TERM_ISSUER)).toString());
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

	public List<CredentialSubject> getCredentialSubjectAsList() {
		return CredentialSubject.getFromJsonLDObjectAsList(this);
	}

	public CredentialStatus getCredentialStatus() {
		return CredentialStatus.getFromJsonLDObject(this);
	}

	public List<CredentialStatus> getCredentialStatusAsList() {
		return CredentialStatus.getFromJsonLDObjectAsList(this);
	}

	public String getName(){
		return JsonLDUtils.jsonLdGetString(this.getJsonObject(), VerifiableCredentialKeywords.JSONLD_TERM_NAME);
	}

	public String getDescription(){
		return JsonLDUtils.jsonLdGetString(this.getJsonObject(), VerifiableCredentialKeywords.JSONLD_TERM_DESCRIPTION);
	}

	public DataIntegrityProof getDataIntegrityProof() {
		return DataIntegrityProof.getFromJsonLDObject(this);
	}

	public List<DataIntegrityProof> getDataIntegrityProofAsList() {
		return DataIntegrityProof.getFromJsonLDObjectAsList(this);
	}

	public CredentialSchema getCredentialSchema() {
		return CredentialSchema.getFromJsonLDObject(this);
	}

	public List<CredentialSchema> getCredentialSchemaAsList() {
		return CredentialSchema.getFromJsonLDObjectAsList(this);
	}

	public Evidence getEvidence() {
		return Evidence.getFromJsonLDObject(this);
	}

	public List<Evidence> getEvidenceAsList() {
		return Evidence.getFromJsonLDObjectAsList(this);
	}

	public TermsOfUse getTermsOfUse() {
		return TermsOfUse.getFromJsonLDObject(this);
	}

	public List<TermsOfUse> getTermsOfUseAsList() {
		return TermsOfUse.getFromJsonLDObjectAsList(this);
	}

	public RefreshService getRefreshService() {
		return RefreshService.getFromJsonLDObject(this);
	}

	public List<RefreshService> getRefreshServiceAsList() {
		return RefreshService.getFromJsonLDObjectAsList(this);
	}
}
