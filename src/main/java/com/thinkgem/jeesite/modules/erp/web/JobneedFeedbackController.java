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
import com.thinkgem.jeesite.modules.erp.entity.JobneedFeedback;
import com.thinkgem.jeesite.modules.erp.service.JobneedFeedbackService;

/**
 * 反馈招聘需求Controller
 * @author zhuangyan
 * @version 2016-07-10
 */
@Controller
@RequestMapping(value = "${adminPath}/erp/jobneedFeedback")
public class JobneedFeedbackController extends BaseController {

	@Autowired
	private JobneedFeedbackService jobneedFeedbackService;
	
	@ModelAttribute
	public JobneedFeedback get(@RequestParam(required=false) String id) {
		JobneedFeedback entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = jobneedFeedbackService.get(id);
		}
		if (entity == null){
			entity = new JobneedFeedback();
		}
		return entity;
	}
	
	@RequiresPermissions("erp:jobneedFeedback:view")
	@RequestMapping(value = {"list", ""})
	public String list(JobneedFeedback jobneedFeedback, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<JobneedFeedback> page = jobneedFeedbackService.findPage(new Page<JobneedFeedback>(request, response), jobneedFeedback); 
		model.addAttribute("page", page);
		return "modules/erp/jobneedFeedbackList";
	}

	@RequiresPermissions("erp:jobneedFeedback:view")
	@RequestMapping(value = "form")
	public String form(JobneedFeedback jobneedFeedback, Model model) {
		model.addAttribute("jobneedFeedback", jobneedFeedback);
		return "modules/erp/jobneedFeedbackForm";
	}

	@RequiresPermissions("erp:jobneedFeedback:edit")
	@RequestMapping(value = "save")
	public String save(JobneedFeedback jobneedFeedback, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, jobneedFeedback)){
			return form(jobneedFeedback, model);
		}
		jobneedFeedbackService.save(jobneedFeedback);
		addMessage(redirectAttributes, "保存反馈信息成功");
		return "redirect:"+Global.getAdminPath()+"/erp/jobneedFeedback/?repage";
	}
	
	@RequiresPermissions("erp:jobneedFeedback:edit")
	@RequestMapping(value = "delete")
	public String delete(JobneedFeedback jobneedFeedback, RedirectAttributes redirectAttributes) {
		jobneedFeedbackService.delete(jobneedFeedback);
		addMessage(redirectAttributes, "删除反馈信息成功");
		return "redirect:"+Global.getAdminPath()+"/erp/jobneedFeedback/?repage";
	}

}