package com.billionsfinance.bas.server;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.CapitalMitigateVO;
import com.billionsfinance.bas.entity.PageVO;

public interface ICapitalMitigateServer {

	public PageVO queryCapitalMitigate(PageVO pageVo  , CapitalMitigateVO vo);
	
	public List<Map<String,Object>> queryCapitalMitigateFindAll(CapitalMitigateVO vo);
	
	public Map<String,Object> queryCapitalMitigateCount(CapitalMitigateVO vo);

}
