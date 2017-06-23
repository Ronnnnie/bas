package com.billionsfinance.bas.server.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.CompensatoryDetailVO;
import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.server.ICompensatoryDetailServer;
import com.billionsfinance.bas.service.CompensatoryDetailService;
import com.billionsfinance.bas.util.CommonUtil;
import com.billionsfinance.bas.util.SpringService;
import com.billionsfinance.bas.util.StringUtil;

public class CompensatoryDetailServer implements ICompensatoryDetailServer {

	private CompensatoryDetailService compensatoryDetailService = SpringService.COMPENSATORYDETAILSERVICE;
	
	@Override
	public PageVO queryRepaymentDetail(PageVO pageVO , CompensatoryDetailVO vo) {

		Map<String, Object> whereMap = new HashMap<String, Object>();

		//组装条件

		if (vo != null) {
			whereMap.put("contractNo",vo.getContractNo());
			whereMap.put("seqId",vo.getSeqId());
			whereMap.put("startKeepaccountsDate",vo.getStartKeepaccountsDate());
			whereMap.put("endKeepaccountsDate",vo.getEndKeepaccountsDate());
			whereMap.put("startDcDate",vo.getStartDcDate());
			whereMap.put("endDcDate",vo.getEndDcDate());
			whereMap.put("startRegisterDate",vo.getStartRegisterDate());
			whereMap.put("endRegisterDate",vo.getEndRegisterDate());
			whereMap.put("productSubType",vo.getProductSubType());
		}

		//统计合同总数
		
		Map<String,Object> brVO = compensatoryDetailService.queryRepaymentDetailCount(whereMap);
		
		Long total = Long.parseLong(brVO.get("contractCount").toString()) ;
		
		//封装分页参数
		Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
		whereMap.putAll(whereMapPage);
		
		//查询合同明细
		List<Map<String, Object>> map2 = compensatoryDetailService.queryRepaymentDetail(whereMap);
		pageVO.setRows(map2);
		if(pageVO.getRows().size()>0){
			//总匹配合同数 总匹配金额
			pageVO.getRows().get(0).put("contractCount",Long.parseLong(brVO.get("contractCount").toString()));
			pageVO.getRows().get(0).put("moneyCount", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("moneyCount"))));
			pageVO.getRows().get(0).put("principalBlanceSum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("principalBlanceSum"))));
			pageVO.getRows().get(0).put("dcoverDueprinCipalBeforeSum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("dcoverDueprinCipalBeforeSum"))));
			pageVO.getRows().get(0).put("dcoverDueinterestBeforeSum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("dcoverDueinterestBeforeSum"))));
			pageVO.getRows().get(0).put("overDuePrincipalSum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("overDuePrincipalSum"))));
			pageVO.getRows().get(0).put("overDueInterestSum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("overDueInterestSum"))));
			pageVO.getRows().get(0).put("dcPrincipalSum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("dcPrincipalSum"))));
			pageVO.getRows().get(0).put("dcInterestSum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("dcInterestSum"))));
			pageVO.getRows().get(0).put("dcAmountSum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("dcAmountSum"))));
		}
		return pageVO;
	}

	@Override
	public Map<String,Object> queryRepaymentDetailCount(CompensatoryDetailVO vo) {
		// TODO Auto-generated method stub
		Map<String, Object> whereMap = new HashMap<String, Object>();
		if (vo != null) {
			whereMap.put("contractNo",vo.getContractNo());
			whereMap.put("seqId",vo.getSeqId());
			whereMap.put("startKeepaccountsDate",vo.getStartKeepaccountsDate());
			whereMap.put("endKeepaccountsDate",vo.getEndKeepaccountsDate());
			whereMap.put("startDcDate",vo.getStartDcDate());
			whereMap.put("endDcDate",vo.getEndDcDate());
			whereMap.put("startRegisterDate",vo.getStartRegisterDate());
			whereMap.put("endRegisterDate",vo.getEndRegisterDate());
			whereMap.put("productSubType",vo.getProductSubType());
		}
		return compensatoryDetailService.queryRepaymentDetailCount(whereMap);
	}

	@Override
	public int accountingMark(CompensatoryDetailVO vo) {
		// TODO Auto-generated method stub
		return compensatoryDetailService.accountingMark(vo);
	}
	
	@Override
	public int cancelAccountingMark(CompensatoryDetailVO vo) {
		// TODO Auto-generated method stub
		return compensatoryDetailService.cancelAccountingMark(vo);
	}
	
	@Override
	public void contractApprove(CompensatoryDetailVO vo) {
		// TODO Auto-generated method stub
		compensatoryDetailService.contractApprove(vo);
	}
	
	@Override
	public List<CompensatoryDetailVO> queryRepaymentDetailById(CompensatoryDetailVO vo) {
		// TODO Auto-generated method stub
		return compensatoryDetailService.queryRepaymentDetailById(vo);
	}
	
	@Override
	public List<Map<String,Object>> queryRepaymentDetailFindAll(CompensatoryDetailVO vo) {
		// TODO Auto-generated method stub
		return compensatoryDetailService.queryRepaymentDetailFindAll(vo);
	}

}
