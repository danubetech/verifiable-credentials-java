package com.danubetech.verifiablecredentials.jwt;

import com.danubetech.verifiablecredentials.VerifiablePresentation;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jwt.JWTClaimsSet;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

public class JwtJwtVerifiablePresentation extends JwtWrappingObject<JwtVerifiableCredential> {

	private JwtJwtVerifiablePresentation(JWTClaimsSet payload, JwtVerifiableCredential payloadObject, JWSObject jwsObject, String compactSerialization) {

		super(payload, payloadObject, jwsObject, compactSerialization);
	}

	/*
	 * Factory methods
	 */

	public static JwtJwtVerifiablePresentation fromJwtVerifiableCredential(JwtVerifiableCredential jwtVerifiableCredential, String aud) throws IOException {

		JwtVerifiableCredential payloadJwtVerifiableCredential = jwtVerifiableCredential;
		VerifiablePresentation verifiablePresentation = FromJwtConverter.fromJwtVerifiableCredentialToVerifiablePresentation(payloadJwtVerifiableCredential);

		JWTClaimsSet.Builder payloadBuilder = new JWTClaimsSet.Builder();

		Date issueTime = new Date();
		
		payloadBuilder.jwtID("urn:uuid:" + UUID.randomUUID());
		payloadBuilder.issuer(jwtVerifiableCredential.getPayload().getSubject());
		payloadBuilder.issueTime(issueTime);
		payloadBuilder.notBeforeTime(issueTime);
		if (aud != null) payloadBuilder.audience(aud);

		payloadBuilder.claim(JwtKeywords.JWT_CLAIM_VP, verifiablePresentation.getJsonObject());

		return new JwtJwtVerifiablePresentation(payloadBuilder.build(), payloadJwtVerifiableCredential, null, null);
	}

	public static JwtJwtVerifiablePresentation fromJwtVerifiableCredential(JwtVerifiableCredential jwtVerifiableCredential) throws IOException {

		return fromJwtVerifiableCredential(jwtVerifiableCredential, null);
	}
}
