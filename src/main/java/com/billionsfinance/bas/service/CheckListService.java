package com.billionsfinance.bas.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.billionsfinance.bas.dao.CheckListDao;
import com.billionsfinance.bas.entity.CheckListVO;

public class CheckListService {

	@Autowired
	private CheckListDao checkListDao;
	
	/**
	 * 根据条件查询用户数量
	 * @param alsUser
	 * @return
	 */
	public List<Map<String,Object>> queryBusinessDetail(Map<String,Object> map){
		return checkListDao.queryBusinessDetail(map);
	}
	
	public void importContract(List<CheckListVO> list) {
		// TODO Auto-generated method stub
		checkListDao.importContract(list);
	}
	
	public void updateContract(List<CheckListVO> list) {
		// TODO Auto-generated method stub
		checkListDao.updateContract(list);
	}
	
	public Map<String,Object> queryContractCount(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return checkListDao.queryContractCount(map);
	}
	
	public List<CheckListVO> queryCheckListDataFindAll() {
		// TODO Auto-generated method stub
		return checkListDao.queryCheckListDataFindAll();
	}
}
