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

import com.billionsfinance.bas.entity.CheckListVO;
import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.server.ICheckListServer;
import com.billionsfinance.bas.server.impl.CheckListServer;
import com.billionsfinance.bas.util.AjaxJson;
import com.billionsfinance.bas.util.ExcelUtils;
import com.billionsfinance.bas.util.ExportExcel;
import com.billionsfinance.bas.util.LogUtil;
import com.billionsfinance.bas.util.ResponseUtil;
import com.billionsfinance.bas.util.UUIDGenerator;

/**
 * @ClassName: BusinessCheckListController
 * @Description: 业务对账表控制器
 * @author zhouFM
 * @Copyright: Copyright (c) 2016 2016年10月10日 下午16:05:09 Company:佰仟金融
 */
@Controller
@RequestMapping("/businessCheckListServer")
public class BusinessCheckListController {

	/** 日志记录 */
	private static final Log LOGGER = LogFactory.getLog(BusinessCheckListController.class);
	
	private static final ICheckListServer checkListServer = new CheckListServer();

	@RequestMapping("/toBusinessCheckList")
	public String toBusinessCheckList(HttpServletRequest request, HttpServletResponse response) {
		return "businessCheckList/businessCheckList";
	}
	
	@RequestMapping("/queryBusinessCheckList")
	public void queryBusinessCheckListData(Integer page,Integer rows,CheckListVO checkListVO,HttpServletRequest request, HttpServletResponse response) {
		PageVO pageVO = new PageVO();
		try {
			pageVO.setPageSize(rows);
			pageVO.setPageNo(page);
			pageVO = checkListServer.queryBusinessDetail(pageVO, checkListVO);
		} catch (Exception e) {
			LOGGER.error("查询业务对账失败!", e);
		}finally {
			ResponseUtil.sendJSON(response, pageVO);
		}
	}

