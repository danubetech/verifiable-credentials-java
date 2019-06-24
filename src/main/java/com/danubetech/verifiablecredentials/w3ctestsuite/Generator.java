package com.danubetech.verifiablecredentials.w3ctestsuite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.lang.JoseException;

import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.danubetech.verifiablecredentials.jwt.JwtVerifiableCredential;
import com.danubetech.verifiablecredentials.jwt.JwtVerifiablePresentation;
import com.fasterxml.jackson.core.JsonParseException;
import com.github.jsonldjava.utils.JsonUtils;

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

		if (input == null || input.trim().length() < 1) throw new NullPointerException();

		// do the work

		try {

			String output = null;

			if (argJwt == null) {

				VerifiableCredential verifiableCredential = VerifiableCredential.fromJsonString(input);

				output = verifiableCredential.toJsonString();
			} else {

				PrivateKey privateKey = readPrivateKey(argJwt);
				PublicKey publicKey = readPublicKey(argJwt);

				if (argDecode) {

					JwtVerifiableCredential jwtVerifiableCredential = JwtVerifiableCredential.fromJwt(input, AlgorithmIdentifiers.RSA_USING_SHA256, publicKey, false);
					VerifiableCredential verifiableCredential = jwtVerifiableCredential.toVerifiableCredential();

					output = verifiableCredential.toJsonString();
				} else {

					VerifiableCredential verifiableCredential = VerifiableCredential.fromJsonString(input);
					JwtVerifiableCredential jwtVerifiableCredential = JwtVerifiableCredential.fromVerifiableCredential(verifiableCredential, argAud);

					if (argPresentation) {

						jwtVerifiableCredential.toJwt(AlgorithmIdentifiers.RSA_USING_SHA256, privateKey);
						JwtVerifiablePresentation jwtVerifiablePresentation = JwtVerifiablePresentation.fromJwtVerifiableCredential(jwtVerifiableCredential, argAud);

						output = jwtVerifiablePresentation.toJwt(AlgorithmIdentifiers.RSA_USING_SHA256, privateKey);
					} else {

						if (argNoJws) {

							output = jwtVerifiableCredential.getPayload().toJson();
						} else {

							output = jwtVerifiableCredential.toJwt(AlgorithmIdentifiers.RSA_USING_SHA256, privateKey);
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

	static PrivateKey readPrivateKey(String jwt) throws JoseException, JsonParseException, IOException {

		LinkedHashMap<String, Object> jsonObject = (LinkedHashMap<String, Object>) JsonUtils.fromString(new String(Base64.decodeBase64(jwt)));
		LinkedHashMap<String, Object> rs256PrivateKeyJwk = (LinkedHashMap<String, Object>) jsonObject.get("rs256PrivateKeyJwk");

		RsaJsonWebKey jwk = (RsaJsonWebKey) JsonWebKey.Factory.newJwk(rs256PrivateKeyJwk);

		return jwk.getPrivateKey();
	}

	static PublicKey readPublicKey(String jwt) throws JoseException, JsonParseException, IOException {

		LinkedHashMap<String, Object> jsonObject = (LinkedHashMap<String, Object>) JsonUtils.fromString(new String(Base64.decodeBase64(jwt)));
		LinkedHashMap<String, Object> rs256PrivateKeyJwk = (LinkedHashMap<String, Object>) jsonObject.get("rs256PrivateKeyJwk");

		RsaJsonWebKey jwk = (RsaJsonWebKey) JsonWebKey.Factory.newJwk(rs256PrivateKeyJwk);

		return jwk.getPublicKey();
	}

	static String readInput(File input) throws Exception {

		BufferedReader reader = new BufferedReader(new FileReader(input));
		StringBuffer buffer = new StringBuffer();

		String line;
		while ((line = reader.readLine()) != null) buffer.append(line);

		return buffer.toString();
	}
}
