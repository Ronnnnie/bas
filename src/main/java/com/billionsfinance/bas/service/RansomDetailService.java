package com.billionsfinance.bas.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.billionsfinance.bas.dao.RansomDetailDao;
import com.billionsfinance.bas.entity.RansomDetailVO;

public class RansomDetailService {

	@Autowired
	private RansomDetailDao ransomDetailDao;
	
	/**
	 * 根据条件查询用户数量
	 * @param alsUser
	 * @return
	 */
	public List<Map<String,Object>> queryRepaymentDetail(Map<String,Object> map){
		return ransomDetailDao.queryRepaymentDetail(map);
	}
	
	public Map<String,Object> queryRepaymentDetailCount(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return ransomDetailDao.queryRepaymentDetailCount(map);
	}
	
	public int accountingMark(RansomDetailVO vo) {
		// TODO Auto-generated method stub
		return ransomDetailDao.accountingMark(vo);
	}	
	
	public int cancelAccountingMark(RansomDetailVO vo) {
		// TODO Auto-generated method stub
		return ransomDetailDao.cancelAccountingMark(vo);
	}	
	
	public void contractApprove(RansomDetailVO vo) {
		// TODO Auto-generated method stub
		ransomDetailDao.contractApprove(vo);
	}	
	
	public List<RansomDetailVO> queryRepaymentDetailById(RansomDetailVO vo) {
		// TODO Auto-generated method stub
		return ransomDetailDao.queryRepaymentDetailById(vo);
	}	
	
	public List<Map<String,Object>> queryRepaymentDetailFindAll(RansomDetailVO vo) {
		// TODO Auto-generated method stub
		return ransomDetailDao.queryRepaymentDetailFindAll(vo);
	}	
}
