package com.billionsfinance.bas.server;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.entity.ClientRepaymentVO;

public interface IClientRepaymentDetailServer {

	public PageVO queryRepaymentDetail(PageVO pageVo  , ClientRepaymentVO vo);
	
	public List<Map<String,Object>> queryRepaymentDetailById(ClientRepaymentVO vo);
	
	public List<Map<String,Object>> queryRepaymentDetailFindAll(ClientRepaymentVO vo);
	
	public Map<String,Object> queryRepaymentDetailCount(ClientRepaymentVO vo);
	
	public int accountingMark(ClientRepaymentVO vo);
	
	public int cancelAccountingMark(ClientRepaymentVO vo);
	
	public void contractApprove(ClientRepaymentVO vo);

}
