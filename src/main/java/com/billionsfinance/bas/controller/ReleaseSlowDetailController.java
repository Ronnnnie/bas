package com.billionsfinance.bas.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.billionsfinance.als.security.CurrentUser;
import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.entity.ReleaseSlowDetailVO;
import com.billionsfinance.bas.server.IReleaseSlowDetailServer;
import com.billionsfinance.bas.server.impl.ReleaseSlowDetailServer;
import com.billionsfinance.bas.util.AjaxJson;
import com.billionsfinance.bas.util.ResponseUtil;
import com.billionsfinance.bas.util.StringUtil;

/**
 * @ClassName: 还款明细Controller(信托回款明细)
 * @Description: RepaymentDetailController
 * @author zhouFM
 * @Copyright Copyright (c) 2016 2016年11月18日 上午15:05:04 Company:佰仟金融
 */
@Controller
@RequestMapping("/releaseSlowDetailServer")
public class ReleaseSlowDetailController {

	/** 日志记录 */
	private static final Log LOGGER = LogFactory.getLog(ReleaseSlowDetailController.class);

	private static final IReleaseSlowDetailServer releaseSlowDetailServer = new ReleaseSlowDetailServer();

	/**
	 * @methodName: toReceivedPaymentsDetail
	 * @Description: trustAllotDetail
	 * @return java.lang.String
	 */
	@RequestMapping("/toRepaymentDetail")
	public String toRepaymentDetail() {
		return "releaseSlowDetail/releaseSlowDetail";	
	}

	/**
	 * 明细合同查询
	 * @param ReleaseSlowDetailVO
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 */
	@RequestMapping("/queryRepaymentDetail")
	public void queryRepaymentDetail(Integer page, Integer rows, ReleaseSlowDetailVO vo,HttpServletRequest request, HttpServletResponse response) {
		System.out.println(vo);
		PageVO pageVO = null;
		try {
			pageVO = new PageVO();
			pageVO.setPageSize(rows);
			pageVO.setPageNo(page);
			pageVO = releaseSlowDetailServer.queryRepaymentDetail(pageVO, vo);
		} catch (Exception e) {
			LOGGER.error("查询还款差额划拨-还款明细-核算失败!", e);
		}finally {
			ResponseUtil.sendJSON(response, pageVO);
		}
	}
	
