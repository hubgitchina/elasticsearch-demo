package cn.com.ut.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

import cn.com.ut.entity.DemoGoods;

/**
 * 
 * @author wangpeng1
 * @since 2018年10月23日
 */
@Component
public interface DemoGoodsRepository extends ElasticsearchRepository<DemoGoods, String> {

	DemoGoods queryGoodsById(String id);
}
