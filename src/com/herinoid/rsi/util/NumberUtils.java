/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herinoid.rsi.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hewrei
 */
public class NumberUtils {

    public final static String DATE_FORMAT_NUMBERING = "yyyyMMdd";

    public static String getNoResep(String noResep) {
        String old = noResep.substring(14, noResep.length());
        String oldDate = noResep.substring(6, 14);
        Date tglnoresep = Utils.convertToDate(oldDate, DATE_FORMAT_NUMBERING);
        if (tglnoresep.before(Utils.getDateOnly(new Date()))) {
            old = "0000";
        }
        int d = 4;
        String n = "ERESEP";
        String number = n + Utils.convertDate(new Date(), DATE_FORMAT_NUMBERING);
        int seq = 0;
        if (old != null) {
            seq = Integer.parseInt(old) + 1;
        }
        String seqStr = String.valueOf(seq);
        int seqLen = seqStr.length();
        for (int i = 0; i < d - seqLen; i++) {
            seqStr = "0" + seqStr;
        }
        return number + seqStr;
    }
}
