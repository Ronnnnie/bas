package com.billionsfinance.bas.server.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.entity.RepaymentDetailVO;
import com.billionsfinance.bas.server.IRepaymentDetailServer;
import com.billionsfinance.bas.service.RepaymentDetailService;
import com.billionsfinance.bas.util.CommonUtil;
import com.billionsfinance.bas.util.SpringService;
import com.billionsfinance.bas.util.StringUtil;

public class RepaymentDetailServer implements IRepaymentDetailServer {

	private RepaymentDetailService repaymentDetailService = SpringService.REPAYMENTDETAILSERVICE;
	
	@Override
	public PageVO queryRepaymentDetail(PageVO pageVO , RepaymentDetailVO vo) {

		Map<String, Object> whereMap = new HashMap<String, Object>();

		//组装条件

		if (vo != null) {
			whereMap.put("seqId",vo.getSeqId());
			whereMap.put("serialNo",vo.getSerialNo());
			whereMap.put("startDueDate",vo.getStartDueDate());
			whereMap.put("endDueDate",vo.getEndDueDate());
			whereMap.put("startPayDate",vo.getStartPayDate());
			whereMap.put("endPayDate",vo.getEndPayDate());
			whereMap.put("startRegistrationDate",vo.getStartRegistrationDate());
			whereMap.put("endRegistrationDate",vo.getEndRegistrationDate());
			whereMap.put("subProductType",vo.getSubProductType());
			whereMap.put("payType",vo.getPayType());
			whereMap.put("approveStatus",vo.getApproveStatus());
			whereMap.put("payStatus",vo.getPayStatus());
			whereMap.put("assetBelong",vo.getAssetBelong());
		}

		//统计合同总数
		
		Map<String,Object> brVO = repaymentDetailService.queryRepaymentDetailCount(whereMap);
		
		Long total = Long.parseLong(brVO.get("contractCount").toString()) ;
		
		//封装分页参数
		Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
		whereMap.putAll(whereMapPage);
		
		//查询合同明细
		List<Map<String, Object>> map2 = repaymentDetailService.queryRepaymentDetail(whereMap);
		pageVO.setRows(map2);
		
		if(pageVO.getRows().size()>0){
			//总匹配合同数 总匹配金额
			pageVO.getRows().get(0).put("moneyCount", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("moneyCount"))));
			pageVO.getRows().get(0).put("contractCount",total);
			pageVO.getRows().get(0).put("payPrincipalamtSum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("payPrincipalamtSum"))));
			pageVO.getRows().get(0).put("payInteAmtSum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("payInteAmtSum"))));
			pageVO.getRows().get(0).put("waiveintamtSum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("waiveintamtSum"))));
			pageVO.getRows().get(0).put("stampDutySum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("stampDutySum"))));
			pageVO.getRows().get(0).put("payAmtSum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("payAmtSum"))));
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		for (Map<String,Object> map :pageVO.getRows()) {
			Date date = (Date) map.get("duedate");
			if(date!=null){
				String duedate = sdf.format(date);
				map.put("duedate", duedate);
			}
		}
		return pageVO;
	}

	@Override
	public Map<String,Object> queryRepaymentDetailCount(RepaymentDetailVO repaymentDetailVO) {
		Map<String, Object> map = new HashMap<String, Object>();
		//组装条件
		if (repaymentDetailVO != null) {
			map.put("seqId",repaymentDetailVO.getSeqId());
			map.put("serialNo",repaymentDetailVO.getSerialNo());
			map.put("startDueDate",repaymentDetailVO.getStartDueDate());
			map.put("endDueDate",repaymentDetailVO.getEndDueDate());
			map.put("startPayDate",repaymentDetailVO.getStartPayDate());
			map.put("endPayDate",repaymentDetailVO.getEndPayDate());
			map.put("startRegistrationDate",repaymentDetailVO.getStartRegistrationDate());
			map.put("endRegistrationDate",repaymentDetailVO.getEndRegistrationDate());
			map.put("subProductType",repaymentDetailVO.getSubProductType());
			map.put("payType",repaymentDetailVO.getPayType());
			map.put("approveStatus",repaymentDetailVO.getApproveStatus());
			map.put("payStatus",repaymentDetailVO.getPayStatus());
			map.put("assetBelong",repaymentDetailVO.getAssetBelong());
		}
		return repaymentDetailService.queryRepaymentDetailCount(map);
	}

	@Override
	public int accountingMark(RepaymentDetailVO vo) {
		// TODO Auto-generated method stub
		return repaymentDetailService.accountingMark(vo);
	}
	
	@Override
	public int cancelAccountingMark(RepaymentDetailVO vo) {
		// TODO Auto-generated method stub
		return repaymentDetailService.cancelAccountingMark(vo);
	}
	
	@Override
	public void contractApprove(RepaymentDetailVO vo) {
		// TODO Auto-generated method stub
		repaymentDetailService.contractApprove(vo);
	}
	
	@Override
	public List<Map<String,Object>> queryRepaymentDetailFindAll(RepaymentDetailVO vo) {
		// TODO Auto-generated method stub
		List<Map<String,Object>> list = repaymentDetailService.queryRepaymentDetailFindAll(vo);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		for (Map<String,Object> map :list) {
			Date date = (Date) map.get("duedate");
			if(date!=null){
				String duedate = sdf.format(date);
				map.put("duedate", duedate);
			}
		}
		return list;
	}

}
