/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.hr.dao.hrUsersDao;
import com.thinkgem.jeesite.modules.service.entity.ServiceReleaseFeedback;
import com.thinkgem.jeesite.modules.service.dao.ServiceReleaseFeedbackDao;
import com.thinkgem.jeesite.modules.service.dao.ServiceReleaseFeedbackDealtDao;

/**
 * 企业回馈服务项目Service
 * @author henry
 * @version 2016-07-07
 */
@Service
@Transactional(readOnly = true)
public class ServiceReleaseFeedbackService extends CrudService<ServiceReleaseFeedbackDao, ServiceReleaseFeedback> {

	@Autowired
	private ServiceReleaseFeedbackDao serviceReleaseFeedbackDao;
	
	public ServiceReleaseFeedback get(String id) {
		return super.get(id);
	}
	
	public List<ServiceReleaseFeedback> findListbyRename(String re_name) {
		return serviceReleaseFeedbackDao.findListbyRename(re_name);
	}
	
	public List<ServiceReleaseFeedback> findList(ServiceReleaseFeedback serviceReleaseFeedback) {
		return super.findList(serviceReleaseFeedback);
	}
	
	public Page<ServiceReleaseFeedback> findPage(Page<ServiceReleaseFeedback> page, ServiceReleaseFeedback serviceReleaseFeedback) {
		return super.findPage(page, serviceReleaseFeedback);
	}
	
	@Transactional(readOnly = false)
	public void save(ServiceReleaseFeedback serviceReleaseFeedback) {
		super.save(serviceReleaseFeedback);
	}
	
	@Transactional(readOnly = false)
	public void delete(ServiceReleaseFeedback serviceReleaseFeedback) {
		super.delete(serviceReleaseFeedback);
	}
	
}