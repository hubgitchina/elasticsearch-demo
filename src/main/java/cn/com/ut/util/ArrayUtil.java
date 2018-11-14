package cn.com.ut.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.StringTokenizer;

/**
 * 数组处理工具类型
 * 
 * @author wuxiaohua
 * @since 2013-12-22下午2:17:45
 */
public class ArrayUtil {

	/**
	 * 判断数组是否为空数组
	 * 
	 * @param array
	 * @return boolean
	 */
	public static <T> boolean isEmptyArray(T[] array) {

		if (array == null || array.length == 0)
			return true;
		return false;
	}

	/**
	 * 将指定的字符串循环指定的次数加入指定的字符串末尾
	 * 
	 * @param element
	 * @param times
	 * @param ch
	 * @return 新的字符串
	 */
	public static String joinSameElement(String element, int times, String ch) {

		if (CommonUtil.isEmpty(element) || times < 1 || ch == null)
			return null;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < times; i++) {
			if (i == 0)
				sb.append(element);
			else
				sb.append(ch).append(element);
		}
		if (sb.length() == 0)
			return null;
		else
			return sb.toString();
	}

	/**
	 * 在指定的数组element中加入指定的字符串 举例： joinArrayElement(new String[]{"ss","ee","vv"},
	 * "dd") 返回：ssddeeddvv
	 * 
	 * @param element
	 * @param ch
	 * @return String
	 */
	public static String joinArrayElement(String[] element, String ch) {

		if (element == null || element.length == 0 || ch == null)
			return null;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < element.length; i++) {
			if (i == 0)
				sb.append(element[i]);
			else
				sb.append(ch).append(element[i]);
		}
		if (sb.length() == 0)
			return null;
		else
			return sb.toString();
	}

	/**
	 * 给数组循环追加元素，追加前缀和后缀
	 * 
	 * @param element
	 * @param ch
	 * @param prefix
	 * @param postfix
	 * @return 返回字符串
	 */
	public static String joinArrayElement(String[] element, String ch, String prefix,
			String postfix) {

		if (element == null || element.length == 0 || ch == null)
			return null;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < element.length; i++) {
			if (i == 0) {
				if (!CommonUtil.isEmpty(prefix))
					sb.append(prefix);
				sb.append(element[i]);
				if (!CommonUtil.isEmpty(postfix))
					sb.append(postfix);
			} else {
				if (!CommonUtil.isEmpty(prefix))
					sb.append(prefix);
				sb.append(ch).append(element[i]);
				if (!CommonUtil.isEmpty(postfix))
					sb.append(postfix);
			}
		}
		if (sb.length() == 0)
			return null;
		else
			return sb.toString();
	}

	/**
	 * {@link #joinArrayElement(String[], String)}
	 * 
	 * @param element
	 * @param ch
	 * @return String
	 */
	public static String joinArrayElement(Collection<String> element, String ch) {

		if (element == null || element.isEmpty())
			return null;
		return joinArrayElement(element.toArray(new String[element.size()]), ch);
	}

	/**
	 * {@link #joinArrayElement(String[], String, String, String)}
	 * 
	 * @param element
	 * @param ch
	 * @param prefix
	 * @param postfix
	 * @return String
	 */
	public static String joinArrayElement(Collection<String> element, String ch, String prefix,
			String postfix) {

		if (element == null || element.isEmpty())
			return null;
		return joinArrayElement(element.toArray(new String[element.size()]), ch, prefix, postfix);
	}

	/**
	 * 转换int类型为4个字节
	 * 
	 * @param intValue
	 * @return byte[]
	 */
	public static byte[] int2Byte(int intValue) {

		byte[] b = new byte[4];
		for (int i = 0; i < 4; i++) {
			b[i] = (byte) (intValue >> 8 * (3 - i) & 0xFF);
			// System.out.print(Integer.toBinaryString(b[i])+" ");
			// System.out.print((b[i]& 0xFF)+" ");
		}
		return b;
	}

	/**
	 * 转换字节数组为int类型
	 * 
	 * @param b
	 * @return int
	 */
	public static int byte2Int(byte[] b) {

		int intValue = 0;
		for (int i = 0; i < b.length; i++) {
			intValue += (b[i] & 0xFF) << (8 * (3 - i));
			// System.out.print(Integer.toBinaryString(intValue)+" ");
		}
		return intValue;
	}

	/**
	 * bytes转换成十六进制字符串
	 * 
	 * @param b
	 * @return String
	 */
	public static String byte2HexStr(byte[] b) {

		if (b == null || b.length == 0)
			return null;

		final StringBuilder hexStr = new StringBuilder();
		for (int i = 0; i < b.length; i++) {
			if ((b[i] & 0xff) < 0x10)// 0~F前面补零
				hexStr.append("0");
			hexStr.append(Integer.toHexString(b[i] & 0xff));
		}
		return hexStr.toString().toLowerCase();

	}

	/**
	 *
	 * @param hexStr
	 * @return byte[]
	 */
	public static byte[] hexStr2Byte(String hexStr) {

		if (CommonUtil.isEmpty(hexStr))
			return null;
		if (hexStr.length() % 2 != 0)
			return null;
		hexStr = hexStr.toLowerCase();
		final byte[] b = new byte[hexStr.length() / 2];
		int k = 0;
		for (int i = 0; i < b.length; i++) {// 因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
			byte high = (byte) (Character.digit(hexStr.charAt(k), 16) & 0xff);
			byte low = (byte) (Character.digit(hexStr.charAt(k + 1), 16) & 0xff);
			b[i] = (byte) (high << 4 | low);
			k += 2;
		}
		return b;
	}

	/**
	 * 转置逆序数组元素,结合泛型
	 * 
	 * @param array
	 * @return 倒序后的数组
	 */
	public static <T> T[] reverseArray(T[] array) {

		if (array == null)
			return null;

		for (int i = 0, j = array.length - 1; (array.length - 2) / 2 < j; i++, j--) {
			T temp = array[j];
			array[j] = array[i];
			array[i] = temp;
		}

		return array;
	}

	/**
	 * 按顺序合并数组元素，结合泛型
	 * 
	 * @param arrays
	 * @return 指定类型的合并后的数组
	 */
	public static <T> T[] joinArray(T[]... arrays) {

		int newLength = 0;
		Class<?> clazz = null;
		if (arrays != null && arrays.length > 0) {
			for (T[] array : arrays) {
				if (array != null && array.length > 0) {
					newLength += array.length;
					if (clazz == null)
						clazz = array.getClass();
				}

			}
		}

		T[] dest = null;
		if (clazz != null)
			dest = (T[]) Array.newInstance(clazz.getComponentType(), newLength);

		if (newLength > 0) {
			int index = 0;
			for (T[] ts : arrays) {
				if (ts != null && ts.length > 0) {
					System.arraycopy(ts, 0, dest, index, ts.length);
					index += ts.length;
				}
			}
		}
		return dest;
	}

	/**
	 * 转置逆序数组元素，用于高低位字节转置，Java和C++中int类型存放的字节顺序相反
	 * 
	 * @param origin
	 * @return 倒序byte数组
	 */
	public static byte[] reverse(byte[] origin) {

		byte[] target = new byte[origin.length];
		int j = 0;
		for (int i = origin.length - 1; i >= 0; i--) {
			target[j++] = origin[i];
		}

		return target;
	}

	/**
	 * 合并两个byte数组
	 * 
	 * @param a
	 * @param b
	 * @return byte[]
	 */
	public static byte[] mergeByteArray(byte[] a, byte[] b) {

		if (a == null || b == null)
			return null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			bos.write(a);
			bos.write(b);
		} catch (IOException e) {
			;
		}
		return bos.toByteArray();
	}

	/**
	 * 合并对象数组
	 * 
	 * @param arrayA
	 *            对象数组
	 * @param arrayB
	 *            对象数组
	 * @return 合并后的数组
	 */
	public static Object[] arrayMerge(Object[] arrayA, Object[] arrayB) {

		if (arrayA == null)
			return arrayB;
		if (arrayB == null)
			return arrayA;
		Object[] merge = new Object[arrayA.length + arrayB.length];
		System.arraycopy(arrayA, 0, merge, 0, arrayA.length);
		System.arraycopy(arrayB, 0, merge, arrayA.length, arrayB.length);
		return merge;
	}

	/**
	 * 替换空白 fill Place holder
	 * 
	 * @param str
	 * @param params
	 * @return 替换空白后的字符串
	 */
	public static String fillPlaceholder(String str, String... params) {

		if (CommonUtil.isEmpty(str))
			return str;

		if (isEmptyArray(params)) {
			return str.replaceAll("\\{\\s*[\\d]+\\s*\\}", "");
		}

		for (int i = 0; i < params.length; i++) {
			str = str.replaceFirst("\\{\\s*" + i + "\\s*\\}", params[i]);
		}
		// params长度小于占位符个数的情况，剩余替换为空白
		str = str.replaceAll("\\{\\s*[\\d]+\\s*\\}", "");

		return str;
	}

	/**
	 * 字符串分割替换
	 * 
	 * @param str
	 * @param delim
	 * @param join
	 * @return 符合替换后的字符串
	 */
	public static String stringTokenizerReplace(String str, String delim, String join) {

		if (CommonUtil.isEmpty(str) || delim == null)
			return str;

		StringBuffer textContent = new StringBuffer();
		StringTokenizer tokenizer = new StringTokenizer(str, delim);

		String joinSign = join == null ? " " : join;

		while (tokenizer.hasMoreTokens()) {
			String temp = tokenizer.nextToken();
			textContent.append(temp);

			if (tokenizer.hasMoreTokens()) {
				textContent.append(joinSign); // separator
			}
		}

		return textContent.toString();
	}
}
