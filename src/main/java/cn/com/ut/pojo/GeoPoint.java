package cn.com.ut.pojo;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class GeoPoint extends RestInfo {

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
}