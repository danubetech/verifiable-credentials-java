package com.danubetech.verifiablecredentials.verifier.checker;

import com.danubetech.verifiablecredentials.verifier.VerifyingException;
import com.danubetech.verifiablecredentials.verifier.result.VerifyResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstractChecker<I, R> implements Checker<I, R> {

	private static Logger log = LoggerFactory.getLogger(AbstractChecker.class);

	private final String checkName;

	protected AbstractChecker(String checkName) {
		this.checkName = checkName;
	}

	protected abstract R checkInternal(I i, VerifyResult verifyResult) throws VerifyingException;

	@Override
	public final R check(I i, VerifyResult verifyResult) {

		try {

			R r = this.checkInternal(i, verifyResult);
			if (log.isDebugEnabled()) log.debug("Result for " + this.getClass().getSimpleName() + ": " + (r == null ? null : r.getClass().getSimpleName()));
			if (r != null) this.getCheckMetadata(verifyResult);
			return r;
		} catch (VerifyingException ex) {

			String message = ex.getMessage() != null ? ex.getMessage() : ex.getClass().getSimpleName();
			if (log.isWarnEnabled()) log.warn("Verifying exception for " + this.getClass().getSimpleName() + ": " + message, ex);
			this.getCheckMetadata(verifyResult);
			this.addProblem(verifyResult, message);
			return null;
		} catch (Exception ex) {

			String message = ex.getMessage() != null ? ex.getMessage() : ex.getClass().getSimpleName();
			if (log.isWarnEnabled()) log.warn("Exception for " + this.getClass().getSimpleName() + ": " + message, ex);
			this.getCheckMetadata(verifyResult);
			this.addProblem(verifyResult, message);
			return null;
		}
	}

	protected final Map<String, Object> getCheckMetadata(VerifyResult verifyResult) {
		return verifyResult.getCheckMetadata(this.getCheckName());
	}

	protected final void addProblem(VerifyResult verifyResult, String message, Map<String, Object> problemMetadata) {
		if (problemMetadata == null) problemMetadata = new LinkedHashMap<>();
		problemMetadata.put("check", this.getCheckName());
		verifyResult.addProblem(message, problemMetadata);
	}

	protected final void addProblem(VerifyResult verifyResult, String message) {
		this.addProblem(verifyResult, message, null);
	}

	protected final void addWarning(VerifyResult verifyResult, String message, Map<String, Object> warningMetadata) {
		if (warningMetadata == null) warningMetadata = new LinkedHashMap<>();
		warningMetadata.put("check", this.getCheckName());
		verifyResult.addWarning(message, warningMetadata);
	}

	protected final void addWarning(VerifyResult verifyResult, String message) {
		this.addWarning(verifyResult, message, null);
	}

	@Override
	public String getCheckName() {
		return this.checkName;
	}
}
