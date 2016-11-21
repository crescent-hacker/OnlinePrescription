package util;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.util.Random;

/**
 * Author: Difan Chen
 * Student No: 4901496
 * Login name: dc946
 * Date: 15/03/16
 * Desc: A collection class of security tools and constants for this project
 */
public class CryptoUtil {
    //the initial vector for AES
    public static String INIT_VECTOR = "ThisIsTheCSCI992";

    /**
     * Generate random 128-bit binary string
     *
     * @return binary string
     */
    static public String genRandomBiStr() {
        int max = 255;
        int min = 128;
        int times = 16;
        Random random = new Random();
        StringBuffer biStrBuf = new StringBuffer("");
        for (int i = 0; i < times; i++) {
            int tmpInt = random.nextInt(max) % (max - min + 1) + min;
            String tmpStr = Integer.toBinaryString(tmpInt);
            biStrBuf.append(tmpStr);
        }
        return biStrBuf.toString();
    }

    /**
     * AES Encryption
     */
    public static String encryptByAES(String key, String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.encode(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * AES Decryption
     */
    public static String decryptByAES(String key, String encrypted) throws Exception {
        IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes("UTF-8"));
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        String result = null;
        try {
            byte[] original;
            original = cipher.doFinal(Base64.decode(encrypted));
            result = new String(original);
        }catch (Exception e){
            System.out.println("Decryption error.");
            System.out.println("==========================");
        }
        return result;
    }

    /**
     * MD5 hash function
     *
     * @param input the preimage
     * @return hash value
     * @throws NoSuchAlgorithmException
     */
    public static String MD5(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] bytes = md.digest(input.getBytes());
        StringBuilder result = new StringBuilder();
        for (byte by : bytes) {
            String hs = Integer.toHexString(by & 0xff);
            if (hs.length() == 1) {
                result.append("0").append(hs);
            } else {
                result.append(hs);
            }
        }
        return result.toString();
    }

    /**
     * generator suitable key for AES key input
     */
    public static String keyGen(String password) throws NoSuchAlgorithmException {
        String passwdHashed = MD5(password);
        byte[] bytes = passwdHashed.getBytes();
        int halfLen = bytes.length / 2;
        String result = "";
        for (int i = 0; i < halfLen; i++) {
            int val = (Integer.valueOf(String.valueOf((char) bytes[i]), 16) & 0xff) ^ (Integer.valueOf(String.valueOf((char) bytes[i + 1]), 16) & 0xff);
            result+= Integer.toHexString(val);
        }
        return result;
    }

    public static void main(String[] args) throws Exception{
        String a = CryptoUtil.MD5("stefantest");
        String b = CryptoUtil.MD5("tanhaotest");
        String c = CryptoUtil.MD5("kunaltest");
        String d = CryptoUtil.MD5("ning_wangtest");
        String e = CryptoUtil.MD5("pengyu_huotest");
        String f = CryptoUtil.MD5("hrudaitest");
        String g = CryptoUtil.MD5("fangyitest");
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);
        System.out.println(e);
        System.out.println(f);
        System.out.println(g);
    }
}

