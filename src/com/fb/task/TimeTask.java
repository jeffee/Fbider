package com.fb.task;

import com.fb.DB.DBProcess;
import com.fb.common.CommonData;

import java.util.*;

/**
 * Created by Jeffee Chen on 2015/4/7.
 */
public class TimeTask {


    static int count = 0;

    private static final long PERIOD = 6 * 60 * 60 * 1000;

    private static final long DAY_PERIOD = 24 * 60 * 60 * 1000;

    public static void main(String[] args) {
        operateOnTime();

    }


    public static void operateOnTime() {

        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                Discarder.discard();            //将30天前的内容从关注列表中删除

                CheckUpdates.update();                  //更新监控用户新发布的posts

                FeedsCrawl feedsCrawl = new FeedsCrawl();             //抓取上一步操作所获取到的新posts的评论和点赞
                feedsCrawl.get();

                CommentUpdate.updateComments();                //更新监控posts的评论和点赞
                LikeUpdate.updateLikes();

                System.out.println("于 " + CommonData.dateFormat.format(new Date()) + " 第 " + ++count + " 次执行 ");
            }
        };


        Timer timer = new Timer();

        // 设置从某一时刻开始执行，然后每隔多长时间重复执行
        // 设置从当前时间开始执行，然后每隔period执行一次

        timer.schedule(task, Calendar.getInstance().getTime(), PERIOD);

    }


}