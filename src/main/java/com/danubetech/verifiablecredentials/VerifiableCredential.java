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

	public static final String JSONLD_CONTEXT_CREDENTIALS = "https://w3id.org/credentials/v1";
	public static final String JSONLD_TYPE_VERIFIABLE_CREDENTIAL = "VerifiableCredential";

	public static final URI URI_TYPE = URI.create("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
	public static final URI URI_ISSUER = URI.create("https://w3id.org/credentials#issuer");
	public static final URI URI_ISSUED = URI.create("https://w3id.org/credentials#issued");

	public static final URI URI_CLAIM = URI.create("https://w3id.org/credentials#claim");

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

	public URI getId() {

		if (this.jsonLdObject.get(JSONLD_TERM_ID) instanceof URI) return (URI) this.jsonLdObject.get(JSONLD_TERM_ID);
		if (this.jsonLdObject.get(JSONLD_TERM_ID) instanceof String) return URI.create((String) this.jsonLdObject.get(JSONLD_TERM_ID));
		return null;
	}

	public void setId(String id) {

		if (id == null)
			this.jsonLdObject.remove(JSONLD_TERM_ID);
		else
			this.jsonLdObject.put(JSONLD_TERM_ID, id);
	}

	public String getCredentialSubject() {

		return (String) this.getJsonLdCredentialSubject().get(JSONLD_TERM_ID);
	}

	public void setCredentialSubject(String subject) {

		if (subject == null)
			this.getJsonLdCredentialSubject().remove(JSONLD_TERM_ID);
		else
			this.getJsonLdCredentialSubject().put(JSONLD_TERM_ID, subject);
	}

	public List<Object> getContext() {

		return (List<Object>) this.jsonLdObject.get(JsonLdConsts.CONTEXT);
	}

	public void setContext(List<Object> context) {

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

		if (this.jsonLdObject.get(JSONLD_TERM_ISSUER) instanceof URI) return ((URI) this.jsonLdObject.get(JSONLD_TERM_ISSUER)).toString();
		if (this.jsonLdObject.get(JSONLD_TERM_ISSUER) instanceof String) return (String) this.jsonLdObject.get(JSONLD_TERM_ISSUER);
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
