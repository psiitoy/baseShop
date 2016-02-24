package com.rise.shop.persistence.query;

import com.rise.shop.persistence.query.domain.ColumnOrder;
import com.rise.shop.persistence.query.domain.OrderByDescEnum;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 排序查找 继承分页
 * Created by wangdi on 15-1-9.
 */
public class OrderByBaseQuery extends BaseQuery {

    private List<ColumnOrder> orderByList = new ArrayList<ColumnOrder>();

    public List<ColumnOrder> getOrderByList() {
        return orderByList;
    }

    public void setOrderByList(List<ColumnOrder> orderByList) {
        this.orderByList = orderByList;
    }

    public void addOrderBy(ColumnOrder columnOrder) {
        orderByList.add(columnOrder);
    }

    public void addOrderBy(OrderByDescEnum orderEnum, String... columnNames) {
        addOrderBy(new ColumnOrder(orderEnum, columnNames));
    }

    public void clearOrderBy() {
        orderByList.clear();
    }

    public String getOrderBy() {
        if (orderByList.isEmpty()) {
            return null;
        }
        return StringUtils.join(orderByList, ColumnOrder.SEPARATOR);
    }
}
