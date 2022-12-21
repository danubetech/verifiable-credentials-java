package com.danubetech.verifiablecredentials.jwt;

import com.danubetech.verifiablecredentials.CredentialSubject;
import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.danubetech.verifiablecredentials.VerifiablePresentation;
import com.danubetech.verifiablecredentials.jsonld.VerifiableCredentialKeywords;
import com.nimbusds.jwt.JWTClaimsSet;
import foundation.identity.jsonld.JsonLDKeywords;
import foundation.identity.jsonld.JsonLDUtils;

import java.net.URI;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class ToJwtConverter {

    /*
     * from JSON-LD to JWT VC
     */

    public static JwtVerifiableCredential toJwtVerifiableCredential(VerifiableCredential verifiableCredential, String aud) {

        JWTClaimsSet.Builder jwtPayloadBuilder = new JWTClaimsSet.Builder();

        VerifiableCredential payloadVerifiableCredential = VerifiableCredential.builder()
                .defaultContexts(false)
                .defaultTypes(false)
                .build();

        JsonLDUtils.jsonLdAddAll(payloadVerifiableCredential, verifiableCredential.getJsonObject());

        URI id = verifiableCredential.getId();
        if (id != null) {
            jwtPayloadBuilder.jwtID(id.toString());
            JsonLDUtils.jsonLdRemove(payloadVerifiableCredential, JsonLDKeywords.JSONLD_TERM_ID);
        }

        CredentialSubject credentialSubject = verifiableCredential.getCredentialSubject();
        if (credentialSubject != null) {
            if (verifiableCredential.getCredentialSubjects().size() > 1) {
                /*
                Per https://www.w3.org/TR/vc-data-model/#jwt-encoding:

                Implementers are warned that JWTs are not capable of encoding multiple subjects and are thus not capable of
                encoding a verifiable credential with more than one subject. JWTs might support multiple subjects in the
                future and implementers are advised to refer to the JSON Web Token Claim Registry for multi-subject JWT
                claim names or the Nested JSON Web Token specification.
                */
                throw new IllegalArgumentException("JWTs are not capable of encoding multiple subjects and are thus not capable of encoding a verifiable credential with more than one subject.");
            }

            if (credentialSubject.getId() != null) {
                jwtPayloadBuilder.subject(credentialSubject.getId().toString());
            }
            CredentialSubject payloadCredentialSubject = CredentialSubject.builder()
                    .base(credentialSubject)
                    .build();
            JsonLDUtils.jsonLdRemove(payloadCredentialSubject, JsonLDKeywords.JSONLD_TERM_ID);
            CredentialSubject.removeFromJsonLdObject(payloadVerifiableCredential);
            payloadCredentialSubject.addToJsonLDObject(payloadVerifiableCredential);
        }

        URI issuer = verifiableCredential.getIssuer();
        if (issuer != null) {
            jwtPayloadBuilder.issuer(issuer.toString());
            if ( verifiableCredential.getJsonObject().containsKey(VerifiableCredentialKeywords.JSONLD_TERM_ISSUER)
                    && !( verifiableCredential.getJsonObject().get(VerifiableCredentialKeywords.JSONLD_TERM_ISSUER) instanceof Map)) {
                JsonLDUtils.jsonLdRemove(payloadVerifiableCredential, VerifiableCredentialKeywords.JSONLD_TERM_ISSUER);
            }
        }

        Date issuanceDate = verifiableCredential.getIssuanceDate();
        if (issuanceDate != null) {
            jwtPayloadBuilder.notBeforeTime(issuanceDate);
            JsonLDUtils.jsonLdRemove(payloadVerifiableCredential, VerifiableCredentialKeywords.JSONLD_TERM_ISSUANCEDATE);
        }

        Date expirationDate = verifiableCredential.getExpirationDate();
        if (expirationDate != null) {
            jwtPayloadBuilder.expirationTime(expirationDate);
            JsonLDUtils.jsonLdRemove(payloadVerifiableCredential, VerifiableCredentialKeywords.JSONLD_TERM_EXPIRATIONDATE);
        }

        if (aud != null) {
            jwtPayloadBuilder.audience(aud);
        }

        Map<String, Object> vcContent = new LinkedHashMap<>(payloadVerifiableCredential.getJsonObject());
        jwtPayloadBuilder.claim(JwtKeywords.JWT_CLAIM_VC, vcContent);

        JWTClaimsSet jwtPayload = jwtPayloadBuilder.build();

        return new JwtVerifiableCredential(jwtPayload, payloadVerifiableCredential, null, null);
    }

    public static JwtVerifiableCredential toJwtVerifiableCredential(VerifiableCredential verifiableCredential) {

        return toJwtVerifiableCredential(verifiableCredential, null);
    }

    /*
     * from JSON-LD to JWT VP
     */

    public static JwtVerifiablePresentation toJwtVerifiablePresentation(VerifiablePresentation verifiablePresentation, String aud) {

        JWTClaimsSet.Builder jwtPayloadBuilder = new JWTClaimsSet.Builder();

        VerifiablePresentation payloadVerifiablePresentation = VerifiablePresentation.builder()
                .defaultContexts(false)
                .defaultTypes(false)
                .build();

        JsonLDUtils.jsonLdAddAll(payloadVerifiablePresentation, verifiablePresentation.getJsonObject());

        URI id = verifiablePresentation.getId();
        if (id != null) {
            jwtPayloadBuilder.jwtID(id.toString());
            JsonLDUtils.jsonLdRemove(payloadVerifiablePresentation, JsonLDKeywords.JSONLD_TERM_ID);
        }

        URI holder = verifiablePresentation.getHolder();
        if (holder != null) {
            jwtPayloadBuilder.issuer(holder.toString());
            jwtPayloadBuilder.subject(holder.toString());
            JsonLDUtils.jsonLdRemove(payloadVerifiablePresentation, VerifiableCredentialKeywords.JSONLD_TERM_HOLDER);
        }

        if (aud != null) {
            jwtPayloadBuilder.audience(aud);
        }

        Map<String, Object> vpContent = new LinkedHashMap<>(payloadVerifiablePresentation.getJsonObject());
        jwtPayloadBuilder.claim(JwtKeywords.JWT_CLAIM_VP, vpContent);

        JWTClaimsSet jwtPayload = jwtPayloadBuilder.build();

        return new JwtVerifiablePresentation(jwtPayload, payloadVerifiablePresentation, null, null);
    }

    public static JwtVerifiablePresentation toJwtVerifiablePresentation(VerifiablePresentation verifiablePresentation) {

        return toJwtVerifiablePresentation(verifiablePresentation, null);
    }
}
