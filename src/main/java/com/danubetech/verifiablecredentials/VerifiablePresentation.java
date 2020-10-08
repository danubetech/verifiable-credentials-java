package com.danubetech.verifiablecredentials;

import java.io.Reader;
import java.net.URI;

import com.danubetech.verifiablecredentials.jsonld.VerifiableCredentialContexts;
import com.danubetech.verifiablecredentials.jsonld.VerifiableCredentialKeywords;
import com.danubetech.verifiablecredentials.validation.Validation;

import foundation.identity.jsonld.JsonLDObject;
import info.weboftrust.ldsignatures.LdProof;

import javax.json.JsonObject;

public class VerifiablePresentation extends JsonLDObject {

	public static final URI[] DEFAULT_JSONLD_CONTEXTS = { VerifiableCredentialContexts.JSONLD_CONTEXT_W3C_2018_CREDENTIALS_V1 };
	public static final String[] DEFAULT_JSONLD_TYPES = { VerifiableCredentialKeywords.JSONLD_TERM_VERIFIABLE_PRESENTATION };
	public static final String DEFAULT_JSONLD_PREDICATE = null;

	private VerifiablePresentation() {
		super(VerifiableCredentialContexts.DOCUMENT_LOADER);
	}

	public VerifiablePresentation(JsonObject jsonObject) {
		super(VerifiableCredentialContexts.DOCUMENT_LOADER, jsonObject);
	}

	/*
	 * Factory methods
	 */

	public static class Builder extends JsonLDObject.Builder<VerifiablePresentation.Builder, VerifiablePresentation> {

		private VerifiableCredential verifiableCredential;
		private LdProof ldProof;

		public Builder(VerifiablePresentation jsonLDObject) {
			super(jsonLDObject);
		}

		@Override
		public VerifiablePresentation build() {

			super.build();

			// add JSON-LD properties
			if (this.verifiableCredential != null) this.verifiableCredential.addToJsonLDObject(this.jsonLDObject);
			if (this.ldProof != null) this.ldProof.addToJsonLDObject(this.jsonLDObject);

			return this.jsonLDObject;
		}

		public Builder verifiableCredential(VerifiableCredential verifiableCredential) {
			this.verifiableCredential = verifiableCredential;
			return this;
		}

		public Builder ldProof(LdProof ldProof) {
			this.ldProof = ldProof;
			return this;
		}
	}

	public static Builder builder() {
		return new Builder(new VerifiablePresentation())
				.defaultContexts(true)
				.defaultTypes(true);
	}

	/*
	 * Reading the JSON-LD object
	 */

	public static VerifiablePresentation fromJson(Reader reader) {
		return JsonLDObject.fromJson(VerifiablePresentation.class, reader);
	}

	public static VerifiablePresentation fromJson(String json) {
		return JsonLDObject.fromJson(VerifiablePresentation.class, json);
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

	@SuppressWarnings("unchecked")
	public VerifiableCredential getVerifiableCredential() {
		return VerifiableCredential.getFromJsonLDObject(this);
	}

	public LdProof getLdProof() {
		return LdProof.getFromJsonLDObject(this);
	}
}
