package com.danubetech.verifiablecredentials;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TimeZone;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.github.jsonldjava.core.JsonLdConsts;
import com.github.jsonldjava.utils.JsonUtils;

import info.weboftrust.ldsignatures.LdSignature;

public class VerifiableCredential {

	public static final String JSONLD_CONTEXT_CREDENTIALS = "https://www.w3.org/2018/credentials/v1";
	public static final String JSONLD_CONTEXT_CREDENTIALS_NO_WWW = "https://w3.org/2018/credentials/v1";
	public static final String JSONLD_TYPE_VERIFIABLE_CREDENTIAL = "VerifiableCredential";

	public static final String JSONLD_TERM_ID = "id";
	public static final String JSONLD_TERM_TYPE = "type";
	public static final String JSONLD_TERM_ISSUER = "issuer";
	public static final String JSONLD_TERM_ISSUANCE_DATE = "issuanceDate";
	public static final String JSONLD_TERM_EXPIRATION_DATE = "expirationDate";

	public static final String JSONLD_TERM_CREDENTIAL_SUBJECT = "credentialSubject";

	public static final SimpleDateFormat DATE_FORMAT;
	public static final SimpleDateFormat DATE_FORMAT_MILLIS;

	private final LinkedHashMap<String, Object> jsonLdObject;

