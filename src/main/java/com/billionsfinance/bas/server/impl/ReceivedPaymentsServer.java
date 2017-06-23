package com.billionsfinance.bas.server.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.entity.ReceivedPaymentsVO;
import com.billionsfinance.bas.server.IReceivedPaymentsServer;
import com.billionsfinance.bas.service.ReceivedPaymentsService;
import com.billionsfinance.bas.util.CommonUtil;
import com.billionsfinance.bas.util.SpringService;
import com.billionsfinance.bas.util.StringUtil;

public class ReceivedPaymentsServer implements IReceivedPaymentsServer {

	ReceivedPaymentsService receivedPaymentsService = SpringService.RECEIVEDPAYMENTSSERVICE;

	@Override
	public PageVO queryReceivedPaymentsDetail(PageVO pageVO, ReceivedPaymentsVO vo) {
		Map<String, Object> whereMap = new HashMap<String, Object>();
		
		
		//组装条件
		if (vo != null) {
			whereMap.put("seqId",vo.getSeqId());
			whereMap.put("serialNo",vo.getSerialNo());
			whereMap.put("startRegistrationDate",vo.getStartRegistrationDate());
			whereMap.put("endRegistrationDate",vo.getEndRegistrationDate());
			whereMap.put("startShouldAlsoDate",vo.getStartShouldAlsoDate());
			whereMap.put("endShouldAlsoDate",vo.getEndShouldAlsoDate());
			whereMap.put("startKeepAccountsDate",vo.getStartKeepAccountsDate());
			whereMap.put("endKeepAccountsDate",vo.getEndKeepAccountsDate());
			whereMap.put("subProductType",vo.getSubProductType());
			whereMap.put("assetBelong",vo.getAssetBelong());
		}

		//统计合同总数
		
		Map<String,Object> brVO = receivedPaymentsService.queryReceivedPaymentsDetailCount(whereMap);
		
		Long total = Long.parseLong(brVO.get("contractCount").toString()) ;
		
		//封装分页参数
		Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
		whereMap.putAll(whereMapPage);
		
		//查询合同明细
		List<Map<String, Object>> map2 = receivedPaymentsService.queryReceivedPaymentsDetail(whereMap);
		pageVO.setRows(map2);
		if(pageVO.getRows().size()>0){
			pageVO.getRows().get(0).put("actualPayPrincipalAmtSum",StringUtil.checkMoneyIsNull(brVO.get("actualPayPrincipalAmtSum")));
			pageVO.getRows().get(0).put("actualPayinteAmtSum",StringUtil.checkMoneyIsNull(brVO.get("actualPayinteAmtSum")));
			pageVO.getRows().get(0).put("a2Sum",StringUtil.checkMoneyIsNull(brVO.get("a2Sum")));
			pageVO.getRows().get(0).put("a7Sum",StringUtil.checkMoneyIsNull(brVO.get("a7Sum")));
			pageVO.getRows().get(0).put("a9Sum",StringUtil.checkMoneyIsNull(brVO.get("a9Sum")));
			pageVO.getRows().get(0).put("a10Sum",StringUtil.checkMoneyIsNull(brVO.get("a10Sum")));
			pageVO.getRows().get(0).put("a11Sum",StringUtil.checkMoneyIsNull(brVO.get("a11Sum")));
			pageVO.getRows().get(0).put("a12Sum",StringUtil.checkMoneyIsNull(brVO.get("a12Sum")));
			pageVO.getRows().get(0).put("a17Sum",StringUtil.checkMoneyIsNull(brVO.get("a17Sum")));
			pageVO.getRows().get(0).put("a18Sum",StringUtil.checkMoneyIsNull(brVO.get("a18Sum")));
			pageVO.getRows().get(0).put("a19Sum",StringUtil.checkMoneyIsNull(brVO.get("a19Sum")));
		}
		return pageVO;
	}
	
