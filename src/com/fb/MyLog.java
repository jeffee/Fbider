package com.fb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Jeffee Chen on 2015/4/28.
 */
public class MyLog {
    private static final Logger logger = LoggerFactory.getLogger(MyLog.class);

    public static void main(String[] args) {
        logger.info("Hello {}","TestLogback");
    }

}
