package com.danubetech.verifiablecredentials.verifier.checker;

import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.danubetech.verifiablecredentials.verifier.VerifyingException;
import com.danubetech.verifiablecredentials.verifier.result.VerifyResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class IssuanceDateChecker extends AbstractChecker<VerifiableCredential, Void> implements Checker<VerifiableCredential, Void> {

	private static Logger log = LoggerFactory.getLogger(IssuanceDateChecker.class);

	public IssuanceDateChecker() {
		super("issuance-date");
	}

	@Override
	public Void checkInternal(VerifiableCredential verifiableCredential, VerifyResult verifyResult) throws VerifyingException {

		if (verifiableCredential.getIssuanceDate() == null) return null;

		// check issuance date

		if (verifiableCredential.getIssuanceDate().after(new Date())) throw new VerifyingException("Issuance date is in the future.");
		this.getCheckMetadata(verifyResult).put("verified", Boolean.TRUE);

		// done

		return null;
	}
}
