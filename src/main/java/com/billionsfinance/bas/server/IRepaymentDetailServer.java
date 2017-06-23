package com.billionsfinance.bas.server;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.entity.RepaymentDetailVO;

public interface IRepaymentDetailServer {

	public PageVO queryRepaymentDetail(PageVO pageVo  , RepaymentDetailVO vo);
	
	public List<Map<String,Object>> queryRepaymentDetailFindAll(RepaymentDetailVO vo);
	
	public Map<String,Object> queryRepaymentDetailCount(RepaymentDetailVO repaymentDetailVO);
	
	public int accountingMark(RepaymentDetailVO vo);
	
	public int cancelAccountingMark(RepaymentDetailVO vo);
	
	public void contractApprove(RepaymentDetailVO vo);

}
