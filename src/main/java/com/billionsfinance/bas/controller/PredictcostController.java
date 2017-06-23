package com.billionsfinance.bas.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.billionsfinance.bas.entity.PredictcostVO;
import com.billionsfinance.bas.server.IPredictcostServer;
import com.billionsfinance.bas.server.impl.PredictcostServer;
import com.billionsfinance.bas.util.AjaxJson;
import com.billionsfinance.bas.util.ResponseUtil;
import com.billionsfinance.bas.util.StringUtil;


/**
 * @ClassName: CurrentExpiredController
 * @Description: 当期到期表控制器
 * @author zsp
 * @Copyright: Copyright (c) 2016 2016年10月10日 下午16:05:09 Company:佰仟金融
 */
@Controller
@RequestMapping("/predictcostServer")
public class PredictcostController {

	/** 日志记录 */
	private static final Log LOGGER = LogFactory.getLog(PredictcostController.class);
	
	private static final IPredictcostServer predictcostServer = new PredictcostServer();

	@RequestMapping("/toPredictcostList")
	public String toPredictcostList(HttpServletRequest request, HttpServletResponse response) {
		return "predictcost/predictcostList";
	}
	
	@RequestMapping("/toPredictcostListSum")
	public String toBusinessCheckListSum(HttpServletRequest request, HttpServletResponse response) {
		return "predictcost/predictcostListSum";
	}
	
