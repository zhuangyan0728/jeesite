/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.erp.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.erp.entity.CompanyInfo;
import com.thinkgem.jeesite.modules.erp.dao.CompanyInfoDao;

/**
 * 企业信息管理Service
 * @author zhuangyan
 * @version 2016-06-26
 */
@Service
@Transactional(readOnly = true)
public class CompanyInfoService extends CrudService<CompanyInfoDao, CompanyInfo> {

	public CompanyInfo get(String id) {
		return super.get(id);
	}
	
	public List<CompanyInfo> findList(CompanyInfo companyInfo) {
		companyInfo.getSqlMap().put("dsf", companyDataScopeFilter(companyInfo.getCurrentUser(), "id", ""));
		return super.findList(companyInfo);
	}
	
	
	public List<CompanyInfo> findListNoFilter(CompanyInfo companyInfo) {
		//companyInfo.getSqlMap().put("dsf", companyDataScopeFilter(companyInfo.getCurrentUser(), "id", ""));
		return super.findList(companyInfo);
	}
	
	public Page<CompanyInfo> findPage(Page<CompanyInfo> page, CompanyInfo companyInfo) {
		companyInfo.getSqlMap().put("dsf", companyDataScopeFilter(companyInfo.getCurrentUser(), "id", ""));
		return super.findPage(page, companyInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(CompanyInfo companyInfo) {
		super.save(companyInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(CompanyInfo companyInfo) {
		super.delete(companyInfo);
	}
	
}