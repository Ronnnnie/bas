package com.billionsfinance.bas.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.billionsfinance.bas.entity.ContractVO;
import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.server.IContractServer;
import com.billionsfinance.bas.server.impl.ContractServer;
import com.billionsfinance.bas.util.AjaxJson;
import com.billionsfinance.bas.util.ExcelUtils;
import com.billionsfinance.bas.util.FileOperateUtil;
import com.billionsfinance.bas.util.LogUtil;
import com.billionsfinance.bas.util.ResponseUtil;
import com.billionsfinance.bas.util.UUIDGenerator;

/**
 * @ClassName: ContractController
 * @Description: 合同控制器
 * @author zhouFM
 * 
 * Copyright: Copyright (c) 2016 2016年9月20日 下午16:05:09 Company:佰仟金融
 */
@Controller
@RequestMapping("/contractServer")
public class ContractController {

	/** 日志记录 */
	private static final Log LOGGER = LogFactory.getLog(ContractController.class);
	
	private static final IContractServer contractServer = new ContractServer();
	/**
	 * <p>
	 * Title: toBusiness
	 * </p>
	 * <p>
	 * Description: 跳转到业务汇总页面
	 * 
	 * @return 跳转到业务汇总页面
	 */
	@RequestMapping("/toBusiness")
	public String toBusiness() {
		return "contract/businessGather";
	}
	/**
	 * <p>
	 * Title: toBusinessDetail
	 * </p>
	 * <p>
	 * Description: 跳转到业务明细页面
	 * 
	 * @return 跳转到业务明细页面
	 */
	@RequestMapping("/toBusinessDetail")
	public String tousinessDetail() {
		return "contract/businessDetail";
	}
	/**
	 * <p>
	 * Title: toBankrollDetail
	 * </p>
	 * <p>
	 * Description: 跳转到业务汇总页面
	 * 
	 * @return 跳转到资金汇总页面
	 */
	@RequestMapping("/toBankrollDetail")
	public String toBankrollDetail() {
		return "contract/bankrollDetail";
	}
	
	@RequestMapping("/queryBusinessDetail")
	public void queryBusinessDetail(Integer page, Integer rows, ContractVO brVO,HttpServletRequest request, HttpServletResponse response/*,BankRollVO brVo */) {
		
		PageVO pageVO = new PageVO();
		pageVO.setPageSize(rows);
		pageVO.setPageNo(page);
		Map<String, Object> whereMap = new HashMap<String, Object>();
		
		//SerialNo拆分
		if (brVO != null &&brVO.getSerialNo() != null&&!("").equals(brVO.getSerialNo())) {
			if ( brVO.getSerialNo().contains(" ")) {
				brVO.setArray(brVO.getSerialNo().split(" "));
				brVO.setSerialNo(null);
			}
			if ( brVO.getSerialNo().contains(",")) {
				brVO.setArray(brVO.getSerialNo().split(","));
				brVO.setSerialNo(null);
			}
		}
		//ID拆分
		if (brVO != null &&brVO.getId() != null&&!("").equals(brVO.getId())) {
			if ( brVO.getId().contains(",")) {
				brVO.setIdArray(brVO.getId().split(","));
				brVO.setId(null);
			}
		}
		
		if (brVO != null) {
			whereMap.put("id",brVO.getId());
			whereMap.put("idArray",brVO.getIdArray());
			whereMap.put("serialNo",brVO.getSerialNo());
			whereMap.put("array",brVO.getArray());
			whereMap.put("startBusinessDate",brVO.getStartBusinessDate());
			whereMap.put("endBusinessDate",brVO.getEndBusinessDate());
			whereMap.put("startKeepAccountsDate",brVO.getStartKeepAccountsDate());
			whereMap.put("endKeepAccountsDate",brVO.getEndKeepAccountsDate());
			whereMap.put("startMakeCollectionsDate",brVO.getStartMakeCollectionsDate());
			whereMap.put("endMakeCollectionsDate",brVO.getEndMakeCollectionsDate());
			whereMap.put("capitalSide",brVO.getCapitalSide());
			whereMap.put("productSubType",brVO.getProductSubType());
			whereMap.put("city",brVO.getCity());
		}
		
		Map<String,Object> brVO2 = contractServer.queryBusinessDetailCount(whereMap);
		pageVO = contractServer.queryBusinessDetail(pageVO,whereMap);
		
		if (brVO2 != null && brVO2.get("moneyCount") != null && brVO2.get("contractCount") != null) {
			pageVO.getRows().get(0).put("moneyCount", Double.parseDouble(brVO2.get("moneyCount").toString()));
			pageVO.getRows().get(0).put("contractCount",Long.parseLong(brVO2.get("contractCount").toString()));
		}
		ResponseUtil.sendJSON(response, pageVO);
	}
	
