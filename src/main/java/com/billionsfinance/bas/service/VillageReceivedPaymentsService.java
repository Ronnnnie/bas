package com.billionsfinance.bas.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.billionsfinance.bas.dao.VillageReceivedPaymentsDao;
import com.billionsfinance.bas.entity.VillageReceivedPaymentsVO;

public class VillageReceivedPaymentsService {
	
	@Autowired
	private VillageReceivedPaymentsDao villageReceivedPaymentsDao;
	
	public List<Map<String, Object>> queryVillageReceivedPaymentsDetail(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return villageReceivedPaymentsDao.queryVillageReceivedPaymentsDetail(map);
	}
	
	public List<Map<String, Object>> queryVillageReceivedPaymentsContract(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return villageReceivedPaymentsDao.queryVillageReceivedPaymentsContract(map);
	}
	
	public List<Map<String, Object>> villageReceivedPaymentsExport(VillageReceivedPaymentsVO vo) {
		// TODO Auto-generated method stub
		return villageReceivedPaymentsDao.villageReceivedPaymentsExport(vo);
	}
	
	public List<Map<String, Object>> queryVillageReceivedPaymentsFindAll(VillageReceivedPaymentsVO vo) {
		// TODO Auto-generated method stub
		return villageReceivedPaymentsDao.queryVillageReceivedPaymentsFindAll(vo);
	}

	public Map<String, Object> queryVillageReceivedPaymentsCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return villageReceivedPaymentsDao.queryVillageReceivedPaymentsCount(map);
	}

	public int accountingMark(VillageReceivedPaymentsVO vo) {
		// TODO Auto-generated method stub
		return villageReceivedPaymentsDao.accountingMark(vo);
	}
	
	public int cancelAccountingMark(VillageReceivedPaymentsVO vo) {
		// TODO Auto-generated method stub
		return villageReceivedPaymentsDao.cancelAccountingMark(vo);
	}
	
	public int approveContract(VillageReceivedPaymentsVO vo) {
		// TODO Auto-generated method stub
		return villageReceivedPaymentsDao.approveContract(vo);
	}
}
