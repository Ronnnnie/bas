package com.billionsfinance.bas.server.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.entity.RepaymentDetailCheckAccountVO;
import com.billionsfinance.bas.server.IRepaymentDetailCheckAccountServer;
import com.billionsfinance.bas.service.RepaymentDetailCheckAccountService;
import com.billionsfinance.bas.util.CommonUtil;
import com.billionsfinance.bas.util.SpringService;
import com.billionsfinance.bas.util.StringUtil;

public class RepaymentDetailCheckAccountServer implements IRepaymentDetailCheckAccountServer {

	private RepaymentDetailCheckAccountService repaymentDetailCheckAccountService = SpringService.REPAYMENTDETAILCHECKACCOUNTSERVICE;
	
	@Override
	public PageVO queryRepaymentDetailCheckAccount(PageVO pageVO , RepaymentDetailCheckAccountVO vo) {

		Map<String, Object> whereMap = new HashMap<String, Object>();

		//组装条件

		if (vo != null) {
			whereMap.put("startCheckdate",vo.getStartcheckdate());
			whereMap.put("endCheckdate",vo.getEndcheckdate());
			whereMap.put("datasource",vo.getDatasource());
		}

		//统计合同总数
		
		Map<String,Object> brVO = repaymentDetailCheckAccountService.queryRepaymentDetailCheckAccountTotal(whereMap);
		
		Long total = Long.parseLong(brVO.get("contractCount").toString()) ;
		
		//封装分页参数
		Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
		whereMap.putAll(whereMapPage);
		
		//查询合同明细
		List<Map<String, Object>> map2 = repaymentDetailCheckAccountService.queryRepaymentDetailCheckAccount(whereMap);
		pageVO.setRows(map2);
		
		if(pageVO.getRows().size()>0){
			//总匹配合同数 总匹配金额
			pageVO.getRows().get(0).put("payprincipalamtsum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("payprincipalamtsum"))));
			pageVO.getRows().get(0).put("payinteamtsum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("payinteamtsum"))));
			pageVO.getRows().get(0).put("a2sum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("a2sum"))));
			pageVO.getRows().get(0).put("a7sum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("a7sum"))));
			pageVO.getRows().get(0).put("a9sum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("a9sum"))));
			pageVO.getRows().get(0).put("a10sum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("a10sum"))));
			pageVO.getRows().get(0).put("a11sum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("a11sum"))));
			pageVO.getRows().get(0).put("a12sum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("a12sum"))));
			pageVO.getRows().get(0).put("a17sum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("a17sum"))));
			pageVO.getRows().get(0).put("a18sum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("a18sum"))));
			pageVO.getRows().get(0).put("a19sum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("a19sum"))));
			pageVO.getRows().get(0).put("a22sum", Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("a22sum"))));
			pageVO.getRows().get(0).put("contractCount",total);
		}
		return pageVO;
	}

	@Override
	public Map<String,Object> queryRepaymentDetailCheckAccountTotal(RepaymentDetailCheckAccountVO vo) {
		Map<String, Object> map = new HashMap<String, Object>();
		//组装条件
		if (map != null) {
			map.put("startCheckdate",vo.getStartcheckdate());
			map.put("endCheckdate",vo.getEndcheckdate());
			map.put("datasource",vo.getDatasource());
		}
		return repaymentDetailCheckAccountService.queryRepaymentDetailCheckAccountTotal(map);
	}
	
	@Override
	public List<Map<String,Object>> queryRepaymentDetailCheckAccountFindAll(RepaymentDetailCheckAccountVO vo) {
		return repaymentDetailCheckAccountService.queryRepaymentDetailCheckAccountFindAll(vo);
	}

}
