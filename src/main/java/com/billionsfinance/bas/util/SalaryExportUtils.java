package com.billionsfinance.bas.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.apache.log4j.Logger;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

/**
 * 
 * @Title:SalaryExportUtils
 * @Description:薪资报表导出工具类
 * @author aihua.tang
 * @date 2016年8月3日
 */
public class SalaryExportUtils {
	
	private static final Logger logger=Logger.getLogger(SalaryExportUtils.class);
	
	/**
	 * 导出excel
	 * @param title
	 * @param colMap
	 * @param dataSet
	 * @return
	 */
	public static SXSSFWorkbook exportToExcel(SXSSFWorkbook workbook, String title, LinkedHashMap<String,Object> colMap,
			List<Map<String, Object>> dataList) {
		try {
			// 首先检查数据看是否是正确的
			if (colMap == null || colMap.size() == 0) {
				throw new Exception("读取表头失败！");
			}
			if (title == null) {
				title = "";
			}
			Sheet sheet = workbook.createSheet(title);
			if(title == "销售代表奖金明细"){
				setBonusDetailColWidth(sheet);
				getSheetData(title, sheet, workbook, colMap, dataList);
			}else if(title.contains("薪资报表")){
				setSalaryReportColWidth(sheet);
				getSalarySheetData(title, sheet, workbook, colMap, dataList);
			}else{
				getSheetData(title, sheet, workbook, colMap, dataList);
			}
		} catch (Exception e) {
			logger.error("写入excel异常",e);
		}
		return workbook;
	}
	
