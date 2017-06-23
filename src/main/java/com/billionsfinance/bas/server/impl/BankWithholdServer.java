package com.billionsfinance.bas.server.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.BankWithholdVO;
import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.server.IBankWithholdServer;
import com.billionsfinance.bas.service.BankWithholdService;
import com.billionsfinance.bas.util.CommonUtil;
import com.billionsfinance.bas.util.SpringService;
import com.billionsfinance.bas.util.StringUtil;
/**
 * 
 * @ClassName: BankWithholdServer.java
 * @Description: 每日银行代扣核对表Server类
 * @author Feima.zhou
 * 
 *         Copyright: Copyright (c) 2017年5月9日上午11:27:44 Company:佰仟金融
 */
public class BankWithholdServer implements IBankWithholdServer {

	private BankWithholdService bankWithholdService = SpringService.BANKWITHHOLDSERVER;
	
	@Override
	public PageVO queryBankWithhold(PageVO pageVO , BankWithholdVO vo) {

		Map<String, Object> whereMap = new HashMap<String, Object>();

		//组装条件

		if (vo != null) {
			whereMap.put("startInputDate",vo.getStartInputDate());
			whereMap.put("endInputDate",vo.getEndInputDate());
		}

		//统计合同总数
		
		Map<String,Object> brVO = bankWithholdService.queryBankWithholdCount(whereMap);
		
		Long total = Long.parseLong(brVO.get("contractcount").toString()) ;
		
		//封装分页参数
		Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
		whereMap.putAll(whereMapPage);
		
		//查询合同明细
		List<Map<String, Object>> map2 = bankWithholdService.queryBankWithhold(whereMap);
		pageVO.setRows(map2);
		
		if(pageVO.getRows().size()>0){
			//总匹配合同数 总匹配金额
			pageVO.getRows().get(0).put("moneyCount", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("moneyCount"))));
			pageVO.getRows().get(0).put("dep_bodsum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("dep_bodsum"))));
			pageVO.getRows().get(0).put("unmatch_bodsum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("unmatch_bodsum"))));
			pageVO.getRows().get(0).put("bank_banksum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("bank_banksum"))));
			pageVO.getRows().get(0).put("bank_coresum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("bank_coresum"))));
			pageVO.getRows().get(0).put("bank_difsum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("bank_difsum"))));
			pageVO.getRows().get(0).put("bank_matchsum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("bank_matchsum"))));
			pageVO.getRows().get(0).put("bank_unmatchsum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("bank_unmatchsum"))));
			pageVO.getRows().get(0).put("bank_roll_handsum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("bank_roll_handsum"))));
			pageVO.getRows().get(0).put("bank_match_handsum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("bank_match_handsum"))));
			pageVO.getRows().get(0).put("unmatch_eodsum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("unmatch_eodsum"))));
			pageVO.getRows().get(0).put("ebu_banksum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("ebu_banksum"))));
			pageVO.getRows().get(0).put("ebu_core1sum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("ebu_core1sum"))));
			pageVO.getRows().get(0).put("ebu_coresum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("ebu_coresum"))));
			pageVO.getRows().get(0).put("ebu_difsum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("ebu_difsum"))));
			pageVO.getRows().get(0).put("kft_bank", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("ebu_difsum"))));
			pageVO.getRows().get(0).put("kft_banksum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("kft_banksum"))));
			pageVO.getRows().get(0).put("kft_coresum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("kft_coresum"))));
			pageVO.getRows().get(0).put("kft_difsum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("kft_difsum"))));
			pageVO.getRows().get(0).put("hbk_banksum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("hbk_banksum"))));
			pageVO.getRows().get(0).put("hbk_coresum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("hbk_coresum"))));
			pageVO.getRows().get(0).put("hbk_difsum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("hbk_difsum"))));
			pageVO.getRows().get(0).put("kfts_banksum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("kfts_banksum"))));
			pageVO.getRows().get(0).put("kfts_coresum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("kfts_coresum"))));
			pageVO.getRows().get(0).put("kfts_difsum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("kfts_difsum"))));
			pageVO.getRows().get(0).put("cft_banksum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("cft_banksum"))));
			pageVO.getRows().get(0).put("check_cft_coresum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("check_cft_coresum"))));
			pageVO.getRows().get(0).put("cft_coresum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("cft_coresum"))));
			pageVO.getRows().get(0).put("cft_core_feesum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("cft_core_feesum"))));
			pageVO.getRows().get(0).put("cft_difsum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("cft_difsum"))));
			pageVO.getRows().get(0).put("refundsum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("refundsum"))));
			pageVO.getRows().get(0).put("paysum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("paysum"))));
			pageVO.getRows().get(0).put("prepaysum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("prepaysum"))));
			pageVO.getRows().get(0).put("va_amtsum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("va_amtsum"))));
			pageVO.getRows().get(0).put("dep_eodsum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("dep_eodsum"))));
			pageVO.getRows().get(0).put("unmatch_difsum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("unmatch_difsum"))));
			pageVO.getRows().get(0).put("dep_difsum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("dep_difsum"))));
			pageVO.getRows().get(0).put("loan_repsum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("loan_repsum"))));
			pageVO.getRows().get(0).put("contractcount",total);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		for (Map<String,Object> map :pageVO.getRows()) {
			Date date = (Date) map.get("inputdate");
			String inputdate = sdf.format(date);
			map.put("inputdate", inputdate);
		}
		return pageVO;
	}
	
	public List<Map<String,Object>> queryBankWithholdFindAll(BankWithholdVO dayTradingVO){
		return bankWithholdService.queryBankWithholdFindAll(dayTradingVO);
	}

	@Override
	public Map<String,Object> queryBankWithholdCount(BankWithholdVO vo) {
		Map<String, Object> map = new HashMap<String, Object>();
		//组装条件
		if (map != null) {
			map.put("startInputDate",vo.getStartInputDate());
			map.put("endInputDate",vo.getEndInputDate());
		}
		return bankWithholdService.queryBankWithholdCount(map);
	}

}
