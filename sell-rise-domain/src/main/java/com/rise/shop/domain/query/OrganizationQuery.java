package com.rise.shop.domain.query;

import com.rise.shop.common.ano.ViewMeta;
import com.rise.shop.persistence.query.BaseQuery;

/**
 * Created by wangdi on 15-1-9.
 */
public class OrganizationQuery extends BaseQuery {
    @ViewMeta(name = "机构名", order = 1)
    private String name;    //机构名

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
