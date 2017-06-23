package com.billionsfinance.bas.entity;

import java.io.Serializable;

/**
 * 
 * @author fmZhoua
 * @describe 回款表VO
 */

public class DayTradingVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String startTransDate;
	
	private String endTransDate;
	
	private String transDate;//交易日期
	
	private String transType;//交易类型 zr转让dc代偿sh赎回lp理赔hk还款fk放款hb划拨
	
	private String transCode;//交易流水
	
	private String belong;//交易主体
	
	private String dataSource;//数据源
	
	private String principalamt;//本金收入，还款主体为佰仟金融专用
	
	private String inteamt;//利息收入，还款主体为佰仟金融专用

	private String payPrincipalamt;//应付本金

	private String receivePrincipalamt;//应收本金

	private String payInteamt;//应付利息

	private String receiveInteamt;//应收利息

	private String a2;//客户服务费

	private String a7;//账户管理费

	private String a9;//提前还款手续费

	private String a10;//滞纳金

	private String a11;//印花税

	private String a12;//增值服务费

	private String a17;//委外催收费

	private String a18;//随心还服务费

	private String a19;//提前委外费

	private String a22;//佰保袋服务费

	private String pureoverflowsum;//应收纯溢价，转让赎回专用

	private String isCheck;//是否核对

	private String payPureoverflowsum;//应付纯溢价，转让赎回专用

	private String createTime;//创建时间

	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getTransCode() {
		return transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	public String getBelong() {
		return belong;
	}

	public void setBelong(String belong) {
		this.belong = belong;
	}

	public String getPrincipalamt() {
		return principalamt;
	}

	public void setPrincipalamt(String principalamt) {
		this.principalamt = principalamt;
	}

	public String getInteamt() {
		return inteamt;
	}

	public void setInteamt(String inteamt) {
		this.inteamt = inteamt;
	}

	public String getPayPrincipalamt() {
		return payPrincipalamt;
	}

	public void setPayPrincipalamt(String payPrincipalamt) {
		this.payPrincipalamt = payPrincipalamt;
	}

	public String getReceivePrincipalamt() {
		return receivePrincipalamt;
	}

	public void setReceivePrincipalamt(String receivePrincipalamt) {
		this.receivePrincipalamt = receivePrincipalamt;
	}

	public String getPayInteamt() {
		return payInteamt;
	}

	public void setPayInteamt(String payInteamt) {
		this.payInteamt = payInteamt;
	}

	public String getReceiveInteamt() {
		return receiveInteamt;
	}

	public void setReceiveInteamt(String receiveInteamt) {
		this.receiveInteamt = receiveInteamt;
	}

	public String getA2() {
		return a2;
	}

	public void setA2(String a2) {
		this.a2 = a2;
	}

	public String getA7() {
		return a7;
	}

	public void setA7(String a7) {
		this.a7 = a7;
	}

	public String getA9() {
		return a9;
	}

	public void setA9(String a9) {
		this.a9 = a9;
	}

	public String getA10() {
		return a10;
	}

	public void setA10(String a10) {
		this.a10 = a10;
	}

	public String getA11() {
		return a11;
	}

	public void setA11(String a11) {
		this.a11 = a11;
	}

	public String getA12() {
		return a12;
	}

	public void setA12(String a12) {
		this.a12 = a12;
	}

	public String getA17() {
		return a17;
	}

	public void setA17(String a17) {
		this.a17 = a17;
	}

	public String getA18() {
		return a18;
	}

	public void setA18(String a18) {
		this.a18 = a18;
	}

	public String getA19() {
		return a19;
	}

	public void setA19(String a19) {
		this.a19 = a19;
	}

	public String getA22() {
		return a22;
	}

	public void setA22(String a22) {
		this.a22 = a22;
	}

	public String getPureoverflowsum() {
		return pureoverflowsum;
	}

	public void setPureoverflowsum(String pureoverflowsum) {
		this.pureoverflowsum = pureoverflowsum;
	}

	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public String getPayPureoverflowsum() {
		return payPureoverflowsum;
	}

	public void setPayPureoverflowsum(String payPureoverflowsum) {
		this.payPureoverflowsum = payPureoverflowsum;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "DayTradingVO [startTransDate=" + startTransDate + ", endTransDate=" + endTransDate + ", transDate="
				+ transDate + ", transType=" + transType + ", transCode=" + transCode + ", belong=" + belong
				+ ", dataSource=" + dataSource + ", principalamt=" + principalamt + ", inteamt=" + inteamt
				+ ", payPrincipalamt=" + payPrincipalamt + ", receivePrincipalamt=" + receivePrincipalamt
				+ ", payInteamt=" + payInteamt + ", receiveInteamt=" + receiveInteamt + ", a2=" + a2 + ", a7=" + a7
				+ ", a9=" + a9 + ", a10=" + a10 + ", a11=" + a11 + ", a12=" + a12 + ", a17=" + a17 + ", a18=" + a18
				+ ", a19=" + a19 + ", a22=" + a22 + ", pureoverflowsum=" + pureoverflowsum + ", isCheck=" + isCheck
				+ ", payPureoverflowsum=" + payPureoverflowsum + ", createTime=" + createTime + "]";
	}

	public String getStartTransDate() {
		return startTransDate;
	}
	
	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public void setStartTransDate(String startTransDate) {
		this.startTransDate = startTransDate;
	}

	public String getEndTransDate() {
		return endTransDate;
	}

	public void setEndTransDate(String endTransDate) {
		this.endTransDate = endTransDate;
	}
	
}
