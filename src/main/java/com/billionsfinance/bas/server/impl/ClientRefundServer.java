package com.billionsfinance.bas.server.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.ClientRefundVO;
import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.server.IClientRefundServer;
import com.billionsfinance.bas.service.ClientRefundService;
import com.billionsfinance.bas.util.CommonUtil;
import com.billionsfinance.bas.util.SpringService;

public class ClientRefundServer implements IClientRefundServer {

	private ClientRefundService clientRefundService = SpringService.CLIENTREFUNDSERVICE;
	
	@Override
	public PageVO queryBusinessDetail(PageVO pageVO , ClientRefundVO vo) {

		Map<String, Object> whereMap = new HashMap<String, Object>();
		
		//SerialNo拆分
		if (vo != null &&vo.getSerialNo() != null&&!("").equals(vo.getSerialNo())) {
			if ( vo.getSerialNo().contains(" ")) {
				vo.setArray(vo.getSerialNo().split(" "));
				vo.setSerialNo(null);
			}
			if ( vo.getSerialNo().contains(",")) {
				vo.setArray(vo.getSerialNo().split(","));
				vo.setSerialNo(null);
			}
		}
		//ID拆分
		if (vo != null &&vo.getId() != null&&!("").equals(vo.getId())) {
			if ( vo.getId().contains(",") ) {
				vo.setIdArray(vo.getId().split(","));
				vo.setId(null);
			}
		}
		
		//组装条件
		if (vo != null) {
			whereMap.put("id",vo.getId());
			whereMap.put("idArray",vo.getIdArray());
			whereMap.put("serialNo",vo.getSerialNo());
			whereMap.put("array",vo.getArray());
			whereMap.put("startBusinessDate",vo.getStartBusinessDate());
			whereMap.put("endBusinessDate",vo.getEndBusinessDate());
			whereMap.put("startKeepAccountsDate",vo.getStartKeepAccountsDate());
			whereMap.put("endKeepAccountsDate",vo.getEndKeepAccountsDate());
			whereMap.put("capitalSide",vo.getCapitalSide());
			whereMap.put("productSubType",vo.getProductSubType());
			whereMap.put("city",vo.getCity());
		}

		//统计合同总数
		
		Map<String,Object> brVO = clientRefundService.queryBusinessDetailCount(whereMap);
		
		Long total = Long.parseLong(brVO.get("contractCount").toString()) ;
		
		//封装分页参数
		Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
		whereMap.putAll(whereMapPage);
		
		//查询合同明细
		List<Map<String, Object>> map2 = clientRefundService.queryBusinessDetail(whereMap);
		pageVO.setRows(map2);
		
		//总匹配合同数 总匹配金额
		if (brVO != null && brVO.get("moneyCount") != null && brVO.get("contractCount") != null) {
			pageVO.getRows().get(0).put("moneyCount", Double.parseDouble(brVO.get("moneyCount").toString()));
			pageVO.getRows().get(0).put("contractCount",Long.parseLong(brVO.get("contractCount").toString()));
		}
		
		return pageVO;
	}

	@Override
	public void updateContract(ClientRefundVO vo) {
		// TODO Auto-generated method stub
		System.out.println("BusinessLoanServer.updateContract()");
		 clientRefundService.updateContract(vo);
	}
	
	@Override
	public void createContract(ClientRefundVO vo) {
		// TODO Auto-generated method stub
		clientRefundService.createContract(vo);
	}	

	@Override
	public Map<String,Object> queryBusinessDetailCount(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return clientRefundService.queryBusinessDetailCount(map);
	}

	@Override
	public void importContract(List<ClientRefundVO> list) {
		// TODO Auto-generated method stub
		clientRefundService.importContract(list);
	}

	
	@Override
	public void deleteBusinessDetail(String id) {
		// TODO Auto-generated method stub
		clientRefundService.deleteBusinessDetail(id);
	}

	@Override
	public void updateBusinessDetail(ClientRefundVO vo) {
		// TODO Auto-generated method stub
		clientRefundService.updateBusinessDetail(vo);
	}

	@Override
	public void payConfirm(ClientRefundVO vo) {
		// TODO Auto-generated method stub
		clientRefundService.payConfirm(vo);
	}
	
	@Override
	public void accountingMark(ClientRefundVO vo) {
		// TODO Auto-generated method stub
		clientRefundService.accountingMark(vo);
	}

	@Override
	public List<Map<String, Object>> queryBusinessDetailId(ClientRefundVO vo) {
		// TODO Auto-generated method stub
		return clientRefundService.queryBusinessDetailId(vo);
	}
	
}
