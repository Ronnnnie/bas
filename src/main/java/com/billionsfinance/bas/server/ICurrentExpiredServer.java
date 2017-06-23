package com.billionsfinance.bas.server;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.CurrentExpiredVO;
import com.billionsfinance.bas.entity.PageVO;

public interface ICurrentExpiredServer {

	public PageVO queryBusinessDetail(PageVO pageVo,CurrentExpiredVO vo);
	
	
	public Map<String,Object> queryCurrentExpiredTotal(CurrentExpiredVO vo);
	/**
	 * 查询合同总数
	 * @param alsUser
	 * @return
	 */
	public Map<String,Object> queryContractCount(Map<String,Object> map);
	
	
	public PageVO queryBusinessDetailSum(PageVO pageVo,CurrentExpiredVO vo);
	
	public Map<String,Object> queryCurrentExpiredSumTotal(CurrentExpiredVO vo);
	
	
	
	/**
	 * 查询合同总数
	 * @param alsUser
	 * @return
	 */
	public Map<String,Object> queryContractCountSum(Map<String,Object> map);



	public List<CurrentExpiredVO> queryBusinessDetailAll(CurrentExpiredVO currentExpiredVO);
	
	public List<CurrentExpiredVO> queryBusinessDetailSumAll(CurrentExpiredVO currentExpiredVO);
	
	public Boolean updateCurrentExpired(CurrentExpiredVO vo);
	
	public Integer checkCurrentExpired(CurrentExpiredVO vo);
	
	public Integer checkCancelCurrentExpired(CurrentExpiredVO vo);
	
	public Boolean cancelCurrentExpired(CurrentExpiredVO vo);
	
}
