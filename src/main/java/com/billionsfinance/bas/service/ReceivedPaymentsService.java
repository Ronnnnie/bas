package com.billionsfinance.bas.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.billionsfinance.bas.dao.ReceivedPaymentsDao;
import com.billionsfinance.bas.entity.ReceivedPaymentsVO;

/**
 * 
 * @author FMZhou
 * 
 * @deprecated:回款表 业务实现层
 *
 */
public class ReceivedPaymentsService {

	@Autowired
	private ReceivedPaymentsDao receivedPaymentsDao;
	
	public List<Map<String,Object>> queryReceivedPaymentsDetail(Map<String,Object> map){
		return receivedPaymentsDao.queryReceivedPaymentsDetail(map);
	}
	
	public List<Map<String,Object>> queryReceivedPaymentsGather(Map<String,Object> map){
		return receivedPaymentsDao.queryReceivedPaymentsGather(map);
	}
	
	public Map<String,Object> queryReceivedPaymentsDetailCount(Map<String,Object> map) {
		return receivedPaymentsDao.queryReceivedPaymentsDetailCount(map);
	}
	
	public Map<String,Object> queryReceivedPaymentsGatherCount(Map<String,Object> map) {
		return receivedPaymentsDao.queryReceivedPaymentsGatherCount(map);
	}
	
	public List<Map<String,Object>> queryReceivedPaymentsId(ReceivedPaymentsVO vo) {
		return receivedPaymentsDao.queryReceivedPaymentsId(vo);
	}
	
	public List<Map<String,Object>> queryReceivedPaymentsGatherFindAll(ReceivedPaymentsVO vo) {
		return receivedPaymentsDao.queryReceivedPaymentsGatherFindAll(vo);
	}
	
	public int accountingMark(ReceivedPaymentsVO vo) {
		return receivedPaymentsDao.accountingMark(vo);
	}	
	
	public int cancelAccountingMark(ReceivedPaymentsVO vo) {
		return receivedPaymentsDao.cancelAccountingMark(vo);
	}	
	
	public List<Map<String, Object>> receivedPaymentsExport(ReceivedPaymentsVO vo) {
		// TODO Auto-generated method stub
		return receivedPaymentsDao.receivedPaymentsExport(vo);
	}
}
