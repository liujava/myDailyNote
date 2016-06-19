package com.liud.dailynote;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/************************************************************************************
 * Copyright (c) 2016 ©  All Rights Reserved.
 * This software is published under liudong.
 * Software License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 * <p>
 * File name:      RsaTest.java
 * Create on:      2016/6/15
 * Author :        liudong
 * <p>
 * ChangeList
 * ----------------------------------------------------------------------------------
 * Date									Editor						ChangeReasons
 * 2016/6/15			                    liudong				    Create
 ************************************************************************************/
public class RsaTest {
    /** RSA:加密算法 */
    public static final String  KEY_ALGORITHM =             "RSA";

    public static final String  KEY_ALGORITHM_RSA_NONE =             "RSA/ECB/PKCS1Padding";

    public static final String KEY_ALGORITHM_RSA_SHA  = "RSA/ECB/OAEPWithSHA1AndMGF1Padding";

    /** SHA1WithRSA:用SHA算法进行签名，用RSA算法进行加密 */
    public static final String  SIGN_ALGORITHMS =           "SHAWithRSA";

    /** 公钥KEY*/
    public static final String  PUB_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCx2zk90WzGIzGjl7opxIFdoinxyp+pjvN1wC0OTrGk6o/c0RyrmQstu690IJPXu/6urLmB7/T2Iy/UUvSkqwzL7oX6D7llTjyR4MQjwvPVy7JZR2WYu1dvPgQn++/DVBuFDtfYW6pRlIi27iPxXyQ3ozAfHo5biR5nNelhu0lnVQIDAQAB";

    /* 私钥 */
    public static final String  PRI_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALHbOT3RbMYjMaOXuinEgV2iKfHKn6mO83XALQ5OsaTqj9zRHKuZCy27r3Qgk9e7/q6suYHv9PYjL9RS9KSrDMvuhfoPuWVOPJHgxCPC89XLsllHZZi7V28+BCf778NUG4UO19hbqlGUiLbuI/FfJDejMB8ejluJHmc16WG7SWdVAgMBAAECgYEAmfYICzdrTenRYqhJgzaUNhXW8XRR2lng7yG43xXIOdbDSofKpdEKGEDMlV5OHQakZVkoDQ9Honq7QLW/CXz4yW71iAK8SFn22LGKxzdSgPZkGmVZ+ZKDPjmKg9QyzI8VbHWKuIHCD0iJgaN7SF5B1CtsGRs66f+aNTLyLLr2QAECQQDWtXfjJi0keQ13mLpAYfc7e7dX0zna5zqkUbA2q1JYPG8j9GIJ/j3NrUUJbhFiBEJUX5BYtP7zXOXA8/QNqvtVAkEA1A9vRllNQNEiAG3ZlvVSh3RXWPsVDQkerzWKBlMvQZIa+eQ/Q9o+b0tOvvt1IIvwaSXpqBEcpC09vzfjoNy8AQJBAMNUmFr4Uj1KO6xAL8F+3pMo/CVULuAtWLZA8tTpi6JmaJ4HKGH7AHLrXVE052+KfGWSAxoQn5j7PLILvk3o7XkCQDcG4ksQ9Tjyi64s0x+W/RllGR1f2fCOA0ZX0D8f6s1LCnD5x2jmAvmCQybPvW76oSHH0r/n4NTBYJpz+D9PyAECQG+WZ/QoXpyHiRr1JUdjq2BX7+EA8hsC6+pmBzOnRzbOxWxmlfMNC5gzhhOyAVMdQM8UaRgKBOSsqsWz6artj1Y=";

    /** 块加密的大小，是不要太大，否则效率会低 */
    private static final int    KEY_SIZE =                  1024;

    /** 获取公钥的key */
    public static final String PUBLIC_KEY =                "RSAPublicKey";

    /** 获取私钥的key */
    public static final String PRIVATE_KEY =               "RSAPrivateKey";

    /** RSA最大加密明文大小 */
    private static final int    MAX_ENCRYPT_BLOCK =        117;

    /** RSA最大解密密文大小 */
    private static final int    MAX_DECRYPT_BLOCK =         128;


