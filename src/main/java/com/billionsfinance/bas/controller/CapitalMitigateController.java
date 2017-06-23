package com.billionsfinance.bas.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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

import com.billionsfinance.bas.entity.CapitalMitigateVO;
import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.server.ICapitalMitigateServer;
import com.billionsfinance.bas.server.impl.CapitalMitigateServer;
import com.billionsfinance.bas.util.AjaxJson;
import com.billionsfinance.bas.util.ResponseUtil;
import com.billionsfinance.bas.util.StringUtil;

/**
 * @ClassName: CapitalMitigateController
 * @Description: 应付资方减免Controller
 * @author FMZhou
 * 
 * Copyright: Copyright (c) 2016 2016年11月28日 下午18:25:09 Company:佰仟金融
 */
@Controller
@RequestMapping("/capitalMitigateServer")
public class CapitalMitigateController {

	/** 日志记录 */
	private static final Log LOGGER = LogFactory.getLog(CapitalMitigateController.class);

	private static final ICapitalMitigateServer capitalMitigateServer = new CapitalMitigateServer();

	/**
	 * Description: 跳转应付资方减免页面
	 */
	@RequestMapping("/toCapitalMitigate")
	public String toCapitalMitigate() {
		return "capitalMitigate/capitalMitigate";	
	}
	
	/**
	 * @Description:应付资方减免数据查询
	 * @param vo
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryCapitalMitigate")
	public void queryCapitalMitigate(Integer page, Integer rows, CapitalMitigateVO vo,HttpServletRequest request, HttpServletResponse response) {
		PageVO pageVO;
		try {
			pageVO = new PageVO();
			pageVO.setPageSize(rows);
			pageVO.setPageNo(page);
			pageVO = capitalMitigateServer.queryCapitalMitigate(pageVO, vo);
			ResponseUtil.sendJSON(response, pageVO);
		} catch (Exception e) {
			LOGGER.error("查询应付资方减免数据失败!", e);
		}
	}
	
	/**
	 * 
	 * @Description 应付资方减免合同导出
	 * @throws ServletException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/capitalMitigateExcelExport")
	public AjaxJson capitalMitigateExcelExport(CapitalMitigateVO capitalMitigateVO,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		AjaxJson ajaxJson = new AjaxJson();
		//逾期明细List
		List<Map<String,Object>> list = capitalMitigateServer.queryCapitalMitigateFindAll(capitalMitigateVO);
	    String fileName = "导出Excel-应付资方减免表.xlsx";  
	    try {  
		    // 定义单元格报头  
	    	SXSSFWorkbook wb = new SXSSFWorkbook(1024); // 这里100是在内存中的数量，如果大于此数量时，会写到硬盘，以避免在内存导致内存溢出  
			Sheet sh = wb.createSheet();
			if (list.size()==0) {
				return ajaxJson;
			}
			for (int i = 0; i < list.size()+1; i++) {  
				///sh.setColumnWidth(i,4000);
			    Row row = sh.createRow(i);
			    if (i==0) {
			    	row.createCell(0).setCellValue("合同号");  
			    	row.createCell(1).setCellValue("注册日期");
			    	row.createCell(2).setCellValue("减免日期");  
			    	row.createCell(3).setCellValue("资金来源");  
			    	row.createCell(4).setCellValue("贷款类型"); 
			    	row.createCell(5).setCellValue("贷款子类型");  
			    	row.createCell(6).setCellValue("省份");  
			    	row.createCell(7).setCellValue("城市");  
			    	row.createCell(8).setCellValue("城市编码");
			    	row.createCell(9).setCellValue("贷款本金");
			    	row.createCell(10).setCellValue("是否P2P");
			    	row.createCell(11).setCellValue("应还日期");
			    	row.createCell(12).setCellValue("期次");
			    	row.createCell(13).setCellValue("转让日期");
			    	row.createCell(14).setCellValue("代偿日期");
			    	row.createCell(15).setCellValue("赎回日期"); 
			    	row.createCell(16).setCellValue("保险理赔日"); 
			    	row.createCell(17).setCellValue("资金方"); 
			    	row.createCell(18).setCellValue("减免类型"); 
			    	row.createCell(19).setCellValue("保证方"); 
			    	row.createCell(20).setCellValue("代垫方"); 
			    	row.createCell(21).setCellValue("资产所属方"); 
			    	row.createCell(22).setCellValue("本金"); 
			    	row.createCell(23).setCellValue("减免利息"); 
				}else{
				    row.createCell(0).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("contractNo")));
				    row.createCell(1).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("registrationDate")));  
				    row.createCell(2).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("waiveDate")));  
				    row.createCell(3).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("creditPersion")));  
				    row.createCell(4).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("businessType")));  
				    row.createCell(5).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("subProductType")));  
				    row.createCell(6).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("province")));  
				    row.createCell(7).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("city")));  
				    row.createCell(8).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("cityCode")));  
				    row.createCell(9).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("businessSum")));  
				    row.createCell(10).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("isp2p")));
				    row.createCell(11).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("payDate")));
				    row.createCell(12).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("seqId")));  
				    row.createCell(13).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("deliveryDate")));  
				    row.createCell(14).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("dcDate")));  
				    row.createCell(15).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("shDate")));  
				    row.createCell(16).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("lpDate")));  
				    row.createCell(17).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("creditPersion")));  
				    row.createCell(18).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("waivetype")));  
				    row.createCell(19).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("guaranteeParty")));  
				    row.createCell(20).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("debours")));  
				    row.createCell(21).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("assetBelong")));  
				    row.createCell(22).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("payprinciPalamt")));  
				    row.createCell(23).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("payInteamt")));  
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
	        ajaxJson.setMsg("数据导出成功!");
	    } catch (IOException e) {  
	    	ajaxJson.setMsg("数据导出失败,请稍后重试!");
	        e.printStackTrace();  
	    } finally {  
	        list.clear();  
	    }
	    return ajaxJson;
	}
	
	@RequestMapping("/queryCapitalMitigateCount")
	public void queryCapitalMitigateCount(CapitalMitigateVO capitalMitigateVO,HttpServletRequest request, HttpServletResponse response){
		ResponseUtil.sendJSON(response, capitalMitigateServer.queryCapitalMitigateCount(capitalMitigateVO));
	}
	
}
