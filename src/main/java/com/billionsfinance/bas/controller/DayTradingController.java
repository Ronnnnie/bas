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

import com.billionsfinance.bas.entity.DayTradingVO;
import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.entity.XZXDPaymentsVO;
import com.billionsfinance.bas.server.IDayTradingServer;
import com.billionsfinance.bas.server.impl.DayTradingServer;
import com.billionsfinance.bas.util.AjaxJson;
import com.billionsfinance.bas.util.ResponseUtil;
import com.billionsfinance.bas.util.StringUtil;

/**
 * @ClassName: 还款明细Controller(信托回款明细)
 * @Description: RepaymentDetailController
 * @author zhouFM
 * @Copyright Copyright (c) 2016 2016年11月18日 上午15:05:04 Company:佰仟金融
 */
@Controller
@RequestMapping("/dayTradingServer")
public class DayTradingController {

	/** 日志记录 */
	private static final Log LOGGER = LogFactory.getLog(DayTradingController.class);

	private static final IDayTradingServer dayTradingServer = new DayTradingServer();

	/**
	 * @methodName: toReceivedPaymentsDetail
	 * @Description: trustAllotDetail
	 * @return java.lang.String
	 */
	@RequestMapping("/toDayTradingDetail")
	public String toDayTradingDetail() {
		return "dayTrading/dayTrading";
	}

	/**
	 * 明细合同查询.
	 * @param DayTradingDetailVO
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 */
	@RequestMapping("/queryDayTradingDetail")
	public void queryDayTradingDetail(Integer page, Integer rows, DayTradingVO vo,HttpServletRequest request, HttpServletResponse response) {
		PageVO pageVO = null;
		try {
			pageVO = new PageVO();
			pageVO.setPageSize(rows);
			pageVO.setPageNo(page);
			pageVO = dayTradingServer.queryDayTradingDetail(pageVO, vo);
		} catch (Exception e) {
			LOGGER.error("查询还款差额划拨-还款明细-核算失败!", e);
		}finally {
			ResponseUtil.sendJSON(response, pageVO);
		}
	}
	
	/**
	 * 
	 * @Description  日交易数据合同导出
	 * @throws ServletException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/excelExport")
	public AjaxJson excelExport(HttpServletRequest request, HttpServletResponse response,DayTradingVO vo) throws ServletException, IOException{
		AjaxJson ajaxJson = null;
		try {
			
			ajaxJson = new AjaxJson();
			String fileName = "导出Excel-日交易数据.xlsx";  
			List<Map<String,Object>> list = dayTradingServer.queryDayTradingDetailFindAll(vo);
			
			SXSSFWorkbook wb = new SXSSFWorkbook(1024); // 这里100是在内存中的数量，如果大于此数量时，会写到硬盘，以避免在内存导致内存溢出  
			Sheet sh = wb.createSheet();
			if (list.size()==0) {
				return ajaxJson;
			}
			for (int i = 0; i < list.size()+1; i++) {  
				///sh.setColumnWidth(i,4000);
			    Row row = sh.createRow(i);
			    if (i==0) {
			    	row.createCell(0).setCellValue("交易日期");  
				    
			    	row.createCell(1).setCellValue("交易类型");  
				
			    	row.createCell(2).setCellValue("交易流水");  
				
			    	row.createCell(3).setCellValue("交易主体");  
				
			    	row.createCell(4).setCellValue("数据源");  
				    
			    	row.createCell(5).setCellValue("本金收入");  
				    
			    	row.createCell(6).setCellValue("利息收入");  
				    
			    	row.createCell(7).setCellValue("应付本金"); 
				    
			    	row.createCell(8).setCellValue("应收本金");
				    
			    	row.createCell(9).setCellValue("应付利息");
			    	
			    	row.createCell(10).setCellValue("应收利息"); 
			    	
			    	row.createCell(11).setCellValue("应付纯溢价"); 
				    
			    	row.createCell(12).setCellValue("应收纯溢价"); 
				    
			    	row.createCell(13).setCellValue("客户服务费"); 
				    
			    	row.createCell(14).setCellValue("账户管理费"); 
				    
			    	row.createCell(15).setCellValue("提前还款手续费"); 
				   
			    	row.createCell(16).setCellValue("滞纳金"); 
				    
			    	row.createCell(17).setCellValue("印花税"); 
				    
			    	row.createCell(18).setCellValue("增值服务费"); 
				    
			    	row.createCell(19).setCellValue("委外催收费"); 
				    
			    	row.createCell(20).setCellValue("随心还服务费"); 
				    
			    	row.createCell(21).setCellValue("提前委外费"); 
				    
			    	row.createCell(22).setCellValue("佰保袋服务费");
			    	
			    	row.createCell(23).setCellValue("是否核对");
			    	
			    	row.createCell(24).setCellValue("创建时间");
				}else{
				    row.createCell(0).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("transDate")));
				    row.createCell(1).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("transType")));  
				    row.createCell(2).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("transCode")));  
				    row.createCell(3).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("belong")));  
				    row.createCell(4).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("dataSource")));  
				    row.createCell(5).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("principalamt")));  
				    row.createCell(6).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("inteamt")));  
				    row.createCell(7).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("payPrincipalamt")));  
				    row.createCell(8).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("receivePrincipalamt")));  
				    row.createCell(9).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("payInteamt")));  
				    row.createCell(10).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("receiveInteamt")));
				    row.createCell(11).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("payPureoverflowsum")));
				    row.createCell(12).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("pureoverflowsum")));  
				    row.createCell(13).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("a2")));  
				    row.createCell(14).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("a7")));  
				    row.createCell(15).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("a9")));  
				    row.createCell(16).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("a10")));  
				    row.createCell(17).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("a11")));  
				    row.createCell(18).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("a12")));  
				    row.createCell(19).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("a17")));  
				    row.createCell(20).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("a18")));  
				    row.createCell(21).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("a19")));  
				    row.createCell(22).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("a22")));  
				    row.createCell(23).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("isCheck")));  
				    row.createCell(24).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("createTime")));  
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
		} catch (Exception e) {
			LOGGER.error("数据导出错误!", e);
		}
        return ajaxJson;
	}
	
	/**
	 * 匹配合同数统计
	 * @param vo
	 * @param request
	 * @param response
	 */
	@RequestMapping("queryDayTradingDetailCount")
	public void queryDayTradingDetailCount(DayTradingVO vo,HttpServletRequest request, HttpServletResponse response){
		//统计合同总数
		Map<String,Object> map = dayTradingServer.queryDayTradingDetailCount(vo);
		ResponseUtil.sendJSON(response, map);
	}
	
}
