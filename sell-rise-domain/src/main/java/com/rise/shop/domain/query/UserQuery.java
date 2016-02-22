package com.rise.shop.domain.query;

import com.rise.shop.persistence.query.DefaultBaseQuery;

/**
 * Created by wangdi on 15-1-18.
 */
public class UserQuery extends DefaultBaseQuery {

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
