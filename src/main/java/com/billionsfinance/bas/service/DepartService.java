package com.billionsfinance.bas.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.billionsfinance.bas.dao.DepartDao;
import com.billionsfinance.bas.entity.DepartVO;

public class DepartService {
	
	@Autowired
	private DepartDao departDao;
	

	public List<DepartVO> loadDepart() {
		return departDao.loadDepart();
	}


	public List<Map<String, Object>> queryAllDepart() {
		return departDao.queryAllDepart();
	}
	
}
