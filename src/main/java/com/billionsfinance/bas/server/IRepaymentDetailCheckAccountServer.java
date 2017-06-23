package com.billionsfinance.bas.server;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.entity.RepaymentDetailCheckAccountVO;
/**
 * 
 * @ClassName: IRepaymentDetailCheckAccountServer.java
 * @Description: 还款明细对账表Server层
 * @author Feima.zhou
 * 
 *         Copyright: Copyright (c) 2017年5月12日下午4:08:48 Company:佰仟金融
 */
public interface IRepaymentDetailCheckAccountServer {

	public PageVO queryRepaymentDetailCheckAccount(PageVO pageVo,RepaymentDetailCheckAccountVO vo);
	
	public Map<String,Object> queryRepaymentDetailCheckAccountTotal(RepaymentDetailCheckAccountVO vo);
	
	public List<Map<String,Object>> queryRepaymentDetailCheckAccountFindAll(RepaymentDetailCheckAccountVO vo);

}
