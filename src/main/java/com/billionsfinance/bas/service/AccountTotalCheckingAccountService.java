package com.billionsfinance.bas.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.billionsfinance.bas.dao.AccountTotalCheckingAccountDao;
import com.billionsfinance.bas.entity.AccountTotalCheckingAccountVO;
/**
 * 
 * @ClassName: AccountTotalCheckingAccountService.java
 * @Description: 总账对账表Service层
 * @author Feima.zhou
 * 
 *         Copyright: Copyright (c) 2017年5月12日下午4:15:39 Company:佰仟金融
 */
public class AccountTotalCheckingAccountService {

	@Autowired
	private AccountTotalCheckingAccountDao accountTotalCheckingAccountDao;
	
	/**
	 * 条件查询总账对账表数据
	 * @param  Map<String,Object>
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> queryAccountTotalCheckingAccount(Map<String,Object> map){
		return accountTotalCheckingAccountDao.queryAccountTotalCheckingAccount(map);
	}
	
	/**
	 * 条件查询总账对账表数据汇总
	 * @param  Map<String,Object> 
	 * @return Map<String,Object>
	 */
	public Map<String,Object> queryAccountTotalCheckingAccountTotal(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return accountTotalCheckingAccountDao.queryAccountTotalCheckingAccountTotal(map);
	}
	
	/**
	 * 条件查询总账对账表数据汇总
	 * @param  Map<String,Object> 
	 * @return Map<String,Object>
	 */
	public List<Map<String,Object>> queryAccountTotalCheckingAccountFindAll(AccountTotalCheckingAccountVO vo) {
		// TODO Auto-generated method stub
		return accountTotalCheckingAccountDao.queryAccountTotalCheckingAccountFindAll(vo);
	}
	
}
