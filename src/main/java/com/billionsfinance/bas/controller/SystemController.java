package com.billionsfinance.bas.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.billionsfinance.als.security.CurrentUser;
import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.entity.SystemVO;
import com.billionsfinance.bas.server.ISystemServer;
import com.billionsfinance.bas.server.impl.SystemServer;
import com.billionsfinance.bas.util.CommonUtil;
import com.billionsfinance.bas.util.DateUtil;
import com.billionsfinance.bas.util.FileUtil;
import com.billionsfinance.bas.util.LogUtil;
import com.billionsfinance.bas.util.ResponseUtil;
import com.billionsfinance.common.Exception.BizException;

/**
 * @ClassName: SystemController
 * @Description: 菜单控制器
 * @author zhanghs 2016年7月19日 下午3:56:46 Copyright: Copyright (c) 2016
 *         Company:佰仟金融
 */
@Controller
@RequestMapping("/menuServer")
public class SystemController {
	/** 日志记录器 */
	private final Log logger = LogFactory.getLog(SystemController.class);

	// 获取系统管理接口
	private final ISystemServer systemServer = new SystemServer();

	/**
	 * <p>
	 * Title: toSystem
	 * </p>
	 * <p>
	 * Description: system
	 * </p>
	 * 
	 * @return system
	 */
	@RequestMapping("/toSystem")
	public String toSystem() {

		return "system/system";
	}

	/**
	 * <p>
	 * Title: saveSystem
	 * </p>
	 * <p>
	 * Description: saveSystem
	 * </p>
	 *  
	 * @param sourceFile 
	 * @param systemVO 
	 * @param request 
	 * @param response 
	 * @throws BizException 
	 */
	@RequestMapping("/saveSystem")
	public void saveSystem(@RequestParam("sourceFile") CommonsMultipartFile sourceFile, SystemVO systemVO,
			HttpServletRequest request, HttpServletResponse response) throws BizException {

		try {
			if (StringUtils.isEmpty(systemVO.getSysCode())) {
				ResponseUtil.sendMessage(response, false, "系统编码不能为空");
				return;
			}
			String currentUser = CurrentUser.getUser().getUsername();
			// 当前用户
			systemVO.setUpdatedUser(currentUser);
			systemVO.setCreatedUser(currentUser);
			// 这里使用WEB系统的系统时间
			systemVO.setCreatedDate(DateUtil.sqlDate());
			// 这里使用WEB系统的系统时间
			systemVO.setUpdatedDate(DateUtil.sqlDate());

			// 查询系统是否已经存在
			SystemVO sys = systemServer.findSysByCode(systemVO.getSysCode());
			if (sys != null && systemVO.getSysCode().equals(sys.getSysCode())) {
				ResponseUtil.sendMessage(response, false, "系统编码已存在");
				return;
			}
			// 校验用户名的唯一性
			Map<String, String> whereMap = new HashMap<String, String>();
			whereMap.put("sysCname", systemVO.getSysCname());
			List<SystemVO> systems = systemServer.findSysByWhere(whereMap);
			for (SystemVO systemVO2 : systems) {
				if (systemVO.getSysCname().equals(systemVO2.getSysCname())) {
					ResponseUtil.sendMessage(response, false, "系统名称已存在");
					return;
				}
			}
			// 校验英文名称不相同
			whereMap.clear();
			whereMap.put("sysEname", systemVO.getSysEname());
			List<SystemVO> systemsE = systemServer.findSysByWhere(whereMap);
			for (SystemVO systemVO2 : systemsE) {
				if (systemVO.getSysEname().equals(systemVO2.getSysEname())) {
					ResponseUtil.sendMessage(response, false, "系统英文名称已存在");
					return;
				}
			}

			String logourl = null;
			if (sourceFile != null && !sourceFile.isEmpty()) {
				// 上传文件并返回路径
				logourl = FileUtil.uploadFile(sourceFile, systemVO.getSysCode(), request);
			} else {
				logourl = FileUtil.uploadLogo(systemVO.getSysCode(), request);
			}
			systemVO.setSysLogo(logourl);
			systemServer.saveSystem(systemVO);

		} catch (Exception e) {
			logger.error("保存数据异常，数据信息为：" + JSONObject.toJSONString(systemVO), e);
			ResponseUtil.sendMessage(response, false, "新增系统失败");
			throw new BizException("保存数据异常，数据信息为：" + JSONObject.toJSONString(systemVO), e);
		}
		ResponseUtil.sendMessage(response, true, "新增系统成功");
	}

