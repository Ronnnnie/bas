package com.billionsfinance.bas.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.common.framework.GlobalParam;

public class CommonUtil {
	
	/**
	 * 1表示公司内部员工，2表示供应商员工
	 */
	public static final String USER_TYPE_TOW = "2";
	
	/**
	 * 统一登录系统的编码
	 */
	public static final String ALS = GlobalParam.getParam("als.framework.syscode");
	
	/**
	 * 失效标记位:validInd=0 无效
	 */
	public static final String VALID_IND_F = "0";
	
	/**
	 * 有效标记:validInd = 1有效
	 */
	public static final String VALID_IND_N = "1";
	
	/**
	 * 级别1
	 */
	public static final String LEVEL_ONE = "1";
	
	/**CommonSystem 公共系统分类*/
	public static final String COMMON_SYSTEM ="CommonSystem";
	
	/**供应商用户开头字母*/
	public static final String SUPPLIER = "S";
	
	/**
	 * 项目类型(1-基础信息类，2-计算类，3-上传类，4-其他)
	 */
	public static final String PROBASEINFO_BASEINFO = "1";
	public static final String PROBASEINFO_CALCULATE = "2";
	public static final String PROBASEINFO_UPLOAD = "3";
	public static final String PROBASEINFO_OTHER = "4";
	
	//分页参数处理
	public static Map<String,Object> getWhereMapPage(PageVO pageVO, Long total)
	{
		Map<String,Object> whereMapPage = new HashMap<String,Object>();
		pageVO.setTotal(total);
		Integer endpoint ;
		Integer startpoint ;
		if(pageVO.getPageNo() > 1){
			startpoint = (pageVO.getPageNo() - 1) * pageVO.getPageSize()  + 1;
		}else{
			startpoint = 1;
		}
		endpoint = pageVO.getPageNo() * pageVO.getPageSize();
		whereMapPage.put("endpoint", endpoint);
		whereMapPage.put("startpoint", startpoint);
		return whereMapPage;
	}

	/**
     * 拆分集合
     * @param <T>
     * @param resList  要拆分的集合
     * @param count    每个集合的元素个数
     * @return  返回拆分后的各个集合
     */
    public static  <T> List<List<T>> splitList(List<T> resList,int count){
        
        if(resList==null ||count<1)
            return  null ;
        List<List<T>> ret=new ArrayList<List<T>>();
        int size=resList.size();
        if(size<=count){ //数据量不足count指定的大小
            ret.add(resList);
        }else{
            int pre=size/count;
            int last=size%count;
            //前面pre个集合，每个大小都是count个元素
            for(int i=0;i<pre;i++){
                List<T> itemList=new ArrayList<T>();
                for(int j=0;j<count;j++){
                    itemList.add(resList.get(i*count+j));
                }
                ret.add(itemList);
            }
            //last的进行处理
            if(last>0){
                List<T> itemList=new ArrayList<T>();
                for(int i=0;i<last;i++){
                    itemList.add(resList.get(pre*count+i));
                }
                ret.add(itemList);
            }
        }
        return ret;
        
    }
}
