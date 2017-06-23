package com.billionsfinance.bas.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.billionsfinance.bas.dao.ClientRepaymentDetailDao;
import com.billionsfinance.bas.entity.ClientRepaymentVO;

public class ClientRepaymentDetailService {

	@Autowired
	private ClientRepaymentDetailDao repaymentDetailDao;
	
	/**
	 * 根据条件查询用户数量
	 * @param alsUser
	 * @return
	 */
	public List<Map<String,Object>> queryRepaymentDetail(Map<String,Object> map){
		return repaymentDetailDao.queryRepaymentDetail(map);
	}
	
	public Map<String,Object> queryRepaymentDetailCount(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return repaymentDetailDao.queryRepaymentDetailCount(map);
	}
	
	public int accountingMark(ClientRepaymentVO vo) {
		// TODO Auto-generated method stub
		return repaymentDetailDao.accountingMark(vo);
	}	
	
	public int cancelAccountingMark(ClientRepaymentVO vo) {
		// TODO Auto-generated method stub
		return repaymentDetailDao.cancelAccountingMark(vo);
	}	
	
	public void contractApprove(ClientRepaymentVO vo) {
		// TODO Auto-generated method stub
		repaymentDetailDao.contractApprove(vo);
	}	
	
	public List<Map<String,Object>> queryRepaymentDetailById(ClientRepaymentVO vo) {
		// TODO Auto-generated method stub
		return repaymentDetailDao.queryRepaymentDetailById(vo);
	}	
	
	public List<Map<String,Object>> queryRepaymentDetailFindAll(ClientRepaymentVO vo) {
		// TODO Auto-generated method stub
		return repaymentDetailDao.queryRepaymentDetailFindAll(vo);
	}	
}
