## Examples

Verifiable Presentations with Linked Data Proofs:

### Example Verifiable Presentation

    {
      "@context" : [ "https://www.w3.org/2018/credentials/v1" ],
      "type" : [ "VerifiablePresentation" ],
      "holder" : "did:key:z6MkwBZ6oiJ71ovCohPfdsgBrQinMXnFn6wJxVZHpZEpSh8x",
      "verifiableCredential" : {
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
          "created" : "2021-11-17T22:43:04Z",
          "proofPurpose" : "assertionMethod",
          "verificationMethod" : "did:example:76e12ec712ebc6f1c221ebfeb1f#keys-1",
          "jws" : "eyJiNjQiOmZhbHNlLCJjcml0IjpbImI2NCJdLCJhbGciOiJFZERTQSJ9..jbhZsH1jylvuZLov502qhTRkYvgbepU8ds8Mbgf4X7dhVp8F1P89Ql5WMA2DQEJxMGsVlaT8plfSY0JUw6XmDg"
        }
      },
      "proof" : {
        "type" : "Ed25519Signature2018",
        "created" : "2021-11-17T22:43:05Z",
        "domain" : "example.com",
        "nonce" : "343s$FSFDa-",
        "proofPurpose" : "assertionMethod",
        "verificationMethod" : "did:key:z6MkwBZ6oiJ71ovCohPfdsgBrQinMXnFn6wJxVZHpZEpSh8x#z6MkwBZ6oiJ71ovCohPfdsgBrQinMXnFn6wJxVZHpZEpSh8x",
        "jws" : "eyJiNjQiOmZhbHNlLCJjcml0IjpbImI2NCJdLCJhbGciOiJFZERTQSJ9..D9T1iZ6bF_do_xFhYWjb3HVicP0NvpaKrBrE6K8ZpReEE1UUj5iV6prkj81bilovcRZ9FZpVmq_Gq3mDfIfnBg"
      }
    }

### Example code (signing)

    VerifiablePresentation verifiablePresentation = VerifiablePresentation.builder()
            .verifiableCredential(verifiableCredential)
            .build();
    
    byte[] testEd25519PrivateKey2 = Hex.decodeHex("984b589e121040156838303f107e13150be4a80fc5088ccba0b0bdc9b1d89090de8777a28f8da1a74e7a13090ed974d879bf692d001cddee16e4cc9f84b60580".toCharArray());
    
    Ed25519Signature2018LdSigner signer2 = new Ed25519Signature2018LdSigner(testEd25519PrivateKey2);
    signer2.setCreated(new Date());
    signer2.setProofPurpose(LDSecurityKeywords.JSONLD_TERM_ASSERTIONMETHOD);
    signer2.setVerificationMethod(URI.create("did:example:45678#keys-1"));
    signer2.setDomain("example.com");
    signer2.setNonce("343s$FSFDa-");
    LdProof ldProof2 = signer2.sign(verifiablePresentation);
    
    System.out.println(verifiablePresentation.toJson(true));

### Example code (verifying)

    byte[] testEd25519PublicKey = Hex.decodeHex("f890ab605908a65b89b926fba3b540c0132ce147e3dc5da9fbe7f1445d7279e5".toCharArray());
    
    VerifiablePresentation verifiablePresentation = VerifiablePresentation.fromJson(new FileReader("input.jsonld"));
    Ed25519Signature2018LdVerifier verifier = new Ed25519Signature2018LdVerifier(testEd25519PublicKey);
    System.out.println(verifier.verify(verifiablePresentation));
