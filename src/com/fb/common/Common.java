package com.fb.common;
/**
 * @author Jeffee C.
 * @date: 下午3:17:53,2015
 */


import com.restfb.json.JsonObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Common {

    public static String genFileName(String dir, int i) {
        return (dir + ((dir.endsWith("//")) ? "" : "//") + i + ".fb");
    }


    public static String genFileName(String arg1, String... args) {
        String str = arg1;
        for (String arg : args)
            str += "\\" + arg;
        return str;
    }

    /**
     * 从Json格式的文件中获取时间，一般格式为："created_time": "2014-11-23T22:49:51+0000",
     * *
     */
    public static String getTime(JsonObject obj, String key) {
        String time = "";
        try {
            time = obj.getString(key);
        } catch (Exception e) {
            // the value is not exist
            return "";
        }
        time = time.substring(0, time.indexOf("+")).replace("T", " ");
        return time;
    }

    public static String getLastDate(int days) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -days);
        return format.format(cal.getTime());
    }

    /****facebook时间格式转换
     * @param dateTime: facebook返回的时间，格式为“2015-04-14T06:27:16+0000”
     * ***/
    public static String parseTime(String dateTime) {
        String timeStr = dateTime.substring(0, dateTime.indexOf("+")).replace('T', ' ');
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return timeStr;
    }

    public static void main(String[] args) {
        /*String str = "46251501064_10152572626026065/comments?access_token=CAACEdEose0cBAPZBE8ZBZBYDJRyOlglzjlZBNNqI0ZASzKsBNLIYoXGfRZAZBxzE9lUmR8OzQjZAU6WMWwFv9nwAZA9H7kf1JOLAX2B1xtyZAOggOJ55N2xP4KxMZA276FuZC6ZCCrT2ZBQLCuRpWpZBzVr6q1KSwygOUr2eaDWi5erp7ssASjhsOkUs2JR8jyDXw9k5kQGOS9oAh7me6aBRBo5U4ci&limit=25&after=MzQ%3D";
		String url = Common.updateAccessToken(str);
		url = Common.updateLimit(url);
		System.out.println(url);*/
        //String url = "oauth/access_token?client_id=1411599875814005&client_secret=39ac7e3a058c4b367acd4469e20dda7e&grant_type=fb_exchange_token&fb_exchange_token=CAAUD17UpsnUBAJtF9Ya1vWfy69ah4HJEs6U6xve1yBKbeFZBdgKvzUZBIkzKfWZAgZB90YKKj8ZB3SggeG9RCRkQelU13MhWCqy8BOSKSWNeyK1OdVa22J4nFa4MipoNeXvsMtCxTuqccF0tYUrgJ5wIjtJO2s5CBavDynXgkZBg6MYNE4YgHIWyOlVtTSwkAhAmP7CZBYSUfVgjvTmmeLe&";
        System.out.println(Common.getLastDate(30));
    }

}
