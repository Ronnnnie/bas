package com.billionsfinance.bas.server.impl;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.server.IUserSystemFacadeServer;
import com.billionsfinance.bas.service.UserSystemFacadeService;
import com.billionsfinance.bas.util.DateUtil;
import com.billionsfinance.bas.util.SpringService;

public class UserSystemFacadeServer implements IUserSystemFacadeServer {

	
	private UserSystemFacadeService sysUserFacadeService = SpringService.SYSUSERFACADESERVICE;
	
	@Override
	public List<Map<String, Object>> getSystemUserAllBySysCode(
			String systemCode, String cityName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> getUserLowerBySysCodeAndRespon(
			String systemCode, String userCode, String respon) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> getUserHigherBySysCodeAndRespon(
			String systemCode, String userCode, String respon) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> getUserHigherAllBySysCodeAndRespon(
			String systemCode, String userCode, String respon) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Map<String, Object>> getSysUserData(String systemCode,String dataTime){
		String startDate="";
		String endDate="";
		String data=DateUtil.dateNow("yyyy-MM-dd");
		if(dataTime != null && !"".equals(dataTime)){
			data = DateUtil.getSpecifiedDayBefore(dataTime);
		}
		startDate =data+ " 00:00:00";
		endDate = data+" 23:59:59";
		List<Map<String, Object>>  list = sysUserFacadeService.getSysUserData(systemCode, startDate,endDate);
		return list;
	}

}
