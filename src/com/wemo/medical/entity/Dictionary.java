package com.wemo.medical.entity;

import net.tsz.afinal.annotation.sqlite.Table;

/**
 * @author baiqiao
 * @see 字典集
 */
@Table(name = "dictionary")
public class Dictionary {

	private String id;
	private String label; // 标签名
	private String value; // 数据值
	private String type; // 类型
	private String description; // 描述
	private String sort; // 排序

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
}
