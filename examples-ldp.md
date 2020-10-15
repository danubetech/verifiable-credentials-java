## Examples

Verifiable Credentials with Linked Data Proofs:

### Example Verifiable Credential

    {
      "@context" : [ "https://www.w3.org/2018/credentials/v1", "https://www.w3.org/2018/credentials/examples/v1" ],
      "type" : [ "VerifiableCredential", "UniversityDegreeCredential" ],
      "id" : "http://example.edu/credentials/3732",
      "issuer" : "did:example:76e12ec712ebc6f1c221ebfeb1f",
      "issuanceDate" : "2019-06-16T18:56:59Z",
      "expirationDate" : "2019-06-17T18:56:59Z",
      "credentialSubject" : {
        "id" : "did:example:ebfeb1f712ebc6f1c276e12ec21",
        "college" : "Test University",
        "degree" : {
          "name" : "Bachelor of Science and Arts",
          "type" : "BachelorDegree"
        }
      },
      "proof" : {
        "type" : "Ed25519Signature2018",
        "created" : "2020-10-15T09:44:25Z",
        "domain" : "example.com",
        "nonce" : "343s$FSFDa-",
        "proofPurpose" : "assertionMethod",
        "verificationMethod" : "did:example:76e12ec712ebc6f1c221ebfeb1f#keys-1",
        "jws" : "eyJiNjQiOmZhbHNlLCJjcml0IjpbImI2NCJdLCJhbGciOiJFZERTQSJ9..yJbi7RlKaNTNf3lhaOXUotKUNBg9N4llcD7--QRFYid_WjRcAovU-qOqtlVWngL_6vbjZWeBCRG-fv2Q9o4_CQ"
      }
    }

Process finished with exit code 0

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

    Ed25519Signature2018LdSigner signer = new Ed25519Signature2018LdSigner(testEd25519PrivateKey);
    signer.setCreated(new Date());
    signer.setProofPurpose(LDSecurityKeywords.JSONLD_TERM_ASSERTIONMETHOD);
    signer.setVerificationMethod(URI.create("did:example:76e12ec712ebc6f1c221ebfeb1f#keys-1"));
    signer.setDomain("example.com");
    signer.setNonce("343s$FSFDa-");
    LdProof ldProof = signer.sign(verifiableCredential);

    System.out.println(verifiableCredential.toJson(true));

### Example code (verifying)

    byte[] testEd25519PublicKey = Hex.decodeHex("de8777a28f8da1a74e7a13090ed974d879bf692d001cddee16e4cc9f84b60580".toCharArray());

    VerifiableCredential verifiableCredential = VerifiableCredential.fromJson(new FileReader("input.jsonld"));
    Ed25519Signature2018LdVerifier verifier = new Ed25519Signature2018LdVerifier(testEd25519PublicKey);
    System.out.println(verifier.verify(verifiableCredential));
