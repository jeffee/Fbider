package com.fb.test;

import com.fb.common.FileProcess;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Jeffee Chen on 2015/4/21.
 */
public class ShareCount {

    private List<String> infoList = new ArrayList<>();

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public ShareCount(String fName) {
        infoList = FileProcess.read(fName);
    }

    public void onDate() {
        int month = 1;
        int count = 0;
        for (String line : infoList) {
            String[] infos = line.split(",");
            Date date = null;
            try {
                date = format.parse(infos[0]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (date.getMonth() < month)
                count += Integer.parseInt(infos[2]);


        }
    }


    public static void main(String[] args) {

    }
}
