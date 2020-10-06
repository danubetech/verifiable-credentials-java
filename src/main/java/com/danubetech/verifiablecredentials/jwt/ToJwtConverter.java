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

        JWTClaimsSet.Builder payloadBuilder = new JWTClaimsSet.Builder();

        VerifiableCredential.Builder payloadVerifiableCredentialBuilder = VerifiableCredential.builder()
            .template(verifiableCredential);

        String id = verifiableCredential.getId();
        if (id != null) {
            payloadBuilder.jwtID(id);
            payloadVerifiableCredentialBuilder.id(null);
        }

        CredentialSubject credentialSubject = verifiableCredential.getCredentialSubject();
        if (credentialSubject != null) {
            if (credentialSubject.getId() != null) payloadBuilder.subject(credentialSubject.getId());
            payloadVerifiableCredentialBuilder.credentialSubject(null);
        }

        String issuer = verifiableCredential.getIssuer();
        if (issuer != null) {
            payloadBuilder.issuer(issuer);
            payloadVerifiableCredentialBuilder.issuer(null);
        }

        Date issuanceDate = verifiableCredential.getIssuanceDate();
        if (issuanceDate != null) {
            payloadBuilder.notBeforeTime(issuanceDate);
            payloadVerifiableCredentialBuilder.issuanceDate(null);
        }

        Date expirationDate = verifiableCredential.getExpirationDate();
        if (expirationDate != null) {
            payloadBuilder.expirationTime(expirationDate);
            payloadVerifiableCredentialBuilder.expirationDate(null);
        }

        if (aud != null) {
            payloadBuilder.audience(aud);
        }

        VerifiableCredential payloadVerifiableCredential = payloadVerifiableCredentialBuilder.build();

        payloadBuilder.claim(JwtKeywords.JWT_CLAIM_VC, payloadVerifiableCredential.getJsonObject());

        JWTClaimsSet payload = payloadBuilder.build();

        return new JwtVerifiableCredential(payload, payloadVerifiableCredential, null, null);
    }

    public static JwtVerifiableCredential toJwtVerifiableCredential(VerifiableCredential verifiableCredential) {

        return toJwtVerifiableCredential(verifiableCredential, null);
    }

}
