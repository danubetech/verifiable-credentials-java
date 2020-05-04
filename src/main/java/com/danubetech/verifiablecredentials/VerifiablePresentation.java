package com.danubetech.verifiablecredentials;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import com.danubetech.verifiablecredentials.jwt.JwtVerifiableCredential;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.github.jsonldjava.core.JsonLdConsts;
import com.github.jsonldjava.utils.JsonUtils;

import info.weboftrust.ldsignatures.LdSignature;

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

		ArrayList<Object> contextList = new ArrayList<Object> ();
		contextList.add(JSONLD_CONTEXT_CREDENTIALS);

		ArrayList<String> typeList = new ArrayList<String> ();
		typeList.add(JSONLD_TYPE_VERIFIABLE_PRESENTATION);

		ArrayList<String> verifiableCredential = new ArrayList<String> ();

		this.jsonLdObject = new LinkedHashMap<String, Object> ();
		this.jsonLdObject.put(JsonLdConsts.CONTEXT, contextList);
		this.jsonLdObject.put(JSONLD_TERM_TYPE, typeList);
		this.jsonLdObject.put(JSONLD_TERM_VERIFIABLE_CREDENTIAL, verifiableCredential);
	}

	public static VerifiablePresentation fromJsonLdObject(LinkedHashMap<String, Object> jsonLdObject, boolean validate) {

		return new VerifiablePresentation(jsonLdObject, validate);
	}

	public static VerifiablePresentation fromJsonLdObject(LinkedHashMap<String, Object> jsonLdObject) {

		return fromJsonLdObject(jsonLdObject, true);
	}

	@SuppressWarnings("unchecked")
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

	@SuppressWarnings("unchecked")
	public VerifiableCredential getVerifiableCredential() {

		Object verifiableCredentialObject = this.getJsonLdObject().get(JSONLD_TERM_VERIFIABLE_CREDENTIAL);

		LinkedHashMap<String, Object> jsonLdObject = null;

		if (verifiableCredentialObject instanceof LinkedHashMap) {

			jsonLdObject = (LinkedHashMap<String, Object>) verifiableCredentialObject;
		} else if (verifiableCredentialObject instanceof List) {

			List<?> verifiableCredentialList = (List<?>) verifiableCredentialObject;

			if (verifiableCredentialList.size() == 1) {

				jsonLdObject = (LinkedHashMap<String, Object>) verifiableCredentialList.get(0);
			}
		}

		return jsonLdObject == null ? null : VerifiableCredential.fromJsonLdObject(jsonLdObject);
	}

	public LdSignature getLdSignature() {

		return LdSignature.getFromJsonLdObject(this.getJsonLdObject());
	}

	@SuppressWarnings("unchecked")
	public List<String> getContext() {

		return (List<String>) this.jsonLdObject.get(JsonLdConsts.CONTEXT);
	}

	public void setContext(List<String> context) {

		if (context == null)
			this.jsonLdObject.remove(JsonLdConsts.CONTEXT);
		else
			this.jsonLdObject.put(JsonLdConsts.CONTEXT, context);
	}

	@SuppressWarnings("unchecked")
	public List<String> getType() {

		Object object = this.jsonLdObject.get(JSONLD_TERM_TYPE);
		if (object == null) return null;

		if (object instanceof List) return (List<String>) object;
		if (object instanceof String) return Collections.singletonList((String) object);

		throw new IllegalStateException("Invalid object for '" + JSONLD_TERM_TYPE + "': " + object);
	}

	public void setType(List<String> type) {

		if (type == null)
			this.jsonLdObject.remove(JSONLD_TERM_TYPE);
		else
			this.jsonLdObject.put(JSONLD_TERM_TYPE, type);
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

	private static void validateTrue(boolean valid) throws IllegalStateException {

		if (! valid) throw new IllegalStateException();
	}

	private static void validateUrl(String uri) {

		try {

			if (! new URI(uri).isAbsolute()) throw new URISyntaxException("Not absolute.", uri);
		} catch (URISyntaxException ex) {

			throw new RuntimeException(ex.getMessage());
		}
	}

	private static void validateRun(Runnable runnable, String message) throws IllegalStateException {

		try {

			runnable.run();
		} catch (Exception ex) {

			throw new IllegalStateException(message);
		}
	}

	public void validate() throws IllegalStateException {

		validateRun(() -> { validateTrue(this.getJsonLdObject() != null); }, "Bad or missing verifiable presentation.");
		validateRun(() -> { validateTrue(this.getVerifiableCredential() != null); }, "Bad or missing 'verifiableCredential'.");

		validateRun(() -> { validateTrue(this.getContext().size() > 0); }, "Bad or missing '@context'.");
		validateRun(() -> { validateTrue(this.getType().size() > 0); }, "Bad or missing '@type'.");

		validateRun(() -> { validateTrue(JSONLD_CONTEXT_CREDENTIALS.equals(this.getContext().get(0)) || JSONLD_CONTEXT_CREDENTIALS_NO_WWW.equals(this.getContext().get(0))); }, "First value of @context must be https://www.w3.org/2018/credentials/v1: " + this.getContext().get(0));
		validateRun(() -> { validateUrl(this.getContext().get(0)); }, "@context must be a valid URI: " + this.getContext().get(0));
		validateRun(() -> { validateTrue(this.getType().contains(JSONLD_TYPE_VERIFIABLE_PRESENTATION)); }, "@type must contain VerifiablePresentation: " + this.getType());
	}

	/*
	 * Object methods
	 */

	@Override
	public String toString() {

		try {

			return this.toJsonString();
		} catch (IOException ex) {

			return super.toString();
		}
	}
}
