package com.billionsfinance.bas.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.billionsfinance.bas.dao.CurrentPayDao;
import com.billionsfinance.bas.entity.CurrentExpiredVO;

public class CurrentPayService {

	@Autowired
	private CurrentPayDao currentPayDao;
	
	/**
	 * 根据条件查询用户数量
	 * @param alsUser
	 * @return
	 */
	public List<Map<String,Object>> queryCurrentPay(Map<String,Object> map){
		return currentPayDao.queryCurrentPay(map);
	}
	
	
	
	public Map<String,Object> queryCurrentPayCount(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return currentPayDao.queryCurrentPayCount(map);
	}
	
	public List<Map<String,Object>> queryCurrentPayAll(Map<String,Object> map){
		return currentPayDao.queryCurrentPayAll(map);
	}
	
	public List<Map<String,Object>> queryCurrentPaySumAll(Map<String,Object> map){
		return currentPayDao.queryCurrentPaySumAll(map);
	}
	
	public void updateCurrentPay(CurrentExpiredVO vo){
		currentPayDao.updateCurrentPay_JY(vo);
		currentPayDao.updateCurrentPay_HH(vo);
		currentPayDao.updateCurrentPay_ZD(vo);
		currentPayDao.updateCurrentPay_XT(vo);
	}
	
	public Integer checkCurrentPay(CurrentExpiredVO vo){
		return currentPayDao.checkCurrentPay(vo);
	}
	
	public List<Map<String,Object>> queryCurrentPaySum(Map<String,Object> map){
		return currentPayDao.queryCurrentPaySum(map);
	}
	
	
	
	public Map<String,Object> queryCurrentPayCountSum(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return currentPayDao.queryCurrentPayCountSum(map);
	}
	
	public Integer checkCancelCurrentPay(CurrentExpiredVO vo){
		return currentPayDao.checkCancelCurrentPay(vo);
	}
	
	public void cancelCurrentPay(CurrentExpiredVO vo){
		currentPayDao.cancelCurrentPay_JY(vo);
		currentPayDao.cancelCurrentPay_HH(vo);
		currentPayDao.cancelCurrentPay_ZD(vo);
		currentPayDao.cancelCurrentPay_XT(vo);
	}

}
