package com.billionsfinance.bas.server;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.entity.PredictcostVO;

public interface IPredictcostServer {

	public PageVO queryPredictcost(PageVO pageVo,PredictcostVO vo);
	
	
	public Map<String,Object> queryPredictcostTotal(PredictcostVO vo);
	/**
	 * 查询合同总数
	 * @param alsUser
	 * @return
	 */
	public Map<String,Object> queryPredictcostCount(Map<String,Object> map);
	
	
	public PageVO queryPredictcostSum(PageVO pageVo,PredictcostVO vo);
	
	public Map<String,Object> queryPredictcostSumTotal(PredictcostVO vo);
	
	
	
	/**
	 * 查询合同总数
	 * @param alsUser
	 * @return
	 */
	public Map<String,Object> queryPredictcostCountSum(Map<String,Object> map);



	public List<PredictcostVO> queryPredictcostAll(PredictcostVO vo);
	
	public List<PredictcostVO> queryPredictcostSumAll(PredictcostVO vo);
	
	public Boolean updatePredictcost(PredictcostVO vo);
	
	public Integer checkPredictcost(PredictcostVO vo);
	
	public Integer checkCancelPredictcost(PredictcostVO vo);
	
	public Boolean cancelPredictcost(PredictcostVO vo);
	
}
