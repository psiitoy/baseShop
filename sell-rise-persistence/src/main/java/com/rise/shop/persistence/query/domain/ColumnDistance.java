package com.rise.shop.persistence.query.domain;

import java.io.Serializable;

/**
 * 时间区间
 * Created by wangdi on 16-2-19.
 */
public final class ColumnDistance implements Serializable {
    private String fieldName;
    private Object columnFrom;
    private Object columnTo;

    public ColumnDistance(String fieldName, Object columnFrom, Object columnTo) {
        this.fieldName = fieldName;
        this.columnFrom = columnFrom;
        this.columnTo = columnTo;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Object getColumnFrom() {
        return columnFrom;
    }

    public void setColumnFrom(Object columnFrom) {
        this.columnFrom = columnFrom;
    }

    public Object getColumnTo() {
        return columnTo;
    }

    public void setColumnTo(Object columnTo) {
        this.columnTo = columnTo;
    }
}