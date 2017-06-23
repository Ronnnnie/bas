package com.billionsfinance.bas.server.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.CapitalMitigateVO;
import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.server.ICapitalMitigateServer;
import com.billionsfinance.bas.service.CapitalMitigateService;
import com.billionsfinance.bas.util.CommonUtil;
import com.billionsfinance.bas.util.SpringService;

public class CapitalMitigateServer implements ICapitalMitigateServer {

	private CapitalMitigateService capitalMitigateService = SpringService.CAPITALMITIGATESERVICE;
	
	@Override
	public PageVO queryCapitalMitigate(PageVO pageVO, CapitalMitigateVO vo) {
		Map<String, Object> whereMap = new HashMap<String, Object>();
		//组装条件
		if (vo != null) {
			whereMap.put("contractNo",vo.getContractNo());
			whereMap.put("startPayDate",vo.getStartPayDate());
			whereMap.put("endPayDate",vo.getEndPayDate());
		}

		//统计合同总数
		
		Map<String,Object> brVO = capitalMitigateService.queryCapitalMitigateCount(whereMap);
		
		Long total = Long.parseLong(brVO.get("contractCount").toString()) ;
		
		//封装分页参数
		Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
		whereMap.putAll(whereMapPage);
		
		//查询合同明细
		List<Map<String, Object>> map2 = capitalMitigateService.queryCapitalMitigate(whereMap);
		pageVO.setRows(map2);
		if(pageVO.getRows().size()>0){
			//总匹配合同数 总匹配金额
			pageVO.getRows().get(0).put("moneyCount", Double.parseDouble(brVO.get("moneyCount").toString()));
			pageVO.getRows().get(0).put("contractCount",Long.parseLong(brVO.get("contractCount").toString()));
			pageVO.getRows().get(0).put("businessSumCount",Double.parseDouble(brVO.get("businessSumCount").toString()));
			pageVO.getRows().get(0).put("payprinciPalamtSum",Double.parseDouble(brVO.get("payprinciPalamtSum").toString()));
			pageVO.getRows().get(0).put("payInteamtSum",Double.parseDouble(brVO.get("payInteamtSum").toString()));
		}
		return pageVO;
	}

	@Override
	public List<Map<String, Object>> queryCapitalMitigateFindAll(CapitalMitigateVO vo) {
		return capitalMitigateService.queryCapitalMitigateFindAll(vo);
	}

	@Override
	public Map<String, Object> queryCapitalMitigateCount(CapitalMitigateVO vo) {
		// TODO Auto-generated method stub
		Map<String, Object> whereMap = new HashMap<String, Object>();
		//组装条件
		if (vo != null) {
			whereMap.put("contractNo",vo.getContractNo());
			whereMap.put("startPayDate",vo.getStartPayDate());
			whereMap.put("endPayDate",vo.getEndPayDate());
		}
		return capitalMitigateService.queryCapitalMitigateCount(whereMap);
	}
}
