package com.danubetech.verifiablecredentials.jwt;

import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.util.JSONObjectUtils;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.Map;

public class JwtVerifiableCredential extends JwtWrappingObject<VerifiableCredential> {

	public JwtVerifiableCredential(JWTClaimsSet payload, VerifiableCredential payloadObject, JWSObject jwsObject, String compactSerialization) {

		super(payload, payloadObject, jwsObject, compactSerialization);
	}

	/*
	 * Factory methods
	 */

	public static JwtVerifiableCredential fromCompactSerialization(String compactSerialization) throws ParseException {

		SignedJWT signedJWT = SignedJWT.parse(compactSerialization);

		JWTClaimsSet jwtPayload = signedJWT.getJWTClaimsSet();
		Map<String, Object> jsonObject = (Map<String, Object>) jwtPayload.getClaims().get(JwtKeywords.JWT_CLAIM_VC);
		VerifiableCredential payloadVerifiableCredential = VerifiableCredential.fromJsonObject(new LinkedHashMap<>(jsonObject));

		return new JwtVerifiableCredential(jwtPayload, payloadVerifiableCredential, signedJWT, compactSerialization);
	}
}
