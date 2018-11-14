package cn.com.ut.service;

import java.util.List;
import java.util.Map;

import cn.com.ut.pojo.GoodsIndexQueryVo;

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
	List<Map<String, Object>> queryIndex(GoodsIndexQueryVo goodsIndexQueryVo);
}
