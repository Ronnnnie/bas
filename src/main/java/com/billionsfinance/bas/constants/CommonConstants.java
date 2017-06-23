package com.billionsfinance.bas.constants;

/**
 * @ClassName: CommonConstants
 * @Description: 常用常量
 * @author zhanghs
 * @date 2016年6月28日 下午5:11:16 Copyright: Copyright (c) 2016 Company:佰仟金融
 */
public interface CommonConstants {

	/**
	 * @Fields EMPLOYEE_TRANS_FLAG_N : 员工转入标志<br>
	 *         Y-已转入 N-未转入
	 */
	String EMPLOYEE_TRANS_FLAG_N = "N";
	/**
	 * @Fields EMPLOYEE_TRANS_FLAG_Y : 员工转入标志<br>
	 *         Y-已转入 N-未转入
	 */
	String EMPLOYEE_TRANS_FLAG_Y = "Y";

	/**
	 * @Fields MAP_KEY_JOB_NO : Map key jobNo<br>
	 *         员工号
	 */
	String MAP_KEY_JOB_NO = "jobNo";

	/**
	 * @Fields MAP_KEY_ISUSED : Map key jobNo<br>
	 *         是否有效标志
	 */
	String MAP_KEY_ISUSED = "isused";

	String MAP_KEY_LIST = "list";

	/**
	 * @Fields MAP_KEY_COUNT_DATE : Map key countDate<br>
	 *         计算日期
	 */
	String MAP_KEY_COUNT_DATE = "countDate";
	
	/**
	 * @Fields MAP_KEY_COUNT_DATE : Map key countDate<br>
	 *         计算日期
	 */
	String MAP_KEY_START_COUNT_DATE = "startCountDate";
	
	/**
	 * @Fields MAP_KEY_COUNT_DATE : Map key countDate<br>
	 *         计算日期
	 */
	String MAP_KEY_END_COUNT_DATE = "endCountDate";

	/**
	 * @Fields MAP_KEY_USER_CODE : 员工号 userCode
	 */
	String MAP_KEY_USER_CODE = "userCode";
	
	/**
	 * @Fields MAP_KEY_USER_NAME : 员工姓名 userName
	 */
	String MAP_KEY_USER_NAME = "userName";
	
	/**
	 * @Fields MAP_KEY_EMPLOYEE_NAME : 薪酬系统-员工姓名 employeeName
	 */
	String MAP_KEY_EMPLOYEE_NAME = "employeeName";

	/**
	 * @Fields MAP_KEY_COLUMNS : Map key columns
	 */
	String MAP_KEY_COLUMNS = "columns";

	/**
	 * @Fields MAP_KEY_IORU : Map key iorU
	 */
	String MAP_KEY_IORU = "iorU";

	/**
	 * @Fields MAP_KEY_ENTRY_DATE : Map key entryDate
	 */
	String MAP_KEY_ENTRY_DATE = "entryDate";

	/**
	 * @Fields MAP_KEY_TRANS_FLAG : Map key transFlag
	 */
	String MAP_KEY_TRANS_FLAG = "transFlag";

	/**
	 * @Fields SUCCESS_NUMBER_FLAG_0 : 成功数字标志：0 表示成功 1表示失败
	 */
	String SUCCESS_NUMBER_FLAG_0 = "0";

	/**
	 * @Fields SUCCESS_NUMBER_FLAG_1 : 成功数字标志：0 表示成功 1表示失败
	 */
	String SUCCESS_NUMBER_FLAG_1 = "1";

	/**
	 * @Fields DATE_FORMART_YYYY_MM : 日期格式 -获取当前月份 xxxx/xx
	 */
	String DATE_FORMART_YYYY_MM = "yyyy/MM";
	
	/**
	 * @Fields DATE_FORMART_YYYY_MM_C : 日期格式 -获取当前月份 xxxx年xx月
	 */
	String DATE_FORMART_YYYY_MM_C = "yyyy年MM月";

	/**
	 * @Fields STRING_NULL : 字符串 null
	 */
	String STRING_NULL = "null";

	/**
	 * @Fields SYMBOL_SINGLE_QUOTE : 单引号 '
	 */
	String SYMBOL_SINGLE_QUOTE = "'";

	/**
	 * @Fields SYMBOL_COMMA : 逗号
	 */
	String SYMBOL_COMMA = ",";

