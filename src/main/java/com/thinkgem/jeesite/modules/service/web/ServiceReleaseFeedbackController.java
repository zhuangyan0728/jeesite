/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.service.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.erp.entity.CompanyInfo;
import com.thinkgem.jeesite.modules.service.entity.ServiceRelease;
import com.thinkgem.jeesite.modules.service.entity.ServiceReleaseFeedback;
import com.thinkgem.jeesite.modules.service.service.ServiceReleaseFeedbackService;

/**
 * 企业回馈服务项目Controller
 * @author henry
 * @version 2016-07-07
 */
@Controller
@RequestMapping(value = "${adminPath}/service/serviceReleaseFeedback")
public class ServiceReleaseFeedbackController extends BaseController {

	@Autowired
	private ServiceReleaseFeedbackService serviceReleaseFeedbackService;
	
	@ModelAttribute
	public ServiceReleaseFeedback get(@RequestParam(required=false) String id) {
		ServiceReleaseFeedback entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = serviceReleaseFeedbackService.get(id);
		}
		if (entity == null){
			entity = new ServiceReleaseFeedback();
		}
		return entity;
	}
	
	@RequiresPermissions("service:serviceReleaseFeedback:view")
	@RequestMapping(value = {"list", ""})
	public String list(ServiceReleaseFeedback serviceReleaseFeedback, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		Page<ServiceReleaseFeedback> page = serviceReleaseFeedbackService.findPage(new Page<ServiceReleaseFeedback>(request, response), serviceReleaseFeedback); 
		model.addAttribute("page", page);
		return "modules/service/serviceReleaseFeedbackList";
	}

	@RequiresPermissions("service:serviceReleaseFeedback:view")
	@RequestMapping(value = "form")
	public String form(ServiceReleaseFeedback serviceReleaseFeedback, Model model) {
		model.addAttribute("serviceReleaseFeedback", serviceReleaseFeedback);
		return "modules/service/serviceReleaseFeedbackForm";
	}

	@RequiresPermissions("service:serviceReleaseFeedback:edit")
	@RequestMapping(value = "save")
	public String save(ServiceReleaseFeedback serviceReleaseFeedback, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, serviceReleaseFeedback)){
			return form(serviceReleaseFeedback, model);
		}
		
		serviceReleaseFeedbackService.save(serviceReleaseFeedback);
		addMessage(redirectAttributes, "保存企业回馈服务项目成功");
		return "redirect:"+Global.getAdminPath()+"/service/serviceReleaseFeedback/?repage";
	}
	
	@RequiresPermissions("service:serviceReleaseFeedback:edit")
	@RequestMapping(value = "delete")
	public String delete(ServiceReleaseFeedback serviceReleaseFeedback, RedirectAttributes redirectAttributes) {
		serviceReleaseFeedbackService.delete(serviceReleaseFeedback);
		addMessage(redirectAttributes, "删除企业回馈服务项目成功");
		return "redirect:"+Global.getAdminPath()+"/service/serviceReleaseFeedback/?repage";
	}
	
	@ResponseBody
	@RequestMapping(value = "treeDatabyRename")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String type, String param, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<ServiceReleaseFeedback> list = serviceReleaseFeedbackService.findListbyRename(param);
		for (int i=0; i<list.size(); i++){
			ServiceReleaseFeedback e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getCompany().getId());
			map.put("name", StringUtils.replace(e.getCompany().getName(), " ", ""));
			mapList.add(map);
		}
		
		return mapList;
	}
	
	
	@ResponseBody
	@RequiresPermissions("service:serviceRelease:edit")
	@RequestMapping(value = "getContentAndCompanyView")
	public Map<String, Object> getContentAndCompanyView(String releaseId,String companyId) {
		Map<String, Object> map = Maps.newHashMap();
		if (StringUtils.isNotBlank(releaseId)&&StringUtils.isNotBlank(companyId)) {
			ServiceReleaseFeedback se = new ServiceReleaseFeedback();
			CompanyInfo company=new CompanyInfo();
			ServiceRelease serviceRelease=new ServiceRelease();
			company.setId(companyId);
			serviceRelease.setId(releaseId);
			se.setCompany(company);
			se.setServiceRelease(serviceRelease);
			List<ServiceReleaseFeedback> sebs=serviceReleaseFeedbackService.findList(se);
			if(sebs.size()>0)
			{		
				map.put("content", sebs.get(0).getServiceRelease().getContent());
				map.put("view", sebs.get(0).getContent());
				
				return map;
			}
			return map;
		}
		return map;
	}

}