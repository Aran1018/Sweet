package com.lucky.sweet.model.qrcodescan.encryption;

import android.util.Base64;

/**
 * Created by C on 2018/3/20.
 */

public class QCcodeEncryption {
    private static final String cdkey="My_Cdkey_for_Aes";
    /**
     * 加密首先Aes加密后转Base64
     *
     * @param clearText 需要加密文本
     * @param cdkey     密钥
     * @return
     * @throws Exception
     */

    public static String CodeEncryption(String clearText) throws Exception {

        byte[] bytes = AesEcryptionCom.localAESencrypt(cdkey, clearText);

        return Base64.encodeToString(bytes, Base64.DEFAULT);

    }

    /**
     * 解密 首先Base64转码后AES解码

     * @param ciphertext 暗文
     * @return
     * @throws Exception
     */
    public static String Codedecrypt( String ciphertext) throws Exception {
        byte[] decode = Base64.decode(ciphertext, Base64.DEFAULT);
        return new String(AesEcryptionCom.localAESdecrypt(cdkey, decode));
    }
}
