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
import com.thinkgem.jeesite.modules.erp.entity.CompanyJobneed;
import com.thinkgem.jeesite.modules.erp.service.CompanyJobneedRecommendService;
import com.thinkgem.jeesite.modules.erp.service.CompanyJobneedService;

/**
 * 第三方反馈招聘需求Controller
 * @author zhuangyan
 * @version 2016-08-01
 */
@Controller
@RequestMapping(value = "${adminPath}/erp/companyJobneedRecommend")
public class CompanyJobneedRecommendController extends BaseController {

	@Autowired
	private CompanyJobneedRecommendService companyJobneedRecommendService;
	
	@ModelAttribute
	public CompanyJobneed get(@RequestParam(required=false) String id) {
		CompanyJobneed entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = companyJobneedRecommendService.get(id);
		}
		if (entity == null){
			entity = new CompanyJobneed();
		}
		return entity;
	}
	
	@RequiresPermissions("erp:companyJobneedRecommend:view")
	@RequestMapping(value = {"list", ""})
	public String list(CompanyJobneed companyJobneed, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CompanyJobneed> page = companyJobneedRecommendService.findPage(new Page<CompanyJobneed>(request, response), companyJobneed); 
		model.addAttribute("page", page);
		return "modules/erp/companyJobneedRecommendList";
	}

	@RequiresPermissions("erp:companyJobneedRecommend:view")
	@RequestMapping(value = "form")
	public String form(CompanyJobneed companyJobneed, Model model) {
		model.addAttribute("companyJobneed", companyJobneed);
		return "modules/erp/companyJobneedRecommendForm";
	}

	@RequiresPermissions("erp:companyJobneedRecommend:edit")
	@RequestMapping(value = "save")
	public String save(CompanyJobneed companyJobneed, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, companyJobneed)){
			return form(companyJobneed, model);
		}
		companyJobneedRecommendService.save(companyJobneed);
		addMessage(redirectAttributes, "保存推荐人才信息成功");
		return "redirect:"+Global.getAdminPath()+"/erp/companyJobneedRecommend/?repage";
	}
	
	@RequiresPermissions("erp:companyJobneedRecommend:edit")
	@RequestMapping(value = "delete")
	public String delete(CompanyJobneed companyJobneed, RedirectAttributes redirectAttributes) {
		companyJobneedRecommendService.delete(companyJobneed);
		addMessage(redirectAttributes, "删除推荐人才信息成功");
		return "redirect:"+Global.getAdminPath()+"/erp/companyJobneedRecommend/?repage";
	}

}