package com.fb.common;

import com.fb.object.TargetDir;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.SimpleTimeZone;

/**
 * Created by Jeffee Chen on 2015/4/7.
 */
public class Demo {

    private void backFile(File sFile) {
        String fPath = sFile.getPath();
        fPath = fPath.substring(fPath.indexOf("todo") + 5);
        fPath = TargetDir.genFileName(TargetDir.BACK_DIR, fPath);
        File dFile = new File(fPath);
        File dir = new File(dFile.getParent());
        if(!dir.exists() || !dir.isDirectory())
            dir.mkdirs();
        sFile.renameTo(dFile);
    }

    public static void main(String[] args) {
            String str = Common.parseTime("2015-04-14T06:27:16+0000");
        System.out.println(str);
//        Demo demo = new Demo();
//        File file = new File("E:\\Data\\facebook\\ids\\todo\\蔡英文 Tsai Ing-wen\\hello");
//        File [] files = file.listFiles();
//        for (File f : files) {
//            demo.backFile(f);
//        }
//        files = file.listFiles();
//        if (files.length < 1) {
//            file.delete();
//        }

//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar cal = Calendar.getInstance();
//        try {
//            cal.setTime(format.parse("2009-01-01"));
//            cal.add(Calendar.DATE, -30);
//            System.out.println(format.format(cal.getTime()));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        // System.out.println(subStr);
        //file.renameTo(new File("E:\\test\\ha\\demo.txt"));
    }
}
