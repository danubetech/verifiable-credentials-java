package com.danubetech.verifiablecredentials;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.bitcoinj.core.Base58;
import org.bitcoinj.core.ECKey;

class TestUtil {

	static final String testEd25519PrivateKeyString =
			"43bt2CEvmvm538bQ6YAnpfWTq5xisAB5Kqz7uiob9sabHsZp2HtFEFXRPGa5Mvdhw5xPEABrLduxFu5vt3AViEgF";

	static final String testEd25519PublicKeyString =
			"FyfKP2HvTKqDZQzvyL38yXH7bExmwofxHf2NR5BrcGf1";

	static final String testSecp256k1PrivateKeyString =
			"2ff4e6b73bc4c4c185c68b2c378f6b233978a88d3c8ed03df536f707f084e24e";

	static final String testSecp256k1PublicKeyString =
			"0343f9455cd248e24c262b1341bbe37cea360e1c5ce526e5d1a71373ba6e557018";

	static final String testRSAPrivateKeyString =
			"-----BEGIN PRIVATE KEY-----\n" +
					"MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC2lLVmZ9UpU/kq\n" +
					"h8iEwE/S1JZziqWHp+baWtlKS4rFSMRpaPNlLOzvaAQgbGtpa6wx2hG5XnjGxZHJ\n" +
					"/kp5lPRL4jk+uV7ch2LeAgKI7g3C8yTonBIFwlbCZIsUQrJRKcHYK1+IZzT/mtAK\n" +
					"lwS38OfmIz4E2ft+qmgshuSzytcpQiPz6oxWqRNewQp4qKcTbe3XKQyV2w1po4f6\n" +
					"G8a2Lkm3YMycfUmOhd0Nd/G9I//SCNRhvR6S251gVegDrB6SZDIl4ia+DHgzLPUj\n" +
					"iIe2Rj8KnsngyfV6Nnoc2bK+hMT/g65jW4J5i/hTJcVzWzW5TJi2PjPnuqwcaxLh\n" +
					"1DcDYwmzAgMBAAECggEAKp0KuZwCZGL1BLgsVM+N0edMNitl9wN5Hf2WOYDoIqOZ\n" +
					"NAEKzdJuenIMhITJjRFUX05GVL138uyp2js/pqDdY9ipA7rAKThwGuDdNphZHech\n" +
					"9ih3DGEPXs+YpmHqvIbCd3GoGm38MKwxYkddEpFnjo8rKna1/BpJthrFxjDRhw9D\n" +
					"xJBycOdH2yWTyp62ZENPvneK40H2a57W4QScTgfecZqD59m2fGUaWaX5uUmIxaEm\n" +
					"tGoJnd9RE4oywKhgN7/TK7wXRlqA4UoRPiH2ACrdU+/cLQL9Jc0u0GqZJK31LDbO\n" +
					"eN95QgtSCc72k3Vtzy3CrVpp5TAA67s1Gj9Skn+CAQKBgQDkEZTVztp//mwXJ+xr\n" +
					"6icgmCjkFm7y4e/PdTJvw4DRr4b1Q87VKEtiNfTBR+FlwUHt/A+2CaZgA3rAoZVx\n" +
					"714wBtfg+WI+Tev4Fylm48qS4uT/AW+BYBDkerDaIS7BctXT97xzaBpS3+HIwLn6\n" +
					"cVzi/QGa/o1Po9+vL5SsrcEpZwKBgQDM8P4H6eueDAX4730Ee9vjtcYpHs43wkIj\n" +
					"onFq/MiS6wxcIHyszJhbzMuzrwvwksDNZnfigyrQU9SfKwHFzmdMXw1vgFnFNnn7\n" +
					"1wd+gqthMjdhayZVbYWkIkUSMyzg1dnbw8GRL1vjON9LYqE12SYJ45hTS0mk1/CY\n" +
					"5Mj3Sp5R1QKBgGia88P5I1ivbg5U3mhEtnuJrr+m1m6KWH6zx1VhuzTxqBnYZwZ3\n" +
					"e9Po4YDBIk2UjVPFV8Nru6awEd5GfpAKdQ3cJannWDsxbDiXDwNFGYWzkcqwct9J\n" +
					"G5Zf+7ugmpxZul+FcicQqXo3e4yjcOnAkxT9bH4VoOTVSeRFE5D8BOujAoGASwz1\n" +
					"+m/vmTFN/pu1bK7vF7S5nNVrL4A0OFiEsGliCmuJWzOKdL14DiYxctvnw3H6qT2d\n" +
					"KZZfV2tbse5N9+JecdldUjfuqAoLIe7dD7dKi42YOlTC9QXmqvTh1ohnJu8pmRFX\n" +
					"EZQGUm/BVhoIb2/WPkjav6YSkguCUHt4HRd2YwECgYAGhy4I4Q6r6jIsMAvDMxdT\n" +
					"yA5/cgvVDX8FbCx2gA2iHqLXv2mzGATgldOhZyldlBCq5vyeDATq5H1+l3ebo388\n" +
					"vhPnm9sMPKM8qasva20LaA63H0quk+H5nstBGjgETjycckmvKy0od8WVofYbsnEc\n" +
					"2AwFhUAPK203T2oShq/w6w==\n" +
					"-----END PRIVATE KEY-----\n";

