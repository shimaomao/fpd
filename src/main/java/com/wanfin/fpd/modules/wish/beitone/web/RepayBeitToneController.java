package com.wanfin.fpd.modules.wish.beitone.web;

import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.utils.IdGen;
import com.wanfin.fpd.common.utils.JsonOUtils;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.api.utils.InterfaceUtil;
import com.wanfin.fpd.modules.api.utils.test.SignEntity;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.files.entity.TContractFiles;
import com.wanfin.fpd.modules.files.service.TContractFilesService;
import com.wanfin.fpd.modules.sys.utils.UserUtils;
import com.wanfin.fpd.modules.wish.beitone.entity.Test;
import com.wanfin.fpd.modules.wish.order.entity.ReturnedMoney;
import com.wanfin.fpd.modules.wish.order.entity.TReturnedMsg;
import com.wanfin.fpd.modules.wish.order.service.ReturnedMoneyService;
import com.wanfin.fpd.modules.wish.order.service.TReturnedMsgService;
import com.wanfin.fpd.modules.wish.utils.SendBeiToneUtil;

import freemarker.ext.beans.HashAdapter;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URLDecoder;
import java.util.*;

/**
 * Created by qiao on 2017/8/29.
 */
@Controller
@RequestMapping(value = "${adminPath}/push/p2p")
public class RepayBeitToneController {
    @Autowired
    private ReturnedMoneyService returnedMoneyService;
    @Autowired
    private TReturnedMsgService returnedMsgService;
    @Autowired
    private TLoanContractService loanContractService;
    @Autowired
    private TContractFilesService contractFilesService;

    /**
     * 还款接口
     *
     * @param ids 多个id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/repayment")
    public Map<String, Object> repayment(String ids, String taskId) {
        TReturnedMsg returnedMsg = new TReturnedMsg();
        returnedMsg.setReturnedMoneyId(ids);
        List<TReturnedMsg> msgList = null;
        Map<String, Object> map = new HashMap<>();


        msgList = returnedMsgService.findListByReturnMoneyIds(returnedMsg);
        if (msgList.isEmpty()) {
            map.put("flag", "false");
            map.put("message", "数据为空！");
            return map;
        }


        try {
            List<TContractFiles> filesList = contractFilesService.findListByTaskIds("'"+taskId+"'");

            JSONObject paramJson = new JSONObject();
            String sign = null;
            Map<String, Object> repayInfoMap = new HashMap<>();

            StringBuilder idSeqs = new StringBuilder("");
            for (int i = 0; i < msgList.size(); i++) {
                returnedMsg = msgList.get(i);
                idSeqs.append(returnedMsg.getLoanContractId()).append(",");
                repayInfoMap.put(returnedMsg.getLoanContractId(), returnedMsg.getMoney());
            }
            idSeqs.deleteCharAt(idSeqs.length() - 1);
            paramJson.put("idSeqs", idSeqs.toString());
            paramJson.put("batchid", IdGen.uuid().substring(6, 16)); //存在且唯一
            paramJson.put("repayinfo", repayInfoMap); //手续费
            if (StringUtils.isBlank(filesList.get(0).getFilePath())){
                return null;
            }
            if (filesList.isEmpty()){
                paramJson.put("imgUrl", ""); //还款凭证
            } else {
                paramJson.put("imgUrl", Cons.BetToneParam.PROJECT_URL + filesList.get(0).getFilePath()); //还款凭证
            }
            paramJson.put("secretKey", SendBeiToneUtil.SECRET_KEY); //key
            String param = paramJson.toString();
            System.out.println("json内容:" + param);


            SignEntity signEntity = (SignEntity) InterfaceUtil.jsonToObject(param, SignEntity.class);
            System.out.println("sign内容:" + signEntity.toString());
            String result = InterfaceUtil.sendRequestByEntity(SendBeiToneUtil.REPAYMENT, paramJson);

            result = URLDecoder.decode(URLDecoder.decode(result, "utf-8"), "utf-8");
            System.out.println("----result:" + result);
            map = JsonOUtils.toMap(result);
            System.out.println("code:" + map.get("code"));
            String code = (String) map.get("code");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    
    
    @RequestMapping(value = "/repaymentNew")
    public String repaymentNew(String ids, String taskId,Model model, RedirectAttributes redirectAttributes) {
        TReturnedMsg returnedMsg = new TReturnedMsg();
        returnedMsg.setReturnedMoneyId(ids);
        List<TReturnedMsg> msgList = null;
        Map<String, Object> map = new HashMap<>();


        msgList = returnedMsgService.findListByReturnMoneyIds(returnedMsg);
        if (msgList.isEmpty()) {
            map.put("flag", "false");
            map.put("message", "发送失败，数据为空！");
            
            redirectAttributes.addAttribute("message", "发送失败，数据为空！");	
        	redirectAttributes.addAttribute("isClose", Global.YES);			
    		return "redirect:"+Global.getAdminPath()+"/wish/contract/returnMsglist";
        }


        try {
            List<TContractFiles> filesList = contractFilesService.findListByTaskIds("'"+taskId+"'");

            JSONObject paramJson = new JSONObject();
            String sign = null;
            Map<String, Object> repayInfoMap = new HashMap<>();

            StringBuilder idSeqs = new StringBuilder("");
            for (int i = 0; i < msgList.size(); i++) {
                returnedMsg = msgList.get(i);
                idSeqs.append(returnedMsg.getLoanContractId()).append(",");
                repayInfoMap.put(returnedMsg.getLoanContractId(), returnedMsg.getMoney());
            }
            idSeqs.deleteCharAt(idSeqs.length() - 1);
            paramJson.put("idSeqs", idSeqs.toString());
            paramJson.put("batchid", IdGen.uuid().substring(6, 16)); //存在且唯一
            paramJson.put("repayinfo", repayInfoMap); //手续费
            if (StringUtils.isBlank(filesList.get(0).getFilePath())){
                return null;
            }
            if (filesList.isEmpty()){
                paramJson.put("imgUrl", ""); //还款凭证
            } else {
                paramJson.put("imgUrl", Cons.BetToneParam.PROJECT_URL + filesList.get(0).getFilePath()); //还款凭证
            }
            paramJson.put("secretKey", SendBeiToneUtil.SECRET_KEY); //key
            String param = paramJson.toString();
            System.out.println("json内容:" + param);


            SignEntity signEntity = (SignEntity) InterfaceUtil.jsonToObject(param, SignEntity.class);
            System.out.println("sign内容:" + signEntity.toString());
            String result = InterfaceUtil.sendRequestByEntity(SendBeiToneUtil.REPAYMENT, paramJson);

            result = URLDecoder.decode(URLDecoder.decode(result, "utf-8"), "utf-8");
            System.out.println("----result:" + result);
            map = JsonOUtils.toMap(result);
            System.out.println("code:" + map.get("code"));
            String code = (String) map.get("code");
            map.put("message", code);
            redirectAttributes.addAttribute("message", (String) map.get("message"));	
        } catch (Exception e) {
            e.printStackTrace();
        }
        redirectAttributes.addAttribute("isClose", Global.YES);		
    	return "modules/wish/contract/returnMsglist";
       // return "redirect:"+Global.getAdminPath()+"/wish/contract/returnMsglist";
    }


}
