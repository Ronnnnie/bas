package com.billionsfinance.bas.dao;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.HHReceivedPaymentsVO;

public interface HHReceivedPaymentsDao {

	/**
	 * 分页查询业务明细信息
	 * @param alsUser
	 * @return
	 */
	
	public List<Map<String,Object>> queryReceivedPaymentsDetail(Map<String,Object> map);
	
	public Map<String,Object> queryReceivedPaymentsDetailCount(Map<String,Object> map);
	
	public List<HHReceivedPaymentsVO> queryReceivedPaymentsDetailById(HHReceivedPaymentsVO vo);
	
	public void accountingMark(HHReceivedPaymentsVO vo);
	
	
	
}
