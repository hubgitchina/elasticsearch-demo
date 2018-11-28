package cn.com.ut.pojo;

import java.io.Serializable;

import lombok.Data;

@Data
public class GoodsQueryRespVo implements Serializable {

	private String store_id;

	private String goods_name;

	private String store_state;

	private String goods_price;

	private String goods_id;

	private String goods_desc;

	private String goods_salenum;

	private String goods_image;

	private String is_del;

	private String store_name;

	private String goods_discount;

	private String goods_state;

	private String location;

	private String app_id;

	private String goods_shelftime;

	private double distance;
}
