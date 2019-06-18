## Examples

Verifiable Credentials with Linked Data Proofs:

### Example Verifiable Credential

	{
		"@context": ["https://www.w3.org/2018/credentials/v1", "https://trafi.fi/credentials/v1", "https://w3id.org/security/v1"],
		"type": ["VerifiableCredential", "DriversLicenseCredential"],
		"credentialSubject": {
			"id": "did:sov:21tDAKCERh95uGgKbJNHYp",
			"driversLicense": {
				"licenseClass": "trucks"
			}
		},
		"id": "urn:uuid:54ac93ea-6db3-4e7f-9ba7-7bb5d81c7e7e",
		"issuer": "did:sov:1yvXbmgPoUm4dl66D7KhyD",
		"issuanceDate": "2019-06-18T07:43:03Z",
		"proof": {
			"type": "RsaSignature2018",
			"creator": "did:sov:1yvXbmgPoUm4dl66D7KhyD#keys-1",
			"created": "2018-01-01T21:19:10Z",
			"domain": null,
			"nonce": "c0ae1c8e-c7e7-469f-b252-86e6a0e7387e",
			"signatureValue": "eyJhbGciOiJSUzI1NiIsImI2NCI6ZmFsc2UsImNyaXQiOlsiYjY0Il19..XiAK8PoseWt2KygoYCKjlGQyzhrpOoDrWpM2qI2dqQPZbCykyWkH6oHBx2uUhZImfQAY_Qf9rICFSF0L7BhrFMc5Jca9mdzA9jS-S3w1CcX4L-6OVykPGZC7WiPnoboHQiX517F-o-1kjV5B6zUc-kLKH4fO5HPiKkEJoY2QCRKivbr4K5sMbPhveZEGwqKwI7RLDKxXJgvdiLzJDb3wVGV0Ed3IKiAuBdT6d53n1nB3NKoCsGZeiiTCjo3QlT-VuWtc-YRIFAeRnNTKEmQxexiw2Rn5piFOtmiKgrlDSxJrOWkfNaPZq2esDsAW0-lAQqdFJf8_eURfs8SeLA5c1A"
		}
	}

### Example code (signing)

	VerifiableCredential verifiableCredential = new VerifiableCredential();
	verifiableCredential.getContext().add("https://trafi.fi/credentials/v1");
	verifiableCredential.getType().add("DriversLicenseCredential");
	verifiableCredential.setId("urn:uuid:" + UUID.randomUUID());
	verifiableCredential.setIssuer("did:sov:1yvXbmgPoUm4dl66D7KhyD");
	verifiableCredential.setIssuanceDate(new Date());
	
	verifiableCredential.setCredentialSubject("did:sov:21tDAKCERh95uGgKbJNHYp");
	LinkedHashMap<String, Object> jsonLdCredentialSubject = verifiableCredential.getJsonLdCredentialSubject();
	LinkedHashMap<String, Object> jsonLdDriversLicense = new LinkedHashMap<String, Object> ();
	jsonLdDriversLicense.put("licenseClass", "trucks");
	jsonLdCredentialSubject.put("driversLicense", jsonLdDriversLicense);
	
	URI creator = URI.create("did:sov:1yvXbmgPoUm4dl66D7KhyD#keys-1");
	String created = "2018-01-01T21:19:10Z";
	String domain = null;
	String nonce = "c0ae1c8e-c7e7-469f-b252-86e6a0e7387e";
	
	RsaSignature2018LdSigner signer = new RsaSignature2018LdSigner(creator, created, domain, nonce, TestUtil.testRSAPrivateKey);
	LdSignature ldSignature = signer.sign(verifiableCredential.getJsonLdObject());
	
	System.out.println(JsonUtils.toString(verifiableCredential.getJsonLdObject()));

### Example code (verifying)

	LinkedHashMap<String, Object> jsonLdObject = (LinkedHashMap<String, Object>) JsonUtils.fromString(...JSON-LD...);
	VerifiableCredential verifiableCredential = VerifiableCredential.fromJsonLdObject(jsonLdObject);
	
	RsaSignature2018LdValidator validator = new RsaSignature2018LdValidator(TestUtil.testRSAPublicKey);
	
	System.out.println(validator.validate(verifiableCredential.getJsonLdObject()));
