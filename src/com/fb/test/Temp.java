package com.fb.test;

import com.fb.common.FileProcess;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jeffee Chen on 2015/4/21.
 */
public class Temp
{

    public static void main(String[] args) {
//        List<String> list = FileProcess.read(new File("E:\\ex\\蔡英文 Tsai Ing-wen\\四月-final.csv"));
//        for (String info : list) {
//            Pattern pattern = Pattern.compile(" (.*?),");
//            Matcher m = pattern.matcher(info);
//            StringBuffer sb = new StringBuffer();
//            if (m.find()) {
//                m.appendReplacement(sb, "");
//                sb.append(",");
//                m.appendTail(sb);
//                System.out.println(sb.toString());
//            }
//        }
    String str = "11;12;243;343";
        String[] strs = str.split(";");
        System.out.println(strs.length);
        str = "1,2";
        strs = str.split(",");
        System.out.println(strs.length);
    }
}
