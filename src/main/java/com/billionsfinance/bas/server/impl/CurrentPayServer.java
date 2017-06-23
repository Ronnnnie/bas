package com.billionsfinance.bas.server.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.billionsfinance.bas.entity.CurrentExpiredVO;
import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.server.ICurrentPayServer;
import com.billionsfinance.bas.service.CurrentPayService;
import com.billionsfinance.bas.util.CommonUtil;
import com.billionsfinance.bas.util.CurrentPayConstant;
import com.billionsfinance.bas.util.SpringService;

public class CurrentPayServer implements ICurrentPayServer {

	
	
	private CurrentPayService currentPayService = SpringService.CURRENTPAYSERVICE;

	@Override
	public PageVO queryCurrentPay(PageVO pageVO,CurrentExpiredVO vo) {
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

		//检查是否包含所属方，不包含则返回空
		if(!CurrentPayConstant.CURRENT_PAY_ASSETBELONG.containsKey(vo.getAssetbelong())){
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			pageVO.setRows(result);
			return pageVO;
		}
		//统计合同总数
		
		Map<String,Object> brVO = currentPayService.queryCurrentPayCount(whereMap);
		
		Long total = Long.parseLong(brVO.get("CONTRACTCOUNT").toString()) ;
		
		//封装分页参数
		Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
		whereMap.putAll(whereMapPage);
		
		//查询合同明细
		List<Map<String, Object>> map2 = currentPayService.queryCurrentPay(whereMap);
		pageVO.setRows(map2);
		
		if (brVO != null && brVO.get("MONEYCOUNT") != null && brVO.get("CONTRACTCOUNT") != null) {
			pageVO.getRows().get(0).put("moneyCount", Double.parseDouble(brVO.get("MONEYCOUNT").toString()));
			pageVO.getRows().get(0).put("PAYPRINCIPALAMTAMOUNT", Double.parseDouble(brVO.get("PAYPRINCIPALAMTAMOUNT").toString()));
			pageVO.getRows().get(0).put("PAYINTEAMTAMOUNT", Double.parseDouble(brVO.get("PAYINTEAMTAMOUNT").toString()));
			pageVO.getRows().get(0).put("WAIVEINTAMTAMOUNT", Double.parseDouble(brVO.get("WAIVEINTAMTAMOUNT").toString()));
			pageVO.getRows().get(0).put("A11AMOUNT", Double.parseDouble(brVO.get("A11AMOUNT").toString()));
		}
		
		return pageVO;
	}

	@Override
	public Map<String, Object> queryCurrentPayTotal(CurrentExpiredVO vo) {
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
		// TODO Auto-generated method stub
		
		//检查是否包含所属方，不包含则返回空
		if(!CurrentPayConstant.CURRENT_PAY_ASSETBELONG.containsKey(vo.getAssetbelong())){
			Map<String,Object> result = new HashMap<String,Object>();
			result.put("moneyCount", 0d);
			return result;
		}
		
		return currentPayService.queryCurrentPayCount(whereMap);
	}

