package com.wemo.medical.activity;

import android.app.Application;

import com.baidu.frontia.FrontiaApplication;
import com.wemo.medical.entity.CustomerInfo;
import com.wemo.medical.entity.Doctor;

/**
 * 
 * @author baiqiao
 * @see 用于保存客户登录信息等
 * 
 */
public class MedicalApplication extends Application {

	private CustomerInfo customerInfo = null;
	private Doctor doctor = null;

	public CustomerInfo getCustomerInfo() {
		return customerInfo;
	}

	public void setCustomerInfo(CustomerInfo customerInfo) {
		this.customerInfo = customerInfo;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		FrontiaApplication.initFrontiaApplication(MedicalApplication.this);
	}
}
