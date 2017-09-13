package com.wanfin.fpd.modules.wish.utils;

import com.wanfin.fpd.common.utils.StringUtils;

import java.math.BigDecimal;

/**
 * 金钱单位装换
 * Created by qiao on 2017/8/24.
 */
public class AmountUtil {

    /**
     * 金钱元转分
     *
     * @param amount
     * @return String
     */
    public static String yuanToFen(String amount) {
        if (StringUtils.isEmpty(amount)) {
            amount = "0";
        }
        BigDecimal hundred = new BigDecimal("100");
        BigDecimal bigDecimal = new BigDecimal(amount);
        String newAmount = bigDecimal.multiply(hundred).toString();
        return newAmount;
    }

    /**
     * 金钱分转元
     *
     * @param amount
     * @return String
     */
    public static String fenToYuan(String amount) {
        if (StringUtils.isEmpty(amount)) {
            amount = "0";
        }
        BigDecimal hundred = new BigDecimal("100");
        BigDecimal bigDecimal = new BigDecimal(amount);
        String newAmount = bigDecimal.divide(hundred).toString();
        return newAmount;
    }


    /**
     * 金钱元转分
     *
     * @param amount
     * @return BigDecimal
     */
    public static String yuanToFen(BigDecimal amount) {
        BigDecimal hundred = new BigDecimal("100");
        return amount.multiply(hundred).toString();
    }



    /**
     * 金钱分转元
     *
     * @param amount
     * @return BigDecimal
     */
    public static String fenToYuan(BigDecimal amount) {
        BigDecimal hundred = new BigDecimal("100");
        String newAmount = amount.divide(hundred).toString();
        return newAmount;
    }


    public static void main(String[] args) {
        BigDecimal num = new BigDecimal("33");
        System.out.println(num.multiply(new BigDecimal("100")));
        System.out.println(num.divide(new BigDecimal("100")));
        System.out.println(num.add(new BigDecimal("1")));
//        System.out.println(fenToYuan(new BigDecimal("11")));
    }
}
