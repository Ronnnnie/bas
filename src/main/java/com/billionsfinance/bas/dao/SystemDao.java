package com.billionsfinance.bas.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.billionsfinance.bas.entity.SystemVO;

public interface SystemDao {

	/**
	 * 根据系统类型编码查询系统清单
	 * @param sysTypeCode
	 * @return
	 */
	public List<SystemVO> querySystembytypeCode(String sysTypeCode);
	
	/**
	 * 判断用户是否是系统管理员
	 * @param userCode
	 * @return
	 */
	public List<Map<String, Object>> isSuperAdmin(Map<String,Object> whereMap);
	/**
	 * 判断用户是否是非Alsx系统的管理员
	 * @param userCode
	 * @return
	 */
	public List<Map<String, Object>> isSysAdmin(Map<String,Object> whereMap);
	/**
	 * 根据条件统计当前用户是管理员的系统个数
	 * @param whereMap
	 * @return
	 */
	public Long querySysCountByAdminUser(Map<String,Object> whereMap);
	
	/**
	 * 根据条件分页查询当前用户是管理管的系统
	 * @param whereMap
	 * @return
	 */
	public List<Map<String, Object>> querySysListPageByAdminUser(Map<String,Object> whereMap);
	
	
	/**
	 * 根据条件统计所有系统的个数（一般是超级管理员操作）
	 * @param whereMap
	 * @return
	 */
	public Long querySysCount(Map<String,Object> whereMap);
	
	/**
	 * 根据条件分页查询所有的系统（一般是超级管理员操作）
	 * @param whereMap
	 * @return
	 */
	public List<Map<String, Object>> querySysListPage(Map<String,Object> whereMap);
	
	/**
	 * 新增系统
	 * @param systemVO
	 */
	public void saveSystem(SystemVO systemVO);
	
	/**
	 * 修改系统
	 * @param systemVO
	 */
	public void updateSystem(SystemVO systemVO);
	
	/**
	 * 根据系统编码查询系统
	 * @param sysCode
	 * @return
	 */
	public SystemVO findSysByCode(String sysCode);
	
	/**
	 * 查询所有的可以用系统信息
	 * @param whereMap
	 * @return
	 */
	public List<SystemVO> queryAllSysCode();
	
	/**
	 * 根据用户统计用户是管理员的系统
	 * @param whereMap
	 * @return
	 */
	public List<SystemVO> querySysCodeByUser(String currentUser);
	
	/**
	 * 根据用户查找用户的下的系统
	 * @param whereMap
	 * @return
	 */
	public List<SystemVO> findSystemByUser(Map<String, String> whereMap);

	/**
	 * 当前用户是系统用户的老系统
	 * @param currentUser 
	 * @param currentUser
	 * @return
	 */
	public List<SystemVO> queryOdlSysCodeByUser(@Param(value="currentUser") String currentUser);
	
	/**
	 * 根据条件精确查询系统
	 * @param whereMap
	 * @return
	 * @throws Exception
	 */
	public List<SystemVO> findSysByWhere(Map<String, String> whereMap) throws Exception;

}
