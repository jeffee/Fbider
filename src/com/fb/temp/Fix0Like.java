package com.fb.temp;

import ch.qos.logback.core.util.FileUtil;
import com.fb.DB.DBProcess;
import com.fb.common.CommonData;
import com.fb.task.FeedsCrawl;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeffee Chen on 2015/5/6.
 * 由于抓取的时候没有加上summary参数，导致最终抓到的数据中likeCount为0
 * 进行修改
 */
public class Fix0Like {

    private static List<String> pList = new ArrayList<String>();

    public static void get() {
        String sql = "select postID, createTime from post_table where likeCount=0";
        pList = DBProcess.get(sql, 2);
        for (String info : pList) {
            String pid = info.split(";")[0];
            String createTime = info.split(";")[1];
            File dir = new File("E:\\Data\\facebook\\raw pages\\feeds\\" + CommonData.getNameByID(pid.split("_")[0]) + "\\" + pid);
            System.out.println(dir.getPath());
            FileUtils.deleteQuietly(dir);
            FeedsCrawl crawl = new FeedsCrawl();
            crawl.getFeedsByID(pid, createTime);

        }
    }



    public static void main(String[] args) {
        Fix0Like.get();
    }
}
