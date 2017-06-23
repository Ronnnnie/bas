package com.billionsfinance.bas.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
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

import com.billionsfinance.bas.entity.DataCheckVO;
import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.server.IDataCheckServer;
import com.billionsfinance.bas.server.impl.DataCheckServer;
import com.billionsfinance.bas.util.AjaxJson;
import com.billionsfinance.bas.util.ResponseUtil;
import com.billionsfinance.bas.util.StringUtil;

/**
 * @ClassName: DataCheckController
 * @Description: 数据校验控制器
 * @author FeimaZhou 2017年3月27日 上午9:56:46 Copyright: Copyright (c) 2017 Company:佰仟金融
 */
@Controller
@RequestMapping("/dataCheckServer")
public class DataCheckController {

	/** 日志记录 */
	private static final Log LOGGER = LogFactory.getLog(DataCheckController.class);

	private static final IDataCheckServer dataCheckServer = new DataCheckServer();

	/**
	 * @methodName: toDataCheckDetail
	 * @Description: toDataCheckDetail
	 * @return java.lang.String
	 */
	@RequestMapping("/toDataCheckDetail")
	public String toDataCheckDetail() {
		return "dataCheck/dataCheck";
	}

	/**
	 * 明细合同查询.
	 * @param queryDataCheckDetail
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 */
	@RequestMapping("/queryDataCheckDetail")
	public void queryDataCheckDetail(Integer page, Integer rows, DataCheckVO vo,HttpServletRequest request, HttpServletResponse response) {
		PageVO pageVO = null;
		try {
			if(vo!=null&&vo.getCheckType()!=null&&!("").equals(vo.getCheckType())){
				pageVO = new PageVO();
				pageVO.setPageSize(rows);
				pageVO.setPageNo(page);
				if(vo.getCheckType().equals("01")){
					pageVO = dataCheckServer.queryDataCheckTableMenuOne(pageVO);
				}else if(vo.getCheckType().equals("02")){
					pageVO = dataCheckServer.queryDataCheckTableMenuTwo(pageVO);
				}else if(vo.getCheckType().equals("03")){
					pageVO = dataCheckServer.queryDataCheckTableMenuThree(pageVO);
				}else if(vo.getCheckType().equals("04")){
					pageVO = dataCheckServer.queryDataCheckTableMenuFour(pageVO);
				}else if(vo.getCheckType().equals("05")){
					pageVO = dataCheckServer.queryDataCheckTableMenuFive(pageVO);
				}else if(vo.getCheckType().equals("09")){
					pageVO = dataCheckServer.queryDataCheckTableMenuNine(pageVO);
				}else if(vo.getCheckType().equals("12")){
					pageVO = dataCheckServer.queryDataCheckTableMenuTwelve(pageVO);
				}else{
					LOGGER.error("表单无法校验,请选择校验表单!");
				}
			}
		} catch (Exception e) {
			LOGGER.error("查询还款差额划拨-还款明细-核算失败!", e);
		}finally {
			ResponseUtil.sendJSON(response, pageVO);
		}
	}
	
	/**
	 * 匹配合同数统计
	 * @param vo
	 * @param request
	 * @param response
	 */
	@RequestMapping("queryDataCheckDetailCount")
	public void queryDataCheckDetailCount(DataCheckVO vo,HttpServletRequest request, HttpServletResponse response){
		//统计合同总数
		Map<String,Object> map = new HashMap<String,Object>();
		if(vo!=null&&vo.getCheckType()!=null&&!("").equals(vo.getCheckType())){
			if(vo.getCheckType().equals("01")){
				map = dataCheckServer.queryDataCheckTableMenuOneCount();
			}else if(vo.getCheckType().equals("02")){
				map = dataCheckServer.queryDataCheckTableMenuTwoCount();
			}else if(vo.getCheckType().equals("03")){
				map = dataCheckServer.queryDataCheckTableMenuThreeCount();
			}else if(vo.getCheckType().equals("04")){
				map = dataCheckServer.queryDataCheckTableMenuFourCount();
			}else if(vo.getCheckType().equals("05")){
				map = dataCheckServer.queryDataCheckTableMenuFiveCount();
			}else if(vo.getCheckType().equals("09")){
				map = dataCheckServer.queryDataCheckTableMenuNineCount();
			}else if(vo.getCheckType().equals("12")){
				map = dataCheckServer.queryDataCheckTableMenuTwelveCount();
			}else{
				LOGGER.error("表单无法校验,请选择校验表单!");
			}
		}
		ResponseUtil.sendJSON(response, map);
	}
	
