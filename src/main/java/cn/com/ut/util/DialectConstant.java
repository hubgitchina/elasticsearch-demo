package cn.com.ut.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * 数据库类型常量
 * 
 * @author wuxiaohua
 * @since 2017年1月10日 下午6:07:09
 */
@Component
public class DialectConstant implements InitializingBean {

	/**
	 * 数据库方言
	 */
	public static final String DATABASE_DIALECT_PROPERTIES = "database.dialect";

	/**
	 * 数据库类型：oracle
	 */
	public static final String ORACLE = "oracle";
	/**
	 * 数据库类型：mysql
	 */
	public static final String MYSQL = "mysql";
	/**
	 * 数据库类型：postgresql
	 */
	public static final String PGSQL = "postgresql";
	/**
	 * 数据库类型：sqlserver
	 */
	public static final String SQLSERVER = "sqlserver";
	/**
	 * 数据库类型：mongodb
	 */
	public static final String MONGO = "mongodb";
	/**
	 * 数据库类型：hbase
	 */
	public static final String HBASE = "hbase";

	/**
	 * 数据库方言
	 */
	private static String dialect;

	@Autowired
	private Environment env;

	public static String getDialect() {

		return dialect;
	}

	public static boolean isPgsql() {

		return DialectConstant.PGSQL.equals(getDialect());
	}

	public static boolean isMysql() {

		return DialectConstant.MYSQL.equals(getDialect());
	}

	@Override
	public void afterPropertiesSet() throws Exception {

		dialect = env.getProperty(DATABASE_DIALECT_PROPERTIES, DialectConstant.MYSQL);

	}
}