	/**
	 * <p>
	 * Title: removeSystem
	 * </p>
	 * <p>
	 * Description: removeSystem
	 * </p>
	 * 
	 * @param sysCodes 
	 * @param request 
	 * @param response 
	 * @throws BizException 
	 */
	@RequestMapping("/removeSystem")
	public void removeSystem(String sysCodes, HttpServletRequest request, HttpServletResponse response)
			throws BizException {
		if (StringUtils.isEmpty(sysCodes)) {
			ResponseUtil.sendMessage(response, false, "系统编码不能为空");
			return;
		}
		String[] idArr = sysCodes.split(",");
		try {
			String currentUser = CurrentUser.getUser().getUsername();
			SystemVO systemVO = null;
			for (String sysCode : idArr) {
				systemVO = new SystemVO();
				// 根据code修改
				systemVO.setSysCode(sysCode);
				// 当前用户
				systemVO.setUpdatedUser(currentUser);
				systemVO.setValidInd(CommonUtil.VALID_IND_F);
				// 这里使用WEB系统的系统时间
				systemVO.setUpdatedDate(DateUtil.sqlDate());
				systemServer.updateSystem(systemVO);
			}
		} catch (Exception e) {
			logger.error("禁用系统异常,参数：" + sysCodes, e);
			ResponseUtil.sendMessage(response, false, "禁用系统失败");
			throw new BizException("禁用系统异常,参数：" + sysCodes, e);
		}
		ResponseUtil.sendMessage(response, true, "禁用成功");
	}

	/**
	 * <p>
	 * Title: openSystem
	 * </p>
	 * <p>
	 * Description: openSystem
	 * </p>
	 * 
	 * @param sysCodes 
	 * @param request 
	 * @param response 
	 * @throws BizException 
	 */
	@RequestMapping("/openSystem")
	public void openSystem(String sysCodes, HttpServletRequest request, HttpServletResponse response)
			throws BizException {
		if (StringUtils.isEmpty(sysCodes)) {
			ResponseUtil.sendMessage(response, false, "系统编码不能为空");
			return;
		}
		String[] idArr = sysCodes.split(",");
		try {
			String currentUser = CurrentUser.getUser().getUsername();
			SystemVO systemVO = null;
			for (String sysCode : idArr) {
				systemVO = new SystemVO();
				// 根据code修改
				systemVO.setSysCode(sysCode);
				// 当前用户
				systemVO.setUpdatedUser(currentUser);
				systemVO.setValidInd(CommonUtil.VALID_IND_N);
				// 这里使用WEB系统的系统时间
				systemVO.setUpdatedDate(DateUtil.sqlDate());
				systemServer.updateSystem(systemVO);
			}
		} catch (Exception e) {
			logger.error("启用系统操作异常,参数：" + sysCodes, e);
			ResponseUtil.sendMessage(response, false, "启用系统操作异常");
			throw new BizException("启用系统操作异常,参数：" + sysCodes, e);
		}
		ResponseUtil.sendMessage(response, true, "启用成功");
	}

	/**
	 * <p>
	 * Title: updateSystem
	 * </p>
	 * <p>
	 * Description: updateSystem
	 * </p>
	 * 
	 * @param sourceFile 
	 * @param systemVO 
	 * @param request 
	 * @param response 
	 * @throws BizException 
	 */
	@RequestMapping(value = "/updateSystem", method = RequestMethod.POST)
	public void updateSystem(@RequestParam("sourceFile") CommonsMultipartFile sourceFile, SystemVO systemVO,
			HttpServletRequest request, HttpServletResponse response) throws BizException {
		try {
			if (StringUtils.isEmpty(systemVO.getSysCode())) {
				ResponseUtil.sendMessage(response, false, "系统编码不能为空");
				return;
			}

			// 校验用户名的唯一性
			Map<String, String> whereMap = new HashMap<String, String>();
			whereMap.put("sysCname", systemVO.getSysCname());
			List<SystemVO> systems = systemServer.findSysByWhere(whereMap);
			for (SystemVO systemVO2 : systems) {
				if (systemVO.getSysCname().equals(systemVO2.getSysCname())
						&& !systemVO.getSysCode().equals(systemVO2.getSysCode())) {
					ResponseUtil.sendMessage(response, false, "系统名称已存在");
					return;
				}
			}
			// 校验英文名称不相同
			whereMap.clear();
			whereMap.put("sysEname", systemVO.getSysEname());
			List<SystemVO> systemsE = systemServer.findSysByWhere(whereMap);
			for (SystemVO systemVO2 : systemsE) {
				if (systemVO.getSysEname().equals(systemVO2.getSysEname())
						&& !systemVO.getSysCode().equals(systemVO2.getSysCode())) {
					ResponseUtil.sendMessage(response, false, "系统英文名称已存在");
					return;
				}
			}

			String currentUser = CurrentUser.getUser().getUsername();
			systemVO.setUpdatedUser(currentUser);
			// 这里使用WEB系统的系统时间
			systemVO.setUpdatedDate(DateUtil.sqlDate());
			String logourl = null;
			if (sourceFile != null && !sourceFile.isEmpty()) {
				// 上传文件并返回路径
				logourl = FileUtil.uploadFile(sourceFile, systemVO.getSysCode(), request);
			}
			systemVO.setSysLogo(logourl);
			systemServer.updateSystem(systemVO);
		} catch (Exception e) {
			ResponseUtil.sendMessage(response, false, "修改失败");
			throw new BizException("更新系统信息出错，数据参数为：" + JSONObject.toJSONString(systemVO), e);
		}
		ResponseUtil.sendMessage(response, true, "修改成功");
	}

