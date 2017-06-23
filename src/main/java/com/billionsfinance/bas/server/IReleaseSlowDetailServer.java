package com.billionsfinance.bas.server;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.entity.ReleaseSlowDetailVO;

public interface IReleaseSlowDetailServer {

	public PageVO queryRepaymentDetail(PageVO pageVo  , ReleaseSlowDetailVO vo);
	
	public List<Map<String,Object>> queryRepaymentDetailFindAll(ReleaseSlowDetailVO vo);
	
	public Map<String,Object> queryRepaymentDetailCount(ReleaseSlowDetailVO repaymentDetailVO);
	
	public int accountingMark(ReleaseSlowDetailVO vo);
	
	public int cancelAccountingMark(ReleaseSlowDetailVO vo);
	
	public void contractApprove(ReleaseSlowDetailVO vo);

}
