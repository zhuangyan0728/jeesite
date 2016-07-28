/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.oa.entity.OaNotifyRecordBack;

/**
 * 通知回复DAO接口
 * @author henry
 * @version 2016-07-14
 */
@MyBatisDao
public interface OaNotifyRecordBackDao extends CrudDao<OaNotifyRecordBack> {
	/**
	 * 保存反馈数据
	 */
	//public int saveRecordBack(OaNotifyRecordBack oaNotifyRecordBack);
	
	
	/**
	 * 获取政府回复企业关于通知的疑问（最新记录）
	 */
	//public List<OaNotifyRecordBack> findList(OaNotifyRecordBack oaNotifyRecordBack);
}