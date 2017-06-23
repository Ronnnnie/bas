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

import com.billionsfinance.bas.entity.AccountTotalCheckingAccountVO;
import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.entity.RepaymentDetailCheckAccountVO;
import com.billionsfinance.bas.server.IRepaymentDetailCheckAccountServer;
import com.billionsfinance.bas.server.impl.RepaymentDetailCheckAccountServer;
import com.billionsfinance.bas.util.AjaxJson;
import com.billionsfinance.bas.util.ResponseUtil;
import com.billionsfinance.bas.util.StringUtil;

/**
 * @ClassName: 还款明细对账Controller
 * @Description: RepaymentDetailCheckAccountController
 * @author zhouFM
 * @Copyright Copyright (c) 2016 2016年11月18日 上午15:05:04 Company:佰仟金融
 */
@Controller
@RequestMapping("/repaymentDetailCheckAccountServer")
public class RepaymentDetailCheckAccountController {

	/** 日志记录 */
	private static final Log LOGGER = LogFactory.getLog(RepaymentDetailCheckAccountController.class);

	private static final IRepaymentDetailCheckAccountServer rdcaServer = new RepaymentDetailCheckAccountServer();

	/**
	 * @methodName: toRepaymentDetailCheckAccount
	 * @Description: 跳转还款明细对账页面
	 * @return java.lang.String
	 */
	@RequestMapping("/toRepaymentDetailCheckAccount")
	public String toRepaymentDetailCheckAccount() {
		return "repaymentDetailCheckAccount/repaymentDetailCheckAccount";
	}

	/**
	 * 查询还款明细对账合同.
	 * @param DayTradingDetailVO
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 */
	@RequestMapping("/queryRepaymentDetailCheckAccount")
	public void queryRepaymentDetailCheckAccount(Integer page, Integer rows, RepaymentDetailCheckAccountVO vo,HttpServletRequest request, HttpServletResponse response) {
		PageVO pageVO = null;
		try {
			pageVO = new PageVO();
			pageVO.setPageSize(rows);
			pageVO.setPageNo(page);
			pageVO = rdcaServer.queryRepaymentDetailCheckAccount(pageVO, vo);
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
	@RequestMapping("queryRepaymentDetailCheckAccountTotal")
	public void queryRepaymentDetailCheckAccountTotal(RepaymentDetailCheckAccountVO vo,HttpServletRequest request, HttpServletResponse response){
		//统计合同总数
		Map<String,Object> map = rdcaServer.queryRepaymentDetailCheckAccountTotal(vo);
		ResponseUtil.sendJSON(response, map);
	}
	
	/**
	 * 
	 * @Description 还款明细对账表导出
	 * @throws ServletException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/exportExcel")
	public AjaxJson exportExcel(HttpServletRequest request, HttpServletResponse response,RepaymentDetailCheckAccountVO vo) throws ServletException, IOException{
		AjaxJson ajaxJson = null;
		try {
			ajaxJson = new AjaxJson();
			String fileName = "导出Excel-还款明细对账表.xlsx";  
			List<Map<String,Object>> list = rdcaServer.queryRepaymentDetailCheckAccountFindAll(vo);
			
			SXSSFWorkbook wb = new SXSSFWorkbook(1000); // 这里100是在内存中的数量，如果大于此数量时，会写到硬盘，以避免在内存导致内存溢出  
			Sheet sh = wb.createSheet();  
			if (list.size()==0) {
				request.getSession().setAttribute("exportedFlag", "true");
				ajaxJson.setMsg("暂无数据可导出!");
				return ajaxJson;
			}
			for (int i = 0; i < list.size()+1; i++) { 
			    Row row = sh.createRow(i);
			    if (i==0) {
			    	row.createCell(0).setCellValue("对账编号");  
				    
				    //第一行第2列  
			    	row.createCell(1).setCellValue("对账日期");  
				
				    //第一行第3列  
			    	row.createCell(2).setCellValue("合同号");  
				
				    //第一行第4列  
			    	row.createCell(3).setCellValue("合同注册日期");  
				
				    //第一行第5列  
			    	row.createCell(4).setCellValue("贷款子类型");
			    	
			    	//第一行第6列  
			    	row.createCell(5).setCellValue("还款类型");  
				    
				    //第一行第7列  
			    	row.createCell(6).setCellValue("代偿日");  
				    
				    //第一行第8列  
			    	row.createCell(7).setCellValue("赎回日"); 
				    
				    //第一行第9列  
			    	row.createCell(8).setCellValue("资产所属方");  
			    	
			    	//第一行第10列  
			    	row.createCell(9).setCellValue("本金");  
				    
				    //第一行第11列  
			    	row.createCell(10).setCellValue("利息"); 
				    
				    //第一行第12列
			    	row.createCell(11).setCellValue("客户服务费"); 
			    	
			    	//第一行第13列
			    	row.createCell(12).setCellValue("账户管理费"); 
				    
				    //第一行第14列  
			    	row.createCell(13).setCellValue("提前还款手续费"); 
				    
				    //第一行第15列  
			    	row.createCell(14).setCellValue("滞纳金"); 
				    
				    //第一行第16列  
			    	row.createCell(15).setCellValue("印花税"); 
				    
				    //第一行第17列
			    	row.createCell(16).setCellValue("增值服务费"); 
				    
				    //第一行第18列  
			    	row.createCell(17).setCellValue("委外催收费"); 
				    
				    //第一行第19列  
			    	row.createCell(18).setCellValue("随心还服务费"); 
			    	
				    //第一行第20列  
			    	row.createCell(19).setCellValue("提前委外费");
				    
				    //第一行第21列  
			    	row.createCell(20).setCellValue("佰保袋服务费");
			    	
			    	//第一行第22列  
			    	row.createCell(21).setCellValue("总额"); 
			    	
			    	//第一行第23列  
			    	row.createCell(22).setCellValue("数据源"); 
			    	
			    	continue;
				}
		        row.createCell(0).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("checknumber")));
			    row.createCell(1).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("checkdate")));  
			    row.createCell(2).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("serialno")));  
			    row.createCell(3).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("registrationdate")));  
			    row.createCell(4).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("subproducttype")));  
			    row.createCell(5).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("paytype")));  
			    row.createCell(6).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("dcdate")));  
			    row.createCell(7).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("shdate")));  
			    row.createCell(8).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("assetbelong")));  
			    row.createCell(9).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("payprincipalamt")));  
			    row.createCell(10).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("payinteamt")));  
			    row.createCell(11).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("a2")));
			    row.createCell(12).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("a7")));
			    row.createCell(13).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("a9")));
			    row.createCell(14).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("a10")));
			    row.createCell(15).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("a11")));
			    row.createCell(16).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("a12")));
			    row.createCell(17).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("a17")));
			    row.createCell(18).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("a18")));
			    row.createCell(19).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("a19")));
			    row.createCell(20).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("a22")));
			    row.createCell(21).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("payamt")));
			    row.createCell(22).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("datasource")));
			    
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
	
}
