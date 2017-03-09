/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.erp.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 第三方反馈招聘需求Entity
 * @author zhuangyan
 * @version 2016-08-01
 */
public class CompanyJobneedRecommend extends DataEntity<CompanyJobneedRecommend> {
	
	private static final long serialVersionUID = 1L;
	private String servercompany;		// 三方人才服务企业
	private CompanyJobneed jobneedid;		// 企业招聘需求 父类
	private String name;		// 岗位要求
	private String sex;		// 性别
	private String education;		// 学历
	private String idcard;		// 身份证号
	private String telephone;		// 联系电话
	private String attactpath;		// 附件路径
	private Date publistime;		// 发布时间
	
	public CompanyJobneedRecommend() {
		super();
	}

	public CompanyJobneedRecommend(String id){
		super(id);
	}

	public CompanyJobneedRecommend(CompanyJobneed jobneedid){
		this.jobneedid = jobneedid;
	}

	@Length(min=0, max=64, message="三方人才服务企业长度必须介于 0 和 64 之间")
	public String getServercompany() {
		return servercompany;
	}

	public void setServercompany(String servercompany) {
		this.servercompany = servercompany;
	}
	
	@Length(min=0, max=64, message="企业招聘需求长度必须介于 0 和 64 之间")
	public CompanyJobneed getJobneedid() {
		return jobneedid;
	}

	public void setJobneedid(CompanyJobneed jobneedid) {
		this.jobneedid = jobneedid;
	}
	
	@Length(min=0, max=200, message="岗位要求长度必须介于 0 和 200 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=10, message="性别长度必须介于 1 和 10 之间")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Length(min=1, max=10, message="学历长度必须介于 1 和 10 之间")
	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}
	
	@Length(min=1, max=50, message="身份证号长度必须介于 1 和 50 之间")
	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	
	@Length(min=1, max=50, message="联系电话长度必须介于 1 和 50 之间")
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	@Length(min=0, max=200, message="附件路径长度必须介于 0 和 200 之间")
	public String getAttactpath() {
		return attactpath;
	}

	public void setAttactpath(String attactpath) {
		this.attactpath = attactpath;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@NotNull(message="发布时间不能为空")
	public Date getPublistime() {
		return publistime;
	}

	public void setPublistime(Date publistime) {
		this.publistime = publistime;
	}
	
}