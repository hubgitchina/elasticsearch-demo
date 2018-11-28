package cn.com.ut.util;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Getter;
import lombok.Setter;

/**
 * 分页实体
 * 
 * @author wangpeng1
 * @since 2018年11月26日
 */
@Getter
@Setter
public class PageInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8744482754159674123L;

	/**
	 * 单页显示的记录数
	 */
	private int pagesize = 10;

	/**
	 * 当前页
	 */
	private int pageno = 0;
	/**
	 * 总记录数
	 */
	private long records = 0;
	/**
	 * 总页数
	 */
	private long total = 0;

	/**
	 * 当前页返回数据
	 */
	private List<?> data;

	public static PageInfo build() {

		return new PageInfo();
	}

	public <T> PageInfo appendPage(Page<T> page) {

		this.setPageno(page.getNumber() + 1);
		this.setPagesize(page.getSize());
		this.setRecords(page.getTotalElements());
		this.setTotal(page.getTotalPages());
		this.setData(page.getContent());
		return this;
	}

	public void setRecords(long records) {

		this.records = records;
		total = (this.records + pagesize - 1) / pagesize;
	}

}
