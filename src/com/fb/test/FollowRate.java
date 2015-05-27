package com.fb.test;

import com.fb.DB.DBProcess;
import com.fb.common.CommonData;
import com.fb.common.FileProcess;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Jeffee Chen on 2015/4/20.
 */
public class FollowRate {

    public static void main(String[] args) {
        FollowRate.OnDate("796255990463702");
    }

    public static void OnDate(String uid) {
        List<String> rList = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        Date date = null;
        try {
            date = format.parse("2015-01-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.setTime(date);
        String beforeTime = format.format(cal.getTime());
        cal.add(Calendar.DATE, 1);
        String afterTime = format.format(cal.getTime());
        while (!afterTime.equals("2015-04-21")) {
            rList.addAll(CommentRate(uid, beforeTime, afterTime));
            beforeTime = afterTime;
            cal.add(Calendar.DATE, 1);
            afterTime = format.format(cal.getTime());
        }
        FileProcess.write("E://ex//"+uid+"-comment.csv", rList);
    }

    public static List<String> CommentRate(String uid, String beforeTime, String afterTime) {
        String sql = "select postID, count(*) from "+CommonData.COMMETN_TABLE+" where postID in (SELECT postID from "+CommonData.POST_TABLE+" WHERE userID='"+uid+"' and createTime>'"+beforeTime+"' and createTime<'"+afterTime+"')\n" +
                "group by postID";
        return DBProcess.get(sql, 2);
    }

    public static List<String> shareRate(String uid, String beforeTime, String afterTime) {
        List<String> rList = new ArrayList<>();
        String sql = "SELECT postID, shareCount from " + CommonData.POST_TABLE + " WHERE userID='"+uid+"' and createTime>'" + beforeTime + "' and createTime<'" + afterTime + "'";
        List<String> list = DBProcess.get(sql, 2);
        if (list.size() > 0) {
            System.out.println(beforeTime + list.toString());
            rList.add(beforeTime + ";" + list.get(0));
            if (list.size() > 1) {
                for (int i = 1; i < list.size(); i++) {
                    rList.add(beforeTime + "-" + (i + 1) + ";" + list.get(i));
                }
            }
        }
        return rList;
    }
}
