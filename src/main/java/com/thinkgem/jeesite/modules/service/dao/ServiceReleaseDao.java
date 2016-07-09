/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.service.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.service.entity.ServiceRelease;

/**
 * 发布服务DAO接口
 * @author henry
 * @version 2016-07-07
 */
@MyBatisDao
public interface ServiceReleaseDao extends CrudDao<ServiceRelease> {
	
}