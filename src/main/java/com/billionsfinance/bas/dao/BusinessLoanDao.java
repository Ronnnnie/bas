package com.billionsfinance.bas.dao;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.BusinessLoanVO;

public interface BusinessLoanDao {

	/**
	 * 分页查询业务明细信息
	 * @param alsUser
	 * @return
	 */
	
	public List<Map<String,Object>> queryBusinessDetail(Map<String,Object> blVO2);
	
	public List<BusinessLoanVO> queryBusinessDetailById(String id);
	
	public List<Map<String,Object>> queryBusinessDetailId(BusinessLoanVO blVO);
	
	/**
	 * 分页查询业务汇总信息
	 * @param alsUser
	 * @return
	 */
	public List<Map<String, Object>> queryBusinessGather(BusinessLoanVO blVO);
	
	/**
	 * 分页查询资金明细信息
	 * @param alsUser
	 * @return
	 */
	public List<Map<String, Object>> queryBankrollDetail(BusinessLoanVO blVO);

	public void updateContract(BusinessLoanVO blVO);
	
	public void createContract(BusinessLoanVO blVO);
	/**
	 * 查询业务明细总数
	 * @param alsUser
	 * @return
	 */
	public Map<String,Object> queryBusinessDetailCount(Map<String,Object> map);
	
	public void importContract(List<BusinessLoanVO> list);
	
	public void deleteBusinessDetail(String id);

	public void updateBusinessDetail(BusinessLoanVO blVO);
	
	public void payConfirm(BusinessLoanVO blVO);
	
	public void accountingMark(BusinessLoanVO blVO);
	
	
	
}
