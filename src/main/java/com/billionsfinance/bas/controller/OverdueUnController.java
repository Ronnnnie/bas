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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.billionsfinance.als.security.CurrentUser;
import com.billionsfinance.bas.entity.OverdueUnVO;
import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.server.IOverdueUnServer;
import com.billionsfinance.bas.server.impl.OverdueUnServer;
import com.billionsfinance.bas.util.AjaxJson;
import com.billionsfinance.bas.util.ExportExcel;
import com.billionsfinance.bas.util.ResponseUtil;
import com.billionsfinance.bas.util.StringUtil;

/**
 * @ClassName: OverdueUnController
 * @Description: 未到期Controller
 * @author FMZhou
 * 
 * Copyright: Copyright (c) 2016 2016年11月25日 下午14:25:09 Company:佰仟金融
 */
@Controller
@RequestMapping("/overdueUnServer")
public class OverdueUnController {

	/** 日志记录 */
	private static final Log LOGGER = LogFactory.getLog(OverdueUnController.class);

	private static final IOverdueUnServer overdueUnServer = new OverdueUnServer();

	/**
	 * @Description: 跳转未到期业务明细页面
	 */
	@RequestMapping("/toOverdueUnDetail")
	public String toOverdueUnDetail() {
		return "overdueUn/overdueUnDetail";	
	}
	/**
	 * @Description: 跳转未到期业务汇总页面
	 */
	@RequestMapping("/toOverdueUnGather")
	public String toOverdueUnGather() {
		return "overdueUn/overdueUnGather";
	}
	
