package com.billionsfinance.bas.server.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.OverdueVO;
import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.server.IOverdueServer;
import com.billionsfinance.bas.service.OverdueService;
import com.billionsfinance.bas.util.CommonUtil;
import com.billionsfinance.bas.util.SpringService;
import com.billionsfinance.bas.util.StringUtil;

public class OverdueServer implements IOverdueServer {

	private OverdueService overdueService = SpringService.OVERDUESERVICE;
	
	@Override
	public PageVO queryOverdueDetail(PageVO pageVO, OverdueVO vo) {
		Map<String, Object> map = new HashMap<String, Object>();
		//组装条件
		if (vo != null) {
			map.put("serialNo",vo.getSerialNo());
			map.put("startInAccountDate",vo.getStartInAccountDate());
			map.put("endInAccountDate",vo.getEndInAccountDate());
			map.put("classfy",vo.getClassfy());
			map.put("assetBelong",vo.getAssetBelong());
			map.put("seqId",vo.getSeqId());
		}

		//统计合同总数
		
		Map<String,Object> brVO = overdueService.queryOverdueDetailCount(map);
		
		Long total = Long.parseLong(brVO.get("contractCount").toString()) ;
		
		//封装分页参数
		Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
		map.putAll(whereMapPage);
		
		//查询合同明细
		List<Map<String, Object>> map2 = overdueService.queryOverdueDetail(map);
		pageVO.setRows(map2);
		
		if(pageVO.getRows().size()>0){
		//总匹配合同数 总匹配金额
			pageVO.getRows().get(0).put("moneyCount", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("moneyCount"))));
			pageVO.getRows().get(0).put("contractCount",Long.parseLong(StringUtil.isNullOrEmpty(brVO.get("contractCount"))));
			pageVO.getRows().get(0).put("payprinciPalamtSum",StringUtil.isNullOrEmpty(brVO.get("payprinciPalamtSum")));
			pageVO.getRows().get(0).put("payInteamtSum",StringUtil.isNullOrEmpty(brVO.get("payInteamtSum")));
			pageVO.getRows().get(0).put("a2Sum",StringUtil.isNullOrEmpty(brVO.get("a2Sum")));
			pageVO.getRows().get(0).put("a7Sum",StringUtil.isNullOrEmpty(brVO.get("a7Sum")));
			pageVO.getRows().get(0).put("a12Sum",StringUtil.isNullOrEmpty(brVO.get("a12Sum")));
			pageVO.getRows().get(0).put("a18Sum",StringUtil.isNullOrEmpty(brVO.get("a18Sum")));
			pageVO.getRows().get(0).put("a22Sum",StringUtil.isNullOrEmpty(brVO.get("a22Sum")));
			pageVO.getRows().get(0).put("amountSum",StringUtil.isNullOrEmpty(brVO.get("amountSum")));
		}
		return pageVO;
	}

	@Override
	public PageVO queryOverdueGather(PageVO pageVO, OverdueVO vo) {
		Map<String, Object> map = new HashMap<String, Object>();
		//组装条件
		if (vo != null) {
			map.put("startInAccountDate",vo.getStartInAccountDate());
			map.put("endInAccountDate",vo.getEndInAccountDate());
			map.put("businessModel",vo.getBusinessModel());
			map.put("subProductType",vo.getSubProductType());
			map.put("city",vo.getCity());
			map.put("overdueremark",vo.getOverdueremark());
			map.put("classfy",vo.getClassfy());
			map.put("canceltype",vo.getCanceltype());
			map.put("assetBelong",vo.getAssetBelong());
			map.put("guaranteeParty",vo.getGuaranteeParty());
		}

		//统计合同总数
		
		Map<String,Object> brVO = overdueService.queryOverdueGatherCount(map);
		
		Long total = Long.parseLong(brVO.get("contractCount").toString()) ;
		
		//封装分页参数
		Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
		map.putAll(whereMapPage);
		
		//查询合同明细
		List<Map<String, Object>> map2 = overdueService.queryOverdueGather(map);
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
			pageVO.getRows().get(0).put("amountSum",StringUtil.isNullOrEmpty(brVO.get("amountSum")));
		}
		
		return pageVO;
	}

	@Override
	public int accountingMark(OverdueVO vo) {
		// TODO Auto-generated method stub
		return overdueService.accountingMark(vo);
	}
	
	@Override
	public int cancelAccountingMark(OverdueVO vo) {
		// TODO Auto-generated method stub
		return overdueService.cancelAccountingMark(vo);
	}

	@Override
	public List<Map<String, Object>> queryOverdueDetailFindAll(OverdueVO overdueVO) {
		// TODO Auto-generated method stub
		return overdueService.queryOverdueDetailFindAll(overdueVO);
	}

	@Override
	public List<Map<String, Object>> queryOverdueGatherFindAll(OverdueVO vo) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		if (vo != null) {
			map.put("startInAccountDate",vo.getStartInAccountDate());
			map.put("endInAccountDate",vo.getEndInAccountDate());
			map.put("businessModel",vo.getBusinessModel());
			map.put("subProductType",vo.getSubProductType());
			map.put("city",vo.getCity());
			map.put("overdueremark",vo.getOverdueremark());
			map.put("classfy",vo.getClassfy());
			map.put("canceltype",vo.getCanceltype());
			map.put("assetBelong",vo.getAssetBelong());
			map.put("guaranteeParty",vo.getGuaranteeParty());
		}
		return overdueService.queryOverdueGatherFindAll(map);
	}

	@Override
	public Map<String, Object> queryOverdueDetailCount(OverdueVO vo) {
		Map<String, Object> map = new HashMap<String, Object>();
		//组装条件
		if (vo != null) {
			map.put("serialNo",vo.getSerialNo());
			map.put("startInAccountDate",vo.getStartInAccountDate());
			map.put("endInAccountDate",vo.getEndInAccountDate());
			map.put("classfy",vo.getClassfy());
			map.put("assetBelong",vo.getAssetBelong());
			map.put("seqId",vo.getSeqId());
		}
		//统计合同总数
		return overdueService.queryOverdueDetailCount(map);
	}
	
	@Override
	public Map<String, Object> queryOverdueGatherCount(OverdueVO vo) {
		Map<String, Object> map = new HashMap<String, Object>();
		//组装条件
		if (vo != null) {
			map.put("startInAccountDate",vo.getStartInAccountDate());
			map.put("endInAccountDate",vo.getEndInAccountDate());
			map.put("businessModel",vo.getBusinessModel());
			map.put("subProductType",vo.getSubProductType());
			map.put("city",vo.getCity());
			map.put("overdueremark",vo.getOverdueremark());
			map.put("classfy",vo.getClassfy());
			map.put("canceltype",vo.getCanceltype());
			map.put("assetBelong",vo.getAssetBelong());
			map.put("guaranteeParty",vo.getGuaranteeParty());
		}
		//统计合同总数
		return overdueService.queryOverdueGatherCount(map);
	}

	@Override
	public List<Map<String,Object>> queryOverdueContract(OverdueVO vo) {
		// TODO Auto-generated method stub
		return overdueService.queryOverdueContract(vo);
	}

	@Override
	public List<Map<String, Object>> overdueGatherExport(OverdueVO vo) {
		// TODO Auto-generated method stub
		return overdueService.overdueGatherExport(vo);
	}

	@Override
	public List<Map<String, Object>> overdueDetailExport(OverdueVO vo) {
		// TODO Auto-generated method stub
		return overdueService.overdueDetailExport(vo);
	}

}
