package com.billionsfinance.bas.server;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.BASUserVO;
import com.billionsfinance.bas.entity.DepartVO;
import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.entity.SysUserRoleVO;

public interface IBASUserServer {

	
	/**
	 * 根据条件分页查询用户
	 * @param whereMap
	 * @return
	 */
	public PageVO initQueryUser(Map<String, Object> whereMap,PageVO pageVO);
	
	/**
	 * 分页查询系统用户
	 * @param whereMap
	 * @param depart 
	 * @return
	 */
	public PageVO querySysUserPage(Map<String,Object> whereMap,PageVO pageVO, Map<String, Object> depart);
	
	
	/**
	 * 根据用户编码查找用户
	 * @param userCode
	 * @return
	 */
	public BASUserVO findUserByCode(String userCode);
	
	/**
	 * 新增用户
	 * @param alsUserVO
	 * @return 
	 * @throws Exception 
	 */
	public String saveUser(BASUserVO alsUserVO,SysUserRoleVO sysUserRoleVO) throws Exception;
	
	/**
	 * 修改用户
	 * @param alsUserVO
	 */
	public void updateUser(BASUserVO alsUserVO,SysUserRoleVO sysUserRoleVO);
	
	
	/**
	 * 修改密码
	 * @param alsUserVO
	 */
	public void updatePwd(BASUserVO alsUserVO);
	
	/**
	 * 用户失效
	 * @param alsUserVO
	 */
	public void removeUser(BASUserVO alsUserVO);

	/**
	 * 开启用户
	 * @param alsUserVO
	 */
	public void openUser(BASUserVO alsUserVO);
	
	public List<DepartVO> loadDepart();
	
	public List<Map<String, Object>> queryAllDepart();
	
	public void deleteUser(String userCode);
	
	public List<String> findUserDept(String currentUser, Map<String, Object> departInfo);

	public BASUserVO checkUser(BASUserVO alsUserVO);

	public SysUserRoleVO queryRoleByMap(Map<String, Object> whereMap);

	public void deleteRoleRe(Map<String, Object> whereMap);

	public void updateStatusByRole(Map<String, Object> whereMap);

	

	

}
