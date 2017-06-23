package com.billionsfinance.bas.server;

import java.util.List;

import com.billionsfinance.bas.entity.voucher.vo.VoucherInfoVo;
import com.billionsfinance.bas.util.SingletonScrollBar;


/**
 * @ClassName: CwVoucherModelInfoServer.java
 * @Description: 模板信息实现接口
 * @author lin.tang
 * @date 2017年5月31日 上午9:58:01
 * Copyright: Copyright (c) 2017-2050 Company:BQJR
 */
public interface ICwVoucherModelInfoServer {
	//按条件查询模板条件组信息
	List<VoucherInfoVo> calculationVoucher(String modelNo,SingletonScrollBar singletonScrollBar)throws Exception;
}


