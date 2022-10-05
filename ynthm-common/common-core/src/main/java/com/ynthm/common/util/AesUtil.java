package com.ynthm.common.util;

import com.ynthm.common.exception.UtilException;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Objects;

/**
 * Cipher 类 getInstance 方法需传递一个加密算法的名称作为参数，用来创建对应的 Cipher，其格式为 algorithm/mode/padding，即
 * 算法名称/工作模式/填充方式，例如 AES/CBC/PKCS5Padding。具体有哪些可选的加密方式，可以参考文档：
 *
 * <p>https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#Cipher
 *
 * @author Ynthm Wang
 */
public class AesUtil {

  public static final String KEY_ALGORITHM_AES = "AES";

  public static final String CIPHER_OPERATION_MODE_ECB = "AES/ECB/PKCS5Padding";
  public static final String CIPHER_OPERATION_MODE_CBC = "AES/CBC/PKCS5Padding";
  public static final String CIPHER_OPERATION_MODE_GCM = "AES/GCM/NoPadding";

  private static final SecureRandom SECURE_RANDOM = new SecureRandom();

  private AesUtil() {
    //    SECURE_RANDOM = SecureRandom.getInstance("SHA1PRNG", "SUN");
  }

  public enum KeyBitLength {
    /** 基本长度 */
    SIXTEEN(128),
    TWENTY_FIVE(192),
    THIRTY_TWO(256);

    KeyBitLength(int length) {
      this.length = length;
    }

    private final int length;

    public int getLength() {
      return length;
    }
  }

