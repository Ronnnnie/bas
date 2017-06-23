package com.billionsfinance.bas.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @ClassName: ExcelUtils
 * @Description: excel操作工具
 * @author zhanghs 2016年7月19日 下午4:11:24 Copyright: Copyright (c) 2016
 *         Company:佰仟金融
 */
public class ExcelUtils {

	/**
	 * <p>
	 * Title: parseProExcel
	 * </p>
	 * <p>
	 * Description: parseProExcel
	 * </p>
	 * 
	 * @param in
	 * @return list
	 * @throws IOException
	 */
	public static List<Map<String, Object>> parseProExcel(InputStream in) throws IOException {
		List<List<String>> dataList = new ArrayList<List<String>>();
		XSSFWorkbook workbook = new XSSFWorkbook(in);
		XSSFSheet sheet = workbook.getSheetAt(0);

		// 读取有效列的项目，如果遇到列的第一行值无效，则以后的列都不读取，值解析之前读取的数据
		XSSFRow projectRow = sheet.getRow(0);
		List<String> proList = validateColumn(projectRow); // 第一行数据

		int cols = proList.size(); // 有效列长度
		// 读取数据
		Map<String, Object> map = loadDataList(sheet, cols);
		dataList = (List<List<String>>) map.get("dataList");

		// 将数据以工号-数据的map结构保存下来
		List<Map<String, Object>> result = dataListToMap(proList, dataList);
		return result;
	}

	/**
	 * <p>
	 * Title: validateColumn
	 * </p>
	 * <p>
	 * Description: 如果碰到列为null，或者没有数值，则后面列的数据不再进行解析
	 * </p>
	 * 
	 * @param projectRow
	 * @return list
	 */
	public static List<String> validateColumn(XSSFRow projectRow) {
		int projectCellNum = projectRow.getLastCellNum();
		// 获取项目名，校验项目名是否正确
		Set<String> set = new HashSet<>();
		List<String> proList = new ArrayList<String>();
		for (int cols = 0; cols < projectCellNum; cols++) {
			if (projectRow.getCell(cols) != null) {
				String proName = projectRow.getCell(cols) + "";
				// 如果检测到有列的项目名为null，则认定后面都没有项目数据。跳出循环
				if (null == proName || 0 == proName.trim().length()) {
					break;
				}
				proList.add(proName);
				set.add(proName);
			}
		}
		if (set.size() != proList.size()) {
			return null;
		}
		return proList;
	}

