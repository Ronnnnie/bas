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

import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.entity.XZXDPaymentsVO;
import com.billionsfinance.bas.server.IXZXDPaymentsServer;
import com.billionsfinance.bas.server.impl.XZXDPaymentsServer;
import com.billionsfinance.bas.util.AjaxJson;
import com.billionsfinance.bas.util.ResponseUtil;
import com.billionsfinance.bas.util.StringUtil;

/**
 * @ClassName: 西藏小贷回款
 * @Description: XZXDPaymentsController
 * @author zhouFM
 * @Copyright Copyright (c) 2016 2016年11月18日 上午15:05:04 Company:佰仟金融
 */
@Controller
@RequestMapping("/xzxdPaymentsServer")
public class XZXDPaymentsController {

	/** 日志记录 */
	private static final Log LOGGER = LogFactory.getLog(XZXDPaymentsController.class);

	private static final IXZXDPaymentsServer  iXZXDPaymentsServer = new XZXDPaymentsServer();

	/**
	 * @methodName: toReceivedPaymentsDetail
	 * @Description: trustAllotDetail
	 * @return java.lang.String
	 */
	@RequestMapping("/toXZXD")
	public String toDayTradingDetail() {
		return "xzxd/xzxdPayments";
	}

	/**
	 * 明细合同查询.
	 * @param DayTradingDetailVO
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 */
	@RequestMapping("/queryXZXDPaymentsDetail")
	public void queryXZXDPaymentsDetail(Integer page, Integer rows, XZXDPaymentsVO vo,HttpServletRequest request, HttpServletResponse response) {
		PageVO pageVO = null;
		try {
			pageVO = new PageVO();
			pageVO.setPageSize(rows);
			pageVO.setPageNo(page);
			pageVO = iXZXDPaymentsServer.queryXZXDPaymentsDetail(pageVO, vo);
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
	@RequestMapping("queryXZXDPaymentsCount")
	public void queryXZXDPaymentsCount(XZXDPaymentsVO vo,HttpServletRequest request, HttpServletResponse response){
		//统计合同总数
		Map<String,Object> map = iXZXDPaymentsServer.queryXZXDPaymentsCount(vo);
		ResponseUtil.sendJSON(response, map);
	}
	
	/**
	 * @Description 逾期记账确认
	 * @param vo
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/paymentsAffirm")
	public AjaxJson paymentsAffirm(Integer page, Integer rows, XZXDPaymentsVO vo,HttpServletRequest request, HttpServletResponse response) {
		AjaxJson ajaxJson = null;
		PageVO pageVO = null;
		try {
			ajaxJson = new AjaxJson();
			pageVO = new PageVO();
			pageVO.setPageSize(10);
			pageVO.setPageNo(1);
			//记账确认
			int resultLine = iXZXDPaymentsServer.paymentsAffirm(vo);
			//修改合同查询
			pageVO = iXZXDPaymentsServer.queryXZXDPaymentsDetail(pageVO,vo);
			ajaxJson.setObj(pageVO);
			ajaxJson.setSuccess(true);
			if (pageVO.getTotal()>0&&resultLine>0) {
				ajaxJson.setMsg("合同回款成功!");
			}else{
				ajaxJson.setMsg("暂无匹配数据可回款!");
			}
		} catch (Exception e) {
			ajaxJson.setMsg("系统异常,合同回款失败!");
			ajaxJson.setObj(pageVO);
			ajaxJson.setSuccess(false);
			LOGGER.error("合同回款异常", e);
		}
		return ajaxJson;
	}
	
	/**
	 * @Description 合同回款确认
	 * @param vo
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/batchPaymentsAffirm")
	public AjaxJson batchPaymentsAffirm(XZXDPaymentsVO vo,HttpServletRequest request, HttpServletResponse response) {
		AjaxJson ajaxJson = null;
		PageVO pageVO = null;
		List<Map<String,Object>> listArr = null;
		try {
			listArr = new ArrayList<Map<String,Object>>();
			vo = paserXZXDPaymentsVO(vo);
			ajaxJson = new AjaxJson();
			pageVO = new PageVO();
			pageVO.setPageSize(10);
			pageVO.setPageNo(1);
			//记账确认
			if(vo.getSeqIdArr()!=null&&vo.getSeqIdArr().length>0){
				for (int i = 0; i < vo.getSerialNoArr().length; i++) {
					vo.setSerialno(vo.getSerialNoArr()[i]);
					vo.setSeqid(vo.getSeqIdArr()[i]);
					iXZXDPaymentsServer.paymentsAffirm(vo);
					listArr.add(iXZXDPaymentsServer.queryXZXDPaymentsFindAll(vo).get(0)) ;
				}
			}else{
				iXZXDPaymentsServer.paymentsAffirm(vo);
				listArr=iXZXDPaymentsServer.queryXZXDPaymentsFindAll(vo);
			}
			if (listArr.size() > 0 ) {
				pageVO.setRows(listArr);
				pageVO.setTotal((long) listArr.size());
				ajaxJson.setObj(pageVO);
				ajaxJson.setSuccess(true);
				ajaxJson.setMsg("合同回款成功!");
			}else{
				ajaxJson.setMsg("暂无匹配数据可回款!");
			}
		} catch (Exception e) {
			ajaxJson.setMsg("系统异常,合同回款失败!");
			ajaxJson.setObj(pageVO);
			ajaxJson.setSuccess(false);
			LOGGER.error("合同回款异常", e);
		}
		return ajaxJson;
	}
	
	/**
	 * 
	 * @Description  逾期汇总合同导出
	 * @throws ServletException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/excelExport")
	public AjaxJson excelExport(HttpServletRequest request, HttpServletResponse response,XZXDPaymentsVO vo) throws ServletException, IOException{
		AjaxJson ajaxJson = null;
		try {
			ajaxJson = new AjaxJson();
			String fileName = "导出Excel-西藏小贷数据.xlsx";  
			List<Map<String,Object>> list = iXZXDPaymentsServer.queryXZXDPaymentsFindAll(vo);
			
			SXSSFWorkbook wb = new SXSSFWorkbook(1024); // 这里100是在内存中的数量，如果大于此数量时，会写到硬盘，以避免在内存导致内存溢出  
			Sheet sh = wb.createSheet();
			if (list.size()==0) {
				request.getSession().setAttribute("exportedFlag", "true");
				return ajaxJson;
			}
			for (int i = 0; i < list.size()+1; i++) {  
			    Row row = sh.createRow(i);
			    if (i==0) {
			    	row.createCell(0).setCellValue("统计月份");  
				    
			    	row.createCell(1).setCellValue("合同号");  
				
			    	row.createCell(2).setCellValue("客户渠道");  
				
			    	row.createCell(3).setCellValue("业务模式");  
			    	
			    	row.createCell(4).setCellValue("产品类型");  
				
			    	row.createCell(5).setCellValue("产品子类型(贷款子类型)");  
				    
			    	row.createCell(6).setCellValue("省");  
				    
			    	row.createCell(7).setCellValue("城");  
				    
			    	row.createCell(8).setCellValue("城市编码"); 
				    
			    	row.createCell(9).setCellValue("期次");
				    
			    	row.createCell(10).setCellValue("注册月份");
			    	
			    	row.createCell(11).setCellValue("注册日"); 
			    	
			    	row.createCell(12).setCellValue("应还款月份"); 
				    
			    	row.createCell(13).setCellValue("应还款日"); 
				    
			    	row.createCell(14).setCellValue("转让月份"); 
				    
			    	row.createCell(15).setCellValue("转让日期"); 
				    
			    	row.createCell(16).setCellValue("转让应还款月份"); 
				   
			    	row.createCell(17).setCellValue("转让应还款日"); 
				    
			    	row.createCell(18).setCellValue("是否取消分期"); 
				    
			    	row.createCell(19).setCellValue("赎回月份"); 
				    
			    	row.createCell(20).setCellValue("理赔月份"); 
				    
			    	row.createCell(21).setCellValue("还款类型"); 
				    
			    	row.createCell(22).setCellValue("代垫方"); 
				    
			    	row.createCell(23).setCellValue("资产所属方");
			    	
			    	row.createCell(24).setCellValue("保证方");
			    	
			    	row.createCell(25).setCellValue("被担保方");
			    	
			    	row.createCell(26).setCellValue("担保方式");
			    	
			    	row.createCell(27).setCellValue("应还本金");
			    	
			    	row.createCell(28).setCellValue("应还利息");
			    	
			    	row.createCell(29).setCellValue("贷款本金总额");
			    	
			    	row.createCell(30).setCellValue("剩余本金总额");
			    	
			    	row.createCell(31).setCellValue("合计");
			    	
			    	row.createCell(32).setCellValue("应还利息罚息");
			    	
			    	row.createCell(33).setCellValue("应还本金罚息");
			    	
			    	row.createCell(34).setCellValue("逾期天数");
			    	
			    	row.createCell(35).setCellValue("实付日期");
			    	
			    	row.createCell(36).setCellValue("实付人");
			    	
			    	row.createCell(37).setCellValue("回款备注");
			    	
				}else{
				    row.createCell(0).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("accordmonth")));
				    row.createCell(1).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("serialno")));  
				    row.createCell(2).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("suretype")));  
				    row.createCell(3).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("businessmodel")));  
				    row.createCell(4).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("subproducttype")));  
				    row.createCell(5).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("productid")));  
				    row.createCell(6).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("province")));  
				    row.createCell(7).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("city")));  
				    row.createCell(8).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("citycode")));  
				    row.createCell(9).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("seqid")));  
				    row.createCell(10).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("registrationmonth")));  
				    row.createCell(11).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("registrationdate")));
				    row.createCell(12).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("paymonth")));
				    row.createCell(13).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("paydate")));  
				    row.createCell(14).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("deliverymonth")));  
				    row.createCell(15).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("deliverydate")));  
				    row.createCell(16).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("transferpaypaymentmonth")));  
				    row.createCell(17).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("transferpaypaymentday")));  
				    row.createCell(18).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("canceltype")));  
				    row.createCell(19).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("shmonth")));  
				    row.createCell(20).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("lpmonth")));  
				    row.createCell(21).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("paytype")));  
				    row.createCell(22).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("debours")));  
				    row.createCell(23).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("assetBelong")));  
				    row.createCell(24).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("guaranteeparty")));  
				    row.createCell(25).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("sponsor")));  
				    row.createCell(26).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("sponsortype")));  
				    row.createCell(27).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("payprincipalamt")));  
				    row.createCell(28).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("payinteamt")));  
				    row.createCell(29).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("businesssum")));  
				    row.createCell(30).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("sywhze")));  
				    row.createCell(31).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("payamt")));  
				    row.createCell(32).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("yhlxfx")));  
				    row.createCell(33).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("yhbjfx")));  
				    row.createCell(34).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("overduedays")));  
				    row.createCell(35).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("actualpaydate")));  
				    row.createCell(36).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("actualpayer")));  
				    row.createCell(37).setCellValue(StringUtil.isNullOrEmpty(list.get(i-1).get("remark")));  
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
			request.getSession().setAttribute("exportedFlag", "true");
		} catch (Exception e) {
			LOGGER.error("数据导出错误!", e);
		}
        return ajaxJson;
	}
	
	public XZXDPaymentsVO paserXZXDPaymentsVO(XZXDPaymentsVO vo){
		/**
		 * contains : true  记账多个合同
		 * contains : false 记账单个合同
		 */
		if(StringUtil.isNullOrEmpty(vo.getSeqid()).contains(",")){
			vo.setSeqIdArr(vo.getSeqid().split(","));
			vo.setSeqid("");
		}
		if(StringUtil.isNullOrEmpty(vo.getSerialno()).contains(",")){
			vo.setSerialNoArr(vo.getSerialno().split(","));
			vo.setSerialno("");
		}
		return vo;
	}
	
	/**
	 * @copyright 查询合同记账状态
	 * @param vo
	 * @param request
	 * @param response
	 */
	@RequestMapping("queryContractStatus")
	public void queryContractStatus(XZXDPaymentsVO vo,HttpServletRequest request, HttpServletResponse response){
		//统计合同总数
		try {
			List<Map<String,Object>> listArr = new ArrayList<Map<String,Object>>();
			vo=paserXZXDPaymentsVO(vo);
			if(vo.getSeqIdArr()!=null&&vo.getSeqIdArr().length>0){
				for (int i = 0; i < vo.getSerialNoArr().length; i++) {
					vo.setSerialno(vo.getSerialNoArr()[i]);
					vo.setSeqid(vo.getSeqIdArr()[i]);
					listArr.add(iXZXDPaymentsServer.queryXZXDPaymentsFindAll(vo).get(0)) ;
				}
			}else{
				listArr = iXZXDPaymentsServer.queryXZXDPaymentsFindAll(vo);
			}
			for (Map<String,Object> map: listArr) {
				if (map.containsKey("paydate")) {
					String paydate = StringUtil.isNullOrEmpty(map.get("paydate"));
					if (!paydate.equals("")){
						ResponseUtil.sendString(response,"1");
						return;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("查询合同回款状态异常!", e);
		}
		ResponseUtil.sendString(response,"");
	}
	
}
