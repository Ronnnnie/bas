package com.billionsfinance.bas.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.billionsfinance.bas.dao.RepaymentDetailCheckAccountDao;
import com.billionsfinance.bas.entity.RepaymentDetailCheckAccountVO;
/**
 * 
 * @ClassName: RepaymentDetailCheckAccountService.java
 * @Description: 还款明细对账表Service层	
 * @author Feima.zhou
 * 
 *         Copyright: Copyright (c) 2017年5月12日下午4:18:48 Company:佰仟金融
 */
public class RepaymentDetailCheckAccountService {

	@Autowired
	private RepaymentDetailCheckAccountDao repaymentDetailCheckAccountDao;
	
	/**
	 * 条件查询还款明细对账表数据
	 * @param  Map<String,Object>
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> queryRepaymentDetailCheckAccount(Map<String,Object> map){
		return repaymentDetailCheckAccountDao.queryRepaymentDetailCheckAccount(map);
	}
	
	/**
	 * 条件查询还款明细对账表数据汇总
	 * @param  Map<String,Object> 
	 * @return Map<String,Object>
	 */
	public Map<String,Object> queryRepaymentDetailCheckAccountTotal(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return repaymentDetailCheckAccountDao.queryRepaymentDetailCheckAccountTotal(map);
	}
	
	/**
	 * 查询还款明细对账表数据(非分页,主要用于导出)
	 * @param  Map<String,Object> 
	 * @return Map<String,Object>
	 */
	public List<Map<String,Object>> queryRepaymentDetailCheckAccountFindAll(RepaymentDetailCheckAccountVO vo) {
		// TODO Auto-generated method stub
		return repaymentDetailCheckAccountDao.queryRepaymentDetailCheckAccountFindAll(vo);
	}
	
}
