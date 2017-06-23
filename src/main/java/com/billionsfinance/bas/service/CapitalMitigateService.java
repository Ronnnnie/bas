package com.billionsfinance.bas.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.billionsfinance.bas.dao.CapitalMitigateDao;
import com.billionsfinance.bas.entity.CapitalMitigateVO;

public class CapitalMitigateService {
	
	@Autowired
	private CapitalMitigateDao capitalMitigateDao;
	
	public List<Map<String, Object>> queryCapitalMitigate(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return capitalMitigateDao.queryCapitalMitigate(map);
	}
	
	public List<Map<String, Object>> queryCapitalMitigateFindAll(CapitalMitigateVO vo) {
		// TODO Auto-generated method stub
		return capitalMitigateDao.queryCapitalMitigateFindAll(vo);
	}

	public Map<String, Object> queryCapitalMitigateCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return capitalMitigateDao.queryCapitalMitigateCount(map);
	}

}
