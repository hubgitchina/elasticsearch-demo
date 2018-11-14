package cn.com.ut.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.hibernate.service.spi.ServiceException;
import org.springframework.util.LinkedCaseInsensitiveMap;

/**
 * 集合工具类
 * 
 * @author wuxiaohua
 * @since 2013-12-22下午2:17:45
 */
public class CollectionUtil {

	/**
	 * 添加数组到集合
	 * 
	 * @param array
	 * @param collection
	 */
	public static <T> void addArrayToCollection(T[] array, Collection<T> collection) {

		if (array != null) {
			for (T t : array) {
				collection.add(t);
			}
		}
	}

	/**
	 * 清理集合
	 * 
	 * @param collection
	 */
	public static void clearCollection(Collection<?> collection) {

		if (collection != null && !collection.isEmpty()) {
			collection.clear();
			collection = null;
		}

	}

	/**
	 * 清理集合 {@link #clearCollection(Collection)}
	 * 
	 * @param collections
	 */
	public static void clearCollection(Collection<?>... collections) {

		if (collections == null || collections.length == 0)
			return;
		for (Collection<?> collection : collections)
			clearCollection(collection);

	}

	/**
	 * 清理map集合
	 * 
	 * @param map
	 */
	public static void clearMap(Map<?, ?> map) {

		if (map != null && !map.isEmpty()) {
			map.clear();
			map = null;
		}

	}

	/**
	 * 清理map集合 {@link #clearMap(Map)}
	 * 
	 * @param maps
	 */
	public static void clearMap(Map<?, ?>... maps) {

		if (maps == null || maps.length == 0)
			return;
		for (Map<?, ?> map : maps)
			clearMap(map);

	}

	/**
	 * 判断集合是否空的
	 * 
	 * @param collection
	 * @return 集合是否空的
	 */
	public static boolean isEmptyCollection(Collection<?> collection) {

		if (collection == null || collection.isEmpty())
			return true;
		return false;
	}

	/**
	 * 判断map集合是否空的
	 * 
	 * @param map
	 * @return boolean
	 */
	public static boolean isEmptyMap(Map<?, ?> map) {

		if (map == null || map.isEmpty())
			return true;
		return false;
	}

	/**
	 * 将字符串分割，以列表形式返回
	 * 
	 * @param str
	 * @param regex
	 * @param trim
	 * @return List<String> or null
	 */
	public static List<String> splitStr2List(String str, String regex, boolean trim) {

		if (CommonUtil.isEmpty(str) || regex == null)
			return null;

		String[] array = str.split(regex);

		List<String> list = null;
		if (array != null) {
			list = new ArrayList<>();
			for (String s : array) {
				if (trim) {
					list.add(s.trim());
				} else {
					list.add(s);
				}
			}
		}

		return list;
	}

	/**
	 * split string to List {@link #splitStr2List(String, String, boolean)}
	 * 
	 * @param str
	 * @param regex
	 * @return List<String>
	 */
	public static List<String> splitStr2List(String str, String regex) {

		return splitStr2List(str, regex, false);
	}

	/**
	 * 将字符串用指定的字符分隔后，以ArrayList列表形式返回
	 * 
	 * @param str
	 * @param regex
	 * @return List<Object>
	 */
	public static List<Object> splitStr2ObjectList(String str, String regex) {

		if (CommonUtil.isEmpty(str) || regex == null)
			return null;

		String[] array = str.split(regex);

		List<Object> list = null;
		if (array != null) {
			list = new ArrayList<Object>();
			for (String s : array) {
				list.add(s);
			}
		}

		return list;
	}

	/**
	 * list to map eg. {{key:value},{key:value}} => {value,{map}}
	 * 
	 * @param maps
	 * @param keyName
	 * @return map
	 */
	public static Map<String, Map<String, Object>> listToMap(Collection<Map<String, Object>> maps,
			String keyName) {

		Map<String, Map<String, Object>> map = new LinkedHashMap<String, Map<String, Object>>();

		if (maps != null) {
			String keyValue = null;
			for (Map<String, Object> temp : maps) {
				keyValue = (String) temp.get(keyName);
				if (keyValue != null)
					map.put(keyValue, temp);
			}
		}

		return map;
	}

