package com.fb.analyser;

import com.fb.DB.DBProcess;

import java.util.Date;
import java.util.List;

/**
 * Created by Jeffee Chen on 2015/5/6.
 */
public class LikeCount {

    //"朱立伦:34,42,24|蔡英文:32,22,34"
    public static String onMonth() {
        String sql = "SELECT A.uid, B.uname FROM sup_user_table A LEFT JOIN user_table B USING(uid)";
        List<String> list = DBProcess.get(sql, 2);
        StringBuilder strb = new StringBuilder();
        for (String info : list) {
            String[] strs = info.split(";");
            String uid = strs[0];
            String uname = strs[1];
            String result = getAvgLike(uid);
            if (result.indexOf("[") != -1)
                result = result.substring(1, result.indexOf("]")).replaceAll(" ", "");

            strb.append(uname + ":" + fill(result) + "|");
        }
        return strb.toString();
    }

    private static String fill(String info) {
        String[] result = new String[new Date().getMonth()+1];
        if (!info.trim().equals("")) {
        String[] strs = info.split(",");

            for (String str : strs) {
                String[] subStrs=str.split(";");
                int month = Integer.parseInt(subStrs[0].trim());
                result[month-1] = subStrs[1];
            }
        }

        StringBuilder strb = new StringBuilder();
        for (int i=0;i<result.length;i++) {
            if (result[i]==null||result[i].equals(""))
                strb.append(",0");
            else
                strb.append(","+result[i]);
        }
        return strb.toString().substring(1);
    }

    private static String getAvgLike(String uid) {
        String sql = "SELECT Month(createTime), ceil(AVG(likeCount)) from post_table WHERE userID='"+uid+"' GROUP BY Month(createTime)";
        List<String> rList = DBProcess.get(sql, 2);
        return rList.toString();
    }
    public static void main(String[] args) {
        String info = LikeCount.onMonth();
        System.out.println(info);
    }
}
