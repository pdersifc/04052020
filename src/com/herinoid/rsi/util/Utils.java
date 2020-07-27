
	/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herinoid.rsi.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Hewrei
 */
public class Utils {

    public static String[] monthName = {"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "Nopember", "Desember"};
    public final static String DATE_FORMAT = "dd/MM/yyyy";
    public final static String DATE_FORMAT2 = "dd-MM-yyyy";
    public final static String DATE_FORMAT_DB = "yyyy-MM-dd";
    public final static String TIMESTAMP_FORMAT = "dd/MM/yyyy HH:mm:ss";
    public static String[] hariName = {"Ahad","Senin","Selasa","Rabu","Kamis","Jum'at","Sabtu"};

    public static java.sql.Date convertUtilToSql(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }

    public static String format(double amount, int digit) {
        String format = "###,##0";
        if (digit > 0) {
            format += ".";
            for (int i = 1; i <= digit; i++) {
                format += "0";
            }
        }
        NumberFormat formater = new DecimalFormat(format);
        return formater.format(amount);
    }
    
    public static String formatTanggal(java.sql.Date sqlDate){
        java.util.Date utilDate = new java.util.Date(sqlDate.getTime());
        Calendar cal = Calendar.getInstance();
        cal.setTime(utilDate);
        int day = cal.get(Calendar.DAY_OF_WEEK);
        return hariName[day-1]+", "+ convertDate(utilDate, DATE_FORMAT2);
    }

    public static String convertDate(Date date, String style) {
        SimpleDateFormat sdf = new SimpleDateFormat(style);
        return date == null ? null : sdf.format(date);
    }

    public static String format(Date date) {
        return convertDate(date, DATE_FORMAT);
    }

    public static String formatDb(Date date) {
        return convertDate(date, DATE_FORMAT_DB);
    }
    
    public static Date getFirstDayInMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }
    
    public static Date getLastDayInMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }
    
    public static Date getPrevWeekDate(Date date) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.WEEK_OF_MONTH, -1);
            return cal.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static Date getTriWulanDate(Date date) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.MONTH, -3);
            return cal.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static Date getSemesterDate(Date date) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.MONTH, -6);
            return cal.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
     public static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }

}