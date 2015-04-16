package com.fb.task;

import com.fb.DB.DBProcess;
import com.fb.common.Common;
import com.fb.common.CommonData;
import com.fb.common.FileProcess;
import com.fb.crawl.Crawl;
import com.fb.object.TargetDir;
import com.restfb.json.JsonArray;
import com.restfb.json.JsonObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeffee Chen on 2015/4/3.
 * 更新所有的comments和likes，需要抓取的内容ID存放在
 */
public class FeedsUpdate {

    /**
     * 监控中的ID列表*
     */
    private List<String> supIDList;

    public FeedsUpdate() {
        supIDList = new ArrayList<>();
    }


    private String geneLikeURL(String pid, String after) {
        String url = pid + "/likes?limit=1000&filter=stream&summary=1&after=" + after + "&access_token=" + CommonData.MY_ACCESS_TOKEN + "&";
        return url;
    }


    /**
     * 更新已监控的posts的新的like
     * 完成之后更新like的after值，提供下次更新的入口地址
     */
    public void updateLikes() {
        String sql = "select postID, userID, commentAfter, likeAfter from " + CommonData.SUP_POST_TABLE;
        List<String> list = DBProcess.get(sql, 4);
        for (String line : list) {
            String[] strs = line.split(";");

            String likeUrl = geneLikeURL(strs[0], strs[3]);
            List<JsonObject> jsonList = Crawl.getPages(likeUrl);
            if (jsonList.size() < 1)
                continue;

            JsonObject lastJobj = jsonList.get(jsonList.size() - 1);
            String after = lastJobj.getJsonObject("paging").getJsonObject("cursors").getString("after");
            sql = String.format("insert into %s values ('%s','','','','2015-01-01','2015-01-01') on duplicate key update LikeAfter='%s'", CommonData.SUP_POST_TABLE, strs[0], after);
            DBProcess.update(sql);

            String rawFeedFile = TargetDir.genFileName(TargetDir.RAW_FEEDS_DIR, strs[1], strs[0], "comments");
            write(jsonList, rawFeedFile);

        }
    }

    /**
     * 更新已监控posts的新的comments
     * 由于comments无法使用since等参数，返回的after在再次执行时也有问题（返回的after大都为Mw==或MQ==，执行时会出错），因此采用替代的方法，即每次都重新抓取，然后用新的数据替换旧数据
     * **
     */
    public void updateComments() {

    }

    private String getUpdatedTime(List<JsonObject> list) {
        String time = "";
        JsonArray jarray = list.get(list.size() - 1).getJsonArray("data");
        JsonObject jobj = jarray.getJsonObject(jarray.length() - 1);
        time = jobj.getString("created_time");
        return Common.parseTime(time);
    }

    private void write(List<JsonObject> list, String dir) {
        int count = 1;
        File file = new File(dir);
        if (file.exists() && file.isDirectory())
            count = file.listFiles().length + 1;

        for (JsonObject jObj : list) {
            System.out.println("write :" + dir);
            FileProcess.write(dir + "\\" + count++, jObj.toString());
        }
    }


    public static void main(String[] args) {
        FeedsUpdate crawl = new FeedsUpdate();
        crawl.updateLikes();
    }
}
