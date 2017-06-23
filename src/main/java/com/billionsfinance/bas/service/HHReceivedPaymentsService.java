package com.billionsfinance.bas.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.billionsfinance.bas.dao.HHReceivedPaymentsDao;
import com.billionsfinance.bas.dao.ReceivedPaymentsDao;
import com.billionsfinance.bas.entity.HHReceivedPaymentsVO;

/**
 * 
 * @author FMZhou
 * 
 * @deprecated:回款表 业务实现层
 *
 */
public class HHReceivedPaymentsService {

	@Autowired
	private HHReceivedPaymentsDao receivedPaymentsDao;
	
	/**
	 * 根据条件查询用户数量
	 * @param alsUser
	 * @return
	 */
	public List<Map<String,Object>> queryReceivedPaymentsDetail(Map<String,Object> map){
		return receivedPaymentsDao.queryReceivedPaymentsDetail(map);
	}
	
	public Map<String,Object> queryReceivedPaymentsDetailCount(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return receivedPaymentsDao.queryReceivedPaymentsDetailCount(map);
	}
	
	public void accountingMark(HHReceivedPaymentsVO vo) {
		// TODO Auto-generated method stub
		receivedPaymentsDao.accountingMark(vo);
	}	
	
	public List<HHReceivedPaymentsVO> queryReceivedPaymentsDetailById(HHReceivedPaymentsVO vo) {
		// TODO Auto-generated method stub
		return receivedPaymentsDao.queryReceivedPaymentsDetailById(vo);
	}	
	
}
