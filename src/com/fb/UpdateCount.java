package com.fb;

import com.fb.DB.PostDB;
import com.fb.common.FileProcess;
import com.fb.common.Parse;
import com.fb.object.TargetDir;
import com.restfb.json.JsonObject;

import java.io.File;

/**
 * Created by Jeffee Chen on 2015/5/6.
 */
public class UpdateCount {

    public static void updatePost(File pDir) {
        String pID = pDir.getName();
        long commentCount = getCount(new File(TargetDir.genFileName(pDir.getPath(), "comments")));
        PostDB.updateCommentCount(pID, commentCount);
        long likeCount = getCount(new File(TargetDir.genFileName(pDir.getPath(), "likes")));
        PostDB.updateLikeCount(pID, likeCount);

    }

    private static long getCount(File dir) {
        File[] files = dir.listFiles();
        if (files == null || files.length < 1) {
            System.out.println(dir.getPath());
            return 0;
        }
        int count = files.length;
        if (count < 1)
            return 0;
        JsonObject jobj = new JsonObject(FileProcess.readLine(TargetDir.genFileName(dir.getPath(), ""+count)));
        return Parse.getTotalCount(jobj);
    }

    public static void main(String[] args) {
        File rootDir = new File("E:\\Data\\facebook\\raw pages\\feeds");
        File[] userDirs = rootDir.listFiles();
        for (File userDir : userDirs) {
            File[] pDirs = userDir.listFiles();
            for (File pDir : pDirs) {
                updatePost(pDir);
            }
        }
    }
}
