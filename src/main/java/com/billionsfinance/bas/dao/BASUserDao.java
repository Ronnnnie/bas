package com.billionsfinance.bas.dao;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.BASUserVO;

public interface BASUserDao {

	/**
	 * 根据条件查询用户数量
	 * @param alsUser
	 * @return
	 */
	public Long queryUserCount(Map<String, Object> whereMap);
	
	/**
	 * 根据条件分页查询用户
	 * @param whereMap
	 * @return
	 */
	public List<Map<String, Object>> queryUserListPage(Map<String,Object> whereMap);
	
	/**
	 * 根据用户编码查找用户
	 * @param userCode
	 * @return
	 */
	public BASUserVO findUserByCode(String userCode);
	
//	public void saveUser(AlsUserVO alsUserVO);
	
	/**
	 * 新增用户
	 * @param alsUserVO
	 */
	public void saveUser(BASUserVO alsUserVO);
	
	/**
	 * 修改用户
	 * @param alsUserVO
	 */
	public void updateUser(BASUserVO alsUserVO);
	
	/**
	 * 
	 * @Title:checkUser
	 * @Description:登录验证用户
	 * @author aihua.tang
	 * @date: 2016年6月14日
	 */
	public BASUserVO checkUser(BASUserVO alsUserVO);
	

	public void deleteUser(String userCode);

	
	
	
	
	
	
	
	
	/**
	 * 修改密码
	 * @param alsUserVO
	 */
	public void updatePwd(BASUserVO alsUserVO);
	
	/**
	 * 新建外部用户时，查询最大用户编码 
	 * @param userMaxCode
	 */
	public void updateUserMaxCode(Map<String,Object> whereMap);
	
	/**
	 * 新建外部用户时，更新最大用户编码 
	 * @return
	 */
	public Map<String, Object> findUserMaxCode();
	
	public void deleteRoleRe(Map<String, Object> whereMap);

	public void updateStatus(Map<String, Object> whereMap);

	
}
