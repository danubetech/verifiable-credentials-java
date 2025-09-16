package com.danubetech.verifiablecredentials.jwt;

import com.danubetech.verifiablecredentials.CredentialSubject;
import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.danubetech.verifiablecredentials.VerifiableCredentialV2;
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

    public static JwtVerifiableCredential toJwtVerifiableCredential(VerifiableCredential verifiableCredential, String aud, boolean preserveVerifiableCredentialProperties, boolean includeIat) {

        JWTClaimsSet.Builder jwtPayloadBuilder = new JWTClaimsSet.Builder();

        VerifiableCredential payloadVerifiableCredential = VerifiableCredential.builder()
                .defaultContexts(false)
                .defaultTypes(false)
                .build();

        JsonLDUtils.jsonLdAddAll(payloadVerifiableCredential, verifiableCredential.getJsonObject());

        URI id = verifiableCredential.getId();
        if (id != null) {
            jwtPayloadBuilder.jwtID(id.toString());
            if (!preserveVerifiableCredentialProperties) {
                JsonLDUtils.jsonLdRemove(payloadVerifiableCredential, JsonLDKeywords.JSONLD_TERM_ID);
            }
        }

        CredentialSubject credentialSubject = verifiableCredential.getCredentialSubject();
        if (credentialSubject != null && credentialSubject.getId() != null) {
            jwtPayloadBuilder.subject(credentialSubject.getId().toString());
            if (!preserveVerifiableCredentialProperties) {
                CredentialSubject payloadCredentialSubject = CredentialSubject.builder()
                        .base(credentialSubject)
                        .build();
                JsonLDUtils.jsonLdRemove(payloadCredentialSubject, JsonLDKeywords.JSONLD_TERM_ID);
                CredentialSubject.removeFromJsonLdObject(payloadVerifiableCredential);
                payloadCredentialSubject.addToJsonLDObject(payloadVerifiableCredential);
            }
        }

        URI issuer = verifiableCredential.getIssuer();
        if (issuer != null) {
            jwtPayloadBuilder.issuer(issuer.toString());
            if (!preserveVerifiableCredentialProperties) {
                JsonLDUtils.jsonLdRemove(payloadVerifiableCredential, VerifiableCredentialKeywords.JSONLD_TERM_ISSUER);
            }
        }

        Date issuanceDate = verifiableCredential.getIssuanceDate();
        if (issuanceDate != null) {
            jwtPayloadBuilder.notBeforeTime(issuanceDate);
            if (includeIat) jwtPayloadBuilder.issueTime(issuanceDate);
            if (!preserveVerifiableCredentialProperties) {
                JsonLDUtils.jsonLdRemove(payloadVerifiableCredential, VerifiableCredentialKeywords.JSONLD_TERM_ISSUANCEDATE);
            }
        }

        Date expirationDate = verifiableCredential.getExpirationDate();
        if (expirationDate != null) {
            jwtPayloadBuilder.expirationTime(expirationDate);
            if (!preserveVerifiableCredentialProperties) {
                JsonLDUtils.jsonLdRemove(payloadVerifiableCredential, VerifiableCredentialKeywords.JSONLD_TERM_EXPIRATIONDATE);
            }
        }

        if (aud != null) {
            jwtPayloadBuilder.audience(aud);
        }

        Map<String, Object> vcContent = new LinkedHashMap<>(payloadVerifiableCredential.getJsonObject());
        jwtPayloadBuilder.claim(JwtKeywords.JWT_CLAIM_VC, vcContent);

        JWTClaimsSet jwtPayload = jwtPayloadBuilder.build();

        return new JwtVerifiableCredential(jwtPayload, payloadVerifiableCredential, null, null);
    }

    public static JwtVerifiableCredential toJwtVerifiableCredential(VerifiableCredential verifiableCredential, String aud) {

        return toJwtVerifiableCredential(verifiableCredential, null, false, false);
    }

    public static JwtVerifiableCredential toJwtVerifiableCredential(VerifiableCredential verifiableCredential, boolean preserveVerifiableCredentialProperties, boolean includeIat) {

        return toJwtVerifiableCredential(verifiableCredential, null, preserveVerifiableCredentialProperties, includeIat);
    }

    public static JwtVerifiableCredential toJwtVerifiableCredential(VerifiableCredential verifiableCredential) {

        return toJwtVerifiableCredential(verifiableCredential, null, false, false);
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


    /*
     * from JSON-LD to JWT VCDM V2
     */

    public static JwtVerifiableCredentialV2 toJwtVerifiableCredentialV2(VerifiableCredentialV2 verifiableCredential, String aud, boolean preserveVerifiableCredentialProperties, boolean includeIat) {

        JWTClaimsSet.Builder jwtPayloadBuilder = new JWTClaimsSet.Builder();

        VerifiableCredentialV2 payloadVerifiableCredential = VerifiableCredentialV2.builder()
                .defaultContexts(false)
                .defaultTypes(false)
                .build();

        JsonLDUtils.jsonLdAddAll(payloadVerifiableCredential, verifiableCredential.getJsonObject());

        URI id = verifiableCredential.getId();
        if (id != null) {
            jwtPayloadBuilder.jwtID(id.toString());
            if (!preserveVerifiableCredentialProperties) {
                JsonLDUtils.jsonLdRemove(payloadVerifiableCredential, JsonLDKeywords.JSONLD_TERM_ID);
            }
        }

        CredentialSubject credentialSubject = verifiableCredential.getCredentialSubject();
        if (credentialSubject != null && credentialSubject.getId() != null) {
            jwtPayloadBuilder.subject(credentialSubject.getId().toString());
            if (!preserveVerifiableCredentialProperties) {
                CredentialSubject payloadCredentialSubject = CredentialSubject.builder()
                        .base(credentialSubject)
                        .build();
                JsonLDUtils.jsonLdRemove(payloadCredentialSubject, JsonLDKeywords.JSONLD_TERM_ID);
                CredentialSubject.removeFromJsonLdObject(payloadVerifiableCredential);
                payloadCredentialSubject.addToJsonLDObject(payloadVerifiableCredential);
            }
        }

        URI issuer = verifiableCredential.getIssuerUri();
        if (issuer != null) {
            jwtPayloadBuilder.issuer(issuer.toString());
            if (!preserveVerifiableCredentialProperties) {
                JsonLDUtils.jsonLdRemove(payloadVerifiableCredential, VerifiableCredentialKeywords.JSONLD_TERM_ISSUER);
            }
        }

        Date issuanceDate = verifiableCredential.getValidFrom();
        if (issuanceDate != null) {
            jwtPayloadBuilder.notBeforeTime(issuanceDate);
            if (includeIat) jwtPayloadBuilder.issueTime(issuanceDate);
            if (!preserveVerifiableCredentialProperties) {
                JsonLDUtils.jsonLdRemove(payloadVerifiableCredential, VerifiableCredentialKeywords.JSONLD_TERM_VALIDFROM);
            }
        }

        Date expirationDate = verifiableCredential.getValidUntil();
        if (expirationDate != null) {
            jwtPayloadBuilder.expirationTime(expirationDate);
            if (!preserveVerifiableCredentialProperties) {
                JsonLDUtils.jsonLdRemove(payloadVerifiableCredential, VerifiableCredentialKeywords.JSONLD_TERM_VALIDUNTIL);
            }
        }

        if (aud != null) {
            jwtPayloadBuilder.audience(aud);
        }

        Map<String, Object> vcContent = new LinkedHashMap<>(payloadVerifiableCredential.getJsonObject());
        jwtPayloadBuilder.claim(JwtKeywords.JWT_CLAIM_VC, vcContent);

        JWTClaimsSet jwtPayload = jwtPayloadBuilder.build();

        return new JwtVerifiableCredentialV2(jwtPayload, payloadVerifiableCredential, null, null);
    }

    public static JwtVerifiableCredentialV2 toJwtVerifiableCredentialV2(VerifiableCredentialV2 verifiableCredential, String aud) {

        return toJwtVerifiableCredentialV2(verifiableCredential, null, false, false);
    }

    public static JwtVerifiableCredentialV2 toJwtVerifiableCredentialV2(VerifiableCredentialV2 verifiableCredential, boolean preserveVerifiableCredentialProperties, boolean includeIat) {

        return toJwtVerifiableCredentialV2(verifiableCredential, null, preserveVerifiableCredentialProperties, includeIat);
    }

    public static JwtVerifiableCredentialV2 toJwtVerifiableCredentialV2(VerifiableCredentialV2 verifiableCredential) {

        return toJwtVerifiableCredentialV2(verifiableCredential, null, false, false);
    }
}
