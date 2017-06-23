package com.billionsfinance.bas.server;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.entity.XZXDPaymentsVO;

public interface IXZXDPaymentsServer {

	public PageVO queryXZXDPaymentsDetail(PageVO pageVo  , XZXDPaymentsVO vo);
	
	public List<Map<String, Object>> queryXZXDPaymentsFindAll(XZXDPaymentsVO vo);
	
	public Map<String,Object> queryXZXDPaymentsCount(XZXDPaymentsVO vo);
	
	public int paymentsAffirm(XZXDPaymentsVO vo);
}
