package com.billionsfinance.bas.util;

import java.util.List;

interface SheetRowListener {
	void addRow(List<String> row, int rowNum);
}