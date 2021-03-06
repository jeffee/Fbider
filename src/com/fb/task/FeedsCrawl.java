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
 * 获取所有的comments和likes
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


    public void getFeedsByID(String pID, String createdTime) {
        String uName = CommonData.getNameByID(pID.split("_")[0]);
        String likeDir = TargetDir.genFileName(TargetDir.RAW_FEEDS_DIR, uName, pID, "likes");
        if (new File(likeDir).exists()) {
            System.out.println("Skip");
            return;
        }

        idList.add(pID);
        System.out.println("post " + pID + " begins:>>>");

        List<JsonObject> commentList = crawlComment(pID);
        long likeCount = crawlLikes(pID);
        long commentCount = 0;
        String commentAfter = "";

        if (commentList.size() >= 1) {
            JsonObject obj = commentList.get(commentList.size() - 1);
            commentCount = Parse.getTotalCount(obj);
            commentAfter = Parse.getAfter(obj);
        }

        SupPostDB.insert(pID, commentAfter, "", createdTime);
        PostDB.updateTotalCount(pID, likeCount, commentCount);

        System.out.println(pID + " post is finished!");
    }

    public static List<JsonObject> crawlComment(String pID) {
        String uID = pID.split("_")[0];
        String uName = CommonData.getNameByID(uID);
        String commentDir = TargetDir.genFileName(TargetDir.RAW_FEEDS_DIR, uName, pID, "comments");
        String commentDBDir =  TargetDir.genFileName(TargetDir.DB_FEEDS_DIR, uID, pID);

        String commentsUrl = pID + "/comments?limit=1000&filter=stream&summary=1&access_token=" + CommonData.MY_ACCESS_TOKEN + "&";
        List<JsonObject> commentList = Crawl.getPages(commentsUrl);
        write(commentList, commentDir);
        writeDB(pID, commentList, commentDBDir);
        return commentList;
    }

    public long crawlLikes(String pID) {
        System.out.println("Likes begins>>>>>>>>");
        String likesUrl = pID + "/likes?limit=0&summary=1&access_token=" + CommonData.MY_ACCESS_TOKEN + "&";
        JsonObject obj = Crawl.getPage(likesUrl);
        try {
            long count = obj.getJsonObject("summary").getLong("total_count");
            return count;
        } catch (Exception e) {
            return 0;
        }
    }

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
        crawl.getFeedsByID("46251501064_10152659782201065", "2015-04-30 02:37:31");
        //crawl.crawlComment("10150145806225128_10155537343990128");
    }
}
