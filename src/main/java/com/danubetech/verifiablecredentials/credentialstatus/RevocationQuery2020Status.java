package com.danubetech.verifiablecredentials.credentialstatus;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.github.jsonldjava.core.JsonLdConsts;

public class RevocationQuery2020Status {

	public static final LinkedHashMap<String, Object> JSONLD_CONTEXT;

	public static final String JSONLD_TERM_CREDENTIALSTATUS = "credentialStatus";
	public static final String JSONLD_TERM_TYPE = "type";
	public static final String JSONLD_TERM_REVOCATIONQUERY2020STATUS = "RevocationQuery2020Status";
	public static final String JSONLD_TERM_CREDENTIALREFERENCE = "credentialReference";
	public static final String JSONLD_TERM_REVOCATIONSERVICE = "revocationService";

	private final LinkedHashMap<String, Object> jsonLdCredentialStatusObject;

	static {

		JSONLD_CONTEXT = new LinkedHashMap<String, Object> ();
		JSONLD_CONTEXT.put(JSONLD_TERM_REVOCATIONQUERY2020STATUS, "https://danubetech.com/schema/2020/proofs/v1#RevocationQuery2020Status");
		JSONLD_CONTEXT.put(JSONLD_TERM_CREDENTIALREFERENCE, "https://danubetech.com/schema/2020/proofs/v1#credentialReference");
		JSONLD_CONTEXT.put(JSONLD_TERM_REVOCATIONSERVICE, "https://danubetech.com/schema/2020/proofs/v1#revocationService");
	}

	protected RevocationQuery2020Status(LinkedHashMap<String, Object> jsonLdCredentialStatusObject) {

		this.jsonLdCredentialStatusObject = jsonLdCredentialStatusObject;
	}

	public RevocationQuery2020Status() {

		this.jsonLdCredentialStatusObject = new LinkedHashMap<String, Object> ();
	}

	public static RevocationQuery2020Status fromJsonLdObject(LinkedHashMap<String, Object> jsonLdCredentialStatusObject) {

		return new RevocationQuery2020Status(jsonLdCredentialStatusObject);
	}

	public LinkedHashMap<String, Object> getJsonLdCredentialStatusObject() {

		return this.jsonLdCredentialStatusObject;
	}

	@SuppressWarnings("unchecked")
	public static void addContextToJsonLdObject(LinkedHashMap<String, Object> jsonLdObject) {

		Object context = jsonLdObject.get(JsonLdConsts.CONTEXT);
		ArrayList<Object> contexts;

		// add as single value

		if (context == null) {

			jsonLdObject.put(JsonLdConsts.CONTEXT, JSONLD_CONTEXT);
			return;
		}

		// add as array member

		if (context instanceof ArrayList<?>) {

			contexts = (ArrayList<Object>) context;
		} else {

			contexts = new ArrayList<Object> ();
			contexts.add(context);
			jsonLdObject.put(JsonLdConsts.CONTEXT, contexts);
		}

		if (! contexts.contains(JSONLD_CONTEXT)) {

			contexts.add(JSONLD_CONTEXT);
		}
	}

	public static void addToJsonLdObject(LinkedHashMap<String, Object> jsonLdObject, LinkedHashMap<String, Object> jsonLdCredentialStatusObject) {

		Object credentialStatus = jsonLdObject.get(JSONLD_TERM_CREDENTIALSTATUS);

		// add as single value

		if (credentialStatus == null) {

			jsonLdObject.put(JSONLD_TERM_CREDENTIALSTATUS, jsonLdCredentialStatusObject);
			return;
		}

		// add as array member

		ArrayList<Object> proofs;

		if (credentialStatus instanceof ArrayList<?>) {

			proofs = (ArrayList<Object>) credentialStatus;
		} else {

			proofs = new ArrayList<Object> ();
			proofs.add(credentialStatus);
			jsonLdObject.put(JSONLD_TERM_CREDENTIALSTATUS, proofs);
		}

		if (! proofs.contains(jsonLdCredentialStatusObject)) {

			proofs.add(jsonLdCredentialStatusObject);
		}
	}

	public void addToJsonLdObject(LinkedHashMap<String, Object> jsonLdObject, boolean addContext) {

		if (addContext) addContextToJsonLdObject(jsonLdObject);

		addToJsonLdObject(jsonLdObject, this.getJsonLdCredentialStatusObject());
	}

	public void addToJsonLdObject(LinkedHashMap<String, Object> jsonLdObject) {

		this.addToJsonLdObject(jsonLdObject, false);
	}

	public static void removeFromJsonLdObject(LinkedHashMap<String, Object> jsonLdObject) {

		jsonLdObject.remove(JSONLD_TERM_CREDENTIALSTATUS);
	}

	@SuppressWarnings("unchecked")
	public static RevocationQuery2020Status getFromJsonLdObject(LinkedHashMap<String, Object> jsonLdObject) {

		Object jsonLdProofObjectEntry = jsonLdObject.get(JSONLD_TERM_CREDENTIALSTATUS);

		if (jsonLdProofObjectEntry instanceof List) {

			List<LinkedHashMap<String, Object>> jsonLdProofObjectList = (List<LinkedHashMap<String, Object>>) jsonLdProofObjectEntry;

			for (LinkedHashMap<String, Object> jsonLdProofObject : jsonLdProofObjectList) {

				if (JSONLD_TERM_REVOCATIONQUERY2020STATUS.equals(jsonLdProofObject.get("type"))) return RevocationQuery2020Status.fromJsonLdObject(jsonLdProofObject);
			}
		} else if (jsonLdProofObjectEntry instanceof LinkedHashMap) {

			LinkedHashMap<String, Object> jsonLdProofObject = (LinkedHashMap<String, Object>) jsonLdProofObjectEntry;

			if (JSONLD_TERM_REVOCATIONQUERY2020STATUS.equals(jsonLdProofObject.get("type"))) return RevocationQuery2020Status.fromJsonLdObject(jsonLdProofObject);
		}

		return null;
	}

	public String getType() {
		return (String) this.jsonLdCredentialStatusObject.get(JSONLD_TERM_TYPE);
	}

	public void setType(String type) {
		this.jsonLdCredentialStatusObject.put(JSONLD_TERM_TYPE, type);
	}

	public String getCredentialReference() {
		return (String) this.jsonLdCredentialStatusObject.get(JSONLD_TERM_CREDENTIALREFERENCE);
	}

	public void setCredentialReference(String credentialReference) {
		this.jsonLdCredentialStatusObject.put(JSONLD_TERM_CREDENTIALREFERENCE, credentialReference);
	}

	public String getRevocationService() {
		return (String) this.jsonLdCredentialStatusObject.get(JSONLD_TERM_REVOCATIONSERVICE);
	}

	public void setRevocationService(String revocationService) {
		this.jsonLdCredentialStatusObject.put(JSONLD_TERM_REVOCATIONSERVICE, revocationService);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jsonLdCredentialStatusObject == null) ? 0 : jsonLdCredentialStatusObject.hashCode());
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
		RevocationQuery2020Status other = (RevocationQuery2020Status) obj;
		if (jsonLdCredentialStatusObject == null) {
			if (other.jsonLdCredentialStatusObject != null)
				return false;
		} else if (!jsonLdCredentialStatusObject.equals(other.jsonLdCredentialStatusObject))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RevocationQuery2020Status [jsonLdObject=" + jsonLdCredentialStatusObject + "]";
	}
}
