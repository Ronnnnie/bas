package com.billionsfinance.bas.server.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.entity.SysUserRoleVO;
import com.billionsfinance.bas.entity.SystemUserVO;
import com.billionsfinance.bas.server.ISystemUserServer;
import com.billionsfinance.bas.service.BASUserService;
import com.billionsfinance.bas.service.SysRoleService;
import com.billionsfinance.bas.service.SysUserRoleService;
import com.billionsfinance.bas.service.SystemService;
import com.billionsfinance.bas.service.SystemUserService;
import com.billionsfinance.bas.util.CommonUtil;
import com.billionsfinance.bas.util.LogUtil;
import com.billionsfinance.bas.util.SpringService;
import com.billionsfinance.common.Exception.BizException;

public class SystemUserServer implements ISystemUserServer {

	/** 日志记录器 */
	private static final Log logger = LogFactory.getLog(SystemUserServer.class);
	
	private SystemUserService systemUserService = SpringService.SYSTEMUSERSERVICE;
	
	private SystemService systemService = SpringService.SYSTEMSERVICE;

	private SysUserRoleService sysUserRoleService = SpringService.SYSUSERROLESERVICE;
	
	private SysRoleService sysRoleService = SpringService.SYSROLESERVICE;
	
	private BASUserService basUserService = SpringService.BASUSERSERVICE;
	
	@Override
	public List<Map<String, Object>> querySysByCurrentUser(
			Map<String, Object> whereMap) {

		//判断当前用户是否是超级管理员
		boolean isAdmin = systemService.isSuperAdmin((String)whereMap.get("currentUser"));
		List<Map<String, Object>> sysByCurrentUser = null;
		List<Map<String, Object>> sysExist = null;
		if(isAdmin){
			//当前用户为超级管理员，根据当前用户查询当前用户有权限分配的系统
			sysByCurrentUser = systemUserService.queryAllSystem(whereMap);
			//当前用户为管理员，在当前系统有权限分配的系统中，所选用户有权限的系统
			sysExist = systemUserService.querySysExistAdmin(whereMap);
			
		}else{
			//根据当前用户查询当前用户有权限分配的系统
			sysByCurrentUser = systemUserService.querySysByCurrentUser(whereMap);
			//在当前系统有权限分配的系统中，所选用户有权限的系统
			 sysExist= systemUserService.querySysExist(whereMap);
			
		}
		if( sysByCurrentUser != null && sysExist != null){
			//移除当前用户已经存在的，剩下的就是可选的系统
			sysByCurrentUser.removeAll(sysExist);
		}
		return sysByCurrentUser;
	}
	@Override
	public List<Map<String, Object>> queryUserSysExist(
			Map<String, Object> whereMap) {
		
		//判断当前用户是否是超级管理员
		boolean isAdmin = systemService.isSuperAdmin((String)whereMap.get("currentUser"));
		List<Map<String, Object>> sysExist = null;
		if(isAdmin){
			//当前用户为管理员，在当前系统有权限分配的系统中，所选用户有权限的系统
			 sysExist = systemUserService.querySysExistAdmin(whereMap);
		}else{
			//在当前系统有权限分配的系统中，所选用户有权限的系统
			sysExist = systemUserService.querySysExist(whereMap);
		}
		
		return sysExist;
	}
	@Override
	public void addSystemUser(List<SystemUserVO> sysUsers, String currentUser,String userCode) {
		
		//判断当前用户是否为超级管理员
		boolean isAdmin = systemService.isSuperAdmin(currentUser);
		Map<String,Object> whereMap = new HashMap<String, Object>();
		whereMap.put("userCode", userCode);
		whereMap.put("currentUser", currentUser);
		List<SystemUserVO> systemUser = null;
		if(isAdmin){
			//系统用户表获取当前用户所有对应的系统
			systemUser = systemUserService.querySysUerByWhere(whereMap);
		}else{
			//查询当前用户有权限的系统，所选 用户也有权限的系统
			systemUser = systemUserService.querySysUserByCurrentUser(whereMap);
		}
		//循环页面选中的系统，已存在系统中没有则插入
		for (SystemUserVO pageSysUser : sysUsers) {
			boolean exist = false;
			for (SystemUserVO existSysUser : systemUser) {
				if(pageSysUser.getSysCode().equals(existSysUser.getSysCode())){
					exist = true;
					break;
				}
			}
			//不存在就插入
			if (!exist) {
				systemUserService.addSysUser(pageSysUser);
				LogUtil.APP.info("创建系统用户成功：系统名编码="+pageSysUser.getSysCode() + "用户编码："+pageSysUser.getUserCode());
			}
		}
		//遍历已存在的系统用户，所选的系统中存在则更新为可用，不存在则更新为不可用
		for (SystemUserVO existSysUser : systemUser) {
			boolean exist = false;
			for (SystemUserVO pageSysUser : sysUsers) {
				//页面上分配的存在于已存在的系统中,并且为失效的则修改为有效
				if(existSysUser.getSysCode().equals(pageSysUser.getSysCode())){
					exist = true;
					if(CommonUtil.VALID_IND_F.equals(existSysUser.getValidInd())){
						Map<String,Object> updatewhereMap = new HashMap<String, Object>();
						updatewhereMap.put("sysUserId", existSysUser.getSysUserId());
						updatewhereMap.put("validInd", CommonUtil.VALID_IND_N);
						updatewhereMap.put("updatedUser", currentUser);
						updatewhereMap.put("updatedDate",new Timestamp(System.currentTimeMillis()));
						systemUserService.updateStatusBySysUserId(updatewhereMap);
						LogUtil.APP.info("更新系统用户状态为有效成功：系统名编码="+pageSysUser.getSysCode() + "用户编码："+pageSysUser.getUserCode());
					}
					break;
				}
			}
			//如果页面没有分配，则设置为失效
			if(!exist){
				Map<String,Object> updatewhereMap = new HashMap<String, Object>();
				updatewhereMap.put("sysUserId", existSysUser.getSysUserId());
				updatewhereMap.put("validInd", CommonUtil.VALID_IND_F);
				updatewhereMap.put("updatedUser", currentUser);
				systemUserService.updateStatusBySysUserId(updatewhereMap);
				LogUtil.APP.info("更新系统用户状态为无效成功：系统名编码="+existSysUser.getSysCode() + "用户编码："+existSysUser.getUserCode());
			}
		}
	}

