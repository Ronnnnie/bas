package com.billionsfinance.bas.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;

public class SimpleSheetContentsHandler implements SheetContentsHandler {

	public SimpleSheetContentsHandler() {
	}

	protected List<String> row = new LinkedList<>();
	private List<String> data = new ArrayList<>();

	public List<String> getData() {
		return data;
	}

	@Override
	public void startRow(int rowNum) {
		row.clear();
		buffer = new StringBuffer();
	}

	@Override
	public void headerFooter(String text, boolean isHeader, String tagName) {

	}

	int i = 0;

	@Override
	public void endRow() {
		// 添加数据
		data.add(buffer.toString().substring(0, buffer.length() - 1));
	}

	StringBuffer buffer = new StringBuffer();

	@Override
	public void cell(String cellReference, String formattedValue) {
		// row.add(formattedValue);
		String temp = "";
		if (formattedValue.contains(".")) {
			try {
				temp = String.format("%.2f", Double.parseDouble(formattedValue));
			} catch (NumberFormatException e) {
				temp = formattedValue;
			}
		} else {
			temp = formattedValue;
		}
		buffer.append(temp).append(",");
	}

}