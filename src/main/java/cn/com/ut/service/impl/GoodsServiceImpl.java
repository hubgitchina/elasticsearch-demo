package cn.com.ut.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import cn.com.ut.dao.DsGoodsRepository;
import cn.com.ut.pojo.GoodsIndexQueryVo;
import cn.com.ut.service.GoodsService;
import cn.com.ut.util.CollectionUtil;
import cn.com.ut.util.CommonUtil;
import cn.com.ut.util.ElasticSearchUtil;
import cn.com.ut.util.JPQLQueryUtil;
import cn.com.ut.util.SQLHelper;

/**
 * @Description: 商品管理业务层接口实现类
 * @Author wangpeng1
 * @Date 2018/11/5 9:50
 */
@Service
@Transactional
public class GoodsServiceImpl extends JPQLQueryUtil implements GoodsService {

	@Autowired
	private DsGoodsRepository goodsRepository;

	@Override
	public List<Map<String, Object>> importGoodsData(String index, String type) {

		SQLHelper selectSql = SQLHelper.builder();
		selectSql.append(
				"select new map(g.id as goods_id,g.goodsName as goods_name,g.goodsDesc as goods_desc,g.storeId as store_id,s.storeName as store_name,g.imagePath as goods_image,g.goodsPrice as goods_price,g.goodsDiscount as goods_discount,g.goodsSalenum as goods_salenum,g.appId as app_id,g.isDel as is_del,g.goodsState as goods_state,s.storeState as store_state,g.goodsShelftime as goods_shelftime) from Goods g left join Store s on g.storeId = s.id");

		Map<String, Object> params = new HashMap<>();

		SQLHelper orderBySql = SQLHelper.builder();

		orderBySql.append("order by g.createTime desc");

		selectSql.append(orderBySql.toSQL());

		List<Map<String, Object>> goodsList = super.queryList(selectSql.toSQL(), params);

		if (!CollectionUtil.isEmptyCollection(goodsList)) {
			for (Map<String, Object> map : goodsList) {
				SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if (map.get("goods_shelftime") != null) {
					String fmDate = fm.format((Date) map.get("goods_shelftime"));
					map.put("goods_shelftime", fmDate);
				}
			}
			JSONArray goodsJsonArray = JSONArray.parseArray(JSON.toJSONString(goodsList));
			ElasticSearchUtil.bulkInsertData(index, type, goodsJsonArray);
		}

		return goodsList;
	}

	@Override
	public List<Map<String, Object>> queryIndex(GoodsIndexQueryVo goodsIndexQueryVo) {

		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must(QueryBuilders.termQuery("is_del", "N"));
		boolQueryBuilder.must(QueryBuilders.termQuery("app_id", goodsIndexQueryVo.getAppId()));
		boolQueryBuilder.must(QueryBuilders.termQuery("goods_state", "1"));
		boolQueryBuilder.must(QueryBuilders.termQuery("store_state", 1));

		// 设置商品名称查询条件
		if (CommonUtil.isNotEmpty(goodsIndexQueryVo.getGoodsName())) {
			boolQueryBuilder
					.must(QueryBuilders.matchQuery("goods_name", goodsIndexQueryVo.getGoodsName()));
		}

		// 设置排序字段和排序方式
		SortBuilder sortBuilder = null;

		if (CommonUtil.isNotEmpty(goodsIndexQueryVo.getOrder())) {
			if (CommonUtil.isEmpty(goodsIndexQueryVo.getSort())) {
				throw new RuntimeException("排序规则不能为空");
			}

			String orderField = goodsIndexQueryVo.getOrder();

			if ("desc".equals(goodsIndexQueryVo.getSort())) {
				sortBuilder = SortBuilders.fieldSort(orderField).order(SortOrder.DESC);
			} else if ("asc".equals(goodsIndexQueryVo.getSort())) {
				sortBuilder = SortBuilders.fieldSort(orderField).order(SortOrder.ASC);
			} else {
				throw new RuntimeException("排序方式有误");
			}
		}

		int pageno = goodsIndexQueryVo.getPageno();
		int pagesize = goodsIndexQueryVo.getPagesize();

		List<Map<String, Object>> goodsList = ElasticSearchUtil.boolQuery(boolQueryBuilder, pageno,
				pagesize, sortBuilder);

		return goodsList;
	}
}
