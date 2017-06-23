package com.billionsfinance.bas.server;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.BankWithholdVO;
import com.billionsfinance.bas.entity.PageVO;

public interface IBankWithholdServer {

	public PageVO queryBankWithhold(PageVO pageVo  , BankWithholdVO vo);
	
	public Map<String,Object> queryBankWithholdCount(BankWithholdVO vo);
	
	public List<Map<String,Object>> queryBankWithholdFindAll(BankWithholdVO vo);
	

}