	/**
	 * 
	 * @Title:getSheetData
	 * @Description:把表头和数据写入sheet
	 * @param title
	 * @param sheet
	 * @param workbook
	 * @param titleMap
	 * @param dataList
	 * @date: 2016年8月3日
	 */
	private static void getSheetData(String title, Sheet sheet, SXSSFWorkbook workbook, 
			LinkedHashMap<String,Object> titleMap, List<Map<String, Object>> dataList ){
		try {
			CellStyle fontStyle = getFontStyle(workbook); 
			CellStyle titleStyle = getTitleFontStyle(workbook);
			CellStyle numsStyle = getNumFontStyle(workbook);
			CellStyle font0Style = getFontStyle(workbook); 
        	font0Style.setAlignment(CellStyle.ALIGN_CENTER);
		
			int rindex = 0;
			int cindex = 0;
			// 产生表格标题行
			Row row = sheet.createRow(rindex);
			row.setHeight((short) 450);
			// 设置表格样式
//			setPosSheetStyle(sheet);
			//遍历标题行
			Iterator<String> it = titleMap.keySet().iterator();
			while(it.hasNext()){
				RichTextString text = null;
				String titleContent = (String) titleMap.get(it.next());
				Cell cell = row.createCell(cindex);
				text = new XSSFRichTextString(titleContent);
				cell.setCellValue(text);
				cell.setCellStyle(titleStyle);
				cindex++;
			}
			//遍历内容
			for (int i=0; i<dataList.size(); ++i) {
				cindex = 0;
				rindex++;
				if(rindex%3000==0){
					((SXSSFSheet)sheet).flushRows();
				}
				row = sheet.createRow(rindex);
				row.setHeight((short) 305);
				Iterator<String> titleIt = titleMap.keySet().iterator();
				Map<String, Object> dataM = dataList.get(i);//获取每一行的内容
				while(titleIt.hasNext()){
					String field = (String) titleIt.next();
					String content = dataM.get(field)==null?"":dataM.get(field).toString();
					Cell cell = row.createCell(cindex);
					// 处理数字
					Pattern p = Pattern.compile("^(\\-?)\\d+(\\.\\d+)?$");
					Matcher matcher = p.matcher(content);
                	if (matcher.matches() && !(field.equals("serialno")) && !(field.equals("operateuserid")) && !(field.equals("SA_ID"))) {
                		cell.setCellValue(Double.parseDouble(content));
                		cell.setCellStyle(numsStyle);
                	} else {
                		RichTextString text = new XSSFRichTextString(content);			
                		cell.setCellValue(text);
                		cell.setCellStyle(fontStyle);
                	}
					cindex++;
				}
			}
		} catch (Exception e) {
			logger.error("写入excel异常！",e);
		}
	}
	
	
	private static void getSalarySheetData(String title, Sheet sheet, SXSSFWorkbook workbook, 
			LinkedHashMap<String,Object> titleMap, List<Map<String, Object>> dataList ){
		try {
			CellStyle fontStyle = getFontStyle(workbook); 
			CellStyle titleStyle = getTitleFontStyle(workbook);
			CellStyle numsStyle = getNumFontStyle(workbook);
			CellStyle font0Style = getFontStyle(workbook); 
        	font0Style.setAlignment(CellStyle.ALIGN_CENTER);
		
			int rindex = 0;
			int cindex = 0;
			// 产生表格标题行
			Row row = sheet.createRow(rindex);
			row.setHeight((short) 450);
			// 设置表格样式
//			setPosSheetStyle(sheet);
			//遍历标题行
			Iterator<String> it = titleMap.keySet().iterator();
			while(it.hasNext()){
				RichTextString text = null;
				String titleContent = (String) titleMap.get(it.next());
				Cell cell = row.createCell(cindex);
				text = new XSSFRichTextString(titleContent);
				cell.setCellValue(text);
				cell.setCellStyle(titleStyle);
				cindex++;
			}
			//遍历内容
			for (int i=0; i<dataList.size(); ++i) {
				cindex = 0;
				rindex++;
				if(rindex%3000==0){
					((SXSSFSheet)sheet).flushRows();
				}
				row = sheet.createRow(rindex);
				row.setHeight((short) 305);
				Iterator<String> titleIt = titleMap.keySet().iterator();
				Map<String, Object> dataM = dataList.get(i);//获取每一行的内容
				while(titleIt.hasNext()){
					String field = (String) titleIt.next();
					String content = dataM.get(field)==null?"":dataM.get(field).toString();
					Cell cell = row.createCell(cindex);
					// 处理数字
					Pattern p = Pattern.compile("^(\\-?)\\d+(\\.\\d+)?$");
					Matcher matcher = p.matcher(content);
                	if (matcher.matches() && !(field.equals("sattend")) && !(field.equals("fattend")) 
                			&& !(field.equals("owoverh")) && !(field.equals("wwoverh")) && !(field.equals("hwoverh")) 
                			&& !(field.equals("sickleave")) && !(field.equals("noleave")) && !(field.equals("mateleave")) 
                			&& !(field.equals("attendt")) && !(field.equals("cardid")) && !(field.equals("bandno"))) {
                		cell.setCellValue(Double.parseDouble(content));
                		cell.setCellStyle(numsStyle);
                	} else {
                		RichTextString text = new XSSFRichTextString(content);			
                		cell.setCellValue(text);
                		cell.setCellStyle(fontStyle);
                	}
					cindex++;
				}
			}
		} catch (Exception e) {
			logger.error("写入excel异常！",e);
		}
	}
	/**
	 * excel标题名称单元格样式处理
	 * @param workbook
	 * @return
	 */
	public static CellStyle getTitleFontStyle(SXSSFWorkbook workbook) {
		// 产生Excel表头
		CellStyle titleStyle = workbook.createCellStyle();
		Font font = workbook.createFont(); 
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 10);// 设置字体大小
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        titleStyle.setFont(font);
        titleStyle.setAlignment(CellStyle.ALIGN_CENTER);// 左右居中    
        titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 上下居中    
        titleStyle.setWrapText(true);    
        titleStyle.setLeftBorderColor(HSSFColor.BLACK.index);//左边框 
        titleStyle.setBorderLeft((short) 1);    
        titleStyle.setRightBorderColor(HSSFColor.BLACK.index);//右边框  
        titleStyle.setBorderRight((short) 1);    
        titleStyle.setTopBorderColor(HSSFColor.BLACK.index); //上边框  
        titleStyle.setBorderTop((short) 1);  
        titleStyle.setBottomBorderColor(HSSFColor.BLACK.index); //下边框
        titleStyle.setBorderBottom((short) 1);  
        titleStyle.setBorderBottom(CellStyle.BORDER_THIN); // 设置单元格的边框为粗体    
        titleStyle.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色．    
        titleStyle.setFillForegroundColor(HSSFColor.WHITE.index);// 设置单元格的背景颜色．
		return titleStyle;
	}
	
	public static void setBonusDetailColWidth(Sheet sheet){
		sheet.setColumnWidth(0, 2500);
		sheet.setColumnWidth(1, 3500);
		sheet.setColumnWidth(2, 5000);
		sheet.setColumnWidth(3, 3000);
		sheet.setColumnWidth(4, 2500);
		sheet.setColumnWidth(5, 3000);
		sheet.setColumnWidth(6, 2500);
		sheet.setColumnWidth(7, 3000);
		sheet.setColumnWidth(8, 3500);
		sheet.setColumnWidth(9, 3500);
		sheet.setColumnWidth(10, 3000);
		sheet.setColumnWidth(11, 3000);
		sheet.setColumnWidth(12, 4000);
		sheet.setColumnWidth(13, 3000);
		sheet.setColumnWidth(14, 3000);
	}
	
	public static void setSalaryReportColWidth(Sheet sheet){
		sheet.setColumnWidth(4, 5000);
		sheet.setColumnWidth(5, 4000);
		sheet.setColumnWidth(6, 4000);
		sheet.setColumnWidth(7, 4000);
	}
	
	/**
	 * exce表头单元格样式处理
	 * @param workbook
	 * @return
	 */
	public static CellStyle getFontStyle(SXSSFWorkbook workbook) {
		// 产生Excel表头
		CellStyle titleStyle = workbook.createCellStyle();
		Font font = workbook.createFont(); 
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 10);// 设置字体大小
        titleStyle.setFont(font);
        titleStyle.setAlignment(CellStyle.ALIGN_LEFT);
        titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 上下居中    
        titleStyle.setWrapText(true);    
        titleStyle.setLeftBorderColor(HSSFColor.BLACK.index);//左边框 
        titleStyle.setBorderLeft((short) 1);    
        titleStyle.setRightBorderColor(HSSFColor.BLACK.index);//右边框  
        titleStyle.setBorderRight((short) 1);    
        titleStyle.setTopBorderColor(HSSFColor.BLACK.index); //上边框  
        titleStyle.setBorderTop((short) 1);  
        titleStyle.setBottomBorderColor(HSSFColor.BLACK.index); //下边框
        titleStyle.setBorderBottom((short) 1);  
        titleStyle.setBorderBottom(CellStyle.BORDER_THIN); // 设置单元格的边框为粗体    
        titleStyle.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色．    
        titleStyle.setFillForegroundColor(HSSFColor.WHITE.index);// 设置单元格的背景颜色．
		return titleStyle;
	}
	
	public static CellStyle getNumFontStyle(SXSSFWorkbook workbook) {
		// 产生Excel表头
		CellStyle titleStyle = workbook.createCellStyle();
		Font font = workbook.createFont(); 
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short) 10);// 设置字体大小
        titleStyle.setFont(font);
        titleStyle.setAlignment(CellStyle.ALIGN_RIGHT);  
        titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 上下居中    
        titleStyle.setWrapText(true);    
        titleStyle.setLeftBorderColor(HSSFColor.BLACK.index);//左边框 
        titleStyle.setBorderLeft((short) 1);    
        titleStyle.setRightBorderColor(HSSFColor.BLACK.index);//右边框  
        titleStyle.setBorderRight((short) 1);    
        titleStyle.setTopBorderColor(HSSFColor.BLACK.index); //上边框  
        titleStyle.setBorderTop((short) 1);  
        titleStyle.setBottomBorderColor(HSSFColor.BLACK.index); //下边框
        titleStyle.setBorderBottom((short) 1);  
        titleStyle.setBorderBottom(CellStyle.BORDER_THIN); // 设置单元格的边框为粗体    
        titleStyle.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色．    
        titleStyle.setFillForegroundColor(HSSFColor.WHITE.index);// 设置单元格的背景颜色．
        DataFormat format= workbook.createDataFormat();
        titleStyle.setDataFormat(format.getFormat("#,##0.00"));
		return titleStyle;
	}
	
	
	public static CellStyle getDateNum1FontStyle(SXSSFWorkbook workbook,int fontNum) {
		// 产生Excel表头
		CellStyle titleStyle = workbook.createCellStyle();
		Font font = workbook.createFont(); 
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) fontNum);// 设置字体大小
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        titleStyle.setFont(font);
        //titleStyle.setAlignment(CellStyle.ALIGN_CENTER);  
        titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 上下居中    
        titleStyle.setWrapText(true);    
        titleStyle.setLeftBorderColor(HSSFColor.BLACK.index);//左边框 
        //titleStyle.setBorderLeft((short) 1);    
        titleStyle.setRightBorderColor(HSSFColor.BLACK.index);//右边框  
        //titleStyle.setBorderRight((short) 1);    
        titleStyle.setTopBorderColor(HSSFColor.BLACK.index); //上边框  
        titleStyle.setBorderTop((short) 1);  
        titleStyle.setBottomBorderColor(HSSFColor.BLACK.index); //下边框
        titleStyle.setBorderBottom((short) 1);  
        titleStyle.setBorderBottom(CellStyle.BORDER_THIN); // 设置单元格的边框为粗体    
        titleStyle.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色．    
        titleStyle.setFillForegroundColor(HSSFColor.WHITE.index);// 设置单元格的背景颜色．
