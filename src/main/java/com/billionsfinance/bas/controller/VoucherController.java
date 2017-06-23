package com.billionsfinance.bas.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.billionsfinance.bas.dao.CwVoucherModelInfoMapper;
import com.billionsfinance.bas.dao.CwVoucherModelMapper;
import com.billionsfinance.bas.entity.voucher.model.CwVoucherModel;
import com.billionsfinance.bas.entity.voucher.model.CwVoucherModelExample;
import com.billionsfinance.bas.entity.voucher.model.CwVoucherModelInfo;
import com.billionsfinance.bas.entity.voucher.model.CwVoucherModelInfoExample;
import com.billionsfinance.bas.entity.voucher.vo.VoucherInfoVo;
import com.billionsfinance.bas.server.ICwVoucherModelInfoServer;
import com.billionsfinance.bas.util.AjaxJson;
import com.billionsfinance.bas.util.SingletonScrollBar;

/**
 * @ClassName: VoucherController.java
 * @Description: 凭证功能导出
 * @author lin.tang
 * @date 2017年5月31日 下午4:33:44
 * Copyright: Copyright (c) 2017-2050 Company:BQJR
 */
@Controller
@RequestMapping("/voucher")
public class VoucherController {
	private static final Logger logger=LoggerFactory.getLogger(VoucherController.class);
	
