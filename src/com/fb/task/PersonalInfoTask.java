package com.fb.task;

import com.fb.DB.DBProcess;
import com.fb.common.CommonData;
import com.fb.common.FileProcess;
import com.fb.crawl.Crawl;
import com.fb.object.TargetDir;
import com.restfb.json.JsonObject;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Jeffee Chen on 2015/4/7.
 */
public class PersonalInfoTask {


    static int count = 0;

    private static final long DAY_PERIOD = 24 * 60 * 60 * 1000;

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    public static void main(String[] args) {
        operateOnTime();
    }


    public static void operateOnTime() {

        TimerTask dailyTask = new TimerTask() {
            @Override
            public void run() {
                String sql = "select userid, uname from " + CommonData.USER_TABLE;
                List<String> list = DBProcess.get(sql, 2);
                for (String info:list) {
                    String[] infoArray = info.split(";");
                    JsonObject obj = Crawl.get(infoArray[0]+"?");
                    SupUser.update(obj);
                    String date = format.format(new Date());
                    String dFile = TargetDir.genFileName(TargetDir.PERSONAL_DIR, infoArray[1], date);
                    FileProcess.write(dFile, obj.toString());
                }
                System.out.println("于 " + format.format(new Date()) + " 第 " + ++count + " 次执行 ");
            }
        };
        Timer timer = new Timer();

        // 设置从某一时刻开始执行，然后每隔多长时间重复执行
        // 设置从当前时间开始执行，然后每隔period执行一次

        timer.schedule(dailyTask, Calendar.getInstance().getTime(), DAY_PERIOD);

    }

}