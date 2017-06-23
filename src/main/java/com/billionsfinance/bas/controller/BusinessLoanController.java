package com.billionsfinance.bas.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.billionsfinance.bas.entity.BusinessLoanVO;
import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.server.IBusinessLoanServer;
import com.billionsfinance.bas.server.impl.BusinessLoanServer;
import com.billionsfinance.bas.util.AjaxJson;
import com.billionsfinance.bas.util.ExcelUtils;
import com.billionsfinance.bas.util.ExportExcel;
import com.billionsfinance.bas.util.FileOperateUtil;
import com.billionsfinance.bas.util.LogUtil;
import com.billionsfinance.bas.util.ResponseUtil;
import com.billionsfinance.bas.util.UUIDGenerator;

/**
 * @ClassName: BusinessLoanController
 * @Description: 放款管理控制器
 * @author zhouFM
 * 
 * Copyright: Copyright (c) 2016 2016年10月09日 下午16:05:09 Company:佰仟金融
 */
@Controller
@RequestMapping("/businessLoanServer")
public class BusinessLoanController {

	/** 日志记录 */
	private static final Log LOGGER = LogFactory.getLog(BusinessLoanController.class);

	private static final IBusinessLoanServer businessLoanServer = new BusinessLoanServer();
	/**
	 * Description: 跳转到业务汇总页面
	 */
	@RequestMapping("/toBusiness")
	public String toBusiness(boolean flag) {
		if (flag) {
			return "releaseMoneyFail/businessGather";
		}else{
			return "releaseMoney/businessGather";
		}
	}
	/**
	 * Description: 跳转业务明细页面
	 */
	@RequestMapping("/toBusinessDetail")
	public String tousinessDetail(boolean flag) {
		if (flag) {
			return "releaseMoneyFail/businessDetail";	
		}else{
			return "releaseMoney/businessDetail";
		}
	}
	/**
	 * Description: 跳转表资金汇总页面
	 */
	@RequestMapping("/toBankrollDetail")
	public String toBankrollDetail(boolean flag) {
		if (flag) {
			return "releaseMoneyFail/bankrollDetail";
		}else{
			return "releaseMoney/bankrollDetail";
		}
	}
	
