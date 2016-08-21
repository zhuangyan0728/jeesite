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
@RequestMapping(value = "${adminPath}/erp/analysis")
public class AnalysisWebController extends BaseController {

	 
	@RequiresPermissions("erp:analysis:view")
	@RequestMapping(value = {"employeeCount"})
		public String employeeCount(HttpServletRequest request, HttpServletResponse response, Model model) {
 
			return "modules/erp/employeeCount";
	}
	
	@RequiresPermissions("erp:analysis:view")
	@RequestMapping(value = {"employeeAnalytics"})
		public String employeeAnalytics(HttpServletRequest request, HttpServletResponse response, Model model) {
 
			return "modules/erp/employeeAnalytics";
	}
	
	@RequiresPermissions("erp:analysis:view")
	@RequestMapping(value = {"companyAnalytics"})
		public String companyAnalytics(HttpServletRequest request, HttpServletResponse response, Model model) {
 
			return "modules/erp/companyAnalytics";
	}
	
	@RequiresPermissions("erp:analysis:view")
	@RequestMapping(value = {"empChange"})
		public String empChange(HttpServletRequest request, HttpServletResponse response, Model model) {
 
			return "modules/erp/empChange";
	}

 
		 
 

}
