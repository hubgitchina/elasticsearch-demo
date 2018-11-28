package cn.com.ut.vo;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;

@Data
public class EsIndex {

	private String index;

	private String type;

	private String id;

	private JSONObject content;
}
