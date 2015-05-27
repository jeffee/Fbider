package com.fb.task;

import com.fb.common.FileProcess;
import com.restfb.json.JsonObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeffee Chen on 2015/5/21.
 */
public class LikeCountUpdate {

    public static void main(String[] args) {
        LikeCountUpdate.update();
    }

    public static void update() {
        File rootDir = new File("E:\\Data\\facebook\\personal");
        File[] uDirs = rootDir.listFiles();
        List<String> list = new ArrayList<String>();
        for(File uDir:uDirs){
            File[] lFiles = uDir.listFiles();
            for (File lFile : lFiles) {
               // System.out.println(lFile.getPath());
                String info = FileProcess.readLine(lFile);
                long count = getLikeCount(info);
                String uName = uDir.getName();
                String[] timeStrs = lFile.getName().split(" ");
                String result = uName+";"+count+";"+timeStrs[0];
                list.add(result);
            }
        }
        FileProcess.write("E:\\like.txt", list);
    }

    private static long getLikeCount(String info){
        long count = 0;
        JsonObject jObj = new JsonObject(info);
        count = jObj.getLong("likes");
        return count;
    }
}
