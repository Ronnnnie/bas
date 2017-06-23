package com.billionsfinance.bas.dao;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.RansomDetailVO;

public interface RansomDetailDao {

	/**
	 * 分页查询业务明细信息
	 * @param alsUser
	 * @return
	 */
	
	public List<Map<String,Object>> queryRepaymentDetail(Map<String,Object> map);
	
	public Map<String,Object> queryRepaymentDetailCount(Map<String,Object> map);
	
	public int accountingMark(RansomDetailVO vo);
	
	public int cancelAccountingMark(RansomDetailVO vo);
	
	public void contractApprove(RansomDetailVO vo);
	
	public List<RansomDetailVO> queryRepaymentDetailById(RansomDetailVO vo);
	
	public List<Map<String,Object>> queryRepaymentDetailFindAll(RansomDetailVO vo);
	
}
