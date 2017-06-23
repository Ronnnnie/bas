package com.billionsfinance.bas.dao;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.BQRefundVO;

public interface BQRefundDao {

	/**
	 * 分页查询业务明细信息
	 * @param alsUser
	 * @return
	 */
	
	public List<Map<String,Object>> queryBusinessDetail(Map<String,Object> map);
	
	public List<BQRefundVO> queryBusinessDetailById(String id);
	
	public List<Map<String,Object>> queryBusinessDetailId(BQRefundVO vo);
	
	/**
	 * 分页查询业务汇总信息
	 * @param alsUser
	 * @return
	 */
	public List<Map<String, Object>> queryBusinessGather(BQRefundVO vo);
	
	/**
	 * 分页查询资金明细信息
	 * @param alsUser
	 * @return
	 */
	public List<Map<String, Object>> queryBankrollDetail(BQRefundVO vo);

	public void updateContract(BQRefundVO vo);
	
	public void createContract(BQRefundVO vo);
	/**
	 * 查询业务明细总数
	 * @param alsUser
	 * @return
	 */
	public Map<String,Object> queryBusinessDetailCount(Map<String,Object> map);
	
	public void importContract(List<BQRefundVO> list);
	
	public void deleteBusinessDetail(String id);

	public void updateBusinessDetail(BQRefundVO vo);
	
	public void payConfirm(BQRefundVO vo);
	
	public void accountingMark(BQRefundVO vo);
	
	
	
}
