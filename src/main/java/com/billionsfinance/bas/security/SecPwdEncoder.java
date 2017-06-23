package com.billionsfinance.bas.security;

import org.springframework.security.authentication.encoding.PasswordEncoder;

import com.billionsfinance.bas.util.MD5Util;

/**
  * @ClassName: SecPwdEncoder
  * @Description: SecPwdEncoder
  * @author zhanghs
  * 2016年7月19日 下午4:04:34
  * Copyright: Copyright (c) 2016 
  * Company:佰仟金融
  */
public class SecPwdEncoder implements PasswordEncoder {

	@Override
	public String encodePassword(String rawPass, Object salt) {
		return MD5Util.toMD5(rawPass);
	}

	@Override
	public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
		String pass1 = "" + encPass;
		String pass2 = encodePassword(rawPass, salt);
		return pass1.toUpperCase().equals(pass2.toUpperCase());

	}

}
