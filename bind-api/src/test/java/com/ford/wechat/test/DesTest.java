package com.ford.wechat.test;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.SecureRandom;

public class DesTest {

    private static final String UTF8 = "UTF-8";


    public static void main(String[] args) throws Exception {
        String plainText = "Hello , world !";
        String key = "12345678";    //要求key至少长度为8个字符

        SecureRandom random1 = SecureRandom.getInstance("SHA1PRNG");
        DESKeySpec keySpec = new DESKeySpec(key.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("des");
        SecretKey secretKey = keyFactory.generateSecret(keySpec);

        Cipher cipher = Cipher.getInstance("des");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, random1);
        byte[] cipherData = cipher.doFinal(plainText.getBytes());

        String cipherText = Base64.encodeBase64String(cipherData);
        System.out.println("cipherText : " + cipherText);



        SecureRandom random2 = SecureRandom.getInstance("SHA1PRNG");
        String encodeStr = URLEncoder.encode(cipherText, UTF8);
        System.out.println("encodeStr : " + encodeStr);
        //PtRYi3sp7TOR69UrKEIicA==

        String decodeStr = URLDecoder.decode(encodeStr, UTF8);
        System.out.println("decodeStr : " + decodeStr);

        cipher.init(Cipher.DECRYPT_MODE, secretKey, random2);
        byte[] plainData = cipher.doFinal(Base64.decodeBase64(decodeStr));
        System.out.println("plainText : " + new String(plainData));
        //Hello , world !
    }
}
