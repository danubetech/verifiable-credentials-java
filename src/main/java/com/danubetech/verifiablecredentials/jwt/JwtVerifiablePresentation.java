package com.danubetech.verifiablecredentials.jwt;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import com.danubetech.verifiablecredentials.VerifiablePresentation;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

public class JwtVerifiablePresentation {

	public static final String JWT_CLAIM_VP = "vp";

	private final JWTClaimsSet payload;
	private final JwtVerifiableCredential payloadJwtVerifiableCredential;

	private String compactSerialization;

	private JwtVerifiablePresentation(JWTClaimsSet payload, JwtVerifiableCredential payloadJwtVerifiableCredential, String compactSerialization) {

		if (payload == null) throw new NullPointerException();
		if (payloadJwtVerifiableCredential == null) throw new NullPointerException();

		this.payload = payload;
		this.payloadJwtVerifiableCredential = payloadJwtVerifiableCredential;
		this.compactSerialization = compactSerialization;
	}

	public static JwtVerifiablePresentation fromJwtVerifiableCredential(JwtVerifiableCredential jwtVerifiableCredential, String aud) throws  IOException {

		JwtVerifiableCredential payloadJwtVerifiableCredential = jwtVerifiableCredential;
		VerifiablePresentation verifiablePresentation = VerifiablePresentation.fromJwtVerifiableCredential(payloadJwtVerifiableCredential);

		JWTClaimsSet.Builder payloadBuilder = new JWTClaimsSet.Builder();

		Date issueTime = new Date();
		
		payloadBuilder.jwtID("urn:uuid:" + UUID.randomUUID().toString());
		payloadBuilder.issuer(jwtVerifiableCredential.getPayload().getSubject());
		payloadBuilder.issueTime(issueTime);
		payloadBuilder.notBeforeTime(issueTime);

		if (aud != null) {

			payloadBuilder.audience(aud);
		}

		payloadBuilder.claim(JWT_CLAIM_VP, verifiablePresentation.getJsonLdObject());

		return new JwtVerifiablePresentation(payloadBuilder.build(), payloadJwtVerifiableCredential, null);
	}

	public static JwtVerifiablePresentation fromJwtVerifiableCredential(JwtVerifiableCredential jwtVerifiableCredential) throws IOException {

		return fromJwtVerifiableCredential(jwtVerifiableCredential, null);
	}

	public JWTClaimsSet getPayload() {

		return this.payload;
	}

	public JwtVerifiableCredential getPayloadJwtVerifiableCredential() {

		return this.payloadJwtVerifiableCredential;
	}

	public String getCompactSerialization() {

		return this.compactSerialization;
	}

	public String toJwt(RSAKey rsaKey) throws JOSEException {

		JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.RS256).build();
		SignedJWT signedJWT = new SignedJWT(jwsHeader, this.getPayload());

		JWSSigner signer = new RSASSASigner(rsaKey);

		signedJWT.sign(signer);

		this.compactSerialization = signedJWT.serialize();
		return compactSerialization;
	}
}
