package com.billionsfinance.bas.dao;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.PredictcostVO;


public interface PredictcostDao {

	
	public List<Map<String,Object>> queryPredictcost(Map<String,Object> map);
	
	public Map<String,Object> queryPredictcostCount(Map<String,Object> map);
	
	public List<Map<String,Object>> queryPredictcostSum(Map<String,Object> map);
	
	public Map<String,Object> queryPredictcostCountSum(Map<String,Object> map);
	
	public List<PredictcostVO> queryPredictcostAll(Map<String,Object> map);
	
	public List<PredictcostVO> queryPredictcostSumAll(Map<String,Object> map);
	
	public void updatePredictcost(PredictcostVO vo);
	
	public Integer checkPredictcost(PredictcostVO vo);
	
	public Integer checkCancelPredictcost(PredictcostVO vo);
	
	public void cancelPredictcost(PredictcostVO vo);
}
