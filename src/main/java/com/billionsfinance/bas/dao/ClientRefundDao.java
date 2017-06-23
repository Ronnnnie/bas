package com.billionsfinance.bas.dao;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.ClientRefundVO;

public interface ClientRefundDao {

	/**
	 * 分页查询业务明细信息
	 * @param alsUser
	 * @return
	 */
	
	public List<Map<String,Object>> queryBusinessDetail(Map<String,Object> map);
	
	public List<ClientRefundVO> queryBusinessDetailById(String id);
	
	public List<Map<String,Object>> queryBusinessDetailId(ClientRefundVO vo);
	
	/**
	 * 分页查询业务汇总信息
	 * @param alsUser
	 * @return
	 */
	public List<Map<String, Object>> queryBusinessGather(ClientRefundVO vo);
	
	/**
	 * 分页查询资金明细信息
	 * @param alsUser
	 * @return
	 */
	public List<Map<String, Object>> queryBankrollDetail(ClientRefundVO vo);

	public void updateContract(ClientRefundVO vo);
	
	public void createContract(ClientRefundVO vo);
	/**
	 * 查询业务明细总数
	 * @param alsUser
	 * @return
	 */
	public Map<String,Object> queryBusinessDetailCount(Map<String,Object> map);
	
	public void importContract(List<ClientRefundVO> list);
	
	public void deleteBusinessDetail(String id);

	public void updateBusinessDetail(ClientRefundVO vo);
	
	public void payConfirm(ClientRefundVO vo);
	
	public void accountingMark(ClientRefundVO vo);
	
	
	
}
