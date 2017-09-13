package com.wanfin.fpd.common.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

/**
 * 描述：数字签名类
 *
 * @author 
 * 创建时间：2009-4-2
 */
public class CryptorUtils {

    private final static int RSA_CRYPTO_ENC_BLOCK_LENGTH = 117;
    private final static int RSA_CRYPTO_DEC_BLOCK_LENGTH = 128;
    private final static String RSA_PUBLIC_KEY_EXPONENT = "010001";

    /**
     * 字符串转字节
     * @param src 字符串
     * @param encoding 字符集
     * @return
     */
    public static byte[] stringToBytes(String src, String encoding){
        byte[] bytes;
        try {
            bytes = src.getBytes(encoding);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
        return bytes;
    }

    /**
     * 进行MD5WithRSA数字签名
     * @param privateKey 私钥
     * @param data 签名数据字节数组
     * @return
     * @throws Exception 
     */
    public static byte[] sign(PrivateKey privateKey, byte[] data) throws Exception{
        byte[] signature = null;
        //获取签名对象
        Signature signer=Signature.getInstance("MD5WithRSA"); 
        signer.initSign(privateKey);
        signer.update(data); 
        signature=signer.sign();

        return signature;
    }

    /**
     * 进行MD5WithRSA数字签名，结果转换成base64字符串
     * @param privateKey 私钥
     * @param data 签名数据字节数组
     * @return
     * @throws Exception 
     */
    public static String signIntoBase64(PrivateKey privateKey, byte[] data) throws Exception{
        byte[] bytes = sign(privateKey, data);
        return Base64.encodeBytes(bytes);
    }

    /**
     * 进行MD5WithRSA数字签名，结果转换成one2Two字符串
     * @param privateKey 私钥
     * @param data 签名数据字节数组
     * @return
     * @throws Exception 
     */
    public static String signIntoHex(PrivateKey privateKey, byte[] data) throws Exception{
        byte[] bytes = sign(privateKey, data);
        return ByteUtil.bytes2HexStr(bytes);
    }

    /**
     * 验证私钥签名
     * @param publicKey 公钥
     * @param data 签名源串
     * @param sign 签名
     * @return
     * @throws Exception 
     */
    public static boolean verify(PublicKey publicKey, byte[] data, byte[] sign) throws Exception{
        boolean b = false;
        if (publicKey == null || data == null || sign == null){
            System.out.println("publicKey="+publicKey);
            System.out.println("data="+data);
            System.out.println("sign="+sign);
            throw new NullPointerException("验证参数有空值");
        }

        //生成验证对象
        Signature signer=Signature.getInstance("MD5WithRSA"); 
        signer.initVerify(publicKey); 
        signer.update(data); 

        //验证签名
        b = signer.verify(sign);
        return b;
    }

    /**
     * 使用公钥或私钥进行加密
     * @param key 公钥或私钥
     * @param data 数据字节数组
     * @return
     * @throws Exception 
     */
    public static byte[] encrypt(Key key, byte[] data) throws Exception{
        Cipher cipher = getCipher(key, Cipher.ENCRYPT_MODE);
        return encrypt(cipher, data);
    }

    /**
     * 使用公钥或私钥进行加密，结果为base64字符串
     * @param key 公钥或私钥
     * @param data 数据字节数组
     * @return
     * @throws Exception 
     */
    public static String encryptIntoBase64(Key key, byte[] data) throws Exception{
        byte[] bytes = encrypt(key, data);
        return Base64.encodeBytes(bytes);
    }

    /**
     * 使用公钥或私钥进行加密，结果为one2Two字符串
     * @param key 公钥或私钥
     * @param data 数据字节数组
     * @return
     * @throws Exception 
     */
    public static String encryptIntoHex(Key key, byte[] data) throws Exception{
        byte[] bytes = encrypt(key, data);
        return ByteUtil.bytes2HexStr(bytes);
    }

    /**
     * 使用公钥或私钥进行解密
     * @param key 公钥或私钥
     * @param data 数据字节数组
     * @return
     * @throws Exception 
     */
    public static byte[] decrypt(Key key, byte[] data) throws Exception{
        Cipher cipher = getCipher(key, Cipher.DECRYPT_MODE);
        return decrypt(cipher, data);
    }

    /**
     * 使用公钥或私钥进行解密，结果转换成目标字符集
     * @param key 公钥或私钥
     * @param data 数据字节数组
     * @param toEncoding 目标字符集
     * @return
     * @throws Exception 
     */
    public static String decrypt(Key key, byte[] data, String toEncoding) throws Exception{
        Cipher cipher = getCipher(key, Cipher.DECRYPT_MODE);
        return decrypt(cipher, data, toEncoding);
    }

    /**
     * 通过公、私钥获取并初始化Cipher对象
     * @param key 公钥或私钥
     * @param mode Cipher._MODE
     * @return
     * @throws Exception 
     */
    public static Cipher getCipher(Key key, int mode) throws Exception{
        Cipher cipher = null;
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        cipher = Cipher.getInstance(keyFactory.getAlgorithm()); 
        cipher.init(mode, key);
        return cipher;
    }

    /**
     * 加密
     * @param cipher 已经调用过cipher.init()方法的Cipher对象，用于提高性能
     * @param data 需要加密的字节数组
     * @return
     * @throws BadPaddingException 
     * @throws IllegalBlockSizeException 
     */
    private static byte[] encrypt(Cipher cipher, byte[] data) throws Exception{
        if (data == null || cipher == null){
            System.out.println("data="+data);
            System.out.println("cipher="+cipher);
            throw new NullPointerException("验证参数有空值");
        }
        byte[] tmp ={};
        byte[] encrypt ={};
        if (data.length > RSA_CRYPTO_ENC_BLOCK_LENGTH){
            boolean isBreak = false;
            for (int i=1;;i++){
                if ((i*RSA_CRYPTO_ENC_BLOCK_LENGTH) <= data.length){
                    tmp = new byte[RSA_CRYPTO_ENC_BLOCK_LENGTH];
                    System.arraycopy(data, (i-1)*RSA_CRYPTO_ENC_BLOCK_LENGTH, tmp, 0, RSA_CRYPTO_ENC_BLOCK_LENGTH);
                }else{
                    isBreak = true;
                    int len = data.length - ((i-1)*RSA_CRYPTO_ENC_BLOCK_LENGTH);
                    tmp = new byte[len];
                    System.arraycopy(data, (i-1)*RSA_CRYPTO_ENC_BLOCK_LENGTH, tmp, 0, len);
                }          
                byte[] en = cipher.doFinal(tmp);
                byte[] buf = new byte[encrypt.length+en.length];
                System.arraycopy(encrypt, 0, buf, 0, encrypt.length);
                System.arraycopy(en, 0, buf, encrypt.length, en.length);
                encrypt = buf;
                if (isBreak){
                    break;
                }
            }
        }else{
            encrypt = cipher.doFinal(data);
        }
        return encrypt;
    }

    /**
     * 解密
     * @param cipher 已经调用过cipher.init()方法的Cipher对象，用于提高性能
     * @param data 需要解密的字节数组
     * @return
     * @throws BadPaddingException 
     * @throws IllegalBlockSizeException 
     */
    private static byte[] decrypt(Cipher cipher, byte[] data) throws Exception{
        if (data == null || cipher == null){
            System.out.println("data="+data);
            System.out.println("cipher="+cipher);
            throw new NullPointerException("验证参数有空值");
        }
        byte[] decrypt ={};
        byte[] tmp ={};
        if (data.length > RSA_CRYPTO_DEC_BLOCK_LENGTH){
            for (int i=1;;i++){
                if ((i*RSA_CRYPTO_DEC_BLOCK_LENGTH) <= data.length){
                    tmp = new byte[RSA_CRYPTO_DEC_BLOCK_LENGTH];
                    System.arraycopy(data, (i-1)*RSA_CRYPTO_DEC_BLOCK_LENGTH, tmp, 0, RSA_CRYPTO_DEC_BLOCK_LENGTH);
                    byte[] en = cipher.doFinal(tmp);
                    byte[] buf = new byte[decrypt.length+en.length];
                    System.arraycopy(decrypt, 0, buf, 0, decrypt.length);
                    System.arraycopy(en, 0, buf, decrypt.length, en.length);
                    decrypt = buf;
                }else{
                    break;
                }       
            }
        }else{
            decrypt = cipher.doFinal(data);
        }

        return decrypt;
    }

    /**
     * 解密成指定字符集的字符串
     * @param cipher 已经调用过cipher.init()方法的Cipher对象，用于提高性能
     * @param data 需要加密的字节数组
     * @param toEncoding 字符串的字符集
     * @return
     * @throws Exception 
     */
    private static String decrypt(Cipher cipher, byte[] data, String toEncoding) throws Exception{
        if (data == null || cipher == null || toEncoding == null){
            System.out.println("data="+data);
            System.out.println("cipher="+cipher);
            System.out.println("toEncoding="+toEncoding);
            throw new NullPointerException("验证参数有空值");
        }
        byte[] bytes = decrypt(cipher, data);
        try {
            String ret = new String(bytes, toEncoding);
            return ret;
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }


    ///////////////////////////////////////////////////////


    /**
     * 根据私钥的one2two字符串，构造私钥对象
     * @param prikey
     * @return
     * @throws Exception 
     */
    public static PrivateKey toPrivateKey(String prikey) throws Exception{
        byte[] data = null;
        data = ByteUtil.hexStr2Bytes(prikey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(data);
        KeyFactory keyFactory;
        keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey key = keyFactory.generatePrivate(keySpec);
        return key;
    }

    /**
     * 根据公钥modulus的one2two字符串，构造公钥对象
     * @param modulus
     * @return
     * @throws Exception 
     */
    public static PublicKey toPublicKey(String modulus) throws Exception{

        BigInteger mod = new BigInteger(modulus, 16);
        BigInteger exp = new BigInteger(RSA_PUBLIC_KEY_EXPONENT, 16);

        //生成公钥
        RSAPublicKeySpec pks = new RSAPublicKeySpec(mod, exp);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey pubKey = kf.generatePublic(pks);
        return pubKey;

    }

    /**
     * 根据私钥的pkcs8格式字符串，构造公钥对象
     * @param pkcs8Key
     * @return
     * @throws Exception 
     */
    public static PrivateKey toPrivateKeyFromPkcs8(String pkcs8Key) throws Exception{
        String privKeyPEM = pkcs8Key.replace("-----BEGIN PRIVATE KEY-----", "");
        privKeyPEM = privKeyPEM.replace("-----END PRIVATE KEY-----", "");

        byte[] decoded = Base64.decode(privKeyPEM);
        return toPrivateKey(decoded);
    }

    /**
     * 创建私钥
     * @param bytes PrivateKey.getEncoded()的字节数组
     * @return
     * @throws Exception 
     */
    public static PrivateKey toPrivateKey(byte[] bytes) throws Exception{
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    /**
     * 根据公钥的pkcs8格式字符串，构造公钥对象
     * @param pkcs8Key
     * @return
     * @throws Exception 
     */
    public static PublicKey toPublicKeyFromPkcs8(String pkcs8Key) throws Exception{
        String publicKeyPEM = pkcs8Key.replace("-----BEGIN PUBLIC KEY-----", "");
        publicKeyPEM = publicKeyPEM.replace("-----END PUBLIC KEY-----", "");

        byte[] decoded = Base64.decode(publicKeyPEM);
        return toPublicKey(decoded);

    }

    /**
     * 创建公钥
     * @param bytes PublicKey.getEncoded()的字节数组
     * @return
     * @throws Exception 
     */
    public static PublicKey toPublicKey(byte[] bytes) throws Exception{
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    /**
     * 获取公钥字节数组
     * @param publicKey
     * @return
     */
    public static byte[] publicKeyToByte(PublicKey publicKey){
        byte[] bs = publicKey.getEncoded();
        return bs;
    }

    /**
     * 获取公钥的pkcs8格式
     * @param publicKey
     * @return
     */
    public static String publicKeyPkcs8(PublicKey publicKey){
        return Base64.encodeBytes(publicKeyToByte(publicKey));
    }

    /**
     * 获取公钥的modulus的16进制数字符串
     * @param publicKey
     * @return
     */
    public static String modulusToHex(PublicKey publicKey){		
        return ((RSAPublicKey)publicKey).getModulus().toString(16);
    }

    /**
     * 获取私钥字节数组
     * @param privateKey
     * @return
     */
    public static byte[] privateKeyToByte(PrivateKey privateKey){
        byte[] bs = privateKey.getEncoded();
        return bs;
    }

    /**
     * 获取私钥的pkcs8格式
     * @param privateKey
     * @return
     */
    public static String privateKeyPkcs8(PrivateKey privateKey){
        return Base64.encodeBytes(privateKeyToByte(privateKey));
    }

    /**
     * 生成非对称密钥对
     * @param algorithm 算法名称，可以有以下值
     * 			DiffieHellman，注意，该密钥对如果调用key.getAlgorithm()，返回的是“DH”而不是“DiffieHellman”
     * 			DSA
     * 			RSA
     * 			EC
     * @param length 密钥长度：1024/2048
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static KeyPair genKeyPair(String algorithm, int length) throws NoSuchAlgorithmException{
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(algorithm);
        keyGen.initialize(length);
        KeyPair key = keyGen.generateKeyPair();
        return key;
    }
}