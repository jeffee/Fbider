package com.fb.task;

import com.fb.DB.DBProcess;
import com.fb.DB.PostDB;
import com.fb.DB.SupPostDB;
import com.fb.common.CommonData;
import com.fb.common.FileProcess;
import com.fb.common.Parse;
import com.fb.crawl.Crawl;
import com.fb.object.TargetDir;
import com.restfb.json.JsonObject;

import java.io.File;
import java.util.List;

/**
 * Created by Jeffee Chen on 2015/4/3.
 */
public class LikeUpdate {


    /**
     * 更新已监控的posts的新的likeCount的值,只获取当前的点赞数
     */
    public static void updateLikes() {
        System.out.println("Please wait，Checking likes …………");
        String sql = "select postID from " + CommonData.SUP_POST_TABLE;
        List<String> list = DBProcess.get(sql, 1);
        for (String pID : list) {
            String likeUrl = String.format("%s/likes?limit=0&summary=1&access_token=%s&", pID, CommonData.MY_ACCESS_TOKEN);
            try {
                JsonObject obj = Crawl.getPage(likeUrl);
                long likeCount = Parse.getTotalCount(obj);
                PostDB.updateLikeCount(pID, likeCount);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        LikeUpdate.updateLikes();
    }
}
