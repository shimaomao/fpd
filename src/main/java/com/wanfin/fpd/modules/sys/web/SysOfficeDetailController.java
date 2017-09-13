/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.common.utils.StringUtils;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.modules.sys.entity.Office;
import com.wanfin.fpd.modules.sys.entity.SysOfficeDetail;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.service.OfficeService;
import com.wanfin.fpd.modules.sys.service.SysOfficeDetailService;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 机构详情Controller
 * @author kewenxiu
 * @version 2017-03-09
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/sys/sysOfficeDetail")
public class SysOfficeDetailController extends BaseController {

	@Autowired
	private SysOfficeDetailService sysOfficeDetailService;
	@Autowired
	private OfficeService officeService;
	
	@ModelAttribute
	public SysOfficeDetail get(@RequestParam(required=false) String id) {
		SysOfficeDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysOfficeDetailService.get(id);
		}
		if (entity == null){
			entity = new SysOfficeDetail();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:sysOfficeDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysOfficeDetail sysOfficeDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysOfficeDetail> page = sysOfficeDetailService.findPage(new Page<SysOfficeDetail>(request, response), sysOfficeDetail); 
		model.addAttribute("page", page);
		model.addAttribute("sysOfficeDetail", sysOfficeDetail);
		return "modules/sys/sysOfficeDetailList";
	}
	
	/**
	 * 个人所属机构基本信息
	 */
	@RequestMapping(value = "formForDetail")
	public String formForDetail(Office office, Model model){
		User user = UserUtils.getUser();
		if(StringUtils.isNoneBlank(office.getId())){
			office = officeService.get(office.getId());
		}else{
			if(user.getCompany()!=null&&StringUtils.isNotBlank(user.getCompany().getId())){
				office = officeService.get(user.getCompany().getId());
			}else{
				office = new Office();
			}
		}
		SysOfficeDetail sysOfficeDetail = sysOfficeDetailService.getByPid(office.getId());
		model.addAttribute("sysOfficeDetail", sysOfficeDetail);
		model.addAttribute("office", office);
		model.addAttribute("user", user);
		model.addAttribute("curUser", user);
		return "modules/sys/officeForm";
	}
	
	/**
	 * 机构部门列表信息
	 */
	@RequestMapping(value = "officeList")
	public String officeList(Office office, Model model){
		User user = UserUtils.getUser();
		if(StringUtils.isNoneBlank(office.getId())){
			office = officeService.get(office.getId());
		}else{
			if(user.getCompany()!=null&&StringUtils.isNotBlank(user.getCompany().getId())){
				office = officeService.get(user.getCompany().getId());
			}
		}
		model.addAttribute("sysOfficeDetail",sysOfficeDetailService.getByPid(office.getId()));
		model.addAttribute("office",office);
        model.addAttribute("list", officeService.findList(office));
		return "modules/sys/officeList";
	}

	@RequiresPermissions("sys:sysOfficeDetail:view")
	@RequestMapping(value = "form")
	public String form(SysOfficeDetail sysOfficeDetail, Model model) {
		Office office = null;
		User user = UserUtils.getUser();
		if(StringUtils.isNoneBlank(sysOfficeDetail.getId())){
			//已有详情
			sysOfficeDetail = sysOfficeDetailService.get(sysOfficeDetail.getId());
			office = officeService.get(sysOfficeDetail.getPid());
		}else{ 
			if(StringUtils.isNoneBlank(sysOfficeDetail.getPid())){
				//没有详情，admin下
				office = officeService.get(sysOfficeDetail.getPid());
				model.addAttribute("office", office);
			}else{
				//没详情,获取当前用户机构
				sysOfficeDetail = new SysOfficeDetail();
				if(user.getCompany()!=null&&StringUtils.isNotBlank(user.getCompany().getId())){
					//设置机构ID
					sysOfficeDetail.setPid(user.getCompany().getId());
					office = officeService.get(user.getCompany().getId());
				}
			}
			if(office!=null){
				//地址
				sysOfficeDetail.setAddress(office.getAddress());
				//名称
				sysOfficeDetail.setName(office.getName());
				//机构类型
				sysOfficeDetail.setType(office.getType());
			}
		}
		model.addAttribute("user", user);
		model.addAttribute("office", office);
		model.addAttribute("sysOfficeDetail", sysOfficeDetail);
		return "modules/sys/sysOfficeDetailForm";
	}

	@RequiresPermissions("sys:sysOfficeDetail:edit")
	@RequestMapping(value = "save")
	public String save(SysOfficeDetail sysOfficeDetail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysOfficeDetail)){
			return form(sysOfficeDetail, model);
		}
		sysOfficeDetailService.save(sysOfficeDetail);
		addMessage(redirectAttributes, "保存机构详情成功");
		return "redirect:" + adminPath + "/sys/sysOfficeDetail/form?id="+sysOfficeDetail.getId();
	}
	
	@RequiresPermissions("sys:sysOfficeDetail:edit")
	@RequestMapping(value = "delete")
	public String delete(SysOfficeDetail sysOfficeDetail, RedirectAttributes redirectAttributes) {
		sysOfficeDetailService.delete(sysOfficeDetail);
		addMessage(redirectAttributes, "删除机构详情成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysOfficeDetail/?repage";
	}

}