package com.billionsfinance.bas.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.billionsfinance.als.security.impl.UserDetailsImpl;
import com.billionsfinance.bas.constants.CommonConstants;
import com.billionsfinance.bas.entity.BASUserVO;
import com.billionsfinance.bas.service.BASUserService;
import com.billionsfinance.bas.util.SpringService;

/**
  * @ClassName: MyUserDetailsService
  * @Description: 
  * @author zhanghs
  *  2016年7月19日 下午4:04:03
  * Copyright: Copyright (c) 2016 
  * Company:佰仟金融
  */
public class MyUserDetailsService implements UserDetailsService {
	
	ThreadLocal< ?> local = new ThreadLocal<>();
	

	/**
	 * 
	 * * 根据用户名获取用户 - 用户的角色、权限等信息
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// IUserDetails userDetails = null;
		UserDetails userDetails = null;
		 BASUserService basUserService = SpringService.BASUSERSERVICE;
		BASUserVO checkUser = new BASUserVO();
		checkUser.setUserCode(username);
		checkUser.setStatus(CommonConstants.USER_STATUS_FLAG_VALID); // 1表示用户可用
		try {
			checkUser = basUserService.checkUser(checkUser);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println(e1);
		}
		
		try {
			Collection<GrantedAuthority> authList = getAuthorities();
			if(checkUser == null){
				userDetails = new UserDetailsImpl(username, "1", true, true, true, true, authList);
			}else{
				userDetails = new UserDetailsImpl(username, checkUser.getPwd().toUpperCase(), true, true, true, true, authList);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return userDetails;
	}

	/**
	 * 
	 * 获取用户的角色权限 , 为了降低实验的难度 ， 这里去掉了根据用户名获取角色的步骤
	 * 
	 * @return collection
	 * 
	 */
	private Collection<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
		authList.add(new SimpleGrantedAuthority("ROLE_USER"));
		authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		return authList;
	}
}
