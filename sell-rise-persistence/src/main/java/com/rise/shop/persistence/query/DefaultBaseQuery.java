package com.rise.shop.persistence.query;

import com.rise.shop.persistence.query.domain.ColumnDistance;

import java.util.ArrayList;
import java.util.List;

/**
 * 区间查询 继承排序查找
 * Created by wangdi on 15-1-9.
 */
public class DefaultBaseQuery extends OrderByBaseQuery {

    private List<ColumnDistance> columnDistanceList = new ArrayList<ColumnDistance>();

    public List<ColumnDistance> getColumnDistanceList() {
        return columnDistanceList;
    }

    public void setColumnDistanceList(List<ColumnDistance> columnDistanceList) {
        this.columnDistanceList = columnDistanceList;
    }

    public void addColumnDistance(ColumnDistance columnTimeDistance) {
        columnDistanceList.add(columnTimeDistance);
    }

    public void clearColumnDistance() {
        columnDistanceList.clear();
    }

    public String getColumnDistance() {
        if (columnDistanceList.isEmpty()) {
            return null;
        }
        return null;
//        return StringUtils.join(columnDistanceList, ColumnOrder.SEPARATOR);
    }
}
