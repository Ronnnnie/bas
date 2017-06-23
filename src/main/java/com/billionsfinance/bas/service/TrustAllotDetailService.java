package com.billionsfinance.bas.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.billionsfinance.bas.dao.TrustAllotDetailDao;
import com.billionsfinance.bas.entity.TrustAllotDetailVO;

public class TrustAllotDetailService {

	@Autowired
	private TrustAllotDetailDao trustAllotDetailDao;
	
	/**
	 * 根据条件查询用户数量
	 * @param alsUser
	 * @return
	 */
	public List<Map<String,Object>> queryBusinessDetail(Map<String,Object> map){
		return trustAllotDetailDao.queryTrustAllotDetail(map);
	}
	
	public Map<String,Object> queryTrustAllotDetailCount(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return trustAllotDetailDao.queryTrustAllotDetailCount(map);
	}
	
	public void accountingMark(TrustAllotDetailVO vo) {
		// TODO Auto-generated method stub
		trustAllotDetailDao.accountingMark(vo);
	}	
	
	public List<TrustAllotDetailVO> queryTrustAllotDetailById(TrustAllotDetailVO vo) {
		// TODO Auto-generated method stub
		return trustAllotDetailDao.queryTrustAllotDetailById(vo);
	}	
}
