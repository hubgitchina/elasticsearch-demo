package cn.com.ut.service;

import java.util.List;
import java.util.Map;

import cn.com.ut.pojo.GoodsIndexQueryVo;
import cn.com.ut.pojo.GoodsLocationQueryVo;
import cn.com.ut.pojo.PolygonQueryVo;
import cn.com.ut.pojo.TwoPointDistanceQueryVo;
import cn.com.ut.util.PageInfo;

/**
 * @Description: 商品管理业务层接口
 * @Author wangpeng1
 * @Date 2018/11/5 9:50
 */
public interface GoodsService {

	/**
	 * 商品数据导入elasticsearch
	 * 
	 * @param index
	 * @param type
	 * @return
	 */
	List<Map<String, Object>> importGoodsData(String index, String type);

	/**
	 * 首页查询
	 * 
	 * @param goodsIndexQueryVo
	 * @return
	 */
	PageInfo queryIndex(GoodsIndexQueryVo goodsIndexQueryVo);

	/**
	 * 根据搜索范围和坐标位置进行分页查询
	 * 
	 * @param goodsLocationQueryVo
	 * @return
	 */
	PageInfo queryPageByLocation(GoodsLocationQueryVo goodsLocationQueryVo);

	/**
	 * 根据坐标和搜索范围进行范围搜索，返回所有符合条件的数据
	 * 
	 * @param goodsLocationQueryVo
	 * @return
	 */
	List<Map<String, Object>> queryListByLocation(GoodsLocationQueryVo goodsLocationQueryVo);

	/**
	 * 多边形查询
	 * 
	 * @param polygonQueryVo
	 * @return
	 */
	List<Map<String, Object>> queryListByPolygon(PolygonQueryVo polygonQueryVo);

	/**
	 * 计算两点距离
	 * 
	 * @param twoPointDistanceQueryVo
	 * @return
	 */
	double getPointToPoint(TwoPointDistanceQueryVo twoPointDistanceQueryVo);
}
