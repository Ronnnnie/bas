package com.billionsfinance.bas.server;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.entity.TrustAllotDetailVO;

public interface ITrustAllotDetailServer {

	public PageVO queryBusinessDetail(PageVO pageVo  , TrustAllotDetailVO vo);
	
	public List<TrustAllotDetailVO> queryTrustAllotDetailById(TrustAllotDetailVO vo);
	
	public Map<String,Object> queryTrustAllotDetailCount(Map<String,Object> map);
	
	public void accountingMark(TrustAllotDetailVO vo);

}
