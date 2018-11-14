package cn.com.ut.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

/**
 * 
 * @author wangpeng1
 * @since 2018年10月23日
 */
// indexName索引名称 可以理解为数据库名 必须为小写
// 不然会报org.elasticsearch.indices.InvalidIndexNameException异常
// type类型 可以理解为表名
// @Document(indexName = "demo", type = "goods", shards = 1, replicas = 0,
// refreshInterval = "-1")
@Data
@Document(indexName = "demo", type = "goods")
public class DemoGoods {

	@Id
	@JSONField(name = "id")
	private String id;

	@Field(index = FieldIndex.analyzed, type = FieldType.String, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	@JSONField(name = "goods_name")
	private String goodsName;

	@Field(index = FieldIndex.analyzed, type = FieldType.String, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	@JSONField(name = "goods_desc")
	private String goodsDesc;

	@Field(index = FieldIndex.not_analyzed, type = FieldType.Double)
	@JSONField(name = "goods_price")
	private Double goodsPrice;

	@Field(index = FieldIndex.not_analyzed, type = FieldType.String)
	@JSONField(name = "shop_id")
	private String shopId;
}
