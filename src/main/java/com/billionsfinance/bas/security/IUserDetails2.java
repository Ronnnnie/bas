package com.billionsfinance.bas.security;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public abstract interface IUserDetails2 extends UserDetails {
	  public abstract String getUserCode();    //用户编号

	  public abstract void setUserCode(String paramString);

	  public abstract String getUserCName();  //用户名称

	  public abstract void setUserCName(String paramString);

	  public abstract String getClientIP();

	  public abstract void setClientIP(String paramString);

	  public abstract Collection<? extends GrantedAuthority> getAuthorities();

	  public abstract void setAuthorities(Collection<? extends GrantedAuthority> paramCollection);

	  public abstract String[] getAuthRoles();

	  public abstract void setAuthRoles(String[] paramArrayOfString);

	  public abstract boolean isAdmin();

	  public abstract void setAdminFlag(boolean paramBoolean);
	  
	  public abstract List<Map<String, Object>> getSysMenu(String sysCode);//权限菜单
	  
	  public abstract void setSysMenu(List<Map<String, Object>> sysMenu);
	  
	  public abstract List<Map<String, Object>> getRoleList();//角色

	  public abstract void setRoleList(List<Map<String, Object>> roleList);
	  
	  public abstract void  setUserTel(String userTel);  //手机号
	  public abstract void  setEmail(String email);   //邮箱
	  public abstract String  getUserTel();  //手机号
	  public abstract String  getEmail();   //邮箱
	  public abstract String getSysUserCode();

	  public abstract void setSysUserCode(String sysUserCode);
	  
	  public abstract List<Map<String, Object>> getRoleListAll();//角色

	  public abstract void setRoleListAll(List<Map<String, Object>> roleList);
	  
      public abstract List<Map<String, Object>> getSysMenuAll();//权限菜单
	  
	  public abstract void setSysMenuAll(List<Map<String, Object>> sysMenu);
	  
	  public abstract List<Map<String, Object>> getSysUserCodeAll();//权限菜单
	  
	  public abstract void setSysUserCodeAll(List<Map<String, Object>> userCodeList);
	  
	  /**
	   * 用户是否能访问应用系统列表
	   * @return
	   */
      public abstract List<Map<String, Object>> getSysEnabledAll();
	  
      /**
	   * 用户是否能访问应用系统列表
	   * @return
	   */
	  public abstract void setSysEnabledAll(List<Map<String, Object>> sysEnabledAll);
	  
	  /**
	   * 获取当前用户是否有访问当前系统权限
	   * 
	   * @param sysCode
	   * @return  true:有访问系统权限    false:无访问系统权限
	   */
	  public abstract boolean isSysEnabled(String sysCode);
	  
	  /**
	   * 设置第一次登陆是否校验
	   * @param firstLoginCheck  默认false 未效验  true 效验证
	   * @return
	   */
	  public abstract void setFirstLoginCheck(boolean firstLoginCheck);
	  public abstract boolean getFirstLoginCheck();
	  
}
