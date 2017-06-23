package com.billionsfinance.bas.dao;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.TrustAllotDetailVO;

public interface TrustAllotDetailDao {

	/**
	 * 分页查询业务明细信息
	 * @param alsUser
	 * @return
	 */
	
	public List<Map<String,Object>> queryTrustAllotDetail(Map<String,Object> map);
	
	public Map<String,Object> queryTrustAllotDetailCount(Map<String,Object> map);
	
	public void accountingMark(TrustAllotDetailVO vo);
	
	public List<TrustAllotDetailVO> queryTrustAllotDetailById(TrustAllotDetailVO vo);
	
}
