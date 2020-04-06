package com.danubetech.verifiablecredentials.jwt;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.util.Date;

import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.fasterxml.jackson.core.JsonParseException;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import net.minidev.json.JSONObject;

public class JwtVerifiableCredential extends JwtWrappingObject<VerifiableCredential> {

	public static final String JWT_CLAIM_VC = "vc";

	private JwtVerifiableCredential(JWTClaimsSet payload, VerifiableCredential payloadObject, JWSObject jwsObject, String compactSerialization) {

		super(payload, payloadObject, jwsObject, compactSerialization);
	}

	public static JwtVerifiableCredential fromCompactSerialization(String compactSerialization) throws GeneralSecurityException, ParseException, JOSEException, JsonParseException, IOException {

		SignedJWT signedJWT = SignedJWT.parse(compactSerialization);

		JWTClaimsSet payload = signedJWT.getJWTClaimsSet();
		JSONObject jsonLdObject = (JSONObject) payload.getClaims().get(JWT_CLAIM_VC);
		VerifiableCredential payloadVerifiableCredential = VerifiableCredential.fromJsonString(jsonLdObject.toJSONString(), false);

		return new JwtVerifiableCredential(payload, payloadVerifiableCredential, signedJWT, compactSerialization);
	}

	public static JwtVerifiableCredential fromVerifiableCredential(VerifiableCredential verifiableCredential, String aud) {

		VerifiableCredential payloadVerifiableCredential;

		try {

			payloadVerifiableCredential = VerifiableCredential.fromJsonString(verifiableCredential.toJsonString());
		} catch (IOException ex) {

			throw new RuntimeException(ex.getMessage(), ex);
		}

		JWTClaimsSet.Builder payloadBuilder = new JWTClaimsSet.Builder();

		String id = payloadVerifiableCredential.getId();
		if (id != null) {
			payloadBuilder.jwtID(id);
			payloadVerifiableCredential.setId(null);
		}

		String credentialSubject = payloadVerifiableCredential.getCredentialSubject();
		if (credentialSubject != null) {
			payloadBuilder.subject(credentialSubject);
			payloadVerifiableCredential.setCredentialSubject(null);
		}

		String issuer = payloadVerifiableCredential.getIssuer();
		if (issuer != null) {
			payloadBuilder.issuer(issuer);
			payloadVerifiableCredential.setIssuer(null);
		}

		Date issuanceDate = payloadVerifiableCredential.getIssuanceDate();
		if (issuanceDate != null) {
			payloadBuilder.notBeforeTime(issuanceDate);
			payloadVerifiableCredential.setIssuanceDate(null);
		}

		Date expirationDate = payloadVerifiableCredential.getExpirationDate();
		if (expirationDate != null) {
			payloadBuilder.expirationTime(expirationDate);
			payloadVerifiableCredential.setExpirationDate(null);
		}

		if (aud != null) {

			payloadBuilder.audience(aud);
		}

		payloadBuilder.claim(JWT_CLAIM_VC, payloadVerifiableCredential.getJsonLdObject());

		JWTClaimsSet payload = payloadBuilder.build();

		return new JwtVerifiableCredential(payload, payloadVerifiableCredential, null, null);
	}

	public static JwtVerifiableCredential fromVerifiableCredential(VerifiableCredential verifiableCredential) {

		return fromVerifiableCredential(verifiableCredential, null);
	}

	public VerifiableCredential toVerifiableCredential() {

		VerifiableCredential verifiableCredential;

		try {

			verifiableCredential = VerifiableCredential.fromJsonString(this.getPayloadObject().toJsonString(), false);
		} catch (IOException ex) {

			throw new RuntimeException(ex.getMessage(), ex);
		}

		JWTClaimsSet payload = this.getPayload();

		String jwtId = payload.getJWTID();
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

		Date notBeforeTime = payload.getNotBeforeTime();
		if (notBeforeTime != null) {
			verifiableCredential.setIssuanceDate(notBeforeTime);
		}

		Date expirationTime = payload.getExpirationTime();
		if (expirationTime != null) {
			verifiableCredential.setExpirationDate(expirationTime);
		}

		return verifiableCredential;
	}
}
