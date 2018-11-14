package cn.com.ut.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @Description: 字符串工具类
 * @Author wangpeng1
 * @Date 2018/9/5 9:33
 */
public class StringTool {

	private static Pattern linePattern = Pattern.compile("_(\\w)");

	/**
	 * 下划线转驼峰
	 * 
	 * @param str
	 * @return
	 */
	public static String lineToHump(String str) {

		str = str.toLowerCase();
		Matcher matcher = linePattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 设置分页查询orderBy条件
	 * 
	 * @param orderValue
	 * @param sortValue
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public static Pageable setPageOrderSort(String orderValue, String sortValue, int pageNo,
			int pageSize) {

		Pageable pageable;
		if (CommonUtil.isNotEmpty(orderValue)) {
			if (CommonUtil.isEmpty(sortValue)) {
				throw new RuntimeException("排序规则不能为空");
			}
			Sort.Direction direction = null;
			if (sortValue.toUpperCase().equals(SQLHelper.ASC)) {
				direction = Sort.Direction.ASC;
			}
			if (sortValue.toUpperCase().equals(SQLHelper.DESC)) {
				direction = Sort.Direction.DESC;
			}
			Sort sort = new Sort(direction, lineToHump(orderValue));
			pageable = new PageRequest(pageNo - 1, pageSize, sort);
		} else {
			pageable = new PageRequest(pageNo - 1, pageSize);
		}
		return pageable;
	}

	/**
	 * 驼峰转下划线
	 * 
	 * @param camel
	 * @return
	 */
	public static String Camel2Underline(String camel) {

		Pattern pattern = Pattern.compile("[A-Z]");
		Matcher matcher = pattern.matcher(camel);
		while (matcher.find()) {
			String w = matcher.group().trim();
			camel = camel.replace(w, "_" + w);
		}
		return camel.toUpperCase();
	}
}
