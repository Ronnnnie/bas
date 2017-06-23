package com.billionsfinance.bas.server;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.ContractVO;
import com.billionsfinance.bas.entity.PageVO;

public interface IContractServer {

	public PageVO queryBusinessDetail(PageVO pageVO,Map<String, Object> map);
	
	public void updateContract(ContractVO brVO);
	
	public void createContract(ContractVO brVO);

	/**
	 * 查询业务明细总数
	 * @param alsUser
	 * @return
	 */
	public Map<String,Object> queryBusinessDetailCount(Map<String, Object> map);
	
	public void importContract(List<ContractVO> list);
	
	public void payConfirm(ContractVO vo);
	
	public void accountingMark(ContractVO vo);

	public List<Map<String, Object>> queryBusinessDetailId(ContractVO vo);
	

	

}
