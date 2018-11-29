package cn.com.ut.service;

import java.util.List;

import cn.com.ut.entity.MallApp;

/**
 * 测试Spring封装ES的API
 * 
 * @author wangpeng1
 * @since 2018年11月29日
 */
public interface MallAppService {

	List<MallApp> findAll();

}
