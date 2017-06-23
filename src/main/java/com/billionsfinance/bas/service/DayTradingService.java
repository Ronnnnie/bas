package com.billionsfinance.bas.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.billionsfinance.bas.dao.DayTradingDao;
import com.billionsfinance.bas.entity.DayTradingVO;

public class DayTradingService {

	@Autowired
	private DayTradingDao dayTradingDao;
	
	/**
	 * 条件查询日交易数据
	 * @param  Map<String,Object>
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> queryDayTradingDetail(Map<String,Object> map){
		return dayTradingDao.queryDayTradingDetail(map);
	}
	
	/**
	 * 条件查询日交易数据Count
	 * @param  Map<String,Object> 
	 * @return Map<String,Object>
	 */
	public Map<String,Object> queryDayTradingDetailCount(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return dayTradingDao.queryDayTradingDetailCount(map);
	}
	
	/**
	 * 查询所有日交易数据
	 * @param  DayTradingVO
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> queryDayTradingDetailFindAll(DayTradingVO dayTradingVO){
		return dayTradingDao.queryDayTradingDetailFindAll(dayTradingVO);
	}
}
