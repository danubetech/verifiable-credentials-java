package com.danubetech.verifiablecredentials.verifier.result;

import com.danubetech.verifiablecredentials.verifier.VerifyingException;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@JsonPropertyOrder({ "verified", "issuer", "subject", "claims", "verifierMetadata", "documentMetadata" })
public class VerifyResult {

	private static final Logger log = LoggerFactory.getLogger(VerifyResult.class);

	public static final String MIME_TYPE = "application/json";

	private static final ObjectMapper objectMapper = new ObjectMapper();

	@JsonProperty
	private Boolean verified;

	@JsonProperty
	private String issuer;

	@JsonProperty
	private String subject;

	@JsonProperty
	private Map<String, Object> claims;

	@JsonProperty
	private Map<String, Object> verifierMetadata;

	@JsonProperty
	private Map<String, Object> documentMetadata;

	private VerifyResult(Boolean verified, String issuer, String subject, Map<String, Object> claims, Map<String, Object> verifierMetadata, Map<String, Object> documentMetadata) {
		this.verified = verified;
		this.issuer = issuer;
		this.subject = subject;
		this.claims = claims;
		this.verifierMetadata = verifierMetadata;
		this.documentMetadata = documentMetadata;
	}

	/*
	 * Factory methods
	 */

	@JsonCreator
	public static VerifyResult build(@JsonProperty(value="verified", required=true) Boolean verified, @JsonProperty(value="issuer", required=true) String issuer, @JsonProperty(value="subject", required=true) String subject, @JsonProperty(value="claims", required=true) Map<String, Object> claims, @JsonProperty(value="verifierMetadata", required=true) Map<String, Object> verifierMetadata, @JsonProperty(value="documentMetadata", required=true) Map<String, Object> documentMetadata) {
		return new VerifyResult(verified, issuer, subject, claims, verifierMetadata, documentMetadata);
	}

	@JsonIgnore
	public static VerifyResult build() {
		return new VerifyResult(null, null, null, null, new LinkedHashMap<>(), new LinkedHashMap<>());
	}

	public static VerifyResult toErrorResult(String errorMessage) {
		VerifyResult verifyResult = VerifyResult.build();
		verifyResult.setVerified(Boolean.FALSE);
		verifyResult.addProblem(errorMessage);
		if (log.isDebugEnabled()) log.debug("Created error verify result: " + verifyResult);
		return verifyResult;
	}

	public static VerifyResult toErrorResult(VerifyingException ex) {
		if (ex.getVerifyResult() != null) {
			return ex.getVerifyResult();
		}
		return toErrorResult(ex.getMessage());
	}

	/*
	 * Serialization
	 */

	public static VerifyResult fromJson(String json) throws JsonParseException, JsonMappingException, IOException {
		return objectMapper.readValue(json, VerifyResult.class);
	}

	public static VerifyResult fromJson(Reader reader) throws JsonParseException, JsonMappingException, IOException {
		return objectMapper.readValue(reader, VerifyResult.class);
	}

	public Map<String, Object> toMap() {
		return objectMapper.convertValue(this, LinkedHashMap.class);
	}

	public String toJson() throws JsonProcessingException {
		return objectMapper.writeValueAsString(this);
	}

	/*
	 * Check methods
	 */

	@JsonIgnore
	public Map<String, Object> getChecks() {
		return this.getDocumentMetadata() == null ? null : (Map<String, Object>) this.getDocumentMetadata().get("checks");
	}

	public Map<String, Object> getCheckMetadata(String checkName) {
		if (this.getDocumentMetadata() == null) this.setDocumentMetadata(new LinkedHashMap<>());
		Map<String, Object> checks = (Map<String, Object>) this.getDocumentMetadata().get("checks");
		if (checks == null) { checks = new LinkedHashMap<>(); this.getDocumentMetadata().put("checks", checks); }
		Map<String, Object> checkMetadata = (Map<String, Object>) checks.get(checkName);
		if (checkMetadata == null) { checkMetadata = new LinkedHashMap<>(); checks.put(checkName, checkMetadata); }
		return checkMetadata;
	}

	/*
	 * Error methods
	 */

