/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.service.web;

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
import com.thinkgem.jeesite.modules.service.entity.ServiceReleaseFeedbackDealt;
import com.thinkgem.jeesite.modules.service.service.ServiceReleaseFeedbackDealtService;

/**
 * 企业服务处理Controller
 * @author henry
 * @version 2016-07-08
 */
@Controller
@RequestMapping(value = "${adminPath}/service/serviceReleaseFeedbackDealt")
public class ServiceReleaseFeedbackDealtController extends BaseController {

	@Autowired
	private ServiceReleaseFeedbackDealtService serviceReleaseFeedbackDealtService;
	
	@ModelAttribute
	public ServiceReleaseFeedbackDealt get(@RequestParam(required=false) String id) {
		ServiceReleaseFeedbackDealt entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = serviceReleaseFeedbackDealtService.get(id);
		}
		if (entity == null){
			entity = new ServiceReleaseFeedbackDealt();
		}
		return entity;
	}
	
	@RequiresPermissions("service:serviceReleaseFeedbackDealt:view")
	@RequestMapping(value = {"list", ""})
	public String list(ServiceReleaseFeedbackDealt serviceReleaseFeedbackDealt, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ServiceReleaseFeedbackDealt> page = serviceReleaseFeedbackDealtService.findPage(new Page<ServiceReleaseFeedbackDealt>(request, response), serviceReleaseFeedbackDealt); 
		model.addAttribute("page", page);
		return "modules/service/serviceReleaseFeedbackDealtList";
	}

	@RequiresPermissions("service:serviceReleaseFeedbackDealt:view")
	@RequestMapping(value = "form")
	public String form(ServiceReleaseFeedbackDealt serviceReleaseFeedbackDealt, Model model) {
		model.addAttribute("serviceReleaseFeedbackDealt", serviceReleaseFeedbackDealt);
		return "modules/service/serviceReleaseFeedbackDealtForm";
	}

	@RequiresPermissions("service:serviceReleaseFeedbackDealt:edit")
	@RequestMapping(value = "save")
	public String save(ServiceReleaseFeedbackDealt serviceReleaseFeedbackDealt, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, serviceReleaseFeedbackDealt)){
			return form(serviceReleaseFeedbackDealt, model);
		}
		serviceReleaseFeedbackDealtService.save(serviceReleaseFeedbackDealt);
		addMessage(redirectAttributes, "保存企业服务处理成功");
		return "redirect:"+Global.getAdminPath()+"/service/serviceReleaseFeedbackDealt/?repage";
	}
	
	@RequiresPermissions("service:serviceReleaseFeedbackDealt:edit")
	@RequestMapping(value = "delete")
	public String delete(ServiceReleaseFeedbackDealt serviceReleaseFeedbackDealt, RedirectAttributes redirectAttributes) {
		serviceReleaseFeedbackDealtService.delete(serviceReleaseFeedbackDealt);
		addMessage(redirectAttributes, "删除企业服务处理成功");
		return "redirect:"+Global.getAdminPath()+"/service/serviceReleaseFeedbackDealt/?repage";
	}


}