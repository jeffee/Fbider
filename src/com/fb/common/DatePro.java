package com.fb.common;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by lenovo on 2015/6/4.
 */
public class DatePro {
    public static Date daysAgo(int count){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, -count);
        return cal.getTime();
    }

    public static void main(String[] argv){
        Date date = DatePro.daysAgo(3);
        System.out.println(date.toString());
    }

}
