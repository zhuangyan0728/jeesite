/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.erp.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.erp.entity.Employeecount;

/**
 * 企业人员结构上报DAO接口
 * @author zhuangyan
 * @version 2016-08-21
 */
@MyBatisDao
public interface EmployeecountDao extends CrudDao<Employeecount> {
	
}