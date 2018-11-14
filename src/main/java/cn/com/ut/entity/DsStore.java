package cn.com.ut.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity(name = "Store")
@Table(name = "ds_store")
public class DsStore extends CommEntity {

	/**
	 * 店铺名称
	 */
	@Column(name = "\"store_name\"")
	private String storeName;

	/**
	 * 用户ID
	 */
	@Column(name = "\"member_id\"")
	private String memberId;
	/**
	 * 用户名
	 */
	@Column(name = "\"member_name\"")
	private String memberName;

	/**
	 * 店铺公司名称
	 */
	@Column(name = "\"store_company_name\"")
	private String storeCompanyName;
	/**
	 * 地区ID
	 */
	@Column(name = "\"area_id\"")
	private String areaId;
	/**
	 * 地区名称
	 */
	@Column(name = "\"area_info\"")
	private String areaInfo;
	/**
	 * 店铺地址
	 */
	@Column(name = "\"store_address\"")
	private String storeAddress;
	/**
	 * 店铺状态:0关闭，1开启，2审核中
	 */
	@Column(name = "\"store_state\"")
	private Integer storeState;
	/**
	 * 店铺关闭原因
	 */
	@Column(name = "\"store_close_info\"")
	private String storeCloseInfo;
	/**
	 * 店铺时间
	 */
	@Column(name = "\"store_addtime\"")
	private Date storeAddtime;
	/**
	 * 店铺关闭时间
	 */
	@Column(name = "\"store_endtime\"")
	private Date storeEndtime;
	/**
	 * 店铺LOGO
	 */
	@Column(name = "\"store_logo\"")
	private String storeLogo;
	/**
	 * 店铺Banner
	 */
	@Column(name = "\"store_banner\"")
	private String storeBanner;
	/**
	 * 店铺头像
	 */
	@Column(name = "\"store_avatar\"")
	private String storeAvatar;
	/**
	 * 店铺SEO关键字
	 */
	@Column(name = "\"store_keywords\"")
	private String storeKeywords;
	/**
	 * 店铺QQ
	 */
	@Column(name = "\"store_qq\"")
	private String storeQq;
	/**
	 * 阿里旺旺
	 */
	@Column(name = "\"store_ww\"")
	private String storeWw;
	/**
	 * 商家电话
	 */
	@Column(name = "\"store_phone\"")
	private String storePhone;
	/**
	 * 主营商品
	 */
	@Column(name = "\"store_mainbusiness\"")
	private String storeMainbusiness;
	/**
	 * 是否推荐:0否 1是
	 */
	@Column(name = "\"store_recommend\"")
	private Integer storeRecommend;
	/**
	 * 店铺当前主题
	 */
	@Column(name = "\"store_theme\"")
	private String storeTheme;
	/**
	 * 店铺信用
	 */
	@Column(name = "\"store_credit\"")
	private Integer storeCredit;
	/**
	 * 描述相符度分数
	 */
	@Column(name = "\"store_desccredit\"")
	private java.math.BigDecimal storeDesccredit;
	/**
	 * 服务态度分数
	 */
	@Column(name = "\"store_servicecredit\"")
	private java.math.BigDecimal storeServicecredit;
	/**
	 * 发货速度分数
	 */
	@Column(name = "\"store_deliverycredit\"")
	private java.math.BigDecimal storeDeliverycredit;
	/**
	 * 店铺收藏数量
	 */
	@Column(name = "\"store_collect\"")
	private Integer storeCollect;
	/**
	 * 店铺销量
	 */
	@Column(name = "\"store_sales\"")
	private Integer storeSales;
	/**
	 * 工作时间
	 */
	@Column(name = "\"store_workingtime\"")
	private String storeWorkingtime;
	/**
	 * 超出该金额免运费，大于0才表示该值有效
	 */
	@Column(name = "\"store_free_price\"")
	private java.math.BigDecimal storeFreePrice;
	/**
	 * 是否自营店铺 1是 0否
	 */
	@Column(name = "\"is_platform_store\"")
	private Integer isPlatformStore;
	/**
	 * 货到付款
	 */
	@Column(name = "\"store_huodaofk\"")
	private Integer storeHuodaofk;
	/**
	 * 应用ID
	 */
	@Column(name = "\"app_id\"")
	private String appId;

	/**
	 * 城市
	 */
	@Column(name = "\"cityId\"")
	private String cityId;
	/**
	 * 省份
	 */
	@Column(name = "\"provinceId\"")
	private String provinceId;
	/**
	 * 位置经纬度
	 */
	@Column(name = "\"position\"")
	private String position;
	/**
	 * 扩展信息
	 */
	@Column(name = "\"extend_info\"")
	private String extendInfo;

	/**
	 * 是否营业：
	 */
	@Column(name = "\"is_open\"")
	private Integer isOpen;

	public String getStoreName() {

		return storeName;
	}

	public void setStoreName(String storeName) {

		this.storeName = storeName;
	}

	public String getMemberId() {

		return memberId;
	}

	public void setMemberId(String memberId) {

		this.memberId = memberId;
	}

	public String getMemberName() {

		return memberName;
	}

	public void setMemberName(String memberName) {

		this.memberName = memberName;
	}

	public String getStoreCompanyName() {

		return storeCompanyName;
	}

	public void setStoreCompanyName(String storeCompanyName) {

		this.storeCompanyName = storeCompanyName;
	}

	public String getAreaId() {

		return areaId;
	}

	public void setAreaId(String areaId) {

		this.areaId = areaId;
	}

	public String getAreaInfo() {

		return areaInfo;
	}