	/**
	 * <p>
	 * Title: loadDataList
	 * </p>
	 * <p>
	 * Description: 读取上传工号和计算日期
	 * </p>
	 * 
	 * @param sheet
	 * @param cols
	 * @return map
	 */
	public static Map<String, Object> loadJobNosAndDate(XSSFSheet sheet, int cols) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
//		List<List<String>> dataList = new ArrayList<List<String>>();
//		List<String> userCodeList = new ArrayList<String>();
		List<String> rowData = new ArrayList<String>(); // 存放按行读出的数据
		String date = null;
		for (int rowNumOfSheet = 1; rowNumOfSheet <= sheet.getLastRowNum(); rowNumOfSheet++) {
			if (null != sheet.getRow(rowNumOfSheet)) {
				XSSFRow aRow = sheet.getRow(rowNumOfSheet);
//				rowData = new ArrayList<String>();

				if (1 == rowNumOfSheet) {
					date = aRow.getCell(1) + "";// 获取核算日期。
				}
				String value = aRow.getCell(0) + "";

				rowData.add(value);
			}
//			dataList.add(rowData);
		}
		returnMap.put("date", date);
		returnMap.put("dataList", rowData);
		return returnMap;
	}

	/**
	 * <p>
	 * Title: loadDataList
	 * </p>
	 * <p>
	 * Description: 读取有效列数的数据，装载成List
	 * </p>
	 * 
	 * @param sheet
	 * @param cols
	 * @return map
	 */
	public static Map<String, Object> loadDataList(XSSFSheet sheet, int cols) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<List<String>> dataList = new ArrayList<List<String>>();
		List<String> userCodeList = new ArrayList<String>();
		List<String> rowData = null; // 存放按行读出的数据
		for (int rowNumOfSheet = 0; rowNumOfSheet <= sheet.getLastRowNum(); rowNumOfSheet++) {
			if (null != sheet.getRow(rowNumOfSheet)) {
				XSSFRow aRow = sheet.getRow(rowNumOfSheet);
				rowData = new ArrayList<String>();

				for (int i = 0; i < cols; i++) {
					String value = aRow.getCell(i) + "";

					if (i == 0 && ( null == value || value.equals("null") || 0 == value.trim().length())) {
						// 如果出现没有工号的，则不添加数据。
						break;
					}
					
 					if (value == null || value.equals("") || value.equals("null")) {
						value = "";
					}else if (value.endsWith(".0")) {
						value = value.substring(0, value.length() - 2);
					}
					if (i == 0 && !value.equals("工号")) {
						userCodeList.add(value);
					}
				
					if (null != aRow.getCell(i) && HSSFCell.CELL_TYPE_NUMERIC == aRow.getCell(i).getCellType()) {
						if (HSSFDateUtil.isCellDateFormatted(aRow.getCell(i))) {
							Date d = aRow.getCell(i).getDateCellValue();
							DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
							value = formater.format(d);
						}
					}
					rowData.add(value);
				}
				if (!rowData.isEmpty()) {
					rowData.add(0, UUIDGenerator.getUUID());
					dataList.add(rowData);
				}
			}
		}
		returnMap.put("dataList", dataList);
		returnMap.put("userCodeList", userCodeList);
		return returnMap;
	}
	
	/**
	  * <p>Title: loadDataList</p>
	  * <p>Description: 读取有效列数的数据，装载成List</p>
	  * @param sheet  
	  * @param cols  
	  * @return map
	  */
	public static Map<String, Object> loadQualityExemptionDataList(XSSFSheet sheet, int cols) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<List<String>> dataList = new ArrayList<List<String>>();
		String errorMsg = "";
		List<String> rowData = null; // 存放按行读出的数据
		Date nowDate = new Date();
		DateFormat nowFormater = new SimpleDateFormat("yyyy/MM/dd");
		String nowDateValue = nowFormater.format(nowDate);
		for (int rowNumOfSheet = 0; rowNumOfSheet <= sheet.getLastRowNum(); rowNumOfSheet++) {
			if (null != sheet.getRow(rowNumOfSheet)) {
				XSSFRow aRow = sheet.getRow(rowNumOfSheet);
				rowData = new ArrayList<String>();
				
				for (int i = 0; i < cols; i++) {
					String value = aRow.getCell(i) + "";
					
					if(value.endsWith(".0")){
						value = value.substring(0, value.length()-2);
					}
					
					if (i == 0 && (value.equals("null") || null == value || 0 == value.trim().length())) {
						// 如果出现没有工号的，则不添加数据。
						break;
					}

					if(value==null||value.equals("")){
						value="";
					}
					
					if (i == 1 && (value.equals("null") || null == value || 0 == value.trim().length())) {
						// 如果时间为空则以当前时间为准
						errorMsg = "豁免月份不能为空";
						break;
					}
					
					if (i == 1 && (!value.equals("null") && null != value && 7 < value.trim().length())) {
						// 如果时间为空则以当前时间为准
						errorMsg = "豁免月份长度过长";
						break;
					}
					
					if (i == 2 && (value.equals("null") || null == value || 0 == value.trim().length())) {
						// 如果时间为空则以当前时间为准
						value = "未指定创建人";
					}
					
					if (HSSFCell.CELL_TYPE_NUMERIC == aRow.getCell(i).getCellType() && i==0) {
						if (HSSFDateUtil.isCellDateFormatted(aRow.getCell(i))) {
						Date d = aRow.getCell(i).getDateCellValue();
						DateFormat formater = new SimpleDateFormat("yyyy/MM");
						value = formater.format(d);
						}
					}
					
					if (HSSFCell.CELL_TYPE_NUMERIC == aRow.getCell(i).getCellType() && i==3) {
						if (HSSFDateUtil.isCellDateFormatted(aRow.getCell(i))) {
						Date d = aRow.getCell(i).getDateCellValue();
						DateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
						value = formater.format(d);
						}
					}
					
					if (i == 3 && (value.equals("null") || null == value || 0 == value.trim().length())) {
						// 如果时间为空则以当前时间为准
						value = nowDateValue;
					}
					rowData.add(value);
				}
				if (!rowData.isEmpty()) {
					rowData.add(0, UUIDGenerator.getUUID());
					dataList.add(rowData);
				}
			}
		}
		returnMap.put("dataList", dataList);
		returnMap.put("errorMsg", errorMsg);
		return returnMap;
	}
	
	public static Map<String, Object> loadBounsIsprepareCityList(XSSFSheet sheet, int cols) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, String> existMap = new HashMap<String, String>();
		List<List<String>> dataList = new ArrayList<List<String>>();
		String errorMsg = "";
		List<String> rowData = null; // 存放按行读出的数据
		Date nowDate = new Date();
		DateFormat nowFormater = new SimpleDateFormat("yyyy/MM/dd");
		String nowDateValue = nowFormater.format(nowDate);
		for (int rowNumOfSheet = 0; rowNumOfSheet <= sheet.getLastRowNum(); rowNumOfSheet++) {
			if (null != sheet.getRow(rowNumOfSheet)) {
				XSSFRow aRow = sheet.getRow(rowNumOfSheet);
				rowData = new ArrayList<String>();
				
				for (int i = 0; i < cols; i++) {
					String value = aRow.getCell(i) + "";
					
					if(value.endsWith(".0")){
						value = value.substring(0, value.length()-2);
					}
					
					if (i == 0 && (value.equals("null") || null == value || 0 == value.trim().length())) {
						// 如果没有月份则不添加
						errorMsg = "月份不能为空";
						break;
					}
					if (i == 1 && (value.equals("null") || null == value || 0 == value.trim().length())) {
						// 如果没有城市则不添加
						errorMsg = "城市不能为空";
						break;
					}
					
					if (i == 1 && (!value.equals("null") || null != value || 0 < value.trim().length())) {
						if(existMap.get(value)!=null){
							//城市不能重复
							errorMsg="城市不能重复";
							break;
						}else{							
							existMap.put(value, value);
						}
					}

					if(value==null||value.equals("")){
						value="";
					}
					if (i == 3 && (value.equals("null") || null == value || 0 == value.trim().length())) {
						// 如果时间为空则以当前时间为准
						value = "未指定创建人";
					}
					
					if (HSSFCell.CELL_TYPE_NUMERIC == aRow.getCell(i).getCellType() && i==0) {
						if (HSSFDateUtil.isCellDateFormatted(aRow.getCell(i))) {
							Date d = aRow.getCell(i).getDateCellValue();
							DateFormat formater = new SimpleDateFormat("yyyy/MM");
							value = formater.format(d);
						}
					}
					if (HSSFCell.CELL_TYPE_NUMERIC == aRow.getCell(i).getCellType() && i==4) {
						if (HSSFDateUtil.isCellDateFormatted(aRow.getCell(i))) {
						Date d = aRow.getCell(i).getDateCellValue();
						DateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
						value = formater.format(d);
						}
					}
					
					
					if (i == 4 && (value.equals("null") || null == value || 0 == value.trim().length())) {
						// 如果时间为空则以当前时间为准
						value = nowDateValue;
					}
					rowData.add(value);
				}
				if (!rowData.isEmpty()) {
					dataList.add(rowData);
				}
			}
		}
		returnMap.put("dataList", dataList);
		returnMap.put("errorMsg", errorMsg);
		return returnMap;
	}

	/**
	 * <p>
	 * Title: dataListToMap
	 * </p>
	 * <p>
	 * Description: dataListToMap
	 * </p>
	 * 
	 * @param proList
	 * @param dataList
	 * @return list
	 */
	public static List<Map<String, Object>> dataListToMap(List<String> proList, List<List<String>> dataList) {
		List<Map<String, Object>> mapDatas = new ArrayList<Map<String, Object>>();
		Map<String, Object> proData = null; // 工号-项目数据
		Map<String, Object> proValues = null; // 项目名称-值
		// dataList是数据list
		for (int j = 1; j < dataList.size(); j++) {
			proData = new HashMap<>();
			proValues = new HashMap<>();
			for (int k = 0; k < proList.size(); k++) {
				proValues.put(proList.get(k), dataList.get(j).get(k));
			}
			proData.put(dataList.get(j).get(0), proValues);
			mapDatas.add(proData);
		}
		return mapDatas;
	}

	/**
	 * exportData2Excel(这里用一句话描述这个方法的作用)
	 *
	 * @Title: exportData2Excel @Description: @param row0 列名 @param data
	 * 数据 @param file 文件 @throws
	 */
	public static void exportData2Excel(List<String> row0, List<Map<String, Object>> data, File file) {
		XSSFWorkbook wb = new XSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		XSSFSheet sheet = wb.createSheet("工资核算表");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		XSSFRow row = sheet.createRow(0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		XSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		XSSFCell cell = null;
		for (int i = 0; i < row0.size(); i++) {

			cell = row.createCell(i);
			cell.setCellValue(row0.get(i));
			cell.setCellStyle(style);
		}

		for (int i = 0; i < data.size(); i++) {

			row = sheet.createRow(i + 1);
			Map<String, Object> tempMap = data.get(i);
			// 组装数据
			// buildData(tempMap, row); // 已转入server

		}
		// 第六步，将文件存到指定位置
		try {
			sheet.setForceFormulaRecalculation(true);
			FileOutputStream fout = new FileOutputStream(file);
			wb.write(fout);
			fout.close();
		} catch (Exception e) {
			throw new RuntimeException("IO错误，导出excel错误");
		}
	}

	// private static void buildData(Map<String,Object> tempMap,XSSFRow row){
	// int cellnum = 0;
	// // 1.序列号
	// row.createCell(cellnum++).setCellValue(tempMap.get("EMPLOYEE_ID") + "");
	// // 2.公司
	// row.createCell(cellnum++).setCellValue("佰仟金融");
	// // 3.员工编号
	// row.createCell(cellnum++).setCellValue(tempMap.get("JOB_NO") + "");
	// // 4.姓名
	// row.createCell(cellnum++).setCellValue(tempMap.get("EMPLOYEE_NAME") +
	// "");
	// // 5.部门
	// row.createCell(cellnum++).setCellValue("部门没有这个字段？");
	// // 6.岗位
	// row.createCell(cellnum++).setCellValue(tempMap.get("POSITION_NAME") +
	// "");
	// // 7.员工属省
	// row.createCell(cellnum++).setCellValue(tempMap.get("EMP_PROVINCE") + "");
	// // 8.员工属地
	// row.createCell(cellnum++).setCellValue(tempMap.get("EMP_CITY") + "");
	// // 9.入职时间
	// row.createCell(cellnum++).setCellValue(tempMap.get("ENTRY_DATE") + "");
	// // 10.离职日期
	// row.createCell(cellnum++).setCellValue(tempMap.get("LEAVE_DATE") + "");
	// // 11.员工状态
	// row.createCell(cellnum++).setCellValue(tempMap.get("ISWORK") + "");
	// // 12.历史状态
	// row.createCell(cellnum++).setCellValue(tempMap.get("STATUS") + "");
	// // 13.计税类别
	// row.createCell(cellnum++).setCellValue(tempMap.get("TAXTYPE") + "");
	// // 14.应出勤天数
	// row.createCell(cellnum++).setCellValue(tempMap.get("SATTEND") + "");
	// // 15.实际出勤天数
	// row.createCell(cellnum++).setCellValue(tempMap.get("FATTEND") + "");
	// // 16.基本工资
	// row.createCell(cellnum++).setCellValue(tempMap.get("BSALARY") + "");
	// // 17.综合补贴
	// row.createCell(cellnum++).setCellValue(tempMap.get("TSUBSI") + "");
	// // 18.月度奖金基数
	// row.createCell(cellnum++).setCellValue(tempMap.get("MPRIZEB") + "");
	// // 19.季度奖金基数
	// row.createCell(cellnum++).setCellValue(tempMap.get("QPRIZEB") + "");
	// // 20.年度奖金基数
	// row.createCell(cellnum++).setCellValue(tempMap.get("YPRIZEB") + "");
	// // 21.季度奖金
	// row.createCell(cellnum++).setCellValue(tempMap.get("QPRIZE") + "");
	// // 22.月工资总额 --公式
	// row.createCell(cellnum++).setCellFormula("ROUND(SUM(P2+Q2+R2+S2/3+T2/12),0)");
	// // 23.年奖月提 --公式
	// row.createCell(cellnum++).setCellFormula("ROUND(IF(AS2=0,V2/12*1.5,V2/12*2),2)");
	// // 24.话费补贴标准
	// row.createCell(cellnum++).setCellValue(tempMap.get("CALLSUBS") + "");
	// // 25.交通补贴标准
	// row.createCell(cellnum++).setCellValue(tempMap.get("TSUBS") + "");
	// // 26.派驻津贴标准
	// row.createCell(cellnum++).setCellValue(tempMap.get("ESUBS") + "");
	// // 27.高温补贴标准
	// row.createCell(cellnum++).setCellValue(tempMap.get("HTSUBS") + "");
	// // 28.低温补贴标准
	// row.createCell(cellnum++).setCellValue(tempMap.get("LTSUBS") + "");
	// // 29.平时加班时数
	// row.createCell(cellnum++).setCellValue(tempMap.get("OWOVERH") + "");
	// // 30.周末加班时数
	// row.createCell(cellnum++).setCellValue(tempMap.get("WWOVERH") + "");
	// // 31.法定节假日加班时数
	// row.createCell(cellnum++).setCellValue(tempMap.get("HWOVERH") + "");
	// // 32.病假（天）
	// row.createCell(cellnum++).setCellValue(tempMap.get("SICKLEAVE") + "");
	// // 33.事假（天）
	// row.createCell(cellnum++).setCellValue(tempMap.get("NOLEAVE") + "");
	// // 34.产假（天）
	// row.createCell(cellnum++).setCellValue(tempMap.get("MATELEAVE") + "");
	// // 35.考勤类别
	// row.createCell(cellnum++).setCellValue(tempMap.get("ATTENDT") + "");
	// // 36.实发基本工资 --公式
	// row.createCell(cellnum++).setCellFormula("ROUND(P2/N2*O2,2)");
	// // 37.实发综合补贴 --公式
	// row.createCell(cellnum++).setCellFormula("ROUND(Q2/N2*O2,2)");
	// // 38.实发话费补贴 --公式
	// row.createCell(cellnum++).setCellFormula("ROUND(X2/N2*O2,2)");
	// // 39.实发交通补贴 --公式
	// row.createCell(cellnum++).setCellFormula("ROUND(Y2/N2*O2,2)");
	// // 40.实发派驻补贴
	// row.createCell(cellnum++).setCellValue(tempMap.get("ESUBSR") + "");
	// // 41.实发高温补贴 --公式
	// row.createCell(cellnum++).setCellFormula("ROUND(AA2/N2*O2,2)");
	// // 42.实发低温补贴 --公式
	// row.createCell(cellnum++).setCellFormula("ROUND(AB2/N2*O2,2)");
	// // 43.绩效系数
	// row.createCell(cellnum++).setCellValue(tempMap.get("PERCOEF") + "");
	// // 44.绩效奖金
	// row.createCell(cellnum++).setCellValue(tempMap.get("PERPRIZE") + "");
	// // 45.销售奖金
	// row.createCell(cellnum++).setCellValue(tempMap.get("SALEPRIZE") + "");
	// // 46.其他奖金
	// row.createCell(cellnum++).setCellValue(tempMap.get("OPRIZE") + "");
	// // 47.夜班补贴
	// row.createCell(cellnum++).setCellValue(tempMap.get("NIGHTSUB") + "");
	// // 48.其他补贴
	// row.createCell(cellnum++).setCellValue(tempMap.get("OSUBSI") + "");
	// // 49.补发工资
	// row.createCell(cellnum++).setCellValue(tempMap.get("SSALARY") + "");
	// // 50.节日津贴
	// row.createCell(cellnum++).setCellValue(tempMap.get("HOLSUBSI") + "");
	// // 51.免税项目
	// row.createCell(cellnum++).setCellValue(tempMap.get("EXEITEM") + "");
	// // 52.实发季度奖金
	// row.createCell(cellnum++).setCellValue(tempMap.get("QPRIZER") + "");
	// // 53.加班费 --公式
	// row.createCell(cellnum++).setCellFormula("ROUND(P2/21.75/8*AC2*1.5+P2/21.75/8*AD2*2+P2/21.75/8*AE2*3,2)");
	// // 54.病假扣款 --公式
	// row.createCell(cellnum++).setCellFormula(
	// "ROUND(IF(AI2=1,(P2+Q2)/N2*AF2*0.4+R2*AQ2/N2*AF2,IF(AI2=2,(P2+Q2+R2)/N2*AF2*0.4,(P2+Q2)/N2*AF2*0.4)),2)");
	// // 55.事假扣款 --公式
	// row.createCell(cellnum++)
	// .setCellFormula("ROUND(IF(AI2=1,(P2+Q2)/N2*AG2+R2*AQ2/N2*AG2,(P2+Q2+R2)/N2*AG2),2)");
	// // 56.产假扣款 --公式
	// row.createCell(cellnum++).setCellFormula("ROUND(IF(AI2=3,0,R2*AQ2/N2*AH2),2)");
	// // 57.应发金额 --公式
	// row.createCell(cellnum++).setCellFormula("ROUND(SUM(AJ2:AP2)+SUM(AR2:BA2)-BB2-BC2-BD2,2)");
	// // 58.社保个人合计 --公式
	// row.createCell(cellnum++).setCellFormula("ROUND(BI2+BK2+BM2,2)");
	// // 59.社保单位合计 --公式
	// row.createCell(cellnum++).setCellFormula("ROUND(BH2+BJ2+BL2+BN2+BO2,2)");
	// // 60.养老单位
	// row.createCell(cellnum++).setCellValue(tempMap.get("PASTUREC") + "");
	// // 61.养老个人
	// row.createCell(cellnum++).setCellValue(tempMap.get("PASTUREP") + "");
	// // 62.医疗单位
	// row.createCell(cellnum++).setCellValue(tempMap.get("HEALTHC") + "");
	// // 63.医疗个人
	// row.createCell(cellnum++).setCellValue(tempMap.get("HEALTHP") + "");
	// // 64.失业单位
	// row.createCell(cellnum++).setCellValue(tempMap.get("OUTWORKC") + "");
	// // 65.失业个人
	// row.createCell(cellnum++).setCellValue(tempMap.get("OUTWORKP") + "");
	// // 66.工伤单位
	// row.createCell(cellnum++).setCellValue(tempMap.get("INDUSTC") + "");
	// // 67.生育单位
	// row.createCell(cellnum++).setCellValue(tempMap.get("REPRODC") + "");
	// // 68.住房公积金单位
	// row.createCell(cellnum++).setCellValue(tempMap.get("FUNDC") + "");
	// // 69.住房公积金个人
	// row.createCell(cellnum++).setCellValue(tempMap.get("FUNDP") + "");
	// // 70.应税金额
	// row.createCell(cellnum++).setCellValue(tempMap.get("TAMOUNT") + "");
	// // 71.工资税 --公式
	// row.createCell(cellnum++).setCellFormula(
	// "ROUND(IF(M2=\"A\",MAX((BR2-3500)*0.05*{0.6,2,4,5,6,7,9}-5*{0,21,111,201,551,1101,2701},0),IF(M2=\"C\",MAX((BR2-4800)*0.05*{0.6,2,4,5,6,7,9}-5*{0,21,111,201,551,1101,2701},0),IF(BR2<=20000,BR2*0.2,(IF(AND(BR2>20000,BR2<=50000),BR2*0.3-2000,(IF(BR2>50000,BR2*0.4-7000))))))),2)");
	// // 72.工资税率 --公式
	// row.createCell(cellnum++).setCellFormula(
	// "IF(M2=\"A\",IF(BR2<=3500,0,IF(BR2<=5000,3%,IF(BR2<=8000,10%,IF(BR2<=12500,20%,IF(BR2<=38500,25%,IF(BR2<=58500,30%,IF(BR2<=83500,35%,45%))))))),\"\")");
	// // 73.税后扣款
	// row.createCell(cellnum++).setCellValue(tempMap.get("TDEDUCT") + "");
	// // 74.已发节日津贴
	// row.createCell(cellnum++).setCellValue(tempMap.get("HOLSUBSID") + "");
	// // 75.税后调整
	// row.createCell(cellnum++).setCellValue(tempMap.get("ATADJUST") + "");
	// // 76.银行实发 --公式
	// row.createCell(cellnum++).setCellFormula("ROUND(BE2-BF2-BS2-BV2-BU2-BQ2-BW2,2)");
	// // 77.身份证号码
	// row.createCell(cellnum++).setCellValue(tempMap.get("CARDID") + "");
	// // 78.计薪状态
	// row.createCell(cellnum++).setCellValue(tempMap.get("PAIDSTATUS") + "");
	// // 79.银行账号
	// row.createCell(cellnum++).setCellValue(tempMap.get("BANDNO") + "");
	// // 80.银行账号开户行名称
	// row.createCell(cellnum++).setCellValue(tempMap.get("ACCOPENBN") + "");
	// // 81.银行账号开户地
	// row.createCell(cellnum++).setCellValue(tempMap.get("ACCOPENBA") + "");
	// // 82.报税公司
	// row.createCell(cellnum++).setCellValue(tempMap.get("FILLBEC") + "");
	// // 83.报税城市
	// row.createCell(cellnum++).setCellValue(tempMap.get("FILLBECITY") + "");
	// }

}
