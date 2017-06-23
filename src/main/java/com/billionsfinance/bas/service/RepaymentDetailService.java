package com.billionsfinance.bas.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.billionsfinance.bas.dao.RepaymentDetailDao;
import com.billionsfinance.bas.entity.RepaymentDetailVO;

public class RepaymentDetailService {

	@Autowired
	private RepaymentDetailDao repaymentDetailDao;
	
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
	
	public int accountingMark(RepaymentDetailVO vo) {
		// TODO Auto-generated method stub
		return repaymentDetailDao.accountingMark(vo);
	}	
	
	public int cancelAccountingMark(RepaymentDetailVO vo) {
		// TODO Auto-generated method stub
		return repaymentDetailDao.cancelAccountingMark(vo);
	}	
	
	public void contractApprove(RepaymentDetailVO vo) {
		// TODO Auto-generated method stub
		repaymentDetailDao.contractApprove(vo);
	}	
	
	public List<Map<String,Object>> queryRepaymentDetailFindAll(RepaymentDetailVO vo) {
		// TODO Auto-generated method stub
		return repaymentDetailDao.queryRepaymentDetailFindAll(vo);
	}	
}
