package com.danubetech.verifiablecredentials.verifier.checker;

import com.danubetech.verifiablecredentials.verifier.result.VerifyResult;

public interface Checker<I, R> {

	public R check(I i, VerifyResult verifyResult);
	public String getCheckName();
}
