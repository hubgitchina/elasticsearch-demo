package cn.com.ut.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import cn.com.ut.dao.GoodsRepository;
import cn.com.ut.entity.Goods;

/**
 * 
 * @author wangpeng1
 * @since 2018年10月23日
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

	@Autowired
	private GoodsRepository goodsRepository;

	/**
	 * 添加
	 * 
	 * @return
	 */
	@GetMapping("/add")
	public String add(Goods goods) {

		goodsRepository.save(goods);
		return "success";
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable String id) {

		Goods goods = goodsRepository.queryGoodsById(id);
		if (goods != null) {
			goodsRepository.delete(goods);
		}
		return "success";
	}

	/**
	 * 删除所有数据
	 * 
	 * @return
	 */
	@GetMapping("/deleteAll")
	public String deleteAll() {

		goodsRepository.deleteAll();
		return "success";
	}

	/**
	 * 局部更新
	 * 
	 * @return
	 */
	@GetMapping("/update")
	public String update(Goods goods) {

		goodsRepository.save(goods);
		return "success";
	}

	/**
	 * 查询
	 * 
	 * @return
	 */
	@GetMapping("/query")
	public Goods query() {

		Goods goods = goodsRepository.queryGoodsById("1");
		String jsonString = JSON.toJSONString(goods);
		return goods;
	}
}
