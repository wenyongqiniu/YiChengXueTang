package com.example.yichengxuetang.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import javax.crypto.Cipher;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;


public class RSADecrypt {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void main(String[] args) throws Exception {
        String encrypted = args[0];
        String prikey = args[1];

        String result = decrypt(encrypted, prikey);
        System.out.println(result);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String decrypt(String cryptograph, String prikey) throws Exception {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(prikey));
        PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(keySpec);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        byte[] b = Base64.getDecoder().decode(cryptograph);
        return new String(cipher.doFinal(b));
    }
}