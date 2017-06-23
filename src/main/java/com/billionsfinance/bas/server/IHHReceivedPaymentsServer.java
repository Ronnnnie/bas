package com.billionsfinance.bas.server;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.entity.HHReceivedPaymentsVO;

public interface IHHReceivedPaymentsServer {

	public PageVO queryReceivedPaymentsDetail(PageVO pageVo  , HHReceivedPaymentsVO vo);
	
	public List<HHReceivedPaymentsVO> queryReceivedPaymentsDetailById(HHReceivedPaymentsVO vo);
	
	public Map<String,Object> queryBusinessDetailCount(Map<String,Object> map);
	
	public void accountingMark(HHReceivedPaymentsVO vo);
	
}
