package cn.com.ut.vo;

import java.util.List;

import lombok.Data;

@Data
public class EsIndexField {

	private String index;

	private String type;

	private String parent;

	private List<EsField> fieldList;
}
