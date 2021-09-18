package com.zhoulei.redis.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字符串工具类
 * 
 * @author dave
 */
public class StringUtils {
	/** 空字符串 */
	private static final String NULLSTR = "";

	/** 下划线 */
	private static final char SEPARATOR = '_';

	/**
	 * 获取参数不为空值
	 * 
	 * @param value
	 *            defaultValue 要判断的value
	 * @return value 返回值
	 */
	public static <T> T nvl(T value, T defaultValue) {
		return value != null ? value : defaultValue;
	}

	/**
	 * * 判断一个Collection是否为空， 包含List，Set，Queue
	 * 
	 * @param coll
	 *            要判断的Collection
	 * @return true：为空 false：非空
	 */
	public static boolean isEmpty(Collection<?> coll) {
		return isNull(coll) || coll.isEmpty();
	}

	/**
	 * * 判断一个Collection是否非空，包含List，Set，Queue
	 * 
	 * @param coll
	 *            要判断的Collection
	 * @return true：非空 false：空
	 */
	public static boolean isNotEmpty(Collection<?> coll) {
		return !isEmpty(coll);
	}

	/**
	 * * 判断一个List是否非空，包含List，Set，Queue
	 * @param <T>
	 * @param coll
	 *            要判断的Collection
	 * @return true：非空 false：空
	 */
	public static <T> boolean isNotEmpty(List<T> coll) {
		return null != coll && coll.size() > 0;
	}

	/**
	 * * 判断一个对象数组是否为空
	 * 
	 * @param objects
	 *            要判断的对象数组
	 ** @return true：为空 false：非空
	 */
	public static boolean isEmpty(Object[] objects) {
		return isNull(objects) || (objects.length == 0);
	}

	/**
	 * * 判断一个对象是否为空
	 * 
	 * @param objects
	 *            要判断的对象
	 ** @return true：为空 false：非空
	 */
	public static boolean isEmpty(Object objects) {
		return isNull(objects);
	}

	/**
	 * * 判断一个对象数组是否非空
	 * 
	 * @param objects
	 *            要判断的对象数组
	 * @return true：非空 false：空
	 */
	public static boolean isNotEmpty(Object[] objects) {
		return !isEmpty(objects);
	}

	/**
	 * * 判断一个Map是否为空
	 * 
	 * @param map
	 *            要判断的Map
	 * @return true：为空 false：非空
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		return isNull(map) || map.isEmpty();
	}

	/**
	 * * 判断一个Map是否为空
	 * 
	 * @param map
	 *            要判断的Map
	 * @return true：非空 false：空
	 */
	public static boolean isNotEmpty(Map<?, ?> map) {
		return !isEmpty(map);
	}

	/**
	 * * 判断一个字符串是否为空串
	 * 
	 * @param str
	 *            String
	 * @return true：为空 false：非空
	 */
	public static boolean isEmpty(String str) {
		return isNull(str) || str.trim().length() < 1 || NULLSTR.equals(str.trim())
				|| str.trim().toLowerCase().equals("null");
	}

	/**
	 * * 判断一个字符串是否为非空串
	 * 
	 * @param str
	 *            String
	 * @return true：非空串 false：空串
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * * 判断一个Object是否为非空串
	 * 
	 * @param str
	 *            String
	 * @return true：非空串 false：空串
	 */
	public static boolean isNotEmpty(Object str) {
		return !isEmpty(str);
	}

	/**
	 * * 判断一个对象是否为空
	 * 
	 * @param object
	 *            Object
	 * @return true：为空 false：非空
	 */
	public static boolean isNull(Object object) {
		return null == object;
	}

	/**
	 * * 判断一个对象是否非空
	 * 
	 * @param object
	 *            Object
	 * @return true：非空 false：空
	 */
	public static boolean isNotNull(Object object) {
		return !isNull(object);
	}

	/**
	 * * 判断一个对象是否是数组类型（Java基本型别的数组）
	 * 
	 * @param object
	 *            对象
	 * @return true：是数组 false：不是数组
	 */
	public static boolean isArray(Object object) {
		return isNotNull(object) && object.getClass().isArray();
	}

	/**
	 * 去空格
	 */
	public static String trim(String str) {
		return (str == null ? "" : str.trim());
	}

	/**
	 * 截取字符串
	 * 
	 * @param str
	 *            字符串
	 * @param start
	 *            开始
	 * @return 结果
	 */
	public static String substring(final String str, int start) {
		if (str == null) {
			return NULLSTR;
		}

		if (start < 0) {
			start = str.length() + start;
		}

		if (start < 0) {
			start = 0;
		}
		if (start > str.length()) {
			return NULLSTR;
		}

		return str.substring(start);
	}

