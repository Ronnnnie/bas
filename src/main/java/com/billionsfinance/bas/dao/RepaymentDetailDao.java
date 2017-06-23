package com.billionsfinance.bas.dao;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.RepaymentDetailVO;

public interface RepaymentDetailDao {

	/**
	 * 分页查询业务明细信息
	 * @param alsUser
	 * @return
	 */
	
	public List<Map<String,Object>> queryRepaymentDetail(Map<String,Object> map);
	
	public Map<String,Object> queryRepaymentDetailCount(Map<String,Object> map);
	
	public int accountingMark(RepaymentDetailVO vo);
	
	public int cancelAccountingMark(RepaymentDetailVO vo);
	
	public void contractApprove(RepaymentDetailVO vo);
	
	public List<Map<String,Object>> queryRepaymentDetailFindAll(RepaymentDetailVO vo);
	
}
