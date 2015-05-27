package com.fb.test;

import com.fb.DB.DBProcess;
import com.fb.common.FileProcess;
import com.fb.object.TargetDir;
import com.restfb.json.JsonArray;
import com.restfb.json.JsonObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeffee Chen on 2015/4/21.
 */
public class LikeCount {

    public static void main(String[] args) {
        LikeCount.get(new File("E:\\Data\\facebook\\raw pages\\feeds\\洪秀柱"));
    }

    public static void get(File uDir) {
        List<String> list = new ArrayList<>();
        File[] pDirs = uDir.listFiles();
        for (File pDir : pDirs) {
            File likeDir = new File(pDir.getPath() + "\\likes");
            File[] files = likeDir.listFiles();
            File sFile = new File(likeDir.getPath() + "\\" + files.length);
            JsonObject obj = new JsonObject(FileProcess.readLine(sFile));
            try {
                long count = obj.getJsonObject("summary").getLong("total_count");
                list.add(pDir.getName() + "," + count);
            } catch (Exception e) {
                JsonArray array = obj.getJsonArray("data");
                long count = 1000*(files.length-1)+array.length();
                list.add(pDir.getName() + "," + count);
                System.out.println("Updated");
            }
        }
        String dFile = TargetDir.genFileName("E:\\ex", uDir.getName(), "like");
        FileProcess.write(dFile, list);
    }

    public void onMonth(String uid) {
        String[] times={"2015-01-01",""};
        String sql = "select a.postID, b.num from post_table a INNER JOIN likeTable b on a.postID=b.postID \n" +
                "where userID='"+uid+"' and a.createTime>";
        List<String> list = DBProcess.get(sql, 2);
    }
}
