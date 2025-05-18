package com.danubetech.verifiablecredentials.verifier.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Reader;
import java.util.LinkedHashMap;
import java.util.Map;

@JsonPropertyOrder({ "verifiableCredential", "verifiablePresentation", "options" })
public class VerifyRequest {

	public static final String MIME_TYPE = "application/json";

	private static final ObjectMapper objectMapper = new ObjectMapper();

	@JsonProperty
	private Map<String, Object> verifiableCredential;

	@JsonProperty
	private Map<String, Object> verifiablePresentation;

	@JsonProperty
	private Map<String, Object> options;

	public VerifyRequest() {
	}

	public VerifyRequest(Map<String, Object> verifiableCredential, Map<String, Object> verifiablePresentation, Map<String, Object> options) {
		this.verifiableCredential = verifiableCredential;
		this.verifiablePresentation = verifiablePresentation;
		this.options = options;
	}

	/*
	 * Factory methods
	 */

	@JsonCreator
	public static VerifyRequest build(@JsonProperty(value="verifiableCredential", required=true) Map<String, Object> verifiableCredential, @JsonProperty(value="verifiablePresentation", required=true) Map<String, Object> verifiablePresentation, @JsonProperty(value="options", required=true) Map<String, Object> options) {
		return new VerifyRequest(verifiableCredential, verifiablePresentation, options);
	}

	@JsonIgnore
	public static VerifyRequest build() {
		return new VerifyRequest(null, null, new LinkedHashMap<String, Object>());
	}

	/*
	 * Serialization
	 */

	public static VerifyRequest fromJson(String json) throws JsonParseException, JsonMappingException, IOException {
		return objectMapper.readValue(json, VerifyRequest.class);
	}

	public static VerifyRequest fromJson(Reader reader) throws JsonParseException, JsonMappingException, IOException {
		return objectMapper.readValue(reader, VerifyRequest.class);
	}

	public Map<String, Object> toMap() {
		return objectMapper.convertValue(this, LinkedHashMap.class);
	}

	public String toJson() throws JsonProcessingException {
		return objectMapper.writeValueAsString(this);
	}

	/*
	 * Getters and setters
	 */

	public Map<String, Object> getVerifiableCredential() {
		return verifiableCredential;
	}

	public void setVerifiableCredential(Map<String, Object> verifiableCredential) {
		this.verifiableCredential = verifiableCredential;
	}

	public Map<String, Object> getVerifiablePresentation() {
		return verifiablePresentation;
	}

	public void setVerifiablePresentation(Map<String, Object> verifiablePresentation) {
		this.verifiablePresentation = verifiablePresentation;
	}

	public Map<String, Object> getOptions() {
		return options;
	}

	public void setOptions(Map<String, Object> options) {
		this.options = options;
	}

	/*
	 * Object methods
	 */

	@Override
	public String toString() {
		try {
			return this.toJson();
		} catch (JsonProcessingException ex) {
			throw new RuntimeException(ex.getMessage(), ex);
		}
	}
}
