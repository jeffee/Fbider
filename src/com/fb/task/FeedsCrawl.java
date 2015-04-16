package com.fb.task;

import com.fb.DB.DBProcess;
import com.fb.common.CommonData;
import com.fb.common.FileProcess;
import com.fb.common.Parse;
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
    private List<String> idList;

    public FeedsCrawl() {
        idList = new ArrayList<>();
    }

    public void get() {
        File[] uDirs = new File(TargetDir.ID_DIR).listFiles();
        for (File uDir : uDirs) {
            File[] pDIrs = uDir.listFiles();
            for (File pDir : pDIrs) {
                getFeeds(pDir);
                System.out.println(pDir.getName() + " finished!");
            }
            if (uDir.listFiles().length < 1)
                uDir.delete();
        }
    }

    /**
     * 处理完ID文件，备份回收*
     */
    private void backFile(File sFile) {
        String fPath = sFile.getPath();
        fPath = fPath.substring(fPath.indexOf("todo") + 5);
        fPath = TargetDir.genFileName(TargetDir.BACK_DIR, fPath);
        File dFile = new File(fPath);
        File dir = new File(dFile.getParent());
        if (!dir.exists() || !dir.isDirectory())
            dir.mkdirs();
        if (dFile.exists() && dFile.isFile()) {
            dFile.delete();
        }
        sFile.renameTo(dFile);
    }


    private List<String> getFeedsByID(String pID, String createdTime) {
        List<String> list = new ArrayList<>();
        String uName = CommonData.getNameByID(pID.split("_")[0]);
        String likeDir = TargetDir.genFileName(TargetDir.RAW_FEEDS_DIR, uName, pID, "likes");
        if (new File(likeDir).exists()) {
            System.out.println("Skip");
            return list;
        }

        idList.add(pID);
        System.out.println("post " + pID + " begins:>>>");

        List<JsonObject> commentList = crawlComment(pID);
        List<JsonObject> likeList = crawlLikes(pID);
        String likeAfter = "";
        if (likeList.size() >= 1)
            likeAfter = Parse.getAfter(likeList.get(likeList.size() - 1));
        String updateTime = createdTime;
        if (commentList.size() >= 1)
            updateTime = Parse.getUpdatedTime(commentList.get(commentList.size() - 1));

        String sql = String.format("insert into %s values ('%s','%s','%s','%s','%s')", CommonData.SUP_POST_TABLE, pID, pID.split("_")[0], likeAfter, createdTime, updateTime);
        DBProcess.update(sql);
        list.add(pID + ";" + updateTime + ";" + likeAfter);
        System.out.println(pID + " post is finished!");
        return list;
    }

    public static List<JsonObject> crawlComment(String pID) {
        String uName = CommonData.getNameByID(pID.split("_")[0]);
        String commentDir = TargetDir.genFileName(TargetDir.RAW_FEEDS_DIR, uName, pID, "comments");

        System.out.println("Comments begins>>>>>");
        String commentsUrl = pID + "/comments?limit=1000&access_token=" + CommonData.MY_ACCESS_TOKEN + "&";
        List<JsonObject> commentList = Crawl.getPages(commentsUrl);
        //String commentAfter = Parse.getAfter(commentList.get(commentList.size()-1));
        write(commentList, commentDir);
        return commentList;
    }

    public List<JsonObject> crawlLikes(String pID) {
        String uName = CommonData.getNameByID(pID.split("_")[0]);
        String likeDir = TargetDir.genFileName(TargetDir.RAW_FEEDS_DIR, uName, pID, "likes");
        System.out.println("Likes begins>>>>>>>>");
        String likesUrl = pID + "/likes?limit=1000&access_token=" + CommonData.MY_ACCESS_TOKEN + "&";
        List<JsonObject> likeList = Crawl.getPages(likesUrl);
        write(likeList, likeDir);
        return likeList;
    }

    private static void write(List<JsonObject> list, String dir) {
        int count = 1;
        for (JsonObject jObj : list) {
            FileProcess.write(dir + "\\" + count++, jObj.toString());
        }

    }

    private void getFeeds(File pDir) {
        if (pDir.isFile()) {
            getFromFile(pDir);
        } else {
            getFromDir(pDir);
        }
    }

    private List<String> getFromFile(File pDir) {
        List<String> list = FileProcess.read(pDir);
        List<String> updateList = new ArrayList<>();
        for (String line : list) {
            if (line == null || line.equals(""))
                continue;
            String[] infos = line.split(";");
            getFeedsByID(infos[0], infos[1]);
        }
        backFile(pDir);
        return updateList;
    }

    private void getFromDir(File pDir) {
        File[] files = pDir.listFiles();
        for (File file : files) {
            getFromFile(file);
        }
        if (pDir.listFiles().length < 1)
            pDir.delete();
    }

    public static void main(String[] args) {
        FeedsCrawl crawl = new FeedsCrawl();
        crawl.get();
    }
}
