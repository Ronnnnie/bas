package com.billionsfinance.bas.server;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.OverdueUnVO;
import com.billionsfinance.bas.entity.PageVO;

public interface IOverdueUnServer {

	public PageVO queryOverdueUnDetail(PageVO pageVo  , OverdueUnVO vo);
	
	public PageVO queryOverdueUnGather(PageVO pageVo  , OverdueUnVO vo);
	
	public List<Map<String,Object>> queryOverdueUnContract(OverdueUnVO vo);
	
	public List<Map<String,Object>> queryOverdueUnDetailFindAll(OverdueUnVO vo);
	
	public List<Map<String,Object>> queryOverdueUnGatherFindAll(OverdueUnVO vo);
	
	public List<Map<String,Object>> overdueUnDetailExport(OverdueUnVO vo);
	
	public Map<String,Object> queryOverdueUnCount(OverdueUnVO vo);
	
	public Map<String,Object> queryOverdueUnDetailCount(OverdueUnVO vo);
	
	public Map<String,Object> queryOverdueUnGatherCount(OverdueUnVO vo);
	
	public int accountingMark(OverdueUnVO vo);
	
	public int selectAccountingMark(OverdueUnVO vo);
	
	public int cancelAccountingMark(OverdueUnVO vo);


}
