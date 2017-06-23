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
import com.billionsfinance.bas.entity.JYReceivedPaymentsVO;
import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.server.IJYReceivedPaymentsServer;
import com.billionsfinance.bas.server.impl.JYReceivedPaymentsServer;
import com.billionsfinance.bas.util.AjaxJson;
import com.billionsfinance.bas.util.ResponseUtil;
import com.billionsfinance.bas.util.StringUtil;

/**
 * @ClassName: JYReceivedPaymentsController
 * @Description: 嘉银汇款Controller
 * @author FMZhou
 * 
 * Copyright: Copyright (c) 2016 2016年11月28日 下午18:25:09 Company:佰仟金融
 */
@Controller
@RequestMapping("/jyReceivedPaymentsServer")
public class JYReceivedPaymentsController {

	/** 日志记录 */
	private static final Log LOGGER = LogFactory.getLog(JYReceivedPaymentsController.class);

	private static final IJYReceivedPaymentsServer jyReceivedPaymentsServer = new JYReceivedPaymentsServer();

	/**
	 * Description: 跳转嘉银回款页面
	 */
	@RequestMapping("/toJYReceivedPaymentsDetail")
	public String toJYReceivedPaymentsDetail() {
		return "jyReceivedPayments/jyReceivedPaymentsDetail";	
	}
	
