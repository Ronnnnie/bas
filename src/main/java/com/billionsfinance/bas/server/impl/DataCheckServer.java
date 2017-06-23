package com.billionsfinance.bas.server.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.DataCheckVO;
import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.server.IDataCheckServer;
import com.billionsfinance.bas.service.DataCheckService;
import com.billionsfinance.bas.util.CommonUtil;
import com.billionsfinance.bas.util.SpringService;

public class DataCheckServer implements IDataCheckServer {

	private DataCheckService dataCheckService = SpringService.DATACHECKSERVICE;
	
	@Override
	public PageVO queryDataCheckTableMenuOne(PageVO pageVO) {
		Map<String, Object> whereMap = new HashMap<String, Object>();
		//统计合同总数
		Map<String,Object> brVO = dataCheckService.queryDataCheckTableMenuOneCount();
		Long total = Long.parseLong(brVO.get("contractCount").toString()) ;
		//封装分页参数
		Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
		whereMap.putAll(whereMapPage);
		//查询合同明细
		List<Map<String, Object>> map2 = dataCheckService.queryDataCheckTableMenuOne(whereMap);
		pageVO.setRows(map2);
		if(pageVO.getRows().size()>0){
			//总匹配合同数 总匹配金额
			pageVO.getRows().get(0).put("contractCount",total);
		}
		return pageVO;
	}

	@Override
	public PageVO queryDataCheckTableMenuTwo(PageVO pageVO) {
		Map<String, Object> whereMap = new HashMap<String, Object>();
		//统计合同总数
		Map<String,Object> brVO = dataCheckService.queryDataCheckTableMenuTwoCount();
		Long total = Long.parseLong(brVO.get("contractCount").toString()) ;
		//封装分页参数
		Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
		whereMap.putAll(whereMapPage);
		//查询合同明细
		List<Map<String, Object>> map2 = dataCheckService.queryDataCheckTableMenuTwo(whereMap);
		pageVO.setRows(map2);
		if(pageVO.getRows().size()>0){
			//总匹配合同数 总匹配金额
			pageVO.getRows().get(0).put("contractCount",total);
		}
		return pageVO;
	}

	@Override
	public PageVO queryDataCheckTableMenuThree(PageVO pageVO) {
		Map<String, Object> whereMap = new HashMap<String, Object>();
		//统计合同总数
		Map<String,Object> brVO = dataCheckService.queryDataCheckTableMenuThreeCount();
		Long total = Long.parseLong(brVO.get("contractCount").toString()) ;
		//封装分页参数
		Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
		whereMap.putAll(whereMapPage);
		//查询合同明细
		List<Map<String, Object>> map2 = dataCheckService.queryDataCheckTableMenuThree(whereMap);
		pageVO.setRows(map2);
		if(pageVO.getRows().size()>0){
			//总匹配合同数 总匹配金额
			pageVO.getRows().get(0).put("contractCount",total);
		}
		return pageVO;
	}
	
	@Override
	public PageVO queryDataCheckTableMenuFour(PageVO pageVO) {
		Map<String, Object> whereMap = new HashMap<String, Object>();
		//统计合同总数
		Map<String,Object> brVO = dataCheckService.queryDataCheckTableMenuFourCount();
		Long total = Long.parseLong(brVO.get("contractCount").toString()) ;
		//封装分页参数
		Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
		whereMap.putAll(whereMapPage);
		//查询合同明细
		List<Map<String, Object>> map2 = dataCheckService.queryDataCheckTableMenuFour(whereMap);
		pageVO.setRows(map2);
		if(pageVO.getRows().size()>0){
			//总匹配合同数 总匹配金额
			pageVO.getRows().get(0).put("contractCount",total);
		}
		return pageVO;
	}

	@Override
	public PageVO queryDataCheckTableMenuFive(PageVO pageVO) {
		Map<String, Object> whereMap = new HashMap<String, Object>();
		//统计合同总数
		Map<String,Object> brVO = dataCheckService.queryDataCheckTableMenuFiveCount();
		Long total = Long.parseLong(brVO.get("contractCount").toString()) ;
		//封装分页参数
		Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
		whereMap.putAll(whereMapPage);
		//查询合同明细
		List<Map<String, Object>> map2 = dataCheckService.queryDataCheckTableMenuFive(whereMap);
		pageVO.setRows(map2);
		if(pageVO.getRows().size()>0){
			//总匹配合同数 总匹配金额
			pageVO.getRows().get(0).put("contractCount",total);
		}
		return pageVO;
	}

