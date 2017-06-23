package com.billionsfinance.bas.server.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.BusinessLoanVO;
import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.server.IBusinessLoanServer;
import com.billionsfinance.bas.service.BusinessLoanService;
import com.billionsfinance.bas.util.CommonUtil;
import com.billionsfinance.bas.util.SpringService;

public class BusinessLoanServer implements IBusinessLoanServer {

	private BusinessLoanService businessLoanService = SpringService.BUSINESSLOANSERVICE;
	
	@Override
	public PageVO queryBusinessDetail(PageVO pageVO , BusinessLoanVO blVO) {

		Map<String, Object> whereMap = new HashMap<String, Object>();
		
		//SerialNo拆分
		if (blVO != null &&blVO.getSerialNo() != null&&!("").equals(blVO.getSerialNo())) {
			if ( blVO.getSerialNo().contains(" ")) {
				blVO.setArray(blVO.getSerialNo().split(" "));
				blVO.setSerialNo(null);
			}
			if ( blVO.getSerialNo().contains(",")) {
				blVO.setArray(blVO.getSerialNo().split(","));
				blVO.setSerialNo(null);
			}
		}
		//ID拆分
		if (blVO != null &&blVO.getId() != null&&!("").equals(blVO.getId())) {
			if ( blVO.getId().contains(",") ) {
				blVO.setIdArray(blVO.getId().split(","));
				blVO.setId(null);
			}
		}
		
		//组装条件
		if (blVO != null) {
			whereMap.put("id",blVO.getId());
			whereMap.put("idArray",blVO.getIdArray());
			whereMap.put("serialNo",blVO.getSerialNo());
			whereMap.put("array",blVO.getArray());
			whereMap.put("startBusinessDate",blVO.getStartBusinessDate());
			whereMap.put("endBusinessDate",blVO.getEndBusinessDate());
			whereMap.put("startKeepAccountsDate",blVO.getStartKeepAccountsDate());
			whereMap.put("endKeepAccountsDate",blVO.getEndKeepAccountsDate());
			whereMap.put("startPayDate",blVO.getStartPayDate());
			whereMap.put("endPayDate",blVO.getEndPayDate());
			whereMap.put("capitalSide",blVO.getCapitalSide());
			whereMap.put("productSubType",blVO.getProductSubType());
			whereMap.put("city",blVO.getCity());
		}

		//统计合同总数
		
		Map<String,Object> brVO = businessLoanService.queryBusinessDetailCount(whereMap);
		
		Long total = Long.parseLong(brVO.get("contractCount").toString()) ;
		
		//封装分页参数
		Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
		whereMap.putAll(whereMapPage);
		
		//查询合同明细
		List<Map<String, Object>> map2 = businessLoanService.queryBusinessDetail(whereMap);
		pageVO.setRows(map2);
		
		//总匹配合同数 总匹配金额
		if (brVO != null && brVO.get("moneyCount") != null && brVO.get("contractCount") != null) {
			pageVO.getRows().get(0).put("moneyCount", Double.parseDouble(brVO.get("moneyCount").toString()));
			pageVO.getRows().get(0).put("contractCount",Long.parseLong(brVO.get("contractCount").toString()));
		}
		
		return pageVO;
	}

	@Override
	public List<Map<String, Object>> queryBusinessDetailId(BusinessLoanVO blVO) {
		return businessLoanService.queryBusinessDetailId(blVO);
	}
	
	@Override
	public List<Map<String, Object>> queryBusinessGather(BusinessLoanVO blVO) {
		return businessLoanService.queryBusinessGather(blVO);
	}

	@Override
	public List<Map<String, Object>> queryBankrollDetail(BusinessLoanVO blVO) {
		// TODO Auto-generated method stub
		return businessLoanService.queryBankrollDetail(blVO);
	}
	
	@Override
	public void updateContract(BusinessLoanVO blVO) {
		// TODO Auto-generated method stub
		System.out.println("BusinessLoanServer.updateContract()");
		 businessLoanService.updateContract(blVO);
	}
	
	@Override
	public void createContract(BusinessLoanVO blVO) {
		// TODO Auto-generated method stub
		businessLoanService.createContract(blVO);
	}	

	@Override
	public Map<String,Object> queryBusinessDetailCount(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return businessLoanService.queryBusinessDetailCount(map);
	}

	@Override
	public void importContract(List<BusinessLoanVO> list) {
		// TODO Auto-generated method stub
		businessLoanService.importContract(list);
	}

	@Override
	public List<BusinessLoanVO> queryBusinessDetailById(String id) {
		// TODO Auto-generated method stub
		return businessLoanService.queryBusinessDetailById(id);
	}
	
	@Override
	public void deleteBusinessDetail(String id) {
		// TODO Auto-generated method stub
		businessLoanService.deleteBusinessDetail(id);
	}

	@Override
	public void updateBusinessDetail(BusinessLoanVO blVO) {
		// TODO Auto-generated method stub
		businessLoanService.updateBusinessDetail(blVO);
	}

	@Override
	public void payConfirm(BusinessLoanVO blVO) {
		// TODO Auto-generated method stub
		businessLoanService.payConfirm(blVO);
	}
	
	@Override
	public void accountingMark(BusinessLoanVO blVO) {
		// TODO Auto-generated method stub
		businessLoanService.accountingMark(blVO);
	}
	
}
