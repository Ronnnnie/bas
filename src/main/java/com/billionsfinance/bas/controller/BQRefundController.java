package com.billionsfinance.bas.controller;

import java.io.IOException;
import java.util.ArrayList;
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

import com.billionsfinance.bas.entity.BQRefundVO;
import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.server.IBQRefundServer;
import com.billionsfinance.bas.server.impl.BQRefundServer;
import com.billionsfinance.bas.util.AjaxJson;
import com.billionsfinance.bas.util.ExcelUtils;
import com.billionsfinance.bas.util.FileOperateUtil;
import com.billionsfinance.bas.util.LogUtil;
import com.billionsfinance.bas.util.ResponseUtil;
import com.billionsfinance.bas.util.UUIDGenerator;

/**
 * @ClassName: BQRefundController
 * @Description: 合同取消佰仟放款控制器
 * @author zhouFM
 * @Copyright Copyright (c) 2016 2016年10月26日 下午14:25:09 Company:佰仟金融
 */
@Controller
@RequestMapping("/bqRefundServer")
public class BQRefundController {

	/** 日志记录 */
	private static final Log LOGGER = LogFactory.getLog(BQRefundController.class);

	private static final IBQRefundServer bqRefundServer = new BQRefundServer();
	/**
	 * Description: 跳转业务汇总页面
	 */
	@RequestMapping("/toBusiness")
	public String toBusiness() {
		return "bqRefund/businessGather";
	}
	/**
	 * @Description: 跳转业务明细页面
	 */
	@RequestMapping("/toBusinessDetail")
	public String toBusinessDetail() {
		return "bqRefund/businessDetail";	
	}
	/**
	 * @Description: 跳转资金明细页面
	 */
	@RequestMapping("/toBankrollDetail")
	public String toBankrollDetail() {
		return "bqRefund/bankrollDetail";
	}
	
	/**
	 * @Description:业务明细查询(分页)
	 */
	@RequestMapping("/queryBusinessDetail")
	public void queryBusinessDetail(Integer page, Integer rows, BQRefundVO crVO,HttpServletRequest request, HttpServletResponse response) {
		
		PageVO pageVO = new PageVO();
		pageVO.setPageSize(rows);
		pageVO.setPageNo(page);

		pageVO = bqRefundServer.queryBusinessDetail(pageVO, crVO);
		ResponseUtil.sendJSON(response, pageVO);
	}
	
	/**
	 * @Description:合同数据修改
	 */
	@RequestMapping("/updateBusinessDetail")
	public void updateBusinessDetail(BQRefundVO vo,HttpServletRequest request, HttpServletResponse response/*,BQRefundVO vo */) {
		bqRefundServer.updateContract(vo);
		ResponseUtil.sendJSON(response, vo);
	}
	
	/**
	 * @Description:合同数据删除
	 */
	@RequestMapping("/deleteBusinessDetail")
	public void deleteBusinessDetail(BQRefundVO vo,HttpServletRequest request, HttpServletResponse response/*,BQRefundVO vo */) {
		bqRefundServer.deleteBusinessDetail(vo.getId());
		ResponseUtil.sendJSON(response, "Remove Successful!");
	}
	
