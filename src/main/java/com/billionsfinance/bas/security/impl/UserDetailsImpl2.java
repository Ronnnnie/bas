package com.billionsfinance.bas.security.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.billionsfinance.bas.security.IUserDetails2;
import com.billionsfinance.bas.util.StringUtil;

/**
 * @ClassName: UserDetailsImpl
 * @Description: UserDetailsImpl
 * @author zhanghs 2016年7月19日 下午4:04:46 Copyright: Copyright (c) 2016
 *         Company:佰仟金融
 */
public class UserDetailsImpl2 extends User implements IUserDetails2 {

	private static final long serialVersionUID = -345392627055344676L;
	private String userCode;
	private String userCName;
	private String sysUserCode; // 工号对应系统账号 (老系统使�??)
	private String clientIP;
	private String[] authRoles;
	private Collection<? extends GrantedAuthority> authorities;
	private boolean isAdmin;
	private List<Map<String, Object>> sysMenu; // 权限菜单
	private String userTel; // 手机�??
	private String email; // 邮箱
	private List<Map<String, Object>> roleList; // 角色
	// als系统编码 sys_als
	private final String SYS_CODE = "SYS_ALS";

	private List<Map<String, Object>> roleListAll; // 角色

	private List<Map<String, Object>> sysMenuAll; // 权限菜单

	private List<Map<String, Object>> sysUserCodeAll;

	private List<Map<String, Object>> sysEnabledAll;

	private boolean firstLoginCheck;

	/**
	
	  * 创建一个新的实例 UserDetailsImpl. 
	  * <p>Title: </p>
	  * <p>Description: </p>
	  * @param username 
	  * @param password 
	  * @param enabled 
	  * @param accountNonExpired 
	  * @param credentialsNonExpired 
	  * @param accountNonLocked 
	  * @param grantedAuthorities 
	  * @throws IllegalArgumentException 
	  */
	public UserDetailsImpl2(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> grantedAuthorities) throws IllegalArgumentException {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
				grantedAuthorities);
	}

	public String getUserCName() {
		return this.userCName;
	}

	public void setUserCName(String userCName) {
		this.userCName = userCName;
	}

	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getClientIP() {
		return this.clientIP;
	}

	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}

	public String[] getAuthRoles() {
		return this.authRoles;
	}

	public void setAuthRoles(String[] authRoles) {
		this.authRoles = authRoles;
	}

	public boolean isAdmin() {
		return this.isAdmin;
	}

	public void setAdminFlag(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	/**
	  * <p>Title: getSysMenu</p>
	  * <p>Description: </p>
	  * @param sysCode  
	  * @return list
	  * @see com.billionsfinance.bas.security.IUserDetails2#getSysMenu(java.lang.String)
	  */
	public List<Map<String, Object>> getSysMenu(String sysCode) {
		if (sysMenuAll != null) {
			sysMenu = StringUtil.getListByParam(sysMenuAll, "SYSCODE", sysCode);
		}
		return sysMenu;
	}

	public void setSysMenu(List<Map<String, Object>> sysMenu) {
		this.sysMenu = sysMenu;
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Map<String, Object>> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Map<String, Object>> roleList) {
		this.roleList = roleList;
	}

	public String getSysUserCode() {
		return sysUserCode;
	}

	public void setSysUserCode(String sysUserCode) {
		this.sysUserCode = sysUserCode;
	}

	public List<Map<String, Object>> getRoleListAll() {
		return roleListAll;
	}

	public void setRoleListAll(List<Map<String, Object>> roleListAll) {
		this.roleListAll = roleListAll;
	}

	public List<Map<String, Object>> getSysMenuAll() {
		return sysMenuAll;
	}

	public void setSysMenuAll(List<Map<String, Object>> sysMenuAll) {
		this.sysMenuAll = sysMenuAll;
	}

	public List<Map<String, Object>> getSysUserCodeAll() {
		return sysUserCodeAll;
	}

	public void setSysUserCodeAll(List<Map<String, Object>> sysUserCodeAll) {
		this.sysUserCodeAll = sysUserCodeAll;
	}

	/**
	 * 用户是否能访问应用系统列�??
	 * 
	 * @return list
	 */
	public List<Map<String, Object>> getSysEnabledAll() {
		return sysEnabledAll;
	}

	/**
	 * 用户是否能访问应用系统列�??
	 * 
	 * @param sysEnabledAll 
	 */
	public void setSysEnabledAll(List<Map<String, Object>> sysEnabledAll) {
		this.sysEnabledAll = sysEnabledAll;
	}

	/**
	 * 获取当前用户是否有访问当前系统权�??
	 * 
	 * @param sysCode 
	 * @return true:有访问系统权�?? false:无访问系统权�??
	 */
	public boolean isSysEnabled(String sysCode) {
		if (sysCode == null || "".equals(sysCode)) {
			return false;
		}

		sysCode = sysCode.toUpperCase();
		if (sysEnabledAll != null) {
			List<Map<String, Object>> list = StringUtil.getListByParam(sysEnabledAll, "SYSCODE", sysCode);
			if (list != null && list.size() > 0) {
				// 1-有效,0-失效
				String isEnabled = list.get(0).get("VALIDIND").toString();
				if ("0".equals(isEnabled)) {
					return false;
				}
			} else {
				if (SYS_CODE.equals(sysCode)) {
					return true;
				}
				return false;
			}
		} else {
			// 过滤统一平台系统编码
			if (!SYS_CODE.equals(sysCode)) {
				return false;
			}
		}
		return true;
	}

	public void setFirstLoginCheck(boolean firstLoginCheck) {
		this.firstLoginCheck = firstLoginCheck;
	}

	public boolean getFirstLoginCheck() {
		return firstLoginCheck;
	}
}
