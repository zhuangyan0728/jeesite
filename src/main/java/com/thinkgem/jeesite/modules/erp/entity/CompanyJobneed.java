/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.erp.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 企业招聘需求管理Entity
 * @author zhuangyan
 * @version 2016-07-08
 */
public class CompanyJobneed extends DataEntity<CompanyJobneed> {
	
	private static final long serialVersionUID = 1L; 
	private CompanyInfo company;		// 所属企业
	private String sort;		// 人才分类
	private String jobskill;		// 岗位要求
	private String sex;		// 性别
	private Integer jobquantity;		// 人才数量
	private Integer educationneed;		// 学历要求
	private Integer workedyear;		// 工作年限
	private Date publistime;		// 发布时间
	private Date jointime;		// 最晚到岗时间
	private String majorneed;		// 专业要求
	
	public CompanyJobneed() {
		super();
	}

	public CompanyJobneed(String id){
		super(id);
	}

 
	public CompanyInfo getCompany() {
		return company;
	}

	public void setCompany(CompanyInfo company) {
		this.company = company;
	}
	
	@Length(min=0, max=10, message="人才分类长度必须介于 0 和 10 之间")
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	@Length(min=0, max=200, message="岗位要求长度必须介于 0 和 200 之间")
	public String getJobskill() {
		return jobskill;
	}

	public void setJobskill(String jobskill) {
		this.jobskill = jobskill;
	}
	
	@Length(min=0, max=10, message="性别长度必须介于 0 和 10 之间")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public Integer getJobquantity() {
		return jobquantity;
	}

	public void setJobquantity(Integer jobquantity) {
		this.jobquantity = jobquantity;
	}
	
	public Integer getEducationneed() {
		return educationneed;
	}

	public void setEducationneed(Integer educationneed) {
		this.educationneed = educationneed;
	}
	
	public Integer getWorkedyear() {
		return workedyear;
	}

	public void setWorkedyear(Integer workedyear) {
		this.workedyear = workedyear;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPublistime() {
		return publistime;
	}

	public void setPublistime(Date publistime) {
		this.publistime = publistime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getJointime() {
		return jointime;
	}

	public void setJointime(Date jointime) {
		this.jointime = jointime;
	}
	
	@Length(min=0, max=100, message="专业要求长度必须介于 0 和 100 之间")
	public String getMajorneed() {
		return majorneed;
	}

	public void setMajorneed(String majorneed) {
		this.majorneed = majorneed;
	}
	
}