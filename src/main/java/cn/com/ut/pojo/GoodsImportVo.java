package cn.com.ut.pojo;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GoodsImportVo extends RestInfo {

	/**
	 * 应用ID
	 */
	@NotBlank(message = "应用ID不能为空")
	private String appId;

	/**
	 * 商品名称或商品广告词
	 */
	private String goodsName;

}