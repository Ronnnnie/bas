package com.billionsfinance.bas.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.billionsfinance.bas.dao.BusinessLoanDao;
import com.billionsfinance.bas.entity.BusinessLoanVO;

public class BusinessLoanService {

	@Autowired
	private BusinessLoanDao businessLoanDao;
	
	/**
	 * 根据条件查询用户数量
	 * @param alsUser
	 * @return
	 */
	public List<Map<String,Object>> queryBusinessDetail(Map<String,Object> map){
		return businessLoanDao.queryBusinessDetail(map);
	}
	
	public List<Map<String,Object>> queryBusinessDetailId(BusinessLoanVO blVO) {
		return businessLoanDao.queryBusinessDetailId(blVO);
	}
	
	public List<BusinessLoanVO> queryBusinessDetailById(String id){
		return businessLoanDao.queryBusinessDetailById(id);
	}
	
	public List<Map<String, Object>> queryBusinessGather(BusinessLoanVO blVO) {
		return businessLoanDao.queryBusinessGather(blVO);
	}

	public List<Map<String, Object>> queryBankrollDetail(BusinessLoanVO blVO) {
		// TODO Auto-generated method stub
		return businessLoanDao.queryBankrollDetail(blVO);
	}
	
	public Map<String,Object> queryBusinessDetailCount(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return businessLoanDao.queryBusinessDetailCount(map);
	}
	
	public void updateContract(BusinessLoanVO blVO) {
		// TODO Auto-generated method stub
		System.out.println("server--:"+blVO);
		businessLoanDao.updateContract(blVO);
	}	
	
	public void createContract(BusinessLoanVO blVO) {
		// TODO Auto-generated method stub
		businessLoanDao.createContract(blVO);
	}	
	
	public void importContract(List<BusinessLoanVO> list) {
		// TODO Auto-generated method stub
		System.out.println("BusinessLoanService.importContract()");
		businessLoanDao.importContract(list);
	}	
	
	public void deleteBusinessDetail(String id) {
		// TODO Auto-generated method stub
		businessLoanDao.deleteBusinessDetail(id);
	}	
	
	public void updateBusinessDetail(BusinessLoanVO blVO) {
		// TODO Auto-generated method stub
		businessLoanDao.updateBusinessDetail(blVO);
	}	
	
	public void payConfirm(BusinessLoanVO blVO) {
		// TODO Auto-generated method stub
		businessLoanDao.payConfirm(blVO);
	}	
	
	public void accountingMark(BusinessLoanVO blVO) {
		// TODO Auto-generated method stub
		businessLoanDao.accountingMark(blVO);
	}	
}
