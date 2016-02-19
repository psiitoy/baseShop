package com.rise.shop.domain.enumtype;

/**
 * Created by wangdi on 15-1-30.
 */
public enum AuthCodeTypeEnum implements BaseViewType {
    AUTH_READONLY(1, "只读"),
    AUTH_EDIT(2, "编辑"),
    AUTH_ALL(3, "全部");

    private int type;
    private String value;

    AuthCodeTypeEnum(int type, String value) {
        this.type = type;
        this.value = value;
    }

    public static String getValue(int type) {
        for (AuthCodeTypeEnum enm : AuthCodeTypeEnum.values()) {
            if (enm.getType() == type) {
                return enm.getValue();
            }
        }
        return "unknow";
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
