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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.billionsfinance.als.security.CurrentUser;
import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.entity.VillageReceivedPaymentsVO;
import com.billionsfinance.bas.server.IVillageReceivedPaymentsServer;
import com.billionsfinance.bas.server.impl.VillageReceivedPaymentsServer;
import com.billionsfinance.bas.util.AjaxJson;
import com.billionsfinance.bas.util.ResponseUtil;
import com.billionsfinance.bas.util.StringUtil;

/**
 * @ClassName: VillageReceivedPaymentsController
 * @Description: 村行回款Controller
 * @author FMZhou
 * 
 * Copyright: Copyright (c) 2016 2016年11月25日 下午14:25:09 Company:佰仟金融
 */
@Controller
@RequestMapping("/villageReceivedPaymentsServer")
public class VillageReceivedPaymentsController {

	/** 日志记录 */
	private static final Log LOGGER = LogFactory.getLog(VillageReceivedPaymentsController.class);

	private static final IVillageReceivedPaymentsServer villageReceivedPaymentsServer = new VillageReceivedPaymentsServer();

	/**
	 * @Description: 跳转村行回款页面
	 */
	@RequestMapping("/toVillageReceivedPaymentsDetail")
	public String toVillageReceivedPaymentsDetail() {
		return "villageReceivedPayments/villageReceivedPaymentsDetail";	
	}
	
