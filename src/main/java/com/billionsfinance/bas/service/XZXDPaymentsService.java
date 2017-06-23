package com.billionsfinance.bas.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.billionsfinance.bas.dao.XZXDPaymentsDao;
import com.billionsfinance.bas.entity.XZXDPaymentsVO;

public class XZXDPaymentsService {

	@Autowired
	private XZXDPaymentsDao paymentsDao;
	
	/**
	 * 根据条件查询用户数量
	 * @param alsUser
	 * @return
	 */
	public List<Map<String,Object>> queryXZXDPaymentsDetail(Map<String,Object> map){
		return paymentsDao.queryXZXDPaymentsDetail(map);
	}
	
	public Map<String,Object> queryXZXDPaymentsCount(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return paymentsDao.queryXZXDPaymentsCount(map);
	}
	
	public int paymentsAffirm(XZXDPaymentsVO vo) {
		// TODO Auto-generated method stub
		return paymentsDao.paymentsAffirm(vo);
	}
	
	public List<Map<String,Object>> queryXZXDPaymentsFindAll(XZXDPaymentsVO vo){
		return paymentsDao.queryXZXDPaymentsFindAll(vo);
	}
	
}
