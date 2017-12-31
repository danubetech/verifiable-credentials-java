Built as part of [TrustNet](http://trustnet.fi/).

![TrustNet Logo](https://github.com/TrustNetFI/verifiable-credentials-java/blob/master/trustnet-logo.png?raw=true)

### Information

This is a work-in-progress implementation of the [Verifiable Credentials](https://w3c.github.io/vc-data-model/) data model.

Highly experimental, incomplete, and not ready for production use! Use at your own risk! Pull requests welcome.

### Maven

First, you need to build the [ld-signatures-java](https://github.com/WebOfTrustInfo/ld-signatures-java) project.

After that, just run:

	mvn clean install

Dependency:

	<dependency>
		<groupId>fi.trustnet</groupId>
		<artifactId>verifiable-credentials-java</artifactId>
		<version>0.1-SNAPSHOT</version>
		<scope>compile</scope>
	</dependency>

### Example

Example Verifiable Credential:

	{
	  "@context": [
	    "https://w3id.org/credentials/v1",
	    "https://trafi.fi/credentials/v1"
	  ],
	  "id": "did:sov:1yvXbmgPoUm4dl66D7KhyD#cred-12",
	  "type": ["Credential", "DriversLicenseCredential"],
	  "issuer": "did:sov:1yvXbmgPoUm4dl66D7KhyD",
	  "issued": "2018-01-01",
	  "claim": {
	    "id": "did:sov:21tDAKCERh95uGgKbJNHYp",
	    "driversLicense": {
	      "licenseClass": "trucks"
	     }
	  },
	  "revocation": {
	    "id": "https://trafi.fi/revocations/738",
	    "type": "SimpleRevocationList2017"
	  },
	  "signature": {
	    "type": "LinkedDataSignature2017",
	    "created": "2018-01-01T21:19:10Z",
	    "creator": "did:sov:1yvXbmgPoUm4dl66D7KhyD#key1",
	    "nonce": "c0ae1c8e-c7e7-469f-b252-86e6a0e7387e",
	    "signatureValue": "BavEll0/I1zpYw8XNi1bgVg/sCneO4Jugez8RwDg/+
	    MCRVpjOboDoe4SxxKjkCOvKiCHGDvc4krqi6Z1n0UfqzxGfmatCuFibcC1wps
	    PRdW+gGsutPTLzvueMWmFhwYmfIFpbBu95t501+rSLHIEuujM/+PXr9Cky6Ed
	    +W3JT24="
	  }
	}

Example code:

	VerifiableCredential verifiableCredential = new VerifiableCredential();
	verifiableCredential.getContext().add("https://trafi.fi/credentials/v1");
	verifiableCredential.getType().add("DriversLicenseCredential");
	verifiableCredential.setIssuer(URI.create("did:sov:1yvXbmgPoUm4dl66D7KhyD"));
	verifiableCredential.setIssued("2018-01-01");
	
	LinkedHashMap<String, Object> jsonLdClaimsObject = verifiableCredential.getJsonLdClaimsObject();
	LinkedHashMap<String, Object> jsonLdDriversLicenseObject = new LinkedHashMap<String, Object> ();
	jsonLdDriversLicenseObject.put("licenseClass", "trucks");
	jsonLdClaimsObject.put("driversLicense", jsonLdDriversLicenseObject);
	
	URI creator = URI.create("did:sov:1yvXbmgPoUm4dl66D7KhyD#key1");
	String created = "2018-01-01T21:19:10Z";
	String domain = null;
	String nonce = "c0ae1c8e-c7e7-469f-b252-86e6a0e7387e";
	
	RsaSignature2017LdSigner signer = new RsaSignature2017LdSigner(creator, created, domain, nonce, TestUtil.testRSAPrivateKey);
	LdSignature ldSignature = signer.sign(verifiableCredential.getJsonLdObject());

Example code:

	LinkedHashMap<String, Object> jsonLdObject = (LinkedHashMap<String, Object>) JsonUtils.fromString(TestUtil.read(ValidateTest.class.getResourceAsStream("verifiable-credential.test.jsonld")));
	VerifiableCredential verifiableCredential = VerifiableCredential.fromJsonLdObject(jsonLdObject);
	
	RsaSignature2017LdValidator validator = new RsaSignature2017LdValidator(TestUtil.testRSAPublicKey);
	boolean validate = validator.validate(verifiableCredential.getJsonLdObject());

### About

TrustNet - http://trustnet.fi/
