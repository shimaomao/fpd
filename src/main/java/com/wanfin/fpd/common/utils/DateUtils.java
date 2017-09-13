/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.joda.time.DateTime;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 *
 * @author ThinkGem
 * @version 2014-4-15
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    private static final int TIME_SECOND = 1000;
    private static final int TIME_MIN = 1000 * 60;
    private static final int TIME_HOURS = 1000 * 60 * 60;
    private static final int TIME_DAY = 1000 * 60 * 60 * 24;
    private static final String DATE_FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_FORMAT_YMD = "yyyy-MM-dd";

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM",
            "yyyyMMdd", "yyyyMMddHHmmss", "yyyyMMddHHmm", "yyyyMM"};

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd）
     */
    public static String getDate() {
        return getDate("yyyy-MM-dd");
    }

    /**
     * 计算两个日期间的月数，按30一个月
     *
     * @param early 早期日期,格式:yyyy-MM-dd
     * @param late  晚期日期,格式:yyyy-MM-dd
     * @param add   不足一个月是否按一个月算
     * @return
     * @throws ParseException
     */
    public static int monthBetween(String early, String late, boolean add) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return monthBetween(sdf.parse(early), sdf.parse(late), add);
    }

    public static int monthBetween(Date early, Date late, boolean add) throws ParseException {
        long l = late.getTime();
        long e = early.getTime();

        long bet = l - e;
        //long month=bet/(24*60*60*1000)/30;
        long day = bet / TIME_DAY;
        //long hour=(bet/(60*60*1000)-day*24);
        //long min=((bet/(60*1000))-day*24*60-hour*60);
        //long s=(bet/1000-day*24*60*60-hour*60*60-min*60);
        //System.out.println(month+"月,余"+(day%30)+"天");
        //System.out.println(""+day+"天"+hour+"小时"+min+"分"+s+"秒");
        int subMonth = (int) (day / 30);
        if (add && (day % 30) > 0) {
            subMonth++;
        }
        return subMonth;
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatDate(Date date, Object... pattern) {
        String formatDate = null;
        if (pattern != null && pattern.length > 0) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }

    /**
     * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到日期时间字符串，转换格式（yyyy-MM-dd）
     */
    public static String formatDate(Date date) {
        return formatDate(date, "yyyy-MM-dd");
    }

    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     */
    public static String getTime() {
        return formatDate(new Date(), "HH:mm:ss");
    }

    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String getDateTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     */
    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    /**
     * 得到当前月份字符串 格式（MM）
     */
    public static String getMonth() {
        return formatDate(new Date(), "MM");
    }

    /**
     * 得到当天字符串 格式（dd）
     */
    public static String getDay() {
        return formatDate(new Date(), "dd");
    }

    /**
     * 得到当前星期字符串 格式（E）星期几
     */
    public static String getWeek() {
        return formatDate(new Date(), "E");
    }

    public static Date stringToDate(String dateString, Object... pattern) {
        if (dateString == null) {
            return null;
        }
        try {
            SimpleDateFormat sdf = null;
            if (pattern != null && pattern.length > 0) {
                sdf = new SimpleDateFormat(pattern[0].toString());
            } else {
                sdf = new SimpleDateFormat(DATE_FORMAT_DEFAULT);
            }
            Date date = sdf.parse(dateString);
            return date;
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * 日期型字符串转化为日期 格式
     * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
     * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
     * "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取过去的天数
     *
     * @param date
     * @return
     */
    public static long pastDays(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / TIME_DAY;
    }

    /**
     * 获取过去的小时
     *
     * @param date
     * @return
     */
    public static long pastHour(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / TIME_HOURS;
    }

    /**
     * 获取过去的分钟
     *
     * @param date
     * @return
     */
    public static long pastMinutes(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / TIME_MIN;
    }

    /**
     * 转换为时间（天,时:分:秒.毫秒）
     *
     * @param timeMillis
     * @return
     */
    public static String formatDateTime(long timeMillis) {
        long day = timeMillis / TIME_DAY;
        long hour = (timeMillis / TIME_HOURS - day * 24);
        long min = ((timeMillis / TIME_MIN) - day * 24 * 60 - hour * 60);
        long s = (timeMillis / TIME_SECOND - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long sss = (timeMillis - day * TIME_DAY - hour * TIME_HOURS - min * TIME_MIN - s * TIME_SECOND);
        return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
    }

    /**
     * 获取两个日期之间的天数
     *
     * @param before
     * @param after
     * @return
     */
    public static double getDistanceOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / TIME_DAY;
    }


    /**
     * 获取两个日期之间的小时数
     *
     * @param before
     * @param after
     * @return
     */
    public static double getDistanceHoursOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / TIME_HOURS;
    }

    /**
     * 获取两个日期之间的毫秒数
     *
     * @param before
     * @param after
     * @return
     */
    public static Long getDistanceTimeOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime);
    }

    public static String getCurrentTime() {
        return new DateTime().toString(DATE_FORMAT_DEFAULT);
    }

    public static String getCurrentDay() {
        return new DateTime().toString(DATE_FORMAT_YMD);
    }

    /**
     * 时间比较
     *
     * @param str1 时间1
     * @param str2 时间2
     * @return date1<date2=-1;date1=date2=0;date1>date2=1
     * @throws ParseException
     */
    public static int compareTo(String str1, String str2) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdf.parse(str1);
        Date date2 = sdf.parse(str2);
        return date1.compareTo(date2);
    }

    /**
     * 判断日期是否在两个时间之间
     *
     * @param date
     * @param dateFirst
     * @param dateEnd
     * @return -2：在两个时间之前；-1和前一个时间相等；0在两个时间之间或三个时间相等；1：和后一个时间相等；2在两个时间之后
     */
    public static int dateDuring(String date, String dateFirst, String dateEnd) {
        return dateDuring(DateUtils.parseDate(date), DateUtils.parseDate(dateFirst), DateUtils.parseDate(dateEnd));
    }

    /**
     * 判断日期是否在两个时间之间
     *
     * @param date
     * @param dateFirst
     * @param dateEnd
     * @return -2：在两个时间之前；-1和前一个时间相等；0在两个时间之间或三个时间相等；1：和后一个时间相等；2在两个时间之后
     */
    public static int dateDuring(Date date, Date dateFirst, Date dateEnd) {
        boolean ifasc = true;
        int position = dateEnd.compareTo(dateFirst);
        if (position > 0) {
            ifasc = true;
        } else if (position == 0) {
            position = date.compareTo(dateFirst);
            if (position > 0) {
                return 2;
            } else if (position == 0) {
                return 0;
            } else {
                return -2;
            }
        } else {
            ifasc = false;
        }

        if (ifasc) {//<
            position = date.compareTo(dateFirst);
            if (position < 0) {
                return -2;
            } else if (position == 0) {
                return -1;
            } else {
                position = date.compareTo(dateEnd);
                if (position < 0) {
                    return 0;
                } else if (position == 0) {
                    return 1;
                } else {
                    return 2;
                }
            }
        } else {//>
            position = date.compareTo(dateFirst);
            if (position > 0) {
                return 2;
            } else if (position == 0) {
                return 1;
            } else {
                position = date.compareTo(dateEnd);
                if (position < 0) {
                    return -2;
                } else if (position == 0) {
                    return -1;
                } else {
                    return 0;
                }
            }
        }
    }

    /**
     * 日期的计算
     *
     * @param baseDate 准备计算的日期
     * @param addYear  增加年数，可以为负
     * @param addMonth 增加月数，可以为负
     * @param addDay   增加天数，可以为负
     * @return 计算后的日期
     */
    public static String calculateDate(String baseDate, int addYear, int addMonth, int addDay, Object... pattern) {
        try {
            String formatPattern = "yyyy-MM-dd";
            if (pattern != null && pattern.length > 0) {
                formatPattern = pattern[0].toString();
            }
            SimpleDateFormat sdf = new SimpleDateFormat(formatPattern);
            Date dt = sdf.parse(baseDate);
            Calendar rightNow = Calendar.getInstance();

            rightNow.setTime(dt);
            rightNow.add(Calendar.YEAR, addYear);// 日期减年
            rightNow.add(Calendar.MONTH, addMonth);// 日期加月
            rightNow.add(Calendar.DAY_OF_YEAR, addDay);// 日期加天
            Date dt1 = rightNow.getTime();
            //String reStr = sdf.format(dt1);
            return sdf.format(dt1);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 日期的计算
     *
     * @param baseDate 准备计算的日期
     * @param addYear  增加年数，可以为负
     * @param addMonth 增加月数，可以为负
     * @param addDay   增加天数，可以为负
     * @return 计算后的日期
     */
    public static Date calculateDate(Date baseDate, int addYear, int addMonth, int addDay) {
        Calendar rightNow = Calendar.getInstance();

        rightNow.setTime(baseDate);
        rightNow.add(Calendar.YEAR, addYear);// 日加年
        rightNow.add(Calendar.MONTH, addMonth);// 日期加月
        rightNow.add(Calendar.DAY_OF_YEAR, addDay);// 日期加天

        return rightNow.getTime();
    }

    /**
     * 日期的计算
     *
     * @param baseDate  准备计算的日期
     * @param addYear   增加年数，可以为负
     * @param addMonth  增加月数，可以为负
     * @param addDay    增加天数，可以为负
     * @param addHours  增加时，可以为负
     * @param addMin    增加分，可以为负
     * @param addSecond 增加秒，可以为负
     * @return 计算后的日期
     */
    public static Date calculateDate(Date baseDate, int addYear, int addMonth, int addDay, int addHours, int addMin, int addSecond) {
        Calendar rightNow = Calendar.getInstance();

        rightNow.setTime(baseDate);
        rightNow.add(Calendar.YEAR, addYear);// 日加年
        rightNow.add(Calendar.MONTH, addMonth);// 日期加月
        rightNow.add(Calendar.DAY_OF_YEAR, addDay);// 日期加天
        rightNow.add(Calendar.HOUR_OF_DAY, addHours);// 日期加时
        rightNow.add(Calendar.MINUTE, addMin);// 日期加分
        rightNow.add(Calendar.SECOND, addSecond);// 日期加秒

        return rightNow.getTime();
    }

    /**
     * 日期的计算
     *
     * @param baseDate  准备计算的日期
     * @param addYear   增加年数，可以为负
     * @param addMonth  增加月数，可以为负
     * @param addDay    增加天数，可以为负
     * @param addWeek   增加周，可以为负
     * @param addHours  增加时，可以为负
     * @param addMin    增加分，可以为负
     * @param addSecond 增加秒，可以为负
     * @return 计算后的日期
     */
    public static Date calculateDate(Date baseDate, int addYear, int addMonth, int addWeek, int addDay, int addHours, int addMin, int addSecond) {
        Calendar rightNow = Calendar.getInstance();

        rightNow.setTime(baseDate);
        rightNow.add(Calendar.YEAR, addYear);// 日加年
        rightNow.add(Calendar.MONTH, addMonth);// 日期加月
        rightNow.add(Calendar.DAY_OF_YEAR, addDay);// 日期加天
        rightNow.add(Calendar.WEEK_OF_YEAR, addWeek);// 日期加周
        rightNow.add(Calendar.HOUR_OF_DAY, addHours);// 日期加时
        rightNow.add(Calendar.MINUTE, addMin);// 日期加分
        rightNow.add(Calendar.SECOND, addSecond);// 日期加秒

        return rightNow.getTime();
    }

    public static String getMorningDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, -12);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        System.out.println(sdf.format(date));
        return sdf.format(date);
    }

    public static String getEveningDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, +12);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        System.out.println(sdf.format(date));
        return sdf.format(date);
    }

    public static String getMonthFirstDay(){
        Calendar cale = null;
        cale = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String firstday, lastday;
        // 获取前月的第一天
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        firstday = format.format(cale.getTime());
        return firstday;
    }

    public static String getMonthLastDay(){
        // 获取前月的最后一天
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cale = null;
        cale = Calendar.getInstance();
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        return format.format(cale.getTime());
    }

    /**
     * @param args
     * @throws ParseException
     */
    public static void main(String[] args) throws ParseException {
        // 获取当月第一天和最后一天
        Calendar cale = null;
        cale = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String firstday, lastday;
        // 获取前月的第一天
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        firstday = format.format(cale.getTime());
        // 获取前月的最后一天
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        lastday = format.format(cale.getTime());
        System.out.println("本月第一天和最后一天分别是 ： " + firstday + " and " + lastday);

//		Date s = calculateDate(new Date(), 1, 1, 1);
//		Date s = calculateDate(new Date(), 1, 1, 1, 1, 1, 1);
//		System.out.println(f.format(s1));
//		System.out.println(f.format(s));
    }
}
