package com.billionsfinance.bas.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.billionsfinance.bas.entity.BankWithholdVO;
import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.server.IBankWithholdServer;
import com.billionsfinance.bas.server.impl.BankWithholdServer;
import com.billionsfinance.bas.util.AjaxJson;
import com.billionsfinance.bas.util.ResponseUtil;
import com.billionsfinance.bas.util.StringUtil;

/**
 * 
 * @ClassName: BankWithholdController.java
 * @Description: 每日银行代扣对账表控制器
 * @author Feima.zhou
 * 
 *         Copyright: Copyright (c) 2017年5月8日下午5:40:48 Company:佰仟金融
 */
@Controller
@RequestMapping("/bankWithholdServer")
public class BankWithholdController {

	/** 日志记录 */
	private static final Log LOGGER = LogFactory.getLog(BankWithholdController.class);

	private static final IBankWithholdServer bankWithholdServer = new BankWithholdServer();

	/**
	 * @methodName: toBankWithhold
	 * @Description: trustAllotDetail
	 * @return java.lang.String
	 */
	@RequestMapping("/toBankWithhold")
	public String toBankWithhold() {
		return "bankWithhold/bankWithhold";
	}

	/**
	 * 查询每日银行代扣核对表.
	 * @param BankWithholdVO
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 */
	@RequestMapping("/queryBankWithhold")
	public void queryBankWithhold(Integer page, Integer rows, BankWithholdVO vo,HttpServletRequest request, HttpServletResponse response) {
		PageVO pageVO = null;
		try {
			pageVO = new PageVO();
			pageVO.setPageSize(rows);
			pageVO.setPageNo(page);
			pageVO = bankWithholdServer.queryBankWithhold(pageVO, vo);
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
	@RequestMapping("queryBankWithholdCount")
	public void queryBankWithholdCount(BankWithholdVO vo,HttpServletRequest request, HttpServletResponse response){
		//统计合同总数
		Map<String,Object> map = bankWithholdServer.queryBankWithholdCount(vo);
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
	public AjaxJson exportExcel(HttpServletRequest request, HttpServletResponse response,BankWithholdVO vo) throws ServletException, IOException{
		AjaxJson ajaxJson = null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");  
		try {
			ajaxJson = new AjaxJson();
			String fileName = "导出Excel-每日银行代扣对账表.xlsx";  
			List<Map<String,Object>> list = bankWithholdServer.queryBankWithholdFindAll(vo);
			
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
			    	row.createCell(0).setCellValue("业务日期");  
				    
				    //第一行第2列  
			    	row.createCell(1).setCellValue("日初预存款");  
				
				    //第一行第3列  
			    	row.createCell(2).setCellValue("日初未匹配");  
				
				    //第一行第4列  
			    	row.createCell(3).setCellValue("银企直连网银");  
				
				    //第一行第5列  
			    	row.createCell(4).setCellValue("银企直连安硕");
			    	
			    	//第一行第6列  
			    	row.createCell(5).setCellValue("银企直连差异");  
				    
				    //第一行第7列  
			    	row.createCell(6).setCellValue("银企直连已匹配");  
				    
				    //第一行第8列  
			    	row.createCell(7).setCellValue("银企直连未匹配"); 
				    
				    //第一行第9列  
			    	row.createCell(8).setCellValue("手工分离");  
			    	
			    	//第一行第10列  
			    	row.createCell(9).setCellValue("手工匹配");  
				    
				    //第一行第11列  
			    	row.createCell(10).setCellValue("日终未匹配"); 
				    
				    //第一行第12列
			    	row.createCell(11).setCellValue("易办事网银"); 
			    	
			    	//第一行第13列
			    	row.createCell(12).setCellValue("易办事安硕1"); 
			    	
			    	//第一行第14列
			    	row.createCell(13).setCellValue("易办事安硕"); 
			    	
			    	//第一行第15列
			    	row.createCell(14).setCellValue("易办事差异"); 
			    	
			    	//第一行第16列
			    	row.createCell(15).setCellValue("快付通网银"); 
			    	
			    	//第一行第17列
			    	row.createCell(16).setCellValue("快付通安硕"); 
			    	
			    	//第一行第18列
			    	row.createCell(17).setCellValue("快付通差异"); 
			    	
			    	//第一行第19列
			    	row.createCell(18).setCellValue("哈行网银"); 
			    	
			    	//第一行第20列
			    	row.createCell(19).setCellValue("哈行安硕"); 
			    	
			    	//第一行第21列
			    	row.createCell(20).setCellValue("哈行差异"); 
			    	
			    	//第一行第22列
			    	row.createCell(21).setCellValue("快付通实时网银"); 
			    	
			    	//第一行第23列
			    	row.createCell(22).setCellValue("快付通实时安硕"); 
			    	
			    	//第一行第24列
			    	row.createCell(23).setCellValue("快付通实时差异"); 
			    	
			    	//第一行第25列
			    	row.createCell(24).setCellValue("微信支付网银"); 
			    	
			    	//第一行第26列
			    	row.createCell(25).setCellValue("微信支付安硕后移一天"); 
			    	
			    	//第一行第27列
			    	row.createCell(26).setCellValue("微信支付安硕"); 
			    	
			    	//第一行第28列
			    	row.createCell(27).setCellValue("微信支付手续费"); 
			    	
			    	//第一行第29列
			    	row.createCell(28).setCellValue("微信支付差异"); 
			    	
			    	//第一行第30列
			    	row.createCell(29).setCellValue("退款"); 
			    	
			    	//第一行第31列
			    	row.createCell(30).setCellValue("普通还款"); 
			    	
			    	//第一行第32列
			    	row.createCell(31).setCellValue("提前还款"); 
			    	
			    	//第一行第33列
			    	row.createCell(32).setCellValue("虚拟入账"); 
			    	
			    	//第一行第34列
			    	row.createCell(33).setCellValue("日终预存款"); 
			    	
			    	//第一行第35列
			    	row.createCell(34).setCellValue("未匹配差异"); 
			    	
			    	//第一行第36列
			    	row.createCell(35).setCellValue("预存款差异"); 
			    	
			    	//第一行第37列
			    	row.createCell(36).setCellValue("冲还款"); 
			    	continue;
				}
			    @SuppressWarnings("deprecation")
				Date inputdate = new Date(StringUtil.isNullOrEmpty(list.get(i-1).get("inputdate")));
			    row.createCell(0).setCellValue(sdf.format(inputdate));
			    row.createCell(1).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("dep_bod")));  
			    row.createCell(2).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("unmatch_bod")));  
			    row.createCell(3).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("bank_bank")));  
			    row.createCell(4).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("bank_core")));  
			    row.createCell(5).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("bank_dif")));  
			    row.createCell(6).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("bank_match")));  
			    row.createCell(7).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("bank_unmatch")));  
			    row.createCell(8).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("bank_roll_hand")));  
			    row.createCell(9).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("bank_match_hand")));  
			    row.createCell(10).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("unmatch_eod")));  
			    row.createCell(11).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("ebu_bank")));
			    row.createCell(12).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("ebu_core1")));
			    row.createCell(13).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("ebu_core")));
			    row.createCell(14).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("ebu_dif")));
			    row.createCell(15).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("kft_bank")));
			    row.createCell(16).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("kft_core")));
			    row.createCell(17).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("kft_dif")));
			    row.createCell(18).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("hbk_bank")));
			    row.createCell(19).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("hbk_core")));
			    row.createCell(20).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("hbk_dif")));
			    row.createCell(21).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("kfts_bank")));
			    row.createCell(22).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("kfts_core")));
			    row.createCell(23).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("kfts_dif")));
			    row.createCell(24).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("cft_bank")));
			    row.createCell(25).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("check_cft_core")));
			    row.createCell(26).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("cft_core")));
			    row.createCell(27).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("cft_core_fee")));
			    row.createCell(28).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("cft_dif")));
			    row.createCell(29).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("refund")));
			    row.createCell(30).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("pay")));
			    row.createCell(31).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("prepay")));
			    row.createCell(32).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("va_amt")));
			    row.createCell(33).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("dep_eod")));
			    row.createCell(34).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("unmatch_dif")));
			    row.createCell(35).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("dep_dif")));
			    row.createCell(36).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("loan_rep")));
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
