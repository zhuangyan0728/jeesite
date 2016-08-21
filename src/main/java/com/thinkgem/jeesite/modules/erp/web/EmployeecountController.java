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
import com.thinkgem.jeesite.modules.erp.entity.Employeecount;
import com.thinkgem.jeesite.modules.erp.service.EmployeecountService;

/**
 * 企业人员结构上报Controller
 * @author zhuangyan
 * @version 2016-08-21
 */
@Controller
@RequestMapping(value = "${adminPath}/erp/employeecount")
public class EmployeecountController extends BaseController {

	@Autowired
	private EmployeecountService employeecountService;
	
	@ModelAttribute
	public Employeecount get(@RequestParam(required=false) String id) {
		Employeecount entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = employeecountService.get(id);
		}
		if (entity == null){
			entity = new Employeecount();
		}
		return entity;
	}
	
	@RequiresPermissions("erp:employeecount:view")
	@RequestMapping(value = {"list", ""})
	public String list(Employeecount employeecount, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Employeecount> page = employeecountService.findPage(new Page<Employeecount>(request, response), employeecount); 
		model.addAttribute("page", page);
		return "modules/erp/employeecountList";
	}

	@RequiresPermissions("erp:employeecount:view")
	@RequestMapping(value = "form")
	public String form(Employeecount employeecount, Model model) {
		model.addAttribute("employeecount", employeecount);
		return "modules/erp/employeecountForm";
	}

	@RequiresPermissions("erp:employeecount:edit")
	@RequestMapping(value = "save")
	public String save(Employeecount employeecount, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, employeecount)){
			return form(employeecount, model);
		}
		employeecountService.save(employeecount);
		addMessage(redirectAttributes, "保存企业人员结构成功");
		return "redirect:"+Global.getAdminPath()+"/erp/employeecount/?repage";
	}
	
	@RequiresPermissions("erp:employeecount:edit")
	@RequestMapping(value = "commit")
	public String commit(Employeecount employeecount, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, employeecount)){
			return form(employeecount, model);
		}
		employeecount.setStatus(1);
		employeecountService.save(employeecount);
		addMessage(redirectAttributes, "企业人员结构上报");
		return "redirect:"+Global.getAdminPath()+"/erp/employeecount/?repage";
	}
	
	
	@RequiresPermissions("erp:employeecount:edit")
	@RequestMapping(value = "delete")
	public String delete(Employeecount employeecount, RedirectAttributes redirectAttributes) {
		employeecountService.delete(employeecount);
		addMessage(redirectAttributes, "删除企业人员结构成功");
		return "redirect:"+Global.getAdminPath()+"/erp/employeecount/?repage";
	}

}