package com.billionsfinance.bas.dao;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.JYReceivedPaymentsVO;

public interface JYReceivedPaymentsDao {

	public List<Map<String, Object>> queryJYReceivedPaymentsDetail(Map<String,Object> map);
	
	public List<Map<String, Object>> queryJYReceivedPaymentsContract(Map<String,Object> map);
	
	public Map<String, Object> queryJYReceivedPaymentsCount(Map<String,Object> map);
	
	public List<Map<String, Object>> queryJYReceivedPaymentsDetailFindAll(JYReceivedPaymentsVO vo);
	
	public List<Map<String, Object>> gatherExport(JYReceivedPaymentsVO vo);
	
	public Map<String, Object> queryGatherCount(JYReceivedPaymentsVO vo);
	
	public Map<String, Object> queryDetailCount(JYReceivedPaymentsVO vo);
	
	public int accountingMark(JYReceivedPaymentsVO vo);
	
	public int cancelAccountingMark(JYReceivedPaymentsVO vo);
	
	public int approveContract(JYReceivedPaymentsVO vo);
	
}
