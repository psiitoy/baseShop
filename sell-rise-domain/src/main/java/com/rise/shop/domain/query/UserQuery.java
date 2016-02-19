package com.rise.shop.domain.query;

import com.rise.shop.domain.art.mysql.User;
import com.rise.shop.persistence.query.Query;

/**
 * Created by wangdi on 15-1-18.
 */
public class UserQuery extends User implements Query {
    private Integer index;
    private Integer pageSize;
    private Integer pageNo;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public Integer getPageNo() {
        return pageNo;
    }

    @Override
    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }
}
