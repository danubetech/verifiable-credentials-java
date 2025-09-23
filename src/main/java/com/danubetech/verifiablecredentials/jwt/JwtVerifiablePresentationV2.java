package com.danubetech.verifiablecredentials.jwt;

import com.danubetech.verifiablecredentials.VerifiablePresentation;
import com.danubetech.verifiablecredentials.VerifiablePresentationV2;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.Map;

public class JwtVerifiablePresentationV2 extends JwtWrappingObject<VerifiablePresentationV2> {

	public JwtVerifiablePresentationV2(JWTClaimsSet payload, VerifiablePresentationV2 payloadObject, JWSObject jwsObject, String compactSerialization) {

		super(payload, payloadObject, jwsObject, compactSerialization);
	}

	/*
	 * Factory methods
	 */

	public static JwtVerifiablePresentationV2 fromCompactSerialization(String compactSerialization) throws ParseException {

		SignedJWT signedJWT = SignedJWT.parse(compactSerialization);

		JWTClaimsSet jwtPayload = signedJWT.getJWTClaimsSet();
		Map<String, Object> jsonObject = (Map<String, Object>) jwtPayload.getClaims().get(JwtKeywords.JWT_CLAIM_VP);
		if (jsonObject == null) return null;

		VerifiablePresentationV2 payloadVerifiablePresentation = VerifiablePresentationV2.fromJsonObject(new LinkedHashMap<>(jsonObject));

		return new JwtVerifiablePresentationV2(jwtPayload, payloadVerifiablePresentation, signedJWT, compactSerialization);
	}
}
