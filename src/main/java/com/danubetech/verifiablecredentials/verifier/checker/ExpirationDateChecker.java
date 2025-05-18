package com.danubetech.verifiablecredentials.verifier.checker;

import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.danubetech.verifiablecredentials.verifier.VerifyingException;
import com.danubetech.verifiablecredentials.verifier.result.VerifyResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class ExpirationDateChecker extends AbstractChecker<VerifiableCredential, Void> implements Checker<VerifiableCredential, Void> {

	private static Logger log = LoggerFactory.getLogger(ExpirationDateChecker.class);

	public ExpirationDateChecker() {
		super("expiration-date");
	}

	@Override
	public Void checkInternal(VerifiableCredential verifiableCredential, VerifyResult verifyResult) throws VerifyingException {

		if (verifiableCredential.getExpirationDate() == null) return null;

		// check issuance date

		if (verifiableCredential.getExpirationDate().before(new Date())) throw new VerifyingException("Expiration date is in the past.");
		this.getCheckMetadata(verifyResult).put("verified", Boolean.TRUE);

		// done

		return null;
	}
}
