package com.billionsfinance.bas.dao;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.CurrentExpiredVO;


public interface CurrentExpiredDao {

	
	public List<Map<String,Object>> queryBusinessDetail(Map<String,Object> map);
	
	public Map<String,Object> queryContractCount(Map<String,Object> map);
	
	public List<Map<String,Object>> queryBusinessDetailSum(Map<String,Object> map);
	
	public Map<String,Object> queryContractCountSum(Map<String,Object> map);
	
	public List<CurrentExpiredVO> queryBusinessDetailAll(Map<String,Object> map);
	
	public List<CurrentExpiredVO> queryBusinessDetailSumAll(Map<String,Object> map);
	
	public void updateCurrentExpired(CurrentExpiredVO vo);
	
	public Integer checkCurrentExpired(CurrentExpiredVO vo);
	
	public Integer checkCancelCurrentExpired(CurrentExpiredVO vo);
	
	public void cancelCurrentExpired(CurrentExpiredVO vo);
}
