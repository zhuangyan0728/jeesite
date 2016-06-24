/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.web;

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

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.oa.entity.OaNotify;
import com.thinkgem.jeesite.modules.oa.entity.hr_users;
import com.thinkgem.jeesite.modules.oa.service.OaNotifyService;

/**
 * 通知通告Controller
 * @author ThinkGem
 * @version 2014-05-16
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaNotify")
public class OaNotifyController extends BaseController {

	@Autowired
	private OaNotifyService oaNotifyService;
	
	@ModelAttribute
	public hr_users get(@RequestParam(required=false) String id) {
		hr_users entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaNotifyService.get(id);
		}
		if (entity == null){
			entity = new hr_users();
		}
		return entity;
	}
	
	/*
	 * 查询人事专员List
	 */
	@RequiresPermissions("oa:oaNotify:view")
	@RequestMapping(value = {"list", ""})
	public String list(hr_users hr_users, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<hr_users> page = oaNotifyService.find(new Page<hr_users>(request, response), hr_users);
		model.addAttribute("page", page);
		return "modules/oa/oaNotifyList";
	}

	/*@RequiresPermissions("oa:oaNotify:view")
	@RequestMapping(value = "form")
	public String form(OaNotify oaNotify, Model model) {
		if (StringUtils.isNotBlank(oaNotify.getId())){
			oaNotify = oaNotifyService.getRecordList(oaNotify);
		}
		model.addAttribute("oaNotify", oaNotify);
		return "modules/oa/oaNotifyForm";
	}*/
	
	/*
	 * 人事专员详细信息view--带编辑
	 */
	@RequiresPermissions("oa:oaNotify:view")
	@RequestMapping(value = "form")
	public String form(hr_users hr_users, Model model) {		
		hr_users = oaNotifyService.get(hr_users);	
		if(hr_users==null)
		{
			hr_users=new hr_users();
		}
		model.addAttribute("hr_users", hr_users);
		return "modules/oa/oaNotifyForm";
	}

	/*
	 * 人事专员详细信息--保存
	 */
	@RequiresPermissions("oa:oaNotify:edit")
	@RequestMapping(value = "save")
	public String save(hr_users hr_users, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, hr_users)){
			return form(hr_users, model);
		}
		/*// 如果是修改，则状态为已发布，则不能再进行操作
		if (StringUtils.isNotBlank(oaNotify.getId())){
			OaNotify e = oaNotifyService.get(oaNotify.getId());
			if ("1".equals(e.getStatus())){
				addMessage(redirectAttributes, "已发布，不能操作！");
				return "redirect:" + adminPath + "/oa/oaNotify/form?id="+oaNotify.getId();
			}
		}*/
		oaNotifyService.save(hr_users);
		addMessage(redirectAttributes, "保存成功");
		return "redirect:" + adminPath + "/oa/oaNotify/?repage";
	}
	
	/*
	 * 人事专员详细信息--删除
	 */
	@RequiresPermissions("oa:oaNotify:edit")
	@RequestMapping(value = "delete")
	public String delete(hr_users hr_users, RedirectAttributes redirectAttributes) {
		oaNotifyService.delete(hr_users);
		addMessage(redirectAttributes, "删除通知成功");
		return "redirect:" + adminPath + "/oa/oaNotify/?repage";
	}
	
	/*
	 * 【查询】
	 */
	@RequestMapping(value = "self")
	public String selfList(hr_users hr_users, HttpServletRequest request, HttpServletResponse response, Model model) {
		hr_users.setSelf(true);
		Page<hr_users> page = oaNotifyService.find(new Page<hr_users>(request, response), hr_users);
		model.addAttribute("page", page);
		return "modules/oa/oaNotifyList";
	}

	/**
	 * 我的通知列表-数据
	 */
	/*@RequiresPermissions("oa:oaNotify:view")
	@RequestMapping(value = "selfData")
	@ResponseBody
	public Page<OaNotify> listData(OaNotify oaNotify, HttpServletRequest request, HttpServletResponse response, Model model) {
		oaNotify.setSelf(true);
		Page<OaNotify> page = oaNotifyService.find(new Page<OaNotify>(request, response), oaNotify);
		return page;
	}*/
	
	/*
	 * 人事专员详细信息view--不编辑
	 */
	@RequestMapping(value = "view")
	public String view(OaNotify oaNotify, Model model) {
		if (StringUtils.isNotBlank(oaNotify.getId())){
			//oaNotifyService.updateReadFlag(oaNotify);
			//oaNotify = oaNotifyService.getRecordList(oaNotify);
			model.addAttribute("oaNotify", oaNotify);
			return "modules/oa/oaNotifyForm";
		}
		return "redirect:" + adminPath + "/oa/oaNotify/self?repage";
	}

	/**
	 * 查看我的通知-数据
	 */
	@RequestMapping(value = "viewData")
	@ResponseBody
	public OaNotify viewData(OaNotify oaNotify, Model model) {
		if (StringUtils.isNotBlank(oaNotify.getId())){
			//oaNotifyService.updateReadFlag(oaNotify);
			return oaNotify;
		}
		return null;
	}
	
	/**
	 * 查看我的通知-发送记录
	 */
	@RequestMapping(value = "viewRecordData")
	@ResponseBody
	public OaNotify viewRecordData(OaNotify oaNotify, Model model) {
		if (StringUtils.isNotBlank(oaNotify.getId())){
			//oaNotify = oaNotifyService.getRecordList(oaNotify);
			return oaNotify;
		}
		return null;
	}
	
	/**
	 * 获取我的通知数目
	 *//*
	@RequestMapping(value = "self/count")
	@ResponseBody
	public String selfCount(OaNotify oaNotify, Model model) {
		oaNotify.setSelf(true);
		oaNotify.setReadFlag("0");
		return String.valueOf(oaNotifyService.findCount(oaNotify));
	}*/
}