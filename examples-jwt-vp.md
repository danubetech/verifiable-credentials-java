## Examples

Verifiable Credentials with JSON Web Tokens:

### Example Verifiable Presentation

	eyJhbGciOiJFZERTQSJ9.eyJuYmYiOjE2MDI3NjQ4MDEsImlzcyI6ImRpZDpleGFtcGxlOmViZmViMWY3MTJlYmM2ZjFjMjc2ZTEyZWMyMSIsInZwIjp7IkBjb250ZXh0IjoiaHR0cHM6Ly93d3cudzMub3JnLzIwMTgvY3JlZGVudGlhbHMvdjEiLCJ0eXBlIjoiVmVyaWZpYWJsZVByZXNlbnRhdGlvbiIsInZlcmlmaWFibGVDcmVkZW50aWFsIjoiZXlKaGJHY2lPaUpGWkVSVFFTSjkuZXlKemRXSWlPaUprYVdRNlpYaGhiWEJzWlRwbFltWmxZakZtTnpFeVpXSmpObVl4WXpJM05tVXhNbVZqTWpFaUxDSnVZbVlpT2pFMU5qQTNNVEUwTVRrc0ltbHpjeUk2SW1ScFpEcGxlR0Z0Y0d4bE9qYzJaVEV5WldNM01USmxZbU0yWmpGak1qSXhaV0ptWldJeFppSXNJbVY0Y0NJNk1UVTJNRGM1TnpneE9Td2lkbU1pT25zaVFHTnZiblJsZUhRaU9sc2lhSFIwY0hNNkx5OTNkM2N1ZHpNdWIzSm5Mekl3TVRndlkzSmxaR1Z1ZEdsaGJITXZkakVpTENKb2RIUndjem92TDNkM2R5NTNNeTV2Y21jdk1qQXhPQzlqY21Wa1pXNTBhV0ZzY3k5bGVHRnRjR3hsY3k5Mk1TSmRMQ0owZVhCbElqcGJJbFpsY21sbWFXRmliR1ZEY21Wa1pXNTBhV0ZzSWl3aVZXNXBkbVZ5YzJsMGVVUmxaM0psWlVOeVpXUmxiblJwWVd3aVhTd2lZM0psWkdWdWRHbGhiRk4xWW1wbFkzUWlPbnNpWTI5c2JHVm5aU0k2SWxSbGMzUWdWVzVwZG1WeWMybDBlU0lzSW1SbFozSmxaU0k2ZXlKdVlXMWxJam9pUW1GamFHVnNiM0lnYjJZZ1UyTnBaVzVqWlNCaGJtUWdRWEowY3lJc0luUjVjR1VpT2lKQ1lXTm9aV3h2Y2tSbFozSmxaU0o5Zlgwc0ltcDBhU0k2SW1oMGRIQTZMeTlsZUdGdGNHeGxMbVZrZFM5amNtVmtaVzUwYVdGc2N5OHpOek15SW4wLkdEcENPbHhpWjJpc0JRbjE1MWk1UGoyZS1rVWdrTmdfd3p4Q1BBZnhMeHRkT3o0ZnBEaW1nODFtTnczTHNuTzBHNTZBT1R2RDRTdXpTUXlqMWNQM0JnIn0sImlhdCI6MTYwMjc2NDgwMSwianRpIjoidXJuOnV1aWQ6ZWM3NDE1NTYtM2Y2ZS00ODkxLWJlNTQtNzRjMjNmZDkzNjA1In0.kv4Votk1DpFT4Irr-v85W3lorPo9r2p9qwdDrq4kH_veo7qTKtiNhC7BshUwP7zDN5_gD3GTr68OoNks2LoXDw

