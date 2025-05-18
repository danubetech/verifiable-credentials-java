package com.danubetech.verifiablecredentials.verifier.checker;

import com.apicatalog.jsonld.loader.DocumentLoader;
import com.danubetech.verifiablecredentials.VerifiablePresentation;
import com.danubetech.verifiablecredentials.validation.Validation;
import com.danubetech.verifiablecredentials.verifier.VerifyingException;
import com.danubetech.verifiablecredentials.verifier.result.VerifyResult;

import java.util.Map;

public class VerifiablePresentationValidationChecker extends AbstractChecker<Map<String, Object>, VerifiablePresentation> implements Checker<Map<String, Object>, VerifiablePresentation> {

	private DocumentLoader documentLoader;

	public VerifiablePresentationValidationChecker(DocumentLoader documentLoader) {

		super("presentation-parse");

		this.documentLoader = documentLoader;
	}

	@Override
	public VerifiablePresentation checkInternal(Map<String, Object> verifiablePresentationJson, VerifyResult verifyResult) throws VerifyingException {

		if (verifiablePresentationJson == null) throw new NullPointerException();

		VerifiablePresentation verifiablePresentation = VerifiablePresentation.fromJsonObject(verifiablePresentationJson);
		verifiablePresentation.setDocumentLoader(this.documentLoader);

		Validation.validate(verifiablePresentation);
		this.getCheckMetadata(verifyResult).put("parsed", Boolean.TRUE);

		return verifiablePresentation;
	}
}
