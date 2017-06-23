package com.billionsfinance.bas.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.billionsfinance.bas.dao.SysUserRoleDao;
import com.billionsfinance.bas.entity.SysUserRoleVO;

public class SysUserRoleService {

	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	
	/**
	 * 新增系统用户角色
	 * @param sysUserRoleVO
	 */
	public void saveSysUserRole(SysUserRoleVO sysUserRoleVO){
		
		sysUserRoleDao.saveSysUserRole(sysUserRoleVO);
	}
	
	/**
	 * 修改系统用户角色
	 * @param sysUserRoleVO
	 */
	public void updateSysUserRole(SysUserRoleVO sysUserRoleVO){
		
		sysUserRoleDao.updateSysUserRole(sysUserRoleVO);
	}
	
	/**
	 * 根据条件查询系统用户已有权限
	 * @param whereMap
	 * @return
	 */
	public List<SysUserRoleVO> querySysUserRole(Map<String,Object> whereMap){
		
		return sysUserRoleDao.querySysUserRole(whereMap);
	}

	public List<String> findUserByRole(Map<String, Object> whereMap) {
		return sysUserRoleDao.findUserByRole(whereMap);
	}
}
