/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.service;

import java.util.ArrayList;
import java.util.List;

import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.oa.entity.OaNotifyRecordBack;
import com.thinkgem.jeesite.modules.oa.dao.OaNotifyRecordBackDao;

/**
 * 通知回复Service
 * @author henry
 * @version 2016-07-14
 */
@Service
@Transactional(readOnly = true)
public class OaNotifyRecordBackService extends CrudService<OaNotifyRecordBackDao, OaNotifyRecordBack> {

	@Autowired
	private OaNotifyRecordBackDao oaNotifyRecordBackDao;
	
	public OaNotifyRecordBack get(String id) {
		return super.get(id);
	}
	
	public List<OaNotifyRecordBack> findList(OaNotifyRecordBack oaNotifyRecordBack) {
        return super.findList(oaNotifyRecordBack);
	}
	
	public Page<OaNotifyRecordBack> findPage(Page<OaNotifyRecordBack> page, OaNotifyRecordBack oaNotifyRecordBack) {
		return super.findPage(page, oaNotifyRecordBack);
	}
	
	@Transactional(readOnly = false)
	public void save(OaNotifyRecordBack oaNotifyRecordBack) {
		super.save(oaNotifyRecordBack);
	}
	
	@Transactional(readOnly = false)
	public void delete(OaNotifyRecordBack oaNotifyRecordBack) {
		super.delete(oaNotifyRecordBack);
	}
	
	/**
	 * 获取政府回复企业关于通知的疑问（最新记录）
	 */
	/*public List<OaNotifyRecordBack> getNotifyRecordBack(OaNotifyRecordBack oaNotifyRecordBack) {
		return oaNotifyRecordBackDao.findList(oaNotifyRecordBack);
	}
	*/
	/**
	 * 保存反馈记录  
	 */
	@Transactional(readOnly = false)
	public int saveRecordBack(OaNotifyRecordBack oaNotifyRecordBack) {
		int rd=0;
		OaNotifyRecordBack param = new OaNotifyRecordBack();
		param.setNrId(oaNotifyRecordBack.getNrId());
		param.setCreateBy(oaNotifyRecordBack.getCreateBy());

		List<OaNotifyRecordBack> result = oaNotifyRecordBackDao.findList(param);
		if(null != result &&result.size()>0){
			rd=result.size();
		}
		if(rd>0){
			rd=oaNotifyRecordBackDao.update(oaNotifyRecordBack);
		}else {
			rd=oaNotifyRecordBackDao.insert(oaNotifyRecordBack);
		}

		return rd;
	}
	
	/**
	 * 保存反馈记录  
	 */
	@Transactional(readOnly = false)
	public int updateRecordBack(OaNotifyRecordBack oaNotifyRecordBack) {
		int rd=0;
		rd=oaNotifyRecordBackDao.update(oaNotifyRecordBack);
		return rd;
	}
}