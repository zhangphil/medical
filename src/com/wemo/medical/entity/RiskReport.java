package com.wemo.medical.entity;

public class RiskReport {

	private String id;
	private String askId;
	private String cusName;
	private String cusHandsetNo;
	private String sex;
	private String askDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAskId() {
		return askId;
	}

	public void setAskId(String askId) {
		this.askId = askId;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getCusHandsetNo() {
		return cusHandsetNo;
	}

	public void setCusHandsetNo(String cusHandsetNo) {
		this.cusHandsetNo = cusHandsetNo;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAskDate() {
		return askDate;
	}

	public void setAskDate(String askDate) {
		this.askDate = askDate;
	}
}
