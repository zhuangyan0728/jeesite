/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.erp.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.erp.entity.Employee;

/**
 * 企业人才管理DAO接口
 * @author zhuangyan
 * @version 2016-06-27
 */
@MyBatisDao
public interface EmployeeDao extends CrudDao<Employee> {
	
}