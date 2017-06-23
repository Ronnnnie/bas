package com.billionsfinance.bas.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.billionsfinance.bas.dao.SystemDao;
import com.billionsfinance.bas.entity.SystemVO;
import com.billionsfinance.bas.util.CommonUtil;

public class SystemService {

	@Autowired
	private SystemDao systemDao;

	/**
	 * 根据系统类型编码查询系统清单
	 * @param sysTypeCode
	 * @return
	 */
	public List<SystemVO> querySystembytypeCode(String sysTypeCode){
		
		return systemDao.querySystembytypeCode(sysTypeCode);
	}
	
	/**
	 * 判断用户是否是系统管理员
	 * @param userCode
	 * @return
	 */
	public boolean isSuperAdmin(String currentUser){
		Map<String,Object> whereMapAdmin = new HashMap<String,Object>();
		//FIXME 判断是否为统一登录系统的超级管理员，系统代码先写死后面用数据字典配置
		whereMapAdmin.put("userCode", currentUser);
		whereMapAdmin.put("sysCode", CommonUtil.ALS);
		List<Map<String, Object>> users = systemDao.isSuperAdmin(whereMapAdmin);
		if(users == null ){
			return false;
		}
		return users.size() > 0;
	}
	
	/**
	 * 判断用户是否是系统管理员
	 * @param userCode
	 * @return
	 */
	public boolean isSysAdmin(String currentUser){
		Map<String,Object> whereMapAdmin = new HashMap<String,Object>();
		//FIXME 判断是否为统一登录系统的超级管理员，系统代码先写死后面用数据字典配置
		whereMapAdmin.put("userCode", currentUser);
		whereMapAdmin.put("sysCode", CommonUtil.ALS);
		List<Map<String, Object>> users = systemDao.isSysAdmin(whereMapAdmin);
		if(users == null ){
			return false;
		}
		return users.size() > 0;
	}
	
	/**
	 * 根据条件统计当前用户是管理员的系统个数
	 * @param whereMap
	 * @return
	 */
	public Long querySysCountByAdminUser(Map<String,Object> whereMap){
		
		return systemDao.querySysCountByAdminUser(whereMap);
	}
	
	/**
	 * 根据条件分页查询当前用户是管理管的系统
	 * @param whereMap
	 * @return
	 */
	public List<Map<String, Object>> querySysListPageByAdminUser(Map<String,Object> whereMap){
		
		return systemDao.querySysListPageByAdminUser(whereMap);
	}
	
	
	/**
	 * 根据条件统计所有系统的个数（一般是超级管理员操作）
	 * @param whereMap
	 * @return
	 */
	public Long querySysCount(Map<String,Object> whereMap){
		
		return systemDao.querySysCount(whereMap);
	}
	
	/**
	 * 根据条件分页查询所有的系统（一般是超级管理员操作）
	 * @param whereMap
	 * @return
	 */
	public List<Map<String, Object>> querySysListPage(Map<String,Object> whereMap){
		
		return systemDao.querySysListPage(whereMap);
	}
	
	/**
	 * 新增系统
	 * @param systemVO
	 */
	public void saveSystem(SystemVO systemVO){
		
		systemDao.saveSystem(systemVO);
	}
	
	/**
	 * 修改系统
	 * @param systemVO
	 */
	public void updateSystem(SystemVO systemVO){
		
		systemDao.updateSystem(systemVO);
	}
	
	/**
	 * 根据系统编码查询系统
	 * @param sysCode
	 * @return
	 */
	public SystemVO findSysByCode(String sysCode){
		
		return systemDao.findSysByCode(sysCode);
	}
	
	/**
	 * 查询所有的可以用系统信息
	 * @param whereMap
	 * @return
	 */
	public List<SystemVO> queryAllSysCode(){
		
		return systemDao.queryAllSysCode();
	}
	
	/**
	 * 根据用户统计用户是管理员的系统
	 * @param whereMap
	 * @return
	 */
	public List<SystemVO> querySysCodeByUser(String currentUser){
		
		return systemDao.querySysCodeByUser(currentUser);
	}
	/**
	 * 根据用户查找用户的下的系统
	 * @param whereMap
	 * @return
	 */
	public List<SystemVO> findSystemByUser(Map<String, String> whereMap){
		
		return systemDao.findSystemByUser(whereMap);
	}

	/**
	 * 当前用户是系统用户的老系统
	 * @param currentUser
	 * @return
	 */
	public List<SystemVO> queryOdlSysCodeByUser(String currentUser) {
		
		return systemDao.queryOdlSysCodeByUser(currentUser);
	}

	/**
	 * 根据条件精确查询系统
	 * @param whereMap
	 * @return
	 * @throws Exception
	 */
	public List<SystemVO> findSysByWhere(Map<String, String> whereMap) throws Exception{
		
		return systemDao.findSysByWhere(whereMap);
	}
}