	static {

		DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));

		DATE_FORMAT_MILLIS = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'");
		DATE_FORMAT_MILLIS.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

	private VerifiableCredential(LinkedHashMap<String, Object> jsonLdObject, boolean validate) { 

		this.jsonLdObject = jsonLdObject;

		if (validate) this.validate();
	}

	public VerifiableCredential() {

		ArrayList<Object> contextList = new ArrayList<Object> ();
		contextList.add(JSONLD_CONTEXT_CREDENTIALS);

		ArrayList<String> typeList = new ArrayList<String> ();
		typeList.add(JSONLD_TYPE_VERIFIABLE_CREDENTIAL);

		LinkedHashMap<String, Object> credentialSubjectMap = new LinkedHashMap<String, Object> ();

		this.jsonLdObject = new LinkedHashMap<String, Object> ();
		this.jsonLdObject.put(JsonLdConsts.CONTEXT, contextList);
		this.jsonLdObject.put(JSONLD_TERM_TYPE, typeList);
		this.jsonLdObject.put(JSONLD_TERM_CREDENTIAL_SUBJECT, credentialSubjectMap);
	}

	public static VerifiableCredential fromJsonLdObject(LinkedHashMap<String, Object> jsonLdObject, boolean validate) {

		return new VerifiableCredential(jsonLdObject, validate);
	}

	public static VerifiableCredential fromJsonLdObject(LinkedHashMap<String, Object> jsonLdObject) {

		return fromJsonLdObject(jsonLdObject, true);
	}

	@SuppressWarnings("unchecked")
	public static VerifiableCredential fromJsonString(String jsonString, boolean validate) throws JsonParseException, IOException {

		LinkedHashMap<String, Object> jsonLdObject = (LinkedHashMap<String, Object>) JsonUtils.fromString(jsonString);

		return fromJsonLdObject(jsonLdObject, validate);
	}

	public static VerifiableCredential fromJsonString(String jsonString) throws JsonParseException, IOException {

		return fromJsonString(jsonString, true);
	}

	public LinkedHashMap<String, Object> getJsonLdObject() {

		return this.jsonLdObject;
	}

	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, Object> getJsonLdCredentialSubject(LinkedHashMap<String, Object> jsonLdObject) {

		return (LinkedHashMap<String, Object>) jsonLdObject.get(JSONLD_TERM_CREDENTIAL_SUBJECT);
	}

	public LinkedHashMap<String, Object> getJsonLdCredentialSubject() {

		return getJsonLdCredentialSubject(this.getJsonLdObject());
	}

	public LdSignature getLdSignature() {

		return LdSignature.getFromJsonLdObject(this.getJsonLdObject());
	}

	public String getId() {

		Object object = this.jsonLdObject.get(JSONLD_TERM_ID);
		if (object == null) return null;

		if (object instanceof URI) return ((URI) object).toString();
		if (object instanceof String) return (String) object;

		throw new IllegalStateException("Invalid object for '" + JSONLD_TERM_ID + "': " + object);
	}

	public void setId(String id) {

		if (id == null)
			this.jsonLdObject.remove(JSONLD_TERM_ID);
		else
			this.jsonLdObject.put(JSONLD_TERM_ID, id);
	}

	public String getCredentialSubject() {

		Object object = this.getJsonLdCredentialSubject().get(JSONLD_TERM_ID);
		if (object == null) return null;

		if (object instanceof URI) return ((URI) object).toString();
		if (object instanceof String) return (String) object;

		throw new IllegalStateException("Invalid object for '" + JSONLD_TERM_ID + "': " + object);
	}

	public void setCredentialSubject(String subject) {

		if (subject == null)
			this.getJsonLdCredentialSubject().remove(JSONLD_TERM_ID);
		else
			this.getJsonLdCredentialSubject().put(JSONLD_TERM_ID, subject);
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

	public String getIssuer() {

		Object object = this.jsonLdObject.get(JSONLD_TERM_ISSUER);
		if (object instanceof URI) return ((URI) object).toString();
		if (object instanceof String) return (String) object;
		return null;
	}

	public void setIssuer(String issuer) {

		if (issuer == null)
			this.jsonLdObject.remove(JSONLD_TERM_ISSUER);
		else
			this.jsonLdObject.put(JSONLD_TERM_ISSUER, issuer);
	}

	public Date getIssuanceDate() {

		String issuanceDateString = (String) this.jsonLdObject.get(JSONLD_TERM_ISSUANCE_DATE);
		if (issuanceDateString == null) return null;
		try {
			return DATE_FORMAT.parse(issuanceDateString);
		} catch (ParseException ex) {
			try {
				return DATE_FORMAT_MILLIS.parse(issuanceDateString);
			} catch (ParseException ex2) {
				throw new RuntimeException(ex.getMessage(), ex);
			}
		}
	}

	public void setIssuanceDate(Date issuanceDate) {

		if (issuanceDate == null)
			this.jsonLdObject.remove(JSONLD_TERM_ISSUANCE_DATE);
		else
			this.jsonLdObject.put(JSONLD_TERM_ISSUANCE_DATE, DATE_FORMAT.format(issuanceDate));
	}

	public Date getExpirationDate() {

		String expirationDateString = (String) this.jsonLdObject.get(JSONLD_TERM_EXPIRATION_DATE);
		if (expirationDateString == null) return null;
		try {
			return DATE_FORMAT.parse(expirationDateString);
		} catch (ParseException ex) {
			try {
				return DATE_FORMAT_MILLIS.parse(expirationDateString);
			} catch (ParseException ex2) {
				throw new RuntimeException(ex.getMessage(), ex);
			}
		}
	}

	public void setExpirationDate(Date expirationDate) {

		if (expirationDate == null)
			this.jsonLdObject.remove(JSONLD_TERM_EXPIRATION_DATE);
		else
			this.jsonLdObject.put(JSONLD_TERM_EXPIRATION_DATE, DATE_FORMAT.format(expirationDate));
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

		validateRun(() -> { validateTrue(this.getContext().size() > 0); }, "Bad or missing '@context'.");
		validateRun(() -> { validateTrue(this.getType().size() > 0); }, "Bad or missing '@type'.");
		validateRun(() -> { validateTrue(this.getIssuer() != null); }, "Bad or missing 'issuer'.");
		validateRun(() -> { validateTrue(this.getIssuanceDate() != null); }, "Bad or missing 'issuanceDate'.");
		validateRun(() -> { this.getExpirationDate(); }, "Bad 'expirationDate'.");
		validateRun(() -> { validateTrue(this.getCredentialSubject() != null); }, "Bad or missing 'credentialSubject'.");

		validateRun(() -> { if (this.getId() != null) validateUrl(this.getId()); }, "'@id' must be a valid URI.");
		validateRun(() -> { validateUrl(this.getIssuer()); }, "'issuer' must be a valid URI.");
		validateRun(() -> { validateTrue(JSONLD_CONTEXT_CREDENTIALS.equals(this.getContext().get(0)) || JSONLD_CONTEXT_CREDENTIALS_NO_WWW.equals(this.getContext().get(0))); }, "First value of @context must be https://www.w3.org/2018/credentials/v1: " + this.getContext().get(0));
		validateRun(() -> { validateUrl(this.getContext().get(0)); }, "@context must be a valid URI: " + this.getContext().get(0));
		validateRun(() -> { validateTrue(this.getType().contains(JSONLD_TYPE_VERIFIABLE_CREDENTIAL)); }, "@type must contain VerifiableCredential: " + this.getType());
	}

	/*
	 * Object methods
	 */

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jsonLdObject == null) ? 0 : jsonLdObject.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VerifiableCredential other = (VerifiableCredential) obj;
		if (jsonLdObject == null) {
			if (other.jsonLdObject != null)
				return false;
		} else if (!jsonLdObject.equals(other.jsonLdObject))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "VerifiableCredential [jsonLdObject=" + jsonLdObject + "]";
	}
}
