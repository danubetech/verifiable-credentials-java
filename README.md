# verifiable-credentials-java

## Information

This is a work-in-progress implementation of the [Verifiable Credentials](https://w3c.github.io/vc-data-model/) data model.

Not ready for production use! Use at your own risk! Pull requests welcome.

## Maven

First, you need to build the [ld-signatures-java](https://github.com/WebOfTrustInfo/ld-signatures-java) project.

After that, just run:

	mvn clean install

Dependency:

	<dependency>
		<groupId>com.danubetech</groupId>
		<artifactId>verifiable-credentials-java</artifactId>
		<version>0.4-SNAPSHOT</version>
		<scope>compile</scope>
	</dependency>

## Examples

 * [examples-ldp.md](examples-ldp.md) - Examples of Verifiable Credentials with Linked Data Proofs
 * [examples-jwt.md](examples-jwt.md) - Examples of Verifiable Credentials with JSON Web Tokens
 * [examples-jwt-vp.md](examples-jwt-vp.md) - Examples of Verifiable Presentations with JSON Web Tokens

## About

Danube Tech - https://danubetech.com/

<img align="left" src="https://raw.githubusercontent.com/danubetech/verifiable-credentials-java/master/docs/trustnet-logo.png" width="115">

Originally built as part of [TrustNet](http://trustnet.fi/).

<br clear="left" />

<img align="left" src="https://raw.githubusercontent.com/danubetech/verifiable-credentials-java/master/docs/logo-ngi-essiflab.png" width="115">

Supported by [ESSIF-Lab](https://essif-lab.eu/), which is made possible with financial support from the European Commission's [Next Generation Internet](https://ngi.eu/) programme.

<br clear="left" />
