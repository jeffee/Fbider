package com.fb.task;

import com.fb.DB.DBProcess;
import com.fb.DB.PostDB;
import com.fb.common.CommonData;
import com.fb.common.FileProcess;
import com.fb.common.Parse;
import com.fb.crawl.Crawl;
import com.fb.object.TargetDir;
import com.restfb.json.JsonObject;

import java.util.List;

/**
 * Created by Jeffee Chen on 2015/4/3.
 */
public class AllLikeUpdate {

    private static String root = "E:\\Data\\facebook\\likes";
    /**
     * 更新已监控的posts的新的likeCount的值,只获取当前的点赞数
     */
    public static void updateLikes() {
        System.out.println("Please wait，Checking likes …………");
        String sql = "select postID from "+ CommonData.POST_TABLE +" where createTime>'2015-05-14' AND userID IN (SELECT userID from mon_user_table)";
        List<String> list = DBProcess.get(sql, 1);
        for (String pID : list) {
            String likeUrl = String.format("%s/likes?limit=1000&summary=1&access_token=%s&", pID, CommonData.MY_ACCESS_TOKEN);
            try {
                List<JsonObject> jsonList = Crawl.getPages(likeUrl);
                String fileName = TargetDir.genFileName(root, CommonData.getNameByID(pID.split("_")[0]), pID);

                for (int i=0;i<jsonList.size();i++) {
                    JsonObject obj = jsonList.get(i);
                    FileProcess.write(TargetDir.genFileName(fileName, ""+i), obj.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        AllLikeUpdate.updateLikes();
    }
}
