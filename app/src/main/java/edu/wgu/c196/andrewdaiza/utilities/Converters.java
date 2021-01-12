package edu.wgu.c196.andrewdaiza.utilities;

import androidx.room.TypeConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class Converters {


    public static final String DATE_PATTERN = "MMM d, yyyy";


    public static boolean checkDatesEqual(final Date date1, final Date date2) {
        boolean equal = date1 == null && date2 == null;
        if (!equal) {
            equal = date1.getTime() == date2.getTime();
        }
        return equal;
    }

    public static String getDateFormatting(int month, int dayOfMonth, int year) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, dayOfMonth);
        return getDateFormatting(c.getTime());
    }

    public static String getDateFormatting(Date date) {
        return getDateFormatting(DATE_PATTERN, date);
    }

    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long convertTimeStamp(Date date) {
        return date == null ? null : date.getTime();
    }

    public static String getDateFormatting(String pattern, Date date) {
        DateFormat formatter = new SimpleDateFormat(pattern, Locale.getDefault());
        return formatter.format(date);
    }

    public static Date getDateWithFormat(String format, String dateText) {
        Date date = null;
        try {
            date = new SimpleDateFormat(format, Locale.getDefault()).parse(dateText);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    public static Date getDateWithFormat(String dateText) {
        return getDateWithFormat(DATE_PATTERN, dateText);
    }

}
