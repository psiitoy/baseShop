package com.rise.shop.persistence.beans;

import java.util.Date;

/**
 * mysql基本字段
 * Created by wangdi on 15-1-18.
 */
public class BasePersistenceBean {
    private Long id;//id
    private Date created;//创建时间
    private Date modified;//更改时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }
}
