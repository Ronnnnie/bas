package com.billionsfinance.bas.util;

import com.billionsfinance.bas.service.AccountTotalCheckingAccountService;
import com.billionsfinance.bas.service.BASUserService;
import com.billionsfinance.bas.service.BQRefundService;
import com.billionsfinance.bas.service.BankWithholdService;
import com.billionsfinance.bas.service.BusinessLoanService;
import com.billionsfinance.bas.service.CapitalMitigateService;
import com.billionsfinance.bas.service.CheckListService;
import com.billionsfinance.bas.service.ClientRefundService;
import com.billionsfinance.bas.service.ClientRepaymentDetailService;
import com.billionsfinance.bas.service.CompensatoryDetailService;
import com.billionsfinance.bas.service.ContractService;
import com.billionsfinance.bas.service.CurrentExpiredService;
import com.billionsfinance.bas.service.CurrentPayService;
import com.billionsfinance.bas.service.DataCheckService;
import com.billionsfinance.bas.service.DayTradingService;
import com.billionsfinance.bas.service.DepartService;
import com.billionsfinance.bas.service.HHReceivedPaymentsService;
import com.billionsfinance.bas.service.JYReceivedPaymentsService;
import com.billionsfinance.bas.service.OverdueService;
import com.billionsfinance.bas.service.OverdueUnService;
import com.billionsfinance.bas.service.PredictcostService;
import com.billionsfinance.bas.service.RansomDetailService;
import com.billionsfinance.bas.service.ReceivedPaymentsService;
import com.billionsfinance.bas.service.ReleaseSlowDetailService;
import com.billionsfinance.bas.service.RepaymentDetailCheckAccountService;
import com.billionsfinance.bas.service.RepaymentDetailService;
import com.billionsfinance.bas.service.ResourceService;
import com.billionsfinance.bas.service.RoleResourcesService;
import com.billionsfinance.bas.service.SysAdminService;
import com.billionsfinance.bas.service.SysRoleService;
import com.billionsfinance.bas.service.SysUserRoleService;
import com.billionsfinance.bas.service.SystemService;
import com.billionsfinance.bas.service.SystemUserService;
import com.billionsfinance.bas.service.TrustAllotDetailService;
import com.billionsfinance.bas.service.UserSystemFacadeService;
import com.billionsfinance.bas.service.VillageReceivedPaymentsService;
import com.billionsfinance.bas.service.XZXDPaymentsService;
import com.billionsfinance.common.core.SpringContext;

/**
* @Title: SpringService.java
* @Package com.billionsfinance.demo.util
* @Description: SpringService集合  
* @author HuGuobo
* @date 2015年10月9日 下午5:00:27
* @version V1.0
 */
public class SpringService {

	public static final ResourceService RESOURCE = (ResourceService) SpringContext
			.getService("resourceService");
	
	public static final SystemService SYSTEMSERVICE = (SystemService) SpringContext
			.getService("systemService");
	
	public static final BASUserService BASUSERSERVICE = (BASUserService) SpringContext
			.getService("basUserService");
	
	public static final SystemUserService SYSTEMUSERSERVICE = (SystemUserService) SpringContext
			.getService("systemUserService");
	
	public static final SysAdminService SYSADMINSERVICE = (SysAdminService) SpringContext
			.getService("sysAdminService");
	
	public static final SysRoleService SYSROLESERVICE = (SysRoleService) SpringContext
			.getService("sysRoleService");
	
	public static final RoleResourcesService ROLERESOURCESSERVICE = (RoleResourcesService) SpringContext
			.getService("roleResourcesService");
	
	public static final SysUserRoleService SYSUSERROLESERVICE = (SysUserRoleService) SpringContext
			.getService("sysUserRoleService");
	
	public static final UserSystemFacadeService SYSUSERFACADESERVICE = (UserSystemFacadeService) SpringContext
			.getService("sysUserFacadeService");
	
	
	
	public static final DepartService DEPARTSERVICE = (DepartService) SpringContext
			.getService("departService");
	
	public static final ContractService CONTRACTSERVICE = (ContractService) SpringContext
			.getService("contractService");
	
