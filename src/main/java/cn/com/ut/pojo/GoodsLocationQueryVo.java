package cn.com.ut.pojo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class GoodsLocationQueryVo extends RestInfo {

	/**
	 * 当前页码数
	 */
	@NotNull(message = "当前页码数不能为空")
	@Min(value = 1, message = "当前页码数必须大于或等于1")
	private Integer pageno;

	/**
	 * 每页的记录数
	 */
	@NotNull(message = "每页的记录数不能为空")
	@Min(value = 0, message = "每页的记录数必须大于或等于0")
	private Integer pagesize;

	/**
	 * 商品名称或商品广告词
	 */
	private String goodsName;

	/**
	 * 目标位置经度
	 */
	@NotNull(message = "目标位置经度不能为空")
	private double longitude;

	/**
	 * 目标位置纬度
	 */
	@NotNull(message = "目标位置纬度不能为空")
	private double latitude;

	/**
	 * 目标位置搜索范围
	 */
	@NotNull(message = "搜索范围不能为空")
	@Min(value = 0, message = "搜索范围不能小于0")
	private double distance;

	/**
	 * 排序字段
	 */
	private String order;

	/**
	 * 排序规则，升序或降序
	 */
	private String sort;

}