	@JsonIgnore
	public boolean isErrorResult() {
		return this.getErrorMessage() != null;
	}

	@JsonIgnore
	public String getErrorMessage() {
		List<Map<String, Object>> problems = this.getProblems();
		if (problems == null) return null;
		if (problems.size() == 1) return (String) problems.get(0).get("message");
		return "Multiple problems: " + problems.stream().map((x) -> (String) x.get("message")).collect(Collectors.joining(","));
	}

	/*
	 * Problem/warning methods
	 */

	@JsonIgnore
	public boolean hasProblems() {
		return this.getProblems() != null && this.getProblems().size() > 0;
	}

	@JsonIgnore
	public List<Map<String, Object>> getProblems() {
		return this.getDocumentMetadata() == null ? null : (List<Map<String, Object>>) this.getDocumentMetadata().get("problems");
	}

	@JsonIgnore
	public void addProblem(String message, Map<String, Object> problemMetadata) {
		if (message == null) throw new NullPointerException();
		if (this.getDocumentMetadata() == null) this.setDocumentMetadata(new LinkedHashMap<>());
		List<Map<String, Object>> problems = (List<Map<String, Object>>) this.getDocumentMetadata().get("problems");
		if (problems == null) { problems = new ArrayList<>(); this.getDocumentMetadata().put("problems", problems); }
		Map<String, Object> problem = new LinkedHashMap<>();
		if (message != null) problem.put("message", message);
		if (problemMetadata != null) problem.putAll(problemMetadata);
		problems.add(problem);
	}

	@JsonIgnore
	public void addProblem(String message) {
		this.addProblem(message, null);
	}

	@JsonIgnore
	public boolean hasWarnings() {
		return this.getWarnings() != null && this.getWarnings().size() > 0;
	}

	@JsonIgnore
	public List<Map<String, Object>> getWarnings() {
		return this.getDocumentMetadata() == null ? null : (List<Map<String, Object>>) this.getDocumentMetadata().get("warnings");
	}

	@JsonIgnore
	public void addWarning(String message, Map<String, Object> warningMetadata) {
		if (message == null) throw new NullPointerException();
		if (this.getDocumentMetadata() == null) this.setDocumentMetadata(new LinkedHashMap<>());
		List<Map<String, Object>> warnings = (List<Map<String, Object>>) this.getDocumentMetadata().get("warnings");
		if (warnings == null) { warnings = new ArrayList<>(); this.getDocumentMetadata().put("warnings", warnings); }
		Map<String, Object> warning = new LinkedHashMap<>();
		if (message != null) warning.put("message", message);
		if (warningMetadata != null) warning.putAll(warningMetadata);
		warnings.add(warning);
	}

	@JsonIgnore
	public void addWarning(String message) {
		this.addWarning(message, null);
	}

	/*
	 * Getters and setters
	 */

	@JsonGetter
	public Boolean getVerified() {
		return verified;
	}

	@JsonSetter
	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

	@JsonGetter
	public String getIssuer() {
		return issuer;
	}

	@JsonSetter
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	@JsonGetter
	public String getSubject() {
		return subject;
	}

	@JsonSetter
	public void setSubject(String subject) {
		this.subject = subject;
	}

	@JsonGetter
	public Map<String, Object> getClaims() {
		return claims;
	}

	@JsonSetter
	public void setClaims(Map<String, Object> claims) {
		this.claims = claims;
	}

	@JsonGetter
	public Map<String, Object> getVerifierMetadata() {
		return verifierMetadata;
	}

	@JsonSetter
	public void setVerifierMetadata(Map<String, Object> verifierMetadata) {
		this.verifierMetadata = verifierMetadata;
	}

	@JsonGetter
	public Map<String, Object> getDocumentMetadata() {
		return documentMetadata;
	}

	@JsonSetter
	public void setDocumentMetadata(Map<String, Object> documentMetadata) {
		this.documentMetadata = documentMetadata;
	}

	/*
	 * Object methods
	 */

	@Override
	public String toString() {
		try {
			return this.toJson();
		} catch (JsonProcessingException ex) {
			return ex.getMessage();
		}
	}
}
