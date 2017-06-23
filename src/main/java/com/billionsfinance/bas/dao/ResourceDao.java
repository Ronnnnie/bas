package com.billionsfinance.bas.dao;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.ResourceVO;

public interface ResourceDao {

	/**
	 * 查询用户是否是该系统的用户
	 * @param whereMap
	 * @return
	 */
	public Integer querySysUser(Map<String, Object> whereMap);
	
	/**
	 * 查询用户是否是该系统的管理员
	 * @param whereMap
	 * @return
	 */
	public Integer querySysManager(Map<String, Object> whereMap);
	
	/**
	 * 根据用户编码查询对应的角色所有的菜单
	 * @param whereMap
	 * @return
	 */
	public List<Map<String, Object>> querySysUserMenus(Map<String, Object> whereMap);
	
	/**
	 * 系统管理员查询对应的所有系统菜单 
	 * @param whereMap
	 * @return
	 */
	public List<Map<String, Object>> querySystemMenus(Map<String, Object> whereMap);
	
	/**
	 * 根据角色查询该角色已经存在的菜单
	 * @param whereMap
	 * @return
	 */
	public List<ResourceVO> queryMenuResourceByRole(Map<String, Object> whereMap);
	
	/**
	 * 根据角色查询该角色已经存在的菜单
	 * @param whereMap
	 * @return
	 */
	public List<Map<String, Object>> queryMenuResourceAllByRole(Map<String, Object> whereMap);
	
	/**
	 * 查询对应系统下的菜单树
	 * @param whereMap
	 * @return
	 */
	public List<ResourceVO> queryAllMenuResource(Map<String, Object> whereMap);
	
	/**
	 * 新增资源
	 * @param resourceVO
	 */
	public void saveResource(ResourceVO resourceVO);
	
	/**
	 * 更新资源
	 * @param resourceVO
	 */
	public void updateResource(ResourceVO resourceVO);
	
	/**
	 * 根据条件查询系统下的资源
	 * @param resourceVO
	 * @return
	 */
	public List<ResourceVO> queryResource(ResourceVO resourceVO);
	
	/**
	 * 根据资源ID查询资源信息
	 * @param resourceId
	 * @return
	 */
	public ResourceVO findResourceById(String resourceId);
	
	/**
	 * 将多个资源设置为失效
	 * @param whereMap
	 */
	public void updateResourcesValidInd(Map<String, Object> whereMap);
	
	/**
	 * 统计按钮的个数
	 * @param whereMap
	 * @return 
	 */
	public Long queryButtCount(Map<String, Object> whereMap);
	
	/**
	 * 分页查询按钮
	 * @param whereMap
	 * @return 
	 */
	public List<Map<String, Object>> queryButtPage(Map<String, Object> whereMap) throws Exception;
	
	/**
	 * 查询系统重所有用户都需要展示的菜单
	 * @param whereMap
	 * @return
	 */
	public List<Map<String, Object>> findShowAllUserMenus(Map<String, Object> whereMap);
	
	/**
	 * 根据系统编码查找权限
	 * @param sysCode
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,String>> queryRoleBySysCode(String sysCode)throws Exception;
	
	/**
	 * 根据资源ID查找资源的父级根路径
	 * @param resourceId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,String>> findParentPath(String resourceId)throws Exception;

	
	/**
	 * 代替上面的querySysUserMenus方法
	 * @param whereMap
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryUserMenus(Map<String, Object> whereMap);
}
