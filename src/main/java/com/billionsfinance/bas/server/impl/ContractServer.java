package com.billionsfinance.bas.server.impl;

import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.ContractVO;
import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.server.IContractServer;
import com.billionsfinance.bas.service.ContractService;
import com.billionsfinance.bas.util.CommonUtil;
import com.billionsfinance.bas.util.SpringService;

public class ContractServer implements IContractServer {

	private ContractService contractService = SpringService.CONTRACTSERVICE;
	
	@Override
	public PageVO queryBusinessDetail(PageVO pageVO,Map<String, Object> map) {
		Map<String,Object> brVO = contractService.queryBusinessDetailCount(map);
		System.out.println(map.get("id"));
		//统计合同总数
		Long total = Long.parseLong(brVO.get("contractCount").toString()) ;
		//封装分页参数
		Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
		map.putAll(whereMapPage);
		//查询合同明细
		List<Map<String, Object>> contract = contractService.queryBusinessDetail(map);
		pageVO.setRows(contract);
		return pageVO;
	}
	
	@Override
	public void updateContract(ContractVO brVO) {
		// TODO Auto-generated method stub
		 contractService.updateContract(brVO);
	}
	
	@Override
	public void createContract(ContractVO brVO) {
		// TODO Auto-generated method stub
		contractService.createContract(brVO);
	}	

	@Override
	public Map<String,Object> queryBusinessDetailCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return contractService.queryBusinessDetailCount(map);
	}

	@Override
	public void importContract(List<ContractVO> list) {
		// TODO Auto-generated method stub
		contractService.importContract(list);
	}
	
	@Override
	public void payConfirm(ContractVO vo) {
		// TODO Auto-generated method stub
		contractService.payConfirm(vo);
	}
	
	@Override
	public void accountingMark(ContractVO vo) {
		// TODO Auto-generated method stub
		contractService.accountingMark(vo);
	}
/*	
	@Override
	public List<Map<String,Object>> queryBusinessDetailId(ContractVO vo) {
		// TODO Auto-generated method stub
		return contractService.queryBusinessDetailCount(map);
	}*/

	@Override
	public List<Map<String, Object>> queryBusinessDetailId(ContractVO vo) {
		// TODO Auto-generated method stub
		return contractService.queryBusinessDetailId(vo);
	}
	
}
