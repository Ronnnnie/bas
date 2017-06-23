package com.billionsfinance.bas.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.billionsfinance.bas.util.ResponseUtil;

/**
 * @ClassName: MessageProgressController.java
 * @Description: 进度条控制器
 * @author Feima.zhou
 * 
 *         Copyright: Copyright (c) 2017年4月10日下午3:57:52 Company:佰仟金融
 */
@Controller
@RequestMapping("/MessageProgressServer")
public class MessageProgressController {
	private static final Log LOGGER = LogFactory.getLog(MessageProgressController.class);
	
	/**
	 * @Description 根据导出结果关闭进度条
	 * @param request
	 * @param response
	 */
	@RequestMapping("/closeMessageProgress")
    public void closeMessageProgress(HttpServletRequest request, HttpServletResponse response){
        Object exportedFlag = request.getSession().getAttribute("exportedFlag");
        if(exportedFlag == "true"){
        	LOGGER.info("导完完毕...");
			request.getSession().removeAttribute("exportedFlag");
            ResponseUtil.sendString(response, "true");
        }else{
        	LOGGER.info("正在导出...");
        	ResponseUtil.sendString(response, "false");
        }
    }
}
