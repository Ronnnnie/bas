package com.billionsfinance.bas.server.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.entity.SysAdminVO;
import com.billionsfinance.bas.server.ISysAdminServer;
import com.billionsfinance.bas.service.SysAdminService;
import com.billionsfinance.bas.util.CommonUtil;
import com.billionsfinance.bas.util.SpringService;

public class SysAdminServer implements ISysAdminServer {

	private final SysAdminService sysAdminService = SpringService.SYSADMINSERVICE;
	
	@Override
	public List<Map<String, Object>> queryCurrSysUser(
			Map<String, Object> whereMap) {
		
		return sysAdminService.queryCurrSysUser(whereMap);
	}

	@Override
	public List<Map<String, Object>> queryAdminData(Map<String, Object> whereMap) {
		
		return sysAdminService.queryAdminData(whereMap);
	}

	@Override
	public void addSysAdmin(List<SysAdminVO> sysAdmins, String sysCode,String currentUser) {
		//查找该系统已经存在的管理员
		Map<String,Object> whereMap = new HashMap<String, Object>();
		whereMap.put("sysCode", sysCode);//查询出该系统下的所有管理员
		List<Map<String, Object>> adminData = sysAdminService.queryAdminData(whereMap);
		
		//先遍历页面上分配的管理员，如果不存在则新建
		for (SysAdminVO sysAdminVO : sysAdmins) {
			boolean isExit = false;
			for (Map<String, Object> map : adminData) {
				//如果系统中已经存在就返回，不存在就插入管理员
				if(sysAdminVO.getUserCode().equals(map.get("userCode"))){
					isExit = true;
					break;
				}
			}
			if(!isExit){
				sysAdminService.saveSysAdmin(sysAdminVO);
			}
		}
		//遍历已经存在的管理员，如果页面出来的数据中有则将状态修改为有效，没有则将状态修改为无效
		
		for (Map<String, Object> map : adminData) {
			boolean isExit = false;
			for (SysAdminVO sysAdminVO : sysAdmins) {
				//如果系统中已经存在就返回，不存在就插入管理员
				if(sysAdminVO.getUserCode().equals(map.get("userCode"))){
					isExit = true;
					if(CommonUtil.VALID_IND_F.equals(map.get("validInd"))){
						whereMap.put("pkUuid", map.get("pkUuid"));
						whereMap.put("validInd", CommonUtil.VALID_IND_N);
						whereMap.put("updatedUser", currentUser);
						whereMap.put("updatedDate",new Timestamp(System.currentTimeMillis()));
						sysAdminService.updateSysAdminById(whereMap);
					}
					break;
				}
			}
			//不存在修改为无效
			if(!isExit){
				whereMap.put("pkUuid", map.get("pkUuid"));
				whereMap.put("validInd", CommonUtil.VALID_IND_F);
				whereMap.put("updatedUser", currentUser);
				whereMap.put("updatedDate",new Timestamp(System.currentTimeMillis()));
				sysAdminService.updateSysAdminById(whereMap);
			}
		}
	}

	@Override
	public PageVO queryAdmin(PageVO pageVO, Map<String, Object> whereMap) {
		
		//根据条件获得管理员的数量
		Long total = sysAdminService.queryAdminCount(whereMap);
		if(total.longValue() == 0){
			return pageVO;
		}
		//获得分页查询条件
		Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
		whereMap.putAll(whereMapPage);
		//分页查询管理员
		List<Map<String, Object>> admins = sysAdminService.queryAdmin(whereMap);
		pageVO.setRows(admins);
		return pageVO;
	}

}
