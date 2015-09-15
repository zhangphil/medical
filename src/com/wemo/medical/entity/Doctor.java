package com.wemo.medical.entity;

/**
 * 
 * @author baiqiao 健管师
 */
public class Doctor {
	private String picUrl; // 健管师头像链接
	private String brief; // 健管师简介
	private String name; // 健管师姓名

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