	/**
	 * @Description:嘉银回款数据查询
	 * @param vo
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryJYReceivedPaymentsDetail")
	public void queryJYReceivedPaymentsDetail(Integer page, Integer rows, JYReceivedPaymentsVO vo,HttpServletRequest request, HttpServletResponse response) {
		PageVO pageVO;
		try {
			pageVO = new PageVO();
			pageVO.setPageSize(rows);
			pageVO.setPageNo(page);
			pageVO = jyReceivedPaymentsServer.queryJYReceivedPaymentsDetail(pageVO, vo);
			ResponseUtil.sendJSON(response, pageVO);
		} catch (Exception e) {
			LOGGER.error("查询嘉银回款数据失败!", e);
		}
	}
	
	
	/**
	 * @Description:嘉银回款数据记账确认
	 * @param vo
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/accountingMark")
	public AjaxJson accountingMark(JYReceivedPaymentsVO vo,HttpServletRequest request, HttpServletResponse response) {
		AjaxJson ajaxJson = null;
		Double moneyCount = null;
		PageVO pageVO = null;
		try {
			ajaxJson = new AjaxJson();
			moneyCount = new Double(0);
			pageVO = new PageVO();
			pageVO.setPageSize(10);
			pageVO.setPageNo(1);
			vo.setKeepAccountsBy(CurrentUser.getUser().getUsername());
			//记账确认
			int resultLine = jyReceivedPaymentsServer.accountingMark(vo);
			if (resultLine > 0 && (!("").equals(vo.getStartKeepAccountsTime())&&vo.getStartKeepAccountsTime()!=null||
						!("").equals(vo.getEndKeepAccountsTime())&&vo.getEndKeepAccountsTime()!=null)) {
				vo.setStartKeepAccountsTime(vo.getUpdateDate());
				vo.setEndKeepAccountsTime(vo.getUpdateDate());
			}
			//修改合同查询
			pageVO = jyReceivedPaymentsServer.queryJYReceivedPaymentsDetail(pageVO,vo);
			ajaxJson.setObj(pageVO);
			ajaxJson.setSuccess(true);
			boolean flag = true;
			if (pageVO.getTotal()>0) {
				for (Map<String,Object> map: pageVO.getRows()) {
					String payStatus = StringUtil.isNullOrEmpty(map.get("payStatus"));
					if (("0").equals(payStatus) || payStatus == "0" || payStatus == null || ("").equals(payStatus)) {
						flag = false;
					}
					moneyCount+=(Double.parseDouble(map.get("loanAmount").toString()));
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
			ajaxJson.setObj(null);
			ajaxJson.setSuccess(false);
			LOGGER.error("记账确认异常", e);
		}
		return ajaxJson;
	}
	
	/**
	 * @Description:嘉银回款数据记账确认
	 * @param vo
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/selectAccountingMark")
	public AjaxJson selectAccountingMark(JYReceivedPaymentsVO vo,HttpServletRequest request, HttpServletResponse response) {
		AjaxJson ajaxJson = null;
		Double moneyCount = null;
		Double payprinciPalamtSum = new Double(0);
		Double payInteamtSum = new Double(0);
		Double paymentSum = new Double(0);
		Double loanAmountSum = new Double(0);
		PageVO pageVO = null;
		List<Map<String,Object>> list = null;
		try {
			ajaxJson = new AjaxJson();
			moneyCount = new Double(0);
			pageVO = new PageVO();
			list = new ArrayList<Map<String,Object>>();
			pageVO.setPageSize(10);
			pageVO.setPageNo(1);
			vo.setKeepAccountsBy(CurrentUser.getUser().getUsername());
			vo=changeVO(vo);
			if(vo.getContractsArray()!=null&&vo.getContractsArray().length>0){
				for (int i = 0; i < vo.getContractsArray().length; i++) {
					vo.setContracts(vo.getContractsArray()[i]);
					vo.setSequence(vo.getSequenceArray()[i]);
					jyReceivedPaymentsServer.accountingMark(vo);
					list.add(jyReceivedPaymentsServer.queryJYReceivedPaymentsDetailFindAll(vo).get(0));
				}
			}else{
				jyReceivedPaymentsServer.accountingMark(vo);
				list=jyReceivedPaymentsServer.queryJYReceivedPaymentsDetailFindAll(vo);
			}
			boolean flag = true;
			if (list.size()>0) {
				for (Map<String,Object> map: list) {
					String payStatus = StringUtil.isNullOrEmpty(map.get("payStatus"));
					if (("0").equals(payStatus) || payStatus == "0" || payStatus == null || ("").equals(payStatus)) {
						flag = false;
					}
					moneyCount+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("loanAmount")).toString()));
					payprinciPalamtSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("payprinciPalamt")).toString()));
					payInteamtSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("payInteamt")).toString()));
					paymentSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("payment")).toString()));
					loanAmountSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("loanAmount")).toString()));
				}
				if (flag) {
					ajaxJson.setMsg("记账确认标记成功!");
				}else{
					ajaxJson.setMsg("当前匹配合同存在非支付状态的合同，该些合同记账失败!");
				}
				pageVO.setRows(list);
				pageVO.setTotal((long)list.size());
				pageVO.getRows().get(0).put("moneyCount",moneyCount);
				pageVO.getRows().get(0).put("payprinciPalamtSum",payprinciPalamtSum);
				pageVO.getRows().get(0).put("payInteamtSum",payInteamtSum);
				pageVO.getRows().get(0).put("paymentSum",paymentSum);
				pageVO.getRows().get(0).put("loanAmountSum",loanAmountSum);
				pageVO.getRows().get(0).put("contractCount",pageVO.getTotal());
				ajaxJson.setObj(pageVO);
				ajaxJson.setSuccess(true);
			}else{
				ajaxJson.setMsg("暂无匹配数据可修改!");
			}
		} catch (Exception e) {
			ajaxJson.setMsg("系统异常,记账确认标记失败!");
			ajaxJson.setObj(null);
			ajaxJson.setSuccess(false);
			LOGGER.error("记账确认异常", e);
		}
		return ajaxJson;
	}
	/**
	 * @Description:嘉银回款数据记账撤销
	 * @param vo
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/cancelAccountingMark")
	public AjaxJson cancelAccountingMark(JYReceivedPaymentsVO vo,HttpServletRequest request, HttpServletResponse response) {
		Boolean roleFlag = (Boolean) request.getSession().getAttribute("isRevokeRole");
		if(roleFlag==null||!roleFlag){
			ResponseUtil.sendMessage(response, false, "暂无权限操作此功能!");
			return null;
		}
		Double payprinciPalamtSum = new Double(0);
		Double payInteamtSum = new Double(0);
		Double paymentSum = new Double(0);
		Double loanAmountSum = new Double(0);
		AjaxJson ajaxJson = null;
		Double moneyCount = null;
		PageVO pageVO = null;
		List<Map<String,Object>> list = null;
		try {
			ajaxJson = new AjaxJson();
			pageVO = new PageVO();
			moneyCount = new Double(0);
			list = new ArrayList<Map<String,Object>>();
			boolean flag = true;
			pageVO.setPageSize(10);
			pageVO.setPageNo(1);
			vo=changeVO(vo);
			if(vo.getContractsArray()!=null&&vo.getContractsArray().length>0){
				for (int i = 0; i < vo.getContractsArray().length; i++) {
					vo.setContracts(vo.getContractsArray()[i]);
					vo.setSequence(vo.getSequenceArray()[i]);
					list.add(jyReceivedPaymentsServer.queryJYReceivedPaymentsDetailFindAll(vo).get(0));
					jyReceivedPaymentsServer.cancelAccountingMark(vo);
				}
			}else{
				list=jyReceivedPaymentsServer.queryJYReceivedPaymentsDetailFindAll(vo);
				jyReceivedPaymentsServer.cancelAccountingMark(vo);
			}
			if (list.size()>0) {
				for (Map<String,Object> map: list) {
					String keepAccountsStatus = StringUtil.isNullOrEmpty(map.get("keepAccountsStatus"));
					if (!("1").equals(keepAccountsStatus)) {
						flag = false;
					}else{
						map.put("keepAccountsTime", "");
						map.put("keepAccountsStatus", 2);
					}
					moneyCount+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("loanAmount")).toString()));
					payprinciPalamtSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("payprinciPalamt")).toString()));
					payInteamtSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("payInteamt")).toString()));
					paymentSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("payment")).toString()));
					loanAmountSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("loanAmount")).toString()));
				}
				if (flag) {
					ajaxJson.setMsg("记账撤销成功!");
				}else{
					ajaxJson.setMsg("当前匹配合同存在未记账/已撤销记账状态的合同，该些合同记账撤销失败!");
				}
				pageVO.setRows(list);
				pageVO.getRows().get(0).put("moneyCount",moneyCount);
				pageVO.getRows().get(0).put("contractCount",pageVO.getTotal());
				pageVO.getRows().get(0).put("payprinciPalamtSum",payprinciPalamtSum);
				pageVO.getRows().get(0).put("payInteamtSum",payInteamtSum);
				pageVO.getRows().get(0).put("paymentSum",paymentSum);
				pageVO.getRows().get(0).put("loanAmountSum",loanAmountSum);
				ajaxJson.setObj(pageVO);
				ajaxJson.setSuccess(true);
			}else{
				ajaxJson.setMsg("暂无匹配数据可修改!");
			}
		} catch (Exception e) {
			ajaxJson.setMsg("系统异常,记账确认标记失败!");
			ajaxJson.setObj(null);
			ajaxJson.setSuccess(false);
			LOGGER.error("记账确认异常", e);
		}
		return ajaxJson;
	}
	
	/**
	 * @Description:嘉银回款合同审核
	 * @param vo
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/contractApprove")
	public AjaxJson contractApprove(Integer page, Integer rows, JYReceivedPaymentsVO vo,HttpServletRequest request, HttpServletResponse response) {
		AjaxJson ajaxJson = null;
		Double moneyCount = null;
		PageVO pageVO = null;
		try {
			ajaxJson = new AjaxJson();
			moneyCount = new Double(0);
			pageVO = new PageVO();
			pageVO.setPageSize(10);
			pageVO.setPageNo(1);
			vo.setApproveBy(CurrentUser.getUser().getUsername());
			//记账确认
			jyReceivedPaymentsServer.approveContract(vo);
			//修改合同查询
			pageVO = jyReceivedPaymentsServer.queryJYReceivedPaymentsDetail(pageVO, vo);
			ajaxJson.setObj(pageVO);
			ajaxJson.setSuccess(true);
			if (pageVO.getTotal()>0) {
				for (Map<String,Object> map: pageVO.getRows()) {
					moneyCount+=(Double.parseDouble(map.get("loanAmount").toString()));
				}
				ajaxJson.setMsg("合同审核成功!");
				pageVO.getRows().get(0).put("moneyCount",moneyCount);
				pageVO.getRows().get(0).put("contractCount",pageVO.getTotal());
			}else{
				ajaxJson.setMsg("暂无匹配合同可审核!");
			}
		} catch (Exception e) {
			ajaxJson.setMsg("系统异常,合同审核失败!");
			ajaxJson.setObj(pageVO);
			ajaxJson.setSuccess(false);
			LOGGER.error("合同审核异常", e);
		}
		return ajaxJson;
		
	}
	
	/**
	 * @Description:查询嘉银回款合同数
	 */
	@RequestMapping("queryJYReceivedPaymentsCount")
	public void queryJYReceivedPaymentsCount(JYReceivedPaymentsVO vo,HttpServletRequest request, HttpServletResponse response){
		//统计合同总数
		Map<String,Object> map = jyReceivedPaymentsServer.queryJYReceivedPaymentsCount(vo);
		ResponseUtil.sendJSON(response, map);
	}
	
