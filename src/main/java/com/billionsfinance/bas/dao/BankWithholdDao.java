package com.billionsfinance.bas.dao;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.BankWithholdVO;
/**
 * 
 * @ClassName: BankWithholdDao.java
 * @Description: 每日银行代扣核对表Dao层
 * @author Feima.zhou
 * 
 *         Copyright: Copyright (c) 2017年5月9日上午11:28:54 Company:佰仟金融
 */
public interface BankWithholdDao {

	
	public List<Map<String,Object>> queryBankWithhold(Map<String,Object> map);
	
	public List<Map<String,Object>> queryBankWithholdFindAll(BankWithholdVO vo);
	
	public Map<String,Object> queryBankWithholdCount(Map<String,Object> map);
	
	
	
	
}
