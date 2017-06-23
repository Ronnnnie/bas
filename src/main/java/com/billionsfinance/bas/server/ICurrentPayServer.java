package com.billionsfinance.bas.server;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.CurrentExpiredVO;
import com.billionsfinance.bas.entity.PageVO;

public interface ICurrentPayServer {

	public PageVO queryCurrentPay(PageVO pageVo,CurrentExpiredVO vo);
	
	public Map<String,Object> queryCurrentPayTotal(CurrentExpiredVO vo);
	
	/**
	 * 查询合同总数
	 * @param alsUser
	 * @return
	 */
	public Map<String,Object> queryCurrentPayCount(Map<String,Object> map);
	
	
	public List<Map<String,Object>> queryCurrentPayAll(CurrentExpiredVO currentExpiredVO);
	
	public List<Map<String,Object>> queryCurrentPaySumAll(CurrentExpiredVO currentExpiredVO);
	
	public Boolean updateCurrentPay(CurrentExpiredVO vo);
	
	public Integer checkCurrentPay(CurrentExpiredVO vo);
	
	
	public PageVO queryCurrentPaySum(PageVO pageVo,CurrentExpiredVO vo);
	
	public Map<String,Object> queryCurrentPaySumTotal(CurrentExpiredVO vo);
	
	public Boolean cancelCurrentPay(CurrentExpiredVO vo);
	
	public Integer checkCancelCurrentPay(CurrentExpiredVO vo);
	
}
