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

import com.fb.common.CommonData;
import com.fb.common.UrlProcess;
import com.fb.crawl.Crawl;
import com.restfb.json.JsonArray;
import com.restfb.json.JsonObject;

import java.util.LinkedList;
import java.util.List;

public class PostPage {


    private List<String> postList;
    private List<String> idList;
    private String next;
    private String previous;

    /***下次抓取的起始时间，在previous中获取****/
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
        JsonArray array = jObj.getJsonArray("data");
        for (int i = 0; i < array.length(); i++) {
            JsonObject obj = array.getJsonObject(i);
            Post post = new Post(obj);
            postList.add(post.toString());
            idList.add(obj.getString("id"));
        }
        if (array.length() > 0) {
            try {
                previous = jObj.getJsonObject("paging").getString("previous");
                setSince();
                next = jObj.getJsonObject("paging").getString("next");
                next = UrlProcess.updateUrl(next);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /***从previous中提取出since的值****/
    private void setSince() {
        int index = previous.indexOf("since=");
        if (index != -1) {
            since = previous.substring(index + 6);
            index = since.indexOf("&");
            if (index != -1) {
                since = since.substring(0, index);
            }
        }
        System.out.println("Since="+since);
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
        page.previous =  "https://graph.facebook.com/v2.2/10150145806225128/posts?since=1428466923&limit=25&__paging_token=enc_AdBtQOaQDnyqx51jOVVtjM6gXAIFEzm08dldE5F5jofpfbBYkhjPDCOcE9upRgxxe3OB5Umd8tN6JWoQZBZBlmE936hCoUCukiVLr60BGCtqI4zQZDZD";
        page.setSince();
        System.out.printf(page.since);
    }
}