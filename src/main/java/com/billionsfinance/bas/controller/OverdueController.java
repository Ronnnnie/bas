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
import com.billionsfinance.bas.entity.OverdueVO;
import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.server.IOverdueServer;
import com.billionsfinance.bas.server.impl.OverdueServer;
import com.billionsfinance.bas.util.AjaxJson;
import com.billionsfinance.bas.util.ResponseUtil;
import com.billionsfinance.bas.util.StringUtil;

/**
 * @ClassName: OverdueController
 * @Description: 逾期合同Controller
 * @author FMZhou
 * 
 * Copyright: Copyright (c) 2016 2016年11月25日 下午14:25:09 Company:佰仟金融
 */
@Controller
@RequestMapping("/overdueServer")
public class OverdueController {

	/** 日志记录 */
	private static final Log LOGGER = LogFactory.getLog(OverdueController.class);

	private static final IOverdueServer overdueServer = new OverdueServer();

	/**
	 * Description: 跳转逾期明细页面
	 */
	@RequestMapping("/toOverdueDetail")
	public String toOverdueDetail() {
		return "overdue/overdueDetail";	
	}
	
	/**
	 * Description: 跳转逾期汇总页面
	 */
	@RequestMapping("/toOverdueGather")
	public String toOverdueGather() {
		return "overdue/overdueGather";
	}
	