	/**
	 * @Description:村行回款数据查询
	 * @param vo
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryVillageReceivedPaymentsDetail")
	public void queryVillageReceivedPaymentsDetail(Integer page, Integer rows, VillageReceivedPaymentsVO vo,HttpServletRequest request, HttpServletResponse response) {
		PageVO pageVO;
		try {
			pageVO = new PageVO();
			pageVO.setPageSize(rows);
			pageVO.setPageNo(page);
			pageVO = villageReceivedPaymentsServer.queryVillageReceivedPaymentsDetail(pageVO, vo);
			ResponseUtil.sendJSON(response, pageVO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("查询村行回款数据失败!",e);
		}
	}
	
	
	/**
	 * @Description:村行回款记账确认
	 * @param vo
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/accountingMark")
	public AjaxJson accountingMark(VillageReceivedPaymentsVO vo,HttpServletRequest request, HttpServletResponse response) {
		AjaxJson ajaxJson = null;
		Double moneyCount = null;
		PageVO pageVO = null;
		try {
			ajaxJson = new AjaxJson();
			moneyCount = new Double(0);
			pageVO = new PageVO();	
			pageVO.setPageSize(10);
			pageVO.setPageNo(1);
			vo.setKeepAccountsBy(CurrentUser.getUser().getUsername());
			//记账确认
			int resultLine = villageReceivedPaymentsServer.accountingMark(vo);
			if (resultLine > 0 && (!("").equals(vo.getStartKeepAccountsDate())&&vo.getStartKeepAccountsDate()!=null||
						!("").equals(vo.getEndKeepAccountsDate())&&vo.getEndKeepAccountsDate()!=null)) {
				vo.setStartKeepAccountsDate(vo.getUpdateDate());
				vo.setEndKeepAccountsDate(vo.getUpdateDate());
			}
			//修改合同查询
			pageVO = villageReceivedPaymentsServer.queryVillageReceivedPaymentsDetail(pageVO,vo);
			ajaxJson.setObj(pageVO);
			ajaxJson.setSuccess(true);
			boolean flag = true;
			if (pageVO.getTotal()>0) {
				for (Map<String,Object> map: pageVO.getRows()) {
					String payStatus = StringUtil.isNullOrEmpty(map.get("payStatus"));
					if (!("1").equals(payStatus)) {
						flag = false;
					}
					moneyCount+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("payprinciPalamt"))));
				}
				if (flag) {
					ajaxJson.setMsg("记账确认标记成功!");
				}else{
					ajaxJson.setMsg("当前匹配合同存在非支付状态的合同，该些合同记账失败!");
				}
				pageVO.getRows().get(0).put("moneyCount",moneyCount);
				pageVO.getRows().get(0).put("contractCount",pageVO.getTotal());
			}else{
				ajaxJson.setMsg("暂无匹配数据可修改!");
			}
		} catch (Exception e) {
			ajaxJson.setMsg("系统异常,记账确认标记失败!");
			ajaxJson.setObj(null);
			ajaxJson.setSuccess(false);
			LOGGER.error("记账确认异常", e);
		}
		return ajaxJson;
	}
	
	/**
	 * @Description:村行回款记账确认
	 * @param vo
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/selectAccountingMark")
	public AjaxJson selectAccountingMark(VillageReceivedPaymentsVO vo,HttpServletRequest request, HttpServletResponse response) {
		Double payprinciPalamtSum = new Double(0);
		Double payInteamtSum = new Double(0);
		Double actualpayprincipalamt = new Double(0);
		Double actualpayinteamt = new Double(0);
		Double payprindefaultinteamt = new Double(0);
		Double businessSumCount = new Double(0);
		AjaxJson ajaxJson = null;
		Double moneyCount = null;
		PageVO pageVO = null;
		List<Map<String,Object>> list = null;
		try {
			ajaxJson = new AjaxJson();
			moneyCount = new Double(0);
			pageVO = new PageVO();
			list = new ArrayList<Map<String,Object>>();
			pageVO.setPageSize(10);
			pageVO.setPageNo(1);
			vo.setKeepAccountsBy(CurrentUser.getUser().getUsername());
			vo = changeVO(vo);
			if(vo.getContractNoArray()!=null&&vo.getContractNoArray().length>0){
				for (int i = 0; i < vo.getContractNoArray().length; i++) {
					vo.setContractNo(vo.getContractNoArray()[i]);
					vo.setSeqId(vo.getSeqIdArray()[i]);
					villageReceivedPaymentsServer.accountingMark(vo);
					list.add(villageReceivedPaymentsServer.queryVillageReceivedPaymentsFindAll(vo).get(0));
				}
			}else{
				villageReceivedPaymentsServer.accountingMark(vo);
				list=villageReceivedPaymentsServer.queryVillageReceivedPaymentsFindAll(vo);
			}
			boolean flag = true;
			if (list.size()>0) {
				for (Map<String,Object> map: list) {
					String payStatus = StringUtil.isNullOrEmpty(map.get("payStatus"));
					if (!("1").equals(payStatus) ) {
						flag = false;
					}
					moneyCount+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("payprinciPalamt"))));
					payprinciPalamtSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("payprinciPalamt"))));
					payInteamtSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("payInteamt"))));
					actualpayprincipalamt+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("actualpayprincipalamt"))));
					actualpayinteamt+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("actualpayinteamt"))));
					payprindefaultinteamt+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("payprindefaultinteamt"))));
					businessSumCount+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("businessSum"))));
				}
				if (flag) {
					ajaxJson.setMsg("记账确认标记成功!");
				}else{
					ajaxJson.setMsg("当前匹配合同存在非支付状态的合同，该些合同记账失败!");
				}
				pageVO.setRows(list);
				pageVO.setTotal((long)list.size());
				pageVO.getRows().get(0).put("moneyCount",moneyCount);
				pageVO.getRows().get(0).put("contractCount",pageVO.getTotal());
				pageVO.getRows().get(0).put("payprinciPalamtSum",payprinciPalamtSum);
				pageVO.getRows().get(0).put("payInteamtSum",payInteamtSum);
				pageVO.getRows().get(0).put("actualpayprincipalamt",actualpayprincipalamt);
				pageVO.getRows().get(0).put("actualpayinteamt",actualpayinteamt);
				pageVO.getRows().get(0).put("payprindefaultinteamt",payprindefaultinteamt);
				pageVO.getRows().get(0).put("businessSumCount",businessSumCount);
				ajaxJson.setObj(pageVO);
				ajaxJson.setSuccess(true);
			}else{
				ajaxJson.setMsg("暂无匹配数据可修改!");
			}
		} catch (Exception e) {
			ajaxJson.setMsg("系统异常,记账确认标记失败!");
			ajaxJson.setObj(null);
			ajaxJson.setSuccess(false);
			LOGGER.error("记账确认异常", e);
		}
		return ajaxJson;
	}
	
	/**
	 * @Description:村行回款记账撤销
	 * @param vo
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/cancelAccountingMark")
	public AjaxJson cancelAccountingMark(VillageReceivedPaymentsVO vo,HttpServletRequest request, HttpServletResponse response) {
		Boolean roleFlag = (Boolean) request.getSession().getAttribute("isRevokeRole");
		if(roleFlag==null||!roleFlag){
			ResponseUtil.sendMessage(response, false, "暂无权限操作此功能!");
			return null;
		}
		Double payprinciPalamtSum = new Double(0);
		Double payInteamtSum = new Double(0);
		Double actualpayprincipalamt = new Double(0);
		Double actualpayinteamt = new Double(0);
		Double payprindefaultinteamt = new Double(0);
		Double businessSumCount = new Double(0);
		AjaxJson ajaxJson = null;
		Double moneyCount = null;
		PageVO pageVO = null;
		List<Map<String,Object>> list = null;
		boolean flag = true;
		try {
			ajaxJson = new AjaxJson();
			moneyCount = new Double(0);
			pageVO = new PageVO();
			list = new ArrayList<Map<String,Object>>();
			pageVO.setPageSize(10);
			pageVO.setPageNo(1);
			vo = changeVO(vo);
			if(vo.getContractNoArray()!=null&&vo.getContractNoArray().length>0){
				for (int i = 0; i < vo.getContractNoArray().length; i++) {
					vo.setContractNo(vo.getContractNoArray()[i]);
					vo.setSeqId(vo.getSeqIdArray()[i]);
					list.add(villageReceivedPaymentsServer.queryVillageReceivedPaymentsFindAll(vo).get(0));
					villageReceivedPaymentsServer.cancelAccountingMark(vo);
				}
			}else{
				list=villageReceivedPaymentsServer.queryVillageReceivedPaymentsFindAll(vo);
				villageReceivedPaymentsServer.cancelAccountingMark(vo);
			}
			if (list.size()>0) {
				for (Map<String,Object> map: list) {
					String keepAccountsStatus = StringUtil.isNullOrEmpty(map.get("keepAccountsStatus"));
					if (!("1").equals(keepAccountsStatus)) {
						flag = false;
					}else{
						map.put("keepAccountsDate", "");
						map.put("keepAccountsStatus", 2);
					}
					moneyCount+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("payprinciPalamt"))));
					payprinciPalamtSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("payprinciPalamt"))));
					payInteamtSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("payInteamt"))));
					actualpayprincipalamt+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("actualpayprincipalamt"))));
					actualpayinteamt+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("actualpayinteamt"))));
					payprindefaultinteamt+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("payprindefaultinteamt"))));
					businessSumCount+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("businessSum"))));
				}
				if (flag) {
					ajaxJson.setMsg("记账确认标记成功!");
				}else{
					ajaxJson.setMsg("当前匹配合同存在未记账/已撤销记账的合同，该些合同记账撤销失败!");
				}
				pageVO.setRows(list);
				pageVO.getRows().get(0).put("moneyCount",moneyCount);
				pageVO.getRows().get(0).put("contractCount",pageVO.getTotal());
				pageVO.getRows().get(0).put("payprinciPalamtSum",payprinciPalamtSum);
				pageVO.getRows().get(0).put("payInteamtSum",payInteamtSum);
				pageVO.getRows().get(0).put("actualpayprincipalamt",actualpayprincipalamt);
				pageVO.getRows().get(0).put("actualpayinteamt",actualpayinteamt);
				pageVO.getRows().get(0).put("payprindefaultinteamt",payprindefaultinteamt);
				pageVO.getRows().get(0).put("businessSumCount",businessSumCount);
				ajaxJson.setObj(pageVO);
				ajaxJson.setSuccess(true);
			}else{
				ajaxJson.setMsg("暂无匹配数据可修改!");
			}
		} catch (Exception e) {
			ajaxJson.setMsg("系统异常,记账确认标记失败!");
			ajaxJson.setObj(null);
			ajaxJson.setSuccess(false);
			LOGGER.error("记账确认异常", e);
		}
		return ajaxJson;
	}
	
	/**
	 * @Description:村行回款记账确认
	 * @param vo 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/contractApprove")
	public AjaxJson contractApprove(Integer page, Integer rows, VillageReceivedPaymentsVO vo,HttpServletRequest request, HttpServletResponse response) {
		AjaxJson ajaxJson = null;
		Double moneyCount = null;
		PageVO pageVO = null;
		Double payprinciPalamtSum = new Double(0);
		Double payInteamtSum = new Double(0);
		Double actualpayprincipalamt = new Double(0);
		Double actualpayinteamt = new Double(0);
		Double payprindefaultinteamt = new Double(0);
		Double businessSumCount = new Double(0);
		try {
			ajaxJson = new AjaxJson();
			moneyCount = new Double(0);
			pageVO = new PageVO();
			pageVO.setPageSize(10);
			pageVO.setPageNo(1);
			vo.setApproveBy(CurrentUser.getUser().getUsername());
			//记账确认
			villageReceivedPaymentsServer.approveContract(vo);
			//修改合同查询
			pageVO = villageReceivedPaymentsServer.queryVillageReceivedPaymentsDetail(pageVO, vo);
			ajaxJson.setObj(pageVO);
			ajaxJson.setSuccess(true);
			if (pageVO.getTotal()>0) {
				for (Map<String,Object> map: pageVO.getRows()) {
					moneyCount+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("payprinciPalamt"))));
					payprinciPalamtSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("payprinciPalamt"))));
					payInteamtSum+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("payInteamt"))));
					actualpayprincipalamt+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("actualpayprincipalamt"))));
					actualpayinteamt+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("actualpayinteamt"))));
					payprindefaultinteamt+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("payprindefaultinteamt"))));
					businessSumCount+=(Double.parseDouble(StringUtil.checkMoneyIsNull(map.get("businessSum"))));
				}
				ajaxJson.setMsg("合同审核成功!");
				pageVO.getRows().get(0).put("moneyCount",moneyCount);
				pageVO.getRows().get(0).put("contractCount",pageVO.getTotal());
				pageVO.getRows().get(0).put("payprinciPalamtSum",payprinciPalamtSum);
				pageVO.getRows().get(0).put("payInteamtSum",payInteamtSum);
				pageVO.getRows().get(0).put("actualpayprincipalamt",actualpayprincipalamt);
				pageVO.getRows().get(0).put("actualpayinteamt",actualpayinteamt);
				pageVO.getRows().get(0).put("payprindefaultinteamt",payprindefaultinteamt);
				pageVO.getRows().get(0).put("businessSumCount",businessSumCount);
			}else{
				ajaxJson.setMsg("暂无匹配合同可审核!");
			}
		} catch (Exception e) {
			ajaxJson.setMsg("系统异常,合同审核失败!");
			ajaxJson.setObj(pageVO);
			ajaxJson.setSuccess(false);
			LOGGER.error("合同审核异常", e);
		}
		return ajaxJson;
		
	}
	
	/**
	 * @Description:查询村行回款合同数
	 */
	@RequestMapping("queryVillageReceivedPaymentsCount")
	public void queryVillageReceivedPaymentsCount(VillageReceivedPaymentsVO vo,HttpServletRequest request, HttpServletResponse response){
		//统计合同总数
		Map<String,Object> map = villageReceivedPaymentsServer.queryVillageReceivedPaymentsCount(vo);
		ResponseUtil.sendJSON(response, map);
	}
	
