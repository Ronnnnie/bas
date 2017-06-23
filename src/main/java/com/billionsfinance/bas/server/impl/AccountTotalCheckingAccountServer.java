package com.billionsfinance.bas.server.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.AccountTotalCheckingAccountVO;
import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.server.IAccountTotalCheckingAccountServer;
import com.billionsfinance.bas.service.AccountTotalCheckingAccountService;
import com.billionsfinance.bas.util.CommonUtil;
import com.billionsfinance.bas.util.SpringService;
import com.billionsfinance.bas.util.StringUtil;

public class AccountTotalCheckingAccountServer implements IAccountTotalCheckingAccountServer {

	private AccountTotalCheckingAccountService accountTotalCheckingAccountService = SpringService.ACCOUNTTOTALCHECKINGACCOUNTSERVICE;
	
	@Override
	public PageVO queryAccountTotalCheckingAccount(PageVO pageVO,AccountTotalCheckingAccountVO vo) {

		Map<String, Object> whereMap = new HashMap<String, Object>();

		//组装条件

		if (vo != null) {
			whereMap.put("startCheckdate",vo.getStartcheckdate());
			whereMap.put("endCheckdate",vo.getEndcheckdate());
			whereMap.put("datasource",vo.getDatasource());
		}

		//统计合同总数
		
		Map<String,Object> brVO = accountTotalCheckingAccountService.queryAccountTotalCheckingAccountTotal(whereMap);
		
		Long total = Long.parseLong(brVO.get("contractCount").toString()) ;
		
		//封装分页参数
		Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
		whereMap.putAll(whereMapPage);
		
		//查询合同明细
		List<Map<String, Object>> map2 = accountTotalCheckingAccountService.queryAccountTotalCheckingAccount(whereMap);
		pageVO.setRows(map2);
		if(pageVO.getRows().size()>0){
			//总匹配合同数 总匹配金额
			pageVO.getRows().get(0).put("moneyCount",new BigDecimal(StringUtil.checkMoneyIsNull(brVO.get("moneyCount"))));
			pageVO.getRows().get(0).put("normal_withhold_backSum", new BigDecimal(StringUtil.checkMoneyIsNull(brVO.get("normal_withhold_backSum"))));
			pageVO.getRows().get(0).put("pre_withhold_backSum", new BigDecimal(StringUtil.checkMoneyIsNull(brVO.get("pre_withhold_backSum"))));
			pageVO.getRows().get(0).put("temp_withhold_backSum", new BigDecimal(StringUtil.checkMoneyIsNull(brVO.get("temp_withhold_backSum"))));
			pageVO.getRows().get(0).put("temp_withhold_back_hangSum", new BigDecimal(StringUtil.checkMoneyIsNull(brVO.get("temp_withhold_back_hangSum"))));
			pageVO.getRows().get(0).put("active_depositSum", new BigDecimal(StringUtil.checkMoneyIsNull(brVO.get("active_depositSum"))));
			pageVO.getRows().get(0).put("active_deposit_hangSum", new BigDecimal(StringUtil.checkMoneyIsNull(brVO.get("active_deposit_hangSum"))));
			pageVO.getRows().get(0).put("push_totalSum", new BigDecimal(StringUtil.checkMoneyIsNull(brVO.get("push_totalSum"))));
			pageVO.getRows().get(0).put("today_deposit_balanceSum", new BigDecimal(StringUtil.checkMoneyIsNull(brVO.get("today_deposit_balanceSum"))));
			pageVO.getRows().get(0).put("tm1_deposit_balanceSum", new BigDecimal(StringUtil.checkMoneyIsNull(brVO.get("tm1_deposit_balanceSum"))));
			pageVO.getRows().get(0).put("contractCount",total);
		}
		return pageVO;
	}
	
	@Override
	public List<Map<String,Object>> queryAccountTotalCheckingAccountFindAll(AccountTotalCheckingAccountVO vo) {
		return accountTotalCheckingAccountService.queryAccountTotalCheckingAccountFindAll(vo);
	}

	@Override
	public Map<String,Object> queryAccountTotalCheckingAccountTotal(AccountTotalCheckingAccountVO vo) {
		Map<String, Object> map = new HashMap<String, Object>();
		//组装条件
		if (map != null) {
			map.put("startCheckdate",vo.getStartcheckdate());
			map.put("endCheckdate",vo.getEndcheckdate());
			map.put("datasource",vo.getDatasource());
		}
		return accountTotalCheckingAccountService.queryAccountTotalCheckingAccountTotal(map);
	}

}
