package com.billionsfinance.bas.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.billionsfinance.als.security.CurrentUser;
import com.billionsfinance.bas.entity.BASUserVO;
import com.billionsfinance.bas.entity.SysUserRoleVO;
import com.billionsfinance.bas.server.IBASUserServer;
import com.billionsfinance.bas.server.IResourceServer;
import com.billionsfinance.bas.server.impl.BASUserServer;
import com.billionsfinance.bas.server.impl.ResourceServer;
import com.billionsfinance.bas.util.DateUtil;
import com.billionsfinance.bas.util.FileUtil;
import com.billionsfinance.bas.util.Globals;
import com.billionsfinance.bas.util.LogUtil;
import com.billionsfinance.bas.util.ResponseUtil;
import com.billionsfinance.common.Exception.BizException;
import com.billionsfinance.common.framework.GlobalParam;

/**
 * @ClassName: MainFrameController
 * @Description: 
 * @author zhanghs 2016年7月19日 下午3:32:16 Copyright: Copyright (c) 2016
 *         Company:佰仟金融
 */
@Controller
@RequestMapping("/main")
public class MainFrameController {

	/** 日志记录器 */
	private final Log logger = LogFactory.getLog(MainFrameController.class);

	private final IBASUserServer alsUserServer = new BASUserServer();

	/**
	 * <p>
	 * Title: mainFramePage
	 * </p>
	 * <p>
	 * Description: 跳转到主页面
	 * </p>
	 * @param request 
	 * @param model 
	 * @return 主页面 
	 * @throws BizException 
	 */
	@RequestMapping(value = "/menu")
	public String mainFramePage(HttpServletRequest request, ModelMap model) throws BizException {
		BASUserVO alsUserVO = new BASUserVO();
		String userCode = "";
		if(CurrentUser.getUser().getUsername()==null){
			alsUserVO.setUserCode("");
		}else{
			userCode = CurrentUser.getUser().getUsername();
			BASUserVO userVO = alsUserServer.findUserByCode(userCode);
			alsUserVO.setUserCname(userVO.getUserCname());
			alsUserVO.setUserCode(userCode);;
		}
		// 1、查询对应的系统菜单

		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("userCode", alsUserVO.getUserCode());
		SysUserRoleVO sysUserRoleVO = alsUserServer.queryRoleByMap(whereMap);
		if (Globals.SUPERAUTHORITY.equals(sysUserRoleVO.getRoleCode())) {
			request.getSession().setAttribute("isSuperAdmin", true);
		}
		
		if (Globals.REVOKE_ROLE.containsKey(sysUserRoleVO.getRoleCode())) {
			request.getSession().setAttribute("isRevokeRole", true);
		}
		
		// 缓存所有部门信息
		Map<String, Object> departInfo = new HashMap<String, Object>();
		List<Map<String, Object>> departs = alsUserServer.queryAllDepart();
		for (Map<String, Object> dept : departs) {
			departInfo.put((String) dept.get("id"), dept.get("departName"));
		}
		request.getSession().setAttribute(Globals.DEPART_CACHE, departInfo);

		// 缓存当前用户所管理的部门信息
		List<String> controlDept = alsUserServer.findUserDept(alsUserVO.getUserCode(), departInfo);
		request.getSession().setAttribute(Globals.CON_DEPT, controlDept);

		List<Map<String, Object>> menuList;
		try {
			IResourceServer resourceServer = new ResourceServer();
			// 查询登录用户有权限访问的菜单
			menuList = resourceServer.findSystemMenusByUser(alsUserVO);
		} catch (Exception e) {
			LogUtil.ERROR.error("查询用户菜单出错", e);
			throw new BizException("查询用户菜单出错", e);
		}
		// 剥离一级菜单
		List<Map<String, Object>> firstMenus = this.getFirstMenuList(menuList);
		// 剥离二级菜单
		Map<String, List<Map<String, Object>>> secondMenus = this.getNextMenuList(menuList, firstMenus);
		model.addAttribute("fMenus", firstMenus);
		model.addAttribute("sMenus", secondMenus);
		model.addAttribute("currentUser", alsUserVO.getUserCode());
		model.addAttribute("userName", alsUserVO.getUserCname());
		model.addAttribute("showDate", DateUtil.SDF.format(new Date()));
		return "common/mainFrame";
	}

