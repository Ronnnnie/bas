package com.billionsfinance.bas.server.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.BQRefundVO;
import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.server.IBQRefundServer;
import com.billionsfinance.bas.service.BQRefundService;
import com.billionsfinance.bas.util.CommonUtil;
import com.billionsfinance.bas.util.SpringService;

public class BQRefundServer implements IBQRefundServer {

	private BQRefundService bqRefundService = SpringService.BQREFUNDSERVICE;
	
	@Override
	public PageVO queryBusinessDetail(PageVO pageVO , BQRefundVO vo) {

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
		
		Map<String,Object> brVO = bqRefundService.queryBusinessDetailCount(whereMap);
		
		Long total = Long.parseLong(brVO.get("contractCount").toString()) ;
		
		//封装分页参数
		Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
		whereMap.putAll(whereMapPage);
		
		//查询合同明细
		List<Map<String, Object>> map2 = bqRefundService.queryBusinessDetail(whereMap);
		pageVO.setRows(map2);
		
		//总匹配合同数 总匹配金额
		if (brVO != null && brVO.get("moneyCount") != null && brVO.get("contractCount") != null) {
			pageVO.getRows().get(0).put("moneyCount", Double.parseDouble(brVO.get("moneyCount").toString()));
			pageVO.getRows().get(0).put("contractCount",Long.parseLong(brVO.get("contractCount").toString()));
		}
		
		return pageVO;
	}

	@Override
	public void updateContract(BQRefundVO vo) {
		// TODO Auto-generated method stub
		System.out.println("BusinessLoanServer.updateContract()");
		 bqRefundService.updateContract(vo);
	}
	
	@Override
	public void createContract(BQRefundVO vo) {
		// TODO Auto-generated method stub
		bqRefundService.createContract(vo);
	}	

	@Override
	public Map<String,Object> queryBusinessDetailCount(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return bqRefundService.queryBusinessDetailCount(map);
	}

	@Override
	public void importContract(List<BQRefundVO> list) {
		// TODO Auto-generated method stub
		bqRefundService.importContract(list);
	}

	
	@Override
	public void deleteBusinessDetail(String id) {
		// TODO Auto-generated method stub
		bqRefundService.deleteBusinessDetail(id);
	}

	@Override
	public void updateBusinessDetail(BQRefundVO vo) {
		// TODO Auto-generated method stub
		bqRefundService.updateBusinessDetail(vo);
	}

	@Override
	public void payConfirm(BQRefundVO vo) {
		// TODO Auto-generated method stub
		bqRefundService.payConfirm(vo);
	}
	
	@Override
	public void accountingMark(BQRefundVO vo) {
		// TODO Auto-generated method stub
		bqRefundService.accountingMark(vo);
	}

	@Override
	public List<Map<String, Object>> queryBusinessDetailId(BQRefundVO vo) {
		// TODO Auto-generated method stub
		return bqRefundService.queryBusinessDetailId(vo);
	}
	
}
