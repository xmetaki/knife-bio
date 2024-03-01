package org.xmetaki.knife.bio.utils;

import com.sun.corba.se.spi.activation.Server;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * 对称加密工具类
 */
public class AESUtil {
    private final String aesKey;
    // 算法
    private static final String algorithm = "AES/ECB/PKCS5Padding";
    // 加密cipher
    private Cipher encryptedCipher;
    // 解密cipher
    private Cipher decryptedCipher;

    public static final String DEFAULT_AES_KEY = "1234567890123456";

    public AESUtil() {
        this(DEFAULT_AES_KEY);
    }
    public AESUtil(String aesKey) {
        this.aesKey = DEFAULT_AES_KEY;
        this.init();
    }

    public String getDynamicAesKey() {
        String result = DEFAULT_AES_KEY;
        try {
            final KeyGenerator generator = KeyGenerator.getInstance("AES");
            generator.init(16 * 8);
            final SecretKey secretKey = generator.generateKey();
            result = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 初始化编码器和解码器
    public void init() {
        // 基于密钥和算法生成密钥管理器
        SecretKeySpec aesSpec = new SecretKeySpec(aesKey.getBytes(), "AES");
        try {
            encryptedCipher = Cipher.getInstance(algorithm); //编码器
            encryptedCipher.init(Cipher.ENCRYPT_MODE, aesSpec); //初始化为加密模式的密码器
            decryptedCipher = Cipher.getInstance(algorithm); //解码器
            decryptedCipher.init(Cipher.DECRYPT_MODE, aesSpec);
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    // 加密
    public byte[] encrypt(byte[] src) {
       byte[] bytes = null;
       try {
           bytes = encryptedCipher.doFinal(src);
       } catch (Exception e) {
          e.printStackTrace();
       }
       return bytes;
    }

    public byte[] encrypt(byte[] src, int offset, int length) {
        byte[] bytes = null;
        try {
            bytes = encryptedCipher.doFinal(src, offset, length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }


    public byte[] decrypt(byte[] src) {
        byte[] bytes = null;
        try {
            bytes = decryptedCipher.doFinal(src);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }
}
