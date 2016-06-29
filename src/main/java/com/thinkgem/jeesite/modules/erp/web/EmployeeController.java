/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.erp.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.erp.entity.Employee;
import com.thinkgem.jeesite.modules.erp.service.EmployeeService;

/**
 * 企业人才管理Controller
 * @author zhuangyan
 * @version 2016-06-27
 */
@Controller
@RequestMapping(value = "${adminPath}/erp/employee")
public class EmployeeController extends BaseController {

	@Autowired
	private EmployeeService employeeService;
	
	@ModelAttribute
	public Employee get(@RequestParam(required=false) String id) {
		Employee entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = employeeService.get(id);
		}
		if (entity == null){
			entity = new Employee();
		}
		return entity;
	}
	
	@RequiresPermissions("erp:employee:view")
	@RequestMapping(value = {"list", ""})
	public String list(Employee employee, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Employee> page = employeeService.findPage(new Page<Employee>(request, response), employee); 
		model.addAttribute("page", page);
		return "modules/erp/employeeList";
	}

	@RequiresPermissions("erp:employee:view")
	@RequestMapping(value = "form")
	public String form(Employee employee, Model model) {
		model.addAttribute("employee", employee);
		return "modules/erp/employeeForm";
	}

	@RequiresPermissions("erp:employee:edit")
	@RequestMapping(value = "save")
	public String save(Employee employee, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, employee)){
			return form(employee, model);
		}
		employeeService.save(employee);
		addMessage(redirectAttributes, "保存企业人才信息成功");
		return "redirect:"+Global.getAdminPath()+"/erp/employee/?repage";
	}
	
	@RequiresPermissions("erp:employee:edit")
	@RequestMapping(value = "delete")
	public String delete(Employee employee, RedirectAttributes redirectAttributes) {
		employeeService.delete(employee);
		addMessage(redirectAttributes, "删除企业人才信息成功");
		return "redirect:"+Global.getAdminPath()+"/erp/employee/?repage";
	}

}