	@Override
	public PageVO queryDataCheckTableMenuNine(PageVO pageVO) {
		Map<String, Object> whereMap = new HashMap<String, Object>();
		//统计合同总数
		Map<String,Object> brVO = dataCheckService.queryDataCheckTableMenuNineCount();
		Long total = Long.parseLong(brVO.get("contractCount").toString()) ;
		//封装分页参数
		Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
		whereMap.putAll(whereMapPage);
		//查询合同明细
		List<Map<String, Object>> map2 = dataCheckService.queryDataCheckTableMenuNine(whereMap);
		pageVO.setRows(map2);
		if(pageVO.getRows().size()>0){
			//总匹配合同数 总匹配金额
			pageVO.getRows().get(0).put("contractCount",total);
		}
		return pageVO;
	}

	@Override
	public PageVO queryDataCheckTableMenuTwelve(PageVO pageVO) {
		Map<String, Object> whereMap = new HashMap<String, Object>();
		//统计合同总数
		Map<String,Object> brVO = dataCheckService.queryDataCheckTableMenuTwelveCount();
		Long total = Long.parseLong(brVO.get("contractCount").toString()) ;
		//封装分页参数
		Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
		whereMap.putAll(whereMapPage);
		//查询合同明细
		List<Map<String, Object>> map2 = dataCheckService.queryDataCheckTableMenuTwelve(whereMap);
		pageVO.setRows(map2);
		if(pageVO.getRows().size()>0){
			pageVO.getRows().get(0).put("contractCount",total);
		}
		return pageVO;
	}

	@Override
	public Map<String,Object> queryDataCheckTableMenuOneCount() {
		return dataCheckService.queryDataCheckTableMenuOneCount();
	}

	@Override
	public Map<String,Object> queryDataCheckTableMenuTwoCount() {
		return dataCheckService.queryDataCheckTableMenuTwoCount();
	}

	@Override
	public Map<String,Object> queryDataCheckTableMenuThreeCount() {
		return dataCheckService.queryDataCheckTableMenuThreeCount();
	}
	
	@Override
	public Map<String,Object> queryDataCheckTableMenuFourCount() {
		return dataCheckService.queryDataCheckTableMenuFourCount();
	}

	@Override
	public Map<String,Object> queryDataCheckTableMenuFiveCount() {
		return dataCheckService.queryDataCheckTableMenuFiveCount();
	}

	@Override
	public Map<String,Object> queryDataCheckTableMenuNineCount() {
		return dataCheckService.queryDataCheckTableMenuNineCount();
	}

	@Override
	public Map<String,Object> queryDataCheckTableMenuTwelveCount() {
		return dataCheckService.queryDataCheckTableMenuTwelveCount();
	}

	@Override
	public List<Map<String, Object>> queryDataCheckTableMenuOneFindAll() {
		// TODO Auto-generated method stub
		return dataCheckService.queryDataCheckTableMenuOneFindAll();
	}

	@Override
	public List<Map<String, Object>> queryDataCheckTableMenuTwoFindAll() {
		// TODO Auto-generated method stub
		return dataCheckService.queryDataCheckTableMenuTwoFindAll();
	}

	@Override
	public List<Map<String, Object>> queryDataCheckTableMenuThreeFindAll() {
		// TODO Auto-generated method stub
		return dataCheckService.queryDataCheckTableMenuThreeFindAll();
	}

	@Override
	public List<Map<String, Object>> queryDataCheckTableMenuFourFindAll() {
		// TODO Auto-generated method stub
		return dataCheckService.queryDataCheckTableMenuFourFindAll();
	}

	@Override
	public List<Map<String, Object>> queryDataCheckTableMenuFiveFindAll() {
		// TODO Auto-generated method stub
		return dataCheckService.queryDataCheckTableMenuFiveFindAll();
	}

	@Override
	public List<Map<String, Object>> queryDataCheckTableMenuNineFindAll() {
		// TODO Auto-generated method stub
		return dataCheckService.queryDataCheckTableMenuNineFindAll();
	}

	@Override
	public List<Map<String, Object>> queryDataCheckTableMenuTwelveFindAll() {
		// TODO Auto-generated method stub
		return dataCheckService.queryDataCheckTableMenuTwelveFindAll();
	}

}
