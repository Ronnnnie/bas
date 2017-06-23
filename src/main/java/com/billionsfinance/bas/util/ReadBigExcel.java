package com.billionsfinance.bas.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.openxml4j.opc.OPCPackage;
//import org.apache.poi.util.SAXHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class ReadBigExcel {

	private SheetContentsHandler handler;
	private InputStream input;
	
	private List<List<String>> list = new ArrayList<>();
	
	public List<List<String>> getList() {
		return list;
	}

	public void setList(List<List<String>>  list) {
		this.list = list;
	}

	public ReadBigExcel(InputStream input){
		this.input = input;
	}
	
	public ReadBigExcel() {
	}

	public ReadBigExcel setHandler(SheetContentsHandler handler) {
		this.handler = handler;
		return this;
	}

	public void parse(){
		OPCPackage pkg = null;
		InputStream sheetInputStream = null;
		
		try {
			pkg = OPCPackage.open(input);
//			pkg = OPCPackage.open(filename, PackageAccess.READ);
			XSSFReader xssfReader = new XSSFReader(pkg);
			
			StylesTable styles = xssfReader.getStylesTable(); 
			ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(pkg);
			sheetInputStream = xssfReader.getSheetsData().next();
			
			processSheet(styles, strings, sheetInputStream);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}finally {
			if(sheetInputStream != null){
				try {
					sheetInputStream.close();
				} catch (IOException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}
			if(pkg != null){
				try {
					pkg.close();
				} catch (IOException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}
		}
	}
	
	private void processSheet(StylesTable styles, ReadOnlySharedStringsTable strings, InputStream sheetInputStream) throws SAXException, ParserConfigurationException, IOException{
		 XMLReader sheetParser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
		
		 SimpleSheetContentsHandler simple = new SimpleSheetContentsHandler();
		 
		 if(handler != null){
			sheetParser.setContentHandler(new XSSFSheetXMLHandler(styles, strings, handler, false));
		}else{
			sheetParser.setContentHandler(new XSSFSheetXMLHandler(styles, strings, simple, false));
		}
		sheetParser.parse(new InputSource(sheetInputStream));
	}
	
	
	
//	public static void main(String[] args) throws FileNotFoundException {
////		Demo demo = new Demo("D:\\佰仟文档\\123.xlsx");
//		ReadBigExcel demo = new ReadBigExcel(new FileInputStream("E:\\TEST\\salarycount\\工资数据-薪资总表.xlsx"));
//		SimpleSheetContentsHandler hanlder = new SimpleSheetContentsHandler();
//		demo.setHandler(hanlder);
//		demo.parse();
//		List<List<String>> all = new ArrayList<>();
//		List<String> templist = new ArrayList<>();
////		System.out.println(hanlder.getData());
//		String[] tempStrs = null;
//		for(String str : hanlder.getData()){
//			templist = new ArrayList<>();
//			tempStrs = str.split(",");
//			for(String s : tempStrs){
//				templist.add(s);
//			}
//			all.add(templist);
//		}
//		System.out.println(all);
//		
//	}
}