package com.zhming.support.util;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Title: 字符工具类 <br/>
 * Description:
 * @ClassName: StringUtil
 * @author ydc
 * @date 2016-1-7 下午5:19:43
 * 
 */
public class StringUtil {
	public static final char UNDERLINE = '_';
	/**
	 * Title: 判断字符是否为空<br/>
	 * Description:
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str) {
		if (str == null) {
			return true;
		}
		if (str.trim().equals("")) {
			return true;
		}
		if (str.trim().equals("null")) {
			return true;
		}
		return false;
	}

	/**
	 * Title: 字符串去前后空格<br/>
	 * Description:
	 * @param str
	 * @return
	 */
	public static String trim(String str) {
		if (str == null) {
			return "";
		}
		if (str.trim().equals("null")) {
			return "";
		}
		return str.trim();
	}

	/**
	 * Title: 对象转换为字符串<br/>
	 * Description:
	 * @param obj
	 * @return
	 */
	public static String convertString(Object obj) {
		if (obj == null) {
			return "";
		}
		if (obj.equals("")) {
			return "";
		}
		if (obj.equals("null")) {
			return "";
		}
		String str = String.valueOf(obj);
		return str;
	}

	/**
	 * Title: 判断字符是否不为空<br/>
	 * Description:
	 * @param str
	 * @return
	 */
	public static boolean isNotNull(String str) {
		if (str == null) {
			return false;
		}
		if (str.trim().equals("")) {
			return false;
		}
		if (str.trim().equals("null")) {
			return false;
		}
		return true;
	}

	/**
	 * Title: 为数据库查询参数前后加上'%',用于模糊查询<br/>
	 * Description:
	 * @param param
	 * @return
	 */
	public static String queryParam(String param) {
		return "%" + param + "%";
	}

	/**
	 * Title: 为数据库查询参数前后加上单引号<br/>
	 * Description:
	 * @param param
	 * @return
	 */
	public static String queryParamByString(String param) {
		return "'" + param + "'";
	}

	/**
	 * Title: 为配置加上大括号<br/>
	 * Description:
	 * @param param
	 * @return
	 */
	public static String treeNode(String param) {
		return "[" + param + "]";
	}

	/**
	 * Title:判断字符串数组是否包含指定字符串 <br/>
	 * Description:
	 * @param obj
	 * @param objs
	 * @return
	 */
	public static boolean isContain(String obj, String[] objs) {
		for (String o : objs) {
			if (obj.equals(o)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Title:判断字符串数组是否包含指定字符串,不区分大小写 <br/>
	 * Description:
	 * @param obj
	 * @param objs
	 * @return true：包含 false：不包含
	 */
	public static boolean isContainIgnoreCase(String obj, String[] objs) {
		for (String o : objs) {
			if (o.equalsIgnoreCase(obj)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Title: 将字符转换为set集合<br/>
	 * Description: 以，分隔的字符
	 * @param str
	 * @return
	 */
	public static Set<String> convertStingToSet(String str) {
		Set<String> retSet = new HashSet<String>();
		String[] strs = str.split(",");
		for (String o : strs) {
			retSet.add(o);
		}
		return retSet;
	}

	/*****************************************/

	public static String print24(String str) {
		StringBuilder re = new StringBuilder();
		re.append(str);
		int i = 24 - re.length();
		for (int j = 0; j < i; j++) {
			re.append(" ");
		}
		return re.toString();
	}

	public static String print24(Object str) {
		String s = StringUtil.convertString(str);
		StringBuilder re = new StringBuilder();
		re.append(s);
		int i = 24 - re.length();
		for (int j = 0; j < i; j++) {
			re.append(" ");
		}
		return re.toString();
	}

	public static int strChinaLength(String str) {
		int count = 0;
		String regex = "[\u4e00-\u9fa5]";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		while (m.find()) {
			count++;
			System.out.print(m.group() + " ");
		}
		return count;
	}

	public static int strDBLength(String str) {
		int count = 0;
		String regex = "[\u4e00-\u9fa5]";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		while (m.find()) {
			count++;
		}
		count = count + str.length();
		return count;
	}

	/**
	 * 把输入字符串的首字母改成大写
	 * 
	 * @param str
	 * @return
	 */
	public static String initcap(String str) {
		char[] ch = str.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z') {
			ch[0] = (char) (ch[0] - 32);
		}
		return new String(ch);
	}

	// 过滤特殊字符
	public static String StringFilter(String str) {
		// 只允许字母和数字 // String regEx ="[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println(print24("列名"));
		System.out.println(print24("abc"));
	}
	/**
	 * 字符串转驼峰模式
	 * @param param
	 * @return
	 */
	public static String underlineToCamel(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		param = param.toLowerCase();
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (c == UNDERLINE) {
				if (++i < len) {
					sb.append(Character.toUpperCase(param.charAt(i)));
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 
	* Title:判断字符是否为中文 <br/>
	* Description: 是中文返回true,不是中文返回false
	* @param str
	* @return
	 */
	public static boolean isChineseCode(String str){
        Pattern p=Pattern.compile("[\u4e00-\u9fa5]"); 
        Matcher m=p.matcher(str); 
        if(m.find()){ 
            return true;
        }
        return false;
}
	/**
	 * 字符串填充/截取
	 * 
	 * @param str
	 *            待填充字符串
	 * @param fStr
	 *            填充字符
	 * @param length
	 *            填充后总长度
	 * @param rol
	 *            填充方式（ 1 左填充; -1 右填充 ）
	 * @return 填充或截取后的字符串
	 */
	public static String stringFillOrCut(String str, String fStr, int length, int rol) {
		if (str == null && fStr == null) {
			throw new NullPointerException();
		}

		StringBuffer sb = new StringBuffer();
		int strLen = 0;

		try {
			strLen = String.valueOf(str).getBytes("GBK").length;

			// 长度小于规定长度，则进行填充操作
			if (strLen <= length) {
				for (int i = 0; i < length - strLen; i++)
					sb.append(fStr);

				// 左填充
				if (rol >= 0)
					return sb + str;
				else
					return str + sb;
			} else {
				return new String(Arrays.copyOf(String.valueOf(str).getBytes("GBK"), length), "GBK");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}

	}	
}
