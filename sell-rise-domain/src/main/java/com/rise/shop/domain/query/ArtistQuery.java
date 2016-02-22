package com.rise.shop.domain.query;

import com.rise.shop.domain.ano.FieldMeta;
import com.rise.shop.persistence.query.DefaultBaseQuery;

/**
 * Created by wangdi on 15-1-9.
 */
public class ArtistQuery extends DefaultBaseQuery {
    @FieldMeta(name = "姓名", order = 1)
    private String name;    //姓名

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
