package com.billionsfinance.bas.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.billionsfinance.bas.dao.CompensatoryDetailDao;
import com.billionsfinance.bas.entity.CompensatoryDetailVO;

public class CompensatoryDetailService {

	@Autowired
	private CompensatoryDetailDao compensatoryDao;
	
	/**
	 * 根据条件查询用户数量
	 * @param alsUser
	 * @return
	 */
	public List<Map<String,Object>> queryRepaymentDetail(Map<String,Object> map){
		return compensatoryDao.queryRepaymentDetail(map);
	}
	
	public Map<String,Object> queryRepaymentDetailCount(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return compensatoryDao.queryRepaymentDetailCount(map);
	}
	
	public int accountingMark(CompensatoryDetailVO vo) {
		// TODO Auto-generated method stub
		return compensatoryDao.accountingMark(vo);
	}	
	
	public int cancelAccountingMark(CompensatoryDetailVO vo) {
		// TODO Auto-generated method stub
		return compensatoryDao.cancelAccountingMark(vo);
	}	
	
	public void contractApprove(CompensatoryDetailVO vo) {
		// TODO Auto-generated method stub
		compensatoryDao.contractApprove(vo);
	}	
	
	public List<CompensatoryDetailVO> queryRepaymentDetailById(CompensatoryDetailVO vo) {
		// TODO Auto-generated method stub
		return compensatoryDao.queryRepaymentDetailById(vo);
	}	
	
	public List<Map<String,Object>> queryRepaymentDetailFindAll(CompensatoryDetailVO vo) {
		// TODO Auto-generated method stub
		return compensatoryDao.queryRepaymentDetailFindAll(vo);
	}	
}
