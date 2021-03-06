package com.rise.shop.domain.query;

import com.rise.shop.common.ano.ViewMeta;
import com.rise.shop.persistence.query.BaseQuery;

/**
 * Created by wangdi on 15-1-9.
 */
public class ArtistWorkQuery extends BaseQuery {
    @ViewMeta(name = "作者名", order = 1)
    private String artistName;  //作者名
    @ViewMeta(name = "作品名", order = 2)
    private String workName;    //作品名

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }
}
