package com.billionsfinance.bas.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.billionsfinance.bas.dao.OverdueDao;
import com.billionsfinance.bas.entity.OverdueVO;

public class OverdueService {
	
	@Autowired
	private OverdueDao overdueDao;
	
	public List<Map<String, Object>> queryOverdueDetail(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return overdueDao.queryOverdueDetail(map);
	}

	public List<Map<String, Object>> queryOverdueGather(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return overdueDao.queryOverdueGather(map);
	}

	public List<Map<String, Object>> queryOverdueContract(OverdueVO vo) {
		// TODO Auto-generated method stub
		return overdueDao.queryOverdueContract(vo);
	}
	
	public Map<String, Object> queryOverdueDetailCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return overdueDao.queryOverdueDetailCount(map);
	}
	
	public Map<String, Object> queryOverdueGatherCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return overdueDao.queryOverdueGatherCount(map);
	}

	public List<Map<String,Object>> queryOverdueDetailFindAll(OverdueVO overdueVO) {
		// TODO Auto-generated method stub
		return overdueDao.queryOverdueDetailFindAll(overdueVO);
	}

	public List<Map<String,Object>> queryOverdueGatherFindAll(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return overdueDao.queryOverdueGatherFindAll(map);
	}

	public int accountingMark(OverdueVO vo) {
		// TODO Auto-generated method stub
		return overdueDao.accountingMark(vo);
	}
	
	public int cancelAccountingMark(OverdueVO vo) {
		// TODO Auto-generated method stub
		return overdueDao.cancelAccountingMark(vo);
	}
	
	public List<Map<String, Object>> overdueGatherExport(OverdueVO vo) {
		// TODO Auto-generated method stub
		return overdueDao.overdueGatherExport(vo);
	}

	public List<Map<String, Object>> overdueDetailExport(OverdueVO vo) {
		// TODO Auto-generated method stub
		return overdueDao.overdueDetailExport(vo);
	}
}
