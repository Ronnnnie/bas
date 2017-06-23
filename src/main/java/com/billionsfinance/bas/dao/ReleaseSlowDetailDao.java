package com.billionsfinance.bas.dao;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.ReleaseSlowDetailVO;

public interface ReleaseSlowDetailDao {

	/**
	 * 分页查询业务明细信息
	 * @param alsUser
	 * @return
	 */
	
	public List<Map<String,Object>> queryRepaymentDetail(Map<String,Object> map);
	
	public Map<String,Object> queryRepaymentDetailCount(Map<String,Object> map);
	
	public int accountingMark(ReleaseSlowDetailVO vo);
	
	public int cancelAccountingMark(ReleaseSlowDetailVO vo);
	
	public void contractApprove(ReleaseSlowDetailVO vo);
	
	public List<Map<String,Object>> queryRepaymentDetailFindAll(ReleaseSlowDetailVO vo);
	
}