	/**
	 * @Description:查询嘉银回款数据记账状态
	 * @param vo
	 * @param request
	 * @param response
	 */
	@RequestMapping("queryJYReceivedPaymentsContract")
	public void queryJYReceivedPaymentsContract(JYReceivedPaymentsVO vo,HttpServletRequest request, HttpServletResponse response){
		//查询匹配未到期合同
		vo=changeVO(vo);
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		if(vo.getContractsArray()!=null&&vo.getContractsArray().length>0){
			for (int i = 0; i < vo.getContractsArray().length; i++) {
				vo.setContracts(vo.getContractsArray()[i]);
				vo.setSequence(vo.getSequenceArray()[i]);
				list.add(jyReceivedPaymentsServer.queryJYReceivedPaymentsDetailFindAll(vo).get(0));
			}
		}else{
			list=jyReceivedPaymentsServer.queryJYReceivedPaymentsDetailFindAll(vo);
		}
		for (Map<String,Object> overdueMap: list) {
			if (overdueMap.containsKey("keepAccountsStatus")) {
				String keepAccountsStatus = StringUtil.isNullOrEmpty(overdueMap.get("keepAccountsStatus"));
				if (keepAccountsStatus.equals("1")){
					ResponseUtil.sendString(response,keepAccountsStatus );
					return;
				}
			}
		}
		ResponseUtil.sendString(response,"");
	}
	
	
	@RequestMapping("queryGatherCount")
	public void queryGatherCount(JYReceivedPaymentsVO vo,HttpServletRequest request, HttpServletResponse response){
		//统计合同总数
		Map<String,Object> map = null;
		try {
			map = jyReceivedPaymentsServer.queryGatherCount(vo);
		} catch (Exception e) {
			LOGGER.error("合同统计异常!", e);
		}
		ResponseUtil.sendJSON(response, map);
	}
	
