
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herinoid.rsi.util;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Hewrei
 */
public class Utils {

    public static String[] monthName = {"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "Nopember", "Desember"};
    public final static String DATE_FORMAT = "dd/MM/yyyy";
    public final static String DATE_FORMAT2 = "dd-MM-yyyy";
    public final static String DATE_FORMAT_DB = "yyyy-MM-dd";
    public final static String DATE_FORMAT_DB_TIME = "yyyy-MM-dd HH:mm:ss";
    public final static String TIMESTAMP_FORMAT = "dd/MM/yyyy HH:mm:ss";
    public static String[] hariName = {"Ahad", "Senin", "Selasa", "Rabu", "Kamis", "Jum'at", "Sabtu"};
    public final static String FORMAT_TIME = "HH:mm:ss";

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

    public static String formatTanggal(java.sql.Date sqlDate) {
        java.util.Date utilDate = new java.util.Date(sqlDate.getTime());
        Calendar cal = Calendar.getInstance();
        cal.setTime(utilDate);
        int day = cal.get(Calendar.DAY_OF_WEEK);
        return hariName[day - 1] + ", " + convertDate(utilDate, DATE_FORMAT2);
    }

    public static String formatDateSql(java.sql.Date sqlDate) {
        java.util.Date utilDate = new java.util.Date(sqlDate.getTime());
        return convertDate(utilDate, DATE_FORMAT2);
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

    public static String formatDateTimeDb(Date date) {
        return convertDate(date, DATE_FORMAT_DB_TIME);
    }

    public static String formatTime(Date date) {
        return convertDate(date, FORMAT_TIME);
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

    public static Date convertToDate(String strDate, String style) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(style);
            return sdf.parse(strDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date getDateOnly(Date date) {
        try {
            Date today = new Date();
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date todayWithZeroTime = formatter.parse(formatter.format(today));
            return todayWithZeroTime;
        } catch (ParseException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static void print(String reportName, String reportDirName, String judul, Collection<?> list, Map parameters) {
        Properties systemProp = System.getProperties();
        String currentDir = systemProp.getProperty("user.dir");
        File dir = new File(currentDir);
        File fileRpt;
        String fullPath = "";
        if (dir.isDirectory()) {
            String[] isiDir = dir.list();
            for (String iDir : isiDir) {
                fileRpt = new File(currentDir + File.separatorChar + iDir + File.separatorChar + reportDirName + File.separatorChar + reportName);
                if (fileRpt.isFile()) { // Cek apakah file RptMaster.jrxml ada
                    fullPath = fileRpt.toString();
                    System.out.println("Found Report File at : " + fullPath);
                } // end if
            } // end for i
        } // end if

        try {
            String namafile = "./" + reportDirName + "/" + reportName;            
            JRDataSource dataSource = new JRBeanCollectionDataSource(list);
            JasperPrint jasperPrint = JasperFillManager.fillReport(namafile, parameters, dataSource);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setTitle(judul);
            Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
            jasperViewer.setSize(screen.width - 50, screen.height - 50);
            jasperViewer.setModalExclusionType(Dialog.ModalExclusionType.TOOLKIT_EXCLUDE);
            jasperViewer.setLocationRelativeTo(null);
            jasperViewer.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Report Can't view because : " + e);
            JOptionPane.showMessageDialog(null, "Report Can't view because : " + e);
        }

    }

}
