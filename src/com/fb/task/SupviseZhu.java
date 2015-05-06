package com.fb.task;

import com.fb.common.CommonData;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Jeffee Chen on 2015/4/7.
 */
public class SupviseZhu {



    private static final long PERIOD =  60 * 1000;


    public static void main(String[] args) {
        operateOnTime();

    }


    public static void operateOnTime() {

        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                Supvise.sup();
                System.out.println("waiting at:" + CommonData.dateFormat.format(new Date()));
            }
        };


        Timer timer = new Timer();

        // 设置从某一时刻开始执行，然后每隔多长时间重复执行
        // 设置从当前时间开始执行，然后每隔period执行一次

        timer.schedule(task, Calendar.getInstance().getTime(), PERIOD);

    }


}