/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.web;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.utils.UploadUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
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
import org.springframework.web.util.UriUtils;

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
	public Map<String, Object> saveRecordBack(@RequestParam(required=false) String nr_id, String messageIn,String useId,HttpServletRequest req,HttpServletResponse response) throws UnsupportedEncodingException {
		String msgtext=java.net.URLDecoder.decode(messageIn, "utf-8");
		int rd=0;
		OaNotifyRecordBack oaNotifyRecordBack=new OaNotifyRecordBack();
		oaNotifyRecordBack.setId(IdGen.uuid());
		oaNotifyRecordBack.setNrId(nr_id);
		oaNotifyRecordBack.setRemarks(msgtext);
		oaNotifyRecordBack.setCreateBy(UserUtils.getUser());
		oaNotifyRecordBack.setCreateDate(new Date());
		oaNotifyRecordBack.setUpdateBy(UserUtils.getUser());
		oaNotifyRecordBack.setUpdateDate(new Date());



		String Path = Global.USERFILES_BASE_URL + "/recordBack/" +oaNotifyRecordBack.getNrId() +"/"+UserUtils.getUser().getId();
		try {

			String name = UpLoadFile(req,response,Path);
			name = java.net.URLEncoder.encode(name, "utf-8");
			if(StringUtils.isNotBlank(name)){

				Path = "/jsqyq/" + Path + "/"+name;


			}
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		oaNotifyRecordBack.setFile(Path);
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


	@ResponseBody
	@RequestMapping(value = "test")
	public String UpLoadFile(HttpServletRequest request, HttpServletResponse response,String Path) throws ServletException, IOException {
		//得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
//		UploadUtils s =  new UploadUtils();
//		s.uploadFile(request);
		String returnName="";
		Path = request.getSession().getServletContext().getRealPath("/") + "/" +Path;
		File file = new File(Path);
		if(!file.exists()&&!file.isDirectory()){
			System.out.println("目录或文件不存在！");

			System.out.println(file.mkdirs());
		}
		//消息提示
		String message = "";
		try {
			//使用Apache文件上传组件处理文件上传步骤：
			//1、创建一个DiskFileItemFactory工厂
			DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
			//2、创建一个文件上传解析器
			ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
			//解决上传文件名的中文乱码
			fileUpload.setHeaderEncoding("UTF-8");
			//3、判断提交上来的数据是否是上传表单的数据
			if(!fileUpload.isMultipartContent(request)){
				//按照传统方式获取数据
				throw new Exception("无文件") ;
			}
			//4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项


			List<FileItem> list = fileUpload.parseRequest(request);

			//response.getWriter().flush();

			//response.getOutputStream().println();

			for (FileItem item : list) {
				//如果fileitem中封装的是普通输入项的数据
				if(item.isFormField()){
					String name = item.getFieldName();
					//解决普通输入项的数据的中文乱码问题
					String value = item.getString("UTF-8");
					String value1 = new String(name.getBytes("iso8859-1"),"UTF-8");
					System.out.println(name+"  "+value);
					System.out.println(name+"  "+value1);
				}else{
					//如果fileitem中封装的是上传文件，得到上传的文件名称，
					String fileName = item.getName();

					System.out.println(fileName);
					if(fileName==null||fileName.trim().equals("")){
						continue;
					}
					//注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
					//处理获取到的上传文件的文件名的路径部分，只保留文件名部分
					fileName = fileName.substring(fileName.lastIndexOf(File.separator)+1);
					returnName = fileName;
					//获取item中的上传文件的输入流
					InputStream is = item.getInputStream();
					//创建一个文件输出流
					FileOutputStream fos = new FileOutputStream(Path+File.separator+fileName);
					System.out.println("d:" + Path+File.separator+fileName);
					//创建一个缓冲区
					byte buffer[] = new byte[1024];
					//判断输入流中的数据是否已经读完的标识
					int length = 0;
					//循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
					while((length = is.read(buffer))>0){
						//使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
						fos.write(buffer, 0, length);
					}
					//关闭输入流
					is.close();
					//关闭输出流
					fos.close();
					//删除处理文件上传时生成的临时文件
					item.delete();
					message = "文件上传成功";
				}
			}
		} catch (Exception e) {
 			e.printStackTrace();
			System.out.println(e.getMessage());
			message = "文件上传失败";
		}
		//request.setAttribute("message",message);
		//request.getRequestDispatcher("/message.jsp").forward(request, response);
		return  returnName;
	}



}