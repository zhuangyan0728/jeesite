/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.erp.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.erp.entity.CompanyJobneed;
import com.thinkgem.jeesite.modules.erp.dao.CompanyJobneedDao;

/**
 * 企业招聘需求管理Service
 * @author zhuangyan
 * @version 2016-07-08
 */
@Service
@Transactional(readOnly = true)
public class CompanyJobneedService extends CrudService<CompanyJobneedDao, CompanyJobneed> {

	public CompanyJobneed get(String id) {
		return super.get(id);
	}
	
	public List<CompanyJobneed> findList(CompanyJobneed companyJobneed) {
		companyJobneed.getSqlMap().put("dsf", companyDataScopeFilter(companyJobneed.getCurrentUser(), "cid", ""));
		
		return super.findList(companyJobneed);
	}
	
	public Page<CompanyJobneed> findPage(Page<CompanyJobneed> page, CompanyJobneed companyJobneed) {
		page.setOrderBy("publistime");
		companyJobneed.getSqlMap().put("dsf", companyDataScopeFilter(companyJobneed.getCurrentUser(), "cid", ""));
		return super.findPage(page, companyJobneed);
	}
	
	@Transactional(readOnly = false)
	public void save(CompanyJobneed companyJobneed) {
		super.save(companyJobneed);
	}
	
	@Transactional(readOnly = false)
	public void delete(CompanyJobneed companyJobneed) {
		super.delete(companyJobneed);
	}
	
}