/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.security.shiro.session.SessionDAO;
import com.thinkgem.jeesite.common.servlet.ValidateCodeServlet;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.CookieUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.erp.entity.CompanyInfo;
import com.thinkgem.jeesite.modules.erp.service.CompanyInfoService;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.security.FormAuthenticationFilter;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 登录Controller
 * @author ThinkGem
 * @version 2013-5-31
 */
@Controller
public class RegisterController extends BaseController{
	
	 
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private CompanyInfoService companyInfoService;
	/**
	 * 注册
	 */

	@RequestMapping(value = "${adminPath}/register", method = RequestMethod.GET)
	public String register(User user, Model model) {
		if (user.getCompany()==null || user.getCompany().getId()==null){
			user.setCompany(UserUtils.getUser().getCompany());
		}
		if (user.getOffice()==null || user.getOffice().getId()==null){
			user.setOffice(UserUtils.getUser().getOffice());
		}
		model.addAttribute("user", user);
		//将非人事专员角色从用户维护中移出
		List<Role> roleList = systemService.findAllRole();
	
		try {
			String defultRoleName = Global.getDefaultHrId();
			Iterator<Role> iter = roleList.iterator();
	        while(iter.hasNext()){
	        	Role b = iter.next();
	            if(!b.getName().toString().equals(defultRoleName)){
	                iter.remove();
	            }
	        }
			
		} catch (Exception e) {
			addMessage( model,"打开用户信息失败！失败信息："+e.getMessage());

		}
		model.addAttribute("allRoles", systemService.findAllRole());
		return "modules/sys/registerForm";
	}
	
	public String checkLoginName(String oldLoginName, String loginName) {
		if (loginName !=null && loginName.equals(oldLoginName)) {
			return "true";
		} else if (loginName !=null && systemService.getUserByLoginName(loginName) == null) {
			return "true";
		}
		return "false";
	}

	@RequestMapping(value = "${adminPath}/register/save")
	public String save(User user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {

		// 修正引用赋值问题，不知道为何，Company和Office引用的一个实例地址，修改了一个，另外一个跟着修改。
		//user.getCompany().setId(request.getParameter("company.id"));
		user.getCompany().setName(request.getParameter("company.name"));
		
		//user.setOffice(new Office(request.getParameter("office.id")));人事专员不用部门
		// 如果新密码为空，则不更换密码
		if (StringUtils.isNotBlank(user.getNewPassword())) {
			user.setPassword(SystemService.entryptPassword(user.getNewPassword()));
		}
		if (!beanValidator(model, user)){
			return register(user, model);
		}
		if (!"true".equals(checkLoginName(user.getOldLoginName(), user.getLoginName()))){
			addMessage(model, "保存用户'" + user.getLoginName() + "'失败，登录名已存在");
			return register(user, model);
		}
		// 角色数据有效性验证，过滤不在授权内的角色
		List<Role> roleList = Lists.newArrayList();
		try {
			String defultRoleName = Global.getDefaultHrId();
			Role hrRole= systemService.getRoleByName(defultRoleName);
			roleList.add(hrRole);
			user.setRoleList(roleList);
			// 保存用户信息
			User us =  systemService.getUserByLoginName("admin");
			
			//保存企业信息
			CompanyInfo companyInfo = new CompanyInfo();
			companyInfo.setName(request.getParameter("company.name"));
			companyInfo.setOrganizecode(request.getParameter("company.organizecode"));
			companyInfo.setCreateBy(us);
			companyInfo.setUpdateBy(us);
			companyInfo.setCreateDate(new Date());
			companyInfo.setUpdateDate(new Date());
			companyInfoService.save(companyInfo);
			
			List<CompanyInfo>  cltmp = companyInfoService.findListNoFilter(companyInfo);
			if(null!=cltmp  && cltmp.size() > 0){
				CompanyInfo cm = cltmp.get(0);
				user.getCompany().setId(cm.getId());
			}else{
				addMessage(redirectAttributes, "用户注册失败！失败信息：企业信息验证失败");
				return "redirect:" + adminPath + "/register";
			}
			user.setCreateBy(us);
			user.setUpdateBy(us);
			user.setCreateDate(new Date());
			user.setUpdateDate(new Date());
			user.setLoginFlag("0");
			systemService.saveUser(user);
			// 清除当前用户缓存
			if (user.getLoginName().equals(UserUtils.getUser().getLoginName())){
				UserUtils.clearCache();
				//UserUtils.getCacheMap().clear();
			}
			addMessage(redirectAttributes, "用户注册'" + user.getLoginName() + "'成功");
		} catch (Exception e) {
			addMessage(redirectAttributes, "用户注册失败！失败信息："+e.getMessage());
			return "redirect:" + adminPath + "/register";
		}
		
		return "redirect:" + adminPath + "/login";
	}
	
	@ResponseBody
	@RequestMapping(value = "${adminPath}/register/companyInfo/treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String type, String param, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		CompanyInfo companyInfo = new CompanyInfo() ;
		if(StringUtils.isNotBlank(param)){
			companyInfo.setName(param);
		}
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

}
