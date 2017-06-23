package com.billionsfinance.bas.server;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.AccountTotalCheckingAccountVO;
import com.billionsfinance.bas.entity.PageVO;
/**
 * 
 * @ClassName: IAccountTotalCheckingAccountServer.java
 * @Description: 总账对账表Server层
 * @author Feima.zhou
 * 
 *         Copyright: Copyright (c) 2017年5月12日下午4:07:03 Company:佰仟金融
 */
public interface IAccountTotalCheckingAccountServer {

	public PageVO queryAccountTotalCheckingAccount(PageVO pageVo  , AccountTotalCheckingAccountVO vo);
	
	public List<Map<String,Object>> queryAccountTotalCheckingAccountFindAll(AccountTotalCheckingAccountVO vo);
	
	public Map<String,Object> queryAccountTotalCheckingAccountTotal(AccountTotalCheckingAccountVO vo);

}
