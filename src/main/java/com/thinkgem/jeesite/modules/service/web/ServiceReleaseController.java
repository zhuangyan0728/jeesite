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
import com.thinkgem.jeesite.modules.service.entity.ServiceRelease;
import com.thinkgem.jeesite.modules.service.service.ServiceReleaseService;

/**
 * 发布服务Controller
 * @author henry
 * @version 2016-07-07
 */
@Controller
@RequestMapping(value = "${adminPath}/service/serviceRelease")
public class ServiceReleaseController extends BaseController {

	@Autowired
	private ServiceReleaseService serviceReleaseService;
	
	@ModelAttribute
	public ServiceRelease get(@RequestParam(required=false) String id) {
		ServiceRelease entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = serviceReleaseService.get(id);
		}
		if (entity == null){
			entity = new ServiceRelease();
		}
		return entity;
	}
	
	@RequiresPermissions("service:serviceRelease:view")
	@RequestMapping(value = {"list", ""})
	public String list(ServiceRelease serviceRelease, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		Page<ServiceRelease> page = serviceReleaseService.findPage(new Page<ServiceRelease>(request, response), serviceRelease); 
		model.addAttribute("page", page);
		return "modules/service/serviceReleaseList";
	}

	/**
	 * 验证标题是否重复
	 */
	@ResponseBody
	@RequiresPermissions("service:serviceRelease:edit")
	@RequestMapping(value = "checkTitle")
	public String checkTitle(String name) {
		if (name !=null && !"".equals(name)) {
			ServiceRelease se = new ServiceRelease();
			se.setName(name);
			List<ServiceRelease> sels=serviceReleaseService.findList(se);
			if(sels.size()>0)
			{
				return "false";
			}
			return "true";
		}
		return "false";
	}
	
	@RequiresPermissions("service:serviceRelease:view")
	@RequestMapping(value = "form")
	public String form(ServiceRelease serviceRelease, Model model) {
		model.addAttribute("serviceRelease", serviceRelease);
		return "modules/service/serviceReleaseForm";
	}

	@RequiresPermissions("service:serviceRelease:edit")
	@RequestMapping(value = "save")
	public String save(ServiceRelease serviceRelease, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, serviceRelease)){
			return form(serviceRelease, model);
		}
		serviceReleaseService.save(serviceRelease);
		addMessage(redirectAttributes, "保存发布服务成功");
		return "redirect:"+Global.getAdminPath()+"/service/serviceRelease/?repage";
	}
	
	@RequiresPermissions("service:serviceRelease:edit")
	@RequestMapping(value = "delete")
	public String delete(ServiceRelease serviceRelease, RedirectAttributes redirectAttributes) {
		serviceReleaseService.delete(serviceRelease);
		addMessage(redirectAttributes, "删除发布服务成功");
		return "redirect:"+Global.getAdminPath()+"/service/serviceRelease/?repage";
	}
	
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String typeId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		ServiceRelease serviceRelease = new ServiceRelease() ;
		List<ServiceRelease> list = serviceReleaseService.findList(serviceRelease);
		for (int i=0; i<list.size(); i++){
			ServiceRelease e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("name", StringUtils.replace(e.getName(), " ", ""));
			mapList.add(map);
		}
		
		return mapList;
	}

}