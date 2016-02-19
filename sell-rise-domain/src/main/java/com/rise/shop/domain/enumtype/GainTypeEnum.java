package com.rise.shop.domain.enumtype;

/**
 * Created by wangdi on 15-1-30.
 */
public enum GainTypeEnum implements BaseViewType {
    GAINTYPE_MONEY(1, "盈利"),
    GAINTYPE_FREE(2, "非盈利");

    private int type;
    private String value;

    GainTypeEnum(int type, String value) {
        this.type = type;
        this.value = value;
    }

    public static String getValue(int type) {
        for (GainTypeEnum enm : GainTypeEnum.values()) {
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
