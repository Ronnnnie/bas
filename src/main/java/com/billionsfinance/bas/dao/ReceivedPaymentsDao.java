package com.billionsfinance.bas.dao;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.ReceivedPaymentsVO;

public interface ReceivedPaymentsDao {

	public List<Map<String,Object>> queryReceivedPaymentsDetail(Map<String,Object> map);
	
	public List<Map<String,Object>> queryReceivedPaymentsGather(Map<String,Object> map);
	
	public List<Map<String,Object>> queryReceivedPaymentsId(ReceivedPaymentsVO vo);
	
	public List<Map<String,Object>> receivedPaymentsExport(ReceivedPaymentsVO vo);
	
	public List<Map<String,Object>> queryReceivedPaymentsGatherFindAll(ReceivedPaymentsVO vo);
	
	public Map<String,Object> queryReceivedPaymentsDetailCount(Map<String,Object> map);
	
	public Map<String,Object> queryReceivedPaymentsGatherCount(Map<String,Object> map);
	
	public int accountingMark(ReceivedPaymentsVO vo);
	
	public int cancelAccountingMark(ReceivedPaymentsVO vo);
	
	
}
