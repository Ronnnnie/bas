package com.billionsfinance.bas.server;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.entity.VillageReceivedPaymentsVO;

public interface IVillageReceivedPaymentsServer {

	public PageVO queryVillageReceivedPaymentsDetail(PageVO pageVo  , VillageReceivedPaymentsVO vo);
	
	public List<Map<String,Object>> queryVillageReceivedPaymentsContract(VillageReceivedPaymentsVO vo);
	
	public List<Map<String,Object>> queryVillageReceivedPaymentsFindAll(VillageReceivedPaymentsVO vo);
	
	public List<Map<String,Object>> villageReceivedPaymentsExport(VillageReceivedPaymentsVO vo);
	
	public Map<String,Object> queryVillageReceivedPaymentsCount(VillageReceivedPaymentsVO vo);
	
	public int accountingMark(VillageReceivedPaymentsVO vo);
	
	public int cancelAccountingMark(VillageReceivedPaymentsVO vo);
	
	public int approveContract(VillageReceivedPaymentsVO vo);


}
