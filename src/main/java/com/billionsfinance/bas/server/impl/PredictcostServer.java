package com.billionsfinance.bas.server.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.entity.PredictcostVO;
import com.billionsfinance.bas.server.IPredictcostServer;
import com.billionsfinance.bas.service.PredictcostService;
import com.billionsfinance.bas.util.CommonUtil;
import com.billionsfinance.bas.util.SpringService;

public class PredictcostServer implements IPredictcostServer {

	
	
	private PredictcostService predictcostService = SpringService.PREDICTCOSTSERVICE;

	@Override
	public PageVO queryPredictcost(PageVO pageVO,PredictcostVO vo) {
		// TODO Auto-generated method stub
		
		Map<String,Object> whereMap = new HashMap<String,Object>();
		//组装条件
		if (vo != null) {
			whereMap.put("startPayDate",vo.getStartPayDate());
			whereMap.put("endPayDate",vo.getEndPayDate());
			whereMap.put("belong",vo.getBelong());
			whereMap.put("contractno",vo.getContractno());
			whereMap.put("startRegistrationDate",vo.getStartRegistrationDate());
			whereMap.put("endRegistrationDate",vo.getEndRegistrationDate());
			whereMap.put("startKeepaccountsDate",vo.getStartKeepaccountsDate());
			whereMap.put("endKeepaccountsDate",vo.getEndKeepaccountsDate());
			
			whereMap.put("startInaccountdate",vo.getStartInaccountdate());
			whereMap.put("endInaccountdate",vo.getEndInaccountdate());
		}

		//统计合同总数
		
		Map<String,Object> brVO = predictcostService.queryPredictcostCount(whereMap);
		
		Long total = Long.parseLong(brVO.get("CONTRACTCOUNT").toString()) ;
		
		//封装分页参数
		Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
		whereMap.putAll(whereMapPage);
		
		//查询合同明细
		List<Map<String, Object>> map2 = predictcostService.queryPredictcost(whereMap);
		pageVO.setRows(map2);
		
		if (brVO != null && brVO.get("MONEYCOUNT") != null && brVO.get("CONTRACTCOUNT") != null) {
			pageVO.getRows().get(0).put("moneyCount", Double.parseDouble(brVO.get("MONEYCOUNT").toString()));
			pageVO.getRows().get(0).put("A2AMOUNT", Double.parseDouble(brVO.get("A2AMOUNT").toString()));
			pageVO.getRows().get(0).put("A7AMOUNT", Double.parseDouble(brVO.get("A7AMOUNT").toString()));
			pageVO.getRows().get(0).put("A12AMOUNT", Double.parseDouble(brVO.get("A12AMOUNT").toString()));
			pageVO.getRows().get(0).put("A18AMOUNT", Double.parseDouble(brVO.get("A18AMOUNT").toString()));
			pageVO.getRows().get(0).put("PAYPRINCIPALAMTAMOUNT", Double.parseDouble(brVO.get("PAYPRINCIPALAMTAMOUNT").toString()));
			pageVO.getRows().get(0).put("PAYINTEAMTAMOUNT", Double.parseDouble(brVO.get("PAYINTEAMTAMOUNT").toString()));
		}
		
		return pageVO;
	}

	

