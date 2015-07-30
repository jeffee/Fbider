package com.fb.task;

import com.fb.DB.DBProcess;
import com.fb.common.CommonData;
import com.fb.common.DatePro;

import java.util.Date;

/**
 * Created by Jeffee Chen on 2015/7/30.
 * 把一个月之前的pos从监控表中去除
 */
public class Discarder {

    public static void discard() {
        String dateStr = CommonData.dayFormat.format(DatePro.daysAgo(30));
       // System.out.println(dateStr);
        String sql = "delete from "+ CommonData.SUP_POST_TABLE +" WHERE createdTime<'"+ dateStr +"'";
        System.out.println(sql);
        DBProcess.update(sql);
    }
    public static void main(String[] args) {
        Discarder.discard();
    }
}
