package com.billionsfinance.bas.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.entity.SysRoleVO;
import com.billionsfinance.als.security.CurrentUser;
import com.billionsfinance.bas.server.IBASUserServer;
import com.billionsfinance.bas.server.ISysRoleServer;
import com.billionsfinance.bas.server.impl.BASUserServer;
import com.billionsfinance.bas.server.impl.SysRoleServer;
import com.billionsfinance.bas.util.CommonUtil;
import com.billionsfinance.bas.util.DateUtil;
import com.billionsfinance.bas.util.ResponseUtil;
import com.billionsfinance.common.Exception.BizException;

/**
 * 角色控制器
 */
@Controller
@RequestMapping("/role")
public class SysRoleController {

	/** 日志记录器 */
	private final Logger logger = Logger.getLogger(SysRoleController.class);

	private ISysRoleServer sysRoleServer = new SysRoleServer();
	
	private static final IBASUserServer alsUserServer = new BASUserServer();

	/**
	 * 进入角色管理
	 * 
	 * @return sysRole
	 */
	@RequestMapping("/toSysRole")
	public String toSysRole() {

		return "role/sysRole";
	}

	/**
	  * <p>Title: queryRolePage</p>
	  * <p>Description: 分页查询角色</p>
	  * @param page 
	  * @param rows 
	  * @param sysRoleVO 
	  * @param request 
	  * @param response 
	  * @throws Exception  
	  */
	@RequestMapping("/queryRolePage")
	public void queryRolePage(Integer page, Integer rows, SysRoleVO sysRoleVO, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		PageVO pageVO = new PageVO();
		try {
			String currentUser = CurrentUser.getUser().getUsername();
			pageVO.setPageSize(rows);
			pageVO.setPageNo(page);
			Map<String, Object> whereMap = new HashMap<String, Object>();
			whereMap.put("userCode", currentUser);
			if (sysRoleVO != null) {
				whereMap.put("roleCname", sysRoleVO.getRoleCname());
				whereMap.put("roleCode", sysRoleVO.getRoleCode());
				whereMap.put("validInd", sysRoleVO.getValidInd());
			}

			pageVO = sysRoleServer.queryRolePage(pageVO, whereMap);

		} catch (Exception e) {
			logger.error("分页查询角色出错", e);
			throw new BizException("分页查询角色出错", e);
		}
		ResponseUtil.sendJSON(response, pageVO);
	}

	/**
	  * <p>Title: queryAllRole</p>
	  * <p>Description: 查询所有角色</p>
	  * @param request 
	  * @param response 
	  * @throws Exception  
	  */
	@RequestMapping("/queryAllRole")
	public void queryAllRole(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Map<String, Object>> allRoles = sysRoleServer.queryAllRole();
		ResponseUtil.sendJSONList(response, allRoles);
	}

	/**
	  * <p>Title: saveSysRole</p>
	  * <p>Description: saveSysRole</p>
	  * @param sysRoleVO 
	  * @param request 
	  * @param response  
	  */
	@RequestMapping("/saveSysRole")
	public void saveSysRole(SysRoleVO sysRoleVO, HttpServletRequest request, HttpServletResponse response) {

		try {
			SysRoleVO roleByCode = sysRoleServer.findSysRoleByCode(sysRoleVO.getRoleCode());
			if (roleByCode != null) {
				ResponseUtil.sendMessage(response, false, "角色已存在");
				return;
			}
			Map<String, Object> whereMap = new HashMap<String, Object>();
			whereMap.put("roleCname", sysRoleVO.getRoleCname());
			List<SysRoleVO> sysRoles = sysRoleServer.findSysRoleBywhere(whereMap);
			for (SysRoleVO sysRoleVO2 : sysRoles) {
				if (sysRoleVO.getRoleCname().equals(sysRoleVO2.getRoleCname())) {
					ResponseUtil.sendMessage(response, false, sysRoleVO.getRoleCname() + "角色名已存在");
					return;
				}
			}
			String currentUser = CurrentUser.getUser().getUsername();
			sysRoleVO.setUpdatedUser(currentUser);
			sysRoleVO.setCreatedUser(currentUser);
			sysRoleVO.setCreatedDate(DateUtil.sqlDate());
			sysRoleVO.setUpdatedDate(DateUtil.sqlDate());
			sysRoleVO.setValidInd("0");
			sysRoleServer.saveSysRole(sysRoleVO);
			ResponseUtil.sendMessage(response, true, "创建角色成功");
		} catch (Exception e) {
			logger.error("创建角色失败", e);
			ResponseUtil.sendMessage(response, false, "创建角色失败");
		}
	}

