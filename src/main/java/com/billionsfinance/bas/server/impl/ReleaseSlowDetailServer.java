package com.billionsfinance.bas.server.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.entity.ReleaseSlowDetailVO;
import com.billionsfinance.bas.server.IReleaseSlowDetailServer;
import com.billionsfinance.bas.service.ReleaseSlowDetailService;
import com.billionsfinance.bas.util.CommonUtil;
import com.billionsfinance.bas.util.SpringService;
import com.billionsfinance.bas.util.StringUtil;

public class ReleaseSlowDetailServer implements IReleaseSlowDetailServer {

	private ReleaseSlowDetailService releaseSlowDetailService = SpringService.RELEASESLOWDETAILSERVICE;
	
	@Override
	public PageVO queryRepaymentDetail(PageVO pageVO , ReleaseSlowDetailVO vo) {

		Map<String, Object> whereMap = new HashMap<String, Object>();

		//组装条件

		if (vo != null) {
			whereMap.put("serialNo",vo.getSerialNo());
			whereMap.put("startKeepAccountsDate",vo.getStartKeepAccountsDate());
			whereMap.put("endKeepAccountsDate",vo.getEndKeepAccountsDate());
			whereMap.put("sffk",vo.getSffk());
		}

		//统计合同总数
		
		Map<String,Object> brVO = releaseSlowDetailService.queryRepaymentDetailCount(whereMap);
		
		Long total = Long.parseLong(brVO.get("contractCount").toString()) ;
		
		//封装分页参数
		Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
		whereMap.putAll(whereMapPage);
		
		//查询合同明细
		List<Map<String, Object>> map2 = releaseSlowDetailService.queryRepaymentDetail(whereMap);
		pageVO.setRows(map2);
		
		if(pageVO.getRows().size()>0){
			//总匹配合同数 总匹配金额
			pageVO.getRows().get(0).put("moneyCount", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("moneyCount"))));
			pageVO.getRows().get(0).put("contractCount",total);
			pageVO.getRows().get(0).put("businessSumCount", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("moneyCount"))));
		}
		return pageVO;
	}

	@Override
	public Map<String,Object> queryRepaymentDetailCount(ReleaseSlowDetailVO vo) {
		Map<String, Object> map = new HashMap<String, Object>();
		//组装条件
		if (map != null) {
			map.put("serialNo",vo.getSerialNo());
			map.put("startKeepAccountsDate",vo.getStartKeepAccountsDate());
			map.put("endKeepAccountsDate",vo.getEndKeepAccountsDate());
			map.put("sffk",vo.getSffk());
		}
		return releaseSlowDetailService.queryRepaymentDetailCount(map);
	}

	@Override
	public int accountingMark(ReleaseSlowDetailVO vo) {
		// TODO Auto-generated method stub
		return releaseSlowDetailService.accountingMark(vo);
	}
	
	@Override
	public int cancelAccountingMark(ReleaseSlowDetailVO vo) {
		// TODO Auto-generated method stub
		return releaseSlowDetailService.cancelAccountingMark(vo);
	}
	
	@Override
	public void contractApprove(ReleaseSlowDetailVO vo) {
		// TODO Auto-generated method stub
		releaseSlowDetailService.contractApprove(vo);
	}
	
	@Override
	public List<Map<String,Object>> queryRepaymentDetailFindAll(ReleaseSlowDetailVO vo) {
		// TODO Auto-generated method stub
		return releaseSlowDetailService.queryRepaymentDetailFindAll(vo);
	}

}
