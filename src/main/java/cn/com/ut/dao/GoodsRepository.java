package cn.com.ut.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

import cn.com.ut.entity.Goods;

/**
 * 
 * @author wangpeng1
 * @since 2018年10月23日
 */
@Component
public interface GoodsRepository extends ElasticsearchRepository<Goods, String> {

	Goods queryGoodsById(String id);
}
