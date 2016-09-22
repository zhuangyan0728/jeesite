/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.erp.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.modules.erp.entity.CompanyInfo;
import com.thinkgem.jeesite.modules.erp.entity.CompanyJobneed;
import com.thinkgem.jeesite.modules.erp.service.CompanyJobneedService;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 企业招聘需求管理Controller
 * @author zhuangyan
 * @version 2016-07-08
 */
@Controller
@RequestMapping(value = "${adminPath}/erp/companyJobneed")
public class CompanyJobneedController extends BaseController {

	@Autowired
	private CompanyJobneedService companyJobneedService;
	
	@ModelAttribute
	public CompanyJobneed get(@RequestParam(required=false) String id) {
		CompanyJobneed entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = companyJobneedService.get(id);
		}
		if (entity == null){
			entity = new CompanyJobneed();
			entity.setPublistime(new Date());
		}
		return entity;
	}
	
	@RequiresPermissions("erp:companyJobneed:view")
	@RequestMapping(value = {"list", ""})
	public String list(CompanyJobneed companyJobneed, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CompanyJobneed> page = companyJobneedService.findPage(new Page<CompanyJobneed>(request, response), companyJobneed); 
	
		model.addAttribute("page", page);
		return "modules/erp/companyJobneedList";
	}

	@RequiresPermissions("erp:companyJobneed:view")
	@RequestMapping(value = "form")
	public String form(CompanyJobneed companyJobneed, Model model,HttpServletRequest request) {
		model.addAttribute("companyJobneed", companyJobneed);
		if(null != request && null != request.getParameter("auditModle") && request.getParameter("auditModle").toString().equals("true")){
			model.addAttribute("auditModle", "true");
		}
		if(null != request && null != request.getAttribute("auditModle") && request.getAttribute("auditModle").toString().equals("true")){
			model.addAttribute("auditModle", "true");
		}
	
		return "modules/erp/companyJobneedForm";
	}
	
	
	@RequiresPermissions(value={"erp:companyJobneed:edit","erp:companyJobneed:audit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(CompanyJobneed companyJobneed, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		 
		if (!beanValidator(model, companyJobneed)){
			return form(companyJobneed, model,request);
		}
		if(null != companyJobneed.getAuditType() && companyJobneed.getAuditType() == 2){
			request.setAttribute("auditModle", "true");
			if(StringUtils.isBlank(companyJobneed.getAuditReason())){
				addMessage(redirectAttributes, "审核企业招聘失败，拒绝必须输入原因");
				return form(companyJobneed, model,request);
			}
		}
		if(null !=companyJobneed.getAuditType() && companyJobneed.getAuditType() == 1){
			companyJobneed.setAuditReason("");
		}
		if(null !=companyJobneed.getAuditType() && companyJobneed.getAuditType() != 0){
			companyJobneed.setAuditTime(new Date());
		}
		companyJobneedService.save(companyJobneed);
		 
		addMessage(redirectAttributes, "保存企业招聘需求成功");
	
		
		return "redirect:"+Global.getAdminPath()+"/erp/companyJobneed/?repage";
	}
	
	@RequiresPermissions("erp:companyJobneed:edit")
	@RequestMapping(value = "delete")
	public String delete(CompanyJobneed companyJobneed, RedirectAttributes redirectAttributes) {
		companyJobneedService.delete(companyJobneed);
		addMessage(redirectAttributes, "删除企业招聘需求成功");
		return "redirect:"+Global.getAdminPath()+"/erp/companyJobneed/?repage";
	}
	
	/**
	 * 导出用户数据
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("erp:companyJobneed:view")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(CompanyJobneed companyJobneed, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "企业招聘数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CompanyJobneed> page = companyJobneedService.findPage(new Page<CompanyJobneed>(request, response, -1), companyJobneed);
    		new ExportExcel("企业招聘数据", CompanyJobneed.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出招聘信息失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/erp/companyJobneed/list?repage";
    }
	
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String type, String param, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		CompanyJobneed companyJobneed = new CompanyJobneed();
		if(StringUtils.isNotBlank(param)){
			companyJobneed.setId(param);
		}
		List<CompanyJobneed> list = companyJobneedService.findList(companyJobneed);
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy年MM月dd日 " );
		for (int i=0; i<list.size(); i++){
			CompanyJobneed e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("name", e.getCompanyName() +" 人数："+e.getJobquantity().toString()+ "   发布时间："+sdf.format(e.getPublistime()));
			mapList.add(map);
		}
		return mapList;
	}
}