	@Override
	public List<Map<String, Object>> queryRoleBySysCode(
			Map<String, Object> whereMap) {
		
		return systemUserService.queryRoleBySysCode(whereMap);
	}
	
	@Override
	public List<Map<String, Object>> queryRoleExist(Map<String, Object> whereMap) {
		
		return systemUserService.queryRoleExist(whereMap);
	}
	@Override
	public List<SystemUserVO> querySysUserByWhere(Map<String, Object> whereMap) {
		
		return systemUserService.querySysUerByWhere(whereMap);
	}
	
	@Override
	public void saveUserRole(List<SysUserRoleVO> sysUserRoles,
			String currentUser, String sysUserId) {
		
		//查询该系统用户已经存在的角色
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("sysUserId", sysUserId);
		List<SysUserRoleVO> sysUserRoleExist = sysUserRoleService.querySysUserRole(whereMap);
		
		//循环页面选中的角色，已存在的角色中没有则插入
		for (SysUserRoleVO pageSysUserRole : sysUserRoles) {
			boolean exist = false;
			for (SysUserRoleVO existSysUserRole : sysUserRoleExist) {
				if(pageSysUserRole.getRoleCode().equals(existSysUserRole.getRoleCode())){
					exist = true;
					break;
				}
			}
			//不存在就插入
			if (!exist) {
				sysUserRoleService.saveSysUserRole(pageSysUserRole);
				LogUtil.APP.info("创建系统用户成功：角色编码="+pageSysUserRole.getRoleCode());
			}
		}
		//遍历已存在的系统用户角色，所选的角色中存在则更新为可用，不存在则更新为不可用
		for (SysUserRoleVO existSysUserRole : sysUserRoleExist) {
			boolean exist = false;
			for (SysUserRoleVO pageSysUserRole : sysUserRoles) {
				//页面上分配的存在于已存在的角色中,并且为失效的则修改为有效
				if(pageSysUserRole.getRoleCode().equals(existSysUserRole.getRoleCode())){
					exist = true;
					if(CommonUtil.VALID_IND_F.equals(existSysUserRole.getValidInd())){
						existSysUserRole.setValidInd(CommonUtil.VALID_IND_N);
						existSysUserRole.setUpdatedUser(currentUser);
						sysUserRoleService.updateSysUserRole(existSysUserRole);
						LogUtil.APP.info("更新系统用户的角色状态为有效成功：角色编码="+pageSysUserRole.getRoleCode() + "状态为有效");
					}
					break;
				}
			}
			//如果页面没有分配，则设置为失效
			if(!exist){
				existSysUserRole.setValidInd(CommonUtil.VALID_IND_F);
				existSysUserRole.setUpdatedUser(currentUser);
				sysUserRoleService.updateSysUserRole(existSysUserRole);
				LogUtil.APP.info("更新系统用户的角色状态为有效成功：角色编码="+existSysUserRole.getRoleCode() + "状态为失效");
			}
		}
	}
	
