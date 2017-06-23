package com.billionsfinance.bas.server.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.BASUserVO;
import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.entity.ResourceVO;
import com.billionsfinance.bas.entity.RoleResourceVO;
import com.billionsfinance.bas.server.IResourceServer;
import com.billionsfinance.bas.service.ResourceService;
import com.billionsfinance.bas.service.RoleResourcesService;
import com.billionsfinance.bas.util.CommonUtil;
import com.billionsfinance.bas.util.DateUtil;
import com.billionsfinance.bas.util.SpringService;
import com.billionsfinance.bas.util.UUIDGenerator;
import com.billionsfinance.common.Exception.BizException;

public class ResourceServer implements IResourceServer {

	private final ResourceService resourceService = SpringService.RESOURCE;
	
	private final RoleResourcesService roleResourcesService = SpringService.ROLERESOURCESSERVICE;
	
	@Override
	public List<Map<String, Object>> findSystemMenusByUser(BASUserVO alsUserVO)
			throws BizException {
		
		Map<String, Object> whereMap = new HashMap<String, Object>();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		if(alsUserVO == null ){
			return result;
		}
		whereMap.put("userCode", alsUserVO.getUserCode());
		
		// 查询系统一级系统管理员是否有对应的用户
//		Integer sysManagerCount = resourceService.querySysManager(whereMap);
//		if (sysManagerCount.intValue() == 0) {
			// 查询系统用户是否有对应的用户
		Integer sysUserCount = resourceService.querySysUser(whereMap);
		if (sysUserCount.intValue() == 0) {
			// 系统用户都没有返回空的集合；,ALS是有所有用户都能看到的按钮
			result = resourceService.findShowAllUserMenus(whereMap);
			return result;
		} else {
//				result = resourceService.querySysUserMenus(whereMap);
			result = resourceService.queryUserMenus(whereMap);
			
			//添加所有用户都能访问的菜单
			List<Map<String, Object>> showAllUserMenus = resourceService.findShowAllUserMenus(whereMap);
			result.addAll(showAllUserMenus);
			//排序
			listSort(result);
			return result;
		}
//		} else {
//			result = resourceService.querySystemMenus(whereMap);
//			return result;
//		}
	}

