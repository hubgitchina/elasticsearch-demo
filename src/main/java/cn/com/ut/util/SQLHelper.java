package cn.com.ut.util;

/**
 * SQL构建辅助工具类
 * 
 * @author wuxiaohua
 * @since 2015-12-30
 */
public class SQLHelper {

	public static final String SELECT = "SELECT";
	public static final String UPDATE = "UPDATE";
	public static final String SET = "SET";
	public static final String INSERT_INTO = "INSERT INTO";
	public static final String DELETE_FROM = "DELETE FROM";
	public static final String VALUES = "VALUES";
	public static final String ORDER_BY = "ORDER BY";
	public static final String GROUP_BY = "GROUP BY";
	public static final String DESC = "DESC";
	public static final String ASC = "ASC";
	public static final String FROM = "FROM";
	public static final String WHERE = "WHERE";
	public static final String ON = "ON";
	public static final String AND = "AND";
	public static final String OR = "OR";
	public static final String EXISTS = "EXISTS";
	public static final String NOT_EXISTS = "NOT EXISTS";
	public static final String IN = "IN";
	public static final String NOT_IN = "NOT IN";
	public static final String INNER_JOIN = "INNER JOIN";
	public static final String LEFT_JOIN = "LEFT JOIN";
	public static final String RIGHT_JOIN = "RIGHT JOIN";
	public static final String IN_REPLACE = " {IN} ";
	public static final String EQ = "=";
	public static final String NE = "<>";
	public static final String GT = ">";
	public static final String LT = "<";
	public static final String LE = "<=";
	public static final String GE = ">=";
	public static final String BETWEEN = "BETWEEN";
	public static final String NULL = "NULL";
	public static final String IS_NULL = "IS NULL";
	public static final String IS_NOT_NULL = "IS NOT NULL";
	public static final String LIKE = "LIKE";
	public static final String NOT_LIKE = "NOT LIKE";
	public static final String TRUE = "TRUE";
	public static final String FALSE = "FALSE";
	public static final String LIMIT = "LIMIT";
	public static final String OFFSET = "OFFSET";
	public static final String UNION = "UNION";
	public static final String UNION_ALL = "UNION ALL";

	private StringBuilder sql = new StringBuilder();

	private SQLHelper() {

	}

	/**
	 * 构建不同数据库方言的like模糊匹配子句
	 * 
	 * @param whereName
	 * @return
	 */
	public static String rlike(String whereName) {

		StringBuilder sql = new StringBuilder();
		if (whereName != null)
			sql.append(" ").append(whereName).append(" ");

		if (DialectConstant.isMysql()) {
			sql.append(" LIKE ? '%' ");
		} else {
			sql.append(" LIKE ?||'%' ");
		}

		return sql.toString();
	}

	/**
	 * 构建不同数据库方言的like模糊匹配子句
	 * 
	 * @param whereName
	 * @return
	 */
	public static String llike(String whereName) {

		StringBuilder sql = new StringBuilder();
		if (whereName != null)
			sql.append(" ").append(whereName).append(" ");

		if (DialectConstant.isMysql()) {
			sql.append(" LIKE '%' ? ");
		} else {
			sql.append(" LIKE '%'||? ");
		}
		return sql.toString();
	}

	/**
	 * 构建不同数据库方言的like模糊匹配子句
	 * 
	 * @param whereName
	 * @return
	 */
	public static String like(String whereName, boolean upper) {

		StringBuilder sql = new StringBuilder();
		if (whereName != null) {
			if (upper) {
				sql.append(" UPPER(" + whereName + ") ");
			} else {
				sql.append(" " + whereName + " ");
			}

		}

		if (DialectConstant.isMysql()) {
			sql.append(" LIKE '%' ? '%' ");
		} else {
			sql.append(" LIKE '%'||?||'%' ");
		}
		return sql.toString();
	}

	public static String like(String whereName) {

		return like(whereName, true);
	}