	@Override
	public void saveUserRoleVO(SysUserRoleVO sysUserRoleVO) {
		sysUserRoleService.saveSysUserRole(sysUserRoleVO);
		LogUtil.APP.info("创建用户角色关系成功：角色编码="+sysUserRoleVO.getRoleCode());
	}
	
	
	@Override
	public PageVO querySysUserPage(Map<String, Object> whereMap,PageVO pageVO) {
		
		//判断当前用户是否为管理员用户
		String currentUser = (String)whereMap.get("currentUser");
		boolean isAdmin = systemService.isSuperAdmin(currentUser);
		if(!isAdmin){
			//如果不是管理员需要加系统来过滤系统用户，查询当前用户是管理员的系统
			List<String> sysCodes = sysRoleService.querySysCodeByUser(currentUser);
			if(sysCodes.size() <= 0){
				throw new BizException("该用户没有分配系统");
			}
			whereMap.put("sysCodeList", sysCodes);
		}
		
		//根据条件获得系统用户
		Long total = systemUserService.querySysUserCount(whereMap);
		if(total.longValue() == 0){
			return pageVO;
		}
		//获得分页查询条件
		Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
		whereMap.putAll(whereMapPage);
		//分页查询系统用户
//		List<Map<String, Object>> sysUsers = systemUserService.querySysUser(whereMap);
		List<Map<String, Object>> userList = basUserService.queryUserListPage(whereMap);
		pageVO.setRows(userList);
		
		return pageVO;
	}
	
	@Override
	public String updateSystemUser(SystemUserVO systemUserVO) {
		String msg = "修改系统用户成功";
		//根据用户和系统来查找系统用户，如果已经存在就不能修改
		//查出所有的
		systemUserVO.setValidInd(null);
		List<SystemUserVO> sysUsers = systemUserService.findSysUser(systemUserVO);
		if(sysUsers != null && sysUsers.size() > 0){
			msg = "该用户已在系统"+systemUserVO.getSysCode()+"中存在";
			return msg;
		}
		systemUserVO.setValidInd(CommonUtil.VALID_IND_N);
		//这个系统用户
		systemUserService.updateSystemUser(systemUserVO);
		
		return msg;
	}
	
	@Override
	public void updateSysUserValidInd(SystemUserVO systemUserVO) {
		
		//这个系统用户
		systemUserService.updateSystemUser(systemUserVO);
		
	}
	