	/**
	 * 按照菜单序号排序
	 * @param result
	 */
	private void listSort(List<Map<String,Object>> result){
		
		Collections.sort(result, new Comparator<Map<String,Object>>() {
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				BigDecimal menuIndex = (BigDecimal)o1.get("menuIndex");
				BigDecimal menuIndex2 = (BigDecimal)o2.get("menuIndex");
				//按照菜单序号排序
				int compare = -1;
				if(menuIndex != null && menuIndex2 != null){
					compare = menuIndex.compareTo(menuIndex2);
				}
				return compare;
			}
		});
	};
	
	/**
	 * 查询用户是否是该系统的管理员
	 * @param whereMap
	 * @return
	 */
	@Override
	public Integer querySysManager(Map<String, Object> whereMap){
		
		return resourceService.querySysManager(whereMap);
	}
	
	@Override
	public List<ResourceVO> queryMenuResourceByRole(Map<String, Object> whereMap) {
		
		return resourceService.queryMenuResourceByRole(whereMap);
	}
	
	@Override
	public List<Map<String, Object>> queryMenuResourceAllByRole(Map<String, Object> whereMap){
		return resourceService.queryMenuResourceAllByRole(whereMap);
	}

	@Override
	public List<ResourceVO> queryAllMenuResource(Map<String, Object> whereMap) {
		
		return resourceService.queryAllMenuResource(whereMap);
	}

	@Override
	public void updateRoleMenus(Map<String, Object> whereMap,
			String resourceIds) {
		
		String[] resourceIdArr  = resourceIds.split(",");
		String currentUser = (String)whereMap.get("currentUser");
		//根据角色和系统查询角色已经存在的资源
		List<ResourceVO> roleMenus = resourceService.queryMenuResourceByRole(whereMap);
		RoleResourceVO roleResourceVO = null;
		//遍历页面分配的菜单，原来系统中不存在的新增
		for (String resourceId : resourceIdArr) {
			boolean isExit = false;
			for (ResourceVO resourceVO : roleMenus) {
				if(resourceId.equals(resourceVO.getResourceId())){
					isExit = true;
					break;
				}
			}
			//不存在就新建
			if(!isExit){
				roleResourceVO = new RoleResourceVO();
				roleResourceVO.setPkUuid(UUIDGenerator.getUUID());
				roleResourceVO.setResourceId(resourceId);
				roleResourceVO.setRoleCode((String)whereMap.get("roleCode"));
				roleResourceVO.setValidInd(CommonUtil.VALID_IND_N);
				roleResourceVO.setCreatedDate(DateUtil.sqlDate());
				roleResourceVO.setCreatedUser(currentUser);
				roleResourceVO.setUpdatedDate(DateUtil.sqlDate());
				roleResourceVO.setUpdatedUser(currentUser);
				roleResourcesService.saveRoleResource(roleResourceVO);
			}
		}
		//遍历原来存在的菜单，页面传入的菜单中没有的状态设置为失效，有的状态设置为有效
		for (ResourceVO resourceVO : roleMenus) {
			boolean isExit = false;
			for (String resourceId : resourceIdArr) {
				if(resourceId.equals(resourceVO.getResourceId())){
					isExit = true;
					//根据角色Code和资源ID修改状态
					whereMap.put("validInd", CommonUtil.VALID_IND_N);
					whereMap.put("resourceId", resourceId);
					whereMap.put("updatedUser", currentUser);
					whereMap.put("updatedDate", DateUtil.sqlDate());
					roleResourcesService.updateByRoleAndResource(whereMap);
					break;
				}
			}
			//如果新分配的权限中没有，将该条数据设置为作废
			if(!isExit){
				//根据角色Code和资源ID修改状态
				whereMap.put("validInd", CommonUtil.VALID_IND_F);
				whereMap.put("resourceId", resourceVO.getResourceId());
				whereMap.put("updatedUser", currentUser);
				whereMap.put("updatedDate", DateUtil.sqlDate());
				roleResourcesService.updateByRoleAndResource(whereMap);
			}
		}
	}

	@Override
	public void saveResource(ResourceVO resourceVO) {
		
		resourceService.saveResource(resourceVO);
	}

	@Override
	public void updateResource(ResourceVO resourceVO) {
		
		resourceService.updateResource(resourceVO);
	}

	@Override
	public List<ResourceVO> queryResource(ResourceVO resourceVO) {
		
		return resourceService.queryResource(resourceVO);
	}

	@Override
	public ResourceVO findResourceById(String resourceId) {
		
		return resourceService.findResourceById(resourceId);
	}

	@Override
	public void updateResourcesValidInd(Map<String, Object> whereMap) {
		
		resourceService.updateResourcesValidInd(whereMap);
	}

	@Override
	public PageVO queryButtPage(ResourceVO resourceVO,PageVO pageVO) throws Exception {
		//组装查询条件
		Map<String,Object> whereMap = new HashMap<String,Object>();
		whereMap.put("sysCode", resourceVO.getSysCode());
		whereMap.put("parentId", resourceVO.getParentId());
		whereMap.put("resourceName", resourceVO.getResourceName());
		whereMap.put("validInd", resourceVO.getValidInd());
		//统计数量
		Long total = resourceService.queryButtCount(whereMap);
		//获取分页参数
		Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
		//合并查询条件
		whereMap.putAll(whereMapPage);
		//查询按钮
		List<Map<String,Object>> butts = resourceService.queryButtPage(whereMap);
		//存放添加菜单名称的按钮
		List<Map<String,Object>> butt = new ArrayList<Map<String,Object>>(); 
		//查询出按钮的父级名称
		for (Map<String, Object> resourceVO2 : butts) {
			String parentName = null;
			List<Map<String, String>> farentPaths = resourceService.findParentPath((String)resourceVO2.get("resourceId"));
			if(farentPaths != null){
				//取左后一个
				Map<String, String> map = farentPaths.get(farentPaths.size() - 1);
				parentName = map.get("parentName");
			}
			//存入路径
			resourceVO2.put("parentName", parentName);
			butt.add(resourceVO2);
		}
		pageVO.setRows(butt);
		return pageVO;
	}
	/**
	 * 根据系统编码查找权限
	 * @param sysCode
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Map<String,String>> queryRoleBySysCode(String sysCode)throws Exception{
		
		return resourceService.queryRoleBySysCode(sysCode);
	}
	
}
