package com.billionsfinance.bas.entity;

import java.io.Serializable;
import java.util.Arrays;
/****
 * 
 * @author FMZhou
 * @describe 系统进账与网银进账核对表VO
 *
 */
public class CheckListVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String[] idArray;
	//日期
	private String importDate;
	
	private String startImportDate;
	
	private String endImportDate;
	
	public String getStartImportDate() {
		return startImportDate;
	}

	public void setStartImportDate(String startImportDate) {
		this.startImportDate = startImportDate;
	}

	public String getEndImportDate() {
		return endImportDate;
	}

	public void setEndImportDate(String endImportDate) {
		this.endImportDate = endImportDate;
	}

	//日出预存款
	private double sunrisePreDeposit;
	
	//日出未匹配
	private String sunriseUnmatch;
	
	//银企直连网银总进账
	private double yqzlwyTotalRevenue;
	
	//银企直连安硕总进账
	private double yqzlasTotalRevenue;
	
	//银企直连总进账差异
	private double yqzlTotalRevenueDifference;
	
	//银企直连当日已匹配
	private String yqzldrAlreadymatch;
	
	//银企直连当日未匹配
	private String yqzkdrUnmatch;
	
	//手工分离
	private String handworkSeparate;
	
	//手工匹配
	private String handworkMatch;
	
	//日终未匹配
	private String rzUnmatch;
	
	//ebu网银总进账
	private double ebuWyTotalRevenue;
	
	//ebu安硕总进账向后移一天
	private String ebuAsTotalRevenues;
	
	//ebu安硕总进账
	private double ebuAsTotalRevenue;
	
	//ebu总进账差异
	private double ebuTotalRevenueDifference;
	
	//kft网银总入账
	private double kftWyTotalRevenue;
	
	//kft安硕总入账
	private double kftAsTotalRevenue;
	
	//kft总入账差异
	private double kftTotalRevenueDifference;
	
	//哈行网银总入账
	private double hhWyTotalRevenue;
	
	//哈行安硕总入账
	private double hhAsTotalRevenue;
	
	//哈行总入账差异
	private double hhTotalRevenueDifference;
	
	//kft实时网银总入账
	private double kftSswyTotalRevenue;
	
	//kft实时安硕总入账
	private double kftSsasTotalRevenue;
	
	//kft实时总入账差异
	private double kftSsTotalRevenueCy;
	
	//退款
	private double refund;
	
	//普通还款
	private double ordinaryRepayment;
	
	//提前还款
	private double earlierRepayment;
	
	//虚拟入账
	private double virtualAccount;
	
	//日终预存款
	private double rzPreDeposit	;
	
	//未匹配差额
	private double unmatchDifference;
	
	//预存款差额
	private double preDepositDifference;
	
	public String getEbuAsTotalRevenues() {
		return ebuAsTotalRevenues;
	}

	public void setEbuAsTotalRevenues(String ebuAsTotalRevenues) {
		this.ebuAsTotalRevenues = ebuAsTotalRevenues;
	}

	public double getEbuWyTotalRevenue() {
		return ebuWyTotalRevenue;
	}

	public void setEbuWyTotalRevenue(double ebuWyTotalRevenue) {
		this.ebuWyTotalRevenue = ebuWyTotalRevenue;
	}

	public double getKftWyTotalRevenue() {
		return kftWyTotalRevenue;
	}

	public void setKftWyTotalRevenue(double kftWyTotalRevenue) {
		this.kftWyTotalRevenue = kftWyTotalRevenue;
	}

	public double getHhWyTotalRevenue() {
		return hhWyTotalRevenue;
	}

	public void setHhWyTotalRevenue(double hhWyTotalRevenue) {
		this.hhWyTotalRevenue = hhWyTotalRevenue;
	}

	public String getImportDate() {
		return importDate;
	}

	public void setImportDate(String importDate) {
		this.importDate = importDate;
	}

	public double getSunrisePreDeposit() {
		return sunrisePreDeposit;
	}

	public void setSunrisePreDeposit(double sunrisePreDeposit) {
		this.sunrisePreDeposit = sunrisePreDeposit;
	}

	public String getSunriseUnmatch() {
		return sunriseUnmatch;
	}

	public void setSunriseUnmatch(String sunriseUnmatch) {
		this.sunriseUnmatch = sunriseUnmatch;
	}

	public double getYqzlwyTotalRevenue() {
		return yqzlwyTotalRevenue;
	}

	public void setYqzlwyTotalRevenue(double yqzlwyTotalRevenue) {
		this.yqzlwyTotalRevenue = yqzlwyTotalRevenue;
	}

	public double getYqzlasTotalRevenue() {
		return yqzlasTotalRevenue;
	}

	public void setYqzlasTotalRevenue(double yqzlasTotalRevenue) {
		this.yqzlasTotalRevenue = yqzlasTotalRevenue;
	}

	public double getYqzlTotalRevenueDifference() {
		return yqzlTotalRevenueDifference;
	}

	public void setYqzlTotalRevenueDifference(double yqzlTotalRevenueDifference) {
		this.yqzlTotalRevenueDifference = yqzlTotalRevenueDifference;
	}

	public String getYqzldrAlreadymatch() {
		return yqzldrAlreadymatch;
	}

	public void setYqzldrAlreadymatch(String yqzldrAlreadymatch) {
		this.yqzldrAlreadymatch = yqzldrAlreadymatch;
	}

	public String getYqzkdrUnmatch() {
		return yqzkdrUnmatch;
	}

	public void setYqzkdrUnmatch(String yqzkdrUnmatch) {
		this.yqzkdrUnmatch = yqzkdrUnmatch;
	}

	public String getHandworkSeparate() {
		return handworkSeparate;
	}

	public void setHandworkSeparate(String handworkSeparate) {
		this.handworkSeparate = handworkSeparate;
	}

	public String getHandworkMatch() {
		return handworkMatch;
	}

	public void setHandworkMatch(String handworkMatch) {
		this.handworkMatch = handworkMatch;
	}

	public String getRzUnmatch() {
		return rzUnmatch;
	}

	public void setRzUnmatch(String rzUnmatch) {
		this.rzUnmatch = rzUnmatch;
	}

	public double getEbuAsTotalRevenue() {
		return ebuAsTotalRevenue;
	}

	public void setEbuAsTotalRevenue(double ebuAsTotalRevenue) {
		this.ebuAsTotalRevenue = ebuAsTotalRevenue;
	}

	public double getEbuTotalRevenueDifference() {
		return ebuTotalRevenueDifference;
	}

	public void setEbuTotalRevenueDifference(double ebuTotalRevenueDifference) {
		this.ebuTotalRevenueDifference = ebuTotalRevenueDifference;
	}

	public double getKftAsTotalRevenue() {
		return kftAsTotalRevenue;
	}

	public void setKftAsTotalRevenue(double kftAsTotalRevenue) {
		this.kftAsTotalRevenue = kftAsTotalRevenue;
	}

	public double getKftTotalRevenueDifference() {
		return kftTotalRevenueDifference;
	}

	public void setKftTotalRevenueDifference(double kftTotalRevenueDifference) {
		this.kftTotalRevenueDifference = kftTotalRevenueDifference;
	}

	public double getHhAsTotalRevenue() {
		return hhAsTotalRevenue;
	}

	public void setHhAsTotalRevenue(double hhAsTotalRevenue) {
		this.hhAsTotalRevenue = hhAsTotalRevenue;
	}

	public double getHhTotalRevenueDifference() {
		return hhTotalRevenueDifference;
	}

	public void setHhTotalRevenueDifference(double hhTotalRevenueDifference) {
		this.hhTotalRevenueDifference = hhTotalRevenueDifference;
	}

	public double getKftSswyTotalRevenue() {
		return kftSswyTotalRevenue;
	}

	public void setKftSswyTotalRevenue(double kftSswyTotalRevenue) {
		this.kftSswyTotalRevenue = kftSswyTotalRevenue;
	}

	public double getKftSsasTotalRevenue() {
		return kftSsasTotalRevenue;
	}

	public void setKftSsasTotalRevenue(double kftSsasTotalRevenue) {
		this.kftSsasTotalRevenue = kftSsasTotalRevenue;
	}

	public double getKftSsTotalRevenueCy() {
		return kftSsTotalRevenueCy;
	}

	public void setKftSsTotalRevenueCy(double kftSsTotalRevenueCy) {
		this.kftSsTotalRevenueCy = kftSsTotalRevenueCy;
	}

	public double getRefund() {
		return refund;
	}

	public void setRefund(double refund) {
		this.refund = refund;
	}

	public double getOrdinaryRepayment() {
		return ordinaryRepayment;
	}

	public void setOrdinaryRepayment(double ordinaryRepayment) {
		this.ordinaryRepayment = ordinaryRepayment;
	}

	public double getEarlierRepayment() {
		return earlierRepayment;
	}

	public void setEarlierRepayment(double earlierRepayment) {
		this.earlierRepayment = earlierRepayment;
	}

	public double getVirtualAccount() {
		return virtualAccount;
	}

	public void setVirtualAccount(double virtualAccount) {
		this.virtualAccount = virtualAccount;
	}

	public double getRzPreDeposit() {
		return rzPreDeposit;
	}

	public void setRzPreDeposit(double rzPreDeposit) {
		this.rzPreDeposit = rzPreDeposit;
	}

	public double getUnmatchDifference() {
		return unmatchDifference;
	}

	public void setUnmatchDifference(double unmatchDifference) {
		this.unmatchDifference = unmatchDifference;
	}

	public double getPreDepositDifference() {
		return preDepositDifference;
	}

	public void setPreDepositDifference(double preDepositDifference) {
		this.preDepositDifference = preDepositDifference;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String[] getIdArray() {
		return idArray;
	}

	public void setIdArray(String[] idArray) {
		this.idArray = idArray;
	}

	@Override
	public String toString() {
		return "CheckListVO [id=" + id + ", idArray=" + Arrays.toString(idArray) + ", importDate=" + importDate
				+ ", startImportDate=" + startImportDate + ", endImportDate=" + endImportDate + ", sunrisePreDeposit="
				+ sunrisePreDeposit + ", sunriseUnmatch=" + sunriseUnmatch + ", yqzlwyTotalRevenue="
				+ yqzlwyTotalRevenue + ", yqzlasTotalRevenue=" + yqzlasTotalRevenue + ", yqzlTotalRevenueDifference="
				+ yqzlTotalRevenueDifference + ", yqzldrAlreadymatch=" + yqzldrAlreadymatch + ", yqzkdrUnmatch="
				+ yqzkdrUnmatch + ", handworkSeparate=" + handworkSeparate + ", handworkMatch=" + handworkMatch
				+ ", rzUnmatch=" + rzUnmatch + ", ebuWyTotalRevenue=" + ebuWyTotalRevenue + ", ebuAsTotalRevenues="
				+ ebuAsTotalRevenues + ", ebuAsTotalRevenue=" + ebuAsTotalRevenue + ", ebuTotalRevenueDifference="
				+ ebuTotalRevenueDifference + ", kftWyTotalRevenue=" + kftWyTotalRevenue + ", kftAsTotalRevenue="
				+ kftAsTotalRevenue + ", kftTotalRevenueDifference=" + kftTotalRevenueDifference + ", hhWyTotalRevenue="
				+ hhWyTotalRevenue + ", hhAsTotalRevenue=" + hhAsTotalRevenue + ", hhTotalRevenueDifference="
				+ hhTotalRevenueDifference + ", kftSswyTotalRevenue=" + kftSswyTotalRevenue + ", kftSsasTotalRevenue="
				+ kftSsasTotalRevenue + ", kftSsTotalRevenueCy=" + kftSsTotalRevenueCy + ", refund=" + refund
				+ ", ordinaryRepayment=" + ordinaryRepayment + ", earlierRepayment=" + earlierRepayment
				+ ", virtualAccount=" + virtualAccount + ", rzPreDeposit=" + rzPreDeposit + ", unmatchDifference="
				+ unmatchDifference + ", preDepositDifference=" + preDepositDifference + "]";
	}
}
