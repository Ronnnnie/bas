package com.billionsfinance.bas.server;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.entity.SysRoleVO;

public interface ISysRoleServer {

	/**
	 * 分页查询角色
	 * @param pageVO
	 * @param whereMap
	 * @return
	 */
	public PageVO queryRolePage(PageVO pageVO, Map<String, Object> whereMap);

	/**
	 * 查询所有角色
	 * @return
	 */
	public List<Map<String,Object>> queryAllRole();

	
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
	 * 精确查询角色
	 * @param whereMap
	 * @return
	 * @throws Exception
	 */
	public List<SysRoleVO> findSysRoleBywhere(Map<String,Object> whereMap) throws Exception;

	public void delRole(Map<String, Object> whereMap);
}
