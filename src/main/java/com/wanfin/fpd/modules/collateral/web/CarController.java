/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.collateral.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.wanfin.fpd.modules.collateral.entity.Car;
import com.wanfin.fpd.modules.collateral.entity.Cunhuo;
import com.wanfin.fpd.modules.collateral.service.CarService;
import com.wanfin.fpd.modules.mortgage.entity.MortgageContract;
import com.wanfin.fpd.modules.pledge.entity.PledgeContract;

/**
 * 车辆信息Controller
 * @author srf
 * @version 2016-03-24
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/collateral/car")
public class CarController extends BaseController {

	@Autowired
	private CarService carService;
	
	@ModelAttribute
	public Car get(@RequestParam(required=false) String id) {
		Car entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = carService.get(id);
		}
		if (entity == null){
			entity = new Car();
		}
		return entity;
	}
	
	@RequiresPermissions("collateral:car:view")
	@RequestMapping(value = {"list", ""})
	public String list(Car car, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Car> page = carService.findPage(new Page<Car>(request, response), car); 
		model.addAttribute("page", page);
		model.addAttribute("car", car);
		return "modules/collateral/carList";
	}

	@RequiresPermissions("collateral:car:view")
	@RequestMapping(value = "form")
	public String form(Car car, Model model) {
		model.addAttribute("car", car);
		return "modules/collateral/carForm";
	}

	@RequestMapping(value = "add")
	public String add(@RequestParam(required=false) String dizhiContractId, Car car, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request ) {
		Car entity = null;
		if (StringUtils.isNotBlank(dizhiContractId)){
			car = carService.getByPledge(dizhiContractId);
		}
		
		//从会话获取 如果 没有保存到数据库
		if (entity == null && StringUtils.isNotBlank(dizhiContractId)){
			HttpSession session = request.getSession();		
			List<PledgeContract> pledgeContractList = (List<PledgeContract>)session.getAttribute("pledgeContractList");	
			if (pledgeContractList != null &&  pledgeContractList.size() > 0) {
		    	for(int i = 0; i < pledgeContractList.size(); i++){
					PledgeContract temp = pledgeContractList.get(i);
					if(dizhiContractId.equals(temp.getId())){		
						entity = temp.getCar();
					}
				}	
		    }	
		}
						
				
		if (entity != null){
			model.addAttribute("car", entity);
		}
		
		return "modules/collateral/carAdd";
	}
	
	
	@RequestMapping(value = "addForMC")
	public String addForMC(@RequestParam(required=false) String dizhiContractId, Car car, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request ) {
		Car entity = null;
		if (StringUtils.isNotBlank(dizhiContractId)){
			car = carService.getByPledge(dizhiContractId);
		}
		
		//从会话获取 如果 没有保存到数据库
		if (entity == null && StringUtils.isNotBlank(dizhiContractId)){
			HttpSession session = request.getSession();		
			List<MortgageContract> mortgageContractList = (List<MortgageContract>)session.getAttribute("mortgageContractList");	
		    if (mortgageContractList != null &&  mortgageContractList.size() > 0) {
		    	for(int i = 0; i < mortgageContractList.size(); i++){
					MortgageContract temp = mortgageContractList.get(i);
					if(dizhiContractId.equals(temp.getId())){		
						entity = temp.getCar();
					}
				}	
		    }	
		}
						
				
		if (entity != null){
			model.addAttribute("car", entity);
		}
		
		return "modules/collateral/carAdd";
	}
	
	@RequiresPermissions("collateral:car:edit")
	@RequestMapping(value = "save")
	public String save(Car car, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, car)){
			return form(car, model);
		}
		carService.save(car);
		addMessage(redirectAttributes, "保存质押车辆信息成功");
		return "redirect:"+Global.getAdminPath()+"/collateral/car/?repage";
	}
	
	@RequiresPermissions("collateral:car:edit")
	@RequestMapping(value = "delete")
	public String delete(Car car, RedirectAttributes redirectAttributes) {
		carService.delete(car);
		addMessage(redirectAttributes, "删除质押车辆信息成功");
		return "redirect:"+Global.getAdminPath()+"/collateral/car/?repage";
	}

}