	/**
	 * @Description:查询村行回款合同记账状态
	 * @param vo
	 * @param request
	 * @param response
	 */
	@RequestMapping("queryVillageReceivedPaymentsContract")
	public void queryVillageReceivedPaymentsContract(VillageReceivedPaymentsVO vo,HttpServletRequest request, HttpServletResponse response){
		//查询匹配未到期合同
		List<Map<String, Object>> list = villageReceivedPaymentsServer.queryVillageReceivedPaymentsContract(vo);
		for (Map<String,Object> overdueMap: list) {
			if (overdueMap.containsKey("keepAccountsStatus")) {
				String keepAccountsStatus = StringUtil.isNullOrEmpty(overdueMap.get("keepAccountsStatus"));
				if (keepAccountsStatus.equals("1")){
					ResponseUtil.sendString(response,keepAccountsStatus );
					return;
				}
			}
		}
		ResponseUtil.sendString(response,"");
	}
	
	/**
	 * @Description:查询村行回款合同记账状态
	 * @param vo
	 * @param request
	 * @param response
	 */
	@RequestMapping("queryVillageReceivedPaymentsContractSelect")
	public void queryVillageReceivedPaymentsContractSelect(VillageReceivedPaymentsVO vo,HttpServletRequest request, HttpServletResponse response){
		//查询匹配未到期合同
		vo=changeVO(vo);
		List<Map<String, Object>> list = villageReceivedPaymentsServer.queryVillageReceivedPaymentsFindAll(vo);
		for (Map<String,Object> overdueMap: list) {
			if (overdueMap.containsKey("keepAccountsStatus")) {
				String keepAccountsStatus = StringUtil.isNullOrEmpty(overdueMap.get("keepAccountsStatus"));
				if (keepAccountsStatus.equals("1")){
					ResponseUtil.sendString(response,keepAccountsStatus );
					return;
				}
			}
		}
		ResponseUtil.sendString(response,"");
	}
	
