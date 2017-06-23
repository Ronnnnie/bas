package com.billionsfinance.bas.dao;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.ContractVO;

public interface ContractDao {

	/**
	 * 分页查询业务明细信息
	 * @param alsUser
	 * @return
	 */
	
	public List<Map<String,Object>> queryBusinessDetail(Map<String, Object> map);
	
	/**
	 * 分页查询业务汇总信息
	 * @param alsUser
	 * @return
	 */
	public List<Map<String, Object>> queryBusinessGather(ContractVO brVO);
	
	/**
	 * 分页查询资金明细信息
	 * @param alsUser
	 * @return
	 */
	public List<Map<String, Object>> queryBankrollDetail(ContractVO brVO);

	public void updateContract(ContractVO brVO);
	
	public void createContract(ContractVO brVO);
	/**
	 * 查询业务明细总数
	 * @param alsUser
	 * @return
	 */
	public Map<String,Object> queryBusinessDetailCount(Map<String,Object> map);
	
	
	public int importContract(List<ContractVO> list);
	
	public void payConfirm(ContractVO vo);
	
	public void accountingMark(ContractVO vo);
	
	public List<Map<String, Object>> queryBusinessDetailId(ContractVO vo);
	
}
