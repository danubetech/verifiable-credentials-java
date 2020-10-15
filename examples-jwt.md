## Examples

Verifiable Credentials with JSON Web Tokens:

### Example Verifiable Credential

	eyJhbGciOiJFZERTQSJ9.eyJzdWIiOiJkaWQ6ZXhhbXBsZTplYmZlYjFmNzEyZWJjNmYxYzI3NmUxMmVjMjEiLCJuYmYiOjE1NjA3MTE0MTksImlzcyI6ImRpZDpleGFtcGxlOjc2ZTEyZWM3MTJlYmM2ZjFjMjIxZWJmZWIxZiIsImV4cCI6MTU2MDc5NzgxOSwidmMiOnsiQGNvbnRleHQiOlsiaHR0cHM6Ly93d3cudzMub3JnLzIwMTgvY3JlZGVudGlhbHMvdjEiLCJodHRwczovL3d3dy53My5vcmcvMjAxOC9jcmVkZW50aWFscy9leGFtcGxlcy92MSJdLCJ0eXBlIjpbIlZlcmlmaWFibGVDcmVkZW50aWFsIiwiVW5pdmVyc2l0eURlZ3JlZUNyZWRlbnRpYWwiXSwiY3JlZGVudGlhbFN1YmplY3QiOnsiY29sbGVnZSI6IlRlc3QgVW5pdmVyc2l0eSIsImRlZ3JlZSI6eyJuYW1lIjoiQmFjaGVsb3Igb2YgU2NpZW5jZSBhbmQgQXJ0cyIsInR5cGUiOiJCYWNoZWxvckRlZ3JlZSJ9fX0sImp0aSI6Imh0dHA6Ly9leGFtcGxlLmVkdS9jcmVkZW50aWFscy8zNzMyIn0.GDpCOlxiZ2isBQn151i5Pj2e-kUgkNg_wzxCPAfxLxtdOz4fpDimg81mNw3LsnO0G56AOTvD4SuzSQyj1cP3Bg

JWT Payload:

    {
      "sub": "did:example:ebfeb1f712ebc6f1c276e12ec21",
      "nbf": 1560711419,
      "iss": "did:example:76e12ec712ebc6f1c221ebfeb1f",
      "exp": 1560797819,
      "vc": {
        "@context": [
          "https://www.w3.org/2018/credentials/v1",
          "https://www.w3.org/2018/credentials/examples/v1"
        ],
        "type": [
          "VerifiableCredential",
          "UniversityDegreeCredential"
        ],
        "credentialSubject": {
          "college": "Test University",
          "degree": {
            "name": "Bachelor of Science and Arts",
            "type": "BachelorDegree"
          }
        }
      },
      "jti": "http://example.edu/credentials/3732"
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

### Example code (verifying)

    byte[] testEd25519PublicKey = Hex.decodeHex("de8777a28f8da1a74e7a13090ed974d879bf692d001cddee16e4cc9f84b60580".toCharArray());

    String jwt = "eyJhbGciOiJFZERTQSJ9.eyJzdWIiOiJkaWQ6ZXhhbXBsZTplYmZlYjFmNzEyZWJjNmYxYzI3NmUxMmVjMjEiLCJuYmYiOjE1NjA3MTE0MTksImlzcyI6ImRpZDpleGFtcGxlOjc2ZTEyZWM3MTJlYmM2ZjFjMjIxZWJmZWIxZiIsImV4cCI6MTU2MDc5NzgxOSwidmMiOnsiQGNvbnRleHQiOlsiaHR0cHM6Ly93d3cudzMub3JnLzIwMTgvY3JlZGVudGlhbHMvdjEiLCJodHRwczovL3d3dy53My5vcmcvMjAxOC9jcmVkZW50aWFscy9leGFtcGxlcy92MSJdLCJ0eXBlIjpbIlZlcmlmaWFibGVDcmVkZW50aWFsIiwiVW5pdmVyc2l0eURlZ3JlZUNyZWRlbnRpYWwiXSwiY3JlZGVudGlhbFN1YmplY3QiOnsiY29sbGVnZSI6IlRlc3QgVW5pdmVyc2l0eSIsImRlZ3JlZSI6eyJuYW1lIjoiQmFjaGVsb3Igb2YgU2NpZW5jZSBhbmQgQXJ0cyIsInR5cGUiOiJCYWNoZWxvckRlZ3JlZSJ9fX0sImp0aSI6Imh0dHA6Ly9leGFtcGxlLmVkdS9jcmVkZW50aWFscy8zNzMyIn0.GDpCOlxiZ2isBQn151i5Pj2e-kUgkNg_wzxCPAfxLxtdOz4fpDimg81mNw3LsnO0G56AOTvD4SuzSQyj1cP3Bg";
    JwtVerifiableCredential jwtVerifiableCredential = JwtVerifiableCredential.fromCompactSerialization(jwt);
    System.out.println(jwtVerifiableCredential.verify_Ed25519_EdDSA(testEd25519PublicKey));

    String jwtPayload = jwtVerifiableCredential.getPayload().toString();
    String jwtPayloadVerifiableCredential = jwtVerifiableCredential.getPayloadObject().toJson(true);
    System.out.println(jwtPayload);
    System.out.println(jwtPayloadVerifiableCredential);

    VerifiableCredential verifiableCredential = FromJwtConverter.fromJwtVerifiableCredential(jwtVerifiableCredential);
    System.out.println(verifiableCredential.toJson(true));
