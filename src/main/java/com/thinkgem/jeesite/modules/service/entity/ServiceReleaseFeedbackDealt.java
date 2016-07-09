/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.service.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.erp.entity.CompanyInfo;

/**
 * 企业服务处理Entity
 * @author henry
 * @version 2016-07-08
 */
public class ServiceReleaseFeedbackDealt extends DataEntity<ServiceReleaseFeedbackDealt> {
	
	private static final long serialVersionUID = 1L;
	private CompanyInfo company;		// 所属公司
	private ServiceRelease serviceRelease;		// 服务名称
	private String dName;		// 服务名称
	//private String company;		// 服务企业
	private String content;		// 服务内容
	private String companyView;		// 企业意见
	private String shcedule;		// 完成情况
	private String remark;		// 备注
	private Date serviceDate;		// 服务时间
	private Date beginServiceDate;		// 开始 服务时间
	private Date endServiceDate;		// 结束 服务时间
	
	public ServiceReleaseFeedbackDealt() {
		super();
	}

	public ServiceReleaseFeedbackDealt(String id){
		super(id);
	}

	@Length(min=1, max=254, message="服务名称长度必须介于 1 和 254 之间")
	public String getDName() {
		return dName;
	}

	public void setDName(String dName) {
		this.dName = dName;
	}
	
	/*@Length(min=1, max=64, message="服务企业长度必须介于 1 和 64 之间")
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}*/
	
	@Length(min=1, max=2000, message="服务内容长度必须介于 1 和 2000 之间")
	public String getContent() {
		return content;
	}

	public ServiceRelease getServiceRelease() {
		return serviceRelease;
	}

	public void setServiceRelease(ServiceRelease serviceRelease) {
		this.serviceRelease = serviceRelease;
	}

	public CompanyInfo getCompany() {
		return company;
	}

	public void setCompany(CompanyInfo company) {
		this.company = company;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=1, max=2000, message="企业意见长度必须介于 1 和 2000 之间")
	public String getCompanyView() {
		return companyView;
	}

	public void setCompanyView(String companyView) {
		this.companyView = companyView;
	}
	
	@Length(min=1, max=64, message="完成情况长度必须介于 1 和 64 之间")
	public String getShcedule() {
		return shcedule;
	}

	public void setShcedule(String shcedule) {
		this.shcedule = shcedule;
	}
	
	@Length(min=0, max=1500, message="备注长度必须介于 0 和 1500 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(Date serviceDate) {
		this.serviceDate = serviceDate;
	}
	
	public Date getBeginServiceDate() {
		return beginServiceDate;
	}

	public void setBeginServiceDate(Date beginServiceDate) {
		this.beginServiceDate = beginServiceDate;
	}
	
	public Date getEndServiceDate() {
		return endServiceDate;
	}

	public void setEndServiceDate(Date endServiceDate) {
		this.endServiceDate = endServiceDate;
	}
		
}