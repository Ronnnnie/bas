package com.billionsfinance.bas.dao;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.CapitalMitigateVO;

public interface CapitalMitigateDao {

	public List<Map<String, Object>> queryCapitalMitigate(Map<String,Object> map);
	
	public List<Map<String, Object>> queryCapitalMitigateFindAll(CapitalMitigateVO vo);
	
	public Map<String, Object> queryCapitalMitigateCount(Map<String,Object> map);
	
}
