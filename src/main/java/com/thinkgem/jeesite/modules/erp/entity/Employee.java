/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.erp.entity;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.erp.service.CompanyInfoService;
import com.thinkgem.jeesite.modules.erp.service.EmployeeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 企业人才信息管理Entity
 * @author zhuangyan
 * @version 2016-07-07
 */
public class Employee extends DataEntity<Employee> {
	@Autowired
	private CompanyInfoService companyInfoService;
	
	private static final long serialVersionUID = 1L;
	private CompanyInfo company;		// 所属公司
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
	private String residenceno; //居住证号码
	private Integer ifquite;		// 是否离职
	private Date validity;		// 居住证有效期
	private String integral;		// 居住积分
	private String virtualintegral;		// 虚拟打分
	private String situation;		// 子女入学情况
	private Date qutietime;		// 离职时间
	private Integer ifsenioremp;		// 是否高端人才
	private String reward;		// 奖励情况
	private String birthday;		// 出生日期
	
	public Employee() {
		super();
	}

	public Employee(String id){
		super(id);
	}

	public CompanyInfo getCompany() {
		return company;
	}

	public void setCompany(CompanyInfo company) {
		this.company = company;
	}
	
	@ExcelField(title="归属公司", align=2, sort=10)
	public String getCompanyName() {
		return company.getName();
	}
	
	//@ExcelField(title="归属公司", align=2, sort=10)
	public void setCompanyName(String name) throws Exception {

		this.company = UserUtils.getCompanyInfoList(name);
		this.company.setName(name);
	}
	
	@Length(min=1, max=200, message="姓名长度必须介于 1 和 200 之间")
	@ExcelField(title="姓名", align=2, sort=10)
	@NotNull(message="姓名不能为空")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull(message="性别不能为空")
	@ExcelField(title="性别", align=2, sort=30, dictType="sex")
	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}
	
	@Length(min=1, max=100, message="身份证号长度必须介于 1 和 100 之间")
	@ExcelField(title="身份证号", align=2, sort=10)
	@NotNull(message="身份证号不能为空")
	public String getIdentityno() {
		return identityno;
	}

	public void setIdentityno(String identityno) {
		this.identityno = identityno;
	}
	
	@Length(min=0, max=200, message="籍贯长度必须介于 0 和 200 之间")
	@ExcelField(title="籍贯", align=2, sort=10)
	public String getNativeplace() {
		return nativeplace;
	}

	public void setNativeplace(String nativeplace) {
		this.nativeplace = nativeplace;
	}
	
	/*@NotNull(message="居住地不能为空")*/
	@ExcelField(title="居住地", align=2, sort=30, dictType="residence_place")
	public Integer getResidenceplace() {
		return residenceplace;
	}

	public void setResidenceplace(Integer residenceplace) {
		this.residenceplace = residenceplace;
	}
	
	/*@NotNull(message="婚姻状况不能为空")*/
	@ExcelField(title="婚姻状况", align=2, sort=30, dictType="marital_status")
	public Integer getMartitalstatus() {
		return martitalstatus;
	}

	public void setMartitalstatus(Integer martitalstatus) {
		this.martitalstatus = martitalstatus;
	}
	
	@NotNull(message="学历不能为空")
	@ExcelField(title="学历", align=2, sort=30, dictType="education")
	public Integer getEducation() {
		return education;
	}

	public void setEducation(Integer education) {
		this.education = education;
	}
	
	@Length(min=0, max=100, message="职称长度必须介于 0 和 100 之间")
	@ExcelField(title="职称", align=2, sort=30)
	public String getWorktitle() {
		return worktitle;
	}

	public void setWorktitle(String worktitle) {
		this.worktitle = worktitle;
	}
	
	@Length(min=0, max=100, message="职务长度必须介于 0 和 100 之间")
	@ExcelField(title="职务", align=2, sort=30)
	public String getWorkpositon() {
		return workpositon;
	}

	public void setWorkpositon(String workpositon) {
		this.workpositon = workpositon;
	}
	
	@Length(min=0, max=50, message="薪酬状况长度必须介于 0 和 50 之间")
	@ExcelField(title="薪酬", align=2, sort=30)
	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}
	
	/*@NotNull(message="人才分类不能为空")*/
	@ExcelField(title="人才分类", align=2, sort=30,dictType="talent_sort")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	/*@NotNull(message="是否关键岗位不能为空")*/
	@ExcelField(title="是否关键岗位", align=2, sort=30,dictType="yes_no")
	public Integer getIfkey() {
		return ifkey;
	}

	public void setIfkey(Integer ifkey) {
		this.ifkey = ifkey;
	}
	
	/*@NotNull(message="是否申领居住证不能为空")*/
	@ExcelField(title="是否申领居住证", align=2, sort=30,dictType="yes_no")
	public Integer getIfresidencecard() {
		return ifresidencecard;
	}

	public void setIfresidencecard(Integer ifresidencecard) {
		this.ifresidencecard = ifresidencecard;
	}
	
	//@Length(min=0, max=100, message="居住证号码长度必须介于 0 和 100 之间")
	public String getResidenceno() {
		return residenceno;
	}

	public void setResidenceno(String residenceno) {
		this.residenceno = residenceno;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getValidity() {
		return validity;
	}

	public void setValidity(Date validity) {
		this.validity = validity;
	}
	
	//@Length(min=0, max=10, message="居住积分长度必须介于 0 和 10 之间")
	public String getIntegral() {
		return integral;
	}

	public void setIntegral(String integral) {
		this.integral = integral;
	}
	
	//@Length(min=0, max=10, message="虚拟打分长度必须介于 0 和 10 之间")
	public String getVirtualintegral() {
		return virtualintegral;
	}

	public void setVirtualintegral(String virtualintegral) {
		this.virtualintegral = virtualintegral;
	}
	
	//@Length(min=0, max=500, message="子女入学情况长度必须介于 0 和 500 之间")
	public String getSituation() {
		return situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}
	
	/*@NotNull(message="是否离职不能为空")*/
	@ExcelField(title="是否在职", align=2, sort=30,dictType="if_quite")
	public Integer getIfquite() {
		return ifquite;
	}

	public void setIfquite(Integer ifquite) {
		this.ifquite = ifquite;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getQutietime() {
		return qutietime;
	}

	public void setQutietime(Date qutietime) {
		this.qutietime = qutietime;
	}
	
	/*@NotNull(message="是否高端人才不能为空")*/
	@ExcelField(title="是否高端人才", align=2, sort=30,dictType="yes_no")
	public Integer getIfsenioremp() {
		return ifsenioremp;
	}

	public void setIfsenioremp(Integer ifsenioremp) {
		this.ifsenioremp = ifsenioremp;
	}
	
	@Length( max=200, message="奖励情况长度必须介于 0 和 200 之间")
	public String getReward() {
		return reward;
	}

	public void setReward(String reward) {
		this.reward = reward;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	
	
}