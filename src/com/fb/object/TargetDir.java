package com.fb.object;

/**
 * Created by Jeffee Chen on 2015/4/2.
 */
public class TargetDir {

    /***��Ŀ¼**/
    public final static String ROOTDIR="E:\\Data\\facebook";

    /****����û���������ҳ���ԭʼ�ļ�***/
    public static String RAW_POST_DIR = genFileName(ROOTDIR, "raw pages", "posts");

    /****�洦������û��������ݣ��ȴ��������ݿ�****/
    public static String DB_POST_DIR = genFileName(ROOTDIR, "DB files", "posts");

    /***����û���������õ����ۡ����޵���Ϣ��ԭʼҳ��***/
    public static String RAW_FEEDS_DIR = genFileName(ROOTDIR, "raw pages", "feeds");

    /*****��Ŵ���������ۡ����ޣ��ȴ��������ݿ�*****/
    public static String DB_FEEDS_DIR = genFileName(ROOTDIR, "DB files", "feeds");

    public static String ID_DIR = genFileName(ROOTDIR, "ids", "todo");

    public static String BACK_DIR = genFileName(ROOTDIR, "ids", "finished");

    public static String genFileName(String arg1, String... args) {
        String str = arg1;
        for(String arg:args)
            str+="\\"+arg;
        return str;
    }

}
