package com.billionsfinance.bas.dao;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.ClientRepaymentVO;

public interface ClientRepaymentDetailDao {

	/**
	 * 分页查询业务明细信息
	 * @param alsUser
	 * @return
	 */
	
	public List<Map<String,Object>> queryRepaymentDetail(Map<String,Object> map);
	
	public Map<String,Object> queryRepaymentDetailCount(Map<String,Object> map);
	
	public int accountingMark(ClientRepaymentVO vo);
	
	public int cancelAccountingMark(ClientRepaymentVO vo);
	
	public void contractApprove(ClientRepaymentVO vo);
	
	public List<Map<String,Object>> queryRepaymentDetailById(ClientRepaymentVO vo);
	
	public List<Map<String,Object>> queryRepaymentDetailFindAll(ClientRepaymentVO vo);
	
}
