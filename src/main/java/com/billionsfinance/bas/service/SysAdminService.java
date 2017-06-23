package com.billionsfinance.bas.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.billionsfinance.bas.dao.SysAdminDao;
import com.billionsfinance.bas.entity.SysAdminVO;

public class SysAdminService {

	@Autowired
	private SysAdminDao sysAdminDao;
	
	/**
	 * 查询选中系统的系统非管理员用户
	 * @param whereMap
	 * @return
	 */
	public List<Map<String,Object>> queryCurrSysUser(Map<String,Object> whereMap){
		
		return sysAdminDao.queryCurrSysUser(whereMap);
	}
	
	/**
	 * 查出选中系统的管理员
	 * @param whereMap
	 * @return
	 */
	public List<Map<String,Object>> queryAdminData(Map<String,Object> whereMap){
		
		return sysAdminDao.queryAdminData(whereMap);
	}
	
	/**
	 * 新增系统管理员
	 * @param sysAdminVO
	 */
	public void saveSysAdmin(SysAdminVO sysAdminVO){
		sysAdminDao.saveSysAdmin(sysAdminVO);
	}
	
	/**
	 * 修改系统管理员
	 * @param whereMap
	 */
	public void updateSysAdminById(Map<String,Object> whereMap){
		
		sysAdminDao.updateSysAdminById(whereMap);
	}
	
	/**
	 * 根据条件统计管理员的数量
	 * @param whereMap
	 * @return
	 */
	public Long queryAdminCount(Map<String,Object> whereMap){
		
		return sysAdminDao.queryAdminCount(whereMap);
	}
	
	/**
	 * 根据条件分页查询系统管理员
	 * @param whereMap
	 * @return
	 */
	public List<Map<String,Object>> queryAdmin(Map<String,Object> whereMap){
		
		return sysAdminDao.queryAdmin(whereMap);
	}
}
