package cn.com.ut.vo;

import com.alibaba.fastjson.JSONObject;

public class EsIndex {

	private String index;

	private String type;

	private String id;

	private JSONObject content;

	public String getIndex() {

		return index;
	}

	public void setIndex(String index) {

		this.index = index;
	}

	public String getType() {

		return type;
	}

	public void setType(String type) {

		this.type = type;
	}

	public String getId() {

		return id;
	}

	public void setId(String id) {

		this.id = id;
	}

	public JSONObject getContent() {

		return content;
	}

	public void setContent(JSONObject content) {

		this.content = content;
	}

}
