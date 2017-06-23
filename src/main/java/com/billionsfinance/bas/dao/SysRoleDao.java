package com.billionsfinance.bas.dao;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.SysRoleVO;

public interface SysRoleDao {

	/**
	 * 根据用户查询用户是管理员的系统编码集合
	 * @param userCode
	 * @return
	 */
	public List<String> querySysCodeByUser(String userCode);
	
	/**
	 * 根据条件统计角色总数
	 * @param whereMap
	 * @return
	 */
	public Long queryRoleCount(Map<String,Object> whereMap);
	
	/**
	 * 分页查询角色列表
	 * @param whereMap
	 * @return
	 */
	public List<Map<String,Object>> queryRolePage(Map<String,Object> whereMap);
	
	/**
	 * 新建角色
	 * @param sysRoleVO
	 */
	public void saveSysRole(SysRoleVO sysRoleVO);
	
	/**
	 * 修改角色
	 * @param sysRoleVO
	 */
	public void updateSysRole(SysRoleVO sysRoleVO);
	
	/**
	 * 根据Code查询角色
	 * @param sysRoleVO
	 */
	public SysRoleVO findSysRoleByCode(String roleCode);
	
	/**
	 * 根据条件查询角色
	 * @param whereMap
	 * @return
	 */
	public List<SysRoleVO> findSysRoleBywhere(Map<String,Object> whereMap) throws Exception;

	/**
	 * 查询所有角色
	 * @return
	 */
	public List<Map<String, Object>> findAllSysRole();

	public void delRole(Map<String, Object> whereMap);
	
}
