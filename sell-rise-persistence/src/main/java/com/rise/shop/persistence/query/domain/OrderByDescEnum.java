package com.rise.shop.persistence.query.domain;

/**
 * 数据库排序
 */
public enum OrderByDescEnum {
    /**
     * 降序
     */
    DESC(-1),
    /**
     * 升序
     */
    ASC(1);

    private int mongoOrder;

    OrderByDescEnum(int mongoOrder) {
        this.mongoOrder = mongoOrder;
    }

    public int getMongoOrder() {
        return mongoOrder;
    }
}