	public static String rlike() {

		return rlike(null);
	}

	public static String llike() {

		return llike(null);
	}

	public static String like() {

		return like(null);
	}

	/**
	 * 在末尾追加sql片段
	 * 
	 * @param script
	 *            追加的sql片段
	 * @return this
	 */
	public SQLHelper append(String script) {

		sql.append(" ").append(script).append(" ");
		return this;
	}

	/**
	 * 创建实例
	 * 
	 * @return 实例
	 */
	public static SQLHelper builder() {

		return new SQLHelper();
	}

	/**
	 * toString
	 * 
	 * @return SQL字符串
	 */
	public String toSQL() {

		return sql.toString();
	}

	/**
	 * 清空sql
	 */
	public void clear() {

		sql.delete(0, sql.length());
	}

	@Override
	public String toString() {

		return toSQL();
	}

	/**
	 * 构建insert语句
	 * 
	 * @param table
	 *            表名称
	 * @param insertColumnArray
	 *            插入字段数组
	 * @return insert语句
	 */
	public static StringBuilder insertSQL(String table, String[] insertColumnArray) {

		StringBuilder sql = new StringBuilder();

		sql.append("INSERT INTO ").append(table).append(" (");
		sql.append(ArrayUtil.joinArrayElement(insertColumnArray, ","));
		sql.append(") VALUES (");
		sql.append(ArrayUtil.joinSameElement("?", insertColumnArray.length, ","));
		sql.append(")");

		return sql;
	}

	/**
	 * 构建select语句
	 * 
	 * @param table
	 *            表名称
	 * @param selectColumnArray
	 *            查询返回字段数组
	 * @param whereColumnArray
	 *            查询条件字段数组
	 * @param orderBy
	 *            查询排序字段拼接形式
	 * @return select语句
	 */
	public static StringBuilder selectSQL(String table, String[] selectColumnArray,
			String[] whereColumnArray, String orderBy) {

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT ").append(ArrayUtil.joinArrayElement(selectColumnArray, ","))
				.append(" FROM ").append(table).append(" ");
		if (whereColumnArray != null && whereColumnArray.length > 0) {
			sql.append(" WHERE ");
			sql.append(ArrayUtil.joinArrayElement(whereColumnArray, " AND ", null, "=?"));
		}
		if (orderBy != null) {
			sql.append(" ORDER BY ");
			sql.append(orderBy);
		}

		return sql;
	}

	/**
	 * 构建update语句
	 * 
	 * @param table
	 *            表名称
	 * @param updateColumnArray
	 *            更新字段数组
	 * @param whereColumnArray
	 *            查询条件字段数组
	 * @return update语句
	 */
	public static StringBuilder updateSQL(String table, String[] updateColumnArray,
			String[] whereColumnArray) {

		StringBuilder sql = new StringBuilder();

		sql.append("UPDATE ").append(table).append(" SET ");
		sql.append(ArrayUtil.joinArrayElement(updateColumnArray, ",", null, "=?"));
		if (whereColumnArray != null && whereColumnArray.length > 0) {
			sql.append(" WHERE ");
			sql.append(ArrayUtil.joinArrayElement(whereColumnArray, " AND ", null, "=?"));
		}

		return sql;
	}

	/**
	 * 构建delete语句
	 * 
	 * @param table
	 *            表名称
	 * @param whereColumnArray
	 *            查询条件字段数组
	 * @return delete语句
	 */
	public static StringBuilder deleteSQL(String table, String[] whereColumnArray) {

		StringBuilder sql = new StringBuilder();

		sql.append("DELETE FROM ").append(table);
		if (whereColumnArray != null && whereColumnArray.length > 0) {
			sql.append(" WHERE ");
			sql.append(ArrayUtil.joinArrayElement(whereColumnArray, " AND ", null, "=?"));
		}

		return sql;
	}

	public static void main(String[] args) {

	}
}
