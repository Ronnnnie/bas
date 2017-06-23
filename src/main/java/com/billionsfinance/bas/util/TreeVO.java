package com.billionsfinance.bas.util;

import java.io.Serializable;
import java.util.List;

/**
  * @ClassName: TreeVO
  * @Description: 
  * 2016年7月19日 下午4:31:15
  * Copyright: Copyright (c) 2016 
  * Company:佰仟金融
  */
public class TreeVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5096060794900527299L;

	private String id;

	private String text;

	private List<TreeVO> children;

	private String parent;

	private String state;

	private boolean checked;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<TreeVO> getChildren() {
		return children;
	}

	public void setChildren(List<TreeVO> children) {
		this.children = children;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
