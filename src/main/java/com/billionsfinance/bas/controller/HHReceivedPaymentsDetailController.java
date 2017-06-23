package com.billionsfinance.bas.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.billionsfinance.bas.entity.PageVO;
import com.billionsfinance.bas.entity.HHReceivedPaymentsVO;
import com.billionsfinance.bas.server.IHHReceivedPaymentsServer;
import com.billionsfinance.bas.server.impl.HHReceivedPaymentsServer;
import com.billionsfinance.bas.util.AjaxJson;
import com.billionsfinance.bas.util.ResponseUtil;

/**
 * @ClassName: ReceivedPaymentsController
 * @Description: 哈行回款明细Controller
 * @author FMZhou
 * 
 * Copyright: Copyright (c) 2016 2016年11月14日 下午14:25:09 Company:佰仟金融
 */
@Controller
@RequestMapping("/hhrRceivedPaymentsServer")
public class HHReceivedPaymentsDetailController {

	/** 日志记录 */
	private static final Log LOGGER = LogFactory.getLog(HHReceivedPaymentsDetailController.class);

	private static final IHHReceivedPaymentsServer hhrRceivedPaymentsServer = new HHReceivedPaymentsServer();

	@RequestMapping("/toReceivedPaymentsDetail")
	public String toReceivedPaymentsDetail() {
		return "hhReceivedPaymentsDetail/receivedPaymentsDetail";
	}
	
	/**
	 * @Description:哈行回款明细查询
	 */
	@RequestMapping("/queryReceivedPaymentsDetail")
	public void queryReceivedPaymentsDetail(Integer page, Integer rows, HHReceivedPaymentsVO vo,HttpServletRequest request, HttpServletResponse response) {
		PageVO pageVO = null;
		try {
			pageVO = new PageVO();
			pageVO.setPageSize(rows);
			pageVO.setPageNo(page);
			pageVO = hhrRceivedPaymentsServer.queryReceivedPaymentsDetail(pageVO, vo);
		} catch (Exception e) {
			LOGGER.error("系统异常,哈行回款明细查询失败!",e);
		}finally {
			ResponseUtil.sendJSON(response, pageVO);
		}
	}
	
	/**
	 * @Description: 记账确认
	 * @param  HHReceivedPaymentsVO  vo
	 * @param  HttpServletRequest  request
	 * @param  HttpServletResponse response
	 * @return AjaxJson ajaxJson
	 */
	@ResponseBody
	@RequestMapping("/accountingMark")
	public AjaxJson accountingMark(HHReceivedPaymentsVO vo,HttpServletRequest request, HttpServletResponse response/*,BQRefundVO vo */) {
		AjaxJson ajaxJson = null;
		List<HHReceivedPaymentsVO> list = null;
		Double moneyCount = null;
		try {
			ajaxJson = new AjaxJson();
			moneyCount = new Double(0);
			//记账确认
			hhrRceivedPaymentsServer.accountingMark(vo);
			
			//修改合同查询
			list = hhrRceivedPaymentsServer.queryReceivedPaymentsDetailById(vo);
			ajaxJson.setObj(list);
			ajaxJson.setSuccess(true);
			if (list.size()>0) {
				for (HHReceivedPaymentsVO paymentsVO : list) {
					if (!("").equals(paymentsVO.getPayAmt())&&paymentsVO.getPayAmt()!=null) {
						moneyCount+=paymentsVO.getPayAmt();
					}
				}
				list.get(0).setMoneyCount(moneyCount);
				list.get(0).setContractCount(list.size());
				ajaxJson.setMsg("记账确认标记成功!");
			}else{
				ajaxJson.setMsg("暂无匹配合同可修改!");
			}

		} catch (Exception e) {
			ajaxJson.setMsg("系统异常,记账确认标记失败!");
			ajaxJson.setObj(list);
			ajaxJson.setSuccess(false);
			LOGGER.error("记账确认异常", e);
		}
		return ajaxJson;
	}
	
}
