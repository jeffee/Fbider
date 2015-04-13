package com.fb.task;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Jeffee Chen on 2015/4/7.
 */
public class TimeTask {


    static int count = 0;

    private static final long PERIOD = 24 * 60 * 60 * 1000;

    public static void main(String[] args) {
        operateOnTime();

    }


    public static void operateOnTime() {

        TimerTask task = new TimerTask() {

            @Override
            public void run() {

                System.out.println(" 第 " + ++count + " 次执行 ");
            }
        };



        Timer timer = new Timer();

        // 设置从某一时刻开始执行，然后每隔多长时间重复执行
        // 设置从当前时间开始执行，然后每隔2 秒执行一次

        timer.schedule(task, Calendar.getInstance().getTime(), 2 * 1000);

    }


}