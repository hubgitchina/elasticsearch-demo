package cn.com.ut.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import cn.com.ut.entity.MallApp;

public interface MallAppRepository extends ElasticsearchRepository<MallApp, String> {

}
