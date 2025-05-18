package com.danubetech.verifiablecredentials.verifier.checker;

import com.danubetech.dataintegrity.DataIntegrityProof;
import com.danubetech.dataintegrity.canonicalizer.RDFC10SHA256Canonicalizer;
import com.danubetech.notarizationservice.client.ApiException;
import com.danubetech.notarizationservice.client.NotarizationServiceClient;
import com.danubetech.notarizationservice.client.swagger.model.BitcoinVerifyResponse;
import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.danubetech.verifiablecredentials.proof.BlockchainHashProof2020;
import com.danubetech.verifiablecredentials.verifier.VerifyingException;
import com.danubetech.verifiablecredentials.verifier.result.VerifyResult;
import foundation.identity.jsonld.JsonLDException;
import foundation.identity.jsonld.JsonLDObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class NotarizationChecker extends AbstractChecker<VerifiableCredential, Void> implements Checker<VerifiableCredential, Void> {

	private static Logger log = LoggerFactory.getLogger(NotarizationChecker.class);

	private NotarizationServiceClient notarizationServiceClient;

	public NotarizationChecker(NotarizationServiceClient notarizationServiceClient) {

		super("notarization");

		this.notarizationServiceClient = notarizationServiceClient;
	}

	@Override
	public Void checkInternal(VerifiableCredential verifiableCredential, VerifyResult verifyResult) throws VerifyingException {

		Map<String, Object> jsonObject = verifiableCredential.getJsonObject();

		// obtain the normalizedDocument document

		String normalizedDocument;

		try {

			JsonLDObject jsonLdDocumentWithoutProof = JsonLDObject.builder().base(verifiableCredential).build();
			jsonLdDocumentWithoutProof.setDocumentLoader(verifiableCredential.getDocumentLoader());
			DataIntegrityProof.removeFromJsonLdObject(jsonLdDocumentWithoutProof);
			normalizedDocument = RDFC10SHA256Canonicalizer.getInstance().canonicalize(jsonLdDocumentWithoutProof);
		} catch (JsonLDException | IOException ex) {

			throw new VerifyingException("Normalization problem: " + ex.getMessage(), ex);
		}

		// check the notarization

		BlockchainHashProof2020 ldProof = BlockchainHashProof2020.getFromJsonLDObject(verifiableCredential);
		if (log.isDebugEnabled()) log.debug("Found proof: " + ldProof);
		if (ldProof == null) return null;

		String proofValue = ldProof.getProofValue();
		String[] proofValueParts = proofValue.split(" ");
		String txId = proofValueParts[0];
		String signature = proofValueParts[1];

		BitcoinVerifyResponse bitcoinVerifyResponse;

		try {

			bitcoinVerifyResponse = this.getNotarizationServiceClient().verifyUsingPOST("SHA-256", null, null, Boolean.FALSE, signature, txId, normalizedDocument);
			if (log.isDebugEnabled()) log.debug("Notarization service response: " + bitcoinVerifyResponse);
		} catch (ApiException ex) {

			throw new VerifyingException("Cannot check notarization: " + ex.getCode() + " - " + ex.getResponseBody(), ex);
		}

		if (Boolean.TRUE != bitcoinVerifyResponse.getVerified()) {

			throw new VerifyingException("Credential notarization cannot be verified. txId: " + bitcoinVerifyResponse.getTxId() + ", notarizationBlockHeight: " + bitcoinVerifyResponse.getNotarizationBlockHeight() + ", currentBlockHeight: " + bitcoinVerifyResponse.getCurrentBlockHeight());
		}

		// CHECK METADATA

		this.getCheckMetadata(verifyResult).put("txId", bitcoinVerifyResponse.getTxId());
		this.getCheckMetadata(verifyResult).put("notarizationBlockHeight", bitcoinVerifyResponse.getNotarizationBlockHeight());
		this.getCheckMetadata(verifyResult).put("currentBlockHeight", bitcoinVerifyResponse.getCurrentBlockHeight());
		this.getCheckMetadata(verifyResult).put("verified", bitcoinVerifyResponse.getVerified());

		// done

		return null;
	}

	/*
	 * Getters and setters
	 */

	public NotarizationServiceClient getNotarizationServiceClient() {
		return this.notarizationServiceClient;
	}

	public void setNotarizationServiceClient(NotarizationServiceClient notarizationServiceClient) {
		this.notarizationServiceClient = notarizationServiceClient;
	}
}
