package com.tbims.common;

/**
* Title:代码转换成中文名称   <br/>
* Description: 
* @ClassName: NameConvert
* @author ydc
* @date 2017年7月20日 下午7:30:31
* 
*/
public class NameConvert {
	
	/** 
	* Title:岗位名称转换 <br/>
	* Description: 
	* @param positionCode
	* @return
	*/ 
	public static String getPositionName(String positionCode){
		//岗位(01-票务中心02-网点管理岗03-网点操作岗)
		String positionName = "";
		if ("01".equals(positionCode)) {
			positionName = "票务中心";
		}
		if ("02".equals(positionCode)) {
			positionName = "网点管理岗";
		}
		if ("03".equals(positionCode)) {
			positionName = "网点操作岗";
		}
		return positionName;
	}
	
}
