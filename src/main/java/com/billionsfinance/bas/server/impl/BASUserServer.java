package com.billionsfinance.bas.server.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.billionsfinance.bas.entity.BASUserVO;
import com.billionsfinance.bas.entity.DepartVO;
import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.entity.SysUserRoleVO;
import com.billionsfinance.bas.server.IBASUserServer;
import com.billionsfinance.bas.service.BASUserService;
import com.billionsfinance.bas.service.DepartService;
import com.billionsfinance.bas.service.SysUserRoleService;
import com.billionsfinance.bas.util.CommonUtil;
import com.billionsfinance.bas.util.SpringService;

public class BASUserServer implements IBASUserServer {

	private BASUserService basUserService = SpringService.BASUSERSERVICE;
	
	private SysUserRoleService sysUserRoleService = SpringService.SYSUSERROLESERVICE;

	private DepartService departService = SpringService.DEPARTSERVICE;
	
	@Override
	public PageVO initQueryUser(Map<String, Object> whereMap,PageVO pageVO) {
		//查询总数
		Long total = basUserService.queryUserCount(whereMap);
		//获取分页参数
		Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
		whereMap.putAll(whereMapPage);
		List<Map<String, Object>> users = basUserService.queryUserListPage(whereMap);
		
		pageVO.setRows(users);
		
		return pageVO;
	}
	
	@Override
	public PageVO querySysUserPage(Map<String, Object> whereMap,PageVO pageVO,Map<String, Object> depart) {
		
		//根据条件获得系统用户
		Long total = basUserService.queryUserCount(whereMap);
		if(total.longValue() == 0){
			return pageVO;
		}
		//获得分页查询条件
		Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
		whereMap.putAll(whereMapPage);
		//分页查询系统用户
		List<Map<String, Object>> userList = null;
		List<Map<String, Object>> userResult = basUserService.queryUserListPage(whereMap);
		for(Map<String, Object> user: userResult){
			String deptNo = (String) user.get("deptNo");
			if(!StringUtils.isEmpty(deptNo)){
				String[] split = deptNo.split(",");
				StringBuffer sb = new StringBuffer();
				for(int i=0; i<split.length; i++){
					String departName = (String) depart.get(split[i]);
					sb.append(departName + ",");
				}
				String substring = sb.substring(0, sb.length()-1);
				user.put("departName", substring);
			}
		}
		pageVO.setRows(userResult);
		
		return pageVO;
	}
	
	@Override
	public List<String> findUserDept(String currentUser, Map<String, Object> departInfo) {
		List<String> list = null;
		BASUserVO basuser = basUserService.findUserByCode(currentUser);
		if(basuser==null){
			return null;
		}
		if(!StringUtils.isEmpty(basuser.getDeptNo())){
			String[] split = basuser.getDeptNo().split(",");
			list = new ArrayList<String>();
			for(int i=0; i<split.length; i++){
				String departName = (String) departInfo.get(split[i]);
				list.add(departName);
			}
		}
		return list;
	}
	
	
	@Override
	public BASUserVO findUserByCode(String userCode) {
		BASUserVO basuser = basUserService.findUserByCode(userCode);
//		if(basuser == null ){
//			return basuser;
//		}
//		Map<String,Object> whereMap = new HashMap<String, Object>();
//		whereMap.put("validInd", CommonUtil.VALID_IND_N);
//		whereMap.put("userCode", userCode);
//		List<Map<String, Object>> userHrDepts = userHrDeptService.findUserHrDept(whereMap);
//		StringBuffer deptId = new StringBuffer();
//		for (Map<String, Object> map : userHrDepts) {
//			deptId.append(map.get("deptid")).append(",");
//		}
//		String deptIdStr = "";
//		if(deptId.length() > 1){
//			deptIdStr = deptId.substring(0,deptId.length() - 1);
//		}
//		basuser.setFhrorgunitid(deptIdStr);
//		//拆分地区编码
//		
//		String hraddrCode = basuser.getHraddrCode();
//		if(!StringUtils.isEmpty(hraddrCode)){
//			String[] hraddrCodes = hraddrCode.split(",");
//			//判断长度区分存到哪一个级别
//			if(hraddrCodes.length >= 1){
//				basuser.setProvinceCode(hraddrCodes[0]);
//			}
//			//判断长度区分存到哪一个级别
//			if(hraddrCodes.length >= 2){
//				basuser.setCityCode(hraddrCodes[1]);
//			}
//			//判断长度区分存到哪一个级别
//			if(hraddrCodes.length >= 3){
//				basuser.setAreaCode(hraddrCodes[2]);
//			}
//		}
		
		return basuser;
	}

