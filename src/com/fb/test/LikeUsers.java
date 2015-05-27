package com.fb.test;

import com.fb.common.FileProcess;
import com.restfb.json.JsonArray;
import com.restfb.json.JsonObject;

import java.io.File;
import java.util.*;

/**
 * Created by Jeffee Chen on 2015/4/20.
 */
public class LikeUsers {

    public static void main(String[] args) {
        File dir = new File("E:\\Data\\facebook\\raw pages\\feeds\\朱立倫");
        LikeUsers.ana(dir);
    }

    public static void dis(File sFile, int count) {
        List<String> list = FileProcess.read("");
        int sub = (int)(count*0.8);
        for (String info : list) {
            Integer cunt = Integer.parseInt(info);
        }
    }

    private static Map<String, Integer> map = new HashMap<>();

    public static void ana(File uDir) {
        File[] pDirs = uDir.listFiles();
        for (File pDir : pDirs) {
            System.out.println(pDir.getName()+"  is starting");
            File dir = new File(pDir.getPath() + "\\likes");
            File[] fileArray = dir.listFiles();
            for (File file : fileArray) {
                JsonObject obj = new JsonObject(FileProcess.readLine(file));
                JsonArray array = obj.getJsonArray("data");
                for (int i = 0; i < array.length(); i++) {
                    JsonObject jObj = array.getJsonObject(i);
                    String id = jObj.getString("id");
                    if (map.get(id) == null) {
                        map.put(id, 1);
                    } else {
                        int count = map.get(id) + 1;
                        map.put(id, count);
                    }
                }
            }
        }
        List<String> list = new ArrayList<String>();
        Iterator<String> iter = map.keySet().iterator();
        while (iter.hasNext()) {
            String id = iter.next();
            list.add(id + "," + map.get(id));
        }

        FileProcess.write("E:\\ex\\likeUser\\"+uDir.getName()+".csv", list);
    }


}