	/**
	 * list to mapList 根据keyname分组
	 * 
	 * @param maps
	 * @param keyName
	 * @return map
	 */
	public static Map<String, Collection<Map<String, Object>>> listToMapList(
			Collection<Map<String, Object>> maps, String keyName) {

		Map<String, Collection<Map<String, Object>>> map = new HashMap<>();

		if (maps != null) {
			String keyValue = null;
			for (Map<String, Object> temp : maps) {
				keyValue = (String) temp.get(keyName);
				if (CommonUtil.isNotEmpty(keyValue)) {
					if (map.containsKey(keyValue)) {
						map.get(keyValue).add(temp);
					} else {
						Collection<Map<String, Object>> listTemp = new ArrayList<>();
						listTemp.add(temp);
						map.put(keyValue, listTemp);
					}
				}
			}
		}

		return map;
	}

	/**
	 * array to Collection
	 * 
	 * @param array
	 * @param set
	 * @return 包含指定对象的集合
	 */
	public static <T> Collection<T> arrayToCollection(T[] array, Collection<T> set) {

		if (array == null || set == null)
			return null;

		for (T t : array)
			set.add(t);

		return set;
	}

	/**
	 * copyMap
	 * 
	 * @param from
	 * @param to
	 */
	public static <T extends Object> void copyMap(Map<String, T> from, Map<String, Object> to) {

		copyMap(from, to, null);
	}

	/**
	 * 复制源Map中的key在指定数组范围中的元素到目标Map
	 * 
	 * @param from
	 *            源Map，类型为Map<Stirng, String>或Map<Stirng, Object>
	 * @param to
	 *            目标Map，类型为Map<Stirng, Object>
	 * @param names
	 *            key范围数组 [{name:oldKey@newKey}]
	 */
	public static <T extends Object> void copyMap(Map<String, T> from, Map<String, Object> to,
			String[] names) {

		if (from == null || to == null)
			return;

		if (names == null) {

			Set<Entry<String, T>> set = from.entrySet();
			Iterator<Entry<String, T>> iterator = set.iterator();
			Entry<String, T> entry = null;
			while (iterator.hasNext()) {
				entry = iterator.next();
				to.put(entry.getKey(), entry.getValue());
			}
		} else {
			for (String name : names) {
				if (name.indexOf("@") == -1) {
					to.put(name, from.get(name));
				} else {
					String[] array = name.split("@");
					to.put(array[1], from.get(array[0]));
				}
			}
		}

	}

	/**
	 * 替换Map中的key
	 * 
	 * @param from
	 * @param names
	 *            [{name:oldKey@newKey}]
	 */
	public static <T extends Object> void replaceMapKey(Map<String, T> from, String[] names) {

		if (from == null || names == null)
			return;

		for (String name : names) {
			if (name.indexOf("@") != -1) {
				String[] array = name.split("@");
				if (array.length == 2) {
					from.put(array[1], from.get(array[0]));
					from.remove(array[0]);
				}
			}
		}
	}

