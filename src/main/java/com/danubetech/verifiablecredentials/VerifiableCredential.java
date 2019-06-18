package com.danubetech.verifiablecredentials;

import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
	public static final String JSONLD_TYPE_VERIFIABLE_CREDENTIAL = "VerifiableCredential";

	public static final String JSONLD_TERM_ID = "id";
	public static final String JSONLD_TERM_TYPE = "type";
	public static final String JSONLD_TERM_ISSUER = "issuer";
	public static final String JSONLD_TERM_ISSUANCE_DATE = "issuanceDate";
	public static final String JSONLD_TERM_EXPIRATION_DATE = "expirationDate";

	public static final String JSONLD_TERM_CREDENTIAL_SUBJECT = "credentialSubject";

	public static final SimpleDateFormat DATE_FORMAT;

	private final LinkedHashMap<String, Object> jsonLdObject;

	static {

		DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

	private VerifiableCredential(LinkedHashMap<String, Object> jsonLdObject) { 

		this.jsonLdObject = jsonLdObject;

		this.validate();
	}

	public VerifiableCredential() {

		ArrayList<Object> context = new ArrayList<Object> ();
		context.add(JSONLD_CONTEXT_CREDENTIALS);

		ArrayList<String> type = new ArrayList<String> ();
		type.add(JSONLD_TYPE_VERIFIABLE_CREDENTIAL);

		LinkedHashMap<String, Object> credentialSubject = new LinkedHashMap<String, Object> ();

		this.jsonLdObject = new LinkedHashMap<String, Object> ();
		this.jsonLdObject.put(JsonLdConsts.CONTEXT, context);
		this.jsonLdObject.put(JSONLD_TERM_TYPE, type);
		this.jsonLdObject.put(JSONLD_TERM_CREDENTIAL_SUBJECT, credentialSubject);
	}

	public static VerifiableCredential fromJsonLdObject(LinkedHashMap<String, Object> jsonLdObject) {

		return new VerifiableCredential(jsonLdObject);
	}

	public static VerifiableCredential fromJsonString(String jsonString) throws JsonParseException, IOException {

		return fromJsonLdObject((LinkedHashMap<String, Object>) JsonUtils.fromString(jsonString));
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
		if (object instanceof URI) return ((URI) object).toString();
		if (object instanceof String) return (String) object;
		return null;
	}

	public void setId(String id) {

		if (id == null)
			this.jsonLdObject.remove(JSONLD_TERM_ID);
		else
			this.jsonLdObject.put(JSONLD_TERM_ID, id);
	}

	public String getCredentialSubject() {

		Object object = this.getJsonLdCredentialSubject().get(JSONLD_TERM_ID);
		if (object instanceof URI) return ((URI) object).toString();
		if (object instanceof String) return (String) object;
		return null;
	}

	public void setCredentialSubject(String subject) {

		if (subject == null)
			this.getJsonLdCredentialSubject().remove(JSONLD_TERM_ID);
		else
			this.getJsonLdCredentialSubject().put(JSONLD_TERM_ID, subject);
	}

	public List<String> getContext() {

		return (List<String>) this.jsonLdObject.get(JsonLdConsts.CONTEXT);
	}

	public void setContext(List<String> context) {

		if (context == null)
			this.jsonLdObject.remove(JsonLdConsts.CONTEXT);
		else
			this.jsonLdObject.put(JsonLdConsts.CONTEXT, context);
	}

	public List<String> getType() {

		return (List<String>) this.jsonLdObject.get(JSONLD_TERM_TYPE);
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

		try {
			String issuanceDateString = (String) this.jsonLdObject.get(JSONLD_TERM_ISSUANCE_DATE);
			if (issuanceDateString == null) return null;
			return DATE_FORMAT.parse(issuanceDateString);
		} catch (ParseException ex) {
			throw new RuntimeException(ex.getMessage(), ex);
		}
	}

	public void setIssuanceDate(Date issuanceDate) {

		if (issuanceDate == null)
			this.jsonLdObject.remove(JSONLD_TERM_ISSUANCE_DATE);
		else
			this.jsonLdObject.put(JSONLD_TERM_ISSUANCE_DATE, DATE_FORMAT.format(issuanceDate));
	}

	public Date getExpirationDate() {

		try {
			String expirationDateString = (String) this.jsonLdObject.get(JSONLD_TERM_EXPIRATION_DATE);
			if (expirationDateString == null) return null;
			return DATE_FORMAT.parse(expirationDateString);
		} catch (ParseException ex) {
			throw new RuntimeException(ex.getMessage(), ex);
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

	private static void validateRun(Runnable runnable, String message) throws IllegalStateException {

		try {

			runnable.run();
		} catch (Exception ex) {

			throw new IllegalStateException(message);
		}
	}

	public void validate() throws IllegalStateException {

		validateRun(() -> { validateTrue(JSONLD_CONTEXT_CREDENTIALS.equals(this.getContext().get(0))); }, "First value of @context must be https://www.w3.org/2018/credentials/v1");
		validateRun(() -> { for (String context : this.getContext()) URI.create(context); }, "@context must be a valid URI");
		validateRun(() -> { for (String type : this.getType()) URI.create(type); }, "@type must be a valid URI");
		validateRun(() -> { this.getType().contains(JSONLD_TYPE_VERIFIABLE_CREDENTIAL); }, "@type must contain VerifiableCredential");
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
		return "LdSignature [jsonLdObject=" + jsonLdObject + "]";
	}
}
