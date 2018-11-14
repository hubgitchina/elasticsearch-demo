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
@Data
@Document(indexName = "demo", type = "shop")
public class Shop {

	@Id
	@JSONField(name = "id")
	private String id;

	@Field(index = FieldIndex.not_analyzed, type = FieldType.String)
	@JSONField(name = "shop_name")
	private String shopName;

	@Field(index = FieldIndex.not_analyzed, type = FieldType.String)
	@JSONField(name = "address")
	private String address;
}