	/**
	 * 
	 * 将指定的map中的key换成大写或小写
	 * 
	 * @param from
	 * @param isUpper
	 *            是否为大写
	 * @throws ServiceException
	 *             前提必须map中同时存在仅大小写不同的两个key，否则大写key转换成小写key后重复键抛出异常
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Object> void replaceMapKey(Map<String, T> from, boolean isUpper)
			throws RuntimeException {

		if (CollectionUtil.isEmptyMap(from))
			return;

		Class<?> classType = from.getClass();
		Map<String, T> copyMap = null;
		try {
			copyMap = (Map<String, T>) classType.newInstance();
			copyMap.putAll(from);
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e.getCause());
		}

		from.clear();
		if (isUpper) {
			for (Map.Entry<String, T> entry : copyMap.entrySet()) {
				String key = entry.getKey();
				if (from.containsKey(key.toUpperCase())) {
					throw new RuntimeException("map中同时存在仅大小写不同的两个key，重复键" + key);
				}
				from.put(key.toUpperCase(), entry.getValue());
			}
		} else {
			for (Map.Entry<String, T> entry : copyMap.entrySet()) {
				String key = entry.getKey();
				if (from.containsKey(key.toLowerCase())) {
					throw new RuntimeException("map中同时存在仅大小写不同的两个key，重复键" + key);
				}
				from.put(key.toLowerCase(), entry.getValue());
			}
		}
	}

	/**
	 * 将指定的一组map中的key换成大写或小写
	 * 
	 * @param froms
	 * @param isUpper
	 */
	public static <T extends Object> void replaceMapsKey(Collection<Map<String, T>> froms,
			boolean isUpper) {

		if (froms == null)
			return;
		for (Map<String, T> from : froms) {
			replaceMapKey(from, isUpper);
		}
	}

	/**
	 * 将指定的集合中指定key的value添加到另一个集合返回
	 * 
	 * @param listMany
	 * @param key
	 * @return List<T>
	 */
	public static <T extends Object> List<T> listManyToOne(List<Map<String, Object>> listMany,
			String key) {

		List<T> listOne = new ArrayList<T>();
		if (listMany != null && !listMany.isEmpty()) {
			for (Map<String, Object> m : listMany) {
				listOne.add((T) m.get(key));
			}
		}
		return listOne;
	}

	/**
	 * 将指定的集合中指定key的value添加到另一个集合返回
	 * 
	 * @param listMany
	 * @param fields
	 * @return
	 */
	public static <T extends Object> List<T> listManyToListOne(List<Map<String, Object>> listMany,
			String[] fields) {

		Set<T> listOne = new LinkedHashSet<>();
		if (listMany != null) {
			for (Map<String, Object> m : listMany) {
				for (String field : fields) {
					Object o = m.get(field);
					if (o != null)
						listOne.add((T) o);
				}
			}
		}
		return new ArrayList<>(listOne);
	}

	/**
	 * 给集合指定标识字段追加名字字段，例如用户名、店铺名等
	 * 
	 * @param idFieldVos
	 * @param idFields
	 * @param nameFields
	 * @param nameFieldVos
	 * @param idField
	 * @param nameField
	 */
	public static void findFieldNamesThenAppend(List<Map<String, Object>> idFieldVos,
			String[] idFields, String[] nameFields, Collection<Map<String, Object>> nameFieldVos,
			String idField, String nameField) {

		List<Object> ids = new ArrayList<>();
		if (idFieldVos == null)
			return;

		Map<String, Map<String, Object>> cache = CollectionUtil.listToMap(nameFieldVos, idField);

		for (Map<String, Object> idFieldVo : idFieldVos) {
			for (int i = 0; i < idFields.length; i++) {
				if (idFieldVo.get(idFields[i]) != null) {
					Map<String, Object> nameFieldVo = cache.get(idFieldVo.get(idFields[i]));
					idFieldVo.put(nameFields[i],
							nameFieldVo == null ? "" : nameFieldVo.get(nameField));
				} else {
					idFieldVo.put(nameFields[i], "");
				}
			}
		}
	}

	/**
	 * copyMap
	 * 
	 * @param from
	 * @param to
	 * @param isUpper
	 *            if isUpper is true,the value converted to uppercase and then
	 *            copy
	 */
	public static <T extends Object> void copyMap(Map<String, T> from, Map<String, Object> to,
			boolean isUpper) {

		if (isUpper) {
			if (from == null || to == null)
				return;

			Set<Entry<String, T>> set = from.entrySet();
			Iterator<Entry<String, T>> iterator = set.iterator();
			Entry<String, T> entry = null;
			while (iterator.hasNext()) {
				entry = iterator.next();
				to.put(entry.getKey().toUpperCase(), entry.getValue());
			}
		} else {
			copyMap(from, to);
		}

	}

