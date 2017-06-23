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
import com.billionsfinance.bas.server.ICurrentExpiredServer;
import com.billionsfinance.bas.server.impl.CurrentExpiredServer;
import com.billionsfinance.bas.util.AjaxJson;
import com.billionsfinance.bas.util.ResponseUtil;
import com.billionsfinance.bas.util.StringUtil;


/**
 * @ClassName: CurrentExpiredController
 * @Description: 当期到期表控制器
 * @author zsp
 * @Copyright: Copyright (c) 2016 2016年10月10日 下午16:05:09 Company:佰仟金融
 */
@Controller
@RequestMapping("/currentExpiredServer")
public class CurrentExpiredController {

	/** 日志记录 */
	private static final Log LOGGER = LogFactory.getLog(CurrentExpiredController.class);
	
	private static final ICurrentExpiredServer currentExpiredServer = new CurrentExpiredServer();

	@RequestMapping("/toCurrentExpiredList")
	public String toBusinessCheckList(HttpServletRequest request, HttpServletResponse response) {
		return "currentExpired/currentExpiredList";
	}
	
	@RequestMapping("/toCurrentExpiredListSum")
	public String toBusinessCheckListSum(HttpServletRequest request, HttpServletResponse response) {
		return "currentExpired/currentExpiredListSum";
	}
	
