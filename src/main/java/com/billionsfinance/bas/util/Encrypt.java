package com.billionsfinance.bas.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
  * @ClassName: Encrypt
  * @Description: 加密工具
  * @author zhanghs
  * 2016年7月19日 下午4:09:07
  * Copyright: Copyright (c) 2016 
  * Company:佰仟金融
  */
public class Encrypt {

	public static final String DES = "DES"; // DES
	public static final String PADDING = "DES/ECB/PKCS5Padding"; // 补位
	public static final String NOPADDING = "DES/ECB/NoPadding"; // 不补位
	public static final String KEY = "0123456789ABCDEF";

	// 加密方式
	private String desType = DES;

	private byte[] desKey;

	/**
	
	  * 创建一个新的实例 Encrypt. 
	  * <p>Title: </p>
	  * <p>Description: </p>
	  * @param desKey 
	  */
	public Encrypt(byte[] desKey) {
		this.desKey = desKey;
	}

	/**
	  * <p>Title: 加密</p>
	  * <p>Description: 加密</p>
	  * @param plainText 
	  * @return byte[]
	  * @throws Exception 
	  */
	public byte[] doEncrypt(byte[] plainText) throws Exception {

		SecureRandom random = new SecureRandom();
		// //new SecureRandom();
		byte[] rawKeyData = desKey;
		DESKeySpec dks = new DESKeySpec(rawKeyData);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(desType);
		SecretKey key = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(PADDING);
		cipher.init(Cipher.ENCRYPT_MODE, key, random);
		byte[] data = plainText;
		byte[] encryptedData = cipher.doFinal(data);
		return encryptedData;
	}

	/**
	  * <p>Title: 对字符串实施加密,然后转换成16进制</p>
	  * <p>Description: 对字符串实施加密,然后转换成16进制</p>
	  * @param text 
	  * @return string
	  * @throws Exception 
	  */
	public String doEncrypt(String text) throws Exception {
		SecureRandom random = new SecureRandom();
		byte[] rawKeyData = desKey;
		DESKeySpec dks = new DESKeySpec(rawKeyData);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(desType);
		SecretKey key = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(PADDING);
		cipher.init(Cipher.ENCRYPT_MODE, key, random);
		byte[] encryptedData = cipher.doFinal(text.getBytes());
		StringBuffer sb = new StringBuffer();
		for (int n = 0; n < encryptedData.length; n++) {
			String stmp = (java.lang.Integer.toHexString(encryptedData[n] & 0XFF));
			if (stmp.length() == 1) {
				sb.append("0" + stmp);
			} else {
				sb.append(stmp);
			}
		}
		return sb.toString().toUpperCase();
	}
}
