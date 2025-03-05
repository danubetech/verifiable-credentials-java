package com.danubetech.verifiablecredentials.w3c.ecdsa;

import com.danubetech.keyformats.PublicKeyBytes;
import com.danubetech.keyformats.crypto.ByteVerifier;
import com.danubetech.keyformats.crypto.PublicKeyVerifierFactory;
import com.danubetech.keyformats.jose.JWSAlgorithm;
import com.danubetech.keyformats.jose.KeyTypeName;
import com.danubetech.verifiablecredentials.util.TestUtil;
import io.ipfs.multibase.Multibase;
import org.bouncycastle.util.encoders.Hex;
import org.junit.jupiter.api.Test;

import java.security.GeneralSecurityException;
import java.security.interfaces.ECPublicKey;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * See https://www.w3.org/TR/vc-di-ecdsa/#test-vectors
 */
public class BasicVerifyTestP_256 {

    public static final ECPublicKey PUBLICKEY = PublicKeyBytes.bytes_to_P_256PublicKey(TestUtil.removeMulticodec(Multibase.decode("zDnaepBuvsQ8cpsWrVKw8fbpGpvPeNSjVPTWoq6cRqaYzBKVP")));
    public static final byte[] PAYLOAD = Hex.decode("3a8a522f689025727fb9d1f0fa99a618da023e8494ac74f51015d009d35abc2e517744132ae165a5349155bef0bb0cf2258fff99dfe1dbd914b938d775a36017");
    public static final byte[] SIGNATURE = Hex.decode("1cb4290918ffb04a55ff7ae1e55e316a9990fda8eec67325eac7fcbf2ddf9dd2b06716a657e72b284c9604df3a172ecbf06a1a475b49ac807b1d9162df855636");
    public static final String SIGNATURE_MULTIBASE = "zaHXrr7AQdydBk3ahpCDpWbxfLokDqmCToYm2dyWvpcFVyWooC2he63w1f7UNQoAMKdhaRtcnaE2KTo5o5vTCcfw";

    /*
     * See https://www.w3.org/TR/vc-di-ecdsa/#representation-ecdsa-rdfc-2019-with-curve-p-256
     */
    @Test
    public void testSignVerify() throws GeneralSecurityException {
        ByteVerifier byteVerifier = PublicKeyVerifierFactory.publicKeyVerifierForKey(KeyTypeName.P_256, JWSAlgorithm.ES256, PUBLICKEY);
        assertTrue(byteVerifier.verify(PAYLOAD, SIGNATURE, JWSAlgorithm.ES256));
        assertArrayEquals(SIGNATURE, Multibase.decode(SIGNATURE_MULTIBASE));
    }
}
