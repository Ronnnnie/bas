package com.billionsfinance.bas.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.billionsfinance.bas.server.IResourceServer;
import com.billionsfinance.bas.server.impl.ResourceServer;
import com.billionsfinance.bas.util.LogUtil;
import com.billionsfinance.bas.util.ResponseUtil;
import com.billionsfinance.common.Exception.BizException;

/**
 * 获取子系统系统菜单权限
 * @author zhangfei
 *
 */
@Controller
@RequestMapping("/sysMain")
public class SysMainFrameController {
	
	/** 日志记录器 */
	private final Log logger = LogFactory.getLog(SysMainFrameController.class);

	/**
	  * <p>Title: getSysResource</p>
	  * <p>Description: getSysResource</p>
	  * @param request 
	  * @param response  
	  */
	@RequestMapping("/getSysResource")
	public void getSysResource(HttpServletRequest request, HttpServletResponse response)
	{
		   String syscode = request.getParameter("syscode");
		   String usercode = request.getParameter("usercode");
		   String role= request.getParameter("role");
		   String json ="";
		   if(syscode == null || "".equals(syscode)){
			   json = "{\"status\":\"false\",\"msg\":\" 系统编码(syscode)不能为空\"}";
			  ResponseUtil.sendJSON(response, json);
			  return; 
		   }
           if(usercode == null || "".equals(usercode)){
        	   json = "{\"status\":\"false\",\"msg\":\"用户账号(usercode) 不能为空\"}";
        	   ResponseUtil.sendJSON(response, json);
        	  return;
		   }
           
           if(role == null || "".equals(role)){
        	   json = "{\"status\":\"false\",\"msg\":\"用户角色(role) 不能为空\"}";
        	   ResponseUtil.sendJSON(response, json);
        	  return;
		   }
           
           logger.info("查询菜单[syscode="+syscode+",usercode="+usercode+",role="+role+"]");
            Map<String, Object> whereMap = new HashMap<String, Object>();
            role = "'"+role+"'";
            role = role.replace(",", "','");
   		    whereMap.put("role", role.split(","));
   		   
			List<Map<String, Object>> menuList;
			try {
				//系统用户有角色权限访问的菜单
				IResourceServer resourceServer = new ResourceServer();
				menuList = resourceServer.queryMenuResourceAllByRole(whereMap);
			} catch (Exception e) {
				LogUtil.ERROR.error("查询用户菜单出错", e);
				throw new BizException("查询用户菜单出错", e);
			}
			Map<String, Object> obj = new HashMap<String, Object>();
			obj.put("menuList", menuList);
		    ResponseUtil.sendJSON(response, obj);
	}
	
	/**
	 * 根据系统编码获取系统有效菜单
	 * @param request 
	 * @param response 
	 */
	@RequestMapping("/findRole")
	public void findRoleBySysCode(HttpServletRequest request, HttpServletResponse response){
		
			try {
				String sysCode = request.getParameter("sysCode");
				 if(sysCode == null || "".equals(sysCode)){
					   String json = "{\"status\":\"false\",\"msg\":\" 系统编码(syscode)不能为空\"}";
					  ResponseUtil.sendJSON(response, json);
					  LogUtil.ERROR.error("系统编码为空");
					  return; 
				   }
				//系统用户有角色权限访问的菜单
				IResourceServer resourceServer = new ResourceServer();
				List<Map<String, String>> roles = resourceServer.queryRoleBySysCode(sysCode);
				ResponseUtil.sendJSONList(response, roles);
			} catch (Exception e) {
				LogUtil.ERROR.error("根据系统查询角色异常", e);
				throw new BizException("根据系统查询角色异常", e);
			}
	}
	
	
}
