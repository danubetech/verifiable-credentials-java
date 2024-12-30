package com.danubetech.verifiablecredentials;

import com.apicatalog.jsonld.loader.DocumentLoader;
import com.danubetech.verifiablecredentials.jsonld.VerifiableCredentialContexts;
import com.danubetech.verifiablecredentials.jsonld.VerifiableCredentialKeywords;
import com.fasterxml.jackson.annotation.JsonCreator;
import foundation.identity.jsonld.JsonLDObject;
import foundation.identity.jsonld.JsonLDUtils;
import info.weboftrust.ldsignatures.LdProof;

import java.io.Reader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class VerifiablePresentationV2 extends JsonLDObject {

	public static final URI[] DEFAULT_JSONLD_CONTEXTS = { VerifiableCredentialContexts.JSONLD_CONTEXT_W3C_CREDENTIALS_V2 };
	public static final String[] DEFAULT_JSONLD_TYPES = { VerifiableCredentialKeywords.JSONLD_TERM_VERIFIABLE_PRESENTATION };
	public static final String DEFAULT_JSONLD_PREDICATE = null;
	public static final DocumentLoader DEFAULT_DOCUMENT_LOADER = VerifiableCredentialContexts.DOCUMENT_LOADER;

	@JsonCreator
	public VerifiablePresentationV2() {
		super();
	}

	protected VerifiablePresentationV2(Map<String, Object> jsonObject) {
		super(jsonObject);
	}

	/*
	 * Factory methods
	 */

	public static class Builder<B extends Builder<B>> extends JsonLDObject.Builder<B> {

		private URI holder;
		private List<VerifiableCredentialV2> verifiableCredential;
		private List<LdProof> ldProof;

		public Builder(VerifiablePresentationV2 jsonLdObject) {
			super(jsonLdObject);
			this.forceContextsArray(true);
			this.forceTypesArray(true);
			this.defaultContexts(true);
			this.defaultTypes(true);
		}

		@Override
		public VerifiablePresentationV2 build() {

			super.build();

			// add JSON-LD properties
			if (this.holder != null) JsonLDUtils.jsonLdAdd(this.jsonLdObject, VerifiableCredentialKeywords.JSONLD_TERM_HOLDER, JsonLDUtils.uriToString(this.holder));
			if (this.verifiableCredential != null) this.verifiableCredential.forEach(verifiableCredential -> verifiableCredential.addToJsonLDObject(this.jsonLdObject));
			if (this.ldProof != null) this.ldProof.forEach(ldProof -> ldProof.addToJsonLDObject(this.jsonLdObject));

			return (VerifiablePresentationV2) this.jsonLdObject;
		}

		public B holder(URI holder) {
			this.holder = holder;
			return (B) this;
		}

		public B verifiableCredential(VerifiableCredentialV2 verifiableCredential) {
			if (this.verifiableCredential == null) this.verifiableCredential = new ArrayList<>();
			this.verifiableCredential.add(verifiableCredential);
			return (B) this;
		}

		public B verifiableCredential(Set<VerifiableCredentialV2> verifiableCredential) {
			if (this.verifiableCredential == null) this.verifiableCredential = new ArrayList<>();
			this.verifiableCredential.addAll(verifiableCredential);
			return (B) this;
		}

		public B ldProof(LdProof ldProof) {
			if (this.ldProof == null) this.ldProof = new ArrayList<>();
			this.ldProof.add(ldProof);
			return (B) this;
		}

		public B ldProof(Set<LdProof> ldProof) {
			if (this.ldProof == null) this.ldProof = new ArrayList<>();
			this.ldProof.addAll(ldProof);
			return (B) this;
		}
	}

	public static Builder<? extends Builder<?>> builder() {
		return new Builder<>(new VerifiablePresentationV2());
	}

	public static VerifiablePresentationV2 fromJsonObject(Map<String, Object> jsonObject) {
		return new VerifiablePresentationV2(jsonObject);
	}

	public static VerifiablePresentationV2 fromJsonLDObject(JsonLDObject jsonLDObject) { return fromJsonObject(jsonLDObject.getJsonObject()); }

	public static VerifiablePresentationV2 fromJson(Reader reader) {
		return new VerifiablePresentationV2(readJson(reader));
	}

	public static VerifiablePresentationV2 fromJson(String json) {
		return new VerifiablePresentationV2(readJson(json));
	}

	public static VerifiablePresentationV2 fromMap(Map<String, Object> jsonObject) {
		return new VerifiablePresentationV2(jsonObject);
	}

	/*
	 * Adding, getting, and removing the JSON-LD object
	 */

	public static VerifiablePresentationV2 getFromJsonLDObject(JsonLDObject jsonLdObject) {
		return JsonLDObject.getFromJsonLDObject(VerifiablePresentationV2.class, jsonLdObject);
	}

	public static List<VerifiablePresentationV2> getFromJsonLDObjectAsList(JsonLDObject jsonLdObject) {
		return JsonLDObject.getFromJsonLDObjectAsList(VerifiablePresentationV2.class, jsonLdObject);
	}

	public static void removeFromJsonLdObject(JsonLDObject jsonLdObject) {
		JsonLDObject.removeFromJsonLdObject(VerifiablePresentationV2.class, jsonLdObject);
	}

	/*
	 * Getters
	 */

	public URI getHolder() {
		return JsonLDUtils.stringToUri(JsonLDUtils.jsonLdGetStringOrObjectId(this.getJsonObject(), VerifiableCredentialKeywords.JSONLD_TERM_HOLDER));
	}

	public VerifiableCredentialV2 getVerifiableCredential() {
		return VerifiableCredentialV2.getFromJsonLDObject(this);
	}

	public List<VerifiableCredentialV2> getVerifiableCredentialAsList() {
		return VerifiableCredentialV2.getFromJsonLDObjectAsList(this);
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

	public LdProof getLdProof() {
		return LdProof.getFromJsonLDObject(this);
	}

	public List<LdProof> getLdProofAsList() {
		return LdProof.getFromJsonLDObjectAsList(this);
	}
}