	/**
	 * @Description:未到期明细数据查询
	 * @param vo
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryOverdueUnDetail")
	public void queryOverdueUnDetail(Integer page, Integer rows, OverdueUnVO vo,HttpServletRequest request, HttpServletResponse response) {
		
		PageVO pageVO = new PageVO();
		pageVO.setPageSize(rows);
		pageVO.setPageNo(page);

		pageVO = overdueUnServer.queryOverdueUnDetail(pageVO, vo);
		ResponseUtil.sendJSON(response, pageVO);
	}
	
	/**
	 * @Description:未到期汇总数据查询
	 * @param vo
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryOverdueUnGather")
	public void queryOverdueUnGather(Integer page, Integer rows, OverdueUnVO vo,HttpServletRequest request, HttpServletResponse response) {
		
		PageVO pageVO = new PageVO();
		pageVO.setPageSize(rows);
		pageVO.setPageNo(page);

		pageVO = overdueUnServer.queryOverdueUnGather(pageVO, vo);
		ResponseUtil.sendJSON(response, pageVO);
	}
	
	
	/**
	 * @Description:未到期数据记账确认
	 * @param vo
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/accountingMark")
	public AjaxJson accountingMark(Integer page, Integer rows, OverdueUnVO vo,HttpServletRequest request, HttpServletResponse response) {
		AjaxJson ajaxJson = null;
		Double moneyCount = null;
		PageVO pageVO = null;
		try {
			ajaxJson = new AjaxJson();
			moneyCount = new Double(0);
			pageVO = new PageVO();
			pageVO.setPageSize(10);
			pageVO.setPageNo(1);
			vo.setInAccountBy(CurrentUser.getUser().getUsername());
			//记账确认
			int resultLine = overdueUnServer.accountingMark(vo);
			if (resultLine > 0 && (!("").equals(vo.getStartInAccountDate())&&vo.getStartInAccountDate()!=null||
						!("").equals(vo.getEndInAccountDate())&&vo.getEndInAccountDate()!=null)) {
				vo.setStartInAccountDate(vo.getUpdateDate());
				vo.setEndInAccountDate(vo.getUpdateDate());
			}
			//修改合同查询
			pageVO = overdueUnServer.queryOverdueUnDetail(pageVO, vo);
			ajaxJson.setObj(pageVO);
			ajaxJson.setSuccess(true);
			if (pageVO.getTotal()>0) {
				for (Map<String,Object> map: pageVO.getRows()) {
					moneyCount+=(Double.parseDouble(map.get("payprinciPalamt").toString()));
				}
				ajaxJson.setMsg("记账确认标记成功!");
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
	 * @Description 逾期记账确认
	 * @param vo
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/selectAccountingMark")
	public AjaxJson selectAccountingMark(OverdueUnVO vo,HttpServletRequest request, HttpServletResponse response) {
		Double payprinciPalamtSum = new Double(0);
		Double payInteamtSum = new Double(0);
		Double a2Sum = new Double(0);
		Double a7Sum = new Double(0);
		Double a12Sum = new Double(0);
		Double a18Sum = new Double(0);
		Double a22Sum = new Double(0);
		AjaxJson ajaxJson = null;
		Double moneyCount = null;
		PageVO pageVO = null;
		List<Map<String,Object>> list = null;
		try {
			vo = changeOverdueUnVO(vo);
			ajaxJson = new AjaxJson();
			moneyCount = new Double(0);
			pageVO = new PageVO();
			list = new ArrayList<Map<String,Object>>();
			pageVO.setPageSize(10);
			pageVO.setPageNo(1);
			vo.setInAccountBy(CurrentUser.getUser().getUsername());
			if(vo.getSeqIdArray()!=null&&vo.getSeqIdArray().length>0){
				for (int i = 0; i < vo.getSerialNoArray().length; i++) {
					vo.setSerialNo(vo.getSerialNoArray()[i]);
					vo.setSeqId(vo.getSeqIdArray()[i]);
					vo.setAssetBelong(vo.getAssetBelongArray()[i]);
					overdueUnServer.accountingMark(vo);
					list.add(overdueUnServer.queryOverdueUnDetailFindAll(vo).get(0)) ;
				}
			}else{
				overdueUnServer.accountingMark(vo);
				list=overdueUnServer.queryOverdueUnDetailFindAll(vo);
			}
			//记账确认
			if (list.size() > 0 ) {
				for (Map<String,Object> map: list) {
					payprinciPalamtSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("payprinciPalamt"))));
					payInteamtSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("payInteamt"))));
					a2Sum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("a2"))));
					a7Sum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("a7"))));
					a12Sum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("a12"))));
					a18Sum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("a18"))));
					a22Sum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("a22"))));
				}
				ajaxJson.setMsg("记账确认标记成功!");
				pageVO.setRows(list);
				pageVO.getRows().get(0).put("moneyCount",moneyCount);
				pageVO.getRows().get(0).put("contractCount",list.size());
				pageVO.getRows().get(0).put("payprinciPalamtSum",payprinciPalamtSum);
				pageVO.getRows().get(0).put("payInteamtSum",payInteamtSum);
				pageVO.getRows().get(0).put("a2Sum",a2Sum);
				pageVO.getRows().get(0).put("a7Sum",a7Sum);
				pageVO.getRows().get(0).put("a12Sum",a12Sum);
				pageVO.getRows().get(0).put("a18Sum",a18Sum);
				pageVO.getRows().get(0).put("a22Sum",a22Sum);
				pageVO.setTotal((long) list.size());
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
	 * @Description:记账撤销
	 * @param vo
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/cancelAccountingMark")
	public AjaxJson cancelAccountingMark(Integer page, Integer rows, OverdueUnVO vo,HttpServletRequest request, HttpServletResponse response) {
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
		AjaxJson ajaxJson = null;
		Double moneyCount = null;
		PageVO pageVO = null;
		List<Map<String,Object>> list = null;
		try {
			ajaxJson = new AjaxJson();
			moneyCount = new Double(0);
			pageVO = new PageVO();
			list = new ArrayList<Map<String,Object>>();
			pageVO.setPageSize(10);
			pageVO.setPageNo(1);
			//修改合同查询
			vo = changeOverdueUnVO(vo);
			if(vo.getSeqIdArray()!=null&&vo.getSeqIdArray().length>0){
				for (int i = 0; i < vo.getSerialNoArray().length; i++) {
					vo.setSerialNo(vo.getSerialNoArray()[i]);
					vo.setSeqId(vo.getSeqIdArray()[i]);
					vo.setAssetBelong(vo.getAssetBelongArray()[i]);
					list.add(overdueUnServer.queryOverdueUnDetailFindAll(vo).get(0)) ;
					overdueUnServer.cancelAccountingMark(vo);
				}
			}else{
				list=overdueUnServer.queryOverdueUnDetailFindAll(vo);
				overdueUnServer.cancelAccountingMark(vo);
			}
			boolean flag = true;
			if (list.size()>0) {
				for (Map<String,Object> map: list) {
					moneyCount+=(Double.parseDouble(map.get("payprinciPalamt").toString()));
					String inAccountStatus = StringUtil.isNullOrEmpty(map.get("inAccountStatus"));
					if(!("1").equals(inAccountStatus)){
						flag=false;
					}else{
						map.put("inAccountDate", "");
						map.put("inAccountStatus", "");
					}
					payprinciPalamtSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("payprinciPalamt"))));
					payInteamtSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("payInteamt"))));
					a2Sum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("a2"))));
					a7Sum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("a7"))));
					a12Sum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("a12"))));
					a18Sum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("a18"))));
					a22Sum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("a22"))));
				}
				if(flag){
					ajaxJson.setMsg("记账撤销成功!");
				}else{
					ajaxJson.setMsg("当前存在未记账/已撤销记账的合同，该些合同记账撤销失败!");
				}
				pageVO.setRows(list);
				pageVO.getRows().get(0).put("payprinciPalamtSum",payprinciPalamtSum);
				pageVO.getRows().get(0).put("payInteamtSum",payInteamtSum);
				pageVO.getRows().get(0).put("a2Sum",a2Sum);
				pageVO.getRows().get(0).put("a7Sum",a7Sum);
				pageVO.getRows().get(0).put("a12Sum",a12Sum);
				pageVO.getRows().get(0).put("a18Sum",a18Sum);
				pageVO.getRows().get(0).put("a22Sum",a22Sum);
				pageVO.getRows().get(0).put("moneyCount",moneyCount);
				pageVO.getRows().get(0).put("contractCount",pageVO.getTotal());
				pageVO.setTotal((long)list.size());
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
	 *	@Description:未到期明细数据导出
	 */
	@ResponseBody
	@RequestMapping("/overdueUnDetailExcelExport")
	public AjaxJson overdueUnDetailExcelExport(OverdueUnVO overdueUnVO,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		AjaxJson ajaxJson = new AjaxJson();
		//逾期明细List
		List<Map<String,Object>> list = overdueUnServer.overdueUnDetailExport(overdueUnVO);
	    String fileName = "导出Excel-未到期明细.xls";  
	    try {  
		    fileName = new String(fileName.getBytes("GBK"), "iso8859-1");  
		    response.reset();  
		    response.setHeader("Content-Disposition", "attachment;filename="  
		            + fileName);// 指定下载的文件名  
		    response.setContentType("application/vnd.ms-excel");  
		    response.setHeader("Pragma", "no-cache");  
		    response.setHeader("Cache-Control", "no-cache");  
		    response.setDateHeader("Expires", 0);  
		    OutputStream output = response.getOutputStream();  
		    BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);  
		
		    // 定义单元格报头  
		    String worksheetTitle = "Excel-未到期明细-数据导出";  
		
		    HSSFWorkbook wb = new HSSFWorkbook();  
		
		    // 创建单元格样式  
		    HSSFCellStyle cellStyleTitle = wb.createCellStyle();  
		    // 指定单元格居中对齐
		    cellStyleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
		    // 指定单元格垂直居中对齐
		    cellStyleTitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
		    // 指定当单元格内容显示不下时自动换行
		    cellStyleTitle.setWrapText(false);
		    // ------------------------------------------------------------------  
		    HSSFCellStyle cellStyle = wb.createCellStyle();  
		    // 指定单元格居中对齐  
		    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
		    // 指定单元格垂直居中对齐  
		    cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
		    // 指定当单元格内容显示不下时自动换行  
		    cellStyle.setWrapText(false);  
		    // ------------------------------------------------------------------  
		    // 设置单元格字体  
		    HSSFFont font = wb.createFont();  
		    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
		    font.setFontName("宋体");  
		    font.setFontHeight((short) 200);  
		    cellStyleTitle.setFont(font);
		
		    //创建开始
		    HSSFSheet sheet = wb.createSheet();  
		  //第一个参数代表列id(从0开始),第2个参数代表宽度值  参考 ："2012-08-10"的宽度为2500
		    for (int i = 0; i < 100; i++) {
		    	sheet.setColumnWidth(i,5000); 
			} 
		    ExportExcel exportExcel = new ExportExcel(wb, sheet);  
		    // 创建报表头部  
		    exportExcel.createNormalHead(worksheetTitle, 13);  
		    // 定义第一行  
		    HSSFRow row1 = sheet.createRow(1);  
		    
		    HSSFCell cell1 = row1.createCell(0);   
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("记账期间"));  
		    
