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
import com.billionsfinance.bas.entity.RansomDetailVO;
import com.billionsfinance.bas.server.IRansomDetailServer;
import com.billionsfinance.bas.server.impl.RansomDetailServer;
import com.billionsfinance.bas.util.AjaxJson;
import com.billionsfinance.bas.util.DateUtil;
import com.billionsfinance.bas.util.ResponseUtil;
import com.billionsfinance.bas.util.StringUtil;

/**
 * @ClassName: 赎回明细Controller
 * @Description: 赎回明细-资金&核算
 * @author zhouFM
 * @Copyright Copyright (c) 2016 2016年11月18日 上午15:05:04 Company:佰仟金融
 */
@Controller
@RequestMapping("/ransomServer")
public class RansomDetailController {

	/** 日志记录 */
	private static final Log LOGGER = LogFactory.getLog(RansomDetailController.class);

	private static final IRansomDetailServer ransomDetailServer = new RansomDetailServer();

	/**
	 * @methodName: toRansomDetail
	 * @Description: 跳转赎回明细
	 * @return java.lang.String
	 */
	@RequestMapping("/toRansomDetail")
	public String toRansomDetail() {
		return "ransom/ransomDetail";	
	}

	/**
	 * 赎回明细查询
	 * @param RepaymentDetailVO
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 */
	@RequestMapping("/queryRepaymentDetail")
	public void queryRepaymentDetail(Integer page, Integer rows, RansomDetailVO vo,HttpServletRequest request, HttpServletResponse response) {
		
		PageVO pageVO = null;
		try {
			pageVO = new PageVO();
			pageVO.setPageSize(rows);
			pageVO.setPageNo(page);
			pageVO = ransomDetailServer.queryRepaymentDetail(pageVO, vo);
		} catch (Exception e) {
			LOGGER.error("查询信托划拨明细失败!", e);
		}finally {
			ResponseUtil.sendJSON(response, pageVO);
		}
	}
	
