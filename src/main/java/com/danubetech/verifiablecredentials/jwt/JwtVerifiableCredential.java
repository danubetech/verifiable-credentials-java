package com.danubetech.verifiablecredentials.jwt;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.LinkedHashMap;

import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.NumericDate;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.lang.JoseException;

import com.danubetech.verifiablecredentials.VerifiableCredential;

public class JwtVerifiableCredential {

	public static final String JWT_CLAIM_VC = "vc";

	private final JwtClaims payload;
	private final VerifiableCredential payloadVerifiableCredential;

	private String compactSerialization;

	private JwtVerifiableCredential(JwtClaims payload, VerifiableCredential payloadVerifiableCredential, String compactSerialization) {

		if (payload == null) throw new NullPointerException();
		if (payloadVerifiableCredential == null) throw new NullPointerException();

		this.payload = payload;
		this.payloadVerifiableCredential = payloadVerifiableCredential;
		this.compactSerialization = compactSerialization;
	}

	public static JwtVerifiableCredential fromJwt(String jwt, String algorithm, PublicKey publicKey, boolean doValidate) throws JoseException, GeneralSecurityException, InvalidJwtException {

		boolean validate;

		JsonWebSignature jws = new JsonWebSignature();
		jws.setAlgorithmConstraints(new AlgorithmConstraints(AlgorithmConstraints.ConstraintType.WHITELIST, algorithm));
		jws.setCompactSerialization(jwt);

		if (doValidate) {

			jws.setKey(publicKey);
			validate = jws.verifySignature();
			if (! validate) throw new GeneralSecurityException("Invalid signature: " + jwt);

			System.setProperty("org.jose4j.jws.getPayload-skip-verify", "false");
		} else {

			System.setProperty("org.jose4j.jws.getPayload-skip-verify", "true");
		}

		JwtClaims jwtPayload = JwtClaims.parse(jws.getPayload());
		LinkedHashMap<String, Object> jsonLdObject = (LinkedHashMap<String, Object>) jwtPayload.getClaimValue(JWT_CLAIM_VC);
		VerifiableCredential payloadVerifiableCredential = VerifiableCredential.fromJsonLdObject(jsonLdObject, false);

		return new JwtVerifiableCredential(jwtPayload, payloadVerifiableCredential, jwt);
	}

	public static JwtVerifiableCredential fromJwt(String jwt, String algorithm, PublicKey publicKey) throws JoseException, GeneralSecurityException, InvalidJwtException {

		return fromJwt(jwt, algorithm, publicKey, true);
	}

	public static JwtVerifiableCredential fromVerifiableCredential(VerifiableCredential verifiableCredential, String aud) {

		VerifiableCredential payloadVerifiableCredential;

		try {

			payloadVerifiableCredential = VerifiableCredential.fromJsonString(verifiableCredential.toJsonString());
		} catch (IOException ex) {

			throw new RuntimeException(ex.getMessage(), ex);
		}

		JwtClaims payload = new JwtClaims();

		String id = payloadVerifiableCredential.getId();
		if (id != null) {
			payload.setJwtId(id);
			payloadVerifiableCredential.setId(null);
		}

		String credentialSubject = payloadVerifiableCredential.getCredentialSubject();
		if (credentialSubject != null) {
			payload.setSubject(credentialSubject);
			payloadVerifiableCredential.setCredentialSubject(null);
		}

		String issuer = payloadVerifiableCredential.getIssuer();
		if (issuer != null) {
			payload.setIssuer(issuer);
			payloadVerifiableCredential.setIssuer(null);
		}

		Date issuanceDate = payloadVerifiableCredential.getIssuanceDate();
		if (issuanceDate != null) {
			payload.setNotBefore(NumericDate.fromMilliseconds(issuanceDate.getTime()));
			payloadVerifiableCredential.setIssuanceDate(null);
		}

		Date expirationDate = payloadVerifiableCredential.getExpirationDate();
		if (expirationDate != null) {
			payload.setExpirationTime(NumericDate.fromMilliseconds(expirationDate.getTime()));
			payloadVerifiableCredential.setExpirationDate(null);
		}

		if (aud != null) {

			payload.setAudience(aud);
		}

		payload.setClaim(JWT_CLAIM_VC, payloadVerifiableCredential.getJsonLdObject());

		return new JwtVerifiableCredential(payload, payloadVerifiableCredential, null);
	}

	public static JwtVerifiableCredential fromVerifiableCredential(VerifiableCredential verifiableCredential) {

		return fromVerifiableCredential(verifiableCredential, null);
	}

	public JwtClaims getPayload() {

		return this.payload;
	}

	public VerifiableCredential getPayloadVerifiableCredential() {

		return this.payloadVerifiableCredential;
	}

	public String getCompactSerialization() {

		return this.compactSerialization;
	}

	public String toJwt(String algorithm, PrivateKey privateKey) throws JoseException {

		String payload = this.getPayload().toJson();

		JsonWebSignature jws = new JsonWebSignature();
		jws.setAlgorithmHeaderValue(algorithm);
		jws.setPayload(payload);

		jws.setKey(privateKey);

		this.compactSerialization = jws.getCompactSerialization();
		return compactSerialization;
	}

	public VerifiableCredential toVerifiableCredential() throws MalformedClaimException {

		VerifiableCredential verifiableCredential;

		try {

			verifiableCredential = VerifiableCredential.fromJsonString(this.getPayloadVerifiableCredential().toJsonString(), false);
		} catch (IOException ex) {

			throw new RuntimeException(ex.getMessage(), ex);
		}

		JwtClaims payload = this.getPayload();

		String jwtId = payload.getJwtId();
		if (jwtId != null) {
			verifiableCredential.setId(jwtId);
		}

		String subject = payload.getSubject();
		if (subject != null) {
			verifiableCredential.setCredentialSubject(subject);
		}

		String issuer = payload.getIssuer();
		if (issuer != null) {
			verifiableCredential.setIssuer(issuer);
		}

		NumericDate notBefore = payload.getNotBefore();
		if (notBefore != null) {
			verifiableCredential.setIssuanceDate(new Date(notBefore.getValueInMillis()));
		}

		NumericDate expirationTime = payload.getExpirationTime();
		if (expirationTime != null) {
			verifiableCredential.setExpirationDate(new Date(expirationTime.getValueInMillis()));
		}

		return verifiableCredential;
	}
}
