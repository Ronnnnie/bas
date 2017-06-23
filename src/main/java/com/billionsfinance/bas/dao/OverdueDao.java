package com.billionsfinance.bas.dao;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.OverdueVO;

public interface OverdueDao {

	public List<Map<String, Object>> queryOverdueDetail(Map<String,Object> map);
	
	public List<Map<String, Object>> queryOverdueGather(Map<String,Object> map);
	
	public List<Map<String, Object>> queryOverdueContract(OverdueVO vo);

	public Map<String,Object> queryOverdueDetailCount(Map<String,Object> map);
	
	public Map<String,Object> queryOverdueGatherCount(Map<String,Object> map);
	
	public List<Map<String,Object>> queryOverdueDetailFindAll(OverdueVO overdueVO);
	
	public List<Map<String,Object>> queryOverdueGatherFindAll(Map<String,Object> map);
	
	public List<Map<String, Object>> overdueGatherExport(OverdueVO vo);

	public List<Map<String, Object>> overdueDetailExport(OverdueVO vo);
	
	public int accountingMark(OverdueVO vo);
	
	public int cancelAccountingMark(OverdueVO vo);
	
}
