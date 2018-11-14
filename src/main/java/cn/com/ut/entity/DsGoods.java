package cn.com.ut.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

/**
 * @Description: 商品表实体类
 * @Author wangpeng1
 * @Date 2018/11/5 9:50
 */
@Data
@Entity(name = "Goods")
@Table(name = "ds_goods")
public class DsGoods extends CommEntity {
	/**
	 * 商品编号
	 */
	@Column(name = "\"goods_serial\"")
	private String goodsSerial;
	/**
	 * 商品名称
	 */
	@Column(name = "\"goods_name\"")
	private String goodsName;
	/**
	 * 商品广告词
	 */
	@Column(name = "\"goods_advword\"")
	private String goodsAdvword;
	/**
	 * 商品分类
	 */
	@Column(name = "\"gc_id\"")
	private String gcId;
	/**
	 * 店铺id
	 */
	@Column(name = "\"store_id\"")
	private String storeId;
	/**
	 * 规格值
	 */
	@Column(name = "\"spec_value\"")
	private String specValue;
	/**
	 * 品牌id
	 */
	@Column(name = "\"brand_id\"")
	private String brandId = "0";
	/**
	 * 类型id
	 */
	@Column(name = "\"type_id\"")
	private String typeId = "0";
	/**
	 * 商品属性
	 */
	@Column(name = "\"goods_attr\"")
	private String goodsAttr;
	/**
	 * 商品描述
	 */
	@Column(name = "\"goods_desc\"")
	private String goodsDesc;
	/**
	 * 商品内容
	 */
	@Column(name = "\"goods_body\"")
	private String goodsBody;
	/**
	 * 商品状态 0:下架 1:正常 10:违规（禁售）2:编辑
	 */
	@Column(name = "\"goods_state\"")
	private String goodsState = "2";
	/**
	 * 违规原因
	 */
	@Column(name = "\"goods_stateremark\"")
	private String goodsStateremark;
	/**
	 * 商品审核 1通过，0未通过，10审核中
	 */
	@Column(name = "\"goods_verify\"")
	private Integer goodsVerify;
	/**
	 * 审核失败原因
	 */
	@Column(name = "\"goods_verifyremark\"")
	private String goodsVerifyremark;
	/**
	 * 商品锁定 0未锁，1已锁
	 */
	@Column(name = "\"goods_lock\"")
	private Integer goodsLock = 0;
	/**
	 * 商品添加时间
	 */
	@Column(name = "\"goods_addtime\"")
	private Timestamp goodsAddtime;
	/**
	 * 上架时间
	 */
	@Column(name = "\"goods_shelftime\"")
	private Timestamp goodsShelftime;
	/**
	 * 商品价格
	 */
	@Column(name = "\"goods_price\"")
	private BigDecimal goodsPrice;
	/**
	 * 商品市场价
	 */
	@Column(name = "\"goods_marketprice\"")
	private BigDecimal goodsMarketprice;
	/**
	 * 商品成本价
	 */
	@Column(name = "\"goods_costprice\"")
	private BigDecimal goodsCostprice = new BigDecimal("0.00");
	/**
	 * 商品折扣
	 */
	@Column(name = "\"goods_discount\"")
	private BigDecimal goodsDiscount = new BigDecimal("1");
	/**
	 * 商品库存报警值
	 */
	@Column(name = "\"goods_storage_alarm\"")
	private Integer goodsStorageAlarm = 0;
	/**
	 * 商品推荐 1:是 0:否
	 */
	@Column(name = "\"goods_commend\"")
	private Integer goodsCommend = 0;
	/**
	 * 商品运费 0为免运费
	 */
	@Column(name = "\"goods_freight\"")
	private BigDecimal goodsFreight = new BigDecimal("0.00");
	/**
	 * 商品是否开具增值税发票 1:是 0:否
	 */
	@Column(name = "\"goods_vat\"")
	private Integer goodsVat = 0;
	/**
	 * 是否为虚拟商品 1:是 0:否
	 */
	@Column(name = "\"is_virtual\"")
	private Integer isVirtual = 0;
	/**
	 * 总库存
	 */
	@Column(name = "\"goods_storage\"")
	private Integer goodsStorage = 0;
	/**
	 * 商品分类完整路径冗余，方便按分类或子分类查询商品
	 */
	@Column(name = "\"gc_fullpath\"")
	private String gcFullpath;
	/**
	 * 最低价
	 */
	@Column(name = "\"price_min\"")
	private BigDecimal priceMin = new BigDecimal("0.00");
	/**
	 * 最高价
	 */
	@Column(name = "\"price_max\"")
	private BigDecimal priceMax = new BigDecimal("0.00");
	/**
	 * 销量
	 */
	@Column(name = "\"goods_salenum\"")
	private Long goodsSalenum = 0L;
	/**
	 * 商品默认图片
	 */
	@Column(name = "\"image_path\"")
	private String imagePath;
	/**
	 * 应用ID
	 */
	@Column(name = "\"app_id\"")
	private String appId;
	/**
	 * 评价数
	 */
	@Column(name = "\"evaluation_count\"")
	private Long evaluationCount = 0L;
	/**
	 * 扩展信息JSON
	 */
	@Column(name = "\"extend_info\"")
	private String extendInfo;
	/**
	 * 应用关联商品ID
	 */
	@Column(name = "\"ref_id\"")
	private String refId;

}