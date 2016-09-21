/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.erp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.erp.entity.CompanyJobneed;
import com.thinkgem.jeesite.modules.erp.dao.CompanyJobneedDao;
import com.thinkgem.jeesite.modules.erp.entity.CompanyJobneedRecommend;
import com.thinkgem.jeesite.modules.erp.dao.CompanyJobneedRecommendDao;

/**
 * 第三方反馈招聘需求Service
 * @author zhuangyan
 * @version 2016-08-01
 */
@Service
@Transactional(readOnly = true)
public class CompanyJobneedRecommendService extends CrudService<CompanyJobneedDao, CompanyJobneed> {

	@Autowired
	private CompanyJobneedRecommendDao companyJobneedRecommendDao;
	
	public CompanyJobneed get(String id) {
		CompanyJobneed companyJobneed = super.get(id);
		companyJobneed.setCompanyJobneedRecommendList(companyJobneedRecommendDao.findList(new CompanyJobneedRecommend(companyJobneed)));
		return companyJobneed;
	}
	
	public List<CompanyJobneed> findList(CompanyJobneed companyJobneed) {
		
		return super.findList(companyJobneed);
	}
	
	public Page<CompanyJobneed> findPage(Page<CompanyJobneed> page, CompanyJobneed companyJobneed) {
		return super.findPage(page, companyJobneed);
	}
	
	@Transactional(readOnly = false)
	public void save(CompanyJobneed companyJobneed) {
		super.save(companyJobneed);
		for (CompanyJobneedRecommend companyJobneedRecommend : companyJobneed.getCompanyJobneedRecommendList()){
			if (companyJobneedRecommend.getId() == null){
				continue;
			}
			if (CompanyJobneedRecommend.DEL_FLAG_NORMAL.equals(companyJobneedRecommend.getDelFlag())){
				if (StringUtils.isBlank(companyJobneedRecommend.getId())){
					companyJobneedRecommend.setJobneedid(companyJobneed);
					companyJobneedRecommend.preInsert();
					companyJobneedRecommendDao.insert(companyJobneedRecommend);
				}else{
					companyJobneedRecommend.preUpdate();
					companyJobneedRecommendDao.update(companyJobneedRecommend);
				}
			}else{
				companyJobneedRecommendDao.delete(companyJobneedRecommend);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(CompanyJobneed companyJobneed) {
		super.delete(companyJobneed);
		companyJobneedRecommendDao.delete(new CompanyJobneedRecommend(companyJobneed));
	}
	
}