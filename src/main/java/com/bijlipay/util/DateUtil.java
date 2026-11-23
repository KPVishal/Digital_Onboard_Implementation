package com.bijlipay.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtil {

    // convert long to date format
    public static Date longToDate(long longDate){
        Date date = new Date(TimeUnit.SECONDS.toMillis(longDate));

        return date;
    }

    // get current date
    public static Date currentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return date;
    }

    // get next date
    public static Date nextDate (Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        return calendar.getTime();
    }
    public static String formattedTime(Date memberSince, String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(date);
        Date date1 = new Date();
        String formattedDate = dateFormat.format(memberSince);
        return formattedDate;
    }

    public static String parseSimpleDate(Date date) throws ParseException {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormatter.format(date);
    }
    public static String currentDateFromString(String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date date = new Date();
        String currentDate = formatter.format(date);
        return currentDate;
    }
}