	/**
	 * @Fields PROJECT_TYPE_COUNT : 项目类型：1.基础信息类 2.计算类 3.上传类 4.其他
	 */
	String PROJECT_TYPE_FORMULA = "2";

	/**
	 * @Fields EMPLOYEE_BASE_INFO : 员工是否存在<br>
	 *         -1-已存在薪资表 0-不存在薪资表和金蝶系统临时表 1-已存在金蝶系统临时表
	 */
	Integer EMPLOYEE_BASE_EXIST = -1;
	Integer EMPLOYEE_NOT_EXIST = 0;
	Integer EMPLOYEE_TEMP_EXIST = 1;

//	String PROJECT_TYPE_FORMULA = "2";

	/**
	 * @Fields USER_STATUS_FLAG_VALID : 用户状态 1.有效 0.无效
	 */
	String USER_STATUS_FLAG_VALID = "1";
	String USER_STATUS_FLAG_AVALID = "0";

	/**
	 * @Fields PROJECT_BASE_INFO_FLAG_VALID : 项目是否有效标志： 1.有效 0无效
	 */
	String PROJECT_BASE_INFO_FLAG_VALID = "1";
	/**
	 * @Fields PROJECT_BASE_INFO_FLAG_AVALID : 项目是否有效标志： 1.有效 0无效
	 */
	String PROJECT_BASE_INFO_FLAG_AVALID = "0";
	

	/**
	 * @Fields MAP_KEY_ISLOCKED : Map key countDate<br>
	 *         结转锁定
	 */
	String MAP_KEY_ISLOCKED = "isLocked";
	

	
	/**
	  * @Fields SALARY_DATA_ISLOCKED : 结转锁定标示1.锁定 0.未锁<br>
	  * 锁定
	  */
	String SALARY_DATA_ISLOCKED = "1";
	
	/**
	  * @Fields SALARY_DATA_ISLOCKED : 结转锁定标示1.锁定 0.未锁 <br>
	  * 未锁
	  */
	String SALARY_DATA_UNLOCKED = "0";
	
	/**
	  * @Fields PRAY_ROLL_TEMPLETE_FILE : 工资模板文件
	  */
	String PRAY_ROLL_TEMPLETE_FILE = "工资模板.docx";
	
	/**
	  * @Fields PRAY_ROLL_TEMPLETE_FILE_ADD : 工资模板文件
	  */
	String PRAY_ROLL_TEMPLETE_FILE_ADD = "pay.roll.templete.down.file";
	
	/**
	  * @Fields SALARY_COUNT_AFTER_XSLX :工资计算后的excel目录
	  */
	String SALARY_COUNT_AFTER_XSLX = "salary.count.after.xslx";
	
	/**
	  * @Fields PRAY_ROLL_PERSON_FILE : 个人工资单
	  */
	String PRAY_ROLL_PERSON_FILE = "pay.roll.person.down.file";
	
	/**
	  * @Fields TEMPE_DOWN_FILE : 临时存放文件目录
	  */
	String TEMPE_DOWN_FILE = "temp.down.file";
	
	
	
	/**
	  * @Fields MAP_KEY_SALARY_TYPE : 工资单，-工资类型
	  */
	String MAP_KEY_SALARY_TYPE = "salarytype";
	
	/**
	  * @Fields MAP_KEY_SALARY_DETAILS : 工资单 -明细类型
	  */
	String MAP_KEY_SALARY_DETAILS = "salarydetails";
	
	/**
	  * @Fields MAP_KEY_IS_DISPLAY : 工资单  -是否显示
	  */
	String MAP_KEY_IS_DISPLAY = "isdisplay";
	
	/**
	  * @Fields PAY_ROLL_TEMPLETE : 工资单模板字段
	  */
	String PAY_ROLL_TEMPLETE = "templete";
	
	/**
	  * @Fields PAY_ROLL_TEMPLETE_VALUES : 薪资模板管理测试值
	  */
	String PAY_ROLL_TEMPLETE_VALUES="test0000";
	
	
	String PROJECT_COUNT_TEMPLETE="count.templete.down.file";
	
	/**
	  * @Fields TEMPLETE_EMPLOYEE_UPLOAD_FILE :人员信息管理批量上传模板
	  */
	String TEMPLETE_EMPLOYEE_UPLOAD_FILE = "人员信息管理批量上传.xlsx";
	
