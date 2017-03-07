/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.web;

import java.io.UnsupportedEncodingException;
import java.util.Date;
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

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.oa.dao.OaNotifyDao;
import com.thinkgem.jeesite.modules.oa.dao.OaNotifyRecordBackDao;
import com.thinkgem.jeesite.modules.oa.entity.OaNotify;
import com.thinkgem.jeesite.modules.oa.entity.OaNotifyRecordBack;
import com.thinkgem.jeesite.modules.oa.service.OaNotifyRecordBackService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 通知回复Controller
 * @author henry
 * @version 2016-07-14
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaNotifyRecordBack")
public class OaNotifyRecordBackController extends BaseController {

	@Autowired
	private OaNotifyRecordBackService oaNotifyRecordBackService;
	private static OaNotifyDao  oaNotifyDao= SpringContextHolder.getBean(OaNotifyDao.class);
	
	private static OaNotifyRecordBackDao  oaNotifyRecordBackDao= SpringContextHolder.getBean(OaNotifyRecordBackDao.class);	
	
	@ModelAttribute
	public OaNotifyRecordBack get(@RequestParam(required=false) String id) {
		OaNotifyRecordBack entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oaNotifyRecordBackService.get(id);
		}
		if (entity == null){
			entity = new OaNotifyRecordBack();
		}
		return entity;
	}
	
	@RequiresPermissions("oa:oaNotifyRecordBack:view")
	@RequestMapping(value = {"list", ""})
	public String list(OaNotifyRecordBack oaNotifyRecordBack, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OaNotifyRecordBack> page = oaNotifyRecordBackService.findPage(new Page<OaNotifyRecordBack>(request, response), oaNotifyRecordBack); 
		model.addAttribute("page", page);
		return "modules/oa/oaNotifyRecordBackList";
	}

	@RequiresPermissions("oa:oaNotifyRecordBack:view")
	@RequestMapping(value = "form")
	public String form(OaNotifyRecordBack oaNotifyRecordBack, Model model) {
		model.addAttribute("oaNotifyRecordBack", oaNotifyRecordBack);
		return "modules/oa/oaNotifyRecordBackForm";
	}

	@RequiresPermissions("oa:oaNotifyRecordBack:edit")
	@RequestMapping(value = "save")
	public String save(OaNotifyRecordBack oaNotifyRecordBack, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, oaNotifyRecordBack)){
			return form(oaNotifyRecordBack, model);
		}
		oaNotifyRecordBackService.save(oaNotifyRecordBack);
		addMessage(redirectAttributes, "保存通知回复成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaNotifyRecordBack/?repage";
	}
	
	@ResponseBody
	@RequestMapping(value = "saveRecordBack")
	public Map<String, Object> saveRecordBack(@RequestParam(required=false) String nr_id, String messageIn,String useId,HttpServletResponse response) throws UnsupportedEncodingException {
		String msgtext=java.net.URLDecoder.decode(messageIn, "utf-8");
		int rd=0;
		OaNotifyRecordBack oaNotifyRecordBack=new OaNotifyRecordBack();
		oaNotifyRecordBack.setId(IdGen.uuid());
		oaNotifyRecordBack.setNrId(nr_id);
		oaNotifyRecordBack.setRemarks(msgtext);
		oaNotifyRecordBack.setCreateBy(UserUtils.getUser());
		oaNotifyRecordBack.setCreateDate(new Date());
		rd=oaNotifyRecordBackService.saveRecordBack(oaNotifyRecordBack);
		Map<String, Object> params = Maps.newHashMap();	
		if(rd>0){
			params.put("result", "ok");
			params.put("msg", "回复通知成功");
		}else
		{
			params.put("result", "false");
			params.put("msg", "回复通知失败");
		}
		return params;
	}
	
	@ResponseBody
	@RequestMapping(value = "updateRecordBack")
	public Map<String, Object> updateRecordBack(@RequestParam(required=false) String nr_id,String messageInback,String useId,HttpServletResponse response) throws UnsupportedEncodingException {
		String msgtext=java.net.URLDecoder.decode(messageInback, "utf-8");
		int rd=0;
		OaNotifyRecordBack oaNotifyRecordBack=new OaNotifyRecordBack();
		oaNotifyRecordBack.setNrId(nr_id);
		oaNotifyRecordBack.setBack(msgtext);
		User u=new User();
		u.setId(useId);
		oaNotifyRecordBack.setCreateBy(u);
		oaNotifyRecordBack.setUpdateBy(UserUtils.getUser());
		oaNotifyRecordBack.setUpdateDate(new Date());
		rd=oaNotifyRecordBackService.updateRecordBack(oaNotifyRecordBack);
		Map<String, Object> params = Maps.newHashMap();	
		if(rd>0){
			params.put("result", "ok");
			params.put("msg", "回复通知成功");
		}else
		{
			params.put("result", "false");
			params.put("msg", "回复通知失败");
		}
		return params;
	}
	
	/**
	 * @param nr_id
	 * @param useId
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getRecordBack")
	public Map<String, Object> getRecordBack(@RequestParam(required=false) String nr_id, String useId,HttpServletResponse response)  {
		OaNotifyRecordBack oaNotifyRecordBack=new OaNotifyRecordBack();
		oaNotifyRecordBack.setNrId(nr_id);
		User u=new User();
		u.setId(useId);
		oaNotifyRecordBack.setCreateBy(u);
		oaNotifyRecordBack.setCreateDate(new Date());
		oaNotifyRecordBack.setUpdateBy(UserUtils.getUser());
		oaNotifyRecordBack.setUpdateDate(new Date());
		List<OaNotifyRecordBack> sbs=oaNotifyRecordBackService.findList(oaNotifyRecordBack);
		Map<String, Object> params = Maps.newHashMap();	
		if(sbs!=null && sbs.size()>0){
			params.put("result", "ok");
			params.put("msg", sbs.get(0).getRemarks());
		}else
		{
			params.put("result", "false");
			params.put("msg", "获取企业回复信息失败，请联系管理员！");
		}
		return params;
	}
	
	public static OaNotifyRecordBack getNotifyRecordBack(String nr_id) {
		OaNotifyRecordBack oaNotifyRecordBack=new OaNotifyRecordBack();
		oaNotifyRecordBack.setNrId(nr_id);
		List<OaNotifyRecordBack> sebs =null;
		try{
			 sebs=oaNotifyRecordBackDao.findList(oaNotifyRecordBack);
		}catch(Exception ex){
			System.out.print(ex.getMessage());
		}
		
		if(sebs!=null && sebs.size()>0)
		{
			return sebs.get(0);
		}
		if(sebs==null || sebs.size()<=0)
		{
			return null;
		}
		return null;
	}
	
	public static OaNotify getNotifyRecordFrom(String rid) {
		OaNotify entity = null;
		if (StringUtils.isNotBlank(rid)){
			entity = oaNotifyDao.get(rid);
		}
		if (entity == null){
			entity = new OaNotify();
		}
		return entity;
	}
	
	@RequiresPermissions("oa:oaNotifyRecordBack:edit")
	@RequestMapping(value = "delete")
	public String delete(OaNotifyRecordBack oaNotifyRecordBack, RedirectAttributes redirectAttributes) {
		oaNotifyRecordBackService.delete(oaNotifyRecordBack);
		addMessage(redirectAttributes, "删除通知回复成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaNotifyRecordBack/?repage";
	}

}