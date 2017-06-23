package com.billionsfinance.bas.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.billionsfinance.bas.dao.SysRoleDao;
import com.billionsfinance.bas.entity.SysRoleVO;

public class SysRoleService {

	@Autowired
	private SysRoleDao sysRoleDao;
	
	/**
	 * 根据用户查询用户是管理员的系统编码集合
	 * @param userCode
	 * @return
	 */
	public List<String> querySysCodeByUser(String userCode){
		
		return sysRoleDao.querySysCodeByUser(userCode);
	}
	
	/**
	 * 根据条件统计角色总数
	 * @param whereMap
	 * @return
	 */
	public Long queryRoleCount(Map<String,Object> whereMap){
		
		return sysRoleDao.queryRoleCount(whereMap);
	}
	
	/**
	 * 分页查询角色列表
	 * @param whereMap
	 * @return
	 */
	public List<Map<String,Object>> queryRolePage(Map<String,Object> whereMap){
		
		return sysRoleDao.queryRolePage(whereMap);
	}
	
	/**
	 * 新建角色
	 * @param sysRoleVO
	 */
	public void saveSysRole(SysRoleVO sysRoleVO){
		
		sysRoleDao.saveSysRole(sysRoleVO);
	}
	
	/**
	 * 修改角色
	 * @param sysRoleVO
	 */
	public void updateSysRole(SysRoleVO sysRoleVO){
		
		sysRoleDao.updateSysRole(sysRoleVO);
	}
	
	/**
	 * 根据Code查询角色
	 * @param sysRoleVO
	 */
	public SysRoleVO findSysRoleByCode(String roleCode){
		
		return sysRoleDao.findSysRoleByCode(roleCode);
	}
	
	/**
	 * 根据条件查询角色
	 * @param whereMap
	 * @return
	 */
	public List<SysRoleVO> findSysRoleBywhere(Map<String,Object> whereMap) throws Exception{
		
		return sysRoleDao.findSysRoleBywhere(whereMap);
	}

	public List<Map<String, Object>> findAllSysRole() {
		return sysRoleDao.findAllSysRole();
	}

	public void delRole(Map<String, Object> whereMap) {
		sysRoleDao.delRole(whereMap);
	}
}
