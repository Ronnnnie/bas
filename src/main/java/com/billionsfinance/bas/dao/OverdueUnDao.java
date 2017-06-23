package com.billionsfinance.bas.dao;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.OverdueUnVO;

public interface OverdueUnDao {

	public List<Map<String, Object>> queryOverdueUnDetail(Map<String,Object> map);
	
	public List<Map<String, Object>> queryOverdueUnGather(Map<String,Object> map);
	
	public List<Map<String, Object>> queryOverdueUnContract(OverdueUnVO vo);

	public Map<String,Object> queryOverdueUnDetailCount(Map<String,Object> map);
	
	public Map<String,Object> queryOverdueUnGatherCount(Map<String,Object> map);
	
	public List<Map<String,Object>> queryOverdueUnDetailFindAll(OverdueUnVO vo);
	
	public List<Map<String,Object>> queryOverdueUnGatherFindAll(OverdueUnVO vo);
	
	public List<Map<String,Object>> overdueUnDetailExport(OverdueUnVO vo);
	
	public int accountingMark(OverdueUnVO vo);
	
	public int selectAccountingMark(OverdueUnVO vo);
	
	public int cancelAccountingMark(OverdueUnVO vo);
	
}
