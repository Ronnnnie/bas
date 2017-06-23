package com.billionsfinance.bas.server.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.CheckListVO;
import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.server.ICheckListServer;
import com.billionsfinance.bas.service.CheckListService;
import com.billionsfinance.bas.util.CommonUtil;
import com.billionsfinance.bas.util.SpringService;

public class CheckListServer implements ICheckListServer {

	private CheckListService checkListService = SpringService.CHECKLISTSERVICE;

	@Override
	public PageVO queryBusinessDetail(PageVO pageVO,CheckListVO vo) {
		// TODO Auto-generated method stub
		
		Map<String,Object> whereMap = new HashMap<String,Object>();
		//组装条件
		if (vo != null) {
			whereMap.put("startImportDate",vo.getStartImportDate());
			whereMap.put("endImportDate",vo.getEndImportDate());
		}

		//统计合同总数
		
		Map<String,Object> brVO = checkListService.queryContractCount(whereMap);
		
		Long total = Long.parseLong(brVO.get("contractCount").toString()) ;
		
		//封装分页参数
		Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
		whereMap.putAll(whereMapPage);
		
		//查询合同明细
		List<Map<String, Object>> map2 = checkListService.queryBusinessDetail(whereMap);
		pageVO.setRows(map2);
		
		return pageVO;
	}

	@Override
	public void importContract(List<CheckListVO> list) {
		// TODO Auto-generated method stub
		checkListService.importContract(list);
	}
	
	@Override
	public void updateContract(List<CheckListVO> list) {
		// TODO Auto-generated method stub
		checkListService.updateContract(list);
	}

	@Override
	public Map<String,Object> queryContractCount(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return checkListService.queryContractCount(map);
	}

	@Override
	public List<CheckListVO> queryCheckListDataFindAll() {
		// TODO Auto-generated method stub
		return checkListService.queryCheckListDataFindAll();
	}
	

	
}
