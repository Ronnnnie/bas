package com.billionsfinance.bas.dao;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.VillageReceivedPaymentsVO;

public interface VillageReceivedPaymentsDao {

	public List<Map<String, Object>> queryVillageReceivedPaymentsDetail(Map<String,Object> map);
	
	public List<Map<String, Object>> queryVillageReceivedPaymentsContract(Map<String,Object> map);
	
	public List<Map<String, Object>> queryVillageReceivedPaymentsFindAll(VillageReceivedPaymentsVO vo);
	
	public List<Map<String, Object>> villageReceivedPaymentsExport(VillageReceivedPaymentsVO vo);
	
	public Map<String, Object> queryVillageReceivedPaymentsCount(Map<String,Object> map);
	
	public int accountingMark(VillageReceivedPaymentsVO vo);
	
	public int cancelAccountingMark(VillageReceivedPaymentsVO vo);
	
	public int approveContract(VillageReceivedPaymentsVO vo);
	
}
