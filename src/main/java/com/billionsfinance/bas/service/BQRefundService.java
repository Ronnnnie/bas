package com.billionsfinance.bas.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.billionsfinance.bas.dao.BQRefundDao;
import com.billionsfinance.bas.entity.BQRefundVO;

public class BQRefundService {

	@Autowired
	private BQRefundDao bqRefundDao;
	
	/**
	 * 根据条件查询用户数量
	 * @param alsUser
	 * @return
	 */
	public List<Map<String,Object>> queryBusinessDetail(Map<String,Object> map){
		return bqRefundDao.queryBusinessDetail(map);
	}
	
	public List<Map<String,Object>> queryBusinessDetailId(BQRefundVO vo) {
		return bqRefundDao.queryBusinessDetailId(vo);
	}
	
	public List<BQRefundVO> queryBusinessDetailById(String id){
		return bqRefundDao.queryBusinessDetailById(id);
	}
	
	public List<Map<String, Object>> queryBusinessGather(BQRefundVO vo) {
		return bqRefundDao.queryBusinessGather(vo);
	}

	public List<Map<String, Object>> queryBankrollDetail(BQRefundVO vo) {
		// TODO Auto-generated method stub
		return bqRefundDao.queryBankrollDetail(vo);
	}
	
	public Map<String,Object> queryBusinessDetailCount(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return bqRefundDao.queryBusinessDetailCount(map);
	}
	
	public void updateContract(BQRefundVO vo) {
		// TODO Auto-generated method stub
		System.out.println("server--:"+vo);
		bqRefundDao.updateContract(vo);
	}	
	
	public void createContract(BQRefundVO vo) {
		// TODO Auto-generated method stub
		bqRefundDao.createContract(vo);
	}	
	
	public void importContract(List<BQRefundVO> list) {
		// TODO Auto-generated method stub
		System.out.println("BusinessLoanService.importContract()");
		bqRefundDao.importContract(list);
	}	
	
	public void deleteBusinessDetail(String id) {
		// TODO Auto-generated method stub
		bqRefundDao.deleteBusinessDetail(id);
	}	
	
	public void updateBusinessDetail(BQRefundVO vo) {
		// TODO Auto-generated method stub
		bqRefundDao.updateBusinessDetail(vo);
	}	
	
	public void payConfirm(BQRefundVO vo) {
		// TODO Auto-generated method stub
		bqRefundDao.payConfirm(vo);
	}	
	
	public void accountingMark(BQRefundVO vo) {
		// TODO Auto-generated method stub
		bqRefundDao.accountingMark(vo);
	}	
}
