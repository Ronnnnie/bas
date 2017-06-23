package com.billionsfinance.bas.server.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.JYReceivedPaymentsVO;
import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.server.IJYReceivedPaymentsServer;
import com.billionsfinance.bas.service.JYReceivedPaymentsService;
import com.billionsfinance.bas.util.CommonUtil;
import com.billionsfinance.bas.util.SpringService;

public class JYReceivedPaymentsServer implements IJYReceivedPaymentsServer {

	private JYReceivedPaymentsService jyReceivedPaymentsService = SpringService.JYRECEIVEDPAYMENTSSERVICE;
	
	@Override
	public PageVO queryJYReceivedPaymentsDetail(PageVO pageVO, JYReceivedPaymentsVO vo) {
		Map<String, Object> whereMap = new HashMap<String, Object>();
		//组装条件
		if (vo != null) {
			whereMap.put("sequence",vo.getSequence());
			whereMap.put("contracts",vo.getContracts());
			whereMap.put("startRepayDate",vo.getStartRepayDate());
			whereMap.put("endRepayDate",vo.getEndRepayDate());
			whereMap.put("startKeepAccountsTime",vo.getStartKeepAccountsTime());
			whereMap.put("endKeepAccountsTime",vo.getEndKeepAccountsTime());
			whereMap.put("startRegistrationDate",vo.getStartRegistrationDate());
			whereMap.put("endRegistrationDate",vo.getEndRegistrationDate());
		}

		//统计合同总数
		
		Map<String,Object> brVO = jyReceivedPaymentsService.queryJYReceivedPaymentsCount(whereMap);
		
		Long total = Long.parseLong(brVO.get("contractCount").toString()) ;
		
		//封装分页参数
		Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
		whereMap.putAll(whereMapPage);
		
		//查询合同明细
		List<Map<String, Object>> map2 = jyReceivedPaymentsService.queryJYReceivedPaymentsDetail(whereMap);
		pageVO.setRows(map2);
		
		if(pageVO.getRows().size()>0){
			//总匹配合同数 总匹配金额
			pageVO.getRows().get(0).put("contractCount",Long.parseLong(brVO.get("contractCount").toString()));
			pageVO.getRows().get(0).put("moneyCount", Double.parseDouble(brVO.get("moneyCount").toString()));
			pageVO.getRows().get(0).put("payprinciPalamtSum", Double.parseDouble(brVO.get("payprinciPalamtSum").toString()));
			pageVO.getRows().get(0).put("payInteamtSum", Double.parseDouble(brVO.get("payInteamtSum").toString()));
			pageVO.getRows().get(0).put("paymentSum", Double.parseDouble(brVO.get("paymentSum").toString()));
			pageVO.getRows().get(0).put("loanAmountSum", Double.parseDouble(brVO.get("loanAmountSum").toString()));
		}
		return pageVO;
	}

	@Override
	public int accountingMark(JYReceivedPaymentsVO vo) {
		// TODO Auto-generated method stub
		return jyReceivedPaymentsService.accountingMark(vo);
	}
	
	@Override
	public int cancelAccountingMark(JYReceivedPaymentsVO vo) {
		// TODO Auto-generated method stub
		return jyReceivedPaymentsService.cancelAccountingMark(vo);
	}
	
	@Override
	public int approveContract(JYReceivedPaymentsVO vo) {
		// TODO Auto-generated method stub
		return jyReceivedPaymentsService.approveContract(vo);
	}

	@Override
	public List<Map<String, Object>> queryJYReceivedPaymentsContract(JYReceivedPaymentsVO vo) {
		Map<String, Object> whereMap = new HashMap<String, Object>();
		//组装条件
		if (vo != null) {
			whereMap.put("sequence",vo.getSequence());
			whereMap.put("contracts",vo.getContracts());
			whereMap.put("startRepayDate",vo.getStartRepayDate());
			whereMap.put("endRepayDate",vo.getEndRepayDate());
			whereMap.put("startKeepAccountsTime",vo.getStartKeepAccountsTime());
			whereMap.put("endKeepAccountsTime",vo.getEndKeepAccountsTime());
			whereMap.put("startRegistrationDate",vo.getStartRegistrationDate());
			whereMap.put("endRegistrationDate",vo.getEndRegistrationDate());
		}
		return jyReceivedPaymentsService.queryJYReceivedPaymentsContract(whereMap);
	}

	@Override
	public Map<String, Object> queryJYReceivedPaymentsCount(JYReceivedPaymentsVO vo) {
		// TODO Auto-generated method stub
		Map<String, Object> whereMap = new HashMap<String, Object>();
		//组装条件
		if (vo != null) {
			whereMap.put("sequence",vo.getSequence());
			whereMap.put("contracts",vo.getContracts());
			whereMap.put("startRepayDate",vo.getStartRepayDate());
			whereMap.put("endRepayDate",vo.getEndRepayDate());
			whereMap.put("startKeepAccountsTime",vo.getStartKeepAccountsTime());
			whereMap.put("endKeepAccountsTime",vo.getEndKeepAccountsTime());
			whereMap.put("startRegistrationDate",vo.getStartRegistrationDate());
			whereMap.put("endRegistrationDate",vo.getEndRegistrationDate());
		}
		return jyReceivedPaymentsService.queryJYReceivedPaymentsCount(whereMap);
	}
	
	@Override
	public List<Map<String, Object>> queryJYReceivedPaymentsDetailFindAll(JYReceivedPaymentsVO vo) {
		// TODO Auto-generated method stub
		return jyReceivedPaymentsService.queryJYReceivedPaymentsDetailFindAll(vo);
	}
	
	@Override
	public Map<String, Object> queryGatherCount(JYReceivedPaymentsVO vo) {
		// TODO Auto-generated method stub
		return jyReceivedPaymentsService.queryGatherCount(vo);
	}
	
	@Override
	public Map<String, Object> queryDetailCount(JYReceivedPaymentsVO vo) {
		// TODO Auto-generated method stub
		return jyReceivedPaymentsService.queryDetailCount(vo);
	}
	
	@Override
	public List<Map<String, Object>> gatherExport(JYReceivedPaymentsVO vo) {
		// TODO Auto-generated method stub
		return jyReceivedPaymentsService.gatherExport(vo);
	}
}
