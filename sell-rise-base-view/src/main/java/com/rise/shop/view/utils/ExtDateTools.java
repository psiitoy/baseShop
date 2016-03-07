package com.rise.shop.view.utils;

import org.apache.velocity.tools.generic.DateTool;

import java.util.Date;

/**
 * Created by wangdi on 15-1-19.
 */
public class ExtDateTools extends DateTool {

    public String formatLong(Long timestamp) {
        if (timestamp == null) {
            return "time is null";
        } else {
            return format("yyyy-MM-dd", new Date(timestamp));
        }
    }
}
