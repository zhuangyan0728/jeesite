/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 通知回复Entity
 * @author henry
 * @version 2016-07-14
 */
public class OaNotifyRecordBack extends DataEntity<OaNotifyRecordBack> {
	
	private static final long serialVersionUID = 1L;
	private String nrId;		// 编号
	private String back;		// 政府再次回复“企业关于通知回复”

	private Date beginCreateDate;		// 开始 时间
	private Date endCreateDate;		// 结束 时间

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	private String file;
	public OaNotifyRecordBack() {
		super();
	}

	public OaNotifyRecordBack(String id){
		super(id);
	}

	@Length(min=1, max=64, message="编号长度必须介于 1 和 64 之间")
	public String getNrId() {
		return nrId;
	}

	public void setNrId(String nrId) {
		this.nrId = nrId;
	}
	
	public String getBack() {
		return back;
	}

	public void setBack(String back) {
		this.back = back;
	}
	
	public Date getBeginCreateDate() {
		return beginCreateDate;
	}

	public void setBeginCreateDate(Date beginCreateDate) {
		this.beginCreateDate = beginCreateDate;
	}
	
	public Date getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}
		
}