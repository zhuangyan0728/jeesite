/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hr.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.hr.entity.hr_users;

/**
 * 人事专员DAO接口
 * @author henry
 * @version 2016-06-16
 */
@MyBatisDao
public interface hrUsersDao extends CrudDao<hr_users> {

	/**
	 * 插入通知记录
	 * @param oaNotifyRecordList
	 * @return
	 */
	/*public int insertAll(List<hr_users> hrusersList);*/
	
	/**
	 * 根据通知ID删除通知记录
	 * @param oaNotifyId 通知ID
	 * @return
	 */
	/*public int deleteByOaNotifyId(String oaNotifyId);*/
	
	/**
	 * 获取人事专员记录
	 * @param nickname
	 * @return
	 *//*
	public hr_users getById(int Uid);*/
	
}