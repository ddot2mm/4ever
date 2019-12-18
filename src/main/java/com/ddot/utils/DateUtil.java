package com.ddot.utils;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * 时间格式化工具类
 */
public class DateUtil {
    //默认类型格式
    public static final String STANDER = "yyyy-MM-dd HH:mm:ss";

    //字符串类型时间转换成Date类型
    public static Date string2Date(String strDate){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(STANDER);
        DateTime dateTime = dateTimeFormatter.parseDateTime(strDate);
        return dateTime.toDate();
    }

    //自定义格式
    public static Date string2Date(String strDate,String format){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(format);
        DateTime dateTime = dateTimeFormatter.parseDateTime(strDate);
        return dateTime.toDate();
    }


    //Date类型转换成字符串
    public static String date2String(Date date){
        if (null == date){
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(STANDER);
    }


    //Date类型转换成指定格式的字符串
    public static String date2String(Date date,String format){
        if (null == date){
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(format);
    }

}
