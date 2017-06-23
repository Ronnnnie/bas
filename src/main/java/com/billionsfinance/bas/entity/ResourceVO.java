package com.billionsfinance.bas.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class ResourceVO implements Serializable{

	  /**
	 * 
	 */
	private static final long serialVersionUID = 6013725722645162781L;
	
	/**资源编码*/
	private String resourceId;
	/**资源名称*/
	private String resourceName;
	/**修改时间*/
	private Timestamp updatedDate;
	/**资源路径URL*/ 
	private String url;
	/**父资源编码*/
	private String parentId;
	/**资源类型 1 表示菜单2表示按钮*/
	private Integer resourceType;
	/**资源CSS*/
	private String resourceCss;
	/**资源图标*/
	private String resourceIcon;
	/**修改人*/
	private String updatedUser;
	/**菜单序号*/
	private Integer menuIndex;
	/**有效标志位:1-有效,0-失效*/
	private String validInd;
	/**创建时间*/
	private Timestamp createdDate;
	/**创建人*/
	private String createdUser;
	/**系统编码*/
	private String sysCode;
	//菜单级别
	private Integer menuLevel;
	
	/**父资源名称*/
	private String parentName;
	/**菜单编码*/
	private String resourceCode;
	/**菜单是否对所有人显示，1显示，0不显示*/
	private String showAllUser;
	
	public String getUpdatedUser() {
		return updatedUser;
	}
	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
	}
	public Integer getMenuIndex() {
		return menuIndex;
	}
	public void setMenuIndex(Integer menuIndex) {
		this.menuIndex = menuIndex;
	}
	public String getValidInd() {
		return validInd;
	}
	public void setValidInd(String validInd) {
		this.validInd = validInd;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedUser() {
		return createdUser;
	}
	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}
	public String getSysCode() {
		return sysCode;
	}
	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public Integer getResourceType() {
		return resourceType;
	}
	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}
	public String getResourceCss() {
		return resourceCss;
	}
	public void setResourceCss(String resourceCss) {
		this.resourceCss = resourceCss;
	}
	public String getResourceIcon() {
		return resourceIcon;
	}
	public void setResourceIcon(String resourceIcon) {
		this.resourceIcon = resourceIcon;
	}
	public Integer getMenuLevel() {
		return menuLevel;
	}
	public void setMenuLevel(Integer menuLevel) {
		this.menuLevel = menuLevel;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getResourceCode() {
		return resourceCode;
	}
	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}
	public String getShowAllUser() {
		return showAllUser;
	}
	public void setShowAllUser(String showAllUser) {
		this.showAllUser = showAllUser;
	}
}
