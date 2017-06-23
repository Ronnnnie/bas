package com.billionsfinance.bas.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.billionsfinance.bas.dao.RoleResourceDao;
import com.billionsfinance.bas.entity.RoleResourceVO;

public class RoleResourcesService {

	@Autowired
	private RoleResourceDao roleResourceDao;
	
	/**
	 * 新建角色和资源的关系数据
	 * @param roleResourceVO
	 */
	public void saveRoleResource(RoleResourceVO roleResourceVO){
		
		roleResourceDao.saveRoleResource(roleResourceVO);
	}
	
	/**
	 * 根据角色ID和资源ID更新角色资源关系数据的状态
	 * @param whereMap
	 */
	public void updateByRoleAndResource(Map<String,Object> whereMap){
		
		roleResourceDao.updateByRoleAndResource(whereMap);
	}
	
}
