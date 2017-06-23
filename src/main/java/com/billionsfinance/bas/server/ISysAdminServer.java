package com.billionsfinance.bas.server;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.entity.SysAdminVO;

public interface ISysAdminServer {

	/**
	 * 查询选中系统的系统非管理员用户
	 * @param whereMap
	 * @return
	 */
	public List<Map<String,Object>> queryCurrSysUser(Map<String,Object> whereMap);
	
	/**
	 * 查出选中系统的管理员
	 * @param whereMap
	 * @return
	 */
	public List<Map<String,Object>> queryAdminData(Map<String,Object> whereMap);

	/**
	 * 保存给系统分配的系统管理员
	 * @param sysAdmins
	 * @param sysCode
	 */
	public void addSysAdmin(List<SysAdminVO> sysAdmins, String sysCode,String currentUser);
	
	/**
	 * 根据条件分页查询系统管理员
	 * @param whereMap
	 * @return
	 */
	public PageVO queryAdmin(PageVO pageVO,Map<String,Object> whereMap);
}
