package com.billionsfinance.bas.server;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.OverdueVO;
import com.billionsfinance.bas.entity.PageVO;

public interface IOverdueServer {

	public PageVO queryOverdueDetail(PageVO pageVo  , OverdueVO vo);
	
	public PageVO queryOverdueGather(PageVO pageVo  , OverdueVO vo);
	
	public List<Map<String,Object>> queryOverdueContract(OverdueVO vo);
	
	public List<Map<String,Object>> queryOverdueDetailFindAll(OverdueVO vo);
	
	public List<Map<String,Object>> queryOverdueGatherFindAll(OverdueVO vo);
	
	public List<Map<String,Object>> overdueGatherExport(OverdueVO vo);
	
	public List<Map<String,Object>> overdueDetailExport(OverdueVO vo);
	
	public Map<String,Object> queryOverdueDetailCount(OverdueVO vo);
	
	public Map<String,Object> queryOverdueGatherCount(OverdueVO vo);
	
	public int accountingMark(OverdueVO vo);
	
	public int cancelAccountingMark(OverdueVO vo);


}
