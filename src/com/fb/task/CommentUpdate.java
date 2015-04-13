package com.fb.task;

import com.fb.DB.DBProcess;
import com.fb.common.Common;
import com.fb.common.CommonData;
import com.fb.crawl.Crawl;
import com.restfb.json.JsonArray;
import com.restfb.json.JsonObject;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Jeffee Chen on 2015/4/10.
 */
public class CommentUpdate {

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");


    /**
     * 数据库中各post的最后更新时间**
     */
    private Map<String, String> commentMap = new HashMap<>();

    private Map<String, String> fbMap = new HashMap<>();

    /**
     * 初始化数据库中抓取的post的评论和点赞的最终时间
     * ***
     */
    private void initDBMap() {
        String sql = "select postID, commentSince from " + CommonData.SUP_POST_TABLE;
        List<String> list = DBProcess.get(sql, 2);
        for (String line : list) {
            String[] infos = line.split(",");
            commentMap.put(infos[0], infos[1]);
        }
    }

    /**
     * 向前搜100条
     * 获取Facebook中post的更新时间
     *
     * @todo 每次搜索更新的时候，查看最新的100条内容的更新时间
     * **
     */
    private Map<String, String> initFBMap() {
        String since = Common.getLastDate(30);
        if (since == null || since.trim().equals(""))
            return null;

        String sql = "select userID FROM " + CommonData.SUP_POST_TABLE + " GROUP BY userID";  //获取表中所有的用户ID
        List<String> list = DBProcess.get(sql, 1);
        for (String uid : list) {
            String url = uid + "/posts?fields=id,updated_time&limit=100&since=" + since;
            JsonArray array = Crawl.get(url).getJsonArray("data");
            if (array.length() == 0)
                return null;

            for (int i = 0; i < array.length(); i++) {
                JsonObject obj = array.getJsonObject(i);
                String id = obj.getString("id");
                String updatedTime = obj.getString("created_time");
                fbMap.put(id, updatedTime);
            }
        }
        return fbMap;
    }


    /**
     * 更新用户发布的post**
     */
    public void update() {
        initDBMap();
        initFBMap();
        Iterator<String> iter = commentMap.keySet().iterator();
        while (iter.hasNext()) {
            String pID = iter.next();
            String dbTime = commentMap.get(pID);
            String fbTime = fbMap.get(pID);
            if (!dbTime.equals(fbTime)) {       //不一致说明有更新

            }
        }
    }

    public void up() {
        Iterator<String> iter = commentMap.keySet().iterator();
        while (iter.hasNext()) {
            String uid = iter.next();
            String since = commentMap.get(uid);
            String until = format.format(new Date());
            SingleCrawl single = new SingleCrawl(uid);
            int count = single.get(since, until);

            String newSince = single.getSince();       //更新时间标记
            commentMap.put(uid, newSince);
            updateTime(uid, newSince);
            if (count == 0) {
                System.out.println(uid + " didn't update");
            } else {
                System.out.println(uid + " updated " + count + " posts");
            }
        }

        FeedsCrawl crawl = new FeedsCrawl();
        crawl.get();
    }



    private void updateTime(String uid, String since) {
        String sql = "update " + CommonData.SUP_USER_TABLE + " set since='" + since + "' where userid='" + uid + "'";
        DBProcess.update(sql);
    }

    public static void main(String[] args) {

    }
}