    /**
     * * 生成密钥对 *
     *
     * @return KeyPair *
     * @throws Exception
     */
    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(KEY_SIZE, new SecureRandom());
        KeyPair keyPair = keyPairGen.generateKeyPair();
        return keyPair;
    }

    /**
     * * 生成公钥 *
     *
     * @param keyPair *
     * @return RSAPublicKey *
     * @throws Exception
     */
    public static String generateRSAPublicKey(KeyPair keyPair) throws Exception {

        RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
        return Base64.encode(publicKey.getEncoded());
    }

    /**
     * * 生成私钥 *
     *
     * @param keyPair *
     * @return RSAPublicKey *
     * @throws Exception
     */
    public static String generateRSAPrivateKey(KeyPair keyPair) throws Exception {

        RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
        return Base64.encode(privateKey.getEncoded());
    }

    /**
     * * 生成公私钥对 *
     *
     * @return Map *
     * @throws Exception
     */
    public static Map<String, Object> generateRSAKey() throws Exception {
        KeyPair keyPair = generateKeyPair();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, generateRSAPublicKey(keyPair));
        keyMap.put(PRIVATE_KEY, generateRSAPrivateKey(keyPair));
        return keyMap;
    }

    /**
     * 用私钥对信息生成数字签名
     *
     * @param content                待签名数据
     * @param privateKey             私钥(BASE64编码)
     * @param input_charset          编码格式
     * @return                       签名值
     */
    public static String sign(String content, String privateKey, String input_charset) throws Exception {
        PKCS8EncodedKeySpec priPKCS8 	= new PKCS8EncodedKeySpec( Base64.decode(privateKey) );
        KeyFactory keyf 				= KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey priKey 				= keyf.generatePrivate(priPKCS8);

        Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
        signature.initSign(priKey);
        signature.update(content.getBytes(input_charset));
        byte[] signed = signature.sign();
        return Base64.encode(signed);
    }

    /**
     * 用公钥对信息RSA验签名检查
     *
     * @param content                待签名数据
     * @param sign                   签名值
     * @param yzf_public_key         公钥
     * @param input_charset          编码格式
     * @return                       布尔值
     */
    public static boolean verify(String content, String sign, String yzf_public_key, String input_charset) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        byte[] encodedKey = Base64.decode(yzf_public_key);
        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

        Signature signature = Signature.getInstance(SIGN_ALGORITHMS);

        signature.initVerify(pubKey);
        signature.update( content.getBytes(input_charset) );

        return signature.verify( Base64.decode(sign) );
    }

    /**
     * 使用公钥RSA加密
     * @param content               明文
     * @param yzf_public_key        公钥
     * @param input_charset         编码格式
     * @return                      解密后的字符串
     */
    public static String encrypt(String content, String yzf_public_key, String input_charset) throws Exception {

        String str = null;
        ByteArrayOutputStream writer = null;
        try {
            PublicKey pubKey = getPublicKey(yzf_public_key);

            //TODO Cipher cipher = Cipher.getInstance(KEY_ALGORITHM_RSA_NONE);
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM_RSA_SHA, "BC");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);

            // 设置编码格式
            InputStream ins = new ByteArrayInputStream(content.getBytes(input_charset));
            writer = new ByteArrayOutputStream();

            byte[] buf = new byte[MAX_DECRYPT_BLOCK];
            int bufl;

            while ((bufl = ins.read(buf)) != -1) {
                byte[] block = null;

                if (buf.length == bufl) {
                    block = buf;
                } else {
                    block = new byte[bufl];
                    for (int i = 0; i < bufl; i++) {
                        block[i] = buf[i];
                    }
                }
                writer.write(cipher.doFinal(block));
            }
            str = new String(Base64.encode(writer.toByteArray()));
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                writer = null;
            }
        }
        return str;
    }

    /**
     * 使用私钥RSA解密
     * @param content 密文
     * @param private_key 商户私钥
     * @param input_charset 编码格式
     * @return 解密后的字符串
     */
    public static String decrypt(String content, String private_key, String input_charset) throws Exception {
        PrivateKey prikey = getPrivateKey(private_key);

        // TODO: 2016/6/15 Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM_RSA_SHA);
        cipher.init(Cipher.DECRYPT_MODE, prikey);

        InputStream ins = new ByteArrayInputStream(Base64.decode(content));
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        //rsa解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
        byte[] buf = new byte[MAX_DECRYPT_BLOCK];
        int bufl;

        while ((bufl = ins.read(buf)) != -1) {
            byte[] block = null;

            if (buf.length == bufl) {
                block = buf;
            } else {
                block = new byte[bufl];
                for (int i = 0; i < bufl; i++) {
                    block[i] = buf[i];
                }
            }
            writer.write(cipher.doFinal(block));
        }

        return new String(writer.toByteArray(), input_charset);
    }

    /**
     * 得到私钥
     *
     * @param key 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {

        byte[] keyBytes = Base64.decode(key);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);

        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

        return privateKey;
    }

    /**
     * 得到公钥
     *
     * @param key 加密字符串（经过base64编码）
     * @throws Exception
     */
    public static PublicKey getPublicKey(String key) throws Exception {

        byte[] keyBytes;

        keyBytes = Base64.decode(key);

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        PublicKey publicKey = keyFactory.generatePublic(keySpec);

        return publicKey;
    }

    public static void main(String args[]) {
        try {
            Map rsaKeyInfo = RsaTest.generateRSAKey();
            String pubKey = rsaKeyInfo.get(PUBLIC_KEY).toString();         // 公钥
            String privKey = rsaKeyInfo.get(PRIVATE_KEY).toString();       // 私钥

            System.out.print(pubKey + "\n" + privKey);

            String content = "我叫liujava";

            String encryStr = RsaTest.encrypt(content, pubKey, "UTF-8");

            System.out.println("加密结果：" + encryStr);

            String decryptStr = RsaTest.decrypt(encryStr, privKey, "UTF-8");

            System.out.println("解密结果：" + decryptStr);

//            String encryStr = "VxhK8Ku73TltoujAl0mE9J8O2tgsFvDQc3CuSJWv/L9MtLBom3XgPd8KngJMlaV7wKafK5JsR9g0zSYCjKOpi26FaBEGPI8yfXvR7s+MDZ0dEvdcuvqYcSccS7jwLm1H5ZFZT1f/Vytxw4/cjGafcZm+BAV6BzeJz6sskMRT2S4=";
//
//            String privKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALHbOT3RbMYjMaOXuinEgV2iKfHKn6mO83XALQ5OsaTqj9zRHKuZCy27r3Qgk9e7/q6suYHv9PYjL9RS9KSrDMvuhfoPuWVOPJHgxCPC89XLsllHZZi7V28+BCf778NUG4UO19hbqlGUiLbuI/FfJDejMB8ejluJHmc16WG7SWdVAgMBAAECgYEAmfYICzdrTenRYqhJgzaUNhXW8XRR2lng7yG43xXIOdbDSofKpdEKGEDMlV5OHQakZVkoDQ9Honq7QLW/CXz4yW71iAK8SFn22LGKxzdSgPZkGmVZ+ZKDPjmKg9QyzI8VbHWKuIHCD0iJgaN7SF5B1CtsGRs66f+aNTLyLLr2QAECQQDWtXfjJi0keQ13mLpAYfc7e7dX0zna5zqkUbA2q1JYPG8j9GIJ/j3NrUUJbhFiBEJUX5BYtP7zXOXA8/QNqvtVAkEA1A9vRllNQNEiAG3ZlvVSh3RXWPsVDQkerzWKBlMvQZIa+eQ/Q9o+b0tOvvt1IIvwaSXpqBEcpC09vzfjoNy8AQJBAMNUmFr4Uj1KO6xAL8F+3pMo/CVULuAtWLZA8tTpi6JmaJ4HKGH7AHLrXVE052+KfGWSAxoQn5j7PLILvk3o7XkCQDcG4ksQ9Tjyi64s0x+W/RllGR1f2fCOA0ZX0D8f6s1LCnD5x2jmAvmCQybPvW76oSHH0r/n4NTBYJpz+D9PyAECQG+WZ/QoXpyHiRr1JUdjq2BX7+EA8hsC6+pmBzOnRzbOxWxmlfMNC5gzhhOyAVMdQM8UaRgKBOSsqsWz6artj1Y=";
//            String decryptStr = RSA.decrypt(encryStr, privKey, "UTF-8");
////
//            System.out.println("解密结果：" + decryptStr);

//            String public_exponent = "010001";
//            String modulus = "009e1b8381423dbbf9bbfa2d963755f6752928f8793a1d966170a3ffb9bad061d096ec9c2d7129f3639c17af17a2d8ee30de94afec1da9533a4e4d00331c31f67a967b9c3d98545e210615a7ff1c5a02dbdefbfffac0c7507240160f076d21ec78b4e6f2b47918d8a47ca195b223343626dd638f0cc8ebf0d21966e51ead24652f";
//
//            RSAPublicKey rsaPublicKey = RSA.getPublicKey(modulus, public_exponent);


        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
