
## Information

This is a work-in-progress implementation of the [Verifiable Credentials](https://w3c.github.io/vc-data-model/) data model.

Highly experimental, incomplete, and not ready for production use! Use at your own risk! Pull requests welcome.

### Maven

First, you need to build the [ld-signatures-java](https://github.com/WebOfTrustInfo/ld-signatures-java) project.

After that, just run:

	mvn clean install

Dependency:

	<dependency>
		<groupId>com.trustnet</groupId>
		<artifactId>verifiable-credentials-java</artifactId>
		<version>0.1-SNAPSHOT</version>
		<scope>compile</scope>
	</dependency>

### Example

Example Verifiable Credential:

	{
		"@context": ["https://w3id.org/credentials/v1", "https://trafi.fi/credentials/v1", "https://w3id.org/security/v1"],
		"type": ["VerifiableCredential", "DriversLicenseCredential"],
		"credentialSubject": {
			"id": "did:sov:21tDAKCERh95uGgKbJNHYp",
			"driversLicense": {
				"licenseClass": "trucks"
			}
		},
		"id": "urn:uuid:163f6260-c674-4988-bc68-526cdc4fcca3",
		"issuer": "did:sov:1yvXbmgPoUm4dl66D7KhyD",
		"issuanceDate": "2019-06-15T19:44:08Z",
		"proof": {
			"type": "RsaSignature2018",
			"creator": "did:sov:1yvXbmgPoUm4dl66D7KhyD#keys-1",
			"created": "2018-01-01T21:19:10Z",
			"domain": null,
			"nonce": "c0ae1c8e-c7e7-469f-b252-86e6a0e7387e",
			"signatureValue": "eyJhbGciOiJSUzI1NiIsImI2NCI6ZmFsc2UsImNyaXQiOlsiYjY0Il19..ES8g88t7Y-eOxZ2kmJy35F7KABbVwmDcUeI67AtUfMgXt-ETXp4VetCA9MKWzdWgrEu_xg157unLWepdyrf8zprM3H0UNqS4PIHP7R3dK1V7n-lDd4VgcjzuJrdwrYHcHTj_oQJfGSZoC9128r0jAn5lK4DQ4YV1JKc8nogOy15Wkj9rDPlMFiuRhRJ0GHMm5gbhYCebbu8A-Gn4PFf7IZHpvcKfytGchz13P0EZIYz8qDZ79tORpp2BhiOFb4MODMqArdRs0yeePxrlWAXvH9JqppVUOWI_3Fw2jaqlxWw5R2fcJNGC61WzLv9rFbeq1FUGfKIqBAL7eHM4zYceew"
		}
	}
Example code:

	VerifiableCredential verifiableCredential = new VerifiableCredential();
	verifiableCredential.getContext().add("https://trafi.fi/credentials/v1");
	verifiableCredential.getType().add("DriversLicenseCredential");
	verifiableCredential.setId("urn:uuid:" + UUID.randomUUID());
	verifiableCredential.setIssuer("did:sov:1yvXbmgPoUm4dl66D7KhyD");
	verifiableCredential.setIssuanceDate(new Date());
	
	verifiableCredential.setCredentialSubject("did:sov:21tDAKCERh95uGgKbJNHYp");
	LinkedHashMap<String, Object> jsonLdClaimsObject = verifiableCredential.getJsonLdCredentialSubject();
	LinkedHashMap<String, Object> jsonLdDriversLicenseObject = new LinkedHashMap<String, Object> ();
	jsonLdDriversLicenseObject.put("licenseClass", "trucks");
	jsonLdClaimsObject.put("driversLicense", jsonLdDriversLicenseObject);
	
	URI creator = URI.create("did:sov:1yvXbmgPoUm4dl66D7KhyD#keys-1");
	String created = "2018-01-01T21:19:10Z";
	String domain = null;
	String nonce = "c0ae1c8e-c7e7-469f-b252-86e6a0e7387e";
	
	RsaSignature2018LdSigner signer = new RsaSignature2018LdSigner(creator, created, domain, nonce, TestUtil.testRSAPrivateKey);
	LdSignature ldSignature = signer.sign(verifiableCredential.getJsonLdObject());
	
	System.out.println(JsonUtils.toString(verifiableCredential.getJsonLdObject()));

Example code:

	LinkedHashMap<String, Object> jsonLdObject = (LinkedHashMap<String, Object>) JsonUtils.fromString(TestUtil.read(ValidateTest.class.getResourceAsStream("verifiable-credential.test.jsonld")));
	VerifiableCredential verifiableCredential = VerifiableCredential.fromJsonLdObject(jsonLdObject);
	
	RsaSignature2018LdValidator validator = new RsaSignature2018LdValidator(TestUtil.testRSAPublicKey);
	
	System.out.println(validator.validate(verifiableCredential.getJsonLdObject()));

### About

Originally built as part of [TrustNet](http://trustnet.fi/).

![TrustNet Logo](https://github.com/danubetech/verifiable-credentials-java/blob/master/images/trustnet-logo.png?raw=true)
