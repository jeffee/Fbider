package com.fb.task;
import com.fb.DB.DBProcess;
import com.fb.common.Common;
import com.fb.common.CommonData;
import com.fb.crawl.Crawl;
import com.fb.object.FBUser;

/**
 * Created by Jeffee Chen on 2015/4/13.
 */
public class SupUser {

    /****添加需要监控的用户
     * @param uid 用户的id
     * ***/
    public static void addSupUser(String uid) {
        String sql = "select * from " + CommonData.USER_TABLE +" where uid='"+uid+"'";
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


    private static void insertUser(FBUser user) {
        String sql = "insert into " + CommonData.USER_TABLE + " (uid, uname, about, category, likes, talking_count) values('";
        sql += user.getId() + "','" + user.getName() + "','" + user.getAbout() + "','" + user.getCategory() + "'," + user.getLikes() + "," + user.getTalking_about_count() + ")";
        DBProcess.update(sql);
    }

    private static void insertSupUser(String uid) {
        String since = Common.getLastDate(CommonData.POST_UPDATE_PERIOD);
        String sql = "insert into " + CommonData.SUP_USER_TABLE + " (uid, since) values('" + uid + "','" +since+"')";
        DBProcess.update(sql);
    }

    public static void main(String[] args) {
        SupUser.addSupUser("100009412503592");
        //SupUser.addSupUser("10150145806225128");
    }
}
