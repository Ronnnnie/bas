package com.billionsfinance.bas.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.billionsfinance.bas.dao.SystemUserDao;
import com.billionsfinance.bas.entity.SystemUserVO;

public class SystemUserService {

	@Autowired
	private SystemUserDao systemUserDao;
	
	/**
	 * 分配系统时，如果是超级用户，查询所有可以分配的系统 
	 * @param whereMap
	 * @return
	 */
	public List<Map<String,Object>> queryAllSystem(Map<String,Object> whereMap){
		
		return systemUserDao.queryAllSystem(whereMap);
	}
	
	/**
	 * 分配系统时，如果非超级用户，查询当前用户拥有的系统
	 * @param whereMap
	 * @return
	 */
	public List<Map<String,Object>> querySysByCurrentUser(Map<String,Object> whereMap){
		
		return systemUserDao.querySysByCurrentUser(whereMap);
	}
	
	/**
	 * 分配系统时，如果是超级用户，超级用户查询当前用户已经分配的系统
	 * @param whereMap
	 * @return
	 */
	public List<Map<String,Object>> querySysExistAdmin(Map<String,Object> whereMap){
		
		return systemUserDao.querySysExistAdmin(whereMap);
	}
	
	/**
	 * 分配系统时，如果非超级用户，查询已经分配的系统(当前用户的和被选中用户所有的系统) 
	 * @param whereMap
	 * @return
	 */
	public List<Map<String,Object>> querySysExist(Map<String,Object> whereMap){
		
		return systemUserDao.querySysExist(whereMap);
	}
	
	/**
	 * 修改系统用户表
	 * @param systemUserVO
	 */
	public void updateSystemUser(SystemUserVO systemUserVO){
		
		systemUserDao.updateSystemUser(systemUserVO);
	}
	
	/**
	 * 插入系统用户
	 * @param systemUserVO
	 */
	public void addSysUser(SystemUserVO systemUserVO){
		
		systemUserDao.addSysUser(systemUserVO);
	}
	
	/**
	 * 查询用户
	 * @param whereMap
	 * @return
	 */
	public List<SystemUserVO> querySysUerByWhere(Map<String,Object> whereMap){
		
		return systemUserDao.querySysUerByWhere(whereMap);
	}
	
	/**
	 * 保存分配系统时， 非UM超级管理员查询当前用户拥有的系统 ，比较左右数据组件时使用
	 * @param whereMap
	 */
	public List<SystemUserVO> querySysUserByCurrentUser(Map<String,Object> whereMap){
		
		return systemUserDao.querySysUserByCurrentUser(whereMap);
	}
	
	/**
	 * 根据系统用户ID修改系统用户的状态
	 * @param whereMap
	 */
	public void updateStatusBySysUserId(Map<String,Object> whereMap){
		
		systemUserDao.updateStatusBySysUserId(whereMap);
	}
	
	/**
	 * 根据系统用户查询该用户
	 * @param whereMap
	 * @return
	 */
	public List<Map<String,Object>> queryRoleBySysCode(Map<String,Object> whereMap){
		
		return systemUserDao.queryRoleBySysCode(whereMap);
	}
	
	/**
	 * 查询当前用户已经存在的系统
	 * @param whereMap
	 * @return
	 */
	public List<Map<String,Object>> queryRoleExist(Map<String,Object> whereMap){
		
		return systemUserDao.queryRoleExist(whereMap);
	}
	
	/**
	 * 查询系统用户列表
	 * @param whereMap
	 * @return
	 */
	public List<Map<String,Object>> querySysUser(Map<String,Object> whereMap){
		
		return systemUserDao.querySysUser(whereMap);
	}
	
	/**
	 * 统计系统用户数量
	 * @param whereMap
	 * @return
	 */
	public Long querySysUserCount(Map<String,Object> whereMap){
		
		return systemUserDao.querySysUserCount(whereMap);
	}
	
	/**
	 * 根据系统Code统计系统用户数量
	 * @param whereMap
	 * @return
	 */
	public Long querySysUserCountBySysCode(Map<String,Object> whereMap){
		
		return systemUserDao.querySysUserCountBySysCode(whereMap);
	}
	
	/**
	 * 查询系统用户
	 * @param systemUserVO
	 * @return
	 */
	public List<SystemUserVO> findSysUser(SystemUserVO systemUserVO){
		
		return systemUserDao.findSysUser(systemUserVO);
	}
	
	/**
	 * 统计绑定系统用户数量
	 * @param whereMap
	 * @return
	 */
	public Long userBindingCount(Map<String,Object> whereMap){
		
		return systemUserDao.queryUserBindingCount(whereMap);
	}
	
	
	/**
	 * 查询绑定系统用户列表
	 * @param whereMap
	 * @return
	 */
	public List<Map<String,Object>> queryUserBinding(Map<String,Object> whereMap){
		
		return systemUserDao.queryUserBinding(whereMap);
	}
	
	public void addSysUserBinding(Map<String,Object> map){
		systemUserDao.addSysUserBinding(map);
	}
	
	/**
	 * 管理员查询绑定用户
	 * @param whereMap
	 * @return
	 */
	public List<Map<String,Object>> queryAdminBinding(Map<String,Object> whereMap){
		
		return systemUserDao.queryAdminBinding(whereMap);
	}
	
	/**
	 * 管理员查询绑定用户数量
	 * @param whereMap
	 * @return
	 */
	public Long queryAdminBindingCount(Map<String,Object> whereMap){
		
		return systemUserDao.queryAdminBindingCount(whereMap);
	}
	
	/**
	 * 根据usercode,syscode删除对象
	 * @param map
	 */
	public void deleteSysUserBinding(Map<String,String> map){
		systemUserDao.deleteSysUserBinding(map);
	}
	
	/**
	 * 根据系统编码和系统中的账号查找该系统账号是否绑定过
	 * @param map
	 * @return
	 */
	public List<Map<String,String>> findSysUsername(Map<String, Object> map){
		
		return systemUserDao.findSysUsername(map);
	}
	
	/**
	 * 根据系统编码和用户Code查找绑定的账号
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> findSysUsernameByCode(Map<String, Object> map){
		
		return systemUserDao.findSysUsernameByCode(map);
	}
	
	/**
	 * 修改系统账号是否为默认账号
	 * @param map
	 */
	public void updateUserIsDefault(Map<String, Object> map){
		
		 systemUserDao.updateUserIsDefault(map);
	}
	
	/**
	 * 查询系统用户的 绑定的账号
	 * @param map
	 * @throws Exception
	 */
	public List<Map<String, Object>> findSysAccount(Map<String, Object> whereMap)throws Exception{
		
		return systemUserDao.findSysAccount(whereMap);
	}
}
