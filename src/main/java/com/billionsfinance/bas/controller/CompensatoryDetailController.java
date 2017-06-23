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
import com.billionsfinance.bas.entity.CompensatoryDetailVO;
import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.server.ICompensatoryDetailServer;
import com.billionsfinance.bas.server.impl.CompensatoryDetailServer;
import com.billionsfinance.bas.util.AjaxJson;
import com.billionsfinance.bas.util.DateUtil;
import com.billionsfinance.bas.util.ResponseUtil;
import com.billionsfinance.bas.util.StringUtil;

/**
 * @ClassName: 代偿明细Controller
 * @Description: CompensatoryDetailController
 * @author zhouFM
 * @Copyright Copyright (c) 2016 2016年11月18日 上午15:05:04 Company:佰仟金融
 */
@Controller
@RequestMapping("/compensatoryServer")
public class CompensatoryDetailController {

	/** 日志记录 */
	private static final Log LOGGER = LogFactory.getLog(CompensatoryDetailController.class);

	private static final ICompensatoryDetailServer compensatoryServer = new CompensatoryDetailServer();

	/**
	 * @Description: toCompensatoryDetail
	 */
	@RequestMapping("/toCompensatoryDetail")
	public String toCompensatoryDetail() {
		return "compensatory/compensatoryDetail";	
	}

