package cn.com.ut.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @Description: 商品表实体类
 * @Author wangpeng1
 * @Date 2018/11/5 9:50
 */
@Data
@Entity(name = "ImportHistory")
@Table(name = "ds_import_history")
public class DsImportHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected int id;

	@Column(name = "create_time")
	protected Date createTime;

	@Column(name = "update_time")
	protected Date updateTime;

	@Column(name = "table_name")
	protected String tableName;
}