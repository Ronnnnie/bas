package com.billionsfinance.bas.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.billionsfinance.als.security.CurrentUser;
import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.entity.RepaymentDetailVO;
import com.billionsfinance.bas.server.IRepaymentDetailServer;
import com.billionsfinance.bas.server.impl.RepaymentDetailServer;
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
@RequestMapping("/repaymentDetailServer")
public class RepaymentDetailController {

	/** 日志记录 */
	private static final Log LOGGER = LogFactory.getLog(RepaymentDetailController.class);

	private static final IRepaymentDetailServer repaymentDetailServer = new RepaymentDetailServer();

	/**
	 * @methodName: toReceivedPaymentsDetail
	 * @Description: trustAllotDetail
	 * @return java.lang.String
	 */
	@RequestMapping("/toRepaymentDetail")
	public String toRepaymentDetail() {
		return "repaymentDetail/repaymentDetail";	
	}

	/**
	 * 明细合同查询
	 * @param RepaymentDetailVO
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 */
	@RequestMapping("/queryRepaymentDetail")
	public void queryRepaymentDetail(Integer page, Integer rows, RepaymentDetailVO vo,HttpServletRequest request, HttpServletResponse response) {
		System.out.println(vo);
		PageVO pageVO = null;
		try {
			pageVO = new PageVO();
			pageVO.setPageSize(rows);
			pageVO.setPageNo(page);
			pageVO = repaymentDetailServer.queryRepaymentDetail(pageVO, vo);
		} catch (Exception e) {
			LOGGER.error("查询还款差额划拨-还款明细-核算失败!", e);
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
	public AjaxJson accountingMark(RepaymentDetailVO vo,HttpServletRequest request, HttpServletResponse response/*,BQRefundVO vo */) {
		Double payPrincipalamtSum = new Double(0);
		Double payInteAmtSum = new Double(0);
		Double waiveintamtSum = new Double(0);
		Double stampDutySum = new Double(0);
		Double payAmtSum = new Double(0);
		AjaxJson ajaxJson = null;
		PageVO pageVO = null;
		boolean flag = true;
		try {
			ajaxJson = new AjaxJson();
			pageVO = new PageVO();
			pageVO.setPageSize(10);
			pageVO.setPageNo(1);
			vo.setKeepaccountsBy(CurrentUser.getUser().getUsername());
			//记账确认
			repaymentDetailServer.accountingMark(vo);
			//修改合同查询
			pageVO = repaymentDetailServer.queryRepaymentDetail(pageVO, vo);
			ajaxJson.setObj(pageVO);
			ajaxJson.setSuccess(true);
			if (pageVO.getTotal()>0) {
				for (Map<String,Object> map: pageVO.getRows()) {
					String payStatus = StringUtil.isNullOrEmpty(map.get("payStatus"));
					payPrincipalamtSum += Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("payPrincipalamt")));
					payInteAmtSum += Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("payInteAmt")));
					waiveintamtSum += Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("waiveintamt")));
					stampDutySum += Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("stampDuty")));
					payAmtSum += Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("payAmt")));
					if (("0").equals(payStatus) || payStatus == "0"||payStatus == null || ("").equals(payStatus)) {
						flag = false;
					}
				}
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
	 * @param RepaymentDetailVO
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 */
	@ResponseBody
	@RequestMapping("/selectAccountingMark")
	public AjaxJson selectAccountingMark(RepaymentDetailVO vo,HttpServletRequest request, HttpServletResponse response/*,BQRefundVO vo */) {
		Double payPrincipalamtSum = new Double(0);
		Double payInteAmtSum = new Double(0);
		Double waiveintamtSum = new Double(0);
		Double stampDutySum = new Double(0);
		Double payAmtSum = new Double(0);
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
			vo.setKeepaccountsBy(CurrentUser.getUser().getUsername());
			vo = changeVO(vo);
			if(vo.getSerialNoArray()!=null&&vo.getSerialNoArray().length>0){
				for (int i = 0; i < vo.getSerialNoArray().length; i++) {
					vo.setSerialNo(vo.getSerialNoArray()[i]);
					vo.setSeqId(vo.getSeqIdArray()[i]);
					vo.setAssetBelong(vo.getAssetBelongArray()[i]);
					vo.setPayInteAmt(vo.getPayInteAmtArray()[i]);
					vo.setPayPrincipalamt(vo.getPayPrincipalamtArray()[i]);
					repaymentDetailServer.accountingMark(vo);
					list.add(repaymentDetailServer.queryRepaymentDetailFindAll(vo).get(0));
				}
			}else{
				repaymentDetailServer.accountingMark(vo);
				list=repaymentDetailServer.queryRepaymentDetailFindAll(vo);
			}
			double moneyCount = 0;
			if (list.size()>0) {
				for (Map<String,Object> map: list) {
					String payStatus = StringUtil.isNullOrEmpty(map.get("payStatus"));
					moneyCount+=Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("moneyCount")));
					payPrincipalamtSum += Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("payPrincipalamt")));
					payInteAmtSum += Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("payInteAmt")));
					waiveintamtSum += Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("waiveintamt")));
					stampDutySum += Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("stampDuty")));
					payAmtSum += Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("payAmt")));
					if (("0").equals(payStatus) || payStatus == "0"||payStatus == null || ("").equals(payStatus)) {
						flag = false;
					}
				}
				if (flag) {
					ajaxJson.setMsg("记账确认标记成功!");
				}else{
					ajaxJson.setMsg("当前匹配合同存在非支付状态的合同，该些合同记账失败!");
				}
				list.get(0).put("payPrincipalamtSum", payPrincipalamtSum);
				list.get(0).put("payInteAmtSum", payInteAmtSum);
				list.get(0).put("waiveintamtSum", waiveintamtSum);
				list.get(0).put("stampDutySum", stampDutySum);
				list.get(0).put("payAmtSum", payAmtSum);
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
	public AjaxJson cancelAccountingMark(RepaymentDetailVO vo,HttpServletRequest request, HttpServletResponse response/*,BQRefundVO vo */) {
		Boolean roleFlag = (Boolean) request.getSession().getAttribute("isRevokeRole");
		if(roleFlag==null||!roleFlag){
			ResponseUtil.sendMessage(response, false, "暂无权限操作此功能!");
			return null;
		}
		AjaxJson ajaxJson = null;
		int resultNumber = 0;
		try {
			ajaxJson = new AjaxJson();
			vo = changeVO(vo);
			if(vo.getSerialNoArray()!=null&&vo.getSerialNoArray().length>0){
				for (int i = 0; i < vo.getSerialNoArray().length; i++) {
					vo.setSerialNo(vo.getSerialNoArray()[i]);
					vo.setSeqId(vo.getSeqIdArray()[i]);
					vo.setAssetBelong(vo.getAssetBelongArray()[i]);
					vo.setPayInteAmt(vo.getPayInteAmtArray()[i]);
					vo.setPayPrincipalamt(vo.getPayPrincipalamtArray()[i]);
					resultNumber += repaymentDetailServer.cancelAccountingMark(vo);
				}
			}else{
				resultNumber = repaymentDetailServer.cancelAccountingMark(vo);
			}
			if (resultNumber != 0) {
				ajaxJson.setObj(resultNumber);
				ajaxJson.setMsg("记账撤销成功!");
			}else{
				ajaxJson.setObj(resultNumber);
				ajaxJson.setMsg("暂无匹配数据可撤销记账!");
			}
			ajaxJson.setSuccess(true);
		} catch (Exception e) {
			ajaxJson.setMsg("系统异常,记账撤销失败!");
			ajaxJson.setSuccess(false);
			LOGGER.error("记账撤销异常", e);
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
	@RequestMapping("/batchCancelAccountingMark")
	public AjaxJson batchCancelAccountingMark(RepaymentDetailVO vo,HttpServletRequest request, HttpServletResponse response/*,BQRefundVO vo */) {
		Boolean roleFlag = (Boolean) request.getSession().getAttribute("isRevokeRole");
		if(roleFlag==null||!roleFlag){
			ResponseUtil.sendMessage(response, false, "暂无权限操作此功能!");
			return null;
		}
		Double payPrincipalamtSum = new Double(0);
		Double payInteAmtSum = new Double(0);
		Double waiveintamtSum = new Double(0);
		Double stampDutySum = new Double(0);
		Double payAmtSum = new Double(0);
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
			vo = changeVO(vo);
			if(vo.getSerialNoArray()!=null&&vo.getSerialNoArray().length>0){
				for (int i = 0; i < vo.getSerialNoArray().length; i++) {
					vo.setSerialNo(vo.getSerialNoArray()[i]);
					vo.setSeqId(vo.getSeqIdArray()[i]);
					vo.setAssetBelong(vo.getAssetBelongArray()[i]);
					vo.setPayInteAmt(vo.getPayInteAmtArray()[i]);
					vo.setPayPrincipalamt(vo.getPayPrincipalamtArray()[i]);
					repaymentDetailServer.cancelAccountingMark(vo);
					list.add(repaymentDetailServer.queryRepaymentDetailFindAll(vo).get(0));
				}
			}
			if (list.size()>0) {
				for (Map<String,Object> map: list) {
					payPrincipalamtSum += Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("payPrincipalamt")));
					payInteAmtSum += Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("payInteAmt")));
					waiveintamtSum += Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("waiveintamt")));
					stampDutySum += Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("stampDuty")));
					payAmtSum += Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("payAmt")));
					String keepAccountsStatus = StringUtil.isNullOrEmpty(map.get("keepAccountsStatus"));
					if (!("2").equals(keepAccountsStatus)) {
						flag = false;
					}
				}
				if (flag) {
					ajaxJson.setMsg("记账撤销成功!");
				}else{
					ajaxJson.setMsg("当前匹配合同存在未记账/已撤销记账的合同，该些合同记账撤销失败!");
				}
				pageVO.setRows(list);
				pageVO.getRows().get(0).put("payPrincipalamtSum", payPrincipalamtSum);
				pageVO.getRows().get(0).put("payInteAmtSum", payInteAmtSum);
				pageVO.getRows().get(0).put("waiveintamtSum", waiveintamtSum);
				pageVO.getRows().get(0).put("stampDutySum", stampDutySum);
				pageVO.getRows().get(0).put("payAmtSum", payAmtSum);
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
	 * @methodName 合同审核
	 * @param RepaymentDetailVO
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 */
	@ResponseBody
	@RequestMapping("/contractApprove")
	public AjaxJson contractApprove(RepaymentDetailVO repaymentDetailVO,HttpServletRequest request, HttpServletResponse response) {
		AjaxJson ajaxJson = null;
		PageVO pageVO = null;
		try {
			ajaxJson = new AjaxJson();
			pageVO = new PageVO();
			pageVO.setPageSize(10);
			pageVO.setPageNo(1);
			repaymentDetailVO.setApproveBy(CurrentUser.getUser().getUsername());
			//合同审核
			repaymentDetailServer.contractApprove(repaymentDetailVO);
			//修改合同查询
			pageVO = repaymentDetailServer.queryRepaymentDetail(pageVO, repaymentDetailVO);
			Map<String,Object> map = repaymentDetailServer.queryRepaymentDetailCount(repaymentDetailVO);
			if (map != null && map.get("moneyCount") != null && map.get("contractCount") != null) {
				pageVO.getRows().get(0).put("moneyCount", Double.parseDouble(map.get("moneyCount").toString()));
				pageVO.getRows().get(0).put("contractCount",Long.parseLong(map.get("contractCount").toString()));
			}
			ajaxJson.setObj(pageVO);
			ajaxJson.setSuccess(true);
			if (pageVO.getTotal()>0) {
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
	 * 匹配合同数统计
	 * @param vo
	 * @param request
	 * @param response
	 */
	@RequestMapping("queryRepaymentDetailCount")
	public void queryRepaymentDetailCount(RepaymentDetailVO vo,HttpServletRequest request, HttpServletResponse response){
		//统计合同总数
		Map<String,Object> map = repaymentDetailServer.queryRepaymentDetailCount(vo);
		ResponseUtil.sendJSON(response, map);
	}
	
	@RequestMapping("queryContractStatus")
	public void queryContractStatus(RepaymentDetailVO vo,HttpServletRequest request, HttpServletResponse response){
		//统计合同总数
		try {
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			vo = changeVO(vo);
			//修改合同查询
			if(vo.getSerialNoArray()!=null&&vo.getSerialNoArray().length>0){
				for (int i = 0; i < vo.getSerialNoArray().length; i++) {
					vo.setSerialNo(vo.getSerialNoArray()[i]);
					vo.setSeqId(vo.getSeqIdArray()[i]);
					vo.setAssetBelong(vo.getAssetBelongArray()[i]);
					vo.setPayInteAmt(vo.getPayInteAmtArray()[i]);
					vo.setPayPrincipalamt(vo.getPayPrincipalamtArray()[i]);
					list.add(repaymentDetailServer.queryRepaymentDetailFindAll(vo).get(0));
				}
			}else{
				list=repaymentDetailServer.queryRepaymentDetailFindAll(vo);
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
	
	/**
	 * 
	 * @Description  信托回款数据导出
	 * @throws ServletException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/excelExport")
	public AjaxJson excelExport(HttpServletRequest request, HttpServletResponse response,RepaymentDetailVO vo) throws ServletException, IOException{
		AjaxJson ajaxJson = null;
		try {
			
			ajaxJson = new AjaxJson();
			String fileName = "导出Excel-信托回款数据.xlsx";  
			List<Map<String,Object>> list = repaymentDetailServer.queryRepaymentDetailFindAll(vo);
			
			SXSSFWorkbook wb = new SXSSFWorkbook(1024); // 这里100是在内存中的数量，如果大于此数量时，会写到硬盘，以避免在内存导致内存溢出  
			Sheet sh = wb.createSheet();
			if (list.size()==0) {
				request.getSession().setAttribute("exportedFlag", "true");
				return ajaxJson;
			}
			for (int i = 0; i < list.size()+1; i++) {  
			    Row row = sh.createRow(i);
			    if (i==0) {
			    	row.createCell(0).setCellValue("交易日期");  
				    
			    	row.createCell(1).setCellValue("统计日期");  
				
			    	row.createCell(2).setCellValue("合同号");  
				
			    	row.createCell(3).setCellValue("客户姓名");  
				
			    	row.createCell(4).setCellValue("注册日期");  
				    
			    	row.createCell(5).setCellValue("合同到期日");  
				    
			    	row.createCell(6).setCellValue("门店代码");  
				    
			    	row.createCell(7).setCellValue("商户代码"); 
				    
			    	row.createCell(8).setCellValue("SA_ID");
			    	
			    	row.createCell(9).setCellValue("商品范畴");
				    
			    	row.createCell(10).setCellValue("客户渠道");
			    	
			    	row.createCell(11).setCellValue("业务模式"); 
			    
			    	row.createCell(12).setCellValue("产品分类"); 
				    
			    	row.createCell(13).setCellValue("产品子类型"); 
				    
			    	row.createCell(14).setCellValue("省份"); 
				    
			    	row.createCell(15).setCellValue("城市"); 
				    
			    	row.createCell(16).setCellValue("城市编码"); 
				   
			    	row.createCell(17).setCellValue("资金方"); 
				    
			    	row.createCell(18).setCellValue("合同轨迹"); 
				    
			    	row.createCell(19).setCellValue("强制日期"); 
				    
			    	row.createCell(20).setCellValue("应还日期"); 
				    
			    	row.createCell(21).setCellValue("期次");
			    	
			    	row.createCell(22).setCellValue("是否取消分期期次");
			    	
			    	row.createCell(23).setCellValue("转让日期");
			    	
			    	row.createCell(24).setCellValue("代偿日");
			    	
			    	row.createCell(25).setCellValue("赎回日");
			    	
			    	row.createCell(26).setCellValue("保险投保日");
			    	
			    	row.createCell(27).setCellValue("保险理赔日");
			    	
			    	row.createCell(28).setCellValue("代垫方");
			    	
			    	row.createCell(29).setCellValue("资产所属方");
			    	
			    	row.createCell(30).setCellValue("保证方");
			    	
			    	row.createCell(31).setCellValue("还款类型");
			    	
			    	row.createCell(32).setCellValue("本金");
			    	
			    	row.createCell(33).setCellValue("利息");
			    	
			    	row.createCell(34).setCellValue("印花税");
			    	
			    	row.createCell(35).setCellValue("总额");
			    	
				}else{
				    row.createCell(0).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("duedate")));
				    row.createCell(1).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("accorddate")));  
				    row.createCell(2).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("serialNo")));  
				    row.createCell(3).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("clientName")));  
				    row.createCell(4).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("registrationDate")));  
				    row.createCell(5).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("maturitydate")));  
				    row.createCell(6).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("sno")));  
				    row.createCell(7).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("rno")));  
				    row.createCell(8).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("sa_id")));  
				    row.createCell(9).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("productcategory")));  
				    row.createCell(10).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("suretype")));
				    row.createCell(11).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("businessmodel")));
				    row.createCell(12).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("productId")));  
				    row.createCell(13).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("subProductType")));  
				    row.createCell(14).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("province")));  
				    row.createCell(15).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("city")));  
				    row.createCell(16).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("citycode")));  
				    row.createCell(17).setCellValue(StringUtil.companyFormatter(StringUtil.isNullOrEmpty(list.get(i-1).get("creditperson"))));  
				    row.createCell(18).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("contractlife")));  
				    row.createCell(19).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("cdate")));  
				    row.createCell(20).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("payDate")));  
				    row.createCell(21).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("seqId")));  
				    row.createCell(22).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("canceltype")));  
				    row.createCell(23).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("deliverydate")));  
				    row.createCell(24).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("dcdate")));  
				    row.createCell(25).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("shdate")));  
				    row.createCell(26).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("tbdate")));  
				    row.createCell(27).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("lpdate")));  
				    row.createCell(28).setCellValue(StringUtil.companyFormatter(StringUtil.isNullOrEmpty(list.get(i-1).get("debours"))));  
				    row.createCell(29).setCellValue(StringUtil.companyFormatter(StringUtil.isNullOrEmpty(list.get(i-1).get("assetBelong"))));  
				    row.createCell(30).setCellValue(StringUtil.companyFormatter(StringUtil.isNullOrEmpty(list.get(i-1).get("guaranteeParty"))));  
				    row.createCell(31).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("payType")));  
				    row.createCell(32).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("payPrincipalamt")));  
				    row.createCell(33).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("payInteAmt")));  
				    row.createCell(34).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("stampDuty")));  
				    row.createCell(35).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("payAmt")));
				}
			}
			fileName = new String(fileName.getBytes("GBK"), "iso8859-1");  
			response.reset();  
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);// 指定下载的文件名  
			response.setContentType("application/vnd.ms-excel");  
			response.setHeader("Pragma", "no-cache");  
			response.setHeader("Cache-Control", "no-cache");  
			response.setDateHeader("Expires", 0);  
			OutputStream output = response.getOutputStream();  
			BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output); 
			wb.write(bufferedOutPut);  
			bufferedOutPut.flush();
			bufferedOutPut.close();
			request.getSession().setAttribute("exportedFlag", "true");
		} catch (Exception e) {
			LOGGER.error("数据导出错误!", e);
		}
        return ajaxJson;
	}
	
	public RepaymentDetailVO changeVO(RepaymentDetailVO vo){
		/**
		 * contains : true  记账多个合同
		 * contains : false 记账单个合同
		 */
		if(StringUtil.isNullOrEmpty(vo.getSeqId()).contains(",")){
			vo.setSeqIdArray(vo.getSeqId().split(","));
		}
		if(StringUtil.isNullOrEmpty(vo.getSerialNo()).contains(",")){
			vo.setSerialNoArray(vo.getSerialNo().split(","));
		}
		if(StringUtil.isNullOrEmpty(vo.getAssetBelong()).contains(",")){
			vo.setAssetBelongArray(vo.getAssetBelong().split(","));
		}
		if(StringUtil.isNullOrEmpty(vo.getPayPrincipalamt()).contains(",")){
			vo.setPayPrincipalamtArray(vo.getPayPrincipalamt().split(","));
		}
		if(StringUtil.isNullOrEmpty(vo.getPayInteAmt()).contains(",")){
			vo.setPayInteAmtArray(vo.getPayInteAmt().split(","));
		}
		return vo;
	}
	
}
