/*
 * Copyright (C) UseKamba Ltda - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential and will be punished by law
 * Written by Alexandre Antonio Juca <corextechnologies@gmail.com>
 *
 */

package com.usekamba.kambapaysdk.helpers;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.ISODateTimeFormat;

public class DateHelper {
    /***
     * Convert a Rails Date Object to day/month/year signature
     * @param string - A string version of the date (Usually this will come from a POJO)
     * @return a date in string format formatted accordingly
     */
    public static String convertDate(String string){
        String format = "dd/MM/yyyy";
        org.joda.time.format.DateTimeFormatter parser = ISODateTimeFormat.dateTimeParser();
        DateTime date = parser.parseDateTime(string);
        org.joda.time.format.DateTimeFormatter fmt = DateTimeFormat.forPattern(format);
        return date.toString(fmt);
    }


    /***
     * Convert a Rails Date Object to year signature
     * @param string - A string version of the date (Usually this will come from a POJO)
     * @return a date in string format formatted accordingly
     */
    public static String convertDateToYear(String string){
        String format = "yyyy";
        org.joda.time.format.DateTimeFormatter parser = ISODateTimeFormat.dateTimeParser();
        DateTime date = parser.parseDateTime(string);
        org.joda.time.format.DateTimeFormatter fmt = DateTimeFormat.forPattern(format);
        return date.toString(fmt);
    }


    /***
     * Convert a Rails Date object to hour:time signature
     @param string - A string version of the date (Usually this will come from a POJO)
      * @return a date in string format formatted accordingly
     */
    public static String convertTimeToMin(String string){
        String format = "HH:mm";
        org.joda.time.format.DateTimeFormatter parser = ISODateTimeFormat.dateTimeParser();
        DateTime date = parser.parseDateTime(string);
        org.joda.time.format.DateTimeFormatter fmt = DateTimeFormat.forPattern(format);
        return date.toString(fmt);
    }
}
