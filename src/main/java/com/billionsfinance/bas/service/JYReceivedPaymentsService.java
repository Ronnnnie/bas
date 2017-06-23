package com.billionsfinance.bas.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.billionsfinance.bas.dao.JYReceivedPaymentsDao;
import com.billionsfinance.bas.entity.JYReceivedPaymentsVO;

public class JYReceivedPaymentsService {
	
	@Autowired
	private JYReceivedPaymentsDao jyReceivedPaymentsDao;
	
	public List<Map<String, Object>> queryJYReceivedPaymentsDetail(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return jyReceivedPaymentsDao.queryJYReceivedPaymentsDetail(map);
	}
	
	public List<Map<String, Object>> queryJYReceivedPaymentsContract(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return jyReceivedPaymentsDao.queryJYReceivedPaymentsContract(map);
	}

	public Map<String, Object> queryJYReceivedPaymentsCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return jyReceivedPaymentsDao.queryJYReceivedPaymentsCount(map);
	}
	
	public List<Map<String, Object>> queryJYReceivedPaymentsDetailFindAll(JYReceivedPaymentsVO vo) {
		// TODO Auto-generated method stub
		return jyReceivedPaymentsDao.queryJYReceivedPaymentsDetailFindAll(vo);
	}
	
	public List<Map<String, Object>> gatherExport(JYReceivedPaymentsVO vo) {
		// TODO Auto-generated method stub
		return jyReceivedPaymentsDao.gatherExport(vo);
	}
	
	public Map<String, Object> queryGatherCount(JYReceivedPaymentsVO vo) {
		// TODO Auto-generated method stub
		return jyReceivedPaymentsDao.queryGatherCount(vo);
	}
	
	public Map<String, Object> queryDetailCount(JYReceivedPaymentsVO vo) {
		// TODO Auto-generated method stub
		return jyReceivedPaymentsDao.queryDetailCount(vo);
	}

	public int accountingMark(JYReceivedPaymentsVO vo) {
		// TODO Auto-generated method stub
		return jyReceivedPaymentsDao.accountingMark(vo);
	}
	
	public int cancelAccountingMark(JYReceivedPaymentsVO vo) {
		// TODO Auto-generated method stub
		return jyReceivedPaymentsDao.cancelAccountingMark(vo);
	}
	
	public int approveContract(JYReceivedPaymentsVO vo) {
		// TODO Auto-generated method stub
		return jyReceivedPaymentsDao.approveContract(vo);
	}
}
