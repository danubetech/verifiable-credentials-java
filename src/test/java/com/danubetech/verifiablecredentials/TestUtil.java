package com.danubetech.verifiablecredentials;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.bitcoinj.core.Base58;
import org.bitcoinj.core.ECKey;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class TestUtil {

	static final String testEd25519PrivateKeyString =
			"43bt2CEvmvm538bQ6YAnpfWTq5xisAB5Kqz7uiob9sabHsZp2HtFEFXRPGa5Mvdhw5xPEABrLduxFu5vt3AViEgF";

	static final String testEd25519PublicKeyString =
			"FyfKP2HvTKqDZQzvyL38yXH7bExmwofxHf2NR5BrcGf1";

	static final String testSecp256k1PrivateKeyString =
			"2ff4e6b73bc4c4c185c68b2c378f6b233978a88d3c8ed03df536f707f084e24e";

	static final String testSecp256k1PublicKeyString =
			"0343f9455cd248e24c262b1341bbe37cea360e1c5ce526e5d1a71373ba6e557018";

	static final String testRSAPrivateKeyString =
            """
                    -----BEGIN PRIVATE KEY-----
                    MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC2lLVmZ9UpU/kq
                    h8iEwE/S1JZziqWHp+baWtlKS4rFSMRpaPNlLOzvaAQgbGtpa6wx2hG5XnjGxZHJ
                    /kp5lPRL4jk+uV7ch2LeAgKI7g3C8yTonBIFwlbCZIsUQrJRKcHYK1+IZzT/mtAK
                    lwS38OfmIz4E2ft+qmgshuSzytcpQiPz6oxWqRNewQp4qKcTbe3XKQyV2w1po4f6
                    G8a2Lkm3YMycfUmOhd0Nd/G9I//SCNRhvR6S251gVegDrB6SZDIl4ia+DHgzLPUj
                    iIe2Rj8KnsngyfV6Nnoc2bK+hMT/g65jW4J5i/hTJcVzWzW5TJi2PjPnuqwcaxLh
                    1DcDYwmzAgMBAAECggEAKp0KuZwCZGL1BLgsVM+N0edMNitl9wN5Hf2WOYDoIqOZ
                    NAEKzdJuenIMhITJjRFUX05GVL138uyp2js/pqDdY9ipA7rAKThwGuDdNphZHech
                    9ih3DGEPXs+YpmHqvIbCd3GoGm38MKwxYkddEpFnjo8rKna1/BpJthrFxjDRhw9D
                    xJBycOdH2yWTyp62ZENPvneK40H2a57W4QScTgfecZqD59m2fGUaWaX5uUmIxaEm
                    tGoJnd9RE4oywKhgN7/TK7wXRlqA4UoRPiH2ACrdU+/cLQL9Jc0u0GqZJK31LDbO
                    eN95QgtSCc72k3Vtzy3CrVpp5TAA67s1Gj9Skn+CAQKBgQDkEZTVztp//mwXJ+xr
                    6icgmCjkFm7y4e/PdTJvw4DRr4b1Q87VKEtiNfTBR+FlwUHt/A+2CaZgA3rAoZVx
                    714wBtfg+WI+Tev4Fylm48qS4uT/AW+BYBDkerDaIS7BctXT97xzaBpS3+HIwLn6
                    cVzi/QGa/o1Po9+vL5SsrcEpZwKBgQDM8P4H6eueDAX4730Ee9vjtcYpHs43wkIj
                    onFq/MiS6wxcIHyszJhbzMuzrwvwksDNZnfigyrQU9SfKwHFzmdMXw1vgFnFNnn7
                    1wd+gqthMjdhayZVbYWkIkUSMyzg1dnbw8GRL1vjON9LYqE12SYJ45hTS0mk1/CY
                    5Mj3Sp5R1QKBgGia88P5I1ivbg5U3mhEtnuJrr+m1m6KWH6zx1VhuzTxqBnYZwZ3
                    e9Po4YDBIk2UjVPFV8Nru6awEd5GfpAKdQ3cJannWDsxbDiXDwNFGYWzkcqwct9J
                    G5Zf+7ugmpxZul+FcicQqXo3e4yjcOnAkxT9bH4VoOTVSeRFE5D8BOujAoGASwz1
                    +m/vmTFN/pu1bK7vF7S5nNVrL4A0OFiEsGliCmuJWzOKdL14DiYxctvnw3H6qT2d
                    KZZfV2tbse5N9+JecdldUjfuqAoLIe7dD7dKi42YOlTC9QXmqvTh1ohnJu8pmRFX
                    EZQGUm/BVhoIb2/WPkjav6YSkguCUHt4HRd2YwECgYAGhy4I4Q6r6jIsMAvDMxdT
                    yA5/cgvVDX8FbCx2gA2iHqLXv2mzGATgldOhZyldlBCq5vyeDATq5H1+l3ebo388
                    vhPnm9sMPKM8qasva20LaA63H0quk+H5nstBGjgETjycckmvKy0od8WVofYbsnEc
                    2AwFhUAPK203T2oShq/w6w==
                    -----END PRIVATE KEY-----
                    """;

	static final String testRSAPublicKeyString =
            """
                    -----BEGIN PUBLIC KEY-----
                    MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtpS1ZmfVKVP5KofIhMBP
                    0tSWc4qlh6fm2lrZSkuKxUjEaWjzZSzs72gEIGxraWusMdoRuV54xsWRyf5KeZT0
                    S+I5Prle3Idi3gICiO4NwvMk6JwSBcJWwmSLFEKyUSnB2CtfiGc0/5rQCpcEt/Dn
                    5iM+BNn7fqpoLIbks8rXKUIj8+qMVqkTXsEKeKinE23t1ykMldsNaaOH+hvGti5J
                    t2DMnH1JjoXdDXfxvSP/0gjUYb0ektudYFXoA6wekmQyJeImvgx4Myz1I4iHtkY/
                    Cp7J4Mn1ejZ6HNmyvoTE/4OuY1uCeYv4UyXFc1s1uUyYtj4z57qsHGsS4dQ3A2MJ
                    swIDAQAB
                    -----END PUBLIC KEY-----
                    """;

	static final byte[] testEd25519PrivateKey;
	static final byte[] testEd25519PublicKey;
	static final ECKey testSecp256k1PrivateKey;
	static final ECKey testSecp256k1PublicKey;
	static final KeyPair testRSAPrivateKey;
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
			testRSAPrivateKey = new KeyPair(testRSAPublicKey, keyFactory.generatePrivate(spec));
		} catch (Exception ex) {

			throw new RuntimeException(ex.getMessage(), ex);
		}
	}

	static String read(InputStream inputStream) throws Exception {

		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		StringBuilder buffer = new StringBuilder();

		String line;
		while ((line = reader.readLine()) != null) buffer.append(line).append("\n");

		return buffer.toString();
	}
}
