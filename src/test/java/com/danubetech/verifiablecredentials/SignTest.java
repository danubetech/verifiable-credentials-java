package com.danubetech.verifiablecredentials;

import info.weboftrust.ldsignatures.LdSignature;
import info.weboftrust.ldsignatures.signer.RsaSignature2018LdSigner;
import info.weboftrust.ldsignatures.suites.SignatureSuites;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SignTest {

    @Test
    void testSign() throws Exception {

        VerifiableCredential verifiableCredential = new VerifiableCredential();
        verifiableCredential.getContext().add("https://trafi.fi/credentials/v1");
        verifiableCredential.getType().add("DriversLicenseCredential");
        verifiableCredential.setIssuer("did:sov:1yvXbmgPoUm4dl66D7KhyD");
        verifiableCredential.setIssuanceDate(VerifiableCredential.DATE_FORMAT.parse("2017-10-24T05:33:31Z"));

        verifiableCredential.setCredentialSubject("did:sov:21tDAKCERh95uGgKbJNHYp");
        LinkedHashMap<String, Object> jsonLdCredentialSubject = verifiableCredential.getJsonLdCredentialSubject();
        LinkedHashMap<String, Object> jsonLdDriversLicenseObject = new LinkedHashMap<>();
        jsonLdDriversLicenseObject.put("licenseClass", "trucks");
        jsonLdCredentialSubject.put("driversLicense", jsonLdDriversLicenseObject);

        URI creator = URI.create("did:sov:1yvXbmgPoUm4dl66D7KhyD#keys-1");
        String created = "2018-01-01T21:19:10Z";
        String domain = null;
        String nonce = "c0ae1c8e-c7e7-469f-b252-86e6a0e7387e";

        RsaSignature2018LdSigner signer = new RsaSignature2018LdSigner(TestUtil.testRSAPrivateKey);
        signer.setCreator(creator);
        signer.setCreated(created);
        signer.setDomain(domain);
        signer.setNonce(nonce);
        LdSignature ldSignature = signer.sign(verifiableCredential.getJsonLdObject());

        assertEquals(SignatureSuites.SIGNATURE_SUITE_RSASIGNATURE2018.getTerm(), ldSignature.getType());
        assertEquals(creator, ldSignature.getCreator());
        assertEquals(created, ldSignature.getCreated());
        assertEquals(domain, ldSignature.getDomain());
        assertEquals(nonce, ldSignature.getNonce());
        assertEquals("eyJjcml0IjpbImI2NCJdLCJiNjQiOmZhbHNlLCJhbGciOiJSUzI1NiJ9..O_-LVz0SghpFOlO0xU1d7dk8rXpoQXpd1dBdGuXyjqE72bSZOn_C65M-_ZasNtgt0AxDmkdEFhb1Ji5hZmkuIm9qhMnZDiMMcn6FuMd0eQyYR2OqLOcxOLVdCjgJF4s7M_Mpl7CgZ1w5QnqIEgCp3kzYskvmJrqOsib4a-VXh7xAyA4Lo9edK02wF7t5BrjO6Yz9xaHjB2V9A-Vh3UEXj8kc3cE3M7rMiPlmAiZRdKpl9lVL0ANW1Y0sMPceL3CPoOsjTomfOnGxXylfnhemnVAjpVs5HhG4NlzBf9FT-YPlxfmzVSw1P6epIGSMq4nVXuRXlWD-E_4KcG6teUD3jQ", ldSignature.getSignatureValue());
    }
}
