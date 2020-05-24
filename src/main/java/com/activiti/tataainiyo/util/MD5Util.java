package com.activiti.tataainiyo.util;

import org.apache.shiro.crypto.hash.SimpleHash;

public class MD5Util {
    public static final String SALT = "admin";
    public static final int ENCRYPTIONNUM = 10;
    public static String encryption(String passWord){
        SimpleHash md5 = new SimpleHash("MD5", passWord, SALT, ENCRYPTIONNUM);
        return md5.toHex();
    }
}
