package com.billionsfinance.bas.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.billionsfinance.bas.dao.CurrentExpiredDao;
import com.billionsfinance.bas.entity.CurrentExpiredVO;

public class CurrentExpiredService {

	@Autowired
	private CurrentExpiredDao currentExpiredDao;
	
	/**
	 * 根据条件查询用户数量
	 * @param alsUser
	 * @return
	 */
	public List<Map<String,Object>> queryBusinessDetail(Map<String,Object> map){
		return currentExpiredDao.queryBusinessDetail(map);
	}
	
	
	
	public Map<String,Object> queryContractCount(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return currentExpiredDao.queryContractCount(map);
	}
	
	public List<Map<String,Object>> queryBusinessDetailSum(Map<String,Object> map){
		return currentExpiredDao.queryBusinessDetailSum(map);
	}
	
	
	
	public Map<String,Object> queryContractCountSum(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return currentExpiredDao.queryContractCountSum(map);
	}
	
	public List<CurrentExpiredVO> queryBusinessDetailAll(Map<String,Object> map){
		return currentExpiredDao.queryBusinessDetailAll(map);
	}
	
	public List<CurrentExpiredVO> queryBusinessDetailSumAll(Map<String,Object> map){
		return currentExpiredDao.queryBusinessDetailSumAll(map);
	}
	
	public void updateCurrentExpired(CurrentExpiredVO vo){
		currentExpiredDao.updateCurrentExpired(vo);
	}
	
	public Integer checkCurrentExpired(CurrentExpiredVO vo){
		return currentExpiredDao.checkCurrentExpired(vo);
	}
	
	public Integer checkCancelCurrentExpired(CurrentExpiredVO vo){
		return currentExpiredDao.checkCancelCurrentExpired(vo);
	}
	
	public void cancelCurrentExpired(CurrentExpiredVO vo){
		currentExpiredDao.cancelCurrentExpired(vo);
	}

}
