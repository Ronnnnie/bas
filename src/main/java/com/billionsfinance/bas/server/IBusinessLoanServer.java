package com.billionsfinance.bas.server;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.BusinessLoanVO;
import com.billionsfinance.bas.entity.PageVO;

public interface IBusinessLoanServer {

	public PageVO queryBusinessDetail(PageVO pageVo  , BusinessLoanVO blVO);
	
	public List<BusinessLoanVO> queryBusinessDetailById(String id);
	
	public List<Map<String, Object>> queryBusinessGather(BusinessLoanVO blVO);
	
	public List<Map<String, Object>> queryBankrollDetail(BusinessLoanVO blVO);
	
	public List<Map<String,Object>> queryBusinessDetailId(BusinessLoanVO blVO);
	
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
