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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.entity.ReceivedPaymentsVO;
import com.billionsfinance.bas.server.IReceivedPaymentsServer;
import com.billionsfinance.bas.server.impl.ReceivedPaymentsServer;
import com.billionsfinance.bas.util.AjaxJson;
import com.billionsfinance.bas.util.ResponseUtil;
import com.billionsfinance.bas.util.StringUtil;

/**
 * @ClassName: ReceivedPaymentsController
 * @Description: 合同回款管理控制器(合同实还明细,汇总表)
 * @author FMZhou
 * 
 * Copyright: Copyright (c) 2016 2016年11月2日 下午14:25:09 Company:佰仟金融
 */
@Controller
@RequestMapping("/receivedPaymentsServer")
public class ReceivedPaymentsDetailController {

	/** 日志记录 */
	private static final Log LOGGER = LogFactory.getLog(ReceivedPaymentsDetailController.class);

	private static final IReceivedPaymentsServer receivedPaymentsServer = new ReceivedPaymentsServer();

	/**
	 * Description: 跳转回款明细
	 */
	@RequestMapping("/toReceivedPaymentsDetail")
	public String toReceivedPaymentsDetail() {
		return "receivedPayments/receivedPaymentsDetail";	
	}
	/**
	 * @Description: 跳转回款汇总页面
	 */
	@RequestMapping("/toReceivedPaymentsGather")
	public String toReceivedPaymentsGather() {
		return "receivedPayments/receivedPaymentsGather";
	}
	
	/**
	 * @Description：查询回款明细(分页)
	 */
	@RequestMapping("/queryReceivedPaymentsDetail")
	public void queryReceivedPaymentsDetail(Integer page, Integer rows, ReceivedPaymentsVO vo,HttpServletRequest request, HttpServletResponse response) {
		PageVO pageVO = null;
		try {
			pageVO = new PageVO();
			pageVO.setPageSize(rows);
			pageVO.setPageNo(page);
			pageVO = receivedPaymentsServer.queryReceivedPaymentsDetail(pageVO, vo);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("查询回款表明细失败!", e);
		}finally {
			ResponseUtil.sendJSON(response, pageVO);
		}
	}
	
	/**
	 * @Description：查询回款汇总(分页)
	 */
	@RequestMapping("/queryReceivedPaymentsGather")
	public void queryReceivedPaymentsGather(Integer page, Integer rows, ReceivedPaymentsVO vo,HttpServletRequest request, HttpServletResponse response) {
		PageVO pageVO = null;
		try {
			pageVO = new PageVO();
			pageVO.setPageSize(rows);
			pageVO.setPageNo(page);
			pageVO = receivedPaymentsServer.queryReceivedPaymentsGather(pageVO, vo);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("查询回款汇总合同失败!", e);
		}
		ResponseUtil.sendJSON(response, pageVO);
	}
	
