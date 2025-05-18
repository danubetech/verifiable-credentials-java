package com.danubetech.verifiablecredentials.verifier.checker;

import com.danubetech.verifiablecredentials.verifier.VerifyingException;
import com.danubetech.verifiablecredentials.verifier.result.VerifyResult;
import foundation.identity.jsonld.JsonLDObject;
import foundation.identity.jsonld.validation.Validation;

public class JsonLDChecker extends AbstractChecker<JsonLDObject, Void> implements Checker<JsonLDObject, Void> {

	public JsonLDChecker() {

		super("jsonld");
	}

	@Override
	public Void checkInternal(JsonLDObject jsonLdObject, VerifyResult verifyResult) throws VerifyingException {

		if (jsonLdObject == null) return null;

		Validation.validate(jsonLdObject);

		return null;
	}
}
