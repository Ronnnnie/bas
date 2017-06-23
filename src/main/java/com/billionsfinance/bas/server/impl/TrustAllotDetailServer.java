package com.billionsfinance.bas.server.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.entity.TrustAllotDetailVO;
import com.billionsfinance.bas.server.ITrustAllotDetailServer;
import com.billionsfinance.bas.service.TrustAllotDetailService;
import com.billionsfinance.bas.util.CommonUtil;
import com.billionsfinance.bas.util.SpringService;
import com.billionsfinance.bas.util.StringUtil;

public class TrustAllotDetailServer implements ITrustAllotDetailServer {

	private TrustAllotDetailService trustAllotDetailService = SpringService.TRUSTALLOTDETAILSERVICE;
	
	@Override
	public PageVO queryBusinessDetail(PageVO pageVO , TrustAllotDetailVO vo) {

		Map<String, Object> whereMap = new HashMap<String, Object>();

		//组装条件

		if (vo != null) {
			whereMap.put("serialNo",vo.getSerialNo());
			whereMap.put("assetBelong",vo.getAssetBelong());
			whereMap.put("startPayDate",vo.getStartPayDate());
			whereMap.put("endPayDate",vo.getEndPayDate());
			whereMap.put("startPayTime",vo.getStartPayTime());
			whereMap.put("endPayTime",vo.getEndPayTime());
		}

		//统计合同总数
		
		Map<String,Object> brVO = trustAllotDetailService.queryTrustAllotDetailCount(whereMap);
		
		Long total = Long.parseLong(brVO.get("contractCount").toString()) ;
		
		//封装分页参数
		Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
		whereMap.putAll(whereMapPage);
		
		//查询合同明细
		List<Map<String, Object>> map2 = trustAllotDetailService.queryBusinessDetail(whereMap);
		pageVO.setRows(map2);
		
		//总匹配合同数 总匹配金额
		if (brVO != null && brVO.get("moneyCount") != null && brVO.get("contractCount") != null) {
			pageVO.getRows().get(0).put("moneyCount", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("moneyCount"))));
			pageVO.getRows().get(0).put("contractCount",total);
		}
		
		return pageVO;
	}

	@Override
	public Map<String,Object> queryTrustAllotDetailCount(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return trustAllotDetailService.queryTrustAllotDetailCount(map);
	}

	@Override
	public void accountingMark(TrustAllotDetailVO vo) {
		// TODO Auto-generated method stub
		trustAllotDetailService.accountingMark(vo);
	}
	
	@Override
	public List<TrustAllotDetailVO> queryTrustAllotDetailById(TrustAllotDetailVO vo) {
		// TODO Auto-generated method stub
		return trustAllotDetailService.queryTrustAllotDetailById(vo);
	}

}
