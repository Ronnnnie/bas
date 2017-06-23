package com.billionsfinance.bas.server;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.entity.SystemVO;

public interface ISystemServer {

	/**
	 * 根据系统类型编码查询系统清单
	 * @param sysTypeCode
	 * @return
	 */
	public List<SystemVO> querySystembytypeCode(String sysTypeCode) throws Exception;
	
	
	/**
	 * 根据用户权限查看系统
	 * @param userCode
	 * @return
	 */
	public PageVO findSystemByWhere(PageVO  pageVO,Map<String,Object> whereMap) throws Exception;
	
	/**
	 * 新增系统
	 * @param systemVO
	 */
	public void saveSystem(SystemVO systemVO) throws Exception;
	
	/**
	 * 修改系统
	 * @param systemVO
	 * @throws Exception 
	 */
	public void updateSystem(SystemVO systemVO) throws Exception;
	
	/**
	 * 根据系统编码查询系统
	 * @param sysCode
	 * @return
	 */
	public SystemVO findSysByCode(String sysCode) throws Exception;
	
	/**
	 * 初始化系统下拉框数据
	 * @param whereMap
	 * @return
	 */
	public List<SystemVO> initSystem(String currentUser) throws Exception;

	/**
	 * 根据用户查找用户可以操作的系统
	 * @param whereMap
	 * @return
	 */
	public List<SystemVO> findSystemByUser(Map<String, String> whereMap);

	/**
	 * 查询需要绑定用户的系统
	 * @param currentUser
	 * @return
	 * @throws Exception 
	 */
	public List<SystemVO> initOldSystem(String currentUser) throws Exception;
	
	/**
	 * 根据条件精确查询系统
	 * @param whereMap
	 * @return
	 * @throws Exception
	 */
	public List<SystemVO> findSysByWhere(Map<String, String> whereMap) throws Exception;
	
	/**
	 * 查询所有的有效系统
	 * @return
	 * @throws Exception
	 */
	public List<SystemVO> queryAllSysCode()throws Exception;
}
