package cn.com.ut.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.ut.dao.MallAppRepository;
import cn.com.ut.entity.MallApp;
import cn.com.ut.service.MallAppService;

/**
 * 测试Spring封装ES的API接口实现类
 * 
 * @author wangpeng1
 * @since 2018年11月29日
 */
@Service
@Transactional
public class MallAppServiceImpl implements MallAppService {

	@Autowired
	private MallAppRepository mallAppRepository;

	@Autowired
	private ElasticsearchTemplate est;

	@Override
	public List<MallApp> findAll() {

		NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder();
		QueryBuilder matchAllQb = QueryBuilders.matchAllQuery();
		searchQuery.withIndices("mall").withQuery(matchAllQb);
		long count = mallAppRepository.count();

		System.out.println("总数目：" + count);

		// 使用document映射对象DAO继承ElasticsearchRepository，
		// 查找所有时，会先计算总数目然后作为分页参数进行查询
		Iterable<MallApp> allList = mallAppRepository.findAll();
		Iterator<MallApp> iterator = allList.iterator();
		List<MallApp> list = new ArrayList<MallApp>();
		while (iterator.hasNext()) {
			list.add(iterator.next());
		}

		// 直接使用ElasticsearchTemplate封装的queryForList，
		// 本质还是调用的queryForPage，不传查询返回数量，默认只返回10条
		List<MallApp> list2 = est.queryForList(searchQuery.build(), MallApp.class);
		System.out.println("list2总数目：" + list2.size());

		return list2;
	}
}
