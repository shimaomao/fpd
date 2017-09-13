package com.wanfin.fpd.modules.wish.beitone.service;

import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.utils.IdcardUtils;
import com.wanfin.fpd.common.utils.JsonOUtils;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.sys.utils.UserUtils;
import com.wanfin.fpd.modules.wish.beitone.dao.ProductPushDao;
import com.wanfin.fpd.modules.wish.beitone.entity.ProductPush;
import com.wanfin.fpd.modules.wish.merchant.entity.Merchant;
import com.wanfin.fpd.modules.wish.merchant.service.MerchantService;
import com.wanfin.fpd.modules.wish.utils.SendBeiToneUtil;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qiao on 2017/8/21.
 */
@Service
@Transactional(readOnly = false)
public class ProductPushService extends CrudService<ProductPushDao, ProductPush> {

    public static final String SECRET_KEY = Cons.BetToneParam.SECRET_KEY;
    public static final String GET_SIGN = Cons.BetToneParam.GET_SIGN;
    public static final String RELEASE_LOAN = Cons.BetToneParam.RELEASE_LOAN;
    public static final String REPAYMENT = Cons.BetToneParam.REPAYMENT;

    @Autowired
    public MerchantService merchantService;

    @Autowired
    private TLoanContractService tLoanContractService;


    @Override
    public ProductPush get(String id) {
        return super.get(id);
    }

    @Override
    public ProductPush get(ProductPush entity) {
        return super.get(entity);
    }

    @Override
    public List<ProductPush> findList(ProductPush entity) {
        return super.findList(entity);
    }

    @Override
    public Page<ProductPush> findPage(Page<ProductPush> page, ProductPush entity) {
        return super.findPage(page, entity);
    }

    @Override
    public void save(ProductPush entity) {
        super.save(entity);
    }

    @Override
    public void delete(ProductPush entity) {
        super.delete(entity);
    }

    @Override
    public List<ProductPush> findAllList(ProductPush entity) {
        return super.findAllList(entity);
    }

    public Map<String, Object> releaseLoan(String loanContractId) {
        Map<String, Object> map = new HashMap<>();

        TLoanContract loanContract = tLoanContractService.get(loanContractId);
        //获取验签
        String sign = (String) UserUtils.getSession().getAttribute("sign");
        if (StringUtils.isBlank(sign)) {
            System.out.println("-------------获取p2p验签-------------");
            sign = SendBeiToneUtil.getSign(loanContract);
        }
        System.out.println("sign:" + sign);

        Merchant merchant = merchantService.getByUserNum(loanContract.getCustomerId());
        String idCard = "";
        String name = "";
        JSONObject paramJson = new JSONObject();
        //客户信息  1个人  2企业
        if ("1".equals(merchant.getShopType())){
            idCard = merchant.getIdNumber();
            name = merchant.getIdName();
        } else {
            idCard = merchant.getLegalPersonIdNumber();
            name = merchant.getLegalPersonName();
        }
        paramJson.put("corpId", merchant.getUserId()); //易联用户id
        paramJson.put("cardNum", idCard); //证件号码
        paramJson.put("name", name); //姓名
        paramJson.put("education", ""); //学历
        paramJson.put("householdRegAddr", getBirthAddress(idCard)); //家庭住址
        paramJson.put("sex", getSex(idCard)); //性别
        paramJson.put("birth", getBirthDay(idCard)); //生日
        paramJson.put("marriage", ""); //婚姻状况
        paramJson.put("phone", merchant.getPhone()); //联系电话

        paramJson.put("corpCardNum", merchant.getBusinessLicenseNumb()); //公司执照证件号
        paramJson.put("corpName", merchant.getCorpName()); //公司名称
        paramJson.put("corpRegisterDate", merchant.getMerchantRegDate());// 公司注册时间
        paramJson.put("corpRegisterAmount", ""); //公司注册资金
        paramJson.put("corpAddr", merchant.getMerchantAddress()); //公司地址
        paramJson.put("corpIndustry", ""); //公司行业

        //借款信息
        paramJson.put("idSeq", loanContract.getId());  //借款合同id
        paramJson.put("amount", loanContract.getLoanAmount()); //贷款金额
        paramJson.put("yearConversion", loanContract.getLoanRate()); //年化利率
        paramJson.put("limitTime", Integer.parseInt(loanContract.getLoanPeriod()) + 30); //贷款期限
        paramJson.put("limit_time_flexible", Integer.parseInt(loanContract.getLoanPeriod()) + 29); //灵活期
        paramJson.put("bankInfo", loanContract.getGatheringBank()); //银行信息
        paramJson.put("bankowner", loanContract.getGatheringName()); //银行姓名
        paramJson.put("bankno", loanContract.getGatheringNumber()); //银行卡号

        paramJson.put("email", merchant.getEmail()); //邮箱
        paramJson.put("shopPhone", ""); //店铺联系电话
        paramJson.put("shopCode", merchant.getId()); //商户号
        paramJson.put("shopName", merchant.getShopName()); //商户名称
        paramJson.put("shopUrl", merchant.getEcommerceLink()); //店铺url
        paramJson.put("shopAddr", merchant.getMerchantAddress()); //商户地址
        paramJson.put("shopType", merchant.getShopType());    //商品类型
        paramJson.put("shopRegTime", merchant.getMerchantRegDate()); //商铺注册时间
        paramJson.put("shopAssessment", merchant.getAssessment()); //评价数量
        paramJson.put("shopAvgAssessment", merchant.getAvgAssessment()); //评价分

        Map<String, String> resourcesMap = new HashMap<>();
        resourcesMap.put("1-1", Cons.BetToneParam.PROJECT_URL + merchant.getIdPhotoPath());
        resourcesMap.put("1-2", Cons.BetToneParam.PROJECT_URL + merchant.getLegalPersonPhotoPath());
        paramJson.put("resources", resourcesMap);
        System.out.println("--------------贝通sign:" + sign);
        paramJson.put("sign", sign); //验签
        paramJson.put("secretKey", SECRET_KEY); //key
        System.out.println("贷款申请信息：" + paramJson.toString());


        try {
            String result = SendBeiToneUtil.getResult(paramJson, RELEASE_LOAN);
            map = JsonOUtils.toMap(result);

            if (!"success".equals((String) map.get("code"))){
                loanContract.setStatus(Cons.LoanContractStatus.TO_SUSPENSION);
                loanContract.setRemarks((String) map.get("message"));
                tLoanContractService.updateStatus(loanContract);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


    public static String getBirthAddress(String idCard) {
        String address = idCard.substring(6, 14);
        System.out.println("address:" + address);
        address = IdcardUtils.getProvinceByIdCard(idCard);
        return address;
    }

    public String getBirthDay(String idCard) {

        StringBuffer birthday = new StringBuffer();
        birthday.append(IdcardUtils.getYearByIdCard(idCard)).append("-")
                .append(IdcardUtils.getMonthByIdCard(idCard)).append("-")
                .append(IdcardUtils.getDateByIdCard(idCard));
        return birthday.toString();
    }

    public static String getSex(String idCard) {
        String sex = idCard.substring(idCard.length() - 2, idCard.length() - 1);
        int sexNum = Integer.parseInt(sex);

        sex = "男";
        if (sexNum % 2 != 0) {
            sex = "男";
        } else {
            sex = "女";
        }
        return sex;
    }

    public static void main(String[] args) {
        System.out.println(getBirthAddress("231081197908151487 "));
    }

}
