package com.billionsfinance.bas.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.billionsfinance.bas.dao.PredictcostDao;
import com.billionsfinance.bas.entity.PredictcostVO;

public class PredictcostService {

	@Autowired
	private PredictcostDao predictcostDao;
	
	/**
	 * 根据条件查询用户数量
	 * @param alsUser
	 * @return
	 */
	public List<Map<String,Object>> queryPredictcost(Map<String,Object> map){
		return predictcostDao.queryPredictcost(map);
	}
	
	
	
	public Map<String,Object> queryPredictcostCount(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return predictcostDao.queryPredictcostCount(map);
	}
	
	public List<Map<String,Object>> queryPredictcostSum(Map<String,Object> map){
		return predictcostDao.queryPredictcostSum(map);
	}
	
	
	
	public Map<String,Object> queryPredictcostCountSum(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return predictcostDao.queryPredictcostCountSum(map);
	}
	
	public List<PredictcostVO> queryPredictcostAll(Map<String,Object> map){
		return predictcostDao.queryPredictcostAll(map);
	}
	
	public List<PredictcostVO> queryPredictcostSumAll(Map<String,Object> map){
		return predictcostDao.queryPredictcostSumAll(map);
	}
	
	public void updatePredictcost(PredictcostVO vo){
		predictcostDao.updatePredictcost(vo);
	}
	
	public Integer checkPredictcost(PredictcostVO vo){
		return predictcostDao.checkPredictcost(vo);
	}
	
	public Integer checkCancelPredictcost(PredictcostVO vo){
		return predictcostDao.checkCancelPredictcost(vo);
	}
	
	public void cancelPredictcost(PredictcostVO vo){
		predictcostDao.cancelPredictcost(vo);
	}

}
