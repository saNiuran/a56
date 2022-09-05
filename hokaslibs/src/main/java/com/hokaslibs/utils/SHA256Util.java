package com.hokaslibs.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 作者： Hokas
 * 时间： 2016/10/12
 * 类别：
 */

public class SHA256Util {

    private static final String ALGORITHM = "SHA-256";

    public static String SHA256Encrypt(String orignal) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (null != md) {
            byte[] origBytes = orignal.getBytes();
            md.update(origBytes);
            byte[] digestRes = md.digest();
            return getDigestStr(digestRes);
        }

        return null;
    }

    private static String getDigestStr(byte[] origBytes) {
        String tempStr;
        StringBuilder stb = new StringBuilder();
        for (byte origByte : origBytes) {
            tempStr = Integer.toHexString(origByte & 0xff);
            if (tempStr.length() == 1) {
                stb.append("0");
            }
            stb.append(tempStr);

        }
        return stb.toString();
    }

}
