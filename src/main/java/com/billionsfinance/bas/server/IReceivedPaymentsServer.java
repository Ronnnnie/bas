package com.billionsfinance.bas.server;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.entity.ReceivedPaymentsVO;

public interface IReceivedPaymentsServer {

	public PageVO queryReceivedPaymentsDetail(PageVO pageVo  , ReceivedPaymentsVO vo);
	
	public PageVO queryReceivedPaymentsGather(PageVO pageVo  , ReceivedPaymentsVO vo);
	
	public Map<String,Object> queryReceivedPaymentsDetailCount(Map<String,Object> map);
	
	public Map<String,Object> queryReceivedPaymentsGatherCount(Map<String,Object> map);
	
	public List<Map<String, Object>> queryReceivedPaymentsId(ReceivedPaymentsVO vo);
	
	public List<Map<String, Object>> receivedPaymentsExport(ReceivedPaymentsVO vo);
	
	public List<Map<String, Object>> queryReceivedPaymentsGatherFindAll(ReceivedPaymentsVO vo);
	
	public int accountingMark(ReceivedPaymentsVO vo);
	
	public int cancelAccountingMark(ReceivedPaymentsVO vo);

}
