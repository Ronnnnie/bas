package com.billionsfinance.bas.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.billionsfinance.bas.dao.BankWithholdDao;
import com.billionsfinance.bas.entity.BankWithholdVO;

public class BankWithholdService {

	@Autowired
	private BankWithholdDao bankWithholdDao;
	
	/**
	 * 条件查询每日银行代扣核对数据
	 * @param  Map<String,Object>
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> queryBankWithhold(Map<String,Object> map){
		return bankWithholdDao.queryBankWithhold(map);
	}
	
	/**
	 * 条件查询每日银行代扣核对数据Count
	 * @param  Map<String,Object> 
	 * @return Map<String,Object>
	 */
	public Map<String,Object> queryBankWithholdCount(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return bankWithholdDao.queryBankWithholdCount(map);
	}
	
	/**
	 * 查询所有每日银行代扣核对数据
	 * @param  BankWithholdVO
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> queryBankWithholdFindAll(BankWithholdVO vo){
		return bankWithholdDao.queryBankWithholdFindAll(vo);
	}
}