	@Override
	public Map<String,Object> queryPredictcostCount(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return predictcostService.queryPredictcostCount(map);
	}

	
	@Override
	public PageVO queryPredictcostSum(PageVO pageVO,PredictcostVO vo) {
		// TODO Auto-generated method stub
		
		Map<String,Object> whereMap = new HashMap<String,Object>();
		//组装条件
		if (vo != null) {
			whereMap.put("startDueDate",vo.getStartDueDate());
			whereMap.put("endDueDate",vo.getEndDueDate());
			whereMap.put("belong",vo.getBelong());
			
			whereMap.put("creditperson",vo.getCreditperson());
			whereMap.put("guaranteeparty",vo.getGuaranteeparty());
			whereMap.put("city",vo.getCity());
			whereMap.put("subproducttype",vo.getSubproducttype());
			whereMap.put("businessmodel",vo.getBusinessmodel());
			whereMap.put("startKeepaccountsDate",vo.getStartKeepaccountsDate());
			whereMap.put("endKeepaccountsDate",vo.getEndKeepaccountsDate());
			whereMap.put("startPayDate",vo.getStartPayDate());
			whereMap.put("endPayDate",vo.getEndPayDate());
			
			whereMap.put("canceltype",vo.getCanceltype().toString());
			
			whereMap.put("startInaccountdate",vo.getStartInaccountdate());
			whereMap.put("endInaccountdate",vo.getEndInaccountdate());
		}

		//统计合同总数
		
		Map<String,Object> brVO = predictcostService.queryPredictcostCountSum(whereMap);
		
		Long total = Long.parseLong(brVO.get("CONTRACTCOUNT").toString()) ;
		
		//封装分页参数
		Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
		whereMap.putAll(whereMapPage);
		
		//查询合同明细
		List<Map<String, Object>> map2 = predictcostService.queryPredictcostSum(whereMap);
		pageVO.setRows(map2);
		
		if (brVO != null && brVO.get("MONEYCOUNT") != null && brVO.get("CONTRACTCOUNT") != null) {
			pageVO.getRows().get(0).put("moneyCount", Double.parseDouble(brVO.get("MONEYCOUNT").toString()));
			pageVO.getRows().get(0).put("A2AMOUNT", Double.parseDouble(brVO.get("A2AMOUNT").toString()));
			pageVO.getRows().get(0).put("A7AMOUNT", Double.parseDouble(brVO.get("A7AMOUNT").toString()));
			pageVO.getRows().get(0).put("A12AMOUNT", Double.parseDouble(brVO.get("A12AMOUNT").toString()));
			pageVO.getRows().get(0).put("A18AMOUNT", Double.parseDouble(brVO.get("A18AMOUNT").toString()));
			pageVO.getRows().get(0).put("PAYPRINCIPALAMTAMOUNT", Double.parseDouble(brVO.get("PAYPRINCIPALAMTAMOUNT").toString()));
			pageVO.getRows().get(0).put("PAYINTEAMTAMOUNT", Double.parseDouble(brVO.get("PAYINTEAMTAMOUNT").toString()));
		}
		
		return pageVO;
	}

	

	@Override
	public Map<String,Object> queryPredictcostCountSum(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return predictcostService.queryPredictcostCountSum(map);
	}