	/**
	 * @Description 逾期明细查询(分页)
	 * @param vo
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryOverdueDetail")
	public void queryOverdueDetail(Integer page, Integer rows, OverdueVO vo,HttpServletRequest request, HttpServletResponse response) {
		PageVO pageVO = new PageVO();
		try {
			pageVO.setPageSize(rows);
			pageVO.setPageNo(page);
			pageVO = overdueServer.queryOverdueDetail(pageVO, vo);
		} catch (Exception e) {
			LOGGER.error("查询逾期明细合同失败!",e);
		}finally {
			ResponseUtil.sendJSON(response, pageVO);
		}
	}
	
	/**
	 * @Description 逾期汇总查询
	 * @param vo
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryOverdueGather")
	public void queryOverdueGather(Integer page, Integer rows, OverdueVO vo,HttpServletRequest request, HttpServletResponse response) {
		PageVO pageVO = new PageVO();
		try {
			pageVO.setPageSize(rows);
			pageVO.setPageNo(page);
			pageVO = overdueServer.queryOverdueGather(pageVO, vo);
		} catch (Exception e) {
			LOGGER.error("查询逾期汇总合同失败!", e);
		}finally {
			ResponseUtil.sendJSON(response, pageVO);
		}
	}
	
	
	/**
	 * @Description 逾期记账确认
	 * @param vo
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/accountingMark")
	public AjaxJson accountingMark(Integer page, Integer rows, OverdueVO vo,HttpServletRequest request, HttpServletResponse response) {
		AjaxJson ajaxJson = null;
		PageVO pageVO = null;
		try {
			ajaxJson = new AjaxJson();
			pageVO = new PageVO();
			pageVO.setPageSize(10);
			pageVO.setPageNo(1);
			vo.setInAccountBy(CurrentUser.getUser().getUsername());
			//记账确认
			int resultLine = overdueServer.accountingMark(vo);
			if (resultLine > 0 ) {
				if(!("").equals(vo.getStartInAccountDate())&&vo.getStartInAccountDate()!=null){
					vo.setStartInAccountDate(vo.getUpdateDate());
				}
				if(!("").equals(vo.getEndInAccountDate())&&vo.getEndInAccountDate()!=null){
					vo.setEndInAccountDate(vo.getUpdateDate());
				}
			}
			//修改合同查询
			pageVO = overdueServer.queryOverdueDetail(pageVO,vo);
			ajaxJson.setObj(pageVO);
			ajaxJson.setSuccess(true);
			if (pageVO.getTotal()>0) {
				ajaxJson.setMsg("记账确认标记成功!");
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
	 * @Description 逾期记账确认
	 * @param vo
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/selectAccountingMark")
	public AjaxJson selectAccountingMark(OverdueVO vo,HttpServletRequest request, HttpServletResponse response) {
		AjaxJson ajaxJson = null;
		PageVO pageVO = null;
		List<Map<String,Object>> listArr = null;
		Double payprinciPalamtSum = new Double(0);
		Double payInteamtSum = new Double(0);
		Double a2Sum = new Double(0);
		Double a7Sum = new Double(0);
		Double a12Sum = new Double(0);
		Double a18Sum = new Double(0);
		Double a22Sum = new Double(0);
		Double amountSum = new Double(0);
		try {
			listArr = new ArrayList<Map<String,Object>>();
			vo = changeOverdueVO(vo);
			ajaxJson = new AjaxJson();
			pageVO = new PageVO();
			pageVO.setPageSize(10);
			pageVO.setPageNo(1);
			vo.setInAccountBy(CurrentUser.getUser().getUsername());
			//记账确认
			if(vo.getSeqIdArray()!=null&&vo.getSeqIdArray().length>0){
				for (int i = 0; i < vo.getSerialNoArray().length; i++) {
					vo.setSerialNo(vo.getSerialNoArray()[i]);
					vo.setSeqId(vo.getSeqIdArray()[i]);
					vo.setAssetBelong(vo.getAssetBelongArray()[i]);
					vo.setPayInteamt(vo.getPayInteamtArray()[i]);
					vo.setPayprinciPalamt(vo.getPayprinciPalamtArray()[i]);
					overdueServer.accountingMark(vo);
					listArr.add(overdueServer.queryOverdueDetailFindAll(vo).get(0)) ;
				}
			}else{
				overdueServer.accountingMark(vo);
				listArr=overdueServer.queryOverdueDetailFindAll(vo);
			}
			if (listArr.size() > 0 ) {
				//修改合同查询
				for (Map<String,Object> map: listArr) {
					payprinciPalamtSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("payprinciPalamt"))));
					payInteamtSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("payInteamt"))));
					a2Sum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("a2"))));
					a7Sum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("a7"))));
					a12Sum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("a12"))));
					a18Sum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("a18"))));
					a22Sum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("a22"))));
					amountSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("amount"))));
				}
				pageVO.setRows(listArr);
				pageVO.getRows().get(0).put("payprinciPalamtSum",payprinciPalamtSum);
				pageVO.getRows().get(0).put("payInteamtSum",payInteamtSum);
				pageVO.getRows().get(0).put("a2Sum",a2Sum);
				pageVO.getRows().get(0).put("a7Sum",a7Sum);
				pageVO.getRows().get(0).put("a12Sum",a12Sum);
				pageVO.getRows().get(0).put("a18Sum",a18Sum);
				pageVO.getRows().get(0).put("a22Sum",a22Sum);
				pageVO.getRows().get(0).put("amountSum",amountSum);
				pageVO.getRows().get(0).put("contractCount",listArr.size());
				pageVO.setTotal((long) listArr.size());
				ajaxJson.setObj(pageVO);
				ajaxJson.setSuccess(true);
				ajaxJson.setMsg("记账确认标记成功!");
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
	 * @Description 逾期记账撤销
	 * @param vo
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/cancelAccountingMark")
	public AjaxJson cancelAccountingMark(OverdueVO vo,HttpServletRequest request, HttpServletResponse response) {
		Boolean roleFlag = (Boolean) request.getSession().getAttribute("isRevokeRole");
		if(roleFlag==null||!roleFlag){
			ResponseUtil.sendMessage(response, false, "暂无权限操作此功能!");
			return null;
		}
		Double payprinciPalamtSum = new Double(0);
		Double payInteamtSum = new Double(0);
		Double a2Sum = new Double(0);
		Double a7Sum = new Double(0);
		Double a12Sum = new Double(0);
		Double a18Sum = new Double(0);
		Double a22Sum = new Double(0);
		Double amountSum = new Double(0);
		AjaxJson ajaxJson = null;
		PageVO pageVO = null;
		List<Map<String,Object>> listArr = null;
		try {
			ajaxJson = new AjaxJson();
			pageVO = new PageVO();
			listArr = new ArrayList<Map<String,Object>>();
			pageVO.setPageSize(15);
			pageVO.setPageNo(1);
			vo = changeOverdueVO(vo);
			if(vo.getSerialNoArray()!=null&&vo.getSerialNoArray().length>0){
				for (int i = 0; i < vo.getSerialNoArray().length; i++) {
					vo.setSerialNo(vo.getSerialNoArray()[i]);
					vo.setSeqId(vo.getSeqIdArray()[i]);
					vo.setAssetBelong(vo.getAssetBelongArray()[i]);
					vo.setPayInteamt(vo.getPayInteamtArray()[i]);
					vo.setPayprinciPalamt(vo.getPayprinciPalamtArray()[i]);
					listArr.add(overdueServer.queryOverdueDetail(pageVO,vo).getRows().get(0)) ;
					overdueServer.cancelAccountingMark(vo);
				}
			}else{
				listArr=overdueServer.queryOverdueDetailFindAll(vo);
				overdueServer.cancelAccountingMark(vo);
			}
			boolean flag = true;
			if (listArr.size()>0) {
				for (Map<String,Object> map: listArr) {
					if(!StringUtil.isNullOrEmpty(map.get("inAccountStatus")).equals("1")){
						flag = false;
					}else{
						map.put("inAccountDate", "");
						map.put("inAccountStatus", "2");
					}
					payprinciPalamtSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("payprinciPalamt"))));
					payInteamtSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("payInteamt"))));
					a2Sum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("a2"))));
					a7Sum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("a7"))));
					a12Sum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("a12"))));
					a18Sum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("a18"))));
					a22Sum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("a22"))));
					amountSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("amount"))));
				}
				if (flag) {
					ajaxJson.setMsg("记账撤销成功!");
				}else{
					ajaxJson.setMsg("当前合同存在未记账或已撤销记账的合同,该些合同记账撤销失败!");
				}
				pageVO.setRows(listArr);
				pageVO.getRows().get(0).put("payprinciPalamtSum",payprinciPalamtSum);
				pageVO.getRows().get(0).put("payInteamtSum",payInteamtSum);
				pageVO.getRows().get(0).put("a2Sum",a2Sum);
				pageVO.getRows().get(0).put("a7Sum",a7Sum);
				pageVO.getRows().get(0).put("a12Sum",a12Sum);
				pageVO.getRows().get(0).put("a18Sum",a18Sum);
				pageVO.getRows().get(0).put("a22Sum",a22Sum);
				pageVO.getRows().get(0).put("amountSum",amountSum);
				pageVO.getRows().get(0).put("contractCount",listArr.size());
				ajaxJson.setObj(pageVO);
				ajaxJson.setSuccess(true);
			}else{
				ajaxJson.setMsg("暂无匹配数据可修改!");
			}
		} catch (Exception e) {
			ajaxJson.setMsg("系统异常,记账撤销败!");
			ajaxJson.setObj(pageVO);
			ajaxJson.setSuccess(false);
			LOGGER.error("记账撤销异常", e);
		}
		return ajaxJson;
	}
	
	/**
	 * 
	 * @Description 逾期明细合同导出
	 * @throws ServletException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/overdueDetailExcelExport")
	public AjaxJson overdueDetailExcelExport(HttpServletRequest request, HttpServletResponse response,OverdueVO overdueVO) throws ServletException, IOException{
		AjaxJson ajaxJson = null;
		try {
			ajaxJson = new AjaxJson();
			String fileName = "导出Excel-逾期明细.xlsx";  
			List<Map<String,Object>> list = overdueServer.overdueDetailExport(overdueVO);
			
			SXSSFWorkbook wb = new SXSSFWorkbook(1000); // 这里100是在内存中的数量，如果大于此数量时，会写到硬盘，以避免在内存导致内存溢出  
			Sheet sh = wb.createSheet();  
			if (list.size()==0) {
				request.getSession().setAttribute("exportedFlag", "true");
				ajaxJson.setMsg("暂无数据可导出!");
				return ajaxJson;
			}
			/*for (int i = 0; i < list.size(); i++) {
				sh.setColumnWidth(i,5000); 
			}*/
			for (int i = 0; i < list.size()+1; i++) { 
			    Row row = sh.createRow(i);
			    if (i==0) {
			    	row.createCell(0).setCellValue("记账期间");  
				    
				    //第一行第2列  
			    	row.createCell(1).setCellValue("统计日期");  
				
				    //第一行第3列  
			    	row.createCell(2).setCellValue("合同号");  
				
				    //第一行第4列  
			    	row.createCell(3).setCellValue("客户渠道");  
				
				    //第一行第5列  
			    	row.createCell(4).setCellValue("业务模式");  
				    
				    //第一行第6列  
			    	row.createCell(5).setCellValue("贷款类型");  
				    
				    //第一行第7列  
			    	row.createCell(6).setCellValue("贷款子类型"); 
				    
				    //第一行第8列  
			    	row.createCell(7).setCellValue("期次");  
				    
				    //第一行第9列  
			    	row.createCell(8).setCellValue("市"); 
				    
				    //第一行第10列
			    	row.createCell(9).setCellValue("城市编码"); 
				    
				    //第一行第11列
			    	row.createCell(10).setCellValue("还款类型"); 
				    
				    //第一行第12列  
			    	row.createCell(11).setCellValue("应还日"); 
				    
				    //第一行第13列  
			    	row.createCell(12).setCellValue("转让日"); 
				    
				    //第一行第14列  
			    	row.createCell(13).setCellValue("代偿日"); 
				    
				    //第一行第15列
			    	row.createCell(14).setCellValue("赎回日"); 
				    
				    //第一行第16列  
			    	row.createCell(15).setCellValue("保险理赔日"); 
				    
				    //第一行第17列  
			    	row.createCell(16).setCellValue("代垫方"); 
				    //第一行第18列  
			    	row.createCell(17).setCellValue("资产所属方");
				    
				    //第一行第19列  
			    	row.createCell(18).setCellValue("保证方"); 
				    
				    //第一行第20列  
			    	row.createCell(19).setCellValue("逾期本金"); 
				    
				    //第一行第21列  
			    	row.createCell(20).setCellValue("逾期利息"); 
				    
				    //第一行第22列  
			    	row.createCell(21).setCellValue("逾期客户服务费"); 
				    
				    //第一行第23列  
			    	row.createCell(22).setCellValue("逾期账户管理费"); 
				    
				    //第一行第24列  
			    	row.createCell(23).setCellValue("逾期增值服务费");
				    
				    //第一行第25列
			    	row.createCell(24).setCellValue("逾期随心还服务费");
				    
				    //第一行第26列		    
				    row.createCell(25).setCellValue("逾期佰保袋服务费"); 
			    	
			    	//第一行第27列
			    	row.createCell(26).setCellValue("合计（此表单金额合计）"); 
			    	
			    	//第一行第28列
			    	row.createCell(27).setCellValue("逾期天数"); 
			    	
			    	//第一行第29列
			    	row.createCell(28).setCellValue("逾期描述"); 
			    	
			    	//第一行第30列
			    	row.createCell(29).setCellValue("五级分类"); 
			    	
			    	//第一行第31列
			    	row.createCell(30).setCellValue("注册日期"); 
			    	continue;
				}
		        row.createCell(0).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("inAccountDate")));
			    row.createCell(1).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("accordDate")));  
			    row.createCell(2).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("serialNo")));  
			    row.createCell(3).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("sureType")));  
			    row.createCell(4).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("businessModel")));  
			    row.createCell(5).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("productId")));  
			    row.createCell(6).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("subProductType")));  
			    row.createCell(7).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("seqId")));  
			    row.createCell(8).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("city")));  
			    row.createCell(9).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("cityCode")));  
			    row.createCell(10).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("payType")));  
			    row.createCell(11).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("shouldAlsoDate")));  
			    row.createCell(12).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("deliveryDate")));  
			    row.createCell(13).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("dcDate")));  
			    row.createCell(14).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("shDate")));  
			    row.createCell(15).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("lpDate")));  
			    row.createCell(16).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("debours")));  
			    row.createCell(17).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("assetBelong")));  
			    row.createCell(18).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("guaranteeParty")));  
			    row.createCell(19).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("payprinciPalamt")));  
			    row.createCell(20).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("payInteamt")));  
			    row.createCell(21).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("a2")));  
			    row.createCell(22).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("a7")));  
			    row.createCell(23).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("a12")));  
			    row.createCell(24).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("a18")));  
			    row.createCell(25).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("a22")));  
			    row.createCell(26).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("amount")));
			    row.createCell(27).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("overdueDays")));
			    row.createCell(28).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("overdueRemark")));
			    row.createCell(29).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("classFy")));
			    row.createCell(30).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("registrationDate")));
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
	
	/**
	 * 
	 * @Description  逾期汇总合同导出
	 * @throws ServletException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/overdueGatherExcelExport")
	public AjaxJson overdueGatherExcelExport(HttpServletRequest request, HttpServletResponse response,OverdueVO vo) throws ServletException, IOException{
		//导出前将导出标识设置为true
		
		AjaxJson ajaxJson = null;
		try {
			
			ajaxJson = new AjaxJson();
			vo.setStartInAccountDate(StringUtil.stringFormatter(vo.getStartInAccountDate()));
			vo.setEndInAccountDate(StringUtil.stringFormatter(vo.getEndInAccountDate()));
			vo.setBusinessModel(StringUtil.stringFormatter(vo.getBusinessModel()));
			vo.setSubProductType(StringUtil.stringFormatter(vo.getSubProductType()));
			vo.setCity(StringUtil.stringFormatter(vo.getCity()));
			vo.setOverdueremark(StringUtil.stringFormatter(vo.getOverdueremark()));
			vo.setClassfy(StringUtil.stringFormatter(vo.getClassfy()));
			vo.setCanceltype(StringUtil.stringFormatter(vo.getCanceltype()));
			vo.setAssetBelong(StringUtil.stringFormatter(vo.getAssetBelong()));
			vo.setGuaranteeParty(StringUtil.stringFormatter(vo.getGuaranteeParty()));
			String fileName = "导出Excel-逾期汇总.xlsx";  
			List<Map<String,Object>> list = overdueServer.queryOverdueGatherFindAll(vo);
			
			SXSSFWorkbook wb = new SXSSFWorkbook(1024); // 这里100是在内存中的数量，如果大于此数量时，会写到硬盘，以避免在内存导致内存溢出  
			Sheet sh = wb.createSheet();
			if (list.size()==0) {
				request.getSession().setAttribute("exportedFlag", "true");
				return ajaxJson;
			}
			for (int i = 0; i < list.size()+1; i++) {  
				///sh.setColumnWidth(i,4000);
			    Row row = sh.createRow(i);
			    if (i==0) {
			    	row.createCell(0).setCellValue("记账期间");  
				    
			    	row.createCell(1).setCellValue("统计日期");  
				
			    	row.createCell(2).setCellValue("客户渠道");  
				
			    	row.createCell(3).setCellValue("业务模式");  
				
			    	row.createCell(4).setCellValue("贷款子类型");  
				    
			    	row.createCell(5).setCellValue("省份");  
				    
			    	row.createCell(6).setCellValue("城市");  
				    
			    	row.createCell(7).setCellValue("城市编码"); 
				    
			    	row.createCell(8).setCellValue("逾期天数");
				    
			    	row.createCell(9).setCellValue("逾期描述");
			    	
			    	row.createCell(10).setCellValue("五级分类"); 
			    	
			    	row.createCell(11).setCellValue("是否取消分期期次"); 
				    
			    	row.createCell(12).setCellValue("代垫方"); 
				    
			    	row.createCell(13).setCellValue("资产所属方"); 
				    
			    	row.createCell(14).setCellValue("保证方"); 
				    
			    	row.createCell(15).setCellValue("逾期本金"); 
				   
			    	row.createCell(16).setCellValue("逾期利息"); 
				    
			    	row.createCell(17).setCellValue("逾期客户服务费"); 
				    
			    	row.createCell(18).setCellValue("逾期账户管理费"); 
				    
			    	row.createCell(19).setCellValue("逾期增值服务费"); 
				    
			    	row.createCell(20).setCellValue("逾期随心还服务费"); 
				    
			    	row.createCell(21).setCellValue("逾期佰保袋服务费"); 
				    
			    	row.createCell(22).setCellValue("合计（此表单金额合计）");
				}else{
				    row.createCell(0).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("inAccountDate")));
				    row.createCell(1).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("accordDate")));  
				    row.createCell(2).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("sureType")));  
				    row.createCell(3).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("businessModel")));  
				    row.createCell(4).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("subProductType")));  
				    row.createCell(5).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("province")));  
				    row.createCell(6).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("city")));  
				    row.createCell(7).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("cityCode")));  
				    row.createCell(8).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("overdueDays")));  
				    row.createCell(9).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("overdueRemark")));  
				    row.createCell(10).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("classFy")));
				    row.createCell(11).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("canceltype")));
				    row.createCell(12).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("debours")));  
				    row.createCell(13).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("assetBelong")));  
				    row.createCell(14).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("guaranteeParty")));  
				    row.createCell(15).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("payprinciPalamt")));  
				    row.createCell(16).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("payInteamt")));  
				    row.createCell(17).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("a2")));  
				    row.createCell(18).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("a7")));  
				    row.createCell(19).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("a12")));  
				    row.createCell(20).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("a18")));  
				    row.createCell(21).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("a22")));  
				    row.createCell(22).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("amount")));  
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
	
	@RequestMapping("queryOverdueDetailCount")
	public void queryOverdueDetailCount(OverdueVO vo,HttpServletRequest request, HttpServletResponse response){
		//统计合同总数
		vo = changeOverdueVO(vo);
		Map<String,Object> map = null;
		try {
			map = overdueServer.queryOverdueDetailCount(vo);
		} catch (Exception e) {
			LOGGER.error("合同统计异常!", e);
		}
		ResponseUtil.sendJSON(response, map);
	}
	
	@RequestMapping("queryOverdueGatherCount")
	public void queryOverdueGatherCount(OverdueVO vo,HttpServletRequest request, HttpServletResponse response){
		//统计合同总数
		Map<String,Object> map = null;
		try {
			map = overdueServer.queryOverdueGatherCount(vo);
		} catch (Exception e) {
			LOGGER.error("合同统计异常!", e);
		}
		ResponseUtil.sendJSON(response, map);
	}
	
	/**
	 * @copyright 查询合同记账状态
	 * @param vo
	 * @param request
	 * @param response
	 */
	@RequestMapping("queryOverdueContract")
	public void queryOverdueContract(OverdueVO vo,HttpServletRequest request, HttpServletResponse response){
		//统计合同总数
		try {
			List<Map<String,Object>> listArr = new ArrayList<Map<String,Object>>();
			vo=changeOverdueVO(vo);
			if(vo.getSeqIdArray()!=null&&vo.getSeqIdArray().length>0){
				for (int i = 0; i < vo.getSerialNoArray().length; i++) {
					vo.setSerialNo(vo.getSerialNoArray()[i]);
					vo.setSeqId(vo.getSeqIdArray()[i]);
					vo.setAssetBelong(vo.getAssetBelongArray()[i]);
					vo.setPayInteamt(vo.getPayInteamtArray()[i]);
					vo.setPayprinciPalamt(vo.getPayprinciPalamtArray()[i]);
					listArr.add(overdueServer.queryOverdueContract(vo).get(0)) ;
				}
			}else{
				listArr = overdueServer.queryOverdueContract(vo);
			}
			for (Map<String,Object> overdueMap: listArr) {
				if (overdueMap.containsKey("inAccountStatus")) {
					String inAccountStatus = StringUtil.isNullOrEmpty(overdueMap.get("inAccountStatus"));
					if (inAccountStatus.equals("1")){
						ResponseUtil.sendString(response,inAccountStatus );
						return;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("查询合同记账状态异常!", e);
		}
		ResponseUtil.sendString(response,"");
	}
	
	public OverdueVO changeOverdueVO(OverdueVO vo){
		/**
		 * contains : true  记账多个合同
		 * contains : false 记账单个合同
		 */
		if(StringUtil.isNullOrEmpty(vo.getSeqId()).contains(",")){
			vo.setSeqIdArray(vo.getSeqId().split(","));
			vo.setSeqId("");
		}
		if(StringUtil.isNullOrEmpty(vo.getSerialNo()).contains(",")){
			vo.setSerialNoArray(vo.getSerialNo().split(","));
			vo.setSerialNo("");
		}
		if(StringUtil.isNullOrEmpty(vo.getAssetBelong()).contains(",")){
			vo.setAssetBelongArray(vo.getAssetBelong().split(","));
			vo.setAssetBelong("");
		}
		if(StringUtil.isNullOrEmpty(vo.getPayprinciPalamt()).contains(",")){
			vo.setPayprinciPalamtArray(vo.getPayprinciPalamt().split(","));
			vo.setPayprinciPalamt("");
		}
		if(StringUtil.isNullOrEmpty(vo.getPayInteamt()).contains(",")){
			vo.setPayInteamtArray(vo.getPayInteamt().split(","));
			vo.setPayInteamt("");
		}
		return vo;
	}
}
