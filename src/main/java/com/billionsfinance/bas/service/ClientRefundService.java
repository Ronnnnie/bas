package com.billionsfinance.bas.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.billionsfinance.bas.dao.ClientRefundDao;
import com.billionsfinance.bas.entity.ClientRefundVO;

public class ClientRefundService {

	@Autowired
	private ClientRefundDao clientRefundDao;
	
	/**
	 * 根据条件查询用户数量
	 * @param alsUser
	 * @return
	 */
	public List<Map<String,Object>> queryBusinessDetail(Map<String,Object> map){
		return clientRefundDao.queryBusinessDetail(map);
	}
	
	public List<Map<String,Object>> queryBusinessDetailId(ClientRefundVO vo) {
		return clientRefundDao.queryBusinessDetailId(vo);
	}
	
	public List<ClientRefundVO> queryBusinessDetailById(String id){
		return clientRefundDao.queryBusinessDetailById(id);
	}
	
	public List<Map<String, Object>> queryBusinessGather(ClientRefundVO vo) {
		return clientRefundDao.queryBusinessGather(vo);
	}

	public List<Map<String, Object>> queryBankrollDetail(ClientRefundVO vo) {
		// TODO Auto-generated method stub
		return clientRefundDao.queryBankrollDetail(vo);
	}
	
	public Map<String,Object> queryBusinessDetailCount(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return clientRefundDao.queryBusinessDetailCount(map);
	}
	
	public void updateContract(ClientRefundVO vo) {
		// TODO Auto-generated method stub
		System.out.println("server--:"+vo);
		clientRefundDao.updateContract(vo);
	}	
	
	public void createContract(ClientRefundVO vo) {
		// TODO Auto-generated method stub
		clientRefundDao.createContract(vo);
	}	
	
	public void importContract(List<ClientRefundVO> list) {
		// TODO Auto-generated method stub
		System.out.println("BusinessLoanService.importContract()");
		clientRefundDao.importContract(list);
	}	
	
	public void deleteBusinessDetail(String id) {
		// TODO Auto-generated method stub
		clientRefundDao.deleteBusinessDetail(id);
	}	
	
	public void updateBusinessDetail(ClientRefundVO vo) {
		// TODO Auto-generated method stub
		clientRefundDao.updateBusinessDetail(vo);
	}	
	
	public void payConfirm(ClientRefundVO vo) {
		// TODO Auto-generated method stub
		clientRefundDao.payConfirm(vo);
	}	
	
	public void accountingMark(ClientRefundVO vo) {
		// TODO Auto-generated method stub
		clientRefundDao.accountingMark(vo);
	}	
}
