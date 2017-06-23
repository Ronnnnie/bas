package com.billionsfinance.bas.dao;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.CurrentExpiredVO;


public interface CurrentPayDao {

	
	public List<Map<String,Object>> queryCurrentPay(Map<String,Object> map);
	
	public Map<String,Object> queryCurrentPayCount(Map<String,Object> map);
	
	public List<Map<String,Object>> queryCurrentPayAll(Map<String,Object> map);
	
	public List<Map<String,Object>> queryCurrentPaySum(Map<String,Object> map);
	
	public Map<String,Object> queryCurrentPayCountSum(Map<String,Object> map);
	
	public List<Map<String,Object>> queryCurrentPaySumAll(Map<String,Object> map);
	
	
	public void updateCurrentPay_JY(CurrentExpiredVO vo);
	
	public void updateCurrentPay_XT(CurrentExpiredVO vo);
	
	public void updateCurrentPay_ZD(CurrentExpiredVO vo);
	
	public void updateCurrentPay_HH(CurrentExpiredVO vo);
	
	public void updateCurrentPay(CurrentExpiredVO vo);
	
	public Integer checkCurrentPay(CurrentExpiredVO vo);
	
	public Integer checkCancelCurrentPay(CurrentExpiredVO vo);
	
	public void cancelCurrentPay_JY(CurrentExpiredVO vo);
	
	public void cancelCurrentPay_HH(CurrentExpiredVO vo);
	
	public void cancelCurrentPay_ZD(CurrentExpiredVO vo);
	
	public void cancelCurrentPay_XT(CurrentExpiredVO vo);
}
