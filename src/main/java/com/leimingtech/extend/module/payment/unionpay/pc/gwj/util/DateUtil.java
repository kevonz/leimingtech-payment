package com.leimingtech.extend.module.payment.unionpay.pc.gwj.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期时间工具类
 */
public class DateUtil {

    public static final String DATE_FMT_YMDHMSS = "yyyyMMddHHmmssSSS";
    public static final String DATE_FMT_YMD_HMSS = "yyyyMMdd HH:mm:ss";
    public static final String DATE_FMT_YMDHMS = "yyyyMMddHHmmss";
    public static final String DATE_FMT_Y_M_D = "yyyy-MM-dd";
    public static final String DATE_FMT_YMD = "yyyyMMdd";
    public static final String DATE_FMT_YM = "yyyyMM";
    public static final String DATE_FMT_HMS = "HHmmss";
    public static final String DATE_FMT_MD = "MMdd";
    public static final String DATE_FMT_HM = "HH:mm";

    /**
     * 将 {@link java.util.Date} 格式化成特定格式。
     * 
     * @param date {@link java.util.Date}
     * @param format 参见 {@link java.text.SimpleDateFormat}
     * @return 格式化后的字符串
     */
    public static String getDate(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 将 {@link java.util.Date} 格式化成格式 "yyyyMMdd"。
     * 
     * @param date {@link java.util.Date}
     * @return 格式化后的字符串
     */
    public static String getDate(Date date) {
        return getDate(date, DATE_FMT_YMD);
    }

    /**
     * 将 {@link java.util.Date} 格式化成格式 "yyyyMMddHHmmss"。
     * 
     * @param date {@link java.util.Date}
     * @return 格式化后的字符串
     */
    public static String getDateTime(Date date) {
        return getDate(date, DATE_FMT_YMDHMS);
    }

    /**
     * 将 {@link java.util.Date} 格式化成格式 "HHmmss"。
     * 
     * @param date {@link java.util.Date}
     * @return 格式化后的字符串
     */
    public static String getHourMinuteOfTime(Date date) {
        return new SimpleDateFormat(DATE_FMT_HMS).format(date);
    }

    /**
     * 将 {@link java.util.Date} 格式化成格式 "MMdd"。
     * 
     * @param date {@link java.util.Date}
     * @return 格式化后的字符串
     */
    public static String getDayOfMonth(Date date) {
        return new SimpleDateFormat(DATE_FMT_MD).format(date);
    }

    /**
     * 参见 {@link java.util.Calendar#add}。
     * 
     * @param date {@link java.util.Date}
     * @param dayInterval
     * @return
     */
    public static Date addDate(Date date, int dayInterval) {
        return addDate(date, Calendar.DAY_OF_MONTH, dayInterval);
    }

    /**
     * 参见 {@link java.util.Calendar#add}。
     * 
     * @param date {@link java.util.Date}
     * @param monthInterval
     * @return
     */
    public static Date addMonth(Date date, int monthInterval) {
        return addDate(date, Calendar.MONDAY, monthInterval);
    }

    /**
     * 参见 {@link java.util.Calendar#add}。
     * 
     * @param date {@link java.util.Date}
     * @param field
     * @param interval
     * @return
     */
    public static Date addDate(Date date, int field, int interval) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(field, interval);
        return c.getTime();
    }

    /**
     * 将一个格式为 "yyyy-MM-dd" 的字符串转成 {@link java.util.Date}。
     * 
     * @param time 一个格式为 "yyyy-MM-dd" 的字符串
     * @return 正常情况返回 {@link java.util.Date}； 转换失败的情况下，返回null。
     */
    public static Date parseDate(String time) {
        return parseDate(time,DATE_FMT_Y_M_D);
    }

    /**
     * 将一个格式为 "yyyyMMddHHmmss" 的字符串转成 {@link java.util.Date}。
     * 
     * @param time 一个格式为 "yyyyMMddHHmmss" 的字符串
     * @return 正常情况返回 {@link java.util.Date}； 转换失败的情况下，返回null。
     */
    public static Date parseDateTime(String time) {
        return parseDate(time,DATE_FMT_YMDHMS);
    }

    /**
     * 将一个格式为  "HH:mm" 的字符串转成 {@link java.util.Date}。
     * 
     * @param time 一个格式为  "HH:mm" 的字符串
     * @return 正常情况返回 {@link java.util.Date}； 转换失败的情况下，返回null。
     */
    public static Date parseTime(String time) {
        return parseDate(time,DATE_FMT_HM);
    }
    
    /**
     * 将一个格式为 {@linkplain format} 的字符串转成 {@link java.util.Date}。
     * 
     * @param time 一个格式为 {@linkplain format} 的字符串
     * @param format 参见 {@link java.text.SimpleDateFormat}
     * @return 正常情况返回 {@link java.util.Date}； 转换失败的情况下，返回null。
     */
    public static Date parseDate(String time, String format) {
        try {
            SimpleDateFormat s = new SimpleDateFormat(format);
            s.setLenient(false);
            return s.parse(time);
        } catch (Exception e) {
            //Ignore
        }
        return null;
    }
    
    /**
     * 判断是否是一个合法的日期
     * 
     * @param date
     *          待判断的日期
     * @param format
     *          日期的格式
     * @return
     *          合法日期返回true，非法日期返回false
     */
    public static boolean isValidDate(String date, String format){
        SimpleDateFormat s = new SimpleDateFormat(format);
        try {
            s.setLenient(false);
            s.parse(date);
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }
}
