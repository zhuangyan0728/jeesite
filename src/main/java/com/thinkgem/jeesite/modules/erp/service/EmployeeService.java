/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.erp.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.erp.entity.Employee;
import com.thinkgem.jeesite.modules.erp.dao.EmployeeDao;

/**
 * 企业人才管理Service
 * @author zhuangyan
 * @version 2016-06-27
 */
@Service
@Transactional(readOnly = true)
public class EmployeeService extends CrudService<EmployeeDao, Employee> {

	public Employee get(String id) {
		return super.get(id);
	}
	
	public List<Employee> findList(Employee employee) {
		employee.getSqlMap().put("dsf", companyDataScopeFilter(employee.getCurrentUser(), "cid", ""));
		return super.findList(employee);
	}
	
	public Page<Employee> findPage(Page<Employee> page, Employee employee) {
		employee.getSqlMap().put("dsf", companyDataScopeFilter(employee.getCurrentUser(), "cid", ""));
		return super.findPage(page, employee);
	}
	
	@Transactional(readOnly = false)
	public void save(Employee employee) {
		super.save(employee);
	}
	
	@Transactional(readOnly = false)
	public void delete(Employee employee) {
		super.delete(employee);
	}
	
}