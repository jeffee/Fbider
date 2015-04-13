package com.fb.task;

import com.fb.common.Common;
import com.fb.common.CommonData;
import com.fb.common.FileProcess;
import com.fb.crawl.Crawl;
import com.fb.object.Post;
import com.fb.object.PostPage;
import com.restfb.json.JsonObject;

import java.util.List;

/**
 * Created by Jeffee Chen on 2015/4/2
 *
 */
public class NineOne {

    public static void crawlByUserID(String uid, String since, String until) {

        String idFile = Common.genFileName(CommonData.ROOT_DIR, "todo", uid+ ".fb");
        String rawFile = Common.genFileName(CommonData.ROOT_DIR, "rawPages", "posts", uid, since+ ".fb");
        String postFile = Common.genFileName(CommonData.ROOT_DIR, "dbFiles", "posts", uid, since+ ".fb");
        String url = uid + "/posts?access_token=" + CommonData.MY_ACCESS_TOKEN + "&limit=110&since="+since+"&until="+until+"&";
        JsonObject jObj = Crawl.get(url);

        PostPage page = new PostPage(jObj);
        FileProcess.write(idFile, page.getIdList());
        FileProcess.write(rawFile, jObj.toString());
        FileProcess.write(postFile, page.getPostList());
    }

    public static void main(String[] args) {
        NineOne.crawlByUserID("chiayi.twu","2014-08-01","2014-09-01");
    }

}
