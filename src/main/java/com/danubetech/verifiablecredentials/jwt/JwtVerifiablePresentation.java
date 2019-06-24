package com.danubetech.verifiablecredentials.jwt;

import java.security.PrivateKey;

import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.lang.JoseException;

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

	public static JwtVerifiablePresentation fromJwtVerifiableCredential(JwtVerifiableCredential jwtVerifiableCredential, String aud) {

		JwtVerifiableCredential payloadJwtVerifiableCredential = jwtVerifiableCredential;

		JwtClaims payload = new JwtClaims();

		if (aud != null) {

			payload.setAudience(aud);
		}

		payload.setClaim(JWT_CLAIM_VP, payloadJwtVerifiableCredential.getCompactSerialization());

		return new JwtVerifiablePresentation(payload, payloadJwtVerifiableCredential, null);
	}

	public static JwtVerifiablePresentation fromJwtVerifiableCredential(JwtVerifiableCredential jwtVerifiableCredential) {

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
