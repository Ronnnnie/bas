package com.billionsfinance.bas.dao;

import java.util.Map;

import com.billionsfinance.bas.entity.RoleResourceVO;


public interface RoleResourceDao {

	/**
	 * 新建角色和资源的关系数据
	 * @param roleResourceVO
	 */
	public void saveRoleResource(RoleResourceVO roleResourceVO);
	
	/**
	 * 根据角色ID和资源ID更新角色资源关系数据的状态
	 * @param whereMap
	 */
	public void updateByRoleAndResource(Map<String,Object> whereMap);
	
}