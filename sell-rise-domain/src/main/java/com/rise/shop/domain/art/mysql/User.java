package com.rise.shop.domain.art.mysql;

import com.rise.shop.domain.ano.FieldMeta;
import com.rise.shop.domain.art.ViewBasePersistenceBean;
import com.rise.shop.domain.constant.FieldTypeConstant;

/**
 * Created by wangdi on 15-1-18.
 */
public class User extends ViewBasePersistenceBean {
    @FieldMeta(name = "用户名", order = 1, editable = false, notnull = true)
    private String email;
    @FieldMeta(name = "密码", order = 2, notnull = true)
    private String pwd;
    private Integer state;
    @FieldMeta(name = "权限", order = 3, type = FieldTypeConstant.FIELD_TYPE_RATIO, notnull = true)
    private Integer authCode;

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", pwd='" + pwd + '\'' +
                ", state=" + state +
                ", authCode=" + authCode +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getAuthCode() {
        return authCode;
    }

    public void setAuthCode(Integer authCode) {
        this.authCode = authCode;
    }
}
