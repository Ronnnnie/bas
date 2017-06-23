package com.billionsfinance.bas.server.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.HHReceivedPaymentsVO;
import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.server.IHHReceivedPaymentsServer;
import com.billionsfinance.bas.service.HHReceivedPaymentsService;
import com.billionsfinance.bas.util.CommonUtil;
import com.billionsfinance.bas.util.SpringService;

public class HHReceivedPaymentsServer implements IHHReceivedPaymentsServer {

	HHReceivedPaymentsService hhReceivedPaymentsService = SpringService.HHRECEIVEDPAYMENTSSERVICE;
	
	@Override
	public PageVO queryReceivedPaymentsDetail(PageVO pageVO, HHReceivedPaymentsVO vo) {
		Map<String, Object> whereMap = new HashMap<String, Object>();
		
		
		//组装条件
		if (vo != null) {
			whereMap.put("serialNo",vo.getSerialNo());
			whereMap.put("startPayDate",vo.getStartPayDate());
			whereMap.put("endPayDate",vo.getEndPayDate());
		}

		//统计合同总数
		
		Map<String,Object> brVO = hhReceivedPaymentsService.queryReceivedPaymentsDetailCount(whereMap);
		
		Long total = Long.parseLong(brVO.get("contractCount").toString());
		
		//封装分页参数
		Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
		whereMap.putAll(whereMapPage);
		
		//查询合同明细
		List<Map<String, Object>> map2 = hhReceivedPaymentsService.queryReceivedPaymentsDetail(whereMap);
		pageVO.setRows(map2);
		
		//总匹配合同数 总匹配金额
		if (brVO != null && brVO.get("moneyCount") != null && brVO.get("contractCount") != null) {
			pageVO.getRows().get(0).put("moneyCount", Double.parseDouble(brVO.get("moneyCount").toString()));
			pageVO.getRows().get(0).put("contractCount",Long.parseLong(brVO.get("contractCount").toString()));
		}
		
		return pageVO;
	}

	@Override
	public Map<String, Object> queryBusinessDetailCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void accountingMark(HHReceivedPaymentsVO vo) {
		hhReceivedPaymentsService.accountingMark(vo);
	}

	@Override
	public List<HHReceivedPaymentsVO> queryReceivedPaymentsDetailById(HHReceivedPaymentsVO vo) {
		// TODO Auto-generated method stub
		return hhReceivedPaymentsService.queryReceivedPaymentsDetailById(vo);
	}

}
