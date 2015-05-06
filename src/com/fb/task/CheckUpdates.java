package com.fb.task;

import com.fb.DB.DBProcess;
import com.fb.common.CommonData;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Jeffee Chen on 2015/4/7.
 * 用于检测更新
 */
public class CheckUpdates {


    private static Map<String, String> getUpdateTimes(){
        String sql = "select uid, since from " + CommonData.SUP_USER_TABLE;
        return DBProcess.getMap(sql);
    }

    /**
     * 更新post**
     */
    public static void update() {
        Map<String, String> timeMap = getUpdateTimes();
        Iterator<String> iter = timeMap.keySet().iterator();
        while (iter.hasNext()) {
            String uid = iter.next();
            String since = timeMap.get(uid);
            SingleCrawl single = new SingleCrawl(uid);
            int count = single.get(since);

            String newSince = single.getSince();       //更新时间标记
            if (newSince != null) {
                timeMap.put(uid, newSince);
                updateTime(uid, newSince);
            }

            if (count == 0) {
                System.out.println(uid + " didn't update");
            } else {
                System.out.println(uid + " updated " + count + " posts");
            }
        }

    }

    private static void updateTime(String uid, String since) {
        String sql = "update " + CommonData.SUP_USER_TABLE + " set since='" + since + "' where uid='" + uid + "'";
        DBProcess.update(sql);
    }

    public static void main(String[] args) {
        CheckUpdates.update();
    }
}