	/**
	 * 核对表数据导入
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
			if (alis == null) {
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
			List<CheckListVO> insertList = new ArrayList<CheckListVO>();
			List<CheckListVO> importList = new ArrayList<CheckListVO>();
			StringBuffer str = new StringBuffer();
			for (int i = 2; i < dataList.size(); i++) {
				List<String> array = dataList.get(i);
				CheckListVO clVO = new CheckListVO();
				String generatorId = UUIDGenerator.getUUID();
				if (i == dataList.size()-1) {
					str.append(generatorId);
				}else{
					str.append(generatorId).append(",");
				}
				clVO.setId(generatorId);
				clVO.setImportDate(array.get((1)));
				clVO.setSunrisePreDeposit(Double.parseDouble(array.get(2).toString()));
				clVO.setSunriseUnmatch(array.get(3));
				clVO.setYqzlwyTotalRevenue(Double.parseDouble(array.get(4).toString()));
				clVO.setYqzlasTotalRevenue(Double.parseDouble(array.get(5).toString()));
				clVO.setYqzlTotalRevenueDifference(Double.parseDouble(array.get(6).toString()));
				clVO.setYqzldrAlreadymatch(array.get(7));
				clVO.setYqzkdrUnmatch(array.get(8));
				clVO.setHandworkSeparate(array.get(9));
				clVO.setHandworkMatch(array.get(10));
				clVO.setRzUnmatch(array.get(11));
				clVO.setEbuWyTotalRevenue(Double.parseDouble(array.get(12).toString()));
				clVO.setEbuAsTotalRevenues(array.get(13));
				clVO.setEbuAsTotalRevenue(Double.parseDouble(array.get(14).toString()));
				clVO.setEbuTotalRevenueDifference(Double.parseDouble(array.get(15).toString()));
				clVO.setKftWyTotalRevenue(Double.parseDouble(array.get(16).toString()));
				clVO.setKftAsTotalRevenue(Double.parseDouble(array.get(17).toString()));
				clVO.setKftTotalRevenueDifference(Double.parseDouble(array.get(18).toString()));
				clVO.setHhWyTotalRevenue(Double.parseDouble(array.get(19).toString()));
				clVO.setHhAsTotalRevenue(Double.parseDouble(array.get(20).toString()));
				clVO.setHhTotalRevenueDifference(Double.parseDouble(array.get(21).toString()));
				clVO.setKftSswyTotalRevenue(Double.parseDouble(array.get(22).toString()));
				clVO.setKftSsasTotalRevenue(Double.parseDouble(array.get(23).toString()));
				clVO.setKftSsTotalRevenueCy(Double.parseDouble(array.get(24).toString()));
				clVO.setRefund(Double.parseDouble(array.get(25).toString()));
				clVO.setOrdinaryRepayment(Double.parseDouble(array.get(26).toString()));
				clVO.setEarlierRepayment(Double.parseDouble(array.get(27).toString()));
				clVO.setVirtualAccount(Double.parseDouble(array.get(28).toString()));
				clVO.setRzPreDeposit(Double.parseDouble(array.get(29).toString()));
				clVO.setUnmatchDifference(Double.parseDouble(array.get(30).toString()));
				clVO.setPreDepositDifference(Double.parseDouble(array.get(31).toString()));
				insertList.add(clVO);
				importList.add(clVO);
			}
			
			boolean flag = true;
			List<CheckListVO> localList = checkListServer.queryCheckListDataFindAll();
			List<CheckListVO> updateList = new ArrayList<CheckListVO>();
			List<CheckListVO> removeList = new ArrayList<CheckListVO>();
			for (int i = 0; i < insertList.size(); i++) {
				for (int j = 0; j < localList.size(); j++) {
					CheckListVO importVO = insertList.get(i);
					CheckListVO localVO = localList.get(j);
					if (importVO.getImportDate().equals(localVO.getImportDate())) {
						flag = false;
						importVO.setId(localVO.getId());
						updateList.add(importVO);
						removeList.add(importVO);
					}
				}
			}
			insertList.removeAll(removeList);
			HttpSession httpSession = request.getSession();
			httpSession.setAttribute("updateList", updateList);
			httpSession.setAttribute("insertList", insertList);
			ajaxResponse.setMsg("解析成功!");
			ajaxResponse.setSuccess(flag);
			ajaxResponse.setObj(importList);
			return ajaxResponse;
		} catch (IOException e) {
			LOGGER.error("解析失败!请稍后重试");
			ajaxResponse.setMsg("Import Befeated!");
			ajaxResponse.setSuccess(false);
			return ajaxResponse;
		}
	}
	
	@RequestMapping("/saveImportData")
	public void saveImportData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		List<CheckListVO> insertList = (List<CheckListVO>)session.getAttribute("insertList");
		@SuppressWarnings("unchecked")
		List<CheckListVO> updateList = (List<CheckListVO>)session.getAttribute("updateList");
		try {
			if (insertList.size()>0) {
				checkListServer.importContract(insertList);
			}
			if (updateList.size()>0) {
				checkListServer.updateContract(updateList);
			}
		} catch (Exception e) {
			LOGGER.error("业务对账导入失败!", e);
		}
	}
	
	@ResponseBody
	@RequestMapping("/excelExport")
	public AjaxJson excelExport(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		AjaxJson ajaxJson = new AjaxJson();
		//统计合同总数
		List<CheckListVO> list = checkListServer.queryCheckListDataFindAll();
	    String fileName = "导出Excel.xls";  
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
		    cell1.setCellValue(new HSSFRichTextString("importDate"));  
		    
		    //第一行第er列  
		    cell1 = row1.createCell(1);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("sunrisePreDeposit"));  
		
		    //第一行第san列  
		    cell1 = row1.createCell(2);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("sunriseUnmatch"));  
		
		    //第一行第si列  
		    cell1 = row1.createCell(3);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("yqzlwyTotalRevenue"));  
		
		    //第一行第wu列  
		    cell1 = row1.createCell(4);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("yqzlasTotalRevenue"));  
		
		    //第一行第liu列  
		    cell1 = row1.createCell(5);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("yqzlTotalRevenueDifference"));  
		
		    //第一行第qi列  
		    cell1 = row1.createCell(6);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("yqzldrAlreadymatch"));  
		    
		    //第一行第ba列  
		    cell1 = row1.createCell(7);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("yqzkdrUnmatch"));  
		    
		    //第一行第九列  
		    cell1 = row1.createCell(8);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("handworkSeparate"));  
		    
		    //第一行第十列  
		    cell1 = row1.createCell(9);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("handworkMatch")); 
		    
		    //第一行第十一列  
		    cell1 = row1.createCell(10);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("rzUnmatch"));  
		    
		    //第一行第十二列  
		    cell1 = row1.createCell(11);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("ebuWyTotalRevenue")); 
		    
		    //第一行第十三列  
		    cell1 = row1.createCell(12);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("ebuAsTotalRevenues")); 
		    
		    //第一行第十四列  
		    cell1 = row1.createCell(13);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("ebuAsTotalRevenue")); 
		    
		    //第一行第十五列  
		    cell1 = row1.createCell(14);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("ebuTotalRevenueDifference")); 
		    
		    //第一行第十六列  
		    cell1 = row1.createCell(15);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("kftWyTotalRevenue")); 
		    
		    //第一行第十七列  
		    cell1 = row1.createCell(16);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("kftAsTotalRevenue")); 
		    
		    //第一行第十八列
		    cell1 = row1.createCell(17);
		    cell1.setCellStyle(cellStyleTitle);
		    cell1.setCellValue(new HSSFRichTextString("kftTotalRevenueDifference")); 
		    
		    //第一行第十九列  
		    cell1 = row1.createCell(18);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("hhWyTotalRevenue")); 
		    
		    //第一行第二十列  
		    cell1 = row1.createCell(19);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("hhAsTotalRevenue")); 
		    
		    //第一行第二十一列  
		    cell1 = row1.createCell(20);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("hhTotalRevenueDifference")); 
		    
		    //第一行第二十二列  
		    cell1 = row1.createCell(21);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("kftSswyTotalRevenue")); 
		    
		    //第一行第二十三列  
		    cell1 = row1.createCell(22);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("kftSsasTotalRevenue")); 
		    
		    //第一行第二十四列  
		    cell1 = row1.createCell(23);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("kftSsTotalRevenueCy")); 
		    
		    //第一行第二十五列  
		    cell1 = row1.createCell(24);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("refund")); 
		    
		    //第一行第二十六列  
		    cell1 = row1.createCell(25);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("ordinaryRepayment")); 
		    
		    //第一行第二十七列  
		    cell1 = row1.createCell(26);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("earlierRepayment")); 
		    
		    //第一行第二十八列  
		    cell1 = row1.createCell(27);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("virtualAccount")); 
		    
		    //第一行第二十九列  
		    cell1 = row1.createCell(28);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("rzPreDeposit")); 
		    
		    //第一行第三十列  
		    cell1 = row1.createCell(29);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("unmatchDifference")); 
		    
		    //第一行第三十一列  
		    cell1 = row1.createCell(30);  
		    cell1.setCellStyle(cellStyleTitle);  
		    cell1.setCellValue(new HSSFRichTextString("preDepositDifference")); 
		
		    //定义第二行
		    HSSFRow row = sheet.createRow(2);
		    HSSFCell cell = row.createCell(1);
		    for (int i = 0; i < list.size(); i++) {
		        
		        row = sheet.createRow(i + 2);
		
		        cell = row.createCell(0);
		        cell.setCellStyle(cellStyle);
		        cell.setCellValue(new HSSFRichTextString(list.get(i).getImportDate()));
		          
		        cell = row.createCell(1);
		        cell.setCellStyle(cellStyle);
		        cell.setCellValue(new HSSFRichTextString(list.get(i).getSunrisePreDeposit()+""));  
		          
		        cell = row.createCell(2);
		        cell.setCellStyle(cellStyle);
		        cell.setCellValue(new HSSFRichTextString(list.get(i).getSunriseUnmatch()));  
		          
		        cell = row.createCell(3);
		        cell.setCellValue(new HSSFRichTextString(list.get(i).getYqzlwyTotalRevenue()+""));  
		          
		        cell = row.createCell(4);
		        cell.setCellStyle(cellStyle);
		        cell.setCellValue(new HSSFRichTextString(list.get(i).getYqzlasTotalRevenue()+""));  
		          
		        cell = row.createCell(5);
		        cell.setCellStyle(cellStyle);
		        cell.setCellValue(new HSSFRichTextString(list.get(i).getYqzlTotalRevenueDifference()+""));  
		          
		        cell = row.createCell(6);  
		        cell.setCellValue(new HSSFRichTextString(list.get(i).getYqzldrAlreadymatch()+""));  
		        cell.setCellStyle(cellStyle);  
		        
		        cell = row.createCell(7);  
		        cell.setCellValue(new HSSFRichTextString(list.get(i).getYqzkdrUnmatch()+""));  
		        cell.setCellStyle(cellStyle);  
		        
		        cell = row.createCell(8);  
		        cell.setCellValue(new HSSFRichTextString(list.get(i).getHandworkSeparate()+""));  
		        cell.setCellStyle(cellStyle);  
		        
		        cell = row.createCell(9);  
		        cell.setCellValue(new HSSFRichTextString(list.get(i).getHandworkMatch()+""));  
		        cell.setCellStyle(cellStyle);  
		        
		        cell = row.createCell(10);  
		        cell.setCellValue(new HSSFRichTextString(list.get(i).getRzUnmatch()+""));  
		        cell.setCellStyle(cellStyle);  
		        
		        cell = row.createCell(11);  
		        cell.setCellValue(new HSSFRichTextString(list.get(i).getEbuWyTotalRevenue()+""));  
		        cell.setCellStyle(cellStyle);  
		        
		        cell = row.createCell(12);  
		        cell.setCellValue(new HSSFRichTextString(list.get(i).getEbuAsTotalRevenues()+""));  
		        cell.setCellStyle(cellStyle);  
		        
		        cell = row.createCell(13);  
		        cell.setCellValue(new HSSFRichTextString(list.get(i).getEbuAsTotalRevenue()+""));  
		        cell.setCellStyle(cellStyle);  
		        
		        cell = row.createCell(14);  
		        cell.setCellValue(new HSSFRichTextString(list.get(i).getEbuTotalRevenueDifference()+""));  
		        cell.setCellStyle(cellStyle);  
		        
		        cell = row.createCell(15);  
		        cell.setCellValue(new HSSFRichTextString(list.get(i).getKftWyTotalRevenue()+""));  
		        cell.setCellStyle(cellStyle);  
		        
		        cell = row.createCell(16);  
		        cell.setCellValue(new HSSFRichTextString(list.get(i).getKftAsTotalRevenue()+""));  
		        cell.setCellStyle(cellStyle);  
		        
		        cell = row.createCell(17);  
		        cell.setCellValue(new HSSFRichTextString(list.get(i).getKftTotalRevenueDifference()+""));  
		        cell.setCellStyle(cellStyle);  
		        
		        cell = row.createCell(18);  
		        cell.setCellValue(new HSSFRichTextString(list.get(i).getHhWyTotalRevenue()+""));  
		        cell.setCellStyle(cellStyle);  
		        
		        cell = row.createCell(19);  
		        cell.setCellValue(new HSSFRichTextString(list.get(i).getHhAsTotalRevenue()+""));  
		        cell.setCellStyle(cellStyle);  
		        
		        cell = row.createCell(20);  
		        cell.setCellValue(new HSSFRichTextString(list.get(i).getHhTotalRevenueDifference()+""));  
		        cell.setCellStyle(cellStyle);
		        
		        cell = row.createCell(21);  
		        cell.setCellValue(new HSSFRichTextString(list.get(i).getKftSswyTotalRevenue()+""));  
		        cell.setCellStyle(cellStyle);
		        
		        cell = row.createCell(22);  
		        cell.setCellValue(new HSSFRichTextString(list.get(i).getKftSsasTotalRevenue()+""));  
		        cell.setCellStyle(cellStyle);
		        
		        cell = row.createCell(23);  
		        cell.setCellValue(new HSSFRichTextString(list.get(i).getKftSsTotalRevenueCy()+""));  
		        cell.setCellStyle(cellStyle);
		        
		        cell = row.createCell(24);  
		        cell.setCellValue(new HSSFRichTextString(list.get(i).getRefund()+""));  
		        cell.setCellStyle(cellStyle);
		        
		        cell = row.createCell(25);  
		        cell.setCellValue(new HSSFRichTextString(list.get(i).getOrdinaryRepayment()+""));  
		        cell.setCellStyle(cellStyle);
		        
		        cell = row.createCell(26);  
		        cell.setCellValue(new HSSFRichTextString(list.get(i).getEarlierRepayment()+""));  
		        cell.setCellStyle(cellStyle); 
		        
		        cell = row.createCell(27);  
		        cell.setCellValue(new HSSFRichTextString(list.get(i).getVirtualAccount()+""));  
		        cell.setCellStyle(cellStyle);  
		        
		        cell = row.createCell(28);  
		        cell.setCellValue(new HSSFRichTextString(list.get(i).getRzPreDeposit()+""));  
		        cell.setCellStyle(cellStyle); 
		        
		        cell = row.createCell(29);  
		        cell.setCellValue(new HSSFRichTextString(list.get(i).getUnmatchDifference()+""));  
		        cell.setCellStyle(cellStyle); 
		        
		        cell = row.createCell(30);
		        cell.setCellValue(new HSSFRichTextString(list.get(i).getPreDepositDifference()+""));  
		        cell.setCellStyle(cellStyle);  
		          
		    }
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
		
	private List<String> validateContractCols(List<String> proList, List<String> values) {
		
		for (int i = 0; i < proList.size(); i++) {
			if (proList.get(i).equals("导入日期")) {
				if (!values.get(i).equals("IMPORT_DATE")) {
					return null;
				}
			} else if (proList.get(i).equals("日出预存款")) {
				if (!values.get(i).equals("SUNRISE_PRE_DEPOSIT")) {
					return null;
				}
			} else if (proList.get(i).equals("日出未匹配")) {
				if (!values.get(i).equals("SUNRISE_UNMATCH")) {
					return null;
				}
			} else if (proList.get(i).equals("银企直连网银总进账")) {
				if (!values.get(i).equals("YQZLWY_TOTAL_REVENUE")) {
					return null;
				}
			} else if (proList.get(i).equals("银企直连安硕总进账")) {
				if (!values.get(i).equals("YQZLAS_TOTAL_REVENUE")) {
					return null;
				}
			} else if (proList.get(i).equals("银企直连总进账差异")) {
				if (!values.get(i).equals("YQZL_TOTAL_REVENUE_DIFFERENCE")) {
					return null;
				}
			} else if (proList.get(i).equals("银企直连当日已匹配")) {
				if (!values.get(i).equals("YQZLDR_ALREADYMATCH")) {
					return null;
				}
			} else if (proList.get(i).equals("银企直连当日未匹配")) {
				if (!values.get(i).equals("YQZKDR_UNMATCH")) {
					return null;
				}
			} else if (proList.get(i).equals("手工分离")) {
				if (!values.get(i).equals("HANDWORK_SEPARATE")) {
					return null;
				}
			} else if (proList.get(i).equals("手工匹配")) {
				if (!values.get(i).equals("HANDWORK_MATCH")) {
					return null;
				}
			} else if (proList.get(i).equals("日终未匹配")) {
				if (!values.get(i).equals("RZ_UNMATCH")) {
					return null;
				}
			} else if (proList.get(i).equals("ebu网银总进账")) {
				if (!values.get(i).equals("EBU_WY_TOTAL_REVENUE")) {
					return null;
				}
			} else if (proList.get(i).equals("ebu安硕总进账向后移一天")) {
				if (!values.get(i).equals("EBU_AS_TOTAL_REVENUES")) {
					return null;
				}
			} else if (proList.get(i).equals("ebu安硕总进账")) {
				if (!values.get(i).equals("EBU_AS_TOTAL_REVENUE")) {
					return null;
				}
			} else if (proList.get(i).equals("ebu总进账差异")) {
				if (!values.get(i).equals("EBU_TOTAL_REVENUE_DIFFERENCE")) {
					return null;
				}
			} else if (proList.get(i).equals("kft网银总入账")) {
				if (!values.get(i).equals("KFT_WY_TOTAL_REVENUE")) {
					return null;
				}
			} else if (proList.get(i).equals("kft安硕总入账")) {
				if (!values.get(i).equals("KFT_AS_TOTAL_REVENUE")) {
					return null;
				}
			} else if (proList.get(i).equals("kft总入账差异")) {
				if (!values.get(i).equals("KFT_SSAS_TOTAL_REVENUE")) {
					return null;
				}
			} else if (proList.get(i).equals("哈行网银总入账")) {
				if (!values.get(i).equals("KFT_TOTAL_REVENUE_DIFFERENCE")) {
					return null;
				}
			} else if (proList.get(i).equals("哈行安硕总入账")) {
				if (!values.get(i).equals("HH_WY_TOTAL_REVENUE")) {
					return null;
				}
			} else if (proList.get(i).equals("哈行总入账差异")) {
				if (!values.get(i).equals("HH_AS_TOTAL_REVENUE")) {
					return null;
				}
			} else if (proList.get(i).equals("kft实时网银总入账")) {
				if (!values.get(i).equals("HH_TOTAL_REVENUE_DIFFERENCE")) {
					return null;
				}
			} else if (proList.get(i).equals("kft实时安硕总入账")) {
				if (!values.get(i).equals("KFT_SSWY_TOTAL_REVENUE")) {
					return null;
				}
			} else if (proList.get(i).equals("kft实时总入账差异")) {
				if (!values.get(i).equals("KFT_SS_TOTAL_REVENUE_CY")) {
					return null;
				}
			} else if (proList.get(i).equals("退款")) {
				if (!values.get(i).equals("REFUND")) {
					return null;
				}
			} else if (proList.get(i).equals("普通还款")) {
				if (!values.get(i).equals("ORDINARY_REPAYMENT")) {
					return null;
				}
			} else if (proList.get(i).equals("提前还款")) {
				if (!values.get(i).equals("EARLIER_REPAYMENT")) {
					return null;
				}
			} else if (proList.get(i).equals("虚拟入账")) {
				if (!values.get(i).equals("VIRTUAL_ACCOUNT")) {
					return null;
				}
			} else if (proList.get(i).equals("日终预存款")) {
				if (!values.get(i).equals("RZ_PRE_DEPOSIT")) {
					return null;
				}
			} else if (proList.get(i).equals("未匹配差额")) {
				if (!values.get(i).equals("UNMATCH_DIFFERENCE")) {
					return null;
				}
			} else if (proList.get(i).equals("预存款差额")) {
				if (!values.get(i).equals("PRE_DEPOSIT_DIFFERENCE")) {
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
	
}