	@Override
	public void addSaveUser(List<SystemUserVO> addsysUsers, List<SystemUserVO> removesysUsers) {
		
		//删除系统用户
		if(!CollectionUtils.isEmpty(removesysUsers)){
			for (SystemUserVO systemUserVO : removesysUsers) {
				//根据用户和系统查询是否已经存在该系统用户,查询所有的
//				systemUserVO.setValidInd(null);
				List<SystemUserVO> sysUserList = systemUserService.findSysUser(systemUserVO);
				Map<String,Object> whereMap = new HashMap<String,Object>();
				
				if(sysUserList != null && sysUserList.size() > 0){
					for (SystemUserVO systemUserVO2 : sysUserList) {
						//将系统用户设置为失效
						whereMap.put("validInd", CommonUtil.VALID_IND_F);
						whereMap.put("updatedUser", systemUserVO.getUpdatedUser());
						whereMap.put("sysUserId", systemUserVO2.getSysUserId());
						systemUserService.updateStatusBySysUserId(whereMap);
					}
				}
			}
		}
		//新增系统用户
		if(!CollectionUtils.isEmpty(addsysUsers)){
			for (SystemUserVO systemUserVO : addsysUsers) {

				//根据用户和系统查询是否已经存在该系统用户,查询所有的
				systemUserVO.setValidInd(null);
				List<SystemUserVO> sysUserList = systemUserService.findSysUser(systemUserVO);
				Map<String,Object> whereMap = new HashMap<String,Object>();
				
				if(sysUserList != null && sysUserList.size() > 0){
					for (SystemUserVO systemUserVO2 : sysUserList) {
						//如果是已经失效的用户则需要修改
						if(CommonUtil.VALID_IND_F.equals(systemUserVO2.getValidInd())){
							whereMap.put("validInd", CommonUtil.VALID_IND_N);
							whereMap.put("updatedUser", systemUserVO.getUpdatedUser());
							whereMap.put("sysUserId", systemUserVO2.getSysUserId());
							systemUserService.updateStatusBySysUserId(whereMap);
						}else{
							logger.error("用户："+systemUserVO.getUserCode()+"已在系统"+systemUserVO.getSysCode() + "中");
						}
					}
				}else{
					//添加系统用户,不存在系统用户
					systemUserVO.setValidInd(CommonUtil.VALID_IND_N);
					systemUserService.addSysUser(systemUserVO);
				}
			}
		}
	}
	@Override
	public PageVO queryUserBindingPage(Map<String,Object> whereMap,PageVO pageVO){

		List<Map<String, Object>> sysUsers = new ArrayList<Map<String,Object>>();
		String currentUser = (String)whereMap.get("currentUser");
		Long total = 0L;
		//判断是否是非ALS的其他系统管理员
		boolean sysAdmin = systemService.isSysAdmin(currentUser);
		if(sysAdmin){
			total = systemUserService.queryAdminBindingCount(whereMap);
			//获得分页查询条件
			Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
			whereMap.putAll(whereMapPage);
			//管理员查询数据
			sysUsers = systemUserService.queryAdminBinding(whereMap);
		}else{
			//判断当前用户是否是系统管理员
			boolean superAdmin = systemService.isSuperAdmin(currentUser);
			if(!superAdmin){
				//如果是非管理员，查询的始终是自己的
				whereMap.put("userCode", currentUser);
			}
			//根据条件获得系统用户
			total = systemUserService.userBindingCount(whereMap);
			if(total.longValue() == 0){
				return pageVO;
			}
			//获得分页查询条件
			Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
			whereMap.putAll(whereMapPage);
			//分页查询系统用户
			 sysUsers = systemUserService.queryUserBinding(whereMap);
		}
		pageVO.setRows(sysUsers);
		return pageVO;
	}
	
	public void addSysUserBinding(Map<String,Object> map){
		//查询出该系统账号原来的默认账号
		List<Map<String, Object>> users = systemUserService.findSysUsernameByCode(map);
		if(!CollectionUtils.isEmpty(users)){
			//查询出该系统账号原来的默认账号
			for (Map<String, Object> map2 : users) {
				map2.put("isDefault", "0");
				map2.put("currentUser", map.get("createUser"));
				systemUserService.updateUserIsDefault(map2);
			}
		}
		map.put("isDefault", "1");
		systemUserService.addSysUserBinding(map);
	}
	
	/**
	 * 根据usercode,syscode删除对象
	 * @param map
	 */
	public void deleteSysUserBinding(Map<String,String> map){
		systemUserService.deleteSysUserBinding(map);
	}
	
	/**
	 * 根据系统编码和系统中的账号查找该系统账号是否绑定过
	 * @param map
	 * @return
	 */
	@Override
	public List<Map<String,String>> findSysUsername(Map<String,Object> map){
		
		return systemUserService.findSysUsername(map);
	}
	
	@Override
	public void opDefaultUser(Map<String, Object> map) {
		//查询出该系统账号原来的默认账号
		List<Map<String, Object>> users = systemUserService.findSysUsernameByCode(map);
		if(!CollectionUtils.isEmpty(users)){
			//查询出该系统账号原来的默认账号
			for (Map<String, Object> map2 : users) {
				map2.put("isDefault", "0");
				map2.put("currentUser", map.get("currentUser"));
				systemUserService.updateUserIsDefault(map2);
			}
		}
		//设置当前选择的为默认
		map.put("isDefault", "1");
		systemUserService.updateUserIsDefault(map);
	}
	
	/**
	 * 查询系统用户的 绑定的账号
	 * @param map
	 * @throws Exception
	 */
	@Override
	public List<Map<String, Object>> findSysAccount(Map<String, Object> whereMap)throws Exception{
		
		return systemUserService.findSysAccount(whereMap);
	}
}
