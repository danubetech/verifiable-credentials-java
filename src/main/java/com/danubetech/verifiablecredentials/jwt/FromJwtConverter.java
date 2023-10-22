package com.danubetech.verifiablecredentials.jwt;

import com.danubetech.verifiablecredentials.CredentialSubject;
import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.danubetech.verifiablecredentials.VerifiablePresentation;
import com.danubetech.verifiablecredentials.jsonld.VerifiableCredentialKeywords;
import com.nimbusds.jwt.JWTClaimsSet;
import foundation.identity.jsonld.JsonLDUtils;

import java.net.URI;
import java.util.Collections;
import java.util.Date;

public class FromJwtConverter {

    /*
     * from JWT to JSON-LD VC
     */

    public static VerifiableCredential fromJwtVerifiableCredential(JwtVerifiableCredential jwtVerifiableCredential) {

        VerifiableCredential payloadVerifiableCredential = VerifiableCredential.fromJson(jwtVerifiableCredential.getPayloadObject().toString());
        CredentialSubject payloadCredentialSubject = payloadVerifiableCredential.getCredentialSubject();
        CredentialSubject.removeFromJsonLdObject(payloadVerifiableCredential);

        VerifiableCredential.Builder<? extends VerifiableCredential.Builder<?>> verifiableCredentialBuilder = VerifiableCredential.builder()
                .base(payloadVerifiableCredential)
                .defaultContexts(false)
                .defaultTypes(false);

        JWTClaimsSet payload = jwtVerifiableCredential.getPayload();

        String jwtId = payload.getJWTID();
        if (jwtId != null && payloadVerifiableCredential.getId() == null) {
            verifiableCredentialBuilder.id(URI.create(jwtId));
        }

        if (payloadCredentialSubject != null) {

            CredentialSubject.Builder<? extends CredentialSubject.Builder<?>> credentialSubjectBuilder = CredentialSubject.builder()
                    .base(payloadCredentialSubject);

            String subject = payload.getSubject();
            if (subject != null && payloadCredentialSubject.getId() == null) {
                credentialSubjectBuilder.id(URI.create(subject));
            }

            CredentialSubject credentialSubject = credentialSubjectBuilder.build();

            verifiableCredentialBuilder.credentialSubject(credentialSubject);
        }

        String issuer = payload.getIssuer();
        if (issuer != null && payloadVerifiableCredential.getIssuer() == null) {
            verifiableCredentialBuilder.issuer(URI.create(issuer));
        }

        Date notBeforeTime = payload.getNotBeforeTime();
        if (notBeforeTime != null && payloadVerifiableCredential.getIssuanceDate() == null) {
            verifiableCredentialBuilder.issuanceDate(notBeforeTime);
        }

        Date expirationTime = payload.getExpirationTime();
        if (expirationTime != null && payloadVerifiableCredential.getExpirationDate() == null) {
            verifiableCredentialBuilder.expirationDate(expirationTime);
        }

        VerifiableCredential verifiableCredential = verifiableCredentialBuilder.build();

        return verifiableCredential;
    }

    public static VerifiablePresentation fromJwtVerifiablePresentation(JwtVerifiablePresentation jwtVerifiablePresentation) {

        VerifiablePresentation payloadVerifiablePresentation = VerifiablePresentation.fromJson(jwtVerifiablePresentation.getPayloadObject().toString());

        VerifiablePresentation.Builder<? extends VerifiablePresentation.Builder<?>> verifiablePresentationBuilder = VerifiablePresentation.builder()
                .base(payloadVerifiablePresentation)
                .defaultContexts(false)
                .defaultTypes(false);

        JWTClaimsSet payload = jwtVerifiablePresentation.getPayload();

        String jwtId = payload.getJWTID();
        if (jwtId != null && payloadVerifiablePresentation.getId() == null) {
            verifiablePresentationBuilder.id(URI.create(jwtId));
        }

        String issuer = payload.getIssuer();
        if (issuer != null && payloadVerifiablePresentation.getHolder() == null) {
            verifiablePresentationBuilder.holder(URI.create(issuer));
        }

        VerifiablePresentation verifiablePresentation = verifiablePresentationBuilder.build();

        return verifiablePresentation;
    }

    public static VerifiablePresentation fromJwtVerifiableCredentialToVerifiablePresentation(JwtVerifiableCredential jwtVerifiableCredential) {

        String jwtVerifiableCredentialCompactSerialization = jwtVerifiableCredential.getCompactSerialization();

        VerifiablePresentation verifiablePresentation = VerifiablePresentation.builder()
                .build();

        JsonLDUtils.jsonLdAddAsJsonArray(verifiablePresentation, VerifiableCredentialKeywords.JSONLD_TERM_VERIFIABLECREDENTIAL, Collections.singletonList(jwtVerifiableCredentialCompactSerialization));

        return verifiablePresentation;
    }
}
