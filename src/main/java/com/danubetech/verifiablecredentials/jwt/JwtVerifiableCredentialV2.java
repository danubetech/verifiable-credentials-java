package com.danubetech.verifiablecredentials.jwt;


import com.danubetech.verifiablecredentials.VerifiableCredentialV2;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.Map;

public class JwtVerifiableCredentialV2 extends JwtWrappingObject<VerifiableCredentialV2> {

	public JwtVerifiableCredentialV2(JWTClaimsSet payload, VerifiableCredentialV2 payloadObject, JWSObject jwsObject, String compactSerialization) {

		super(payload, payloadObject, jwsObject, compactSerialization);
	}

	/*
	 * Factory methods
	 */

	public static JwtVerifiableCredentialV2 fromCompactSerialization(String compactSerialization) throws ParseException {

		SignedJWT signedJWT = SignedJWT.parse(compactSerialization);

		JWTClaimsSet jwtPayload = signedJWT.getJWTClaimsSet();
		Map<String, Object> jsonObject = (Map<String, Object>) jwtPayload.getClaims().get(JwtKeywords.JWT_CLAIM_VC);
		if (jsonObject == null) return null;

		VerifiableCredentialV2 payloadVerifiableCredential = VerifiableCredentialV2.fromJsonObject(new LinkedHashMap<>(jsonObject));
		if(!payloadVerifiableCredential.getContexts().contains(VerifiableCredentialV2.DEFAULT_JSONLD_CONTEXTS[0])) throw new ParseException("The 'vc' claim must contain the default context v2",0);
		return new JwtVerifiableCredentialV2(jwtPayload, payloadVerifiableCredential, signedJWT, compactSerialization);
	}
}
