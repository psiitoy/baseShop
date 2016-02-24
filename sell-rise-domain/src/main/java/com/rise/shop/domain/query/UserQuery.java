package com.rise.shop.domain.query;

import com.rise.shop.persistence.query.DefaultBaseQuery;

/**
 * Created by wangdi on 15-1-18.
 */
public class UserQuery extends DefaultBaseQuery {

    private String email;
    private Integer authCodeIntervalGt;
    private Integer authCodeIntervalGte;
    private Integer authCodeIntervalLt;
    private Integer authCodeIntervalLte;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAuthCodeIntervalGt() {
        return authCodeIntervalGt;
    }

    public void setAuthCodeIntervalGt(Integer authCodeIntervalGt) {
        this.authCodeIntervalGt = authCodeIntervalGt;
    }

    public Integer getAuthCodeIntervalGte() {
        return authCodeIntervalGte;
    }

    public void setAuthCodeIntervalGte(Integer authCodeIntervalGte) {
        this.authCodeIntervalGte = authCodeIntervalGte;
    }

    public Integer getAuthCodeIntervalLt() {
        return authCodeIntervalLt;
    }

    public void setAuthCodeIntervalLt(Integer authCodeIntervalLt) {
        this.authCodeIntervalLt = authCodeIntervalLt;
    }

    public Integer getAuthCodeIntervalLte() {
        return authCodeIntervalLte;
    }

    public void setAuthCodeIntervalLte(Integer authCodeIntervalLte) {
        this.authCodeIntervalLte = authCodeIntervalLte;
    }
}
