package com.billionsfinance.bas.server;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.BASUserVO;
import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.entity.ResourceVO;
import com.billionsfinance.common.Exception.BizException;

public interface IResourceServer {

	/**
	 * 根据登录用户查询用户有权限访问的UM系统的菜单
	 * @param whereMap
	 * @return
	 * @throws BizException
	 */
	public List<Map<String, Object>> findSystemMenusByUser(BASUserVO alsUserVO) throws BizException ;
	
	
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
	 * 分配菜单资源
	 * @param whereMap
	 * @param resourceIds
	 */
	public void updateRoleMenus(Map<String, Object> whereMap,
			String resourceIds);
	
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
	 * 分页查询按钮
	 * @param resourceVO
	 * @param pageVO 
	 * @return
	 * @throws Exception 
	 */
	public PageVO queryButtPage(ResourceVO resourceVO, PageVO pageVO) throws Exception;
	
	/**
	 * 根据系统编码查询角色
	 * @param sysCode
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,String>> queryRoleBySysCode(String sysCode)throws Exception;
	
	/**
	 * 查询用户是否是该系统的管理员
	 * @param whereMap
	 * @return
	 */
	public Integer querySysManager(Map<String, Object> whereMap);
}
