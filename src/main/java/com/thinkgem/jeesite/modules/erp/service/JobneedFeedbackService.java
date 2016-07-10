/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.erp.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.erp.entity.JobneedFeedback;
import com.thinkgem.jeesite.modules.erp.dao.JobneedFeedbackDao;

/**
 * 反馈招聘需求Service
 * @author zhuangyan
 * @version 2016-07-10
 */
@Service
@Transactional(readOnly = true)
public class JobneedFeedbackService extends CrudService<JobneedFeedbackDao, JobneedFeedback> {

	public JobneedFeedback get(String id) {
		return super.get(id);
	}
	
	public List<JobneedFeedback> findList(JobneedFeedback jobneedFeedback) {
		return super.findList(jobneedFeedback);
	}
	
	public Page<JobneedFeedback> findPage(Page<JobneedFeedback> page, JobneedFeedback jobneedFeedback) {
		return super.findPage(page, jobneedFeedback);
	}
	
	@Transactional(readOnly = false)
	public void save(JobneedFeedback jobneedFeedback) {
		super.save(jobneedFeedback);
	}
	
	@Transactional(readOnly = false)
	public void delete(JobneedFeedback jobneedFeedback) {
		super.delete(jobneedFeedback);
	}
	
}