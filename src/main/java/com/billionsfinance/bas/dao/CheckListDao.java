package com.billionsfinance.bas.dao;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.CheckListVO;

public interface CheckListDao {

	
	public List<Map<String,Object>> queryBusinessDetail(Map<String,Object> map);
	
	public Map<String,Object> queryContractCount(Map<String,Object> map);
	
	public void importContract(List<CheckListVO> list);
	
	public void updateContract(List<CheckListVO> list);
	
	public List<CheckListVO> queryCheckListDataFindAll();
	
}
