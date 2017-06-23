package com.billionsfinance.bas.server;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.entity.RansomDetailVO;

public interface IRansomDetailServer {

	public PageVO queryRepaymentDetail(PageVO pageVo  , RansomDetailVO vo);
	
	public List<RansomDetailVO> queryRepaymentDetailById(RansomDetailVO vo);
	
	public List<Map<String,Object>> queryRepaymentDetailFindAll(RansomDetailVO vo);
	
	public Map<String,Object> queryRepaymentDetailCount(RansomDetailVO vo);
	
	public int accountingMark(RansomDetailVO vo);
	
	public int cancelAccountingMark(RansomDetailVO vo);
	
	public void contractApprove(RansomDetailVO vo);

}
