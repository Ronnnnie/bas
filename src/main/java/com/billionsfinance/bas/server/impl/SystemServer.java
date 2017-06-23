package com.billionsfinance.bas.server.impl;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.entity.SystemVO;
import com.billionsfinance.bas.server.ISystemServer;
import com.billionsfinance.bas.service.SystemService;
import com.billionsfinance.bas.util.CommonUtil;
import com.billionsfinance.bas.util.SpringService;

public class SystemServer implements ISystemServer {

	private SystemService systemService = SpringService.SYSTEMSERVICE;
	
	@Override
	public List<SystemVO> querySystembytypeCode(String sysTypeCode) {
		
		
		return systemService.querySystembytypeCode(sysTypeCode);
	}


	@Override
	public PageVO findSystemByWhere(PageVO  pageVO,Map<String,Object> whereMap) throws Exception {
		
//		String userCode = (String)whereMap.get("currentUser");
		//判断用户是否为超级管理员
//		boolean isAdmin = systemService.isSuperAdmin(userCode);
//		if(!isAdmin){
//			whereMap.put("userCode", userCode);
//			//如果不是超级管理员就只能查出当前用户是管理员的系统
//			Long total = systemService.querySysCountByAdminUser(whereMap);
//			//处理分页数据，分页条件
//			Map<String,Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO,total);
//			//拼接分页条件和查询条件
//			whereMap.putAll(whereMapPage);
//			List<Map<String, Object>> sysListAdminUser = systemService.querySysListPageByAdminUser(whereMap);
//			//给系统查找系统分类名称
//			setSysTypeName(sysListAdminUser);
//			pageVO.setRows(sysListAdminUser);
//		}else{
			Long total = systemService.querySysCount(whereMap);
			//处理分页数据，分页条件
			Map<String,Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO,total);
			//拼接分页条件和查询条件
			whereMap.putAll(whereMapPage);
			List<Map<String, Object>> sysLists = systemService.querySysListPage(whereMap);
//			setSysTypeName(sysLists);
			pageVO.setRows(sysLists);
//		}
		
		return pageVO;
	}

	/**
	 * 查询系统的系统分类名称
	 * @param sysListAdminUser
	 */
//	private void setSysTypeName(List<Map<String, Object>> list) throws Exception {
//		if(list == null){
//			return;
//		}
//		for (Map<String, Object> map : list) {
//			String sysTypeCode = (String)map.get("sysTypeCode");
//			SystemTypeVO sysType = systemTypeService.findSystemTypeByCode(sysTypeCode);
//			if(sysType != null){
//				map.put("sysTypeCname", sysType.getSysTypeCname());
//			}
//		}
//	}


	@Override
	public void saveSystem(SystemVO systemVO) {
		
		systemService.saveSystem(systemVO);
	}

	@Override
	public void updateSystem(SystemVO systemVO) throws Exception {
		
		systemService.updateSystem(systemVO);
	}
	
	/**
	 * 根据系统编码查询系统
	 * @param sysCode
	 * @return
	 */
	@Override
	public SystemVO findSysByCode(String sysCode) throws Exception{
		
		return systemService.findSysByCode(sysCode);
	}

	@Override
	public List<SystemVO> initSystem(String currentUser)throws Exception {
		List<SystemVO> systems;
		//判断用户是否为超级管理员
		boolean isAdmin = systemService.isSuperAdmin(currentUser);
		if(isAdmin){
			systems = systemService.queryAllSysCode();
		}else{
			systems = systemService.querySysCodeByUser(currentUser);
		}
		
		return systems;
	}

	@Override
	public List<SystemVO> queryAllSysCode()throws Exception {
		
		return systemService.queryAllSysCode();
	}
	
	@Override
	public List<SystemVO> findSystemByUser(Map<String, String> whereMap) {
		
		return systemService.findSystemByUser(whereMap);
	}

	@Override
	public List<SystemVO> initOldSystem(String currentUser)throws Exception {
		//判断用户是否为超级管理员
		boolean isAdmin = systemService.isSuperAdmin(currentUser);
		//如果是超级管理员查询所有老系统
		if(isAdmin){
			currentUser = null;
		}
		//当前用户是系统用户的老系统
		List<SystemVO> systems = systemService.queryOdlSysCodeByUser(currentUser);
		
		return systems;
	}
	
	/**
	 * 根据条件精确查询系统
	 * @param whereMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<SystemVO> findSysByWhere(Map<String, String> whereMap) throws Exception{
		
		return systemService.findSysByWhere(whereMap);
	}
}