	@RequestMapping("queryDetailCount")
	public void queryDetailCount(VillageReceivedPaymentsVO vo,HttpServletRequest request, HttpServletResponse response){
		//统计合同总数
		Map<String,Object> map = null;
		try {
			map = villageReceivedPaymentsServer.queryVillageReceivedPaymentsCount(vo);
		} catch (Exception e) {
			LOGGER.error("合同统计异常!", e);
		}
		ResponseUtil.sendJSON(response, map);
	}
	
	/**
	 * 
	 * @Description 村行回款合同导出
	 * @throws ServletException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/detailExport")
	public AjaxJson detailExport(HttpServletRequest request, HttpServletResponse response,VillageReceivedPaymentsVO vo) throws ServletException, IOException{
		AjaxJson ajaxJson = null;
		try {
			ajaxJson = new AjaxJson();
			String fileName = "导出Excel-村行回款明细.xlsx";  
			List<Map<String,Object>> list = villageReceivedPaymentsServer.villageReceivedPaymentsExport(vo);
			
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
			    	row.createCell(0).setCellValue("记账日期");  
				    
				    //第一行第2列  
			    	row.createCell(1).setCellValue("合同号");  
				
				    //第一行第3列  
			    	row.createCell(2).setCellValue("注册日期");  
				
				    //第一行第4列  
			    	row.createCell(3).setCellValue("贷款本金");  
				
				    //第一行第5列  
			    	row.createCell(4).setCellValue("应还日期");
			    	
			    	//第一行第6列  
			    	row.createCell(5).setCellValue("到期日期");  
				    
				    //第一行第7列  
			    	row.createCell(6).setCellValue("期次");  
				    
				    //第一行第8列  
			    	row.createCell(7).setCellValue("强制日期"); 
				    
				    //第一行第9列  
			    	row.createCell(8).setCellValue("省");  
			    	
			    	//第一行第10列  
			    	row.createCell(9).setCellValue("城市");  
				    
				    //第一行第11列  
			    	row.createCell(10).setCellValue("城市编码"); 
				    
				    //第一行第12列
			    	row.createCell(11).setCellValue("贷款类型"); 
				    
				    //第一行第13列
			    	row.createCell(12).setCellValue("贷款子类型"); 
				    
				    //第一行第14列  
			    	row.createCell(13).setCellValue("还款类型"); 
				    
				    //第一行第15列  
			    	row.createCell(14).setCellValue("业务模式"); 
				    
				    //第一行第16列  
			    	row.createCell(15).setCellValue("资产所属方"); 
				    
				    //第一行第17列
			    	row.createCell(16).setCellValue("保证方"); 
				    
				    //第一行第18列  
			    	row.createCell(17).setCellValue("应还本金"); 
				    
				    //第一行第19列  
			    	row.createCell(18).setCellValue("应还利息"); 
				    
				    //第一行第18列  
			    	row.createCell(19).setCellValue("实还本金"); 
				    
				    //第一行第19列  
			    	row.createCell(20).setCellValue("实还利息"); 
				    
				    //第一行第19列  
			    	//row.createCell(21).setCellValue("逾期罚息");

                    //第一行第19列
                    row.createCell(21).setCellValue("实还本金罚息");

                    //第一行第19列
                    row.createCell(22).setCellValue("实还利息罚息");

                    //第一行第20列
			    	row.createCell(23).setCellValue("合计");
				    
			    	continue;
				}
		        row.createCell(0).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("keepAccountsDate")));
			    row.createCell(1).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("contractNo")));  
			    row.createCell(2).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("registrationDate")));  
			    row.createCell(3).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("businessSum")));  
			    row.createCell(4).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("payDate")));  
			    row.createCell(5).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("maturityDate")));  
			    row.createCell(6).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("seqId")));  
			    row.createCell(7).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("cdate")));  
			    row.createCell(8).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("province")));  
			    row.createCell(9).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("city")));  
			    row.createCell(10).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("cityCode")));  
			    row.createCell(11).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("productId")));  
			    row.createCell(12).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("subProductType")));  
			    row.createCell(13).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("payType")));  
			    row.createCell(14).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("businessmodel")));  
			    row.createCell(15).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("assetBelong")));  
			    row.createCell(16).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("guaranteeParty")));  
			    row.createCell(17).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("payprinciPalamt")));  
			    row.createCell(18).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("payInteamt")));   
			    row.createCell(19).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("actualpayprincipalamt")));  
			    row.createCell(20).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("actualpayinteamt")));   
			    //row.createCell(21).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("payprindefaultinteamt")));
			    row.createCell(21).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("actpayprindefaultinteamt")));
			    row.createCell(22).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("actpayintedefaultinteamt")));
			    row.createCell(23).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("amount")));
			    
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
	
	public VillageReceivedPaymentsVO changeVO(VillageReceivedPaymentsVO vo){
		/**
		 * contains : true  记账多个合同
		 * contains : false 记账单个合同
		 */
		if(StringUtil.isNullOrEmpty(vo.getContractNo()).contains(",")){
			vo.setContractNoArray(vo.getContractNo().split(","));
		}
		if(StringUtil.isNullOrEmpty(vo.getSeqId()).contains(",")){
			vo.setSeqIdArray(vo.getSeqId().split(","));
		}
		return vo;
	}
}
