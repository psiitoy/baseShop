package com.rise.shop.domain.art.mongo;

/**
 * Created by wangdi on 15-1-8.
 */
public class Event {
    private long id;
    private String name;
    private long time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
