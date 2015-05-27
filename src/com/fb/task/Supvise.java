package com.fb.task;


import com.fb.DB.DBProcess;
import com.fb.common.CommonData;
import com.fb.common.MyPlayer;


/**
 * Created by Jeffee Chen on 2015/4/29.
 */
public class Supvise {

    public static void sup() {
        String uid = "10150145806225128";
        String since = "1430384760";
        String sql = "select since from "+CommonData.CORE_USER_TABLE+" WHERE uid=\"10150145806225128\"";
        since = DBProcess.getString(sql);
      //  String uid = "754944064621165";
       // String since = "2015-04-21";
        SingleCrawl single = new SingleCrawl(uid);
        int count = single.get(since);

        String newSince = single.getSince();
        if (newSince != null) {
            try {
                updateTime(uid, newSince);      //更新时间标记；添加try catch防止有数据库读取意外影响告警程序
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.err.println("朱立伦已于更新Facebook ");
            while (true) {
                MyPlayer player = new MyPlayer();
                player.play();
            }
        }

        if (count == 0) {
            System.out.println("朱立伦尚未更新Facebook");
        } else {
            System.out.println(uid + " updated " + count + " posts");
        }
    }
    private static void updateTime(String uid, String since) {
        String sql = "update " + CommonData.CORE_USER_TABLE + " set since='" + since + "' where uid='" + uid + "'";
        DBProcess.update(sql);
    }

    public static void main(String[] args) {

    }
}
