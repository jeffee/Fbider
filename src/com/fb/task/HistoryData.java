package com.fb.task;

/**
 * Created by Jeffee Chen on 2015/4/20.
 */
public class HistoryData {

    public static void get(String uid, String since, String until) {
/*        SingleCrawl crawl = new SingleCrawl(uid);
        crawl.get(since, until);*/
        FeedsCrawl feedsCrawl = new FeedsCrawl();
        feedsCrawl.get();
    }


    public static void main(String[] args) {
        HistoryData.get("10150145806225128", "2015-01-01", "2015-03-17");

    }
}