	/**
	 * <p>
	 * Title: querySystem
	 * </p>
	 * <p>
	 * Description: querySystem
	 * </p>
	 * 
	 * @param page 
	 * @param rows 
	 * @param systemVO 
	 * @param request 
	 * @param response 
	 * @throws BizException 
	 */
	@RequestMapping("/querySysByWhere")
	public void querySystem(Integer page, Integer rows, SystemVO systemVO, HttpServletRequest request,
			HttpServletResponse response) throws BizException {
		PageVO pageVO = new PageVO();
		try {
			String currentUser = CurrentUser.getUser().getUsername();
			pageVO.setPageSize(rows);
			pageVO.setPageNo(page);
			Map<String, Object> whereMap = new HashMap<String, Object>();
			whereMap.put("currentUser", currentUser);
			// if(systemVO != null){
			// whereMap.put("sysCname", systemVO.getSysCname());
			// whereMap.put("validInd", systemVO.getValidInd());
			// whereMap.put("sysTypeCode", systemVO.getSysTypeCode());
			// whereMap.put("sysCode", systemVO.getSysCode());
			// }

			pageVO = systemServer.findSystemByWhere(pageVO, whereMap);

		} catch (Exception e) {
			logger.error("条件查询出现异常，数据参数为：", e);
			throw new BizException("条件查询出现异常，数据参数为：", e);
		}
		ResponseUtil.sendJSON(response, pageVO);
	}

	/**
	  * <p>Title: findSysByCode</p>
	  * <p>Description: findSysByCode</p>
	  * @param sysCode 
	  * @param request 
	  * @param response 
	  * @throws BizException  
	  */
	@RequestMapping("/findSysByCode")
	public void findSysByCode(String sysCode, HttpServletRequest request, HttpServletResponse response)
			throws BizException {
		try {
			SystemVO system = systemServer.findSysByCode(sysCode);
			ResponseUtil.sendJSON(response, system);
		} catch (Exception e) {
			LogUtil.ERROR.error("查询系统异常，数据参数为：", e);
			throw new BizException("查询 系统信息出错，参数为：" + sysCode, e);
		}

	}

	/**
	  * <p>Title: initSystem</p>
	  * <p>Description: initSystem</p>
	  * @param needNull 
	  * @param request 
	  * @param response 
	  * @throws BizException  
	  */
	@RequestMapping("/initSystem")
	public void initSystem(boolean needNull, HttpServletRequest request, HttpServletResponse response)
			throws BizException {
		try {
			String currentUser = CurrentUser.getUser().getUsername();
			List<SystemVO> systems = systemServer.initSystem(currentUser);

			if (systems != null && needNull) {
				SystemVO sysVo = new SystemVO();
				sysVo.setSysCname("全部");
				sysVo.setSysCode("");
				systems.add(0, sysVo);
			}
			ResponseUtil.sendJSON(response, systems);
		} catch (Exception e) {
			LogUtil.ERROR.error("查询 系统信息出错，", e);
			throw new BizException("查询 系统信息出错，", e);
		}

	}

	/**
	  * <p>Title: queryAllSysCode</p>
	  * <p>Description: queryAllSysCode</p>
	  * @param needNull 
	  * @param request 
	  * @param response 
	  * @throws BizException  
	  */
	@RequestMapping("/queryAllSysCode")
	public void queryAllSysCode(boolean needNull, HttpServletRequest request, HttpServletResponse response)
			throws BizException {
		try {
			List<SystemVO> systems = systemServer.queryAllSysCode();

			if (systems != null && needNull) {
				SystemVO sysVo = new SystemVO();
				sysVo.setSysCname("全部");
				sysVo.setSysCode("");
				systems.add(0, sysVo);
			}
			ResponseUtil.sendJSON(response, systems);
		} catch (Exception e) {
			LogUtil.ERROR.error("查询所有系统异常", e);
			throw new BizException("查询所有系统异常", e);
		}

	}

