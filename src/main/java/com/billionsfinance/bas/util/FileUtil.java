package com.billionsfinance.bas.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.billionsfinance.common.Exception.BizException;
import com.billionsfinance.common.framework.GlobalParam;

/**
 * @ClassName: FileUtil
 * @Description: 文件操作工具
 * @author zhanghs 2016年7月19日 下午4:17:03 Copyright: Copyright (c) 2016
 *         Company:佰仟金融
 */
public class FileUtil {

	private static final int BUFFER_SIZE = 16 * 1024; // 读文件时的缓冲区

	/** 图片保存的目录 */
	public static final String SAVEPATH = "uploadFile";

	public static final String IMAGES_PAHT = "als.images.paht";

	/** 图片获取地址 */
	public static final String IMAGES_URL = "als.images.url";

	/** 默认佰仟金融的logo */
	public static final String BQJRLOGO = "images" + File.separator + "bqjrlogo.jpg";
	/**
	  * <p>Title: 文件上传工具</p>
	  * <p>Description: 文件上传工具</p>
	  * @param sourceFile 
	  * @param sysCode 
	  * @param request 
	  * @return string
	  */
	public static String uploadFile(CommonsMultipartFile sourceFile, String sysCode, HttpServletRequest request) {

		// 图片名称修改为系统名称
		String dstImageFileName = sysCode + getExtention(sourceFile.getOriginalFilename());
		// 形成：在服务器端，带存储路径的目的文件名。根据服务器的文件保存地址和原文件名，创建目的文件全路径
		String path = GlobalParam.getParam(IMAGES_PAHT) + File.separator + SAVEPATH;
		if (!path.endsWith(File.separator)) {
			path = path + File.separator;
		}
		File dir = new File(path);
		if (!dir.exists()) {
			// 创建目录
			boolean mkdir = dir.mkdir();
			if (!mkdir) {
				throw new BizException("创建目录失败");
			}
		}
		File dstImageFile = new File(path, dstImageFileName);
		try {
			copy(sourceFile.getInputStream(), dstImageFile);
		} catch (IOException e) {
			LogUtil.ERROR.error("logo上传失败！", e);
		} // 拷贝临时文件到目标文件
			// 目的文件的urls返回给jsp。主机地址从配置文件中读取
		String urls = SAVEPATH + "/" + dstImageFileName;

		return urls;
	}

	/**
	  * <p>Title: copy</p>
	  * <p>Description: copy</p>
	  * @param inputStream 
	  * @param dst  
	  */
	private static void copy(InputStream inputStream, File dst) {
		try {
			InputStream in = null;
			OutputStream out = null;
			try {

				in = new BufferedInputStream(inputStream, BUFFER_SIZE);
				out = new BufferedOutputStream(new FileOutputStream(dst), BUFFER_SIZE);
				byte[] buffer = new byte[BUFFER_SIZE];
				while (in.read(buffer) > 0) {
					out.write(buffer);
				}
				LogUtil.APP.info("logo上传成功！");
			} finally {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			}
		} catch (Exception e) {
			LogUtil.ERROR.error("logo上传失败！", e);
		}
	}

	/**
	  * <p>Title: 获取扩展名</p>
	  * <p>Description: 获取扩展名</p>
	  * @param fileName 
	  * @return string
	  */
	private static String getExtention(String fileName) {
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos);
	}

	/**
	  * <p>Title: 文件上传工具</p>
	  * <p>Description: 文件上传工具</p>
	  * @param sysCode 
	  * @param request 
	  * @return string
	  */
	public static String uploadLogo(String sysCode, HttpServletRequest request) {

		// 图片名称修改为系统名称
		String urls = null;
		try {
			String realPath = request.getSession().getServletContext().getRealPath(BQJRLOGO);

			InputStream inputStream = new FileInputStream(realPath);
			String dstImageFileName = sysCode + getExtention(BQJRLOGO);
			// 形成：在服务器端，带存储路径的目的文件名。根据服务器的文件保存地址和原文件名，创建目的文件全路径
			String path = GlobalParam.getParam(IMAGES_PAHT) + File.separator + SAVEPATH;
			if (!path.endsWith(File.separator)) {
				path = path + File.separator;
			}
			File dir = new File(path);
			if (!dir.exists()) {
				// 创建目录
				dir.mkdir();
			}
			File dstImageFile = new File(path, dstImageFileName);
			copy(inputStream, dstImageFile);
			// 拷贝临时文件到目标文件
			// 目的文件的urls返回给jsp。主机地址从配置文件中读取
			urls = SAVEPATH + "/" + dstImageFileName;

		} catch (Exception e) {
			LogUtil.ERROR.error("默认logo上传失败！", e);
		}

		return urls;
	}
}
