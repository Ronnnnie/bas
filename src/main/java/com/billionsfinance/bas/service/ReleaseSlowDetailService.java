package com.billionsfinance.bas.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.billionsfinance.bas.dao.ReleaseSlowDetailDao;
import com.billionsfinance.bas.entity.ReleaseSlowDetailVO;

public class ReleaseSlowDetailService {

	@Autowired
	private ReleaseSlowDetailDao releaseSlowDetailDao;
	
	/**
	 * 根据条件查询用户数量
	 * @param alsUser
	 * @return
	 */
	public List<Map<String,Object>> queryRepaymentDetail(Map<String,Object> map){
		return releaseSlowDetailDao.queryRepaymentDetail(map);
	}
	
	public Map<String,Object> queryRepaymentDetailCount(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return releaseSlowDetailDao.queryRepaymentDetailCount(map);
	}
	
	public int accountingMark(ReleaseSlowDetailVO vo) {
		// TODO Auto-generated method stub
		return releaseSlowDetailDao.accountingMark(vo);
	}	
	
	public int cancelAccountingMark(ReleaseSlowDetailVO vo) {
		// TODO Auto-generated method stub
		return releaseSlowDetailDao.cancelAccountingMark(vo);
	}	
	
	public void contractApprove(ReleaseSlowDetailVO vo) {
		// TODO Auto-generated method stub
		releaseSlowDetailDao.contractApprove(vo);
	}	
	
	public List<Map<String,Object>> queryRepaymentDetailFindAll(ReleaseSlowDetailVO vo) {
		// TODO Auto-generated method stub
		return releaseSlowDetailDao.queryRepaymentDetailFindAll(vo);
	}	
}
