package cn.com.ut.util;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class JPQLQueryUtil {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * 根据分页对象，查询jpql语句，参数查询，返回分页数据
	 *
	 * @param pageable
	 * @param queryJpql
	 * @param params
	 * @param <T>
	 * @return
	 */
	public <T> Page<T> queryPage(Pageable pageable, String queryJpql, Map<String, Object> params) {

		int fromIndex = queryJpql.toLowerCase().indexOf("from");
		SQLHelper countSql = SQLHelper.builder();
		countSql.append("select count(*)").append(queryJpql.substring(fromIndex));
		Query countQuery = entityManager.createQuery(countSql.toSQL(), Long.class);
		this.setParameters(countQuery, params);

		Long count;
		// 使用group
		// by时，count函数在没有记录时会返回NULL，getSingleResult会抛EntityNotFoundException异常
		try {
			count = (Long) countQuery.getSingleResult();
		} catch (NoResultException e) {
			count = 0L;
		}

		// 如果记录总数为0，则直接返回，减少一次查询，提升性能
		if (count == 0) {
			return new PageImpl<T>(Collections.EMPTY_LIST, pageable, count);
		} else {
			Query query = entityManager.createQuery(queryJpql);

			this.setParameters(query, params);

			// 实际查询返回分页对象
			query.setFirstResult(pageable.getOffset());
			query.setMaxResults(pageable.getPageSize());

			List<T> list = query.getResultList();

			return new PageImpl<T>(list, pageable, count);
		}
	}

	/**
	 * 根据分页对象，查询jpql语句，参数查询，返回分页数据
	 *
	 * @param queryJpql
	 * @param params
	 * @param <T>
	 * @return
	 */
	public <T> List<T> queryList(String queryJpql, Map<String, Object> params) {

		Query query = entityManager.createQuery(queryJpql);

		this.setParameters(query, params);

		return query.getResultList();
	}

	/**
	 * 根据jpql语句，参数查询，返回一条记录
	 *
	 * @param queryJpql
	 * @param params
	 * @param <T>
	 * @return
	 */
	public <T> T getOne(String queryJpql, Map<String, Object> params) {

		T t = null;

		Query query = entityManager.createQuery(queryJpql);

		this.setParameters(query, params);

		if (query.getResultList().size() > 0) {
			t = (T) query.getSingleResult();
		}
		return t;
	}

	/**
	 * 给jpql参数设置值
	 *
	 * @param query
	 * @param params
	 */
	private void setParameters(Query query, Map<String, Object> params) {

		for (Map.Entry<String, Object> entry : params.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
	}
}