//        HSSFDataFormat format= workbook.createDataFormat();
//        titleStyle.setDataFormat(format.getFormat("#,##0.00"));
		return titleStyle;
	}
	
	/**
	 * 
	 * @Title:getFileCreateDate
	 * @Description:获取文件创建时间
	 * @param _file
	 * @return
	 * @date: 2016年8月3日
	 */
	public static String getFileCreateDate(File _file) {
		  File file = _file;
		  try {
		      Process ls_proc = Runtime.getRuntime().exec(
		      "cmd.exe /c dir " + file.getAbsolutePath() + " /tc");
		      BufferedReader br = new BufferedReader(new InputStreamReader(ls_proc.getInputStream()));
		      for (int i = 0; i < 5; i++) {
		          br.readLine();
		      }
		      String stuff = br.readLine();
		      StringTokenizer st = new StringTokenizer(stuff);
		      String dateC = st.nextToken();
		      String time = st.nextToken();
		      String datetime = dateC.concat(" " + time);
		      SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy/MM/dd");
		      SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		      datetime = formatter1.format(formatter2.parse(datetime));
		 
		      br.close();
		      return datetime;
		  } catch (Exception e) {
			  logger.error("",e);
		      return null;
		  }
	 }
	
	public static String formatNumber(String iniNum){
	   //去掉小数点后位数
	   String scale="";
	   if(iniNum.contains(".")){
		 String[]  iniNums=iniNum.replace('.', 'a').split("a");
		   iniNum=iniNums[0];
		   scale="."+iniNums[1];
	   }
        StringBuffer tmp = new StringBuffer(iniNum).reverse();
        // ② 替换这样的串：连续split位数字的串，其右边还有个数字，在串的右边添加逗号
        String retNum = Pattern.compile( "(\\d{3})(?=\\d)" )
                        .matcher( tmp.toString() ).replaceAll("$1,");
        // ③ 替换完后，再把串倒回去返回
        return new StringBuffer(retNum).reverse().append(scale).toString();
	}


	/**
	 * @Title:createStyle
	 * @Description:创建Workbook总体样式
	 * @param workbook
	 * @return
	 * @date: 2016年8月3日
	 */
	public static SXSSFWorkbook createStyle(SXSSFWorkbook workbook) {
		Font columnHeadFont = workbook.createFont();    
        columnHeadFont.setFontName("宋体");
        columnHeadFont.setFontHeightInPoints((short) 24);    
        columnHeadFont.setBoldweight(Font.BOLDWEIGHT_BOLD);    
        // 列头的样式    
        CellStyle columnHeadStyle = workbook.createCellStyle();    
        columnHeadStyle.setFont(columnHeadFont);    
        columnHeadStyle.setAlignment(CellStyle.ALIGN_CENTER); 
        columnHeadStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 上下居中    
        columnHeadStyle.setLocked(true);    
        columnHeadStyle.setWrapText(true);    
        columnHeadStyle.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色    
        // 设置单元格的背景颜色（单元格的样式会覆盖列或行的样式）    
        columnHeadStyle.setFillForegroundColor(HSSFColor.WHITE.index);
        return workbook;
	}
	
}
