package com.danubetech.verifiablecredentials.validation;

import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.danubetech.verifiablecredentials.VerifiablePresentation;

import java.net.URI;
import java.net.URISyntaxException;

public class Validation {

    private static void validateTrue(boolean valid) throws IllegalStateException {

        if (! valid) throw new IllegalStateException();
    }

    private static void validateUrl(String uri) {

        try {

            if (! new URI(uri).isAbsolute()) throw new URISyntaxException("Not absolute.", uri);
        } catch (URISyntaxException ex) {

            throw new RuntimeException(ex.getMessage());
        }
    }

    private static void validateRun(Runnable runnable, String message) throws IllegalStateException {

        try {

            runnable.run();
        } catch (Exception ex) {

            throw new IllegalStateException(message);
        }
    }

    public static void validate(VerifiableCredential verifiableCredential) throws IllegalStateException {

        validateRun(() -> { validateTrue(verifiableCredential.getJsonObject() != null); }, "Bad or missing JSON object.");
        validateRun(() -> { validateTrue(verifiableCredential.getContexts().size() > 0); }, "Bad or missing '@context'.");
        validateRun(() -> { validateUrl(verifiableCredential.getContexts().get(0)); }, "@context must be a valid URI: " + verifiableCredential.getContexts().get(0));
        validateRun(() -> { validateTrue(VerifiableCredential.DEFAULT_JSONLD_CONTEXT.equals(verifiableCredential.getContexts().get(0))); }, "First value of @context must be https://www.w3.org/2018/credentials/v1: " + verifiableCredential.getContexts().get(0));
        validateRun(() -> { if (verifiableCredential.getId() != null) validateUrl(verifiableCredential.getId()); }, "'id' must be a valid URI.");

        validateRun(() -> { validateTrue(verifiableCredential.getTypes().size() > 0); }, "Bad or missing 'type'.");
        validateRun(() -> { validateTrue(verifiableCredential.getType().contains(VerifiableCredential.DEFAULT_JSONLD_CONTEXT)); }, "'type' must contain 'VerifiableCredential': " + verifiableCredential.getType());
        validateRun(() -> { validateTrue(verifiableCredential.getIssuer() != null); }, "Bad or missing 'issuer'.");
        validateRun(() -> { validateUrl(verifiableCredential.getIssuer()); }, "'issuer' must be a valid URI.");
        validateRun(() -> { validateTrue(verifiableCredential.getIssuanceDate() != null); }, "Bad or missing 'issuanceDate'.");
        validateRun(() -> { verifiableCredential.getExpirationDate(); }, "Bad 'expirationDate'.");
        validateRun(() -> { verifiableCredential.getCredentialSubject(); }, "Bad 'credentialSubject'.");
        validateRun(() -> { validateTrue(verifiableCredential.getCredentialSubject() != null); }, "Bad or missing 'credentialSubject'.");
    }

    public static void validate(VerifiablePresentation verifiablePresentation) throws IllegalStateException {

        validateRun(() -> { validateTrue(verifiablePresentation.getJsonObject() != null); }, "Bad or missing JSON object.");
        validateRun(() -> { validateTrue(verifiablePresentation.getContexts().size() > 0); }, "Bad or missing '@context'.");
        validateRun(() -> { validateUrl(verifiablePresentation.getContexts().get(0)); }, "@context must be a valid URI: " + verifiablePresentation.getContexts().get(0));
        validateRun(() -> { validateTrue(VerifiableCredential.DEFAULT_JSONLD_CONTEXT.equals(verifiablePresentation.getContexts().get(0))); }, "First value of @context must be https://www.w3.org/2018/credentials/v1: " + verifiablePresentation.getContexts().get(0));
        validateRun(() -> { if (verifiablePresentation.getId() != null) validateUrl(verifiablePresentation.getId()); }, "'id' must be a valid URI.");

        validateRun(() -> { validateTrue(verifiablePresentation.getType().size() > 0); }, "Bad or missing 'type'.");
        validateRun(() -> { validateTrue(verifiablePresentation.getType().contains(VerifiableCredential.DEFAULT_JSONLD_CONTEXT)); }, "type must contain VerifiablePresentation: " + verifiablePresentation.getType());
        validateRun(() -> { validateTrue(verifiablePresentation.getVerifiableCredential() != null); }, "Bad or missing 'verifiableCredential'.");
    }
}
