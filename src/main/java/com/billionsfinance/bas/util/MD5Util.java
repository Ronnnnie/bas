package com.billionsfinance.bas.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 加密
 *
 */
public class MD5Util {

	private static final Log LOG = LogFactory.getLog(MD5Util.class);

	/**
	  * <p>Title: 生成MD5 32位密文</p>
	  * <p>Description: 生成MD5 32位密文</p>
	  * @param plainText 
	  * @return string
	  */
	public static String encryption(String plainText) {
		// String re_md5 = new String();
		StringBuffer remd5 = new StringBuffer("");
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte[] b = md.digest();

			int i;

			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0){
					i += 256;
				}
				if (i < 16){
					remd5.append("0");
				}
				remd5.append(Integer.toHexString(i));
			}

		} catch (NoSuchAlgorithmException e) {
			LOG.error("MD5加密异常！", e);
		}
		return remd5.toString();
	}

	/**
	  * <p>Title: 加密</p>
	  * <p>Description: 加密</p>
	  * @param str 
	  * @return string
	  */
	public static String doEncrypt(String str) {
		String enceypt = new String();
		try {
			String key = Encrypt.KEY; // 16位key
			Encrypt desEncrypt = new Encrypt(key.getBytes());
			enceypt = desEncrypt.doEncrypt(str);
		} catch (Exception e) {
			LOG.error("加密异常！", e);
		}
		return enceypt;
	}

	/**
	  * <p>Title: 给加密后的密文再转成MD5 32位密文</p>
	  * <p>Description: 给加密后的密文再转成MD5 32位密文</p>
	  * @param str 
	  * @return STRING
	  */
	public static String toMD5(String str) {
		String encryptStr = doEncrypt(str);
		String mdStr = encryption(encryptStr);
		return mdStr;
	}
}
