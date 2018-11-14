package cn.com.ut.vo;

import java.util.List;

public class EsIndexField {

	private String index;

	private String type;

	private List<EsField> fieldList;

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

	public List<EsField> getFieldList() {

		return fieldList;
	}

	public void setFieldList(List<EsField> fieldList) {

		this.fieldList = fieldList;
	}

}
