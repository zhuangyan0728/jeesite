/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.service.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.service.entity.ServiceRelease;
import com.thinkgem.jeesite.modules.service.dao.ServiceReleaseDao;

/**
 * 发布服务Service
 * @author henry
 * @version 2016-07-07
 */
@Service
@Transactional(readOnly = true)
public class ServiceReleaseService extends CrudService<ServiceReleaseDao, ServiceRelease> {

	public ServiceRelease get(String id) {
		return super.get(id);
	}
	
	public List<ServiceRelease> findList(ServiceRelease serviceRelease) {
		return super.findList(serviceRelease);
	}
	
	public Page<ServiceRelease> findPage(Page<ServiceRelease> page, ServiceRelease serviceRelease) {
		return super.findPage(page, serviceRelease);
	}
	
	@Transactional(readOnly = false)
	public void save(ServiceRelease serviceRelease) {
		super.save(serviceRelease);
	}
	
	@Transactional(readOnly = false)
	public void delete(ServiceRelease serviceRelease) {
		super.delete(serviceRelease);
	}
	
}