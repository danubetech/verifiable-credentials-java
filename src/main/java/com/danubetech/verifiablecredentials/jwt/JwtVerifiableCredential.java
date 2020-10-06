package com.danubetech.verifiablecredentials.jwt;

import java.text.ParseException;

import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import net.minidev.json.JSONObject;

public class JwtVerifiableCredential extends JwtWrappingObject<VerifiableCredential> {

	public JwtVerifiableCredential(JWTClaimsSet payload, VerifiableCredential payloadObject, JWSObject jwsObject, String compactSerialization) {

		super(payload, payloadObject, jwsObject, compactSerialization);
	}

	/*
	 * Factory methods
	 */

	public static JwtVerifiableCredential fromCompactSerialization(String compactSerialization) throws ParseException {

		SignedJWT signedJWT = SignedJWT.parse(compactSerialization);

		JWTClaimsSet payload = signedJWT.getJWTClaimsSet();
		JSONObject jsonLdObject = (JSONObject) payload.getClaims().get(JwtKeywords.JWT_CLAIM_VC);
		VerifiableCredential payloadVerifiableCredential = VerifiableCredential.fromJson(jsonLdObject.toJSONString(), false);

		return new JwtVerifiableCredential(payload, payloadVerifiableCredential, signedJWT, compactSerialization);
	}
}
