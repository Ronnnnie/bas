package com.billionsfinance.bas.dao;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.RepaymentDetailCheckAccountVO;
/**
 * 
 * @ClassName: RepaymentDetailCheckAccountDao.java
 * @Description: 还款明细对账表Dao层
 * @author Feima.zhou
 * 
 *         Copyright: Copyright (c) 2017年5月12日下午4:02:22 Company:佰仟金融
 */
public interface RepaymentDetailCheckAccountDao {

	
	public List<Map<String,Object>> queryRepaymentDetailCheckAccount(Map<String,Object> map);
	
	public Map<String,Object> queryRepaymentDetailCheckAccountTotal(Map<String,Object> map);
	
	public List<Map<String,Object>> queryRepaymentDetailCheckAccountFindAll(RepaymentDetailCheckAccountVO vo);
	
	
	
	
}