	static final String testRSAPublicKeyString =
			"-----BEGIN PUBLIC KEY-----\n" +
					"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtpS1ZmfVKVP5KofIhMBP\n" +
					"0tSWc4qlh6fm2lrZSkuKxUjEaWjzZSzs72gEIGxraWusMdoRuV54xsWRyf5KeZT0\n" +
					"S+I5Prle3Idi3gICiO4NwvMk6JwSBcJWwmSLFEKyUSnB2CtfiGc0/5rQCpcEt/Dn\n" +
					"5iM+BNn7fqpoLIbks8rXKUIj8+qMVqkTXsEKeKinE23t1ykMldsNaaOH+hvGti5J\n" +
					"t2DMnH1JjoXdDXfxvSP/0gjUYb0ektudYFXoA6wekmQyJeImvgx4Myz1I4iHtkY/\n" +
					"Cp7J4Mn1ejZ6HNmyvoTE/4OuY1uCeYv4UyXFc1s1uUyYtj4z57qsHGsS4dQ3A2MJ\n" +
					"swIDAQAB\n" +
					"-----END PUBLIC KEY-----\n";

	static final byte[] testEd25519PrivateKey;
	static final byte[] testEd25519PublicKey;
	static final ECKey testSecp256k1PrivateKey;
	static final ECKey testSecp256k1PublicKey;
	static final RSAPrivateKey testRSAPrivateKey;
	static final RSAPublicKey testRSAPublicKey;

	static {

		try {

			testEd25519PrivateKey = Base58.decode(testEd25519PrivateKeyString);
			testEd25519PublicKey = Base58.decode(testEd25519PublicKeyString);
		} catch (Exception ex) {

			throw new RuntimeException(ex.getMessage(), ex);
		}

		try {

			testSecp256k1PrivateKey = ECKey.fromPrivate(Hex.decodeHex(testSecp256k1PrivateKeyString.toCharArray()));
			testSecp256k1PublicKey = ECKey.fromPublicOnly(Hex.decodeHex(testSecp256k1PublicKeyString.toCharArray()));
		} catch (Exception ex) {

			throw new RuntimeException(ex.getMessage(), ex);
		}

		try {

			String pem = testRSAPublicKeyString;
			pem = pem.replace("-----BEGIN PUBLIC KEY-----", "").replace("\n", "");
			pem = pem.replace("-----END PUBLIC KEY-----", "");

			byte[] encoded = Base64.decodeBase64(pem);

			X509EncodedKeySpec spec = new X509EncodedKeySpec(encoded);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			testRSAPublicKey = (RSAPublicKey) keyFactory.generatePublic(spec);
		} catch (Exception ex) {

			throw new RuntimeException(ex.getMessage(), ex);
		}

		try {

			String pem = testRSAPrivateKeyString;
			pem = pem.replace("-----BEGIN PRIVATE KEY-----", "").replace("\n", "");
			pem = pem.replace("-----END PRIVATE KEY-----", "");

			byte[] encoded = Base64.decodeBase64(pem);

			PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(encoded);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			testRSAPrivateKey = (RSAPrivateKey) keyFactory.generatePrivate(spec);
		} catch (Exception ex) {

			throw new RuntimeException(ex.getMessage(), ex);
		}
	}

	static String read(InputStream inputStream) throws Exception {

		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		StringBuffer buffer = new StringBuffer();

		String line;
		while ((line = reader.readLine()) != null) buffer.append(line + "\n");

		return buffer.toString();
	}
}