	/**
	 * 截取字符串
	 * 
	 * @param str
	 *            字符串
	 * @param start
	 *            开始
	 * @param end
	 *            结束
	 * @return 结果
	 */
	public static String substring(final String str, int start, int end) {
		if (str == null) {
			return NULLSTR;
		}

		if (end < 0) {
			end = str.length() + end;
		}
		if (start < 0) {
			start = str.length() + start;
		}

		if (end > str.length()) {
			end = str.length();
		}

		if (start > end) {
			return NULLSTR;
		}

		if (start < 0) {
			start = 0;
		}
		if (end < 0) {
			end = 0;
		}

		return str.substring(start, end);
	}

	/**
	 * 格式化文本, {} 表示占位符<br>
	 * 此方法只是简单将占位符 {} 按照顺序替换为参数<br>
	 * 如果想输出 {} 使用 \\转义 { 即可，如果想输出 {} 之前的 \ 使用双转义符 \\\\ 即可<br>
	 * 例：<br>
	 * 通常使用：format("this is {} for {}", "a", "b") -> this is a for b<br>
	 * 转义{}： format("this is \\{} for {}", "a", "b") -> this is \{} for a<br>
	 * 转义\： format("this is \\\\{} for {}", "a", "b") -> this is \a for b<br>
	 * 
	 * @param template
	 *            文本模板，被替换的部分用 {} 表示
	 * @param params
	 *            参数值
	 * @return 格式化后的文本
	 */
//	public static String format(String template, Object... params) {
//		if (isEmpty(params) || isEmpty(template)) {
//			return template;
//		}
//		return StrFormatter.format(template, params);
//	}

	/**
	 * 下划线转驼峰命名
	 */
	public static String toUnderScoreCase(String str) {
		if (str == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		// 前置字符是否大写
		boolean preCharIsUpperCase = true;
		// 当前字符是否大写
		boolean curreCharIsUpperCase = true;
		// 下一字符是否大写
		boolean nexteCharIsUpperCase = true;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (i > 0) {
				preCharIsUpperCase = Character.isUpperCase(str.charAt(i - 1));
			} else {
				preCharIsUpperCase = false;
			}

			curreCharIsUpperCase = Character.isUpperCase(c);

			if (i < (str.length() - 1)) {
				nexteCharIsUpperCase = Character.isUpperCase(str.charAt(i + 1));
			}

			if (preCharIsUpperCase && curreCharIsUpperCase && !nexteCharIsUpperCase) {
				sb.append(SEPARATOR);
			} else if ((i != 0 && !preCharIsUpperCase) && curreCharIsUpperCase) {
				sb.append(SEPARATOR);
			}
			sb.append(Character.toLowerCase(c));
		}

		return sb.toString();
	}

