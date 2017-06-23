package com.billionsfinance.bas.server;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.DayTradingVO;
import com.billionsfinance.bas.entity.PageVO;

public interface IDayTradingServer {

	public PageVO queryDayTradingDetail(PageVO pageVo  , DayTradingVO vo);
	
	public Map<String,Object> queryDayTradingDetailCount(DayTradingVO vo);
	
	public List<Map<String,Object>> queryDayTradingDetailFindAll(DayTradingVO dayTradingVO);
	

}
