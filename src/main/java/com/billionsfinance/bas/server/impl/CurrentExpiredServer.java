package com.billionsfinance.bas.server.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.CurrentExpiredVO;
import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.server.ICurrentExpiredServer;
import com.billionsfinance.bas.service.CurrentExpiredService;
import com.billionsfinance.bas.util.CommonUtil;
import com.billionsfinance.bas.util.SpringService;

public class CurrentExpiredServer implements ICurrentExpiredServer {

	
	
	private CurrentExpiredService currentExpiredService = SpringService.CURRENTEXPIREDSERVICE;

	@Override
	public PageVO queryBusinessDetail(PageVO pageVO,CurrentExpiredVO vo) {
		// TODO Auto-generated method stub
		
		Map<String,Object> whereMap = new HashMap<String,Object>();
		//组装条件
		if (vo != null) {
			whereMap.put("startPayDate",vo.getStartPayDate());
			whereMap.put("endPayDate",vo.getEndPayDate());
			whereMap.put("assetbelong",vo.getAssetbelong());
			whereMap.put("serialno",vo.getSerialno());
			whereMap.put("startRegistrationDate",vo.getStartRegistrationDate());
			whereMap.put("endRegistrationDate",vo.getEndRegistrationDate());
			
			whereMap.put("startInaccountdate",vo.getStartInaccountdate());
			whereMap.put("endInaccountdate",vo.getEndInaccountdate());
		}

		//统计合同总数
		
		Map<String,Object> brVO = currentExpiredService.queryContractCount(whereMap);
		
		Long total = Long.parseLong(brVO.get("CONTRACTCOUNT").toString()) ;
		
		//封装分页参数
		Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
		whereMap.putAll(whereMapPage);
		
		//查询合同明细
		List<Map<String, Object>> map2 = currentExpiredService.queryBusinessDetail(whereMap);
		pageVO.setRows(map2);
		
		if (brVO != null && brVO.get("MONEYCOUNT") != null ) {
			pageVO.getRows().get(0).put("moneyCount", Double.parseDouble(brVO.get("MONEYCOUNT").toString()));
			pageVO.getRows().get(0).put("A2AMOUNT",Double.parseDouble(brVO.get("A2AMOUNT").toString()));
			pageVO.getRows().get(0).put("A7AMOUNT",Double.parseDouble(brVO.get("A7AMOUNT").toString()));
			pageVO.getRows().get(0).put("A12AMOUNT",Double.parseDouble(brVO.get("A12AMOUNT").toString()));
			pageVO.getRows().get(0).put("A18AMOUNT",Double.parseDouble(brVO.get("A18AMOUNT").toString()));
			pageVO.getRows().get(0).put("A22AMOUNT",Double.parseDouble(brVO.get("A22AMOUNT").toString()));
			pageVO.getRows().get(0).put("PAYPRINCIPALAMTAMOUNT",Double.parseDouble(brVO.get("PAYPRINCIPALAMTAMOUNT").toString()));
			pageVO.getRows().get(0).put("PAYINTEAMTAMOUNT",Double.parseDouble(brVO.get("PAYINTEAMTAMOUNT").toString()));
		}
		
		return pageVO;
	}

	

	@Override
	public Map<String,Object> queryContractCount(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return currentExpiredService.queryContractCount(map);
	}

	
	@Override
	public PageVO queryBusinessDetailSum(PageVO pageVO,CurrentExpiredVO vo) {
		// TODO Auto-generated method stub
		
		Map<String,Object> whereMap = new HashMap<String,Object>();
		//组装条件
		if (vo != null) {
			whereMap.put("startPayDate",vo.getStartPayDate());
			whereMap.put("endPayDate",vo.getEndPayDate());
			whereMap.put("assetbelong",vo.getAssetbelong());
			
			whereMap.put("startInaccountdate",vo.getStartInaccountdate());
			whereMap.put("endInaccountdate",vo.getEndInaccountdate());
			whereMap.put("guaranteeparty",vo.getGuaranteeparty());
			whereMap.put("creditperson",vo.getCreditperson());
			
			whereMap.put("city",vo.getCity());
			whereMap.put("businessmodel",vo.getBusinessmodel());
			whereMap.put("subproducttype",vo.getSubproducttype());
			
			whereMap.put("canceltype",vo.getCanceltype());
		}

		//统计合同总数
		
		Map<String,Object> brVO = currentExpiredService.queryContractCountSum(whereMap);
		
		Long total = Long.parseLong(brVO.get("CONTRACTCOUNT").toString()) ;
		
		//封装分页参数
		Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
		whereMap.putAll(whereMapPage);
		
		//查询合同明细
		List<Map<String, Object>> map2 = currentExpiredService.queryBusinessDetailSum(whereMap);
		pageVO.setRows(map2);
		
		if (brVO != null && brVO.get("MONEYCOUNT") != null ) {
			pageVO.getRows().get(0).put("moneyCount", Double.parseDouble(brVO.get("MONEYCOUNT").toString()));
			pageVO.getRows().get(0).put("A2AMOUNT",Double.parseDouble(brVO.get("A2AMOUNT").toString()));
			pageVO.getRows().get(0).put("A7AMOUNT",Double.parseDouble(brVO.get("A7AMOUNT").toString()));
			pageVO.getRows().get(0).put("A12AMOUNT",Double.parseDouble(brVO.get("A12AMOUNT").toString()));
			pageVO.getRows().get(0).put("A18AMOUNT",Double.parseDouble(brVO.get("A18AMOUNT").toString()));
			pageVO.getRows().get(0).put("A22AMOUNT",Double.parseDouble(brVO.get("A22AMOUNT").toString()));
			pageVO.getRows().get(0).put("PAYPRINCIPALAMTAMOUNT",Double.parseDouble(brVO.get("PAYPRINCIPALAMTAMOUNT").toString()));
			pageVO.getRows().get(0).put("PAYINTEAMTAMOUNT",Double.parseDouble(brVO.get("PAYINTEAMTAMOUNT").toString()));
		}
		
		return pageVO;
	}

	

