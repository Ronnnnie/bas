package com.billionsfinance.bas.dao;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.CompensatoryDetailVO;

public interface CompensatoryDetailDao {

	/**
	 * 分页查询业务明细信息
	 * @param alsUser
	 * @return
	 */
	
	public List<Map<String,Object>> queryRepaymentDetail(Map<String,Object> map);
	
	public Map<String,Object> queryRepaymentDetailCount(Map<String,Object> map);
	
	public int accountingMark(CompensatoryDetailVO vo);
	
	public int cancelAccountingMark(CompensatoryDetailVO vo);
	
	public void contractApprove(CompensatoryDetailVO vo);
	
	public List<CompensatoryDetailVO> queryRepaymentDetailById(CompensatoryDetailVO vo);
	
	public List<Map<String,Object>> queryRepaymentDetailFindAll(CompensatoryDetailVO vo);
	
}
