package com.danubetech.verifiablecredentials.verifier;

import com.danubetech.verifiablecredentials.verifier.result.VerifyResult;

public class VerifyingException extends Exception {

	private VerifyResult verifyResult;

	public VerifyingException(String message) {
		super(message);
	}

	public VerifyingException(String message, Throwable ex) {
		super(message, ex);
	}

	public VerifyingException(VerifyResult verifyResult) {
		this(verifyResult.getErrorMessage() == null ? "(unknown error)" : verifyResult.getErrorMessage());
		this.verifyResult = verifyResult;
	}

	/*
	 * Getters and setters
	 */

	public VerifyResult getVerifyResult() {
		return verifyResult;
	}

	public void setVerifyResult(VerifyResult verifyResult) {
		this.verifyResult = verifyResult;
	}
}
