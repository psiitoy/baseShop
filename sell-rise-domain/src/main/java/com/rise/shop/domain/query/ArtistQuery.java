package com.rise.shop.domain.query;

import com.rise.shop.common.ano.ViewMeta;
import com.rise.shop.persistence.query.DefaultBaseQuery;

/**
 * Created by wangdi on 15-1-9.
 */
public class ArtistQuery extends DefaultBaseQuery {
    @ViewMeta(name = "姓名", order = 1)
    private String name;    //姓名
    private Long ageSymbolGt;
    private Long ageSymbolGte;
    private Long ageSymbolLt;
    private Long ageSymbolLte;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAgeSymbolGt() {
        return ageSymbolGt;
    }

    public void setAgeSymbolGt(Long ageSymbolGt) {
        this.ageSymbolGt = ageSymbolGt;
    }

    public Long getAgeSymbolGte() {
        return ageSymbolGte;
    }

    public void setAgeSymbolGte(Long ageSymbolGte) {
        this.ageSymbolGte = ageSymbolGte;
    }

    public Long getAgeSymbolLt() {
        return ageSymbolLt;
    }

    public void setAgeSymbolLt(Long ageSymbolLt) {
        this.ageSymbolLt = ageSymbolLt;
    }

    public Long getAgeSymbolLte() {
        return ageSymbolLte;
    }

    public void setAgeSymbolLte(Long ageSymbolLte) {
        this.ageSymbolLte = ageSymbolLte;
    }
}