	@Override
	public String saveUser(BASUserVO basUserVO,SysUserRoleVO sysUserRoleVO) throws Exception {
		sysUserRoleService.saveSysUserRole(sysUserRoleVO);
		basUserService.saveUser(basUserVO);
		return basUserVO.getUserCode();
	}

	@Override
	public void updateUser(BASUserVO basUserVO,SysUserRoleVO sysUserRoleVO) {
		
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("userCode",sysUserRoleVO.getUserCode());
		List<SysUserRoleVO> querySysUserRole = sysUserRoleService.querySysUserRole(whereMap);
		if(querySysUserRole.isEmpty()){
			sysUserRoleService.saveSysUserRole(sysUserRoleVO);
		}else{
			sysUserRoleService.updateSysUserRole(sysUserRoleVO);
		}
		basUserService.updateUser(basUserVO);
		//添加HR部门和用户关系表数据
//		Map<String,Object> whereMap = new HashMap<String, Object>();
		// TODO 还需要根据用户的部门信息修改用户部门关系表
//		if(!StringUtils.isEmpty(basUserVO.getFhrorgunitid())){
//			whereMap.put("userCode", basUserVO.getUserCode());
//			//根据用户编码和 部门ID查找关系表
//			List<Map<String, Object>> userHrDepts = userHrDeptService.findUserHrDept(whereMap);
//			if(userHrDepts.size() == 0){
//				//直接将选中的部门存入关系表,只能选中一个部门
//				whereMap.put("id", UUIDGenerator.getUUID());
//				whereMap.put("deptid", basUserVO.getFhrorgunitid());
//				whereMap.put("validInd", CommonUtil.VALID_IND_N);
//				whereMap.put("updateUser", basUserVO.getUpdatedUser());
//				whereMap.put("createUser", basUserVO.getUpdatedUser());
//				userHrDeptService.saveUserHrDept(whereMap);
//			}else{
//				boolean isExit = fbase;
//				for (Map<String, Object> map : userHrDepts) {
//					if(map.get("deptid").equbas(basUserVO.getFhrorgunitid())){
//						if(CommonUtil.VALID_IND_F.equbas(map.get("validInd"))){
//							whereMap.put("id", map.get("id"));
//							whereMap.put("validInd", CommonUtil.VALID_IND_N);
//							whereMap.put("updateUser", basUserVO.getUpdatedUser());
//							userHrDeptService.updateUserHrDept(whereMap);
//						}
//						isExit = true;
//					}else{
//						whereMap.put("id", map.get("id"));
//						whereMap.put("validInd", CommonUtil.VALID_IND_F);
//						whereMap.put("updateUser", basUserVO.getUpdatedUser());
//						userHrDeptService.updateUserHrDept(whereMap);
//					}
//				}
//				if(!isExit){
//					//直接将选中的部门存入关系表,只能选中一个部门
//					whereMap.put("id", UUIDGenerator.getUUID());
//					whereMap.put("deptid", basUserVO.getFhrorgunitid());
//					whereMap.put("validInd", CommonUtil.VALID_IND_N);
//					whereMap.put("updateUser", basUserVO.getUpdatedUser());
//					whereMap.put("createUser", basUserVO.getUpdatedUser());
//					userHrDeptService.saveUserHrDept(whereMap);
//				}
//			}
//		}
		
	}
	
	
	@Override
	public List<DepartVO> loadDepart() {
		return departService.loadDepart();
	}
	
