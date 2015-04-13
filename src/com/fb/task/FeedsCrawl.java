package com.fb.task;

import com.fb.common.CommonData;
import com.fb.common.FileProcess;
import com.fb.crawl.Crawl;
import com.fb.object.TargetDir;
import com.restfb.json.JsonObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeffee Chen on 2015/4/3.
 * 获取所有的comments和likes，需要抓取的内容ID存放在
 */
public class FeedsCrawl {
    private String rawFeedFile;
    private List<String> idList;
    public FeedsCrawl() {
        idList = new ArrayList<>();
    }

    public void get() {
        File[] uDirs = new File(TargetDir.ID_DIR).listFiles();
        for (File uDir : uDirs) {
            System.out.println(uDir);
            File[] pDIrs = uDir.listFiles();
            for (File pDir : pDIrs) {
                String subName = uDir.getName()+"\\"+pDir.getName();
                rawFeedFile = TargetDir.genFileName(TargetDir.RAW_FEEDS_DIR, subName);
                if (pDir.isFile()) {
                    getFromFile(pDir);

                } else {
                    getFromDir(pDir);
                }
                System.out.println(pDir.getName()+" finished!");

            }
            if(uDir.listFiles().length<1)
                uDir.delete();
        }
    }

    private void backFile(File sFile) {
        String fPath = sFile.getPath();
        fPath = fPath.substring(fPath.indexOf("todo") + 5);
        fPath = TargetDir.genFileName(TargetDir.BACK_DIR, fPath);
        File dFile = new File(fPath);
        File dir = new File(dFile.getParent());
        if(!dir.exists() || !dir.isDirectory())
            dir.mkdirs();
        sFile.renameTo(dFile);
    }


    private void getFeedsByID(String id) {
        String commentDir = TargetDir.genFileName(rawFeedFile, id, "comments");
        String likeDir = TargetDir.genFileName(rawFeedFile, id, "likes");
        if(new File(likeDir).exists()) {
            System.out.println("Skip");
            return;
        }

        System.out.println("post "+id+" begins:>>>");
        String commentsUrl = id+"/comments?limit=1000&access_token="+CommonData.MY_ACCESS_TOKEN+"&";
        String likesUrl = id +"/likes?limit=1000&access_token="+CommonData.MY_ACCESS_TOKEN+"&";
        System.out.println("Comments begins>>>>>");
        List<JsonObject> commentList = Crawl.getPages(commentsUrl);
        System.out.println("Likes    begins>>>>>>>>");
        List<JsonObject> likeList = Crawl.getPages(likesUrl);

        write(commentList, commentDir);
        write(likeList, likeDir);
        System.out.println(id+" post is finished!");
    }

    private void write(List<JsonObject> list, String dir) {
        int count = 1;
        for (JsonObject jObj : list) {
            FileProcess.write(dir + "\\" + count++, jObj.toString());
        }

    }

    private void getFromFile(File pDir) {
        List<String> list = FileProcess.read(pDir);
        list.forEach(this::getFeedsByID);
        backFile(pDir);
    }

    private void getFromDir(File pDir) {
        File[] files=pDir.listFiles();
        for (File file : files) {
            getFromFile(file);
        }
        if(pDir.listFiles().length<1)
            pDir.delete();
    }

    public static void main(String[] args) {
        FeedsCrawl crawl = new FeedsCrawl();
        crawl.get();

    }
}
