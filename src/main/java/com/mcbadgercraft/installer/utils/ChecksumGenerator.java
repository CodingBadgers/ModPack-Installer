package com.mcbadgercraft.installer.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ChecksumGenerator {

    final protected static char[] hexArray = "0123456789abcdef".toCharArray();
    private static final int BUFFER = 128 << 6; // 8192
    private static final String MD_5 = "MD5";
    private static final String SHA_1 = "SHA-1";

    private ChecksumGenerator() {
    }

    public static String createSha1(File file) throws IOException, NoSuchAlgorithmException {
        return createHash(file, SHA_1);
    }

    public static String createMD5(File file) throws IOException, NoSuchAlgorithmException {
        return createHash(file, MD_5);
    }

    private static String createHash(File file, String algorithm) throws IOException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        return createHash(file, digest);
    }

    private static String createHash(File file, MessageDigest digest) throws IOException {
        InputStream fis = null;
        try {
            fis = new FileInputStream(file);
            int n = 0;
            byte[] buffer = new byte[BUFFER];
            while (n != -1) {
                n = fis.read(buffer);
                if (n > 0) {
                    digest.update(buffer, 0, n);
                }
            }
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
        return bytesToHex(digest.digest());
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