		    //第一行第er列  
		    cell1 = row1.createCell(1);
		    cell1.setCellStyle(cellStyleTitle);
		    cell1.setCellValue(new HSSFRichTextString("统计日期"));  
		
		    //第一行第san列  
		    cell1 = row1.createCell(2);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("合同号"));  
		
		    //第一行第si列  
		    cell1 = row1.createCell(3);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("客户渠道"));  
		
		    //第一行第wu列  
		    cell1 = row1.createCell(4);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("业务模式"));  
		
		    //第一行第liu列  
		    cell1 = row1.createCell(5);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("贷款类型"));  
		
		    //第一行第qi列  
		    cell1 = row1.createCell(6);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("贷款子类型"));  
		    
		    //第一行第ba列  
		    cell1 = row1.createCell(7);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("期次"));  
		    
		    //第一行第九列  
		    cell1 = row1.createCell(8);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("城市"));  
		    
		    //第一行第十列  
		    cell1 = row1.createCell(9);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("城市编码")); 
		    
		    //第一行第十一列  
		    cell1 = row1.createCell(10);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("还款类型"));  
		    
		    //第一行第十二列
		    cell1 = row1.createCell(11);
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("应还日期")); 
		    
		    //第一行第十三列  
		    cell1 = row1.createCell(12);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("转让日期")); 
		    
		    //第一行第十四列  
		    cell1 = row1.createCell(13);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("代偿日期")); 
		    
		    //第一行第十五列  
		    cell1 = row1.createCell(14);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("赎回日期")); 
		    
		    //第一行第十六列  
		    cell1 = row1.createCell(15);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("理赔日期")); 
		    
		    //第一行第十七列  
		    cell1 = row1.createCell(16);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("代垫方")); 
		    
		    //第一行第十八列
		    cell1 = row1.createCell(17);
		    cell1.setCellStyle(cellStyleTitle);
		    cell1.setCellValue(new HSSFRichTextString("资产所属方")); 
		    
		    //第一行第十九列  
		    cell1 = row1.createCell(18);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("保证方")); 
		    
		    //第一行第二十列  
		    cell1 = row1.createCell(19);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("未到期本金")); 
		    
		    //第一行第二十一列  
		    cell1 = row1.createCell(20);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("未到期利息")); 
		    
		    //第一行第二十二列  
		    cell1 = row1.createCell(21);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("未到期客户服务费")); 
		    
		    //第一行第二十三列  
		    cell1 = row1.createCell(22);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("未到期账户管理费")); 
		    
		    //第一行第二十四列  
		    cell1 = row1.createCell(23);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("未到期增值服务费")); 
		    
		    //第一行第二十五列  
		    cell1 = row1.createCell(24);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("未到期随心还服务费")); 
		    
		    //第一行第二十六列  
		    cell1 = row1.createCell(25);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("未到期佰保袋服务费")); 
		    
		    //第一行第二十七列  
		    cell1 = row1.createCell(26);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("合计（此表单金额合计）")); 
		    
		    //第一行第二十八列  
		    cell1 = row1.createCell(27);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("逾期天数")); 
		    
		    //第一行第二十九列  
		    cell1 = row1.createCell(28);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("逾期描述")); 
		    
		    //第一行第三十列  
		    cell1 = row1.createCell(29);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("五级分类")); 
		    
		    //第一行第三十一列  
		    cell1 = row1.createCell(30);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("注册日期")); 
		    
		    //定义第二行
		    HSSFRow row = sheet.createRow(2);
		    HSSFCell cell = row.createCell(1);
		    for (int i = 0; i < list.size(); i++) {
		        
		        row = sheet.createRow(i + 2);
		
		        cell = row.createCell(0);
		        cell.setCellStyle(cellStyle);
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("inAccountDate"))));
		          
		        cell = row.createCell(1);
		        cell.setCellStyle(cellStyle);
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("accordDate"))));  
		          
		        cell = row.createCell(2);
		        cell.setCellStyle(cellStyle);
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("serialNo"))));  
		          
		        cell = row.createCell(3);
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("sureType"))));  
		          
		        cell = row.createCell(4);
		        cell.setCellStyle(cellStyle);
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("businessModel"))));  
		          
		        cell = row.createCell(5);
		        cell.setCellStyle(cellStyle);
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("productId"))));  
		          
		        cell = row.createCell(6);  
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("subProductType"))));  
		        cell.setCellStyle(cellStyle);  
		        
		        cell = row.createCell(7);  
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("seqId"))));  
		        cell.setCellStyle(cellStyle);  
		        
		        cell = row.createCell(8);  
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("city"))));  
		        cell.setCellStyle(cellStyle);  
		        
		        cell = row.createCell(9);  
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("cityCode"))));  
		        cell.setCellStyle(cellStyle);  
		        
		        cell = row.createCell(10);  
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("payType"))));  
		        cell.setCellStyle(cellStyle);  
		        
		        cell = row.createCell(11);  
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("shouldAlsoDate"))));  
		        cell.setCellStyle(cellStyle);  
		        
		        cell = row.createCell(12);  
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("deliveryDate"))));  
		        cell.setCellStyle(cellStyle);  
		        
		        cell = row.createCell(13);  
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("dcDate"))));  
		        cell.setCellStyle(cellStyle);  
		        
		        cell = row.createCell(14);  
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("shDate"))));  
		        cell.setCellStyle(cellStyle);  
		        
		        cell = row.createCell(15);  
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("lpDate"))));  
		        cell.setCellStyle(cellStyle);  
		        
		        cell = row.createCell(16);  
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("debours"))));  
		        cell.setCellStyle(cellStyle);  
		        
		        cell = row.createCell(17);  
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("assetBelong"))));  
		        cell.setCellStyle(cellStyle);  
		        
		        cell = row.createCell(18);  
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("guaranteeParty"))));  
		        cell.setCellStyle(cellStyle);  
		        
		        cell = row.createCell(19);  
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("payprinciPalamt"))));  
		        cell.setCellStyle(cellStyle);  
		        
		        cell = row.createCell(20);  
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("payInteamt"))));  
		        cell.setCellStyle(cellStyle);
		        
		        cell = row.createCell(21);  
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("a2"))));  
		        cell.setCellStyle(cellStyle);
		        
		        cell = row.createCell(22);  
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("a7"))));  
		        cell.setCellStyle(cellStyle);
		        
		        cell = row.createCell(23);  
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("a12"))));  
		        cell.setCellStyle(cellStyle);
		        
		        cell = row.createCell(24);  
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("a18"))));  
		        cell.setCellStyle(cellStyle);
		        
		        cell = row.createCell(25);  
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("a22"))));  
		        cell.setCellStyle(cellStyle);
		        
		        cell = row.createCell(26);  
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("amount"))));  
		        cell.setCellStyle(cellStyle); 
		        
		        cell = row.createCell(27);  
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("overdueDays"))));  
		        cell.setCellStyle(cellStyle);  
		        
		        cell = row.createCell(28);  
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("overdueRemark"))));  
		        cell.setCellStyle(cellStyle); 
		        
		        cell = row.createCell(29);  
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("classFy"))));  
		        cell.setCellStyle(cellStyle); 
		        
		        cell = row.createCell(30);  
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("registrationDate"))));  
		        cell.setCellStyle(cellStyle); 
		          
		    }
	        bufferedOutPut.flush();
	        wb.write(bufferedOutPut);  
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
	
	/**
	 *@Description:未到期汇总数据导出
	 */
	@ResponseBody
	@RequestMapping("/overdueUnGatherExcelExport")
	public AjaxJson overdueUnGatherExcelExport(OverdueUnVO overdueUnVO,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		AjaxJson ajaxJson = new AjaxJson();
		overdueUnVO.setSubProductType(StringUtil.stringFormatter(overdueUnVO.getSubProductType()));
		overdueUnVO.setStartInAccountDate(StringUtil.stringFormatter(overdueUnVO.getStartInAccountDate()));
		overdueUnVO.setEndInAccountDate(StringUtil.stringFormatter(overdueUnVO.getEndInAccountDate()));
		overdueUnVO.setBusinessModel(StringUtil.stringFormatter(overdueUnVO.getBusinessModel()));
		overdueUnVO.setCity(StringUtil.stringFormatter(overdueUnVO.getCity()));
		overdueUnVO.setClassFy(StringUtil.stringFormatter(overdueUnVO.getClassFy()));
		overdueUnVO.setAssetBelong(StringUtil.stringFormatter(overdueUnVO.getAssetBelong()));
		overdueUnVO.setGuaranteeParty(StringUtil.stringFormatter(overdueUnVO.getGuaranteeParty()));
		//逾期明细List
		List<Map<String,Object>> list = overdueUnServer.queryOverdueUnGatherFindAll(overdueUnVO);
	    String fileName = "导出Excel-未到期汇总.xls";  
	    try {
		    fileName = new String(fileName.getBytes("GBK"), "iso8859-1");  
		    response.reset();
		    response.setHeader("Content-Disposition", "attachment;filename="  
		            + fileName);// 指定下载的文件名  
		    response.setContentType("application/vnd.ms-excel");
		    response.setHeader("Pragma", "no-cache");  
		    response.setHeader("Cache-Control", "no-cache");  
		    response.setDateHeader("Expires", 0);  
		    OutputStream output = response.getOutputStream();  
		    BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);  
		
		    // 定义单元格报头  
		    String worksheetTitle = "Excel-未到期汇总-数据导出";  
		
		    HSSFWorkbook wb = new HSSFWorkbook();  
		
		    // 创建单元格样式  
		    HSSFCellStyle cellStyleTitle = wb.createCellStyle();
		    // 指定单元格居中对齐
		    cellStyleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
		    // 指定单元格垂直居中对齐
		    cellStyleTitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
		    // 指定当单元格内容显示不下时自动换行
		    cellStyleTitle.setWrapText(false);
		    // ------------------------------------------------------------------  
		    HSSFCellStyle cellStyle = wb.createCellStyle();  
		    // 指定单元格居中对齐  
		    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
		    // 指定单元格垂直居中对齐  
		    cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
		    // 指定当单元格内容显示不下时自动换行  
		    cellStyle.setWrapText(false);  
		    // ------------------------------------------------------------------  
		    // 设置单元格字体  
		    HSSFFont font = wb.createFont();  
		    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
		    font.setFontName("宋体");  
		    font.setFontHeight((short) 200);  
		    cellStyleTitle.setFont(font);
		
		    //创建开始
		    HSSFSheet sheet = wb.createSheet();  
		  //第一个参数代表列id(从0开始),第2个参数代表宽度值  参考 ："2012-08-10"的宽度为2500
		    for (int i = 0; i < 100; i++) {
		    	sheet.setColumnWidth(i,5000); 
			} 
		    ExportExcel exportExcel = new ExportExcel(wb, sheet);  
		    // 创建报表头部  
		    exportExcel.createNormalHead(worksheetTitle, 13);  
		    // 定义第一行  
		    HSSFRow row1 = sheet.createRow(1);  
		    
		  //第一行第1列  
		    HSSFCell cell1 = row1.createCell(0);   
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("记账期间"));  
		    
		    //第一行第2列  
		    cell1 = row1.createCell(1);
		    cell1.setCellStyle(cellStyleTitle);
		    cell1.setCellValue(new HSSFRichTextString("统计日期"));  
		
		    //第一行第3列  
		    cell1 = row1.createCell(2);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("客户渠道"));  
		
		    //第一行第4列  
		    cell1 = row1.createCell(3);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("业务模式"));  
		
		    //第一行第5列  
		    cell1 = row1.createCell(4);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("贷款子类型"));  
		    
		    //第一行第6列  
		    cell1 = row1.createCell(5);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("应还日")); 
		    
		    //第一行第7列  
		    cell1 = row1.createCell(6);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("转让日")); 
		    
		    //第一行第8列  
		    cell1 = row1.createCell(7);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("代偿日")); 
		    
		    //第一行第9列  
		    cell1 = row1.createCell(8);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("赎回日")); 
		    
		    //第一行第10列  
		    cell1 = row1.createCell(9);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("保险理赔日")); 
		    
		    //第一行第11列  
		    cell1 = row1.createCell(10);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("代垫方")); 
		    
		    //第一行第12列
		    cell1 = row1.createCell(11);
		    cell1.setCellStyle(cellStyleTitle);
		    cell1.setCellValue(new HSSFRichTextString("资产所属方")); 
		    
		    //第一行第13列  
		    cell1 = row1.createCell(12);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("保证方")); 
		    
		    //第一行第14列  
		    cell1 = row1.createCell(13);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("未到期本金")); 
		    
		    //第一行第15列  
		    cell1 = row1.createCell(14);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("未到期利息")); 
		    
		    //第一行第16列  
		    cell1 = row1.createCell(15);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("未到期客户服务费")); 
		    
		    //第一行第17列  
		    cell1 = row1.createCell(16);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("未到期账户管理费")); 
		    
		    //第一行第18列  
		    cell1 = row1.createCell(17);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("未到期增值服务费")); 
		    
		    //第一行第19列  
		    cell1 = row1.createCell(18);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("未到期随心还服务费")); 
		    
		    //第一行第20列  
		    cell1 = row1.createCell(19);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("未到期佰保袋服务费")); 
		    
		    //第一行第21列  
		    cell1 = row1.createCell(20);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("合计（此表单金额合计）")); 
		    
		    //第一行第22列  
		    cell1 = row1.createCell(21);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("逾期天数")); 
		    
		    //第一行第23列  
		    cell1 = row1.createCell(22);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("逾期描述")); 
		    
		    //第一行第24列  
		    cell1 = row1.createCell(23);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("五级分类")); 
		    
		    //定义第二行
		    HSSFRow row = sheet.createRow(2);
		    HSSFCell cell = row.createCell(1);
		    for (int i = 0; i < list.size(); i++) {
		        
		        row = sheet.createRow(i + 2);
		
		        cell = row.createCell(0);
		        cell.setCellStyle(cellStyle);
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("inAccountDate"))));
		          
		        cell = row.createCell(1);
		        cell.setCellStyle(cellStyle);
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("accordDate"))));  
		          
		        cell = row.createCell(2);
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("sureType"))));  
		          
		        cell = row.createCell(3);
		        cell.setCellStyle(cellStyle);
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("businessModel"))));  
		          
		        cell = row.createCell(4);
		        cell.setCellStyle(cellStyle);
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("subProductType"))));  
		        
		        cell = row.createCell(5);  
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("shouldAlsoDate"))));  
		        cell.setCellStyle(cellStyle);  
		        
		        cell = row.createCell(6);  
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("deliveryDate"))));  
		        cell.setCellStyle(cellStyle);  
		        
		        cell = row.createCell(7);  
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("dcDate"))));  
		        cell.setCellStyle(cellStyle);  
		        
		        cell = row.createCell(8);  
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("shDate"))));  
		        cell.setCellStyle(cellStyle);  
		        
		        cell = row.createCell(9);  
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("lpDate"))));  
		        cell.setCellStyle(cellStyle);  
		        
		        cell = row.createCell(10);  
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("debours"))));  
		        cell.setCellStyle(cellStyle);  
		        
		        cell = row.createCell(11);  
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("assetBelong"))));  
		        cell.setCellStyle(cellStyle);  
		        
		        cell = row.createCell(12);  
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("guaranteeParty"))));  
		        cell.setCellStyle(cellStyle);  
		        
		        cell = row.createCell(13);  
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("payprinciPalamt"))));  
		        cell.setCellStyle(cellStyle);  
		        
		        cell = row.createCell(14);  
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("payInteamt"))));  
		        cell.setCellStyle(cellStyle);
		        
		        cell = row.createCell(15);  
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("a2"))));  
		        cell.setCellStyle(cellStyle);
		        
		        cell = row.createCell(16);  
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("a7"))));  
		        cell.setCellStyle(cellStyle);
		        
		        cell = row.createCell(17);  
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("a12"))));  
		        cell.setCellStyle(cellStyle);
		        
		        cell = row.createCell(18);  
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("a18"))));  
		        cell.setCellStyle(cellStyle);
		        
		        cell = row.createCell(19);  
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("a22"))));  
		        cell.setCellStyle(cellStyle);
		        
		        cell = row.createCell(20);  
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("amount"))));  
		        cell.setCellStyle(cellStyle); 
		        
		        cell = row.createCell(21);  
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("overdueDays"))));  
		        cell.setCellStyle(cellStyle);  
		        
		        cell = row.createCell(22);  
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("overdueRemark"))));  
		        cell.setCellStyle(cellStyle); 
		        
		        cell = row.createCell(23);  
		        cell.setCellValue(new HSSFRichTextString(StringUtil.isNullOrEmpty(list.get(i).get("classFy"))));  
		        cell.setCellStyle(cellStyle); 
		          
		    }
	        bufferedOutPut.flush();  
	        wb.write(bufferedOutPut);  
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
	
	/**
	 * @Description:查询未到期合同数
	 */
	@RequestMapping("queryOverdueUnCount")
	public void queryOverdueUnCount(OverdueUnVO vo,HttpServletRequest request, HttpServletResponse response){
		//统计合同总数
		Map<String,Object> map = overdueUnServer.queryOverdueUnCount(vo);
		ResponseUtil.sendJSON(response, map);
	}
	
	/**
	 * @Description:查询未到期合同数
	 */
	@RequestMapping("queryOverdueUnDetailCount")
	public void queryOverdueUnDetailCount(OverdueUnVO vo,HttpServletRequest request, HttpServletResponse response){
		//统计合同总数
		Map<String,Object> map = overdueUnServer.queryOverdueUnDetailCount(vo);
		ResponseUtil.sendJSON(response, map);
	}
	
	/**
	 * @Description:查询未到期合同数
	 */
	@RequestMapping("queryOverdueUnGatherCount")
	public void queryOverdueUnGatherCount(OverdueUnVO vo,HttpServletRequest request, HttpServletResponse response){
		//统计合同总数
		Map<String,Object> map = overdueUnServer.queryOverdueUnGatherCount(vo);
		ResponseUtil.sendJSON(response, map);
	}
	
	/**
	 * @Description:查询未到期合同记账状态
	 * @param vo
	 * @param request
	 * @param response
	 */
	@RequestMapping("queryOverdueUnContract")
	public void queryOverdueUnContract(OverdueUnVO vo,HttpServletRequest request, HttpServletResponse response){
		//查询匹配未到期合同
		vo = changeOverdueUnVO(vo);
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		if(vo.getSeqIdArray()!=null&&vo.getSeqIdArray().length>0){
			for (int i = 0; i < vo.getSerialNoArray().length; i++) {
				vo.setSerialNo(vo.getSerialNoArray()[i]);
				vo.setSeqId(vo.getSeqIdArray()[i]);
				vo.setAssetBelong(vo.getAssetBelongArray()[i]);
				list.add(overdueUnServer.queryOverdueUnDetailFindAll(vo).get(0)) ;
			}
		}else{
			list=overdueUnServer.queryOverdueUnDetailFindAll(vo);
		}
		for (Map<String,Object> overdueMap: list) {
				String inAccountStatus = StringUtil.isNullOrEmpty(overdueMap.get("inAccountStatus"));
				if (inAccountStatus.equals("1")){
					ResponseUtil.sendString(response,inAccountStatus );
					return;
				}
		}
		ResponseUtil.sendString(response,"");
	}
	
	public OverdueUnVO changeOverdueUnVO(OverdueUnVO vo){
		if(StringUtil.isNullOrEmpty(vo.getSeqId()).contains(",")){
			vo.setSeqIdArray(vo.getSeqId().split(","));
		}
		if(StringUtil.isNullOrEmpty(vo.getSerialNo()).contains(",")){
			vo.setSerialNoArray(vo.getSerialNo().split(","));
		}
		if(StringUtil.isNullOrEmpty(vo.getAssetBelong()).contains(",")){
			vo.setAssetBelongArray(vo.getAssetBelong().split(","));
		}
		return vo;
	}
	
}
