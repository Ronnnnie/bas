package com.billionsfinance.bas.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.billionsfinance.bas.entity.CurrentExpiredVO;
import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.server.ICurrentPayServer;
import com.billionsfinance.bas.server.impl.CurrentPayServer;
import com.billionsfinance.bas.util.AjaxJson;
import com.billionsfinance.bas.util.ResponseUtil;
import com.billionsfinance.bas.util.StringUtil;



/**
 * @ClassName: CurrentPayController
 * @Description: 当期应付资产方表控制器
 * @author zsp
 * @Copyright: Copyright (c) 2016 2016年10月10日 下午16:05:09 Company:佰仟金融
 */
@Controller
@RequestMapping("/currentPayServer")
public class CurrentPayController {

	/** 日志记录 */
	private static final Log LOGGER = LogFactory.getLog(CurrentPayController.class);
	
	private static final ICurrentPayServer currentPayServer = new CurrentPayServer();
	
	//@Autowired
	//private ThreadPoolExecutor threadPoolExecutor;
	
	//@Autowired
	//private JdbcTemplate jdbcTemplate;

	@RequestMapping("/toCurrentPayList")
	public String toBusinessCheckList(HttpServletRequest request, HttpServletResponse response) {
		return "currentPay/currentPayList";
	}
	
	@RequestMapping("/toCurrentPayListSum")
	public String toBusinessCheckListSum(HttpServletRequest request, HttpServletResponse response) {
		return "currentPay/currentPayListSum";
	}
	
	/**
	 * @Description:查询当期应付资产方表数据
	 * @param vo
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryCurrentPayList")
	public void queryCurrentPayList(Integer page,Integer rows,CurrentExpiredVO currentExpiredVO,HttpServletRequest request, HttpServletResponse response) {
		PageVO pageVO = new PageVO();
		try {
			pageVO.setPageSize(rows);
			pageVO.setPageNo(page);
			pageVO = currentPayServer.queryCurrentPay(pageVO, currentExpiredVO);
		} catch (Exception e) {
			LOGGER.error("查询当期应付资产方表失败!", e);
		}finally {
			ResponseUtil.sendJSON(response, pageVO);
		}
	}
	
	/**
	 * @Description:查询当期应付资产方表数量
	 * @param vo
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryCurrentPayListTotal")
	public void queryCurrentPayListTotal(Integer page,Integer rows,CurrentExpiredVO currentExpiredVO,HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			map = currentPayServer.queryCurrentPayTotal(currentExpiredVO);
		} catch (Exception e) {
			LOGGER.error("查询当期到期表失败!", e);
		}finally {
			ResponseUtil.sendJSON(response, map);
		}
	}
	
	/**
	 * @Description:查询当期应付资产方汇总表数据
	 * @param vo
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryCurrentPayListSum")
	public void queryCurrentPayListSum(Integer page,Integer rows,CurrentExpiredVO currentExpiredVO,HttpServletRequest request, HttpServletResponse response) {
		PageVO pageVO = new PageVO();
		try {
			pageVO.setPageSize(rows);
			pageVO.setPageNo(page);
			pageVO = currentPayServer.queryCurrentPaySum(pageVO, currentExpiredVO);
		} catch (Exception e) {
			LOGGER.error("查询当期应付资产方汇总表失败!", e);
		}finally {
			ResponseUtil.sendJSON(response, pageVO);
		}
	}
	
	/**
	 * @Description:查询当期应付资产方汇总表数量
	 * @param vo
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryCurrentPayListSumTotal")
	public void queryCurrentPayListSumTotal(Integer page,Integer rows,CurrentExpiredVO currentExpiredVO,HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			map = currentPayServer.queryCurrentPaySumTotal(currentExpiredVO);
		} catch (Exception e) {
			LOGGER.error("查询当期到期表失败!", e);
		}finally {
			ResponseUtil.sendJSON(response, map);
		}
	}
	
	/**
	 * @Description:检测当期应付资产方表数量
	 * @param vo
	 * @param request
	 * @param response
	 */
	@RequestMapping("/checkCurrentPay")
	public void checkCurrentPay(CurrentExpiredVO currentExpiredVO, HttpServletRequest request,
			HttpServletResponse response) {
		try {

			// 判断角色名称在同一个系统中不能重复

			if (currentExpiredVO==null) {
				ResponseUtil.sendMessage(response, false, "参数数据出错");
				return;
			}

			String currentUser = CurrentUser.getUser().getUsername();
			currentExpiredVO.setInaccountby(currentUser);
			
			Integer count = currentPayServer.checkCurrentPay(currentExpiredVO);
			if(count!=null){
				if(count>0){
					ResponseUtil.sendMessage(response, false, "所选合同包含已记账合同");
				}else{
					ResponseUtil.sendMessage(response, true, "通过");
				}
			}else{
				ResponseUtil.sendMessage(response, false, "检查出错");
			}

			
		} catch (Exception e) {
			LOGGER.error("检测是否已记账出错", e);
			ResponseUtil.sendMessage(response, false, "系统出错");
		}
	}
	
