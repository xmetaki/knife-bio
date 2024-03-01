package org.xmetaki.knife.bio.utils;

import org.junit.Before;
import org.junit.Test;
import sun.misc.BASE64Encoder;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Base64;

import static org.junit.Assert.*;

public class AESUtilTest {
    public AESUtil aesUtil;

    @Before
    public void setup() {
        aesUtil = new AESUtil();
    }

    @Test
    public void encrypt() throws Exception{
        final String source = "123456";
        final byte[] sourceBytes = source.getBytes();
        final byte[] encrypt = aesUtil.encrypt(sourceBytes);
        System.out.println(Base64.getEncoder().encodeToString(encrypt));
    }

    @Test
    public void decrypt() {
        final String source = "wJp7qpISLWeSEMbKQTcM/0LnWDwCzN/ddpZlCG3b/OQ=";
        final byte[] sourceBytes = Base64.getDecoder().decode(source);
        final byte[] decrypt = aesUtil.decrypt(sourceBytes);
        System.out.println(new String(decrypt));
    }
    @Test
    public void testKeyGenerator() throws Exception{
        final KeyGenerator aes = KeyGenerator.getInstance("AES");
        final int secretLength = 16;
        aes.init(secretLength * 8); // 位密钥
        final SecretKey secretKey = aes.generateKey();
        assertEquals(secretKey.getEncoded().length, secretLength);
        final String s = Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }
}