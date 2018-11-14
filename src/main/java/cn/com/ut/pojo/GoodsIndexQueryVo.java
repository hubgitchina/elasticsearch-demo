package cn.com.ut.pojo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GoodsIndexQueryVo extends RestInfo {

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
	 * 应用ID
	 */
	@NotBlank(message = "应用ID不能为空")
	private String appId;

	/**
	 * 商品名称或商品广告词
	 */
	private String goodsName;

	/**
	 * 商品最低价
	 */
	private String priceMin;

	/**
	 * 商品最高价
	 */
	private String priceMax;

	/**
	 * 类型ID
	 */
	private String typeId;

	/**
	 * 品牌ID
	 */
	private String brandId;

	/**
	 * 商品分类ID
	 */
	private String gcId;

	/**
	 * 排序字段
	 */
	private String order;

	/**
	 * 排序规则，升序或降序
	 */
	private String sort;

}