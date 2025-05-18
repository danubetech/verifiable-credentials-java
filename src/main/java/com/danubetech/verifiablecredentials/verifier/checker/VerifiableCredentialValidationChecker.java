package com.danubetech.verifiablecredentials.verifier.checker;

import com.apicatalog.jsonld.loader.DocumentLoader;
import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.danubetech.verifiablecredentials.validation.Validation;
import com.danubetech.verifiablecredentials.verifier.VerifyingException;
import com.danubetech.verifiablecredentials.verifier.result.VerifyResult;

import java.util.Map;

public class VerifiableCredentialValidationChecker extends AbstractChecker<Map<String, Object>, VerifiableCredential> implements Checker<Map<String, Object>, VerifiableCredential> {

	private DocumentLoader documentLoader;

	public VerifiableCredentialValidationChecker(DocumentLoader documentLoader) {

		super("credential-parse");

		this.documentLoader = documentLoader;
	}

	@Override
	public VerifiableCredential checkInternal(Map<String, Object> verifiableCredentialJson, VerifyResult verifyResult) throws VerifyingException {

		if (verifiableCredentialJson == null) throw new NullPointerException();

		VerifiableCredential verifiableCredential = VerifiableCredential.fromJsonObject(verifiableCredentialJson);
		verifiableCredential.setDocumentLoader(this.documentLoader);

		Validation.validate(verifiableCredential);
		this.getCheckMetadata(verifyResult).put("parsed", Boolean.TRUE);

		return verifiableCredential;
	}
}