	/**
	 * 
	 * @Description  逾期汇总合同导出
	 * @throws ServletException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/excelExport")
	public AjaxJson excelExport(HttpServletRequest request, HttpServletResponse response,DataCheckVO vo) throws ServletException, IOException{
		AjaxJson ajaxJson = null;
		try {
			
			ajaxJson = new AjaxJson();
			String fileName = "导出Excel-表单"+vo.getCheckType()+"校验数据.xlsx";  
			SXSSFWorkbook wb = new SXSSFWorkbook(1024); // 这里100是在内存中的数量，如果大于此数量时，会写到硬盘，以避免在内存导致内存溢出  
			Sheet sh = wb.createSheet();
			List<Map<String,Object>> list = tableMenuListCheck(vo.getCheckType(), sh);
			if (list==null||list.size()==0) {
				return ajaxJson;
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
		} catch (Exception e) {
			LOGGER.error("数据导出错误!", e);
		}
        return ajaxJson;
	}
	
	public List<Map<String,Object>> tableMenuListCheck(String checkType,Sheet sh){
		List<Map<String,Object>> list = null;
		if("01".equals(checkType)){
			list = dataCheckServer.queryDataCheckTableMenuOneFindAll();
			for (int i = 0; i < list.size()+1; i++) { 
				sh.setColumnWidth(i,4500);
			    Row row = sh.createRow(i);
			    if (i==0) {
			    	row.createCell(0).setCellValue("十四级分类");  
			    	row.createCell(1).setCellValue("放款本金");  
			    	row.createCell(2).setCellValue("实还本金");  
			    	row.createCell(3).setCellValue("逾期本金");  
			    	row.createCell(4).setCellValue("未到期本金");  
			    	row.createCell(5).setCellValue("差异");  
				}else{
				    row.createCell(0).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("classfy")));
				    row.createCell(1).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("fkbj")));  
				    row.createCell(2).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("shbj")));  
				    row.createCell(3).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("yqbj")));  
				    row.createCell(4).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("wdqbj")));  
				    row.createCell(5).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("cy")));  
				}
			}
		}else if("02".equals(checkType)){
			list = dataCheckServer.queryDataCheckTableMenuTwoFindAll();
			for (int i = 0; i < list.size()+1; i++) { 
				sh.setColumnWidth(i,4500);
			    Row row = sh.createRow(i);
			    if (i==0) {
			    	row.createCell(0).setCellValue("所属方");  
			    	row.createCell(1).setCellValue("保证方");  
			    	row.createCell(2).setCellValue("到期本金");  
			    	row.createCell(3).setCellValue("实还本金");  
			    	row.createCell(4).setCellValue("逾期本金");  
			    	row.createCell(5).setCellValue("到期利息");  
			    	row.createCell(6).setCellValue("实还利息"); 
			    	row.createCell(7).setCellValue("逾期利息"); 
			    	row.createCell(8).setCellValue("本金差异");  
			    	row.createCell(9).setCellValue("利息差异");  
				}else{
				    row.createCell(0).setCellValue(StringUtil.companyFormatter(StringUtil.isNullOrEmpty(list.get(i-1).get("belong"))));
				    row.createCell(1).setCellValue(StringUtil.companyFormatter(StringUtil.isNullOrEmpty(list.get(i-1).get("guaranteeparty"))));  
				    row.createCell(2).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("dqbj")));  
				    row.createCell(3).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("shbj")));  
				    row.createCell(4).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("yqbj")));  
				    row.createCell(5).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("dqlx")));  
				    row.createCell(6).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("shlx")));  
				    row.createCell(7).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("yqlx")));  
				    row.createCell(8).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("bjcy")));  
				    row.createCell(9).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("lxcy")));  
				}
			}
		}else if("03".equals(checkType)){
			list = dataCheckServer.queryDataCheckTableMenuThreeFindAll();
			for (int i = 0; i < list.size()+1; i++) { 
				sh.setColumnWidth(i,4500);
			    Row row = sh.createRow(i);
			    if (i==0) {
			    	row.createCell(0).setCellValue("所属方");  
			    	row.createCell(1).setCellValue("保证方");  
			    	row.createCell(2).setCellValue("到期本金");  
			    	row.createCell(3).setCellValue("实还本金");  
			    	row.createCell(4).setCellValue("逾期本金");  
			    	row.createCell(5).setCellValue("到期利息");  
			    	row.createCell(6).setCellValue("实还利息"); 
			    	row.createCell(7).setCellValue("逾期利息"); 
			    	row.createCell(8).setCellValue("本金差异");  
			    	row.createCell(9).setCellValue("利息差异");  
				}else{
				    row.createCell(0).setCellValue(StringUtil.companyFormatter(StringUtil.isNullOrEmpty(list.get(i-1).get("belong"))));
				    row.createCell(1).setCellValue(StringUtil.companyFormatter(StringUtil.isNullOrEmpty(list.get(i-1).get("guaranteeparty"))));  
				    row.createCell(2).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("dqbj")));  
				    row.createCell(3).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("shbj")));  
				    row.createCell(4).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("yqbj")));  
				    row.createCell(5).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("dqlx")));  
				    row.createCell(6).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("shlx")));  
				    row.createCell(7).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("yqlx")));  
				    row.createCell(8).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("bjcy")));  
				    row.createCell(9).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("lxcy")));  
				}
			}
		}else if("04".equals(checkType)){
			list = dataCheckServer.queryDataCheckTableMenuFourFindAll();
			for (int i = 0; i < list.size()+1; i++) { 
				sh.setColumnWidth(i,4500);
			    Row row = sh.createRow(i);
			    if (i==0) {
			    	row.createCell(0).setCellValue("是否当期");  
			    	row.createCell(1).setCellValue("保证方");  
			    	row.createCell(2).setCellValue("到期本金");  
			    	row.createCell(3).setCellValue("未到期本金");  
			    	row.createCell(4).setCellValue("代偿本金");  
			    	row.createCell(5).setCellValue("到期利息");  
			    	row.createCell(6).setCellValue("未到期利息"); 
			    	row.createCell(7).setCellValue("代偿利息"); 
			    	row.createCell(8).setCellValue("本金差异");  
			    	row.createCell(9).setCellValue("利息差异");  
				}else{
				    row.createCell(0).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("sfdq")));
				    row.createCell(1).setCellValue(StringUtil.companyFormatter(StringUtil.isNullOrEmpty(list.get(i-1).get("guaranteeparty"))));  
				    row.createCell(2).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("dqbj")));  
				    row.createCell(3).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("wdqbj")));  
				    row.createCell(4).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("dcbj")));  
				    row.createCell(5).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("dqlx")));  
				    row.createCell(6).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("wdqlx")));  
				    row.createCell(7).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("dclx")));  
				    row.createCell(8).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("bjcy")));  
				    row.createCell(9).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("lxcy")));  
				}
			}
		}else if("05".equals(checkType)){
			list = dataCheckServer.queryDataCheckTableMenuFiveFindAll();
			for (int i = 0; i < list.size()+1; i++) { 
				sh.setColumnWidth(i,4500);
			    Row row = sh.createRow(i);
			    if (i==0) {
			    	row.createCell(0).setCellValue("是否当期");  
			    	row.createCell(1).setCellValue("保证方");  
			    	row.createCell(2).setCellValue("到期本金");  
			    	row.createCell(3).setCellValue("未到期本金");  
			    	row.createCell(4).setCellValue("赎回本金");  
			    	row.createCell(5).setCellValue("本金差异");  
				}else{
				    row.createCell(0).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("sfdq")));
				    row.createCell(1).setCellValue(StringUtil.companyFormatter(StringUtil.isNullOrEmpty(list.get(i-1).get("guaranteeparty"))));  
				    row.createCell(2).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("dqbj")));  
				    row.createCell(3).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("wdqbj")));  
				    row.createCell(4).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("ransomsum")));  
				    row.createCell(5).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("bjcy")));  
				}
			}
		}else if("09".equals(checkType)){
			list = dataCheckServer.queryDataCheckTableMenuNineFindAll();
			for (int i = 0; i < list.size()+1; i++) { 
				sh.setColumnWidth(i,4500);
			    Row row = sh.createRow(i);
			    if (i==0) {
			    	row.createCell(0).setCellValue("是否当期");  
			    	row.createCell(1).setCellValue("受让方");  
			    	row.createCell(2).setCellValue("到期本金");  
			    	row.createCell(3).setCellValue("未到期本金");  
			    	row.createCell(4).setCellValue("转让本金");  
			    	row.createCell(5).setCellValue("本金差异");  
				}else{
				    row.createCell(0).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("sfdq")));
				    row.createCell(1).setCellValue(StringUtil.companyFormatter(StringUtil.isNullOrEmpty(list.get(i-1).get("srf"))));  
				    row.createCell(2).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("dqbj")));  
				    row.createCell(3).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("wdqbj")));  
				    row.createCell(4).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("zrbj")));  
				    row.createCell(5).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("bjcy")));  
				}
			}
		}else if("12".equals(checkType)){
			list = dataCheckServer.queryDataCheckTableMenuTwelveFindAll();
			for (int i = 0; i < list.size()+1; i++) { 
				sh.setColumnWidth(i,4500);
			    Row row = sh.createRow(i);
			    if (i==0) {
			    	row.createCell(0).setCellValue("所属表单");  
			    	row.createCell(1).setCellValue("所属方");  
			    	row.createCell(2).setCellValue("保证方");  
			    	row.createCell(3).setCellValue("上月累计本金");  
			    	row.createCell(4).setCellValue("当月本金");  
			    	row.createCell(5).setCellValue("当月累计本金");  
			    	row.createCell(6).setCellValue("上月累计利息");  
			    	row.createCell(7).setCellValue("当月利息");  
			    	row.createCell(8).setCellValue("当月累计利息");  
			    	row.createCell(9).setCellValue("本金差异");  
			    	row.createCell(10).setCellValue("利息差异");  
				}else{
				    row.createCell(0).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("shbd")));
				    row.createCell(1).setCellValue(StringUtil.companyFormatter(StringUtil.isNullOrEmpty(list.get(i-1).get("belong"))));  
				    row.createCell(2).setCellValue(StringUtil.companyFormatter(StringUtil.isNullOrEmpty(list.get(i-1).get("guaranteeparty"))));  
				    row.createCell(3).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("syljbj")));  
				    row.createCell(4).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("dybj")));  
				    row.createCell(5).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("dyljbj")));  
				    row.createCell(6).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("syljlx")));  
				    row.createCell(7).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("dylx")));  
				    row.createCell(8).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("dyljlx")));  
				    row.createCell(9).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("bjcy")));  
				    row.createCell(10).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("lxcy")));  
				}
			}
		}
		return list;
	}
}