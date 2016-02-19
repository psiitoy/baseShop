package com.rise.shop.persistence.query;

/**
 * 数据库排序
 * Created by wangdi on 16-2-19.
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


    /**
     * 找不到 抛出异常
     *
     * @param mongoOrder
     * @return
     */
    public static OrderByDescEnum parserByMongoOrder(int mongoOrder) {
        return parserByMongoOrder(mongoOrder, true, null);
    }

    /**
     * 找不到用def代替
     *
     * @param mongoOrder
     * @param def
     * @return
     */
    public static OrderByDescEnum parserByMongoOrder(int mongoOrder, OrderByDescEnum def) {
        return parserByMongoOrder(mongoOrder, false, def);
    }

    /**
     * @param mongoOrder
     * @param throwException
     * @param def
     * @return
     */
    public static OrderByDescEnum parserByMongoOrder(int mongoOrder, boolean throwException, OrderByDescEnum def) {
        for (OrderByDescEnum orderByDescEnum : OrderByDescEnum.values()) {
            if (orderByDescEnum.getMongoOrder() == mongoOrder) {
                return orderByDescEnum;
            }
        }
        if (throwException) {
            throw new RuntimeException("mongoOrder:" + mongoOrder);
        } else {
            return def;
        }
    }

    private int mongoOrder;

    OrderByDescEnum(int mongoOrder) {
        this.mongoOrder = mongoOrder;
    }

    public int getMongoOrder() {
        return mongoOrder;
    }
}