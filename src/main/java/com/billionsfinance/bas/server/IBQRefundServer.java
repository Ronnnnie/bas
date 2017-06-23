package com.billionsfinance.bas.server;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.BQRefundVO;
import com.billionsfinance.bas.entity.PageVO;

public interface IBQRefundServer {

	public PageVO queryBusinessDetail(PageVO pageVo  , BQRefundVO vo);
	
	public void updateContract(BQRefundVO vo);
	
	public void createContract(BQRefundVO vo);

	public Map<String,Object> queryBusinessDetailCount(Map<String,Object> map);
	
	public void importContract(List<BQRefundVO> list);
	
	public void deleteBusinessDetail(String id);
	
	public void updateBusinessDetail(BQRefundVO vo);
	
	public void payConfirm(BQRefundVO vo);
	
	public void accountingMark(BQRefundVO vo);

	public List<Map<String, Object>> queryBusinessDetailId(BQRefundVO vo);

}
