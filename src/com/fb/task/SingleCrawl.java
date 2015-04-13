package com.fb.task;

import com.fb.common.CommonData;
import com.fb.common.FileProcess;
import com.fb.crawl.Crawl;
import com.fb.object.PostPage;
import com.fb.object.TargetDir;
import com.restfb.json.JsonObject;

/**
 * Created by Jeffee Chen on 2015/4/2.
 */
public class SingleCrawl {

    private String uID;
    private String uName;
    private String rawPostFile;
    private String DBPostFile;
    private String idFile;

    /**
     * 记录抓取的最后时限，用于下一次抓取**
     */
    private String since;

    public SingleCrawl(String uID) {
        this.uID = uID;
        this.uName = CommonData.getNameByID(uID);
        System.out.println(uName + " begins:");
    }

    /**初始化抓取的数据存放的目录**/
    public void initDir(String since, String until) {
        String subName = TargetDir.genFileName(uName, since + "." + until);
        idFile = TargetDir.genFileName(TargetDir.ID_DIR, subName);
        rawPostFile = TargetDir.genFileName(TargetDir.RAW_POST_DIR, subName);
        DBPostFile = TargetDir.genFileName(TargetDir.DB_POST_DIR, subName);
    }

    /**
     * *
     *
     * @param since:起始时间，格式为2015-03-01 **
     * @param until:终止时间，格式同上
     */
    public int get(String since, String until) {
        if(since==null || until==null || since.trim().equals("") || until.trim().equals(""))
            return 0;

        long bTime = System.currentTimeMillis();
        initDir(since, until);
        String url = uID + "/posts?access_token=" + CommonData.MY_ACCESS_TOKEN + "&since=" + since + "&until=" + until + "&limit=100&";
        JsonObject jObj = Crawl.get(url);

        PostPage page = new PostPage(jObj);
        long eTime = System.currentTimeMillis();
        System.out.println(eTime - bTime + " seconds");

        int count = page.getIdList().size();
        if (count == 0)   //没有数据
            return 0;


        this.since = page.getSince();       //since是下次的起始时间，只需在这次的第一页中获取previous链接地址即可

        if (page.getIdList().size() < 100) {         //ֻ说明没有多页
            FileProcess.write(idFile, page.getIdList());
            FileProcess.write(rawPostFile, jObj.toString());
            FileProcess.write(DBPostFile, page.getPostList());
        } else {                       //有多页需要处理
            FileProcess.write(idFile + "\\1", page.getIdList());
            FileProcess.write(rawPostFile + "\\1", jObj.toString());
            FileProcess.write(DBPostFile + "\\1", page.getPostList());
            morePage(page.getNext(), 1);
        }
        return count;
    }

    public String getSince() {
        return since;
    }

    private void morePage(String next, int count) {
        if (next.trim().equals(""))
            return;

        count++;
        long bTime = System.currentTimeMillis();
        JsonObject jObj = Crawl.get(next);
        PostPage page = new PostPage(jObj);
        if (page.getIdList().size() == 0)
            return;

        FileProcess.write(idFile + "\\" + count, page.getIdList());
        FileProcess.write(rawPostFile + "\\" + count, jObj.toString());
        FileProcess.write(DBPostFile + "\\" + count, page.getPostList());
        long eTime = System.currentTimeMillis();
        System.out.println(eTime - bTime + " seconds");
        morePage(page.getNext(), count);
        System.out.println(count + " pages finished...");
    }

    public static void main(String[] args) {
        SingleCrawl crawl = new SingleCrawl("46251501064");
        crawl.get("2015-01-01", "2015-03-01");
    }
}