	/**
	 * @Description:合同数据导入(解析)
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
			if (alis == null) {
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
			List<BQRefundVO> list = new ArrayList<BQRefundVO>();
			StringBuffer str = new StringBuffer();
			for (int i = 2; i < dataList.size(); i++) {
				List<String> array = dataList.get(i);
				BQRefundVO vo = new BQRefundVO();
				String generatorId = UUIDGenerator.getUUID();
				if (i == dataList.size()-1) {
					str.append(generatorId);
				}else{
					str.append(generatorId).append(",");
				}
				vo.setId(generatorId);
				vo.setSerialNo(array.get(1));
				vo.setBusinessDate(array.get(2));
				vo.setKeepAccountsDate(array.get(3));
				vo.setPayDate(array.get(4));
				vo.setMakeCollectionsDate(array.get(5));
				vo.setPeriods(Integer.parseInt(array.get(6).toString()));
				vo.setBusinessSum(Double.parseDouble(array.get(7).toString()));
				vo.setDeductionKHServiceFee(Double.parseDouble(array.get(8).toString()));
				vo.setDeductionSHServiceFee(Double.parseDouble(array.get(9).toString()));
				vo.setReturnSum(Double.parseDouble(array.get(10).toString()));
				vo.setProductSubType(array.get(11));
				vo.setCapitalSide(array.get(12));
				vo.setKhName(array.get(13));
				vo.setShName(array.get(14));
				vo.setShId(array.get(15));
				vo.setCity(array.get(16));
				vo.setContractStatus(array.get(17));
				list.add(vo);
			}
			HttpSession httpSession = request.getSession();
			httpSession.setAttribute("list", list);
			ajaxResponse.setMsg("解析成功!");
			ajaxResponse.setSuccess(true);
			ajaxResponse.setObj(list);
			return ajaxResponse;
		} catch (IOException e) {
			LOGGER.error("导入失败");
			ajaxResponse.setMsg("解析失败!");
			ajaxResponse.setSuccess(false);
			return ajaxResponse;
		}
	}
	
	/**
	 * @Description:合同数据导入(保存)
	 */
	@ResponseBody
	@RequestMapping("/importContractSave")
	public void importContractSave(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		List<BQRefundVO> list = (List<BQRefundVO>) session.getAttribute("list");
		bqRefundServer.importContract(list);
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
	 * 合同模板下载
	 */
	@RequestMapping("/exportExcel")
	@ResponseBody
	public AjaxJson exportExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson ajaxResponse = new AjaxJson();
		try {
			String storeName = "D:/workSpace/bqjr_bas/src/main/webapp/取消合同客户退款.xlsx";
			String realName = "取消合同客户退款模板.xlsx";
			FileOperateUtil.download(request, response, storeName, realName);
			ajaxResponse.setMsg("Export Successful!");
			ajaxResponse.setSuccess(true);
			return ajaxResponse;
		} catch (IOException e) {
			LOGGER.error("导出失败");
			ajaxResponse.setMsg("Export Befeated!");
			ajaxResponse.setSuccess(false);
			return ajaxResponse;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("导出失败");
			ajaxResponse.setMsg("Export Befeated!");
			e.printStackTrace();
			return ajaxResponse;
		}
	}
		

	
	/**
	 * @Description:帐务标记
	 */
	@ResponseBody
	@RequestMapping("/accountingMark")
	public AjaxJson accountingMark(BQRefundVO vo,HttpServletRequest request, HttpServletResponse response/*,BQRefundVO vo */) {
		AjaxJson ajaxResponse = new AjaxJson();
		//查询匹配合同
		List<Map<String,Object>> list = bqRefundServer.queryBusinessDetailId(vo);
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
		bqRefundServer.accountingMark(vo);
		ajaxResponse.setSuccess(true);
		ajaxResponse.setMsg("账务标记成功!");
		ajaxResponse.setObj(buffer);
		return ajaxResponse;
	}
	
	/**
	 * @Description:付款确认
	 */
	@ResponseBody
	@RequestMapping("/payConfirm")
	public AjaxJson payConfirm(BQRefundVO vo,HttpServletRequest request, HttpServletResponse response) {
		
		AjaxJson ajaxResponse = new AjaxJson();
		List<Map<String,Object>> list = bqRefundServer.queryBusinessDetailId(vo);
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
		bqRefundServer.payConfirm(vo);
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
	
	/**
	 * 字段校验
	 * @param proList
	 * @param values
	 * @return
	 */
	private List<String> validateContractCols(List<String> proList, List<String> values) {
		
		for (int i = 0; i < proList.size(); i++) {
			if (proList.get(i).equals("合同号")) {
				if (!values.get(i).equals("SERIALNO")) {
					return null;
				}
			} else if (proList.get(i).equals("期数")) {
				if (!values.get(i).equals("PERIODS")) {
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
			} else if (proList.get(i).equals("付款日")) {
				if (!values.get(i).equals("PAY_DATE")) {
					return null;
				}
			} else if (proList.get(i).equals("收款日")) {
				if (!values.get(i).equals("MAKECOLLECTIONS_DATE")) {
					return null;
				}
			} else if (proList.get(i).equals("贷款本金")) {
				if (!values.get(i).equals("BUSINESSSUM")) {
					return null;
				}
			} else if (proList.get(i).equals("扣减客户服务费")) {
				if (!values.get(i).equals("DEDUCTION_KH_SERVICE_FEE")) {
					return null;
				}
			} else if (proList.get(i).equals("扣减商户服务费")) {
				if (!values.get(i).equals("DEDUCTION_SH_SERVICE_FEE")) {
					return null;
				}
			} else if (proList.get(i).equals("退还金额")) {
				if (!values.get(i).equals("RETURN_SUM")) {
					return null;
				}
			} else if (proList.get(i).equals("产品子类型")) {
				if (!values.get(i).equals("PRODUCT_SUBTYPE")) {
					return null;
				}
			} else if (proList.get(i).equals("资金方")) {
				if (!values.get(i).equals("CAPITALSIDE")) {
					return null;
				}
			} else if (proList.get(i).equals("客户名称")) {
				if (!values.get(i).equals("KH_NAME")) {
					return null;
				}
			} else if (proList.get(i).equals("商户名称")) {
				if (!values.get(i).equals("SH_NAME")) {
					return null;
				}
			} else if (proList.get(i).equals("商户编号")) {
				if (!values.get(i).equals("SH_ID")) {
					return null;
				}
			} else if (proList.get(i).equals("所属城市")) {
				if (!values.get(i).equals("CITY")) {
					return null;
				}
			} else if (proList.get(i).equals("合同状态")) {
				if (!values.get(i).equals("CONTRACT_STATUS")) {
					return null;
				}
			}
		}
		if (!values.isEmpty()) {
			values.add(0, "ID");
		}
		return values;
	}
	
}
