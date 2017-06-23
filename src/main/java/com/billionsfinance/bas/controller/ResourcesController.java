package com.billionsfinance.bas.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.billionsfinance.als.security.CurrentUser;
import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.entity.ResourceVO;
import com.billionsfinance.bas.server.IResourceServer;
import com.billionsfinance.bas.server.impl.ResourceServer;
import com.billionsfinance.bas.util.CommonUtil;
import com.billionsfinance.bas.util.DateUtil;
import com.billionsfinance.bas.util.LogUtil;
import com.billionsfinance.bas.util.ResponseUtil;
import com.billionsfinance.bas.util.TreeVO;
import com.billionsfinance.bas.util.UUIDGenerator;
import com.billionsfinance.common.Exception.BizException;

/**
 * @ClassName: ResourcesController
 * @Description: 角色控制器
 * @author zhanghs 2016年7月19日 下午3:35:17 Copyright: Copyright (c) 2016
 *         Company:佰仟金融
 */
@Controller
@RequestMapping("/resources")
public class ResourcesController {
  
	private final Logger logger = Logger.getLogger(ResourcesController.class);

	private IResourceServer resourceServer = new ResourceServer();

	/**
	 * 进入菜单管理
	 * 
	 * @return 菜单管理
	 */
	@RequestMapping("/toMenuResources")
	public String toMenu() {

		return "resources/menuResources";
	}

	/**
	  * <p>Title: queryMenuBySysCode</p>
	  * <p>Description: 查询系统下的菜单树</p>
	  * @param sysCode 
	  * @param roleCode 
	  * @param request 
	  * @param response 
	  * @throws BizException  
	  */
	@RequestMapping("/queryMenuBySysCode")
	public void queryMenuBySysCode(String sysCode, String roleCode, HttpServletRequest request,
			HttpServletResponse response) throws BizException {
		try {
			Map<String, Object> whereMap = new HashMap<String, Object>();
			// whereMap.put("sysCode", sysCode);
			List<ResourceVO> menuResource = resourceServer.queryAllMenuResource(whereMap);
			// 查询该角色已经存在的资源
			whereMap.put("roleCode", roleCode);
			whereMap.put("validInd", CommonUtil.VALID_IND_N);
			List<ResourceVO> menuExitResource = resourceServer.queryMenuResourceByRole(whereMap);
			List<TreeVO> resourceTree = getMenuTree(menuResource, menuExitResource);

			ResponseUtil.sendJSONList(response, resourceTree);

		} catch (Exception e) {

			logger.error("查询系统下的菜单树", e);
			throw new BizException("查询系统下的菜单树", e);
		}
	}

	/**
	 * 根据角色查询系统树
	 * 
	 * @param sysCode 
	 * @param roleCode 
	 * @param response 
	 * @throws BizException 
	 */
	@RequestMapping("/queryMenuByRole")
	public void queryMenuByRole(String sysCode, String roleCode, HttpServletResponse response) throws BizException {
		try {

			Map<String, Object> whereMap = new HashMap<String, Object>();
			whereMap.put("sysCode", sysCode);
			whereMap.put("roleCode", roleCode);
			whereMap.put("validInd", CommonUtil.VALID_IND_N);
			List<ResourceVO> menuResource = resourceServer.queryMenuResourceByRole(whereMap);
			List<TreeVO> resourceTree = getMenuTree(menuResource, null);
			ResponseUtil.sendJSONList(response, resourceTree);

		} catch (Exception e) {

			LogUtil.ERROR.error("根据角色查询菜单树异常", e);
			throw new BizException("根据角色查询菜单树异常", e);
		}
	}