	/**
	 * 代偿明细数据查询
	 * @param RepaymentDetailVO
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 */
	@RequestMapping("/queryRepaymentDetail")
	public void queryRepaymentDetail(Integer page,Integer rows, CompensatoryDetailVO vo,HttpServletRequest request, HttpServletResponse response) {
		System.out.println(vo);
		PageVO pageVO = null;
		try {
			pageVO = new PageVO();
			pageVO.setPageSize(rows);
			pageVO.setPageNo(page);
			pageVO = compensatoryServer.queryRepaymentDetail(pageVO, vo);
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
	public AjaxJson accountingMark(CompensatoryDetailVO vo,HttpServletRequest request, HttpServletResponse response/*,BQRefundVO vo */) {
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
			int result = compensatoryServer.accountingMark(vo);
			//修改合同查询
			if (result > 0 &&(!("").equals(vo.getStartKeepaccountsDate())&&vo.getStartKeepaccountsDate()!=null||
					!("").equals(vo.getEndKeepaccountsDate())&&vo.getEndKeepaccountsDate()!=null)) {
				vo.setStartKeepaccountsDate(vo.getUpdateDate());
				vo.setEndKeepaccountsDate(vo.getUpdateDate());
			}
			pageVO = compensatoryServer.queryRepaymentDetail(pageVO, vo);
			ajaxJson.setObj(pageVO);
			ajaxJson.setSuccess(true);
			boolean flag = true;
			if (pageVO.getTotal()>0) {
				for (Map<String,Object> map: pageVO.getRows()) {
					String payStatus = StringUtil.isNullOrEmpty(map.get("payStatus"));
					if (("0").equals(payStatus) || payStatus == "0"||("").equals(payStatus) || payStatus == null) {
						flag = false;
					}
					moneyCount+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("dcAmount"))));
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
	public AjaxJson selectAccountingMark(CompensatoryDetailVO vo,HttpServletRequest request, HttpServletResponse response/*,BQRefundVO vo */) {
		AjaxJson ajaxJson = null;
		Double moneyCount = null;
		Double principalBlanceSum = new Double(0);
		Double dcoverDueprinCipalBeforeSum = new Double(0);
		Double dcoverDueinterestBeforeSum = new Double(0);
		Double overDuePrincipalSum = new Double(0);
		Double overDueInterestSum = new Double(0);
		Double dcPrincipalSum = new Double(0);
		Double dcInterestSum = new Double(0);
		Double dcAmountSum = new Double(0);
		PageVO pageVO = null;
		List<Map<String,Object>> list = null;
		try {
			vo = changeVO(vo);
			ajaxJson = new AjaxJson();
			moneyCount = new Double(0);
			pageVO = new PageVO();
			list = new ArrayList<Map<String,Object>>();
			pageVO.setPageSize(10);
			pageVO.setPageNo(1);
			vo.setKeepaccountsBy(CurrentUser.getUser().getUsername());
			if(vo.getContractNoArray()!=null&&vo.getContractNoArray().length>0){
				for (int i = 0; i < vo.getContractNoArray().length; i++) {
					vo.setContractNo(vo.getContractNoArray()[i]);
					vo.setSeqId(vo.getSeqIdArray()[i]);
					compensatoryServer.accountingMark(vo);
					list.add(compensatoryServer.queryRepaymentDetailFindAll(vo).get(0));
				}
			}else{
				compensatoryServer.accountingMark(vo);
				list=compensatoryServer.queryRepaymentDetailFindAll(vo);
			}
			boolean flag = true;
			if (list.size()>0) {
				for (Map<String,Object> map: list) {
					String payStatus = StringUtil.isNullOrEmpty(map.get("payStatus"));
					if (("0").equals(payStatus) || payStatus == "0"||("").equals(payStatus) || payStatus == null) {
						flag = false;
					}
					moneyCount+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("dcAmount"))));
					principalBlanceSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("principalBlance"))));
					dcoverDueprinCipalBeforeSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("dcoverDueprinCipalBefore"))));
					dcoverDueinterestBeforeSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("dcoverDueinterestBefore"))));
					overDuePrincipalSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("overDuePrincipal"))));
					overDueInterestSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("overDueInterest"))));
					dcPrincipalSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("dcPrincipal"))));
					dcInterestSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("dcInterest"))));
					dcAmountSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("dcAmount"))));
				}
				if (flag) {
					ajaxJson.setMsg("记账确认标记成功!");
				}else{
					ajaxJson.setMsg("当前匹配合同存在非支付状态的合同，该些合同记账失败!");
				}
				//记账确认
				pageVO.setRows(list);
				pageVO.setTotal((long)list.size());
				pageVO.getRows().get(0).put("moneyCount",moneyCount);
				pageVO.getRows().get(0).put("contractCount",pageVO.getTotal());
				pageVO.getRows().get(0).put("principalBlanceSum",principalBlanceSum);
				pageVO.getRows().get(0).put("dcoverDueprinCipalBeforeSum",dcoverDueprinCipalBeforeSum);
				pageVO.getRows().get(0).put("dcoverDueinterestBeforeSum",dcoverDueinterestBeforeSum);
				pageVO.getRows().get(0).put("overDuePrincipalSum",overDuePrincipalSum);
				pageVO.getRows().get(0).put("overDueInterestSum",overDueInterestSum);
				pageVO.getRows().get(0).put("dcPrincipalSum",dcPrincipalSum);
				pageVO.getRows().get(0).put("dcInterestSum",dcInterestSum);
				pageVO.getRows().get(0).put("dcAmountSum",dcAmountSum);
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
	public AjaxJson cancelAccountingMark(CompensatoryDetailVO vo,HttpServletRequest request, HttpServletResponse response/*,BQRefundVO vo */) {
		Boolean roleFlag = (Boolean) request.getSession().getAttribute("isRevokeRole");
		if(roleFlag==null||!roleFlag){
			ResponseUtil.sendMessage(response, false, "没有该功能的操作权限");
			return null;
		}
		Double principalBlanceSum = new Double(0);
		Double dcoverDueprinCipalBeforeSum = new Double(0);
		Double dcoverDueinterestBeforeSum = new Double(0);
		Double overDuePrincipalSum = new Double(0);
		Double overDueInterestSum = new Double(0);
		Double dcPrincipalSum = new Double(0);
		Double dcInterestSum = new Double(0);
		Double dcAmountSum = new Double(0);
		AjaxJson ajaxJson = null;
		Double moneyCount = null;
		PageVO pageVO = null;
		List<Map<String,Object>> list = null;
		try {
			ajaxJson = new AjaxJson();
			moneyCount = new Double(0);
			pageVO = new PageVO();
			list = new ArrayList<Map<String,Object>>();
			vo = changeVO(vo);
			pageVO.setPageSize(10);
			pageVO.setPageNo(1);
			vo.setKeepaccountsBy(CurrentUser.getUser().getUsername());
			if(vo.getContractNoArray()!=null&&vo.getContractNoArray().length>0){
				for (int i = 0; i < vo.getContractNoArray().length; i++) {
					vo.setContractNo(vo.getContractNoArray()[i]);
					vo.setSeqId(vo.getSeqIdArray()[i]);
					list.add(compensatoryServer.queryRepaymentDetailFindAll(vo).get(0));
					compensatoryServer.cancelAccountingMark(vo);
				}
			}else{
				list=compensatoryServer.queryRepaymentDetailFindAll(vo);
				compensatoryServer.cancelAccountingMark(vo);
			}
			boolean flag = true;
			if (list.size()>0) {
				for (Map<String,Object> map: list) {
					String keepAccountsStatus = StringUtil.isNullOrEmpty(map.get("keepaccountsStatus"));
					if (!("1").equals(keepAccountsStatus)) {
						flag = false;
					}else{
						map.put("keepaccountsDate","");
						map.put("keepaccountsStatus",2);
					}
					moneyCount+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("dcAmount"))));
					principalBlanceSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("principalBlance"))));
					dcoverDueprinCipalBeforeSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("dcoverDueprinCipalBefore"))));
					dcoverDueinterestBeforeSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("dcoverDueinterestBefore"))));
					overDuePrincipalSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("overDuePrincipal"))));
					overDueInterestSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("overDueInterest"))));
					dcPrincipalSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("dcPrincipal"))));
					dcInterestSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("dcInterest"))));
					dcAmountSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("dcAmount"))));
				}
				if (flag) {
					ajaxJson.setMsg("记账撤销成功!");
				}else{
					ajaxJson.setMsg("当前匹配合同存在未记账/已撤销记账状态的合同，该些合同记账失败!");
				}
				pageVO.setRows(list);
				pageVO.getRows().get(0).put("moneyCount",moneyCount);
				pageVO.getRows().get(0).put("contractCount",pageVO.getTotal());
				pageVO.getRows().get(0).put("principalBlanceSum",principalBlanceSum);
				pageVO.getRows().get(0).put("dcoverDueprinCipalBeforeSum",dcoverDueprinCipalBeforeSum);
				pageVO.getRows().get(0).put("dcoverDueinterestBeforeSum",dcoverDueinterestBeforeSum);
				pageVO.getRows().get(0).put("overDuePrincipalSum",overDuePrincipalSum);
				pageVO.getRows().get(0).put("overDueInterestSum",overDueInterestSum);
				pageVO.getRows().get(0).put("dcPrincipalSum",dcPrincipalSum);
				pageVO.getRows().get(0).put("dcInterestSum",dcInterestSum);
				pageVO.getRows().get(0).put("dcAmountSum",dcAmountSum);
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
	public AjaxJson contractApprove(CompensatoryDetailVO vo,HttpServletRequest request, HttpServletResponse response/*,BQRefundVO vo */) {
		AjaxJson ajaxJson = null;
		Double moneyCount = null;
		PageVO pageVO = null;
		try {
			ajaxJson = new AjaxJson();
			moneyCount = new Double(0);
			pageVO = new PageVO();
			pageVO.setPageSize(10);
			pageVO.setPageNo(1);
			vo.setApproveTime(DateUtil.getToDate());
			vo.setApproveBy(CurrentUser.getUser().getUsername());
			//合同审核
			compensatoryServer.contractApprove(vo);
			//修改合同查询
			pageVO = compensatoryServer.queryRepaymentDetail(pageVO, vo);
			ajaxJson.setObj(pageVO);
			ajaxJson.setSuccess(true);
			if (pageVO.getTotal()>0) {
				for (Map<String,Object> map: pageVO.getRows()) {
					moneyCount+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("dcAmount"))));
				}
				pageVO.getRows().get(0).put("moneyCount",moneyCount);
				pageVO.getRows().get(0).put("contractCount",pageVO.getTotal());
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
	
	/**
	 * 查询匹配合同数
	 * @param vo
	 * @param request
	 * @param response
	 */
	@RequestMapping("queryRepaymentDetailCount")
	public void queryRepaymentDetailCount(CompensatoryDetailVO vo,HttpServletRequest request, HttpServletResponse response){
		//统计合同总数
		Map<String,Object> map = compensatoryServer.queryRepaymentDetailCount(vo);
		ResponseUtil.sendJSON(response, map);
	}
	
	@RequestMapping("queryContractStatus")
	public void queryContractStatus(CompensatoryDetailVO vo,HttpServletRequest request, HttpServletResponse response){
		//统计合同总数
		try {
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			vo = changeVO(vo);
			//修改合同查询
			if(vo.getContractNoArray()!=null&&vo.getContractNoArray().length>0){
				for (int i = 0; i < vo.getContractNoArray().length; i++) {
					vo.setContractNo(vo.getContractNoArray()[i]);
					vo.setSeqId(vo.getSeqIdArray()[i]);
					list.add(compensatoryServer.queryRepaymentDetailFindAll(vo).get(0));
				}
			}else{
				list=compensatoryServer.queryRepaymentDetailFindAll(vo);
			}
			for(Map<String,Object> map:list){
				String keepAccountsStatus = StringUtil.isNullOrEmpty(map.get("keepaccountsStatus"));
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
	
	public CompensatoryDetailVO changeVO(CompensatoryDetailVO vo){
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