	/**
	 * 给<code>Map</code>集合添加一组元素
	 * 
	 * @param vo
	 *            集合
	 * @param keys
	 *            一组键
	 * @param values
	 *            一组值
	 */
	public static <T extends Object> void mapPopulated(Map<String, T> vo, String[] keys,
			T[] values) {

		if (keys.length > values.length)
			throw new IllegalArgumentException();
		for (int i = 0; i < keys.length; i++) {
			vo.put(keys[i], values[i]);
		}
	}

	/**
	 * 克隆一个List
	 * 
	 * @param from
	 * @return List<T>
	 */
	public static <T extends Object> List<T> listClone(List<T> from) {

		List<T> to = new ArrayList<T>();
		for (T t : from) {
			to.add(t);
		}
		return to;
	}

	/**
	 * 比较两个List中的元素
	 * 
	 * @param a
	 * @param b
	 * @return a和b都包含的,仅a包含的,仅b包含的map集合
	 */
	public static <T extends Object> Map<String, List<T>> listCompared(List<T> a, List<T> b) {

		List<T> c = listClone(a);

		Map<String, List<T>> map = new HashMap<String, List<T>>();

		c.retainAll(b);
		a.removeAll(c);
		b.removeAll(c);

		// 两者都包含的元素
		map.put("AB", c);
		// 仅前者包含的元素
		map.put("A", a);
		// 仅后者包含的元素
		map.put("B", b);

		return map;

	}

	public static <T extends Object> List<T> removeDuplicate(List<T> arlList) {

		if (!CollectionUtil.isEmptyCollection(arlList)) {
			Set<T> h = new HashSet<T>(arlList);
			arlList.clear();
			arlList.addAll(h);
			return arlList;
		}
		return null;
	}

	public static Map<String, Object> listToMap(Collection<Map<String, Object>> nextRules,
			String keyName, String valueName) {

		Map<String, Object> map = new HashMap<String, Object>();
		if (!CollectionUtil.isEmptyCollection(nextRules)) {
			for (Map<String, Object> rule : nextRules) {
				map.put((String) rule.get(keyName), rule.get(valueName));
			}
		}
		return map;
	}

	public static List<Map<String, Object>> replaceListMapKey(List<Map<String, Object>> list,
			String[] names) {

		if (!CollectionUtil.isEmptyCollection(list)) {
			for (Map<String, Object> temp : list) {
				replaceMapKey(temp, names);
			}
			return list;
		}
		return null;
	}

	/**
	 * 通过Set对一组Collection进行去重合并
	 * 
	 * @param listArray
	 * @return
	 */
	@SafeVarargs
	public static <T> Collection<T> removeDuplicate(Collection<T>... listArray) {

		if (listArray == null)
			return null;

		Set<T> set = new HashSet<T>();
		for (Collection<T> list : listArray) {
			if (!CollectionUtil.isEmptyCollection(list))
				set.addAll(list);
		}

		return set;
	}

	public static Object[] toArrayByKey(Collection<Map<String, Object>> collection, String key) {

		if (collection == null || collection.isEmpty())
			return null;
		List<Object> tList = new ArrayList<>();
		for (Map<String, Object> from : collection) {
			tList.add(from.get(key));
		}
		return tList.toArray();
	}

	/**
	 * 根据key查找集合中的对应的value
	 * 
	 * @param itemList
	 * @param key
	 * @return
	 */
	public static List<Object> findValue(List<Map<String, Object>> itemList, String key) {

		if (isEmptyCollection(itemList) || CommonUtil.isEmpty(key)) {
			return null;
		}
		List<Object> resultSet = new ArrayList<>();
		for (Map<String, Object> temp : itemList) {
			Object value = temp.get(key);
			resultSet.add(value);
		}
		return resultSet;
	}

