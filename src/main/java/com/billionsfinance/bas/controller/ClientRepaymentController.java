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
import com.billionsfinance.bas.entity.ClientRepaymentVO;
import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.server.IClientRepaymentDetailServer;
import com.billionsfinance.bas.server.impl.ClientRepaymentDetailServer;
import com.billionsfinance.bas.util.AjaxJson;
import com.billionsfinance.bas.util.ResponseUtil;
import com.billionsfinance.bas.util.StringUtil;

/**
 * @ClassName: 转让资产客户回款明细Controller(哈行回款明细)
 * @Description: ClientRepaymentController
 * @author zhouFM
 * @Copyright Copyright (c) 2016 2016年11月18日 上午15:05:04 Company:佰仟金融
 */
@Controller
@RequestMapping("/clientRepaymentServer")
public class ClientRepaymentController {

	/** 日志记录 */
	private static final Log LOGGER = LogFactory.getLog(ClientRepaymentController.class);

	private static final IClientRepaymentDetailServer repaymentDetailServer = new ClientRepaymentDetailServer();

	/**
	 * @methodName: toReceivedPaymentsDetail
	 * @Description: trustAllotDetail
	 * @return java.lang.String
	 */
	@RequestMapping("/toClientRepaymentDetail")
	public String toClientRepaymentDetail() {
		return "clientRepayment/clientRepaymentDetail";	
	}

