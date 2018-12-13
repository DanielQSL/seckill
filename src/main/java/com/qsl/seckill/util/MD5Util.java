package com.qsl.seckill.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author DanielQSL
 * @date 2018-10-08
 */
public class MD5Util {

    private static final String salt = "1a2b3c4d";

    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    public static String inputPassToFormPass(String inputPass) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String formPassToDatabasePass(String formPass, String saltDb) {
        String str = "" + saltDb.charAt(0) + saltDb.charAt(2) + formPass + saltDb.charAt(5) + saltDb.charAt(4);
        return md5(str);
    }

    public static String inputPassToDatabasePass(String inputPass, String saltDb) {
        String formPass = inputPassToFormPass(inputPass);
        String dataBasePass = formPassToDatabasePass(formPass, saltDb);
        return dataBasePass;
    }

    public static void main(String[] args) {
        System.out.println(inputPassToDatabasePass("123456", "1a2b3c4d"));
    }
}
