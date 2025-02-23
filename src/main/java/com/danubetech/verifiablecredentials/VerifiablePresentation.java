package com.danubetech.verifiablecredentials;

import com.apicatalog.jsonld.loader.DocumentLoader;
import com.danubetech.dataintegrity.DataIntegrityProof;
import com.danubetech.verifiablecredentials.jsonld.VerifiableCredentialContexts;
import com.danubetech.verifiablecredentials.jsonld.VerifiableCredentialKeywords;
import com.fasterxml.jackson.annotation.JsonCreator;
import foundation.identity.jsonld.JsonLDObject;
import foundation.identity.jsonld.JsonLDUtils;

import java.io.Reader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class VerifiablePresentation extends JsonLDObject {

	public static final URI[] DEFAULT_JSONLD_CONTEXTS = { VerifiableCredentialContexts.JSONLD_CONTEXT_W3C_2018_CREDENTIALS_V1 };
	public static final String[] DEFAULT_JSONLD_TYPES = { VerifiableCredentialKeywords.JSONLD_TERM_VERIFIABLE_PRESENTATION };
	public static final String DEFAULT_JSONLD_PREDICATE = null;
	public static final DocumentLoader DEFAULT_DOCUMENT_LOADER = VerifiableCredentialContexts.DOCUMENT_LOADER;

	@JsonCreator
	public VerifiablePresentation() {
		super();
	}

	protected VerifiablePresentation(Map<String, Object> jsonObject) {
		super(jsonObject);
	}

	/*
	 * Factory methods
	 */

	public static class Builder<B extends Builder<B>> extends JsonLDObject.Builder<B> {

		private URI holder;
		private List<VerifiableCredential> verifiableCredential;
		private List<DataIntegrityProof> dataIntegrityProof;

		public Builder(VerifiablePresentation jsonLdObject) {
			super(jsonLdObject);
			this.forceContextsArray(true);
			this.forceTypesArray(true);
			this.defaultContexts(true);
			this.defaultTypes(true);
		}

		@Override
		public VerifiablePresentation build() {

			super.build();

			// add JSON-LD properties
			if (this.holder != null) JsonLDUtils.jsonLdAdd(this.jsonLdObject, VerifiableCredentialKeywords.JSONLD_TERM_HOLDER, JsonLDUtils.uriToString(this.holder));
			if (this.verifiableCredential != null) this.verifiableCredential.forEach(verifiableCredential -> verifiableCredential.addToJsonLDObject(this.jsonLdObject));
			if (this.dataIntegrityProof != null) this.dataIntegrityProof.forEach(dataIntegrityProof -> dataIntegrityProof.addToJsonLDObject(this.jsonLdObject));

			return (VerifiablePresentation) this.jsonLdObject;
		}

		public B holder(URI holder) {
			this.holder = holder;
			return (B) this;
		}

		public B verifiableCredential(VerifiableCredential verifiableCredential) {
			if (this.verifiableCredential == null) this.verifiableCredential = new ArrayList<>();
			this.verifiableCredential.add(verifiableCredential);
			return (B) this;
		}

		public B verifiableCredential(Collection<VerifiableCredential> verifiableCredential) {
			if (this.verifiableCredential == null) this.verifiableCredential = new ArrayList<>();
			this.verifiableCredential.addAll(verifiableCredential);
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
	}

	public static Builder<? extends Builder<?>> builder() {
		return new Builder<>(new VerifiablePresentation());
	}

	public static VerifiablePresentation fromJsonObject(Map<String, Object> jsonObject) {
		return new VerifiablePresentation(jsonObject);
	}

	public static VerifiablePresentation fromJsonLDObject(JsonLDObject jsonLDObject) { return fromJsonObject(jsonLDObject.getJsonObject()); }

	public static VerifiablePresentation fromJson(Reader reader) {
		return new VerifiablePresentation(readJson(reader));
	}

	public static VerifiablePresentation fromJson(String json) {
		return new VerifiablePresentation(readJson(json));
	}

	public static VerifiablePresentation fromMap(Map<String, Object> jsonObject) {
		return new VerifiablePresentation(jsonObject);
	}

	/*
	 * Adding, getting, and removing the JSON-LD object
	 */

	public static VerifiablePresentation getFromJsonLDObject(JsonLDObject jsonLdObject) {
		return JsonLDObject.getFromJsonLDObject(VerifiablePresentation.class, jsonLdObject);
	}

	public static List<VerifiablePresentation> getFromJsonLDObjectAsList(JsonLDObject jsonLdObject) {
		return JsonLDObject.getFromJsonLDObjectAsList(VerifiablePresentation.class, jsonLdObject);
	}

	public static void removeFromJsonLdObject(JsonLDObject jsonLdObject) {
		JsonLDObject.removeFromJsonLdObject(VerifiablePresentation.class, jsonLdObject);
	}

	/*
	 * Getters
	 */

	public URI getHolder() {
		return JsonLDUtils.stringToUri(JsonLDUtils.jsonLdGetStringOrObjectId(this.getJsonObject(), VerifiableCredentialKeywords.JSONLD_TERM_HOLDER));
	}

	public VerifiableCredential getVerifiableCredential() {
		return VerifiableCredential.getFromJsonLDObject(this);
	}

	public List<VerifiableCredential> getVerifiableCredentialAsList() {
		return VerifiableCredential.getFromJsonLDObjectAsList(this);
	}

	public String getJwtVerifiableCredentialString() {
		Object verifiableCredentialObject = this.getJsonObject().get(VerifiableCredentialKeywords.JSONLD_TERM_VERIFIABLECREDENTIAL);
		if (verifiableCredentialObject instanceof List<?> && ! ((List<?>) verifiableCredentialObject).isEmpty() && ((List<?>) verifiableCredentialObject).get(0) instanceof String) {
			return (String) ((List<?>) verifiableCredentialObject).get(0);
		} else if (verifiableCredentialObject instanceof String) {
			return (String) verifiableCredentialObject;
		}
		return null;
	}

	public DataIntegrityProof getDataIntegrityProof() {
		return DataIntegrityProof.getFromJsonLDObject(this);
	}

	public List<DataIntegrityProof> getDataIntegrityProofAsList() {
		return DataIntegrityProof.getFromJsonLDObjectAsList(this);
	}
}
