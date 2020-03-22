package com.jero.common.utils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description AES、SHA、MD5、RSA加解密算法
 * @Author lixuetao
 * @Date 2020/3/22
 **/
public class CryptoUtils {

    public static final String KEY_SHA="SHA";
    public static final String KEY_MD5="MD5";

    public static final String KEY_ALGORTHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    public static final String PUBLIC_KEY = "RSAPublicKey";// 公钥
    public static final String PRIVATE_KEY = "RSAPrivateKey";// 私钥

    /**
     * MD5加密
     * @param data 待加密数据
     * @return String加密结果
     * @throws Exception
     */
    public static String encryptByMD5(String data)throws Exception{
        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
        md5.update(parseHexStr2Byte(data));
        return parseByte2HexStr(md5.digest());
    }

    /**
     * SHA加密
     * @param data 待加密数据
     * @return String加密结果
     * @throws Exception
     */
    public static String encryptBySHA(String data) throws Exception{
        MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
        sha.update(data.getBytes("UTF-8"));
        return parseByte2HexStr(sha.digest());
    }

    /**
     * AES加密字符串
     * @param content 需要被加密的字符串
     * @param password 加密需要的密码
     * @return 密文
     */
    public static String encryptByAES(String content,String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");// 创建AES的Key生产者
            kgen.init(128, new SecureRandom(password.getBytes()));// 利用用户密码作为随机数初始化出
            SecretKey secretKey = kgen.generateKey();// 根据用户密码，生成一个密钥
            byte[] enCodeFormat = secretKey.getEncoded();// 返回基本编码格式的密钥，如果此密钥不支持编码，则返回null。
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");// 转换为AES专用密钥
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化为加密模式的密码器
            byte[] result = cipher.doFinal(byteContent);// 加密
            return parseByte2HexStr(result);
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密AES加密过的字符串
     *
     * @param hexStr AES加密过过的内容
     * @param password 加密时的密码
     * @return 明文
     */
    public static String decryptByAES(String hexStr, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");// 创建AES的Key生产者
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();// 根据用户密码，生成一个密钥
            byte[] enCodeFormat = secretKey.getEncoded();// 返回基本编码格式的密钥
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");// 转换为AES专用密钥
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化为解密模式的密码器
            byte[] result = cipher.doFinal(parseHexStr2Byte(hexStr));
            return new String(result); // 明文
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**将二进制转换成16进制
     * @param buff
     * @return
     */
    public static String parseByte2HexStr(byte buff[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buff.length; i++) {
            String hex = Integer.toHexString(buff[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**将16进制转换为二进制
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length()/2];
        for (int i = 0;i< hexStr.length()/2; i++) {
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 初始化密钥
     *
     * @return
     * @throws Exception
     */
    public static Map<String,Object> initKey() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORTHM,new BouncyCastleProvider());
        keyPairGenerator.initialize(1024,new SecureRandom());
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // 公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        // 私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);

        return keyMap;
    }

    /**
     * 取得公钥，并转化为String类型
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return EncodeUtils.encodeBase64(parseByte2HexStr(key.getEncoded()));
    }

    /**
     * 取得私钥，并转化为String类型
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return EncodeUtils.encodeBase64(parseByte2HexStr(key.getEncoded()));
    }

    /**
     * 用私钥加密
     *
     * @param hexStr 加密数据
     * @param key 密钥
     * @return
     * @throws Exception
     */
    public static String encryptByPrivateKey(String hexStr, String key) throws Exception {
        // 解密密钥
        byte[] keyBytes =parseHexStr2Byte(EncodeUtils.decodeBase64toString(key));
        // 取私钥
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return parseByte2HexStr(cipher.doFinal(parseHexStr2Byte(hexStr)));
    }

    /**
     * 用私钥解密 *
     *
     * @param hexStr 加密数据
     * @param key 密钥
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKey(String hexStr, String key) throws Exception {
        // 对私钥解密
        byte[] keyBytes = parseHexStr2Byte(EncodeUtils.decodeBase64toString(key));
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return parseByte2HexStr(cipher.doFinal(parseHexStr2Byte(hexStr)));
    }

    /**
     * 用公钥加密
     *
     * @param hexStr 加密数据
     * @param key 密钥
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKey(String hexStr, String key) throws Exception {
        // 对公钥解密
        byte[] keyBytes = parseHexStr2Byte(EncodeUtils.decodeBase64toString(key));
        // 取公钥
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return parseByte2HexStr(cipher.doFinal(parseHexStr2Byte(hexStr)));
    }

    /**
     * 用公钥解密
     *
     * @param hexStr 加密数据
     * @param key 密钥
     * @return
     * @throws Exception
     */
    public static String decryptByPublicKey(String hexStr, String key) throws Exception {
        // 对私钥解密
        byte[] keyBytes =parseHexStr2Byte(EncodeUtils.decodeBase64toString(key));
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return parseByte2HexStr(cipher.doFinal(parseHexStr2Byte(hexStr)));
    }

    /**
     * 用私钥对信息生成数字签名
     *
     * @param hexData 加密数据
     * @param privateKey 私钥
     * @return
     * @throws Exception
     */
    public static String sign(String hexData, String privateKey) throws Exception {
        // 解密私钥
        byte[] keyBytes = parseHexStr2Byte(EncodeUtils.decodeBase64toString(privateKey));
        // 构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        // 指定加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        // 取私钥匙对象
        PrivateKey privateKey2 = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        // 用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateKey2);
        signature.update(parseHexStr2Byte(hexData));
        return EncodeUtils.encodeBase64(parseByte2HexStr(signature.sign()));
    }

    /**
     * 校验数字签名
     *
     * @param hexData 加密数据
     * @param publicKey 公钥
     * @param sign 数字签名
     * @return
     * @throws Exception
     */
    public static boolean verify(String hexData, String publicKey, String sign) throws Exception {
        // 解密公钥
        byte[] keyBytes=parseHexStr2Byte(EncodeUtils.decodeBase64toString(publicKey));
        // 构造X509EncodedKeySpec对象
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        // 指定加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        // 取公钥匙对象
        PublicKey publicKey2 = keyFactory.generatePublic(x509EncodedKeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicKey2);
        signature.update(parseHexStr2Byte(hexData));
        // 验证签名是否正常
        return signature.verify(parseHexStr2Byte(EncodeUtils.decodeBase64toString(sign)));
    }

}
