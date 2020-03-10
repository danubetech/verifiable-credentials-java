
## Information

This is a work-in-progress implementation of the [Verifiable Credentials](https://w3c.github.io/vc-data-model/) data model.

Not ready for production use! Use at your own risk! Pull requests welcome.

### Maven
First you need to add the following to to your "~/.m2/settings.xml". Here OWNER should be be developer's GitHub user name and TOKEN must be GitHub personal access token . For more information [follow](https://help.github.com/en/github/authenticating-to-github/creating-a-personal-access-token-for-the-command-line) .

	<profiles>
         <profile>
           <id>github</id>
           <repositories>
	        <repository>
              <id>github</id>
              <name>GitHub WebOfTrustInfo ld-signatures-java</name>
              <url>https://maven.pkg.github.com/WebOfTrustInfo/ld-signatures-java</url>
            </repository>
           </repositories>
         </profile>
    </profiles>
	<servers>
        <server>
          <id>github</id>
          <username> OWNER </username>
          <password> TOKEN </password>
        </server>
      </servers>

To build the project
~~~
mvn install
~~~
Dependency:

	<dependency>
		<groupId>com.trustnet</groupId>
		<artifactId>verifiable-credentials-java</artifactId>
		<version>0.2-SNAPSHOT</version>
		<scope>compile</scope>
	</dependency>

### Examples

 * [examples-ldp.md](examples-ldp.md) - Examples of Verifiable Credentials with Linked Data Proofs
 * [examples-jwt.md](examples-jwt.md) - Examples of Verifiable Credentials with JSON Web Tokens
 * [examples-jwt-vp.md](examples-jwt-vp.md) - Examples of Verifiable Presentations with JSON Web Tokens

### About

Originally built as part of [TrustNet](http://trustnet.fi/).

![TrustNet Logo](https://github.com/danubetech/verifiable-credentials-java/blob/master/images/trustnet-logo.png?raw=true)