	/**
	 * 
	 * @Description 嘉银回款汇总合同导出
	 * @throws ServletException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/gatherExport")
	public AjaxJson gatherExport(HttpServletRequest request, HttpServletResponse response,JYReceivedPaymentsVO vo) throws ServletException, IOException{
		AjaxJson ajaxJson = null;
		try {
			ajaxJson = new AjaxJson();
			String fileName = "导出Excel-嘉银回款汇总.xlsx";  
			List<Map<String,Object>> list = jyReceivedPaymentsServer.gatherExport(vo);
			
			SXSSFWorkbook wb = new SXSSFWorkbook(1000); // 这里100是在内存中的数量，如果大于此数量时，会写到硬盘，以避免在内存导致内存溢出  
			Sheet sh = wb.createSheet();  
			if (list.size()==0) {
				request.getSession().setAttribute("exportedFlag", "true");
				ajaxJson.setMsg("暂无数据可导出!");
				return ajaxJson;
			}
			for (int i = 0; i < list.size()+1; i++) { 
			    Row row = sh.createRow(i);
			    if (i==0) {
			    	row.createCell(0).setCellValue("记账日期");  
				    
				    //第一行第2列  
			    	row.createCell(1).setCellValue("统计日期");  
				
				    //第一行第3列  
			    	row.createCell(2).setCellValue("客户渠道");  
				
				    //第一行第4列  
			    	row.createCell(3).setCellValue("业务模式");  
				
				    //第一行第5列  
			    	row.createCell(4).setCellValue("贷款类型");  
				    
				    //第一行第6列  
			    	row.createCell(5).setCellValue("贷款子类型");  
				    
				    //第一行第7列  
			    	row.createCell(6).setCellValue("省"); 
				    
				    //第一行第8列  
			    	row.createCell(7).setCellValue("城市");  
			    	
			    	//第一行第9列  
			    	row.createCell(8).setCellValue("城市编码");  
				    
				    //第一行第10列  
			    	row.createCell(9).setCellValue("当月应回款日"); 
				    
				    //第一行第11列
			    	row.createCell(10).setCellValue("实回款日"); 
			    	
			    	//第一行第12列
			    	row.createCell(11).setCellValue("注册日"); 
				    
				    //第一行第13列
			    	row.createCell(12).setCellValue("保证方"); 
				    
				    //第一行第14列  
			    	row.createCell(13).setCellValue("资金方"); 
				    
				    //第一行第15列  
			    	row.createCell(14).setCellValue("代垫方"); 
				    
				    //第一行第16列  
			    	row.createCell(15).setCellValue("资产所属方"); 
				    
				    //第一行第17列
			    	row.createCell(16).setCellValue("数据来源表单"); 
				    
				    //第一行第18列  
			    	row.createCell(17).setCellValue("本金"); 
				    
				    //第一行第19列  
			    	row.createCell(18).setCellValue("利息"); 
			    	
				    //第一行第20列  
			    	row.createCell(19).setCellValue("印花税");
				    
				    //第一行第21列  
			    	row.createCell(20).setCellValue("总计"); 
				    
			    	continue;
				}
		        row.createCell(0).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("keepAccountsTime")));
			    row.createCell(1).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("accordDate")));  
			    row.createCell(2).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("suretype")));  
			    row.createCell(3).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("businessmodel")));  
			    row.createCell(4).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("productId")));  
			    row.createCell(5).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("subProductType")));  
			    row.createCell(6).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("province")));  
			    row.createCell(7).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("city")));  
			    row.createCell(8).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("cityCode")));  
			    row.createCell(9).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("payDate")));  
			    row.createCell(10).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("repayDate")));  
			    row.createCell(11).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("registrationDate")));  
			    row.createCell(12).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("guaranteeParty")));  
			    row.createCell(13).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("creditPerson")));  
			    row.createCell(14).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("debours")));  
			    row.createCell(15).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("assetBelong")));  
			    row.createCell(16).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("sjlybd")));  
			    row.createCell(17).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("payPrincipalAmt")));  
			    row.createCell(18).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("payInteamt")));  
			    row.createCell(19).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("a11")));  
			    row.createCell(20).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("amount")));  
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
	
	
	@RequestMapping("queryDetailCount")
	public void queryDetailCount(JYReceivedPaymentsVO vo,HttpServletRequest request, HttpServletResponse response){
		//统计合同总数
		Map<String,Object> map = null;
		try {
			map = jyReceivedPaymentsServer.queryDetailCount(vo);
		} catch (Exception e) {
			LOGGER.error("合同统计异常!", e);
		}
		ResponseUtil.sendJSON(response, map);
	}
	
	/**
	 * 
	 * @Description 嘉银回款明细合同导出
	 * @throws ServletException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/detailExport")
	public AjaxJson detailExport(HttpServletRequest request, HttpServletResponse response,JYReceivedPaymentsVO vo) throws ServletException, IOException{
		AjaxJson ajaxJson = null;
		try {
			ajaxJson = new AjaxJson();
			String fileName = "导出Excel-嘉银回款明细.xlsx";  
			List<Map<String,Object>> list = jyReceivedPaymentsServer.queryJYReceivedPaymentsDetailFindAll(vo);
			
			SXSSFWorkbook wb = new SXSSFWorkbook(1000); // 这里100是在内存中的数量，如果大于此数量时，会写到硬盘，以避免在内存导致内存溢出  
			Sheet sh = wb.createSheet();  
			if (list.size()==0) {
				request.getSession().setAttribute("exportedFlag", "true");
				ajaxJson.setMsg("暂无数据可导出!");
				return ajaxJson;
			}
			for (int i = 0; i < list.size()+1; i++) { 
				Row row = sh.createRow(i);
				if (i==0) {
					row.createCell(0).setCellValue("记账日期");  
					
					//第一行第2列  
					row.createCell(1).setCellValue("合同号");  
					
					//第一行第3列  
					row.createCell(2).setCellValue("客户姓名");  
					
					//第一行第4列  
					row.createCell(3).setCellValue("注册日");  
					
					//第一行第5列  
					row.createCell(4).setCellValue("门店代码");  
					
					//第一行第6列  
					row.createCell(5).setCellValue("商户代码");  
					
					//第一行第7列  
					row.createCell(6).setCellValue("SA_ID"); 
					
					//第一行第8列  
					row.createCell(7).setCellValue("商品范畴");  
					
					//第一行第9列  
					row.createCell(8).setCellValue("客户渠道");  
					
					//第一行第10列  
					row.createCell(9).setCellValue("业务模式"); 
					
					//第一行第11列
					row.createCell(10).setCellValue("产品类型"); 
					
					//第一行第12列
					row.createCell(11).setCellValue("产品子类型"); 
					
					//第一行第13列
					row.createCell(12).setCellValue("省"); 
					
					//第一行第14列  
					row.createCell(13).setCellValue("市"); 
					
					//第一行第15列  
					row.createCell(14).setCellValue("市编码"); 
					
					//第一行第16列  
					row.createCell(15).setCellValue("资金方"); 
					
					//第一行第17列
					row.createCell(16).setCellValue("强制日期"); 
					
					//第一行第18列  
					row.createCell(17).setCellValue("应还日"); 
					
					//第一行第19列  
					row.createCell(18).setCellValue("期次"); 
					
					//第一行第20列  
					row.createCell(19).setCellValue("还款类型");
					
					//第一行第21列  
					row.createCell(20).setCellValue("本金"); 
					
					//第一行第22列  
					row.createCell(21).setCellValue("利息"); 
					
					//第一行第23列  
					row.createCell(22).setCellValue("本息"); 
					
					//第一行第24列  
					row.createCell(23).setCellValue("贷款本金"); 
					
					//第一行第25列  
					row.createCell(24).setCellValue("回款日期"); 
					
					//第一行第26列  
					row.createCell(25).setCellValue("保证方"); 
					
					//第一行第27列  
					row.createCell(26).setCellValue("登记日期"); 
					
					//第一行第28列  
					row.createCell(27).setCellValue("资产所属方"); 
					
					continue;
				}
				row.createCell(0).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("keepAccountsTime")));
				row.createCell(1).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("contractNo")));  
				row.createCell(2).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("clientName")));  
				row.createCell(3).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("registrationDate")));  
				row.createCell(4).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("sno")));  
				row.createCell(5).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("rno")));  
				row.createCell(6).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("sa_id")));  
				row.createCell(7).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("productcategory")));  
				row.createCell(8).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("suretype")));  
				row.createCell(9).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("businessmodel")));  
				row.createCell(10).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("productId")));  
				row.createCell(11).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("subProductType")));  
				row.createCell(12).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("province")));  
				row.createCell(13).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("city")));  
				row.createCell(14).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("cityCode")));  
				row.createCell(15).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("creditPerson")));  
				row.createCell(16).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("cdate")));  
				row.createCell(17).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("payDate")));  
				row.createCell(18).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("sequence")));  
				row.createCell(19).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("payType")));  
				row.createCell(20).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("payprinciPalamt")));  
				row.createCell(21).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("payInteamt")));  
				row.createCell(22).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("payment")));  
				row.createCell(23).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("loanAmount")));  
				row.createCell(24).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("repayDate")));  
				row.createCell(25).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("guaranteeParty")));  
				row.createCell(26).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("registrationDate")));  
				row.createCell(27).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("assetBelong")));  
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
	
	public JYReceivedPaymentsVO changeVO(JYReceivedPaymentsVO vo){
		/**
		 * contains : true  记账多个合同
		 * contains : false 记账单个合同
		 */
		if(StringUtil.isNullOrEmpty(vo.getContracts()).contains(",")){
			vo.setContractsArray(vo.getContracts().split(","));
		}
		if(StringUtil.isNullOrEmpty(vo.getSequence()).contains(",")){
			vo.setSequenceArray(vo.getSequence().split(","));
		}
		return vo;
	}
	
}
