package com.rise.shop.domain.enumtype;

/**
 * Created by wangdi on 15-1-30.
 */
public enum OrgTypeEnum implements BaseViewType {
    ORG_HUALANG(1, "画廊"),
    ORG_YUANXIAO(2, "院校"),
    ORG_XIEHUI(3, "协会"),
    ORG_JIJINHUI(4, "基金会"),
    ORG_PAIMAIGONGSI(5, "拍卖公司");//画廊、院校、协会、基金会、拍卖公司

    private int type;
    private String value;

    OrgTypeEnum(int type, String value) {
        this.type = type;
        this.value = value;
    }

    public static String getValue(int type) {
        for (OrgTypeEnum enm : OrgTypeEnum.values()) {
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
