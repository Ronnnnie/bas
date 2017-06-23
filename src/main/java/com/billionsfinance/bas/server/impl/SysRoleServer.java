package com.billionsfinance.bas.server.impl;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.entity.SysRoleVO;
import com.billionsfinance.bas.server.ISysRoleServer;
import com.billionsfinance.bas.service.BASUserService;
import com.billionsfinance.bas.service.SysRoleService;
import com.billionsfinance.bas.util.CommonUtil;
import com.billionsfinance.bas.util.SpringService;

public class SysRoleServer implements ISysRoleServer {

	
	private SysRoleService sysRoleService = SpringService.SYSROLESERVICE;
	
	private BASUserService basUserService = SpringService.BASUSERSERVICE;
	
	
	@Override
	public PageVO queryRolePage(PageVO pageVO, Map<String, Object> whereMap) {
		
		//判断当前用户是否为管理员用户
//		String userCode = (String)whereMap.get("userCode");
//		boolean isAdmin = systemService.isSuperAdmin(userCode);
//		if(!isAdmin){
//			//如果不是管理员需要加系统来过滤角色
//			List<String> sysCodes = sysRoleService.querySysCodeByUser(userCode);
//			if(sysCodes.size() <= 0){
//				throw new BizException("该用户没有分配系统");
//			}
//			whereMap.put("sysCodeList", sysCodes);
//		}
		//统计角色总数
		Long total = sysRoleService.queryRoleCount(whereMap);
		//封装分页参数
		Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
		whereMap.putAll(whereMapPage);
		//查询角色
		List<Map<String, Object>> roles = sysRoleService.queryRolePage(whereMap);
		pageVO.setRows(roles);
		
		return pageVO;
	}

	@Override
	public void saveSysRole(SysRoleVO sysRoleVO) {
		
		sysRoleService.saveSysRole(sysRoleVO);
	}

	@Override
	public void updateSysRole(SysRoleVO sysRoleVO) {
		
		sysRoleService.updateSysRole(sysRoleVO);
	}

	@Override
	public SysRoleVO findSysRoleByCode(String roleCode) {
		
		return sysRoleService.findSysRoleByCode(roleCode);
	}
	
	@Override
	public void delRole(Map<String, Object> whereMap) {
		whereMap.put("status", '0');
		basUserService.updateStatus(whereMap);
		basUserService.deleteRoleRe(whereMap);
		sysRoleService.delRole(whereMap);
	}

	/**
	 * 根据条件查询角色
	 * @param whereMap
	 * @return
	 */
	@Override
	public List<SysRoleVO> findSysRoleBywhere(Map<String,Object> whereMap) throws Exception{
		
		return sysRoleService.findSysRoleBywhere(whereMap);
	}

	/**
	 * 查询所有角色
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryAllRole() {
		return sysRoleService.findAllSysRole();
	}

	
	
}
