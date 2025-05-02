package com.danubetech.verifiablecredentials.validation;

import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.danubetech.verifiablecredentials.VerifiableCredentialV2;
import com.danubetech.verifiablecredentials.VerifiablePresentation;
import com.danubetech.verifiablecredentials.credentialstatus.CredentialStatus;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public class Validation {

    private static void validateTrue(boolean valid) {
        if (! valid) throw new RuntimeException();
    }

    private static void validateUrl(URI uri) {
        try {
            if (! uri.isAbsolute()) throw new URISyntaxException("Not absolute.", uri.toString());
        } catch (URISyntaxException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    private static void validateRun(Runnable runnable, String message) throws IllegalStateException {
        try {
            runnable.run();
        } catch (Exception ex) {
            if (ex.getMessage() != null && ! ex.getMessage().isEmpty()) message = message + " (" + ex.getMessage().trim() + ")";
            throw new IllegalStateException(message, ex);
        }
    }

    public static void validate(VerifiableCredential verifiableCredential) throws IllegalStateException {

        foundation.identity.jsonld.validation.Validation.validate(verifiableCredential);

        validateRun(() -> validateTrue(verifiableCredential.getJsonObject() != null), "Bad or missing JSON object.");
        validateRun(() -> validateTrue(!verifiableCredential.getContexts().isEmpty()), "Bad or missing '@context'.");
        validateRun(() -> validateUrl(verifiableCredential.getContexts().get(0)), "@context must be a valid URI: " + verifiableCredential.getContexts().get(0));
        validateRun(() -> validateTrue(VerifiableCredential.DEFAULT_JSONLD_CONTEXTS[0].equals(verifiableCredential.getContexts().get(0))), "First value of @context must be " + VerifiableCredential.DEFAULT_JSONLD_CONTEXTS[0] + ": " + verifiableCredential.getContexts().get(0));
        validateRun(() -> { if (verifiableCredential.getId() != null) validateUrl(verifiableCredential.getId()); }, "'id' must be a valid URI.");

        validateRun(() -> validateTrue(!verifiableCredential.getTypes().isEmpty()), "Bad or missing 'type'.");
        validateRun(() -> validateTrue(verifiableCredential.getTypes().contains(VerifiableCredential.DEFAULT_JSONLD_TYPES[0])), "'type' must contain 'VerifiableCredential': " + verifiableCredential.getTypes());
        validateRun(() -> validateTrue(verifiableCredential.getIssuer() != null), "Bad or missing 'issuer'.");
        validateRun(() -> validateUrl(verifiableCredential.getIssuer()), "'issuer' must be a valid URI.");
        validateRun(() -> validateTrue(verifiableCredential.getIssuanceDate() != null), "Bad or missing 'issuanceDate'.");
        validateRun(verifiableCredential::getExpirationDate, "Bad 'expirationDate'.");
        validateRun(verifiableCredential::getCredentialSubject, "Bad 'credentialSubject'.");
        validateRun(() -> validateTrue(verifiableCredential.getCredentialSubject() != null), "Bad or missing 'credentialSubject'.");
    }

    public static void validate(VerifiablePresentation verifiablePresentation) throws IllegalStateException {

        foundation.identity.jsonld.validation.Validation.validate(verifiablePresentation);

        validateRun(() -> validateTrue(verifiablePresentation.getJsonObject() != null), "Bad or missing JSON object.");
        validateRun(() -> validateTrue(! verifiablePresentation.getContexts().isEmpty()), "Bad or missing '@context'.");
        validateRun(() -> validateUrl(verifiablePresentation.getContexts().get(0)), "@context must be a valid URI: " + verifiablePresentation.getContexts().get(0));
        validateRun(() -> validateTrue(VerifiableCredential.DEFAULT_JSONLD_CONTEXTS[0].equals(verifiablePresentation.getContexts().get(0))), "First value of @context must be " + VerifiableCredential.DEFAULT_JSONLD_CONTEXTS[0] + ": " + verifiablePresentation.getContexts().get(0));
        validateRun(() -> { if (verifiablePresentation.getId() != null) validateUrl(verifiablePresentation.getId()); }, "'id' must be a valid URI.");

        validateRun(() -> validateTrue(! verifiablePresentation.getTypes().isEmpty()), "Bad or missing 'type'.");
        validateRun(() -> validateTrue(verifiablePresentation.getTypes().contains(VerifiablePresentation.DEFAULT_JSONLD_TYPES[0])), "type must contain VerifiablePresentation: " + verifiablePresentation.getTypes());
        validateRun(() -> validateTrue(verifiablePresentation.getVerifiableCredential() != null || verifiablePresentation.getJwtVerifiableCredentialString() != null), "Bad or missing 'verifiableCredential'.");
    }

    public static void validate(VerifiableCredentialV2 verifiableCredential) throws IllegalStateException {

        foundation.identity.jsonld.validation.Validation.validate(verifiableCredential);

        validateRun(() -> validateTrue(verifiableCredential.getJsonObject() != null), "Bad or missing JSON object.");
        validateRun(() -> validateTrue(!verifiableCredential.getContexts().isEmpty()), "Bad or missing '@context'.");
        validateRun(() -> validateUrl(verifiableCredential.getContexts().get(0)), "@context must be a valid URI: " + verifiableCredential.getContexts().get(0));
        validateRun(() -> validateTrue(VerifiableCredentialV2.DEFAULT_JSONLD_CONTEXTS[0].equals(verifiableCredential.getContexts().get(0))), "First value of @context must be " + VerifiableCredentialV2.DEFAULT_JSONLD_CONTEXTS[0] + ": " + verifiableCredential.getContexts().get(0));
        validateRun(() -> { if (verifiableCredential.getId() != null) validateUrl(verifiableCredential.getId()); }, "'id' must be a valid URI.");

        validateRun(() -> validateTrue(!verifiableCredential.getTypes().isEmpty()), "Bad or missing 'type'.");
        validateRun(() -> validateTrue(verifiableCredential.getTypes().contains(VerifiableCredential.DEFAULT_JSONLD_TYPES[0])), "'type' must contain 'VerifiableCredential': " + verifiableCredential.getTypes());

        //Issuer validation
        validateIssuer(verifiableCredential);
        validateRun(() -> validateTrue(verifiableCredential.getValidFrom() != null), "Bad or missing 'validFrom'.");
        validateRun(verifiableCredential::getValidUntil, "Bad 'validUntil'.");
        validateRun(verifiableCredential::getCredentialSubject, "Bad 'credentialSubject'.");
        validateRun(() -> validateTrue(verifiableCredential.getCredentialSubject() != null), "Bad or missing 'credentialSubject'.");

        //Validate credential Status : Handle both list and single object of credentialStatus
        validateStatus(verifiableCredential);
    }

    private static void validateIssuer(VerifiableCredentialV2 verifiableCredential) throws IllegalStateException {

        validateRun(() -> validateTrue(verifiableCredential.getIssuer() != null), "Bad or missing 'issuer'.");
        if (verifiableCredential.getIssuer() instanceof String issuerString) validateRun(() -> validateUrl(URI.create(issuerString)), "'issuer' must be a valid URI.");
        else if (verifiableCredential.getIssuer() instanceof Map<?,?> issuerMap) validateRun(()-> validateUrl(URI.create(((Map<String,Object>) issuerMap).get("id").toString())), "'issuer' must contain be a valid 'id'.");
        else validateRun(() -> validateTrue(false),"'issuer' must be a valid URI or object containing an 'id' property.");
    }

    private static void validateStatus(VerifiableCredentialV2 verifiableCredential) throws IllegalStateException {
        if (verifiableCredential.getCredentialStatus() == null) return;
        verifiableCredential.getCredentialStatusAsList().forEach(Validation::validateCredentialStatus);
    }

    private static void validateCredentialStatus(CredentialStatus credentialStatus) throws IllegalStateException {
        validateRun(() -> validateTrue(credentialStatus.getType() != null), "Bad or missing 'credentialStatus Type'.");
    }
}