	@Override
	public List<PredictcostVO> queryPredictcostAll(PredictcostVO vo) {
		Map<String,Object> whereMap = new HashMap<String,Object>();
		//组装条件
		if (vo != null) {
			whereMap.put("startPayDate",vo.getStartPayDate());
			whereMap.put("endPayDate",vo.getEndPayDate());
			whereMap.put("belong",vo.getBelong());
			whereMap.put("contractno",vo.getContractno());
			whereMap.put("startRegistrationDate",vo.getStartRegistrationDate());
			whereMap.put("endRegistrationDate",vo.getEndRegistrationDate());
			whereMap.put("startKeepaccountsDate",vo.getStartKeepaccountsDate());
			whereMap.put("endKeepaccountsDate",vo.getEndKeepaccountsDate());
			
			whereMap.put("startInaccountdate",vo.getStartInaccountdate());
			whereMap.put("endInaccountdate",vo.getEndInaccountdate());
		}
		// TODO Auto-generated method stub
		List<PredictcostVO> list = null;
		try {
			list = predictcostService.queryPredictcostAll(whereMap);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return list;
	}
	
	@Override
	public List<PredictcostVO> queryPredictcostSumAll(PredictcostVO vo) {
		Map<String,Object> whereMap = new HashMap<String,Object>();
		//组装条件
		if (vo != null) {
			whereMap.put("startDueDate",vo.getStartDueDate());
			whereMap.put("endDueDate",vo.getEndDueDate());
			whereMap.put("belong",vo.getBelong());
			
			whereMap.put("creditperson",vo.getCreditperson());
			whereMap.put("guaranteeparty",vo.getGuaranteeparty());
			whereMap.put("city",vo.getCity());
			whereMap.put("subproducttype",vo.getSubproducttype());
			whereMap.put("businessmodel",vo.getBusinessmodel());
			whereMap.put("startKeepaccountsDate",vo.getStartKeepaccountsDate());
			whereMap.put("endKeepaccountsDate",vo.getEndKeepaccountsDate());
			whereMap.put("startPayDate",vo.getStartPayDate());
			whereMap.put("endPayDate",vo.getEndPayDate());
			
			whereMap.put("canceltype",vo.getCanceltype());
			
			whereMap.put("startInaccountdate",vo.getStartInaccountdate());
			whereMap.put("endInaccountdate",vo.getEndInaccountdate());
		}
		// TODO Auto-generated method stub
		List<PredictcostVO> list = null;
		try {
			list = predictcostService.queryPredictcostSumAll(whereMap);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public Boolean updatePredictcost(PredictcostVO vo) {
		// TODO Auto-generated method stub
		
		if(vo.getContractnoArray()!=null){
			String[] serialnoArray = vo.getContractnoArray();
			String[] seqidArray = vo.getSeqidArray();
			String[] assetbelongArray = vo.getBelongArray();
			String[] payprincipalamtArray = vo.getPayprincipalamtArray();
			String[] payinteamtArray = vo.getPayinteamtArray();
			
			if(serialnoArray.length>1&&serialnoArray.length==seqidArray.length&&seqidArray.length == assetbelongArray.length && 
			assetbelongArray.length == payprincipalamtArray.length && payprincipalamtArray.length == payinteamtArray.length){
				
				for (int i = 0; i < serialnoArray.length; i++) {
					PredictcostVO updateVO = new PredictcostVO();
					updateVO.setContractno(serialnoArray[i]);
					updateVO.setSeqid(seqidArray[i]);
					updateVO.setBelong(assetbelongArray[i]);
					updateVO.setPayprincipalamtStr(payprincipalamtArray[i]);
					updateVO.setPayinteamtStr(payinteamtArray[i]);
					updateVO.setInaccountby(vo.getInaccountby());
					updateVO.setInaccountremark(vo.getInaccountremark());
					updateVO.setInaccountdate(vo.getInaccountdate());
					//updateVO.setStartInaccountdate(vo.getStartInaccountdate());
					//updateVO.setEndInaccountdate(vo.getEndInaccountdate());
					//updateVO.setStartPayDate(vo.getStartPayDate());
					//updateVO.setEndPayDate(vo.getEndPayDate());
					predictcostService.updatePredictcost(updateVO);
				}
				return true;
			}else if(serialnoArray.length==1){
				predictcostService.updatePredictcost(vo);
				return true;
			}else{
				return false;
			}
			
		}else{			
			predictcostService.updatePredictcost(vo);
			return true;
		}
	}



	@Override
	public Integer checkPredictcost(PredictcostVO vo) {
		// TODO Auto-generated method stub
		return predictcostService.checkPredictcost(vo);
	}



	@Override
	public Map<String, Object> queryPredictcostTotal(PredictcostVO vo) {
		// TODO Auto-generated method stub
		
		Map<String,Object> whereMap = new HashMap<String,Object>();
		//组装条件
		if (vo != null) {
			whereMap.put("startPayDate",vo.getStartPayDate());
			whereMap.put("endPayDate",vo.getEndPayDate());
			whereMap.put("belong",vo.getBelong());
			whereMap.put("contractno",vo.getContractno());
			whereMap.put("startRegistrationDate",vo.getStartRegistrationDate());
			whereMap.put("endRegistrationDate",vo.getEndRegistrationDate());
			whereMap.put("startKeepaccountsDate",vo.getStartKeepaccountsDate());
			whereMap.put("endKeepaccountsDate",vo.getEndKeepaccountsDate());
			
			whereMap.put("startInaccountdate",vo.getStartInaccountdate());
			whereMap.put("endInaccountdate",vo.getEndInaccountdate());
		}

		//统计合同总数
		
		Map<String,Object> brVO = predictcostService.queryPredictcostCount(whereMap);
		
		return brVO;
	}

	@Override
	public Map<String, Object> queryPredictcostSumTotal(PredictcostVO vo) {
		// TODO Auto-generated method stub
		
		Map<String,Object> whereMap = new HashMap<String,Object>();
		//组装条件
		if (vo != null) {
			whereMap.put("startDueDate",vo.getStartDueDate());
			whereMap.put("endDueDate",vo.getEndDueDate());
			whereMap.put("belong",vo.getBelong());
			
			whereMap.put("creditperson",vo.getCreditperson());
			whereMap.put("guaranteeparty",vo.getGuaranteeparty());
			whereMap.put("city",vo.getCity());
			whereMap.put("subproducttype",vo.getSubproducttype());
			whereMap.put("businessmodel",vo.getBusinessmodel());
			whereMap.put("startKeepaccountsDate",vo.getStartKeepaccountsDate());
			whereMap.put("endKeepaccountsDate",vo.getEndKeepaccountsDate());
			whereMap.put("startPayDate",vo.getStartPayDate());
			whereMap.put("endPayDate",vo.getEndPayDate());
			
			whereMap.put("canceltype",vo.getCanceltype());
			
			whereMap.put("startInaccountdate",vo.getStartInaccountdate());
			whereMap.put("endInaccountdate",vo.getEndInaccountdate());
		}

		//统计合同总数
		
		Map<String,Object> brVO = predictcostService.queryPredictcostCountSum(whereMap);
		
		return brVO;
	}



	@Override
	public Integer checkCancelPredictcost(PredictcostVO vo) {
		// TODO Auto-generated method stub
		return predictcostService.checkCancelPredictcost(vo);
	}



	@Override
	public Boolean cancelPredictcost(PredictcostVO vo) {
		// TODO Auto-generated method stub
		
		if(vo.getContractnoArray()!=null){
			String[] serialnoArray = vo.getContractnoArray();
			String[] seqidArray = vo.getSeqidArray();
			String[] assetbelongArray = vo.getBelongArray();
			String[] payprincipalamtArray = vo.getPayprincipalamtArray();
			String[] payinteamtArray = vo.getPayinteamtArray();
			
			if(serialnoArray.length>1&&serialnoArray.length==seqidArray.length&&seqidArray.length == assetbelongArray.length && 
			assetbelongArray.length == payprincipalamtArray.length && payprincipalamtArray.length == payinteamtArray.length){
				
				for (int i = 0; i < serialnoArray.length; i++) {
					PredictcostVO updateVO = new PredictcostVO();
					updateVO.setContractno(serialnoArray[i]);
					updateVO.setSeqid(seqidArray[i]);
					updateVO.setBelong(assetbelongArray[i]);
					updateVO.setPayprincipalamtStr(payprincipalamtArray[i]);
					updateVO.setPayinteamtStr(payinteamtArray[i]);
					updateVO.setInaccountby(vo.getInaccountby());
					updateVO.setInaccountremark(vo.getInaccountremark());
					updateVO.setInaccountdate(vo.getInaccountdate());
					//updateVO.setStartInaccountdate(vo.getStartInaccountdate());
					//updateVO.setEndInaccountdate(vo.getEndInaccountdate());
					//updateVO.setStartPayDate(vo.getStartPayDate());
					//updateVO.setEndPayDate(vo.getEndPayDate());
					predictcostService.cancelPredictcost(updateVO);
				}
				return true;
			}else if(serialnoArray.length==1){
				predictcostService.cancelPredictcost(vo);
				return true;
			}else{
				return false;
			}
			
		}else{			
			predictcostService.cancelPredictcost(vo);
			return true;
		}
	}
	
}