	@Override
	public Map<String,Object> queryCurrentPayCount(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return currentPayService.queryCurrentPayCount(map);
	}

	
	@Override
	public Boolean updateCurrentPay(CurrentExpiredVO vo) {
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
					updateVO.setStartInaccountdate(vo.getStartInaccountdate());
					updateVO.setEndInaccountdate(vo.getEndInaccountdate());
					updateVO.setStartPayDate(vo.getStartPayDate());
					updateVO.setEndPayDate(vo.getEndPayDate());
					currentPayService.updateCurrentPay(updateVO);
				}
				return true;
			}else if(serialnoArray.length==1){
				currentPayService.updateCurrentPay(vo);
				return true;
			}else{
				return false;
			}
			
		}else{			
			currentPayService.updateCurrentPay(vo);
			return true;
		}
	}



	@Override
	public Integer checkCurrentPay(CurrentExpiredVO vo) {
		// TODO Auto-generated method stub
		return currentPayService.checkCurrentPay(vo);
	}
	
	
	
	@Override
	public PageVO queryCurrentPaySum(PageVO pageVO,CurrentExpiredVO vo) {
		// TODO Auto-generated method stub
		
		Map<String,Object> whereMap = new HashMap<String,Object>();
		//组装条件
		if (vo != null) {
			whereMap.put("startPayDate",vo.getStartPayDate());
			whereMap.put("endPayDate",vo.getEndPayDate());
			whereMap.put("paybelong",vo.getPaybelong());
			whereMap.put("assetbelong",vo.getAssetbelong());
			
			whereMap.put("startInaccountdate",vo.getStartInaccountdate());
			whereMap.put("endInaccountdate",vo.getEndInaccountdate());
			whereMap.put("guaranteeparty",vo.getGuaranteeparty());
			whereMap.put("creditperson",vo.getCreditperson());
			
			whereMap.put("city",vo.getCity());
			whereMap.put("businessmodel",vo.getBusinessmodel());
			whereMap.put("subproducttype",vo.getSubproducttype());
			
			whereMap.put("datasource",vo.getDatasource());
			
			whereMap.put("startActualpaydate",vo.getStartActualpaydate());
			whereMap.put("endActualpaydate",vo.getEndActualpaydate());
		}

		//统计合同总数
		
		Map<String,Object> brVO = currentPayService.queryCurrentPayCountSum(whereMap);
		
		Long total = Long.parseLong(brVO.get("CONTRACTCOUNT").toString()) ;
		
		//封装分页参数
		Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
		whereMap.putAll(whereMapPage);
		
		//查询合同明细
		List<Map<String, Object>> map2 = currentPayService.queryCurrentPaySum(whereMap);
		pageVO.setRows(map2);
		
		if (brVO != null && brVO.get("MONEYCOUNT") != null && brVO.get("CONTRACTCOUNT") != null) {
			pageVO.getRows().get(0).put("moneyCount", Double.parseDouble(brVO.get("MONEYCOUNT").toString()));
			pageVO.getRows().get(0).put("PAYPRINCIPALAMTAMOUNT", Double.parseDouble(brVO.get("PAYPRINCIPALAMTAMOUNT").toString()));
			pageVO.getRows().get(0).put("PAYINTEAMTAMOUNT", Double.parseDouble(brVO.get("PAYINTEAMTAMOUNT").toString()));
			pageVO.getRows().get(0).put("WAIVEINTAMTAMOUNT", Double.parseDouble(brVO.get("WAIVEINTAMTAMOUNT").toString()));
			pageVO.getRows().get(0).put("A11AMOUNT", Double.parseDouble(brVO.get("A11AMOUNT").toString()));
		}
		
		return pageVO;
	}

	@Override
	public Map<String, Object> queryCurrentPaySumTotal(CurrentExpiredVO vo) {
		// TODO Auto-generated method stub
		Map<String,Object> whereMap = new HashMap<String,Object>();
		//组装条件
		if (vo != null) {
			whereMap.put("startPayDate",vo.getStartPayDate());
			whereMap.put("endPayDate",vo.getEndPayDate());
			whereMap.put("paybelong",vo.getPaybelong());
			whereMap.put("assetbelong",vo.getAssetbelong());
			
			whereMap.put("startInaccountdate",vo.getStartInaccountdate());
			whereMap.put("endInaccountdate",vo.getEndInaccountdate());
			whereMap.put("guaranteeparty",vo.getGuaranteeparty());
			whereMap.put("creditperson",vo.getCreditperson());
			
			whereMap.put("city",vo.getCity());
			whereMap.put("businessmodel",vo.getBusinessmodel());
			whereMap.put("subproducttype",vo.getSubproducttype());
			
			whereMap.put("datasource",vo.getDatasource());
			
			whereMap.put("startActualpaydate",vo.getStartActualpaydate());
			whereMap.put("endActualpaydate",vo.getEndActualpaydate());
		}
		// TODO Auto-generated method stub
		return currentPayService.queryCurrentPayCountSum(whereMap);
	}

	@Override
	public List<Map<String,Object>> queryCurrentPayAll(CurrentExpiredVO vo) {
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
		try {
			List<Map<String,Object>> list = currentPayService.queryCurrentPayAll(whereMap);
			return list;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	@Override
	public List<Map<String,Object>> queryCurrentPaySumAll(CurrentExpiredVO vo) {
		Map<String,Object> whereMap = new HashMap<String,Object>();
		//组装条件
		if (vo != null) {
			whereMap.put("startPayDate",vo.getStartPayDate());
			whereMap.put("endPayDate",vo.getEndPayDate());
			whereMap.put("paybelong",vo.getPaybelong());
			whereMap.put("assetbelong",vo.getAssetbelong());
			
			whereMap.put("startInaccountdate",vo.getStartInaccountdate());
			whereMap.put("endInaccountdate",vo.getEndInaccountdate());
			whereMap.put("guaranteeparty",vo.getGuaranteeparty());
			whereMap.put("creditperson",vo.getCreditperson());
			
			whereMap.put("city",vo.getCity());
			whereMap.put("businessmodel",vo.getBusinessmodel());
			whereMap.put("subproducttype",vo.getSubproducttype());
			
			whereMap.put("datasource",vo.getDatasource());
			
			whereMap.put("startActualpaydate",vo.getStartActualpaydate());
			whereMap.put("endActualpaydate",vo.getEndActualpaydate());
		}
		// TODO Auto-generated method stub
		return currentPayService.queryCurrentPaySumAll(whereMap);
	}

	@Override
	public Boolean cancelCurrentPay(CurrentExpiredVO vo) {
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
					currentPayService.cancelCurrentPay(updateVO);
				}
				return true;
			}else if(serialnoArray.length==1){
				currentPayService.cancelCurrentPay(vo);
				return true;
			}else{
				return false;
			}
			
		}else{			
			currentPayService.cancelCurrentPay(vo);
			return true;
		}
	}

	@Override
	public Integer checkCancelCurrentPay(CurrentExpiredVO vo) {
		// TODO Auto-generated method stub
		return currentPayService.checkCancelCurrentPay(vo);
	}



	
	
}
