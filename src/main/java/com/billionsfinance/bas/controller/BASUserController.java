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

import com.billionsfinance.bas.util.CommonUtil;
import com.billionsfinance.bas.util.DateUtil;
import com.billionsfinance.bas.util.UUIDGenerator;
import com.billionsfinance.als.security.CurrentUser;
import com.billionsfinance.bas.entity.BASUserVO;
import com.billionsfinance.bas.entity.DepartVO;
import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.entity.SysUserRoleVO;
import com.billionsfinance.bas.server.IBASUserServer;
import com.billionsfinance.bas.server.impl.BASUserServer;
import com.billionsfinance.bas.util.Globals;
import com.billionsfinance.bas.util.MD5Util;
import com.billionsfinance.bas.util.ResponseUtil;
import com.billionsfinance.bas.util.TreeVO;
import com.billionsfinance.common.Exception.BizException;

/**
 * @ClassName: BASUserController
 * @Description: 用户控制器
 * @author zhanghs
 * 
 *         Copyright: Copyright (c) 2016 2016年7月19日 下午3:04:09 Company:佰仟金融
 */
@Controller
@RequestMapping("/userServer")
public class BASUserController {

	/** 日志记录�?? */
	private static final Log LOGGER = LogFactory.getLog(BASUserController.class);
	// private static final Log LOG =
	// LogFactory.getLog(BASUserController.class);
	// 获取用户管理接口
	private static final IBASUserServer basUserServer = new BASUserServer();

	/**
	 * <p>
	 * Title: toSysUser
	 * </p>
	 * <p>
	 * Description: 跳转到用户页面
	 * 
	 * @return 跳转到用户页面
	 */
	@RequestMapping("/toSysUser")
	public String toSysUser() {

		return "sysUser/sysUser";
	}

	/**
	 * <p>
	 * Title: initDepart
	 * </p>
	 * <p>
	 * Description: 初始化部门
	 * 
	 * @param request
	 *            request
	 * @param response
	 *            response
	 */
	@RequestMapping("/initDepart")
	public void initDepart(HttpServletRequest request, HttpServletResponse response) {
		List<DepartVO> departList = basUserServer.loadDepart();
		List<TreeVO> departTree = getDeptTree(departList);
		ResponseUtil.sendJSONList(response, departTree);
	}

	/**
	  * <p>Title: queryAllDepart</p>
	  * <p>Description: 查询所有部门信息</p>
	  * @param request request
	  * @param response  response
	  */
	@RequestMapping("/queryAllDepart")
	public void queryAllDepart(HttpServletRequest request, HttpServletResponse response) {
		List<Map<String, Object>> allDepart = basUserServer.queryAllDepart();
		ResponseUtil.sendJSONList(response, allDepart);
	}

