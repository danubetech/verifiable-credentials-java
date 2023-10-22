package com.danubetech.verifiablecredentials.w3ctestsuite;

import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.danubetech.verifiablecredentials.VerifiablePresentation;
import com.danubetech.verifiablecredentials.jwt.FromJwtConverter;
import com.danubetech.verifiablecredentials.jwt.JwtJwtVerifiablePresentation;
import com.danubetech.verifiablecredentials.jwt.JwtVerifiableCredential;
import com.danubetech.verifiablecredentials.jwt.ToJwtConverter;
import com.danubetech.verifiablecredentials.validation.Validation;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.util.JSONObjectUtils;
import foundation.identity.jsonld.JsonLDObject;
import org.apache.commons.codec.binary.Base64;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Generator {

	public static void main(String[] args) throws Exception {

		// command line args

		List<String> argsList = Arrays.asList(args);
		String argJwt = argJwt(argsList);
		String argAud = argAud(argsList);
		boolean argNoJws = argNoJws(argsList);
		boolean argPresentation = argPresentation(argsList);
		boolean argDecode = argDecode(argsList);
		String argInput = argInput(argsList);

		if (argInput == null) throw new NullPointerException();

		// input file

		String input = readInput(new File(argInput));

		if (input.trim().isEmpty()) throw new NullPointerException();

		// do the work

		try {

			String output;

			if (argJwt == null) {

				JsonLDObject jsonLdObject = JsonLDObject.fromJson(input);

				if (jsonLdObject.isType(VerifiableCredential.DEFAULT_JSONLD_TYPES[0])) {

					VerifiableCredential verifiableCredential = VerifiableCredential.fromJsonObject(jsonLdObject.getJsonObject());
					Validation.validate(verifiableCredential);
					if (verifiableCredential.getLdProof() == null) throw new IllegalStateException("No proof in VC");
				} else if (jsonLdObject.isType(VerifiablePresentation.DEFAULT_JSONLD_TYPES[0])) {

					VerifiablePresentation verifiablePresentation = VerifiablePresentation.fromJsonObject(jsonLdObject.getJsonObject());
					Validation.validate(verifiablePresentation);
					if (verifiablePresentation.getLdProof() == null) throw new IllegalStateException("No proof in VP");
				} else {

					throw new IllegalStateException("Unknown JSON-LD object type: " + jsonLdObject.getTypes());
				}

				output = jsonLdObject.toJson();
			} else {

				RSAKey rsaKey = readRSAKey(argJwt);

				if (argDecode) {

					JwtVerifiableCredential jwtVerifiableCredential = JwtVerifiableCredential.fromCompactSerialization(input);
					//if (! jwtVerifiableCredential.verify_RSA_RS256(rsaKey.toPublicJWK())) throw new GeneralSecurityException("Invalid signature.");

					VerifiableCredential verifiableCredential = FromJwtConverter.fromJwtVerifiableCredential(jwtVerifiableCredential);

					output = verifiableCredential.toJson();
				} else {

					VerifiableCredential verifiableCredential = VerifiableCredential.fromJson(input);
					JwtVerifiableCredential jwtVerifiableCredential = ToJwtConverter.toJwtVerifiableCredential(verifiableCredential, argAud);

					if (argPresentation) {

						jwtVerifiableCredential.sign_RSA_RS256(rsaKey);

						JwtJwtVerifiablePresentation jwtJwtVerifiablePresentation = JwtJwtVerifiablePresentation.fromJwtVerifiableCredential(jwtVerifiableCredential, argAud);
						output = jwtJwtVerifiablePresentation.sign_RSA_RS256(rsaKey);
					} else {

						if (argNoJws) {

							output = JSONObjectUtils.toJSONString(jwtVerifiableCredential.getPayload().toJSONObject());
						} else {

							output = jwtVerifiableCredential.sign_RSA_RS256(rsaKey);
						}
					}
				}
			}

			System.out.println(output);
		} catch (Exception ex) {

			System.err.println(ex.getMessage());
			ex.printStackTrace(System.err);
		}
	}

	/*
	 * Helper methods
	 */

	static String argJwt(List<String> argsList) {

		int pos = argsList.indexOf("--jwt");
		if (pos == -1) return null;
		return argsList.get(pos+1);
	}

	static String argAud(List<String> argsList) {

		int pos = argsList.indexOf("--jwt-aud");
		if (pos == -1) return null;
		return argsList.get(pos+1);
	}

	static boolean argNoJws(List<String> argsList) {

		return argsList.contains("--jwt-no-jws");
	}

	static boolean argPresentation(List<String> argsList) {

		return argsList.contains("--jwt-presentation");
	}

	static boolean argDecode(List<String> argsList) {

		return argsList.contains("--jwt-decode");
	}

	static String argInput(List<String> argsList) {

		return argsList.get(argsList.size()-1);
	}

	static RSAKey readRSAKey(String jwt) throws ParseException, JOSEException {

		Map<String, Object> jsonObject = JSONObjectUtils.parse(new String(Base64.decodeBase64(jwt)));
		Map<String, Object> rs256PrivateKeyJwk = (Map<String, Object>) jsonObject.get("rs256PrivateKeyJwk");

		RSAKey jwk = (RSAKey) JWK.parse(rs256PrivateKeyJwk);

		return jwk;
	}

	static String readInput(File input) throws Exception {

		BufferedReader reader = new BufferedReader(new FileReader(input));
		StringBuilder buffer = new StringBuilder();

		String line;
		while ((line = reader.readLine()) != null) buffer.append(line);

		reader.close();

		return buffer.toString();
	}
}
