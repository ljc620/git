package com.zhming.support;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.zhming.support.bean.UserBean;
import com.zhming.support.common.Constant;
import com.zhming.support.doExcel.ExportExcelUtil;
import com.zhming.support.util.StringUtil;

@RestController
public class BaseController {
	/**
	 * 得到当前登录用户session
	 * @return
	 */
	public UserBean getLoginUserBean() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		UserBean userBean = (UserBean) request.getSession().getAttribute(Constant.USERKEY);
		Integer pageNum=null;
		Integer pageSize=null;
		String page=request.getParameter("page");
		String rows=request.getParameter("rows");
		if(page==null){
			pageNum=1;
		}
		else{
			pageNum=Integer.valueOf(page);;
		}
		if(rows==null){
			pageSize=10;
		}
		else{
			pageSize=Integer.valueOf(rows);
		}
		userBean.setPageNum(pageNum);
		userBean.setPageSize(pageSize);
		return userBean;
	}
 

	/** 
	* Title: 得到当前方法的导航信息<br/>
	* Description: navigation通过ControllerAspect切面来赋值
	* @return
	*/ 
	public String getNavigation() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String navigation = StringUtil.convertString(request.getAttribute("navigation"));
		return navigation;
	}
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.registerCustomEditor(Timestamp.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
	}
	 /**
     * 导出EXCEL
     * 
     * @param map
     * @param tempFile
     * @throws IOException
     * @throws Exception
     */
	public void exportDetailExcelMerge(Map<String, Object> map, String tempFile,String expfileNm,List<Integer[]> merge) throws IOException, Exception {
		HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
		OutputStream out =null;
		try{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/vnd.ms-excel");
		String formatFileName = StringUtil.encodingFileName(expfileNm);
		response.setHeader("content-disposition",
				"attachment; filename=" + formatFileName); 
		 out = response.getOutputStream();
		
		ExportExcelUtil.convertExcelByTemplate(tempFile, map, out,merge);
		out.flush();
		} catch (Exception e) {
            e.printStackTrace();
        } finally{
            if(out!=null){
                try {
                	out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}
	 /**
     * 导出EXCEL
     * 
     * @param map
     * @param tempFile
     * @throws IOException
     * @throws Exception
     */
	public void exportDetailExcel(Map<String, Object> map, String tempFile,String expfileNm) throws IOException, Exception {
		HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
		OutputStream out =null;
		try{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/vnd.ms-excel");
		String formatFileName = StringUtil.encodingFileName(expfileNm);
		response.setHeader("content-disposition",
				"attachment; filename=" + formatFileName); 
		 out = response.getOutputStream();
		
		ExportExcelUtil.convertExcelByTemplate(tempFile, map, out,null);
		out.flush();
		} catch (Exception e) {
            e.printStackTrace();
        } finally{
            if(out!=null){
                try {
                	out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}
}
