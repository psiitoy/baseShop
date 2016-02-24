package com.rise.shop.domain.query;

import com.rise.shop.persistence.query.DefaultBaseQuery;

/**
 * Created by wangdi on 15-1-18.
 */
public class UserQuery extends DefaultBaseQuery {

    private String email;
    private Integer authCodeSymbolGt;
    private Integer authCodeSymbolGte;
    private Integer authCodeSymbolLt;
    private Integer authCodeSymbolLte;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAuthCodeSymbolGt() {
        return authCodeSymbolGt;
    }

    public void setAuthCodeSymbolGt(Integer authCodeSymbolGt) {
        this.authCodeSymbolGt = authCodeSymbolGt;
    }

    public Integer getAuthCodeSymbolGte() {
        return authCodeSymbolGte;
    }

    public void setAuthCodeSymbolGte(Integer authCodeSymbolGte) {
        this.authCodeSymbolGte = authCodeSymbolGte;
    }

    public Integer getAuthCodeSymbolLt() {
        return authCodeSymbolLt;
    }

    public void setAuthCodeSymbolLt(Integer authCodeSymbolLt) {
        this.authCodeSymbolLt = authCodeSymbolLt;
    }

    public Integer getAuthCodeSymbolLte() {
        return authCodeSymbolLte;
    }

    public void setAuthCodeSymbolLte(Integer authCodeSymbolLte) {
        this.authCodeSymbolLte = authCodeSymbolLte;
    }

}
