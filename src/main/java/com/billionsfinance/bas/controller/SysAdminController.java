package com.billionsfinance.bas.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.billionsfinance.als.security.CurrentUser;
import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.entity.SysAdminVO;
import com.billionsfinance.bas.entity.SystemUserVO;
import com.billionsfinance.bas.server.ISysAdminServer;
import com.billionsfinance.bas.server.impl.SysAdminServer;
import com.billionsfinance.bas.util.CommonUtil;
import com.billionsfinance.bas.util.DateUtil;
import com.billionsfinance.bas.util.ResponseUtil;
import com.billionsfinance.bas.util.UUIDGenerator;
import com.billionsfinance.common.Exception.BizException;


/**
 *
 * 基本思路:
 * 特别说明:处理系统管理员
 * 修改说明: 类的修改说明
 * </pre>
 */
@Controller
@RequestMapping("/admin")
public class SysAdminController {

	private final Log log = LogFactory.getLog(SysAdminController.class);

	//系统管理员管理
	private final  ISysAdminServer sysAdminServer = new SysAdminServer();
	
	/**
	 * 进入管理员信息页面
	 * @return admin
	 */
	@RequestMapping("/toAdmin")
	public String toSystem(){
		
		return "sysAdmin/admin";
	}
	
	/**
	 * 进入系统管理员管理页面
	 * @return sysAdmin
	 */
	@RequestMapping("/toSysAdmin")
	public String toSysAdmin(){
		
		return "sysAdmin/sysAdmin";
	}
	
	/**
	  * <p>Title: querySysUser</p>
	  * <p>Description: 给系统分配管理员时查看可分配的系统用户</p>
	  * @param systemUserVO 
	  * @param userCname 
	  * @param request 
	  * @param response 
	  * @throws BizException  
	  */
	@RequestMapping("/querySysUser")
	public void querySysUserNoPage(SystemUserVO systemUserVO, String userCname, 
			HttpServletRequest request, HttpServletResponse response) throws BizException {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("validInd", CommonUtil.VALID_IND_N); //只查有效数据
		paramMap.put("sysCode", systemUserVO.getSysCode());
		paramMap.put("userCode", systemUserVO.getUserCode());
		paramMap.put("userCname", userCname);
		
		List<Map<String, Object>> sysUserLs;
		try {
			sysUserLs = sysAdminServer.queryCurrSysUser(paramMap);
		} catch (Exception e) {
			log.error("查询可分配用户异常", e);
			throw new BizException("失效操作异常，传入参数为：" + paramMap, e);
		}
		ResponseUtil.sendJSON(response, sysUserLs);
	}

	
	/**
	  * <p>Title: querySysAdmin</p>
	  * <p>Description: 该系统已存在的系统管理员</p>
	  * @param sysCode 
	  * @param request 
	  * @param response 
	  * @throws BizException  
	  */
	@RequestMapping("/querySysAdmin")
	public void querySysAdmin(String sysCode, 
			HttpServletRequest request, HttpServletResponse response) throws BizException {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("validInd", CommonUtil.VALID_IND_N); //只查有效数据
		paramMap.put("sysCode", sysCode);
		
		List<Map<String, Object>> sysAdmins;
		try {
			sysAdmins = sysAdminServer.queryAdminData(paramMap);
		} catch (Exception e) {
			log.error("查询系统管理员异常", e);
			throw new BizException("失效操作异常，传入参数为：" + paramMap, e);
		}
		ResponseUtil.sendJSON(response, sysAdmins);
	}
	
	/**
	  * <p>Title: addSysAdmin</p>
	  * <p>Description: </p>
	  * @param userCodes 
	  * @param sysCode 
	  * @param request 
	  * @param response  
	  */
	
	
	@RequestMapping("/addSysAdmin")
	public void addSysAdmin(String userCodes, String sysCode,
			HttpServletRequest request, HttpServletResponse response){
		
		String msg = "分配系统管理员成功";
		try {
			String currentUser = CurrentUser.getUser().getUsername();
			String[] codes = {};
			if(!StringUtils.isEmpty(userCodes)){
				codes = userCodes.split(",");
			}
			List<SysAdminVO> sysAdmins = new ArrayList<SysAdminVO>();
			for (String userCode : codes) {
				SysAdminVO sysAdminVO = new SysAdminVO();
				sysAdminVO.setPkUuid(UUIDGenerator.getUUID());
				sysAdminVO.setSysCode(sysCode);
				sysAdminVO.setUserCode(userCode);
				sysAdminVO.setValidInd(CommonUtil.VALID_IND_N);
				sysAdminVO.setAdminLevel(CommonUtil.LEVEL_ONE);
				sysAdminVO.setCreatedUser(currentUser);
				sysAdminVO.setUpdatedUser(currentUser);
				sysAdminVO.setCreatedDate(DateUtil.sqlDate());
				sysAdminVO.setUpdatedDate(DateUtil.sqlDate());
				sysAdmins.add(sysAdminVO);
			}
			
			//保存给系统分配的管理员
			sysAdminServer.addSysAdmin(sysAdmins, sysCode, currentUser);
			
		} catch (Exception e) {
			msg = "分配系统管理员失败";
			log.error("分配系统管理员失败", e);
			ResponseUtil.sendMessage(response, false, msg);
		}
		ResponseUtil.sendMessage(response, true, msg);
	}
	
	/**
	  * <p>Title: queryAdmin</p>
	  * <p>Description: queryAdmin</p>
	  * @param page 
	  * @param rows 
	  * @param sysAdminVO 
	  * @param request 
	  * @param response  
	  */
	@RequestMapping("/queryAdmin")
	public void queryAdmin(Integer page, Integer rows, SysAdminVO sysAdminVO,
			HttpServletRequest request, HttpServletResponse response){
		
		Map<String, Object> whereMap = new HashMap<String, Object>();
		
		PageVO pageVO = new PageVO();
		try {
			pageVO.setPageSize(rows);
			pageVO.setPageNo(page);

			whereMap.put("userCode", sysAdminVO.getUserCode());
			whereMap.put("sysCode", sysAdminVO.getSysCode());
			whereMap.put("userCname", sysAdminVO.getUserCname());
			whereMap.put("validInd", sysAdminVO.getValidInd());
			pageVO = sysAdminServer.queryAdmin(pageVO, whereMap);
		} catch (Exception e) {
			log.error("查询管理员失败", e);
		}
	    ResponseUtil.sendJSON(response, pageVO);
	}
	
}
