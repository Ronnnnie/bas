package com.billionsfinance.bas.server.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.entity.RansomDetailVO;
import com.billionsfinance.bas.server.IRansomDetailServer;
import com.billionsfinance.bas.service.RansomDetailService;
import com.billionsfinance.bas.util.CommonUtil;
import com.billionsfinance.bas.util.SpringService;
import com.billionsfinance.bas.util.StringUtil;

public class RansomDetailServer implements IRansomDetailServer {

	private RansomDetailService ransomDetailService = SpringService.RANSOMDETAILSERVICE;
	
	@Override
	public PageVO queryRepaymentDetail(PageVO pageVO , RansomDetailVO vo) {

		Map<String, Object> whereMap = new HashMap<String, Object>();

		//组装条件

		if (vo != null) {
			whereMap.put("contractNo",vo.getContractNo());
			whereMap.put("seqId",vo.getSeqId());
			whereMap.put("startKeepaccountsDate",vo.getStartKeepaccountsDate());
			whereMap.put("endKeepaccountsDate",vo.getEndKeepaccountsDate());
			whereMap.put("startApproveTime",vo.getStartApproveTime());
			whereMap.put("endApproveTime",vo.getEndApproveTime());
			whereMap.put("productSubType",vo.getProductSubType());
			whereMap.put("startAtoneForDate",vo.getStartAtoneForDate());
			whereMap.put("endAtoneForDate",vo.getEndAtoneForDate());
			whereMap.put("startRegisterDate",vo.getStartRegisterDate());
			whereMap.put("endRegisterDate",vo.getEndRegisterDate());
			whereMap.put("approveStatus",vo.getApproveStatus());
		}

		//统计合同总数
		
		Map<String,Object> brVO = ransomDetailService.queryRepaymentDetailCount(whereMap);
		
		Long total = Long.parseLong(brVO.get("contractCount").toString()) ;
		
		//封装分页参数
		Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
		whereMap.putAll(whereMapPage);
		
		//查询合同明细
		List<Map<String, Object>> map2 = ransomDetailService.queryRepaymentDetail(whereMap);
		pageVO.setRows(map2);
		
		//总匹配合同数 总匹配金额
		if(pageVO.getRows().size()>0){
			pageVO.getRows().get(0).put("contractCount",Long.parseLong(StringUtil.isNullOrEmpty(brVO.get("contractCount"))));
			pageVO.getRows().get(0).put("moneyCount", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("moneyCount"))));
			pageVO.getRows().get(0).put("principalRemainingSumCount", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("principalRemainingSumCount"))));
			pageVO.getRows().get(0).put("overDuePrincipalSum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("overDuePrincipalSum"))));
			pageVO.getRows().get(0).put("overDueInterestSum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("overDueInterestSum"))));
			pageVO.getRows().get(0).put("totalPremiumSum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("totalPremiumSum"))));
			pageVO.getRows().get(0).put("premiumSum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("premiumSum"))));
			pageVO.getRows().get(0).put("atoneForAmountSum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("atoneForAmountSum"))));
		}
		
		return pageVO;
	}

	@Override
	public Map<String,Object> queryRepaymentDetailCount(RansomDetailVO vo) {
		// TODO Auto-generated method stub
		Map<String, Object> whereMap = new HashMap<String, Object>();
		if (vo != null) {
			whereMap.put("contractNo",vo.getContractNo());
			whereMap.put("seqId",vo.getSeqId());
			whereMap.put("startKeepaccountsDate",vo.getStartKeepaccountsDate());
			whereMap.put("endKeepaccountsDate",vo.getEndKeepaccountsDate());
			whereMap.put("startApproveTime",vo.getStartApproveTime());
			whereMap.put("endApproveTime",vo.getEndApproveTime());
			whereMap.put("productSubType",vo.getProductSubType());
			whereMap.put("startAtoneForDate",vo.getStartAtoneForDate());
			whereMap.put("endAtoneForDate",vo.getEndAtoneForDate());
			whereMap.put("startRegisterDate",vo.getStartRegisterDate());
			whereMap.put("endRegisterDate",vo.getEndRegisterDate());
			whereMap.put("approveStatus",vo.getApproveStatus());
		}
		return ransomDetailService.queryRepaymentDetailCount(whereMap);
	}

	@Override
	public int accountingMark(RansomDetailVO vo) {
		// TODO Auto-generated method stub
		return ransomDetailService.accountingMark(vo);
	}
	
	@Override
	public int cancelAccountingMark(RansomDetailVO vo) {
		// TODO Auto-generated method stub
		return ransomDetailService.cancelAccountingMark(vo);
	}
	
	@Override
	public void contractApprove(RansomDetailVO vo) {
		// TODO Auto-generated method stub
		ransomDetailService.contractApprove(vo);
	}
	
	@Override
	public List<RansomDetailVO> queryRepaymentDetailById(RansomDetailVO vo) {
		// TODO Auto-generated method stub
		return ransomDetailService.queryRepaymentDetailById(vo);
	}
	
	@Override
	public List<Map<String,Object>> queryRepaymentDetailFindAll(RansomDetailVO vo) {
		// TODO Auto-generated method stub
		return ransomDetailService.queryRepaymentDetailFindAll(vo);
	}

}
