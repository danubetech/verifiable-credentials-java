package com.danubetech.verifiablecredentials.jwt;

import com.danubetech.verifiablecredentials.CredentialSubject;
import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.danubetech.verifiablecredentials.VerifiablePresentation;
import com.nimbusds.jwt.JWTClaimsSet;

import java.io.IOException;
import java.net.URI;
import java.util.Date;

public class FromJwtConverter {

    /*
     * from JWT to JSON-LD VC
     */

    public static VerifiableCredential fromJwtVerifiableCredential(JwtVerifiableCredential jwtVerifiableCredential) {

        VerifiableCredential payloadVerifiableCredential = VerifiableCredential.fromJson(jwtVerifiableCredential.getPayloadObject().toString());
        CredentialSubject payloadCredentialSubject = payloadVerifiableCredential.getCredentialSubject();
        CredentialSubject.removeFromJsonLdObject(payloadVerifiableCredential);

        VerifiableCredential.Builder verifiableCredentialBuilder = VerifiableCredential.builder()
                .base(payloadVerifiableCredential)
                .defaultContexts(false)
                .defaultTypes(false);

        JWTClaimsSet payload = jwtVerifiableCredential.getPayload();

        String jwtId = payload.getJWTID();
        if (jwtId != null) {
            verifiableCredentialBuilder.id(URI.create(jwtId));
        }

        String subject = payload.getSubject();
        if (subject != null) {
            CredentialSubject credentialSubject = CredentialSubject.builder()
                    .base(payloadCredentialSubject)
                    .id(URI.create(subject))
                    .build();
            verifiableCredentialBuilder.credentialSubject(credentialSubject);
        }

        String issuer = payload.getIssuer();
        if (issuer != null) {
            verifiableCredentialBuilder.issuer(URI.create(issuer));
        }

        Date notBeforeTime = payload.getNotBeforeTime();
        if (notBeforeTime != null) {
            verifiableCredentialBuilder.issuanceDate(notBeforeTime);
        }

        Date expirationTime = payload.getExpirationTime();
        if (expirationTime != null) {
            verifiableCredentialBuilder.expirationDate(expirationTime);
        }

        VerifiableCredential verifiableCredential = verifiableCredentialBuilder.build();

        return verifiableCredential;
    }

    public static VerifiablePresentation fromJwtVerifiableCredentialToVerifiablePresentation(JwtVerifiableCredential jwtVerifiableCredential) {

        VerifiableCredential verifiableCredential = VerifiableCredential.fromJson(jwtVerifiableCredential.getCompactSerialization());

        VerifiablePresentation verifiablePresentation = VerifiablePresentation.builder()
                .verifiableCredential(verifiableCredential)
                .build();

        return verifiablePresentation;
    }
}
