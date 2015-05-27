package com.fb.task;

import com.fb.DB.DBProcess;
import com.fb.DB.PostDB;
import com.fb.DB.SupPostDB;
import com.fb.common.CommonData;
import com.fb.common.FileProcess;
import com.fb.common.Parse;
import com.fb.crawl.Crawl;
import com.fb.object.Comment;
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
public class CommentUpdate {


    /**
     * 更新已监控的posts的新的like
     * 完成之后更新like的after值，提供下次更新的入口地址
     */
    public static void updateComments() {
        System.out.println("Please wait，Checking comments …………");
        String sql = "select postID, commentAfter from " + CommonData.SUP_POST_TABLE;
        List<String> list = DBProcess.get(sql, 2);
        // System.out.println(list.toString());
        for (String line : list) {
            String[] strs = line.split(";");
            String likeUrl = String.format("%s/comments?limit=1000&filter=stream&summary=1&access_token=%s&", strs[0], CommonData.MY_ACCESS_TOKEN);

            if (strs.length > 1) {
                String after = (strs[1].length() < 5) ? "" : strs[1];
                likeUrl = String.format("%s/comments?limit=1000&filter=stream&summary=1&after=%s&access_token=%s&", strs[0], after, CommonData.MY_ACCESS_TOKEN);
            }
            List<JsonObject> jsonList = Crawl.getPages(likeUrl);
            if (jsonList.size() < 1) {
                continue;
            }

            JsonObject lastJobj = jsonList.get(jsonList.size() - 1);
            String after = Parse.getAfter(lastJobj);
            SupPostDB.updateCommentAfter(strs[0], after);

            long count = lastJobj.getJsonObject("summary").getLong("total_count");
            PostDB.updateCommentCount(strs[0], count);

            System.out.println(strs[0] + " updated");
            String uname = CommonData.getNameByID(strs[0].split("_")[0]);
            String rawFeedFile = TargetDir.genFileName(TargetDir.RAW_FEEDS_DIR, uname, strs[0], "comments");
            write(jsonList, rawFeedFile);

            String commentDBDir =  TargetDir.genFileName(TargetDir.DB_FEEDS_DIR, strs[0].split("_")[0], strs[0]);
            writeDB(strs[0], jsonList, commentDBDir);
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

    /***写评论文件，然后导入数据库**/
    private static void writeDB(String pID, List<JsonObject> list, String dir) {
        List<String> infoList = new ArrayList<>();
        for (JsonObject jObj : list) {
            JsonArray array = jObj.getJsonArray("data");
            for (int i = 0; i < array.length(); i++) {
                JsonObject obj = (JsonObject)array.get(i);
                Comment comment = new Comment(pID, obj);
                infoList.add(comment.toString());
            }
        }
        FileProcess.write(dir, infoList);
        DBProcess.inport(dir, CommonData.COMMETN_TABLE);
        System.out.println(infoList.size()+" posts have been stored");
    }

    public static void main(String[] args) {
        CommentUpdate.updateComments();
    }
}
