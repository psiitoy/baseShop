package com.rise.shop.web.tools;

import com.rise.shop.view.enumtype.BaseViewType;
import com.rise.shop.view.enumtype.viewtype.AuthCodeTypeEnum;
import com.rise.shop.view.enumtype.viewtype.SexTypeEnum;
import com.rise.shop.view.enumtype.viewtype.YesNoTypeEnum;
import com.rise.shop.web.enumtype.*;

/**
 * Created by wangdi on 15-1-30.
 */
public class EnumTools {

    /**
     * 根据字段名获取枚举
     *
     * @param fieldName
     * @return
     */
    public BaseViewType[] getEnumByFieldName(String fieldName) {
        if ("orgType".equals(fieldName)) {
            return OrgTypeEnum.values();
        } else if ("gainType".equals(fieldName)) {
            return GainTypeEnum.values();
        } else if ("sex".equals(fieldName)) {
            return SexTypeEnum.values();
        } else if ("authCode".equals(fieldName)) {
            return AuthCodeTypeEnum.values();
        } else if ("isShow".equals(fieldName)) {
            return YesNoTypeEnum.values();
        }
        return null;
    }

    public String getEnumValueByFieldNameAndType(String fieldName, int type) {
        if ("orgType".equals(fieldName)) {
            return OrgTypeEnum.getValue(type);
        } else if ("gainType".equals(fieldName)) {
            return GainTypeEnum.getValue(type);
        } else if ("sex".equals(fieldName)) {
            return SexTypeEnum.getValue(type);
        } else if ("authCode".equals(fieldName)) {
            return AuthCodeTypeEnum.getValue(type);
        } else if ("isShow".equals(fieldName)) {
            return YesNoTypeEnum.getValue(type);
        }
        return null;
    }
}