	public void setAreaInfo(String areaInfo) {

		this.areaInfo = areaInfo;
	}

	public String getStoreAddress() {

		return storeAddress;
	}

	public void setStoreAddress(String storeAddress) {

		this.storeAddress = storeAddress;
	}

	public Integer getStoreState() {

		return storeState;
	}

	public void setStoreState(Integer storeState) {

		this.storeState = storeState;
	}

	public String getStoreCloseInfo() {

		return storeCloseInfo;
	}

	public void setStoreCloseInfo(String storeCloseInfo) {

		this.storeCloseInfo = storeCloseInfo;
	}

	public Date getStoreAddtime() {

		return storeAddtime;
	}

	public void setStoreAddtime(Date storeAddtime) {

		this.storeAddtime = storeAddtime;
	}

	public Date getStoreEndtime() {

		return storeEndtime;
	}

	public void setStoreEndtime(Date storeEndtime) {

		this.storeEndtime = storeEndtime;
	}

	public String getStoreLogo() {

		return storeLogo;
	}

	public void setStoreLogo(String storeLogo) {

		this.storeLogo = storeLogo;
	}

	public String getStoreBanner() {

		return storeBanner;
	}

	public void setStoreBanner(String storeBanner) {

		this.storeBanner = storeBanner;
	}

	public String getStoreAvatar() {

		return storeAvatar;
	}

	public void setStoreAvatar(String storeAvatar) {

		this.storeAvatar = storeAvatar;
	}

	public String getStoreKeywords() {

		return storeKeywords;
	}

	public void setStoreKeywords(String storeKeywords) {

		this.storeKeywords = storeKeywords;
	}

	public String getStoreQq() {

		return storeQq;
	}

	public void setStoreQq(String storeQq) {

		this.storeQq = storeQq;
	}

	public String getStoreWw() {

		return storeWw;
	}

	public void setStoreWw(String storeWw) {

		this.storeWw = storeWw;
	}

	public String getStorePhone() {

		return storePhone;
	}

	public void setStorePhone(String storePhone) {

		this.storePhone = storePhone;
	}

	public String getStoreMainbusiness() {

		return storeMainbusiness;
	}

	public void setStoreMainbusiness(String storeMainbusiness) {

		this.storeMainbusiness = storeMainbusiness;
	}

	public Integer getStoreRecommend() {

		return storeRecommend;
	}

	public void setStoreRecommend(Integer storeRecommend) {

		this.storeRecommend = storeRecommend;
	}

	public String getStoreTheme() {

		return storeTheme;
	}

	public void setStoreTheme(String storeTheme) {

		this.storeTheme = storeTheme;
	}

	public Integer getStoreCredit() {

		return storeCredit;
	}

	public void setStoreCredit(Integer storeCredit) {

		this.storeCredit = storeCredit;
	}

	public java.math.BigDecimal getStoreDesccredit() {

		return storeDesccredit;
	}

	public void setStoreDesccredit(java.math.BigDecimal storeDesccredit) {

		this.storeDesccredit = storeDesccredit;
	}

	public java.math.BigDecimal getStoreServicecredit() {

		return storeServicecredit;
	}

	public void setStoreServicecredit(java.math.BigDecimal storeServicecredit) {

		this.storeServicecredit = storeServicecredit;
	}

	public java.math.BigDecimal getStoreDeliverycredit() {

		return storeDeliverycredit;
	}

	public void setStoreDeliverycredit(java.math.BigDecimal storeDeliverycredit) {

		this.storeDeliverycredit = storeDeliverycredit;
	}

	public Integer getStoreCollect() {

		return storeCollect;
	}

	public void setStoreCollect(Integer storeCollect) {

		this.storeCollect = storeCollect;
	}

	public Integer getStoreSales() {

		return storeSales;
	}

	public void setStoreSales(Integer storeSales) {

		this.storeSales = storeSales;
	}

	public String getStoreWorkingtime() {

		return storeWorkingtime;
	}

	public void setStoreWorkingtime(String storeWorkingtime) {

		this.storeWorkingtime = storeWorkingtime;
	}

	public java.math.BigDecimal getStoreFreePrice() {

		return storeFreePrice;
	}

	public void setStoreFreePrice(java.math.BigDecimal storeFreePrice) {

		this.storeFreePrice = storeFreePrice;
	}

	public Integer getIsPlatformStore() {

		return isPlatformStore;
	}

	public void setIsPlatformStore(Integer isPlatformStore) {

		this.isPlatformStore = isPlatformStore;
	}

	public Integer getStoreHuodaofk() {

		return storeHuodaofk;
	}

	public void setStoreHuodaofk(Integer storeHuodaofk) {

		this.storeHuodaofk = storeHuodaofk;
	}

	public String getAppId() {

		return appId;
	}

	public void setAppId(String appId) {

		this.appId = appId;
	}

	public String getCityId() {

		return cityId;
	}

	public void setCityId(String cityId) {

		this.cityId = cityId;
	}

	public String getProvinceId() {

		return provinceId;
	}

	public void setProvinceId(String provinceId) {

		this.provinceId = provinceId;
	}

	public String getPosition() {

		return position;
	}

	public void setPosition(String position) {

		this.position = position;
	}

	public String getExtendInfo() {

		return extendInfo;
	}

	public void setExtendInfo(String extendInfo) {

		this.extendInfo = extendInfo;
	}

	public Integer getIsOpen() {

		return isOpen;
	}

	public void setIsOpen(Integer isOpen) {

		this.isOpen = isOpen;
	}

}