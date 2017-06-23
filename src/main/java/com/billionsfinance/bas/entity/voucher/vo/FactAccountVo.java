package com.billionsfinance.bas.entity.voucher.vo;

import java.util.List;

/**
 * @ClassName: FactAccountVo.java
 * @Description: shihuanbiao
 * @author lin.tang
 * @date 2017年6月2日 上午9:30:29 Copyright: Copyright (c) 2017-2050 Company:BQJR
 */
public class FactAccountVo {
	private String businessmodel;
	private String canceltype;
	private String assetbelong;

	private String guaranteeparty;
	private String sponsor;
	private String sponsortype;
	private String isinstalmentsend;
	private String baddebtdate;
	
	private int count;
	private List<String> citys;

	
	public List<String> getCitys() {
		return citys;
	}

	public void setCitys(List<String> citys) {
		this.citys = citys;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getBusinessmodel() {
		return businessmodel;
	}

	public void setBusinessmodel(String businessmodel) {
		this.businessmodel = businessmodel;
	}

	public String getCanceltype() {
		return canceltype;
	}

	public void setCanceltype(String canceltype) {
		this.canceltype = canceltype;
	}

	public String getAssetbelong() {
		return assetbelong;
	}

	public void setAssetbelong(String assetbelong) {
		this.assetbelong = assetbelong;
	}

	public String getGuaranteeparty() {
		return guaranteeparty;
	}

	public void setGuaranteeparty(String guaranteeparty) {
		this.guaranteeparty = guaranteeparty;
	}

	public String getSponsor() {
		return sponsor;
	}

	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	public String getSponsortype() {
		return sponsortype;
	}

	public void setSponsortype(String sponsortype) {
		this.sponsortype = sponsortype;
	}

	public String getIsinstalmentsend() {
		return isinstalmentsend;
	}

	public void setIsinstalmentsend(String isinstalmentsend) {
		this.isinstalmentsend = isinstalmentsend;
	}

	public String getBaddebtdate() {
		return baddebtdate;
	}

	public void setBaddebtdate(String baddebtdate) {
		this.baddebtdate = baddebtdate;
	}

}
