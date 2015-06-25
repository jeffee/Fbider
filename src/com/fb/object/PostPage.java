/**
 * ********************************************
 *
 * @ date  : 2015年4月1日,下午3:35:49
 * @ author: Jeffee Chen
 * **********************************************
 * Copyright © Jeffee, All Rights Reserved   *
 * **********************************************
 */
package com.fb.object;

import com.fb.common.Common;
import com.fb.common.UrlProcess;
import com.restfb.json.JsonArray;
import com.restfb.json.JsonObject;

import java.util.LinkedList;
import java.util.List;

public class PostPage {


    /***post信息，提供存储数据库的post_table***/
    private List<String> postList;

    /***post的ID信息，包括ID和created_time，下一步抓取评论和点赞用，供给存储数据库的sup_post_table**/
    private List<String> idList;

    private String next;
    private String previous;

    /**
     * 下次抓取的起始时间，在previous中获取***
     */
    private String since;

    public PostPage() {
        postList = new LinkedList<>();
        idList = new LinkedList<>();
        next = "";
        previous = "";
    }

    public PostPage(JsonObject jObj) {
        this();
        getPage(jObj);
    }

    private void getPage(JsonObject jObj) {
        if (jObj==null)
            return;
        JsonArray array = jObj.getJsonArray("data");
        if (array.length() < 1)
            return;

        for (int i = 0; i < array.length(); i++) {
            JsonObject obj = array.getJsonObject(i);
            Post post = new Post(obj);
            postList.add(post.toString());
            String pID = obj.getString("id");
            String cTime = obj.getString("created_time");       //时间格式为2015-04-14T06:27:16+0000
            idList.add(pID+";"+Common.parseTime(cTime));
        }

        try {
            previous = jObj.getJsonObject("paging").getString("previous");
            setSince();
            next = jObj.getJsonObject("paging").getString("next");
            next = UrlProcess.updateUrl(next);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 从previous中提取出since的值***
     */
    private void setSince() {
        int index = previous.indexOf("since=");
        if (index != -1) {
            since = previous.substring(index + 6);
            index = since.indexOf("&");
            if (index != -1) {
                since = since.substring(0, index);
            }
        }
    }

    public String getNext() {
        return next;
    }

    public String getPrevious() {
        return previous;
    }

    public String getSince() {
        return since;
    }

    public List<String> getIdList() {

        return idList;
    }

    public List<String> getPostList() {

        return postList;
    }

    public static void main(String[] args) {
        PostPage page = new PostPage();
        page.previous = "https://graph.facebook.com/v2.2/10150145806225128/posts?since=1428466923&limit=25&__paging_token=enc_AdBtQOaQDnyqx51jOVVtjM6gXAIFEzm08dldE5F5jofpfbBYkhjPDCOcE9upRgxxe3OB5Umd8tN6JWoQZBZBlmE936hCoUCukiVLr60BGCtqI4zQZDZD";
        page.setSince();
        System.out.printf(page.since);
    }
}