	/**
	 * 明细合同查询
	 * @param RepaymentDetailVO
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 */
	@RequestMapping("/queryRepaymentDetail")
	public void queryRepaymentDetail(Integer page, Integer rows, ClientRepaymentVO vo,HttpServletRequest request, HttpServletResponse response) {
		PageVO pageVO = null;
		try {
			pageVO = new PageVO();
			pageVO.setPageSize(rows);
			pageVO.setPageNo(page);
			pageVO = repaymentDetailServer.queryRepaymentDetail(pageVO, vo);
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
	public AjaxJson accountingMark(ClientRepaymentVO vo,HttpServletRequest request, HttpServletResponse response/*,BQRefundVO vo */) {
		AjaxJson ajaxJson = null;
		Double actualPayPrincipalAmtSum = new Double(0);
		Double actualPayinteAmtSum = new Double(0);
		Double payAmtSum = new Double(0);
		PageVO pageVO = null;
		try {
			ajaxJson = new AjaxJson();
			pageVO = new PageVO();
			pageVO.setPageSize(10);
			pageVO.setPageNo(1);
			vo.setKeepaccountsBy(CurrentUser.getUser().getUsername());
			int status = 0;
			//修改合同查询
			List<Map<String,Object>> list = repaymentDetailServer.queryRepaymentDetailFindAll(vo);
			if (list.size()>0) {
				for (Map<String,Object> map: list) {
					String payStatus = StringUtil.isNullOrEmpty(map.get("payStatus"));
					String keepAccountsStatus = StringUtil.isNullOrEmpty(map.get("keepAccountsStatus"));
					if (("0").equals(payStatus) || payStatus == "0" ||payStatus == null || ("").equals(payStatus)) {
						status = 1;
					}
					if(("1").equals(keepAccountsStatus) || keepAccountsStatus == "1" ){
						status = 2;
					}
					actualPayPrincipalAmtSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("actualPayPrincipalAmt"))));
					actualPayinteAmtSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("actualPayinteAmt"))));
					payAmtSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("payAmt"))));
				}
				if (status == 0) {
					ajaxJson.setMsg("记账确认标记成功!");
				}else if (status == 0){
					ajaxJson.setMsg("当前匹配合同存在非支付状态的合同，该些合同记账失败!");
				}else{
					ajaxJson.setMsg("当前匹配合同存在已记账状态的合同，该些合同记账失败!");
				}
				//记账确认
				int result = repaymentDetailServer.accountingMark(vo);
				if(result>0){
					vo.setStartKeepaccountsDate(vo.getUpdateDate());
					vo.setEndKeepaccountsDate(vo.getUpdateDate());
				}
				pageVO = repaymentDetailServer.queryRepaymentDetail(pageVO, vo);
				pageVO.getRows().get(0).put("actualPayPrincipalAmtSum",actualPayPrincipalAmtSum);
				pageVO.getRows().get(0).put("actualPayinteAmtSum",actualPayinteAmtSum);
				pageVO.getRows().get(0).put("payAmtSum",payAmtSum);
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
	@RequestMapping("/selectAccountingMark")
	public AjaxJson selectAccountingMark(ClientRepaymentVO vo,HttpServletRequest request, HttpServletResponse response/*,BQRefundVO vo */) {
		AjaxJson ajaxJson = null;
		Double actualPayPrincipalAmtSum = new Double(0);
		Double actualPayinteAmtSum = new Double(0);
		Double payAmtSum = new Double(0);
		PageVO pageVO = null;
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
					vo.setActualPayPrincipalAmt(vo.getActualPayPrincipalAmtArray()[i]);
					vo.setActualPayinteAmt(vo.getActualPayinteAmtArray()[i]);
					repaymentDetailServer.accountingMark(vo);
					list.add(repaymentDetailServer.queryRepaymentDetailFindAll(vo).get(0));
				}
			}else{
				repaymentDetailServer.accountingMark(vo);
				list = repaymentDetailServer.queryRepaymentDetailFindAll(vo);
			}
			boolean flag = true;
			if (list.size()>0) {
				//记账确认
				for (Map<String,Object> map: list) {
					String payStatus = StringUtil.isNullOrEmpty(map.get("payStatus"));
					if (("0").equals(payStatus) || payStatus == "0" ||payStatus == null || ("").equals(payStatus)) {
						flag = false;
					}
					actualPayPrincipalAmtSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("actualPayPrincipalAmt"))));
					actualPayinteAmtSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("actualPayinteAmt"))));
					payAmtSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("payAmt"))));
				}
				if(flag) {
					ajaxJson.setMsg("记账确认标记成功!");
				}else{
					ajaxJson.setMsg("当前匹配合同存在非支付状态的合同，该些合同记账失败!");
				}
				pageVO.setRows(list);
				pageVO.setTotal((long)list.size());
				pageVO.getRows().get(0).put("actualPayPrincipalAmtSum",actualPayPrincipalAmtSum);
				pageVO.getRows().get(0).put("actualPayinteAmtSum",actualPayinteAmtSum);
				pageVO.getRows().get(0).put("payAmtSum",payAmtSum);
				ajaxJson.setObj(pageVO);
				ajaxJson.setSuccess(true);
			}else{
				ajaxJson.setMsg("暂无匹配数据可更改!");
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
	public AjaxJson cancelAccountingMark(ClientRepaymentVO vo,HttpServletRequest request, HttpServletResponse response/*,BQRefundVO vo */) {
		Double actualPayPrincipalAmtSum = new Double(0);
		Double actualPayinteAmtSum = new Double(0);
		Double payAmtSum = new Double(0);
		Boolean roleFlag = (Boolean) request.getSession().getAttribute("isRevokeRole");
		if(roleFlag==null||!roleFlag){
			ResponseUtil.sendMessage(response, false, "没有该功能的操作权限");
			return null;
		}
		AjaxJson ajaxJson = null;
		PageVO pageVO = null;
		List<Map<String,Object>> list = null;
		try {
			ajaxJson = new AjaxJson();
			pageVO = new PageVO();
			list = new ArrayList<Map<String,Object>>();
			pageVO.setPageSize(10);
			pageVO.setPageNo(1);
			//记账确认
			vo = changeVO(vo);
			if(vo.getSerialNoArray()!=null&&vo.getSerialNoArray().length>0){
				for (int i = 0; i < vo.getSerialNoArray().length; i++) {
					vo.setSerialNo(vo.getSerialNoArray()[i]);
					vo.setSeqId(vo.getSeqIdArray()[i]);
					vo.setActualPayPrincipalAmt(vo.getActualPayPrincipalAmtArray()[i]);
					vo.setActualPayinteAmt(vo.getActualPayinteAmtArray()[i]);
					list.add(repaymentDetailServer.queryRepaymentDetailFindAll(vo).get(0));
					repaymentDetailServer.cancelAccountingMark(vo);
				}
			}else{
				list = repaymentDetailServer.queryRepaymentDetailFindAll(vo);
				repaymentDetailServer.cancelAccountingMark(vo);
			}
			boolean flag = true;
			if (list.size()>0) {
				for (Map<String,Object> map: list) {
					String keepAccountsStatus = StringUtil.isNullOrEmpty(map.get("keepAccountsStatus"));
					if (!("1").equals(keepAccountsStatus)) {
						flag = false;
					}else{
						map.put("keepaccountsDate", "");
						map.put("keepAccountsStatus", "");
					}
					actualPayPrincipalAmtSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("actualPayPrincipalAmt"))));
					actualPayinteAmtSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("actualPayinteAmt"))));
					payAmtSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("payAmt"))));
				}
				if (flag) {
					ajaxJson.setMsg("记账撤销成功!");
				}else{
					ajaxJson.setMsg("当前匹配合同存在未记账/撤销记账的合同，该些合同记账撤销失败!");
				}
				pageVO.setRows(list);
				pageVO.getRows().get(0).put("actualPayPrincipalAmtSum",actualPayPrincipalAmtSum);
				pageVO.getRows().get(0).put("actualPayinteAmtSum",actualPayinteAmtSum);
				pageVO.getRows().get(0).put("payAmtSum",payAmtSum);
				pageVO.setTotal((long) list.size());
				ajaxJson.setObj(pageVO);
				ajaxJson.setSuccess(true);
			}else{
				ajaxJson.setMsg("暂无匹配数据可撤销!");
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
	public AjaxJson contractApprove(ClientRepaymentVO vo,HttpServletRequest request, HttpServletResponse response) {
		AjaxJson ajaxJson = null;
		PageVO pageVO = null;
		Double actualPayPrincipalAmtSum = new Double(0);
		Double actualPayinteAmtSum = new Double(0);
		Double payAmtSum = new Double(0);
		try {
			ajaxJson = new AjaxJson();
			pageVO = new PageVO();
			pageVO.setPageSize(10);
			pageVO.setPageNo(1);
			vo.setApproveBy(CurrentUser.getUser().getUsername());
			//合同审核
			repaymentDetailServer.contractApprove(vo);
			//修改合同查询
			pageVO = repaymentDetailServer.queryRepaymentDetail(pageVO, vo);
			ajaxJson.setObj(pageVO);
			ajaxJson.setSuccess(true);
			if (pageVO.getTotal()>0) {
				for (Map<String,Object> map: pageVO.getRows()) {
					actualPayPrincipalAmtSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("actualPayPrincipalAmt"))));
					actualPayinteAmtSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("actualPayinteAmt"))));
					payAmtSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("payAmt"))));
				}
				pageVO.getRows().get(0).put("actualPayPrincipalAmtSum",actualPayPrincipalAmtSum);
				pageVO.getRows().get(0).put("actualPayinteAmtSum",actualPayinteAmtSum);
				pageVO.getRows().get(0).put("payAmtSum",payAmtSum);
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
	public void queryRepaymentDetailCount(ClientRepaymentVO vo,HttpServletRequest request, HttpServletResponse response){
		//统计合同总数
		Map<String,Object> map = repaymentDetailServer.queryRepaymentDetailCount(vo);
		ResponseUtil.sendJSON(response,map);
	}
	
	@RequestMapping("queryContractStatus")
	public void queryContractStatus(ClientRepaymentVO vo,HttpServletRequest request, HttpServletResponse response){
		//统计合同总数
		try {
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			vo = changeVO(vo);
			//修改合同查询
			if(vo.getSerialNoArray()!=null&&vo.getSerialNoArray().length>0){
				for (int i = 0; i < vo.getSerialNoArray().length; i++) {
					vo.setSerialNo(vo.getSerialNoArray()[i]);
					vo.setSeqId(vo.getSeqIdArray()[i]);
					vo.setActualPayPrincipalAmt(vo.getActualPayPrincipalAmtArray()[i]);
					vo.setActualPayinteAmt(vo.getActualPayinteAmtArray()[i]);
					list.add(repaymentDetailServer.queryRepaymentDetailFindAll(vo).get(0));
				}
			}else{
				list = repaymentDetailServer.queryRepaymentDetailFindAll(vo);
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
	 * @Description  逾期汇总合同导出
	 * @throws ServletException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/excelExport")
	public AjaxJson excelExport(HttpServletRequest request, HttpServletResponse response,ClientRepaymentVO vo) throws ServletException, IOException{
		AjaxJson ajaxJson = null;
		try {
			
			ajaxJson = new AjaxJson();
			String fileName = "导出Excel-哈行回款数据.xlsx";  
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
			    	row.createCell(0).setCellValue("记账日期");  
				    
			    	row.createCell(1).setCellValue("统计日期");  
				
			    	row.createCell(2).setCellValue("合同号");  
				
			    	row.createCell(3).setCellValue("客户姓名");  
				
			    	row.createCell(4).setCellValue("注册日期");  
				    
			    	row.createCell(5).setCellValue("合同到期日");  
				    
			    	row.createCell(6).setCellValue("门店代码");  
				    
			    	row.createCell(7).setCellValue("商户代码"); 
				    
			    	row.createCell(8).setCellValue("SA_ID");
				    
			    	row.createCell(9).setCellValue("客户渠道");
			    	
			    	row.createCell(10).setCellValue("业务模式"); 
			    	
			    	row.createCell(11).setCellValue("产品分类"); 
				    
			    	row.createCell(12).setCellValue("产品子类型"); 
				    
			    	row.createCell(13).setCellValue("省份"); 
				    
			    	row.createCell(14).setCellValue("城市"); 
				    
			    	row.createCell(15).setCellValue("城市编码"); 
				   
			    	row.createCell(16).setCellValue("资金方"); 
				    
			    	row.createCell(17).setCellValue("合同轨迹"); 
				    
			    	row.createCell(18).setCellValue("强制日期"); 
				    
			    	row.createCell(19).setCellValue("逾期天数"); 
				    
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
			    	
			    	row.createCell(31).setCellValue("实还日期");
			    	
			    	row.createCell(32).setCellValue("还款类型");
			    	
			    	row.createCell(33).setCellValue("实还本金");
			    	
			    	row.createCell(34).setCellValue("实还利息");
			    	
			    	row.createCell(35).setCellValue("总额");
			    	
				}else{
				    row.createCell(0).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("keepaccountsDate")));
				    row.createCell(1).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("accorddate")));  
				    row.createCell(2).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("serialNo")));  
				    row.createCell(3).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("clientName")));  
				    row.createCell(4).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("registrationDate")));  
				    row.createCell(5).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("maturitydate")));  
				    row.createCell(6).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("sno")));  
				    row.createCell(7).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("rno")));  
				    row.createCell(8).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("sa_id")));  
				    row.createCell(9).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("suretype")));  
				    row.createCell(10).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("businessmodel")));
				    row.createCell(11).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("productType")));
				    row.createCell(12).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("subProductType")));  
				    row.createCell(13).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("province")));  
				    row.createCell(14).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("city")));  
				    row.createCell(15).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("citycode")));  
				    row.createCell(16).setCellValue(StringUtil.companyFormatter(StringUtil.isNullOrEmpty(list.get(i-1).get("fundProviders"))));  
				    row.createCell(17).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("contractlife")));  
				    row.createCell(18).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("cdate")));  
				    row.createCell(19).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("overduedays")));  
				    row.createCell(20).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("payDate")));  
				    row.createCell(21).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("seqId")));  
				    row.createCell(22).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("canceltype")));  
				    row.createCell(23).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("transferDate")));  
				    row.createCell(24).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("dcdate")));  
				    row.createCell(25).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("shdate")));  
				    row.createCell(26).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("tbdate")));  
				    row.createCell(27).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("lpdate")));  
				    row.createCell(28).setCellValue(StringUtil.companyFormatter(StringUtil.isNullOrEmpty(list.get(i-1).get("debours"))));  
				    row.createCell(29).setCellValue(StringUtil.companyFormatter(StringUtil.isNullOrEmpty(list.get(i-1).get("assetBelong"))));  
				    row.createCell(30).setCellValue(StringUtil.companyFormatter(StringUtil.isNullOrEmpty(list.get(i-1).get("guaranteeParty"))));  
				    row.createCell(31).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("actualPayDate")));  
				    row.createCell(32).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("payType")));  
				    row.createCell(33).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("actualPayPrincipalAmt")));  
				    row.createCell(34).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("actualPayinteAmt")));  
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
	
	public ClientRepaymentVO changeVO(ClientRepaymentVO vo){
		if(StringUtil.isNullOrEmpty(vo.getSeqId()).contains(",")){
			vo.setSeqIdArray(vo.getSeqId().split(","));
		}
		if(StringUtil.isNullOrEmpty(vo.getSerialNo()).contains(",")){
			vo.setSerialNoArray(vo.getSerialNo().split(","));
		}
		if(StringUtil.isNullOrEmpty(vo.getActualPayPrincipalAmt()).contains(",")){
			vo.setActualPayPrincipalAmtArray(vo.getActualPayPrincipalAmt().split(","));
		}
		if(StringUtil.isNullOrEmpty(vo.getActualPayinteAmt()).contains(",")){
			vo.setActualPayinteAmtArray(vo.getActualPayinteAmt().split(","));
		}
		return vo;
	}
}