	/**
	 * @Description:查询当期到期表数据
	 * @param vo
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryCurrentExpiredList")
	public void queryCurrentExpiredList(Integer page,Integer rows,CurrentExpiredVO currentExpiredVO,HttpServletRequest request, HttpServletResponse response) {
		PageVO pageVO = new PageVO();
		try {
			pageVO.setPageSize(rows);
			pageVO.setPageNo(page);
			pageVO = currentExpiredServer.queryBusinessDetail(pageVO, currentExpiredVO);
		} catch (Exception e) {
			LOGGER.error("查询当期到期表失败!", e);
		}finally {
			ResponseUtil.sendJSON(response, pageVO);
		}
	}
	
	/**
	 * @Description:查询当期到期汇总表数据
	 * @param vo
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryCurrentExpiredTotal")
	public void queryCurrentExpiredSummary(Integer page,Integer rows,CurrentExpiredVO currentExpiredVO,HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			map = currentExpiredServer.queryCurrentExpiredTotal(currentExpiredVO);
		} catch (Exception e) {
			LOGGER.error("查询当期到期表失败!", e);
		}finally {
			ResponseUtil.sendJSON(response, map);
		}
	}
	
	/**
	 * @Description:查询当期到期汇总表数据
	 * @param vo
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryCurrentExpiredListSum")
	public void queryBusinessCheckListDataSum(Integer page,Integer rows,CurrentExpiredVO currentExpiredVO,HttpServletRequest request, HttpServletResponse response) {
		PageVO pageVO = new PageVO();
		try {
			pageVO.setPageSize(rows);
			pageVO.setPageNo(page);
			pageVO = currentExpiredServer.queryBusinessDetailSum(pageVO, currentExpiredVO);
		} catch (Exception e) {
			LOGGER.error("查询当期到期汇总表失败!", e);
		}finally {
			ResponseUtil.sendJSON(response, pageVO);
		}
	}
	
	/**
	 * @Description:查询当期到期汇总表总数
	 * @param vo
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryCurrentExpiredSumTotal")
	public void queryCurrentExpiredSumTotal(Integer page,Integer rows,CurrentExpiredVO currentExpiredVO,HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			map = currentExpiredServer.queryCurrentExpiredSumTotal(currentExpiredVO);
		} catch (Exception e) {
			LOGGER.error("查询当期到期表失败!", e);
		}finally {
			ResponseUtil.sendJSON(response, map);
		}
	}

	
	/**
	 * @Description:导出当期到期表
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
		List<CurrentExpiredVO> list = currentExpiredServer.queryBusinessDetailAll(queryVo);
		if(list.isEmpty()){
			ajaxJson.setMsg("暂无数据可导出!");
			return ajaxJson;
		}
		String fileName = "当期到期表导出Excel.xlsx";  
	    
	    
		try {  
			
		    // 定义单元格报头  
		    //String worksheetTitle = "当期应付资产方汇总";  
	        SXSSFWorkbook wb = new SXSSFWorkbook(100); // 这里100是在内存中的数量，如果大于此数量时，会写到硬盘，以避免在内存导致内存溢出  
			Sheet sh = wb.createSheet();  
			
			Row row = sh.createRow(0);
	    	row.createCell(0).setCellValue("记账期间");  
		    
	    	row.createCell(1).setCellValue("统计日期");  
		
	    	row.createCell(2).setCellValue("合同号");  
	    	
	    	row.createCell(3).setCellValue("注册日期");  
	    	
	    	row.createCell(4).setCellValue("业务模式");  
		
	    	row.createCell(5).setCellValue("贷款类型");  
	    	
	    	row.createCell(6).setCellValue("贷款子类型");  
		    
	    	row.createCell(7).setCellValue("城市");  
		    
	    	row.createCell(8).setCellValue("城市编码"); 
		    
	    	row.createCell(9).setCellValue("还款类型");  
		    
	    	row.createCell(10).setCellValue("应还日"); 
	    	
	    	row.createCell(11).setCellValue("期次"); 
	    	
	    	row.createCell(12).setCellValue("转让日"); 
	    	
	    	row.createCell(13).setCellValue("代偿日"); 
		    
	    	row.createCell(14).setCellValue("赎回日"); 
		    
	    	row.createCell(15).setCellValue("保险理赔日"); 
		    
	    	row.createCell(16).setCellValue("代垫方"); 
		   
	    	row.createCell(17).setCellValue("资产所属方"); 
		    
	    	row.createCell(18).setCellValue("保证方"); 
	    	
	    	row.createCell(19).setCellValue("应还本金"); 
		   
	    	row.createCell(20).setCellValue("应还利息"); 
	    	
	    	row.createCell(21).setCellValue("减免利息"); 
	    	
	    	row.createCell(22).setCellValue("应还客户服务费"); 
	    	
	    	row.createCell(23).setCellValue("应还账户管理费"); 
	    	
	    	row.createCell(24).setCellValue("应还增值服务费"); 
	    	
	    	row.createCell(25).setCellValue("应还随心还服务费"); 
	    	
	    	row.createCell(26).setCellValue("应还佰保袋服务费"); 
		    
	    	row.createCell(27).setCellValue("合计（此表单金额合计）"); 
		    
		    //第一行第20列  
			for (int i = 0; i < list.size(); i++) {  
				//sh.setColumnWidth(i,5000);
					Row inrow = sh.createRow(i+1);
				    
					inrow.createCell(0).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getInaccountdate()));
					inrow.createCell(1).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getAccorddate())); 
					
					inrow.createCell(2).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getSerialno())); 
					inrow.createCell(3).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getRegistrationdate())); 
					
					inrow.createCell(4).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getBusinessmodel())); 
					inrow.createCell(5).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getProductid()));  
					inrow.createCell(6).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getShouldalsodate()));  
					inrow.createCell(7).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getCity()));  
					inrow.createCell(8).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getCitycode()));  
					
					String payType="无";
			        if(list.get(i).getPaytype()!=null){
			        	if(list.get(i).getPaytype().equals("1")){
			        		payType="正常还款";
			        	}else if(list.get(i).getPaytype().equals("5")){
			        		payType="提前还款";
			        	}
			        }
					
					inrow.createCell(9).setCellValue(StringUtil.isNullOrEmpty(payType));  
					inrow.createCell(10).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getShouldalsodate()));  
					inrow.createCell(11).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getSeqid().toString())); 
					inrow.createCell(12).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getDeliverydate()));  
					inrow.createCell(13).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getDcdate()));  
					
					inrow.createCell(14).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getShdate()));  
					inrow.createCell(15).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getLpdate()));  
					inrow.createCell(16).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getDebours()));  
					inrow.createCell(17).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getAssetbelong()));  
					inrow.createCell(18).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getGuaranteeparty()));  
					inrow.createCell(19).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getPayprincipalamt()));  
					inrow.createCell(20).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getPayinteamt()));  
					inrow.createCell(21).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getWaiveintamt()));  
					inrow.createCell(22).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getA2().toString()));  
					inrow.createCell(23).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getA7().toString()));  
					inrow.createCell(24).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getA12().toString()));  
					inrow.createCell(25).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getA18().toString()));  
					inrow.createCell(26).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getA22().toString()));  
					inrow.createCell(27).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getAmount().toString()));  
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
	 * @Description:检测导出是否有数据
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
		
		Map<String, Object> map = currentExpiredServer.queryCurrentExpiredTotal(queryVo);
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
	 * @Description:导出当期到期汇总表
	 * @param vo
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/excelExportSum")
	public AjaxJson excelExportSum(HttpServletRequest request,CurrentExpiredVO currentExpiredVO, HttpServletResponse response){
		AjaxJson ajaxJson = new AjaxJson();
		
		
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
		
		String tempCanceltype = currentExpiredVO.getCanceltype();
		String canceltype="";
		if(tempCanceltype!=null){
			try {
				canceltype = java.net.URLDecoder.decode(tempCanceltype, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				canceltype="";
			} 
		}
		currentExpiredVO.setCanceltype(canceltype);
		
		//统计合同总数
		List<CurrentExpiredVO> list = currentExpiredServer.queryBusinessDetailSumAll(currentExpiredVO);
		String fileName = "当期到期汇总表导出Excel.xlsx";  
	    
	    
		try {  
			
		    // 定义单元格报头  
		    //String worksheetTitle = "当期应付资产方汇总";  
	        SXSSFWorkbook wb = new SXSSFWorkbook(100); // 这里100是在内存中的数量，如果大于此数量时，会写到硬盘，以避免在内存导致内存溢出  
			Sheet sh = wb.createSheet();  
			
			Row row = sh.createRow(0);
	    	row.createCell(0).setCellValue("记账期间");  
		    
	    	row.createCell(1).setCellValue("统计日期");  
		
	    	row.createCell(2).setCellValue("业务模式");  
		
	    	row.createCell(3).setCellValue("贷款类型");  
	    	
	    	row.createCell(4).setCellValue("贷款子类型");  
		    
	    	row.createCell(5).setCellValue("城市");  
		    
	    	row.createCell(6).setCellValue("城市编码"); 
		    
	    	row.createCell(7).setCellValue("还款类型");  
		    
	    	row.createCell(8).setCellValue("应还日"); 
	    	
	    	row.createCell(9).setCellValue("转让日"); 
	    	
	    	row.createCell(10).setCellValue("代偿日"); 
		    
	    	row.createCell(11).setCellValue("赎回日"); 
		    
	    	row.createCell(12).setCellValue("保险理赔日"); 
		    
	    	row.createCell(13).setCellValue("代垫方"); 
		    
	    	row.createCell(14).setCellValue("资产所属方"); 
		    
	    	row.createCell(15).setCellValue("保证方"); 
	    	
	    	row.createCell(16).setCellValue("应还本金"); 
		   
	    	row.createCell(17).setCellValue("应还利息"); 
	    	
	    	row.createCell(18).setCellValue("减免利息"); 
	    	
	    	row.createCell(19).setCellValue("应还客户服务费"); 
	    	
	    	row.createCell(20).setCellValue("应还账户管理费"); 
	    	
	    	row.createCell(21).setCellValue("应还增值服务费"); 
	    	
	    	row.createCell(22).setCellValue("应还随心还服务费"); 
	    	
	    	row.createCell(23).setCellValue("应还佰保袋服务费"); 
		    
	    	row.createCell(24).setCellValue("合计（此表单金额合计）"); 
		    
		    //第一行第20列  
			for (int i = 0; i < list.size(); i++) {  
				//sh.setColumnWidth(i,5000);
					Row inrow = sh.createRow(i+1);
				    
					inrow.createCell(0).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getInaccountdate()));
					inrow.createCell(1).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getAccorddate())); 
					inrow.createCell(2).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getBusinessmodel())); 
					inrow.createCell(3).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getProductid()));  
					inrow.createCell(4).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getShouldalsodate()));  
					inrow.createCell(5).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getCity()));  
					inrow.createCell(6).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getCitycode()));  
					
					String payType="无";
			        if(list.get(i).getPaytype()!=null){
			        	if(list.get(i).getPaytype().equals("1")){
			        		payType="正常还款";
			        	}else if(list.get(i).getPaytype().equals("5")){
			        		payType="提前还款";
			        	}
			        }
					
					inrow.createCell(7).setCellValue(StringUtil.isNullOrEmpty(payType));  
					inrow.createCell(8).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getShouldalsodate()));  
					inrow.createCell(9).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getDeliverydate()));  
					inrow.createCell(10).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getDcdate()));  
					inrow.createCell(11).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getShdate()));  
					inrow.createCell(12).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getLpdate()));  
					inrow.createCell(13).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getDebours()));  
					inrow.createCell(14).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getAssetbelong()));  
					inrow.createCell(15).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getGuaranteeparty()));  
					inrow.createCell(16).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getPayprincipalamt()));  
					inrow.createCell(17).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getPayinteamt()));  
					inrow.createCell(18).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getWaiveintamt()));  
					inrow.createCell(19).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getA2().toString()));  
					inrow.createCell(20).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getA7().toString()));  
					inrow.createCell(21).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getA12().toString()));  
					inrow.createCell(22).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getA18().toString()));  
					inrow.createCell(23).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getA22().toString()));  
					inrow.createCell(24).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getAmount().toString()));  
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
	 * @Description:导出检测是否有数据
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/excelCheckSum")
	public AjaxJson excelCheckSum(CurrentExpiredVO currentExpiredVO,HttpServletRequest request, HttpServletResponse response){
		AjaxJson ajaxJson = new AjaxJson();
		//统计合同总数
		String tempAssert = currentExpiredVO.getAssetbelong();
		String assetbelong="";
		if(tempAssert!=null){
			try {
				assetbelong = java.net.URLDecoder.decode(tempAssert, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				assetbelong="";
			} 
		}
		currentExpiredVO.setAssetbelong(assetbelong);
		
		
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
		
		String tempCanceltype = currentExpiredVO.getCanceltype();
		String canceltype="";
		if(tempCanceltype!=null){
			try {
				canceltype = java.net.URLDecoder.decode(tempCanceltype, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				canceltype="";
			} 
		}
		currentExpiredVO.setCanceltype(canceltype);
		
		Map<String, Object> map = currentExpiredServer.queryCurrentExpiredSumTotal(currentExpiredVO);
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
	 * @Description:更新入账信息
	 * @param vo
	 * @param request
	 * @param response
	 */
	@RequestMapping("/updateCurrentExpired")
	public void updateCurrentExpired(CurrentExpiredVO currentExpiredVO, HttpServletRequest request,
			HttpServletResponse response) {
		try {


			if (currentExpiredVO==null) {
				ResponseUtil.sendMessage(response, false, "参数数据出错");
				return;
			}
			currentExpiredVO = changeCurrentExpiredVO(currentExpiredVO);

			String currentUser = CurrentUser.getUser().getUsername();
			currentExpiredVO.setInaccountby(currentUser);
			
			Boolean flag = currentExpiredServer.updateCurrentExpired(currentExpiredVO);
			if(flag){
				ResponseUtil.sendMessage(response, true, "更新成功");
			}else{
				ResponseUtil.sendMessage(response, false, "更新失败");
			}

		} catch (Exception e) {
			LOGGER.error("当期到期表入账失败", e);
			ResponseUtil.sendMessage(response, false, "更新失败");
		}
	}
	
	
	/**
	 * @Description:检测数据是否已入账
	 * @param vo
	 * @param request
	 * @param response
	 */
	@RequestMapping("/checkCurrentExpired")
	public void checkCurrentExpired(CurrentExpiredVO currentExpiredVO, HttpServletRequest request,
			HttpServletResponse response) {
		try {


			if (currentExpiredVO==null) {
				ResponseUtil.sendMessage(response, false, "参数数据出错");
				return;
			}

			String currentUser = CurrentUser.getUser().getUsername();
			currentExpiredVO.setInaccountby(currentUser);
			
			Integer count = currentExpiredServer.checkCurrentExpired(currentExpiredVO);
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
	 * @Description:检测数据是否已入账
	 * @param vo
	 * @param request
	 * @param response
	 */
	@RequestMapping("/checkCancelCurrentExpired")
	public void checkCancelCurrentExpired(CurrentExpiredVO currentExpiredVO, HttpServletRequest request,
			HttpServletResponse response) {
		try {


			if (currentExpiredVO==null) {
				ResponseUtil.sendMessage(response, false, "参数数据出错");
				return;
			}

			String currentUser = CurrentUser.getUser().getUsername();
			currentExpiredVO.setInaccountby(currentUser);
			
			Integer count = currentExpiredServer.checkCancelCurrentExpired(currentExpiredVO);
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
	@RequestMapping("/cancelCurrentExpired")
	public void cancelCurrentExpired(CurrentExpiredVO currentExpiredVO, HttpServletRequest request,
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
			
			Boolean flag = currentExpiredServer.cancelCurrentExpired(currentExpiredVO);
			
			if(flag){
				ResponseUtil.sendMessage(response, true, "更新成功");
			}else{
				ResponseUtil.sendMessage(response, false, "更新失败");
			}
		} catch (Exception e) {
			LOGGER.error("当期到期表入账失败", e);
			ResponseUtil.sendMessage(response, false, "更新失败");
		}
	}
	
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
