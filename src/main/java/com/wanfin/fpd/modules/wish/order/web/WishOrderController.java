/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wish.order.web;

import java.io.*;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wanfin.fpd.common.quartz.ftp.DateUtil;
import com.wanfin.fpd.modules.receivables.service.RepayRecordService;
import com.wanfin.fpd.modules.wish.contract.vo.RepayRecordVo;
import com.wanfin.fpd.modules.wish.utils.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zefer.d.c.s;

import com.sun.media.sound.SF2GlobalRegion;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.quartz.WishOrderQuartz;
import com.wanfin.fpd.common.quartz.ftp.FileUtil;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.common.utils.StringUtils;
import com.jcraft.jsch.ChannelSftp;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.modules.api.wiss.entity.InformOrder;
import com.wanfin.fpd.modules.api.wiss.service.InformOrderService;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.sys.utils.UserUtils;
import com.wanfin.fpd.modules.wish.order.entity.WishOrder;
import com.wanfin.fpd.modules.wish.order.service.WishOrderService;

/**
 * 原始订单数据Controller
 *
 * @author cjp
 * @version 2017-06-27
 */
@ApiIgnore
@Controller
@RequestMapping(value = "/wish/order/wishOrder")
public class WishOrderController extends BaseController {
    private String userId;// 卖家在易联的编号

    @Autowired
    private WishOrderService wishOrderService;
    @Autowired
    public InformOrderService informOrderService;
    @Autowired
    private RepayRecordService repayRecordService;


    @ModelAttribute
    public WishOrder get(@RequestParam(required = false) String id) {
        WishOrder entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = wishOrderService.get(id);
        }
        if (entity == null) {
            entity = new WishOrder();
        }
        return entity;
    }

    @RequestMapping(value = {"list", ""})
    public String list(WishOrder wishOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
        userId = (String) UserUtils.getSession().getAttribute("wishUserId");

        wishOrder.setUserId(userId);
        wishOrder.setLoanOperation("0");
        int pageNo = wishOrder.getPage().getPageNo(); // 当前页码
        int pageSize = wishOrder.getPage().getPageSize();
        Double sumAmount = wishOrderService.getSumAmount(wishOrder);
        Page<WishOrder> page = new Page<WishOrder>(request, response);
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        if (StringUtils.isNotBlank(userId)) {
            page = wishOrderService.findPage(page, wishOrder);
        }
        model.addAttribute("page", page);
        model.addAttribute("wishOrder", wishOrder);
        model.addAttribute("sumAmount", sumAmount);
        return "modules/wishNew/order/orderList";// 已开通跳转到---收款信息页面
    }


    @RequiresPermissions("wish.order:wishOrder:view")
    @RequestMapping(value = "form")
    public String form(WishOrder wishOrder, Model model) {
        model.addAttribute("wishOrder", wishOrder);
        return "modules/wish.order/wishOrderForm";
    }

    @RequiresPermissions("wish.order:wishOrder:edit")
    @RequestMapping(value = "save")
    public String save(WishOrder wishOrder, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, wishOrder)) {
            return form(wishOrder, model);
        }
        wishOrderService.save(wishOrder);
        addMessage(redirectAttributes, "保存原始订单数据成功");
        return "redirect:" + Global.getAdminPath() + "/wish.order/wishOrder/?repage";
    }

    @RequiresPermissions("wish.order:wishOrder:edit")
    @RequestMapping(value = "delete")
    public String delete(WishOrder wishOrder, RedirectAttributes redirectAttributes) {
        wishOrderService.delete(wishOrder);
        addMessage(redirectAttributes, "删除原始订单数据成功");
        return "redirect:" + Global.getAdminPath() + "/wish.order/wishOrder/?repage";
    }




}