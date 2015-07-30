package com.fb.task;
import com.fb.DB.DBProcess;
import com.fb.common.Common;
import com.fb.common.CommonData;
import com.fb.common.FileProcess;
import com.fb.crawl.Crawl;
import com.fb.object.FBUser;
import com.restfb.json.JsonObject;

import java.util.List;

/**
 * Created by Jeffee Chen on 2015/4/13.
 */
public class SupUser {

    /****添加需要监控的用户
     * @param uid 用户的id
     * ***/
    public static void addSupUser(String uid) {
        String sql = "select * from " + CommonData.USER_TABLE +" where userid='"+uid+"'";
        if (DBProcess.get(sql, 1).size() < 1) {       //不存在
            FBUser user = new FBUser(Crawl.get(uid + "?"));
            insertUser(user);
        }
        sql = "select * from " + CommonData.SUP_USER_TABLE +" where uid='"+uid+"'";
        if (DBProcess.get(sql, 1).size() < 1) {       //不存在
            insertSupUser(uid);
        }

        System.out.println(CommonData.getNameByID(uid)+" has been added!");
    }

    /***更新用户信息，主要是关注数量和讨论数量**/
    public static void update(JsonObject obj) {
        String uid = obj.getString("id");
        long likeCount = obj.getLong("likes");
        long talking_about_count = obj.getLong("talking_about_count");
        String sql = String.format("insert into %s values ('%s', '','','',%d,%d) on duplicate key update likes=%d, talking_count=%d", CommonData.USER_TABLE, uid, likeCount, talking_about_count, likeCount, talking_about_count);
        DBProcess.update(sql);
    }

    private static void insertUser(FBUser user) {
        String sql = "insert into " + CommonData.USER_TABLE + " (userid, uname, about, category, likes, talking_count) values('";
        sql += user.getId() + "','" + user.getName() + "','" + user.getAbout() + "','" + user.getCategory() + "'," + user.getLikes() + "," + user.getTalking_about_count() + ")";
        DBProcess.update(sql);
    }

    private static void insertSupUser(String uid) {
        String since = Common.getLastDate(CommonData.POST_UPDATE_PERIOD);
        String sql = "insert into " + CommonData.SUP_USER_TABLE + " (uid, since) values('" + uid + "','" +since+"')";
        DBProcess.update(sql);
    }

    private static void insertCoreUser(String uid) {
        String since = Common.getLastDate(CommonData.POST_UPDATE_PERIOD);
        String sql = "insert into " + CommonData.CORE_USER_TABLE + " (uid, since) values('" + uid + "','" +since+"')";
        DBProcess.update(sql);
    }

    public static void main(String[] args) {
        SupUser.addSupUser("781585891901624");
        SupUser.insertCoreUser("781585891901624");
//        List<String> list = FileProcess.read("E:\\Data\\facebook\\newUser.txt");
//        for (String info : list) {
//
//            String uid = info.split(";")[1];
//            System.out.println(uid);
//            SupUser.addSupUser(uid);
//        }
    }
}
