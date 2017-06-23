package com.billionsfinance.bas.dao;

import java.util.List;
import java.util.Map;

public interface DataCheckDao {

	public List<Map<String,Object>> queryDataCheckTableMenuOne(Map<String,Object> map);
	
	public List<Map<String,Object>> queryDataCheckTableMenuTwo(Map<String,Object> map);
	
	public List<Map<String,Object>> queryDataCheckTableMenuThree(Map<String,Object> map);
	
	public List<Map<String,Object>> queryDataCheckTableMenuFour(Map<String,Object> map);
	
	public List<Map<String,Object>> queryDataCheckTableMenuFive(Map<String,Object> map);
	
	public List<Map<String,Object>> queryDataCheckTableMenuNine(Map<String,Object> map);
	
	public List<Map<String,Object>> queryDataCheckTableMenuTwelve(Map<String,Object> map);
	
	public Map<String,Object> queryDataCheckTableMenuOneCount();
	
	public Map<String,Object> queryDataCheckTableMenuTwoCount();
	
	public Map<String,Object> queryDataCheckTableMenuThreeCount();
	
	public Map<String,Object> queryDataCheckTableMenuFourCount();
	
	public Map<String,Object> queryDataCheckTableMenuFiveCount();
	
	public Map<String,Object> queryDataCheckTableMenuNineCount();
	
	public Map<String,Object> queryDataCheckTableMenuTwelveCount();
	
	public List<Map<String,Object>> queryDataCheckTableMenuOneFindAll();
	
	public List<Map<String,Object>> queryDataCheckTableMenuTwoFindAll();
	
	public List<Map<String,Object>> queryDataCheckTableMenuThreeFindAll();
	
	public List<Map<String,Object>> queryDataCheckTableMenuFourFindAll();
	
	public List<Map<String,Object>> queryDataCheckTableMenuFiveFindAll();
	
	public List<Map<String,Object>> queryDataCheckTableMenuNineFindAll();
	
	public List<Map<String,Object>> queryDataCheckTableMenuTwelveFindAll();
	
}
