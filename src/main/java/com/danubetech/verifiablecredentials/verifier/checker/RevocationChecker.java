package com.danubetech.verifiablecredentials.verifier.checker;

import com.danubetech.revocationservice.client.ApiException;
import com.danubetech.revocationservice.client.RevocationServiceClient;
import com.danubetech.revocationservice.client.openapi.model.CheckRequest;
import com.danubetech.revocationservice.client.openapi.model.CheckResponse;
import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.danubetech.verifiablecredentials.verifier.VerifyingException;
import com.danubetech.verifiablecredentials.verifier.result.VerifyResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RevocationChecker extends AbstractChecker<VerifiableCredential, Void> implements Checker<VerifiableCredential, Void> {

	private static Logger log = LoggerFactory.getLogger(RevocationChecker.class);

	private RevocationServiceClient revocationServiceClient;

	public RevocationChecker(RevocationServiceClient revocationServiceClient) {
		super("credential-status");
		this.revocationServiceClient = revocationServiceClient;
	}

	@Override
	public Void checkInternal(VerifiableCredential verifiableCredential, VerifyResult verifyResult) throws VerifyingException {

		// check revocation

		CheckRequest checkRequest = new CheckRequest();
		checkRequest.setCredential(verifiableCredential);

		CheckResponse checkResponse;

		try {

			checkResponse = this.getRevocationServiceClient().check("jsonld", checkRequest);
		} catch (ApiException ex) {

			throw new VerifyingException("Cannot check revocation: " + ex.getCode() + " - " + ex.getResponseBody(), ex);
		}
		if (checkResponse == null || checkResponse.getRevoked() == null)  throw new VerifyingException("Revocation Check Failed: " + checkResponse );
		if (! Boolean.FALSE.equals(checkResponse.getRevoked())) throw new VerifyingException("Credential has been revoked");

		this.getCheckMetadata(verifyResult).put("revoked", checkResponse.getRevoked());
		this.getCheckMetadata(verifyResult).put("metadata", checkResponse.getMetadata());

		return null;

/*		// check the revocation

		RevocationQuery2020Status ldCredentialStatus = RevocationQuery2020Status.getFromJsonLDObject(verifiableCredential);
		if (log.isDebugEnabled()) log.debug("Found credential status: " + ldCredentialStatus);
		if (ldCredentialStatus == null) return null;

		String credentialReference = ldCredentialStatus.getCredentialReference();
		String revocationService = ldCredentialStatus.getRevocationService();

		Revocation revocation;

		try {

			String revocationServiceBasePath = revocationService.substring(0, revocationService.lastIndexOf("/"));
			if (log.isDebugEnabled()) log.debug("Revocation service base path: " + revocationServiceBasePath);
			RevocationServiceClient revocationServiceClient = RevocationServiceClient.create(revocationServiceBasePath);

			revocation = revocationServiceClient.getRevocation(UUID.fromString(credentialReference));
			if (log.isDebugEnabled()) log.debug("Revocation service response: " + revocation);
		} catch (ApiException ex) {

			if (ex.getCode() == 404) {

				revocation = null;
			} else {

				String exMessage = ex.getCode() + ": " + ex.getMessage();
				throw new VerifyingException("Revocation problem: " + exMessage, ex);
			}
		}

		if (revocation != null) {

			throw new VerifyingException("Credential has been revoked. timestamp: " + revocation.getTimestamp() + ", reason: " + revocation.getReason());
		}

		// CHECK METADATA

		this.getCheckMetadata().put("credentialReference", credentialReference);

		// done

		return null;*/
	}

	/*
	 * Getters and setters
	 */

	public RevocationServiceClient getRevocationServiceClient() {

		return this.revocationServiceClient;
	}

	public void setRevocationServiceClient(RevocationServiceClient revocationServiceClient) {

		this.revocationServiceClient = revocationServiceClient;
	}
}
