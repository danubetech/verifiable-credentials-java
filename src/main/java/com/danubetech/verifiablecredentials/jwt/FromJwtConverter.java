package com.danubetech.verifiablecredentials.jwt;

import com.danubetech.verifiablecredentials.CredentialSubject;
import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.danubetech.verifiablecredentials.VerifiableCredentialV2;
import com.danubetech.verifiablecredentials.VerifiablePresentation;
import com.danubetech.verifiablecredentials.VerifiablePresentationV2;
import com.danubetech.verifiablecredentials.jsonld.VerifiableCredentialKeywords;
import com.nimbusds.jwt.JWTClaimsSet;
import foundation.identity.jsonld.JsonLDUtils;

import java.net.URI;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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

        return verifiableCredentialBuilder.build();
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

        return verifiablePresentationBuilder.build();
    }

    public static VerifiablePresentation fromJwtVerifiableCredentialToVerifiablePresentation(JwtVerifiableCredential jwtVerifiableCredential) {

        String jwtVerifiableCredentialCompactSerialization = jwtVerifiableCredential.getCompactSerialization();

        VerifiablePresentation verifiablePresentation = VerifiablePresentation.builder()
                .build();

        JsonLDUtils.jsonLdAddAsJsonArray(verifiablePresentation, VerifiableCredentialKeywords.JSONLD_TERM_VERIFIABLECREDENTIAL, Collections.singletonList(jwtVerifiableCredentialCompactSerialization));

        return verifiablePresentation;
    }


    public static VerifiableCredentialV2 fromJwtVerifiableCredentialV2(JwtVerifiableCredentialV2 jwtVerifiableCredential) {

        VerifiableCredentialV2 payloadVerifiableCredential = VerifiableCredentialV2.fromJson(jwtVerifiableCredential.getPayloadObject().toString());

        List<CredentialSubject> credentialSubjects = payloadVerifiableCredential.getCredentialSubjectAsList();

        CredentialSubject.removeFromJsonLdObject(payloadVerifiableCredential);

        VerifiableCredentialV2.Builder<? extends VerifiableCredentialV2.Builder<?>> verifiableCredentialBuilder = VerifiableCredentialV2.builder()
                .base(payloadVerifiableCredential)
                .defaultContexts(false)
                .defaultTypes(false);

        JWTClaimsSet payload = jwtVerifiableCredential.getPayload();

        String jwtId = payload.getJWTID();
        if (jwtId != null && payloadVerifiableCredential.getId() == null) {
            verifiableCredentialBuilder.id(URI.create(jwtId));
        }

        for(CredentialSubject cs : credentialSubjects) {
            CredentialSubject.Builder<? extends CredentialSubject.Builder<?>> credentialSubjectBuilder = CredentialSubject.builder()
                    .base(cs);

            String subject = payload.getSubject();
            if (subject != null && cs.getId() == null) {
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
        if (notBeforeTime != null && payloadVerifiableCredential.getValidFrom() == null) {
            verifiableCredentialBuilder.validFrom(notBeforeTime);
        }

        Date expirationTime = payload.getExpirationTime();
        if (expirationTime != null && payloadVerifiableCredential.getValidUntil() == null) {
            verifiableCredentialBuilder.validUntil(expirationTime);
        }

        return verifiableCredentialBuilder.build();
    }

    public static VerifiablePresentationV2 fromJwtVerifiablePresentationV2(JwtVerifiablePresentationV2 jwtVerifiablePresentation) {

        VerifiablePresentationV2 payloadVerifiablePresentation = VerifiablePresentationV2.fromJson(jwtVerifiablePresentation.getPayloadObject().toString());

        VerifiablePresentationV2.Builder<? extends VerifiablePresentationV2.Builder<?>> verifiablePresentationBuilder = VerifiablePresentationV2.builder()
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

        return  verifiablePresentationBuilder.build();
    }

}
