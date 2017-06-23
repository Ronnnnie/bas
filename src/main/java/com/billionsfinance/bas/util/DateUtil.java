package com.billionsfinance.bas.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

	public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy年MM月dd日  E", Locale.CHINESE);
	
	/**
	 * 获取插入数据库的时间
	 * @return
	 */
	public static Timestamp sqlDate(){
		return new Timestamp(System.currentTimeMillis());
	}
	
	/** 
	* 获得指定日期的前一天 
	* @param specifiedDay 
	* @return 
	* @throws Exception 
	*/ 
	public static String getSpecifiedDayBefore(String specifiedDay){ 
	//SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		Calendar c = Calendar.getInstance(); 
		Date date=null; 
		try { 
		date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay); 
		} catch (ParseException e) { 
		e.printStackTrace(); 
		} 
		c.setTime(date); 
		int day=c.get(Calendar.DATE); 
		c.set(Calendar.DATE,day-1); 
	
		String dayBefore=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()); 
		return dayBefore; 
	} 
	
	/** 
	* 获得指定日期的后一天 
	* @param specifiedDay 
	* @return 
	*/ 
	public static String getSpecifiedDayAfter(String specifiedDay){ 
		Calendar c = Calendar.getInstance(); 
		Date date=null; 
		try { 
		date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay); 
		} catch (ParseException e) { 
		e.printStackTrace(); 
		} 
		c.setTime(date); 
		int day=c.get(Calendar.DATE); 
		c.set(Calendar.DATE,day+1); 
	
		String dayAfter=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()); 
		return dayAfter; 
	}
	
	/**
	 * 
	 * @param format  yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String dateNow(String format){
		 Date d = new Date();  
		 SimpleDateFormat sdf = new SimpleDateFormat(format);
		 String dateNowStr = sdf.format(d);  
		 return dateNowStr;
	}
	
	
	  /**
     * 格式化当前日期字符串，返回yyyy/MM/dd
     * 
     * @return
     */
    public static String getToDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return sdf.format(new Date());
    }
    
    /**
     * 格式化当前日期字符串，返回yyyyMMdd
     * 
     * @return
     */
    public static String getCurrentDate() {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	return sdf.format(new Date());
    }

    /**
     * 获取昨天的日期，返回yyyyMMdd
     * 
     * @return
     */
    public static String getLastDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return sdf.format(calendar.getTime());
    }
    
    /**
     * 获取昨天的日期，返回yyyy-MM-dd
     * @param pattern  yyyy-MM-dd HH:mm:ss
     * @return  yyyy-MM-dd
     */
    public static String getLastDate(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(new Date());//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_YEAR, -1);//设置为前一天
        return sdf.format(calendar.getTime());//得到前一天的时间
    }
    
	

    /**
     * 格式化当前时间字符串，返回HHmmssSSSsss
     * 
     * @return
     */
    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HHmmssSSSsss");
        return sdf.format(new Date());
    }

    /**
     * 格式化当前日期时间字符串，返回yyyyMMddHHmmssSSS
     * 
     * @return
     */
    public static String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sdf.format(new Date());
    }

    /**
     * 格式化当前日期时间字符串
     * 
     * @return
     */
    public static String getCurrentDateTime(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(new Date());
    }

    /**
     * 格式化指定时间和指定格式字符串
     * 
     * @return
     */
    public static String formatDate(Date date, String pattern) {
    	
    	if(date == null)
    		return null;
    	
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 把字符串按照指定格式转为日期时间
     * 
     * @return
     */
    public static Date toDate(String dateStr, String pattern) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.parse(dateStr);
    }

    /**
     * 对指定日期增加或减少多少天
     * 
     * @param sourceDate
     *            基准日期,格式yyyyMMdd
     * @param days
     *            增加或减少天数
     * @return 格式yyyyMMdd
     * @throws Exception
     */
    public static String addDay(String sourceDate, int days) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sdf.parse(sourceDate));
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return sdf.format(calendar.getTime());
    }

    /**
     * 对指定日期增加或减少多少天
     * 
     * @Title: 对指定日期增加或减少多少天
     * @Description: 对指定日期增加或减少多少天
     * @param sourceDate
     * @param days
     * @return
     * @return Date
     * @throws
     */
    public static Date addDay(Date sourceDate, int days) throws Exception  {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sourceDate);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }
    
    /**
     * @Description 获取系统当前日期并以字符串形式返回
     * @return 系统当前日期
     */
    public static String getSysDateString(){
    	return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
    }
//	public static void main(String args[]){
//		System.out.println(getSpecifiedDayBefore("2016-01-01 00:50:00"));
//	}

}
