package com.billionsfinance.bas.server;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.DataCheckVO;
import com.billionsfinance.bas.entity.PageVO;

public interface IDataCheckServer {

	public PageVO queryDataCheckTableMenuOne(PageVO pageVo);
	
	public PageVO queryDataCheckTableMenuTwo(PageVO pageVo);
	
	public PageVO queryDataCheckTableMenuThree(PageVO pageVo);
	
	public PageVO queryDataCheckTableMenuFour(PageVO pageVo);
	
	public PageVO queryDataCheckTableMenuFive(PageVO pageVo);
	
	public PageVO queryDataCheckTableMenuNine(PageVO pageVo);
	
	public PageVO queryDataCheckTableMenuTwelve(PageVO pageVo);
	
	public List<Map<String,Object>> queryDataCheckTableMenuOneFindAll();
	
	public List<Map<String,Object>> queryDataCheckTableMenuTwoFindAll();
	
	public List<Map<String,Object>> queryDataCheckTableMenuThreeFindAll();
	
	public List<Map<String,Object>> queryDataCheckTableMenuFourFindAll();
	
	public List<Map<String,Object>> queryDataCheckTableMenuFiveFindAll();
	
	public List<Map<String,Object>> queryDataCheckTableMenuNineFindAll();
	
	public List<Map<String,Object>> queryDataCheckTableMenuTwelveFindAll();
	
	public Map<String,Object> queryDataCheckTableMenuOneCount();
	
	public Map<String,Object> queryDataCheckTableMenuTwoCount();
	
	public Map<String,Object> queryDataCheckTableMenuThreeCount();
	
	public Map<String,Object> queryDataCheckTableMenuFourCount();
	
	public Map<String,Object> queryDataCheckTableMenuFiveCount();
	
	public Map<String,Object> queryDataCheckTableMenuNineCount();
	
	public Map<String,Object> queryDataCheckTableMenuTwelveCount();
	
}
