/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.hr.entity.hr_users;
import com.thinkgem.jeesite.modules.hr.dao.hrUsersDao;

/**
 * 人事专员Service
 * @author henry
 * @version 2016-06-16
 */
@Service
@Transactional(readOnly = true)
public class hrUsersService extends CrudService<hrUsersDao,hr_users> {

	@Autowired
	private hrUsersDao hrUserDao;

	/**
	 * 获取人事专员记录
	 * @param uid
	 * @return
	 */
	/*public hr_users get(hr_users hr_users) {
		hr_users entity = oaNotifyRecordDao.get(hr_users);
		return entity;
	}
	*/
	/**
	 * 获取人事专员记录
	 * @param uid
	 * @return
	 *//*
	public hr_users getById(hr_users hr_users) {
		//hr_users.setOaNotifyRecordList(oaNotifyRecordDao.findList(new OaNotifyRecord(hr_users)));
		hr_users=get(String.valueOf(hr_users.getUid()));
		return hr_users;
	}*/
	
	public Page<hr_users> find(Page<hr_users> page, hr_users hr_users) {
		hr_users.setPage(page);
		page.setList(hrUserDao.findList(hr_users));
		return page;
	}
	
	/**
	 * 获取通知数目
	 * @param oaNotify
	 * @return
	 */
	/*public Long findCount(OaNotify oaNotify) {
		return oaNotifyRecordDao.findCount(oaNotify);
	}*/
	
	@Transactional(readOnly = false)
	public void save(hr_users hr_users) {
		super.save(hr_users);
		
		/*// 更新发送接受人记录
		oaNotifyRecordDao.deleteByOaNotifyId(oaNotify.getId());
		if (oaNotify.getOaNotifyRecordList().size() > 0){
			oaNotifyRecordDao.insertAll(oaNotify.getOaNotifyRecordList());
		}*/
	}
	
	/**
	 * 更新阅读状态
	 */
	/*@Transactional(readOnly = false)
	public void updateReadFlag(OaNotify oaNotify) {
		OaNotifyRecord oaNotifyRecord = new OaNotifyRecord(oaNotify);
		oaNotifyRecord.setUser(oaNotifyRecord.getCurrentUser());
		oaNotifyRecord.setReadDate(new Date());
		oaNotifyRecord.setReadFlag("1");
		oaNotifyRecordDao.update(oaNotifyRecord);
	}*/
}