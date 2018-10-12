package com.tbims.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tbims.rpc.service.impl.DataSyncServiceImpl;

/**
 * Title: 文件路径 <br/>
 * Description:
 * 
 * @ClassName: FilePathUtil
 * @author ydc
 * @date 2016-1-7 下午5:18:05
 * 
 */
public class FileUtil {
	private static final Log logger = LogFactory.getLog(DataSyncServiceImpl.class);

	public static String projectRealPath;

	/**
	 * Title: 获取配值文件的路径<br/>
	 * Description:
	 * 
	 * @param fileName
	 * @return
	 */
	public static InputStream getConfigFile(String fileName) {
		ClassLoader cl = FileUtil.class.getClassLoader();
		if (cl == null) {
			return ClassLoader.getSystemResourceAsStream(fileName);
		}
		return cl.getResourceAsStream(fileName);
	}

	/**
	 * Title:创建多级目录,存在则跳过 (带文件名: D:\aa\bb\2015\text.tx)<br/>
	 * Description:
	 * 
	 * @param path
	 * @throws InterruptedException
	 */
	public static void createPathByFileName(String path) throws InterruptedException {
		logger.debug("路径：" + path);
		String[] paths = path.split("/");
		StringBuffer fullPath = new StringBuffer();
		for (int i = 0; i < paths.length; i++) {
			fullPath.append(paths[i]).append("/");
			File file = new File(fullPath.toString());
			if (paths.length - 1 != i) {// 判断path到文件名时，无须继续创建文件夹！
				if (!file.exists()) {
					file.mkdir();
					logger.debug("创建目录：" + fullPath.toString());
					Thread.sleep(1500);
				}
			}
		}
	}

	/**
	 * Title:创建多级目录,存在则跳过 (不带文件名: D:\aa\bb\2015)<br/>
	 * Description:
	 * 
	 * @param path
	 * @throws InterruptedException
	 */
	public static void createPath(String path) throws InterruptedException {
		String[] paths = path.split("/");
		StringBuffer fullPath = new StringBuffer();
		for (int i = 0; i < paths.length; i++) {
			fullPath.append(paths[i]).append("/");
			File file = new File(fullPath.toString());
			if (!file.exists()) {
				file.mkdir();
				logger.debug("创建目录：" + fullPath.toString());
				Thread.sleep(1500);
			}
		}
	}

	/**
	 * Title: 获取web class的绝对路径<br/>
	 * Description:
	 * 
	 * @return
	 */
	public static String getClassPath() {
		return projectRealPath + "/WEB-INF/classes";
	}

	/**
	 * Title: 获取web的绝对路径<br/>
	 * Description:
	 * 
	 * @return
	 */
	public static String getProjectPath() {
		return projectRealPath;
	}

	/**
	 * Title: 文件拷贝<br/>
	 * Description:
	 * 
	 * @param f1 源文件
	 * @param f2 目标文件
	 * @throws IOException
	 */
	public static void copyFile(File f1, File f2) throws IOException {
		FileInputStream in = new FileInputStream(f1);
		FileOutputStream out = new FileOutputStream(f2);
		// 设置缓存
		byte[] buffer = new byte[1024];
		int length = 0;
		// 读取f1文件输出到f2文件中
		while ((length = in.read(buffer)) > 0) {
			out.write(buffer, 0, length);
		}
		in.close();
		out.close();
	}

	/**
	 * Title: 文件拷贝<br/>
	 * Description:
	 * 
	 * @param f1 源文件
	 * @param f2 目标文件
	 * @throws IOException
	 */
	public static void copyFile(String f1, String f2) throws IOException {
		copyFile(new File(f1), new File(f2));
	}

	/**
	 * Title: 清空目录下的所有文件<br/>
	 * Description:
	 * 
	 * @param dir
	 */
	public static void clsDir(String dir) {
		try {
			File dirFile = new File(dir);
			File[] lst = dirFile.listFiles();
			if (lst == null) {
				return;
			}
			for (File f : lst) {
				f.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Title: 判断文件是否存在<br/>
	 * Description:
	 * 
	 * @param dir
	 * @return true:存在 false:不存在
	 */
	public static boolean isExistsFile(String fileStr) {
		File file = new File(fileStr);
		return file.isFile();
	}

	/**
	 * Title:输入流转换输出流 <br/>
	 * Description:
	 * 
	 * @param input
	 * @param output
	 * @return
	 * @throws IOException
	 */
	public static int copy(final InputStream input, final OutputStream output) throws IOException {
		final byte[] buffer = new byte[4096];
		int n = 0;
		n = input.read(buffer);
		int total = 0;
		while (-1 != n) {
			output.write(buffer, 0, n);
			total += n;
			n = input.read(buffer);
		}
		return total;
	}
}