	/**
	 * @Description:更新当期应付资产方表
	 * @param vo
	 * @param request
	 * @param response
	 */
	@RequestMapping("/updateCurrentPay")
	public void updateCurrentPay(CurrentExpiredVO currentExpiredVO, HttpServletRequest request,
			HttpServletResponse response) {
		try {

			// 判断角色名称在同一个系统中不能重复

			if (currentExpiredVO==null) {
				ResponseUtil.sendMessage(response, false, "参数数据出错");
				return;
			}
			currentExpiredVO = changeCurrentExpiredVO(currentExpiredVO);

			String currentUser = CurrentUser.getUser().getUsername();
			currentExpiredVO.setInaccountby(currentUser);
			
			Boolean flag = currentPayServer.updateCurrentPay(currentExpiredVO);

			if(flag){
				ResponseUtil.sendMessage(response, true, "更新成功");
			}else{
				ResponseUtil.sendMessage(response, false, "更新失败");
			}
		} catch (Exception e) {
			LOGGER.error("当期应付资产方表入账失败", e);
			ResponseUtil.sendMessage(response, false, "更新失败");
		}
	}
	
	/**
	 * @Description:导出当期应付资产方表
	 * @param vo
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/excelExport")
	public AjaxJson excelExport(HttpServletRequest request, HttpServletResponse response){
		AjaxJson ajaxJson = new AjaxJson();
		//统计合同总数
		String tempAssert = request.getParameter("assetbelong");
		String assetbelong="";
		if(tempAssert!=null){
			try {
				assetbelong = java.net.URLDecoder.decode(tempAssert, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				assetbelong="";
			} 
		}
		String startPayDate = request.getParameter("startPayDate");
		String endPayDate = request.getParameter("endPayDate");
		String serialno = request.getParameter("serialno");
		String startRegistrationDate = request.getParameter("startRegistrationDate");
		String endRegistrationDate = request.getParameter("endRegistrationDate");
		
		String startInaccountdate = request.getParameter("startInaccountdate");
		String endInaccountdate = request.getParameter("endInaccountdate");
		
		CurrentExpiredVO queryVo = new CurrentExpiredVO();
		queryVo.setAssetbelong(assetbelong);
		queryVo.setStartPayDate(startPayDate);
		queryVo.setEndPayDate(endPayDate);
		queryVo.setSerialno(serialno);
		queryVo.setStartRegistrationDate(startRegistrationDate);
		queryVo.setEndRegistrationDate(endRegistrationDate);
		
		queryVo.setStartInaccountdate(startInaccountdate);
		queryVo.setEndInaccountdate(endInaccountdate);
		
		List<Map<String,Object>> list = currentPayServer.queryCurrentPayAll(queryVo);
		if(list.isEmpty()){
			ajaxJson.setMsg("暂无数据可导出!");
			return ajaxJson;
		}
		String fileName = "当期应付资产方表导出Excel.xlsx";  
	    
	    try {  
			
		    // 定义单元格报头  
		    //String worksheetTitle = "当期应付资产方汇总";  
		
	        
	        SXSSFWorkbook wb = new SXSSFWorkbook(100); // 这里100是在内存中的数量，如果大于此数量时，会写到硬盘，以避免在内存导致内存溢出  
			Sheet sh = wb.createSheet();  
			
			Row row = sh.createRow(0);
	    	row.createCell(0).setCellValue("记账期间");  
		    
		    //第一行第2列  
	    	row.createCell(1).setCellValue("统计日期");  
		
	    	row.createCell(2).setCellValue("合同号");  
	    	
		    //第一行第3列  
	    	row.createCell(3).setCellValue("客户渠道");  
		
		    //第一行第4列  
	    	row.createCell(4).setCellValue("业务模式");  
		
		    //第一行第5列  
	    	row.createCell(5).setCellValue("贷款类型");  
	    	
	    	 //第一行第5列  
	    	row.createCell(6).setCellValue("贷款子类型");  
		    
		    //第一行第6列  
	    	row.createCell(7).setCellValue("城市");  
		    
		    //第一行第7列  
	    	row.createCell(8).setCellValue("城市编码"); 
		    
		    //第一行第8列  
	    	row.createCell(9).setCellValue("还款类型");  
		    
		    //第一行第9列  
	    	row.createCell(10).setCellValue("应还日"); 
	    	
	    	row.createCell(11).setCellValue("期次"); 
		    
	    	row.createCell(12).setCellValue("实还日"); 
		    //第一行第10列
	    	row.createCell(13).setCellValue("转让日"); 
		    
		    //第一行第11列  
	    	row.createCell(14).setCellValue("代偿日"); 
		    
		    //第一行第12列  
	    	row.createCell(15).setCellValue("赎回日"); 
		    
		    //第一行第13列  
	    	row.createCell(16).setCellValue("保险理赔日"); 
		    
		    //第一行第14列  
	    	row.createCell(17).setCellValue("代垫方"); 
		    
		    //第一行第15列
	    	row.createCell(18).setCellValue("资产所属方"); 
		    
		    //第一行第16列  
	    	row.createCell(19).setCellValue("保证方"); 
	    	
		    //第一行第17列  
	    	row.createCell(20).setCellValue("应还本金"); 
		   
		    //第一行第18列  
	    	row.createCell(21).setCellValue("应还利息"); 
		    
		    //第一行第19列  
	    	row.createCell(22).setCellValue("应还印花税"); 
		    
		    //第一行第20列  
	    	row.createCell(23).setCellValue("合计（此表单金额合计）"); 
			for (int i = 0; i < list.size(); i++) {  
				//sh.setColumnWidth(i,5000);
					Row inrow = sh.createRow(i+1);
				    
					inrow.createCell(0).setCellValue(StringUtil.isNullOrEmpty(list.get(i).get("inaccountdate")));
					inrow.createCell(1).setCellValue(StringUtil.isNullOrEmpty(list.get(i).get("accorddate"))); 
					inrow.createCell(2).setCellValue(StringUtil.isNullOrEmpty(list.get(i).get("serialno"))); 
					inrow.createCell(3).setCellValue(StringUtil.isNullOrEmpty(list.get(i).get("suretype")));  
					inrow.createCell(4).setCellValue(StringUtil.isNullOrEmpty(list.get(i).get("businessmodel")));  
					inrow.createCell(5).setCellValue(StringUtil.isNullOrEmpty(list.get(i).get("productid")));  
					inrow.createCell(6).setCellValue(StringUtil.isNullOrEmpty(list.get(i).get("subproducttype")));  
					inrow.createCell(7).setCellValue(StringUtil.isNullOrEmpty(list.get(i).get("city")));  
					inrow.createCell(8).setCellValue(StringUtil.isNullOrEmpty(list.get(i).get("citycode")));  
					inrow.createCell(9).setCellValue(StringUtil.isNullOrEmpty(list.get(i).get("paytype")));  
					inrow.createCell(10).setCellValue(StringUtil.isNullOrEmpty(list.get(i).get("paydate")));  
					inrow.createCell(11).setCellValue(StringUtil.isNullOrEmpty(list.get(i).get("sequence")));  
					inrow.createCell(12).setCellValue(StringUtil.isNullOrEmpty(list.get(i).get("actualpaydate")));  
					inrow.createCell(13).setCellValue(StringUtil.isNullOrEmpty(list.get(i).get("deliverydate")));  
					inrow.createCell(14).setCellValue(StringUtil.isNullOrEmpty(list.get(i).get("dcdate")));  
					inrow.createCell(15).setCellValue(StringUtil.isNullOrEmpty(list.get(i).get("shdate")));  
					inrow.createCell(16).setCellValue(StringUtil.isNullOrEmpty(list.get(i).get("lpdate")));  
					inrow.createCell(17).setCellValue(StringUtil.isNullOrEmpty(list.get(i).get("debours")));  
					inrow.createCell(18).setCellValue(StringUtil.isNullOrEmpty(list.get(i).get("assetbelong")));  
					inrow.createCell(19).setCellValue(StringUtil.isNullOrEmpty(list.get(i).get("guaranteeparty")));  
					inrow.createCell(20).setCellValue(StringUtil.isNullOrEmpty(list.get(i).get("payprincipalamt")));  
					inrow.createCell(21).setCellValue(StringUtil.isNullOrEmpty(list.get(i).get("payinteamt")));  
					inrow.createCell(22).setCellValue(StringUtil.isNullOrEmpty(list.get(i).get("a11")));  
					inrow.createCell(23).setCellValue(StringUtil.isNullOrEmpty(list.get(i).get("amount")));  
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
			ajaxJson.setMsg("数据导出成功!");
	    } catch (IOException e) {  
	    	ajaxJson.setMsg("数据导出失败,请稍后重试!");
	        e.printStackTrace();  
	        System.out.println("Output   is   closed ");  
	    }
	    return ajaxJson;
	    
	}
	
	/**
	 * @Description:检测当期应付资产方表是否有数据
	 * @param vo
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/excelCheck")
	public AjaxJson excelCheck(HttpServletRequest request, HttpServletResponse response){
		AjaxJson ajaxJson = new AjaxJson();
		//统计合同总数
		String tempAssert = request.getParameter("assetbelong");
		String assetbelong="";
		if(tempAssert!=null){
			try {
				assetbelong = java.net.URLDecoder.decode(tempAssert, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				assetbelong="";
			} 
		}
		String startPayDate = request.getParameter("startPayDate");
		String endPayDate = request.getParameter("endPayDate");
		String startRegistrationDate = request.getParameter("startRegistrationDate");
		String endRegistrationDate = request.getParameter("endRegistrationDate");
		String serialno = request.getParameter("serialno");
		
		String startInaccountdate = request.getParameter("startInaccountdate");
		String endInaccountdate = request.getParameter("endInaccountdate");
		CurrentExpiredVO queryVo = new CurrentExpiredVO();
		queryVo.setAssetbelong(assetbelong);
		queryVo.setStartPayDate(startPayDate);
		queryVo.setEndPayDate(endPayDate);
		queryVo.setStartRegistrationDate(startRegistrationDate);
		queryVo.setEndRegistrationDate(endRegistrationDate);
		queryVo.setSerialno(serialno);
		queryVo.setStartInaccountdate(startInaccountdate);
		queryVo.setEndInaccountdate(endInaccountdate);
		
		Map<String, Object> map = currentPayServer.queryCurrentPayTotal(queryVo);
		if(map.isEmpty()){
			ajaxJson.setSuccess(false);
			ajaxJson.setMsg("暂无数据可导出!");
			return ajaxJson;
		}else{
			if(map.get("CONTRACTCOUNT") != null&&Long.parseLong(map.get("CONTRACTCOUNT").toString()) >0){
				if(Long.parseLong(map.get("CONTRACTCOUNT").toString())>300000){
					ajaxJson.setSuccess(false);
					ajaxJson.setMsg("导出数量超过30万，请重新筛选!");
					return ajaxJson;
				}else{
					ajaxJson.setSuccess(true);
					return ajaxJson;					
				}
			}else{
				ajaxJson.setSuccess(false);
				ajaxJson.setMsg("暂无数据可导出!");
				return ajaxJson;
			}
		}
	}
	

	/**
	 * @Description:导出当期应付资产方汇总表
	 * @param vo
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/excelExportSum")
	public AjaxJson excelExportSum(HttpServletRequest request,CurrentExpiredVO currentExpiredVO, HttpServletResponse response){
		AjaxJson ajaxJson = new AjaxJson();
		//统计合同总数
		String tempPay = currentExpiredVO.getPaybelong();
		String paybelong="";
		if(tempPay!=null){
			try {
				paybelong = java.net.URLDecoder.decode(tempPay, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				paybelong="";
			} 
		}
		currentExpiredVO.setPaybelong(paybelong);
		
		String tempAssert = currentExpiredVO.getAssetbelong();
		String belong="";
		if(tempAssert!=null){
			try {
				belong = java.net.URLDecoder.decode(tempAssert, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				belong="";
			} 
		}
		currentExpiredVO.setAssetbelong(belong);
		
		String tempSub = currentExpiredVO.getSubproducttype();
		String subproducttype="";
		if(tempSub!=null){
			try {
				subproducttype = java.net.URLDecoder.decode(tempSub, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				subproducttype="";
			} 
		}
		currentExpiredVO.setSubproducttype(subproducttype);
		
		String tempBus = currentExpiredVO.getBusinessmodel();
		String businessmodel="";
		if(tempBus!=null){
			try {
				businessmodel = java.net.URLDecoder.decode(tempBus, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				businessmodel="";
			} 
		}
		currentExpiredVO.setBusinessmodel(businessmodel);
		
		String tempCre = currentExpiredVO.getCreditperson();
		String creditperson="";
		if(tempCre!=null){
			try {
				creditperson = java.net.URLDecoder.decode(tempCre, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				creditperson="";
			} 
		}
		currentExpiredVO.setCreditperson(creditperson);
		
		String tempGua = currentExpiredVO.getGuaranteeparty();
		String guaranteeparty="";
		if(tempGua!=null){
			try {
				guaranteeparty = java.net.URLDecoder.decode(tempGua, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				guaranteeparty="";
			} 
		}
		currentExpiredVO.setGuaranteeparty(guaranteeparty);
		
		String tempCity = currentExpiredVO.getCity();
		String city="";
		if(tempCity!=null){
			try {
				city = java.net.URLDecoder.decode(tempCity, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				city="";
			} 
		}
		currentExpiredVO.setCity(city);
		
		String tempDatasource = currentExpiredVO.getDatasource();
		String datasource="";
		if(tempDatasource!=null){
			try {
				datasource = java.net.URLDecoder.decode(tempDatasource, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				datasource="";
			} 
		}
		currentExpiredVO.setDatasource(datasource);
		
		List<Map<String,Object>> list = currentPayServer.queryCurrentPaySumAll(currentExpiredVO);
		if(list.isEmpty()){
			ajaxJson.setMsg("暂无数据可导出!");
			return ajaxJson;
		}
		String fileName = "当期应付资产方汇总表导出Excel.xlsx";  
	    try {  
		
		    // 定义单元格报头  
		    //String worksheetTitle = "当期应付资产方汇总";  
		
	        
	        SXSSFWorkbook wb = new SXSSFWorkbook(100); // 这里100是在内存中的数量，如果大于此数量时，会写到硬盘，以避免在内存导致内存溢出  
			Sheet sh = wb.createSheet();  
			
			Row row = sh.createRow(0);
	    	row.createCell(0).setCellValue("记账期间");  
		    
		    //第一行第2列  
	    	row.createCell(1).setCellValue("统计日期");  
		
		    //第一行第3列  
	    	row.createCell(2).setCellValue("客户渠道");  
		
		    //第一行第4列  
	    	row.createCell(3).setCellValue("业务模式");  
		
		    //第一行第5列  
	    	row.createCell(4).setCellValue("贷款类型");  
	    	
	    	 //第一行第5列  
	    	row.createCell(5).setCellValue("贷款子类型");  
		    
		    //第一行第6列  
	    	row.createCell(6).setCellValue("城市");  
		    
		    //第一行第7列  
	    	row.createCell(7).setCellValue("城市编码"); 
	    	
	    	row.createCell(8).setCellValue("资金方"); 
		    
		    //第一行第9列  
	    	row.createCell(9).setCellValue("应还日"); 
	    	row.createCell(10).setCellValue("减免日"); 
		    
	    	row.createCell(11).setCellValue("实还日"); 
		    
		    //第一行第14列  
	    	row.createCell(12).setCellValue("代垫方"); 
		    
		    //第一行第15列
	    	row.createCell(13).setCellValue("资产所属方"); 
		    
		    //第一行第16列  
	    	row.createCell(14).setCellValue("保证方"); 
	    	
	    	row.createCell(15).setCellValue("应付资产方"); 
	    	
	    	row.createCell(16).setCellValue("数据来源表单"); 
	    	
		    //第一行第17列  
	    	row.createCell(17).setCellValue("应还本金"); 
		   
		    //第一行第18列  
	    	row.createCell(18).setCellValue("应还利息"); 
		    
		    //第一行第19列  
	    	row.createCell(19).setCellValue("应还印花税"); 
		    
		    //第一行第20列  
	    	row.createCell(20).setCellValue("合计（此表单金额合计）"); 
			for (int i = 0; i < list.size(); i++) {  
				//sh.setColumnWidth(i,5000);
					Row inrow = sh.createRow(i+1);
				    
					inrow.createCell(0).setCellValue(StringUtil.isNullOrEmpty(list.get(i).get("inaccountdate")));
					inrow.createCell(1).setCellValue(StringUtil.isNullOrEmpty(list.get(i).get("accorddate")));  
					inrow.createCell(2).setCellValue(StringUtil.isNullOrEmpty(list.get(i).get("suretype")));  
					inrow.createCell(3).setCellValue(StringUtil.isNullOrEmpty(list.get(i).get("businessmodel")));  
					inrow.createCell(4).setCellValue(StringUtil.isNullOrEmpty(list.get(i).get("productid")));  
					inrow.createCell(5).setCellValue(StringUtil.isNullOrEmpty(list.get(i).get("subproducttype")));  
					inrow.createCell(6).setCellValue(StringUtil.isNullOrEmpty(list.get(i).get("city")));  
					inrow.createCell(7).setCellValue(StringUtil.isNullOrEmpty(list.get(i).get("citycode")));
					inrow.createCell(8).setCellValue(StringUtil.isNullOrEmpty(list.get(i).get("creditperson")));
					inrow.createCell(9).setCellValue(StringUtil.isNullOrEmpty(list.get(i).get("paydate")));  
					inrow.createCell(10).setCellValue(StringUtil.isNullOrEmpty(list.get(i).get("waivedate")));  
					inrow.createCell(11).setCellValue(StringUtil.isNullOrEmpty(list.get(i).get("actualpaydate")));  
					inrow.createCell(12).setCellValue(StringUtil.isNullOrEmpty(list.get(i).get("debours")));  
					inrow.createCell(13).setCellValue(StringUtil.isNullOrEmpty(list.get(i).get("assetbelong")));  
					inrow.createCell(14).setCellValue(StringUtil.isNullOrEmpty(list.get(i).get("guaranteeparty")));  
					inrow.createCell(15).setCellValue(StringUtil.isNullOrEmpty(list.get(i).get("paybelong")));  
					inrow.createCell(16).setCellValue(StringUtil.isNullOrEmpty(list.get(i).get("datasource")));  
					inrow.createCell(17).setCellValue(StringUtil.isNullOrEmpty(list.get(i).get("payprincipalamt")));  
					inrow.createCell(18).setCellValue(StringUtil.isNullOrEmpty(list.get(i).get("payinteamt")));  
					inrow.createCell(19).setCellValue(StringUtil.isNullOrEmpty(list.get(i).get("a11")));  
					inrow.createCell(20).setCellValue(StringUtil.isNullOrEmpty(list.get(i).get("amount")));  
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
			ajaxJson.setMsg("数据导出成功!");
	    } catch (IOException e) {  
	    	ajaxJson.setMsg("数据导出失败,请稍后重试!");
	        e.printStackTrace();  
	        System.out.println("Output   is   closed ");  
	    }
	    return ajaxJson;
	}
	
	
	/**
	 * @Description:检测导出当期应付资产方汇总表是否有数据
	 * @param vo
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/excelCheckSum")
	public AjaxJson excelCheckSum(CurrentExpiredVO currentExpiredVO,HttpServletRequest request, HttpServletResponse response){
		
		AjaxJson ajaxJson = new AjaxJson();
		//统计合同总数
		String tempPay = currentExpiredVO.getPaybelong();
		String paybelong="";
		if(tempPay!=null){
			try {
				paybelong = java.net.URLDecoder.decode(tempPay, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				paybelong="";
			} 
		}
		currentExpiredVO.setPaybelong(paybelong);
		
		String tempAssert = currentExpiredVO.getAssetbelong();
		String belong="";
		if(tempAssert!=null){
			try {
				belong = java.net.URLDecoder.decode(tempAssert, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				belong="";
			} 
		}
		currentExpiredVO.setAssetbelong(belong);
		
		String tempSub = currentExpiredVO.getSubproducttype();
		String subproducttype="";
		if(tempSub!=null){
			try {
				subproducttype = java.net.URLDecoder.decode(tempSub, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				subproducttype="";
			} 
		}
		currentExpiredVO.setSubproducttype(subproducttype);
		
		String tempBus = currentExpiredVO.getBusinessmodel();
		String businessmodel="";
		if(tempBus!=null){
			try {
				businessmodel = java.net.URLDecoder.decode(tempBus, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				businessmodel="";
			} 
		}
		currentExpiredVO.setBusinessmodel(businessmodel);
		
		String tempCre = currentExpiredVO.getCreditperson();
		String creditperson="";
		if(tempCre!=null){
			try {
				creditperson = java.net.URLDecoder.decode(tempCre, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				creditperson="";
			} 
		}
		currentExpiredVO.setCreditperson(creditperson);
		
		String tempGua = currentExpiredVO.getGuaranteeparty();
		String guaranteeparty="";
		if(tempGua!=null){
			try {
				guaranteeparty = java.net.URLDecoder.decode(tempGua, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				guaranteeparty="";
			} 
		}
		currentExpiredVO.setGuaranteeparty(guaranteeparty);
		
		String tempCity = currentExpiredVO.getCity();
		String city="";
		if(tempCity!=null){
			try {
				city = java.net.URLDecoder.decode(tempCity, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				city="";
			} 
		}
		currentExpiredVO.setCity(city);
		
		String tempDatasource = currentExpiredVO.getDatasource();
		String datasource="";
		if(tempDatasource!=null){
			try {
				datasource = java.net.URLDecoder.decode(tempDatasource, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				datasource="";
			} 
		}
		currentExpiredVO.setDatasource(datasource);
		
		Map<String, Object> map = currentPayServer.queryCurrentPaySumTotal(currentExpiredVO);
		//导出前将导出标识设置为true
		request.getSession().setAttribute("exportedFlag", "true");
		if(map.isEmpty()){
			ajaxJson.setSuccess(false);
			ajaxJson.setMsg("暂无数据可导出!");
			return ajaxJson;
		}else{
			if(map.get("CONTRACTCOUNT") != null&&Long.parseLong(map.get("CONTRACTCOUNT").toString()) >0){
				if(Long.parseLong(map.get("CONTRACTCOUNT").toString())>300000){
					ajaxJson.setSuccess(false);
					ajaxJson.setMsg("导出数量超过30万，请重新筛选!");
					return ajaxJson;
				}else{
					ajaxJson.setSuccess(true);
					return ajaxJson;					
				}
			}else{
				ajaxJson.setSuccess(false);
				ajaxJson.setMsg("暂无数据可导出!");
				return ajaxJson;
			}
		}
	}
	
	
	
	/**
	 * @Description:检测数据是否已入账
	 * @param vo
	 * @param request
	 * @param response
	 */
	@RequestMapping("/checkCancelCurrentPay")
	public void checkCancelCurrentPay(CurrentExpiredVO currentExpiredVO, HttpServletRequest request,
			HttpServletResponse response) {
		try {


			if (currentExpiredVO==null) {
				ResponseUtil.sendMessage(response, false, "参数数据出错");
				return;
			}

			String currentUser = CurrentUser.getUser().getUsername();
			currentExpiredVO.setInaccountby(currentUser);
			
			Integer count = currentPayServer.checkCancelCurrentPay(currentExpiredVO);
			if(count!=null){
				if(count>0){
					ResponseUtil.sendMessage(response, false, "所选合同包含未记账合同或已撤销记账合同");
				}else{
					ResponseUtil.sendMessage(response, true, "通过");
				}
			}else{
				ResponseUtil.sendMessage(response, false, "检查出错");
			}

			
		} catch (Exception e) {
			LOGGER.error("检测是否已记账出错", e);
			ResponseUtil.sendMessage(response, false, "系统出错");
		}
	}
	
