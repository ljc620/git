package com.zhming.support.doExcel;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * 导出Excel
 * 
 * @author wgb
 */
public class ExportExcelUtil {
	/*private static final int BUFFER_SIZE = 2 * 1024;
	private static ResourcerUtil resourcer = new ResourcerUtil("config");
	private static final String HOME_PATH = resourcer.get("HOME_PATH");//图片临时保存路径
	private static final String TEMPLATE_PATH = resourcer.get("TEMPLATE_PATH");//图片临时保存路径
	 */	
	/**
	 * 导出Excel
	 * @param fileName 该项目包下对应的文件名称
	 * @param params excel模板对应的JEXL表达式
	 * @param os excel输出流
	 * @throws Exception
	 */
	public static void convertExcelByTemplate(String fileName, Map<String, Object> params, OutputStream os,List<Integer[]> merge) throws Exception {
		
		//File src= new File(HOME_PATH+File.separator+TEMPLATE_PATH+File.separator+fileName);
		//InputStream is = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
		if(merge==null){
			merge =new ArrayList<Integer[]>();
		}
		InputStream is = ExportExcelUtil.class.getResourceAsStream(fileName);//new FileInputStream(new File(pathname));
		convertExcelByTemplate(is, params, os,merge);
		is.close();
	}

	/**
	 * 导出Excel
	 * @param is excel模板流
	 * @param params excel模板对应的JEXL表达式
	 * @param os excel输出流
	 * @throws Exception
	 */
	public static void convertExcelByTemplate(InputStream is, Map<String, Object> params, OutputStream os,List<Integer[]> merge) throws Exception {
		XLSTransformer transformer = new XLSTransformer();
		Workbook workbook = transformer.transformXLS(is, params);
		HSSFSheet sheet = (HSSFSheet) workbook.getSheetAt(0);
		for(int i=0;i<merge.size();i++){
			Integer[] intArrThs=merge.get(i);
		sheet.addMergedRegion(new CellRangeAddress(intArrThs[0],intArrThs[1],intArrThs[2],intArrThs[3]));
		}
		workbook.write(os);
		 

	}
}