	/**
	 * @methodName 记账确认
	 * @param RepaymentDetailVO
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 */
	@ResponseBody
	@RequestMapping("/accountingMark")
	public AjaxJson accountingMark(RansomDetailVO vo,HttpServletRequest request, HttpServletResponse response/*,BQRefundVO vo */) {
		AjaxJson ajaxJson = null;
		Double moneyCount = null;
		PageVO pageVO = null;
		try {
			ajaxJson = new AjaxJson();
			moneyCount = new Double(0);
			pageVO = new PageVO();
			pageVO.setPageSize(10);
			pageVO.setPageNo(1);
			vo.setKeepaccountsBy(CurrentUser.getUser().getUsername());
			//记账确认
			int resultLine = ransomDetailServer.accountingMark(vo);
			if (resultLine > 0) {
				vo.setStartKeepaccountsDate(vo.getUpdateDate());
				vo.setEndKeepaccountsDate(vo.getUpdateDate());
			}
			//修改合同查询
			pageVO = ransomDetailServer.queryRepaymentDetail(pageVO, vo);
			ajaxJson.setObj(pageVO);
			ajaxJson.setSuccess(true);
			boolean flag = true;
			if (pageVO.getTotal()>0) {
				for (Map<String,Object> map: pageVO.getRows()) {
					String payStatus = StringUtil.isNullOrEmpty(map.get("payStatus"));
					if (("0").equals(payStatus) || payStatus == "0"||payStatus==null||("").equals(payStatus)) {
						flag = false;
					}
					moneyCount+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("principalRemainingSum"))));
				}
				if (flag) {
					ajaxJson.setMsg("记账确认标记成功!");
				}else{
					ajaxJson.setMsg("当前匹配合同存在非支付状态的合同，该些合同记账失败!");
				}
				pageVO.getRows().get(0).put("moneyCount",moneyCount);
				pageVO.getRows().get(0).put("contractCount",pageVO.getTotal());
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
	 * @param RepaymentDetailVO
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 */
	@ResponseBody
	@RequestMapping("/selectAccountingMark")
	public AjaxJson selectAccountingMark(RansomDetailVO vo,HttpServletRequest request, HttpServletResponse response/*,BQRefundVO vo */) {
		AjaxJson ajaxJson = null;
		Double moneyCount = null;
		Double principalRemainingSumCount = new Double(0);
		Double overDuePrincipalSum = new Double(0);
		Double overDueInterestSum = new Double(0);
		Double totalPremiumSum = new Double(0);
		Double premiumSum = new Double(0);
		Double atoneForAmountSum = new Double(0);
		PageVO pageVO = null;
		List<Map<String,Object>> list = null;
		try {
			vo = changeRansomDetailVO(vo);
			ajaxJson = new AjaxJson();
			moneyCount = new Double(0);
			pageVO = new PageVO();
			list = new ArrayList<Map<String,Object>>();
			pageVO.setPageSize(10);
			pageVO.setPageNo(1);
			vo.setKeepaccountsBy(CurrentUser.getUser().getUsername());
			if(vo.getContractNoArray()!=null&& vo.getContractNoArray().length>0){
				for (int i = 0; i < vo.getContractNoArray().length; i++) {
					vo.setContractNo(vo.getContractNoArray()[i]);
					vo.setSeqId(vo.getSeqIdArray()[i]);
					ransomDetailServer.accountingMark(vo);
					list.add(ransomDetailServer.queryRepaymentDetailFindAll(vo).get(0));
				}
			}else{
				ransomDetailServer.accountingMark(vo);
				list=ransomDetailServer.queryRepaymentDetailFindAll(vo);
			}
			boolean flag = true;
			if (list.size()>0) {
				for (Map<String,Object> map: list) {
					String payStatus = StringUtil.isNullOrEmpty(map.get("payStatus"));
					if (("0").equals(payStatus) || payStatus == "0"||payStatus==null||("").equals(payStatus)) {
						flag = false;
					}
					moneyCount+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("principalRemainingSum"))));
					principalRemainingSumCount+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("principalRemainingSum"))));
					overDuePrincipalSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("overDuePrincipal"))));
					overDueInterestSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("overDueInterest"))));
					totalPremiumSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("totalPremium"))));
					premiumSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("premium"))));
					atoneForAmountSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("atoneForAmount"))));
				}
				if (flag) {
					ajaxJson.setMsg("记账确认标记成功!");
				}else{
					ajaxJson.setMsg("当前匹配合同存在非支付状态的合同，该些合同记账失败!");
				}
				pageVO.setRows(list);
				pageVO.setTotal((long)list.size());
				pageVO.getRows().get(0).put("moneyCount",moneyCount);
				pageVO.getRows().get(0).put("contractCount",list.size());
				pageVO.getRows().get(0).put("principalRemainingSumCount",principalRemainingSumCount);
				pageVO.getRows().get(0).put("overDuePrincipalSum",overDuePrincipalSum);
				pageVO.getRows().get(0).put("overDueInterestSum",overDueInterestSum);
				pageVO.getRows().get(0).put("totalPremiumSum",totalPremiumSum);
				pageVO.getRows().get(0).put("premiumSum",premiumSum);
				pageVO.getRows().get(0).put("atoneForAmountSum",atoneForAmountSum);
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
	 * @methodName 记账确认
	 * @param RepaymentDetailVO
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 */
	@ResponseBody
	@RequestMapping("/cancelAccountingMark")
	public AjaxJson cancelAccountingMark(RansomDetailVO vo,HttpServletRequest request, HttpServletResponse response/*,BQRefundVO vo */) {
		Boolean roleFlag = (Boolean) request.getSession().getAttribute("isRevokeRole");
		if(roleFlag==null||!roleFlag){
			ResponseUtil.sendMessage(response, false, "暂无权限操作此功能!");
			return null;
		}
		Double principalRemainingSumCount = new Double(0);
		Double overDuePrincipalSum = new Double(0);
		Double overDueInterestSum = new Double(0);
		Double totalPremiumSum = new Double(0);
		Double premiumSum = new Double(0);
		Double atoneForAmountSum = new Double(0);
		AjaxJson ajaxJson = null;
		Double moneyCount = null;
		PageVO pageVO = null;
		List<Map<String,Object>> list = null;
		boolean flag = true;
		try {
			vo = changeRansomDetailVO(vo);
			ajaxJson = new AjaxJson();
			moneyCount = new Double(0);
			pageVO = new PageVO();
			list = new ArrayList<Map<String,Object>>();
			pageVO.setPageSize(10);
			pageVO.setPageNo(1);
			//修改合同查询
			if(vo.getContractNoArray()!=null&& vo.getContractNoArray().length>0){
				for (int i = 0; i < vo.getContractNoArray().length; i++) {
					vo.setContractNo(vo.getContractNoArray()[i]);
					vo.setSeqId(vo.getSeqIdArray()[i]);
					list.add(ransomDetailServer.queryRepaymentDetailFindAll(vo).get(0));
					ransomDetailServer.cancelAccountingMark(vo);
				}
			}else{
				list=ransomDetailServer.queryRepaymentDetailFindAll(vo);
				ransomDetailServer.cancelAccountingMark(vo);
			}
			if (list.size()>0) {
				for (Map<String,Object> map: list) {
					String keepAccountsStatus = StringUtil.isNullOrEmpty(map.get("keepAccountsStatus"));
					if (!("1").equals(keepAccountsStatus)) {
						flag = false;
					}else{
						map.put("keepaccountsDate", "");
						map.put("keepAccountsStatus", 2);
					}
					moneyCount+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("principalRemainingSum"))));
					principalRemainingSumCount+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("principalRemainingSum"))));
					overDuePrincipalSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("overDuePrincipal"))));
					overDueInterestSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("overDueInterest"))));
					totalPremiumSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("totalPremium"))));
					premiumSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("premium"))));
					atoneForAmountSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("atoneForAmount"))));
				}
				if (flag) {
					ajaxJson.setMsg("记账撤销成功!");
				}else{
					ajaxJson.setMsg("当前匹配合同存在未记账/已撤销记账的合同，该些合同记账撤销失败!");
				}
				pageVO.setRows(list);
				pageVO.getRows().get(0).put("moneyCount",moneyCount);
				pageVO.getRows().get(0).put("contractCount",pageVO.getTotal());
				pageVO.getRows().get(0).put("principalRemainingSumCount",principalRemainingSumCount);
				pageVO.getRows().get(0).put("overDuePrincipalSum",overDuePrincipalSum);
				pageVO.getRows().get(0).put("overDueInterestSum",overDueInterestSum);
				pageVO.getRows().get(0).put("totalPremiumSum",totalPremiumSum);
				pageVO.getRows().get(0).put("premiumSum",premiumSum);
				pageVO.getRows().get(0).put("atoneForAmountSum",atoneForAmountSum);
				ajaxJson.setObj(pageVO);
				ajaxJson.setSuccess(true);
			}else{
				ajaxJson.setMsg("暂无匹配数据可修改!");
			}
		} catch (Exception e) {
			ajaxJson.setMsg("系统异常,记账撤销失败!");
			ajaxJson.setObj(pageVO);
			ajaxJson.setSuccess(false);
			LOGGER.error("记账撤销异常", e);
		}
		return ajaxJson;
	}
	
	/**
	 * @methodName 合同审核
	 * @param RepaymentDetailVO
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 */
	@ResponseBody
	@RequestMapping("/contractApprove")
	public AjaxJson contractApprove(RansomDetailVO vo,HttpServletRequest request, HttpServletResponse response/*,BQRefundVO vo */) {
		AjaxJson ajaxJson = null;
		Double moneyCount = null;
		PageVO pageVO = null;
		Double principalRemainingSumCount = new Double(0);
		Double overDuePrincipalSum = new Double(0);
		Double overDueInterestSum = new Double(0);
		Double totalPremiumSum = new Double(0);
		Double premiumSum = new Double(0);
		Double atoneForAmountSum = new Double(0);
		try {
			ajaxJson = new AjaxJson();
			moneyCount = new Double(0);
			pageVO = new PageVO();
			pageVO.setPageSize(10);
			pageVO.setPageNo(1);
			vo.setApproveTime(DateUtil.getToDate());
			vo.setApproveBy(CurrentUser.getUser().getUsername());
			//合同审核
			ransomDetailServer.contractApprove(vo);
			//修改合同查询
			pageVO = ransomDetailServer.queryRepaymentDetail(pageVO, vo);
			ajaxJson.setObj(pageVO);
			ajaxJson.setSuccess(true);
			if (pageVO.getTotal()>0) {
				for (Map<String,Object> map: pageVO.getRows()) {
					moneyCount+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("principalRemainingSum"))));
					principalRemainingSumCount+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("principalRemainingSum"))));
					overDuePrincipalSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("overDuePrincipal"))));
					overDueInterestSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("overDueInterest"))));
					totalPremiumSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("totalPremium"))));
					premiumSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("premium"))));
					atoneForAmountSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("atoneForAmount"))));
				}
				pageVO.getRows().get(0).put("moneyCount",moneyCount);
				pageVO.getRows().get(0).put("contractCount",pageVO.getTotal());
				pageVO.getRows().get(0).put("principalRemainingSumCount",principalRemainingSumCount);
				pageVO.getRows().get(0).put("overDuePrincipalSum",overDuePrincipalSum);
				pageVO.getRows().get(0).put("overDueInterestSum",overDueInterestSum);
				pageVO.getRows().get(0).put("totalPremiumSum",totalPremiumSum);
				pageVO.getRows().get(0).put("premiumSum",premiumSum);
				pageVO.getRows().get(0).put("atoneForAmountSum",atoneForAmountSum);
				ajaxJson.setMsg("合同审核成功!");
			}else{
				ajaxJson.setMsg("暂无匹配合同可审核!");
			}
		} catch (Exception e) {
			ajaxJson.setMsg("系统异常,合同审核失败!");
			ajaxJson.setObj(pageVO);
			ajaxJson.setSuccess(false);
			LOGGER.error("合同审核异常!", e);
		}
		return ajaxJson;
	}
	
	
	@RequestMapping("queryRepaymentDetailCount")
	public void queryRepaymentDetailCount(RansomDetailVO vo,HttpServletRequest request, HttpServletResponse response){
		//统计合同总数
		try {
			Map<String,Object> map = ransomDetailServer.queryRepaymentDetailCount(vo);
			ResponseUtil.sendJSON(response, map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("查询匹配合同异常!", e);
		}
	}
	
	@RequestMapping("queryContractStatus")
	public void queryContractStatus(RansomDetailVO vo,HttpServletRequest request, HttpServletResponse response){
		//统计合同总数
		try {
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			vo = changeRansomDetailVO(vo);
			//修改合同查询
			if(vo.getContractNoArray()!=null&& vo.getContractNoArray().length>0){
				for (int i = 0; i < vo.getContractNoArray().length; i++) {
					vo.setContractNo(vo.getContractNoArray()[i]);
					vo.setSeqId(vo.getSeqIdArray()[i]);
					list.add(ransomDetailServer.queryRepaymentDetailFindAll(vo).get(0));
				}
			}else{
				list=ransomDetailServer.queryRepaymentDetailFindAll(vo);
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
	
	public RansomDetailVO changeRansomDetailVO(RansomDetailVO vo){
		/**
		 * contains : true  记账多个合同
		 * contains : false 记账单个合同
		 */
		if(StringUtil.isNullOrEmpty(vo.getContractNo()).contains(",")){
			vo.setContractNoArray(vo.getContractNo().split(","));
		}
		if(StringUtil.isNullOrEmpty(vo.getSeqId()).contains(",")){
			vo.setSeqIdArray(vo.getSeqId().split(","));
		}
		return vo;
	}
}