	public static final CheckListService CHECKLISTSERVICE = (CheckListService) SpringContext
			.getService("checkListService");
	
	public static final BusinessLoanService BUSINESSLOANSERVICE = (BusinessLoanService) SpringContext
			.getService("businessLoanService");
	
	public static final ClientRefundService CLIENTREFUNDSERVICE = (ClientRefundService) SpringContext
			.getService("clientRefundService");
	
	public static final BQRefundService BQREFUNDSERVICE = (BQRefundService) SpringContext
			.getService("bqRefundService");
	
	public static final ReceivedPaymentsService RECEIVEDPAYMENTSSERVICE = (ReceivedPaymentsService) SpringContext
			.getService("receivedPaymentsService");
	
	public static final HHReceivedPaymentsService HHRECEIVEDPAYMENTSSERVICE = (HHReceivedPaymentsService) SpringContext
			.getService("hhReceivedPaymentsService");
	
	public static final TrustAllotDetailService TRUSTALLOTDETAILSERVICE = (TrustAllotDetailService) SpringContext
			.getService("trustAllotDetailService");
	
	public static final RepaymentDetailService REPAYMENTDETAILSERVICE = (RepaymentDetailService) SpringContext
			.getService("repaymentDetailService");
	
	public static final ClientRepaymentDetailService CLIENTREPAYMENTDETAILSERVICE = (ClientRepaymentDetailService) SpringContext
			.getService("clientRepaymentDetailService");
	
	public static final CompensatoryDetailService COMPENSATORYDETAILSERVICE = (CompensatoryDetailService) SpringContext
			.getService("compensatoryDetailService");
	
	public static final RansomDetailService RANSOMDETAILSERVICE = (RansomDetailService) SpringContext
			.getService("ransomDetailService");
	
	public static final OverdueService OVERDUESERVICE = (OverdueService) SpringContext
			.getService("overdueService");
	
	public static final OverdueUnService OVERDUEUNSERVICE = (OverdueUnService) SpringContext
			.getService("overdueUnService");
	
	public static final CurrentExpiredService CURRENTEXPIREDSERVICE = (CurrentExpiredService) SpringContext
			.getService("currentExpiredService");
	
	public static final CurrentPayService CURRENTPAYSERVICE = (CurrentPayService) SpringContext
			.getService("currentPayService");
	
	public static final JYReceivedPaymentsService JYRECEIVEDPAYMENTSSERVICE = (JYReceivedPaymentsService) SpringContext
			.getService("jyReceivedPaymentsService");
	
	public static final VillageReceivedPaymentsService VILLAGERECEIVEDPAYMENTS = (VillageReceivedPaymentsService) SpringContext
			.getService("villageReceivedPaymentsService");
	
	public static final CapitalMitigateService CAPITALMITIGATESERVICE = (CapitalMitigateService) SpringContext
			.getService("capitalMitigateService");
	
	public static final PredictcostService PREDICTCOSTSERVICE = (PredictcostService) SpringContext
			.getService("predictcostService");
	
	public static final ReleaseSlowDetailService RELEASESLOWDETAILSERVICE = (ReleaseSlowDetailService) SpringContext
			.getService("releaseSlowDetailService");
	
	public static final DayTradingService DAYTRADINGSERVICE = (DayTradingService) SpringContext
			.getService("dayTradingService");
	
	public static final XZXDPaymentsService XZXDPAYMENTSSERVICE = (XZXDPaymentsService) SpringContext
			.getService("xzxdPaymentsService");
	
	public static final DataCheckService DATACHECKSERVICE = (DataCheckService) SpringContext
			.getService("dataCheckService");
	
	public static final BankWithholdService BANKWITHHOLDSERVER = (BankWithholdService) SpringContext
			.getService("bankWithholdService");
	
	public static final AccountTotalCheckingAccountService ACCOUNTTOTALCHECKINGACCOUNTSERVICE = (AccountTotalCheckingAccountService) SpringContext
			.getService("accountTotalCheckingAccountService");
	
	public static final RepaymentDetailCheckAccountService REPAYMENTDETAILCHECKACCOUNTSERVICE = (RepaymentDetailCheckAccountService) SpringContext
			.getService("repaymentDetailCheckAccountService");
}
