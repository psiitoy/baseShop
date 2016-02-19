package com.rise.shop.domain.enumtype;

/**
 * Created by wangdi on 15-1-30.
 */
public enum YesNoTypeEnum implements BaseViewType {
    YES(1, "是"),
    NO(2, "否");

    private int type;
    private String value;

    YesNoTypeEnum(int type, String value) {
        this.type = type;
        this.value = value;
    }

    public static String getValue(int type) {
        for (YesNoTypeEnum enm : YesNoTypeEnum.values()) {
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
