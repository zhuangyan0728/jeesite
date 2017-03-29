/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.erp.web;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.modules.erp.entity.Employee;
import com.thinkgem.jeesite.modules.erp.service.EmployeeService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 企业人才管理Controller
 * @author zhuangyan
 * @version 2016-06-27
 */
@Controller
@RequestMapping(value = "${adminPath}/erp/employee")
public class EmployeeController extends BaseController {

	@Autowired
	private EmployeeService employeeService;
	
	@ModelAttribute
	public Employee get(@RequestParam(required=false) String id) {
		Employee entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = employeeService.get(id);
		}
		if (entity == null){
			entity = new Employee();
			entity.setIntegral("0");
		}
		return entity;
	}
	
	@RequiresPermissions("erp:employee:view")
	@RequestMapping(value = {"list", ""})
	public String list(Employee employee, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Employee> page = employeeService.findPage(new Page<Employee>(request, response), employee); 
		model.addAttribute("page", page);
		return "modules/erp/employeeList";
	}

	@RequiresPermissions("erp:employee:view")
	@RequestMapping(value = "form")
	public String form(Employee employee, Model model) {
		model.addAttribute("employee", employee);
		return "modules/erp/employeeForm";
	}

	@RequiresPermissions("erp:employee:edit")
	@RequestMapping(value = "save")
	public String save(Employee employee, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, employee)){
			return form(employee, model);
		}
		employeeService.save(employee);
		addMessage(redirectAttributes, "保存企业人才信息成功");
		return "redirect:"+Global.getAdminPath()+"/erp/employee/?repage";
	}
	
	@RequiresPermissions("erp:employee:edit")
	@RequestMapping(value = "delete")
	public String delete(Employee employee, RedirectAttributes redirectAttributes) {
		employeeService.delete(employee);
		addMessage(redirectAttributes, "删除企业人才信息成功");
		return "redirect:"+Global.getAdminPath()+"/erp/employee/?repage";
	}
	
	@RequiresPermissions("erp:employee:view")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(User user, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Employee employee = new Employee();
            Page<Employee> page = employeeService.findPage(new Page<Employee>(request, response), employee); 
            new ExportExcel("人员信息数据", Employee.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出人员信息失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/erp/employee/list?repage";
    }
	
	@RequiresPermissions("erp:employee:edit")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		 
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Employee> list = ei.getDataList(Employee.class);
			for (Employee employee : list){
				try{
					
						BeanValidators.validateWithException(validator, employee);
						employeeService.save(employee);
						successNum++;
					 
				}catch(ConstraintViolationException ex){
					failureMsg.append("<br/>登录名 "+employee.getName()+" 导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("<br/>登录名 "+employee.getName()+" 导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条用户，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条用户"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/erp/employee/list?repage";
    }
		
	@RequiresPermissions("erp:employee:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "人才信息数据导入模板.xlsx";
    		List<Employee> list = Lists.newArrayList(); 
    		list.add(employeeService.get("0206d521bba04a7bb1dede6641816882"));
    		new ExportExcel("人才信息数据", Employee.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/erp/employee/list?repage";
    }


	/**
	 * --------------------------------------人才公寓房申请相关------------
	 */

	@RequiresPermissions("erp:employee:view")
	@RequestMapping(value = {"applylist"})
	public String applyList(Employee employee, HttpServletRequest request, HttpServletResponse response, Model model) {
        employee.setIfApply(1);
		Page<Employee> page = employeeService.findPage(new Page<Employee>(request, response), employee);
		model.addAttribute("page", page);
		return "modules/erp/employeeApplyRoomList";
	}

	@RequiresPermissions("erp:employee:view")
	@RequestMapping(value = "applyform")
	public String applyForm(Employee employee, Model model) {
		model.addAttribute("employee", employee);
		return "modules/erp/employeeApplyRoomForm";
	}

    @RequiresPermissions("erp:employee:view")
    @RequestMapping(value = "roomApply")
    public String roomApply(Employee employee, RedirectAttributes redirectAttributes) {
        employee.setIfApply(1);
        employeeService.save(employee);
		employeeService.insertApplyLog(employee,"申请人才公寓");
        addMessage(redirectAttributes, "申请人才公寓成功");
        return "redirect:"+Global.getAdminPath()+"/erp/employee/?repage";
    }

    @RequiresPermissions("erp:employee:view")
    @RequestMapping(value = "cancelApply")
    public String cancelApply(Employee employee, RedirectAttributes redirectAttributes) {
        employee.setIfApply(0);
        employeeService.save(employee);
		employeeService.insertApplyLog(employee,"取消人才公寓申请");
        addMessage(redirectAttributes, "取消人才公寓申请成功");
        return "redirect:"+Global.getAdminPath()+"/erp/employee/applylist/?repage";
    }

    @RequiresPermissions("erp:employee:view")
    @RequestMapping(value = "passApply")
    public String passApply(Employee employee, RedirectAttributes redirectAttributes) {

		employee.setReason1(null);
		employee.setIfApplyAudit(1);
        employeeService.save(employee);
		employeeService.insertApplyLog(employee,"审核人才公寓");
        addMessage(redirectAttributes, "审核人才公寓成功");
        return "redirect:"+Global.getAdminPath()+"/erp/employee/applylist/?repage";
    }

    @RequiresPermissions("erp:employee:view")
    @RequestMapping(value = "ajustApply")
    public String ajustApply(Employee employee,HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String messageIn = request.getParameter("messageIn");
		try {
			messageIn=java.net.URLDecoder.decode(messageIn, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		employee.setReason1(messageIn);
        employee.setIfApplyAudit(2);
        employeeService.save(employee);
		employeeService.insertApplyLog(employee,"拒绝人才公寓申请");
        addMessage(redirectAttributes, "拒绝人才公寓申请成功");
        return "redirect:"+Global.getAdminPath()+"/erp/employee/applylist?repage";
    }


	/**
	 * --------------------------------------人才购房申请相关------------
	 */

	@RequiresPermissions("erp:employee:view")
	@RequestMapping(value = {"buylist"})
	public String buyRoomList(Employee employee, HttpServletRequest request, HttpServletResponse response, Model model) {
		employee.setIfBuyApply(1);
		Page<Employee> page = employeeService.findPage(new Page<Employee>(request, response), employee);
		model.addAttribute("page", page);
		return "modules/erp/employeeBuyRoomList";
	}

	@RequiresPermissions("erp:employee:view")
	@RequestMapping(value = "buyform")
	public String buyForm(Employee employee, Model model) {
		model.addAttribute("employee", employee);
		return "modules/erp/employeeBuyRoomForm";
	}

	@RequiresPermissions("erp:employee:view")
	@RequestMapping(value = "buyApply")
	public String buyApply(Employee employee, RedirectAttributes redirectAttributes) {
		employee.setIfBuyApply(1);
		employeeService.save(employee);
		employeeService.insertApplyLog(employee,"购买住房申请");
		addMessage(redirectAttributes, "购买住房申请成功");
		return "redirect:"+Global.getAdminPath()+"/erp/employee/?repage";
	}

	@RequiresPermissions("erp:employee:view")
	@RequestMapping(value = "cancelBuyApply")
	public String cancelBuyApply(Employee employee, RedirectAttributes redirectAttributes) {
		employee.setIfBuyApply(0);
		employeeService.save(employee);
		employeeService.insertApplyLog(employee,"取消购房申请");
		addMessage(redirectAttributes, "取消购房申请成功");
		return "redirect:"+Global.getAdminPath()+"/erp/employee/buylist/?repage";
	}

	@RequiresPermissions("erp:employee:view")
	@RequestMapping(value = "passBuyApply")
	public String passBuyApply(Employee employee, RedirectAttributes redirectAttributes) {
		employee.setIfBuyApplyAudit(1);
		employee.setReason3(null);
		employeeService.save(employee);
		employeeService.insertApplyLog(employee,"审核购房申请");
		addMessage(redirectAttributes, "审核购房申请成功");
		return "redirect:"+Global.getAdminPath()+"/erp/employee/buylist/?repage";
	}

	@RequiresPermissions("erp:employee:view")
	@RequestMapping(value = "ajustBuyApply")
	public String ajustBuyApply(Employee employee, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String messageIn = request.getParameter("messageIn");
		try {
			messageIn=java.net.URLDecoder.decode(messageIn, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		employee.setReason3(messageIn);
		employee.setIfBuyApplyAudit(2);
		employeeService.save(employee);
		employeeService.insertApplyLog(employee,"拒绝购房申请");
		addMessage(redirectAttributes, "拒绝购房申请成功");
		return "redirect:"+Global.getAdminPath()+"/erp/employee/buylist?repage";
	}

	/**
	 * --------------------------------------人才租房申请相关------------
	 */

	@RequiresPermissions("erp:employee:view")
	@RequestMapping(value = {"rentlist"})
	public String rentRoomList(Employee employee, HttpServletRequest request, HttpServletResponse response, Model model) {
		employee.setIfRentApply(1);
		Page<Employee> page = employeeService.findPage(new Page<Employee>(request, response), employee);
		model.addAttribute("page", page);
		return "modules/erp/employeeRentRoomList";
	}

	@RequiresPermissions("erp:employee:view")
	@RequestMapping(value = "rentform")
	public String rentForm(Employee employee, Model model) {
		model.addAttribute("employee", employee);
		return "modules/erp/employeeRentRoomForm";
	}

	@RequiresPermissions("erp:employee:view")
	@RequestMapping(value = "rentApply")
	public String rentApply(Employee employee, RedirectAttributes redirectAttributes) {
		employee.setIfRentApply(1);
		employeeService.save(employee);
		employeeService.insertApplyLog(employee,"租房申请");
		addMessage(redirectAttributes, "租房申请成功");
		return "redirect:"+Global.getAdminPath()+"/erp/employee/?repage";
	}

	@RequiresPermissions("erp:employee:view")
	@RequestMapping(value = "cancelRentApply")
	public String cancelRentApply(Employee employee, RedirectAttributes redirectAttributes) {
		employee.setIfRentApply(0);
		employeeService.save(employee);
		employeeService.insertApplyLog(employee,"取消租房申请");
		addMessage(redirectAttributes, "取消租房申请成功");
		return "redirect:"+Global.getAdminPath()+"/erp/employee/rentlist/?repage";
	}

	@RequiresPermissions("erp:employee:view")
	@RequestMapping(value = "passRentApply")
	public String passRentApply(Employee employee, RedirectAttributes redirectAttributes) {
		employee.setIfRentApplyAudit(1);
		employee.setReason2(null);
		employeeService.save(employee);
		employeeService.insertApplyLog(employee,"审核租房申请");
		addMessage(redirectAttributes, "审核租房申请成功");
		return "redirect:"+Global.getAdminPath()+"/erp/employee/rentlist/?repage";
	}

	@RequiresPermissions("erp:employee:view")
	@RequestMapping(value = "ajustRentApply")
	public String ajustRentApply(Employee employee,HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String messageIn = request.getParameter("messageIn");
		try {
			messageIn=java.net.URLDecoder.decode(messageIn, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		employee.setReason2(messageIn);
		employee.setIfRentApplyAudit(2);
		employeeService.save(employee);
		employeeService.insertApplyLog(employee,"拒绝租房申请");
		addMessage(redirectAttributes, "拒绝租房申请成功");
		return "redirect:"+Global.getAdminPath()+"/erp/employee/rentlist?repage";
	}

}