	/**
	 * @Description:查询当期到期表数据
	 * @param vo
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryPredictcostList")
	public void queryPredictcostList(Integer page,Integer rows,PredictcostVO vo,HttpServletRequest request, HttpServletResponse response) {
		PageVO pageVO = new PageVO();
		try {
			pageVO.setPageSize(rows);
			pageVO.setPageNo(page);
			pageVO = predictcostServer.queryPredictcost(pageVO, vo);
		} catch (Exception e) {
			LOGGER.error("查询当期到期表失败!", e);
		}finally {
			ResponseUtil.sendJSON(response, pageVO);
		}
	}
	
	/**
	 * @Description:查询当期到期汇总表数据
	 * @param vo
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryPredictcostTotal")
	public void queryPredictcostSummary(Integer page,Integer rows,PredictcostVO vo,HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			map = predictcostServer.queryPredictcostTotal(vo);
		} catch (Exception e) {
			LOGGER.error("查询当期到期表失败!", e);
		}finally {
			ResponseUtil.sendJSON(response, map);
		}
	}
	
	/**
	 * @Description:查询当期到期汇总表数据
	 * @param vo
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryPredictcostListSum")
	public void queryBusinessCheckListDataSum(Integer page,Integer rows,PredictcostVO vo,HttpServletRequest request, HttpServletResponse response) {
		PageVO pageVO = new PageVO();
		try {
			pageVO.setPageSize(rows);
			pageVO.setPageNo(page);
			pageVO = predictcostServer.queryPredictcostSum(pageVO, vo);
		} catch (Exception e) {
			LOGGER.error("查询当期到期汇总表失败!", e);
		}finally {
			ResponseUtil.sendJSON(response, pageVO);
		}
	}
	
	/**
	 * @Description:查询当期到期汇总表总数
	 * @param vo
	 * @param request
	 * @param response
	 */
	@RequestMapping("/queryPredictcostSumTotal")
	public void queryCurrentExpiredSumTotal(Integer page,Integer rows,PredictcostVO vo,HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			map = predictcostServer.queryPredictcostSumTotal(vo);
		} catch (Exception e) {
			LOGGER.error("查询当期到期表失败!", e);
		}finally {
			ResponseUtil.sendJSON(response, map);
		}
	}

	
	/**
	 * @Description:导出当期到期表
	 * @param vo
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/excelExport")
	public AjaxJson excelExport(HttpServletRequest request, HttpServletResponse response){
		AjaxJson ajaxJson = new AjaxJson();
		//统计合同总数
		String tempAssert = request.getParameter("belong");
		String belong="";
		if(tempAssert!=null){
			try {
				belong = java.net.URLDecoder.decode(tempAssert, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				belong="";
			} 
		}
		String startPayDate = request.getParameter("startPayDate");
		String endPayDate = request.getParameter("endPayDate");
		String contractno = request.getParameter("contractno");
		String startRegistrationDate = request.getParameter("startRegistrationDate");
		String endRegistrationDate = request.getParameter("endRegistrationDate");
		String startKeepaccountsDate = request.getParameter("startKeepaccountsDate");
		String endKeepaccountsDate = request.getParameter("endKeepaccountsDate");
		String startInaccountdate = request.getParameter("startInaccountdate");
		String endInaccountdate = request.getParameter("endInaccountdate");
		PredictcostVO queryVo = new PredictcostVO();
		queryVo.setBelong(belong);
		queryVo.setStartPayDate(startPayDate);
		queryVo.setEndPayDate(endPayDate);
		queryVo.setContractno(contractno);
		queryVo.setStartRegistrationDate(startRegistrationDate);
		queryVo.setEndRegistrationDate(endRegistrationDate);
		queryVo.setStartKeepaccountsDate(startKeepaccountsDate);
		queryVo.setEndKeepaccountsDate(endKeepaccountsDate);
		queryVo.setStartInaccountdate(startInaccountdate);
		queryVo.setEndInaccountdate(endInaccountdate);
		
		List<PredictcostVO> list = predictcostServer.queryPredictcostAll(queryVo);
		if(list.isEmpty()){
			ajaxJson.setMsg("暂无数据可导出!");
			return ajaxJson;
		}
		String fileName = "预提明细表导出Excel.xlsx";  
	    
	    
		try {  
			
		    // 定义单元格报头  
		    //String worksheetTitle = "当期应付资产方汇总";  
	        SXSSFWorkbook wb = new SXSSFWorkbook(100); // 这里100是在内存中的数量，如果大于此数量时，会写到硬盘，以避免在内存导致内存溢出  
			Sheet sh = wb.createSheet();  
			
			Row row = sh.createRow(0);
	    	row.createCell(0).setCellValue("记账期间");  
		    
	    	row.createCell(1).setCellValue("统计日期");  
		
	    	row.createCell(2).setCellValue("合同号");  
	    	row.createCell(3).setCellValue("客户姓名");
	    	row.createCell(4).setCellValue("合同注册日");  
	    	row.createCell(5).setCellValue("合同到期日");  
	    	
	    	row.createCell(6).setCellValue("门店代码");  
	    	row.createCell(7).setCellValue("商户代码");  
	    	row.createCell(8).setCellValue("SA_ID");  
	    	row.createCell(9).setCellValue("商品范畴");  
	    	
	    	row.createCell(10).setCellValue("客户渠道");  
	    	
	    	row.createCell(11).setCellValue("业务模式");  
		
	    	row.createCell(12).setCellValue("贷款类型");  
	    	
	    	row.createCell(13).setCellValue("贷款子类型");  
		    
	    	row.createCell(14).setCellValue("省份");
	    	
	    	row.createCell(15).setCellValue("城市");  
		    
	    	row.createCell(16).setCellValue("城市编码"); 
		    
	    	row.createCell(17).setCellValue("资金方");  
		    
	    	row.createCell(18).setCellValue("合同轨迹"); 
	    	
	    	row.createCell(19).setCellValue("强制日期"); 
	    	
	    	row.createCell(20).setCellValue("逾期天数"); 
	    	
	    	row.createCell(21).setCellValue("逾期描述"); 
		    
	    	row.createCell(22).setCellValue("五级分类"); 
		    
	    	row.createCell(23).setCellValue("应还日"); 
		    
	    	row.createCell(24).setCellValue("期次"); 
		   
	    	row.createCell(25).setCellValue("是否取消分期期次"); 
		    
	    	row.createCell(26).setCellValue("转让日"); 
	    	
	    	row.createCell(27).setCellValue("代偿日"); 
		   
	    	row.createCell(28).setCellValue("赎回日"); 
	    	
	    	row.createCell(29).setCellValue("保险投保日"); 
	    	
	    	row.createCell(30).setCellValue("保险理赔日"); 
	    	
	    	row.createCell(31).setCellValue("代垫方"); 
	    	
	    	row.createCell(32).setCellValue("资产所属方"); 
	    	
	    	row.createCell(33).setCellValue("保证方"); 
	    	
	    	row.createCell(34).setCellValue("预提本金"); 
	    	row.createCell(35).setCellValue("预提利息"); 
	    	
	    	row.createCell(36).setCellValue("预提客户服务费"); 
	    	row.createCell(37).setCellValue("预提财务管理费"); 
	    	row.createCell(38).setCellValue("预提增值服务费"); 
	    	row.createCell(39).setCellValue("预提随心还服务费"); 
		    
	    	row.createCell(40).setCellValue("合计（此表单金额合计）"); 
		    
		    //第一行第20列  
			for (int i = 0; i < list.size(); i++) {  
				//sh.setColumnWidth(i,5000);
					Row inrow = sh.createRow(i+1);
				    
					inrow.createCell(0).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getInaccountdate()));
					inrow.createCell(1).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getDuedate())); 
					
					inrow.createCell(2).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getContractno())); 
					inrow.createCell(3).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getClientname())); 
					
					inrow.createCell(4).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getRegistrationdate())); 
					inrow.createCell(5).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getMaturitydate()));  
					inrow.createCell(6).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getSno()));  
					inrow.createCell(7).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getRno()));  
					inrow.createCell(8).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getSaId()));  
					
					inrow.createCell(9).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getProductcategory()));  
					inrow.createCell(10).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getSuretype()));  
					inrow.createCell(11).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getBusinessmodel()));  
					inrow.createCell(12).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getProductid())); 
					inrow.createCell(13).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getSubproducttype()));  
					inrow.createCell(14).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getProvince()));  
					
					inrow.createCell(15).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getCity()));  
					inrow.createCell(16).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getCitycode()));  
					inrow.createCell(17).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getCreditperson()));  
					inrow.createCell(18).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getBelonglife()));  
					inrow.createCell(19).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getCdate()));  
					inrow.createCell(20).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getOverduedays()));  
					
					inrow.createCell(21).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getDescribe()));  
					inrow.createCell(22).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getClassfy()));  
					inrow.createCell(23).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getPaydate()));  
					inrow.createCell(24).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getSeqid()));  
					inrow.createCell(25).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getCanceltype()));  
					inrow.createCell(26).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getDeliverydate()));  
					
					inrow.createCell(27).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getDcdate()));  
					inrow.createCell(28).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getShdate()));  
					inrow.createCell(29).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getTbdate()));  
					inrow.createCell(30).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getLpdate()));  
					inrow.createCell(31).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getDebours()));  
					inrow.createCell(32).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getBelong())); 
					
					inrow.createCell(33).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getGuaranteeparty()));  
					inrow.createCell(34).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getPayprincipalamt()));  
					inrow.createCell(35).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getPayinteamt())); 
					inrow.createCell(36).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getA2()));  
					inrow.createCell(37).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getA7()));  
					inrow.createCell(38).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getA12())); 
					inrow.createCell(39).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getA18()));  
					inrow.createCell(40).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getAmount()));  
					
					
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
			ajaxJson.setMsg("数据导出成功!");
	    } catch (IOException e) {  
	    	ajaxJson.setMsg("数据导出失败,请稍后重试!");
	        e.printStackTrace();  
	        System.out.println("Output   is   closed ");  
	    }
	    
	    return ajaxJson;
	}
	
	
	/**
	 * @Description:检测导出是否有数据
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/excelCheck")
	public AjaxJson excelCheck(HttpServletRequest request, HttpServletResponse response){
		AjaxJson ajaxJson = new AjaxJson();
		//统计合同总数
		String tempAssert = request.getParameter("belong");
		String belong="";
		if(tempAssert!=null){
			try {
				belong = java.net.URLDecoder.decode(tempAssert, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				belong="";
			} 
		}
		String startPayDate = request.getParameter("startPayDate");
		String endPayDate = request.getParameter("endPayDate");
		String startRegistrationDate = request.getParameter("startRegistrationDate");
		String endRegistrationDate = request.getParameter("endRegistrationDate");
		String contractno = request.getParameter("contractno");
		String startKeepaccountsDate = request.getParameter("startKeepaccountsDate");
		String endKeepaccountsDate = request.getParameter("endKeepaccountsDate");
		String startInaccountdate = request.getParameter("startInaccountdate");
		String endInaccountdate = request.getParameter("endInaccountdate");
		
		PredictcostVO queryVo = new PredictcostVO();
		queryVo.setBelong(belong);
		queryVo.setStartPayDate(startPayDate);
		queryVo.setEndPayDate(endPayDate);
		queryVo.setStartRegistrationDate(startRegistrationDate);
		queryVo.setEndRegistrationDate(endRegistrationDate);
		queryVo.setContractno(contractno);
		queryVo.setStartKeepaccountsDate(startKeepaccountsDate);
		queryVo.setEndKeepaccountsDate(endKeepaccountsDate);
		queryVo.setStartInaccountdate(startInaccountdate);
		queryVo.setEndInaccountdate(endInaccountdate);

		Map<String, Object> map = predictcostServer.queryPredictcostTotal(queryVo);
		if(map.isEmpty()){
			ajaxJson.setSuccess(false);
			ajaxJson.setMsg("暂无数据可导出!");
			return ajaxJson;
		}else{
			if(map.get("CONTRACTCOUNT") != null&&Long.parseLong(map.get("CONTRACTCOUNT").toString()) >0){
				if(Long.parseLong(map.get("CONTRACTCOUNT").toString())>300000){
					ajaxJson.setSuccess(false);
					ajaxJson.setMsg("导出数量超过30万，请重新筛选!");
					return ajaxJson;
				}else{
					ajaxJson.setSuccess(true);
					return ajaxJson;					
				}
			}else{
				ajaxJson.setSuccess(false);
				ajaxJson.setMsg("暂无数据可导出!");
				return ajaxJson;
			}
		}
	}
		
	
	/**
	 * @Description:导出当期到期汇总表
	 * @param vo
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/excelExportSum")
	public AjaxJson excelExportSum(HttpServletRequest request,PredictcostVO vo, HttpServletResponse response){
		AjaxJson ajaxJson = new AjaxJson();
		//统计合同总数
		String tempAssert = vo.getBelong();
		String belong="";
		if(tempAssert!=null){
			try {
				belong = java.net.URLDecoder.decode(tempAssert, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				belong="";
			} 
		}
		vo.setBelong(belong);
		
		String tempSub = vo.getSubproducttype();
		String subproducttype="";
		if(tempSub!=null){
			try {
				subproducttype = java.net.URLDecoder.decode(tempSub, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				subproducttype="";
			} 
		}
		vo.setSubproducttype(subproducttype);
		
		String tempBus = vo.getBusinessmodel();
		String businessmodel="";
		if(tempBus!=null){
			try {
				businessmodel = java.net.URLDecoder.decode(tempBus, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				businessmodel="";
			} 
		}
		vo.setBusinessmodel(businessmodel);
		
		String tempCre = vo.getCreditperson();
		String creditperson="";
		if(tempCre!=null){
			try {
				creditperson = java.net.URLDecoder.decode(tempCre, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				creditperson="";
			} 
		}
		vo.setCreditperson(creditperson);
		
		String tempGua = vo.getGuaranteeparty();
		String guaranteeparty="";
		if(tempGua!=null){
			try {
				guaranteeparty = java.net.URLDecoder.decode(tempGua, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				guaranteeparty="";
			} 
		}
		vo.setGuaranteeparty(guaranteeparty);
		
		String tempCity = vo.getCity();
		String city="";
		if(tempCity!=null){
			try {
				city = java.net.URLDecoder.decode(tempCity, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				city="";
			} 
		}
		vo.setCity(city);
		
		String tempCancel = vo.getCanceltype();
		String canceltype="";
		if(tempCancel!=null){
			try {
				canceltype = java.net.URLDecoder.decode(tempCancel, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				canceltype="";
			} 
		}
		vo.setCanceltype(canceltype);
		
		List<PredictcostVO> list = predictcostServer.queryPredictcostSumAll(vo);
		String fileName = "预提汇总表导出Excel.xlsx";  
	    
	    
		try {  
			
		    // 定义单元格报头  
		    //String worksheetTitle = "当期应付资产方汇总";  
	        SXSSFWorkbook wb = new SXSSFWorkbook(100); // 这里100是在内存中的数量，如果大于此数量时，会写到硬盘，以避免在内存导致内存溢出  
			Sheet sh = wb.createSheet();  
			
			Row row = sh.createRow(0);
			row.createCell(0).setCellValue("记账期间");  
		    
	    	row.createCell(1).setCellValue("统计日期");  
		
	    	
	    	row.createCell(2).setCellValue("客户渠道");  
	    	
	    	row.createCell(3).setCellValue("业务模式");  
		
	    	
	    	row.createCell(4).setCellValue("贷款子类型");  
		    
	    	
	    	row.createCell(5).setCellValue("城市");  
		    
	    	row.createCell(6).setCellValue("城市编码"); 
		    
	    	row.createCell(7).setCellValue("逾期天数"); 
	    	
	    	row.createCell(8).setCellValue("逾期描述"); 
		    
	    	row.createCell(9).setCellValue("五级分类"); 
	    	
	    	row.createCell(10).setCellValue("应还日"); 
	    	row.createCell(11).setCellValue("是否取消分期期次"); 
		    
	    	row.createCell(12).setCellValue("资产所属方"); 
	    	
	    	row.createCell(13).setCellValue("保证方"); 
	    	
	    	row.createCell(14).setCellValue("预提本金"); 
	    	row.createCell(15).setCellValue("预提利息"); 
	    	
	    	row.createCell(16).setCellValue("预提客户服务费"); 
	    	row.createCell(17).setCellValue("预提财务管理费"); 
	    	row.createCell(18).setCellValue("预提增值服务费"); 
	    	row.createCell(19).setCellValue("预提随心还服务费"); 
		    
	    	row.createCell(20).setCellValue("合计（此表单金额合计）"); 
		    
		    //第一行第20列  
			for (int i = 0; i < list.size(); i++) {  
				//sh.setColumnWidth(i,5000);
					Row inrow = sh.createRow(i+1);
				    
					inrow.createCell(0).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getInaccountdate()));
					inrow.createCell(1).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getDuedate())); 
					
					inrow.createCell(2).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getSuretype()));  
					inrow.createCell(3).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getBusinessmodel()));  
					
					inrow.createCell(4).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getSubproducttype()));  
					
					inrow.createCell(5).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getCity()));  
					inrow.createCell(6).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getCitycode()));  
					
					inrow.createCell(7).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getOverduedays()));  
					
					inrow.createCell(8).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getDescribe()));  
					inrow.createCell(9).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getClassfy()));
					
					inrow.createCell(10).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getPaydate())); 
					inrow.createCell(11).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getCanceltype())); 
					
					inrow.createCell(12).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getBelong())); 
					
					inrow.createCell(13).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getGuaranteeparty()));  
					inrow.createCell(14).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getPayprincipalamt()));  
					inrow.createCell(15).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getPayinteamt())); 
					inrow.createCell(16).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getA2()));  
					inrow.createCell(17).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getA7()));  
					inrow.createCell(18).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getA12())); 
					inrow.createCell(19).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getA18()));  
					inrow.createCell(20).setCellValue(StringUtil.isNullOrEmpty(list.get(i).getAmount()));  
					
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
			ajaxJson.setMsg("数据导出成功!");
	    } catch (IOException e) {  
	    	ajaxJson.setMsg("数据导出失败,请稍后重试!");
	        e.printStackTrace();  
	        System.out.println("Output   is   closed ");  
	    }
	    
	    return ajaxJson;
	}
	
	
	/**
	 * @Description:导出检测是否有数据
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/excelCheckSum")
	public AjaxJson excelCheckSum(PredictcostVO queryVo,HttpServletRequest request, HttpServletResponse response){
		AjaxJson ajaxJson = new AjaxJson();
		//统计合同总数
		String tempAssert = queryVo.getBelong();
		String belong="";
		if(tempAssert!=null){
			try {
				belong = java.net.URLDecoder.decode(tempAssert, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				belong="";
			} 
		}
		queryVo.setBelong(belong);
		
		String tempSub = queryVo.getSubproducttype();
		String subproducttype="";
		if(tempSub!=null){
			try {
				subproducttype = java.net.URLDecoder.decode(tempSub, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				subproducttype="";
			} 
		}
		queryVo.setSubproducttype(subproducttype);
		
		String tempBus = queryVo.getBusinessmodel();
		String businessmodel="";
		if(tempBus!=null){
			try {
				businessmodel = java.net.URLDecoder.decode(tempBus, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				businessmodel="";
			} 
		}
		queryVo.setBusinessmodel(businessmodel);
		
		String tempCre = queryVo.getCreditperson();
		String creditperson="";
		if(tempCre!=null){
			try {
				creditperson = java.net.URLDecoder.decode(tempCre, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				creditperson="";
			} 
		}
		queryVo.setCreditperson(creditperson);
		
		String tempGua = queryVo.getGuaranteeparty();
		String guaranteeparty="";
		if(tempGua!=null){
			try {
				guaranteeparty = java.net.URLDecoder.decode(tempGua, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				guaranteeparty="";
			} 
		}
		queryVo.setGuaranteeparty(guaranteeparty);
		
		String tempCity = queryVo.getCity();
		String city="";
		if(tempCity!=null){
			try {
				city = java.net.URLDecoder.decode(tempCity, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				city="";
			} 
		}
		queryVo.setCity(city);
		
		String tempCancel = queryVo.getCanceltype();
		String canceltype="";
		if(tempCancel!=null){
			try {
				canceltype = java.net.URLDecoder.decode(tempCancel, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				canceltype="";
			} 
		}
		queryVo.setCanceltype(canceltype);
		
		Map<String, Object> map = predictcostServer.queryPredictcostSumTotal(queryVo);
		if(map.isEmpty()){
			ajaxJson.setSuccess(false);
			ajaxJson.setMsg("暂无数据可导出!");
			return ajaxJson;
		}else{
			if(map.get("CONTRACTCOUNT") != null&&Long.parseLong(map.get("CONTRACTCOUNT").toString()) >0){
				if(Long.parseLong(map.get("CONTRACTCOUNT").toString())>300000){
					ajaxJson.setSuccess(false);
					ajaxJson.setMsg("导出数量超过30万，请重新筛选!");
					return ajaxJson;
				}else{
					ajaxJson.setSuccess(true);
					return ajaxJson;					
				}
			}else{
				ajaxJson.setSuccess(false);
				ajaxJson.setMsg("暂无数据可导出!");
				return ajaxJson;
			}
		}
	}
	
	/**
	 * @Description:更新入账信息
	 * @param vo
	 * @param request
	 * @param response
	 */
	@RequestMapping("/updatePredictcost")
	public void updatePredictcost(PredictcostVO vo, HttpServletRequest request,
			HttpServletResponse response) {
		try {


			if (vo==null) {
				ResponseUtil.sendMessage(response, false, "参数数据出错");
				return;
			}
			
			vo = changePredictcostVO(vo);

			String currentUser = CurrentUser.getUser().getUsername();
			vo.setInaccountby(currentUser);
			
			Boolean flag = predictcostServer.updatePredictcost(vo);
			
			if(flag){
				ResponseUtil.sendMessage(response, true, "更新成功");
			}else{
				ResponseUtil.sendMessage(response, false, "更新失败");
			}

		} catch (Exception e) {
			LOGGER.error("当期到期表入账失败", e);
			ResponseUtil.sendMessage(response, false, "更新失败");
		}
	}
	
	
	/**
	 * @Description:检测数据是否已入账
	 * @param vo
	 * @param request
	 * @param response
	 */
	@RequestMapping("/checkPredictcost")
	public void checkPredictcost(PredictcostVO vo, HttpServletRequest request,
			HttpServletResponse response) {
		try {


			if (vo==null) {
				ResponseUtil.sendMessage(response, false, "参数数据出错");
				return;
			}

			String currentUser = CurrentUser.getUser().getUsername();
			vo.setInaccountby(currentUser);
			
			Integer count = predictcostServer.checkPredictcost(vo);
			if(count!=null){
				if(count>0){
					ResponseUtil.sendMessage(response, false, "所选合同包含已记账合同");
				}else{
					ResponseUtil.sendMessage(response, true, "通过");
				}
			}else{
				ResponseUtil.sendMessage(response, false, "检查出错");
			}

			
		} catch (Exception e) {
			LOGGER.error("检测是否已记账出错", e);
			ResponseUtil.sendMessage(response, false, "系统出错");
		}
	}
	
	
	/**
	 * @Description:检测数据是否已入账
	 * @param vo
	 * @param request
	 * @param response
	 */
	@RequestMapping("/checkCancelPredictcost")
	public void checkCancelPredictcost(PredictcostVO vo, HttpServletRequest request,
			HttpServletResponse response) {
		try {


			if (vo==null) {
				ResponseUtil.sendMessage(response, false, "参数数据出错");
				return;
			}

			String currentUser = CurrentUser.getUser().getUsername();
			vo.setInaccountby(currentUser);
			
			Integer count = predictcostServer.checkCancelPredictcost(vo);
			if(count!=null){
				if(count>0){
					ResponseUtil.sendMessage(response, false, "所选合同包含未记账合同或已撤销合同");
				}else{
					ResponseUtil.sendMessage(response, true, "通过");
				}
			}else{
				ResponseUtil.sendMessage(response, false, "检查出错");
			}

			
		} catch (Exception e) {
			LOGGER.error("检测是否已记账出错", e);
			ResponseUtil.sendMessage(response, false, "系统出错");
		}
	}
	
	/**
	 * @Description:更新入账信息
	 * @param vo
	 * @param request
	 * @param response
	 */
	@RequestMapping("/cancelPredictcost")
	public void cancelPredictcost(PredictcostVO vo, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			
			Boolean roleFlag = (Boolean) request.getSession().getAttribute("isRevokeRole");
			if(roleFlag==null||!roleFlag){
				ResponseUtil.sendMessage(response, false, "没有该功能的操作权限");
				return;
			}
			
			
			if (vo==null) {
				ResponseUtil.sendMessage(response, false, "参数数据出错");
				return;
			}
			
			vo = changePredictcostVO(vo);

			String currentUser = CurrentUser.getUser().getUsername();
			vo.setInaccountby(currentUser);
			
			Boolean flag = predictcostServer.cancelPredictcost(vo);
			
			if(flag){
				ResponseUtil.sendMessage(response, true, "撤销成功");
			}else{
				ResponseUtil.sendMessage(response, false, "撤销失败");
			}

		} catch (Exception e) {
			LOGGER.error("当期到期表入账失败", e);
			ResponseUtil.sendMessage(response, false, "撤销失败");
		}
	}
	
	
	
	
	private PredictcostVO changePredictcostVO(PredictcostVO vo){
		/**
		 * contains : true  记账多个合同
		 * contains : false 记账单个合同
		 */
		if(StringUtil.isNullOrEmpty(vo.getSeqid()).contains(";")){
			vo.setSeqidArray(vo.getSeqid().replace(",", "").split(";"));
		}else{
			vo.setSeqidArray(new String[]{vo.getSeqid()});
		}
		if(StringUtil.isNullOrEmpty(vo.getContractno()).contains(";")){
			vo.setContractnoArray(vo.getContractno().replace(",", "").split(";"));
		}else{
			vo.setContractnoArray(new String[]{vo.getContractno()});
		}
		if(StringUtil.isNullOrEmpty(vo.getBelong()).contains(";")){
			vo.setBelongArray(vo.getBelong().replace(",", "").split(";"));
		}else{
			vo.setBelongArray(new String[]{vo.getBelong()});
		}
		if(StringUtil.isNullOrEmpty(vo.getPayprincipalamtStr()).contains(";")){
			vo.setPayprincipalamtArray(vo.getPayprincipalamtStr().replace(",", "").split(";"));
		}else{
			vo.setPayprincipalamtArray(new String[]{vo.getPayprincipalamtStr()});
		}
		if(StringUtil.isNullOrEmpty(vo.getPayinteamtStr()).contains(";")){
			vo.setPayinteamtArray(vo.getPayinteamtStr().replace(",", "").split(";"));
		}else{
			vo.setPayinteamtArray(new String[]{vo.getPayinteamtStr()});
		}
		vo.setSeqid("");
		vo.setPayprincipalamtStr("");
		vo.setPayinteamtStr("");
		return vo;
	}
}
