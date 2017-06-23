package com.billionsfinance.bas.server.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.OverdueUnVO;
import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.server.IOverdueUnServer;
import com.billionsfinance.bas.service.OverdueUnService;
import com.billionsfinance.bas.util.CommonUtil;
import com.billionsfinance.bas.util.SpringService;
import com.billionsfinance.bas.util.StringUtil;

public class OverdueUnServer implements IOverdueUnServer {

	private OverdueUnService overdueService = SpringService.OVERDUEUNSERVICE;
	
	@Override
	public PageVO queryOverdueUnDetail(PageVO pageVO, OverdueUnVO vo) {
		Map<String, Object> whereMap = new HashMap<String, Object>();
		//组装条件
		if (vo != null) {
			whereMap.put("seqId",vo.getSeqId());
			whereMap.put("serialNo",vo.getSerialNo());
			whereMap.put("startInAccountDate",vo.getStartInAccountDate());
			whereMap.put("endInAccountDate",vo.getEndInAccountDate());
			whereMap.put("startRegistrationDate",vo.getStartRegistrationDate());
			whereMap.put("endRegistrationDate",vo.getEndRegistrationDate());
			whereMap.put("startShouldAlsoDate",vo.getStartShouldAlsoDate());
			whereMap.put("endShouldAlsoDate",vo.getEndShouldAlsoDate());
			whereMap.put("subProductType",vo.getSubProductType());
			whereMap.put("assetBelong",vo.getAssetBelong());
			whereMap.put("classFy",vo.getClassFy());
		}

		//统计合同总数
		
		Map<String,Object> brVO = overdueService.queryOverdueUnDetailCount(whereMap);
		
		Long total = Long.parseLong(brVO.get("contractCount").toString()) ;
		
		//封装分页参数
		Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
		whereMap.putAll(whereMapPage);
		
		//查询合同明细
		List<Map<String, Object>> map2 = overdueService.queryOverdueUnDetail(whereMap);
		pageVO.setRows(map2);
		
		//总匹配合同数 总匹配金额
		if(pageVO.getRows().size()>0){
			pageVO.getRows().get(0).put("moneyCount", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("moneyCount"))));
			pageVO.getRows().get(0).put("contractCount",Long.parseLong(StringUtil.isNullOrEmpty(brVO.get("contractCount"))));
			pageVO.getRows().get(0).put("payprinciPalamtSum",StringUtil.isNullOrEmpty(brVO.get("payprinciPalamtSum")));
			pageVO.getRows().get(0).put("payInteamtSum",StringUtil.isNullOrEmpty(brVO.get("payInteamtSum")));
			pageVO.getRows().get(0).put("a2Sum",StringUtil.isNullOrEmpty(brVO.get("a2Sum")));
			pageVO.getRows().get(0).put("a7Sum",StringUtil.isNullOrEmpty(brVO.get("a7Sum")));
			pageVO.getRows().get(0).put("a12Sum",StringUtil.isNullOrEmpty(brVO.get("a12Sum")));
			pageVO.getRows().get(0).put("a18Sum",StringUtil.isNullOrEmpty(brVO.get("a18Sum")));
			pageVO.getRows().get(0).put("a22Sum",StringUtil.isNullOrEmpty(brVO.get("a22Sum")));
		}
		
		return pageVO;
	}

	@Override
	public PageVO queryOverdueUnGather(PageVO pageVO, OverdueUnVO vo) {
		Map<String, Object> whereMap = new HashMap<String, Object>();
		//组装条件
		if (vo != null) {
			whereMap.put("startInAccountDate",vo.getStartInAccountDate());
			whereMap.put("endInAccountDate",vo.getEndInAccountDate());
			whereMap.put("startShouldAlsoDate",vo.getStartShouldAlsoDate());
			whereMap.put("endShouldAlsoDate",vo.getEndShouldAlsoDate());
			whereMap.put("businessModel",vo.getBusinessModel());
			whereMap.put("subProductType",vo.getSubProductType());
			whereMap.put("city",vo.getCity());
			whereMap.put("creditperson",vo.getCreditperson());
			whereMap.put("assetBelong",vo.getAssetBelong());
			whereMap.put("guaranteeParty",vo.getGuaranteeParty());
			whereMap.put("classFy",vo.getClassFy());
		}

		//统计合同总数
		
		Map<String,Object> brVO = overdueService.queryOverdueUnGatherCount(whereMap);
		
		Long total = Long.parseLong(brVO.get("contractCount").toString()) ;
		
		//封装分页参数
		Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
		whereMap.putAll(whereMapPage);
		
		//查询合同明细
		List<Map<String, Object>> map2 = overdueService.queryOverdueUnGather(whereMap);
		pageVO.setRows(map2);
		
		//总匹配合同数 总匹配金额
		if (brVO != null && brVO.get("moneyCount") != null && brVO.get("contractCount") != null) {
			pageVO.getRows().get(0).put("moneyCount", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("moneyCount"))));
			pageVO.getRows().get(0).put("contractCount",Long.parseLong(StringUtil.isNullOrEmpty(brVO.get("contractCount"))));
			pageVO.getRows().get(0).put("payprinciPalamtSum",StringUtil.isNullOrEmpty(brVO.get("payprinciPalamtSum")));
			pageVO.getRows().get(0).put("payInteamtSum",StringUtil.isNullOrEmpty(brVO.get("payInteamtSum")));
			pageVO.getRows().get(0).put("a2Sum",StringUtil.isNullOrEmpty(brVO.get("a2Sum")));
			pageVO.getRows().get(0).put("a7Sum",StringUtil.isNullOrEmpty(brVO.get("a7Sum")));
			pageVO.getRows().get(0).put("a12Sum",StringUtil.isNullOrEmpty(brVO.get("a12Sum")));
			pageVO.getRows().get(0).put("a18Sum",StringUtil.isNullOrEmpty(brVO.get("a18Sum")));
			pageVO.getRows().get(0).put("a22Sum",StringUtil.isNullOrEmpty(brVO.get("a22Sum")));
		}
		
		return pageVO;
	}

	@Override
	public int accountingMark(OverdueUnVO vo) {
		// TODO Auto-generated method stub
		return overdueService.accountingMark(vo);
	}
	
	@Override
	public int selectAccountingMark(OverdueUnVO vo) {
		// TODO Auto-generated method stub
		return overdueService.selectAccountingMark(vo);
	}
	
	@Override
	public int cancelAccountingMark(OverdueUnVO vo) {
		// TODO Auto-generated method stub
		return overdueService.cancelAccountingMark(vo);
	}

	@Override
	public List<Map<String, Object>> queryOverdueUnDetailFindAll(OverdueUnVO vo) {
		// TODO Auto-generated method stub
		return overdueService.queryOverdueUnDetailFindAll(vo);
	}

	@Override
	public List<Map<String, Object>> queryOverdueUnGatherFindAll(OverdueUnVO vo) {
		// TODO Auto-generated method stub
		return overdueService.queryOverdueUnGatherFindAll(vo);
	}

	@Override
	public Map<String, Object> queryOverdueUnCount(OverdueUnVO vo) {
		Map<String, Object> whereMap = new HashMap<String, Object>();
		//组装条件
		if (vo != null) {
			whereMap.put("seqId",vo.getSeqId());
			whereMap.put("serialNo",vo.getSerialNo());
			whereMap.put("startInAccountDate",vo.getStartInAccountDate());
			whereMap.put("endInAccountDate",vo.getEndInAccountDate());
			whereMap.put("startRegistrationDate",vo.getStartRegistrationDate());
			whereMap.put("endRegistrationDate",vo.getEndRegistrationDate());
			whereMap.put("startShouldAlsoDate",vo.getStartShouldAlsoDate());
			whereMap.put("city",vo.getCity());
			whereMap.put("endShouldAlsoDate",vo.getEndShouldAlsoDate());
			whereMap.put("subProductType",vo.getSubProductType());
			whereMap.put("assetBelong",vo.getAssetBelong());
			whereMap.put("classFy",vo.getClassFy());
		}
		//统计合同总数
		return overdueService.queryOverdueUnDetailCount(whereMap);
	}
	
	@Override
	public Map<String, Object> queryOverdueUnDetailCount(OverdueUnVO vo) {
		Map<String, Object> whereMap = new HashMap<String, Object>();
		//组装条件
		if (vo != null) {
			whereMap.put("seqId",vo.getSeqId());
			whereMap.put("serialNo",vo.getSerialNo());
			whereMap.put("startInAccountDate",vo.getStartInAccountDate());
			whereMap.put("endInAccountDate",vo.getEndInAccountDate());
			whereMap.put("startRegistrationDate",vo.getStartRegistrationDate());
			whereMap.put("endRegistrationDate",vo.getEndRegistrationDate());
			whereMap.put("startShouldAlsoDate",vo.getStartShouldAlsoDate());
			whereMap.put("endShouldAlsoDate",vo.getEndShouldAlsoDate());
			whereMap.put("subProductType",vo.getSubProductType());
			whereMap.put("assetBelong",vo.getAssetBelong());
			whereMap.put("classFy",vo.getClassFy());
		}
		//统计合同总数
		return overdueService.queryOverdueUnDetailCount(whereMap);
	}
	
	@Override
	public Map<String, Object> queryOverdueUnGatherCount(OverdueUnVO vo) {
		Map<String, Object> whereMap = new HashMap<String, Object>();
		//组装条件
		if (vo != null) {
			whereMap.put("startInAccountDate",vo.getStartInAccountDate());
			whereMap.put("endInAccountDate",vo.getEndInAccountDate());
			whereMap.put("startShouldAlsoDate",vo.getStartShouldAlsoDate());
			whereMap.put("endShouldAlsoDate",vo.getEndShouldAlsoDate());
			whereMap.put("businessModel",vo.getBusinessModel());
			whereMap.put("subProductType",vo.getSubProductType());
			whereMap.put("city",vo.getCity());
			whereMap.put("creditperson",vo.getCreditperson());
			whereMap.put("assetBelong",vo.getAssetBelong());
			whereMap.put("guaranteeParty",vo.getGuaranteeParty());
			whereMap.put("classFy",vo.getClassFy());
		}
		//统计合同总数
		return overdueService.queryOverdueUnGatherCount(whereMap);
	}

	@Override
	public List<Map<String, Object>> queryOverdueUnContract(OverdueUnVO vo) {
		// TODO Auto-generated method stub
		return overdueService.queryOverdueUnContract(vo);
	}

	@Override
	public List<Map<String, Object>> overdueUnDetailExport(OverdueUnVO vo) {
		// TODO Auto-generated method stub
		return overdueService.overdueUnDetailExport(vo);
	}

}