	/**
	 * 放款表帐务标记
	 * @param vo
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/accountingMark")
	public AjaxJson accountingMark(ReceivedPaymentsVO vo,HttpServletRequest request, HttpServletResponse response) {
		AjaxJson ajaxResponse = new AjaxJson();
		Double actualPayPrincipalAmtSum = new Double(0);
		Double actualPayinteAmtSum = new Double(0);
		Double a2Sum = new Double(0);
		Double a7Sum = new Double(0);
		Double a9Sum = new Double(0);
		Double a10Sum = new Double(0);
		Double a11Sum = new Double(0);
		Double a12Sum = new Double(0);
		Double a17Sum = new Double(0);
		Double a18Sum = new Double(0);
		Double a19Sum = new Double(0);
		try {
			int result = receivedPaymentsServer.accountingMark(vo);
			vo.setStartKeepAccountsDate(vo.getUpdateDate());
			vo.setEndKeepAccountsDate(vo.getUpdateDate());
			//查询匹配合同
			List<Map<String,Object>> list = receivedPaymentsServer.queryReceivedPaymentsId(vo);
			if (result == 0) {
				ajaxResponse.setMsg("匹配数据为空，标记失败!");
				ajaxResponse.setObj(null);
				return ajaxResponse;
			}else{
				double moneyCount = 0;
				for (int i = 0; i < list.size(); i++) {
					moneyCount+=Double.parseDouble(StringUtil.checkMoneyIsNull(list.get(i).get("actualPayPrincipalAmt")));
					actualPayPrincipalAmtSum+=Double.parseDouble(StringUtil.checkMoneyIsNull(list.get(i).get("actualPayPrincipalAmt")));
					actualPayinteAmtSum+=Double.parseDouble(StringUtil.checkMoneyIsNull(list.get(i).get("actualPayinteAmt")));
					a2Sum+=Double.parseDouble(StringUtil.checkMoneyIsNull(list.get(i).get("a2")));
					a7Sum+=Double.parseDouble(StringUtil.checkMoneyIsNull(list.get(i).get("a7")));
					a9Sum+=Double.parseDouble(StringUtil.checkMoneyIsNull(list.get(i).get("a9")));
					a10Sum+=Double.parseDouble(StringUtil.checkMoneyIsNull(list.get(i).get("a10")));
					a11Sum+=Double.parseDouble(StringUtil.checkMoneyIsNull(list.get(i).get("a11")));
					a12Sum+=Double.parseDouble(StringUtil.checkMoneyIsNull(list.get(i).get("a12")));
					a17Sum+=Double.parseDouble(StringUtil.checkMoneyIsNull(list.get(i).get("a17")));
					a18Sum+=Double.parseDouble(StringUtil.checkMoneyIsNull(list.get(i).get("a18")));
					a19Sum+=Double.parseDouble(StringUtil.checkMoneyIsNull(list.get(i).get("a19")));
				}
				list.get(0).put("moneyCount", moneyCount);
				list.get(0).put("contractCount", list.size());
				list.get(0).put("actualPayPrincipalAmtSum", actualPayPrincipalAmtSum);
				list.get(0).put("actualPayinteAmtSum", actualPayinteAmtSum);
				list.get(0).put("a2Sum", a2Sum);
				list.get(0).put("a7Sum", a7Sum);
				list.get(0).put("a9Sum", a9Sum);
				list.get(0).put("a10Sum", a10Sum);
				list.get(0).put("a11Sum", a11Sum);
				list.get(0).put("a12Sum", a12Sum);
				list.get(0).put("a17Sum", a17Sum);
				list.get(0).put("a18Sum", a18Sum);
				list.get(0).put("a19Sum", a19Sum);
				ajaxResponse.setSuccess(true);
				ajaxResponse.setMsg("记账成功!");
				ajaxResponse.setObj(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("合同记账失败!", e);
		}
		return ajaxResponse;
	}
	
	/**
	 * 放款表帐务标记
	 * @param vo
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/selectAccountingMark")
	public AjaxJson selectAccountingMark(ReceivedPaymentsVO vo,HttpServletRequest request, HttpServletResponse response) {
		Double actualPayPrincipalAmtSum = new Double(0);
		Double actualPayinteAmtSum = new Double(0);
		Double a2Sum = new Double(0);
		Double a7Sum = new Double(0);
		Double a9Sum = new Double(0);
		Double a10Sum = new Double(0);
		Double a11Sum = new Double(0);
		Double a12Sum = new Double(0);
		Double a17Sum = new Double(0);
		Double a18Sum = new Double(0);
		Double a19Sum = new Double(0);
		AjaxJson ajaxResponse = new AjaxJson();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
			vo = changeVO(vo);
			if(vo.getSerialNoArray()!=null&&vo.getSerialNoArray().length>0){
				for (int i = 0; i < vo.getSerialNoArray().length; i++) {
					vo.setSerialNo(vo.getSerialNoArray()[i]);
					vo.setSeqId(vo.getSeqIdArray()[i]);
					vo.setAssetBelong(vo.getAssetBelongArray()[i]);
					vo.setActualPayinteAmt(vo.getActualPayinteAmtArray()[i]);
					vo.setActualPayPrincipalAmt(vo.getActualPayPrincipalAmtArray()[i]);
					receivedPaymentsServer.accountingMark(vo);
					list.add(receivedPaymentsServer.queryReceivedPaymentsId(vo).get(0));
				}
			}else{
				receivedPaymentsServer.accountingMark(vo);
				list=receivedPaymentsServer.queryReceivedPaymentsId(vo);
			}
			if(list.size()==0){
				ajaxResponse.setMsg("匹配数据为空，标记失败!");
				ajaxResponse.setObj(null);
				return ajaxResponse;
			}else{
				double moneyCount = 0;
				for (int i = 0; i < list.size(); i++) {
					moneyCount+=Double.parseDouble(StringUtil.checkMoneyIsNull(list.get(i).get("actualPayPrincipalAmt")));
					actualPayPrincipalAmtSum+=Double.parseDouble(StringUtil.checkMoneyIsNull(list.get(i).get("actualPayPrincipalAmt")));
					actualPayinteAmtSum+=Double.parseDouble(StringUtil.checkMoneyIsNull(list.get(i).get("actualPayinteAmt")));
					a2Sum+=Double.parseDouble(StringUtil.checkMoneyIsNull(list.get(i).get("a2")));
					a7Sum+=Double.parseDouble(StringUtil.checkMoneyIsNull(list.get(i).get("a7")));
					a9Sum+=Double.parseDouble(StringUtil.checkMoneyIsNull(list.get(i).get("a9")));
					a10Sum+=Double.parseDouble(StringUtil.checkMoneyIsNull(list.get(i).get("a10")));
					a11Sum+=Double.parseDouble(StringUtil.checkMoneyIsNull(list.get(i).get("a11")));
					a12Sum+=Double.parseDouble(StringUtil.checkMoneyIsNull(list.get(i).get("a12")));
					a17Sum+=Double.parseDouble(StringUtil.checkMoneyIsNull(list.get(i).get("a17")));
					a18Sum+=Double.parseDouble(StringUtil.checkMoneyIsNull(list.get(i).get("a18")));
					a19Sum+=Double.parseDouble(StringUtil.checkMoneyIsNull(list.get(i).get("a19")));
				}
				list.get(0).put("moneyCount", moneyCount);
				list.get(0).put("contractCount", list.size());
				list.get(0).put("actualPayPrincipalAmtSum", actualPayPrincipalAmtSum);
				list.get(0).put("actualPayinteAmtSum", actualPayinteAmtSum);
				list.get(0).put("a2Sum", a2Sum);
				list.get(0).put("a7Sum", a7Sum);
				list.get(0).put("a9Sum", a9Sum);
				list.get(0).put("a10Sum", a10Sum);
				list.get(0).put("a11Sum", a11Sum);
				list.get(0).put("a12Sum", a12Sum);
				list.get(0).put("a17Sum", a17Sum);
				list.get(0).put("a18Sum", a18Sum);
				list.get(0).put("a19Sum", a19Sum);
				ajaxResponse.setSuccess(true);
				ajaxResponse.setMsg("记账成功!");
				ajaxResponse.setObj(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("合同记账失败!", e);
		}
		return ajaxResponse;
	}
	
	/**
	 * 放款表记账撤销
	 * @param vo
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/cancelAccountingMark")
	public AjaxJson cancelAccountingMark(ReceivedPaymentsVO vo,HttpServletRequest request, HttpServletResponse response) {
		Boolean roleFlag = (Boolean) request.getSession().getAttribute("isRevokeRole");
		if(roleFlag==null||!roleFlag){
			ResponseUtil.sendMessage(response, false, "暂无权限操作此功能!");
			return null;
		}
		Double actualPayPrincipalAmtSum = new Double(0);
		Double actualPayinteAmtSum = new Double(0);
		Double a2Sum = new Double(0);
		Double a7Sum = new Double(0);
		Double a9Sum = new Double(0);
		Double a10Sum = new Double(0);
		Double a11Sum = new Double(0);
		Double a12Sum = new Double(0);
		Double a17Sum = new Double(0);
		Double a18Sum = new Double(0);
		Double a19Sum = new Double(0);
		
		AjaxJson ajaxResponse = new AjaxJson();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
			vo = changeVO(vo);
			if(vo.getSerialNoArray()!=null&&vo.getSerialNoArray().length>0){
				for (int i = 0; i < vo.getSerialNoArray().length; i++) {
					vo.setSerialNo(vo.getSerialNoArray()[i]);
					vo.setSeqId(vo.getSeqIdArray()[i]);
					vo.setAssetBelong(vo.getAssetBelongArray()[i]);
					vo.setActualPayinteAmt(vo.getActualPayinteAmtArray()[i]);
					vo.setActualPayPrincipalAmt(vo.getActualPayPrincipalAmtArray()[i]);
					list.add(receivedPaymentsServer.queryReceivedPaymentsId(vo).get(0));
					receivedPaymentsServer.cancelAccountingMark(vo);
				}
			}else{
				list=receivedPaymentsServer.queryReceivedPaymentsId(vo);
				receivedPaymentsServer.cancelAccountingMark(vo);
			}
			if (list.size() == 0) {
				ajaxResponse.setMsg("匹配数据为空，记账撤销失败!");
				ajaxResponse.setObj(null);
				return ajaxResponse;
			}else{
				boolean flag = true;
				double moneyCount = 0;
				for (int i = 0; i < list.size(); i++) {
					String keepAccountsStatus = StringUtil.isNullOrEmpty(list.get(i).get("keepAccountsStatus"));
					if(!("1").equals(keepAccountsStatus)){
						flag = false;
					}else{
						list.get(i).put("keepAccountsDate", "");
						list.get(i).put("keepAccountsStatus", 2);
					}
					moneyCount+=Double.parseDouble(StringUtil.checkMoneyIsNull(list.get(i).get("actualPayPrincipalAmt")));
					actualPayPrincipalAmtSum+=Double.parseDouble(StringUtil.checkMoneyIsNull(list.get(i).get("actualPayPrincipalAmt")));
					actualPayinteAmtSum+=Double.parseDouble(StringUtil.checkMoneyIsNull(list.get(i).get("actualPayinteAmt")));
					a2Sum+=Double.parseDouble(StringUtil.checkMoneyIsNull(list.get(i).get("a2")));
					a7Sum+=Double.parseDouble(StringUtil.checkMoneyIsNull(list.get(i).get("a7")));
					a9Sum+=Double.parseDouble(StringUtil.checkMoneyIsNull(list.get(i).get("a9")));
					a10Sum+=Double.parseDouble(StringUtil.checkMoneyIsNull(list.get(i).get("a10")));
					a11Sum+=Double.parseDouble(StringUtil.checkMoneyIsNull(list.get(i).get("a11")));
					a12Sum+=Double.parseDouble(StringUtil.checkMoneyIsNull(list.get(i).get("a12")));
					a17Sum+=Double.parseDouble(StringUtil.checkMoneyIsNull(list.get(i).get("a17")));
					a18Sum+=Double.parseDouble(StringUtil.checkMoneyIsNull(list.get(i).get("a18")));
					a19Sum+=Double.parseDouble(StringUtil.checkMoneyIsNull(list.get(i).get("a19")));
				}
				if(flag){
					ajaxResponse.setMsg("记账撤销成功!");
				}else{
					ajaxResponse.setMsg("当前存在未记账/已撤销的合同!该些合同撤销失败");
				}
				list.get(0).put("contractCount", list.size());
				list.get(0).put("moneyCount", moneyCount);
				list.get(0).put("actualPayPrincipalAmtSum", actualPayPrincipalAmtSum);
				list.get(0).put("actualPayinteAmtSum", actualPayinteAmtSum);
				list.get(0).put("a2Sum", a2Sum);
				list.get(0).put("a7Sum", a7Sum);
				list.get(0).put("a9Sum", a9Sum);
				list.get(0).put("a10Sum", a10Sum);
				list.get(0).put("a11Sum", a11Sum);
				list.get(0).put("a12Sum", a12Sum);
				list.get(0).put("a17Sum", a17Sum);
				list.get(0).put("a18Sum", a18Sum);
				list.get(0).put("a19Sum", a19Sum);
				ajaxResponse.setSuccess(true);
				ajaxResponse.setObj(list);
				
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("记账撤销失败!", e);
		}
		return ajaxResponse;
	}
	
	public ReceivedPaymentsVO changeVO(ReceivedPaymentsVO vo){
		if(StringUtil.isNullOrEmpty(vo.getSeqId()).contains(",")){
			vo.setSeqIdArray(vo.getSeqId().split(","));
			vo.setSeqId("");
		}
		if(StringUtil.isNullOrEmpty(vo.getSerialNo()).contains(",")){
			vo.setSerialNoArray(vo.getSerialNo().split(","));
			vo.setSerialNo("");
		}
		if(StringUtil.isNullOrEmpty(vo.getAssetBelong()).contains(",")){
			vo.setAssetBelongArray(vo.getAssetBelong().split(","));
			vo.setAssetBelong("");
		}
		if(StringUtil.isNullOrEmpty(vo.getActualPayPrincipalAmt()).contains(",")){
			vo.setActualPayPrincipalAmtArray(vo.getActualPayPrincipalAmt().split(","));
			vo.setActualPayPrincipalAmt("");
		}
		if(StringUtil.isNullOrEmpty(vo.getActualPayinteAmt()).contains(",")){
			vo.setActualPayinteAmtArray(vo.getActualPayinteAmt().split(","));
			vo.setActualPayinteAmt("");
		}
		return vo;
	}
	
	@RequestMapping("queryContractStatus")
	public void queryContractStatus(ReceivedPaymentsVO vo,HttpServletRequest request, HttpServletResponse response){
		//统计合同总数
		try {
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			vo = changeVO(vo);
			//修改合同查询
			if(vo.getSerialNoArray()!=null&&vo.getSerialNoArray().length>0){
				for (int i = 0; i < vo.getSerialNoArray().length; i++) {
					vo.setSerialNo(vo.getSerialNoArray()[i]);
					vo.setSeqId(vo.getSeqIdArray()[i]);
					vo.setAssetBelong(vo.getAssetBelongArray()[i]);
					vo.setActualPayinteAmt(vo.getActualPayinteAmtArray()[i]);
					vo.setActualPayPrincipalAmt(vo.getActualPayPrincipalAmtArray()[i]);
					list.add(receivedPaymentsServer.queryReceivedPaymentsId(vo).get(0));
				}
			}else{
				list=receivedPaymentsServer.queryReceivedPaymentsId(vo);
			}
			for(Map<String,Object> map:list){
				String keepAccountsStatus = StringUtil.isNullOrEmpty(map.get("keepAccountsStatus"));
				if(keepAccountsStatus.equals("1")){
					ResponseUtil.sendString(response,keepAccountsStatus);
					return;
				}
			}
			ResponseUtil.sendString(response,"");
		} catch (Exception e) {
			LOGGER.error("查询匹配合同状态异常!", e);
		}
	}
	
	/**
	 * 
	 * @Description 逾期明细合同导出
	 * @throws ServletException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/detailDateExcelExport")
	public AjaxJson detailDateExcelExport(HttpServletRequest request, HttpServletResponse response,ReceivedPaymentsVO vo) throws ServletException, IOException{
		AjaxJson ajaxJson = null;
		try {
			ajaxJson = new AjaxJson();
			String fileName = "导出Excel-合同实还明细.xlsx";  
			List<Map<String,Object>> list=receivedPaymentsServer.receivedPaymentsExport(vo);
			
			SXSSFWorkbook wb = new SXSSFWorkbook(1000); // 这里100是在内存中的数量，如果大于此数量时，会写到硬盘，以避免在内存导致内存溢出  
			Sheet sh = wb.createSheet();  
			if (list.size()==0) {
				ajaxJson.setMsg("暂无数据可导出!");
				return ajaxJson;
			}
			for (int i = 0; i < list.size()+1; i++) { 
			    Row row = sh.createRow(i);
			    if (i==0) {
			    	row.createCell(0).setCellValue("记账日期");  
				    
				    //第一行第2列  
			    	row.createCell(1).setCellValue("统计日期");  
				
				    //第一行第3列  
			    	row.createCell(2).setCellValue("合同号");  
				
				    //第一行第4列  
			    	row.createCell(3).setCellValue("客户姓名");  
				
				    //第一行第5列  
			    	row.createCell(4).setCellValue("注册日期");  
				    
				    //第一行第6列  
			    	row.createCell(5).setCellValue("合同到期日");  
				    
				    //第一行第7列  
			    	row.createCell(6).setCellValue("门店代码"); 
				    
				    //第一行第8列  
			    	row.createCell(7).setCellValue("商户代码");  
				    
				    //第一行第9列  
			    	row.createCell(8).setCellValue("SA_ID"); 
				    
				    //第一行第10列
			    	row.createCell(9).setCellValue("商品范畴"); 
				    
				    //第一行第11列
			    	row.createCell(10).setCellValue("客户渠道"); 
				    
				    //第一行第12列  
			    	row.createCell(11).setCellValue("业务模式"); 
				    
				    //第一行第13列  
			    	row.createCell(12).setCellValue("贷款类型"); 
				    
				    //第一行第14列  
			    	row.createCell(13).setCellValue("产品子类型"); 
				    
				    //第一行第15列
			    	row.createCell(14).setCellValue("省份"); 
				    
				    //第一行第16列  
			    	row.createCell(15).setCellValue("城市"); 
				    
				    //第一行第17列  
			    	row.createCell(16).setCellValue("城市编码"); 
				    //第一行第18列  
			    	row.createCell(17).setCellValue("资金方");
				    
				    //第一行第19列  
			    	row.createCell(18).setCellValue("合同轨迹"); 
				    
				    //第一行第20列  
			    	row.createCell(19).setCellValue("强制日期"); 
				    
				    //第一行第21列  
			    	row.createCell(20).setCellValue("逾期天数"); 
				    
				    //第一行第22列  
			    	row.createCell(21).setCellValue("应还日期"); 
				    
				    //第一行第23列  
			    	row.createCell(22).setCellValue("实还日期"); 
				    
				    //第一行第24列  
			    	row.createCell(23).setCellValue("期数");
				    
				    //第一行第25列
			    	row.createCell(24).setCellValue("是否取消分期期次");
				    
				    //第一行第26列		    
				    row.createCell(25).setCellValue("转让日期"); 
			    	
			    	//第一行第27列
			    	row.createCell(26).setCellValue("代偿日期"); 
			    	
			    	//第一行第28列
			    	row.createCell(27).setCellValue("赎回日期"); 
			    	
			    	//第一行第29列
			    	row.createCell(28).setCellValue("保险投保日"); 
			    	
			    	//第一行第30列
			    	row.createCell(29).setCellValue("保险理赔日"); 
			    	
			    	//第一行第31列
			    	row.createCell(30).setCellValue("代垫方"); 
			    	
			    	row.createCell(31).setCellValue("资产所属方"); 
			    	
			    	row.createCell(32).setCellValue("保证方"); 
			    	
			    	row.createCell(33).setCellValue("本息核销日期"); 
			    	
			    	row.createCell(34).setCellValue("费用核销日期"); 
			    	
			    	row.createCell(35).setCellValue("实还本金"); 
			    	
			    	row.createCell(36).setCellValue("实还利息"); 
			    	
			    	row.createCell(37).setCellValue("实还客户服务费"); 
			    	
			    	row.createCell(38).setCellValue("实还账户管理费"); 
			    	
			    	row.createCell(39).setCellValue("实还提前还款手续费"); 
			    	
			    	row.createCell(40).setCellValue("实还滞纳金"); 
			    	
			    	row.createCell(41).setCellValue("实还印花税"); 
			    	
			    	row.createCell(42).setCellValue("实还增值服务费");
			    	
			    	row.createCell(43).setCellValue("实还委外催收费"); 
			    	
			    	row.createCell(44).setCellValue("实还随心还服务费"); 
			    	
			    	row.createCell(45).setCellValue("实还委外费"); 
			    	
			    	row.createCell(46).setCellValue("记账状态"); 
			    	continue;
				}
		        row.createCell(0).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("keepAccountsDate")));
			    row.createCell(1).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("accorddate")));  
			    row.createCell(2).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("serialNo")));  
			    row.createCell(3).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("clientname")));  
			    row.createCell(4).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("registrationDate")));  
			    row.createCell(5).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("maturitydate")));  
			    row.createCell(6).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("sno")));  
			    row.createCell(7).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("rno")));  
			    row.createCell(8).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("sa_id")));  
			    row.createCell(9).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("productcategory")));  
			    row.createCell(10).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("suretype")));  
			    row.createCell(11).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("businessmodel")));  
			    row.createCell(12).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("producttype")));  
			    row.createCell(13).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("subProductType")));  
			    row.createCell(14).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("province")));  
			    row.createCell(15).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("city")));  
			    row.createCell(16).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("citycode")));  
			    row.createCell(17).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("fundProviders")));  
			    row.createCell(18).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("contractlife")));  
			    row.createCell(19).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("cdate")));  
			    row.createCell(20).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("overduedays")));  
			    row.createCell(21).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("payDate")));  
			    row.createCell(22).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("actualPayDate")));  
			    row.createCell(23).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("seqId")));  
			    row.createCell(24).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("canceltype")));  
			    row.createCell(25).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("transferDate")));  
			    row.createCell(26).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("dcDate")));
			    row.createCell(27).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("shDate")));
			    row.createCell(28).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("tbdate")));
			    row.createCell(29).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("lpdate")));
			    row.createCell(30).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("debours")));
			    row.createCell(31).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("assetBelong")));
			    row.createCell(32).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("guaranteeParty")));
			    row.createCell(33).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("bxhxDate")));
			    row.createCell(34).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("fyhxDate")));
			    row.createCell(35).setCellValue(StringUtil.checkMoneyIsNull(list.get(i-1).get("actualPayPrincipalAmt")));
			    row.createCell(36).setCellValue(StringUtil.checkMoneyIsNull(list.get(i-1).get("actualPayinteAmt")));
			    row.createCell(37).setCellValue(StringUtil.checkMoneyIsNull(list.get(i-1).get("a2")));
			    row.createCell(38).setCellValue(StringUtil.checkMoneyIsNull(list.get(i-1).get("a7")));
			    row.createCell(39).setCellValue(StringUtil.checkMoneyIsNull(list.get(i-1).get("a9")));
			    row.createCell(40).setCellValue(StringUtil.checkMoneyIsNull(list.get(i-1).get("a10")));
			    row.createCell(41).setCellValue(StringUtil.checkMoneyIsNull(list.get(i-1).get("a11")));
			    row.createCell(42).setCellValue(StringUtil.checkMoneyIsNull(list.get(i-1).get("a12")));
			    row.createCell(43).setCellValue(StringUtil.checkMoneyIsNull(list.get(i-1).get("a17")));
			    row.createCell(44).setCellValue(StringUtil.checkMoneyIsNull(list.get(i-1).get("a18")));
			    row.createCell(45).setCellValue(StringUtil.checkMoneyIsNull(list.get(i-1).get("a19")));
			    row.createCell(46).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("keepAccountsStatus")));
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
	 * 
	 * @Description  汇总合同导出
	 * @throws ServletException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/gatherDateExcelExport")
	public AjaxJson gatherDateExcelExport(HttpServletRequest request, HttpServletResponse response,ReceivedPaymentsVO vo) throws ServletException, IOException{
		AjaxJson ajaxJson = null;
		try {
			vo.setStartKeepAccountsDate(StringUtil.stringFormatter(vo.getStartKeepAccountsDate()));
			vo.setEndKeepAccountsDate(StringUtil.stringFormatter(vo.getEndKeepAccountsDate()));
			vo.setBusinessModel(StringUtil.stringFormatter(vo.getBusinessModel()));
			vo.setSubProductType(StringUtil.stringFormatter(vo.getSubProductType()));
			vo.setCity(StringUtil.stringFormatter(vo.getCity()));
			vo.setAssetBelong(StringUtil.stringFormatter(vo.getAssetBelong()));
			vo.setStartShouldAlsoDate(StringUtil.stringFormatter(vo.getStartShouldAlsoDate()));
			vo.setEndShouldAlsoDate(StringUtil.stringFormatter(vo.getEndShouldAlsoDate()));
			vo.setGuaranteeParty(StringUtil.stringFormatter(vo.getGuaranteeParty()));
			vo.setCancelType(StringUtil.stringFormatter(vo.getCancelType()));
			ajaxJson = new AjaxJson();
			String fileName = "导出Excel-实还汇总.xlsx";  
			List<Map<String,Object>> list = receivedPaymentsServer.queryReceivedPaymentsGatherFindAll(vo);
			
			SXSSFWorkbook wb = new SXSSFWorkbook(1024); // 这里100是在内存中的数量，如果大于此数量时，会写到硬盘，以避免在内存导致内存溢出  
			Sheet sh = wb.createSheet();
			if (list.size()==0) {
				return ajaxJson;
			}
			for (int i = 0; i < list.size()+1; i++) {  
			    Row row = sh.createRow(i);
			    if (i==0) {
			    	row.createCell(0).setCellValue("记账日期");  
				    
			    	row.createCell(1).setCellValue("产品子类型");  
				
			    	row.createCell(2).setCellValue("城市");  
				
			    	row.createCell(3).setCellValue("资产所属方");  
				
			    	row.createCell(4).setCellValue("资金方");  
				    
			    	row.createCell(5).setCellValue("保证方");  
				    
			    	row.createCell(6).setCellValue("实还本金");  
				    
			    	row.createCell(7).setCellValue("实还利息"); 
				    
			    	row.createCell(8).setCellValue("本息核销日期");
				    
			    	row.createCell(9).setCellValue("费用核销日期");
			    	
			    	row.createCell(10).setCellValue("实还客户服务费"); 
			    
			    	row.createCell(11).setCellValue("实还账户管理费"); 
			    	
			    	row.createCell(12).setCellValue("实还提前还款手续费"); 
			    	
			    	row.createCell(13).setCellValue("实还滞纳金"); 
			    	
			    	row.createCell(14).setCellValue("实还印花税"); 
			    	
			    	row.createCell(15).setCellValue("实还增值服务费"); 
			    	
			    	row.createCell(16).setCellValue("实还委外催收费"); 
			    	
			    	row.createCell(17).setCellValue("实还随心还服务费"); 
			    	
			    	row.createCell(18).setCellValue("实还委外费"); 
			    	
				}else{
				    row.createCell(0).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("keepAccountsDate")));
				    row.createCell(1).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("subProductType")));  
				    row.createCell(2).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("city")));  
				    row.createCell(3).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("assetBelong")));  
				    row.createCell(4).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("fundProviders")));  
				    row.createCell(5).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("guaranteeParty")));  
				    row.createCell(6).setCellValue(StringUtil.checkMoneyIsNull(list.get(i-1).get("actualPayPrincipalAmt")));  
				    row.createCell(7).setCellValue(StringUtil.checkMoneyIsNull(list.get(i-1).get("actualPayinteAmt")));  
				    row.createCell(8).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("bxhxDate")));  
				    row.createCell(9).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("fyhxDate")));  
				    row.createCell(10).setCellValue(StringUtil.checkMoneyIsNull(list.get(i-1).get("a2")));
				    row.createCell(11).setCellValue(StringUtil.checkMoneyIsNull(list.get(i-1).get("a7")));
				    row.createCell(12).setCellValue(StringUtil.checkMoneyIsNull(list.get(i-1).get("a9")));  
				    row.createCell(13).setCellValue(StringUtil.checkMoneyIsNull(list.get(i-1).get("a10")));  
				    row.createCell(14).setCellValue(StringUtil.checkMoneyIsNull(list.get(i-1).get("a11")));  
				    row.createCell(15).setCellValue(StringUtil.checkMoneyIsNull(list.get(i-1).get("a12")));  
				    row.createCell(16).setCellValue(StringUtil.checkMoneyIsNull(list.get(i-1).get("a17")));  
				    row.createCell(17).setCellValue(StringUtil.checkMoneyIsNull(list.get(i-1).get("a18")));  
				    row.createCell(18).setCellValue(StringUtil.checkMoneyIsNull(list.get(i-1).get("a19")));  
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
	
	@RequestMapping("queryReceivedPaymentsDetailCount")
	public void queryReceivedPaymentsDetailCount(ReceivedPaymentsVO vo,HttpServletRequest request, HttpServletResponse response){
		//统计合同总数
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			if (vo != null) {
				map.put("seqId",vo.getSeqId());
				map.put("serialNo",vo.getSerialNo());
				map.put("startRegistrationDate",vo.getStartRegistrationDate());
				map.put("endRegistrationDate",vo.getEndRegistrationDate());
				map.put("startShouldAlsoDate",vo.getStartShouldAlsoDate());
				map.put("endShouldAlsoDate",vo.getEndShouldAlsoDate());
				map.put("startKeepAccountsDate",vo.getStartKeepAccountsDate());
				map.put("endKeepAccountsDate",vo.getEndKeepAccountsDate());
				map.put("subProductType",vo.getSubProductType());
				map.put("assetBelong",vo.getAssetBelong());
			}
			map = receivedPaymentsServer.queryReceivedPaymentsDetailCount(map);
		} catch (Exception e) {
			LOGGER.error("合同统计异常!", e);
		}
		ResponseUtil.sendJSON(response, map);
	}
	
	@RequestMapping("queryReceivedPaymentsGatherCount")
	public void queryReceivedPaymentsGatherCount(ReceivedPaymentsVO vo,HttpServletRequest request, HttpServletResponse response){
		//统计合同总数
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			//组装条件
			if (vo != null) {
				map.put("startKeepAccountsDate",vo.getStartKeepAccountsDate());
				map.put("endKeepAccountsDate",vo.getEndKeepAccountsDate());
				map.put("startShouldAlsoDate",vo.getStartShouldAlsoDate());
				map.put("endShouldAlsoDate",vo.getEndShouldAlsoDate());
				map.put("businessModel",vo.getBusinessModel());
				map.put("subProductType",vo.getSubProductType());
				map.put("city",vo.getCity());
				map.put("creditperson",vo.getCreditperson());
				map.put("assetBelong",vo.getAssetBelong());
				map.put("guaranteeParty",vo.getGuaranteeParty());
				map.put("cancelType",vo.getCancelType());
			}
			map = receivedPaymentsServer.queryReceivedPaymentsGatherCount(map);
		} catch (Exception e) {
			LOGGER.error("合同统计异常!", e);
		}
		ResponseUtil.sendJSON(response, map);
	}
	
}
