package com.billionsfinance.bas.server;

import java.util.List;
import java.util.Map;


/**
 * 获取系统用户信息
 * 根据系统编号查询系统用户
 * @author zhangfei
 *
 */
public interface IUserSystemFacadeServer {

	/**
	 * 根据系统编号或城市获取所属所有用户
	 * 
	 * 返回  a1.用户登录名、a2.用户姓名、a3.角色ID、a4.角色名称、a5.用户所在城市名称
	 * @param systemCode 系统编号
	 * @param cityName  城市名称
	 * @return
	 */
	public List<Map<String, Object>> getSystemUserAllBySysCode(String systemCode,String cityName);
	
	/**
	 * 根据系统编号，用户，职位获取该用户的角色的下级角色的用户信息
	 * 
	 * 返回所有满足用户的a.用户ID(编号)、b.用户姓名、c.角色ID、d.角色名称、e.员工类型(内部员工，外部员工)、f.城市(员工所在城市)、g.手机号码
	 * @param systemCode  系统编号
	 * @param userCode    用户
	 * @param respon      当前职位
	 * @return
	 */
	public List<Map<String, Object>> getUserLowerBySysCodeAndRespon(String systemCode,String userCode,String respon);
	
	/**
	 * 根据系统编号，用户，职位获取该用户的角色的上级角色的用户信息（每个用户只有一个上级）
	 * 返回上级用户的a.用户ID(编号)、b.用户姓名、c.角色ID、d.角色名称、e.员工类型(内部员工，外部员工)、f.城市(员工所在城市)、g.手机号码
	 * 
	 * @param systemCode  系统编号
	 * @param userCode    用户
	 * @param respon      当前职位
	 * @return
	 */
	public List<Map<String, Object>> getUserHigherBySysCodeAndRespon(String systemCode,String userCode,String respon);
	
	/**
	 * 根据系统编号，用户，职位获取该用户的角色的同级，以及上级角色的用户信息
	 * 
	 * 返回所有满足用户的a.用户ID(编号)、b.用户姓名、c.角色ID、d.角色名称、e.员工类型(内部员工，外部员工)、f.城市(员工所在城市)、g.手机号码
	 * @param systemCode  系统编号
	 * @param userCode    用户
	 * @param respon      当前职位
	 * @return
	 */
	public List<Map<String, Object>> getUserHigherAllBySysCodeAndRespon(String systemCode,String userCode,String respon);
	
    /**
     * 根据时间戳获取前天更新的用户
     * 
     * 返回数据:
         a1.用户工号、a2.用户姓名、a3.用户电话、a4.用户邮箱、a5.用户所在城市名称、a6.用户类型、a7.用户状态、a8.最后更新时间
     * @param systemCode 系统编号
     * @param dataTime 当前时间戳，时间格式:2016-01-20 10:20:60
     * @return  系统用户
     */
	public List<Map<String, Object>> getSysUserData(String systemCode,String dataTime);
	
}