	@Autowired
	private CwVoucherModelInfoMapper cwVoucherModelInfoMapper;
	@Autowired
	private CwVoucherModelMapper cwVoucherModelMapper;
	@Autowired
	private ICwVoucherModelInfoServer cwVoucherModelInfoServer;
	@RequestMapping("/voucher")
	public String voucher(HttpServletRequest request, HttpServletResponse response) {
		return "voucher/voucher";
	}
	@RequestMapping("/voucherModer")
	public String voucherModer(HttpServletRequest request, HttpServletResponse response) {
		return "voucher/voucherModer";
	}
	@ResponseBody
	@RequestMapping("/voucherScrollBar")
	public AjaxJson voucherScrollBar() {
		AjaxJson ajaxJson = new AjaxJson();
		SingletonScrollBar singleton = SingletonScrollBar.getInstance();
		ajaxJson.setObj(singleton);
		if (singleton.isFlag()) {
			singleton.setCount(0);
			singleton.setTemp(0);
			singleton.setPercentage(0.00);
			singleton.setNode("");
			singleton.setFlag(false);
			ajaxJson.setObj(true);
		}
		return ajaxJson;
	}
	@ResponseBody
	@RequestMapping("/exportVoucher")
	public AjaxJson exportVoucher(HttpServletRequest request,
			HttpServletResponse response,String[] vouchers) {
		AjaxJson ajaxJson = new AjaxJson();
		logger.info("导出凭证的id为：{}",vouchers);
		String[] titles = new String[] { "公司", "记账日期", "业务日期",
				"会计期间","凭证类型","凭证号","分录号","摘要","科目","币种","汇率",
				"方向","原币金额","数量","单价","借方金额","贷方金额",
				"制单人","过账人","审核人","附件数量","过账标记","机制凭证",
				"删除标记","凭证序号","单位","参考信息","是否有现金流量","现金流量标记",
				"业务编号","结算方式","结算号","辅助账摘要","核算项目1","编码1","名称1"
				,"核算项目2","编码2","名称2","核算项目3","编码3","名称3","核算项目4","编码4","名称4"
				,"核算项目5","编码5","名称5","核算项目6","编码6","名称6","核算项目7","编码7","名称7"
				,"核算项目8","编码8","名称8","发票号","换票证号","客户","费用类别","收款人","物料"
				,"财务组织","供应商","辅助账业务日期","到期日"};
		SingletonScrollBar singletonScrollBar=SingletonScrollBar.getInstance();
		try {
			singletonScrollBar.setTasks(vouchers.length);
			for (int i = 0; i < vouchers.length; i++) {
				singletonScrollBar.setCurrentTask(i+1);
				singletonScrollBar.setPercentage(0.00);
				
				List<VoucherInfoVo> voucherInfoVos = cwVoucherModelInfoServer
						.calculationVoucher(vouchers[i],singletonScrollBar);
				buildExcel(voucherInfoVos, titles, singletonScrollBar.getNode(), response,i,vouchers.length);
			}
			singletonScrollBar.setFlag(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ajaxJson;
	}
	
	private void buildExcel(List<VoucherInfoVo> voucherInfoVos,
			String[] titles, String fileName, HttpServletResponse response,int current,int total) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet  sheet = wb.createSheet();
		wb.setSheetName(0, fileName);
		HSSFRow  row0 = sheet.createRow(0);
		HSSFCellStyle  style1 = wb.createCellStyle();
		HSSFFont font1 = wb.createFont();
		font1.setFontHeightInPoints((short)12);
		font1.setFontName("宋体");
		style1.setFont(font1);
		style1.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		for (int i = 0; i < titles.length; i++) {
			//设置表头的字体样式
			sheet.setColumnWidth(i, 21 * 128);
			row0.createCell(i).setCellValue(titles[i]);
			row0.getCell(i).setCellStyle(style1);
			row0.setHeight((short)(19.95*14.25));
		}
		//设置正文的字体样式
		HSSFCellStyle style = wb.createCellStyle();
		HSSFFont font=wb.createFont();
		font.setFontHeightInPoints((short)10);
		font.setFontName("Arial");
		style.setFont(font);
		if (voucherInfoVos != null && voucherInfoVos.size() > 0) {
			for (int i = 0; i < voucherInfoVos.size(); i++) {
				VoucherInfoVo vo = voucherInfoVos.get(i);
				HSSFRow row = sheet.createRow(i + 1);
				row.createCell(0).setCellValue(vo.getCompany()); 
				row.getCell(0).setCellStyle(style);
				row.createCell(1).setCellValue(vo.getAccountDate()); 
				row.getCell(1).setCellStyle(style);
				row.createCell(2).setCellValue(vo.getBusinessDate()); 
				row.getCell(2).setCellStyle(style);
				row.createCell(3).setCellValue(vo.getAccountingGap()); 
				row.getCell(3).setCellStyle(style);
				row.createCell(4).setCellValue(vo.getVoucherType()); 
				row.getCell(4).setCellStyle(style);
				row.createCell(5).setCellValue(vo.getVoucherNo());
				row.getCell(5).setCellStyle(style);
				row.createCell(6).setCellValue(vo.getEntryNo()); 
				row.getCell(6).setCellStyle(style);
				row.createCell(7).setCellValue(vo.getAbstractInfo()); 
				row.getCell(7).setCellStyle(style);
				row.createCell(8).setCellValue(vo.getSubjectInfo()); 
				row.getCell(8).setCellStyle(style);
				row.createCell(9).setCellValue(vo.getCurrency());
				row.getCell(9).setCellStyle(style);
				row.createCell(10).setCellValue(vo.getExchangeRate()); 
				row.getCell(10).setCellStyle(style);
				row.createCell(11).setCellValue(Integer.valueOf(vo.getDirection())); 
				row.getCell(11).setCellStyle(style);
				if (vo.getTotalFee()==null||("").equals(vo.getTotalFee())) {
					row.createCell(12).setCellValue(vo.getTotalFee()); 
				}else {
					row.createCell(12).setCellValue(Double.valueOf(vo.getTotalFee())); 
				}
				row.getCell(12).setCellStyle(style);
				row.createCell(13).setCellValue(Integer.valueOf(vo.getTotal())); 
				row.getCell(13).setCellStyle(style);
				row.createCell(14).setCellValue(Integer.valueOf(vo.getSinglePrice())); 
				row.getCell(14).setCellStyle(style);
				if (vo.getDebtorMoney()==null||("").equals(vo.getDebtorMoney())) {
					row.createCell(15).setCellValue(vo.getDebtorMoney()); 
				}else {
					row.createCell(15).setCellValue(Double.valueOf(vo.getDebtorMoney())); 
				}
				row.getCell(15).setCellStyle(style);
				if (vo.getLenderMoney()==null||("").equals(vo.getLenderMoney())) {
					row.createCell(16).setCellValue(vo.getLenderMoney()); 
				}else {
					row.createCell(16).setCellValue(Double.valueOf(vo.getLenderMoney())); 
				}
				row.getCell(16).setCellStyle(style);
				row.createCell(17).setCellValue(vo.getOriginator()); 
				row.getCell(17).setCellStyle(style);
				row.createCell(18).setCellValue(vo.getPostinger()); 
				row.getCell(18).setCellStyle(style);
				row.createCell(19).setCellValue(vo.getAuditor()); 
				row.getCell(19).setCellStyle(style);
				row.createCell(20).setCellValue(Integer.valueOf(vo.getEnclosureTotal())); 
				row.getCell(20).setCellStyle(style);
				row.createCell(21).setCellValue(vo.getPostingFlag()); 
				row.getCell(21).setCellStyle(style);
				row.createCell(22).setCellValue(vo.getMechanismCredential()); 
				row.getCell(22).setCellStyle(style);
				row.createCell(23).setCellValue(vo.getDeleteFlag()); 
				row.getCell(23).setCellStyle(style);
				row.createCell(24).setCellValue(vo.getVoucherNum()); 
				row.getCell(24).setCellStyle(style);
				row.createCell(25).setCellValue(vo.getCompanyNum()); 
				row.getCell(25).setCellStyle(style);
				row.createCell(26).setCellValue(vo.getReferInfo()); 
				row.getCell(26).setCellStyle(style);
				row.createCell(27).setCellValue(vo.getIsCashFlow()); 
				row.getCell(27).setCellStyle(style);
				row.createCell(28).setCellValue(Integer.valueOf(vo.getCashFlowFlag())); 
				row.getCell(28).setCellStyle(style);
				row.createCell(29).setCellValue(vo.getBusinessNum());
				row.getCell(29).setCellStyle(style);
				row.createCell(30).setCellValue(vo.getSettlementType()); 
				row.getCell(30).setCellStyle(style);
				row.createCell(31).setCellValue(vo.getSettlementNum()); 
				row.getCell(31).setCellStyle(style);
				row.createCell(32).setCellValue(vo.getAuxiliaryAbstractInfo()); 
				row.getCell(32).setCellStyle(style);
				row.createCell(33).setCellValue(vo.getBusinessAccounting1()); 
				row.getCell(33).setCellStyle(style);
				row.createCell(34).setCellValue(vo.getCode1());
				row.getCell(34).setCellStyle(style);
				row.createCell(35).setCellValue(vo.getName1()); 
				row.getCell(35).setCellStyle(style);
				row.createCell(36).setCellValue(vo.getBusinessAccounting2()); 
				row.getCell(36).setCellStyle(style);
				row.createCell(37).setCellValue(vo.getCode2()); 
				row.getCell(37).setCellStyle(style);
				row.createCell(38).setCellValue(vo.getName2()); 
				row.getCell(38).setCellStyle(style);
				row.createCell(39).setCellValue(vo.getBusinessAccounting3()); 
				row.getCell(39).setCellStyle(style);
				row.createCell(40).setCellValue(vo.getCode3()); 
				row.getCell(40).setCellStyle(style);
				row.createCell(41).setCellValue(vo.getName3()); 
				row.getCell(41).setCellStyle(style);
				row.createCell(42).setCellValue(vo.getBusinessAccounting4()); 
				row.getCell(42).setCellStyle(style);
				row.createCell(43).setCellValue(vo.getCode4()); 
				row.getCell(43).setCellStyle(style);
				row.createCell(44).setCellValue(vo.getName4()); 
				row.getCell(44).setCellStyle(style);
				row.createCell(45).setCellValue(vo.getBusinessAccounting5()); 
				row.getCell(45).setCellStyle(style);
				row.createCell(46).setCellValue(vo.getCode5()); 
				row.getCell(46).setCellStyle(style);
				row.createCell(47).setCellValue(vo.getName5()); 
				row.getCell(47).setCellStyle(style);
				row.createCell(48).setCellValue(vo.getBusinessAccounting6()); 
				row.getCell(48).setCellStyle(style);
				row.createCell(49).setCellValue(vo.getCode6()); 
				row.getCell(49).setCellStyle(style);
				row.createCell(50).setCellValue(vo.getName6()); 
				row.getCell(50).setCellStyle(style);
				row.createCell(51).setCellValue(vo.getBusinessAccounting7()); 
				row.getCell(51).setCellStyle(style);
				row.createCell(52).setCellValue(vo.getCode7()); 
				row.getCell(52).setCellStyle(style);
				row.createCell(53).setCellValue(vo.getName7()); 
				row.getCell(53).setCellStyle(style);
				row.createCell(54).setCellValue(vo.getBusinessAccounting8()); 
				row.getCell(54).setCellStyle(style);
				row.createCell(55).setCellValue(vo.getCode8()); 
				row.getCell(55).setCellStyle(style);
				row.createCell(56).setCellValue(vo.getName8()); 
				row.getCell(56).setCellStyle(style);
				row.createCell(57).setCellValue(vo.getInvoiceNum()); 
				row.getCell(57).setCellStyle(style);
				row.createCell(58).setCellValue(vo.getExchangeInvoiceNum()); 
				row.getCell(58).setCellStyle(style);
				row.createCell(59).setCellValue(vo.getCustomer()); 
				row.getCell(59).setCellStyle(style);
				row.createCell(60).setCellValue(vo.getFeeType()); 
				row.getCell(60).setCellStyle(style);
				row.createCell(61).setCellValue(vo.getPayee()); 
				row.getCell(61).setCellStyle(style);
				row.createCell(62).setCellValue(vo.getMateriel()); 
				row.getCell(62).setCellStyle(style);
				row.createCell(63).setCellValue(vo.getFinancialOrganization()); 
				row.getCell(63).setCellStyle(style);
				row.createCell(64).setCellValue(vo.getSupplier()); 
				row.getCell(64).setCellStyle(style);
				row.createCell(65).setCellValue(vo.getAuxiliaryAccountingDate()); 
				row.getCell(65).setCellStyle(style);
				row.createCell(66).setCellValue(vo.getExpireDate()); 
				row.getCell(66).setCellStyle(style);
			}
			
		}
		try {
			fileName = new String(fileName.getBytes("utf-8"), "ISO8859-1")+ ".xls";
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=utf-8");
		response.setHeader("Content-Disposition", "attachment; filename="+ fileName);
		
		BufferedOutputStream bufferedOutPut = null;
		try {
			
			response.setContentType("application/vnd.ms-excel");  
			response.setHeader("Pragma", "no-cache");  
			response.setHeader("Cache-Control", "no-cache");  
			response.setDateHeader("Expires", 0);  
			OutputStream output = response.getOutputStream();  
			bufferedOutPut = new BufferedOutputStream(output);  
			wb.write(bufferedOutPut);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bufferedOutPut != null) {
				try {
					bufferedOutPut.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (current+1==total) {//任务完成才关闭整个流
					try {
						bufferedOutPut.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	@ResponseBody
	@RequestMapping("/importVoucher")
	public AjaxJson importVoucher(HttpServletRequest request,@RequestParam(value = "file", required = false) MultipartFile file) {
		AjaxJson ajaxJson = new AjaxJson();
		try {
//			InputStream isInputStream=new FileInputStream("C:\\Users\\lin.tang\\Desktop\\TO IT-主数据分组汇总表（实还）0531.xlsx");
			XSSFWorkbook workbook=new XSSFWorkbook(file.getInputStream());
			 for (int i = 0; i < workbook.getNumberOfSheets(); i++) { 
				Sheet sheet=workbook.getSheetAt(i);
				//每一个sheet对应一个模板
				String modelNo=sheet.getSheetName().split("_")[1];
				String modelName=sheet.getSheetName().split("_")[0];
				CwVoucherModel cwVoucherModel=new CwVoucherModel();
				cwVoucherModel.setModelName(modelName);
				cwVoucherModel.setModelNo(modelNo);
				//删除原来的模板
				CwVoucherModelExample example1=new CwVoucherModelExample();
				example1.createCriteria().andModelNoEqualTo(modelNo);
				cwVoucherModelMapper.deleteByExample(example1);
				
				//插入模板
				cwVoucherModelMapper.insertRecord(cwVoucherModel);
				
				//删除原来的模板信息
				CwVoucherModelInfoExample example=new CwVoucherModelInfoExample();
				example.createCriteria().andModelNoEqualTo(modelNo);
				cwVoucherModelInfoMapper.deleteByExample(example);
				
				for (int j = 3; j < sheet.getLastRowNum(); j++) {
					CwVoucherModelInfo cwVoucherModelInfo=new CwVoucherModelInfo();
					Row Row=sheet.getRow(j);
					int mincell=Row.getFirstCellNum();
					int maxcell=Row.getLastCellNum();
					cwVoucherModelInfo.setModelNo(modelNo);
					for (int k = mincell; k <maxcell; k++) {
						Cell Cell=Row.getCell(k);
						switch (k) {
						case 0:
							cwVoucherModelInfo.setBusinessmodel(getStringVal(Cell));
							break;
						case 1:
							cwVoucherModelInfo.setCity(getStringVal(Cell));
							break;
						case 2:
							cwVoucherModelInfo.setCanceltype(getStringVal(Cell));
							break;
						case 3:
							cwVoucherModelInfo.setAssetbelong(getStringVal(Cell));
							break;
						case 4:
							cwVoucherModelInfo.setGuaranteeparty(getStringVal(Cell));
							break;
//						case 5:
//							cwVoucherModelInfo.setRelation(getStringVal(Cell));
//							break;
						case 5:
							cwVoucherModelInfo.setSponsor(getStringVal(Cell));
							break;
						case 6:
							cwVoucherModelInfo.setSponsortype(getStringVal(Cell));
							break;
						case 7:
							cwVoucherModelInfo.setIsInstalmentsEnd(getStringVal(Cell));
							break;
						case 8:
							cwVoucherModelInfo.setBadDebtDate(getStringVal(Cell));
							break;
						case 9:
							cwVoucherModelInfo.setCalculatedAmountField(getStringVal(Cell));
							break;
						case 10:
							cwVoucherModelInfo.setAccountNum(getStringVal(Cell));
							break;
						case 11:
							cwVoucherModelInfo.setAccountDir(getStringVal(Cell));
							break;
						case 12:
							cwVoucherModelInfo.setSubjectNum(getStringVal(Cell));
							break;
						case 13:
							cwVoucherModelInfo.setAccountingSub(getStringVal(Cell));
							break;
						case 14:
							cwVoucherModelInfo.setAccountingAux1(getStringVal(Cell));
							break;
						case 15:
							cwVoucherModelInfo.setAccountingAux2(getStringVal(Cell));
							break;
						case 16:
							cwVoucherModelInfo.setAccountingAux3(getStringVal(Cell));
							break;
						case 17:
							cwVoucherModelInfo.setAccountingAbs(getStringVal(Cell));
							break;
						default:
							break;
						}
					}
					//插入每一条记录
					cwVoucherModelInfoMapper.insertRecord(cwVoucherModelInfo);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ajaxJson;
	}
	@ResponseBody
	@RequestMapping("/getAllVoucherModels")
	public List<CwVoucherModel> getAllVoucherModels(){
		AjaxJson ajaxJson=new AjaxJson();
		CwVoucherModelExample example=new CwVoucherModelExample();
		example.createCriteria().andCreateTimeIsNotNull();
		example.setOrderByClause("id asc");
		List<CwVoucherModel> list=cwVoucherModelMapper.selectByExample(example);
		ajaxJson.setObj(list);
		return list;
	}
	private static String getStringVal(Cell cell){
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue()?"TURE":"FALSE".trim();
		case Cell.CELL_TYPE_FORMULA:
			return cell.getCellFormula().trim();
		case Cell.CELL_TYPE_NUMERIC:
			cell.setCellType(cell.CELL_TYPE_STRING);
			return cell.getStringCellValue().trim();
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue().trim();
		default:
			return "";
		}
	}
}


