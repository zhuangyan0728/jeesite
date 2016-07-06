/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.erp.web;

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
import com.thinkgem.jeesite.modules.erp.service.CompanyInfoService;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 企业信息管理Controller
 * @author zhuangyan
 * @version 2016-06-26
 */
@Controller
@RequestMapping(value = "${adminPath}/erp/companyInfo")
public class CompanyInfoController extends BaseController {

	@Autowired
	private CompanyInfoService companyInfoService;
	
	@ModelAttribute
	public CompanyInfo get(@RequestParam(required=false) String id) {
		CompanyInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = companyInfoService.get(id);
		}
		if (entity == null){
			entity = new CompanyInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("erp:companyInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(CompanyInfo companyInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CompanyInfo> page = companyInfoService.findPage(new Page<CompanyInfo>(request, response), companyInfo); 
		model.addAttribute("page", page);
		return "modules/erp/companyInfoList";
	}

	@RequiresPermissions("erp:companyInfo:view")
	@RequestMapping(value = "form")
	public String form(CompanyInfo companyInfo, Model model) {
		model.addAttribute("companyInfo", companyInfo);
		return "modules/erp/companyInfoForm";
	}

	@RequiresPermissions("erp:companyInfo:edit")
	@RequestMapping(value = "save")
	public String save(CompanyInfo companyInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, companyInfo)){
			return form(companyInfo, model);
		}
		companyInfoService.save(companyInfo);
		addMessage(redirectAttributes, "保存企业信息成功");
		return "redirect:"+Global.getAdminPath()+"/erp/companyInfo/?repage";
	}
	
	@RequiresPermissions("erp:companyInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(CompanyInfo companyInfo, RedirectAttributes redirectAttributes) {
		companyInfoService.delete(companyInfo);
		addMessage(redirectAttributes, "删除企业信息成功");
		return "redirect:"+Global.getAdminPath()+"/erp/companyInfo/?repage";
	}
	
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String typeId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		CompanyInfo companyInfo = new CompanyInfo() ;
		List<CompanyInfo> list = companyInfoService.findList(companyInfo);
		for (int i=0; i<list.size(); i++){
			CompanyInfo e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("name", StringUtils.replace(e.getName(), " ", ""));
			mapList.add(map);
		}
		return mapList;
	}
	
	@ResponseBody
	@RequestMapping(value = "checkCompanyName")
	public String checkCompanyName(String companyname,String name) {
		if (companyname == companyname){
			return "true";
		}
		System.out.print(name);
		
		
		if (name == companyname){
			return "true";
		}
		return "false";
	}

}

	