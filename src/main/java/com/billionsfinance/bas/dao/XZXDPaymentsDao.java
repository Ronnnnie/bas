package com.billionsfinance.bas.dao;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.XZXDPaymentsVO;

public interface XZXDPaymentsDao {

	
	public List<Map<String, Object>> queryXZXDPaymentsDetail(Map<String,Object> map);
	
	public Map<String,Object> queryXZXDPaymentsCount(Map<String,Object> map);
	
	public int paymentsAffirm(XZXDPaymentsVO vo);
	
	public List<Map<String,Object>> queryXZXDPaymentsFindAll(XZXDPaymentsVO vo);
	
}
