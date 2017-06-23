package com.billionsfinance.bas.server.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.DayTradingVO;
import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.server.IDayTradingServer;
import com.billionsfinance.bas.service.DayTradingService;
import com.billionsfinance.bas.util.CommonUtil;
import com.billionsfinance.bas.util.SpringService;
import com.billionsfinance.bas.util.StringUtil;

public class DayTradingServer implements IDayTradingServer {

	private DayTradingService dayTradingService = SpringService.DAYTRADINGSERVICE;
	
	@Override
	public PageVO queryDayTradingDetail(PageVO pageVO , DayTradingVO vo) {

		Map<String, Object> whereMap = new HashMap<String, Object>();

		//组装条件

		if (vo != null) {
			whereMap.put("startTransDate",vo.getStartTransDate());
			whereMap.put("endTransDate",vo.getEndTransDate());
			whereMap.put("transType",vo.getTransType());
			whereMap.put("dataSource",vo.getDataSource());
			whereMap.put("belong",vo.getBelong());
		}

		//统计合同总数
		
		Map<String,Object> brVO = dayTradingService.queryDayTradingDetailCount(whereMap);
		
		Long total = Long.parseLong(brVO.get("contractCount").toString()) ;
		
		//封装分页参数
		Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
		whereMap.putAll(whereMapPage);
		
		//查询合同明细
		List<Map<String, Object>> map2 = dayTradingService.queryDayTradingDetail(whereMap);
		pageVO.setRows(map2);
		
		if(pageVO.getRows().size()>0){
			//总匹配合同数 总匹配金额
			pageVO.getRows().get(0).put("moneyCount", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("moneyCount"))));
			pageVO.getRows().get(0).put("principalamtSum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("principalamtSum"))));
			pageVO.getRows().get(0).put("inteamtSum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("inteamtSum"))));
			pageVO.getRows().get(0).put("payPrincipalamtSum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("payPrincipalamtSum"))));
			pageVO.getRows().get(0).put("receivePrincipalamtSum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("receivePrincipalamtSum"))));
			pageVO.getRows().get(0).put("payInteamtSum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("payInteamtSum"))));
			pageVO.getRows().get(0).put("receiveInteamtSum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("receiveInteamtSum"))));
			pageVO.getRows().get(0).put("pureoverflowsumTotal", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("pureoverflowsumTotal"))));
			pageVO.getRows().get(0).put("payPureoverflowsumTotal", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("payPureoverflowsumTotal"))));
			pageVO.getRows().get(0).put("a2Sum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("a2Sum"))));
			pageVO.getRows().get(0).put("a7Sum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("a7Sum"))));
			pageVO.getRows().get(0).put("a9Sum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("a9Sum"))));
			pageVO.getRows().get(0).put("a10Sum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("a10Sum"))));
			pageVO.getRows().get(0).put("a11Sum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("a11Sum"))));
			pageVO.getRows().get(0).put("a12Sum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("a12Sum"))));
			pageVO.getRows().get(0).put("a17Sum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("a17Sum"))));
			pageVO.getRows().get(0).put("a18Sum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("a18Sum"))));
			pageVO.getRows().get(0).put("a19Sum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("a19Sum"))));
			pageVO.getRows().get(0).put("a22Sum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("a22Sum"))));
			pageVO.getRows().get(0).put("contractCount",total);
		}
		return pageVO;
	}
	
	public List<Map<String,Object>> queryDayTradingDetailFindAll(DayTradingVO dayTradingVO){
		return dayTradingService.queryDayTradingDetailFindAll(dayTradingVO);
	}

	@Override
	public Map<String,Object> queryDayTradingDetailCount(DayTradingVO vo) {
		Map<String, Object> map = new HashMap<String, Object>();
		//组装条件
		if (map != null) {
			map.put("startTransDate",vo.getStartTransDate());
			map.put("endTransDate",vo.getEndTransDate());
			map.put("transType",vo.getTransType());
			map.put("dataSource",vo.getDataSource());
			map.put("belong",vo.getBelong());
		}
		return dayTradingService.queryDayTradingDetailCount(map);
	}

}