	// @RequestMapping("/getSysMenu")
	// public void createDomain(HttpServletRequest request,HttpServletResponse
	// response)
	// {
	// String syscode = request.getParameter("syscode");
	// String usercode = request.getParameter("usercode");
	// String json ="";
	// if(syscode == null || "".equals(syscode)){
	// json = "{\"status\":\"false\",\"msg\":\" 系统编码(syscode)不能为空\"}";
	// ResponseUtil.sendJSON(response, json);
	// return;
	// }
	// if(usercode == null || "".equals(usercode)){
	// json = "{\"status\":\"false\",\"msg\":\"用户账号(usercode) 不能为空\"}";
	// ResponseUtil.sendJSON(response, json);
	// return;
	// }
	// SystemUserVO systemUserVO = new SystemUserVO();
	// systemUserVO.setSysCode(syscode);
	// systemUserVO.setUserCode(usercode);
	// List<Map<String, Object>> menuList;
	// try {
	// //查询登录用户有权限访问的菜单
	// IResourceServer resourceServer =
	// RemoteCall.getRemoteObject(IResourceServer.class);
	// menuList = resourceServer.findSystemMenusByUser(systemUserVO);
	// } catch (Exception e) {
	// LogUtil.ERROR.error("查询用户菜单出错", e);
	// throw new BizException("查询用户菜单出错", e);
	// }
	// // 剥离一级菜单
	// List<Map<String, Object>> firstMenus = this.getFirstMenuList(menuList);
	// // 剥离二级菜单
	// Map<String, List<Map<String, Object>>> secondMenus =
	// this.getNextMenuList(menuList, firstMenus);
	// Map<String, Object> obj = new HashMap<String, Object>();
	// obj.put("fMenus", firstMenus);
	// obj.put("sMenus", secondMenus);
	// ResponseUtil.sendJSON(response, obj);
	// }

	/**
	 * 剥离二级菜单 <br>
	 * 
	 * <pre>
	 * 方法getSecondMenuList的详细说明 <br>
	 * </pre>
	 * 
	 * @param menuList 
	 * @param nextMenu  
	 * @return List<Map<String,Object>> 说明
	 * @throws 异常类型
	 *             说明
	 */
	private Map<String, List<Map<String, Object>>> getNextMenuList(List<Map<String, Object>> menuList,
			List<Map<String, Object>> nextMenu) {
		Map<String, List<Map<String, Object>>> secondMenu = new HashMap<String, List<Map<String, Object>>>();
		List<Map<String, Object>> secondMenuList = null;
		Iterator<Map<String, Object>> menuIt = null;
		Iterator<Map<String, Object>> firstIt = nextMenu.iterator();
		Map<String, Object> firstMap = null;
		Map<String, Object> menuMap = null;
		String parentId = null;
		while (firstIt.hasNext()) {
			firstMap = firstIt.next();
			menuIt = menuList.iterator();
			secondMenuList = new ArrayList<Map<String, Object>>();
			while (menuIt.hasNext()) {
				menuMap = menuIt.next();
				parentId = menuMap.get("parentId") + "";
				if (firstMap.get("resourceId").toString().equalsIgnoreCase(parentId)) {
					secondMenuList.add(menuMap);
					menuIt.remove();
				}
			}
			secondMenu.put(firstMap.get("resourceId") + "", secondMenuList);
		}
		return secondMenu;
	}

	/**
	 * 
	 * 剥离一级菜单的方法 <br>
	 * 
	 * <pre>
	 * 方法getFirstMenuList的详细说明 <br>
	 *  
	 * @param  menuList 
	 * @return list
	 * @throws 异常类型
	 */
	private List<Map<String, Object>> getFirstMenuList(List<Map<String, Object>> menuList) {
		List<Map<String, Object>> firstList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		Iterator<Map<String, Object>> it = menuList.iterator();
		while (it.hasNext()) {
			map = it.next();
			if (map.get("parentId") == null || map.get("parentId").equals("")) {
				firstList.add(map);
				it.remove();
			}
		}
		return firstList;
	}

	/**
	  * <p>Title: imagesUrl</p>
	  * <p>Description: 获得图片路径</p>
	  * @param request 
	  * @param response  
	  */
	@RequestMapping("imagesUrl")
	public void imagesUrl(HttpServletRequest request, HttpServletResponse response) {
		try {
			String imagesUrl = GlobalParam.getParam(FileUtil.IMAGES_URL);
			Map<String, String> obj = new HashMap<String, String>();
			obj.put("imagesUrl", imagesUrl);
			ResponseUtil.sendJSON(response, obj);
		} catch (Exception e) {
			logger.error("获取图片路径失败！");
		}
	}
	

}
