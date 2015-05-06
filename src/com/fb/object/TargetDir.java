package com.fb.object;

/**
 * Created by Jeffee Chen on 2015/4/2.
 */
public class TargetDir {

    /***根目录**/
    public final static String ROOTDIR="E:\\Data\\facebook";

    /****存放用户发布内容页面的原始文件***/
    public static String RAW_POST_DIR = genFileName(ROOTDIR, "raw pages", "posts");

    /****存处理过的用户发布内容，等待被存数据库****/
    public static String DB_POST_DIR = genFileName(ROOTDIR, "DB files", "posts");

    /***存放用户内容所获得的评论、点赞等信息的原始页面***/
    public static String RAW_FEEDS_DIR = genFileName(ROOTDIR, "raw pages", "feeds");

    /*****存放处理过的评论、点赞，等待存入数据库*****/
    public static String DB_FEEDS_DIR = genFileName(ROOTDIR, "DB files", "feeds");

    public static String ID_DIR = genFileName(ROOTDIR, "ids", "todo");

    public static String BACK_DIR = genFileName(ROOTDIR, "ids", "finished");

    public static String LOG_DIR = genFileName(ROOTDIR, "logs");

    public static String PERSONAL_DIR = genFileName(ROOTDIR, "personal");
    public static String genFileName(String arg1, String... args) {
        String str = arg1;
        for(String arg:args)
            str+="\\"+arg;
        return str;
    }

}
