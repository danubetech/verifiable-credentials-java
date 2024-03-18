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
		private VerifiableCredential verifiableCredential;
		private LdProof ldProof;

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
			if (this.verifiableCredential != null) this.verifiableCredential.addToJsonLDObject(this.jsonLdObject);
			if (this.ldProof != null) this.ldProof.addToJsonLDObject(this.jsonLdObject);

			return (VerifiablePresentation) this.jsonLdObject;
		}

		public B holder(URI holder) {
			this.holder = holder;
			return (B) this;
		}

		public B verifiableCredential(VerifiableCredential verifiableCredential) {
			this.verifiableCredential = verifiableCredential;
			return (B) this;
		}

		public B ldProof(LdProof ldProof) {
			this.ldProof = ldProof;
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

	public static void removeFromJsonLdObject(JsonLDObject jsonLdObject) {
		JsonLDObject.removeFromJsonLdObject(VerifiablePresentation.class, jsonLdObject);
	}

	/*
	public static VerifiablePresentation fromVerifiableCredential(VerifiableCredential verifiableCredential, boolean validate) throws JsonGenerationException, IOException {

		LinkedHashMap<String, Object> jsonLdObject = new LinkedHashMap<String, Object> ();

		ArrayList<Object> contextList = new ArrayList<Object> ();
		contextList.add(JSONLD_CONTEXT_CREDENTIALS);

		ArrayList<String> typeList = new ArrayList<String> ();
		typeList.add(JSONLD_TYPE_VERIFIABLE_PRESENTATION);

		ArrayList<Object> verifiableCredentialList = new ArrayList<Object> ();
		verifiableCredentialList.add(verifiableCredential.getJsonLdObject());

		jsonLdObject = new LinkedHashMap<String, Object> ();
		jsonLdObject.put(JsonLdConsts.CONTEXT, contextList);
		jsonLdObject.put(JSONLD_TERM_TYPE, typeList);
		jsonLdObject.put(JSONLD_TERM_VERIFIABLE_CREDENTIAL, verifiableCredentialList);

		return fromJsonLdObject(jsonLdObject, validate);
	}

	public static VerifiablePresentation fromVerifiableCredential(VerifiableCredential verifiableCredential) throws JsonParseException, IOException {

		return fromVerifiableCredential(verifiableCredential, true);
	}
*/

	/*
	 * Getters
	 */

	public URI getHolder() {
		return JsonLDUtils.stringToUri(JsonLDUtils.jsonLdGetStringOrObjectId(this.getJsonObject(), VerifiableCredentialKeywords.JSONLD_TERM_HOLDER));
	}

	public VerifiableCredential getVerifiableCredential() {
		return getVerifiableCredentials().stream().findFirst().orElse(null);
	}

	public String getJwtVerifiableCredentialString() {
		return getJwtVerifiableCredentialStrings().stream().findFirst().orElse(null);
	}

	private List<?> getVerifiableCredentialObjects() {
		Object verifiableCredentialObject = this.getJsonObject().get(VerifiableCredentialKeywords.JSONLD_TERM_VERIFIABLECREDENTIAL);
		if (verifiableCredentialObject instanceof List<?>) {
			return (List<?>) verifiableCredentialObject;
		} else if (verifiableCredentialObject != null) {
			return List.of(verifiableCredentialObject);
		} else {
			return List.of();
		}
	}

	public List<VerifiableCredential> getVerifiableCredentials() {
		List<?> vcObjs = getVerifiableCredentialObjects();
		return vcObjs.stream()
				.filter(o -> o instanceof Map)
				.map(o -> VerifiableCredential.fromMap((Map) o))
				.toList();
	}

	public List<String> getJwtVerifiableCredentialStrings() {
		List<?> vcObjs = getVerifiableCredentialObjects();
		return vcObjs.stream()
				.filter(o -> o instanceof String)
				.map(o -> (String) o)
				.toList();
	}

	public List<?> getUnsupportedVerifiableCredentials() {
		List<?> vcObjs = getVerifiableCredentialObjects();
		return vcObjs.stream()
				.filter(o -> ! (o instanceof Map || o instanceof String))
				.toList();
	}

	public LdProof getLdProof() {
		return LdProof.getFromJsonLDObject(this);
	}
}
