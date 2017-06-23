package com.billionsfinance.bas.server;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.JYReceivedPaymentsVO;
import com.billionsfinance.bas.entity.PageVO;

public interface IJYReceivedPaymentsServer {

	public PageVO queryJYReceivedPaymentsDetail(PageVO pageVo  , JYReceivedPaymentsVO vo);
	
	public List<Map<String,Object>> queryJYReceivedPaymentsContract(JYReceivedPaymentsVO vo);
	
	public List<Map<String,Object>> queryJYReceivedPaymentsDetailFindAll(JYReceivedPaymentsVO vo);
	
	public List<Map<String,Object>> gatherExport(JYReceivedPaymentsVO vo);
	
	public Map<String,Object> queryJYReceivedPaymentsCount(JYReceivedPaymentsVO vo);
	
	public Map<String,Object> queryGatherCount(JYReceivedPaymentsVO vo);
	
	public Map<String,Object> queryDetailCount(JYReceivedPaymentsVO vo);
	
	public int accountingMark(JYReceivedPaymentsVO vo);
	
	public int cancelAccountingMark(JYReceivedPaymentsVO vo);
	
	public int approveContract(JYReceivedPaymentsVO vo);


}
