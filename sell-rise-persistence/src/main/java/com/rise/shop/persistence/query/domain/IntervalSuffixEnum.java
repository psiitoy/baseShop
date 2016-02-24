package com.rise.shop.persistence.query.domain;

/**
 * 区间查询 后缀
 */
public enum IntervalSuffixEnum {
    /**
     * 大于
     */
    GREATER_THAN("IntervalGt"),
    /**
     * 大于等于
     */
    GREATER_THAN_EQUALS("IntervalGte"),
    /**
     * 小于等于
     */
    LESS_THAN("IntervalLt"),
    /**
     * 小于等于
     */
    LESS_THAN_EQUALS("IntervalLte");

    private String fieldSuffix;

    IntervalSuffixEnum(String fieldSuffix) {
        this.fieldSuffix = fieldSuffix;
    }

    public static IntervalSuffixEnum getIntervalSuffixEnumByFieldName(String fieldName) {
        if (fieldName == null) {
            return null;
        }
        for (IntervalSuffixEnum intervalSuffixEnum : IntervalSuffixEnum.values()) {
            if (fieldName.endsWith(intervalSuffixEnum.fieldSuffix)) {
                return intervalSuffixEnum;
            }
        }
        return null;
    }

    public static String getRealFieldNameWithoutSuffix(String fieldName) {
        for (IntervalSuffixEnum intervalSuffixEnum : IntervalSuffixEnum.values()) {
            if (fieldName.endsWith(intervalSuffixEnum.fieldSuffix)) {
                return fieldName.split(intervalSuffixEnum.fieldSuffix)[0];
            }
        }
        return fieldName;
    }

}