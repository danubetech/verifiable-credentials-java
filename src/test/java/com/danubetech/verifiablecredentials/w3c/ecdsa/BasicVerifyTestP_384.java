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
public class BasicVerifyTestP_384 {

    public static final ECPublicKey PUBLICKEY = PublicKeyBytes.bytes_to_P_384PublicKey(TestUtil.removeMulticodec(Multibase.decode("z82LkuBieyGShVBhvtE2zoiD6Kma4tJGFtkAhxR5pfkp5QPw4LutoYWhvQCnGjdVn14kujQ")));
    public static final byte[] PAYLOAD = Hex.decode("e32805a26492eac777aa7a138f6d8da3c74e0c7be7b296dcaccf97420c3b92eaad7be6449ca565e165031567f5c7cbc18bf6e01df72c5b62f91b685231915ac4b8c58ea95f002c6b8f6bfafa1b251df476b56b8e01518e317dab099d3ecbff96");
    public static final byte[] SIGNATURE = Hex.decode("177ac088806c2506d49f0bfec16056a6a80ace62cd029888ad561aba22a59d192d77d9b1fc28df80dea5ee6c8bceb16f1b8bff6bd6ff2d8f8778bdde48bafa7b6cc1f914c0168b5c04499882f632deea9cb7d977e888bb0e1ee9fb20ff03b025");
    public static final String SIGNATURE_MULTIBASE = "z967Mvv5bxtmLNqTzPZ8KmJjFmFXaAKeQNzq7GWnQkMcLtaGSSmuozE5WtJ8PipMe178B1tE28K1vsJur9bGVJhz6jgSJsRHFSQeqgH8hhjcg8gZDFJC1b9FsR5ggNmDBqHv";

    /*
     * See https://www.w3.org/TR/vc-di-ecdsa/#representation-ecdsa-rdfc-2019-with-curve-p-384
     */
    @Test
    public void testSignVerify() throws GeneralSecurityException {
        ByteVerifier byteVerifier = PublicKeyVerifierFactory.publicKeyVerifierForKey(KeyTypeName.P_384, JWSAlgorithm.ES384, PUBLICKEY);
        assertTrue(byteVerifier.verify(PAYLOAD, SIGNATURE, JWSAlgorithm.ES384));
        assertArrayEquals(SIGNATURE, Multibase.decode(SIGNATURE_MULTIBASE));
    }
}
