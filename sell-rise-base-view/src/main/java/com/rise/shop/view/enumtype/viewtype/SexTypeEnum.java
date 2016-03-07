package com.rise.shop.view.enumtype.viewtype;

import com.rise.shop.view.enumtype.BaseViewType;

/**
 * Created by wangdi on 15-1-30.
 */
public enum SexTypeEnum implements BaseViewType {
    SEX_MALE(1, "男"),
    SEX_FEMALE(2, "女");

    private int type;
    private String value;

    SexTypeEnum(int type, String value) {
        this.type = type;
        this.value = value;
    }

    public static String getValue(int type) {
        for (SexTypeEnum enm : SexTypeEnum.values()) {
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
