package com.billionsfinance.bas.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.billionsfinance.bas.dao.OverdueUnDao;
import com.billionsfinance.bas.entity.OverdueUnVO;

public class OverdueUnService {
	
	@Autowired
	private OverdueUnDao overdueUnDao;
	
	public List<Map<String, Object>> queryOverdueUnDetail(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return overdueUnDao.queryOverdueUnDetail(map);
	}

	public List<Map<String, Object>> queryOverdueUnGather(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return overdueUnDao.queryOverdueUnGather(map);
	}
	
	public List<Map<String, Object>> queryOverdueUnContract(OverdueUnVO vo) {
		// TODO Auto-generated method stub
		return overdueUnDao.queryOverdueUnContract(vo);
	}

	public Map<String, Object> queryOverdueUnDetailCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return overdueUnDao.queryOverdueUnDetailCount(map);
	}
	
	public Map<String, Object> queryOverdueUnGatherCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return overdueUnDao.queryOverdueUnGatherCount(map);
	}

	public List<Map<String,Object>> queryOverdueUnDetailFindAll(OverdueUnVO vo) {
		// TODO Auto-generated method stub
		return overdueUnDao.queryOverdueUnDetailFindAll(vo);
	}

	public List<Map<String,Object>> queryOverdueUnGatherFindAll(OverdueUnVO vo) {
		// TODO Auto-generated method stub
		return overdueUnDao.queryOverdueUnGatherFindAll(vo);
	}

	public int accountingMark(OverdueUnVO vo) {
		// TODO Auto-generated method stub
		return overdueUnDao.accountingMark(vo);
	}
	
	public int selectAccountingMark(OverdueUnVO vo) {
		// TODO Auto-generated method stub
		return overdueUnDao.selectAccountingMark(vo);
	}
	
	public int cancelAccountingMark(OverdueUnVO vo) {
		// TODO Auto-generated method stub
		return overdueUnDao.cancelAccountingMark(vo);
	}
	
	public List<Map<String, Object>> overdueUnDetailExport(OverdueUnVO vo) {
		// TODO Auto-generated method stub
		return overdueUnDao.overdueUnDetailExport(vo);
	}
}
