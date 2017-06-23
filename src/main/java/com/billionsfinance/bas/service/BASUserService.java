package com.billionsfinance.bas.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.billionsfinance.bas.dao.BASUserDao;
import com.billionsfinance.bas.entity.BASUserVO;

public class BASUserService {

	@Autowired
	private BASUserDao alsUserDao;
	
	/**
	 * 根据条件查询用户数量
	 * @param alsUser
	 * @return
	 */
	public Long queryUserCount(Map<String, Object> whereMap){
		
		return alsUserDao.queryUserCount(whereMap);
	}
	
	/**
	 * 根据条件分页查询用户
	 * @param whereMap
	 * @return
	 */
	public List<Map<String, Object>> queryUserListPage(Map<String,Object> whereMap){
		
		return alsUserDao.queryUserListPage(whereMap);
	}
	
	/**
	 * 根据用户编码查找用户
	 * @param userCode
	 * @return
	 */
	public BASUserVO findUserByCode(String userCode){
		
		return alsUserDao.findUserByCode(userCode);
	}
	
	/**
	 * 新增用户
	 * @param alsUserVO
	 */
	public void saveUser(BASUserVO alsUserVO){
		
		alsUserDao.saveUser(alsUserVO);
	}
	
	/**
	 * 修改用户
	 * @param alsUserVO
	 */
	public void updateUser(BASUserVO alsUserVO){
		
		alsUserDao.updateUser(alsUserVO);
	}
	
	public void deleteUser(String userCode) {
		alsUserDao.deleteUser(userCode);
	}
	
	
	
	
	/**
	 * 修改密码
	 * @param alsUserVO
	 */
	public void updatePwd(BASUserVO alsUserVO){
		
		alsUserDao.updatePwd(alsUserVO);
	}
	
	/**
	 * 新建外部用户时，查询最大用户编码 
	 * @param userMaxCode
	 */
	public void updateUserMaxCode(Map<String,Object> whereMap){
		
		alsUserDao.updateUserMaxCode(whereMap);
	}
	
	/**
	 * 新建外部用户时，更新最大用户编码 
	 * @return
	 */
	public Map<String, Object> findUserMaxCode(){
		
		return alsUserDao.findUserMaxCode();
	}
	
	public BASUserVO checkUser(BASUserVO alsUserVO) {
		return alsUserDao.checkUser(alsUserVO);
	}

	public void deleteRoleRe(Map<String, Object> whereMap) {
		alsUserDao.deleteRoleRe(whereMap);
	}

	public void updateStatus(Map<String, Object> whereMap) {
		alsUserDao.updateStatus(whereMap);
	}

	
}
