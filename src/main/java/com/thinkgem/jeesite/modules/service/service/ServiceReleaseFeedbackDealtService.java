/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.service.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.service.entity.ServiceReleaseFeedbackDealt;
import com.thinkgem.jeesite.modules.service.dao.ServiceReleaseFeedbackDealtDao;

/**
 * 企业服务处理Service
 * @author henry
 * @version 2016-07-08
 */
@Service
@Transactional(readOnly = true)
public class ServiceReleaseFeedbackDealtService extends CrudService<ServiceReleaseFeedbackDealtDao, ServiceReleaseFeedbackDealt> {

	public ServiceReleaseFeedbackDealt get(String id) {
		return super.get(id);
	}
	
	public List<ServiceReleaseFeedbackDealt> findList(ServiceReleaseFeedbackDealt serviceReleaseFeedbackDealt) {
		return super.findList(serviceReleaseFeedbackDealt);
	}
	
	public Page<ServiceReleaseFeedbackDealt> findPage(Page<ServiceReleaseFeedbackDealt> page, ServiceReleaseFeedbackDealt serviceReleaseFeedbackDealt) {
		return super.findPage(page, serviceReleaseFeedbackDealt);
	}
	
	@Transactional(readOnly = false)
	public void save(ServiceReleaseFeedbackDealt serviceReleaseFeedbackDealt) {
		super.save(serviceReleaseFeedbackDealt);
	}
	
	@Transactional(readOnly = false)
	public void delete(ServiceReleaseFeedbackDealt serviceReleaseFeedbackDealt) {
		super.delete(serviceReleaseFeedbackDealt);
	}
	
}