	/**
	  * <p>Title: updateRoleMenus</p>
	  * <p>Description: 分配资源保存菜单资源</p>
	  * @param roleCode 
	  * @param sysCode 
	  * @param resourceIds 
	  * @param request 
	  * @param response  
	  */
	@RequestMapping("updateRoleMenus")
	public void updateRoleMenus(String roleCode, String sysCode, String resourceIds, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String currentUser = CurrentUser.getUser().getUsername();
			Map<String, Object> whereMap = new HashMap<String, Object>();
			whereMap.put("sysCode", sysCode);
			whereMap.put("roleCode", roleCode);
			whereMap.put("currentUser", currentUser);
			// 更新角色资源
			resourceServer.updateRoleMenus(whereMap, resourceIds);
			ResponseUtil.sendMessage(response, true, "分配菜单成功");
		} catch (Exception e) {

			logger.error("分配资源失败", e);
			ResponseUtil.sendMessage(response, false, "分配菜单失败");
		}

	}

	/**
	 * 在给角色分配资源时封装菜单树
	 * 
	 * @param menuResource 
	 * @param menuExitResource 
	 * @return list
	 * @throws BizException 
	 */
	private List<TreeVO> getMenuTree(List<ResourceVO> menuResource, List<ResourceVO> menuExitResource)
			throws BizException {
 
		Map<String, List<TreeVO>> subTree = new HashMap<String, List<TreeVO>>();
		List<TreeVO> allTree = new ArrayList<TreeVO>();
		TreeVO tree = null;
		for (ResourceVO resource : menuResource) {
			tree = new TreeVO();
			String parentId = resource.getParentId();
			tree.setId(resource.getResourceId());
			// 资源类型为2说明是按钮
			if (resource.getResourceType() == 2) {
				tree.setText(resource.getResourceName() + "【按钮】");
			} else {
				tree.setText(resource.getResourceName());
			}
			// 如果已经存在的权限让树选中
			if (menuExitResource != null) {
				for (ResourceVO resourceVO : menuExitResource) {
					if (tree.getId().equals(resourceVO.getResourceId())) {
						tree.setChecked(true);
						break;
					}
				}
			} else {
				tree.setChecked(true);
			}

			// 统计父分类对应的子分类
			if (!StringUtils.isEmpty(parentId)) {
				tree.setParent(parentId);
				List<TreeVO> subList = subTree.get(parentId);
				if (subList == null) {
					subList = new ArrayList<TreeVO>();
				}
				subList.add(tree);
				subTree.put(parentId, subList);
			}
			// 有
			allTree.add(tree);
		}
		List<TreeVO> showTree = new ArrayList<TreeVO>();
		// 遍历所有的节点，将有子节点的添加进去
		for (TreeVO tree2 : allTree) {
			List<TreeVO> children = subTree.get(tree2.getId());
			if (children != null && children.size() > 0) {
				boolean isChecked = false;
				// 遍历子，只要有子没选中，就不选中父
				for (TreeVO treeVO : children) {
					if (!treeVO.isChecked()) {
						isChecked = true;
						break;
					}
				}
				// 只要有子没选中，就不选中父
				if (isChecked) {
					tree2.setChecked(false);
				}
				tree2.setChildren(children);
			}
			// 去重
			if (StringUtils.isEmpty(tree2.getParent())) {
				showTree.add(tree2);
			}
		}
		return showTree;
	}

	/**
	 * 根据系统，查询系统下的菜单树
	 * 
	 * @param sysCode 
	 * @param needNull  
	 * @param response  
	 * @throws BizException   
	 */
	@RequestMapping("/queryMenusSysCode")
	public void queryMenusSysCode(String sysCode, boolean needNull, HttpServletResponse response) throws BizException {
		try {

			ResourceVO resourceVO = new ResourceVO();
			resourceVO.setSysCode(sysCode);
			resourceVO.setValidInd(CommonUtil.VALID_IND_N);
			resourceVO.setResourceType(1); // 菜单
			List<ResourceVO> sysMenus = resourceServer.queryResource(resourceVO);
			List<TreeVO> resourceTree = allMenuTree(sysMenus);
			if (needNull) {
				TreeVO tree = new TreeVO();
				tree.setId("");
				tree.setText("全部");
				resourceTree.add(0, tree);
			}
			ResponseUtil.sendJSONList(response, resourceTree);

		} catch (Exception e) {

			LogUtil.ERROR.error("根据系统查询菜单树异常", e);
			throw new BizException("根据系统查询菜单树异常", e);
		}
	}

	/**
	 * 系统资源管理中展示的菜单树
	 * 
	 * @param menuResource 
	 * @return list
	 * @throws BizException 
	 */
	private List<TreeVO> allMenuTree(List<ResourceVO> menuResource) throws BizException {

		Map<String, List<TreeVO>> subTree = new HashMap<String, List<TreeVO>>();
		List<TreeVO> allTree = new ArrayList<TreeVO>();
		TreeVO tree = null;
		for (ResourceVO resource : menuResource) {
			tree = new TreeVO();
			String parentId = resource.getParentId();
			tree.setId(resource.getResourceId());
			// 有路径的就需要展示路径
			String text = "";
			if (!StringUtils.isEmpty(resource.getUrl())) {
				text = resource.getResourceName() + " || URL:" + resource.getUrl();
			} else {
				text = resource.getResourceName();
			}
			tree.setText(text);

			// 统计父分类对应的子分类
			if (!StringUtils.isEmpty(parentId)) {
				tree.setParent(parentId);
				List<TreeVO> subList = subTree.get(parentId);
				if (subList == null) {
					subList = new ArrayList<TreeVO>();
				}
				subList.add(tree);
				subTree.put(parentId, subList);
			}
			// 有
			allTree.add(tree);
		}
		List<TreeVO> showTree = new ArrayList<TreeVO>();
		// 遍历所有的节点，将有子节点的添加进去
		for (TreeVO tree2 : allTree) {
			List<TreeVO> children = subTree.get(tree2.getId());
			if (children != null && children.size() > 0) {
				tree2.setChildren(children);
			}
			// 去重
			if (StringUtils.isEmpty(tree2.getParent())) {
				showTree.add(tree2);
			}
		}
		return showTree;
	}

	/**
	  * <p>Title: saveResource</p>
	  * <p>Description: saveResource</p>
	  * @param resourceVO 
	  * @param request 
	  * @param response 
	  * @throws BizException  
	  */
	@RequestMapping("/saveResource")
	public void saveResource(ResourceVO resourceVO, HttpServletRequest request, HttpServletResponse response)
			throws BizException {

		if (StringUtils.isEmpty(resourceVO.getResourceName())) {
			ResponseUtil.sendMessage(response, false, "资源名称不能为空");
			return;
		}

		String currentUser = CurrentUser.getUser().getUsername();
		try {
			resourceVO.setResourceId(UUIDGenerator.getUUID());
			// resourceVO.setResourceType(new Integer(1));
			resourceVO.setValidInd(CommonUtil.VALID_IND_N);
			resourceVO.setUpdatedUser(currentUser);
			resourceVO.setCreatedUser(currentUser);
			resourceVO.setCreatedDate(DateUtil.sqlDate());
			resourceVO.setUpdatedDate(DateUtil.sqlDate());

			// 判断资源编码唯一
			String resourceCode = resourceVO.getResourceCode();
			if (!StringUtils.isEmpty(resourceCode)) {
				ResourceVO resource = new ResourceVO();
				// 系统内资源编码唯一
				resource.setResourceCode(resourceCode);
				resource.setSysCode(resourceVO.getSysCode());
				List<ResourceVO> resources = resourceServer.queryResource(resource);
				for (ResourceVO resourceVO2 : resources) {
					if (resourceCode.equals(resourceVO2.getResourceCode())) {
						ResponseUtil.sendMessage(response, false, "资源编码已存在");
						return;
					}
				}
			}

			resourceServer.saveResource(resourceVO);

			ResponseUtil.sendMessage(response, true, "新增资源成功");
		} catch (Exception e) {
			ResponseUtil.sendMessage(response, false, "新增菜单失败");
			logger.error("新增资源成功异常！资源名称：" + resourceVO.getResourceName(), e);
			throw new BizException("新增资源成功异常！资源名称：" + resourceVO.getResourceName(), e);
		}
	}

	@RequestMapping("/updateResource")
	public void updateResource(ResourceVO resourceVO, HttpServletRequest request, HttpServletResponse response)
			throws BizException {

		String currentUser = CurrentUser.getUser().getUsername();
		try {
			// 修改资源信息
			resourceVO.setUpdatedUser(currentUser);
			resourceServer.updateResource(resourceVO);

			ResponseUtil.sendMessage(response, true, "修改资源成功");
		} catch (Exception e) {
			ResponseUtil.sendMessage(response, false, "修改菜单失败");
			logger.error("修改资源成功异常！资源名称：" + resourceVO.getResourceName(), e);
			throw new BizException("修改资源成功异常！资源名称：" + resourceVO.getResourceName(), e);
		}
	}

	@RequestMapping("/updateMenuValidInd")
	public void updateResourcesValidInd(String resourceIds, HttpServletRequest request, HttpServletResponse response)
			throws BizException {

		String currentUser = CurrentUser.getUser().getUsername();
		try {
			// 将资源设置为失效
			String[] resourceIdArr = resourceIds.split(",");
			List<String> resourceList = new ArrayList<String>();
			for (String resourceId : resourceIdArr) {
				resourceList.add(resourceId);
			}
			if (resourceList.size() > 0) {
				Map<String, Object> whereMap = new HashMap<String, Object>();
				whereMap.put("updatedUser", currentUser);
				whereMap.put("validInd", CommonUtil.VALID_IND_F);
				whereMap.put("resourceList", resourceList);
				resourceServer.updateResourcesValidInd(whereMap);
				ResponseUtil.sendMessage(response, true, "删除资源成功");
			}
		} catch (Exception e) {
			logger.error("修改资源状态异常！资源ID：" + resourceIds, e);
			ResponseUtil.sendMessage(response, false, "删除菜单失败");
			throw new BizException("修改资源状态异常！资源ID：" + resourceIds, e);
		}
	}

	@RequestMapping("/findResourceById")
	public void findResourceById(String resourceId, HttpServletRequest request, HttpServletResponse response)
			throws BizException {

		try {
			ResourceVO resource = resourceServer.findResourceById(resourceId);
			ResponseUtil.sendJSON(response, resource);

		} catch (Exception e) {
			logger.error("查询异常！资源ID：" + resourceId, e);
			throw new BizException("查询异常！资源ID：" + resourceId, e);
		}
	}

	@RequestMapping("queryButt")
	public void queryButt(Integer page, Integer rows, ResourceVO resourceVO, HttpServletResponse response) {
		try {
			PageVO pageVO = new PageVO();
			pageVO.setPageNo(page);
			pageVO.setPageSize(rows);
			// 根据系统编码查询按钮
			pageVO = resourceServer.queryButtPage(resourceVO, pageVO);
			ResponseUtil.sendJSON(response, pageVO);

		} catch (Exception e) {
			LogUtil.ERROR.error("根据系统查询按钮异常", e);
			throw new BizException("根据系统查询按钮异常", e);
		}
	}

	@RequestMapping("/updateButtValidInd")
	public void updateButtValidInd(String resourceId, String validInd, HttpServletRequest request,
			HttpServletResponse response) throws BizException {

		String currentUser = CurrentUser.getUser().getUsername();
		try {
			// 将资源设置为失效
			List<String> resourceList = new ArrayList<String>();
			resourceList.add(resourceId);
			if (resourceList.size() > 0) {
				Map<String, Object> whereMap = new HashMap<String, Object>();
				whereMap.put("updatedUser", currentUser);
				whereMap.put("validInd", validInd);
				whereMap.put("resourceList", resourceList);
				resourceServer.updateResourcesValidInd(whereMap);
				ResponseUtil.sendMessage(response, true, "删除资源成功");
			}
		} catch (Exception e) {
			logger.error("修改资源状态异常！资源ID：" + resourceId, e);
			ResponseUtil.sendMessage(response, false, "删除菜单失败");
			throw new BizException("修改资源状态异常！资源ID：" + resourceId, e);
		}
	}
}
