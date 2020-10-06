package com.danubetech.verifiablecredentials;


import com.danubetech.verifiablecredentials.jsonld.VerifiableCredentialContexts;
import foundation.identity.jsonld.JsonLDObject;
import foundation.identity.jsonld.JsonLDUtils;

import javax.json.JsonObject;
import javax.json.JsonValue;
import java.util.Map;

public class CredentialSubject extends JsonLDObject {

	private CredentialSubject() {
		super(VerifiableCredentialContexts.DOCUMENT_LOADER);
	}

	public CredentialSubject(JsonObject jsonObject) {
		super(VerifiableCredentialContexts.DOCUMENT_LOADER, jsonObject);
	}

	/*
	 * Factory methods
	 */

	public static class Builder extends JsonLDObject.Builder<Builder, CredentialSubject> {

		private Map<String, JsonValue> claims;

		public Builder() {
			super(new CredentialSubject());
		}

		@Override
		public CredentialSubject build() {

			super.build();

			// add JSON-LD properties
			if (this.claims != null) JsonLDUtils.jsonLdAddAllJsonValueMap(this.jsonLDObject.getJsonObjectBuilder(), this.claims);

			return this.jsonLDObject;
		}

		public Builder claims(Map<String, JsonValue> claims) {
			this.claims = claims;
			return this;
		}
	}

	public static Builder builder() {

		return new Builder();
	}

	/*
	 * Getters
	 */

	public Map<String, JsonValue> getClaims() {

		return JsonLDUtils.jsonLdGetAsJsonValueMap(this.getJsonObject());
	}
}