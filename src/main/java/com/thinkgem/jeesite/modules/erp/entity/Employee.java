/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.erp.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 企业人才管理Entity
 * @author zhuangyan
 * @version 2016-06-27
 */
public class Employee extends DataEntity<Employee> {
	
	private static final long serialVersionUID = 1L;
	private String companyid;		// 所属公司
	private String name;		// 姓名
	private Integer sex;		// 性别
	private String identityno;		// 身份证号
	private String nativeplace;		// 籍贯
	private Integer residenceplace;		// 居住地
	private Integer martitalstatus;		// 婚姻状况
	private Integer education;		// 学历
	private String worktitle;		// 职称
	private String workpositon;		// 职务
	private String salary;		// 薪酬状况
	private Integer sort;		// 人才分类
	private Integer ifkey;		// 是否关键岗位
	private Integer ifresidencecard;		// 是否申领居住证
	private Integer ifquite;		// 是否离职
	private Date qutietime;		// 离职时间
	private Integer ifsenioremp;		// 是否高端人才
	private String reward;		// 奖励情况
	
	public Employee() {
		super();
	}

	public Employee(String id){
		super(id);
	}

	@Length(min=1, max=64, message="所属公司长度必须介于 1 和 64 之间")
	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}
	
	@Length(min=1, max=200, message="姓名长度必须介于 1 和 200 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull(message="性别不能为空")
	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}
	
	@Length(min=1, max=100, message="身份证号长度必须介于 1 和 100 之间")
	public String getIdentityno() {
		return identityno;
	}

	public void setIdentityno(String identityno) {
		this.identityno = identityno;
	}
	
	@Length(min=1, max=200, message="籍贯长度必须介于 1 和 200 之间")
	public String getNativeplace() {
		return nativeplace;
	}

	public void setNativeplace(String nativeplace) {
		this.nativeplace = nativeplace;
	}
	
	@NotNull(message="居住地不能为空")
	public Integer getResidenceplace() {
		return residenceplace;
	}

	public void setResidenceplace(Integer residenceplace) {
		this.residenceplace = residenceplace;
	}
	
	@NotNull(message="婚姻状况不能为空")
	public Integer getMartitalstatus() {
		return martitalstatus;
	}

	public void setMartitalstatus(Integer martitalstatus) {
		this.martitalstatus = martitalstatus;
	}
	
	@NotNull(message="学历不能为空")
	public Integer getEducation() {
		return education;
	}

	public void setEducation(Integer education) {
		this.education = education;
	}
	
	@Length(min=1, max=100, message="职称长度必须介于 1 和 100 之间")
	public String getWorktitle() {
		return worktitle;
	}

	public void setWorktitle(String worktitle) {
		this.worktitle = worktitle;
	}
	
	@Length(min=1, max=100, message="职务长度必须介于 1 和 100 之间")
	public String getWorkpositon() {
		return workpositon;
	}

	public void setWorkpositon(String workpositon) {
		this.workpositon = workpositon;
	}
	
	@Length(min=1, max=50, message="薪酬状况长度必须介于 1 和 50 之间")
	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}
	
	@NotNull(message="人才分类不能为空")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	@NotNull(message="是否关键岗位不能为空")
	public Integer getIfkey() {
		return ifkey;
	}

	public void setIfkey(Integer ifkey) {
		this.ifkey = ifkey;
	}
	
	@NotNull(message="是否申领居住证不能为空")
	public Integer getIfresidencecard() {
		return ifresidencecard;
	}

	public void setIfresidencecard(Integer ifresidencecard) {
		this.ifresidencecard = ifresidencecard;
	}
	
	@NotNull(message="是否离职不能为空")
	public Integer getIfquite() {
		return ifquite;
	}

	public void setIfquite(Integer ifquite) {
		this.ifquite = ifquite;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getQutietime() {
		return qutietime;
	}

	public void setQutietime(Date qutietime) {
		this.qutietime = qutietime;
	}
	
	@NotNull(message="是否高端人才不能为空")
	public Integer getIfsenioremp() {
		return ifsenioremp;
	}

	public void setIfsenioremp(Integer ifsenioremp) {
		this.ifsenioremp = ifsenioremp;
	}
	
	@Length(min=0, max=200, message="奖励情况长度必须介于 0 和 200 之间")
	public String getReward() {
		return reward;
	}

	public void setReward(String reward) {
		this.reward = reward;
	}
	
}