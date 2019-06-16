## Examples

Verifiable Credentials with JSON Web Tokens:

### Example Verifiable Credential

	eyJhbGciOiJSUzI1NiJ9.eyJqdGkiOiJ1cm46dXVpZDphODdiZGZiOC1hN2RmLTRiZDktYWUwZC1kODgzMTMzNTM4ZmUiLCJzdWIiOiJkaWQ6c292OjIxdERBS0NFUmg5NXVHZ0tiSk5IWXAiLCJpc3MiOiJkaWQ6c292OjF5dlhibWdQb1VtNGRsNjZEN0toeUQiLCJpYXQiOjE1NjA3MTE0MTksImV4cCI6MTU2MDc5NzgxOSwidmMiOnsiQGNvbnRleHQiOlsiaHR0cHM6Ly93M2lkLm9yZy9jcmVkZW50aWFscy92MSIsImh0dHBzOi8vdHJhZmkuZmkvY3JlZGVudGlhbHMvdjEiXSwidHlwZSI6WyJWZXJpZmlhYmxlQ3JlZGVudGlhbCIsIkRyaXZlcnNMaWNlbnNlQ3JlZGVudGlhbCJdLCJjcmVkZW50aWFsU3ViamVjdCI6eyJkcml2ZXJzTGljZW5zZSI6eyJsaWNlbnNlQ2xhc3MiOiJ0cnVja3MifX19fQ.sBLdUmYcys7ewHG_4iXGwPH3oayKC8qJmHqLbaKrlJhgPPxYGayUM7JD_HlS0GWaudTMaQ5nK2Dkvwka0PuzoFdJemKdy_ua8Qobe_h6BLdtiCV_3063D_KNyiGu7SBhfemL0lcaLo6rCKcPrd5ZdSi0_vfAXH1ZlMS-oBYpdXWompbY87-H1H-ARW0aicN6YofMzewwKKrgPLsNih603hgzJj63M1HbbIiKjMMzGNwGFn96vUQ-ZC24O-i_Fgl4BWTK2a9bIcq4zzzUfQ7TxnI9f0Y08O5hxBZq71ZQtDfebkEvOv-XL73L_sw7GMXBE_j-mcif8OehkHOnesvXBQ

JWT Payload:

	{
		"jti": "urn:uuid:a87bdfb8-a7df-4bd9-ae0d-d883133538fe",
		"sub": "did:sov:21tDAKCERh95uGgKbJNHYp",
		"iss": "did:sov:1yvXbmgPoUm4dl66D7KhyD",
		"iat": 1560711419,
		"exp": 1560797819,
		"vc": {
			"@context": ["https://w3id.org/credentials/v1", "https://trafi.fi/credentials/v1"],
			"type": ["VerifiableCredential", "DriversLicenseCredential"],
			"credentialSubject": {
				"driversLicense": {
					"licenseClass": "trucks"
				}
			}
		}
	}

### Example code (signing)

	VerifiableCredential verifiableCredential = new VerifiableCredential();
	verifiableCredential.getContext().add("https://trafi.fi/credentials/v1");
	verifiableCredential.getType().add("DriversLicenseCredential");
	verifiableCredential.setId("urn:uuid:a87bdfb8-a7df-4bd9-ae0d-d883133538fe");
	verifiableCredential.setIssuer("did:sov:1yvXbmgPoUm4dl66D7KhyD");
	verifiableCredential.setIssuanceDate(VerifiableCredential.DATE_FORMAT.parse("2019-06-16T18:56:59Z"));
	verifiableCredential.setExpirationDate(VerifiableCredential.DATE_FORMAT.parse("2019-06-17T18:56:59Z"));
	
	verifiableCredential.setCredentialSubject("did:sov:21tDAKCERh95uGgKbJNHYp");
	LinkedHashMap<String, Object> jsonLdClaimsObject = verifiableCredential.getJsonLdCredentialSubject();
	LinkedHashMap<String, Object> jsonLdDriversLicenseObject = new LinkedHashMap<String, Object> ();
	jsonLdDriversLicenseObject.put("licenseClass", "trucks");
	jsonLdClaimsObject.put("driversLicense", jsonLdDriversLicenseObject);
	
	JwtVerifiableCredential jwtVerifiableCredential = JwtVerifiableCredential.fromVerifiableCredential(verifiableCredential);
	
	String jwtPayload = jwtVerifiableCredential.getPayload().toJson();
	System.out.println(jwtPayload);
	
	String jwtString = jwtVerifiableCredential.toJwt(AlgorithmIdentifiers.RSA_USING_SHA256, TestUtil.testRSAPrivateKey);
	System.out.println(jwtString);

### Example code (verifying)

	JwtVerifiableCredential jwtVerifiableCredential = JwtVerifiableCredential.fromJwt(...JWT..., AlgorithmIdentifiers.RSA_USING_SHA256, TestUtil.testRSAPublicKey);
	
	String jwtPayload = jwtVerifiableCredential.getPayload().toJson();
	String jwtPayloadVerifiableCredential = jwtVerifiableCredential.getPayloadVerifiableCredential().toJsonString();
	System.out.println(jwtPayload);
	System.out.println(jwtPayloadVerifiableCredential);
	
	VerifiableCredential verifiableCredential = jwtVerifiableCredential.toVerifiableCredential();
	System.out.println(verifiableCredential.toJsonString());
