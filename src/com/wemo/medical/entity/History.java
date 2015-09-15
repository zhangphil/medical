package com.wemo.medical.entity;

public class History {
	private String id;
	private String date;
	private String events;
	private String status; // 状态： 0：已拒绝 1：已接受 2：待处理

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getEvents() {
		return events;
	}

	public void setEvents(String events) {
		this.events = events;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
