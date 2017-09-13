package com.wanfin.fpd.modules.wish.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DoubleTwo {
    /**
     * 保留整数部分
     * @param d
     * @return
     */
    public static double formatDown(double d) {

        BigDecimal bg = new BigDecimal(d).setScale(0, RoundingMode.DOWN);
        return bg.doubleValue();
    }
    /**
     * 保留两位小数，去除小数点后两位
     * @param d
     * @return
     */
    public static double formatDoubleDown(double d) {

        BigDecimal bg = new BigDecimal(d).setScale(2, RoundingMode.DOWN);
        return bg.doubleValue();
    }
    
    /**
     * 四舍五入保留两位小数
     * @param d
     * @return
     */
    public static double formatDoubleUp(double d) {
        BigDecimal bg = new BigDecimal(d).setScale(2, RoundingMode.UP);
        return bg.doubleValue();
    }


    public static void main(String[] args) {
        double d = 12345.67890;
        System.out.println(formatDown(d));
        System.out.println(formatDoubleDown(d));
        System.out.println(formatDoubleUp(d));
       
    }
    
    
    
    
}
