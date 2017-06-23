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
import com.billionsfinance.bas.entity.TrustAllotDetailVO;
import com.billionsfinance.bas.server.ITrustAllotDetailServer;
import com.billionsfinance.bas.server.impl.TrustAllotDetailServer;
import com.billionsfinance.bas.util.AjaxJson;
import com.billionsfinance.bas.util.ResponseUtil;

/**
 * @ClassName: 信托划拨Controller
 * @Description: TrustAllotDetailController
 * @author zhouFM
 * @Copyright Copyright (c) 2016 2016年11月14日 上午10:37:04 Company:佰仟金融
 */
@Controller
@RequestMapping("/trustAllotDetailServer")
public class TrustAllotDetailController {

	/** 日志记录 */
	private static final Log LOGGER = LogFactory.getLog(TrustAllotDetailController.class);

	private static final ITrustAllotDetailServer trustAllotDetailServer = new TrustAllotDetailServer();

	/**
	 * @methodName: toReceivedPaymentsDetail
	 * @Description: trustAllotDetail
	 * @return java.lang.String
	 */
	@RequestMapping("/toTrustAllotDetail")
	public String toTrustAllotDetail() {
		return "trustAllotDetail/trustAllotDetail";	
	}

	/**
	 * 放款表业务明细查询
	 * @param TrustAllotDetailVO
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 */
	@RequestMapping("/queryTrustAllotDetail")
	public void queryTrustAllotDetail(Integer page, Integer rows, TrustAllotDetailVO vo,HttpServletRequest request, HttpServletResponse response) {
		System.out.println(vo);
		PageVO pageVO = null;
		try {
			pageVO = new PageVO();
			pageVO.setPageSize(rows);
			pageVO.setPageNo(page);
			pageVO = trustAllotDetailServer.queryBusinessDetail(pageVO, vo);
		} catch (Exception e) {
			LOGGER.error("查询信托划拨明细失败!", e);
		}finally {
			ResponseUtil.sendJSON(response, pageVO);
		}
	}
	
	/**
	 * @methodName 记账确认
	 * @param TrustAllotDetailVO
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 */
	@ResponseBody
	@RequestMapping("/accountingMark")
	public AjaxJson accountingMark(TrustAllotDetailVO vo,HttpServletRequest request, HttpServletResponse response/*,BQRefundVO vo */) {
		AjaxJson ajaxJson = null;
		List<TrustAllotDetailVO> list = null;
		Double moneyCount = null;
		try {
			ajaxJson = new AjaxJson();
			moneyCount = new Double(0);
			//记账确认
			trustAllotDetailServer.accountingMark(vo);
			//修改合同查询
			list = trustAllotDetailServer.queryTrustAllotDetailById(vo);
			ajaxJson.setObj(list);
			ajaxJson.setSuccess(true);
			if (list.size()>0) {
				for (TrustAllotDetailVO trustAllotDetailVO : list) {
					moneyCount+=trustAllotDetailVO.getPayAmt();
				}
				list.get(0).setMoneyCount(moneyCount);
				list.get(0).setContractCount(list.size());
				ajaxJson.setMsg("记账确认标记成功!");
			}else{
				ajaxJson.setMsg("暂无匹配数据可修改!");
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