	/**
	 * @Description:更新入账信息
	 * @param vo
	 * @param request
	 * @param response
	 */
	@RequestMapping("/cancelCurrentPay")
	public void cancelCurrentPay(CurrentExpiredVO currentExpiredVO, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Boolean roleFlag = (Boolean) request.getSession().getAttribute("isRevokeRole");
			if(roleFlag==null||!roleFlag){
				ResponseUtil.sendMessage(response, false, "没有该功能的操作权限");
				return;
			}

			if (currentExpiredVO==null) {
				ResponseUtil.sendMessage(response, false, "参数数据出错");
				return;
			}
			currentExpiredVO = changeCurrentExpiredVO(currentExpiredVO);

			String currentUser = CurrentUser.getUser().getUsername();
			currentExpiredVO.setInaccountby(currentUser);
			
			Boolean flag = currentPayServer.cancelCurrentPay(currentExpiredVO);

			if(flag){
				ResponseUtil.sendMessage(response, true, "撤销成功");
			}else{
				ResponseUtil.sendMessage(response, false, "更新失败");
			}
		} catch (Exception e) {
			LOGGER.error("当期到期表入账失败", e);
			ResponseUtil.sendMessage(response, false, "更新失败");
		}
	}
	
	/*@RequestMapping("/exportReport")
	public void exportReport(HttpServletRequest request, HttpServletResponse response) {
		//设置数据源
		
		String startPayDate = request.getParameter("startPayDate");
		String endPayDate = request.getParameter("endPayDate");
		String serialno = request.getParameter("serialno");
		String startRegistrationDate = request.getParameter("startRegistrationDate");
		String endRegistrationDate = request.getParameter("endRegistrationDate");


		String reportName = "应付资方明细";// 报表名称
		String searchCriteria = "";// 查询条件，用于显示和重复导出检查
		searchCriteria = searchCriteria.replace("{", "")
										.replace("}", "")
										.replace("\"contracts\"", "合同号")
										.replace("\"producttype\"", "产品分类")
										.replace("\"paydateStart\"", "回款起始日")
										.replace("\"paydateEnd\"", "回款结束日")
										.replace("\"approvedate\"", "审核日")
										.replace("\"paytype\"", "还款类型")
										.replace("\"payamt\"", "总额");
		StringBuilder querySql = new StringBuilder(); // 查询数据sql
		Object[] sqlParams = null; // 查询sql参数

		// 获取sql
		Map<String,String> queryParams = new HashMap<String,String>();
		queryParams.put("startPayDate", startPayDate);
		queryParams.put("endPayDate", endPayDate);
		queryParams.put("serialno", serialno);
		queryParams.put("startRegistrationDate", startRegistrationDate);
		queryParams.put("endRegistrationDate", endRegistrationDate);
		
		//if ("ch.normal".equalsIgnoreCase(type)) {
			querySql = CurrentUtils.getAllSql(CurrentConstants.EXPORT_SQL_OF_CURRENTPAY_HH.toString());
			sqlParams = CurrentUtils.installSqlJoinAlias(querySql, queryParams);
		//} 
		CurrentExpiredVO vo = new CurrentExpiredVO();
		vo.setSerialno(serialno);
		vo.setStartPayDate(startPayDate);
		vo.setEndPayDate(endPayDate);
		vo.setStartRegistrationDate(startRegistrationDate);
		vo.setEndRegistrationDate(endRegistrationDate);
		
		
		createExportTask(request,vo,reportName, searchCriteria, querySql.toString(), sqlParams, "currentpay");
	}
	
	*//**
	 * 创建导出任务
	 * @param reportName excel名称
	 * @param searchCriteria 查询条件
	 * @param querySql 查询sql
	 * @param sqlParams 查询sql参数
	 * @param type 查询类型
	 * @param others 其他参数
	 *//*
	private void createExportTask (HttpServletRequest request,CurrentExpiredVO vo , String reportName, String searchCriteria, String querySql, Object[] sqlParams, String type, String ... others) {
		// 设置报表中间库数据源
		// 固定信息
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String requestTime = sdf.format(date);// 报表请求时间
		String requestUser = CurrentUser.getUser().getUsername();
		String remoteHost = request.getRemoteHost();
		String remoteAddr = request.getRemoteAddr();
		String insertSql = new StringBuilder("insert into report_download(id, requesttime, report_name, request_user, download_times, record_volume, search_criteria, remote_host, remote_addr, report_status, creation_time, creation_user) values('")
									.append(uuid).append("','")
									.append(requestTime).append("','")
									.append(reportName).append("','")
									.append(requestUser).append("',0,0,'")
									.append(searchCriteria).append("','")
									.append(remoteHost).append("','")
									.append(remoteAddr).append("','正在导出','")
									.append(requestTime).append("','")
									.append(requestUser).append("')").toString();
		jdbcTemplate.execute(insertSql);

		ExcelExportThread exportXls = new ExcelExportThread();
		exportXls.setUuid(uuid);
		exportXls.setReportName(reportName);
		exportXls.setRequestUser(requestUser);
		exportXls.setSearchCriteria(searchCriteria);
		exportXls.setRequestTime(requestTime);
		exportXls.setSqlParams(sqlParams);
		exportXls.setJdbcTemplate(jdbcTemplate);
		exportXls.setQuerySql(querySql);
		exportXls.setType(type);
		// 启用线程池
		ExecutorService executorService = threadPoolExecutor.getExecutor();
		executorService.submit(exportXls);

	}*/
	
	public CurrentExpiredVO changeCurrentExpiredVO(CurrentExpiredVO vo){
		/**
		 * contains : true  记账多个合同
		 * contains : false 记账单个合同
		 */
		if(StringUtil.isNullOrEmpty(vo.getSeqidStr()).contains(";")){
			vo.setSeqidArray(vo.getSeqidStr().replace(",", "").split(";"));
		}else{
			vo.setSeqidArray(new String[]{vo.getSeqidStr()});
		}
		if(StringUtil.isNullOrEmpty(vo.getSerialno()).contains(";")){
			vo.setSerialnoArray(vo.getSerialno().replace(",", "").split(";"));
		}else{
			vo.setSerialnoArray(new String[]{vo.getSerialno()});
		}
		if(StringUtil.isNullOrEmpty(vo.getAssetbelong()).contains(";")){
			vo.setAssetbelongArray(vo.getAssetbelong().replace(",", "").split(";"));
		}else{
			vo.setAssetbelongArray(new String[]{vo.getAssetbelong()});
		}
		if(StringUtil.isNullOrEmpty(vo.getPayprincipalamtStr()).contains(";")){
			vo.setPayprincipalamtArray(vo.getPayprincipalamtStr().replace(",", "").split(";"));
		}else{
			vo.setPayprincipalamtArray(new String[]{vo.getPayprincipalamtStr()});
		}
		if(StringUtil.isNullOrEmpty(vo.getPayinteamtStr()).contains(";")){
			vo.setPayinteamtArray(vo.getPayinteamtStr().replace(",", "").split(";"));
		}else{
			vo.setPayinteamtArray(new String[]{vo.getPayinteamtStr()});
		}
		vo.setSeqidStr("");
		vo.setPayprincipalamtStr("");
		vo.setPayinteamtStr("");
		return vo;
	}
	
}
