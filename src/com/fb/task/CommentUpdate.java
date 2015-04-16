package com.fb.task;

import com.fb.DB.DBProcess;
import com.fb.common.Common;
import com.fb.common.CommonData;
import com.fb.common.Parse;
import com.fb.crawl.Crawl;
import com.restfb.json.JsonArray;
import com.restfb.json.JsonObject;

import java.util.*;

/**
 * Created by Jeffee Chen on 2015/4/10.
 * 更新已监控posts的新的comments
 * 由于comments无法使用since等参数，返回的after在再次执行时也有问题（返回的after大都为Mw==或MQ==，执行时会出错），因此采用替代的方法，即每次都重新抓取，然后用新的数据替换旧数据
 * **
 */

public class CommentUpdate {


    /**
     * 初始化数据库中抓取的post的评论和点赞的最终时间
     * ***
     */
    private static Map<String, String> initDBMap() {
        Map<String, String> commentMap = new HashMap<>();
        String sql = "select postID, updatedTime from " + CommonData.SUP_POST_TABLE;
        List<String> list = DBProcess.get(sql, 2);
        for (String line : list) {
            String[] infos = line.split(";");
            String updateTime = infos[1];
            if (updateTime.indexOf(".") != -1)
                updateTime = updateTime.substring(0, updateTime.indexOf("."));
            commentMap.put(infos[0], updateTime);
        }
        return commentMap;
    }

    /**
     * 获取Facebook中post的更新时间
     *
     * @todo 每次搜索的时候，提取POST_UPDATE_PERIOD时间内的post的更新时间
     * **
     */
    private static Map<String, String> initFBMap() {
        Map<String, String> fbMap = new HashMap<>();
        String since = Common.getLastDate(CommonData.POST_UPDATE_PERIOD);
        if (since == null || since.trim().equals(""))
            return fbMap;

        String sql = "select userID FROM " + CommonData.SUP_POST_TABLE + " GROUP BY userID";  //获取表中所有的用户ID
        List<String> list = DBProcess.get(sql, 1);
        for (String uid : list) {
            String url = uid + "/posts?fields=id,updated_time&limit=100&since=" + since;
            JsonArray array = Crawl.get(url).getJsonArray("data");
            if (array.length() == 0)
                return fbMap;

            for (int i = 0; i < array.length(); i++) {
                JsonObject obj = array.getJsonObject(i);
                String id = obj.getString("id");
                String updatedTime = obj.getString("updated_time");
                updatedTime = Common.parseTime(updatedTime);
                fbMap.put(id, updatedTime);
            }
        }
        return fbMap;
    }


    /**
     * 更新用户发布的post**
     */
    public static void updateComments() {
        Map<String, String> fbMap = initFBMap();
        Map<String, String> dbMap = initDBMap();
        Iterator<String> iter = dbMap.keySet().iterator();
        while (iter.hasNext()) {
            String pID = iter.next();
            String dbTime = dbMap.get(pID);
            String fbTime = fbMap.get(pID);

            if (!dbTime.equals(fbTime)) {       //不一致说明有更新
                List<JsonObject> list = FeedsCrawl.crawlComment(pID);
                String updatedTime = Parse.getUpdatedTime(list.get(list.size() - 1));
                updateTime(pID, updatedTime);
            } else System.out.println("no update ************");
        }
    }


    private static void updateTime(String pID, String updateTime) {
        String sql = "update " + CommonData.SUP_POST_TABLE + " set updatedTime='" + updateTime + "' where postID='" + pID + "'";
        DBProcess.update(sql);
    }

    public static void main(String[] args) {
        CommentUpdate.updateComments();
    }
}
