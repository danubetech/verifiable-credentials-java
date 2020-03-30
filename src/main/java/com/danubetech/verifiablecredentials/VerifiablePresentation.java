package com.danubetech.verifiablecredentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.danubetech.verifiablecredentials.jwt.JwtVerifiableCredential;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.github.jsonldjava.core.JsonLdConsts;
import com.github.jsonldjava.utils.JsonUtils;

public class VerifiablePresentation {

	public static final String JSONLD_CONTEXT_CREDENTIALS = "https://www.w3.org/2018/credentials/v1";
	public static final String JSONLD_CONTEXT_CREDENTIALS_NO_WWW = "https://w3.org/2018/credentials/v1";
	public static final String JSONLD_TYPE_VERIFIABLE_PRESENTATION = "VerifiablePresentation";

	public static final String JSONLD_TERM_ID = "id";
	public static final String JSONLD_TERM_TYPE = "type";

	public static final String JSONLD_TERM_VERIFIABLE_CREDENTIAL = "verifiableCredential";

	private final LinkedHashMap<String, Object> jsonLdObject;

	private VerifiablePresentation(LinkedHashMap<String, Object> jsonLdObject, boolean validate) { 

		this.jsonLdObject = jsonLdObject;

		if (validate) this.validate();
	}

	public VerifiablePresentation() {

		ArrayList<Object> context = new ArrayList<Object> ();
		context.add(JSONLD_CONTEXT_CREDENTIALS);

		ArrayList<String> type = new ArrayList<String> ();
		type.add(JSONLD_TYPE_VERIFIABLE_PRESENTATION);

		ArrayList<String> verifiableCredential = new ArrayList<String> ();

		this.jsonLdObject = new LinkedHashMap<String, Object> ();
		this.jsonLdObject.put(JsonLdConsts.CONTEXT, context);
		this.jsonLdObject.put(JSONLD_TERM_TYPE, type);
		this.jsonLdObject.put(JSONLD_TERM_VERIFIABLE_CREDENTIAL, verifiableCredential);
	}

	public static VerifiablePresentation fromJsonLdObject(LinkedHashMap<String, Object> jsonLdObject, boolean validate) {

		return new VerifiablePresentation(jsonLdObject, validate);
	}

	public static VerifiablePresentation fromJsonLdObject(LinkedHashMap<String, Object> jsonLdObject) {

		return fromJsonLdObject(jsonLdObject, true);
	}

	public static VerifiablePresentation fromJsonString(String jsonString, boolean validate) throws JsonParseException, IOException {

		LinkedHashMap<String, Object> jsonLdObject = (LinkedHashMap<String, Object>) JsonUtils.fromString(jsonString);

		return fromJsonLdObject(jsonLdObject, validate);
	}

	public static VerifiablePresentation fromJsonString(String jsonString) throws JsonParseException, IOException {

		return fromJsonString(jsonString, true);
	}

	public static VerifiablePresentation fromVerifiableCredential(VerifiableCredential verifiableCredential, boolean validate) throws JsonGenerationException, IOException {

		LinkedHashMap<String, Object> jsonLdObject = new LinkedHashMap<String, Object> ();

		ArrayList<Object> contextList = new ArrayList<Object> ();
		contextList.add(JSONLD_CONTEXT_CREDENTIALS);

		ArrayList<String> typeList = new ArrayList<String> ();
		typeList.add(JSONLD_TYPE_VERIFIABLE_PRESENTATION);

		ArrayList<String> verifiableCredentialList = new ArrayList<String> ();
		verifiableCredentialList.add(verifiableCredential.toJsonString());

		jsonLdObject = new LinkedHashMap<String, Object> ();
		jsonLdObject.put(JsonLdConsts.CONTEXT, contextList);
		jsonLdObject.put(JSONLD_TERM_TYPE, typeList);
		jsonLdObject.put(JSONLD_TERM_VERIFIABLE_CREDENTIAL, verifiableCredentialList);

		return fromJsonLdObject(jsonLdObject, validate);
	}

	public static VerifiablePresentation fromVerifiableCredential(VerifiableCredential verifiableCredential) throws JsonParseException, IOException {

		return fromVerifiableCredential(verifiableCredential, true);
	}

	public static VerifiablePresentation fromJwtVerifiableCredential(JwtVerifiableCredential jwtVerifiableCredential, boolean validate) {

		LinkedHashMap<String, Object> jsonLdObject = new LinkedHashMap<String, Object> ();

		ArrayList<Object> contextList = new ArrayList<Object> ();
		contextList.add(JSONLD_CONTEXT_CREDENTIALS);

		ArrayList<String> typeList = new ArrayList<String> ();
		typeList.add(JSONLD_TYPE_VERIFIABLE_PRESENTATION);

		ArrayList<String> verifiableCredentialList = new ArrayList<String> ();
		verifiableCredentialList.add(jwtVerifiableCredential.getCompactSerialization());

		jsonLdObject = new LinkedHashMap<String, Object> ();
		jsonLdObject.put(JsonLdConsts.CONTEXT, contextList);
		jsonLdObject.put(JSONLD_TERM_TYPE, typeList);
		jsonLdObject.put(JSONLD_TERM_VERIFIABLE_CREDENTIAL, verifiableCredentialList);

		return fromJsonLdObject(jsonLdObject, validate);
	}

	public static VerifiablePresentation fromJwtVerifiableCredential(JwtVerifiableCredential jwtVerifiableCredential) {

		return fromJwtVerifiableCredential(jwtVerifiableCredential, true);
	}

	public LinkedHashMap<String, Object> getJsonLdObject() {

		return this.jsonLdObject;
	}

	public String toPrettyJsonString() throws JsonGenerationException, IOException {

		return JsonUtils.toPrettyString(this.jsonLdObject);
	}

	public String toJsonString() throws JsonGenerationException, IOException {

		return JsonUtils.toString(this.jsonLdObject);
	}

	/*
	 * Validation
	 */

	public void validate() throws IllegalStateException {

	}
}