	/**
	 * 合同明细数据查询
	 * @param blVO
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryBusinessDetail")
	public void queryBusinessDetail(Integer page, Integer rows, BusinessLoanVO blVO,HttpServletRequest request, HttpServletResponse response) {
		
		PageVO pageVO = new PageVO();
		pageVO.setPageSize(rows);
		pageVO.setPageNo(page);

		pageVO = businessLoanServer.queryBusinessDetail(pageVO, blVO);
		ResponseUtil.sendJSON(response, pageVO);
	}
	
	/**
	 * 合同明细数据查询（负数金额）
	 * @param blVO
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryBusinessDetails")
	public void queryBusinessDetails(Integer page , Integer rows , BusinessLoanVO blVO,HttpServletRequest request, HttpServletResponse response/*,BusinessLoanVO blVO */) {
		
		PageVO pageVO = new PageVO();
		pageVO.setPageSize(rows);
		pageVO.setPageNo(page);
		
		
		pageVO = businessLoanServer.queryBusinessDetail(pageVO,blVO);
		
		List<Map<String,Object>> list = pageVO.getRows();
		
		Double moneyCount = new Double(0);
		
		for (int i = 0; i < list.size(); i++) {
			Double money = Double.parseDouble(list.get(i).get("businessSum").toString());
			moneyCount += money;
			list.get(i).put("businessSum",money*-1);
			list.get(i).put("deductionKHServiceFee", Double.parseDouble(list.get(i).get("deductionKHServiceFee").toString())*-1);
			list.get(i).put("deductionSHServiceFee", Double.parseDouble(list.get(i).get("deductionSHServiceFee").toString())*-1);
			list.get(i).put("paySum", Double.parseDouble(list.get(i).get("paySum").toString())*-1);
		}
		list.get(0).put("moneyCount" , moneyCount*-1);
		list.get(0).put("contractCount", list.size());
		
		pageVO.setRows(list);
		
		ResponseUtil.sendJSON(response, pageVO);
	}
	
	/**
	 * 根据ID查询匹配合同(用于remove,update后定位数据)
	 * @param id
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryBusinessDetailById")
	public void queryBusinessDetailById(String id,HttpServletRequest request, HttpServletResponse response/*,BusinessLoanVO blVO */) {
		//匹配总放款本金
		double moneyCount = 0;
		List<BusinessLoanVO> list = businessLoanServer.queryBusinessDetailById(id);
		for (int i = 0; i < list.size(); i++) {
			moneyCount+=list.get(i).getBusinessSum();	
		}
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setContractCount(list.size());
			list.get(i).setMoneyCount(moneyCount);
		}
		ResponseUtil.sendJSONList(response, list);
	}
	
	/**
	 * 根据ID查询匹配合同(用于remove,update后定位数据) 展示负数金额
	 * @param id
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryBusinessDetailByIds")
	public void queryBusinessDetailByIds(String id,HttpServletRequest request, HttpServletResponse response/*,BusinessLoanVO blVO */) {
		//匹配总合同数
		//long contractCount = businessLoanServer.queryBusinessDetailCount(blVO);
		//匹配总放款本金
		double moneyCount = 0;
		System.out.println("BusinessLoanController.queryBusinessDetail()");
		List<BusinessLoanVO> list = businessLoanServer.queryBusinessDetailById(id);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setBusinessSum(list.get(i).getBusinessSum()*-1);
			list.get(i).setDeductionKHServiceFee(list.get(i).getDeductionKHServiceFee()*-1);
			list.get(i).setDeductionSHServiceFee(list.get(i).getDeductionSHServiceFee()*-1);
			list.get(i).setPaySum(list.get(i).getPaySum()*-1);
			moneyCount+=list.get(i).getBusinessSum();	
		}
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setContractCount(list.size());
			list.get(i).setMoneyCount(moneyCount);
		}
		list.get(0).setMoneyCount(list.get(0).getMoneyCount()*-1);
		ResponseUtil.sendJSONList(response, list);
	}
	
	/**
	 * 合同数据修改
	 * @param blVO
	 * @param request
	 * @param response
	 */
	@RequestMapping("/updateBusinessDetail")
	public void updateBusinessDetail(BusinessLoanVO blVO,HttpServletRequest request, HttpServletResponse response/*,BusinessLoanVO blVO */) {
		
		blVO.setBusinessSum(Math.abs(blVO.getBusinessSum()));
		blVO.setDeductionKHServiceFee(Math.abs(blVO.getDeductionKHServiceFee()));
		blVO.setDeductionSHServiceFee(Math.abs(blVO.getDeductionSHServiceFee()));
		blVO.setPaySum(Math.abs(blVO.getPaySum()));
		businessLoanServer.updateContract(blVO);
		ResponseUtil.sendJSON(response, blVO);
	}
	
	/**
	 * 合同数据新增
	 * @param blVO
	 * @param request
	 * @param response
	 */
	@RequestMapping("/createContract")
	public void createContract(BusinessLoanVO blVO,HttpServletRequest request, HttpServletResponse response/*,BusinessLoanVO blVO */) {
		businessLoanServer.createContract(blVO);
		ResponseUtil.sendJSON(response, blVO);
	}
	
	/**
	 * 合同数据删除
	 * @param blVO
	 * @param request
	 * @param response
	 */
	@RequestMapping("/deleteBusinessDetail")
	public void deleteBusinessDetail(BusinessLoanVO blVO,HttpServletRequest request, HttpServletResponse response/*,BusinessLoanVO blVO */) {
		businessLoanServer.deleteBusinessDetail(blVO.getId());
		ResponseUtil.sendJSON(response, "Remove Successful!");
	}
	
	/**
	 * 导入合同数据解析
	 * @param request
	 * @param file
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/importContract")
	@ResponseBody
	public AjaxJson importContract(HttpServletRequest request,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		AjaxJson ajaxResponse = new AjaxJson();
		try {

			List<List<String>> dataList = new ArrayList<List<String>>();
			XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
			XSSFSheet sheet = workbook.getSheetAt(0);
			XSSFRow projectRow = sheet.getRow(0);
			XSSFRow projectRowValues = sheet.getRow(1);

			List<String> proList = ExcelUtils.validateColumn(projectRow); // 第一行数据
			if (proList == null) {
				LogUtil.MSG.info("项目名称有重复，请检查");
				ajaxResponse.setMsg("项目名称有重复，请检查");
				ajaxResponse.setSuccess(false);
				return ajaxResponse;
			}

			List<String> proValuesList = ExcelUtils.validateColumn(projectRowValues); // 第一行数据
			List<String> alis = validateContractCols(proList, proValuesList);
			if (alis == null || proValuesList == null) {
				LogUtil.MSG.info("导入字段名与别名不匹配，请检查");
				ajaxResponse.setMsg("导入字段名与别名不匹配，请检查");
				ajaxResponse.setSuccess(false);
				return ajaxResponse;
			}
			// 读取数据
			int cols = alis.size() - 1; // 有效列长度
			// 读取数据
			Map<String, Object> dataMap = ExcelUtils.loadDataList(sheet, cols);
			dataList = (List<List<String>>) dataMap.get("dataList");
			// values 解析出来
			List<BusinessLoanVO> list = new ArrayList<BusinessLoanVO>();
			StringBuffer str = new StringBuffer();
			for (int i = 2; i < dataList.size(); i++) {
				List<String> array = dataList.get(i);
				BusinessLoanVO blVO = new BusinessLoanVO();
				String generatorId = UUIDGenerator.getUUID();
				if (i == dataList.size()-1) {
					str.append(generatorId);
				}else{
					str.append(generatorId).append(",");
				}
				blVO.setId(generatorId);
				blVO.setSerialNo(array.get(1));
				blVO.setBusinessDate(array.get(2));
				blVO.setKeepAccountsDate(array.get(3));
				blVO.setPayDate(array.get(4));
				blVO.setProductSubType(array.get(5));
				blVO.setCapitalSide(array.get(6));
				blVO.setBusinessSum(new Integer(array.get(7).toString()));
				blVO.setDeductionKHServiceFee(new Double(array.get(8).toString()));
				blVO.setDeductionSHServiceFee(new Double(array.get(9).toString()));
				blVO.setPaySum(new Double(array.get(10).toString()));
				blVO.setKhName(array.get(11));
				blVO.setShName(array.get(12));
				blVO.setShId(array.get(13));
				blVO.setCity(array.get(14));
				list.add(blVO);
			}
			HttpSession session = request.getSession();
			session.setAttribute("list", list);
			ajaxResponse.setMsg("加载成功!");
			ajaxResponse.setSuccess(true);
			ajaxResponse.setObj(list);
			return ajaxResponse;
		} catch (IOException e) {
			LOGGER.error("导入失败");
			ajaxResponse.setMsg("Import Befeated!");
			ajaxResponse.setSuccess(false);
			return ajaxResponse;
		}
	}
	
	/**
	 * 导入合同数据保存
	 * @param request
	 * @param response
	 */
	@RequestMapping("/importContractSave")
	@ResponseBody
	public void importContractSave(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		List<BusinessLoanVO> list = (List<BusinessLoanVO>) session.getAttribute("list");
		businessLoanServer.importContract(list);
		String str = new String();
		for (int i = 0; i < list.size(); i++) {
			if (i == list.size()-1) {
				str+=list.get(i).getId();
				break;
			}
			str+=list.get(i).getId()+(",");
		}
		ResponseUtil.sendString(response, str);
	}
	
	/**
	 * 放款表资金明细模板下载
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/exportExcel")
	@ResponseBody
	public AjaxJson exportExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson ajaxResponse = new AjaxJson();
		try {
			String storeName = "D:/workSpace/bqjr_bas/src/main/webapp/ExportExcel.xlsx";
			String realName = "ExportExcel.xlsx";
			FileOperateUtil.download(request, response, storeName, realName);
			ajaxResponse.setMsg("Export Successful!");
			ajaxResponse.setSuccess(true);
			return ajaxResponse;
		} catch (IOException e) {
			LOGGER.error("导入失败");
			ajaxResponse.setMsg("导入失败!");
			ajaxResponse.setSuccess(false);
			return ajaxResponse;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("导入失败");
			ajaxResponse.setMsg("导入失败!");
			e.printStackTrace();
			return ajaxResponse;
		}
	}
	
	private List<String> validateContractCols(List<String> proList, List<String> values) {
		
		for (int i = 0; i < proList.size(); i++) {
			if (proList.get(i).equals("合同号")) {
				if (!values.get(i).equals("SERIALNO")) {
					return null;
				}
			} else if (proList.get(i).equals("注册日期")) {
				if (!values.get(i).equals("BUSINESSDATE")) {
					return null;
				}
			} else if (proList.get(i).equals("记账日期")) {
				if (!values.get(i).equals("KEEPACCOUNTS_DATE")) {
					return null;
				}
			} else if (proList.get(i).equals("付款日期")) {
				if (!values.get(i).equals("PAY_DATE")) {
					return null;
				}
			} else if (proList.get(i).equals("产品子类型")) {
				if (!values.get(i).equals("PRODUCT_SUBTYPE")) {
					return null;
				}
			} else if (proList.get(i).equals("资金方")) {
				if (!values.get(i).equals("CAPITALSIDE")) {
					return null;
				}
			} else if (proList.get(i).equals("贷款本金")) {
				if (!values.get(i).equals("BUSINESSSUM")) {
					return null;
				}
			} else if (proList.get(i).equals("扣减客户服务费")) {
				if (!values.get(i).equals("DEDUCTION_KH_SERVICE_FEE")) {
					return null;
				}
			} else if (proList.get(i).equals("扣减商户服务费")) {
				if (!values.get(i).equals("DEDUCTION_SH_SERVICE_FEE")) {
					return null;
				}
			} else if (proList.get(i).equals("支付金额")) {
				if (!values.get(i).equals("PAY_SUM")) {
					return null;
				}
			} else if (proList.get(i).equals("客户名称")) {
				if (!values.get(i).equals("KH_NAME")) {
					return null;
				}
			} else if (proList.get(i).equals("商户名称")) {
				if (!values.get(i).equals("SH_NAME")) {
					return null;
				}
			} else if (proList.get(i).equals("商户编号")) {
				if (!values.get(i).equals("SH_ID")) {
					return null;
				}
			} else if (proList.get(i).equals("所属城市")) {
				if (!values.get(i).equals("CITY")) {
					return null;
				}
			}else{
				return null;
			}
		}
		if (!values.isEmpty()) {
			values.add(0, "ID");
		}
		return values;
	}
	
	/**
	 * 放款表帐务标记
	 * @param blVO
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/accountingMark")
	public AjaxJson accountingMark(BusinessLoanVO blVO,HttpServletRequest request, HttpServletResponse response/*,BusinessLoanVO blVO */) {
		AjaxJson ajaxResponse = new AjaxJson();
		List<Map<String,Object>> list = businessLoanServer.queryBusinessDetailId(blVO);
		StringBuffer buffer = new StringBuffer();
		String[] idArray = null;
		if(list.size() == 1){
			idArray = new String[1];
			idArray[0] = list.get(0).get("id").toString();
			buffer.append(list.get(0).get("id"));
		}else{
			idArray = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				idArray[i] = list.get(i).get("id").toString();
				if (i == list.size()-1) {
					buffer.append(list.get(i).get("id"));
				}else{
					buffer.append(list.get(i).get("id")).append(",");
				}
			}
		}
		
		blVO.setIdArray(idArray);
		businessLoanServer.accountingMark(blVO);
		ajaxResponse.setSuccess(true);
		ajaxResponse.setMsg("账务标记成功!");
		ajaxResponse.setObj(buffer);
		return ajaxResponse;
	}
	/**
	 * 放款表付款确认
	 * @param blVO
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/payConfirm")
	public AjaxJson payConfirm(BusinessLoanVO blVO,HttpServletRequest request, HttpServletResponse response/*,BusinessLoanVO blVO */) {
		
		AjaxJson ajaxResponse = new AjaxJson();
		List<Map<String,Object>> list = businessLoanServer.queryBusinessDetailId(blVO);
		StringBuffer buffer = new StringBuffer();
		String[] idArray = null;
		if(list.size() == 1){
			idArray = new String[1];
			idArray[0] = list.get(0).get("id").toString();
			buffer.append(list.get(0).get("id"));
		}else{
			idArray = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				idArray[i] = list.get(i).get("id").toString();
				if (i == list.size()-1) {
					buffer.append(list.get(i).get("id"));
				}else{
					buffer.append(list.get(i).get("id")).append(",");
				}
			}
		}
		
		blVO.setIdArray(idArray);
		businessLoanServer.payConfirm(blVO);
		ajaxResponse.setSuccess(true);
		ajaxResponse.setMsg("付款确认标记成功!");
		
		for (int i = 0; i < list.size(); i++) {
			if (!("").equals(list.get(i).get("keepAccountsDate")) && list.get(i).get("keepAccountsDate") != null) {
				ajaxResponse.setMsg("当前匹配合同中部分包含记账日期不为空的记录,该些合同修改失败!");
				ajaxResponse.setSuccess(false);
			}
		}
		ajaxResponse.setObj(buffer);
		return ajaxResponse;
	}
	
	@ResponseBody
	@RequestMapping("/excelExport")
	public AjaxJson excelExport(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		AjaxJson ajaxJson = new AjaxJson();
		//统计合同总数
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> map2 = businessLoanServer.queryBusinessDetailCount(map);
		PageVO pageVO = new PageVO();
		pageVO.setPageNo(1);
		pageVO.setPageSize(Integer.parseInt(map2.get("contractCount").toString()));
		
		BusinessLoanVO businessLoanVO = new BusinessLoanVO();
	    pageVO = businessLoanServer.queryBusinessDetail(pageVO, businessLoanVO);
	
	    List<Map<String,Object>> list = pageVO.getRows();
	    
	    String fileName = "导出Excel.xls";  
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
	    String worksheetTitle = "Excel导出核对表信息";  
	
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
	    for (int i = 0; i < Integer.parseInt(map2.get("contractCount").toString()); i++) {
	    	sheet.setColumnWidth(i,5000); 
		} 
	    ExportExcel exportExcel = new ExportExcel(wb, sheet);  
	    // 创建报表头部  
	    exportExcel.createNormalHead(worksheetTitle, 13);  
	    // 定义第一行  
	    HSSFRow row1 = sheet.createRow(1);  
	    
	    HSSFCell cell1 = row1.createCell(0);   
	    cell1.setCellStyle(cellStyleTitle);  
	    cell1.setCellValue(new HSSFRichTextString("serialNo"));  
	    
	    //第一行第er列  
	    cell1 = row1.createCell(1);  
	    cell1.setCellStyle(cellStyleTitle);  
	    cell1.setCellValue(new HSSFRichTextString("businessDate"));  
	
	    //第一行第san列  
	    cell1 = row1.createCell(2);  
	    cell1.setCellStyle(cellStyleTitle);  
	    cell1.setCellValue(new HSSFRichTextString("keepAccountsDate"));  
	
	    //第一行第si列  
	    cell1 = row1.createCell(3);  
	    cell1.setCellStyle(cellStyleTitle);  
	    cell1.setCellValue(new HSSFRichTextString("payDate"));  
	
	    //第一行第wu列  
	    cell1 = row1.createCell(4);  
	    cell1.setCellStyle(cellStyleTitle);  
	    cell1.setCellValue(new HSSFRichTextString("productSubType"));  
	
	    //第一行第liu列  
	    cell1 = row1.createCell(5);  
	    cell1.setCellStyle(cellStyleTitle);  
	    cell1.setCellValue(new HSSFRichTextString("businessSum"));  
	
	    //第一行第qi列  
	    cell1 = row1.createCell(6);  
	    cell1.setCellStyle(cellStyleTitle);  
	    cell1.setCellValue(new HSSFRichTextString("capitalSide"));  
	    
	    //第一行第ba列  
	    cell1 = row1.createCell(7);  
	    cell1.setCellStyle(cellStyleTitle);  
	    cell1.setCellValue(new HSSFRichTextString("city"));  
	    
	    //第一行第九列  
	    cell1 = row1.createCell(8);  
	    cell1.setCellStyle(cellStyleTitle);  
	    cell1.setCellValue(new HSSFRichTextString("deductionKHServiceFee"));  
	    
	    //第一行第十列  
	    cell1 = row1.createCell(9);  
	    cell1.setCellStyle(cellStyleTitle);  
	    cell1.setCellValue(new HSSFRichTextString("deductionSHServiceFee")); 
	    
	    //第一行第十一列  
	    cell1 = row1.createCell(10);  
	    cell1.setCellStyle(cellStyleTitle);  
	    cell1.setCellValue(new HSSFRichTextString("paySum"));  
	    
	    //第一行第十二列  
	    cell1 = row1.createCell(11);  
	    cell1.setCellStyle(cellStyleTitle);  
	    cell1.setCellValue(new HSSFRichTextString("khName")); 
	    
	    //第一行第十三列  
	    cell1 = row1.createCell(12);  
	    cell1.setCellStyle(cellStyleTitle);  
	    cell1.setCellValue(new HSSFRichTextString("shName")); 
	    
	    //第一行第十四列  
	    cell1 = row1.createCell(13);  
	    cell1.setCellStyle(cellStyleTitle);  
	    cell1.setCellValue(new HSSFRichTextString("shId")); 
	
	      
	    //定义第二行  
	    HSSFRow row = sheet.createRow(2);  
	    HSSFCell cell = row.createCell(1);  
	    for (int i = 0; i < list.size(); i++) {  
	        
	        row = sheet.createRow(i + 2);  
	
	        cell = row.createCell(0);  
	        cell.setCellStyle(cellStyle);  
	        cell.setCellValue(new HSSFRichTextString(list.get(i).get("serialNo").toString()));  
	          
	        cell = row.createCell(1);  
	        cell.setCellStyle(cellStyle);  
	        cell.setCellValue(new HSSFRichTextString(list.get(i).get("businessDate").toString()));  
	          
	        cell = row.createCell(2);  
	        cell.setCellStyle(cellStyle);  
	        cell.setCellValue(new HSSFRichTextString(list.get(i).get("keepAccountsDate").toString()));  
	          
	        cell = row.createCell(3);  
	        cell.setCellStyle(cellStyle);  
	        cell.setCellValue(new HSSFRichTextString(list.get(i).get("payDate").toString()));  
	          
	        cell = row.createCell(4);  
	        cell.setCellStyle(cellStyle);  
	        cell.setCellValue(new HSSFRichTextString(list.get(i).get("productSubType").toString()));  
	          
	        cell = row.createCell(5);  
	        cell.setCellStyle(cellStyle);  
	        cell.setCellValue(new HSSFRichTextString(list.get(i).get("businessSum").toString()));  
	          
	        cell = row.createCell(6);  
	        cell.setCellValue(new HSSFRichTextString(list.get(i).get("capitalSide").toString()));  
	        cell.setCellStyle(cellStyle);  
	        
	        cell = row.createCell(7);  
	        cell.setCellValue(new HSSFRichTextString(list.get(i).get("city").toString()));  
	        cell.setCellStyle(cellStyle);  
	        
	        cell = row.createCell(8);  
	        cell.setCellValue(new HSSFRichTextString(list.get(i).get("deductionKHServiceFee").toString()));  
	        cell.setCellStyle(cellStyle);  
	        
	        cell = row.createCell(9);  
	        cell.setCellValue(new HSSFRichTextString(list.get(i).get("deductionSHServiceFee").toString()));  
	        cell.setCellStyle(cellStyle);  
	        
	        cell = row.createCell(10);  
	        cell.setCellValue(new HSSFRichTextString(list.get(i).get("paySum").toString()));  
	        cell.setCellStyle(cellStyle);  
	        
	        cell = row.createCell(11);  
	        cell.setCellValue(new HSSFRichTextString(list.get(i).get("khName").toString()));  
	        cell.setCellStyle(cellStyle);  
	        
	        cell = row.createCell(12);  
	        cell.setCellValue(new HSSFRichTextString(list.get(i).get("shName").toString()));  
	        cell.setCellStyle(cellStyle);  
	        
	        cell = row.createCell(13);  
	        cell.setCellValue(new HSSFRichTextString(list.get(i).get("shId").toString()));  
	        cell.setCellStyle(cellStyle);  
	          
	    }  
	    try {  
	        bufferedOutPut.flush();  
	        wb.write(bufferedOutPut);  
	        bufferedOutPut.close();  
	        ajaxJson.setMsg("数据导出成功!");
	    } catch (IOException e) {  
	    	ajaxJson.setMsg("数据导出失败,请稍后重试!");
	        e.printStackTrace();  
	        System.out.println("Output   is   closed ");  
	    } finally {  
	        list.clear();  
	    }
	    return ajaxJson;
	}
	
	
}
