package cn.com.ut.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@MappedSuperclass
public class CommEntity {

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	protected String id;
	@Column(name = "is_del", insertable = false)
	protected String isDel = "N";
	@Column(name = "create_time", updatable = false)
	protected Date createTime;
	@Column(name = "create_id", updatable = false)
	protected String createId;
	@Column(name = "update_time", insertable = false)
	protected Date updateTime;
	@Column(name = "update_id", insertable = false)
	protected String updateId;

}
