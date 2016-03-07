package com.rise.shop.web.enumtype;

/**
 * Created by wangdi on 14-12-10.
 */
public enum ServiceResultCodeEnum {

    SUCCESS(0, "成功"),
    DB_ERR(1, "数据库异常");

    public static String getValue(int type) {
        for (ServiceResultCodeEnum enm : ServiceResultCodeEnum.values()) {
            if (enm.getType() == type) {
                return enm.getValue();
            }
        }
        return "unknow";
    }

    private int type;
    private String value;

    ServiceResultCodeEnum(int type, String value) {
        this.type = type;
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
