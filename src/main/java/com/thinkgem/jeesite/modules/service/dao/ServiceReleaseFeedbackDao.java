/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.service.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.service.entity.ServiceReleaseFeedback;

/**
 * 企业回馈服务项目DAO接口
 * @author henry
 * @version 2016-07-07
 */
@MyBatisDao
public interface ServiceReleaseFeedbackDao extends CrudDao<ServiceReleaseFeedback> {
	public List<ServiceReleaseFeedback> findListbyRename(String re_name);
}