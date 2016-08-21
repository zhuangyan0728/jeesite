/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.erp.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.erp.entity.CompanyJobneedRecommend;

/**
 * 第三方反馈招聘需求DAO接口
 * @author zhuangyan
 * @version 2016-08-01
 */
@MyBatisDao
public interface CompanyJobneedRecommendDao extends CrudDao<CompanyJobneedRecommend> {
	
}