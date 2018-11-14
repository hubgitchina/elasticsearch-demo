package cn.com.ut.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.UUID;

/**
 * 通用工具类
 * 
 * @author wuxiaohua
 * @since 2013-12-22下午2:17:45
 */
public class CommonUtil {

	/**
	 * 加密符号
	 */
	private static final String ENCRYPT_SYMBOL = "******";

	/**
	 * 判断当前字符串值是否存在于目标数组中
	 * 
	 * @param str
	 *            当前字符串
	 * @param matchStrArray
	 *            目标数组
	 * @return 结果
	 */
	public static final boolean isExists(String str, String[] matchStrArray) {

		if (str == null || matchStrArray == null || matchStrArray.length == 0) {
			return false;
		}

		for (String matchStr : matchStrArray) {

			if (str.equals(matchStr)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * JDK提供MD5实现
	 * 
	 * @param data
	 * @return md5字符串
	 */
	public static String encodeMD5(byte[] data) {

		byte[] result = null;
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			result = md.digest(data);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return ArrayUtil.byte2HexStr(result);
	}

	/**
	 * 判断字符串类型为空
	 * 
	 * @param str
	 * @return 字符串类型为空
	 */
	public static boolean isEmpty(String str) {

		return str == null || "".equals(str.trim());
	}

	/**
	 * 判断当前运行环境是否为windows
	 * 
	 * @return 是否为windows
	 */
	public static boolean isWindows() {

		return System.getProperty("os.name").toLowerCase().indexOf("windows") != -1;
	}

	/**
	 * 判断字符串类型为空 {@link #isEmpty(String)}
	 * 
	 * @param array
	 * @return 字符串类型为空
	 */
	public static boolean isEmpty(String... array) {

		if (array == null)
			return true;
		for (String e : array) {
			if (isEmpty(e))
				return true;
		}
		return false;
	}

	/**
	 * 判断集中是否存在值为空的元素
	 * 
	 * @param vo
	 *            集合
	 * @param names
	 *            参数名称数组
	 * @return
	 */
	public static boolean isEmpty(Map<String, ? extends Object> vo, String[] names) {

		if (CollectionUtil.isEmptyMap(vo)) {
			return true;
		}

		if (names == null || names.length == 0) {
			Set<String> set = vo.keySet();
			names = set.toArray(new String[set.size()]);
		}

		for (String name : names) {
			if (vo.get(name) == null || CommonUtil.isEmpty(String.valueOf(vo.get(name)))) {
				return true;
			}
		}

		return false;

	}

	/**
	 * 判断字符串不为空
	 * 
	 * @param str
	 * @return 字符串是否不为空
	 */
	public static boolean isNotEmpty(String str) {

		return !isEmpty(str);
	}

	/**
	 * 使用给定值替换字符串为空的情况
	 * 
	 * @param str
	 * @param value
	 * @return str==null返回value,否则返回str字符串
	 */
	public static String doNull(Object str, String value) {

		if (str == null)
			return value;
		return str.toString();
	}

	/**
	 * 使用连接符连接字符串
	 * 
	 * @param sign
	 * @param args
	 * @return 新的字符串
	 */
	public static String stringAppend(String sign, String... args) {

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < args.length; i++) {
			if (i == 0)
				sb.append(args[i]);
			else
				sb.append(sign).append(args[i]);
		}
		return sb.toString();
	}

	/**
	 * str to int
	 * 
	 * @param str
	 * @param value
	 * @return Integer
	 */
	public static Integer str2Int(String str, Integer value) {

		Integer rtn = value;
		try {
			rtn = Integer.parseInt(str);
		} catch (Exception e) {
		}
		return rtn;
	}

	/**
	 * 
	 * @return 返回32位的uuid
	 */
	public static String getUUID() {

		String result = UUID.randomUUID().toString();

		return result.replaceAll("-", "");
	}

	/**
	 * 在指定的字符串中获取指定位数的随机字符串 {@link #getRandomAesKey(int)}
	 * 
	 * @param str
	 * @param length
	 * @return String
	 */
	public static String getRandomKey(String str, int length) {

		if (CommonUtil.isEmpty(str))
			return null;
		StringBuffer buffer = new StringBuffer(str);
		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		int range = buffer.length();
		for (int i = 0; i < length; i++) {
			sb.append(buffer.charAt(r.nextInt(range)));
		}
		return sb.toString();

	}

	/**
	 * 如果字符串长度小于指定位数，则用指定的前缀补齐
	 * 
	 * @param str
	 * @param prefix
	 * @param length
	 * @return String
	 */
	public static String fillPrefixToLength(String str, String prefix, int length) {

		if (isEmpty(prefix))
			return str;
		str = str == null ? "" : str.trim();
		int strLen = str.length();
		if (strLen == length)
			return str;
		else if (strLen > length)
			throw new IllegalArgumentException("the str had exceed the limited length");

		StringBuffer temp = new StringBuffer();
		for (int i = 0; i < length - strLen; i++) {
			temp.append(prefix);
		}
		temp.append(str);
		return temp.toString();
	}

	/**
	 * 向上逐级找出某菜单的祖先菜单，直到祖先为一级菜单结束
	 * 
	 * @param menuIds
	 * @return List<String>
	 */
	public static List<String> withParentMenus(List<String> menuIds) {

		if (CollectionUtil.isEmptyCollection(menuIds))
			return menuIds;

		HashSet<String> set = new HashSet<String>();
		set.addAll(menuIds);
		for (String menuId : menuIds) {
			withParentMenus(menuId, set);
		}
		menuIds = new ArrayList<String>();
		menuIds.addAll(set);
		return menuIds;
	}

	/**
	 * 向上逐级找出某菜单的祖先菜单，直到祖先为一级菜单结束
	 * 
	 * @param id
	 * @param set
	 */
	private static void withParentMenus(String id, HashSet<String> set) {

		int LEN = 3;
		while (id.length() > LEN) {
			id = id.substring(0, id.length() - LEN);
			if (!set.contains(id))
				set.add(id);
		}
	}

	/**
	 *
	 * @param id
	 * @return List<String>
	 */
	public static List<String> withParentMenus(String id) {

		int LEN = 3;
		List<String> list = new ArrayList<String>();
		while (id.length() > LEN) {
			id = id.substring(0, id.length() - LEN);
			list.add(id);
		}
		return list;
	}

	/**
	 * 对字符串进行隐式转换，除去头部和尾部显示部分明文，剩余中间部分的字符全部转换为隐式字符*，如果不足6位则补齐6颗*字符
	 * 
	 * @param value
	 *            明文
	 * @param startLength
	 *            头部保持明文的字符数
	 * @param endLength
	 *            尾部保持明文的字符数
	 * @return String
	 */
	public static String encryptFormat(String value, int startLength, int endLength) {

		if (isEmpty(value) || (startLength < 1 && endLength < 1)) {
			return value;
		}

		int length = value.length();
		if (startLength > length)
			startLength = length;

		if (endLength > length)
			endLength = length;

		StringBuilder builder = new StringBuilder(value);

		for (int i = startLength; i < builder.length() - endLength; i++) {
			builder.setCharAt(i, '*');
		}

		int num = length - startLength - endLength;
		num = num < 0 ? 0 : num;
		num = ENCRYPT_SYMBOL.length() - num;
		if (num > 0) {
			StringBuilder temp = new StringBuilder(num);
			for (int j = 0; j < num; j++) {
				temp.append("*");
			}
			builder.insert(startLength, temp);
		}

		return builder.toString();
	}

	/**
	 * 判断数组中是否包含 某个字符
	 * 
	 * @param strs
	 * @param s
	 * @return
	 */
	public static boolean isHave(String[] strs, String s) {

		if (isEmpty(s)) {
			return false;
		}
		if (strs == null || strs.length < 1) {
			return false;
		}
		for (String tempString : strs) {
			if (isEmpty(tempString)) {
				continue;
			}
			if (s.equals(tempString)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取扩展名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileExtName(String fileName) {

		if (fileName == null || "".equals(fileName)) {
			return null;
		}
		if (fileName.contains(".")) {
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		}
		return null;
	}

	/**
	 * 根据水平表结构中拥有父子关系的数据构建父子引用层级关系
	 * 
	 * @param list
	 *            水平表中的行
	 * @param parentIdField
	 *            父字段名
	 * @param idField
	 *            子字段名
	 * @param nodeName
	 *            孩子结点名称
	 */
	public static List<Map<String, Object>> createTree(List<Map<String, Object>> list,
			String parentIdField, String idField, String nodeName) {

		List<Map<String, Object>> array = new ArrayList<>();
		if (list == null || list.isEmpty()) {
			return array;
		}

		Map<String, Map<String, Object>> cache = CollectionUtil.listToMap(list, idField);
		for (Map<String, Object> vo : list) {

			String parentId = (String) vo.get(parentIdField);
			if (parentId == null) {
				array.add(vo);
			} else {
				Map<String, Object> parentVo = cache.get(parentId);
				if (parentVo != null) {
					List<Map<String, Object>> child = (List<Map<String, Object>>) parentVo
							.get(nodeName);
					if (child == null) {
						child = new ArrayList<>();
						parentVo.put(nodeName, child);
					}
					child.add(vo);
				}
			}
		}
		return array;
	}

	/**
	 * 对字符串进行分割，判断分割后的多个子串中是否存在与待查找的目标子串匹配的子串
	 * 
	 * @param str
	 *            待分割字符串
	 * @param delim
	 *            分割符
	 * @param target
	 *            待查找的目标子串
	 * @return 是否找到目标子串
	 */
	public static boolean indexOf(String str, String delim, String target) {

		StringTokenizer st = new StringTokenizer(str, delim);
		while (st.hasMoreElements()) {
			if (st.nextElement().equals(target))
				return true;
		}
		return false;
	}
}