	/**
	 * 是否包含字符串
	 * 
	 * @param str
	 *            验证字符串
	 * @param strs
	 *            字符串组
	 * @return 包含返回true
	 */
	public static boolean inStringIgnoreCase(String str, String... strs) {
		if (str != null && strs != null) {
			for (String s : strs) {
				if (str.equalsIgnoreCase(trim(s))) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。
	 * 例如：HELLO_WORLD->HelloWorld
	 * 
	 * @param name
	 *            转换前的下划线大写方式命名的字符串
	 * @return 转换后的驼峰式命名的字符串
	 */
	public static String convertToCamelCase(String name) {
		StringBuilder result = new StringBuilder();
		// 快速检查
		if (name == null || name.isEmpty()) {
			// 没必要转换
			return "";
		} else if (!name.contains("_")) {
			// 不含下划线，仅将首字母大写
			return name.substring(0, 1).toUpperCase() + name.substring(1);
		}
		// 用下划线将原始字符串分割
		String[] camels = name.split("_");
		for (String camel : camels) {
			// 跳过原始字符串中开头、结尾的下换线或双重下划线
			if (camel.isEmpty()) {
				continue;
			}
			// 首字母大写
			result.append(camel.substring(0, 1).toUpperCase());
			result.append(camel.substring(1).toLowerCase());
		}
		return result.toString();
	}

	/**
	 * 驼峰式命名法 例如：user_name->userName
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

	@SuppressWarnings("unchecked")
	public static <T> T cast(Object obj) {
		return (T) obj;
	}

	/**
	 * 将str字符串转换为json对象
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static Map<String, String> toMapStr(String jsonStr) {
		Map<String, String> map = new HashMap<>();
		JSONObject parseObject = JSON.parseObject(jsonStr);
		for (String key : parseObject.keySet()) {
			map.put(key, parseObject.get(key).toString());
		}
		return map;
	}

	public static long IsOutOfMax(String s) {
		long l = 0;
		l = Long.parseLong(s);
		if (l < -9223372036854775808L || l > 9223372036854775807L) {
			return 0;
		} else
			return l;
	}

	/**
	 * value存在空 java类转map
	 * 
	 * @param bean
	 * @return
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static Map<String, String> convertBeanToMap(Object bean)
			throws IntrospectionException, IllegalAccessException, InvocationTargetException {
		Class type = bean.getClass();
		Map<String, String> returnMap = new HashMap<String, String>();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				if (result != null) {
					returnMap.put(propertyName, result.toString());
				} else {
					returnMap.put(propertyName, "");
				}
			}
		}
		return returnMap;
	}

	/**
	 * value存在空 java类转url
	 * 
	 * @param bean
	 * @return
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static String convertBeanToUrl(Object bean)
			throws IntrospectionException, IllegalAccessException, InvocationTargetException {
		Class type = bean.getClass();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				sb.append(propertyName + "=");
				Object result = readMethod.invoke(bean, new Object[0]);
				if (result != null) {
					sb.append(result + "&");
				} else {
					sb.append("&");
				}
			}
		}
		String result = sb.toString();
		return result.substring(0, result.length() - 1);
	}

	/**
	 * 将url参数转换成map
	 * 
	 * @param param
	 *            aa=11&bb=22&cc=33
	 * @return
	 */
	public static Map<String, String> getUrlParams(String param) {
		Map<String, String> map = new HashMap<String, String>(0);
		if (isEmpty(param)) {
			return map;
		}
		String[] params = param.split("&");
		for (int i = 0; i < params.length; i++) {
			String[] p = params[i].split("=");
			if (p.length == 2) {
				map.put(p[0], p[1]);
			}
		}
		return map;
	}
	
	/**
	 * 去掉空字符串
	 * @param str
	 * @return
	 */
	private static String[] replaceNull(String[] str){
	    //用StringBuffer来存放数组中的非空元素，用“;”分隔
	       StringBuffer sb = new StringBuffer();
	       for(int i=0; i<str.length; i++) {
	           if("".equals(str[i])) {
	               continue;
	           }
	           sb.append(str[i]);
	           if(i != str.length - 1) {
	               sb.append(";");
	           }
	       }
	       //用String的split方法分割，得到数组
	       str = sb.toString().split(";");
	       return str;
	}
	
	/**
	 * 将map转换成url
	 * 
	 * @param map
	 * @return
	 */
	public static String getUrlParamsByMap(Map<String, String> map) {
		if (map == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			sb.append(entry.getKey() + "=" + entry.getValue());
			sb.append("&");
		}
		String s = sb.toString();
		if (s.endsWith("&")) {
			s = s.substring(0, s.length() - 1);
		}
		return s;
	}
	
	public static int changeInt(String str) {
		if (str == null || str != null && str.equals("")) {// 输入非法结束。
			return 0;
		}
		if (str.charAt(0) == '-' || str.charAt(0) == '+' || (str.charAt(0) >= '0' && str.charAt(0) <= '9')) {// 负数
			long l = IsOutOfMax(str);
			return (int) l;
		} else {// 非法输入，直接结束
			return 0;
		}
	}
	
	/**
	 * 过滤空NULL
	 * 
	 * @param o
	 * @return
	 */
	public static String FilterStrNull(Object o) {
		return o != null && !"null".equals(o.toString()) ? o.toString().trim() : "";
	}
	
	/**
     * 判断多个字符串其中任意一个是否为空
     * @param strings
     * @return
     */
    public static boolean isHasEmpty(String... strings) {
        if (strings == null) {
            return true;
        }
        for (String str : strings) {
            if (isEmpty(str)) {
                return true;
            }
        }
        return false;
    }
    
    /**
	 * 转化为Long型数字, 不可转化时返回0
	 * 
	 * @param o
	 * @return
	 */
	public static Long toLong(Object o) {
		if (isLong(o)) {
			return new Long(o.toString());
		} else {
			return 0L;
		}
	}
	
	/**
	 * 是否可转化为Long型数字
	 * 
	 * @param o
	 * @return
	 */
	public static boolean isLong(Object o) {
		try {
			new Long(o.toString());
			return true;
		} catch (Exception e) {
		}
		return false;
	}
	
	/**
	 * map 转换url请求参数
	 * @param map
	 * @return
	 */
	public static String buildMap(Map<String, String> map) {
        StringBuffer sb = new StringBuffer();
        if (map.size() > 0) {
            for (String key : map.keySet()) {
                sb.append(key + "=");
                if (isEmpty(map.get(key))) {
                    sb.append("&");
                } else {
                    sb.append(map.get(key) + "&");
                }
            }
        }
        String result = sb.toString();
        return result.substring(0, result.length() - 1);
    }
	
	public static boolean getBoolean(String str) {
		return str.equals("true") ? true : false;
	}

}