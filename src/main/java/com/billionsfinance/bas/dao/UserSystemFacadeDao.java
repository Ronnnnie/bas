package com.billionsfinance.bas.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface UserSystemFacadeDao {

	/**
	 * 根据系统编号或城市获取所属所有用户
	 * 
	 * 返回  a1.用户登录名、a2.用户姓名、a3.角色ID、a4.角色名称、a5.用户所在城市名称
	 * @param systemCode 系统编号
	 * @param cityName  城市名称
	 * @return
	 */
	public List<Map<String, Object>> getSystemUserAllBySysCode(@Param("systemCode")String systemCode,@Param("cityName")String cityName);
	
	/**
	 * 根据系统编号，用户，职位获取该用户的角色的下级角色的用户信息
	 * 
	 * 返回所有满足用户的a.用户ID(编号)、b.用户姓名、c.角色ID、d.角色名称、e.员工类型(内部员工，外部员工)、f.城市(员工所在城市)、g.手机号码
	 * @param systemCode  系统编号
	 * @param userCode    用户
	 * @param respon      当前职位
	 * @return
	 */
	public List<Map<String, Object>> getUserLowerBySysCodeAndRespon(@Param("systemCode")String systemCode,
			@Param("userCode")String userCode,@Param("respon")String respon);
	
	/**
	 * 根据系统编号，用户，职位获取该用户的角色的上级角色的用户信息（每个用户只有一个上级）
	 * 返回上级用户的a.用户ID(编号)、b.用户姓名、c.角色ID、d.角色名称、e.员工类型(内部员工，外部员工)、f.城市(员工所在城市)、g.手机号码
	 * 
	 * @param systemCode  系统编号
	 * @param userCode    用户
	 * @param respon      当前职位
	 * @return
	 */
	public List<Map<String, Object>> getUserHigherBySysCodeAndRespon(@Param("systemCode")String systemCode,
			@Param("userCode")String userCode,@Param("respon")String respon);
	
	/**
	 * 根据系统编号，用户，职位获取该用户的角色的同级，以及上级角色的用户信息
	 * 
	 * 返回所有满足用户的a.用户ID(编号)、b.用户姓名、c.角色ID、d.角色名称、e.员工类型(内部员工，外部员工)、f.城市(员工所在城市)、g.手机号码
	 * @param systemCode  系统编号
	 * @param userCode    用户
	 * @param respon      当前职位
	 * @return
	 */
	public List<Map<String, Object>> getUserHigherAllBySysCodeAndRespon(@Param("systemCode")String systemCode,
			@Param("userCode")String userCode,@Param("respon")String respon);
	
	public List<Map<String, Object>> getSysUserData(String systemCode,
			String startDate,String endDate);
	public List<Map<String, Object>> getSysUserData(Map map);

}