JWT Payload:

    {
        "nbf": 1602764801,
        "iss": "did:example:ebfeb1f712ebc6f1c276e12ec21",
        "vp": {
            "@context": "https://www.w3.org/2018/credentials/v1",
            "type": "VerifiablePresentation",
            "verifiableCredential": [
                "eyJhbGciOiJFZERTQSJ9.eyJzdWIiOiJkaWQ6ZXhhbXBsZTplYmZlYjFmNzEyZWJjNmYxYzI3NmUxMmVjMjEiLCJuYmYiOjE1NjA3MTE0MTksImlzcyI6ImRpZDpleGFtcGxlOjc2ZTEyZWM3MTJlYmM2ZjFjMjIxZWJmZWIxZiIsImV4cCI6MTU2MDc5NzgxOSwidmMiOnsiQGNvbnRleHQiOlsiaHR0cHM6Ly93d3cudzMub3JnLzIwMTgvY3JlZGVudGlhbHMvdjEiLCJodHRwczovL3d3dy53My5vcmcvMjAxOC9jcmVkZW50aWFscy9leGFtcGxlcy92MSJdLCJ0eXBlIjpbIlZlcmlmaWFibGVDcmVkZW50aWFsIiwiVW5pdmVyc2l0eURlZ3JlZUNyZWRlbnRpYWwiXSwiY3JlZGVudGlhbFN1YmplY3QiOnsiY29sbGVnZSI6IlRlc3QgVW5pdmVyc2l0eSIsImRlZ3JlZSI6eyJuYW1lIjoiQmFjaGVsb3Igb2YgU2NpZW5jZSBhbmQgQXJ0cyIsInR5cGUiOiJCYWNoZWxvckRlZ3JlZSJ9fX0sImp0aSI6Imh0dHA6Ly9leGFtcGxlLmVkdS9jcmVkZW50aWFscy8zNzMyIn0.GDpCOlxiZ2isBQn151i5Pj2e-kUgkNg_wzxCPAfxLxtdOz4fpDimg81mNw3LsnO0G56AOTvD4SuzSQyj1cP3Bg"
            ]
        },
        "iat": 1602764801,
        "jti": "urn:uuid:ec741556-3f6e-4891-be54-74c23fd93605"
    }

### Example code (signing)

        Map<String, Object> claims = new LinkedHashMap<>();
        Map<String, Object> degree = new LinkedHashMap<String, Object>();
        degree.put("name", "Bachelor of Science and Arts");
        degree.put("type", "BachelorDegree");
        claims.put("college", "Test University");
        claims.put("degree", degree);

        CredentialSubject credentialSubject = CredentialSubject.builder()
                .id(URI.create("did:example:ebfeb1f712ebc6f1c276e12ec21"))
                .claims(claims)
                .build();

        VerifiableCredential verifiableCredential = VerifiableCredential.builder()
                .context(VerifiableCredentialContexts.JSONLD_CONTEXT_W3C_2018_CREDENTIALS_EXAMPLES_V1)
                .type("UniversityDegreeCredential")
                .id(URI.create("http://example.edu/credentials/3732"))
                .issuer(URI.create("did:example:76e12ec712ebc6f1c221ebfeb1f"))
                .issuanceDate(JsonLDUtils.stringToDate("2019-06-16T18:56:59Z"))
                .expirationDate(JsonLDUtils.stringToDate("2019-06-17T18:56:59Z"))
                .credentialSubject(credentialSubject)
                .build();

        byte[] testEd25519PrivateKey = Hex.decodeHex("984b589e121040156838303f107e13150be4a80fc5088ccba0b0bdc9b1d89090de8777a28f8da1a74e7a13090ed974d879bf692d001cddee16e4cc9f84b60580".toCharArray());

        JwtVerifiableCredential jwtVerifiableCredential = ToJwtConverter.toJwtVerifiableCredential(verifiableCredential);

        String jwtPayload = jwtVerifiableCredential.getPayload().toString();
        System.out.println(jwtPayload);

        String jwtString = jwtVerifiableCredential.sign_Ed25519_EdDSA(testEd25519PrivateKey);
        System.out.println(jwtString);

        JwtVerifiablePresentation jwtVerifiablePresentation = JwtVerifiablePresentation.fromJwtVerifiableCredential(jwtVerifiableCredential);
        String jwtPayload2 = jwtVerifiablePresentation.getPayload().toString();
        System.out.println(jwtPayload2);

        String jwtString2 = jwtVerifiablePresentation.sign_Ed25519_EdDSA(testEd25519PrivateKey);
        System.out.println(jwtString2);
