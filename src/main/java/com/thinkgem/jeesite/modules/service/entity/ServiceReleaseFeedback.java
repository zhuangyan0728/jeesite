/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.service.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.erp.entity.CompanyInfo;

/**
 * 企业回馈服务项目Entity
 * @author henry
 * @version 2016-07-07
 */
public class ServiceReleaseFeedback extends DataEntity<ServiceReleaseFeedback> {
	
	private static final long serialVersionUID = 1L;
	private CompanyInfo company;		// 所属公司
	private String reId;		// 项目编号
	private ServiceRelease serviceRelease;		// 服务名称
	//private String company;		// 反馈公司
	private String content;		// 反馈内容
	private String contacts;		// 联系人
	private String telephone;		// 联系电话
	private Date beginUpdateDate;		// 开始 发布时间
	private Date endUpdateDate;		// 结束 发布时间
	
	public ServiceReleaseFeedback() {
		super();
	}

	public ServiceReleaseFeedback(String id){
		super(id);
	}

	@Length(min=1, max=64, message="项目编号长度必须介于 1 和 64 之间")
	public String getReId() {
		return reId;
	}

	public void setReId(String reId) {
		this.reId = reId;
	}
	
	/*@Length(min=1, max=254, message="服务名称长度必须介于 1 和 254 之间")
	public String getReName() {
		return reName;
	}

	public void setReName(String reName) {
		this.reName = reName;
	}*/
	
	public CompanyInfo getCompany() {
		return company;
	}

	public ServiceRelease getServiceRelease() {
		return serviceRelease;
	}

	public void setServiceRelease(ServiceRelease serviceRelease) {
		this.serviceRelease = serviceRelease;
	}

	public void setCompany(CompanyInfo company) {
		this.company = company;
	}
	
	@Length(min=1, max=2000, message="反馈内容长度必须介于 1 和 2000 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=1, max=50, message="联系人长度必须介于 1 和 50 之间")
	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	
	@Length(min=1, max=64, message="联系电话长度必须介于 1 和 64 之间")
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public Date getBeginUpdateDate() {
		return beginUpdateDate;
	}

	public void setBeginUpdateDate(Date beginUpdateDate) {
		this.beginUpdateDate = beginUpdateDate;
	}
	
	public Date getEndUpdateDate() {
		return endUpdateDate;
	}

	public void setEndUpdateDate(Date endUpdateDate) {
		this.endUpdateDate = endUpdateDate;
	}
		
}