	/**
	 * @methodName 记账确认
	 * @param ReleaseSlowDetailVO
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 */
	@ResponseBody
	@RequestMapping("/accountingMark")
	public AjaxJson accountingMark(ReleaseSlowDetailVO vo,HttpServletRequest request, HttpServletResponse response/*,BQRefundVO vo */) {
		Double businessSumCount = new Double(0);
		AjaxJson ajaxJson = null;
		PageVO pageVO = null;
		boolean flag = true;
		try {
			ajaxJson = new AjaxJson();
			pageVO = new PageVO();
			pageVO.setPageSize(10);
			pageVO.setPageNo(1);
			vo.setKeepAccountsBy(CurrentUser.getUser().getUsername());
			//记账确认
			int result = releaseSlowDetailServer.accountingMark(vo);
			if (result > 0 &&(!("").equals(vo.getStartKeepAccountsDate())&&vo.getStartKeepAccountsDate()!=null||
					!("").equals(vo.getEndKeepAccountsDate())&&vo.getEndKeepAccountsDate()!=null)) {
				vo.setStartKeepAccountsDate(vo.getUpdateDate());
				vo.setEndKeepAccountsDate(vo.getUpdateDate());
			}
			//修改合同查询
			pageVO = releaseSlowDetailServer.queryRepaymentDetail(pageVO, vo);
			ajaxJson.setObj(pageVO);
			ajaxJson.setSuccess(true);
			if (pageVO.getTotal()>0) {
				for (Map<String,Object> map: pageVO.getRows()) {
					//String payStatus = StringUtil.isNullOrEmpty(map.get("payStatus"));
					businessSumCount += Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("businessSumCount")));
					/*if (("0").equals(payStatus) || payStatus == "0"||payStatus == null || ("").equals(payStatus)) {
						flag = false;
					}*/
				}
				pageVO.getRows().get(0).put("businessSumCount", "businessSumCount");
				if (flag) {
					ajaxJson.setMsg("记账确认标记成功!");
				}else{
					ajaxJson.setMsg("当前匹配合同存在非支付状态的合同，该些合同记账失败!");
				}
			}else{
				ajaxJson.setMsg("暂无匹配数据可修改!");
			}
			
		} catch (Exception e) {
			ajaxJson.setMsg("系统异常,记账确认标记失败!");
			ajaxJson.setObj(pageVO);
			ajaxJson.setSuccess(false);
			LOGGER.error("记账确认异常", e);
		}
		return ajaxJson;
	}
	
	/**
	 * @methodName 记账确认
	 * @param ReleaseSlowDetailVO
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 */
	@ResponseBody
	@RequestMapping("/selectAccountingMark")
	public AjaxJson selectAccountingMark(ReleaseSlowDetailVO vo,HttpServletRequest request, HttpServletResponse response/*,BQRefundVO vo */) {
		Double businessSumCount = new Double(0);
		AjaxJson ajaxJson = null;
		PageVO pageVO = null;
		boolean flag = true;
		List<Map<String,Object>> list = null;
		try {
			ajaxJson = new AjaxJson();
			pageVO = new PageVO();
			list = new ArrayList<Map<String,Object>>();
			pageVO.setPageSize(10);
			pageVO.setPageNo(1);
			vo.setKeepAccountsBy(CurrentUser.getUser().getUsername());
			vo = changeVO(vo);
			if(vo.getSerialNoArray()!=null&&vo.getSerialNoArray().length>0){
				for (int i = 0; i < vo.getSerialNoArray().length; i++) {
					vo.setSerialNo(vo.getSerialNoArray()[i]);
					releaseSlowDetailServer.accountingMark(vo);
					list.add(releaseSlowDetailServer.queryRepaymentDetailFindAll(vo).get(0));
				}
			}else{
				releaseSlowDetailServer.accountingMark(vo);
				list=releaseSlowDetailServer.queryRepaymentDetailFindAll(vo);
			}
			double moneyCount = 0;
			if (list.size()>0) {
				for (Map<String,Object> map: list) {
					//String payStatus = StringUtil.isNullOrEmpty(map.get("payStatus"));
					moneyCount+=Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("moneyCount")));
					businessSumCount += Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("businessSum")));
					/*if (("0").equals(payStatus) || payStatus == "0"||payStatus == null || ("").equals(payStatus)) {
						flag = false;
					}*/
				}
				if (flag) {
					ajaxJson.setMsg("记账确认标记成功!");
				}else{
					ajaxJson.setMsg("当前匹配合同存在非支付状态的合同，该些合同记账失败!");
				}
				list.get(0).put("businessSumCount", businessSumCount);
				pageVO.setRows(list);
				pageVO.setTotal((long)list.size());
				pageVO.getRows().get(0).put("contractCount",pageVO.getTotal());
				pageVO.getRows().get(0).put("moneyCount",moneyCount);
				ajaxJson.setObj(pageVO);
				ajaxJson.setSuccess(true);
			}else{
				ajaxJson.setMsg("暂无匹配数据可修改!");
			}
		} catch (Exception e) {
			ajaxJson.setMsg("系统异常,记账确认标记失败!");
			ajaxJson.setObj(pageVO);
			ajaxJson.setSuccess(false);
			LOGGER.error("记账确认异常", e);
		}
		return ajaxJson;
	}
	
	/**
	 * @methodName 记账撤销
	 * @param RepaymentDetailVO
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 */
	@ResponseBody
	@RequestMapping("/cancelAccountingMark")
	public AjaxJson cancelAccountingMark(ReleaseSlowDetailVO vo,HttpServletRequest request, HttpServletResponse response/*,BQRefundVO vo */) {
		Boolean roleFlag = (Boolean) request.getSession().getAttribute("isRevokeRole");
		if(roleFlag==null||!roleFlag){
			ResponseUtil.sendMessage(response, false, "暂无权限操作此功能!");
			return null;
		}
		Double businessSumCount = new Double(0);
		AjaxJson ajaxJson = null;
		PageVO pageVO = null;
		boolean flag = true;
		List<Map<String,Object>> list = null;
		List<Map<String,Object>> list2 = null;
		try {
			ajaxJson = new AjaxJson();
			pageVO = new PageVO();
			list = new ArrayList<Map<String,Object>>();
			list2 = new ArrayList<Map<String,Object>>();
			pageVO.setPageSize(10);
			pageVO.setPageNo(1);
			vo = changeVO(vo);
			if(vo.getSerialNoArray()!=null&&vo.getSerialNoArray().length>0){
				for (int i = 0; i < vo.getSerialNoArray().length; i++) {
					vo.setSerialNo(vo.getSerialNoArray()[i]);
					list2.add(releaseSlowDetailServer.queryRepaymentDetailFindAll(vo).get(0));
					releaseSlowDetailServer.cancelAccountingMark(vo);
					list.add(releaseSlowDetailServer.queryRepaymentDetailFindAll(vo).get(0));
				}
			}else{
				list2=releaseSlowDetailServer.queryRepaymentDetailFindAll(vo);
				releaseSlowDetailServer.cancelAccountingMark(vo);
				list=releaseSlowDetailServer.queryRepaymentDetailFindAll(vo);
			}
			if (list2.size()>0) {
				for (Map<String,Object> map: list2) {
					businessSumCount += Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("businessSum")));
					String keepAccountsStatus = StringUtil.isNullOrEmpty(map.get("keepAccountsStatus"));
					if (!("1").equals(keepAccountsStatus)) {
						flag = false;
					}
				}
				if (flag) {
					ajaxJson.setMsg("记账撤销成功!");
				}else{
					ajaxJson.setMsg("当前匹配合同存在未记账/已撤销记账的合同，该些合同记账撤销失败!");
				}
				pageVO.setRows(list);
				pageVO.getRows().get(0).put("businessSumCount", businessSumCount);
				ajaxJson.setObj(pageVO);
			}else{
				ajaxJson.setMsg("暂无匹配数据可撤销记账!");
			}
			ajaxJson.setSuccess(true);
		} catch (Exception e) {
			ajaxJson.setMsg("系统异常,记账撤销失败!");
			ajaxJson.setObj(pageVO);
			ajaxJson.setSuccess(false);
			LOGGER.error("记账撤销异常", e);
		}
		return ajaxJson;
	}
	
	/**
	 * 匹配合同数统计
	 * @param vo
	 * @param request
	 * @param response
	 */
	@RequestMapping("queryRepaymentDetailCount")
	public void queryRepaymentDetailCount(ReleaseSlowDetailVO vo,HttpServletRequest request, HttpServletResponse response){
		//统计合同总数
		Map<String,Object> map = releaseSlowDetailServer.queryRepaymentDetailCount(vo);
		ResponseUtil.sendJSON(response, map);
	}
	
	@RequestMapping("queryContractStatus")
	public void queryContractStatus(ReleaseSlowDetailVO vo,HttpServletRequest request, HttpServletResponse response){
		//统计合同总数
		try {
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			vo = changeVO(vo);
			//修改合同查询
			if(vo.getSerialNoArray()!=null&&vo.getSerialNoArray().length>0){
				for (int i = 0; i < vo.getSerialNoArray().length; i++) {
					vo.setSerialNo(vo.getSerialNoArray()[i]);
					list.add(releaseSlowDetailServer.queryRepaymentDetailFindAll(vo).get(0));
				}
			}else{
				list=releaseSlowDetailServer.queryRepaymentDetailFindAll(vo);
			}
			for(Map<String,Object> map:list){
				String keepAccountsStatus = StringUtil.isNullOrEmpty(map.get("keepAccountsStatus"));
				if(keepAccountsStatus.equals("1")){
					ResponseUtil.sendString(response,keepAccountsStatus);
					return;
				}
			}
			ResponseUtil.sendString(response,"");
		} catch (Exception e) {
			LOGGER.error("查询匹配合同状态异常!", e);
		}
	}
	
	public ReleaseSlowDetailVO changeVO(ReleaseSlowDetailVO vo){
		/**
		 * contains : true  记账多个合同
		 * contains : false 记账单个合同
		 */
		if(StringUtil.isNullOrEmpty(vo.getSerialNo()).contains(",")){
			vo.setSerialNoArray(vo.getSerialNo().split(","));
		}
		return vo;
	}
	
}