	@Override
	public PageVO queryReceivedPaymentsGather(PageVO pageVO, ReceivedPaymentsVO vo) {
		Map<String, Object> whereMap = new HashMap<String, Object>();
		
		
		
		//组装条件
		if (vo != null) {
			whereMap.put("startKeepAccountsDate",vo.getStartKeepAccountsDate());
			whereMap.put("endKeepAccountsDate",vo.getEndKeepAccountsDate());
			whereMap.put("startShouldAlsoDate",vo.getStartShouldAlsoDate());
			whereMap.put("endShouldAlsoDate",vo.getEndShouldAlsoDate());
			whereMap.put("businessModel",vo.getBusinessModel());
			whereMap.put("subProductType",vo.getSubProductType());
			whereMap.put("city",vo.getCity());
			whereMap.put("creditperson",vo.getCreditperson());
			whereMap.put("assetBelong",vo.getAssetBelong());
			whereMap.put("guaranteeParty",vo.getGuaranteeParty());
			whereMap.put("cancelType",vo.getCancelType());
		}
		
		//统计合同总数
		
		Map<String,Object> brVO = receivedPaymentsService.queryReceivedPaymentsGatherCount(whereMap);
		
		Long total = Long.parseLong(brVO.get("contractCount").toString()) ;
		
		//封装分页参数
		Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
		whereMap.putAll(whereMapPage);
		
		//查询合同明细
		List<Map<String, Object>> map2 = receivedPaymentsService.queryReceivedPaymentsGather(whereMap);
		pageVO.setRows(map2);
		
		if(pageVO.getRows().size()>0){
			//总匹配合同数 总匹配金额
			pageVO.getRows().get(0).put("contractCount", brVO.get("contractCount"));
			pageVO.getRows().get(0).put("actualPayPrincipalAmtSum",StringUtil.checkMoneyIsNull(brVO.get("actualPayPrincipalAmtSum")));
			pageVO.getRows().get(0).put("actualPayinteAmtSum",StringUtil.checkMoneyIsNull(brVO.get("actualPayinteAmtSum")));
			pageVO.getRows().get(0).put("a2Sum",StringUtil.checkMoneyIsNull(brVO.get("a2Sum")));
			pageVO.getRows().get(0).put("a7Sum",StringUtil.checkMoneyIsNull(brVO.get("a7Sum")));
			pageVO.getRows().get(0).put("a9Sum",StringUtil.checkMoneyIsNull(brVO.get("a9Sum")));
			pageVO.getRows().get(0).put("a10Sum",StringUtil.checkMoneyIsNull(brVO.get("a10Sum")));
			pageVO.getRows().get(0).put("a11Sum",StringUtil.checkMoneyIsNull(brVO.get("a11Sum")));
			pageVO.getRows().get(0).put("a12Sum",StringUtil.checkMoneyIsNull(brVO.get("a12Sum")));
			pageVO.getRows().get(0).put("a17Sum",StringUtil.checkMoneyIsNull(brVO.get("a17Sum")));
			pageVO.getRows().get(0).put("a18Sum",StringUtil.checkMoneyIsNull(brVO.get("a18Sum")));
			pageVO.getRows().get(0).put("a19Sum",StringUtil.checkMoneyIsNull(brVO.get("a19Sum")));
		}
		return pageVO;
	}

	@Override
	public Map<String, Object> queryReceivedPaymentsDetailCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return receivedPaymentsService.queryReceivedPaymentsDetailCount(map);
	}
	
	@Override
	public Map<String, Object> queryReceivedPaymentsGatherCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return receivedPaymentsService.queryReceivedPaymentsGatherCount(map);
	}
	
	@Override
	public List<Map<String, Object>> queryReceivedPaymentsId(ReceivedPaymentsVO vo) {
		// TODO Auto-generated method stub
		return receivedPaymentsService.queryReceivedPaymentsId(vo);
	}
	
	@Override
	public List<Map<String, Object>> queryReceivedPaymentsGatherFindAll(ReceivedPaymentsVO vo) {
		// TODO Auto-generated method stub
		return receivedPaymentsService.queryReceivedPaymentsGatherFindAll(vo);
	}

	@Override
	public int accountingMark(ReceivedPaymentsVO vo) {
		// TODO Auto-generated method stub
		return receivedPaymentsService.accountingMark(vo);
	}
	
	@Override
	public int cancelAccountingMark(ReceivedPaymentsVO vo) {
		// TODO Auto-generated method stub
		return receivedPaymentsService.cancelAccountingMark(vo);
	}

	@Override
	public List<Map<String, Object>> receivedPaymentsExport(ReceivedPaymentsVO vo) {
		// TODO Auto-generated method stub
		return receivedPaymentsService.receivedPaymentsExport(vo);
	}


}
