package com.danubetech.verifiablecredentials.jwt;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.Date;

import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.fasterxml.jackson.core.JsonParseException;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import net.minidev.json.JSONObject;

public class JwtVerifiableCredential {

	public static final String JWT_CLAIM_VC = "vc";

	private final JWTClaimsSet payload;
	private final VerifiableCredential payloadVerifiableCredential;

	private String compactSerialization;

	private JwtVerifiableCredential(JWTClaimsSet payload, VerifiableCredential payloadVerifiableCredential, String compactSerialization) {

		if (payload == null) throw new NullPointerException();
		if (payloadVerifiableCredential == null) throw new NullPointerException();

		this.payload = payload;
		this.payloadVerifiableCredential = payloadVerifiableCredential;
		this.compactSerialization = compactSerialization;
	}

	public static JwtVerifiableCredential fromJwt(String jwt, String algorithm, PublicKey publicKey, boolean doValidate) throws GeneralSecurityException, ParseException, JOSEException, JsonParseException, IOException {

		boolean validate;

		SignedJWT signedJWT = SignedJWT.parse(jwt);

		if (doValidate) {

			JWSVerifier verifier = new RSASSAVerifier((RSAPublicKey) publicKey);
			validate = signedJWT.verify(verifier);

			if (! validate) throw new GeneralSecurityException("Invalid signature: " + jwt);
		}

		JWTClaimsSet jwtPayload = signedJWT.getJWTClaimsSet();
		JSONObject jsonLdObject = (JSONObject) jwtPayload.getClaims().get(JWT_CLAIM_VC);
		VerifiableCredential payloadVerifiableCredential = VerifiableCredential.fromJsonString(jsonLdObject.toJSONString(), false);

		return new JwtVerifiableCredential(jwtPayload, payloadVerifiableCredential, jwt);
	}

	public static JwtVerifiableCredential fromJwt(String jwt, String algorithm, PublicKey publicKey) throws GeneralSecurityException, ParseException, JOSEException, JsonParseException, IOException {

		return fromJwt(jwt, algorithm, publicKey, true);
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

		return new JwtVerifiableCredential(payloadBuilder.build(), payloadVerifiableCredential, null);
	}

	public static JwtVerifiableCredential fromVerifiableCredential(VerifiableCredential verifiableCredential) {

		return fromVerifiableCredential(verifiableCredential, null);
	}

	public JWTClaimsSet getPayload() {

		return this.payload;
	}

	public VerifiableCredential getPayloadVerifiableCredential() {

		return this.payloadVerifiableCredential;
	}

	public String getCompactSerialization() {

		return this.compactSerialization;
	}

	public String toJwt(String algorithm, PrivateKey privateKey) throws JOSEException {

		JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.parse(algorithm)).build();
		SignedJWT signedJWT = new SignedJWT(jwsHeader, this.getPayload());

		JWSSigner signer = new RSASSASigner(privateKey);

		signedJWT.sign(signer);

		this.compactSerialization = signedJWT.serialize();
		return compactSerialization;
	}

	public VerifiableCredential toVerifiableCredential() {

		VerifiableCredential verifiableCredential;

		try {

			verifiableCredential = VerifiableCredential.fromJsonString(this.getPayloadVerifiableCredential().toJsonString(), false);
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
