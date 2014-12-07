package ru.qiwi.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {

    private final static Logger logger = LoggerFactory.getLogger(HashUtils.class);

    public static String hash(String password) {
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            byte[] passBytes = password.getBytes();
            return convertByteArrayToHexString(sha256.digest(passBytes));
        } catch (NoSuchAlgorithmException e) {
            logger.error("NoSuchAlgorithmException", e);
            throw new RuntimeException(e);
        }
    }


    private static String convertByteArrayToHexString(byte[] arrayBytes) {
        StringBuilder stringBuffer = new StringBuilder();
        for (byte arrayByte : arrayBytes) {
            stringBuffer.append(Integer.toString((arrayByte & 0xff) + 0x100, 16).substring(1));
        }
        return stringBuffer.toString();
    }


    public static void main(String[] args) {
        //test
        String originalPassword = "password";
        String generatedSecuredPasswordHash1 = hash(originalPassword);
        String generatedSecuredPasswordHash2 = hash(originalPassword);
        System.out.println(generatedSecuredPasswordHash1);
        System.out.println(generatedSecuredPasswordHash2);
        System.out.println(generatedSecuredPasswordHash1.equals(generatedSecuredPasswordHash2));
    }

}
