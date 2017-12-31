package fi.trustnet.verifiablecredentials;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.github.jsonldjava.core.JsonLdConsts;

public class VerifiableCredential {

	public static final String JSONLD_CONTEXT_CREDENTIALS = "https://w3id.org/credentials/v1";
	public static final String JSONLD_TYPE_CREDENTIAL = "Credential";

	public static final URI URI_TYPE = URI.create("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
	public static final URI URI_ISSUER = URI.create("https://w3id.org/credentials#issuer");
	public static final URI URI_ISSUED = URI.create("https://w3id.org/credentials#issued");

	public static final URI URI_CLAIM = URI.create("https://w3id.org/credentials#claim");

	public static final String JSONLD_TERM_TYPE = "type";
	public static final String JSONLD_TERM_ISSUER = "issuer";
	public static final String JSONLD_TERM_ISSUED = "issued";

	public static final String JSONLD_TERM_CLAIM = "claim";

	private final LinkedHashMap<String, Object> jsonLdObject;

	private VerifiableCredential(LinkedHashMap<String, Object> jsonLdObject) { 

		this.jsonLdObject = jsonLdObject;
	}

	public VerifiableCredential() {

		ArrayList<Object> context = new ArrayList<Object> ();
		context.add(JSONLD_CONTEXT_CREDENTIALS);

		ArrayList<String> type = new ArrayList<String> ();
		type.add(JSONLD_TYPE_CREDENTIAL);

		LinkedHashMap<String, Object> claim = new LinkedHashMap<String, Object> ();

		this.jsonLdObject = new LinkedHashMap<String, Object> ();
		this.jsonLdObject.put(JsonLdConsts.CONTEXT, context);
		this.jsonLdObject.put(JSONLD_TERM_TYPE, type);
		this.jsonLdObject.put(JSONLD_TERM_CLAIM, claim);
	}

	public static VerifiableCredential fromJsonLdObject(LinkedHashMap<String, Object> jsonLdObject) {

		return new VerifiableCredential(jsonLdObject);
	}

	public LinkedHashMap<String, Object> getJsonLdObject() {

		return this.jsonLdObject;
	}

	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, Object> getJsonLdClaimsObject(LinkedHashMap<String, Object> jsonLdObject) {

		return (LinkedHashMap<String, Object>) jsonLdObject.get(JSONLD_TERM_CLAIM);
	}

	public LinkedHashMap<String, Object> getJsonLdClaimsObject() {

		return getJsonLdClaimsObject(this.getJsonLdObject());
	}

	public List<Object> getContext() {
		return (List<Object>) this.jsonLdObject.get(JsonLdConsts.CONTEXT);
	}

	public void setContext(List<Object> context) {
		this.jsonLdObject.put(JsonLdConsts.CONTEXT, context);
	}

	public List<String> getType() {
		return (List<String>) this.jsonLdObject.get(JSONLD_TERM_TYPE);
	}

	public void setType(List<String> type) {
		this.jsonLdObject.put(JSONLD_TERM_TYPE, type);
	}

	public URI getIssuer() {
		return (URI) this.jsonLdObject.get(JSONLD_TERM_ISSUER);
	}

	public void setIssuer(URI issuer) {
		this.jsonLdObject.put(JSONLD_TERM_ISSUER, issuer);
	}

	public String getIssued() {
		return (String) this.jsonLdObject.get(JSONLD_TERM_ISSUED);
	}

	public void setIssued(String issued) {
		this.jsonLdObject.put(JSONLD_TERM_ISSUED, issued);
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
