/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.erp.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.erp.entity.Employeecount;
import com.thinkgem.jeesite.modules.erp.dao.EmployeecountDao;

/**
 * 企业人员结构上报Service
 * @author zhuangyan
 * @version 2016-08-21
 */
@Service
@Transactional(readOnly = true)
public class EmployeecountService extends CrudService<EmployeecountDao, Employeecount> {

	public Employeecount get(String id) {
		return super.get(id);
	}
	
	public List<Employeecount> findList(Employeecount employeecount) {
		employeecount.getSqlMap().put("dsf", companyDataScopeFilter(employeecount.getCurrentUser(), "cid", ""));
		return super.findList(employeecount);
	}
	
	public Page<Employeecount> findPage(Page<Employeecount> page, Employeecount employeecount) {
		employeecount.getSqlMap().put("dsf", companyDataScopeFilter(employeecount.getCurrentUser(), "cid", ""));
		return super.findPage(page, employeecount);
	}
	
	@Transactional(readOnly = false)
	public void save(Employeecount employeecount) {
		super.save(employeecount);
	}
	
	@Transactional(readOnly = false)
	public void delete(Employeecount employeecount) {
		super.delete(employeecount);
	}
	
}