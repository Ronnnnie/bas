package com.billionsfinance.bas.server.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.entity.XZXDPaymentsVO;
import com.billionsfinance.bas.server.IXZXDPaymentsServer;
import com.billionsfinance.bas.service.XZXDPaymentsService;
import com.billionsfinance.bas.util.CommonUtil;
import com.billionsfinance.bas.util.SpringService;
import com.billionsfinance.bas.util.StringUtil;

public class XZXDPaymentsServer implements IXZXDPaymentsServer {

	private XZXDPaymentsService paymentsService = SpringService.XZXDPAYMENTSSERVICE;
	
	@Override
	public PageVO queryXZXDPaymentsDetail(PageVO pageVO , XZXDPaymentsVO vo) {

		Map<String, Object> map = new HashMap<String, Object>();

		//组装条件

		if (vo != null) {
			map.put("accordmonth",vo.getAccordmonth());
			map.put("startpaydate",vo.getStartpaydate());
			map.put("endpaydate",vo.getEndpaydate());
			map.put("starttransferpaypaymentday",vo.getStarttransferpaypaymentday());
			map.put("endtransferpaypaymentday",vo.getEndtransferpaypaymentday());
			map.put("startactualpaydate",vo.getStartactualpaydate());
			map.put("endactualpaydate",vo.getEndactualpaydate());
			map.put("startregistrationdate",vo.getStartregistrationdate());
			map.put("endregistrationdate",vo.getEndregistrationdate());
			map.put("subproducttype",vo.getSubproducttype());
			map.put("paytype",vo.getPaytype());
		}

		//统计合同总数
		
		Map<String,Object> brVO = paymentsService.queryXZXDPaymentsCount(map);
		
		Long total = Long.parseLong(brVO.get("contractCount").toString()) ;
		
		//封装分页参数
		Map<String, Object> whereMapPage = CommonUtil.getWhereMapPage(pageVO, total);
		map.putAll(whereMapPage);
		
		//查询合同明细
		List<Map<String, Object>> map2 = paymentsService.queryXZXDPaymentsDetail(map);
		pageVO.setRows(map2);
		
		if(pageVO.getRows().size()>0){
			//总匹配合同数 总匹配金额
			pageVO.getRows().get(0).put("businesssumtotal",Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("businesssumtotal"))));
			pageVO.getRows().get(0).put("payprincipalamtsum",Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("payprincipalamtsum"))));
			pageVO.getRows().get(0).put("payinteamtsum",Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("payinteamtsum"))));
			pageVO.getRows().get(0).put("sywhzesum",Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("sywhzesum"))));
			pageVO.getRows().get(0).put("payamtsum",Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("payamtsum"))));
			pageVO.getRows().get(0).put("yhlxfxsum",Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("yhlxfxsum"))));
			pageVO.getRows().get(0).put("yhbjfxsum",Double.parseDouble(StringUtil.checkMoneyIsNull(brVO.get("yhbjfxsum"))));
			pageVO.getRows().get(0).put("contractCount",total);
		}
		return pageVO;
	}

	@Override
	public Map<String,Object> queryXZXDPaymentsCount(XZXDPaymentsVO vo) {
		Map<String, Object> map = new HashMap<String, Object>();
		//组装条件
		if (map != null) {
			map.put("accordmonth",vo.getAccordmonth());
			map.put("startpaydate",vo.getStartpaydate());
			map.put("endpaydate",vo.getEndpaydate());
			map.put("starttransferpaypaymentday",vo.getStarttransferpaypaymentday());
			map.put("endtransferpaypaymentday",vo.getEndtransferpaypaymentday());
			map.put("startactualpaydate",vo.getStartactualpaydate());
			map.put("endactualpaydate",vo.getEndactualpaydate());
			map.put("startregistrationdate",vo.getStartregistrationdate());
			map.put("endregistrationdate",vo.getEndregistrationdate());
			map.put("subproducttype",vo.getSubproducttype());
			map.put("paytype",vo.getPaytype());
		}
		return paymentsService.queryXZXDPaymentsCount(map);
	}

	@Override
	public int paymentsAffirm(XZXDPaymentsVO vo) {
		// TODO Auto-generated method stub
		return paymentsService.paymentsAffirm(vo);
	}
	
	@Override
	public List<Map<String,Object>> queryXZXDPaymentsFindAll(XZXDPaymentsVO vo){
		return paymentsService.queryXZXDPaymentsFindAll(vo);
	}

}
