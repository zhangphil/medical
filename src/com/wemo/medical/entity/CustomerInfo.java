package com.wemo.medical.entity;

import net.tsz.afinal.annotation.sqlite.Table;

/**
 * @author baiqiao
 * @see 个人客户信息
 */
@Table(name = "CustomerInfo")
public class CustomerInfo {

	private String id;
	private String token; // 客户token
	private String cusName; // 客户姓名
	private String nameFullSpel; // 姓名全拼,系统自动得到,不带空格
	private Dictionary sex; // 性别
	private String birth; // 出生日期
	private Dictionary marriage; // 婚姻状况
	private Dictionary cusType; // 客户类型
	private String cusLvl; // 客户等级默认为”0”暂不使用
	private String corpId; // 所属单位Id
	private String handsetNo; // 手机号
	private String email; // 邮箱
	private String pwd; // 密码（加密）
	private String plainPassword; // 密码（未加密）
	private String permRes; // 常驻地区
	private String qq; // QQ
	private String contName; // 联系人姓名
	private Dictionary contRela; // 联系人关系
	private String contHandsetNo; // 联系人电话
	private String remark; // 备注
	private String healMngId; // 责任健管师Id
	private String healMngName; // 责任健管师姓名
	private String mngAllocRzn; // mngAllocRzn
	private String cusStat; // 客户状态,默认”0”,暂不使用
	private String crtDt; // 创建日期
	private String crtId; // 创建人Id
	private String updDt; // 上次修改日期
	private String updId; // 上次修改人Id
	private String delDt; // 注销日期
	private String delId; // 注销人Id
    private String cusTypeId;//客户类型编号
	
	public String getCusTypeId() {
		return cusTypeId;
	}

	public void setCusTypeId(String cusTypeId) {
		this.cusTypeId = cusTypeId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getNameFullSpel() {
		return nameFullSpel;
	}

	public void setNameFullSpel(String nameFullSpel) {
		this.nameFullSpel = nameFullSpel;
	}

	public Dictionary getSex() {
		return sex;
	}

	public void setSex(Dictionary sex) {
		this.sex = sex;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public Dictionary getMarriage() {
		return marriage;
	}

	public void setMarriage(Dictionary marriage) {
		this.marriage = marriage;
	}

	public Dictionary getCusType() {
		return cusType;
	}

	public void setCusType(Dictionary cusType) {
		this.cusType = cusType;
	}

	public String getCusLvl() {
		return cusLvl;
	}

	public void setCusLvl(String cusLvl) {
		this.cusLvl = cusLvl;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getHandsetNo() {
		return handsetNo;
	}

	public void setHandsetNo(String handsetNo) {
		this.handsetNo = handsetNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPlainPassword() {
		return plainPassword;
	}

	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}

	public String getPermRes() {
		return permRes;
	}

	public void setPermRes(String permRes) {
		this.permRes = permRes;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getContName() {
		return contName;
	}

	public void setContName(String contName) {
		this.contName = contName;
	}

	public Dictionary getContRela() {
		return contRela;
	}

	public void setContRela(Dictionary contRela) {
		this.contRela = contRela;
	}

	public String getContHandsetNo() {
		return contHandsetNo;
	}

	public void setContHandsetNo(String contHandsetNo) {
		this.contHandsetNo = contHandsetNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getHealMngId() {
		return healMngId;
	}

	public void setHealMngId(String healMngId) {
		this.healMngId = healMngId;
	}

	public String getHealMngName() {
		return healMngName;
	}

	public void setHealMngName(String healMngName) {
		this.healMngName = healMngName;
	}

	public String getMngAllocRzn() {
		return mngAllocRzn;
	}

	public void setMngAllocRzn(String mngAllocRzn) {
		this.mngAllocRzn = mngAllocRzn;
	}

	public String getCusStat() {
		return cusStat;
	}

	public void setCusStat(String cusStat) {
		this.cusStat = cusStat;
	}

	public String getCrtDt() {
		return crtDt;
	}

	public void setCrtDt(String crtDt) {
		this.crtDt = crtDt;
	}

	public String getCrtId() {
		return crtId;
	}

	public void setCrtId(String crtId) {
		this.crtId = crtId;
	}

	public String getUpdDt() {
		return updDt;
	}

	public void setUpdDt(String updDt) {
		this.updDt = updDt;
	}

	public String getUpdId() {
		return updId;
	}

	public void setUpdId(String updId) {
		this.updId = updId;
	}

	public String getDelDt() {
		return delDt;
	}

	public void setDelDt(String delDt) {
		this.delDt = delDt;
	}

	public String getDelId() {
		return delId;
	}

	public void setDelId(String delId) {
		this.delId = delId;
	}
}
