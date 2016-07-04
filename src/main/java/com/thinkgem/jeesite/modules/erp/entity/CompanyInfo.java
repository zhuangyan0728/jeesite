/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.erp.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 企业信息管理Entity
 * @author zhuangyan
 * @version 2016-06-26
 */
public class CompanyInfo extends DataEntity<CompanyInfo> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 企业名称
	private String organizecode;		// 组织机构代码
	private String taxcode;		// 税务登记证号
	private String registeraddress;		// 注册地址
	private String contactaddress;		// 联系地址
	private String legalperson;		// 法人
	private String phone;		// 联系电话
	
	public CompanyInfo() {
		super();
	}

	public CompanyInfo(String id){
		super(id);
	}

	@Length(min=1, max=100, message="企业名称长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=100, message="组织机构代码长度必须介于 1 和 100 之间")
	public String getOrganizecode() {
		return organizecode;
	}

	public void setOrganizecode(String organizecode) {
		this.organizecode = organizecode;
	}
	
	@Length(min=1, max=100, message="税务登记证号长度必须介于 1 和 100 之间")
	public String getTaxcode() {
		return taxcode;
	}

	public void setTaxcode(String taxcode) {
		this.taxcode = taxcode;
	}
	
	@Length(min=1, max=500, message="注册地址长度必须介于 1 和 500 之间")
	public String getRegisteraddress() {
		return registeraddress;
	}

	public void setRegisteraddress(String registeraddress) {
		this.registeraddress = registeraddress;
	}
	
	@Length(min=1, max=500, message="联系地址长度必须介于 1 和 500 之间")
	public String getContactaddress() {
		return contactaddress;
	}

	public void setContactaddress(String contactaddress) {
		this.contactaddress = contactaddress;
	}
	
	@Length(min=1, max=100, message="法人长度必须介于 1 和 100 之间")
	public String getLegalperson() {
		return legalperson;
	}

	public void setLegalperson(String legalperson) {
		this.legalperson = legalperson;
	}
	
	@Length(min=1, max=100, message="联系电话长度必须介于 1 和 100 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}