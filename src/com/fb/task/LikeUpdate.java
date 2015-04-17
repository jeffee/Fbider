package com.fb.task;

import com.fb.DB.DBProcess;
import com.fb.common.CommonData;
import com.fb.common.FileProcess;
import com.fb.crawl.Crawl;
import com.fb.object.TargetDir;
import com.restfb.json.JsonObject;

import java.io.File;
import java.util.List;

/**
 * Created by Jeffee Chen on 2015/4/3.
 * 更新所有的comments和likes，需要抓取的内容ID存放在
 */
public class LikeUpdate {


    /**
     * 更新已监控的posts的新的like
     * 完成之后更新like的after值，提供下次更新的入口地址
     */
    public static void updateLikes() {
        System.out.println("Please wait，Checking likes …………");
        String sql = "select postID, likeAfter from " + CommonData.SUP_POST_TABLE;
        List<String> list = DBProcess.get(sql, 2);
        for (String line : list) {
            String[] strs = line.split(";");
            String likeUrl = String.format("%s/likes?limit=1000&summary=1&access_token=%s&", strs[0], CommonData.MY_ACCESS_TOKEN);

            if(strs.length>1)
                likeUrl = String.format("%s/likes?limit=1000&summary=1&after=%s&access_token=%s&", strs[0], strs[1], CommonData.MY_ACCESS_TOKEN);

            List<JsonObject> jsonList = Crawl.getPages(likeUrl);
            if (jsonList.size() < 1)
                continue;

            JsonObject lastJobj = jsonList.get(jsonList.size() - 1);
            String after = lastJobj.getJsonObject("paging").getJsonObject("cursors").getString("after");
            sql = String.format("insert into %s values ('%s','','','2015-01-01','2015-01-01') on duplicate key update LikeAfter='%s'", CommonData.SUP_POST_TABLE, strs[0], after);
            DBProcess.update(sql);

            System.out.println(strs[0] + " updated");
            String uname = CommonData.getNameByID(strs[0].split("_")[0]);
            String rawFeedFile = TargetDir.genFileName(TargetDir.RAW_FEEDS_DIR, uname, strs[0], "likes");
            write(jsonList, rawFeedFile);
        }
    }



    private static void write(List<JsonObject> list, String dir) {
        int count = 1;
        File file = new File(dir);
        if (file.exists() && file.isDirectory())
            count = file.listFiles().length + 1;

        for (JsonObject jObj : list) {
            FileProcess.write(dir + "\\" + count++, jObj.toString());
        }
    }


    public static void main(String[] args) {
        LikeUpdate.updateLikes();
    }
}
