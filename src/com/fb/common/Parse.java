package com.fb.common;

import com.restfb.json.JsonArray;
import com.restfb.json.JsonObject;

import java.util.List;

/**
 * Created by Jeffee Chen on 2015/4/16.
 * Json文本解析的常用函数
 */
public class Parse {

    public static String getAfter(JsonObject jobj) {
        return jobj.getJsonObject("paging").getJsonObject("cursors").getString("after");
    }

    public static String getUpdatedTime(JsonObject jobj) {
        JsonArray jarray = jobj.getJsonArray("data");
        JsonObject subObj = jarray.getJsonObject(jarray.length() - 1);
        String time = subObj.getString("created_time");
        return Common.parseTime(time);
    }

    public static long getTotalCount(JsonObject jobj) {
        try {
            return jobj.getJsonObject("summary").getLong("total_count");
        } catch (Exception e) {
            return 0;
        }
    }

    public static void main(String[] args) {

    }
}
