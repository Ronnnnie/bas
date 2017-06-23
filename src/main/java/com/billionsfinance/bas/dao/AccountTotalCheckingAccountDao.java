package com.billionsfinance.bas.dao;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.AccountTotalCheckingAccountVO;
/**
 * 
 * @ClassName: AccountTotalCheckingAccountDao.java
 * @Description: 总账对账表Dao层
 * @author Feima.zhou
 * 
 *         Copyright: Copyright (c) 2017年5月12日下午3:58:44 Company:佰仟金融
 */
public interface AccountTotalCheckingAccountDao {

	
	public List<Map<String,Object>> queryAccountTotalCheckingAccount(Map<String,Object> map);
	
	public Map<String,Object> queryAccountTotalCheckingAccountTotal(Map<String,Object> map);
	
	public List<Map<String,Object>> queryAccountTotalCheckingAccountFindAll(AccountTotalCheckingAccountVO vo);
	
	
	
	
}
