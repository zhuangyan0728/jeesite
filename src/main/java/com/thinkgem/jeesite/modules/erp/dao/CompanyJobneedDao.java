/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.erp.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.erp.entity.CompanyJobneed;

/**
 * 企业招聘需求管理DAO接口
 * @author zhuangyan
 * @version 2016-07-08
 */
@MyBatisDao
public interface CompanyJobneedDao extends CrudDao<CompanyJobneed> {
	
}