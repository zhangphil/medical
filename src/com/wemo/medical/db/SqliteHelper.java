package com.wemo.medical.db;

import java.util.List;

import net.tsz.afinal.FinalDb;
import android.content.Context;
import android.util.Log;

import com.wemo.medical.entity.CustomerInfo;
import com.wemo.medical.entity.Dictionary;

public class SqliteHelper {

	private FinalDb finalDb;
	private String TAG = "SqliteHelper";

	public SqliteHelper(Context context) {
		finalDb = FinalDb.create(context, "medical.db");
	}

	/**
	 * @see 将字典集保存到本地数据库
	 * @param dictionary
	 *            字典集
	 */
	public void insertDictionary(List<Dictionary> dictionaries) {
		if (finalDb.findAll(Dictionary.class).size() == 0) {
			for (int i = 0; i < dictionaries.size(); i++) {
				finalDb.save(dictionaries.get(i));
			}
			Log.d(TAG, "字典集保存成功");
		}
	}

	/**
	 * 获取字典集中的某些项
	 * 
	 * @param type
	 *            类型
	 * @return dictionaries
	 */
	public List<Dictionary> getDictionary(String type) {
		type = "\"" + type + "\"";
		List<Dictionary> dictionaries = finalDb.findAllByWhere(
				Dictionary.class, "type=" + type);
		return dictionaries;
	}

	/**
	 * @see 将客户信息添加到数据库
	 * @param customerInfo
	 */
	public void insertCustomerInfo(CustomerInfo customerInfo) {
		if (finalDb.findAll(CustomerInfo.class).size() == 0) {
			finalDb.save(customerInfo);
		} else {
			//findAllByWhere的orderby是什么意思
			finalDb.deleteById(
					CustomerInfo.class,
					finalDb.findAllByWhere(CustomerInfo.class, null,
							"id limit 1").get(0).getId());
			finalDb.save(customerInfo);
		}
	}

	/**
	 * @see 获取本地数据中客户信息
	 * @return customerInfo 客户信息
	 */
	public CustomerInfo getUser() {
		CustomerInfo customerInfo = null;
		if (finalDb.findAll(CustomerInfo.class).size() > 0) {
			customerInfo = finalDb.findAll(CustomerInfo.class).get(0);
		}
		return customerInfo;
	}

	/**
	 * @see 删除本地数据库中的用户信息
	 * @param customerInfo
	 */
	public void deleteCustomerInfo() {
		if (finalDb.findAll(CustomerInfo.class).size() != 0) {
			finalDb.deleteAll(CustomerInfo.class);
			Log.d("sqliteHelper", "用户已删除");
		}
	}
}
