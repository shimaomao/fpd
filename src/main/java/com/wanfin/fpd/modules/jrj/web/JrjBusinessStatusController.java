/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.jrj.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.balancesheep.entity.TBalanceSheep;
import com.wanfin.fpd.modules.balancesheep.service.TBalanceSheepService;
import com.wanfin.fpd.modules.jrj.entity.JrjBalanceSheep;
import com.wanfin.fpd.modules.jrj.entity.JrjBusinessStatus;
import com.wanfin.fpd.modules.jrj.entity.JrjProceeds;
import com.wanfin.fpd.modules.jrj.service.JrjBalanceSheepService;
import com.wanfin.fpd.modules.jrj.service.JrjBusinessStatusService;
import com.wanfin.fpd.modules.jrj.service.JrjProceedsService;
import com.wanfin.fpd.modules.sys.entity.Office;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 担保业务状况Controller
 * @author xzt
 * @version 2016-10-29
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/bussiness/businessStatus")
public class JrjBusinessStatusController extends BaseController {

	@Autowired
	private JrjBusinessStatusService jrjBusinessStatusServie;	
	
	@ModelAttribute
	public JrjBusinessStatus get(@RequestParam(required=false) String id) {
		JrjBusinessStatus entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = jrjBusinessStatusServie.get(id);
		}
		if (entity == null){
			entity = new JrjBusinessStatus();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(JrjBusinessStatus jrjBusinessStatus, HttpServletRequest request, HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();//当前登录人
		jrjBusinessStatus.setCurrentUser(currentUser);
		Page<JrjBusinessStatus> page = jrjBusinessStatusServie.findPage(new Page<JrjBusinessStatus>(request, response), jrjBusinessStatus);
		String  first = "";
		if(jrjBusinessStatus.getSubmitDate() ==null||jrjBusinessStatus.getSubmitDate().equals("")){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM"); 
			 Calendar c = Calendar.getInstance();    
		     c.add(Calendar.MONTH, 0);
		     c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		      first= format.format(c.getTime());
		      jrjBusinessStatus.setSubmitDate(first);
		}	
		model.addAttribute("page", page);
		model.addAttribute("jrjBusinessStatus", jrjBusinessStatus);
		return "modules/jrj/tBusinessStatusList";
	}

	@RequestMapping(value = "form")
	public String form(JrjBusinessStatus jrjBusinessStatus, Model model) {	
		 User user = UserUtils.getUser();
		 jrjBusinessStatus.setCurrentUser(user);	
		 List<JrjBusinessStatus> statusList = jrjBusinessStatusServie.findListOrderByCreateDate(jrjBusinessStatus);
		
		String  first = "";
		if(jrjBusinessStatus.getSubmitDate()==null||jrjBusinessStatus.getSubmitDate().equals("")){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM"); 
			Calendar c = Calendar.getInstance();    
		    c.add(Calendar.MONTH, 0);
		    c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		    first= format.format(c.getTime());
		    jrjBusinessStatus.setSubmitDate(first);
		}
		
		//查询是否为第一次添加记录		   
		if(jrjBusinessStatus.getId() != null){
			if(jrjBusinessStatus.getReportName().contains("本条数据为第一次报送")){ //编辑第一条数据				
				model.addAttribute("jrjBusinessStatus", jrjBusinessStatus);
				return "modules/jrj/tBusinessStatusFirstForm";
			}else {  //编辑其它条数据记录
				model.addAttribute("jrjBusinessStatus", jrjBusinessStatus);
				return "modules/jrj/tBusinessStatusForm";
			}
		}else{
			if(statusList !=null && statusList.size() > 0){	
				//获取最新一条的期末数 赋值给下一期初数   1-24字段
				JrjBusinessStatus temp = statusList.get(0);
				jrjBusinessStatus.setRowOne(temp.getRowFour());
				jrjBusinessStatus.setRow1One(temp.getRow1Four());
				jrjBusinessStatus.setRow2One(temp.getRow2Four());
				jrjBusinessStatus.setRow3One(temp.getRow3Four());
				jrjBusinessStatus.setRow4One(temp.getRow4Four());
				jrjBusinessStatus.setRow5One(temp.getRow5Four());
				jrjBusinessStatus.setRow6One(temp.getRow6Four());
				jrjBusinessStatus.setRow7One(temp.getRow7Four());
				jrjBusinessStatus.setRow8One(temp.getRow8Four());
				jrjBusinessStatus.setRow9One(temp.getRow9Four());
				jrjBusinessStatus.setRow10One(temp.getRow10Four());
				jrjBusinessStatus.setRow11One(temp.getRow11Four());
				jrjBusinessStatus.setRow12One(temp.getRow12Four());
				jrjBusinessStatus.setRow13One(temp.getRow13Four());
				jrjBusinessStatus.setRow14One(temp.getRow14Four());
				jrjBusinessStatus.setRow15One(temp.getRow15Four());
				jrjBusinessStatus.setRow16One(temp.getRow16Four());
				jrjBusinessStatus.setRow17One(temp.getRow17Four());
				jrjBusinessStatus.setRow18One(temp.getRow18Four());
				jrjBusinessStatus.setRow19One(temp.getRow19Four());
				jrjBusinessStatus.setRow20One(temp.getRow20Four());
				jrjBusinessStatus.setRow21One(temp.getRow21Four());
				jrjBusinessStatus.setRow22One(temp.getRow22Four());
				jrjBusinessStatus.setRow23One(temp.getRow23Four());
				jrjBusinessStatus.setRow24One(temp.getRow24Four());
				model.addAttribute("jrjBusinessStatus", jrjBusinessStatus);
				return "modules/jrj/tBusinessStatusForm";
			}else{			
				model.addAttribute("jrjBusinessStatus", jrjBusinessStatus);
				return "modules/jrj/tBusinessStatusFirstForm";
			}
		}	
	}

	@RequestMapping(value = "save")
	public String save(JrjBusinessStatus jrjBusinessStatus, Model model, RedirectAttributes redirectAttributes) {
		User currentUser = UserUtils.getUser();//当前登录人		
		if (!beanValidator(model, jrjBusinessStatus)){
			return form(jrjBusinessStatus, model);
		}		
		
		if(jrjBusinessStatus.getId() == null || "".equals(jrjBusinessStatus.getId())){ //添加
			jrjBusinessStatus.setCurrentUser(currentUser);
			List<JrjBusinessStatus> statusList = jrjBusinessStatusServie.findListOrderByCreateDate(jrjBusinessStatus);
			if(statusList != null && statusList.size() > 0){
				jrjBusinessStatus.setReportName(currentUser.getCompany().getName());			
			}else{
				jrjBusinessStatus.setReportName(currentUser.getCompany().getName()+"(本条数据为第一次报送)");
			}	
			jrjBusinessStatus.setOrganId(currentUser.getCompany().getId()); 
			jrjBusinessStatus.setCreateBy(currentUser);	
			jrjBusinessStatus.setCompanyName(currentUser.getCompany().getName());
		}	 
		 
		jrjBusinessStatus.setScanFlag("0");	
		jrjBusinessStatusServie.save(jrjBusinessStatus);
		addMessage(redirectAttributes, "保存担保业务状况成功");
		return "redirect:"+Global.getAdminPath()+"/bussiness/businessStatus/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(JrjBusinessStatus jrjBusinessStatus, RedirectAttributes redirectAttributes) {		
		//根据所需删除记录的  创建时间  查询  需删除记录's
		List<JrjBusinessStatus> list = jrjBusinessStatusServie.findListByCreateDate(jrjBusinessStatus);
		if(list != null && list.size() > 0){
			for(JrjBusinessStatus status:list){
				jrjBusinessStatusServie.delete(status);
			}
		}		
		addMessage(redirectAttributes, "删除担保业务状况成功");
		return "redirect:"+Global.getAdminPath()+"/bussiness/businessStatus/?repage";
	}	

}