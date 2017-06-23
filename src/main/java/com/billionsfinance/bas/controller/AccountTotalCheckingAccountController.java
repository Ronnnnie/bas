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
import com.billionsfinance.bas.server.IAccountTotalCheckingAccountServer;
import com.billionsfinance.bas.server.impl.AccountTotalCheckingAccountServer;
import com.billionsfinance.bas.util.AjaxJson;
import com.billionsfinance.bas.util.ResponseUtil;
import com.billionsfinance.bas.util.StringUtil;

/**
 * 
 * @ClassName: AccountTotalCheckingAccountController.java
 * @Description: 总账对账控制器
 * @author Feima.zhou
 * 
 *         Copyright: Copyright (c) 2017年5月12日下午3:50:11 Company:佰仟金融
 */
@Controller
@RequestMapping("/accountTotalCheckingAccountServer")
public class AccountTotalCheckingAccountController {

	/** 日志记录 */
	private static final Log LOGGER = LogFactory.getLog(AccountTotalCheckingAccountController.class);

	private static final IAccountTotalCheckingAccountServer accountTotalCheckingAccountServer = new AccountTotalCheckingAccountServer();

	/**
	 * @methodName: toAccountTotalCheckingAccount
	 * @Description: 跳转总账对账页面
	 * @return folderName/Jsp FileName
	 */
	@RequestMapping("/toAccountTotalCheckingAccount")
	public String toAccountTotalCheckingAccount() {
		return "accountTotalCheckingAccount/accountTotalCheckingAccount";
	}

	/**
	 * 总账对账合同查询.
	 * @param DayTradingDetailVO
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 */
	@RequestMapping("/queryAccountTotalCheckingAccount")
	public void queryAccountTotalCheckingAccount(Integer page, Integer rows, AccountTotalCheckingAccountVO vo,HttpServletRequest request, HttpServletResponse response) {
		PageVO pageVO = null;
		try {
			pageVO = new PageVO();
			pageVO.setPageSize(rows);
			pageVO.setPageNo(page);
			pageVO = accountTotalCheckingAccountServer.queryAccountTotalCheckingAccount(pageVO, vo);
		} catch (Exception e) {
			LOGGER.error("查询总账对账明细失败!", e);
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
	@RequestMapping("queryAccountTotalCheckingAccountTotal")
	public void queryAccountTotalCheckingAccount(AccountTotalCheckingAccountVO vo,HttpServletRequest request, HttpServletResponse response){
		//统计合同总数
		Map<String,Object> map = accountTotalCheckingAccountServer.queryAccountTotalCheckingAccountTotal(vo);
		ResponseUtil.sendJSON(response, map);
	}
	
	/**
	 * 
	 * @Description 对账总张表导出
	 * @throws ServletException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/exportExcel")
	public AjaxJson exportExcel(HttpServletRequest request, HttpServletResponse response,AccountTotalCheckingAccountVO vo) throws ServletException, IOException{
		AjaxJson ajaxJson = null;
		try {
			ajaxJson = new AjaxJson();
			String fileName = "导出Excel-总账对账明细表.xlsx";  
			List<Map<String,Object>> list = accountTotalCheckingAccountServer.queryAccountTotalCheckingAccountFindAll(vo);
			
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
			    	row.createCell(2).setCellValue("当日正常代扣回盘总金额");  
				
				    //第一行第4列  
			    	row.createCell(3).setCellValue("当日提前还款代扣回盘总金额");  
				
				    //第一行第5列  
			    	row.createCell(4).setCellValue("当日临时代扣回盘总金额");
			    	
			    	//第一行第6列  
			    	row.createCell(5).setCellValue("当日临时代扣回盘挂账总金额");  
				    
				    //第一行第7列  
			    	row.createCell(6).setCellValue("当日主动存款总金额");  
				    
				    //第一行第8列  
			    	row.createCell(7).setCellValue("当日推送金额"); 
				    
				    //第一行第9列  
			    	row.createCell(8).setCellValue("当日主动存款挂账总金额");  
			    	
			    	//第一行第10列  
			    	row.createCell(9).setCellValue("T－1日预存款余额");  
				    
				    //第一行第11列  
			    	row.createCell(10).setCellValue("当日预存款余额"); 
				    
				    //第一行第12列
			    	row.createCell(11).setCellValue("数据源"); 
			    	continue;
				}
		        row.createCell(0).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("checknumber")));
			    row.createCell(1).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("checkdate")));  
			    row.createCell(2).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("normal_withhold_back")));  
			    row.createCell(3).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("pre_withhold_back")));  
			    row.createCell(4).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("temp_withhold_back")));  
			    row.createCell(5).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("temp_withhold_back_hang")));  
			    row.createCell(6).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("active_deposit")));  
			    row.createCell(7).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("push_total")));  
			    row.createCell(8).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("active_deposit_hang")));  
			    row.createCell(9).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("tm1_deposit_balance")));  
			    row.createCell(10).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("today_deposit_balance")));  
			    row.createCell(11).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("datasource")));
			    
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