  /**
   * 密码生成 AES要求密钥的长度可以是128位16个字节、192位(25字节)或者256位(32字节), 位数越高, 加密强度自然越大, 但是加密的效率自然会低一 些, 因此要做好衡量
   *
   * @param salt 加盐
   * @param keyBitLength 密钥长度 bits
   * @return 密钥 new String(x, UTF_8) 根据需要 Base64.getEncoder().encodeToString(x)
   */
  public static byte[] generateAesKey(final String salt, KeyBitLength keyBitLength) {
    Objects.requireNonNull(salt);
    try {
      KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM_AES);
      SECURE_RANDOM.setSeed(salt.getBytes());
      kg.init(keyBitLength.getLength(), SECURE_RANDOM);
      // 产生原始对称密钥
      SecretKey secretKey = kg.generateKey();
      // key转换,根据字节数组生成AES密钥
      return secretKey.getEncoded();
    } catch (NoSuchAlgorithmException e) {
      throw new UtilException(e);
    }
  }

  /**
   * 加密
   *
   * @param data 需要加密的明文
   * @param key 加密用密钥 base64 byte[]
   * @return 加密结果 base64
   */
  public static byte[] encryptECB(byte[] data, byte[] key) {
    byte[] result;
    try {
      Cipher cipher = Cipher.getInstance(CIPHER_OPERATION_MODE_ECB);
      cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, KEY_ALGORITHM_AES));
      result = cipher.doFinal(data);
    } catch (NoSuchPaddingException
        | NoSuchAlgorithmException
        | InvalidKeyException
        | BadPaddingException
        | IllegalBlockSizeException e) {
      throw new UtilException(e);
    }
    return result;
  }

  /**
   * 解密 ECB 模式
   *
   * @param data data.getBytes(UTF_8) 如果编码 Base64.getDecoder().decode(data)
   * @param key key.getBytes(UTF_8)
   * @return 解密 new String(x, UTF_8) 根据需要 Base64.getEncoder().encodeToString(x)
   */
  public static byte[] decryptECB(byte[] data, byte[] key) {
    byte[] result;
    try {
      Cipher cipher = Cipher.getInstance(CIPHER_OPERATION_MODE_ECB);
      cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"));
      result = cipher.doFinal(data);
    } catch (NoSuchPaddingException
        | NoSuchAlgorithmException
        | InvalidKeyException
        | BadPaddingException
        | IllegalBlockSizeException e) {
      throw new UtilException(e);
    }
    return result;
  }

  /**
   * 初始向量IV, 初始向量IV的长度规定为128位16个字节, 初始向量的来源为随机生成.
   *
   * @return 结果 new String(x, UTF_8) 根据需要 Base64.getEncoder().encodeToString(x)
   */
  public static byte[] iv() {
    byte[] bytes = new byte[16];
    SECURE_RANDOM.nextBytes(bytes);
    return bytes;
  }

  /**
   * 解密时用到的密钥, 初始向量IV, 加密模式, Padding模式必须和加密时的保持一致, 否则则会解密失败.
   *
   * @param data data.getBytes(UTF_8) 如果编码 Base64.getDecoder().decode(data)
   * @param key key.getBytes(UTF_8)
   * @return 解密 new String(x, UTF_8) 根据需要 Base64.getEncoder().encodeToString(x)
   */
  public static byte[] encryptCBC(byte[] data, byte[] key, byte[] iv) {
    byte[] result;
    try {
      IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
      Cipher cipher = Cipher.getInstance(CIPHER_OPERATION_MODE_CBC);
      cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), ivParameterSpec);
      result = cipher.doFinal(data);
    } catch (NoSuchPaddingException
        | NoSuchAlgorithmException
        | InvalidKeyException
        | BadPaddingException
        | IllegalBlockSizeException
        | InvalidAlgorithmParameterException e) {
      throw new UtilException(e);
    }
    return result;
  }

  /**
   * @param data data.getBytes(UTF_8) 如果编码 Base64.getDecoder().decode(data)
   * @param key key.getBytes(UTF_8)
   * @return 解密 new String(x, UTF_8) 根据需要 Base64.getEncoder().encodeToString(x)
   */
  public static byte[] decryptCBC(byte[] data, byte[] key, byte[] iv) {
    byte[] result;

    try {
      //  CBC 模式需要用到初始向量参数
      IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
      Cipher cipher = Cipher.getInstance(CIPHER_OPERATION_MODE_CBC);
      cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), ivParameterSpec);
      result = cipher.doFinal(data);
    } catch (NoSuchPaddingException
        | NoSuchAlgorithmException
        | InvalidKeyException
        | BadPaddingException
        | IllegalBlockSizeException
        | InvalidAlgorithmParameterException e) {
      throw new UtilException(e);
    }

    return result;
  }

  /**
   * @param data data.getBytes(UTF_8) 如果编码 Base64.getDecoder().decode(data)
   * @param key key.getBytes(UTF_8)
   * @return 结果 new String(x, UTF_8) 根据需要 Base64.getEncoder().encodeToString(x)
   */
  public static byte[] encryptGCM(byte[] data, byte[] key, byte[] iv, byte[] aad) {
    byte[] result;
    try {
      Cipher cipher = Cipher.getInstance(CIPHER_OPERATION_MODE_GCM);
      cipher.init(
          Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), new GCMParameterSpec(128, iv));
      cipher.updateAAD(aad);
      result = cipher.doFinal(data);
    } catch (NoSuchPaddingException
        | NoSuchAlgorithmException
        | InvalidKeyException
        | BadPaddingException
        | IllegalBlockSizeException
        | InvalidAlgorithmParameterException e) {
      throw new UtilException(e);
    }
    return result;
  }

  /**
   * @param data data.getBytes(UTF_8) 如果编码 Base64.getDecoder().decode(data)
   * @param key key.getBytes(UTF_8)
   * @return 解密 new String(x, UTF_8) 根据需要 Base64.getEncoder().encodeToString(x)
   */
  public static byte[] decryptGCM(byte[] data, byte[] key, byte[] iv, byte[] aad) {
    byte[] result;
    try {
      Cipher cipher = Cipher.getInstance(CIPHER_OPERATION_MODE_GCM);
      cipher.init(
          Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new GCMParameterSpec(128, iv));
      cipher.updateAAD(aad);
      result = cipher.doFinal(data);
    } catch (NoSuchPaddingException
        | NoSuchAlgorithmException
        | InvalidKeyException
        | BadPaddingException
        | IllegalBlockSizeException
        | InvalidAlgorithmParameterException e) {
      throw new UtilException(e);
    }
    return result;
  }
}