	/**
	 * 根据key查找集合中的对应的value
	 * 
	 * @param itemList
	 * @param key
	 * @return
	 */
	public static Set<Object> findValueToSet(List<Map<String, Object>> itemList, String key) {

		if (isEmptyCollection(itemList) || CommonUtil.isEmpty(key)) {
			return null;
		}
		Set<Object> resultSet = new HashSet<>();
		for (Map<String, Object> temp : itemList) {
			Object value = temp.get(key);
			resultSet.add(value);
		}
		return resultSet;
	}

	public static Object[] StringToObject(List<Object> lst, String[] par) {

		if (par == null || par.length < 1) {
			return null;
		}
		if (isEmptyCollection(lst)) {
			lst = new ArrayList<>();
		}
		for (String t : par) {
			lst.add(t);
		}
		return lst.toArray();
	}

	/**
	 * 合并指定键对应的值相同的行，并转为Map。eg: [{a1,b1,c1,d1},{a1,b2,c2,d2}] =>
	 * a1:[{b1,c1,d1},{b2,c2,d2}]
	 * 
	 * @param fromList
	 * @param key
	 *            a1为key对应的值
	 */
	public static Map<String, List<Map<String, Object>>> combineListToMap(
			List<Map<String, Object>> fromList, String key) {

		Map<String, List<Map<String, Object>>> toMap = new HashMap<>();
		if (CollectionUtil.isEmptyCollection(fromList)) {
			return toMap;
		}
		for (Map<String, Object> fromMap : fromList) {
			if (fromMap.containsKey(key)) {
				String value = (String) fromMap.get(key);
				List<Map<String, Object>> listIntoMap = new ArrayList<>();
				if (toMap.containsKey(value)) {
					listIntoMap = toMap.get(value);
					fromMap.remove(key);
					listIntoMap.add(fromMap);
					toMap.remove(value);
					toMap.put(value, listIntoMap);
				} else {
					listIntoMap.clear();
					fromMap.remove(key);
					listIntoMap.add(fromMap);
					toMap.put(value, listIntoMap);
				}
			}
		}
		return toMap;
	}

	/**
	 * 检查是否缺少必要参数或必要参数为空（若缺少则抛出验证异常并提示缺少哪些参数）
	 * 
	 * @see cn.com.ut.core.common.util.validator.ValidatorUtil#validateMapContainsKey
	 * @param map
	 *            被检查的map
	 * @param keys
	 *            必要参数 存在并不为空
	 * @return 返回缺少的参数，不缺参数返回null
	 */
	public static List<String> checkMapContainsKey(Map<String, ? extends Object> map,
			String... keys) {

		// 如果keys参数为空，则验证map集合中的每一个元素
		if (ArrayUtil.isEmptyArray(keys)) {
			Set<String> set = map.keySet();
			keys = set.toArray(new String[set.size()]);
		}

		boolean isMiss = false;
		List<String> missKeys = new ArrayList<>();
		for (String key : keys) {
			if (map.containsKey(key)) {
				Object value = map.get(key);
				if (value == null) {
					isMiss = true;
					missKeys.add(key);
				} else if ("".equals(value.toString().trim())) {
					isMiss = true;
					missKeys.add(key);
				}
			} else {
				isMiss = true;
				missKeys.add(key);
			}
		}
		return isMiss ? missKeys : null;
	}

	/**
	 * 检查是否缺少必要参数（若缺少则抛出验证异常并提示缺少哪些参数）
	 * 
	 * @see cn.com.ut.core.common.util.validator.ValidatorUtil#validateMapContainsKey
	 * @param map
	 *            被检查的map
	 * @param keys
	 *            必要参数 存在即可，可以为空
	 * @return 返回缺少的参数，不缺参数返回null
	 */
	public static List<String> checkMapContainsKeyIgnoreNull(Map<String, Object> map,
			String... keys) {

		boolean isMiss = false;
		List<String> missKeys = new ArrayList<>();
		for (String key : keys) {
			if (map.containsKey(key)) {
			} else {
				isMiss = true;
				missKeys.add(key);
			}
		}
		return isMiss ? missKeys : null;
	}

