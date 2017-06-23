package com.billionsfinance.bas.dao;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.DepartVO;

public interface DepartDao {
	
	/**
	 * 加载部门信息
	 * @return
	 */
	public List<DepartVO> loadDepart();

	public List<Map<String, Object>> queryAllDepart();

}
