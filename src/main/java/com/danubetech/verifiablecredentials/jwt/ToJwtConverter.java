package com.danubetech.verifiablecredentials.jwt;

import com.danubetech.verifiablecredentials.CredentialSubject;
import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.danubetech.verifiablecredentials.VerifiablePresentation;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import net.minidev.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashMap;

public class ToJwtConverter {

    /*
     * from JSON-LD to JWT VC
     */

    public static JwtVerifiableCredential toJwtVerifiableCredential(VerifiableCredential verifiableCredential, String aud) {

        JWTClaimsSet.Builder jwtPayloadBuilder = new JWTClaimsSet.Builder();

        VerifiableCredential.Builder payloadVerifiableCredentialBuilder = VerifiableCredential.builder()
                .defaultContexts(false)
                .defaultTypes(false)
                .contexts(verifiableCredential.getContexts())
                .types(verifiableCredential.getTypes());
//            .template(verifiableCredential);

        URI id = verifiableCredential.getId();
        if (id != null) {
            jwtPayloadBuilder.jwtID(id.toString());
//            payloadVerifiableCredentialBuilder.id(null);
        }

        CredentialSubject credentialSubject = verifiableCredential.getCredentialSubject();
        if (credentialSubject != null) {
            if (credentialSubject.getId() != null) jwtPayloadBuilder.subject(credentialSubject.getId().toString());
//            payloadVerifiableCredentialBuilder.credentialSubject(null);
        }

        URI issuer = verifiableCredential.getIssuer();
        if (issuer != null) {
            jwtPayloadBuilder.issuer(issuer.toString());
//            payloadVerifiableCredentialBuilder.issuer(null);
        }

        Date issuanceDate = verifiableCredential.getIssuanceDate();
        if (issuanceDate != null) {
            jwtPayloadBuilder.notBeforeTime(issuanceDate);
//            payloadVerifiableCredentialBuilder.issuanceDate(null);
        }

        Date expirationDate = verifiableCredential.getExpirationDate();
        if (expirationDate != null) {
            jwtPayloadBuilder.expirationTime(expirationDate);
//            payloadVerifiableCredentialBuilder.expirationDate(null);
        }

        if (aud != null) {
            jwtPayloadBuilder.audience(aud);
        }

        VerifiableCredential payloadVerifiableCredential = payloadVerifiableCredentialBuilder.build();

        jwtPayloadBuilder.claim(JwtKeywords.JWT_CLAIM_VC, payloadVerifiableCredential.getJsonObject());

        JWTClaimsSet jwtPayload = jwtPayloadBuilder.build();

        return new JwtVerifiableCredential(jwtPayload, payloadVerifiableCredential, null, null);
    }

    public static JwtVerifiableCredential toJwtVerifiableCredential(VerifiableCredential verifiableCredential) {

        return toJwtVerifiableCredential(verifiableCredential, null);
    }

}
