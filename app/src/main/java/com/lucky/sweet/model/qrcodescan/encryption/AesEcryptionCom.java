/*
 * ECB模式：
 * 优点：
 * 1. 简单；
 * 2. 有利于并行计算；
 * 3. 误差不会被传递；
 * 缺点：
 * 1. 不能隐藏明文的模式；
 * 2. 可能对明文进行主动攻击；
 * DES ECB（电子密本方式）其实非常简单，就是将数据按照8个字节一段进行DES加密或解密得到
 * 一段段的8个字节的密文或者明文，最后一段不足8个字节（一般补0或者F），按照需求补足8个字节
 * 进行计算（并行计算），之后按照顺序将计算所得的数据连在一起即可，各段数据之间互不影响。
 * CBC模式：
 * 优点：
 * 1. 不容易主动攻击，安全性好于ECB，是SSL、IPSec的标准；
 * 缺点：
 * 1. 不利于并行计算；
 * 2. 误差传递；
 * 3. 需要初始化向量IV；
 * DES CBC（密文分组链接方式）有点麻烦，它的实现机制使加密的各段数据之间有了联系。其实现的机理如下：
 * 加密步骤如下：
 * 1. 首先将数据按照8个字节一组进行分组得到D1D2......Dn（若数据不是8的整数倍，用指定的PADDING数据补位）
 * 2. 第一组数据D1与初始化向量I异或后的结果进行DES加密得到第一组密文C1（初始化向量I为全零）
 * 3. 第二组数据D2与第一组的加密结果C1异或以后的结果进行DES加密，得到第二组密文C2
 * 4. 之后的数据以此类推，得到Cn
 * 5. 按顺序连为C1C2C3......Cn即为加密结果。
 */
package com.lucky.sweet.model.qrcodescan.encryption;


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @flag [3090] [lily] [2014-4-10] [ADD] 128位AES加密算法兼容Java与Android
 */
public class AesEcryptionCom {
    // 种子码
    private static final String CHARSET_NAME = "utf-8";

    /**
     * 加密
     *
     * @param cleartext
     * @return
     * @throws Exception
     */

    public static byte[] localAESencrypt(String seed, String cleartext)
            throws Exception {
        byte[] rawKey = getRawKey(seed.getBytes());
        return encrypt(rawKey, cleartext.getBytes(CHARSET_NAME));

    }

    /**
     * 解密
     *
     * @param encrypted
     * @return
     * @throws Exception
     */
    public static byte[] localAESdecrypt(String seed, byte[] encrypted)
            throws Exception {
        byte[] rawKey = getRawKey(seed.getBytes());
        return decrypt(rawKey, encrypted);
    }

    /**
     * 生成密钥
     *
     * @param seed
     * @return
     * @throws Exception
     */
    private static byte[] getRawKey(byte[] seed) throws Exception {
//		KeyGenerator kgen = KeyGenerator.getInstance("AES");
//		// SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
//		/*
//		 * modyfied by lilaiyi 2013-1-17 10:57:36 为了解决在Android4.2上兼容性问题
//		 * 指定算法提供者为Crypto，在Android4.2默认使用了AndroidOpenSSL与之前默认的Crypto不同
//		 * 导致加密算法不同、解密出现异常
//		 * 
//		 * SecureRandom的SHA1PRNG算法提供者在Java环境下只有SUN
//		 * 而在Android下是Crypto。可能是此算法不同导致的解密/加密无法兼容
//		 */
//		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");
//		// SecureRandom sr = SecureRandom.getInstance("AES","BC");
//		sr.setSeed(seed);
//		kgen.init(256, sr); // 192 and 256 bits may not be available
//		SecretKey skey = kgen.generateKey();
//		byte[] raw = skey.getEncoded();
//		return raw;

        //由于无法统一此算法，自己补齐16位:128位加密，长度需要16位     那如果是256就需要32位
        int length = seed.length;
        int rawLength = 16;

        byte[] raw = new byte[rawLength];
        for (int i = 0; i < rawLength; i++) {
            if (i < length) {
                raw[i] = seed[i];
            } else {
                raw[i] = 0;
            }
        }
        return raw;
    }

    /**
     * 加密
     *
     * @param raw
     * @param clear
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");

        //Java上使用BC打包为Jar后在其它环境上可能无法解密（BC包是拓展包Java环境不一定带），在Java上时就不指定了
        //Android版也不指定了，即使后来Android版的默认提供者与之前的不一致升级解决，否则一旦android
        //不给带BC提供者就会导致无法加密
        //Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding", "BC");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

//		//CBC工作模式
//		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC");
//		IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());
//		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, zeroIv);

        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    /**
     * 解密
     *
     * @param raw
     * @param encrypted
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] raw, byte[] encrypted)
            throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");

        //Java上使用BC打包为Jar后在其它环境上可能无法解密（BC包是拓展包Java环境不一定带），在Java上时就不指定了
        //Android版也不指定了，即使后来Android版的默认提供者与之前的不一致升级解决，否则一旦android
        //不给带BC提供者就会导致无法加密
        //Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding", "BC");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);

//		//CBC工作模式
//		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC");
//		IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());
//		cipher.init(Cipher.DECRYPT_MODE, skeySpec, zeroIv);

        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }

    /**
     * 二进制转为16进制串
     *
     * @param buf
     * @return
     */
    private static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    private final static String HEX = "0123456789ABCDEF";

    /**
     * 二进制转为16进制
     *
     * @param sb
     * @param b
     */
    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }

    /**
     * 十六进制转为二进制
     *
     * @param hexString
     * @return
     */
    private static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2),
                    16).byteValue();
        return result;
    }

    // ----------------------------其它测试==========================

    /**
     * 获取生成Key的十六进制形式
     *
     * @param seed
     * @return
     */
    public static String getKeyHexString(String seed) {
        try {
            return toHex(getRawKey(seed.getBytes()));
        } catch (Exception e) {
            return "Get Key to Hex Error !!!\n" + e.getMessage();
        }
    }
//	/**
//	 * 加密
//	 *
//	 * @param seed
//	 * @param cleartext
//	 * @return
//	 * @throws Exception
//	 */
//	public static String localAESencrypt( String cleartext)
//			throws Exception {
//		byte[] rawKey = getRawKey(seed.getBytes());
//		byte[] result = encrypt(rawKey, cleartext.getBytes(CHARSET_NAME));
//		return toHex(result);
//	}
//	/**
//	 * 解密
//	 *
//	 * @param seed
//	 * @param encrypted
//	 * @return
//	 * @throws Exception
//	 */
//	public static String localAESdecrypt( String encrypted)
//			throws Exception {
//		byte[] rawKey = getRawKey(seed.getBytes());
//		byte[] enc = toByte(encrypted);
//		byte[] result = decrypt(rawKey, enc);
//		return new String(result, CHARSET_NAME);
//	}
}
