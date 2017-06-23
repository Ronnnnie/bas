package com.billionsfinance.bas.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.billionsfinance.bas.dao.ContractDao;
import com.billionsfinance.bas.entity.ContractVO;

public class ContractService {

	@Autowired
	private ContractDao contarctDao;
	
	/**
	 * 根据条件查询用户数量
	 * @param alsUser
	 * @return
	 */
	public List<Map<String,Object>> queryBusinessDetail(Map<String, Object> map){
		return contarctDao.queryBusinessDetail(map);
	}
	
	public List<Map<String, Object>> queryBusinessGather(ContractVO brVO) {
		return contarctDao.queryBusinessGather(brVO);
	}

	public List<Map<String, Object>> queryBankrollDetail(ContractVO brVO) {
		// TODO Auto-generated method stub
		return contarctDao.queryBankrollDetail(brVO);
	}
	
	public Map<String,Object> queryBusinessDetailCount(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return contarctDao.queryBusinessDetailCount(map);
	}
	
	public void updateContract(ContractVO brVO) {
		// TODO Auto-generated method stub
		System.out.println("server--:"+brVO);
		contarctDao.updateContract(brVO);
	}	
	
	public void createContract(ContractVO brVO) {
		// TODO Auto-generated method stub
		contarctDao.createContract(brVO);
	}	
	
	public void importContract(List<ContractVO> list) {
		// TODO Auto-generated method stub
		contarctDao.importContract(list);
	}
	
	public void payConfirm(ContractVO vo) {
		// TODO Auto-generated method stub
		contarctDao.payConfirm(vo);
	}	
	
	public void accountingMark(ContractVO vo) {
		// TODO Auto-generated method stub
		contarctDao.accountingMark(vo);
	}	
	
	public List<Map<String, Object>> queryBusinessDetailId(ContractVO vo) {
		// TODO Auto-generated method stub
		return contarctDao.queryBusinessDetailId(vo);
	}	
}
