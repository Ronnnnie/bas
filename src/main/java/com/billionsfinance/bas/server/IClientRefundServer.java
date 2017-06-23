package com.billionsfinance.bas.server;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.ClientRefundVO;
import com.billionsfinance.bas.entity.PageVO;

public interface IClientRefundServer {

	public PageVO queryBusinessDetail(PageVO pageVo  , ClientRefundVO vo);
	
	public void updateContract(ClientRefundVO vo);
	
	public void createContract(ClientRefundVO vo);

	public Map<String,Object> queryBusinessDetailCount(Map<String,Object> map);
	
	public void importContract(List<ClientRefundVO> list);
	
	public void deleteBusinessDetail(String id);
	
	public void updateBusinessDetail(ClientRefundVO vo);
	
	public void payConfirm(ClientRefundVO vo);
	
	public void accountingMark(ClientRefundVO vo);

	public List<Map<String, Object>> queryBusinessDetailId(ClientRefundVO vo);

}
