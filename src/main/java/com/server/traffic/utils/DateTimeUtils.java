package com.server.traffic.utils;

import com.server.traffic.utils.error.CustomException;
import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DateTimeUtils
 *
 * @author DatDV
 */
public class DateTimeUtils {

    /**
     * getDateTimeNow get date time now convert to String
     *
     * @param format : format of date
     * @return String {java.lang.String}
     */
    public static String getDateTimeNow(String format) {
        if (format == null) {
            format = "dd/MM/yyyy HH:mm:ss";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date dateNow = new Date(System.currentTimeMillis());
        return formatter.format(dateNow);
    }

    /**
     * formatDateTimeQuery : format String date use query in database
     *
     * @param date : string date input of function
     * @return String {java.lang.String}
     * @throws ParseException
     */
    public static String formatDateTimeQuery(String date) throws ParseException {
        String dateNew = date;
        if (date.trim().contains("-")) {
            dateNew = date.trim().replace("-", "/");
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(dateNew);
        return formatter.format(date1);
    }

    /**
     * convertStringRequestToTimesTamp : convert string date client request to Timestamp
     *
     * @param date       : string date client request
     * @param dateFormat : format of date (client request)
     * @return Timestamp {java.sql.Timestamp}
     */
    public static Timestamp convertStringRequestToTimesTamp(String date, String dateFormat) {
        try {
            if (StringUtils.isBlank(date)) {
                return null;
            } else {
                DateFormat formatter = new SimpleDateFormat(dateFormat);
                Timestamp result = null;
                if (date.contains("T")) {
                    java.sql.Date dateAfterFormat = (java.sql.Date) formatter.parse(date.trim().replaceAll("Z$", "+0000"));
                    result = new Timestamp(dateAfterFormat.getTime());
                } else {
                    Date dateAfterFormat = formatter.parse(date);
                    result = new Timestamp(dateAfterFormat.getTime());
                }
                return result;
            }
        } catch (Exception e) {
            throw new CustomException("convert date to timestamp fail", CommonUtils.putError("date", "ERR_007"));
        }
    }

    /**
     * compareDateTimeNow : compare date vs date now
     *
     * @param date : date for compare
     * @return Boolean
     */
    public static Boolean compareAfterDateTimeNow(Date date) {
        Date dateNow = new Date(System.currentTimeMillis());
        if (date.after(dateNow)) {
            return true;
        }
        return false;
    }

}