	/**
	  * @Fields PAYROLL_JOBNO_UPLOAD_FILE :工资单工号上传模板
	  */
	String PAYROLL_JOBNO_UPLOAD_FILE = "工资单工号上传.xlsx";
	
	/**
	  * @Fields TEMPLETE_EMPLOYEE_DELETE_FILE : 人员信息管理批量删除模板
	  */
	String TEMPLETE_EMPLOYEE_DELETE_FILE = "人员信息管理批量删除.xlsx";
	
	/**
	  * @Fields rows : 固定列，属于基本信息，不能更改
	  */
	String[] rows = { "序号", "公司", "员工工号", "姓名", "部门", "岗位", "员工属省", "员工属地", "入职时间", "离职日期", "员工状态",
	"历史状态" };

	/**
	  * @Fields EMAIL_HOST : 发送邮件邮箱的Host
	  */
	String EMAIL_HOST = "send.email.host";
	
	/**
	  * @Fields EMAIL_USERNAME : 发送邮件邮箱的Username
	  */
	String EMAIL_USERNAME = "send.email.username";
	
	/**
	  * @Fields EMAIL_PASSWORD : 发送邮件邮箱的Password
	  */
	String EMAIL_PASSWORD = "send.email.password";
	
	/**
	  * @Fields EMAIL_DELETE_TYPE_FAILED : 删除失败邮件类型
	  */
	String EMAIL_DELETE_TYPE_FAILED = "failed";
	
	/**
	  * @Fields EMAIL_DELETE_TYPE_ALL : 删除所有邮件类型
	  */
	String EMAIL_DELETE_TYPE_ALL = "all";
	
	/**
	  * @Fields QUALITY_EXEMPTION_LIST_DELETE_LIST : 需要删除的豁免合同号
	  */
	String QUALITY_EXEMPTION_LIST_DELETE_LIST = "qualityExemptionDeleteList";
	
	/**
	  * @Fields BONUS_ISPREPARE_CITY_DELETE_LIST : 需要删除的城市筹备期信息
	  */
	String BONUS_ISPREPARE_CITY_DELETE_LIST = "bonusIsprepareCityDeleteList";

	/**
	  * @Fields QUALITY_EXEMPTION_LIST_SERIALNO : 合同号
	  */
	String QUALITY_EXEMPTION_LIST_SERIALNO= "serialno";
	
	/**
	  * @Fields QUALITY_EXEMPTION_LIST_EXEMONTH : 豁免月份
	  */
	String QUALITY_EXEMPTION_LIST_EXEMONTH= "exeMonth";
	
	/**
	  * @Fields QUALITY_EXEMPTION_LIST_CREATEBY : 创建人
	  */
	String QUALITY_EXEMPTION_LIST_CREATEBY= "createby";
	
	/**
	  * @Fields QUALITY_EXEMPTION_LIST_CREATETIME : 创建时间
	  */
	String QUALITY_EXEMPTION_LIST_CREATETIME= "createtime";
	
	/**
	  * @Fields BONUS_ISPREPARE_CITY_BONUSMONTH : 月份
	  */
	String BONUS_ISPREPARE_CITY_BONUSMONTH= "bonusmonth";
	
	/**
	  * @Fields BONUS_ISPREPARE_CITY_CITY : 城市名
	  */
	String BONUS_ISPREPARE_CITY_CITY= "city";
	
	/**
	  * @Fields BONUS_ISPREPARE_CITY_ISPREPAREPERIOD : 是否筹备期
	  */
	String BONUS_ISPREPARE_CITY_ISPREPAREPERIOD= "isprepareperiod";
	
	/**
	  * @Fields BONUS_ISPREPARE_CITY_CREATEBY : 创建人
	  */
	String BONUS_ISPREPARE_CITY_CREATEBY= "createby";
	
	/**
	  * @Fields BONUS_ISPREPARE_CITY_CREATETIME : 创建时间
	  */
	String BONUS_ISPREPARE_CITY_CREATETIME= "createtime";
	
	/**
	  * @Fields EMAIL_STATUS_SUCCESS : 发送邮件成功标识
	  */
	String EMAIL_STATUS_SUCCESS= "S";
	
	/**
	  * @Fields EMAIL_STATUS_FAILURE : 发送邮件失败标识
	  */
	String EMAIL_STATUS_FAILURE= "F";
}
