package com.danubetech.verifiablecredentials.jwt;

import java.io.IOException;
import java.security.PrivateKey;
import java.util.UUID;

import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.lang.JoseException;

import com.danubetech.verifiablecredentials.VerifiablePresentation;
import com.fasterxml.jackson.core.JsonGenerationException;

public class JwtVerifiablePresentation {

	public static final String JWT_CLAIM_VP = "vp";

	private final JwtClaims payload;
	private final JwtVerifiableCredential payloadJwtVerifiableCredential;

	private String compactSerialization;

	private JwtVerifiablePresentation(JwtClaims payload, JwtVerifiableCredential payloadJwtVerifiableCredential, String compactSerialization) {

		if (payload == null) throw new NullPointerException();
		if (payloadJwtVerifiableCredential == null) throw new NullPointerException();

		this.payload = payload;
		this.payloadJwtVerifiableCredential = payloadJwtVerifiableCredential;
		this.compactSerialization = compactSerialization;
	}

	public static JwtVerifiablePresentation fromJwtVerifiableCredential(JwtVerifiableCredential jwtVerifiableCredential, String aud) throws JsonGenerationException, IOException, MalformedClaimException {

		JwtVerifiableCredential payloadJwtVerifiableCredential = jwtVerifiableCredential;

		JwtClaims payload = new JwtClaims();

		if (aud != null) {

			payload.setAudience(aud);
		}

		VerifiablePresentation verifiablePresentation = VerifiablePresentation.fromJwtVerifiableCredential(payloadJwtVerifiableCredential);

		payload.setJwtId("urn:uuid:" + UUID.randomUUID().toString());
		payload.setIssuer(jwtVerifiableCredential.getPayload().getSubject());
		payload.setIssuedAtToNow();
		payload.setClaim(JWT_CLAIM_VP, verifiablePresentation.getJsonLdObject());

		return new JwtVerifiablePresentation(payload, payloadJwtVerifiableCredential, null);
	}

	public static JwtVerifiablePresentation fromJwtVerifiableCredential(JwtVerifiableCredential jwtVerifiableCredential) throws JsonGenerationException, IOException, MalformedClaimException {

		return fromJwtVerifiableCredential(jwtVerifiableCredential, null);
	}

	public JwtClaims getPayload() {

		return this.payload;
	}

	public JwtVerifiableCredential getPayloadJwtVerifiableCredential() {

		return this.payloadJwtVerifiableCredential;
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
}
