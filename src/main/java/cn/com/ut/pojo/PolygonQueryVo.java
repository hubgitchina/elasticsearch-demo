package cn.com.ut.pojo;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class PolygonQueryVo {

	@NotNull(message = "坐标位置不能为空")
	@Size(min = 1, message = "坐标位置不能为空")
	private List<GeoPoint> geoPoints;

}
