package com.billionsfinance.bas.server;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.entity.CompensatoryDetailVO;

public interface ICompensatoryDetailServer {

	public PageVO queryRepaymentDetail(PageVO pageVo  , CompensatoryDetailVO vo);
	
	public List<CompensatoryDetailVO> queryRepaymentDetailById(CompensatoryDetailVO vo);
	
	public List<Map<String,Object>> queryRepaymentDetailFindAll(CompensatoryDetailVO vo);
	
	public Map<String,Object> queryRepaymentDetailCount(CompensatoryDetailVO vo);
	
	public int accountingMark(CompensatoryDetailVO vo);
	
	public int cancelAccountingMark(CompensatoryDetailVO vo);
	
	public void contractApprove(CompensatoryDetailVO vo);

}
