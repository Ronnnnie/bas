package com.billionsfinance.bas.dao;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.DayTradingVO;

public interface DayTradingDao {

	
	public List<Map<String,Object>> queryDayTradingDetail(Map<String,Object> map);
	
	public List<Map<String,Object>> queryDayTradingDetailFindAll(DayTradingVO dayTradingVO);
	
	public Map<String,Object> queryDayTradingDetailCount(Map<String,Object> map);
	
	
	
	
}
