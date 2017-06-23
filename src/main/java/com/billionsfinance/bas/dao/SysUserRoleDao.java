package com.billionsfinance.bas.dao;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.SysUserRoleVO;

public interface SysUserRoleDao {

	/**
	 * 新增系统用户角色
	 * @param sysUserRoleVO
	 */
	public void saveSysUserRole(SysUserRoleVO sysUserRoleVO);
	
	/**
	 * 修改系统用户角色
	 * @param sysUserRoleVO
	 */
	public void updateSysUserRole(SysUserRoleVO sysUserRoleVO);
	
	/**
	 * 根据条件查询系统用户已有权限
	 * @param whereMap
	 * @return
	 */
	public List<SysUserRoleVO> querySysUserRole(Map<String,Object> whereMap);

	public List<String> findUserByRole(Map<String, Object> whereMap);
	
}
