/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wish.order.web;

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
import com.wanfin.fpd.modules.wish.order.entity.AvgRecord;
import com.wanfin.fpd.modules.wish.order.service.AvgRecordService;

/**
 * 历史平均数据Controller
 * @author cjp
 * @version 2017-06-28
 */
@ApiIgnore
@Controller


@RequestMapping(value = "/wish/order/avgRecord")
public class AvgRecordController extends BaseController {

    @Autowired
    private AvgRecordService avgRecordService;

    @ModelAttribute
    public AvgRecord get(@RequestParam(required=false) String id) {
        AvgRecord entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = avgRecordService.get(id);
        }
        if (entity == null){
            entity = new AvgRecord();
        }
        return entity;
    }

    @RequiresPermissions("wish.order:avgRecord:view")
    @RequestMapping(value = {"list", ""})
    public String list(AvgRecord avgRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<AvgRecord> page = avgRecordService.findPage(new Page<AvgRecord>(request, response), avgRecord);
        model.addAttribute("page", page);
        model.addAttribute("avgRecord", avgRecord);
        return "modules/wish.order/avgRecordList";
    }

    @RequiresPermissions("wish.order:avgRecord:view")
    @RequestMapping(value = "form")
    public String form(AvgRecord avgRecord, Model model) {
        model.addAttribute("avgRecord", avgRecord);
        return "modules/wish.order/avgRecordForm";
    }

    @RequiresPermissions("wish.order:avgRecord:edit")
    @RequestMapping(value = "save")
    public String save(AvgRecord avgRecord, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, avgRecord)){
            return form(avgRecord, model);
        }
        avgRecordService.save(avgRecord);
        addMessage(redirectAttributes, "保存历史平均数据成功");
        return "redirect:"+Global.getAdminPath()+"/wish.order/avgRecord/?repage";
    }

    @RequiresPermissions("wish.order:avgRecord:edit")
    @RequestMapping(value = "delete")
    public String delete(AvgRecord avgRecord, RedirectAttributes redirectAttributes) {
        avgRecordService.delete(avgRecord);
        addMessage(redirectAttributes, "删除历史平均数据成功");
        return "redirect:"+Global.getAdminPath()+"/wish.order/avgRecord/?repage";
    }

}