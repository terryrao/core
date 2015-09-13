package com.raowei.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @author raow
 * @version 2015-09-13
 * @since 1.0
 */
public class DateUtils {

    /**
     * 获取指定年份、指定周数的第一天
     *
     * @param year 年
     * @param week 周
     */
    public static Date getFisrtDayOfWeek(int year, int week) {
        Calendar cd = Calendar.getInstance();
        cd.set(Calendar.YEAR, year);
        cd.set(Calendar.WEEK_OF_YEAR, week);
        cd.setFirstDayOfWeek(Calendar.SUNDAY);
        cd.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return cd.getTime();
    }

    /**
     * 获取指定年份、指定周数的最后一天
     *
     * @param year 年
     * @param week 周
     */
    public static Date getLastDayOfWeek(int year, int week) {
        Calendar cd = Calendar.getInstance();
        cd.set(Calendar.YEAR, year);
        cd.setFirstDayOfWeek(Calendar.SUNDAY);
        cd.set(Calendar.WEEK_OF_YEAR, week);
        cd.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        return cd.getTime();
    }


    /**
     * 获取指定年份、指定月份的第一天
     *
     * @param year  年
     * @param month 月
     */
    public static Date getFirstDayOfMonth(int year, int month) {
        Calendar cd = Calendar.getInstance();
        cd.set(Calendar.YEAR, year);
        cd.set(Calendar.MONTH, month);
        return DateUtils.getFirstDayOfMonth(cd.getTime());
    }

    /**
     * 获取指定年份、指定月份的最后一天
     *
     * @param year  年
     * @param month 月
     */
    public static Date getLastDayOfMonth(int year, int month) {
        Calendar cd = Calendar.getInstance();
        cd.set(Calendar.YEAR, year);
        cd.set(Calendar.MONTH, month);
        return DateUtils.getLastDayOfMonth(cd.getTime());
    }

    /**
     * 获取指定日期当月最后一天 date为null时为当前月
     *
     * @param date
     * @return Date
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        if (date == null) {
            date = new Date();
        }
        c.setTime(date);
        c.set(Calendar.DATE, c.getActualMaximum(Calendar.DATE));
        return c.getTime();
    }

    /**
     * 获取指定日期当月第一天 date为null时为当前月
     *
     * @param date
     * @return Date
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        if (date == null) {
            date = new Date();
        }
        c.setTime(date);
        c.set(Calendar.DATE, c.getActualMinimum(Calendar.DATE));
        return c.getTime();
    }


}
