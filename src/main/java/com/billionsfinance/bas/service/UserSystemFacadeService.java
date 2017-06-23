package com.billionsfinance.bas.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.billionsfinance.bas.dao.UserSystemFacadeDao;

public class UserSystemFacadeService {

	@Autowired
	private UserSystemFacadeDao sysUserFacadeDao;
	
	public List<Map<String, Object>> getSystemUserAllBySysCode(
			String systemCode, String cityName) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public List<Map<String, Object>> getUserLowerBySysCodeAndRespon(
			String systemCode, String userCode, String respon) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public List<Map<String, Object>> getUserHigherBySysCodeAndRespon(
			String systemCode, String userCode, String respon) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public List<Map<String, Object>> getUserHigherAllBySysCodeAndRespon(
			String systemCode, String userCode, String respon) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Map<String, Object>> getSysUserData(String systemCode,String startDate,String endDate){
		Map map = new HashMap();
		map.put("systemCode", systemCode);
		map.put("startDate", startDate);
		map.put("endDate",endDate );
		return sysUserFacadeDao.getSysUserData(map);
	}

}
