package com.danubetech.verifiablecredentials;

import java.io.Reader;
import java.io.StringReader;

import com.danubetech.verifiablecredentials.jsonld.VerifiableCredentialContexts;
import com.danubetech.verifiablecredentials.jsonld.VerifiableCredentialKeywords;
import com.danubetech.verifiablecredentials.validation.Validation;

import foundation.identity.jsonld.JsonLDObject;
import foundation.identity.jsonld.JsonLDUtils;
import info.weboftrust.ldsignatures.LdProof;
import info.weboftrust.ldsignatures.jsonld.LDSecurityKeywords;

import javax.json.Json;
import javax.json.JsonObject;

public class VerifiablePresentation extends JsonLDObject {

	public static final String DEFAULT_JSONLD_CONTEXT = "https://www.w3.org/2018/credentials/v1";
	public static final String DEFAULT_JSONLD_TYPE = "VerifiablePresentation";

	private VerifiablePresentation() {
		super(VerifiableCredentialContexts.DOCUMENT_LOADER);
	}

	private VerifiablePresentation(JsonObject jsonObject, boolean validate) {
		super(VerifiableCredentialContexts.DOCUMENT_LOADER, jsonObject);
		if (validate) Validation.validate(this);
	}

	public VerifiablePresentation(JsonObject jsonObject) {
		this(jsonObject, true);
	}

	/*
	 * Factory methods
	 */

	public static class Builder extends JsonLDObject.Builder<VerifiablePresentation.Builder, VerifiablePresentation> {

		private VerifiableCredential verifiableCredential;
		private LdProof ldProof;

		public Builder() {
			super(new VerifiablePresentation());
		}

		public VerifiablePresentation build() {

			super.build();

			// add JSON-LD properties
			if (this.verifiableCredential != null) JsonLDUtils.jsonLdAddJsonValue(this.jsonLDObject.getJsonObjectBuilder(), VerifiableCredentialKeywords.JSONLD_TERM_VERIFIABLE_CREDENTIAL, this.verifiableCredential.getJsonObject());

			return this.jsonLDObject;
		}

		public VerifiablePresentation.Builder verifiableCredential(VerifiableCredential verifiableCredential) {
			this.verifiableCredential = verifiableCredential;
			return this;
		}

		public VerifiablePresentation.Builder ldProof(LdProof ldProof) {
			this.ldProof = ldProof;
			return this;
		}
	}

	public static VerifiablePresentation.Builder builder() {

		return new VerifiablePresentation.Builder()
				.context(DEFAULT_JSONLD_CONTEXT)
				.type(DEFAULT_JSONLD_TYPE);
	}

	/*
	 * Serialization
	 */

	public static VerifiablePresentation fromJson(Reader reader, boolean validate) {
		JsonObject jsonObject = Json.createReader(reader).readObject();
		return new VerifiablePresentation(jsonObject, validate);
	}

	public static VerifiablePresentation fromJson(String json, boolean validate) {
		return fromJson(new StringReader(json), validate);
	}

	public static VerifiablePresentation fromJson(Reader reader) {
		return fromJson(reader, true);
	}

	public static VerifiablePresentation fromJson(String json) {
		return fromJson(json, true);
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

	@SuppressWarnings("unchecked")
	public VerifiableCredential getVerifiableCredential() {
		return new VerifiableCredential(JsonLDUtils.jsonLdGetJsonObject(this.getJsonObject(), VerifiableCredentialKeywords.JSONLD_TERM_VERIFIABLE_CREDENTIAL));
	}

	public LdProof getLdProof() {
		return new LdProof(JsonLDUtils.jsonLdGetJsonObject(this.getJsonObject(), LDSecurityKeywords.JSONLD_TERM_PROOF));
	}
}
