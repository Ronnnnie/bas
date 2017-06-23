package com.billionsfinance.bas.server.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.VillageReceivedPaymentsVO;
import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.server.IVillageReceivedPaymentsServer;
import com.billionsfinance.bas.service.VillageReceivedPaymentsService;
import com.billionsfinance.bas.util.CommonUtil;
import com.billionsfinance.bas.util.SpringService;
import com.billionsfinance.bas.util.StringUtil;

public class VillageReceivedPaymentsServer implements IVillageReceivedPaymentsServer {

	private VillageReceivedPaymentsService villageReceivedPaymentsService = SpringService.VILLAGERECEIVEDPAYMENTS;
	
	@Override
	public PageVO queryVillageReceivedPaymentsDetail(PageVO pageVO, VillageReceivedPaymentsVO vo) {
		Map<String, Object> whereMap = new HashMap<String, Object>();
		//组装条件
		if (vo != null) {
			whereMap.put("seqId",vo.getSeqId());
			whereMap.put("contractNo",vo.getContractNo());
			whereMap.put("startPayDate",vo.getStartPayDate());
			whereMap.put("endPayDate",vo.getEndPayDate());
			whereMap.put("startActualPayDate",vo.getStartActualPayDate());
			whereMap.put("endActualPayDate",vo.getEndActualPayDate());
			whereMap.put("startKeepAccountsDate",vo.getStartKeepAccountsDate());
			whereMap.put("endKeepAccountsDate",vo.getEndKeepAccountsDate());
			whereMap.put("startRegistrationDate",vo.getStartRegistrationDate());
			whereMap.put("endRegistrationDate",vo.getEndRegistrationDate());
			whereMap.put("payType",vo.getPayType());
		}

		//统计合同总数
		
		Map<String,Object> brVO = villageReceivedPaymentsService.queryVillageReceivedPaymentsCount(whereMap);
		
		Long total = Long.parseLong(brVO.get("contractCount").toString()) ;
		
		//封装分页参数
		Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
		whereMap.putAll(whereMapPage);
		
		//查询合同明细
		List<Map<String, Object>> map2 = villageReceivedPaymentsService.queryVillageReceivedPaymentsDetail(whereMap);
		pageVO.setRows(map2);
		
		//总匹配合同数 总匹配金额
		if(pageVO.getRows().size()>0){
			pageVO.getRows().get(0).put("moneyCount", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("moneyCount"))));
			pageVO.getRows().get(0).put("contractCount",total);
			pageVO.getRows().get(0).put("payprinciPalamtSum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("payprinciPalamtSum"))));
			pageVO.getRows().get(0).put("payInteamtSum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("payInteamtSum"))));
			pageVO.getRows().get(0).put("actualpayprincipalamtSum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("actualpayprincipalamt"))));
			pageVO.getRows().get(0).put("actualpayinteamtSum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("actualpayinteamt"))));
            pageVO.getRows().get(0).put("payprindefaultinteamtSum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("payprindefaultinteamt"))));
			//@黄瀚林 业务帐系统，村行回款，把这个逾期罚息删了，增加2列实还本息罚息和实还利息罚息
            pageVO.getRows().get(0).put("actpayprindefaultinteamtSum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("actpayprindefaultinteamt"))));
            pageVO.getRows().get(0).put("actpayintedefaultinteamtSum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("actpayintedefaultinteamt"))));

            pageVO.getRows().get(0).put("businessSumCount", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("businessSumCount"))));
		}
		return pageVO;
	}

	@Override
	public int accountingMark(VillageReceivedPaymentsVO vo) {
		// TODO Auto-generated method stub
		return villageReceivedPaymentsService.accountingMark(vo);
	}
	
	@Override
	public int cancelAccountingMark(VillageReceivedPaymentsVO vo) {
		// TODO Auto-generated method stub
		return villageReceivedPaymentsService.cancelAccountingMark(vo);
	}
	
	@Override
	public int approveContract(VillageReceivedPaymentsVO vo) {
		// TODO Auto-generated method stub
		return villageReceivedPaymentsService.approveContract(vo);
	}
	
	@Override
	public List<Map<String,Object>> queryVillageReceivedPaymentsFindAll(VillageReceivedPaymentsVO vo) {
		// TODO Auto-generated method stub
		return villageReceivedPaymentsService.queryVillageReceivedPaymentsFindAll(vo);
	}
	
	@Override
	public List<Map<String,Object>> villageReceivedPaymentsExport(VillageReceivedPaymentsVO vo) {
		// TODO Auto-generated method stub
		return villageReceivedPaymentsService.villageReceivedPaymentsExport(vo);
	}

	@Override
	public List<Map<String, Object>> queryVillageReceivedPaymentsContract(VillageReceivedPaymentsVO vo) {
		Map<String, Object> whereMap = new HashMap<String, Object>();
		//组装条件
		if (vo != null) {
			whereMap.put("seqId",vo.getSeqId());
			whereMap.put("contractNo",vo.getContractNo());
			whereMap.put("startPayDate",vo.getStartPayDate());
			whereMap.put("endPayDate",vo.getEndPayDate());
			whereMap.put("startActualPayDate",vo.getStartActualPayDate());
			whereMap.put("endActualPayDate",vo.getEndActualPayDate());
			whereMap.put("startKeepAccountsDate",vo.getStartKeepAccountsDate());
			whereMap.put("endKeepAccountsDate",vo.getEndKeepAccountsDate());
			whereMap.put("startRegistrationDate",vo.getStartRegistrationDate());
			whereMap.put("endRegistrationDate",vo.getEndRegistrationDate());
			whereMap.put("payType",vo.getPayType());
		}
		return villageReceivedPaymentsService.queryVillageReceivedPaymentsContract(whereMap);
	}

	@Override
	public Map<String, Object> queryVillageReceivedPaymentsCount(VillageReceivedPaymentsVO vo) {
		// TODO Auto-generated method stub
		Map<String, Object> whereMap = new HashMap<String, Object>();
		//组装条件
		if (vo != null) {
			whereMap.put("seqId",vo.getSeqId());
			whereMap.put("contractNo",vo.getContractNo());
			whereMap.put("startPayDate",vo.getStartPayDate());
			whereMap.put("endPayDate",vo.getEndPayDate());
			whereMap.put("startActualPayDate",vo.getStartActualPayDate());
			whereMap.put("endActualPayDate",vo.getEndActualPayDate());
			whereMap.put("startKeepAccountsDate",vo.getStartKeepAccountsDate());
			whereMap.put("endKeepAccountsDate",vo.getEndKeepAccountsDate());
			whereMap.put("startRegistrationDate",vo.getStartRegistrationDate());
			whereMap.put("endRegistrationDate",vo.getEndRegistrationDate());
			whereMap.put("payType",vo.getPayType());
		}
		return villageReceivedPaymentsService.queryVillageReceivedPaymentsCount(whereMap);
	}


}
