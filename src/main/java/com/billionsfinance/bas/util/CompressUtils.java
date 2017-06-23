package com.billionsfinance.bas.util;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class CompressUtils {
	
	private final static Log logger = LogFactory.getLog(CompressUtils.class);
	
	public static void createCompressFile(File sourceFile,ZipFile zipFile,String password) {
		ArrayList<File> filesToAdd = new ArrayList<File>();
		filesToAdd.add(sourceFile);
		ZipParameters parameters = new ZipParameters();  
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);  
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
		//设置密码
		parameters.setEncryptFiles(true);  
		parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);   
		parameters.setPassword(password);  
		try {
			zipFile.addFiles(filesToAdd, parameters);
		} catch (ZipException e) {
			logger.error("文件压缩异常",e);
		}
	}

}
