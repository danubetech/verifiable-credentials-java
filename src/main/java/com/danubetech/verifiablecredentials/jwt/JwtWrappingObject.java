package com.danubetech.verifiablecredentials.jwt;

import com.nimbusds.jose.JWSObject;
import com.nimbusds.jwt.JWTClaimsSet;

public abstract class JwtWrappingObject <T> extends JwtObject {

	private final T payloadObject;

	protected JwtWrappingObject(JWTClaimsSet payload, T payloadObject, JWSObject jwsObject, String compactSerialization) {

		super(payload, jwsObject, compactSerialization);

		if (payloadObject == null) throw new NullPointerException();
		this.payloadObject = payloadObject;
	}

	/*
	 * Getters
	 */

	public T getPayloadObject() {
		return this.payloadObject;
	}
}
