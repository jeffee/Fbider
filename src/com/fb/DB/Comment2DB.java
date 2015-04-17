package com.fb.DB;

import com.fb.common.FileProcess;
import com.fb.object.Comment;
import com.fb.object.TargetDir;
import com.restfb.json.JsonArray;
import com.restfb.json.JsonObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeffee Chen on 2015/4/16.
 */
public class Comment2DB {

    public static void store() {

        File rootDir = new File(TargetDir.RAW_FEEDS_DIR);
        File[] uDirArray = rootDir.listFiles();
        for (File uDir : uDirArray) {
            File[] pDirArray = uDir.listFiles();
            for (File pDir : pDirArray) {
                String pID = pDir.getName();
                List<String> list = new ArrayList<>();
                File likeDir = new File(pDir.getPath() + "\\comments");
                File[] likeFileArray = likeDir.listFiles();
                for (File likeFile : likeFileArray) {
                    JsonObject jobj = new JsonObject(FileProcess.readLine(likeFile));
                    JsonArray jarray = jobj.getJsonArray("data");
                    for (int i = 0; i < jarray.length(); i++) {
                        JsonObject obj = jarray.getJsonObject(i);
                        Comment comment = new Comment(pID, obj);
                        list.add(comment.toString());
                    }
                }

               // File dFile = new File(TargetDir.DB_FEEDS_DIR+"\\");
            }
        }
    }

    public static void main(String[] args) {

    }
}
