package com.danubetech.verifiablecredentials.validation;

import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.danubetech.verifiablecredentials.VerifiablePresentation;

import java.net.URI;
import java.net.URISyntaxException;

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
        validateRun(() -> validateTrue(verifiablePresentation.getUnsupportedVerifiableCredentials().isEmpty()), "Unsupported type in 'verifiableCredential'.");
        validateRun(() -> validateTrue(verifiablePresentation.getVerifiableCredential() != null || verifiablePresentation.getJwtVerifiableCredentialString() != null), "Missing 'verifiableCredential'.");
    }
}