	@Override
	public List<Map<String, Object>> queryAllDepart() {
		return departService.queryAllDepart();
	}
	
	@Override
	public SysUserRoleVO queryRoleByMap(Map<String, Object> whereMap) {
		List<SysUserRoleVO> sysUserRole = sysUserRoleService.querySysUserRole(whereMap);
		
		if(sysUserRole.isEmpty()){
			return new SysUserRoleVO();
		}
		
		SysUserRoleVO sysUserRoleVO = sysUserRole.get(0);
		return sysUserRoleVO; 
	}

	@Override
	public void updatePwd(BASUserVO basUserVO) {
		
		basUserService.updatePwd(basUserVO);
	}



	@Override
	public void removeUser(BASUserVO basUserVO) {
//		//失效用户时需要将改用户对应的所有系统用户都设置为失效
//		//根据用户编码查询对应的有效的系统用户
//		SystemUserVO systemUserVO = new SystemUserVO();
//		systemUserVO.setUserCode(basUserVO.getUserCode());
//		systemUserVO.setValidInd(CommonUtil.VALID_IND_N);
//		List<SystemUserVO> sysUsers = systemUserService.findSysUser(systemUserVO);
//		Map<String,Object> whereMap = new HashMap<String,Object>();
//		whereMap.put("validInd", CommonUtil.VALID_IND_F);
//		for (SystemUserVO systemUserVO2 : sysUsers) {
//			//将系统用户设置为失效  
//			whereMap.put("sysUserId", systemUserVO2.getSysUserId());
//			whereMap.put("updatedUser", basUserVO.getUpdatedUser());
//			systemUserService.updateStatusBySysUserId(whereMap);
//		}
		//修改用户状态
		basUserService.updateUser(basUserVO);
	}



	@Override
	public void openUser(BASUserVO basUserVO) {
//		//开启时默认开启公共系统分类下的系统
//		//查出公共系统类下的系统
//		List<SystemVO> systems = systemService.querySystembytypeCode(CommonUtil.COMMON_SYSTEM);
//		//根据用户编码查询对应的有效的系统用户
//		SystemUserVO systemUserVO = new SystemUserVO();
//		List<SystemUserVO> systemUsers = new ArrayList<SystemUserVO>();
//		for (SystemVO systemVO : systems) {
//			//根据系统和用户查询系统用户
//			systemUserVO.setUserCode(basUserVO.getUserCode());
//			systemUserVO.setSysCode(systemVO.getSysCode());
//			systemUserVO.setValidInd(CommonUtil.VALID_IND_F);
//			List<SystemUserVO> sysUsers = systemUserService.findSysUser(systemUserVO);
//			systemUsers.addAll(sysUsers);
//		}
//		
//		Map<String,Object> whereMap = new HashMap<String,Object>();
//		whereMap.put("validInd", CommonUtil.VALID_IND_N);
//		for (SystemUserVO systemUserVO2 : systemUsers) {
//			//将系统用户设置为失效
//			whereMap.put("sysUserId", systemUserVO2.getSysUserId());
//			whereMap.put("updatedUser", basUserVO.getUpdatedUser());
//			systemUserService.updateStatusBySysUserId(whereMap);
//		}
		//开启用户
		basUserService.updateUser(basUserVO);
	}
	
	@Override
	public BASUserVO checkUser(BASUserVO basUserVO) {
		return basUserService.checkUser(basUserVO);
	}
	
	@Override
	public void deleteUser(String userCode) {
		basUserService.deleteUser(userCode);
	}
	
	@Override
	public void deleteRoleRe(Map<String, Object> whereMap) {
		basUserService.deleteRoleRe(whereMap);
	}

	@Override
	public void updateStatusByRole(Map<String, Object> whereMap) {
		basUserService.updateStatus(whereMap);
	}
	
}