	/**
	 * 封装部门机构
	 * 
	 * @param departList departList
	 * @return 封装好的树对象 
	 * @throws BizException 异常
	 */
	private List<TreeVO> getDeptTree(List<DepartVO> departList) throws BizException {
		Map<String, List<TreeVO>> subTree = new HashMap<String, List<TreeVO>>();
		List<TreeVO> allTree = new ArrayList<TreeVO>();
		TreeVO tree = null;
		for (DepartVO departVO : departList) {
			tree = new TreeVO();
			String parentCode = departVO.getParentDepartId();
			tree.setId(departVO.getId());
			tree.setText(departVO.getDepartName());
			// 统计父分类对应的子分�??
			if (!StringUtils.isEmpty(parentCode)) {
				tree.setParent(parentCode);
				List<TreeVO> subList = subTree.get(parentCode);
				if (subList == null) {
					subList = new ArrayList<TreeVO>();
					subList.add(tree);
				} else {
					subList.add(tree);
				}
				subTree.put(parentCode, subList);
			}
			// �??
			allTree.add(tree);
		}
		List<TreeVO> showTree = new ArrayList<TreeVO>();
		// 遍历�??有的节点，将有子节点的添加进�??
		for (TreeVO tree2 : allTree) {
			List<TreeVO> children = subTree.get(tree2.getId());
			if (children != null && children.size() > 0) {
				tree2.setState("closed");
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
	 * 分页查询系统用户
	 * 
	 * @param page 页
	 * @param rows 行
	 * @param basUserVO 用户对象
	 * @param userCname 名字
	 * @param request 请求
	 * @param response 响应
	 */
	@RequestMapping("/querySysUser")
	public void querySysUserPage(Integer page, Integer rows, BASUserVO basUserVO, String userCname,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> whereMap = new HashMap<String, Object>();
		PageVO pageVO = new PageVO();
		Map<String, Object> depart = (Map<String, Object>) request.getSession().getAttribute(Globals.DEPART_CACHE);
		try {
			pageVO.setPageSize(rows);
			pageVO.setPageNo(page);
			String currentUser = CurrentUser.getUser().getUsername();
			whereMap.put("currentUser", currentUser);
			whereMap.put("userCname", userCname);
			whereMap.put("userCode", basUserVO.getUserCode());
			whereMap.put("status", basUserVO.getStatus());
			pageVO = basUserServer.querySysUserPage(whereMap, pageVO, depart);
		} catch (Exception e) {
			LOGGER.error("查询系统用户失败", e);
		}
		ResponseUtil.sendJSON(response, pageVO);
	}

	/**
	  * <p>Title: initQueryUser</p>
	  * <p>Description: 初始化查询用户</p>
	  * @param page 页
	  * @param rows 行
	  * @param basUser 对象
	  * @param request 请求
	  * @param response 响应
	  */
	@RequestMapping("/initQueryUser")
	public void initQueryUser(Integer page, Integer rows, BASUserVO basUser, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> whereMap = new HashMap<String, Object>();

		PageVO pageVO = new PageVO();
		try {
			pageVO.setPageSize(rows);
			pageVO.setPageNo(page);

			whereMap.put("userCode", basUser.getUserCode());
			whereMap.put("userCname", basUser.getUserCname());
			whereMap.put("status", basUser.getStatus());
//			String[] deptList = null;
//			List<String> deptIdList = new ArrayList<String>();
			// if(!StringUtils.isEmpty(basUser.getFhrorgunitid())){
			// deptList = basUser.getFhrorgunitid().split(",");
			// for (String deptId : deptList) {
			// deptIdList.add(deptId);
			// }
			// //因为Oracle数据的in �??大允�??1000 ,超过就会报错�?? �??以需要将集合拆分为多个集合进行处�??.
			// List<List<String>> deptIdLists = CommonUtil.splitList(deptIdList,
			// 1000);
			// whereMap.put("deptIdLists", deptIdLists);
			// }

			// 取request中的标记位�?�如果将Session中的deptList取出作为条件�??
			pageVO = basUserServer.initQueryUser(whereMap, pageVO);
		} catch (Exception e) {
			LOGGER.error("查询用户失败", e);
		}
		ResponseUtil.sendJSON(response, pageVO);
	}

	/**
	  * <p>Title: saveUser</p>
	  * <p>Description: 保存用户</p>
	  * @param basUserVO 对象
	  * @param request 请求
	  * @param response 响应
	  * @throws BizException 异常 
	  */
	@RequestMapping("/saveUser")
	public void saveUser(BASUserVO basUserVO, HttpServletRequest request, HttpServletResponse response)
			throws BizException {
		String roleCodes = request.getParameter("upRoleCode");
		// 根据用户编码查询数据
		BASUserVO findUser = basUserServer.findUserByCode(basUserVO.getUserCode());
		if (findUser != null) {
			ResponseUtil.sendMessage(response, false, "用户已存在");
			return;
		}
		String currentUser = CurrentUser.getUser().getUsername();
		try {
			String mD5Pwd = MD5Util.toMD5(basUserVO.getPwd());
			basUserVO.setPwd(mD5Pwd);
			basUserVO.setCreatedUser(currentUser);
			basUserVO.setCreatedDate(DateUtil.sqlDate());
			basUserVO.setUpdatedUser(currentUser);
			basUserVO.setUpdatedDate(DateUtil.sqlDate());
			basUserVO.setPwdUpdateTime(DateUtil.sqlDate());

			// 根据用户的角色信息，存入用户角色关系�??
			SysUserRoleVO sysUserRoleVO = new SysUserRoleVO();
			sysUserRoleVO.setUserRoleId(UUIDGenerator.getUUID());
			sysUserRoleVO.setUserCode(basUserVO.getUserCode());
			sysUserRoleVO.setCreatedUser(currentUser);
			sysUserRoleVO.setUpdatedUser(currentUser);
			sysUserRoleVO.setValidInd(CommonUtil.VALID_IND_N);
			if (!StringUtils.isEmpty(roleCodes)) {
				sysUserRoleVO.setRoleCode(roleCodes);
			} else {
				sysUserRoleVO.setRoleCode(null);
			}
			// 增加用户和角色关�??
			String userCode = basUserServer.saveUser(basUserVO, sysUserRoleVO);
			ResponseUtil.sendMessage(response, true, "新增用户成功,账号为：" + userCode);
		} catch (Exception e) {
			ResponseUtil.sendMessage(response, false, "新增用户失败");
			LOGGER.error("新增用户失败", e);
			throw new BizException("添加用户出现异常！用户代码为�??" + basUserVO.getUserCode(), e);
		}

	}

	/**
	  * <p>Title: updateSysRole</p>
	  * <p>Description: 更新用户</p>
	  * @param basUserVO 对象
	  * @param request 请求
	  * @param response  响应
	  */
	@RequestMapping("/updateSysUser")
	public void updateSysRole(BASUserVO basUserVO, HttpServletRequest request, HttpServletResponse response) {

		String msg = "用户信息修改成功";
		String roleCodes = request.getParameter("roleCode");
		try {
			String currentUser = CurrentUser.getUser().getUsername();
			basUserVO.setUpdatedUser(currentUser);
			if (basUserVO.getPwd() != null && !basUserVO.getPwd().equals("")) {
				String mD5Pwd = MD5Util.toMD5(basUserVO.getPwd());
				basUserVO.setPwd(mD5Pwd);
			}
			SysUserRoleVO sysUserRoleVO = new SysUserRoleVO();
			sysUserRoleVO.setUserRoleId(UUIDGenerator.getUUID());
			sysUserRoleVO.setUserCode(basUserVO.getUserCode());
			sysUserRoleVO.setUpdatedUser(currentUser);
			sysUserRoleVO.setValidInd(CommonUtil.VALID_IND_N);
			if (!StringUtils.isEmpty(roleCodes)) {
				sysUserRoleVO.setRoleCode(roleCodes);
			} else {
				sysUserRoleVO.setRoleCode(null);
			}
			Map<String,Object> departInfo = (Map<String, Object>) request.getSession().getAttribute(Globals.DEPART_CACHE);
			List<String> list = new ArrayList<String>();
			if(!StringUtils.isEmpty(basUserVO.getDeptNo())){
				String[] split = basUserVO.getDeptNo().split(",");
				for(int i=0; i<split.length; i++){
					String departName = (String) departInfo.get(split[i]);
					list.add(departName);
				}
			}
			request.getSession().setAttribute(Globals.CON_DEPT, list);
			basUserServer.updateUser(basUserVO, sysUserRoleVO);
		} catch (Exception e) {
			ResponseUtil.sendMessage(response, false, "修改系统用户失败");
			LOGGER.error("修改系统用户失败", e);
		}
		ResponseUtil.sendMessage(response, true, msg);
	}

	/**
	  * <p>Title: removeUser</p>
	  * <p>Description: 删除用户</p>
	  * @param userCodes 员工号
	  * @param request 请求
	  * @param response  响应
	  */
	@RequestMapping("/removeUser")
	public void removeUser(String userCodes, HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isEmpty(userCodes)) {
			ResponseUtil.sendMessage(response, false, "没有用户");
			return;
		}
		String[] codes = userCodes.split(",");
		try {
			String currentUser = CurrentUser.getUser().getUsername();
			BASUserVO basUserVO = null;
			for (String userCode : codes) {
				basUserVO = new BASUserVO();
				// 根据code修改
				basUserVO.setUserCode(userCode);
				// 当前用户
				basUserVO.setUpdatedUser(currentUser);
				basUserVO.setStatus(CommonUtil.VALID_IND_F);
				basUserServer.removeUser(basUserVO);
			}
			ResponseUtil.sendMessage(response, true, "禁用用户成功");
		} catch (Exception e) {
			ResponseUtil.sendMessage(response, false, "禁用用户失败");
			LOGGER.error("禁用用户异常", e);
		}
	}

	/**
	  * <p>Title: openUser</p>
	  * <p>Description: 打开用户</p>
	  * @param userCodes 员工号
	  * @param request 
	  * @param response 
	  */
	@RequestMapping("/openUser")
	public void openUser(String userCodes, HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isEmpty(userCodes)) {
			ResponseUtil.sendMessage(response, false, "没有用户");
			return;
		}

		String[] codes = userCodes.split(",");
		try {
			BASUserVO basUserVO = null;
			String currentUser = CurrentUser.getUser().getUsername();
			for (String userCode : codes) {
				basUserVO = new BASUserVO();
				// 根据code修改
				basUserVO.setUserCode(userCode);
				// 当前用户
				basUserVO.setUpdatedUser(currentUser);
				basUserVO.setStatus(CommonUtil.VALID_IND_N);
				basUserServer.openUser(basUserVO);
			}
			ResponseUtil.sendMessage(response, true, "用户启用成功");
		} catch (Exception e) {
			ResponseUtil.sendMessage(response, false, "用户启用失败");
			LOGGER.error("用户启用失败", e);
		}
	}

	/**
	  * <p>Title: deleteUser</p>
	  * <p>Description: 删除用户</p>
	  * @param userCode 员工号
	  * @param request
	  * @param response 
	  */
	@RequestMapping("/deleteUser")
	public void deleteUser(String userCode, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("userCode", userCode);
		try {
			basUserServer.deleteUser(userCode);
			basUserServer.deleteRoleRe(whereMap);
		} catch (Exception e) {
			ResponseUtil.sendMessage(response, false, "删除失败");
		}
		ResponseUtil.sendMessage(response, true, "删除成功");
	}

	/**
	  * <p>Title: findUserByCode</p>
	  * <p>Description: 根据工号查找用户</p>
	  * @param userCode 员工号
	  * @param request 
	  * @param response  
	  */
	@RequestMapping("/findUserByCode")
	public void findUserByCode(String userCode, HttpServletRequest request, HttpServletResponse response) {

		BASUserVO user = basUserServer.findUserByCode(userCode);
		// 还需要获取部门信�??

		ResponseUtil.sendJSON(response, user);
	}

	/**
	  * <p>Title: updatePwd</p>
	  * <p>Description: 重设密码</p>
	  * @param userCode 员工号
	  * @param request  
	  * @param response  
	  */
	@RequestMapping("/updatePwd")
	public void updatePwd(String userCode, HttpServletRequest request, HttpServletResponse response) {
 
		try {
			String currentUser = CurrentUser.getUser().getUsername();
			// 根据用户查出身份证后六位的密�??
			// 根据用户编码查询数据
			BASUserVO findUser = basUserServer.findUserByCode(userCode);
			if (findUser == null) {
				ResponseUtil.sendMessage(response, false, "用户不存�??");
				return;
			}

			BASUserVO basUserVO = new BASUserVO();
			basUserVO.setUserCode(userCode);
			basUserVO.setUpdatedUser(currentUser);
			// 将身份证后六位的密文设置为密�??
			// basUserVO.setPwd(findUser.getFidcardno());
			basUserServer.updatePwd(basUserVO);
			ResponseUtil.sendMessage(response, true, "重置密码成功,密码为用户身份证后六位数");
		} catch (Exception e) {
			ResponseUtil.sendMessage(response, false, "重置密码失败");
			LOGGER.error("重置密码失败", e);
		}
	}
}
