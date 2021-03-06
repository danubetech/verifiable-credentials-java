package com.danubetech.verifiablecredentials.jwt;

import com.danubetech.verifiablecredentials.CredentialSubject;
import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.danubetech.verifiablecredentials.jsonld.VerifiableCredentialKeywords;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jwt.JWTClaimsSet;
import foundation.identity.jsonld.JsonLDKeywords;
import foundation.identity.jsonld.JsonLDUtils;

import java.io.IOException;
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
            JsonLDUtils.jsonLdRemove(payloadVerifiableCredential, VerifiableCredentialKeywords.JSONLD_TERM_ISSUER);
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

        Map<String, Object> vcClaimValue = new LinkedHashMap<>(payloadVerifiableCredential.getJsonObject());
        jwtPayloadBuilder.claim(JwtKeywords.JWT_CLAIM_VC, vcClaimValue);

        JWTClaimsSet jwtPayload = jwtPayloadBuilder.build();

        return new JwtVerifiableCredential(jwtPayload, payloadVerifiableCredential, null, null);
    }

    public static JwtVerifiableCredential toJwtVerifiableCredential(VerifiableCredential verifiableCredential) {

        return toJwtVerifiableCredential(verifiableCredential, null);
    }

}