	/**
	  * <p>Title: initOldSystem</p>
	  * <p>Description: initOldSystem</p>
	  * @param request 
	  * @param response 
	  * @throws BizException  
	  */
	@RequestMapping("/initOldSystem")
	public void initOldSystem(HttpServletRequest request, HttpServletResponse response) throws BizException {
		try {
			String currentUser = CurrentUser.getUser().getUsername();
			List<SystemVO> systems = systemServer.initOldSystem(currentUser);

			if (systems != null) {
				SystemVO sysVo = new SystemVO();
				sysVo.setSysCname("全部");
				sysVo.setSysCode("");
				systems.add(0, sysVo);
			}
			ResponseUtil.sendJSON(response, systems);
		} catch (Exception e) {
			LogUtil.ERROR.error("查询 需要绑定用户的系统信息出错，", e);
		}

	}

	/**
	  * <p>Title: 返回用户可以操作的系统</p>
	  * <p>Description: findSystem</p>
	  * @param request 
	  * @param model 
	  * @return string
	  */
	@RequestMapping("findSystem")
	public String findSystemByUser(HttpServletRequest request, ModelMap model) {
//		try {
//			// String user = CurrentUser.getUser().getUsername();
//			String user = CurrentUser.getUser().getUsername();
//			if (user == null) {
//				logger.error("用户为空");
//				return null;
//			}
//			if (StringUtils.isEmpty(user)) {
//				logger.error("用户为空");
//				return null;
//			}
//			Map<String, String> whereMap = new HashMap<String, String>();
//			// 根据当前用户获取用户可以操作的有效系统
//			whereMap.put("currentUser", user);
//			whereMap.put("sysCode", CommonUtil.ALS);
//			List<SystemVO> systems = systemServer.findSystemByUser(whereMap);
//			// 图片服务地址
//			String imagesUrl = GlobalParam.getParam(FileUtil.IMAGES_URL);
//			model.addAttribute("systems", systems);
//			model.addAttribute("imagesUrl", imagesUrl);
//
//		} catch (Exception e) {
//			logger.error("获取图片路径失败！");
//		}
		return "common/userSys";
	}

	/**
	  * <p>Title: checkUnique</p>
	  * <p>Description: checkUnique</p>
	  * @param request 
	  * @param response 
	  * @throws BizException  
	  */ 
	@RequestMapping("/checkUnique")
	public void checkUnique(HttpServletRequest request, HttpServletResponse response) throws BizException {
		try {
			String sysCode = request.getParameter("sysCode");
			String sysEname = request.getParameter("sysEname");
			String sysCname = request.getParameter("sysCname");

			Map<String, String> whereMap = new HashMap<String, String>();
			// 校验唯一性，如果系统编码不为空，就校验系统编码，系统名称不为空就校验系统名称
			if (!StringUtils.isEmpty(sysCode)) {
				whereMap.put("sysCode", sysCode);
			}
			if (!StringUtils.isEmpty(sysEname)) {
				whereMap.put("sysEname", sysEname);
			}
			if (!StringUtils.isEmpty(sysCname)) {
				whereMap.put("sysCname", sysCname);
			}

			List<SystemVO> systems = systemServer.findSysByWhere(whereMap);
			boolean unique = true;

			// 校验编码唯一
			if (!StringUtils.isEmpty(sysCode)) {
				for (SystemVO systemVO : systems) {
					if (sysCode.equals(systemVO.getSysCode())) {
						unique = false;
						break;
					}
				}
			}
			// 校验英文名唯一
			if (!StringUtils.isEmpty(sysEname)) {
				for (SystemVO systemVO : systems) {
					if (sysEname.equals(systemVO.getSysEname())) {
						unique = false;
						break;
					}
				}
			}
			// 校验中文名称唯一
			if (!StringUtils.isEmpty(sysCname)) {
				for (SystemVO systemVO : systems) {
					if (sysCname.equals(systemVO.getSysCname())) {
						unique = false;
						break;
					}
				}
			}

			Map<String, Object> responseMsg = new HashMap<String, Object>();
			// 返回成功或失败信息
			responseMsg.put("unique", unique);
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().print(JSONObject.toJSONString(responseMsg));

		} catch (Exception e) {
			LogUtil.ERROR.error("查询 需要绑定用户的系统信息出错，", e);
		}

	}

}
