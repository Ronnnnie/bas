package com.billionsfinance.bas.server;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.entity.SysUserRoleVO;
import com.billionsfinance.bas.entity.SystemUserVO;

public interface ISystemUserServer {

	
	/**
	 * 分配系统时，如果非超级用户，查询当前用户拥有的系统
	 * @param whereMap
	 * @return
	 */
	public List<Map<String,Object>> querySysByCurrentUser(Map<String,Object> whereMap);
	
	
	/**
	 * 查询已经存在的系统
	 * @param whereMap
	 * @return
	 */
	public List<Map<String,Object>> queryUserSysExist(Map<String,Object> whereMap);

	/**
	 * 给用户添加系统权限
	 * @param sysUsers
	 * @param currentUser
	 * @return
	 */
	public void addSystemUser(List<SystemUserVO> sysUsers, String currentUser,String userCode);
	
	/**
	 * 根据条件查询系统用户
	 * @param whereMap
	 * @return
	 */
	public List<SystemUserVO> querySysUserByWhere(Map<String,Object> whereMap);
	
	/**
	 * 根据系统用户查询该用户
	 * @param whereMap
	 * @return
	 */
	public List<Map<String,Object>> queryRoleBySysCode(Map<String,Object> whereMap);
	
	/**
	 * 查询当前用户已经存在的系统
	 * @param whereMap
	 * @return
	 */
	public List<Map<String,Object>> queryRoleExist(Map<String,Object> whereMap);


	/**
	 * 保存给系统用户分配的角色
	 * @param sysUserRoles
	 * @param currentUser
	 * @param userCode
	 */
	public void saveUserRole(List<SysUserRoleVO> sysUserRoles,
			String currentUser, String sysUserId);
	
	/**
	 * 保存用户分配的角色
	 * @param sysUserRoleVO
	 */
	public void saveUserRoleVO(SysUserRoleVO sysUserRoleVO);
	
	
	/**
	 * 分页查询系统用户
	 * @param whereMap
	 * @return
	 */
	public PageVO querySysUserPage(Map<String,Object> whereMap,PageVO pageVO);
	
	/**
	 * 修改系统用户表
	 * @param systemUserVO
	 * @return 
	 */
	public String updateSystemUser(SystemUserVO systemUserVO);

	/**
	 * 系统用户管理时，新增系统用户
	 * @param sysUsers
	 * @param removesysUsers 
	 */
	public void addSaveUser(List<SystemUserVO> addsysUsers, List<SystemUserVO> removesysUsers);
	
	/**
	 * 分页查询当前用户访问系统列表
	 * @param whereMap
	 * @return
	 */
	public PageVO queryUserBindingPage(Map<String,Object> whereMap,PageVO pageVO);
	
	/**
	 * 新增绑定用户
	 * @param map
	 */
	public void addSysUserBinding(Map<String,Object> map);
	
	/**
	 * 修改状态
	 * @param systemUserVO
	 */
	public void updateSysUserValidInd(SystemUserVO systemUserVO);
	
	/**
	 * 根据usercode,syscode删除对象
	 * @param map
	 */
	public void deleteSysUserBinding(Map<String,String> map);
	
	/**
	 * 根据系统编码和系统中的账号查找该系统账号是否绑定过
	 * @param map
	 * @return
	 */
	public List<Map<String,String>> findSysUsername(Map<String, Object> map);

	/**
	 * 设置系统账号为默认账号
	 * @param map
	 */
	public void opDefaultUser(Map<String, Object> map);
	
	/**
	 * 查询系统用户的 绑定的账号
	 * @param map
	 * @throws Exception
	 */
	public List<Map<String, Object>> findSysAccount(Map<String, Object> whereMap)throws Exception;
}