	/**
	 * Map to LinkedCaseInsensitiveMap,其key不区分大小写
	 * 
	 * @param vo
	 * @return
	 */
	public static LinkedCaseInsensitiveMap<Object> toInsensitiveMap(Map<String, Object> vo) {

		LinkedCaseInsensitiveMap<Object> newVo = new LinkedCaseInsensitiveMap<Object>();
		newVo.putAll(vo);
		return newVo;
	}

	/**
	 * 判断两个字符串数组是否相同
	 * 
	 * @param array1
	 * @param array2
	 * @return
	 */
	public static boolean compareStringArray(String[] array1, String[] array2) {

		if ((array1 == null || array1.length < 1) && (array2 == null || array2.length < 1)) {
			return true;
		}
		if (array1.length != array2.length) {
			return false;
		}
		for (String tempString1 : array1) {
			boolean flag = true;
			for (String tempStr2 : array2) {
				if (tempStr2.equals(tempString1)) {
					flag = false;
					break;
				}
			}
			if (flag) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 创建树形集合
	 * 
	 * @param treeList
	 * @param parentId
	 *            父节点
	 * @param child
	 *            子节点
	 * @param nodeName
	 *            名字
	 * @return
	 */
	public static List<Map<String, Object>> createTreeList(List<Map<String, Object>> treeList,
			String parentId, String child, String nodeName) {

		if (isEmptyCollection(treeList)) {
			return treeList;
		}
		List<Map<String, Object>> result = new ArrayList<>();
		Map<String, Map<String, Object>> allMapTemp = listToMap(treeList, child);
		for (Map<String, Object> tempMap : treeList) {
			String parVal = (String) tempMap.get(parentId);
			if (CommonUtil.isEmpty(parVal)) {
				result.add(tempMap);
			} else {
				Map<String, Object> parMap = allMapTemp.get(parVal);
				if (isEmptyMap(parMap)) {
					result.add(tempMap);
					continue;
				}
				List<Map<String, Object>> tempList = (List<Map<String, Object>>) parMap
						.get(nodeName);
				if (isEmptyCollection(tempList)) {
					tempList = new ArrayList<>();
					parMap.put(nodeName, tempList);
				}
				tempList.add(tempMap);

			}
		}
		return result;
	}

	public static List<Map<String, Object>> findList(List<Map<String, Object>> ogList, String key,
			String value) {

		List<Map<String, Object>> resultList = new ArrayList<>();
		if (isEmptyCollection(ogList)) {
			return resultList;
		}
		for (Map<String, Object> tempMap : ogList) {
			if (value.equals(tempMap.get(key))) {
				resultList.add(tempMap);
			}
		}
		return resultList;
	}

	public static Map<String, Object> findMap(List<Map<String, Object>> ogList, String key,
			String value) {

		if (isEmptyCollection(ogList)) {
			return null;
		}
		for (Map<String, Object> tempMap : ogList) {
			if (value.equals(tempMap.get(key))) {
				return tempMap;
			}
		}
		return null;
	}

	/**
	 * 循环List中Map元素，并移除Map中指定的键值对
	 * 
	 * @param maps
	 * @param keys
	 */
	public static <K extends Object, V extends Object> void removeKeysFromMaps(List<Map<K, V>> maps,
			Object[] keys) {

		if (!CollectionUtil.isEmptyCollection(maps) && !ArrayUtil.isEmptyArray(keys)) {
			for (Map<K, V> map : maps) {

				for (Object key : keys) {
					map.remove(key);
				}
			}
		}
	}

	/**
	 * 移除Map中指定的键值对
	 * 
	 * @param map
	 * @param keys
	 */
	public static <K extends Object, V extends Object> void removeKeysFromMap(Map<K, V> map,
			Object[] keys) {

		if (!CollectionUtil.isEmptyMap(map) && !ArrayUtil.isEmptyArray(keys)) {

			for (Object key : keys) {
				map.remove(key);
			}
		}
	}
}
