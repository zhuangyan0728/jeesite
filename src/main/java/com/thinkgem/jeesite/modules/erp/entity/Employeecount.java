/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.erp.entity;

import com.thinkgem.jeesite.modules.erp.entity.CompanyInfo;

import javax.validation.constraints.NotNull;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 企业人员结构上报Entity
 * @author zhuangyan
 * @version 2016-08-21
 */
public class Employeecount extends DataEntity<Employeecount> {
	
	private static final long serialVersionUID = 1L;
	private CompanyInfo company;		// 所属企业
	private Date time;		// 填报时间
	private Integer totalsum;		// 总人数
	private Integer doctor;		// 博士人数
	private Integer master;		// 研究生人数
	private Integer bachelordegree;		// 大学本科人数
	private Integer collegeeducation;		// 大学专科
	private Integer seniorhighschool;		// 高中
	private Integer middleschool;		// 初中以下
	private Integer seniortechnicaltitles;		// 高级技术职称
	private Integer middletechnicaltitles;		// 中级技术职称
	private Integer seniortechnician;		// 高级技师
	private Integer technician;		// 技师
	private Integer seniorengineer;		// 高级工程师
	private Integer middleengineer;		// 中级工程师
	private Integer status;		// 状态
	private String reward;		// reward
	private Date beginTime;		// 开始 填报时间
	private Date endTime;		// 结束 填报时间
	
	public Employeecount() {
		super();
	}

	public Employeecount(String id){
		super(id);
	}

	@NotNull(message="所属企业不能为空")
	public CompanyInfo getCompany() {
		return company;
	}

	public void setCompany(CompanyInfo company) {
		this.company = company;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="填报时间不能为空")
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Integer getTotalsum() {
		return totalsum;
	}

	public void setTotalsum(Integer totalsum) {
		this.totalsum = totalsum;
	}
	

	public Integer getDoctor() {
		return doctor;
	}

	public void setDoctor(Integer doctor) {
		this.doctor = doctor;
	}
	
	public Integer getMaster() {
		return master;
	}

	public void setMaster(Integer master) {
		this.master = master;
	}
 
	public Integer getBachelordegree() {
		return bachelordegree;
	}

	public void setBachelordegree(Integer bachelordegree) {
		this.bachelordegree = bachelordegree;
	}
	
 
	public Integer getCollegeeducation() {
		return collegeeducation;
	}

	public void setCollegeeducation(Integer collegeeducation) {
		this.collegeeducation = collegeeducation;
	}
	
 
	public Integer getSeniorhighschool() {
		return seniorhighschool;
	}

	public void setSeniorhighschool(Integer seniorhighschool) {
		this.seniorhighschool = seniorhighschool;
	}
 
	public Integer getMiddleschool() {
		return middleschool;
	}

	public void setMiddleschool(Integer middleschool) {
		this.middleschool = middleschool;
	}
	 
	public Integer getSeniortechnicaltitles() {
		return seniortechnicaltitles;
	}

	public void setSeniortechnicaltitles(Integer seniortechnicaltitles) {
		this.seniortechnicaltitles = seniortechnicaltitles;
	}
	
	public Integer getMiddletechnicaltitles() {
		return middletechnicaltitles;
	}

	public void setMiddletechnicaltitles(Integer middletechnicaltitles) {
		this.middletechnicaltitles = middletechnicaltitles;
	}
	
	public Integer getSeniortechnician() {
		return seniortechnician;
	}

	public void setSeniortechnician(Integer seniortechnician) {
		this.seniortechnician = seniortechnician;
	}
	
	public Integer getTechnician() {
		return technician;
	}

	public void setTechnician(Integer technician) {
		this.technician = technician;
	}

	public Integer getSeniorengineer() {
		return seniorengineer;
	}

	public void setSeniorengineer(Integer seniorengineer) {
		this.seniorengineer = seniorengineer;
	}

	public Integer getMiddleengineer() {
		return middleengineer;
	}

	public void setMiddleengineer(Integer middleengineer) {
		this.middleengineer = middleengineer;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getReward() {
		return reward;
	}

	public void setReward(String reward) {
		this.reward = reward;
	}
	
	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
		
}