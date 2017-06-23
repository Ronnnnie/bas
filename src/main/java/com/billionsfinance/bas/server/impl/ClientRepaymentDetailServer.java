package com.billionsfinance.bas.server.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.ClientRepaymentVO;
import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.server.IClientRepaymentDetailServer;
import com.billionsfinance.bas.service.ClientRepaymentDetailService;
import com.billionsfinance.bas.util.CommonUtil;
import com.billionsfinance.bas.util.SpringService;

public class ClientRepaymentDetailServer implements IClientRepaymentDetailServer {

	private ClientRepaymentDetailService repaymentDetailService = SpringService.CLIENTREPAYMENTDETAILSERVICE;
	
	@Override
	public PageVO queryRepaymentDetail(PageVO pageVO , ClientRepaymentVO vo) {

		Map<String, Object> whereMap = new HashMap<String, Object>();

		//组装条件

		if (vo != null) {
			whereMap.put("serialNo",vo.getSerialNo());
			whereMap.put("seqId",vo.getSeqId());
			whereMap.put("startKeepaccountsDate",vo.getStartKeepaccountsDate());
			whereMap.put("endKeepaccountsDate",vo.getEndKeepaccountsDate());
			whereMap.put("startActualPayDate",vo.getStartActualPayDate());
			whereMap.put("endActualPayDate",vo.getEndActualPayDate());
			whereMap.put("startRegistrationDate",vo.getStartRegistrationDate());
			whereMap.put("endRegistrationDate",vo.getEndRegistrationDate());
			whereMap.put("startApproveTime",vo.getStartApproveTime());
			whereMap.put("endApproveTime",vo.getEndApproveTime());
			whereMap.put("subProductType",vo.getSubProductType());
			whereMap.put("payType",vo.getPayType());
			whereMap.put("approveStatus",vo.getApproveStatus());
			whereMap.put("payStatus",vo.getPayStatus());
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
		
		//总匹配合同数 总匹配金额
		if(pageVO.getRows().size()>0){
			pageVO.getRows().get(0).put("actualPayPrincipalAmtSum", Double.parseDouble(brVO.get("actualPayPrincipalAmtSum").toString()));
			pageVO.getRows().get(0).put("actualPayinteAmtSum",Double.parseDouble(brVO.get("actualPayinteAmtSum").toString()));
			pageVO.getRows().get(0).put("payAmtSum",Double.parseDouble(brVO.get("payAmtSum").toString()));
		}
		
		return pageVO;
	}

	@Override
	public Map<String,Object> queryRepaymentDetailCount(ClientRepaymentVO vo) {
		// TODO Auto-generated method stub
		Map<String, Object> whereMap = new HashMap<String, Object>();
		if (vo != null) {
			whereMap.put("seqId",vo.getSeqId());
			whereMap.put("serialNo",vo.getSerialNo());
			whereMap.put("startKeepaccountsDate",vo.getStartKeepaccountsDate());
			whereMap.put("endKeepaccountsDate",vo.getEndKeepaccountsDate());
			whereMap.put("startActualPayDate",vo.getStartActualPayDate());
			whereMap.put("endActualPayDate",vo.getEndActualPayDate());
			whereMap.put("startRegistrationDate",vo.getStartRegistrationDate());
			whereMap.put("endRegistrationDate",vo.getEndRegistrationDate());
			whereMap.put("startApproveTime",vo.getStartApproveTime());
			whereMap.put("endApproveTime",vo.getEndApproveTime());
			whereMap.put("subProductType",vo.getSubProductType());
			whereMap.put("payType",vo.getPayType());
			whereMap.put("approveStatus",vo.getApproveStatus());
			whereMap.put("payStatus",vo.getPayStatus());
		}
		return repaymentDetailService.queryRepaymentDetailCount(whereMap);
	}

	@Override
	public int accountingMark(ClientRepaymentVO vo) {
		// TODO Auto-generated method stub
		return repaymentDetailService.accountingMark(vo);
	}
	
	@Override
	public int cancelAccountingMark(ClientRepaymentVO vo) {
		// TODO Auto-generated method stub
		return repaymentDetailService.cancelAccountingMark(vo);
	}
	
	@Override
	public void contractApprove(ClientRepaymentVO vo) {
		// TODO Auto-generated method stub
		repaymentDetailService.contractApprove(vo);
	}
	
	@Override
	public List<Map<String,Object>> queryRepaymentDetailById(ClientRepaymentVO vo) {
		// TODO Auto-generated method stub
		return repaymentDetailService.queryRepaymentDetailById(vo);
	}
	
	@Override
	public List<Map<String,Object>> queryRepaymentDetailFindAll(ClientRepaymentVO vo) {
		// TODO Auto-generated method stub
		return repaymentDetailService.queryRepaymentDetailFindAll(vo);
	}

}