	@Override
	public Map<String,Object> queryContractCountSum(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return currentExpiredService.queryContractCount(map);
	}

	@Override
	public List<CurrentExpiredVO> queryBusinessDetailAll(CurrentExpiredVO vo) {
		Map<String,Object> whereMap = new HashMap<String,Object>();
		//组装条件
		if (vo != null) {
			whereMap.put("startPayDate",vo.getStartPayDate());
			whereMap.put("endPayDate",vo.getEndPayDate());
			whereMap.put("assetbelong",vo.getAssetbelong());
			whereMap.put("serialno",vo.getSerialno());
			whereMap.put("startRegistrationDate",vo.getStartRegistrationDate());
			whereMap.put("endRegistrationDate",vo.getEndRegistrationDate());
			
			whereMap.put("startInaccountdate",vo.getStartInaccountdate());
			whereMap.put("endInaccountdate",vo.getEndInaccountdate());
		}
		// TODO Auto-generated method stub
		List<CurrentExpiredVO> list = null;
		try {
			list = currentExpiredService.queryBusinessDetailAll(whereMap);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return list;
	}
	
	@Override
	public List<CurrentExpiredVO> queryBusinessDetailSumAll(CurrentExpiredVO vo) {
		Map<String,Object> whereMap = new HashMap<String,Object>();
		//组装条件
		if (vo != null) {
			whereMap.put("startPayDate",vo.getStartPayDate());
			whereMap.put("endPayDate",vo.getEndPayDate());
			whereMap.put("assetbelong",vo.getAssetbelong());
			
			whereMap.put("startInaccountdate",vo.getStartInaccountdate());
			whereMap.put("endInaccountdate",vo.getEndInaccountdate());
			whereMap.put("guaranteeparty",vo.getGuaranteeparty());
			whereMap.put("creditperson",vo.getCreditperson());
			
			whereMap.put("city",vo.getCity());
			whereMap.put("businessmodel",vo.getBusinessmodel());
			whereMap.put("subproducttype",vo.getSubproducttype());
			
			whereMap.put("canceltype",vo.getCanceltype());
		}
		// TODO Auto-generated method stub
		List<CurrentExpiredVO> list = null;
		try {
			list = currentExpiredService.queryBusinessDetailSumAll(whereMap);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public Boolean updateCurrentExpired(CurrentExpiredVO vo) {
		// TODO Auto-generated method stub
		if(vo.getSerialnoArray()!=null){
			String[] serialnoArray = vo.getSerialnoArray();
			String[] seqidArray = vo.getSeqidArray();
			String[] assetbelongArray = vo.getAssetbelongArray();
			String[] payprincipalamtArray = vo.getPayprincipalamtArray();
			String[] payinteamtArray = vo.getPayinteamtArray();
			
			if(serialnoArray.length>1&&serialnoArray.length==seqidArray.length&&seqidArray.length == assetbelongArray.length && 
			assetbelongArray.length == payprincipalamtArray.length && payprincipalamtArray.length == payinteamtArray.length){
				
				for (int i = 0; i < serialnoArray.length; i++) {
					CurrentExpiredVO updateVO = new CurrentExpiredVO();
					updateVO.setSerialno(serialnoArray[i]);
					updateVO.setSeqidStr(seqidArray[i]);
					updateVO.setAssetbelong(assetbelongArray[i]);
					updateVO.setPayprincipalamtStr(payprincipalamtArray[i]);
					updateVO.setPayinteamtStr(payinteamtArray[i]);
					updateVO.setInaccountby(vo.getInaccountby());
					updateVO.setInaccountremark(vo.getInaccountremark());
					updateVO.setInaccountdate(vo.getInaccountdate());
					//updateVO.setStartInaccountdate(vo.getStartInaccountdate());
					//updateVO.setEndInaccountdate(vo.getEndInaccountdate());
					//updateVO.setStartPayDate(vo.getStartPayDate());
					//updateVO.setEndPayDate(vo.getEndPayDate());
					currentExpiredService.updateCurrentExpired(updateVO);
				}
				return true;
			}else if(serialnoArray.length==1){
				currentExpiredService.updateCurrentExpired(vo);
				return true;
			}else{
				return false;
			}
			
		}else{			
			currentExpiredService.updateCurrentExpired(vo);
			return true;
		}
	}



	@Override
	public Integer checkCurrentExpired(CurrentExpiredVO vo) {
		// TODO Auto-generated method stub
		return currentExpiredService.checkCurrentExpired(vo);
	}



	@Override
	public Map<String, Object> queryCurrentExpiredTotal(CurrentExpiredVO vo) {
		// TODO Auto-generated method stub
		
		Map<String,Object> whereMap = new HashMap<String,Object>();
		//组装条件
		if (vo != null) {
			whereMap.put("startPayDate",vo.getStartPayDate());
			whereMap.put("endPayDate",vo.getEndPayDate());
			whereMap.put("assetbelong",vo.getAssetbelong());
			whereMap.put("serialno",vo.getSerialno());
			whereMap.put("startRegistrationDate",vo.getStartRegistrationDate());
			whereMap.put("endRegistrationDate",vo.getEndRegistrationDate());
			
			whereMap.put("startInaccountdate",vo.getStartInaccountdate());
			whereMap.put("endInaccountdate",vo.getEndInaccountdate());
		}

		//统计合同总数
		
		Map<String,Object> brVO = currentExpiredService.queryContractCount(whereMap);
		
		return brVO;
	}

	@Override
	public Map<String, Object> queryCurrentExpiredSumTotal(CurrentExpiredVO vo) {
		// TODO Auto-generated method stub
		
		Map<String,Object> whereMap = new HashMap<String,Object>();
		//组装条件
		if (vo != null) {
			whereMap.put("startPayDate",vo.getStartPayDate());
			whereMap.put("endPayDate",vo.getEndPayDate());
			whereMap.put("assetbelong",vo.getAssetbelong());
			
			whereMap.put("startInaccountdate",vo.getStartInaccountdate());
			whereMap.put("endInaccountdate",vo.getEndInaccountdate());
			whereMap.put("guaranteeparty",vo.getGuaranteeparty());
			whereMap.put("creditperson",vo.getCreditperson());
			
			whereMap.put("city",vo.getCity());
			whereMap.put("businessmodel",vo.getBusinessmodel());
			whereMap.put("subproducttype",vo.getSubproducttype());
			
			whereMap.put("canceltype",vo.getCanceltype());
		}

		//统计合同总数
		
		Map<String,Object> brVO = currentExpiredService.queryContractCountSum(whereMap);
		
		return brVO;
	}



	@Override
	public Integer checkCancelCurrentExpired(CurrentExpiredVO vo) {
		// TODO Auto-generated method stub
		return currentExpiredService.checkCancelCurrentExpired(vo);
	}



	@Override
	public Boolean cancelCurrentExpired(CurrentExpiredVO vo) {
		// TODO Auto-generated method stub
		
		if(vo.getSerialnoArray()!=null){
			String[] serialnoArray = vo.getSerialnoArray();
			String[] seqidArray = vo.getSeqidArray();
			String[] assetbelongArray = vo.getAssetbelongArray();
			String[] payprincipalamtArray = vo.getPayprincipalamtArray();
			String[] payinteamtArray = vo.getPayinteamtArray();
			
			if(serialnoArray.length>1&&serialnoArray.length==seqidArray.length&&seqidArray.length == assetbelongArray.length && 
			assetbelongArray.length == payprincipalamtArray.length && payprincipalamtArray.length == payinteamtArray.length){
				
				for (int i = 0; i < serialnoArray.length; i++) {
					CurrentExpiredVO updateVO = new CurrentExpiredVO();
					updateVO.setSerialno(serialnoArray[i]);
					updateVO.setSeqidStr(seqidArray[i]);
					updateVO.setAssetbelong(assetbelongArray[i]);
					updateVO.setPayprincipalamtStr(payprincipalamtArray[i]);
					updateVO.setPayinteamtStr(payinteamtArray[i]);
					updateVO.setInaccountby(vo.getInaccountby());
					updateVO.setInaccountremark(vo.getInaccountremark());
					updateVO.setInaccountdate(vo.getInaccountdate());
					//updateVO.setStartInaccountdate(vo.getStartInaccountdate());
					//updateVO.setEndInaccountdate(vo.getEndInaccountdate());
					//updateVO.setStartPayDate(vo.getStartPayDate());
					//updateVO.setEndPayDate(vo.getEndPayDate());
					currentExpiredService.cancelCurrentExpired(updateVO);
				}
				return true;
			}else if(serialnoArray.length==1){
				currentExpiredService.cancelCurrentExpired(vo);
				return true;
			}else{
				return false;
			}
			
		}else{			
			currentExpiredService.cancelCurrentExpired(vo);
			return true;
		}
	}
	
}
