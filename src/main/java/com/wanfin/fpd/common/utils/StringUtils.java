/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.common.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;

import com.google.common.collect.Lists;

/**
 * 字符串工具类, 继承org.apache.commons.lang3.StringUtils类
 * @author ThinkGem
 * @version 2013-05-22
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
	
    private static final char SEPARATOR = '_';
    private static final String CHARSET_NAME = "UTF-8";
    
    /**
     * 转换为字节数组
     * @param str
     * @return
     */
    public static byte[] getBytes(String str){
    	if (str != null){
    		try {
				return str.getBytes(CHARSET_NAME);
			} catch (UnsupportedEncodingException e) {
				return null;
			}
    	}else{
    		return null;
    	}
    }
    
    /**
     * 转换为字节数组
     * @param str
     * @return
     */
    public static String toString(byte[] bytes){
    	try {
			return new String(bytes, CHARSET_NAME);
		} catch (UnsupportedEncodingException e) {
			return EMPTY;
		}
    }
    
    /**
     * 是否包含字符串
     * @param str 验证字符串
     * @param strs 字符串组
     * @return 包含返回true
     */
    public static boolean inString(String str, String... strs){
    	if (str != null){
        	for (String s : strs){
        		if (str.equals(trim(s))){
        			return true;
        		}
        	}
    	}
    	return false;
    }
    
	/**
	 * 替换掉HTML标签方法
	 */
	public static String replaceHtml(String html) {
		if (isBlank(html)){
			return "";
		}
		String regEx = "<.+?>";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(html);
		String s = m.replaceAll("");
		return s;
	}
	
	/**
	 * 替换为手机识别的HTML，去掉样式及属性，保留回车。
	 * @param html
	 * @return
	 */
	public static String replaceMobileHtml(String html){
		if (html == null){
			return "";
		}
		return html.replaceAll("<([a-z]+?)\\s+?.*?>", "<$1>");
	}
	
	/**
	 * 替换为手机识别的HTML，去掉样式及属性，保留回车。
	 * @param txt
	 * @return
	 */
	public static String toHtml(String txt){
		if (txt == null){
			return "";
		}
		return replace(replace(Encodes.escapeHtml(txt), "\n", "<br/>"), "\t", "&nbsp; &nbsp; ");
	}

	/**
	 * 将引号等特殊符号的编码转换成正常符号
	 * @param str
	 * @return
	 */
	public static String stringEscape(String str){
		if (str == null) {
			return "";
		}
		str = StringEscapeUtils.unescapeHtml4(str);
		
		return str;
	}
	
	/**
	 * 缩略字符串（不区分中英文字符）
	 * @param str 目标字符串
	 * @param length 截取长度
	 * @return
	 */
	public static String abbr(String str, int length) {
		if (str == null) {
			return "";
		}
		try {
			StringBuilder sb = new StringBuilder();
			int currentLength = 0;
			for (char c : replaceHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray()) {
				currentLength += String.valueOf(c).getBytes("GBK").length;
				if (currentLength <= length - 3) {
					sb.append(c);
				} else {
					sb.append("...");
					break;
				}
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static String abbr2(String param, int length) {
		if (param == null) {
			return "";
		}
		StringBuffer result = new StringBuffer();
		int n = 0;
		char temp;
		boolean isCode = false; // 是不是HTML代码
		boolean isHTML = false; // 是不是HTML特殊字符,如&nbsp;
		for (int i = 0; i < param.length(); i++) {
			temp = param.charAt(i);
			if (temp == '<') {
				isCode = true;
			} else if (temp == '&') {
				isHTML = true;
			} else if (temp == '>' && isCode) {
				n = n - 1;
				isCode = false;
			} else if (temp == ';' && isHTML) {
				isHTML = false;
			}
			try {
				if (!isCode && !isHTML) {
					n += String.valueOf(temp).getBytes("GBK").length;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			if (n <= length - 3) {
				result.append(temp);
			} else {
				result.append("...");
				break;
			}
		}
		// 取出截取字符串中的HTML标记
		String temp_result = result.toString().replaceAll("(>)[^<>]*(<?)",
				"$1$2");
		// 去掉不需要结素标记的HTML标记
		temp_result = temp_result
				.replaceAll(
						"</?(AREA|BASE|BASEFONT|BODY|BR|COL|COLGROUP|DD|DT|FRAME|HEAD|HR|HTML|IMG|INPUT|ISINDEX|LI|LINK|META|OPTION|P|PARAM|TBODY|TD|TFOOT|TH|THEAD|TR|area|base|basefont|body|br|col|colgroup|dd|dt|frame|head|hr|html|img|input|isindex|li|link|meta|option|p|param|tbody|td|tfoot|th|thead|tr)[^<>]*/?>",
						"");
		// 去掉成对的HTML标记
		temp_result = temp_result.replaceAll("<([a-zA-Z]+)[^<>]*>(.*?)</\\1>",
				"$2");
		// 用正则表达式取出标记
		Pattern p = Pattern.compile("<([a-zA-Z]+)[^<>]*>");
		Matcher m = p.matcher(temp_result);
		List<String> endHTML = Lists.newArrayList();
		while (m.find()) {
			endHTML.add(m.group(1));
		}
		// 补全不成对的HTML标记
		for (int i = endHTML.size() - 1; i >= 0; i--) {
			result.append("</");
			result.append(endHTML.get(i));
			result.append(">");
		}
		return result.toString();
	}
	
	/**
	 * 转换为Double类型
	 */
	public static Double toDouble(Object val){
		if (val == null){
			return 0D;
		}
		try {
			return Double.valueOf(trim(val.toString()));
		} catch (Exception e) {
			return 0D;
		}
	}

	/**
	 * 转换为Float类型
	 */
	public static Float toFloat(Object val){
		return toDouble(val).floatValue();
	}

	/**
	 * 转换为Long类型
	 */
	public static Long toLong(Object val){
		return toDouble(val).longValue();
	}

	/**
	 * 转换为Integer类型
	 */
	public static Integer toInteger(Object val){
		return toLong(val).intValue();
	}
	
	/**
	 * 获得i18n字符串
	 */
	public static String getMessage(String code, Object[] args) {
		LocaleResolver localLocaleResolver = (LocaleResolver) SpringContextHolder.getBean(LocaleResolver.class);
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		Locale localLocale = localLocaleResolver.resolveLocale(request);
		return SpringContextHolder.getApplicationContext().getMessage(code, args, localLocale);
	}
	
	/**
	 * 获得用户远程地址
	 */
	public static String getRemoteAddr(HttpServletRequest request){
		String remoteAddr = request.getHeader("X-Real-IP");
        if (isNotBlank(remoteAddr)) {
        	remoteAddr = request.getHeader("X-Forwarded-For");
        }else if (isNotBlank(remoteAddr)) {
        	remoteAddr = request.getHeader("Proxy-Client-IP");
        }else if (isNotBlank(remoteAddr)) {
        	remoteAddr = request.getHeader("WL-Proxy-Client-IP");
        }
        return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
	}

	/**
	 * 驼峰命名法工具
	 * @return
	 * 		toCamelCase("hello_world") == "helloWorld" 
	 * 		toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 * 		toUnderScoreCase("helloWorld") = "hello_world"
	 */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }

        s = s.toLowerCase();

        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
	 * 驼峰命名法工具
	 * @return
	 * 		toCamelCase("hello_world") == "helloWorld" 
	 * 		toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 * 		toUnderScoreCase("helloWorld") = "hello_world"
	 */
    public static String toCapitalizeCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = toCamelCase(s);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
    
    /**
	 * 驼峰命名法工具
	 * @return
	 * 		toCamelCase("hello_world") == "helloWorld" 
	 * 		toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 * 		toUnderScoreCase("helloWorld") = "hello_world"
	 */
    public static String toUnderScoreCase(String s) {
        if (s == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            boolean nextUpperCase = true;

            if (i < (s.length() - 1)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }

            if ((i > 0) && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    sb.append(SEPARATOR);
                }
                upperCase = true;
            } else {
                upperCase = false;
            }

            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }
    
    /**
     * 如果不为空，则设置值
     * @param target
     * @param source
     */
    public static void setValueIfNotBlank(String target, String source) {
		if (isNotBlank(source)){
			target = source;
		}
	}
 
    /**
     * 转换为JS获取对象值，生成三目运算返回结果
     * @param objectString 对象串
     *   例如：row.user.id
     *   返回：!row?'':!row.user?'':!row.user.id?'':row.user.id
     */
    public static String jsGetVal(String objectString){
    	StringBuilder result = new StringBuilder();
    	StringBuilder val = new StringBuilder();
    	String[] vals = split(objectString, ".");
    	for (int i=0; i<vals.length; i++){
    		val.append("." + vals[i]);
    		result.append("!"+(val.substring(1))+"?'':");
    	}
    	result.append(val.substring(1));
    	return result.toString();
    }

	/**
	 * @Description TODO
	 * @param ids
	 * @return
	 * @author Chenh 
	 * @date 2016年5月27日 下午2:52:59  
	 */
	public static String sqlInByList(List<String> ids) {
		StringBuffer sb = new StringBuffer();
		boolean isFirst = true;
		for (String id : ids) {
			if(isFirst){
				sb.append("'"+id+"'");
				isFirst = false;
			}else{
				sb.append(",'"+id+"'");
			}
		}
		return sb.toString();
	}
	
	/**
	 * @Description 获取排序字符串
	 * @param sortby
	 * @param orderby
	 * @return
	 * @author Chenh 
	 * @date 2016年6月8日 下午6:03:02
	 */
	public static String sqlSorts(String sortby, String orderby) {
		if(StringUtils.isNotEmpty(sortby) && StringUtils.isNotEmpty(orderby)){
			String[] sortbys =  sortby.split(",");
			String[] orderbys =  orderby.split(",");
			return sqlSorts(sortbys, orderbys);
		}
		return null;
	}
	
	/**
	 * @Description 获取排序字符串
	 * @param sortby
	 * @param orderby
	 * @return
	 * @author Chenh 
	 * @date 2016年6月8日 下午6:03:02
	 */
	public static String sqlSorts(String[] sortbys, String[] orderbys) {
		if((sortbys != null) && (orderbys != null) && (sortbys.length == orderbys.length)){
			StringBuffer sortsBuffer = new StringBuffer();
			for (int i = 0; i < orderbys.length; i++) {
				if(i != 0){
					sortsBuffer.append(",");
				}
				sortsBuffer.append(sortbys[i] + " " + orderbys[i]);
			}
			return sortsBuffer.toString();
		}
		return null;
	}
	
	/**
	 * @Description 获取排序字符串
	 * @param sortby
	 * @param orderby
	 * @return
	 * @author Chenh 
	 * @date 2016年6月8日 下午6:03:02
	 */
	public static String listToString(List<String> strs) {
		return listToString(strs, ",");
	}
	public static String listToString(List<String> strs, String trim) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < strs.size(); i++) {
			String str = strs.get(i);
			if(i != 0){
				buffer.append(trim);
			}
			buffer.append(str);
		}
		return buffer.toString();
	}

	/**
	 * 字符串转SQL参数
	 * a36c95b7f77e44729f757e0227356267,a36c95b7f77e44729f757e0227356262
	 * 'a36c95b7f77e44729f757e0227356267', 'a36c95b7f77e44729f757e0227356262'
	 * @param strs
	 * @return
	 */
	public static String listToSql(String[] strs) {
		return listToSql(Arrays.asList(strs));
	}
	public static String listToSql(List<String> strs) {
		StringBuffer buffer = new StringBuffer();
		int size = strs.size();
		for (int i = 0; i < size; i++) {
			String str = strs.get(i);
			if((size == 1)){
				buffer.append("'");
				buffer.append(str);
				buffer.append("'");
			}else{
				if((i == 0)){
					buffer.append("'");
					buffer.append(str);
				}else if(i == (size-1)){
					buffer.append("','");
					buffer.append(str);
					buffer.append("'");
				}else{
					buffer.append("','");
					buffer.append(str);
				} 
			}
		}
		return buffer.toString();
	}
	
	/**
	 * 字符串转SQL参数
	 * [a36c95b7f77e44729f757e0227356267]
	 * ['a36c95b7f77e44729f757e0227356267']
	 * @param strs
	 * @return
	 */
	public static List<String> listToSqlList(String[] strs) {
		return listToSqlList(Arrays.asList(strs));
	}
	public static List<String> listToSqlList(List<String> strs) {
		List<String> result = new ArrayList<String>();
		StringBuffer buffer = null;
		for (String str : strs) {
			buffer = new StringBuffer();
			buffer.append("'");
			buffer.append(str);
			buffer.append("'");
			result.add(buffer.toString());
		}
		return result;
	}
	
	/**
	 * 字符串转义
	 * @param str
	 * @return
	 */
	public static String escapeStr(String str) {
		return StringEscapeUtils.escapeEcmaScript(str);
	}
	
	/**
	 * 字符串反转义
	 * @param str
	 * @return
	 */
	public static String unescapeStr(String str) {
		return StringEscapeUtils.unescapeEcmaScript(str);
	}
	
	/**
	 * 手机号码的简单处理，去掉前面的加等符号
	 * @param phone
	 * @return
	 */
	public static String dealPhone(String phone){
		if(StringUtils.isNotBlank(phone)){
			if(phone.startsWith("+86")){
				phone = phone.substring(3);
			}else if(phone.startsWith("+0")){
				phone = phone.substring(2);
			}else if(phone.startsWith("+")){
				phone = phone.substring(1);
			}
		}
		return phone;
	}

	public static void main(String[] args) {
//		String[] ids = new String[]{"a36c95b7f77e44729f757e0227356267",
//				"ff120b0403f54ec4a6bf7acdc58ddcaa",
//				"d207bb69b96544f29d2021667924863a",
//				"58be58cc7be84799ba603399120f6674",
//				"445096a64de343808d76e4d5dceb9c0e",
//				"757204422ed845829c3ed55ec35e645b",
//				"4ae4850408332015040b24912002612",
//				"49d13b1970e4488eb2a281aee2e98f01",
//				"80784504af51901504b47734e000e12",
//				"4ae4850408332015040b24912002712",
//				"4ae485040c38d015040f43caa003712",
//				"807875035f4bb015035f77f57000712",
//				"214a381349164320ae1098ae30102707"};
//		
//		List<String> ss = Arrays.asList(ids);
//		System.out.println(listToSql(ss));
//		System.out.println(listToSqlList(ss));
		
		String phone1 = "+8614151231234";
		String phone2 = "+014151231234";
		String phone3 = "+14151231234";
		
		System.out.println(dealPhone(phone1));
		System.out.println(dealPhone(phone2));
		System.out.println(dealPhone(phone3));
		
	}
}