	/**
	  * <p>Title: updateSysRole</p>
	  * <p>Description: updateSysRole</p>
	  * @param sysRoleVO 
	  * @param request 
	  * @param response  
	  */
	@RequestMapping("/updateSysRole")
	public void updateSysRole(SysRoleVO sysRoleVO, HttpServletRequest request, HttpServletResponse response) {

		try {

			// 判断角色名称在同一个系统中不能重复
			Map<String, Object> whereMap = new HashMap<String, Object>();
			whereMap.put("roleCode", sysRoleVO.getRoleCode());
			whereMap.put("roleCname", sysRoleVO.getRoleCname());
			// List<SysRoleVO> sysRoles =
			// sysRoleServer.findSysRoleBywhere(whereMap);
			// for (SysRoleVO sysRoleVO2 : sysRoles) {
			// if(sysRoleVO.getRoleCname().equals(sysRoleVO2.getRoleCname())
			// && !sysRoleVO.getRoleCode().equals(sysRoleVO2.getRoleCode())){
			// ResponseUtil.sendMessage(response,
			// false,"系统"+sysRoleVO.getSysCode()+"中角色名已存在");
			// return;
			// }
			// }
			String currentUser = CurrentUser.getUser().getUsername();
			sysRoleVO.setUpdatedUser(currentUser);
			sysRoleServer.updateSysRole(sysRoleVO);
			ResponseUtil.sendMessage(response, true, "修改角色成功");
		} catch (Exception e) {
			ResponseUtil.sendMessage(response, false, "修改角色失败");
			logger.error("修改角色失败", e);
		}
	}

	/**
	  * <p>Title: findSysRoleByCode</p>
	  * <p>Description: findSysRoleByCode</p>
	  * @param roleCode  
	  * @param response   
	  */
	@RequestMapping("/findSysRoleByCode")
	public void findSysRoleByCode(String roleCode, HttpServletResponse response) {

		try {
			SysRoleVO sysRole = sysRoleServer.findSysRoleByCode(roleCode);
			// 还需要获取部门信息

			ResponseUtil.sendJSON(response, sysRole);
		} catch (Exception e) {
			logger.error("根据RoleCode查询角色异常，roleCode=" + roleCode, e);
		}
	}

	/**
	 * 将角色设置为失效
	 * 
	 * @param roleCode 
	 * @param request  
	 * @param response 
	 */
	@RequestMapping("/removeRole")
	public void removeRole(String roleCode, HttpServletRequest request, HttpServletResponse response) {

		try {
			String currentUser = CurrentUser.getUser().getUsername();

			if (StringUtils.isEmpty(roleCode)) {
				ResponseUtil.sendMessage(response, true, "角色编码不能为空");
				return;
			}
			SysRoleVO sysRoleVO = new SysRoleVO();
			sysRoleVO.setRoleCode(roleCode);
			sysRoleVO.setUpdatedUser(currentUser);
			sysRoleVO.setValidInd(CommonUtil.VALID_IND_F);
			sysRoleServer.updateSysRole(sysRoleVO);
			Map<String, Object> whereMap = new HashMap<String, Object>();
			whereMap.put("roleCode", roleCode);
			whereMap.put("updatedUser", currentUser);
			whereMap.put("status", CommonUtil.VALID_IND_F);
			alsUserServer.updateStatusByRole(whereMap);
			ResponseUtil.sendMessage(response, true, "禁用角色成功");
		} catch (Exception e) {
			ResponseUtil.sendMessage(response, false, "禁用角色失败");
			logger.error("禁用角色异常，角色编码：" + roleCode, e);
		}
	}

	/**
	  * <p>Title: delRole</p>
	  * <p>Description: delRole</p>
	  * @param roleCode 
	  * @param request 
	  * @param response  
	  */
	@RequestMapping("/delRole")
	public void delRole(String roleCode, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("roleCode", roleCode);
		try {
			sysRoleServer.delRole(whereMap);
			ResponseUtil.sendMessage(response, true, "删除角色成功");
		} catch (Exception e) {
			ResponseUtil.sendMessage(response, false, "删除角色失败");
			logger.error("删除角色失败，角色编码：" + roleCode, e);
		}
	}

	/**
	 * 将角色设置为可用
	 * 
	 * @param roleCode
	 * @param validInd
	 * @param response
	 */
	@RequestMapping("/openRole")
	public void openRole(String roleCode, HttpServletRequest request, HttpServletResponse response) {

		try {
			String currentUser = CurrentUser.getUser().getUsername();

			if (StringUtils.isEmpty(roleCode)) {
				ResponseUtil.sendMessage(response, true, "角色编码不能为空");
				return;
			}
			SysRoleVO sysRoleVO = new SysRoleVO();
			sysRoleVO.setRoleCode(roleCode);
			sysRoleVO.setUpdatedUser(currentUser);
			sysRoleVO.setValidInd(CommonUtil.VALID_IND_N);
			sysRoleServer.updateSysRole(sysRoleVO);
			Map<String, Object> whereMap = new HashMap<String, Object>();
			whereMap.put("roleCode", roleCode);
			whereMap.put("updatedUser", currentUser);
			whereMap.put("status", CommonUtil.VALID_IND_N);
			alsUserServer.updateStatusByRole(whereMap);
			ResponseUtil.sendMessage(response, true, "启用角色成功");
		} catch (Exception e) {
			ResponseUtil.sendMessage(response, false, "启用角色失败");
			logger.error("启用角色异常，角色编码：" + roleCode, e);
		}
	}
}
