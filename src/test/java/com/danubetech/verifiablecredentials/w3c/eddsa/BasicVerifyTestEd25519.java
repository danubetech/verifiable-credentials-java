package com.danubetech.verifiablecredentials.w3c.eddsa;

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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * See https://www.w3.org/TR/vc-di-eddsa/#representation-eddsa-rdfc-2022
 */
public class BasicVerifyTestEd25519 {

    public static final byte[] PUBLICKEY = PublicKeyBytes.bytes_to_Ed25519PublicKey(TestUtil.removeMulticodec(Multibase.decode("z6MkrJVnaZkeFzdQyMZu1cgjg7k1pZZ6pvBQ7XJPt4swbTQ2")));
    public static final byte[] PAYLOAD = Hex.decode("bea7b7acfbad0126b135104024a5f1733e705108f42d59668b05c0c50004c6b0517744132ae165a5349155bef0bb0cf2258fff99dfe1dbd914b938d775a36017");
    public static final byte[] SIGNATURE = Hex.decode("4d8e53c2d5b3f2a7891753eb16ca993325bdb0d3cfc5be1093d0a18426f5ef8578cadc0fd4b5f4dd0d1ce0aefd15ab120b7a894d0eb094ffda4e6553cd1ed50d");
    public static final String SIGNATURE_MULTIBASE = "z2YwC8z3ap7yx1nZYCg4L3j3ApHsF8kgPdSb5xoS1VR7vPG3F561B52hYnQF9iseabecm3ijx4K1FBTQsCZahKZme";

    /*
     * See https://www.w3.org/TR/vc-di-eddsa/#representation-eddsa-rdfc-2022
     */
    @Test
    public void testSignVerify() throws GeneralSecurityException {
        ByteVerifier byteVerifier = PublicKeyVerifierFactory.publicKeyVerifierForKey(KeyTypeName.Ed25519, JWSAlgorithm.EdDSA, PUBLICKEY);
        assertTrue(byteVerifier.verify(PAYLOAD, SIGNATURE, JWSAlgorithm.EdDSA));
        assertArrayEquals(SIGNATURE, Multibase.decode(SIGNATURE_MULTIBASE));
    }
}