	@RequestMapping("/updateBusinessDetail")
	public void updateBusinessDetail(ContractVO brVO,HttpServletRequest request, HttpServletResponse response/*,BankRollVO brVo */) {
		contractServer.updateContract(brVO);
		ResponseUtil.sendString(response,brVO.getId());
	}
	
	@RequestMapping("/createContract")
	public void createContract(ContractVO brVO,HttpServletRequest request, HttpServletResponse response/*,BankRollVO brVo */) {
		brVO.setId(UUIDGenerator.getUUID());
		contractServer.createContract(brVO);
		ResponseUtil.sendString(response,brVO.getId());
	}
	
	/**
	 * <p>
	 * Title: checkContractUpload
	 * </p>
	 * <p>
	 * Description: 检查合同信息上传
	 * </p>
	 * 
	 * @param request
	 * @param file
	 * @return json
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/importContract")
	@ResponseBody
	public AjaxJson importContract(HttpServletRequest request,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		AjaxJson ajaxResponse = new AjaxJson();
		try {

			List<List<String>> dataList = new ArrayList<List<String>>();
			XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
			XSSFSheet sheet = workbook.getSheetAt(0);
			XSSFRow projectRow = sheet.getRow(0);
			XSSFRow projectRowValues = sheet.getRow(1);

			List<String> proList = ExcelUtils.validateColumn(projectRow); // 第一行数据
			if (proList == null) {
				LogUtil.MSG.info("项目名称有重复，请检查");
				ajaxResponse.setMsg("项目名称有重复，请检查");
				ajaxResponse.setSuccess(false);
				return ajaxResponse;
			}

			List<String> proValuesList = ExcelUtils.validateColumn(projectRowValues); // 第一行数据
			List<String> alis = validateContractCols(proList, proValuesList);
			if (alis == null || proValuesList == null) {
				LogUtil.MSG.info("导入字段名与别名不匹配，请检查");
				ajaxResponse.setMsg("导入字段名与别名不匹配，请检查");
				ajaxResponse.setSuccess(false);
				return ajaxResponse;
			}
			// 读取数据
			int cols = alis.size() - 1; // 有效列长度
			// 读取数据
			Map<String, Object> dataMap = ExcelUtils.loadDataList(sheet, cols);
			dataList = (List<List<String>>) dataMap.get("dataList");
			// values 解析出来
			List<ContractVO> list = new ArrayList<ContractVO>();
			StringBuffer buffer = new StringBuffer();
			for (int i = 2; i < dataList.size(); i++) {
				ContractVO bankRollVO = new ContractVO();
				List<String> array = dataList.get(i);
				//自动生成ID
				String generatorId = UUIDGenerator.getUUID();
				if (i == dataList.size()-1) {
					buffer.append(generatorId);
				}else{
					buffer.append(generatorId).append(",");
				}
				bankRollVO.setId(generatorId);
				bankRollVO.setSerialNo(array.get(1));
				bankRollVO.setBusinessDate(array.get(2));
				bankRollVO.setKeepAccountsDate(array.get(3));
				bankRollVO.setMakeCollectionsDate(array.get(4));
				bankRollVO.setProductSubType(array.get(5));
				bankRollVO.setBusinessSum(Integer.parseInt(array.get(6).toString()));
				bankRollVO.setCapitalSide(array.get(7));
				bankRollVO.setClientName(array.get(8));
				bankRollVO.setCity(array.get(9));
				//放款本金汇总
				list.add(bankRollVO);
			}
			HttpSession session = request.getSession();
			session.setAttribute("list", list);
			ajaxResponse.setMsg("解析成功!");
			ajaxResponse.setSuccess(true);
			ajaxResponse.setObj(list);
			return ajaxResponse;
		} catch (IOException e) {
			LOGGER.error("解析失败");
			ajaxResponse.setMsg("解析失败,请尝试重新导入!");
			ajaxResponse.setSuccess(false);
			return ajaxResponse;
		}
	}
	
	@ResponseBody
	@RequestMapping("/importContractSave")
	public void importContractSave(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		List<ContractVO> list = (List<ContractVO>) session.getAttribute("list");
		contractServer.importContract(list);
		String str = new String();
		for (int i = 0; i < list.size(); i++) {
			if (i == list.size()-1) {
				str+=list.get(i).getId();
				break;
			}
			str+=list.get(i).getId()+(",");
		}
		ResponseUtil.sendString(response, str);
	}
	
	/**
	 * 放款表帐务标记
	 * @param vo
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/accountingMark")
	public AjaxJson accountingMark(ContractVO vo,HttpServletRequest request, HttpServletResponse response) {
		AjaxJson ajaxResponse = new AjaxJson();
		//查询匹配合同
		List<Map<String,Object>> list = contractServer.queryBusinessDetailId(vo);
		StringBuffer buffer = new StringBuffer();
		if (list.size() == 0) {
			ajaxResponse.setMsg("匹配数据为空，标记失败!");
			ajaxResponse.setObj(null);
			return ajaxResponse;
		}
		String[] idArray = null;
		//记录ID 便于查询
		if(list.size() == 1){
			idArray = new String[1];
			idArray[0] = list.get(0).get("id").toString();
			buffer.append(list.get(0).get("id"));
		}else{
			idArray = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				idArray[i] = list.get(i).get("id").toString();
				if (i == list.size()-1) {
					buffer.append(list.get(i).get("id"));
				}else{
					buffer.append(list.get(i).get("id")).append(",");
				}
			}
		}
		
		vo.setIdArray(idArray);
		contractServer.accountingMark(vo);
		ajaxResponse.setSuccess(true);
		ajaxResponse.setMsg("账务标记成功!");
		ajaxResponse.setObj(buffer);
		return ajaxResponse;
	}
	
	/**
	 * 放款表付款确认
	 * @param vo
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/payConfirm")
	public AjaxJson payConfirm(ContractVO vo,HttpServletRequest request, HttpServletResponse response) {
		
		AjaxJson ajaxResponse = new AjaxJson();
		List<Map<String,Object>> list = contractServer.queryBusinessDetailId(vo);
		StringBuffer buffer = new StringBuffer();
		String[] idArray = null;
		if (list.size() == 0) {
			ajaxResponse.setMsg("匹配数据为空，标记失败!");
			ajaxResponse.setObj(null);
			return ajaxResponse;
		}
		if(list.size() == 1){
			idArray = new String[1];
			idArray[0] = list.get(0).get("id").toString();
			buffer.append(list.get(0).get("id"));
		}else{
			idArray = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				idArray[i] = list.get(i).get("id").toString();
				if (i == list.size()-1) {
					buffer.append(list.get(i).get("id"));
				}else{
					buffer.append(list.get(i).get("id")).append(",");
				}
			}
		}
		
		vo.setIdArray(idArray);
		contractServer.payConfirm(vo);
		ajaxResponse.setSuccess(true);
		ajaxResponse.setMsg("付款确认标记成功!");
		
		for (int i = 0; i < list.size(); i++) {
			if (!("").equals(list.get(i).get("keepAccountsDate")) && list.get(i).get("keepAccountsDate") != null) {
				ajaxResponse.setMsg("当前匹配合同中部分包含记账日期不为空的记录,该些合同修改失败!");
				ajaxResponse.setSuccess(false);
			}
		}
		ajaxResponse.setObj(buffer);
		return ajaxResponse;
	}
		
	private List<String> validateContractCols(List<String> proList, List<String> values) {
		
		for (int i = 0; i < proList.size(); i++) {
			if (proList.get(i).equals("合同号")) {
				if (!values.get(i).equals("SERIALNO")) {
					return null;
				}
			} else if (proList.get(i).equals("注册日")) {
				if (!values.get(i).equals("BUSINESSDATE")) {
					return null;
				}
			} else if (proList.get(i).equals("记账日")) {
				if (!values.get(i).equals("KEEPACCOUNTS_DATE")) {
					return null;
				}
			} else if (proList.get(i).equals("收款日")) {
				if (!values.get(i).equals("MAKECOLLECTIONS_DATE")) {
					return null;
				}
			} else if (proList.get(i).equals("产品子类型")) {
				if (!values.get(i).equals("PRODUCT_SUBTYPE")) {
					return null;
				}
			} else if (proList.get(i).equals("放款本金")) {
				if (!values.get(i).equals("BUSINESSSUM1")) {
					return null;
				}
			} else if (proList.get(i).equals("资金方")) {
				if (!values.get(i).equals("CAPITALSIDE")) {
					return null;
				}
			} else if (proList.get(i).equals("客户姓名")) {
				if (!values.get(i).equals("CLIENT_NAME")) {
					return null;
				}
			} else if (proList.get(i).equals("所属城市")) {
				if (!values.get(i).equals("CITY")) {
					return null;
				}
			}else{
				return null;
			}
		}
		if (!values.isEmpty()) {
			values.add(0, "ID");
		}
		return values;
	}
	
	/**
	 * 资金明细模板下载
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/exportExcel")
	@ResponseBody
	public AjaxJson exportExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson ajaxResponse = new AjaxJson();
		try {
			String storeName = "D:/workSpace/bqjr_bas/src/main/webapp/ContractExportExcel.xlsx";
			String realName = "ContractExportExcel.xlsx";
			FileOperateUtil.download(request, response, storeName, realName);
			ajaxResponse.setMsg("模板下载成功!");
			ajaxResponse.setSuccess(true);
			return ajaxResponse;
		} catch (IOException e) {
			LOGGER.error("模板下载失败");
			ajaxResponse.setMsg("模板下载失败!");
			ajaxResponse.setSuccess(false);
			return ajaxResponse;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("模板下载失败");
			ajaxResponse.setMsg("模板下载失败!");
			e.printStackTrace();
			return ajaxResponse;
		}
	}
	
}
